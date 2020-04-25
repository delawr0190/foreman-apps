package mn.foreman.pickaxe.command.scan;

import mn.foreman.aixin.AixinTypeFactory;
import mn.foreman.antminer.AntminerTypeFactory;
import mn.foreman.avalon.AvalonTypeFactory;
import mn.foreman.baikal.BaikalTypeFactory;
import mn.foreman.cgminer.TypeFactory;
import mn.foreman.cgminer.request.CgMinerCommand;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/** An enumeration containing all of the known manufacturers. */
public enum Manufacturer {

    /** Aixin. */
    AIXIN(
            "aixin",
            CgMinerCommand.DEVS,
            new AixinTypeFactory()),

    /** Antminer. */
    ANTMINER(
            "antminer",
            CgMinerCommand.VERSION,
            new AntminerTypeFactory()),

    /** Avalon. */
    AVALON(
            "avalon",
            CgMinerCommand.STATS,
            new AvalonTypeFactory()),

    /** Baikal. */
    BAIKAL(
            "baikal",
            CgMinerCommand.DEVS,
            new BaikalTypeFactory());

    /** All of the known manufacturers. */
    private static final ConcurrentMap<String, Manufacturer> TYPES =
            new ConcurrentHashMap<>();

    static {
        for (final Manufacturer manufacturer : values()) {
            TYPES.put(
                    manufacturer.name,
                    manufacturer);
        }
    }

    /** The command to run against cgminer. */
    private final CgMinerCommand command;

    /** The name. */
    private final String name;

    /** The factory to use for making types. */
    private final TypeFactory typeFactory;

    /**
     * Constructor.
     *
     * @param name        The name.
     * @param command     The command.
     * @param typeFactory The type factory.
     */
    Manufacturer(
            final String name,
            final CgMinerCommand command,
            final TypeFactory typeFactory) {
        this.name = name;
        this.command = command;
        this.typeFactory = typeFactory;
    }

    /**
     * Converts the provided name to a {@link Manufacturer}.
     *
     * @param name The name.
     *
     * @return The {@link Manufacturer}.
     */
    public static Optional<Manufacturer> fromName(final String name) {
        return Optional.ofNullable(TYPES.get(name));
    }

    /**
     * Returns the command.
     *
     * @return The command.
     */
    public CgMinerCommand getCommand() {
        return this.command;
    }

    /**
     * Returns the name.
     *
     * @return The name.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Returns the factory.
     *
     * @return The factory.
     */
    public TypeFactory getTypeFactory() {
        return this.typeFactory;
    }
}
