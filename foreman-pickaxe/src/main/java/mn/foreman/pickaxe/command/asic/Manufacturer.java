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
import mn.foreman.ebang.*;
import mn.foreman.epic.*;
import mn.foreman.futurebit.FutureBitTypeFactory;
import mn.foreman.goldshell.*;
import mn.foreman.honorknight.*;
import mn.foreman.hyperbit.HyperbitTypeFactory;
import mn.foreman.innosilicon.ApiType;
import mn.foreman.innosilicon.InnosiliconFactory;
import mn.foreman.innosilicon.InnosiliconType;
import mn.foreman.minerva.MinerVaMacStrategy;
import mn.foreman.minerva.MinerVaTypeFactory;
import mn.foreman.model.*;
import mn.foreman.model.cache.StatsCache;
import mn.foreman.multminer.MultMinerChangePoolsAction;
import mn.foreman.multminer.MultMinerDetectionStrategy;
import mn.foreman.multminer.MultMinerFactory;
import mn.foreman.multminer.MultMinerRebootAction;
import mn.foreman.obelisk.*;
import mn.foreman.openminer.*;
import mn.foreman.spondoolies.SpondooliesTypeFactory;
import mn.foreman.strongu.StrongUConfValue;
import mn.foreman.strongu.StrongUFactory;
import mn.foreman.strongu.StrongUTypeFactory;
import mn.foreman.whatsminer.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import one.util.streamex.EntryStream;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/** An enumeration containing all of the known manufacturers. */
public enum Manufacturer {

    /** Aixin. */
    AIXIN(
            "aixin",
            (args, ip, configuration) ->
                    new HonorKnightDetectionStrategy<>(
                            AixinType::forType,
                            new HonorKnightMacStrategy(
                                    ip,
                                    80),
                            new HonorKnightFactory().create(
                                    EntryStream
                                            .of(args)
                                            .append(
                                                    "apiIp",
                                                    ip)
                                            .append(
                                                    "apiPort",
                                                    "4028")
                                            .toMap())),
            (threadPool, blacklist, statsCache, configuration) ->
                    AsicActionFactory.toAsync(
                            threadPool,
                            blacklist,
                            statsCache,
                            new HonorKnightFactory(),
                            new HonorKnightChangePoolsAction()),
            (threadPool, blacklist, statsCache, configuration) ->
                    AsicActionFactory.toAsync(
                            threadPool,
                            blacklist,
                            statsCache,
                            new HonorKnightFactory(),
                            new HonorKnightRebootAction()),
            (threadPool, blacklist, statsCache, configuration) -> new NullAsicAction(),
            (threadPool, blacklist, statsCache, configuration) ->
                    AsicActionFactory.toAsync(
                            threadPool,
                            blacklist,
                            statsCache,
                            new HonorKnightFactory(),
                            new HonorKnightNetworkAction()),
            (threadPool, blacklist, statsCache, configuration) -> new NullAsicAction(),
            (threadPool, blacklist, statsCache, configuration) -> new NullAsicAction(),
            (threadPool, blacklist, statsCache, configuration) -> new NullAsicAction()),

