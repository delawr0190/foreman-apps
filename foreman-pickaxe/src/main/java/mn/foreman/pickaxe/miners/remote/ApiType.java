package mn.foreman.pickaxe.miners.remote;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * An API type represents a FOREMAN server json that indicates the miner API
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

    /** xmrstak (gpu). */
    XMRSTAK_GPU_API(14),

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
    DRAGONMINT_API(22),

    /** Innosilicon. */
    INNOSILICON_HS_API(23),

    /** Innosilicon. */
    INNOSILICON_KHS_API(24),

    /** Innosilicon. */
    INNOSILICON_MHS_API(25),

    /** Innosilicon. */
    INNOSILICON_GHS_API(26),

    /** Dayun. */
    DAYUN_API(27),

    /** Whatsminer. */
    WHATSMINER_API(28),

    /** Avalon. */
    AVALON_API(29),

    /** Gminer. */
    GMINER_API(30),

    /** mkxminer. */
    MKXMINER_API(31),

    /** Blackminer API. */
    BLACKMINER_API(32),

    /** rhminer. */
    RHMINER_API(33),

    /** multiminer. */
    MULTIMINER_API(34),

    /** hspminer. */
    HSPMINER_API(35),

    /** nanominer. */
    NANOMINER_API(36),

    /** nicehash. */
    NICEHASH_API(37),

    /** optiminer. */
    OPTIMINER_API(38),

    /** grinpro. */
    GRINPRO_API(39),

    /** autominer. */
    AUTOMINER_API(40),

    /** spondoolies. */
    SPONDOOLIES_API(41),

    /** nbminer. */
    NBMINER_API(42),

    /** miniz. */
    MINIZ_API(43),

    /** moonlander. */
    MOONLANDER_API(44),

    /** swarm. */
    SWARM_API(45),

    /** xmrstak (cpu). */
    XMRSTAK_CPU_API(46),

    /** cpuminer. */
    CPUMINER_API(47),

    /** iximiner. */
    IXIMINER_API(48),

    /** aixin. */
    AIXIN_API(49);

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