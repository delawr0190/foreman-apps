package mn.foreman.antminer;

import mn.foreman.model.MinerType;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/** All of the known Antminer types. */
public enum AntminerType
        implements MinerType {

    /** Antminer A3. */
    ANTMINER_A3("Antminer A3", "antminer-a3"),

    /** Antminer B3. */
    ANTMINER_B3("Antminer B3", "antminer-b3"),

    /** Antminer B7. */
    ANTMINER_B7("Antminer B7", "antminer-b7"),

    /** Antminer D3. */
    ANTMINER_D3("Antminer D3", "antminer-d3"),

    /** Antminer DR3. */
    ANTMINER_DR3("Antminer DR3", "antminer-dr3"),

    /** Antminer DR5. */
    ANTMINER_DR5("Antminer DR5", "antminer-dr5"),

    /** Antminer E3. */
    ANTMINER_E3("Antminer E3", "antminer-e3"),

    /** Antminer L3. */
    ANTMINER_L3("Antminer L3", "antminer-l3"),

    /** Antminer S7. */
    ANTMINER_S7("Antminer S7", "antminer-s7"),

    /** Antminer S9. */
    ANTMINER_S9("Antminer S9", "antminer-s9"),

    /** Antminer S15. */
    ANTMINER_S15("Antminer S15", "antminer-s15"),

    /** Antminer S17. */
    ANTMINER_S17("Antminer S17", "antminer-s17"),

    /** Antminer T9. */
    ANTMINER_T9("Antminer T9", "antminer-t9"),

    /** Antminer T15. */
    ANTMINER_T15("Antminer T15", "antminer-t15"),

    /** Antminer T17. */
    ANTMINER_T17("Antminer T17", "antminer-t17"),

    /** Antminer X3. */
    ANTMINER_X3("Antminer X3", "antminer-x3"),

    /** Antminer Z9. */
    ANTMINER_Z9("Antminer Z9", "antminer-z9"),

    /** Antminer Z11. */
    ANTMINER_Z11("Antminer Z11", "antminer-z11"),

    /** BraiinsOS Antminer S9. */
    BRAIINS_S9("braiins-am1-s9", "antminer-s9"),

    /** Minecenter S9. */
    MINECENTER_S9("Minecenter S9", "antminer-s9");

    /** All of the types, by string, mapped to their type. */
    private static final Map<String, AntminerType> TYPE_MAP =
            new ConcurrentHashMap<>();

    static {
        for (final AntminerType asicType : values()) {
            TYPE_MAP.put(asicType.identifier, asicType);
        }
    }

    /** The miner identifier. */
    private final String identifier;

    /** The miner ID associated with the miner in Foreman. */
    private final String slug;

    /**
     * Constructor.
     *
     * @param identifier The identifier.
     * @param slug       The slug.
     */
    AntminerType(
            final String identifier,
            final String slug) {
        this.identifier = identifier;
        this.slug = slug;
    }

    /**
     * Converts the provided model to an {@link AntminerType}.
     *
     * @param model The model.
     *
     * @return The corresponding {@link AntminerType}.
     */
    public static Optional<AntminerType> forModel(final String model) {
        if (model != null && !model.isEmpty()) {
            return TYPE_MAP.entrySet()
                    .stream()
                    .filter(entry -> model.startsWith(entry.getKey()))
                    .map(Map.Entry::getValue)
                    .findFirst();
        }
        return Optional.empty();
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
