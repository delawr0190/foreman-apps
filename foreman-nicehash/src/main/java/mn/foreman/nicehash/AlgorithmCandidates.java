package mn.foreman.nicehash;

import mn.foreman.model.Miner;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * An {@link AlgorithmCandidates} provides a mapping of a nicehash algorithm to
 * all possible mining candidates for that algorithm.
 */
public class AlgorithmCandidates {

    /** The algorithm map. */
    private final Map<Integer, List<Miner>> algoMap;

    /**
     * Constructor.
     *
     * @param builder The reference builder.
     */
    private AlgorithmCandidates(final Builder builder) {
        this.algoMap = new ConcurrentHashMap<>(builder.algoMap);
    }

    @Override
    public boolean equals(final Object other) {
        boolean isEqual = false;
        if (other == this) {
            isEqual = true;
        } else if ((other != null) && (getClass() == other.getClass())) {
            final AlgorithmCandidates algorithmCandidates =
                    (AlgorithmCandidates) other;
            isEqual =
                    new EqualsBuilder()
                            .append(this.algoMap,
                                    algorithmCandidates.algoMap)
                            .isEquals();
        }
        return isEqual;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(this.algoMap)
                .hashCode();
    }

    @Override
    public String toString() {
        final StringBuilder stringBuilder = new StringBuilder();
        this.algoMap.forEach((key, value) -> {
            stringBuilder
                    .append(key)
                    .append(" -> [ ");
            value.forEach(stringBuilder::append);
            stringBuilder.append(" ] ");
        });
        return String.format(
                "%s [ mappings=[ %s ] ]",
                getClass().getSimpleName(),
                stringBuilder.toString());
    }

    /**
     * Returns the {@link Miner Miners} for the provided algorithm.
     *
     * @param algo The algorithm.
     *
     * @return The {@link Miner Miners}.
     */
    List<Miner> getForAlgo(final int algo) {
        return this.algoMap.getOrDefault(algo, new LinkedList<>());
    }

    /** A builder for creating new mappings. */
    public static class Builder {

        /** The algorithm mapping. */
        private final Map<Integer, List<Miner>> algoMap =
                new ConcurrentHashMap<>();

        /**
         * Adds the candidate miner for the provided algorithm.
         *
         * @param algo  The algorithm.
         * @param miner The miner.
         *
         * @return This builder instance.
         */
        public Builder addCandidate(
                final int algo,
                final Miner miner) {
            final List<Miner> candidates =
                    this.algoMap.computeIfAbsent(
                            algo,
                            newAlgo -> new LinkedList<>());
            candidates.add(miner);
            return this;
        }

        /**
         * Creates the new candidates.
         *
         * @return The new candidates.
         */
        public AlgorithmCandidates build() {
            return new AlgorithmCandidates(this);
        }
    }
}