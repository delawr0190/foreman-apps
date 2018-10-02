package mn.foreman.innosilicon;

import java.math.BigDecimal;

/** An Innosilicon API type. */
public enum ApiType {

    /** API that returns GHs in the MHS field. */
    GHS_API(new BigDecimal(1000)),

    /** API that returns KHs in the MHS field. */
    KHS_API(new BigDecimal(0.001)),

    /** API that returns MHs in the MHS field. */
    MHS_API(BigDecimal.ONE),

    /** API that returns Hs in the MHS field. */
    HS_API(new BigDecimal(0.000001));

    /**
     * How much to multiply the rates that are returned by to get them to match
     * the label units.
     */
    private final BigDecimal multiplier;

    /**
     * Constructor.
     *
     * @param multiplier The multiplier.
     */
    ApiType(final BigDecimal multiplier) {
        this.multiplier = multiplier;
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
