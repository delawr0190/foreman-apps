package mn.foreman.pickaxe.command.asic.discover;

import mn.foreman.discover.CgminerDiscoverStrategy;
import mn.foreman.discover.DiscoverStrategy;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * A {@link DiscoverType} represents a type of discovery that can be initiated
 * from the Foreman dashboard.
 */
public enum DiscoverType {

    /** Discover ASIC cgminer device. */
    ASIC_CGMINER(
            "asic_cgminer",
            new CgminerDiscoverStrategy());

    /** All of the types, by string, mapped to their type. */
    private static final Map<String, DiscoverType> TYPE_MAP =
            new ConcurrentHashMap<>();

    static {
        for (final DiscoverType type : values()) {
            TYPE_MAP.put(type.type, type);
        }
    }

    /** The discovery strategy. */
    private final DiscoverStrategy discoverStrategy;

    /** The type. */
    private final String type;

    /**
     * Constructor.
     *
     * @param type             The type.
     * @param discoverStrategy The strategy.
     */
    DiscoverType(
            final String type,
            final DiscoverStrategy discoverStrategy) {
        this.type = type;
        this.discoverStrategy = discoverStrategy;
    }

    /**
     * Returns the type for the provided type.
     *
     * @param type The type.
     *
     * @return The type.
     */
    public static Optional<DiscoverType> forType(final String type) {
        return Optional.ofNullable(TYPE_MAP.get(type));
    }

    /**
     * Returns the strategy.
     *
     * @return The strategy.
     */
    public DiscoverStrategy getStrategy() {
        return this.discoverStrategy;
    }
}
