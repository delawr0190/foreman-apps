package mn.foreman.antminer;

import mn.foreman.model.MinerType;

import java.util.Comparator;
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

    /** Antminer S17+. */
    ANTMINER_S17P("Antminer S17+", "antminer-s17p"),

    /** Antminer S17e. */
    ANTMINER_S17E("Antminer S17e", "antminer-s17e"),

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

    /** Antminer Z9-Mini. */
    ANTMINER_Z9M("Antminer Z9-Mini", "antminer-z9-mini"),

    /** Antminer Z11. */
    ANTMINER_Z11("Antminer Z11", "antminer-z11"),

    /** BraiinsOS Antminer S9. */
    BRAIINS_S9("braiins-am1-s9", "antminer-s9"),

    /** BraiinsOS+ Antminer S9. */
    BRAIINS_S9_BOSP("BOSminer+", "antminer-s9"),

    /** BraiinsOS Antminer S9. */
    BRAIINS_S9_BOS("BOSminer", "antminer-s9"),

    /** Minecenter S9. */
    MINECENTER_S9("Minecenter S9", "antminer-s9"),

    /** Antminer S19. */
    ANTMINER_S19("Antminer S19", "antminer-s19"),

    /** Antminer S19 Pro. */
    ANTMINER_S19_PRO("Antminer S19 Pro", "antminer-s19-pro"),

    /** Antminer Z15. */
    ANTMINER_Z15("Antminer Z15", "antminer-z15");

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
     * @param key   The key.
     * @param model The model.
     *
     * @return The corresponding {@link AntminerType}.
     */
    public static Optional<AntminerType> forModel(
            final String key,
            final String model) {
        final String slug =
                key.contains("BOS")
                        ? key
                        : model;
        if (slug != null && !slug.isEmpty()) {
            return TYPE_MAP.entrySet()
                    .stream()
                    .filter(entry -> slug.startsWith(entry.getKey()))
                    .map(Map.Entry::getValue)
                    .max(Comparator.comparing(type -> type.getIdentifier().length()));
        }
        return Optional.empty();
    }

    @Override
    public Category getCategory() {
        return Category.ASIC;
    }

    /**
     * Returns the identifier.
     *
     * @return The identifier.
     */
    public String getIdentifier() {
        return this.identifier;
    }

    @Override
    public String getSlug() {
        return this.slug;
    }

    /**
     * Returns whether or not the type indicates braiins os.
     *
     * @return Whether or not the type indicates braiins os.
     */
    public boolean isBraiins() {
        final String name = this.name().toLowerCase();
        return name.contains("bos") || name.contains("braiins");
    }
}