    /** Antminer. */
    ANTMINER(
            "antminer",
            (args, ip, configuration) ->
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
                                    new BraiinsHostnameStrategy()),
                            new AntminerFactory(BigDecimal.ONE).create(
                                    EntryStream
                                            .of(args)
                                            .append(
                                                    "apiIp",
                                                    ip)
                                            .append(
                                                    "apiPort",
                                                    "4028")
                                            .toMap())),
            (threadPool, blacklist, statsCache, configuration) ->
                    new ChainedAsicAction(
                            AsicActionFactory.toAsync(
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
                            AsicActionFactory.toAsync(
                                    threadPool,
                                    blacklist,
                                    statsCache,
                                    new AntminerFactory(BigDecimal.ONE),
                                    new FirmwareAwareAction(
                                            "antMiner Configuration",
                                            new StockRebootAction("antMiner Configuration"),
                                            new BraiinsRebootAction()))),
            (threadPool, blacklist, statsCache, configuration) ->
                    AsicActionFactory.toAsync(
                            threadPool,
                            blacklist,
                            statsCache,
                            new AntminerFactory(BigDecimal.ONE),
                            new FirmwareAwareAction(
                                    "antMiner Configuration",
                                    new StockRebootAction("antMiner Configuration"),
                                    new BraiinsRebootAction())),
            (threadPool, blacklist, statsCache, configuration) ->
                    new ChainedAsicAction(
                            AsicActionFactory.toSync(
                                    new FirmwareAwareAction(
                                            "antMiner Configuration",
                                            new StockFactoryResetAction(
                                                    "antMiner Configuration"),
                                            new BraiinsFactoryResetAction()),
                                    60,
                                    TimeUnit.SECONDS),
                            AsicActionFactory.toAsync(
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
            (threadPool, blacklist, statsCache, configuration) ->
                    AsicActionFactory.toAsync(
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
                            AsyncAsicActionUtils::ipChangingHook),
            (threadPool, blacklist, statsCache, configuration) ->
                    AsicActionFactory.toSync(
                            new FirmwareAwareAction(
                                    "antMiner Configuration",
                                    new StockPowerModeAction(
                                            "antMiner Configuration",
                                            new ObjectMapper()),
                                    new BraiinsPowerModeAction())),
            (threadPool, blacklist, statsCache, configuration) ->
                    AsicActionFactory.toSync(
                            new FirmwareAwareAction(
                                    "antMiner Configuration",
                                    new StockPasswordAction(
                                            "antMiner Configuration"),
                                    new BraiinsPasswordAction())),
            (threadPool, blacklist, statsCache, configuration) ->
                    new BlinkAction(
                            threadPool,
                            new StockBlinkStrategy(
                                    "antMiner Configuration"))),

    /** Avalon. */
    AVALON(
            "avalon",
            (args, ip, configuration) ->
                    new CgMinerDetectionStrategy(
                            CgMinerCommand.STATS,
                            new AvalonTypeFactory(),
                            new AvalonMacStrategy(
                                    ip,
                                    80),
                            new NullPatchingStrategy()),
            (threadPool, blacklist, statsCache, configuration) ->
                    AsicActionFactory.toAsync(
                            threadPool,
                            blacklist,
                            statsCache,
                            new AvalonFactory(),
                            new RetryingAction(
                                    5,
                                    1,
                                    TimeUnit.SECONDS,
                                    new AvalonChangePoolsAction(
                                            new AvalonRebootAction()))),
            (threadPool, blacklist, statsCache, configuration) ->
                    AsicActionFactory.toAsync(
                            threadPool,
                            blacklist,
                            statsCache,
                            new AvalonFactory(),
                            new RetryingAction(
                                    5,
                                    1,
                                    TimeUnit.SECONDS,
                                    new AvalonRebootAction())),
            (threadPool, blacklist, statsCache, configuration) -> new NullAsicAction(),
            (threadPool, blacklist, statsCache, configuration) ->
                    AsicActionFactory.toAsync(
                            threadPool,
                            blacklist,
                            statsCache,
                            new AvalonFactory(),
                            new RetryingAction(
                                    5,
                                    1,
                                    TimeUnit.SECONDS,
                                    new AvalonNetworkAction(
                                            new AvalonRebootAction())),
                            AsyncAsicActionUtils::ipChangingHook),
            (threadPool, blacklist, statsCache, configuration) -> new NullAsicAction(),
            (threadPool, blacklist, statsCache, configuration) ->
                    AsicActionFactory.toSync(
                            new RetryingAction(
                                    5,
                                    1,
                                    TimeUnit.SECONDS,
                                    new AvalonPasswordAction())),
            (threadPool, blacklist, statsCache, configuration) ->
                    new BlinkAction(
                            threadPool,
                            new AvalonBlinkStrategy())),

    /** Baikal. */
    BAIKAL(
            "baikal",
            (args, ip, configuration) ->
                    new BaikalDetectionStrategy(
                            "80",
                            new BaikalFactory().create(
                                    EntryStream
                                            .of(args)
                                            .append(
                                                    "apiIp",
                                                    ip)
                                            .append(
                                                    "apiPort",
                                                    "4028")
                                            .toMap())),
            (threadPool, blacklist, statsCache, configuration) ->
                    AsicActionFactory.toAsync(
                            threadPool,
                            blacklist,
                            statsCache,
                            new BaikalFactory(),
                            new BaikalChangePoolsAction()),
            (threadPool, blacklist, statsCache, configuration) ->
                    AsicActionFactory.toAsync(
                            threadPool,
                            blacklist,
                            statsCache,
                            new BaikalFactory(),
                            new BaikalRebootAction()),
            (threadPool, blacklist, statsCache, configuration) -> new NullAsicAction(),
            (threadPool, blacklist, statsCache, configuration) -> new NullAsicAction(),
            (threadPool, blacklist, statsCache, configuration) -> new NullAsicAction(),
            (threadPool, blacklist, statsCache, configuration) -> new NullAsicAction(),
            (threadPool, blacklist, statsCache, configuration) -> new NullAsicAction()),

    /** Bitfury. */
    BITFURY(
            "bitfury",
            (args, ip, configuration) -> new OpenMinerDetectionStrategy(),
            (threadPool, blacklist, statsCache, configuration) ->
                    AsicActionFactory.toAsync(
                            threadPool,
                            blacklist,
                            statsCache,
                            new OpenMinerFactory(),
                            new OpenMinerChangePoolsAction()),
            (threadPool, blacklist, statsCache, configuration) ->
                    AsicActionFactory.toAsync(
                            threadPool,
                            blacklist,
                            statsCache,
                            new OpenMinerFactory(),
                            new OpenMinerRebootAction()),
            (threadPool, blacklist, statsCache, configuration) -> new NullAsicAction(),
            (threadPool, blacklist, statsCache, configuration) -> new NullAsicAction(),
            (threadPool, blacklist, statsCache, configuration) -> new NullAsicAction(),
            (threadPool, blacklist, statsCache, configuration) ->
                    AsicActionFactory.toSync(
                            new OpenMinerPasswordAction()),
            (threadPool, blacklist, statsCache, configuration) -> new NullAsicAction()),

    /** Blackminer. */
    BLACKMINER(
            "blackminer",
            (args, ip, configuration) ->
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
            (threadPool, blacklist, statsCache, configuration) ->
                    AsicActionFactory.toAsync(
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
            (threadPool, blacklist, statsCache, configuration) ->
                    AsicActionFactory.toAsync(
                            threadPool,
                            blacklist,
                            statsCache,
                            new BlackminerFactory(),
                            new StockRebootAction("blackMiner Configuration")),
            (threadPool, blacklist, statsCache, configuration) ->
                    new ChainedAsicAction(
                            AsicActionFactory.toAsync(
                                    threadPool,
                                    blacklist,
                                    statsCache,
                                    new BlackminerFactory(),
                                    new StockFactoryResetAction(
                                            "blackMiner Configuration")),
                            AsicActionFactory.toAsync(
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
            (threadPool, blacklist, statsCache, configuration) ->
                    AsicActionFactory.toAsync(
                            threadPool,
                            blacklist,
                            statsCache,
                            new BlackminerFactory(),
                            new StockNetworkAction(
                                    "blackMiner Configuration",
                                    "bb"),
                            AsyncAsicActionUtils::ipChangingHook),
            (threadPool, blacklist, statsCache, configuration) -> new NullAsicAction(),
            (threadPool, blacklist, statsCache, configuration) ->
                    AsicActionFactory.toSync(
                            new StockPasswordAction(
                                    "blackMiner Configuration")),
            (threadPool, blacklist, statsCache, configuration) -> new NullAsicAction()),

    /** Cheetahminer. */
    CHEETAHMINER(
            "cheetahminer",
            (args, ip, configuration) ->
                    new HonorKnightDetectionStrategy<>(
                            CheetahminerType::forType,
                            new HonorKnightMacStrategy(
                                    ip,
                                    80),
                            new HonorKnightFactory().create(
                                    EntryStream
                                            .of(args)
                                            .append(
                                                    "apiIp",
                                                    ip)
                                            .append(
                                                    "apiPort",
                                                    "4028")
                                            .toMap())),
            (threadPool, blacklist, statsCache, configuration) ->
                    AsicActionFactory.toAsync(
                            threadPool,
                            blacklist,
                            statsCache,
                            new HonorKnightFactory(),
                            new HonorKnightChangePoolsAction()),
            (threadPool, blacklist, statsCache, configuration) ->
                    AsicActionFactory.toAsync(
                            threadPool,
                            blacklist,
                            statsCache,
                            new HonorKnightFactory(),
                            new HonorKnightRebootAction()),
            (threadPool, blacklist, statsCache, configuration) -> new NullAsicAction(),
            (threadPool, blacklist, statsCache, configuration) ->
                    AsicActionFactory.toAsync(
                            threadPool,
                            blacklist,
                            statsCache,
                            new HonorKnightFactory(),
                            new HonorKnightNetworkAction()),
            (threadPool, blacklist, statsCache, configuration) -> new NullAsicAction(),
            (threadPool, blacklist, statsCache, configuration) -> new NullAsicAction(),
            (threadPool, blacklist, statsCache, configuration) -> new NullAsicAction()),

    /** Dayun. */
    DAYUN(
            "dayun",
            (args, ip, configuration) ->
                    new CgMinerDetectionStrategy(
                            CgMinerCommand.STATS,
                            new DayunTypeFactory(),
                            new NullMacStrategy(),
                            new StatsPatchingStrategy()),
            (threadPool, blacklist, statsCache, configuration) -> new NullAsicAction(),
            (threadPool, blacklist, statsCache, configuration) -> new NullAsicAction(),
            (threadPool, blacklist, statsCache, configuration) -> new NullAsicAction(),
            (threadPool, blacklist, statsCache, configuration) -> new NullAsicAction(),
            (threadPool, blacklist, statsCache, configuration) -> new NullAsicAction(),
            (threadPool, blacklist, statsCache, configuration) -> new NullAsicAction(),
            (threadPool, blacklist, statsCache, configuration) -> new NullAsicAction()),

    /** Dragonmint. */
    DRAGONMINT(
            "dragonmint",
            (args, ip, configuration) ->
                    new DragonmintDetectionStrategy<>(
                            DragonmintType::forType,
                            "DragonMint",
                            new DragonmintMacStrategy(
                                    ip,
                                    80,
                                    args.getOrDefault("username", "").toString(),
                                    args.getOrDefault("password", "").toString())),
            (threadPool, blacklist, statsCache, configuration) ->
                    AsicActionFactory.toAsync(
                            threadPool,
                            blacklist,
                            statsCache,
                            new DragonmintFactory(),
                            new DragonmintChangePoolsAction()),
            (threadPool, blacklist, statsCache, configuration) ->
                    AsicActionFactory.toAsync(
                            threadPool,
                            blacklist,
                            statsCache,
                            new DragonmintFactory(),
                            new DragonmintRebootAction()),
            (threadPool, blacklist, statsCache, configuration) ->
                    new ChainedAsicAction(
                            AsicActionFactory.toAsync(
                                    threadPool,
                                    blacklist,
                                    statsCache,
                                    new DragonmintFactory(),
                                    new DragonmintFactoryResetAction()),
                            AsicActionFactory.toAsync(
                                    threadPool,
                                    blacklist,
                                    statsCache,
                                    new DragonmintFactory(),
                                    new DragonmintChangePoolsAction())),
            (threadPool, blacklist, statsCache, configuration) ->
                    AsicActionFactory.toAsync(
                            threadPool,
                            blacklist,
                            statsCache,
                            new DragonmintFactory(),
                            new DragonmintNetworkAction(),
                            AsyncAsicActionUtils::ipChangingHook),
            (threadPool, blacklist, statsCache, configuration) -> new NullAsicAction(),
            (threadPool, blacklist, statsCache, configuration) ->
                    AsicActionFactory.toSync(
                            new DragonmintPasswordAction()),
            (threadPool, blacklist, statsCache, configuration) -> new NullAsicAction()),

    /** Ebang. */
    EBANG(
            "ebang",
            (args, ip, configuration) ->
                    new EbangDetectionStrategy(
                            new EbangMacStrategy(
                                    ip,
                                    80,
                                    args.getOrDefault("username", "").toString(),
                                    args.getOrDefault("password", "").toString(),
                                    2,
                                    TimeUnit.SECONDS,
                                    new ObjectMapper()),
                            2,
                            TimeUnit.SECONDS,
                            new ObjectMapper()),
            (threadPool, blacklist, statsCache, configuration) ->
                    new ChainedAsicAction(
                            AsicActionFactory.toAsync(
                                    threadPool,
                                    blacklist,
                                    statsCache,
                                    new EbangFactory(
                                            1,
                                            TimeUnit.SECONDS,
                                            new ObjectMapper()),
                                    new EbangChangePoolsAction(
                                            1,
                                            TimeUnit.SECONDS,
                                            new ObjectMapper())),
                            AsicActionFactory.toAsync(
                                    threadPool,
                                    blacklist,
                                    statsCache,
                                    new EbangFactory(
                                            1,
                                            TimeUnit.SECONDS,
                                            new ObjectMapper()),
                                    new EbangRebootAction(
                                            1,
                                            TimeUnit.SECONDS))),
            (threadPool, blacklist, statsCache, configuration) ->
                    AsicActionFactory.toAsync(
                            threadPool,
                            blacklist,
                            statsCache,
                            new EbangFactory(
                                    1,
                                    TimeUnit.SECONDS,
                                    new ObjectMapper()),
                            new EbangRebootAction(
                                    1,
                                    TimeUnit.SECONDS)),
            (threadPool, blacklist, statsCache, configuration) ->
                    new ChainedAsicAction(
                            AsicActionFactory.toSync(
                                    new EbangFactoryResetAction(
                                            1,
                                            TimeUnit.SECONDS),
                                    60,
                                    TimeUnit.SECONDS),
                            AsicActionFactory.toAsync(
                                    threadPool,
                                    blacklist,
                                    statsCache,
                                    new EbangFactory(
                                            1,
                                            TimeUnit.SECONDS,
                                            new ObjectMapper()),
                                    new EbangChangePoolsAction(
                                            1,
                                            TimeUnit.SECONDS,
                                            new ObjectMapper()))),
            (threadPool, blacklist, statsCache, configuration) ->
                    AsicActionFactory.toAsync(
                            threadPool,
                            blacklist,
                            statsCache,
                            new EbangFactory(
                                    1,
                                    TimeUnit.SECONDS,
                                    new ObjectMapper()),
                            new EbangNetworkAction(
                                    1,
                                    TimeUnit.SECONDS,
                                    new ObjectMapper()),
                            AsyncAsicActionUtils::ipChangingHook),
            (threadPool, blacklist, statsCache, configuration) -> new NullAsicAction(),
            (threadPool, blacklist, statsCache, configuration) -> new NullAsicAction(),
            (threadPool, blacklist, statsCache, configuration) -> new NullAsicAction()),

    /** ePIC. */
    EPIC(
            "epic",
            (args, ip, configuration) -> new EpicDetectionStrategy(new NullMacStrategy()),
            (threadPool, blacklist, statsCache, configuration) ->
                    AsicActionFactory.toAsync(
                            threadPool,
                            blacklist,
                            statsCache,
                            new EpicFactory(),
                            new EpicChangePoolsAction()),
            (threadPool, blacklist, statsCache, configuration) ->
                    AsicActionFactory.toAsync(
                            threadPool,
                            blacklist,
                            statsCache,
                            new EpicFactory(),
                            new EpicRebootAction()),
            (threadPool, blacklist, statsCache, configuration) -> new NullAsicAction(),
            (threadPool, blacklist, statsCache, configuration) -> new NullAsicAction(),
            (threadPool, blacklist, statsCache, configuration) ->
                    AsicActionFactory.toSync(
                            new EpicPowerModeAction()),
            (threadPool, blacklist, statsCache, configuration) ->
                    AsicActionFactory.toSync(
                            new EpicPasswordAction()),
            (threadPool, blacklist, statsCache, configuration) -> new NullAsicAction()),

    /** FutureBit. */
    FUTUREBIT(
            "futurebit",
            (args, ip, configuration) ->
                    new CgMinerDetectionStrategy(
                            CgMinerCommand.DEVS,
                            new FutureBitTypeFactory()),
            (threadPool, blacklist, statsCache, configuration) -> new NullAsicAction(),
            (threadPool, blacklist, statsCache, configuration) -> new NullAsicAction(),
            (threadPool, blacklist, statsCache, configuration) -> new NullAsicAction(),
            (threadPool, blacklist, statsCache, configuration) -> new NullAsicAction(),
            (threadPool, blacklist, statsCache, configuration) -> new NullAsicAction(),
            (threadPool, blacklist, statsCache, configuration) -> new NullAsicAction(),
            (threadPool, blacklist, statsCache, configuration) -> new NullAsicAction()),

    /** Goldshell. */
    GOLDSHELL(
            "goldshell",
            (args, ip, configuration) ->
                    new GoldshellDetectionStrategy(
                            new GoldshellMacStrategy(
                                    ip,
                                    80,
                                    configuration),
                            configuration),
            (threadPool, blacklist, statsCache, configuration) ->
                    new ChainedAsicAction(
                            AsicActionFactory.toAsync(
                                    threadPool,
                                    blacklist,
                                    statsCache,
                                    new GoldshellFactory(
                                            configuration),
                                    new GoldshellChangePoolsAction(
                                            configuration)),
                            AsicActionFactory.toAsync(
                                    threadPool,
                                    blacklist,
                                    statsCache,
                                    new GoldshellFactory(
                                            configuration),
                                    new GoldshellRebootAction(
                                            configuration))),
            (threadPool, blacklist, statsCache, configuration) ->
                    AsicActionFactory.toAsync(
                            threadPool,
                            blacklist,
                            statsCache,
                            new GoldshellFactory(
                                    configuration),
                            new GoldshellRebootAction(
                                    configuration)),
            (threadPool, blacklist, statsCache, configuration) ->
                    new ChainedAsicAction(
                            AsicActionFactory.toSync(
                                    new GoldshellFactoryResetAction(
                                            configuration),
                                    60,
                                    TimeUnit.SECONDS),
                            AsicActionFactory.toAsync(
                                    threadPool,
                                    blacklist,
                                    statsCache,
                                    new GoldshellFactory(
                                            configuration),
                                    new GoldshellChangePoolsAction(
                                            configuration))),
            (threadPool, blacklist, statsCache, configuration) ->
                    AsicActionFactory.toAsync(
                            threadPool,
                            blacklist,
                            statsCache,
                            new GoldshellFactory(
                                    configuration),
                            new GoldshellNetworkAction(
                                    configuration),
                            AsyncAsicActionUtils::ipChangingHook),
            (threadPool, blacklist, statsCache, configuration) -> new NullAsicAction(),
            (threadPool, blacklist, statsCache, configuration) ->
                    AsicActionFactory.toSync(
                            new GoldshellPasswordAction(
                                    configuration)),
            (threadPool, blacklist, statsCache, configuration) ->
                    new BlinkAction(
                            threadPool,
                            new GoldshellBlinkStrategy(
                                    configuration))),

    /** HonorKnight. */
    HONORKNIGHT(
            "honorknight",
            (args, ip, configuration) ->
                    new HonorKnightDetectionStrategy<>(
                            HonorKnightType::forType,
                            new HonorKnightMacStrategy(
                                    ip,
                                    80),
                            new HonorKnightFactory().create(
                                    EntryStream
                                            .of(args)
                                            .append(
                                                    "apiIp",
                                                    ip)
                                            .append(
                                                    "apiPort",
                                                    "4028")
                                            .toMap())),
            (threadPool, blacklist, statsCache, configuration) ->
                    AsicActionFactory.toAsync(
                            threadPool,
                            blacklist,
                            statsCache,
                            new HonorKnightFactory(),
                            new HonorKnightChangePoolsAction()),
            (threadPool, blacklist, statsCache, configuration) ->
                    AsicActionFactory.toAsync(
                            threadPool,
                            blacklist,
                            statsCache,
                            new HonorKnightFactory(),
                            new HonorKnightRebootAction()),
            (threadPool, blacklist, statsCache, configuration) -> new NullAsicAction(),
            (threadPool, blacklist, statsCache, configuration) ->
                    AsicActionFactory.toAsync(
                            threadPool,
                            blacklist,
                            statsCache,
                            new HonorKnightFactory(),
                            new HonorKnightNetworkAction()),
            (threadPool, blacklist, statsCache, configuration) -> new NullAsicAction(),
            (threadPool, blacklist, statsCache, configuration) -> new NullAsicAction(),
            (threadPool, blacklist, statsCache, configuration) -> new NullAsicAction()),

    /** HyperBit. */
    HYPERBIT(
            "hyperbit",
            (args, ip, configuration) ->
                    new CgMinerDetectionStrategy(
                            CgMinerCommand.DEVS,
                            new HyperbitTypeFactory()),
            (threadPool, blacklist, statsCache, configuration) -> new NullAsicAction(),
            (threadPool, blacklist, statsCache, configuration) -> new NullAsicAction(),
            (threadPool, blacklist, statsCache, configuration) -> new NullAsicAction(),
            (threadPool, blacklist, statsCache, configuration) -> new NullAsicAction(),
            (threadPool, blacklist, statsCache, configuration) -> new NullAsicAction(),
            (threadPool, blacklist, statsCache, configuration) -> new NullAsicAction(),
            (threadPool, blacklist, statsCache, configuration) -> new NullAsicAction()),

    /** Innosilicon. */
    INNOSILICON(
            "innosilicon",
            (args, ip, configuration) ->
                    new DragonmintDetectionStrategy<>(
                            InnosiliconType::forType,
                            "Innosilicon",
                            new DragonmintMacStrategy(
                                    ip,
                                    80,
                                    args.getOrDefault("username", "").toString(),
                                    args.getOrDefault("password", "").toString())),
            (threadPool, blacklist, statsCache, configuration) ->
                    AsicActionFactory.toAsync(
                            threadPool,
                            blacklist,
                            statsCache,
                            new InnosiliconFactory(ApiType.HS_API),
                            new DragonmintChangePoolsAction()),
            (threadPool, blacklist, statsCache, configuration) ->
                    AsicActionFactory.toAsync(
                            threadPool,
                            blacklist,
                            statsCache,
                            new InnosiliconFactory(ApiType.HS_API),
                            new DragonmintRebootAction()),
            (threadPool, blacklist, statsCache, configuration) ->
                    new ChainedAsicAction(
                            AsicActionFactory.toAsync(
                                    threadPool,
                                    blacklist,
                                    statsCache,
                                    new InnosiliconFactory(ApiType.HS_API),
                                    new DragonmintFactoryResetAction()),
                            AsicActionFactory.toAsync(
                                    threadPool,
                                    blacklist,
                                    statsCache,
                                    new InnosiliconFactory(ApiType.HS_API),
                                    new DragonmintChangePoolsAction())),
            (threadPool, blacklist, statsCache, configuration) ->
                    AsicActionFactory.toAsync(
                            threadPool,
                            blacklist,
                            statsCache,
                            new InnosiliconFactory(ApiType.HS_API),
                            new DragonmintNetworkAction(),
                            AsyncAsicActionUtils::ipChangingHook),
            (threadPool, blacklist, statsCache, configuration) -> new NullAsicAction(),
            (threadPool, blacklist, statsCache, configuration) ->
                    AsicActionFactory.toSync(
                            new DragonmintPasswordAction()),
            (threadPool, blacklist, statsCache, configuration) -> new NullAsicAction()),

    /** Miner-Va. */
    MINERVA(
            "miner-va",
            (args, ip, configuration) ->
                    new CgMinerDetectionStrategy(
                            CgMinerCommand.STATS,
                            new MinerVaTypeFactory(),
                            new MinerVaMacStrategy(
                                    ip,
                                    4028),
                            new NullPatchingStrategy()),
            (threadPool, blacklist, statsCache, configuration) -> new NullAsicAction(),
            (threadPool, blacklist, statsCache, configuration) -> new NullAsicAction(),
            (threadPool, blacklist, statsCache, configuration) -> new NullAsicAction(),
            (threadPool, blacklist, statsCache, configuration) -> new NullAsicAction(),
            (threadPool, blacklist, statsCache, configuration) -> new NullAsicAction(),
            (threadPool, blacklist, statsCache, configuration) -> new NullAsicAction(),
            (threadPool, blacklist, statsCache, configuration) -> new NullAsicAction()),

    /** MultMiner. */
    MULTMINER(
            "multminer",
            (args, ip, configuration) ->
                    new MultMinerDetectionStrategy(
                            new MultMinerFactory().create(
                                    EntryStream
                                            .of(args)
                                            .append(
                                                    "apiIp",
                                                    ip)
                                            .append(
                                                    "apiPort",
                                                    "80")
                                            .toMap())),
            (threadPool, blacklist, statsCache, configuration) ->
                    AsicActionFactory.toAsync(
                            threadPool,
                            blacklist,
                            statsCache,
                            new MultMinerFactory(),
                            new MultMinerChangePoolsAction()),
            (threadPool, blacklist, statsCache, configuration) ->
                    AsicActionFactory.toAsync(
                            threadPool,
                            blacklist,
                            statsCache,
                            new MultMinerFactory(),
                            new MultMinerRebootAction()),
            (threadPool, blacklist, statsCache, configuration) -> new NullAsicAction(),
            (threadPool, blacklist, statsCache, configuration) -> new NullAsicAction(),
            (threadPool, blacklist, statsCache, configuration) -> new NullAsicAction(),
            (threadPool, blacklist, statsCache, configuration) -> new NullAsicAction(),
            (threadPool, blacklist, statsCache, configuration) -> new NullAsicAction()),

    /** Obelisk. */
    OBELISK(
            "obelisk",
            (args, ip, configuration) ->
                    new ObeliskDetectionStrategy<>(
                            new ObeliskMacStrategy(
                                    ip,
                                    80,
                                    1,
                                    TimeUnit.SECONDS,
                                    new ObjectMapper()),
                            new ObeliskFactory().create(
                                    EntryStream
                                            .of(args)
                                            .append(
                                                    "apiIp",
                                                    ip)
                                            .append(
                                                    "apiPort",
                                                    "80")
                                            .toMap()),
                            1,
                            TimeUnit.SECONDS,
                            new ObjectMapper()),
            (threadPool, blacklist, statsCache, configuration) ->
                    AsicActionFactory.toAsync(
                            threadPool,
                            blacklist,
                            statsCache,
                            new ObeliskFactory(),
                            new ObeliskChangePoolsAction(
                                    1,
                                    TimeUnit.SECONDS,
                                    new ObjectMapper())),
            (threadPool, blacklist, statsCache, configuration) ->
                    AsicActionFactory.toAsync(
                            threadPool,
                            blacklist,
                            statsCache,
                            new ObeliskFactory(),
                            new ObeliskRebootAction(
                                    1,
                                    TimeUnit.SECONDS)),
            (threadPool, blacklist, statsCache, configuration) ->
                    new ChainedAsicAction(
                            AsicActionFactory.toAsync(
                                    threadPool,
                                    blacklist,
                                    statsCache,
                                    new ObeliskFactory(),
                                    new ObeliskFactoryResetAction(
                                            1,
                                            TimeUnit.SECONDS)),
                            AsicActionFactory.toAsync(
                                    threadPool,
                                    blacklist,
                                    statsCache,
                                    new ObeliskFactory(),
                                    new ObeliskChangePoolsAction(
                                            1,
                                            TimeUnit.SECONDS,
                                            new ObjectMapper()))),
            (threadPool, blacklist, statsCache, configuration) ->
                    AsicActionFactory.toAsync(
                            threadPool,
                            blacklist,
                            statsCache,
                            new ObeliskFactory(),
                            new ObeliskNetworkAction(
                                    1,
                                    TimeUnit.SECONDS,
                                    new ObjectMapper()),
                            AsyncAsicActionUtils::ipChangingHook),
            (threadPool, blacklist, statsCache, configuration) -> new NullAsicAction(),
            (threadPool, blacklist, statsCache, configuration) ->
                    AsicActionFactory.toSync(
                            new ObeliskPasswordAction(
                                    1,
                                    TimeUnit.SECONDS,
                                    new ObjectMapper())),
            (threadPool, blacklist, statsCache, configuration) -> new NullAsicAction()),

    /** Spondoolies. */
    SPONDOOLIES(
            "spondoolies",
            (args, ip, configuration) ->
                    new CgMinerDetectionStrategy(
                            CgMinerCommand.SUMMARY,
                            new SpondooliesTypeFactory()),
            (threadPool, blacklist, statsCache, configuration) -> new NullAsicAction(),
            (threadPool, blacklist, statsCache, configuration) -> new NullAsicAction(),
            (threadPool, blacklist, statsCache, configuration) -> new NullAsicAction(),
            (threadPool, blacklist, statsCache, configuration) -> new NullAsicAction(),
            (threadPool, blacklist, statsCache, configuration) -> new NullAsicAction(),
            (threadPool, blacklist, statsCache, configuration) -> new NullAsicAction(),
            (threadPool, blacklist, statsCache, configuration) -> new NullAsicAction()),

    /** StrongU. */
    STRONGU(
            "strongu",
            (args, ip, configuration) ->
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
            (threadPool, blacklist, statsCache, configuration) ->
                    AsicActionFactory.toAsync(
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
            (threadPool, blacklist, statsCache, configuration) ->
                    AsicActionFactory.toAsync(
                            threadPool,
                            blacklist,
                            statsCache,
                            new StrongUFactory(),
                            new StockRebootAction(
                                    "stuMiner Configuration")),
            (threadPool, blacklist, statsCache, configuration) ->
                    new ChainedAsicAction(
                            AsicActionFactory.toAsync(
                                    threadPool,
                                    blacklist,
                                    statsCache,
                                    new StrongUFactory(),
                                    new StockFactoryResetAction(
                                            "stuMiner Configuration")),
                            AsicActionFactory.toAsync(
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
            (threadPool, blacklist, statsCache, configuration) ->
                    AsicActionFactory.toAsync(
                            threadPool,
                            blacklist,
                            statsCache,
                            new StrongUFactory(),
                            new StockNetworkAction(
                                    "stuMiner Configuration",
                                    "stu"),
                            AsyncAsicActionUtils::ipChangingHook),
            (threadPool, blacklist, statsCache, configuration) -> new NullAsicAction(),
            (threadPool, blacklist, statsCache, configuration) ->
                    AsicActionFactory.toSync(
                            new StockPasswordAction(
                                    "stuMiner Configuration")),
            (threadPool, blacklist, statsCache, configuration) -> new NullAsicAction()),

    /** Whatsminer. */
    WHATSMINER(
            "whatsminer",
            (args, ip, configuration) ->
                    new FirmwareAwareDetectionStrategy(
                            new WhatsminerDetectionStrategy(
                                    new NewFirmwareMacStrategy(
                                            ip,
                                            4028,
                                            configuration),
                                    new WhatsminerFactory(
                                            configuration).create(
                                            EntryStream
                                                    .of(args)
                                                    .append(
                                                            "apiIp",
                                                            ip)
                                                    .append(
                                                            "apiPort",
                                                            "4028")
                                                    .toMap()),
                                    configuration),
                            new CgMinerDetectionStrategy(
                                    CgMinerCommand.STATS,
                                    new WhatsminerTypeFactory(),
                                    new NewFirmwareMacStrategy(
                                            ip,
                                            4028,
                                            configuration),
                                    new NullPatchingStrategy())),
            (threadPool, blacklist, statsCache, configuration) ->
                    AsicActionFactory.toAsync(
                            threadPool,
                            blacklist,
                            statsCache,
                            new WhatsminerFactory(configuration),
                            new WhatsminerFirmwareAwareAction(
                                    new WhatsminerChangePoolsActionOld(
                                            configuration),
                                    new WhatsminerChangePoolsActionNew(
                                            configuration))),
            (threadPool, blacklist, statsCache, configuration) ->
                    AsicActionFactory.toAsync(
                            threadPool,
                            blacklist,
                            statsCache,
                            new WhatsminerFactory(configuration),
                            new WhatsminerFirmwareAwareAction(
                                    new WhatsminerRebootActionOld(
                                            configuration),
                                    new WhatsminerRebootActionNew(
                                            configuration))),
            (threadPool, blacklist, statsCache, configuration) ->
                    AsicActionFactory.toAsync(
                            threadPool,
                            blacklist,
                            statsCache,
                            new WhatsminerFactory(
                                    configuration),
                            new WhatsminerFactoryResetStrategy(
                                    configuration)),
            (threadPool, blacklist, statsCache, configuration) ->
                    AsicActionFactory.toAsync(
                            threadPool,
                            blacklist,
                            statsCache,
                            new WhatsminerFactory(
                                    configuration),
                            new WhatsminerNetworkAction(
                                    configuration),
                            AsyncAsicActionUtils::ipChangingHook),
            (threadPool, blacklist, statsCache, configuration) ->
                    AsicActionFactory.toSync(
                            new WhatsminerPowerModeAction(
                                    configuration)),
            (threadPool, blacklist, statsCache, configuration) ->
                    AsicActionFactory.toSync(
                            new WhatsminerPasswordAction(
                                    configuration)),
            (threadPool, blacklist, statsCache, configuration) ->
                    new BlinkAction(
                            threadPool,
                            new WhatsminerBlinkStrategy(
                                    configuration)));

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

    /** The strategy for blinking LEDs. */
    private final ActionSupplier blinkStrategy;

    /** The strategy for changing pools. */
    private final ActionSupplier changePoolsStrategy;

    /** The strategy for detecting. */
    private final DetectionSupplier detectionStrategy;

    /** The strategy for performing a factory reset. */
    private final ActionSupplier factoryResetStrategy;

    /** The name. */
    private final String name;

    /** The network strategy. */
    private final ActionSupplier networkStrategy;

    /** The strategy for changing passwords. */
    private final ActionSupplier passwordStrategy;

    /** The strategy for configuring power modes. */
    private final ActionSupplier powerModeStrategy;

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
     * @param powerModeStrategy    The strategy for configuring power modes.
     * @param passwordStrategy     The strategy for changing passwords.
     * @param blinkStrategy        The strategy for blinking LEDs.
     */
    Manufacturer(
            final String name,
            final DetectionSupplier detectionStrategy,
            final ActionSupplier changePoolsStrategy,
            final ActionSupplier rebootStrategy,
            final ActionSupplier factoryResetStrategy,
            final ActionSupplier networkStrategy,
            final ActionSupplier powerModeStrategy,
            final ActionSupplier passwordStrategy,
            final ActionSupplier blinkStrategy) {
        this.name = name;
        this.detectionStrategy = detectionStrategy;
        this.changePoolsStrategy = changePoolsStrategy;
        this.rebootStrategy = rebootStrategy;
        this.factoryResetStrategy = factoryResetStrategy;
        this.networkStrategy = networkStrategy;
        this.powerModeStrategy = powerModeStrategy;
        this.passwordStrategy = passwordStrategy;
        this.blinkStrategy = blinkStrategy;
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
     * Returns the strategy for blinking LEDs.
     *
     * @param threadPool    The thread pool.
     * @param blacklist     The blacklist.
     * @param statsCache    The stats cache.
     * @param configuration The configuration.
     *
     * @return The strategy for blinking LEDs.
     */
    public AsicAction getBlinkStrategy(
            final ScheduledExecutorService threadPool,
            final Set<MinerID> blacklist,
            final StatsCache statsCache,
            final ApplicationConfiguration configuration) {
        return this.blinkStrategy.create(
                threadPool,
                blacklist,
                statsCache,
                configuration);
    }

    /**
     * Returns the strategy for changing pools.
     *
     * @param threadPool    The thread pool.
     * @param blacklist     The blacklist.
     * @param statsCache    The stats cache.
     * @param configuration The configuration.
     *
     * @return The strategy for changing pools.
     */
    public AsicAction getChangePoolsAction(
            final ScheduledExecutorService threadPool,
            final Set<MinerID> blacklist,
            final StatsCache statsCache,
            final ApplicationConfiguration configuration) {
        return this.changePoolsStrategy.create(
                threadPool,
                blacklist,
                statsCache,
                configuration);
    }

    /**
     * Returns the strategy.
     *
     * @param args          The args.
     * @param ip            The IP.
     * @param configuration The configuration.
     *
     * @return The strategy.
     */
    public DetectionStrategy getDetectionStrategy(
            final Map<String, Object> args,
            final String ip,
            final ApplicationConfiguration configuration) {
        return this.detectionStrategy.create(
                args,
                ip,
                configuration);
    }

    /**
     * Returns the strategy for factory resets.
     *
     * @param threadPool    The thread pool.
     * @param blacklist     The blacklist.
     * @param statsCache    The stats cache.
     * @param configuration The configuration.
     *
     * @return The action for factory resets.
     */
    public AsicAction getFactoryResetStrategy(
            final ScheduledExecutorService threadPool,
            final Set<MinerID> blacklist,
            final StatsCache statsCache,
            final ApplicationConfiguration configuration) {
        return this.factoryResetStrategy.create(
                threadPool,
                blacklist,
                statsCache,
                configuration);
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
     * @param threadPool    The thread pool.
     * @param blacklist     The blacklist.
     * @param statsCache    The stats cache.
     * @param configuration The configuration.
     *
     * @return The action for factory resets.
     */
    public AsicAction getNetworkStrategy(
            final ScheduledExecutorService threadPool,
            final Set<MinerID> blacklist,
            final StatsCache statsCache,
            final ApplicationConfiguration configuration) {
        return this.networkStrategy.create(
                threadPool,
                blacklist,
                statsCache,
                configuration);
    }

    /**
     * Returns the strategy for changing passwords.
     *
     * @param threadPool    The thread pool.
     * @param blacklist     The blacklist.
     * @param statsCache    The stats cache.
     * @param configuration The configuration.
     *
     * @return The strategy for changing pools.
     */
    public AsicAction getPasswordStrategy(
            final ScheduledExecutorService threadPool,
            final Set<MinerID> blacklist,
            final StatsCache statsCache,
            final ApplicationConfiguration configuration) {
        return this.passwordStrategy.create(
                threadPool,
                blacklist,
                statsCache,
                configuration);
    }

    /**
     * Returns the strategy for changing power modes.
     *
     * @param threadPool    The thread pool.
     * @param blacklist     The blacklist.
     * @param statsCache    The stats cache.
     * @param configuration The configuration.
     *
     * @return The strategy for changing power modes.
     */
    public AsicAction getPowerModeStrategy(
            final ScheduledExecutorService threadPool,
            final Set<MinerID> blacklist,
            final StatsCache statsCache,
            final ApplicationConfiguration configuration) {
        return this.powerModeStrategy.create(
                threadPool,
                blacklist,
                statsCache,
                configuration);
    }

    /**
     * Returns the action for rebooting.
     *
     * @param threadPool    The thread pool.
     * @param blacklist     The blacklist.
     * @param statsCache    The stats cache.
     * @param configuration The configuration.
     *
     * @return The action for rebooting.
     */
    public AsicAction getRebootAction(
            final ScheduledExecutorService threadPool,
            final Set<MinerID> blacklist,
            final StatsCache statsCache,
            final ApplicationConfiguration configuration) {
        return this.rebootStrategy.create(
                threadPool,
                blacklist,
                statsCache,
                configuration);
    }

    /** Provides a supplier for creating {@link AsicAction actions}. */
    @FunctionalInterface
    private interface ActionSupplier {

        /**
         * Creates an {@link AsicAction}.
         *
         * @param threadPool    The thread pool.
         * @param blacklist     The blacklist.
         * @param statsCache    The cache.
         * @param configuration The configuration.
         *
         * @return The action.
         */
        AsicAction create(
                ScheduledExecutorService threadPool,
                Set<MinerID> blacklist,
                StatsCache statsCache,
                ApplicationConfiguration configuration);
    }

    private interface DetectionSupplier {

        DetectionStrategy create(
                Map<String, Object> args,
                String ip,
                ApplicationConfiguration configuration);
    }
}