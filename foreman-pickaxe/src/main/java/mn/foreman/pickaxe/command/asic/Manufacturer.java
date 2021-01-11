package mn.foreman.pickaxe.command.asic;

import mn.foreman.aixin.AixinType;
import mn.foreman.antminer.*;
import mn.foreman.avalon.*;
import mn.foreman.baikal.BaikalChangePoolsAction;
import mn.foreman.baikal.BaikalDetectionStrategy;
import mn.foreman.baikal.BaikalFactory;
import mn.foreman.baikal.BaikalRebootAction;
import mn.foreman.blackminer.BlackminerConfValue;
import mn.foreman.blackminer.BlackminerFactory;
import mn.foreman.blackminer.BlackminerTypeFactory;
import mn.foreman.cgminer.CgMinerDetectionStrategy;
import mn.foreman.cgminer.NullPatchingStrategy;
import mn.foreman.cgminer.request.CgMinerCommand;
import mn.foreman.cheetahminer.CheetahminerType;
import mn.foreman.dayun.DayunTypeFactory;
import mn.foreman.dayun.response.StatsPatchingStrategy;
import mn.foreman.dragonmint.*;
import mn.foreman.futurebit.FutureBitTypeFactory;
import mn.foreman.honorknight.*;
import mn.foreman.hyperbit.HyperbitTypeFactory;
import mn.foreman.innosilicon.ApiType;
import mn.foreman.innosilicon.InnosiliconFactory;
import mn.foreman.innosilicon.InnosiliconType;
import mn.foreman.model.*;
import mn.foreman.model.cache.StatsCache;
import mn.foreman.multminer.MultMinerChangePoolsAction;
import mn.foreman.multminer.MultMinerDetectionStrategy;
import mn.foreman.multminer.MultMinerFactory;
import mn.foreman.multminer.MultMinerRebootAction;
import mn.foreman.obelisk.*;
import mn.foreman.spondoolies.SpondooliesTypeFactory;
import mn.foreman.strongu.StrongUConfValue;
import mn.foreman.strongu.StrongUFactory;
import mn.foreman.strongu.StrongUTypeFactory;
import mn.foreman.whatsminer.*;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ScheduledExecutorService;
import java.util.function.BiFunction;

/** An enumeration containing all of the known manufacturers. */
public enum Manufacturer {

    /** Aixin. */
    AIXIN(
            "aixin",
            (args, ip) ->
                    new HonorKnightDetectionStrategy<>(
                            AixinType::forType,
                            new HonorKnightMacStrategy(
                                    ip,
                                    80)),
            (threadPool, blacklist, statsCache) ->
                    AsyncActionFactory.toAsync(
                            threadPool,
                            blacklist,
                            statsCache,
                            new HonorKnightFactory(),
                            new HonorKnightChangePoolsAction()),
            (threadPool, blacklist, statsCache) ->
                    AsyncActionFactory.toAsync(
                            threadPool,
                            blacklist,
                            statsCache,
                            new HonorKnightFactory(),
                            new HonorKnightRebootAction()),
            (threadPool, blacklist, statsCache) -> new NullAsicAction(),
            (threadPool, blacklist, statsCache) ->
                    AsyncActionFactory.toAsync(
                            threadPool,
                            blacklist,
                            statsCache,
                            new HonorKnightFactory(),
                            new HonorKnightNetworkAction())),

