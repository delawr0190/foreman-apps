package mn.foreman.pickaxe.miners.remote;

import mn.foreman.antminer.AntminerFactory;
import mn.foreman.autominer.AutoMinerFactory;
import mn.foreman.autominer.MinerMapping;
import mn.foreman.avalon.AvalonFactory;
import mn.foreman.baikal.BaikalFactory;
import mn.foreman.blackminer.BlackminerFactory;
import mn.foreman.bminer.BminerFactory;
import mn.foreman.castxmr.CastxmrFactory;
import mn.foreman.ccminer.CcminerFactory;
import mn.foreman.claymore.ClaymoreFactory;
import mn.foreman.claymore.TypeMapping;
import mn.foreman.cpuminer.CpuminerFactory;
import mn.foreman.dayun.DayunFactory;
import mn.foreman.dragonmint.DragonmintFactory;
import mn.foreman.dstm.DstmFactory;
import mn.foreman.ethminer.EthminerFactory;
import mn.foreman.ewbf.EwbfFactory;
import mn.foreman.excavator.ExcavatorFactory;
import mn.foreman.futurebit.FutureBitFactory;
import mn.foreman.gminer.GminerFactory;
import mn.foreman.grinpro.GrinProFactory;
import mn.foreman.honorknight.HonorKnightFactory;
import mn.foreman.hspminer.HspminerFactory;
import mn.foreman.hyperbit.HyperbitFactory;
import mn.foreman.innosilicon.InnosiliconFactory;
import mn.foreman.iximiner.IximinerFactory;
import mn.foreman.jceminer.JceminerFactory;
import mn.foreman.lolminer.LolminerFactory;
import mn.foreman.minerva.MinerVaFactory;
import mn.foreman.miniz.MinizFactory;
import mn.foreman.mkxminer.MkxminerFactory;
import mn.foreman.model.Miner;
import mn.foreman.model.MinerFactory;
import mn.foreman.multiminer.MultiminerFactory;
import mn.foreman.multminer.MultMinerFactory;
import mn.foreman.nanominer.NanominerFactory;
import mn.foreman.nbminer.NbminerFactory;
import mn.foreman.nicehash.NiceHashMinerFactory;
import mn.foreman.obelisk.ObeliskFactory;
import mn.foreman.openminer.OpenMinerFactory;
import mn.foreman.optiminer.OptiminerFactory;
import mn.foreman.pickaxe.miners.remote.json.MinerConfig;
import mn.foreman.rhminer.RhminerFactory;
import mn.foreman.sgminer.SgminerFactory;
import mn.foreman.spondoolies.SpondooliesFactory;
import mn.foreman.srbminer.SrbminerFactory;
import mn.foreman.strongu.StrongUFactory;
import mn.foreman.swarm.SwarmFactory;
import mn.foreman.trex.TrexFactory;
import mn.foreman.whatsminer.WhatsminerFactory;
import mn.foreman.xmrig.XmrigFactory;
import mn.foreman.xmrstak.XmrstakFactory;
import mn.foreman.xmrstak.XmrstakType;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * An API type represents a FOREMAN server json that indicates the miner API
 * type.
 */
public enum ApiType {

    /** bminer. */
    BMINER_API(
            1,
            (port, config, nicehash, autominer, claymore, nicehashMiners) -> {
                return new BminerFactory();
            }),

    /** castxmr. */
    CASTXMR_API(
            2,
            (port, config, nicehash, autominer, claymore, nicehashMiners) -> {
                return new CastxmrFactory();
            }),

    /** ccminer. */
    CCMINER_API(
            3,
            (port, config, nicehash, autominer, claymore, nicehashMiners) -> {
                return new CcminerFactory();
            }),

    /** dstm. */
    DSTM_API(
            4,
            (port, config, nicehash, autominer, claymore, nicehashMiners) -> {
                return new DstmFactory();
            }),

    /** ethminer. */
    ETHMINER_API(
            5,
            (port, config, nicehash, autominer, claymore, nicehashMiners) -> {
                return new EthminerFactory();
            }),

    /** ewbf. */
    EWBF_API(
            6,
            (port, config, nicehash, autominer, claymore, nicehashMiners) -> {
                return new EwbfFactory();
            }),

    /** excavator. */
    EXCAVATOR_API(
            7,
            (port, config, nicehash, autominer, claymore, nicehashMiners) -> {
                return new ExcavatorFactory();
            }),

