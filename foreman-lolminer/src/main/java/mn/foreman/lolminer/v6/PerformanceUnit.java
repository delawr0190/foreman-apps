package mn.foreman.lolminer.v6;

import java.math.BigDecimal;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/** The units that lolminer can return. */
public enum PerformanceUnit {

    /** Sols. */
    SOLS("sol/s", BigDecimal.ONE),

    /** Hs. */
    HS("h/s", BigDecimal.ONE),

    /** KHs. */
    KHS("kh/s", new BigDecimal(1000)),

    /** MHs. */
    MHS("mh/s", new BigDecimal(1000 * 1000)),

    /** GHs. */
    GHS("gh/s", new BigDecimal(1000 * 1000 * 1000)),

    /** Graphs. */
    GRAPHS("g/s", BigDecimal.ONE);

    /** The units. */
    private static final Map<String, PerformanceUnit> UNITS =
            new ConcurrentHashMap<>();

    static {
        for (final PerformanceUnit units : values()) {
            UNITS.put(units.units, units);
        }
    }

    /** The multiplier. */
    private final BigDecimal multiplier;

    /** The units. */
    private final String units;

    /**
     * Constructor.
     *
     * @param units      The units.
     * @param multiplier The multiplier.
     */
    PerformanceUnit(
            final String units,
            final BigDecimal multiplier) {
        this.units = units;
        this.multiplier = multiplier;
    }

    /**
     * Returns the unit.
     *
     * @param units The units.
     *
     * @return The unit.
     */
    public static PerformanceUnit toUnit(final String units) {
        if (units != null) {
            return UNITS.getOrDefault(units, HS);
        }
        return HS;
    }

    /**
     * Returns the multiplier.
     *
     * @return The multiplier.
     */
    public BigDecimal getMultiplier() {
        return this.multiplier;
    }
}
