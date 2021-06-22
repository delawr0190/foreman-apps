package mn.foreman.cgminer;

import mn.foreman.cgminer.response.CgMinerResponse;
import mn.foreman.model.error.MinerException;
import mn.foreman.model.miners.MinerStats;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A {@link RateMultiplyingDecorator} provides a decorator that fixes an error
 * in cgminer forks that incorrectly put the hash rate from every ASIC into the
 * hash rate field despite the actual hash rates being different units.
 *
 * <p>This class patches that by multiplying the obtained hash rate by a {@link
 * #multiplier}, which will convert the hash rate to the desired units so it can
 * be properly interpreted.</p>
 */
public class RateMultiplyingDecorator
        implements ResponseStrategy {

    /** The hash rate key. */
    private final String hashRateKey;

    /** The rate multiplier. */
    private final double multiplier;

    /** The object key. */
    private final String objectKey;

    /** The real {@link ResponseStrategy}. */
    private final ResponseStrategy real;

    /**
     * Constructor.
     *
     * @param objectKey   The object key.
     * @param hashRateKey The hash rate key.
     * @param multiplier  The rate multiplier.
     * @param real        The real strategy.
     */
    public RateMultiplyingDecorator(
            final String objectKey,
            final String hashRateKey,
            final double multiplier,
            final ResponseStrategy real) {
        this.objectKey = objectKey;
        this.hashRateKey = hashRateKey;
        this.multiplier = multiplier;
        this.real = real;
    }

    @Override
    public void processResponse(
            final MinerStats.Builder builder,
            final CgMinerResponse response)
            throws MinerException {
        final Map<String, List<Map<String, String>>> origValues =
                response.getValues();

        final CgMinerResponse.Builder responseBuilder =
                new CgMinerResponse.Builder()
                        .setRequest(response.getRequest())
                        .addStatus(response.getStatus());

        for (final Map.Entry<String, List<Map<String, String>>> entry :
                origValues.entrySet()) {
            final String key = entry.getKey();
            if (key.equals(this.objectKey)) {
                entry.getValue()
                        .stream()
                        .map(this::multiplyRate)
                        .forEach(value ->
                                responseBuilder.addValues(
                                        key,
                                        value));
            } else {
                entry.getValue().forEach(
                        value ->
                                responseBuilder.addValues(
                                        key,
                                        value));
            }
        }

        this.real.processResponse(
                builder,
                responseBuilder.build());
    }

    /**
     * Checks to see if the provided {@link Map} contains the provided key, and
     * if so, multiplies the value associated with that key by the {@link
     * #multiplier}.
     *
     * @param values The values.
     * @param key    The key.
     */
    private void multiplyIfContains(
            final Map<String, String> values,
            final String key) {
        if (values.containsKey(key)) {
            final double rate = Double.parseDouble(values.get(key));
            if (rate > 0) {
                // Only scale the value if the rate isn't 0
                values.put(
                        key,
                        Double.toString(rate * this.multiplier));
            }
        }
    }

    /**
     * Multiplies the rates to convert them if they're incorrect.
     *
     * @param values The values.
     *
     * @return The new, corrected values.
     */
    private Map<String, String> multiplyRate(final Map<String, String> values) {
        final Map<String, String> newValues = new HashMap<>(values);
        multiplyIfContains(newValues, this.hashRateKey);
        return newValues;
    }
}