    /** Antminer. */
    ANTMINER(
            "antminer",
            (args, ip) ->
                    new AntminerDetectionStrategy(
                            "antMiner Configuration",
                            Arrays.asList(
                                    new StockMacStrategy(
                                            ip,
                                            80,
                                            "antMiner Configuration",
                                            args.getOrDefault("username", "").toString(),
                                            args.getOrDefault("password", "").toString()),
                                    new BraiinsMacStrategy(
                                            ip,
                                            80,
                                            args.getOrDefault("username", "").toString(),
                                            args.getOrDefault("password", "").toString())),
                            Arrays.asList(
                                    new StockHostnameStrategy(
                                            "antMiner Configuration"),
                                    new BraiinsHostnameStrategy())),
            (threadPool, blacklist, statsCache) ->
                    AsyncActionFactory.toAsync(
                            threadPool,
                            blacklist,
                            statsCache,
                            new AntminerFactory(BigDecimal.ONE),
                            new FirmwareAwareAction(
                                    "antMiner Configuration",
                                    new StockChangePoolsAction(
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
                                    new BraiinsChangePoolsAction())),
            (threadPool, blacklist, statsCache) ->
                    AsyncActionFactory.toAsync(
                            threadPool,
                            blacklist,
                            statsCache,
                            new AntminerFactory(BigDecimal.ONE),
                            new FirmwareAwareAction(
                                    "antMiner Configuration",
                                    new StockRebootAction("antMiner Configuration"),
                                    new BraiinsRebootAction())),
            (threadPool, blacklist, statsCache) ->
                    new ChainedAsicAction(
                            AsyncActionFactory.toAsync(
                                    threadPool,
                                    blacklist,
                                    statsCache,
                                    new AntminerFactory(BigDecimal.ONE),
                                    new FirmwareAwareAction(
                                            "antMiner Configuration",
                                            new StockFactoryResetAction(
                                                    "antMiner Configuration"),
                                            new BraiinsFactoryResetAction())),
                            AsyncActionFactory.toAsync(
                                    threadPool,
                                    blacklist,
                                    statsCache,
                                    new AntminerFactory(BigDecimal.ONE),
                                    new FirmwareAwareAction(
                                            "antMiner Configuration",
                                            new StockChangePoolsAction(
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
                                            new BraiinsChangePoolsAction()))),
            (threadPool, blacklist, statsCache) ->
                    AsyncActionFactory.toAsync(
                            threadPool,
                            blacklist,
                            statsCache,
                            new AntminerFactory(BigDecimal.ONE),
                            new FirmwareAwareAction(
                                    "antMiner Configuration",
                                    new StockNetworkAction(
                                            "antMiner Configuration",
                                            "ant"),
                                    new BraiinsNetworkAction()),
                            AsyncAsicActionUtils::ipChangingHook)),

    /** Avalon. */
    AVALON(
            "avalon",
            (args, ip) ->
                    new CgMinerDetectionStrategy(
                            CgMinerCommand.STATS,
                            new AvalonTypeFactory(),
                            new AvalonMacStrategy(
                                    ip,
                                    80),
                            new NullPatchingStrategy()),
            (threadPool, blacklist, statsCache) ->
                    AsyncActionFactory.toAsync(
                            threadPool,
                            blacklist,
                            statsCache,
                            new AvalonFactory(),
                            new AvalonChangePoolsAction(
                                    new AvalonRebootAction())),
            (threadPool, blacklist, statsCache) ->
                    AsyncActionFactory.toAsync(
                            threadPool,
                            blacklist,
                            statsCache,
                            new AvalonFactory(),
                            new AvalonRebootAction()),
            (threadPool, blacklist, statsCache) -> new NullAsicAction(),
            (threadPool, blacklist, statsCache) ->
                    AsyncActionFactory.toAsync(
                            threadPool,
                            blacklist,
                            statsCache,
                            new AvalonFactory(),
                            new AvalonNetworkAction(
                                    new AvalonRebootAction()),
                            AsyncAsicActionUtils::ipChangingHook)),

    /** Baikal. */
    BAIKAL(
            "baikal",
            (args, ip) -> new BaikalDetectionStrategy("80"),
            (threadPool, blacklist, statsCache) ->
                    AsyncActionFactory.toAsync(
                            threadPool,
                            blacklist,
                            statsCache,
                            new BaikalFactory(),
                            new BaikalChangePoolsAction()),
            (threadPool, blacklist, statsCache) ->
                    AsyncActionFactory.toAsync(
                            threadPool,
                            blacklist,
                            statsCache,
                            new BaikalFactory(),
                            new BaikalRebootAction()),
            (threadPool, blacklist, statsCache) -> new NullAsicAction(),
            (threadPool, blacklist, statsCache) -> new NullAsicAction()),

    /** Blackminer. */
    BLACKMINER(
            "blackminer",
            (args, ip) ->
                    new CgMinerDetectionStrategy(
                            CgMinerCommand.VERSION,
                            new BlackminerTypeFactory(),
                            new StockMacStrategy(
                                    ip,
                                    80,
                                    "blackMiner Configuration",
                                    args.getOrDefault("username", "").toString(),
                                    args.getOrDefault("password", "").toString()),
                            new NullPatchingStrategy()),
            (threadPool, blacklist, statsCache) ->
                    AsyncActionFactory.toAsync(
                            threadPool,
                            blacklist,
                            statsCache,
                            new BlackminerFactory(),
                            new StockChangePoolsAction(
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
                                            BlackminerConfValue.COIN_TYPE))),
            (threadPool, blacklist, statsCache) ->
                    AsyncActionFactory.toAsync(
                            threadPool,
                            blacklist,
                            statsCache,
                            new BlackminerFactory(),
                            new StockRebootAction("blackMiner Configuration")),
            (threadPool, blacklist, statsCache) ->
                    new ChainedAsicAction(
                            AsyncActionFactory.toAsync(
                                    threadPool,
                                    blacklist,
                                    statsCache,
                                    new BlackminerFactory(),
                                    new StockFactoryResetAction(
                                            "blackMiner Configuration")),
                            AsyncActionFactory.toAsync(
                                    threadPool,
                                    blacklist,
                                    statsCache,
                                    new BlackminerFactory(),
                                    new StockChangePoolsAction(
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
                                                    BlackminerConfValue.COIN_TYPE)))),
            (threadPool, blacklist, statsCache) ->
                    AsyncActionFactory.toAsync(
                            threadPool,
                            blacklist,
                            statsCache,
                            new BlackminerFactory(),
                            new StockNetworkAction(
                                    "blackMiner Configuration",
                                    "bb"),
                            AsyncAsicActionUtils::ipChangingHook)),