    /** jceminer. */
    JCEMINER_API(
            8,
            (port, config, nicehash, autominer, claymore, nicehashMiners) -> {
                return new JceminerFactory();
            }),

    /** lolminer. */
    LOLMINER_API(
            9,
            (port, config, nicehash, autominer, claymore, nicehashMiners) -> {
                return new LolminerFactory();
            }),

    /** sgminer. */
    SGMINER_API(
            10,
            (port, config, nicehash, autominer, claymore, nicehashMiners) -> {
                return new SgminerFactory();
            }),

    /** srbminer. */
    SRBMINER_API(
            11,
            (port, config, nicehash, autominer, claymore, nicehashMiners) -> {
                return new SrbminerFactory();
            }),

    /** trex. */
    TREX_API(
            12,
            (port, config, nicehash, autominer, claymore, nicehashMiners) -> {
                return new TrexFactory();
            }),

    /** xmrig */
    XMRIG_API(
            13,
            (port, config, nicehash, autominer, claymore, nicehashMiners) -> {
                return new XmrigFactory();
            }),

    /** xmrstak (gpu). */
    XMRSTAK_GPU_API(
            14,
            (port, config, nicehash, autominer, claymore, nicehashMiners) -> {
                return new XmrstakFactory(XmrstakType.GPU);
            }),

    /** claymore-eth. */
    CLAYMORE_ETH_API(
            15,
            (port, config, nicehash, autominer, claymore, nicehashMiners) -> {
                return new ClaymoreFactory(claymore);
            }),

    /** claymore-zec. */
    CLAYMORE_ZEC_API(
            16,
            (port, config, nicehash, autominer, claymore, nicehashMiners) -> {
                return new ClaymoreFactory(claymore);
            }),

    /** Antminer API that reports rates in Hs. */
    ANTMINER_HS_API(
            17,
            (port, config, nicehash, autominer, claymore, nicehashMiners) -> {
                return new AntminerFactory(new BigDecimal(0.000000001));
            }),

    /** Antminer API that reports rates in MHs. */
    ANTMINER_MHS_API(
            18,
            (port, config, nicehash, autominer, claymore, nicehashMiners) -> {
                return new AntminerFactory(new BigDecimal(0.001));
            }),

    /** Antminer API that reports rates in GHs. */
    ANTMINER_GHS_API(
            19,
            (port, config, nicehash, autominer, claymore, nicehashMiners) -> {
                return new AntminerFactory(BigDecimal.ONE);
            }),

    /** Antminer API that reports rates in MHs. */
    ANTMINER_KHS_API(
            20,
            (port, config, nicehash, autominer, claymore, nicehashMiners) -> {
                return new AntminerFactory(new BigDecimal(0.000001));
            }),

    /** baikal. */
    BAIKAL_API(
            21,
            (port, config, nicehash, autominer, claymore, nicehashMiners) -> {
                return new BaikalFactory();
            }),

    /** dragonmint. */
    DRAGONMINT_API(
            22,
            (port, config, nicehash, autominer, claymore, nicehashMiners) -> {
                return new DragonmintFactory();
            }),

    /** Innosilicon. */
    INNOSILICON_HS_API(
            23,
            (port, config, nicehash, autominer, claymore, nicehashMiners) -> {
                return new InnosiliconFactory(mn.foreman.innosilicon.ApiType.HS_API);
            }),

    /** Innosilicon. */
    INNOSILICON_KHS_API(
            24,
            (port, config, nicehash, autominer, claymore, nicehashMiners) -> {
                return new InnosiliconFactory(mn.foreman.innosilicon.ApiType.KHS_API);
            }),

    /** Innosilicon. */
    INNOSILICON_MHS_API(
            25,
            (port, config, nicehash, autominer, claymore, nicehashMiners) -> {
                return new InnosiliconFactory(mn.foreman.innosilicon.ApiType.MHS_API);
            }),

    /** Innosilicon. */
    INNOSILICON_GHS_API(
            26,
            (port, config, nicehash, autominer, claymore, nicehashMiners) -> {
                return new InnosiliconFactory(mn.foreman.innosilicon.ApiType.GHS_API);
            }),

    /** Dayun. */
    DAYUN_API(
            27,
            (port, config, nicehash, autominer, claymore, nicehashMiners) -> {
                return new DayunFactory();
            }),

