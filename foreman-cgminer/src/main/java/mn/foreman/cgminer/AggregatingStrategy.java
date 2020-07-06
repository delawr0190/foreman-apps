package mn.foreman.cgminer;

import mn.foreman.model.miners.asic.Asic;

import java.util.List;
import java.util.Map;

/**
 * An {@link AggregatingStrategy} provides a strategy for processing cgminer
 * response values.
 *
 * @param <T> The context type.
 */
public interface AggregatingStrategy<T extends AggregationContext> {

    /**
     * Processes the cgminer response values.
     *
     * @param values  The values.
     * @param builder The builder being updated.
     * @param context The current context.
     */
    void process(
            Map<String, List<Map<String, String>>> values,
            Asic.Builder builder,
            T context);
}