    /** Cheetahminer. */
    CHEETAHMINER(
            "cheetahminer",
            (args, ip) ->
                    new HonorKnightDetectionStrategy<>(
                            CheetahminerType::forType,
                            new HonorKnightMacStrategy(
                                    ip,
                                    80)),
            (threadPool, blacklist, statsCache) ->
                    AsyncActionFactory.toAsync(
                            threadPool,
                            blacklist,
                            statsCache,
                            new HonorKnightFactory(),
                            new HonorKnightChangePoolsAction()),
            (threadPool, blacklist, statsCache) ->
                    AsyncActionFactory.toAsync(
                            threadPool,
                            blacklist,
                            statsCache,
                            new HonorKnightFactory(),
                            new HonorKnightRebootAction()),
            (threadPool, blacklist, statsCache) -> new NullAsicAction(),
            (threadPool, blacklist, statsCache) ->
                    AsyncActionFactory.toAsync(
                            threadPool,
                            blacklist,
                            statsCache,
                            new HonorKnightFactory(),
                            new HonorKnightNetworkAction())),

    /** Dayun. */
    DAYUN(
            "dayun",
            (args, ip) ->
                    new CgMinerDetectionStrategy(
                            CgMinerCommand.STATS,
                            new DayunTypeFactory(),
                            new NullMacStrategy(),
                            new StatsPatchingStrategy()),
            (threadPool, blacklist, statsCache) -> new NullAsicAction(),
            (threadPool, blacklist, statsCache) -> new NullAsicAction(),
            (threadPool, blacklist, statsCache) -> new NullAsicAction(),
            (threadPool, blacklist, statsCache) -> new NullAsicAction()),

