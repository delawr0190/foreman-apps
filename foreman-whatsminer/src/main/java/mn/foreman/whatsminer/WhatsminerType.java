package mn.foreman.whatsminer;

import mn.foreman.model.MinerType;

import java.util.Comparator;
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

    /** D1. */
    WHATSMINER_D1(180, "WhatsMiner D1", "whatsminer-d1"),

    /** M3. */
    WHATSMINER_M3(189, "WhatsMiner M3", "whatsminer-m3"),

    /** M3X. */
    WHATSMINER_M3X(198, "WhatsMiner M3X", "whatsminer-m3x"),

    /** M10. */
    WHATSMINER_M10(315, true, "WhatsMiner M10", "whatsminer-m10"),

    /** M10S. */
    WHATSMINER_M10S("WhatsMiner M10S", "whatsminer-m10s"),

    /** M20S. */
    WHATSMINER_M20S(315, "WhatsMiner M20S", "whatsminer-m20s"),

    /** M20. */
    WHATSMINER_M20("WhatsMiner M20", "whatsminer-m20"),

    /** M21. */
    WHATSMINER_M21("WhatsMiner M21", "whatsminer-m21"),

    /** M20S. */
    WHATSMINER_M21S("WhatsMiner M21S", "whatsminer-m21s"),

    /** M30S. */
    WHATSMINER_M30S(444, "WhatsMiner M30S", "whatsminer-m30s"),

    /** M30S+. */
    WHATSMINER_M30SP("WhatsMiner M30S+", "whatsminer-m30s+"),

    /** M30S++. */
    WHATSMINER_M30SPP("WhatsMiner M30S++", "whatsminer-m30s++"),

    /** M31S. */
    WHATSMINER_M31S("WhatsMiner M31S", "whatsminer-m31s"),

    /** M31S+. */
    WHATSMINER_M31SP("WhatsMiner M31S+", "whatsminer-m31s+"),

    /** M32. */
    WHATSMINER_M32("WhatsMiner M32", "whatsminer-m32");

    /** All of the types, by number of chips, mapped to their type. */
    private static final Map<Integer, Map<Boolean, WhatsminerType>> TYPE_MAP =
            new ConcurrentHashMap<>();

    /** The version map. */
    private static final Map<String, WhatsminerType> VERSION_MAP =
            new ConcurrentHashMap<>();

    static {
        for (final WhatsminerType asicType : values()) {
            final Map<Boolean, WhatsminerType> map =
                    TYPE_MAP.computeIfAbsent(
                            asicType.numChips,
                            numChips -> new HashMap<>());
            map.put(asicType.hasPowerVersion, asicType);
            VERSION_MAP.put(asicType.version, asicType);
        }
    }

    /** The power version. */
    private final boolean hasPowerVersion;

    /** The number of chips. */
    private final int numChips;

    /** The miner slug. */
    private final String slug;

    /** The version. */
    private final String version;

    /**
     * Constructor.
     *
     * @param version The version.
     * @param slug    The miner slug.
     */
    WhatsminerType(
            final String version,
            final String slug) {
        this(
                -1,
                false,
                version,
                slug);
    }

    /**
     * Constructor.
     *
     * @param numChips The number of chips.
     * @param version  The version.
     * @param slug     The miner slug.
     */
    WhatsminerType(
            final int numChips,
            final String version,
            final String slug) {
        this(
                numChips,
                false,
                version,
                slug);
    }

    /**
     * Constructor.
     *
     * @param numChips        The number of chips.
     * @param hasPowerVersion The power version.
     * @param version         The version.
     * @param slug            The miner slug.
     */
    WhatsminerType(
            final int numChips,
            final boolean hasPowerVersion,
            final String version,
            final String slug) {
        this.numChips = numChips;
        this.hasPowerVersion = hasPowerVersion;
        this.version = version;
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

    /**
     * Returns the type from the version.
     *
     * @param version The version.
     *
     * @return The type.
     */
    public static Optional<WhatsminerType> fromVersion(final String version) {
        return VERSION_MAP
                .entrySet()
                .stream()
                .filter(entry -> version.startsWith(entry.getKey()))
                .max(Comparator.comparing(entry -> entry.getValue().version.length()))
                .map(Map.Entry::getValue);
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
