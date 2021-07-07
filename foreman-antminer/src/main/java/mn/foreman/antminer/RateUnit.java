package mn.foreman.antminer;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/** A representation of an Antminer rate_unit. */
public enum RateUnit {

    /** THs. */
    THS("TH", 1_000_000_000_000D),

    /** GHs. */
    GHS("GH", 1_000_000_000D),

    /** MH. */
    MHS("MH", 1_000_000D),

    /** KH. */
    KHS("KH", 1_000D),

    /** H. */
    HS("H", 1D);

    /** All of the know units. */
    private static final ConcurrentMap<String, RateUnit> MAP =
            new ConcurrentHashMap<>();

    static {
        for (final RateUnit rateUnit : values()) {
            MAP.put(rateUnit.unit, rateUnit);
        }
    }

    /** The multiplier. */
    private final double multiplier;

    /** The unit. */
    private final String unit;

    /**
     * Constructor.
     *
     * @param unit       The unit.
     * @param multiplier The multiplier.
     */
    RateUnit(
            final String unit,
            final double multiplier) {
        this.unit = unit;
        this.multiplier = multiplier;
    }

    /**
     * Returns the unit.
     *
     * @param unit The unit slug.
     *
     * @return The unit.
     */
    public static RateUnit toUnit(final String unit) {
        if (unit != null && !unit.isEmpty()) {
            return MAP.getOrDefault(unit, HS);
        }
        return HS;
    }

    /**
     * Returns the multiplier.
     *
     * @return The multiplier.
     */
    public double getMultiplier() {
        return this.multiplier;
    }
}
