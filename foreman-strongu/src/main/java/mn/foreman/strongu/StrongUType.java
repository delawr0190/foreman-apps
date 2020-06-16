package mn.foreman.strongu;

import mn.foreman.model.MinerType;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * A {@link StrongUType} provides a {@link MinerType} implementation that
 * contains all of the known StrongU types.
 */
public enum StrongUType
        implements MinerType {

    /** STU-U1. */
    STU_U1("u1"),

    /** STU-U2. */
    STU_U2("u2"),

    /** STU-U1+. */
    STU_U1P("u1+"),

    /** STU-U1++. */
    STU_U1PP("u1++"),

    /** STU-U6. */
    STU_U6("u6"),

    /** STU-U8. */
    STU_U8("u8");

    /** All of the types, by string, mapped to their type. */
    private static final Map<String, StrongUType> TYPE_MAP =
            new ConcurrentHashMap<>();

    static {
        for (final StrongUType asicType : values()) {
            TYPE_MAP.put(asicType.name, asicType);
        }
    }

    /** The miner name. */
    private final String name;

    /**
     * Constructor.
     *
     * @param name The miner name.
     */
    StrongUType(final String name) {
        this.name = name;
    }

    /**
     * Converts the provided model to an {@link StrongUType}.
     *
     * @param model The model.
     *
     * @return The corresponding {@link StrongUType}.
     */
    public static Optional<StrongUType> forModel(final String model) {
        Optional<StrongUType> type = Optional.empty();
        if (model != null && !model.isEmpty()) {
            type =
                    TYPE_MAP.entrySet()
                            .stream()
                            .filter(entry -> model.equalsIgnoreCase(entry.getKey()))
                            .map(Map.Entry::getValue)
                            .findFirst();
        }
        return type;
    }

    @Override
    public Category getCategory() {
        return Category.ASIC;
    }

    @Override
    public String getSlug() {
        return String.format(
                "strongu-stu-%s",
                this.name);
    }
}
