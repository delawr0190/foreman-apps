package mn.foreman.pickaxe.command.asic.digest;

import mn.foreman.digest.HttpGet;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * A {@link DigestType} represents a type of digest operation that can be
 * performed.
 */
public enum DigestType {

    /** Digest HTTP GET. */
    DIGEST_HTTP_GET(
            "get",
            new HttpGet());

    /** All of the types, by string, mapped to their type. */
    private static final Map<String, DigestType> TYPE_MAP =
            new ConcurrentHashMap<>();

    static {
        for (final DigestType type : values()) {
            TYPE_MAP.put(type.type, type);
        }
    }

    /** The digest strategy. */
    private final mn.foreman.digest.DigestStrategy digestStrategy;

    /** The type. */
    private final String type;

    /**
     * Constructor.
     *
     * @param type           The type.
     * @param digestStrategy The strategy.
     */
    DigestType(
            final String type,
            final mn.foreman.digest.DigestStrategy digestStrategy) {
        this.type = type;
        this.digestStrategy = digestStrategy;
    }

    /**
     * Returns the type for the provided type.
     *
     * @param type The type.
     *
     * @return The type.
     */
    public static Optional<DigestType> forType(final String type) {
        return Optional.ofNullable(TYPE_MAP.get(type));
    }

    /**
     * Returns the strategy.
     *
     * @return The strategy.
     */
    public mn.foreman.digest.DigestStrategy getStrategy() {
        return this.digestStrategy;
    }
}
