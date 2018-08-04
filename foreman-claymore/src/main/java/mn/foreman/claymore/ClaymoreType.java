package mn.foreman.claymore;

import java.math.BigDecimal;

/** A type of claymore. */
public enum ClaymoreType {

    /** claymore-eth. */
    ETH(new BigDecimal(1000)),

    /** claymore-zec. */
    ZEC(new BigDecimal(1)),

    /** claymore-xmr. */
    XMR(new BigDecimal(1));

    /** The hash rate multiplier. */
    private final BigDecimal multiplier;

    /**
     * Constructor.
     *
     * @param multiplier The multiplier.
     */
    ClaymoreType(final BigDecimal multiplier) {
        this.multiplier = multiplier;
    }

    /**
     * Returns the hash rate multiplier to convert to KHs.
     *
     * @return The hash rate multiplier.
     */
    public BigDecimal getMultiplier() {
        return this.multiplier;
    }
}