    /** Whatsminer. */
    WHATSMINER_API(
            28,
            (port, config, nicehash, autominer, claymore, nicehashMiners) -> {
                return new WhatsminerFactory();
            }),

    /** Avalon. */
    AVALON_API(
            29,
            (port, config, nicehash, autominer, claymore, nicehashMiners) -> {
                return new AvalonFactory();
            }),

    /** Gminer. */
    GMINER_API(
            30,
            (port, config, nicehash, autominer, claymore, nicehashMiners) -> {
                return new GminerFactory();
            }),

    /** mkxminer. */
    MKXMINER_API(
            31,
            (port, config, nicehash, autominer, claymore, nicehashMiners) -> {
                return new MkxminerFactory();
            }),

    /** Blackminer API. */
    BLACKMINER_API(
            32,
            (port, config, nicehash, autominer, claymore, nicehashMiners) -> {
                return new BlackminerFactory();
            }),

    /** rhminer. */
    RHMINER_API(
            33,
            (port, config, nicehash, autominer, claymore, nicehashMiners) -> {
                return new RhminerFactory();
            }),

    /** multiminer. */
    MULTIMINER_API(
            34,
            (port, config, nicehash, autominer, claymore, nicehashMiners) -> {
                return new MultiminerFactory();
            }),

    /** hspminer. */
    HSPMINER_API(
            35,
            (port, config, nicehash, autominer, claymore, nicehashMiners) -> {
                return new HspminerFactory();
            }),

    /** nanominer. */
    NANOMINER_API(
            36,
            (port, config, nicehash, autominer, claymore, nicehashMiners) -> {
                return new NanominerFactory();
            }),

    /** nicehash. */
    NICEHASH_API(
            37,
            (port, config, nicehash, autominer, claymore, nicehashMiners) -> {
                return new NiceHashMinerFactory(
                        nicehashMiners.create(
                                config,
                                nicehash,
                                autominer,
                                claymore));
            }),

    /** optiminer. */
    OPTIMINER_API(
            38,
            (port, config, nicehash, autominer, claymore, nicehashMiners) -> {
                return new OptiminerFactory();
            }),

    /** grinpro. */
    GRINPRO_API(
            39,
            (port, config, nicehash, autominer, claymore, nicehashMiners) -> {
                return new GrinProFactory();
            }),

    /** autominer. */
    AUTOMINER_API(
            40,
            (port, config, nicehash, autominer, claymore, nicehashMiners) -> {
                final MinerMapping.Builder mappingBuilder =
                        new MinerMapping.Builder();
                autominer.forEach(
                        (s, apiType) ->
                                mappingBuilder.addMapping(
                                        s,
                                        apiType.toFactory(
                                                port,
                                                config,
                                                nicehash,
                                                autominer,
                                                claymore,
                                                nicehashMiners)));
                return new AutoMinerFactory(mappingBuilder.build());
            }),

    /** spondoolies. */
    SPONDOOLIES_API(
            41,
            (port, config, nicehash, autominer, claymore, nicehashMiners) -> {
                return new SpondooliesFactory();
            }),

    /** nbminer. */
    NBMINER_API(
            42,
            (port, config, nicehash, autominer, claymore, nicehashMiners) -> {
                return new NbminerFactory();
            }),

    /** miniz. */
    MINIZ_API(
            43,
            (port, config, nicehash, autominer, claymore, nicehashMiners) -> {
                return new MinizFactory();
            }),

    /** futurebit. */
    FUTUREBIT_API(
            44,
            (port, config, nicehash, autominer, claymore, nicehashMiners) -> {
                return new FutureBitFactory();
            }),

    /** swarm. */
    SWARM_API(
            45,
            (port, config, nicehash, autominer, claymore, nicehashMiners) -> {
                return new SwarmFactory();
            }),

    /** xmrstak (cpu). */
    XMRSTAK_CPU_API(
            46,
            (port, config, nicehash, autominer, claymore, nicehashMiners) -> {
                return new XmrstakFactory(XmrstakType.CPU);
            }),

    /** cpuminer. */
    CPUMINER_API(
            47,
            (port, config, nicehash, autominer, claymore, nicehashMiners) -> {
                return new CpuminerFactory();
            }),

