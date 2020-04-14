package mn.foreman.antminer;

import mn.foreman.model.MinerType;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/** All of the known Antminer types. */
public enum AntminerType
        implements MinerType {

    /** The Antminer A3. */
    ANTMINER_A3("Antminer A3", 0),

    /** The Antminer B3. */
    ANTMINER_B3("Antminer B3", 0),

    /** The Antminer B7. */
    ANTMINER_B7("Antminer B7", 0),

    /** The Antminer D3. */
    ANTMINER_D3("Antminer D3", 0),

    /** The Antminer DR3. */
    ANTMINER_DR3("Antminer DR3", 0),

    /** The Antminer DR5. */
    ANTMINER_DR5("Antminer DR5", 0),

    /** The Antminer E3. */
    ANTMINER_E3("Antminer E3", 0),

    /** The Antminer L3. */
    ANTMINER_L3("Antminer L3", 0),

    /** The Antminer S7. */
    ANTMINER_S7("Antminer S7", 0),

    /** The Antminer S9. */
    ANTMINER_S9("Antminer S9", 0),

    /** The Antminer S15. */
    ANTMINER_S15("Antminer S15", 0),

    /** The Antminer S17. */
    ANTMINER_S17("Antminer S17", 0),

    /** The Antminer T9. */
    ANTMINER_T9("Antminer T9", 0),

    /** The Antminer T15. */
    ANTMINER_T15("Antminer T15", 0),

    /** The Antminer T17. */
    ANTMINER_T17("Antminer T17", 0),

    /** The Antminer X3. */
    ANTMINER_X3("Antminer X3", 0),

    /** The Antminer Z9. */
    ANTMINER_Z9("Antminer Z9", 0),

    /** The Antminer Z11. */
    ANTMINER_Z11("Antminer Z11", 0),

    /** The BraiinsOS Antminer S9. */
    BRAIINS_S9("braiins-am1-s9", "Antminer S9", 0);

    /** All of the types, by string, mapped to their type. */
    private static final Map<String, AntminerType> TYPE_MAP =
            new ConcurrentHashMap<>();

    static {
        for (final AntminerType asicType : values()) {
            TYPE_MAP.put(asicType.identifier, asicType);
        }
    }

    /** The miner ID associated with the miner in Foreman. */
    private final int id;

    /** The miner identifier. */
    private final String identifier;

    /** The miner name. */
    private final String name;

    /**
     * Constructor.
     *
     * @param identifier The identifier.
     * @param name       The miner name.
     * @param id         The ID.
     */
    AntminerType(
            final String identifier,
            final String name,
            final int id) {
        this.identifier = identifier;
        this.name = name;
        this.id = id;
    }

    /**
     * Constructor.
     *
     * @param name    The miner name.
     * @param minerId The miner ID.
     */
    AntminerType(
            final String name,
            final int minerId) {
        this(name, name, minerId);
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
    public int getId() {
        return this.id;
    }

    @Override
    public String getName() {
        return this.name;
    }
}
