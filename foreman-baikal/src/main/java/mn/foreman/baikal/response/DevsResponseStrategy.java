package mn.foreman.baikal.response;

import mn.foreman.cgminer.PoolsResponseStrategy;
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
 * A {@link PoolsResponseStrategy} provides a {@link ResponseStrategy}
 * implementation that's capable of parsing a {@link CgMinerCommand#DEVS}
 * response from a baikal.
 */
public class DevsResponseStrategy
        implements ResponseStrategy {

    /** The logger for this class. */
    private static final Logger LOG =
            LoggerFactory.getLogger(DevsResponseStrategy.class);

    @Override
    public void processResponse(
            final MinerStats.Builder builder,
            final CgMinerResponse response) {
        if (response.hasValues()) {
            final List<Map<String, String>> values = response.getValues();
            if (hasAsics(values)) {
                addAsic(
                        values,
                        builder);
            }
        } else {
            LOG.debug("Received an empty response");
        }
    }

    /**
     * Adds all of the ASICs from the provided values.
     *
     * @param values       The values.
     * @param statsBuilder The builder.
     */
    private static void addAsic(
            final List<Map<String, String>> values,
            final MinerStats.Builder statsBuilder) {
        final List<Map<String, String>> asicValues =
                values
                        .stream()
                        .filter((map) -> map.containsKey("ASC"))
                        .collect(Collectors.toList());
        final Asic.Builder asicBuilder =
                new Asic.Builder()
                        .setHashRate(toRate(asicValues))
                        .hasErrors(toErrors(asicValues))
                        .setFanInfo(
                                new FanInfo.Builder()
                                        .setCount(0)
                                        .setSpeedUnits("%")
                                        .build());
        asicValues
                .stream()
                .map((map) -> map.get("Temperature"))
                .map(Double::parseDouble)
                .map(Double::intValue)
                .forEach(asicBuilder::addTemp);
        statsBuilder.addAsic(asicBuilder.build());
    }

    /**
     * Checks to see if the values have ASICs.
     *
     * @param values The values.
     *
     * @return Whether or not ASICs exist.
     */
    private static boolean hasAsics(
            final List<Map<String, String>> values) {
        return values
                .stream()
                .anyMatch((map) -> map.containsKey("ASC"));
    }

    /**
     * Determines whether or not there are hardware errors.
     *
     * @param values The values to examine.
     *
     * @return Whether or not there are hardware errors.
     */
    private static Boolean toErrors(
            final List<Map<String, String>> values) {
        return values
                .stream()
                .mapToInt(
                        (map) ->
                                Integer.parseInt(
                                        map.get("Hardware Errors")))
                .sum() > 0;
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
                .map((map) -> new BigDecimal(map.get("MHS 5s")))
                .map((value) ->
                        value.multiply(
                                new BigDecimal(1000 * 1000)))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}