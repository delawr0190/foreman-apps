package mn.foreman.innosilicon;

import mn.foreman.model.MinerType;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * An {@link InnosiliconType} provides a {@link MinerType} implementation that
 * contains all of the known Innosilicon types.
 */
public enum InnosiliconType
        implements MinerType {

    /** Innosilicon T3s. */
    T3S("t3s", "innosilicon-t3s"),

    /** Innosilicon T3+pro. */
    T3PB("t3+b", "innosilicon-t3+pro"),

    /** Innosilicon T3A. */
    T3A("t3a", "innosilicon-t3a"),

    /** Innosilicon T3+. */
    T3P("t3+", "innosilicon-t3+"),

    /** Innosilicon T3h. */
    T3H("t3h", "innosilicon-t3h"),

    /** Innosilicon T3h. */
    T3HP("t3h+", "innosilicon-t3h"),

    /** Innosilicon T2Th+. */
    T2THP("t2th+", "innosilicon-t2th+"),

    /** Innosilicon T2Thf+. */
    T2THFP("t2thf+", "innosilicon-t2thf+"),

    /** Innosilicon T2Thl+. */
    T2THLP("t2thl+", "innosilicon-t2thl+"),

    /** Innosilicon T2Th. */
    T2TH("t2th", "innosilicon-t2th"),

    /** Innosilicon T2Tz-30T. */
    T2TZ("t2tz", "innosilicon-t2tz"),

    /** Innosilicon T2Thm. */
    T2THM("t2thm", "innosilicon-t2thm"),

    /** Innosilicon T2Thf. */
    T2THF("t2thf", "innosilicon-t2thf"),

    /** Innosilicon A10. */
    A10("a10", "innosilicon-a10"),

    /** Innosilicon T3-43T. */
    T3("t3", "innosilicon-t3"),

    /** Innosilicon T2T+(32T). */
    T2TP("t2t+", "innosilicon-t2t+"),

    /** Innosilicon T2Ts-26T. */
    T2TS("t2ts", "innosilicon-t2ts"),

    /** Innosilicon T2Ti-25T. */
    T2TI("t2ti", "innosilicon-t2ti"),

    /** Innosilicon T2T-24T. */
    T2T("t2t", "innosilicon-t2t"),

    /** Innosilicon T2. */
    T2("t2", "innosilicon-t2"),

    /** Innosilicon T1. */
    T1("t1", "innosilicon-t1"),

    /** Innosilicon A9. */
    B29P("b29+", "innosilicon-a9"),

    /** Innosilicon A9+. */
    A9P("a9+", "innosilicon-a9+"),

    /** Innosilicon A9++. */
    A9PP("a9++", "innosilicon-a9++"),

    /** Innosilicon D9+. */
    D9P("d9+", "innosilicon-d9+"),

    /** Innosilicon D9. */
    D9("d9", "innosilicon-d9"),

    /** Innosilicon S11. */
    S11("s11", "innosilicon-s11"),

    /** Innosilicon A8. */
    A8("T4", "innosilicon-a8"),

    /** Innosilicon A8+. */
    A8P("a8+", "innosilicon-a8+"),

    /** Innosilicon A5+. */
    A5P("a5+", "innosilicon-a5+"),

    /** Innosilicon A5. */
    A5("a5", "innosilicon-a5"),

    /** Innosilicon A6. */
    A6("a6", "innosilicon-a6"),

    /** Innosilicon A6+. */
    A6P("a6+", "innosilicon-a6+"),

    /** Innosilicon A4+. */
    A4P("a4+", "innosilicon-a4+"),

    /** Innosilicon A4. */
    A4("a4", "innosilicon-a4");

    /** All of the types, by string, mapped to their type. */
    private static final Map<String, InnosiliconType> TYPE_MAP =
            new ConcurrentHashMap<>();

    static {
        for (final InnosiliconType asicType : values()) {
            TYPE_MAP.put(asicType.type, asicType);
        }
    }

    /** The Foreman slug. */
    private final String slug;

    /** The type. */
    private final String type;

    /**
     * Constructor.
     *
     * @param type The type.
     * @param slug The slug.
     */
    InnosiliconType(
            final String type,
            final String slug) {
        this.type = type;
        this.slug = slug;
    }

    /**
     * Converts the provided model to an {@link InnosiliconType}.
     *
     * @param type The type.
     *
     * @return The corresponding {@link InnosiliconType}.
     */
    public static Optional<InnosiliconType> forType(final String type) {
        if (type != null && !type.isEmpty()) {
            final String lowerType = type.toLowerCase();
            return TYPE_MAP.entrySet()
                    .stream()
                    .filter(entry -> lowerType.equals(entry.getKey()))
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
