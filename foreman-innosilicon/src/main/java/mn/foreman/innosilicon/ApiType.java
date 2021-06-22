package mn.foreman.innosilicon;

/** An Innosilicon API type. */
public enum ApiType {

    /** API that returns GHs in the MHS field. */
    GHS_API(1000),

    /** API that returns KHs in the MHS field. */
    KHS_API(0.001),

    /** API that returns MHs in the MHS field. */
    MHS_API(1),

    /** API that returns Hs in the MHS field. */
    HS_API(0.000001);

    /**
     * How much to multiply the rates that are returned by to get them to match
     * the label units.
     */
    private final double multiplier;

    /**
     * Constructor.
     *
     * @param multiplier The multiplier.
     */
    ApiType(final double multiplier) {
        this.multiplier = multiplier;
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