    /** Dragonmint. */
    DRAGONMINT(
            "dragonmint",
            (args, ip) ->
                    new DragonmintDetectionStrategy<>(
                            DragonmintType::forType,
                            "DragonMint",
                            new DragonmintMacStrategy(
                                    ip,
                                    80,
                                    args.getOrDefault("username", "").toString(),
                                    args.getOrDefault("password", "").toString())),
            (threadPool, blacklist, statsCache) ->
                    AsyncActionFactory.toAsync(
                            threadPool,
                            blacklist,
                            statsCache,
                            new DragonmintFactory(),
                            new DragonmintChangePoolsAction()),
            (threadPool, blacklist, statsCache) ->
                    AsyncActionFactory.toAsync(
                            threadPool,
                            blacklist,
                            statsCache,
                            new DragonmintFactory(),
                            new DragonmintRebootAction()),
            (threadPool, blacklist, statsCache) ->
                    new ChainedAsicAction(
                            AsyncActionFactory.toAsync(
                                    threadPool,
                                    blacklist,
                                    statsCache,
                                    new DragonmintFactory(),
                                    new DragonmintFactoryResetAction()),
                            AsyncActionFactory.toAsync(
                                    threadPool,
                                    blacklist,
                                    statsCache,
                                    new DragonmintFactory(),
                                    new DragonmintChangePoolsAction())),
            (threadPool, blacklist, statsCache) ->
                    AsyncActionFactory.toAsync(
                            threadPool,
                            blacklist,
                            statsCache,
                            new DragonmintFactory(),
                            new DragonmintNetworkAction(),
                            AsyncAsicActionUtils::ipChangingHook)),

    /** FutureBit. */
    FUTUREBIT(
            "futurebit",
            (args, ip) ->
                    new CgMinerDetectionStrategy(
                            CgMinerCommand.DEVS,
                            new FutureBitTypeFactory()),
            (threadPool, blacklist, statsCache) -> new NullAsicAction(),
            (threadPool, blacklist, statsCache) -> new NullAsicAction(),
            (threadPool, blacklist, statsCache) -> new NullAsicAction(),
            (threadPool, blacklist, statsCache) -> new NullAsicAction()),

    /** HonorKnight. */
    HONORKNIGHT(
            "honorknight",
            (args, ip) ->
                    new HonorKnightDetectionStrategy<>(
                            HonorKnightType::forType,
                            new HonorKnightMacStrategy(
                                    ip,
                                    80)),
            (threadPool, blacklist, statsCache) ->
                    AsyncActionFactory.toAsync(
                            threadPool,
                            blacklist,
                            statsCache,
                            new HonorKnightFactory(),
                            new HonorKnightChangePoolsAction()),
            (threadPool, blacklist, statsCache) ->
                    AsyncActionFactory.toAsync(
                            threadPool,
                            blacklist,
                            statsCache,
                            new HonorKnightFactory(),
                            new HonorKnightRebootAction()),
            (threadPool, blacklist, statsCache) -> new NullAsicAction(),
            (threadPool, blacklist, statsCache) ->
                    AsyncActionFactory.toAsync(
                            threadPool,
                            blacklist,
                            statsCache,
                            new HonorKnightFactory(),
                            new HonorKnightNetworkAction())),

    /** HyperBit. */
    HYPERBIT(
            "hyperbit",
            (args, ip) ->
                    new CgMinerDetectionStrategy(
                            CgMinerCommand.DEVS,
                            new HyperbitTypeFactory()),
            (threadPool, blacklist, statsCache) -> new NullAsicAction(),
            (threadPool, blacklist, statsCache) -> new NullAsicAction(),
            (threadPool, blacklist, statsCache) -> new NullAsicAction(),
            (threadPool, blacklist, statsCache) -> new NullAsicAction()),

