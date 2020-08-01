package mn.foreman.pickaxe.command.asic;

import mn.foreman.aixin.AixinTypeFactory;
import mn.foreman.antminer.*;
import mn.foreman.avalon.AvalonTypeFactory;
import mn.foreman.baikal.BaikalTypeFactory;
import mn.foreman.blackminer.BlackminerConfValue;
import mn.foreman.blackminer.BlackminerFactory;
import mn.foreman.blackminer.BlackminerTypeFactory;
import mn.foreman.cgminer.CgMinerDetectionStrategy;
import mn.foreman.cgminer.request.CgMinerCommand;
import mn.foreman.dayun.DayunTypeFactory;
import mn.foreman.dayun.response.StatsPatchingStrategy;
import mn.foreman.dragonmint.*;
import mn.foreman.futurebit.FutureBitTypeFactory;
import mn.foreman.hyperbit.HyperbitTypeFactory;
import mn.foreman.innosilicon.ApiType;
import mn.foreman.innosilicon.InnosiliconFactory;
import mn.foreman.innosilicon.InnosiliconType;
import mn.foreman.model.*;
import mn.foreman.multminer.MultMinerChangePoolsStrategy;
import mn.foreman.multminer.MultMinerDetectionStrategy;
import mn.foreman.multminer.MultMinerFactory;
import mn.foreman.multminer.MultMinerRebootStrategy;
import mn.foreman.obelisk.ObeliskChangePoolsStrategy;
import mn.foreman.obelisk.ObeliskDetectionStrategy;
import mn.foreman.obelisk.ObeliskFactory;
import mn.foreman.obelisk.ObeliskRebootStrategy;
import mn.foreman.spondoolies.SpondooliesTypeFactory;
import mn.foreman.strongu.StrongUConfValue;
import mn.foreman.strongu.StrongUFactory;
import mn.foreman.strongu.StrongUTypeFactory;
import mn.foreman.whatsminer.WhatsminerTypeFactory;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

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
                    new AntminerTypeFactory()),
            new AntminerChangePoolsStrategy(
                    "antMiner Configuration",
                    Arrays.asList(
                            AntminerConfValue.POOL_1_URL,
                            AntminerConfValue.POOL_1_USER,
                            AntminerConfValue.POOL_1_PASS,
                            AntminerConfValue.POOL_2_URL,
                            AntminerConfValue.POOL_2_USER,
                            AntminerConfValue.POOL_2_PASS,
                            AntminerConfValue.POOL_3_URL,
                            AntminerConfValue.POOL_3_USER,
                            AntminerConfValue.POOL_3_PASS,
                            AntminerConfValue.NO_BEEPER,
                            AntminerConfValue.NO_TEMP_OVER_CTRL,
                            AntminerConfValue.FAN_CTRL,
                            AntminerConfValue.FAN_PWM,
                            AntminerConfValue.FREQ)),
            scheduledThreadPoolExecutor ->
                    new AntminerRebootStrategy(
                            30,
                            30,
                            TimeUnit.SECONDS,
                            scheduledThreadPoolExecutor,
                            new AntminerFactory(BigDecimal.ONE),
                            "antMiner Configuration")),

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
                    new BlackminerTypeFactory()),
            new AntminerChangePoolsStrategy(
                    "blackMiner Configuration",
                    Arrays.asList(
                            BlackminerConfValue.POOL_1_URL,
                            BlackminerConfValue.POOL_1_USER,
                            BlackminerConfValue.POOL_1_PASS,
                            BlackminerConfValue.POOL_2_URL,
                            BlackminerConfValue.POOL_2_USER,
                            BlackminerConfValue.POOL_2_PASS,
                            BlackminerConfValue.POOL_3_URL,
                            BlackminerConfValue.POOL_3_USER,
                            BlackminerConfValue.POOL_3_PASS,
                            BlackminerConfValue.NO_BEEPER,
                            BlackminerConfValue.NO_TEMP_OVER_CTRL,
                            BlackminerConfValue.FAN_CTRL,
                            BlackminerConfValue.FAN_PWM,
                            BlackminerConfValue.FREQ,
                            BlackminerConfValue.COIN_TYPE)),
            scheduledThreadPoolExecutor ->
                    new AntminerRebootStrategy(
                            30,
                            30,
                            TimeUnit.SECONDS,
                            scheduledThreadPoolExecutor,
                            new BlackminerFactory(),
                            "blackMiner Configuration")),

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
                    "DragonMint"),
            new DragonmintChangePoolsStrategy(),
            scheduledThreadPoolExecutor ->
                    new DragonmintRebootStrategy(
                            30,
                            30,
                            TimeUnit.SECONDS,
                            scheduledThreadPoolExecutor,
                            new DragonmintFactory())),

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
                    "Innosilicon"),
            new DragonmintChangePoolsStrategy(),
            scheduledThreadPoolExecutor ->
                    new DragonmintRebootStrategy(
                            30,
                            30,
                            TimeUnit.SECONDS,
                            scheduledThreadPoolExecutor,
                            new InnosiliconFactory(ApiType.HS_API))),

    /** MultMiner. */
    MULTMINER(
            "multminer",
            new MultMinerDetectionStrategy(),
            new MultMinerChangePoolsStrategy(),
            scheduledThreadPoolExecutor ->
                    new MultMinerRebootStrategy(
                            30,
                            30,
                            TimeUnit.SECONDS,
                            scheduledThreadPoolExecutor,
                            new MultMinerFactory())),

    /** Obelisk. */
    OBELISK(
            "obelisk",
            new ObeliskDetectionStrategy<>(),
            new ObeliskChangePoolsStrategy(),
            scheduledThreadPoolExecutor ->
                    new ObeliskRebootStrategy(
                            30,
                            30,
                            TimeUnit.SECONDS,
                            scheduledThreadPoolExecutor,
                            new ObeliskFactory())),

    /** Spondoolies. */
    SPONDOOLIES(
            "spondoolies",
            new CgMinerDetectionStrategy(
                    CgMinerCommand.SUMMARY,
                    new SpondooliesTypeFactory())),

    /** StrongU. */
    STRONGU(
            "strongu",
            new CgMinerDetectionStrategy(
                    CgMinerCommand.DEVS,
                    new StrongUTypeFactory()),
            new AntminerChangePoolsStrategy(
                    "stuMiner Configuration",
                    Arrays.asList(
                            StrongUConfValue.POOL_1_URL,
                            StrongUConfValue.POOL_1_USER,
                            StrongUConfValue.POOL_1_PASS,
                            StrongUConfValue.POOL_2_URL,
                            StrongUConfValue.POOL_2_USER,
                            StrongUConfValue.POOL_2_PASS,
                            StrongUConfValue.POOL_3_URL,
                            StrongUConfValue.POOL_3_USER,
                            StrongUConfValue.POOL_3_PASS,
                            StrongUConfValue.NO_BEEPER,
                            StrongUConfValue.NO_TEMP_OVER_CTRL,
                            StrongUConfValue.FAN_PWM,
                            StrongUConfValue.FREQ_1,
                            StrongUConfValue.FREQ_2,
                            StrongUConfValue.FREQ_3,
                            StrongUConfValue.FREQ_4,
                            StrongUConfValue.WORK_VOLT,
                            StrongUConfValue.START_VOLT,
                            StrongUConfValue.PLL_START,
                            StrongUConfValue.PLL_STEP)),
            scheduledThreadPoolExecutor ->
                    new AntminerRebootStrategy(
                            30,
                            30,
                            TimeUnit.SECONDS,
                            scheduledThreadPoolExecutor,
                            new StrongUFactory(),
                            "stuMiner Configuration")),

    /** Whatsminer. */
    WHATSMINER(
            "whatsminer",
            new CgMinerDetectionStrategy(
                    CgMinerCommand.STATS,
                    new WhatsminerTypeFactory()));

    /** The thread pool to use for querying for miner status. */
    private static final ScheduledThreadPoolExecutor THREAD_POOL =
            new ScheduledThreadPoolExecutor(
                    Runtime
                            .getRuntime()
                            .availableProcessors());

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

    /** The strategy for changing pools. */
    private final ChangePoolsStrategy changePoolsStrategy;

    /** The strategy for detecting. */
    private final DetectionStrategy detectionStrategy;

    /** The name. */
    private final String name;

    /** The strategy for rebooting. */
    private final Function<ScheduledThreadPoolExecutor, RebootStrategy> rebootStrategy;

    /**
     * Constructor.
     *
     * @param name              The name.
     * @param detectionStrategy The strategy for detecting.
     */
    Manufacturer(
            final String name,
            final DetectionStrategy detectionStrategy) {
        this(
                name,
                detectionStrategy,
                new NullChangePoolsStrategy(),
                executor -> new NullRebootStrategy());
    }

    /**
     * Constructor.
     *
     * @param name                The name.
     * @param detectionStrategy   The strategy for detecting.
     * @param changePoolsStrategy The strategy for changing pools.
     * @param rebootStrategy      The strategy for rebooting.
     */
    Manufacturer(
            final String name,
            final DetectionStrategy detectionStrategy,
            final ChangePoolsStrategy changePoolsStrategy,
            final Function<ScheduledThreadPoolExecutor, RebootStrategy> rebootStrategy) {
        this.name = name;
        this.detectionStrategy = detectionStrategy;
        this.changePoolsStrategy = changePoolsStrategy;
        this.rebootStrategy = rebootStrategy;
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
     * Returns the strategy for changing pools.
     *
     * @return The strategy for changing pools.
     */
    public ChangePoolsStrategy getChangePoolsStrategy() {
        return this.changePoolsStrategy;
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

    /**
     * Returns the {@link RebootStrategy}.
     *
     * @return The {@link RebootStrategy}.
     */
    public RebootStrategy getRebootStrategy() {
        return this.rebootStrategy.apply(THREAD_POOL);
    }
}
