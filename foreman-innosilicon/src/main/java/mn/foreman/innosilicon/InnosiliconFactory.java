package mn.foreman.innosilicon;

import mn.foreman.cgminer.*;
import mn.foreman.cgminer.request.CgMinerCommand;
import mn.foreman.cgminer.request.CgMinerRequest;
import mn.foreman.model.Miner;
import mn.foreman.model.MinerFactory;
import mn.foreman.model.miners.FanInfo;
import mn.foreman.model.miners.asic.Asic;

import com.google.common.collect.ImmutableMap;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * A {@link MinerFactory} implementation that parses a configuration and creates
 * a {@link Miner} that will query an Innosilicon.
 */
public class InnosiliconFactory
        implements MinerFactory {

    /** The api type. */
    private final ApiType apiType;

    /**
     * Constructor.
     *
     * @param apiType The api type.
     */
    public InnosiliconFactory(final ApiType apiType) {
        this.apiType = apiType;
    }

    @Override
    public Miner create(final Map<String, String> config) {
        final Context context = new Context();
        final ResponseStrategy responseStrategy =
                createResponseStrategy(
                        this.apiType,
                        context);
        return new CgMiner.Builder()
                .setApiIp(config.get("apiIp"))
                .setApiPort(config.get("apiPort"))
                .addRequest(
                        new CgMinerRequest.Builder()
                                .setCommand(CgMinerCommand.POOLS)
                                .build(),
                        new PoolsResponseStrategy(
                                new MrrRigIdCallback(context)))
                .addRequest(
                        new CgMinerRequest.Builder()
                                .setCommand(CgMinerCommand.SUMMARY)
                                .build(),
                        responseStrategy)
                .addRequest(
                        new CgMinerRequest.Builder()
                                .setCommand(CgMinerCommand.STATS)
                                .build(),
                        responseStrategy)
                .build();
    }

    /**
     * Creates a strategy that will correct the hash rate returned by the API
     * and aggregate summary and stats responses to extract {@link Asic ASICs}.
     *
     * @param apiType   The type.
     * @param cgContext The context.
     *
     * @return The strategy.
     */
    private static ResponseStrategy createResponseStrategy(
            final ApiType apiType,
            final Context cgContext) {
        return new RateMultiplyingDecorator(
                "SUMMARY",
                "MHS av",
                apiType.getMultiplier(),
                new AggregatingResponseStrategy<>(
                        ImmutableMap.of(
                                "SUMMARY",
                                (values, builder, context) -> updateSummary(values, builder),
                                "STATS",
                                (values, builder, context) -> updateStats(values, builder)),
                        () -> null,
                        cgContext));
    }

    /**
     * Determines whether or not the ASIC has errors.
     *
     * @param values The values to examine.
     *
     * @return Whether or not there are errors.
     */
    private static boolean hasErrors(final Map<String, String> values) {
        return Integer.parseInt(values.get("Num chips")) >
                Integer.parseInt(values.get("Num active chips"));
    }

    /**
     * Updates the builder with stats.
     *
     * @param values The response values.
     */
    private static void updateStats(
            final Map<String, List<Map<String, String>>> values,
            final Asic.Builder builder) {
        final List<Map<String, String>> asics =
                values
                        .entrySet()
                        .stream()
                        .filter(entry -> entry.getKey().startsWith("STATS"))
                        .map(Map.Entry::getValue)
                        .flatMap(List::stream)
                        .filter(value -> value.containsKey("Num chips"))
                        .collect(Collectors.toList());
        if (!asics.isEmpty()) {
            // Fans
            final List<Integer> fans =
                    asics
                            .stream()
                            .map(map -> map.get("Fan duty"))
                            .map(Integer::parseInt)
                            .collect(Collectors.toList());
            final FanInfo.Builder fanInfo =
                    new FanInfo.Builder()
                            .setCount(fans.size())
                            .setSpeedUnits("%");
            fans.forEach(fanInfo::addSpeed);
            builder.setFanInfo(fanInfo.build());

            // Temps
            asics.stream()
                    .map(map -> map.get("Temp"))
                    .forEach(builder::addTemp);

            // HW errors
            builder.hasErrors(
                    asics
                            .stream()
                            .filter(InnosiliconFactory::hasErrors)
                            .anyMatch(InnosiliconFactory::hasErrors));
        }
    }

    /**
     * Updates the builder with summary info.
     *
     * @param values The response values.
     */
    private static void updateSummary(
            final Map<String, List<Map<String, String>>> values,
            final Asic.Builder builder) {
        values.entrySet()
                .stream()
                .filter(entry -> "SUMMARY".equals(entry.getKey()))
                .map(Map.Entry::getValue)
                .flatMap(List::stream)
                .filter(map -> map.containsKey("MHS av"))
                .map(map -> new BigDecimal(map.get("MHS av")))
                .map(value ->
                        value.multiply(
                                new BigDecimal(1000 * 1000)))
                .forEach(builder::setHashRate);
    }
}