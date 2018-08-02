package mn.foreman.antminer;

import mn.foreman.model.MinerType;

import java.math.BigDecimal;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/** A {@link MinerType} that represents Antminers. */
public enum AntminerType
        implements MinerType {

    /** An Antminer B3. */
    ANTMINER_B3(
            "antminer_b3",
            // Need to convert Hs to GHs
            new BigDecimal(0.000000001)),

    ANTMINER_D3(
            "antminer_d3",
            // Need to convert MHs to GHs
            new BigDecimal(0.001)),

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
            new BigDecimal(1));

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
