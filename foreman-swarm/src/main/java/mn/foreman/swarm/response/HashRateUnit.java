package mn.foreman.swarm.response;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.math.BigDecimal;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/** The hash rate units as reported by swarm. */
public enum HashRateUnit {

    /** Hs. */
    HS("hs", BigDecimal.ONE),

    /** KHs. */
    KHS("khs", new BigDecimal("1000")),

    /** MHs. */
    MHS("mhs", new BigDecimal("1000000")),

    /** GHs. */
    GHS("ghs", new BigDecimal("1000000000")),

    /** THs. */
    THS("ths", new BigDecimal("1000000000000")),

    /** Unknown. */
    UNKNOWN("unknown", BigDecimal.ONE);

    /** The mappings. */
    private static final Map<String, HashRateUnit> MAPPINGS;

    static {
        MAPPINGS = new ConcurrentHashMap<>();
        for (final HashRateUnit hashRateUnit : values()) {
            MAPPINGS.put(hashRateUnit.key, hashRateUnit);
        }
    }

    /** The key. */
    private final String key;

    /** The multiplier. */
    private final BigDecimal multiplier;

    /**
     * Constructor.
     *
     * @param key The key.
     */
    HashRateUnit(
            final String key,
            final BigDecimal multiplier) {
        this.key = key;
        this.multiplier = multiplier;
    }

    /**
     * Gets the {@link HashRateUnit} for the provided key.
     *
     * @param key The key.
     *
     * @return The unit.
     */
    @JsonCreator
    public static HashRateUnit fromKey(final String key) {
        return MAPPINGS.getOrDefault(key, UNKNOWN);
    }

    /**
     * Converts the provided value to Hs.
     *
     * @param value The value.
     *
     * @return The rate in Hs.
     */
    public BigDecimal toHs(final BigDecimal value) {
        return value.multiply(this.multiplier);
    }

    @Override
    public String toString() {
        return this.key;
    }
}
