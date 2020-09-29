package mn.foreman.cgminer;

import mn.foreman.cgminer.response.CgMinerResponse;
import mn.foreman.model.miners.MinerStats;
import mn.foreman.model.miners.asic.Asic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.function.Supplier;

/**
 * An {@link AggregatingResponseStrategy} provides a {@link ResponseStrategy}
 * implementation that aggregates multiple cgminer responses together to produce
 * a single {@link Asic}.
 *
 * @param <T> The context type.
 */
public class AggregatingResponseStrategy<T extends AggregationContext>
        implements ResponseStrategy {

    /** The logger for this class. */
    private static final Logger LOG =
            LoggerFactory.getLogger(AggregatingResponseStrategy.class);

    /** The context. */
    private final Context context;

    /** The factory for creating new contexts. */
    private final Supplier<T> contextFactory;

    /** All of the strategies that have been invoked over the current round. */
    private final Set<AggregatingStrategy<T>> observedResponses =
            new HashSet<>();

    /** The mapping of response key to strategy. */
    private final Map<String, AggregatingStrategy<T>> responseMapping;

    /** The current, active context. */
    private T activeContext;

    /** The active builder. */
    private Asic.Builder asicBuilder;

    /**
     * Constructor.
     *
     * @param responseMapping The mapping of response key to strategy.
     * @param contextFactory  The factory for creating new contexts.
     * @param context         The context.
     */
    public AggregatingResponseStrategy(
            final Map<String, AggregatingStrategy<T>> responseMapping,
            final Supplier<T> contextFactory,
            final Context context) {
        this.responseMapping = new HashMap<>(responseMapping);
        this.contextFactory = contextFactory;
        this.context = context;
        reset();
    }

    @Override
    public void processResponse(
            final MinerStats.Builder builder,
            final CgMinerResponse response) {
        if (response.hasValues()) {
            final Map<String, List<Map<String, String>>> values =
                    response.getValues();
            final Optional<AggregatingStrategy<T>> strategyOptional =
                    getStrategy(values);
            if (strategyOptional.isPresent()) {
                final AggregatingStrategy<T> strategy =
                        strategyOptional.get();

                // Shouldn't have seen this one before - if we did, then
                // we're out of sync
                if (this.observedResponses.contains(strategy)) {
                    LOG.warn("Responses have fallen out of sync - resetting");
                    reset();
                }

                strategy.process(
                        values,
                        this.asicBuilder,
                        this.activeContext);
                this.observedResponses.add(strategy);
            } else {
                LOG.info("Received a response type with no known mapping");
            }

            if (isComplete()) {
                // Context data
                this.context.getSimple(ContextKey.MRR_RIG_ID)
                        .ifPresent(asicBuilder::setMrrRigId);
                this.context.getMulti(ContextKey.RAW_STATS)
                        .ifPresent(asicBuilder::addRawStats);

                builder.addAsic(this.asicBuilder.build());
                reset();
            }
        }
    }

    /**
     * Returns the strategy for the key.
     *
     * @param values The values to inspect.
     *
     * @return The strategy, if one exists.
     */
    private Optional<AggregatingStrategy<T>> getStrategy(
            final Map<String, List<Map<String, String>>> values) {
        return values
                .keySet()
                .stream()
                .map(String::toLowerCase)
                .filter(key ->
                        this.responseMapping
                                .keySet()
                                .stream()
                                .map(String::toLowerCase)
                                .anyMatch(key::startsWith))
                .map(key ->
                        this.responseMapping
                                .entrySet()
                                .stream()
                                .filter(entry -> key.startsWith(entry.getKey().toLowerCase()))
                                .findFirst())
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(Map.Entry::getValue)
                .findFirst();
    }

    /**
     * Returns whether or not the strategy is complete.
     *
     * @return Whether or not the strategy is complete.
     */
    private boolean isComplete() {
        return (this.observedResponses.size() == this.responseMapping.size());
    }

    /** Resets the state of this class (new metrics round started). */
    private void reset() {
        this.observedResponses.clear();
        this.asicBuilder = new Asic.Builder();
        this.activeContext = this.contextFactory.get();
    }
}