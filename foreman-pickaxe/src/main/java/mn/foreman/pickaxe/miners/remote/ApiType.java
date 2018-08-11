package mn.foreman.pickaxe.miners.remote;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * An API type represents a FOREMAN server response that indicates the miner API
 * type.
 */
public enum ApiType {

    /** bminer. */
    BMINER_API(0),

    /** castxmr. */
    CASTXMR_API(1),

    /** ccminer. */
    CCMINER_API(2),

    /** dstm. */
    DSTM_API(3),

    /** ethminer. */
    ETHMINER_API(4),

    /** ewbf. */
    EWBF_API(5),

    /** excavator. */
    EXCAVATOR_API(6),

    /** jceminer. */
    JCEMINER_API(7),

    /** lolminer. */
    LOLMINER_API(8),

    /** sgminer. */
    SGMINER_API(9),

    /** srbminer. */
    SRBMINER_API(10),

    /** trex. */
    TREX_API(11),

    /** xmrig */
    XMRIG_API(12),

    /** xmrstak. */
    XMRSTAK_API(13),

    /** claymore-eth. */
    CLAYMORE_ETH_API(14),

    /** claymore-zec. */
    CLAYMORE_ZEC_API(15),

    /** Antminer API that reports rates in Hs. */
    ANTMINER_HS_API(16),

    /** Antminer API that reports rates in MHs. */
    ANTMINER_MHS_API(17),

    /** Antminer API that reports rates in GHs. */
    ANTMINER_GHS_API(18),

    /** Antminer API that reports rates in MHs. */
    ANTMINER_KHS_API(19),

    /** baikal. */
    BAIKAL_API(20);

    /** The type. */
    private final int type;

    /**
     * Constructor.
     *
     * @param type The type.
     */
    ApiType(final int type) {
        this.type = type;
    }

    /**
     * Returns the type.
     *
     * @return The type.
     */
    @JsonValue
    public int getType() {
        return this.type;
    }
}