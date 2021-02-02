package mn.foreman.antminer.response.braiins;

import mn.foreman.cgminer.Context;
import mn.foreman.cgminer.ContextKey;
import mn.foreman.cgminer.ResponseStrategy;
import mn.foreman.cgminer.response.CgMinerResponse;
import mn.foreman.model.error.MinerException;
import mn.foreman.model.miners.FanInfo;
import mn.foreman.model.miners.MinerStats;
import mn.foreman.model.miners.asic.Asic;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * A {@link BraiinsResponseStrategy} provides a {@link ResponseStrategy}
 * implementation that processes a braiins OS response.
 */
public class BraiinsResponseStrategy
        implements ResponseStrategy {

    /** The context. */
    private final Context context;

    /** The temps. */
    private final List<String> temps = new LinkedList<>();

    /** The fans. */
    private FanInfo fanInfo;

    /** The hash rate. */
    private BigDecimal hashRate;

    /**
     * Constructor.
     *
     * @param context The context.
     */
    public BraiinsResponseStrategy(final Context context) {
        this.context = context;
    }

    @Override
    public void processResponse(
            final MinerStats.Builder builder,
            final CgMinerResponse response)
            throws MinerException {
        final Map<String, List<Map<String, String>>> values =
                response.getValues();
        for (final String key : values.keySet()) {
            switch (key) {
                case "SUMMARY":
                    processSummary(values.get("SUMMARY"));
                    break;
                case "FANS":
                    processFans(values.get("FANS"));
                    break;
                case "TEMPS":
                    processTemps(values.get("TEMPS"));
                    break;
                case "DEVS":
                    processDevs(
                            values.get("DEVS"),
                            builder);
                    break;
            }
        }
    }

    /**
     * Processes the device response.
     *
     * @param values  The values.
     * @param builder The builder.
     */
    private void processDevs(
            final List<Map<String, String>> values,
            final MinerStats.Builder builder) {
        // We know we're done after we see devs based on the ordering that
        // we defined the requests to be executed
        final Asic.Builder asicBuilder =
                new Asic.Builder()
                        .setBoards(
                                values
                                        .stream()
                                        .map(map -> map.getOrDefault("MHS av", "0"))
                                        .map(BigDecimal::new)
                                        .filter(value -> value.compareTo(BigDecimal.ZERO) > 0)
                                        .count())
                        .setHashRate(this.hashRate)
                        .setFanInfo(this.fanInfo)
                        .addTemps(this.temps);

        // Context data
        this.context.getSimple(ContextKey.MRR_RIG_ID)
                .ifPresent(asicBuilder::setMrrRigId);
        this.context.getMulti(ContextKey.RAW_STATS)
                .ifPresent(asicBuilder::addRawStats);

        builder.addAsic(asicBuilder.build());
    }

    /**
     * Processes a fans response.
     *
     * @param values The response values.
     */
    private void processFans(final List<Map<String, String>> values) {
        final List<String> speeds =
                values
                        .stream()
                        .map(map -> map.get("RPM"))
                        .filter(rpm -> !"0".equals(rpm))
                        .collect(Collectors.toList());
        final FanInfo.Builder fanBuilder =
                new FanInfo.Builder()
                        .setCount(speeds.size())
                        .setSpeedUnits("RPM");
        speeds.forEach(fanBuilder::addSpeed);
        this.fanInfo = fanBuilder.build();
    }

    /**
     * Processes a summary response.
     *
     * @param values The response values.
     *
     * @throws MinerException if too many summaries were found.
     */
    private void processSummary(final List<Map<String, String>> values)
            throws MinerException {
        if (values.size() == 1) {
            final Map<String, String> summary = values.get(0);
            this.hashRate =
                    new BigDecimal(summary.get("MHS 5s"))
                            .multiply(new BigDecimal(1000 * 1000));
        } else {
            throw new MinerException("Received too many summaries");
        }
    }

    /**
     * Process the temps response.
     *
     * @param values The values.
     */
    private void processTemps(final List<Map<String, String>> values) {
        this.temps.clear();
        values.forEach(map -> {
            this.temps.add(map.getOrDefault("Board", "0"));
            this.temps.add(map.getOrDefault("Chip", "0"));
        });
    }
}