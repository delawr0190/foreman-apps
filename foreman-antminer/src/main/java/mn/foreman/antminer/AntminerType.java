package mn.foreman.antminer;

import mn.foreman.model.MinerType;

import java.math.BigDecimal;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/** A {@link MinerType} that represents Antminers. */
public enum AntminerType
        implements MinerType {

    /** An Antminer A3. */
    ANTMINER_A3(
            "antminer_a3",
            // Already in GHs
            BigDecimal.ONE),

    /** An Antminer B3. */
    ANTMINER_B3(
            "antminer_b3",
            // Need to convert Hs to GHs
            new BigDecimal(0.000000001)),

    /** An Antminer D3. */
    ANTMINER_D3(
            "antminer_d3",
            // Need to convert MHs to GHs
            new BigDecimal(0.001)),

    /** An Antminer E3. */
    ANTMINER_E3(
            "antminer_e3",
            // Need to convert MHs to GHs
            new BigDecimal(0.001)),

    /** An Antminer L3. */
    ANTMINER_L3(
            "antminer_l3",
            // Need to convert MHs to GHs
            new BigDecimal(0.001)),

    /** An Antminer S9. */
    ANTMINER_S9(
            "antminer_s9",
            // Already in GHs
            BigDecimal.ONE),

    /** An Antminer S17. */
    ANTMINER_S17(
            "antminer_s17",
            BigDecimal.ONE),

    /** An Antminer T9. */
    ANTMINER_T9(
            "antminer_t9",
            // Already in GHs
            BigDecimal.ONE),

    /** An Antminer Z9. */
    ANTMINER_Z9(
            "antminer_z9",
            // Need to convert KHs to GHs
            new BigDecimal(0.000001)),

    /** An Antminer Z9 mini. */
    ANTMINER_Z9_MINI(
            "antminer_z9_mini",
            // Need to convert KHs to GHs
            new BigDecimal(0.000001));

    /** A {@link Map} of labels to types. */
    private static final Map<String, AntminerType> VALUES =
            new ConcurrentHashMap<>();

    static {
        for (final AntminerType type : values()) {
            VALUES.put(type.getLabel(), type);
        }
    }

    /** The label for the type. */
    private final String label;

    /**
     * How much to multiply the rates that are returned by to get them to match
     * the label units (cgminer API returns rates in GHs regardless of the
     * actual rate).
     */
    private final BigDecimal multiplier;

    /**
     * Constructor.
     *
     * @param label      The label.
     * @param multiplier The multiplier.
     */
    AntminerType(
            final String label,
            final BigDecimal multiplier) {
        this.label = label;
        this.multiplier = multiplier;
    }

    /**
     * Returns the type for the provided label.
     *
     * @param label The label.
     *
     * @return The type.
     */
    public static AntminerType forLabel(final String label) {
        return VALUES.get(label);
    }

    @Override
    public String getLabel() {
        return this.label;
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
