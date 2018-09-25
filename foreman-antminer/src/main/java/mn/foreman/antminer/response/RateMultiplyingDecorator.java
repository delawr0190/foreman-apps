package mn.foreman.antminer.response;

import mn.foreman.cgminer.ResponseStrategy;
import mn.foreman.cgminer.response.CgMinerResponse;
import mn.foreman.model.miners.MinerStats;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A {@link RateMultiplyingDecorator} provides a decorator that fixes an error
 * in the cgminer fork used by Bitmain that incorrectly puts the hash rate from
 * every Antminer into the "GHS av" and "GHS 5s" fields, marked as GHs, despite
 * the actual hash rate being a different unit.
 *
 * <p>This class patches that by multiplying the obtained hash rate by a {@link
 * #multiplier}, which will convert the hash rate to GHs so it can be properly
 * interpreted.</p>
 */
public class RateMultiplyingDecorator
        implements ResponseStrategy {

    /** The rate multiplier. */
    private final BigDecimal multiplier;

    /** The real {@link ResponseStrategy}. */
    private final ResponseStrategy real;

    /**
     * Constructor.
     *
     * @param multiplier The rate multiplier.
     * @param real       The real strategy.
     */
    public RateMultiplyingDecorator(
            final BigDecimal multiplier,
            final ResponseStrategy real) {
        this.multiplier = multiplier;
        this.real = real;
    }

    @Override
    public void processResponse(
            final MinerStats.Builder builder,
            final CgMinerResponse response) {
        final List<Map<String, String>> origValues =
                response.getValues();

        final CgMinerResponse.Builder responseBuilder =
                new CgMinerResponse.Builder()
                        .setId(response.getId())
                        .setStatusSection(response.getStatusSection());
        origValues
                .stream()
                .map(this::multiplyRate)
                .forEach(responseBuilder::addValues);

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
            final BigDecimal rate = new BigDecimal(values.get(key));
            // Only scale the value if the rate isn't 0
            if (rate.compareTo(BigDecimal.ZERO) != 0) {
                values.put(
                        key,
                        new BigDecimal(values.get(key))
                                .multiply(this.multiplier)
                                .toString());
            }
        }
    }

    /**
     * Multiplies the rates to convert them to GHs if they're incorrect.
     *
     * @param values The values.
     *
     * @return The new, corrected values.
     */
    private Map<String, String> multiplyRate(final Map<String, String> values) {
        final Map<String, String> newValues = new HashMap<>(values);
        multiplyIfContains(newValues, "GHS av");
        multiplyIfContains(newValues, "GHS 5s");
        return newValues;
    }
}
