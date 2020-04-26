package mn.foreman.whatsminer;

import mn.foreman.model.MinerType;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * A {@link WhatsminerType} provides a {@link MinerType} implementation that
 * contains all of the known Whatsminer types.
 */
public enum WhatsminerType
        implements MinerType {

    /** The Whatsminer D1V1. */
    WHATSMINER_D1V1(180, "whatsminer-d1"),

    /** The Whatsminer M3V1. */
    WHATSMINER_M3V1(189, "whatsminer-m3"),

    /** The Whatsminer M3V2. */
    WHATSMINER_M3V2(198, "whatsminer-m3"),

    /** The Whatsminer M10V1. */
    WHATSMINER_M10V1(315, true, "whatsminer-m10"),

    /** The Whatsminer M20S. */
    WHATSMINER_M20S(315, "whatsminer-m20s"),

    /** The Whatsminer M30S. */
    WHATSMINER_M30S(444, "whastminer-m30s");

    /** All of the types, by number of chips, mapped to their type. */
    private static final Map<Integer, Map<Boolean, WhatsminerType>> TYPE_MAP =
            new ConcurrentHashMap<>();

    static {
        for (final WhatsminerType asicType : values()) {
            final Map<Boolean, WhatsminerType> map =
                    TYPE_MAP.computeIfAbsent(
                            asicType.numChips,
                            numChips -> new HashMap<>());
            map.put(asicType.hasPowerVersion, asicType);
        }
    }

    /** The power version. */
    private final boolean hasPowerVersion;

    /** The number of chips. */
    private final int numChips;

    /** The miner slug. */
    private final String slug;

    /**
     * Constructor.
     *
     * @param numChips The number of chips.
     * @param slug     The miner slug.
     */
    WhatsminerType(
            final int numChips,
            final String slug) {
        this(
                numChips,
                false,
                slug);
    }

    /**
     * Constructor.
     *
     * @param numChips        The number of chips.
     * @param hasPowerVersion The power version.
     * @param slug            The miner slug.
     */
    WhatsminerType(
            final int numChips,
            final boolean hasPowerVersion,
            final String slug) {
        this.numChips = numChips;
        this.hasPowerVersion = hasPowerVersion;
        this.slug = slug;
    }

    /**
     * Converts the provided number of chips to a {@link WhatsminerType}.
     *
     * @param numChips The number of chips.
     *
     * @return The corresponding {@link WhatsminerType}.
     */
    public static Optional<WhatsminerType> fromChips(
            final int numChips,
            final boolean hasPowerVersion) {
        final Map<Boolean, WhatsminerType> map =
                TYPE_MAP.getOrDefault(
                        numChips,
                        new HashMap<>());
        if (map.size() > 1) {
            return Optional.ofNullable(map.get(hasPowerVersion));
        }
        return Optional.ofNullable(map.getOrDefault(true, map.get(false)));
    }

    @Override
    public Category getCategory() {
        return Category.ASIC;
    }

    @Override
    public String getSlug() {
        return this.slug;
    }
}