    /** Innosilicon. */
    INNOSILICON(
            "innosilicon",
            (args, ip) ->
                    new DragonmintDetectionStrategy<>(
                            InnosiliconType::forType,
                            "Innosilicon",
                            new DragonmintMacStrategy(
                                    ip,
                                    80,
                                    args.getOrDefault("username", "").toString(),
                                    args.getOrDefault("password", "").toString())),
            (threadPool, blacklist, statsCache) ->
                    AsyncActionFactory.toAsync(
                            threadPool,
                            blacklist,
                            statsCache,
                            new InnosiliconFactory(ApiType.HS_API),
                            new DragonmintChangePoolsAction()),
            (threadPool, blacklist, statsCache) ->
                    AsyncActionFactory.toAsync(
                            threadPool,
                            blacklist,
                            statsCache,
                            new InnosiliconFactory(ApiType.HS_API),
                            new DragonmintRebootAction()),
            (threadPool, blacklist, statsCache) ->
                    new ChainedAsicAction(
                            AsyncActionFactory.toAsync(
                                    threadPool,
                                    blacklist,
                                    statsCache,
                                    new InnosiliconFactory(ApiType.HS_API),
                                    new DragonmintFactoryResetAction()),
                            AsyncActionFactory.toAsync(
                                    threadPool,
                                    blacklist,
                                    statsCache,
                                    new InnosiliconFactory(ApiType.HS_API),
                                    new DragonmintChangePoolsAction())),
            (threadPool, blacklist, statsCache) ->
                    AsyncActionFactory.toAsync(
                            threadPool,
                            blacklist,
                            statsCache,
                            new InnosiliconFactory(ApiType.HS_API),
                            new DragonmintNetworkAction(),
                            AsyncAsicActionUtils::ipChangingHook)),

    /** MultMiner. */
    MULTMINER(
            "multminer",
            (args, ip) -> new MultMinerDetectionStrategy(),
            (threadPool, blacklist, statsCache) ->
                    AsyncActionFactory.toAsync(
                            threadPool,
                            blacklist,
                            statsCache,
                            new MultMinerFactory(),
                            new MultMinerChangePoolsAction()),
            (threadPool, blacklist, statsCache) ->
                    AsyncActionFactory.toAsync(
                            threadPool,
                            blacklist,
                            statsCache,
                            new MultMinerFactory(),
                            new MultMinerRebootAction()),
            (threadPool, blacklist, statsCache) -> new NullAsicAction(),
            (threadPool, blacklist, statsCache) -> new NullAsicAction()),

    /** Obelisk. */
    OBELISK(
            "obelisk",
            (args, ip) ->
                    new ObeliskDetectionStrategy<>(
                            new ObeliskMacStrategy(
                                    ip,
                                    80)),
            (threadPool, blacklist, statsCache) ->
                    AsyncActionFactory.toAsync(
                            threadPool,
                            blacklist,
                            statsCache,
                            new ObeliskFactory(),
                            new ObeliskChangePoolsAction()),
            (threadPool, blacklist, statsCache) ->
                    AsyncActionFactory.toAsync(
                            threadPool,
                            blacklist,
                            statsCache,
                            new ObeliskFactory(),
                            new ObeliskRebootAction()),
            (threadPool, blacklist, statsCache) ->
                    new ChainedAsicAction(
                            AsyncActionFactory.toAsync(
                                    threadPool,
                                    blacklist,
                                    statsCache,
                                    new ObeliskFactory(),
                                    new ObeliskFactoryResetAction()),
                            AsyncActionFactory.toAsync(
                                    threadPool,
                                    blacklist,
                                    statsCache,
                                    new ObeliskFactory(),
                                    new ObeliskChangePoolsAction())),
            (threadPool, blacklist, statsCache) ->
                    AsyncActionFactory.toAsync(
                            threadPool,
                            blacklist,
                            statsCache,
                            new ObeliskFactory(),
                            new ObeliskNetworkAction(),
                            AsyncAsicActionUtils::ipChangingHook)),

    /** Spondoolies. */
    SPONDOOLIES(
            "spondoolies",
            (args, ip) ->
                    new CgMinerDetectionStrategy(
                            CgMinerCommand.SUMMARY,
                            new SpondooliesTypeFactory()),
            (threadPool, blacklist, statsCache) -> new NullAsicAction(),
            (threadPool, blacklist, statsCache) -> new NullAsicAction(),
            (threadPool, blacklist, statsCache) -> new NullAsicAction(),
            (threadPool, blacklist, statsCache) -> new NullAsicAction()),

