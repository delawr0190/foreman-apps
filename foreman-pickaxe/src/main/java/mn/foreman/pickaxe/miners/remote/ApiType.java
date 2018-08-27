package mn.foreman.pickaxe.miners.remote;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * An API type represents a FOREMAN server response that indicates the miner API
 * type.
 */
public enum ApiType {

    /** bminer. */
    BMINER_API(1),

    /** castxmr. */
    CASTXMR_API(2),

    /** ccminer. */
    CCMINER_API(3),

    /** dstm. */
    DSTM_API(4),

    /** ethminer. */
    ETHMINER_API(5),

    /** ewbf. */
    EWBF_API(6),

    /** excavator. */
    EXCAVATOR_API(7),

    /** jceminer. */
    JCEMINER_API(8),

    /** lolminer. */
    LOLMINER_API(9),

    /** sgminer. */
    SGMINER_API(10),

    /** srbminer. */
    SRBMINER_API(11),

    /** trex. */
    TREX_API(12),

    /** xmrig */
    XMRIG_API(13),

    /** xmrstak. */
    XMRSTAK_API(14),

    /** claymore-eth. */
    CLAYMORE_ETH_API(15),

    /** claymore-zec. */
    CLAYMORE_ZEC_API(16),

    /** Antminer API that reports rates in Hs. */
    ANTMINER_HS_API(17),

    /** Antminer API that reports rates in MHs. */
    ANTMINER_MHS_API(18),

    /** Antminer API that reports rates in GHs. */
    ANTMINER_GHS_API(19),

    /** Antminer API that reports rates in MHs. */
    ANTMINER_KHS_API(20),

    /** baikal. */
    BAIKAL_API(21),

    /** dragonmint. */
    DRAGONMINT_API(22);

    /** A mapping of {@link #type} to {@link ApiType}. */
    private static final Map<Integer, ApiType> MAPPINGS;

    static {
        MAPPINGS = new ConcurrentHashMap<>();
        for (final ApiType apiType : values()) {
            MAPPINGS.put(apiType.getType(), apiType);
        }
    }

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
     * Returns the type for the provided value.
     *
     * @param value The value.
     *
     * @return The type.
     */
    @JsonCreator
    public static ApiType forValue(final int value) {
        return MAPPINGS.get(value);
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