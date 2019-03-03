package mn.foreman.autominer;

import mn.foreman.ccminer.CcminerFactory;
import mn.foreman.model.MinerFactory;
import mn.foreman.trex.TrexFactory;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * A {@link MinerType} provides an enumeration of all of the known autominer
 * types.
 */
public enum MinerType {

    /** trex. */
    TREX("trex", new TrexFactory()),

    /** cryptodredge. */
    CRYPTODREDGE("cryptodredge", new CcminerFactory()),

    /** ccminer. */
    CCMINER("ccminer", new CcminerFactory()),

    /** Unknown miner type. */
    UNKNOWN("NONE", null);

    /** A mapping of {@link #amType} to {@link MinerType}. */
    private static final Map<String, MinerType> MAPPINGS;

    static {
        MAPPINGS = new ConcurrentHashMap<>();
        for (final MinerType minerType : values()) {
            MAPPINGS.put(minerType.getType(), minerType);
        }
    }

    /** The autominer response miner type. */
    private final String amType;

    /** The factory for creating new miners. */
    private final MinerFactory minerFactory;

    /**
     * Constructor.
     *
     * @param amType       The type.
     * @param minerFactory The factory.
     */
    MinerType(
            final String amType,
            final MinerFactory minerFactory) {
        this.amType = amType;
        this.minerFactory = minerFactory;
    }

    /**
     * Returns the type for the provided value.
     *
     * @param value The value.
     *
     * @return The type.
     */
    @JsonCreator
    public static MinerType forValue(final String value) {
        return MAPPINGS.getOrDefault(value, UNKNOWN);
    }

    /**
     * Returns the {@link MinerFactory} for this {@link #amType}.
     *
     * @return The type.
     */
    public Optional<MinerFactory> getFactory() {
        return Optional.ofNullable(this.minerFactory);
    }

    /**
     * Returns the type.
     *
     * @return The type.
     */
    public String getType() {
        return this.amType;
    }
}