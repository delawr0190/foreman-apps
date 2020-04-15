package mn.foreman.aixin;

import mn.foreman.model.MinerType;

/**
 * A {@link AixinType} provides a {@link MinerType} implementation that contains
 * all of the known Aixin types.
 */
public enum AixinType
        implements MinerType {

    /** An A1. */
    AIXIN_A1("Aixin A1", 0);

    /** The ID. */
    private final int id;

    /** The miner name. */
    private final String name;

    /**
     * Constructor.
     *
     * @param name The miner name.
     * @param id   The ID.
     */
    AixinType(
            final String name,
            final int id) {
        this.name = name;
        this.id = id;
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
