package mn.foreman.nicehash;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * A nicehash algorithm type provides an enum representing a nicehash
 * algorithm.
 */
public enum AlgoType {

    /** The scrypt algorithm. */
    SCRYPT(0),

    /** The sha-256 algorithm. */
    SHA256(1),

    /** The scrypt-nf algorithm. */
    SCRYPTNF(2),

    /** The x11 algorithm. */
    X11(3),

    /** The x13 algorithm. */
    X13(4),

    /** The keccak algorithm. */
    KECCAK(5),

    /** The x15 algorithm. */
    X15(6),

    /** The nist5 algorithm. */
    NIST5(7),

    /** The neoscrypt algorithm. */
    NEOSCRYPT(8),

    /** The lyra2re algorithm. */
    LYRA2RE(9),

    /** The whirlpoolx algorithm. */
    WHIRLPOOLX(10),

    /** The qubit algorithm. */
    QUBIT(11),

    /** The quark algorithm. */
    QUARK(12),

    /** The axiom algorithm. */
    AXIOM(13),

    /** The lyra2rev2 algorithm. */
    LYRA2REV2(14),

    /** The scryptjanenf16 algorithm. */
    SCRYPTJANENF16(15),

    /** The blake256r8 algorithm. */
    BLAKE256R8(16),

    /** The blake256r14 algorithm. */
    BLAKE256R14(17),

    /** The blake256r8vnl algorithm. */
    BLAKE256R8VNL(18),

    /** The hodl algorithm. */
    HODL(19),

    /** The daggerhashimoto algorithm. */
    DAGGERHASHIMOTO(20),

    /** The decred algorithm. */
    DECRED(21),

    /** The cryptonight algorithm. */
    CRYPTONIGHT(22),

    /** The lbry algorithm. */
    LBRY(23),

    /** The equihash algorithm. */
    EQUIHASH(24),

    /** The pascal algorithm. */
    PASCAL(25),

    /** The x11gost algorithm. */
    X11GOST(26),

    /** The sia algorithm. */
    SIA(27),

    /** The blake2s algorithm. */
    BLAKE2S(28),

    /** The skunk algorithm. */
    SKUNK(29),

    /** The cryptonightv8algorithm. */
    CRYPTONIGHTV7(30),

    /** The cryptonightheavy algorithm. */
    CRYPTONIGHTHEAVY(31),

    /** The lyra2z algorithm. */
    LYRA2Z(32),

    /** The x16r algorithm. */
    X16R(33),

    /** The cryptonightv8 algorithm. */
    CRYPTONIGHTV8(34),

    /** The sha256 asic boost algorithm. */
    SHA256ASICBOOST(35),

    /** The zhash algorithm. */
    ZHASH(36),

    /** The beam algorithm. */
    BEAM(37),

    /** The cuckaroo29 algorithm. */
    CUCKAROO29(38),

    /** The cuckatoo31 algorithm. */
    CUCKATOO31(39),

    /** The lyra2rev3 algorithm. */
    LYRA2REV3(40);

    /** A mapping of {@link #type} to {@link AlgoType}. */
    private static final Map<Integer, AlgoType> MAPPINGS;

    static {
        MAPPINGS = new ConcurrentHashMap<>();
        for (final AlgoType apiType : values()) {
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
    AlgoType(final int type) {
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
    public static AlgoType forValue(final int value) {
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