    /** StrongU. */
    STRONGU(
            "strongu",
            (args, ip) ->
                    new CgMinerDetectionStrategy(
                            CgMinerCommand.DEVS,
                            new StrongUTypeFactory(),
                            new StockMacStrategy(
                                    ip,
                                    80,
                                    "stuMiner Configuration",
                                    args.getOrDefault("username", "").toString(),
                                    args.getOrDefault("password", "").toString()),
                            new NullPatchingStrategy()),
            (threadPool, blacklist, statsCache) ->
                    AsyncActionFactory.toAsync(
                            threadPool,
                            blacklist,
                            statsCache,
                            new StrongUFactory(),
                            new StockChangePoolsAction(
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
                                            StrongUConfValue.PLL_STEP))),
            (threadPool, blacklist, statsCache) ->
                    AsyncActionFactory.toAsync(
                            threadPool,
                            blacklist,
                            statsCache,
                            new StrongUFactory(),
                            new StockRebootAction(
                                    "stuMiner Configuration")),
            (threadPool, blacklist, statsCache) ->
                    new ChainedAsicAction(
                            AsyncActionFactory.toAsync(
                                    threadPool,
                                    blacklist,
                                    statsCache,
                                    new StrongUFactory(),
                                    new StockFactoryResetAction(
                                            "stuMiner Configuration")),
                            AsyncActionFactory.toAsync(
                                    threadPool,
                                    blacklist,
                                    statsCache,
                                    new StrongUFactory(),
                                    new StockChangePoolsAction(
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
                                                    StrongUConfValue.PLL_STEP)))),
            (threadPool, blacklist, statsCache) ->
                    AsyncActionFactory.toAsync(
                            threadPool,
                            blacklist,
                            statsCache,
                            new StrongUFactory(),
                            new StockNetworkAction(
                                    "stuMiner Configuration",
                                    "stu"),
                            AsyncAsicActionUtils::ipChangingHook)),

    /** Whatsminer. */
    WHATSMINER(
            "whatsminer",
            (args, ip) ->
                    new FirmwareAwareDetectionStrategy(
                            new WhatsminerDetectionStrategy(
                                    new NewFirmwareMacStrategy(
                                            ip,
                                            4028)),
                            new CgMinerDetectionStrategy(
                                    CgMinerCommand.STATS,
                                    new WhatsminerTypeFactory(),
                                    new NewFirmwareMacStrategy(
                                            ip,
                                            4028),
                                    new NullPatchingStrategy())),
            (threadPool, blacklist, statsCache) ->
                    AsyncActionFactory.toAsync(
                            threadPool,
                            blacklist,
                            statsCache,
                            new WhatsminerFactory(),
                            new WhatsminerFirmwareAwareAction(
                                    new WhatsminerChangePoolsActionOld(),
                                    new WhatsminerChangePoolsActionNew())),
            (threadPool, blacklist, statsCache) ->
                    AsyncActionFactory.toAsync(
                            threadPool,
                            blacklist,
                            statsCache,
                            new WhatsminerFactory(),
                            new WhatsminerFirmwareAwareAction(
                                    new WhatsminerRebootActionOld(),
                                    new WhatsminerRebootActionNew())),
            (threadPool, blacklist, statsCache) ->
                    AsyncActionFactory.toAsync(
                            threadPool,
                            blacklist,
                            statsCache,
                            new WhatsminerFactory(),
                            new WhatsminerFactoryResetStrategy()),
            (threadPool, blacklist, statsCache) ->
                    AsyncActionFactory.toAsync(
                            threadPool,
                            blacklist,
                            statsCache,
                            new WhatsminerFactory(),
                            new WhatsminerNetworkAction(),
                            AsyncAsicActionUtils::ipChangingHook));

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
    private final ActionSupplier changePoolsStrategy;

    /** The strategy for detecting. */
    private final BiFunction<Map<String, Object>, String, DetectionStrategy> detectionStrategy;

    /** The strategy for performing a factory reset. */
    private final ActionSupplier factoryResetStrategy;

    /** The name. */
    private final String name;

    /** The network strategy. */
    private final ActionSupplier networkStrategy;

    /** The strategy for rebooting. */
    private final ActionSupplier rebootStrategy;

    /**
     * Constructor.
     *
     * @param name                 The name.
     * @param detectionStrategy    The strategy for detecting.
     * @param changePoolsStrategy  The strategy for changing pools.
     * @param rebootStrategy       The strategy for rebooting.
     * @param factoryResetStrategy The strategy for factory resets.
     * @param networkStrategy      The strategy for configuring the network.
     */
    Manufacturer(
            final String name,
            final BiFunction<Map<String, Object>, String, DetectionStrategy> detectionStrategy,
            final ActionSupplier changePoolsStrategy,
            final ActionSupplier rebootStrategy,
            final ActionSupplier factoryResetStrategy,
            final ActionSupplier networkStrategy) {
        this.name = name;
        this.detectionStrategy = detectionStrategy;
        this.changePoolsStrategy = changePoolsStrategy;
        this.rebootStrategy = rebootStrategy;
        this.factoryResetStrategy = factoryResetStrategy;
        this.networkStrategy = networkStrategy;
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
     * @param threadPool The thread pool.
     * @param blacklist  The blacklist.
     * @param statsCache The stats cache.
     *
     * @return The strategy for changing pools.
     */
    public AsicAction getChangePoolsAction(
            final ScheduledExecutorService threadPool,
            final Set<MinerID> blacklist,
            final StatsCache statsCache) {
        return this.changePoolsStrategy.create(
                threadPool,
                blacklist,
                statsCache);
    }

    /**
     * Returns the strategy.
     *
     * @param args The args.
     * @param ip   The IP.
     *
     * @return The strategy.
     */
    public DetectionStrategy getDetectionStrategy(
            final Map<String, Object> args,
            final String ip) {
        return this.detectionStrategy.apply(
                args,
                ip);
    }

    /**
     * Returns the strategy for factory resets.
     *
     * @param threadPool The thread pool.
     * @param blacklist  The blacklist.
     * @param statsCache The stats cache.
     *
     * @return The action for factory resets.
     */
    public AsicAction getFactoryResetStrategy(
            final ScheduledExecutorService threadPool,
            final Set<MinerID> blacklist,
            final StatsCache statsCache) {
        return this.factoryResetStrategy.create(
                threadPool,
                blacklist,
                statsCache);
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
     * Returns the strategy for configuring networks.
     *
     * @param threadPool The thread pool.
     * @param blacklist  The blacklist.
     * @param statsCache The stats cache.
     *
     * @return The action for factory resets.
     */
    public AsicAction getNetworkStrategy(
            final ScheduledExecutorService threadPool,
            final Set<MinerID> blacklist,
            final StatsCache statsCache) {
        return this.networkStrategy.create(
                threadPool,
                blacklist,
                statsCache);
    }

    /**
     * Returns the action for rebooting.
     *
     * @param threadPool The thread pool.
     * @param blacklist  The blacklist.
     * @param statsCache The stats cache.
     *
     * @return The action for rebooting.
     */
    public AsicAction getRebootAction(
            final ScheduledExecutorService threadPool,
            final Set<MinerID> blacklist,
            final StatsCache statsCache) {
        return this.rebootStrategy.create(
                threadPool,
                blacklist,
                statsCache);
    }

    /** Provides a supplier for creating {@link AsicAction actions}. */
    @FunctionalInterface
    private interface ActionSupplier {

        /**
         * Creates an {@link AsicAction}.
         *
         * @param threadPool The thread pool.
         * @param blacklist  The blacklist.
         * @param statsCache The cache.
         *
         * @return The action.
         */
        AsicAction create(
                ScheduledExecutorService threadPool,
                Set<MinerID> blacklist,
                StatsCache statsCache);
    }
}