    /** iximiner. */
    IXIMINER_API(
            48,
            (port, config, nicehash, autominer, claymore, nicehashMiners) -> {
                return new IximinerFactory();
            }),

    /** aixin. */
    AIXIN_API(
            49,
            (port, config, nicehash, autominer, claymore, nicehashMiners) -> {
                return new HonorKnightFactory();
            }),

    /** hyperbit. */
    HYPERBIT_API(
            50,
            (port, config, nicehash, autominer, claymore, nicehashMiners) -> {
                return new HyperbitFactory();
            }),

    /** obelisk. */
    OBELISK_API(
            51,
            (port, config, nicehash, autominer, claymore, nicehashMiners) -> {
                return new ObeliskFactory();
            }),

    /** strongu. */
    STRONGU_API(
            52,
            (port, config, nicehash, autominer, claymore, nicehashMiners) -> {
                return new StrongUFactory();
            }),

    /** multminer. */
    MULTMINER_API(
            53,
            (port, config, nicehash, autominer, claymore, nicehashMiners) -> {
                return new MultMinerFactory();
            }),

    /** miner-va. */
    MINERVA_API(
            54,
            (port, config, nicehash, autominer, claymore, nicehashMiners) -> {
                return new MinerVaFactory();
            }),

    /** open-miner. */
    OPENMINER_API(
            55,
            (port, config, nicehash, autominer, claymore, nicehashMiners) -> {
                return new OpenMinerFactory();
            });

    /** A mapping of {@link #type} to {@link ApiType}. */
    private static final Map<Integer, ApiType> MAPPINGS;

    static {
        MAPPINGS = new ConcurrentHashMap<>();
        for (final ApiType apiType : values()) {
            MAPPINGS.put(apiType.getType(), apiType);
        }
    }

    /** The factory supplier. */
    private final FactorySupplier factorySupplier;

    /** The type. */
    private final int type;

    /**
     * Constructor.
     *
     * @param type The type.
     */
    ApiType(
            final int type,
            final FactorySupplier factorySupplier) {
        this.type = type;
        this.factorySupplier = factorySupplier;
    }

    /**
     * Returns the type for the provided value.
     *
     * @param value The value.
     *
     * @return The type.
     */
    @JsonCreator
    public static ApiType forValue(final int value) {
        return MAPPINGS.get(value);
    }

    /**
     * Returns the type.
     *
     * @return The type.
     */
    @JsonValue
    public int getType() {
        return this.type;
    }

    /**
     * Creates a factory for the type.
     *
     * @param port           The port.
     * @param config         The config.
     * @param nicehash       The nicehash config.
     * @param autominer      The autominer config.
     * @param claymore       The claymore config.
     * @param nicehashMiners The nicehash miners.
     *
     * @return The new factory.
     */
    public MinerFactory toFactory(
            final int port,
            final MinerConfig config,
            final List<ApiType> nicehash,
            final Map<String, ApiType> autominer,
            final TypeMapping claymore,
            final MinerSupplier nicehashMiners) {
        return this.factorySupplier.create(
                port,
                config,
                nicehash,
                autominer,
                claymore,
                nicehashMiners);
    }

    /** A factory for creating {@link MinerFactory factories}. */
    @FunctionalInterface
    private interface FactorySupplier {

        /**
         * Creates a {@link MinerFactory} from the provided configuration.
         *
         * @param port           The port.
         * @param config         The config.
         * @param nicehash       The nicehash config.
         * @param autominer      The autominer config.
         * @param claymore       The claymore config.
         * @param nicehashMiners The nicehash candidates.
         *
         * @return The factory.
         */
        MinerFactory create(
                int port,
                MinerConfig config,
                List<ApiType> nicehash,
                Map<String, ApiType> autominer,
                TypeMapping claymore,
                MinerSupplier nicehashMiners);
    }

    /**
     * A {@link MinerSupplier} provides a supplier for producing related
     * miners.
     */
    @FunctionalInterface
    public interface MinerSupplier {

        /**
         * Creates the miners that will be used.
         *
         * @param config    The config.
         * @param nicehash  The nicehash config.
         * @param autominer The autominer config.
         * @param claymore  The claymore config.
         *
         * @return The miners, if any.
         */
        List<Miner> create(
                MinerConfig config,
                List<ApiType> nicehash,
                Map<String, ApiType> autominer,
                TypeMapping claymore);
    }
}