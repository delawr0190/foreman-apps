package mn.foreman.futurebit.response;

import mn.foreman.cgminer.Context;
import mn.foreman.cgminer.ContextKey;
import mn.foreman.cgminer.ResponseStrategy;
import mn.foreman.cgminer.request.CgMinerCommand;
import mn.foreman.cgminer.response.CgMinerResponse;
import mn.foreman.model.miners.FanInfo;
import mn.foreman.model.miners.MinerStats;
import mn.foreman.model.miners.asic.Asic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * A {@link DevsResponseStrategy} provides a {@link ResponseStrategy}
 * implementation that's capable of parsing a {@link CgMinerCommand#DEVS}
 * response from a futurebit.
 */
public class DevsResponseStrategy
        implements ResponseStrategy {

    /** The logger for this class. */
    private static final Logger LOG =
            LoggerFactory.getLogger(DevsResponseStrategy.class);

    /** MHs 20s. */
    private static final String MHS_20_KEY = "MHS 20s";

    /** Sets the context. */
    private final Context context;

    /**
     * Constructor.
     *
     * @param context The context.
     */
    public DevsResponseStrategy(final Context context) {
        this.context = context;
    }

    @Override
    public void processResponse(
            final MinerStats.Builder builder,
            final CgMinerResponse response) {
        if (response.hasValues()) {
            final List<Map<String, String>> values =
                    response.getValues()
                            .entrySet()
                            .stream()
                            .filter(entry -> entry.getKey().equals("DEVS"))
                            .map(Map.Entry::getValue)
                            .flatMap(List::stream)
                            .collect(Collectors.toList());
            if (hasPgas(values)) {
                addPga(
                        values,
                        builder);
            }
        } else {
            LOG.debug("Received an empty response");
        }
    }

    /**
     * Checks to see if the values have PGAs.
     *
     * @param values The values.
     *
     * @return Whether or not PGAs exist.
     */
    private static boolean hasPgas(
            final List<Map<String, String>> values) {
        return values
                .stream()
                .anyMatch((map) -> map.containsKey("PGA"));
    }

    /**
     * Extracts the hash rate.
     *
     * @param values The values.
     *
     * @return The rate.
     */
    private static BigDecimal toRate(
            final List<Map<String, String>> values) {
        return values
                .stream()
                .map(entry -> entry.get(MHS_20_KEY))
                .map(BigDecimal::new)
                .map((value) ->
                        value.multiply(
                                new BigDecimal(1000 * 1000)))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    /**
     * Adds all of the PGAs from the provided values.
     *
     * @param values       The values.
     * @param statsBuilder The builder.
     */
    private void addPga(
            final List<Map<String, String>> values,
            final MinerStats.Builder statsBuilder) {
        final List<Map<String, String>> pgaValues =
                values
                        .stream()
                        .filter((map) -> map.containsKey("PGA"))
                        .collect(Collectors.toList());
        final Asic.Builder asicBuilder =
                new Asic.Builder()
                        .setHashRate(toRate(pgaValues))
                        .setFanInfo(
                                new FanInfo.Builder()
                                        .setCount(0)
                                        .setSpeedUnits("RPM")
                                        .build())
                        .hasErrors(false);
        this.context.getSimple(ContextKey.MRR_RIG_ID)
                .ifPresent(asicBuilder::setMrrRigId);
        statsBuilder.addAsic(asicBuilder.build());
    }
}