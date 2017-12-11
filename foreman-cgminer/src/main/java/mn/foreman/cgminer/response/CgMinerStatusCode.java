package mn.foreman.cgminer.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/** A {@link CgMinerStatusCode} represents a code in the cgminer section header. */
public enum CgMinerStatusCode {

    /** Warning */
    WARNING("W"),

    /** Informational */
    INFORMATIONAL("I"),

    /** Success */
    SUCCESS("S"),

    /** Error */
    ERROR("E"),

    /** Fatal */
    FATAL("F");

    /** All of the codes, mapped by cgminer value. */
    private static final ConcurrentMap<String, CgMinerStatusCode> CODES =
            new ConcurrentHashMap<>();

    static {
        for (final CgMinerStatusCode statusCode : values()) {
            CODES.put(statusCode.getCode(), statusCode);
        }
    }

    /** The code. */
    private final String code;

    /**
     * Constructor.
     *
     * @param code The code.
     */
    CgMinerStatusCode(final String code) {
        this.code = code;
    }

    /**
     * Returns the code.
     *
     * @return The code.
     */
    public String getCode() {
        return this.code;
    }

    @JsonValue
    public String toValue() {
        return getCode();
    }

    @JsonCreator
    public static CgMinerStatusCode forValue(final String value) {
        return CODES.get(value);
    }
}