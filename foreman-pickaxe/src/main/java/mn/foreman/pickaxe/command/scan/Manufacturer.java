package mn.foreman.pickaxe.command.scan;

import mn.foreman.aixin.AixinTypeFactory;
import mn.foreman.antminer.AntminerTypeFactory;
import mn.foreman.avalon.AvalonTypeFactory;
import mn.foreman.baikal.BaikalTypeFactory;
import mn.foreman.blackminer.BlackminerTypeFactory;
import mn.foreman.cgminer.CgMinerDetectionStrategy;
import mn.foreman.cgminer.request.CgMinerCommand;
import mn.foreman.dayun.DayunTypeFactory;
import mn.foreman.dayun.response.StatsPatchingStrategy;
import mn.foreman.dragonmint.DragonmintDetectionStrategy;
import mn.foreman.dragonmint.DragonmintType;
import mn.foreman.futurebit.FutureBitTypeFactory;
import mn.foreman.hyperbit.HyperbitTypeFactory;
import mn.foreman.innosilicon.InnosiliconType;
import mn.foreman.model.DetectionStrategy;
import mn.foreman.obelisk.ObeliskDetectionStrategy;
import mn.foreman.spondoolies.SpondooliesTypeFactory;
import mn.foreman.whatsminer.WhatsminerTypeFactory;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/** An enumeration containing all of the known manufacturers. */
public enum Manufacturer {

    /** Aixin. */
    AIXIN(
            "aixin",
            new CgMinerDetectionStrategy(
                    CgMinerCommand.DEVS,
                    new AixinTypeFactory())),

    /** Antminer. */
    ANTMINER(
            "antminer",
            new CgMinerDetectionStrategy(
                    CgMinerCommand.VERSION,
                    new AntminerTypeFactory())),

    /** Avalon. */
    AVALON(
            "avalon",
            new CgMinerDetectionStrategy(
                    CgMinerCommand.STATS,
                    new AvalonTypeFactory())),

    /** Baikal. */
    BAIKAL(
            "baikal",
            new CgMinerDetectionStrategy(
                    CgMinerCommand.DEVS,
                    new BaikalTypeFactory())),

    /** Blackminer. */
    BLACKMINER(
            "blackminer",
            new CgMinerDetectionStrategy(
                    CgMinerCommand.VERSION,
                    new BlackminerTypeFactory())),

    /** Dayun. */
    DAYUN(
            "dayun",
            new CgMinerDetectionStrategy(
                    CgMinerCommand.STATS,
                    new DayunTypeFactory(),
                    new StatsPatchingStrategy())),

    /** Dragonmint. */
    DRAGONMINT(
            "dragonmint",
            new DragonmintDetectionStrategy<>(
                    DragonmintType::forType,
                    "DragonMint")),

    /** FutureBit. */
    FUTUREBIT(
            "futurebit",
            new CgMinerDetectionStrategy(
                    CgMinerCommand.DEVS,
                    new FutureBitTypeFactory())),

    /** HyperBit. */
    HYPERBIT(
            "hyperbit",
            new CgMinerDetectionStrategy(
                    CgMinerCommand.DEVS,
                    new HyperbitTypeFactory())),

    /** Innosilicon. */
    INNOSILICON(
            "innosilicon",
            new DragonmintDetectionStrategy<>(
                    InnosiliconType::forType,
                    "Innosilicon")),

    /** Obelisk. */
    OBELISK(
            "obelisk",
            new ObeliskDetectionStrategy<>()),

    /** Spondoolies. */
    SPONDOOLIES(
            "spondoolies",
            new CgMinerDetectionStrategy(
                    CgMinerCommand.SUMMARY,
                    new SpondooliesTypeFactory())),

    WHATSMINER(
            "whatsminer",
            new CgMinerDetectionStrategy(
                    CgMinerCommand.STATS,
                    new WhatsminerTypeFactory()));

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

    /** The strategy for detecting. */
    private final DetectionStrategy detectionStrategy;

    /** The name. */
    private final String name;

    /**
     * Constructor.
     *
     * @param name              The name.
     * @param detectionStrategy The strategy for detecting.
     */
    Manufacturer(
            final String name,
            final DetectionStrategy detectionStrategy) {
        this.name = name;
        this.detectionStrategy = detectionStrategy;
    }

    /**
     * Converts the provided name to a {@link Manufacturer}.
     *
     * @param name The name.
     *
     * @return The {@link Manufacturer}.
     */
    public static Optional<Manufacturer> fromName(final String name) {
        return Optional.ofNullable(TYPES.get(name.toLowerCase()));
    }

    /**
     * Returns the strategy.
     *
     * @return The strategy.
     */
    public DetectionStrategy getDetectionStrategy() {
        return this.detectionStrategy;
    }

    /**
     * Returns the name.
     *
     * @return The name.
     */
    public String getName() {
        return this.name;
    }
}
