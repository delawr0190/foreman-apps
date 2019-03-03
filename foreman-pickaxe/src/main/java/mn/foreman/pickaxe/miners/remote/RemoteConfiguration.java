package mn.foreman.pickaxe.miners.remote;

import mn.foreman.antminer.AntminerFactory;
import mn.foreman.antminer.AntminerType;
import mn.foreman.autominer.AutoMinerFactory;
import mn.foreman.avalon.AvalonFactory;
import mn.foreman.blackminer.BlackminerFactory;
import mn.foreman.bminer.BminerFactory;
import mn.foreman.castxmr.CastxmrFactory;
import mn.foreman.ccminer.CcminerFactory;
import mn.foreman.chisel.ChiselMinerDecorator;
import mn.foreman.claymore.ClaymoreFactory;
import mn.foreman.claymore.ClaymoreType;
import mn.foreman.dayun.DayunFactory;
import mn.foreman.dragonmint.Dragonmint;
import mn.foreman.dragonmint.DragonmintFactory;
import mn.foreman.dstm.DstmFactory;
import mn.foreman.ethminer.EthminerFactory;
import mn.foreman.ewbf.EwbfFactory;
import mn.foreman.excavator.ExcavatorFactory;
import mn.foreman.gminer.GminerFactory;
import mn.foreman.grinpro.GrinProFactory;
import mn.foreman.hspminer.HspminerFactory;
import mn.foreman.innosilicon.InnosiliconFactory;
import mn.foreman.jceminer.JceminerFactory;
import mn.foreman.lolminer.LolminerFactory;
import mn.foreman.mkxminer.MkxminerFactory;
import mn.foreman.model.Miner;
import mn.foreman.model.MinerFactory;
import mn.foreman.multiminer.MultiminerFactory;
import mn.foreman.nanominer.NanominerFactory;
import mn.foreman.nicehash.AlgorithmCandidates;
import mn.foreman.nicehash.NiceHashMiner;
import mn.foreman.optiminer.OptiminerFactory;
import mn.foreman.pickaxe.miners.MinerConfiguration;
import mn.foreman.pickaxe.miners.remote.json.MinerConfig;
import mn.foreman.rhminer.RhminerFactory;
import mn.foreman.sgminer.SgminerFactory;
import mn.foreman.srbminer.SrbminerFactory;
import mn.foreman.trex.TrexFactory;
import mn.foreman.whatsminer.WhatsminerFactory;
import mn.foreman.xmrig.XmrigFactory;
import mn.foreman.xmrstak.XmrstakFactory;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * A {@link RemoteConfiguration} provides a factory that will create the list of
 * {@link Miner miners} to query based on the results of a query to FOREMAN's
 * configuration API.  This allows users to configure their pickaxe instances
 * from the dashboard.
 */
public class RemoteConfiguration
        implements MinerConfiguration {

    /** The logger for this class. */
    private static final Logger LOG =
            LoggerFactory.getLogger(RemoteConfiguration.class);

    /** How long to wait on socket operations before disconnecting. */
    private static final int SOCKET_TIMEOUT =
            (int) TimeUnit.SECONDS.toMillis(10);

    /** The API key. */
    private final String apiKey;

    /** The config URL. */
    private final String configUrl;

    /** The nicehash config URL. */
    private final String nicehashConfigUrl;

    /**
     * Constructor.
     *
     * @param configUrl         The config URL.
     * @param nicehashConfigUrl The nicehash config URL.
     * @param apiKey            The API key.
     */
    public RemoteConfiguration(
            final String configUrl,
            final String nicehashConfigUrl,
            final String apiKey) {
        Validate.notNull(
                configUrl,
                "configUrl cannot be null");
        Validate.notEmpty(
                configUrl,
                "configUrl cannot be empty");
        Validate.notNull(
                nicehashConfigUrl,
                "nicehashConfigUrl cannot be null");
        Validate.notEmpty(
                nicehashConfigUrl,
                "nicehashConfigUrl cannot be empty");
        Validate.notNull(
                apiKey,
                "apiKey cannot be null");
        Validate.notEmpty(
                apiKey,
                "apiKey cannot be empty");
        this.configUrl = configUrl;
        this.nicehashConfigUrl = nicehashConfigUrl;
        this.apiKey = apiKey;
    }

    @Override
    public List<Miner> load()
            throws Exception {
        LOG.debug("Querying {} for miners", this.configUrl);

        final ObjectMapper objectMapper = new ObjectMapper();

        final List<MinerConfig> configs = new LinkedList<>();
        getConfig(
                this.configUrl,
                response -> {
                    try {
                        configs.addAll(
                                Arrays.asList(
                                        objectMapper.readValue(
                                                response,
                                                MinerConfig[].class)));
                    } catch (final IOException ioe) {
                        LOG.warn("Failed to parse response", ioe);
                    }
                });
        LOG.info("Downloaded configuration: {} miners", configs.size());

        final Map<Integer, List<ApiType>> niceHashConfig = new HashMap<>();
        getConfig(
                this.nicehashConfigUrl,
                response -> {
                    try {
                        niceHashConfig.putAll(
                                objectMapper.readValue(
                                        response,
                                        new TypeReference<Map<Integer, List<ApiType>>>() {
                                        }));
                    } catch (final IOException ioe) {
                        LOG.warn("Failed to parse response", ioe);
                    }
                });

        return toMiners(
                configs,
                niceHashConfig);
    }

    /**
     * Finds the {@link MinerConfig.Param} that matches the provided key.
     *
     * @param key    The key.
     * @param params The params.
     *
     * @return The matching param.
     */
    private static Optional<MinerConfig.Param> findParam(
            final String key,
            final List<MinerConfig.Param> params) {
        return params
                .stream()
                .filter(param -> key.equals(param.key))
                .findAny();
    }

    /**
     * Converts the type to an {@link AntminerType}.
     *
     * @param apiType The {@link ApiType}.
     *
     * @return The {@link AntminerType}.
     */
    private static String toAntminerType(
            final ApiType apiType) {
        String type = null;
        switch (apiType) {
            case ANTMINER_HS_API:
                type = AntminerType.ANTMINER_B3.getLabel();
                break;
            case ANTMINER_KHS_API:
                type = AntminerType.ANTMINER_Z9.getLabel();
                break;
            case ANTMINER_MHS_API:
                type = AntminerType.ANTMINER_L3.getLabel();
                break;
            case ANTMINER_GHS_API:
                type = AntminerType.ANTMINER_S9.getLabel();
                break;
            default:
                break;
        }
        return type;
    }

    /**
     * Creates an ASIC {@link Miner} from the provided config.
     *
     * @param port         The port.
     * @param apiType      The {@link ApiType}.
     * @param config       The config.
     * @param minerFactory The factory.
     * @param typeFunction The function for providing the type.
     *
     * @return The {@link Miner}.
     */
    private static Miner toAsic(
            final int port,
            final ApiType apiType,
            final MinerConfig config,
            final MinerFactory minerFactory,
            final Function<ApiType, String> typeFunction) {
        Miner miner = null;

        final String type = typeFunction.apply(apiType);
        if (type != null) {
            miner =
                    minerFactory.create(
                            ImmutableMap.of(
                                    "type",
                                    type,
                                    "apiIp",
                                    config.apiIp,
                                    "apiPort",
                                    Integer.toString(port)));
        }

        return miner;
    }

    /**
     * Creates a claymore {@link Miner} from the config.
     *
     * @param port    The port.
     * @param apiType The {@link ApiType}.
     * @param config  The config.
     *
     * @return The {@link Miner}.
     */
    private static Miner toClaymore(
            final int port,
            final ApiType apiType,
            final MinerConfig config) {
        final Map<String, String> attributes = new HashMap<>();
        attributes.put("type", toClaymoreType(apiType));
        attributes.put("apiIp", config.apiIp);
        attributes.put("apiPort", Integer.toString(port));
        findParam(
                "apiPassword",
                config.params).ifPresent(
                (password) ->
                        attributes.put(
                                "apiPassword",
                                password.value));
        return new ClaymoreFactory().create(attributes);
    }

    /**
     * Converts the {@link ApiType} to a {@link ClaymoreType}.
     *
     * @param apiType The {@link ApiType}.
     *
     * @return The {@link ClaymoreType}.
     */
    private static String toClaymoreType(
            final ApiType apiType) {
        String type = null;
        switch (apiType) {
            case CLAYMORE_ETH_API:
                type = ClaymoreType.ETH.name().toLowerCase();
                break;
            case CLAYMORE_ZEC_API:
                type = ClaymoreType.ZEC.name().toLowerCase();
                break;
            default:
                break;
        }
        return type;
    }

    /**
     * Creates a {@link Dragonmint} miner from the configuration.
     *
     * @param port   The port.
     * @param config The config.
     *
     * @return The {@link Miner}.
     */
    private static Miner toDragonmintApi(
            final int port,
            final MinerConfig config) {
        final Map<String, String> attributes = new HashMap<>();
        attributes.put("apiIp", config.apiIp);
        attributes.put("apiPort", Integer.toString(port));
        findParam(
                "username",
                config.params).ifPresent(
                (username) ->
                        attributes.put(
                                "username",
                                username.value));
        findParam(
                "password",
                config.params).ifPresent(
                (password) ->
                        attributes.put(
                                "password",
                                password.value));
        return new DragonmintFactory().create(attributes);
    }

    /**
     * Creates an ethminer {@link Miner} from the config.
     *
     * @param port   The port.
     * @param config The config.
     *
     * @return The {@link Miner}.
     */
    private static Miner toEthminerApi(
            final int port,
            final MinerConfig config) {
        final Map<String, String> attributes = new HashMap<>();
        attributes.put("apiIp", config.apiIp);
        attributes.put("apiPort", Integer.toString(port));
        findParam(
                "apiPassword",
                config.params).ifPresent(
                (password) ->
                        attributes.put(
                                "apiPassword",
                                password.value));
        return new EthminerFactory().create(attributes);
    }

    /**
     * Converts the type to an Innosilicon type.
     *
     * @param apiType The type.
     *
     * @return The type.
     */
    private static String toInnosiliconType(
            final ApiType apiType) {
        String type = null;
        switch (apiType) {
            case INNOSILICON_HS_API:
                type = mn.foreman.innosilicon.ApiType.HS_API.name();
                break;
            case INNOSILICON_KHS_API:
                type = mn.foreman.innosilicon.ApiType.KHS_API.name();
                break;
            case INNOSILICON_MHS_API:
                type = mn.foreman.innosilicon.ApiType.MHS_API.name();
                break;
            case INNOSILICON_GHS_API:
                type = mn.foreman.innosilicon.ApiType.GHS_API.name();
                break;
            default:
                break;
        }
        return type;
    }

    /**
     * Converts the provided config to a {@link Miner}.
     *
     * @param port         The port.
     * @param config       The config.
     * @param minerFactory The factory.
     *
     * @return The {@link Miner}.
     */
    private static Miner toMiner(
            final int port,
            final MinerConfig config,
            final mn.foreman.model.MinerFactory minerFactory) {
        return minerFactory.create(
                ImmutableMap.of(
                        "apiIp",
                        config.apiIp,
                        "apiPort",
                        Integer.toString(port)));
    }

    /**
     * Converts each {@link MinerConfig} to a {@link Miner}.
     *
     * @param apiType         The {@link ApiType}.
     * @param port            The port.
     * @param config          The {@link MinerConfig}.
     * @param niceHashConfigs The NiceHash configuration.
     *
     * @return The {@link Miner miners}.
     */
    private static List<Miner> toMiner(
            final ApiType apiType,
            final int port,
            final MinerConfig config,
            final Map<Integer, List<ApiType>> niceHashConfigs) {
        LOG.debug("Adding miner for {}", config);

        final List<Miner> miners = new LinkedList<>();
        switch (apiType) {
            case ANTMINER_HS_API:
                // Fall through
            case ANTMINER_KHS_API:
                // Fall through
            case ANTMINER_MHS_API:
                // Fall through
            case ANTMINER_GHS_API:
                miners.add(
                        toAsic(
                                port,
                                apiType,
                                config,
                                new AntminerFactory(),
                                RemoteConfiguration::toAntminerType));
                break;
            case AUTOMINER_API:
                miners.add(toMiner(port, config, new AutoMinerFactory()));
                break;
            case AVALON_API:
                miners.add(toMiner(port, config, new AvalonFactory()));
                break;
            case BAIKAL_API:
                miners.add(toMiner(port, config, new BlackminerFactory()));
                break;
            case BLACKMINER_API:
                miners.add(toMiner(port, config, new BlackminerFactory()));
                break;
            case BMINER_API:
                miners.add(toMiner(port, config, new BminerFactory()));
                break;
            case CASTXMR_API:
                miners.add(toMiner(port, config, new CastxmrFactory()));
                break;
            case CCMINER_API:
                miners.add(toMiner(port, config, new CcminerFactory()));
                break;
            case CLAYMORE_ETH_API:
                // Fall through
            case CLAYMORE_ZEC_API:
                miners.add(toClaymore(port, apiType, config));
                break;
            case DAYUN_API:
                miners.add(toMiner(port, config, new DayunFactory()));
                break;
            case DSTM_API:
                miners.add(toMiner(port, config, new DstmFactory()));
                break;
            case DRAGONMINT_API:
                miners.add(toDragonmintApi(port, config));
                break;
            case ETHMINER_API:
                miners.add(toEthminerApi(port, config));
                break;
            case EWBF_API:
                miners.add(toMiner(port, config, new EwbfFactory()));
                break;
            case EXCAVATOR_API:
                miners.add(toMiner(port, config, new ExcavatorFactory()));
                break;
            case GMINER_API:
                miners.add(toMiner(port, config, new GminerFactory()));
                break;
            case GRINPRO_API:
                miners.add(toMiner(port, config, new GrinProFactory()));
                break;
            case HSPMINER_API:
                miners.add(toMiner(port, config, new HspminerFactory()));
                break;
            case INNOSILICON_HS_API:
                // Fall through
            case INNOSILICON_KHS_API:
                // Fall through
            case INNOSILICON_MHS_API:
                // Fall through
            case INNOSILICON_GHS_API:
                miners.add(
                        toAsic(
                                port,
                                apiType,
                                config,
                                new InnosiliconFactory(),
                                RemoteConfiguration::toInnosiliconType));
                break;
            case JCEMINER_API:
                miners.add(toMiner(port, config, new JceminerFactory()));
                break;
            case LOLMINER_API:
                miners.add(toMiner(port, config, new LolminerFactory()));
                break;
            case MKXMINER_API:
                miners.add(toMiner(port, config, new MkxminerFactory()));
                break;
            case MULTIMINER_API:
                miners.add(toMiner(port, config, new MultiminerFactory()));
                break;
            case NANOMINER_API:
                miners.add(toMiner(port, config, new NanominerFactory()));
                break;
            case NICEHASH_API:
                miners.addAll(toNiceHashMiner(config, niceHashConfigs));
                break;
            case OPTIMINER_API:
                miners.add(toMiner(port, config, new OptiminerFactory()));
                break;
            case RHMINER_API:
                miners.add(toMiner(port, config, new RhminerFactory()));
                break;
            case SGMINER_API:
                miners.add(toMiner(port, config, new SgminerFactory()));
                break;
            case SRBMINER_API:
                miners.add(toMiner(port, config, new SrbminerFactory()));
                break;
            case TREX_API:
                miners.add(toMiner(port, config, new TrexFactory()));
                break;
            case WHATSMINER_API:
                miners.add(toMiner(port, config, new WhatsminerFactory()));
                break;
            case XMRIG_API:
                miners.add(toMiner(port, config, new XmrigFactory()));
                break;
            case XMRSTAK_API:
                miners.add(toMiner(port, config, new XmrstakFactory()));
                break;
            default:
                break;
        }

        // The user may be running chisel to extract additional metrics from
        // miners that offer weak APIs
        final MinerConfig.ChiselConfig chiselConfig = config.chisel;
        if (chiselConfig != null && chiselConfig.apiPort > 0) {
            final List<Miner> chiseledMiners = miners
                    .stream()
                    .map(miner ->
                            new ChiselMinerDecorator(
                                    config.apiIp,
                                    chiselConfig.apiPort,
                                    miner))
                    .collect(Collectors.toList());
            miners.clear();
            miners.addAll(chiseledMiners);
        }

        return miners;
    }

    /**
     * Creates a {@link Miner} from every miner in the {@link MinerConfig
     * configs}.
     *
     * @param configs         The configurations.
     * @param niceHashConfigs The NiceHash configurations.
     *
     * @return The {@link Miner miners}.
     */
    private static List<Miner> toMiners(
            final List<MinerConfig> configs,
            final Map<Integer, List<ApiType>> niceHashConfigs) {
        return configs
                .stream()
                .filter(config -> config.apiType != null)
                .map(config ->
                        toMiner(
                                config.apiType,
                                config.apiPort,
                                config,
                                niceHashConfigs))
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }

    /**
     * Creates a {@link NiceHashMiner}.
     *
     * @param config          The configuration.
     * @param niceHashConfigs The algorithm mappings.
     *
     * @return The new {@link Miner miners}.
     */
    private static List<Miner> toNiceHashMiner(
            final MinerConfig config,
            final Map<Integer, List<ApiType>> niceHashConfigs) {
        final List<Miner> miners = new LinkedList<>();
        final MinerConfig.NiceHashConfig niceHashConfig =
                config.niceHashConfig;
        if (niceHashConfig != null) {
            final AlgorithmCandidates.Builder candidatesBuilder =
                    new AlgorithmCandidates.Builder();
            // Query up to a 5-port range for nicehashlegacy
            for (int i = 0; i < 5; i++) {
                final int port = config.apiPort + i;
                niceHashConfigs.forEach((algoType, apiTypes) ->
                        apiTypes.stream()
                                .map(apiType ->
                                        toMiner(
                                                apiType,
                                                port,
                                                config,
                                                niceHashConfigs))
                                .flatMap(List::stream)
                                .forEach(m ->
                                        candidatesBuilder.addCandidate(
                                                algoType,
                                                m)));
            }
            miners.add(
                    new NiceHashMiner(
                            config.apiIp,
                            config.apiPort,
                            niceHashConfig.algo,
                            candidatesBuilder.build()));
        }
        return miners;
    }

    /**
     * Downloads the configurations.
     *
     * @param configUrl The configuration URL.
     *
     * @throws IOException on failure to download configs.
     */
    private void getConfig(
            final String configUrl,
            final Consumer<String> responseStrategy)
            throws IOException {
        final URL url = new URL(configUrl);
        final HttpURLConnection connection =
                (HttpURLConnection) url.openConnection();

        connection.setRequestMethod("GET");
        connection.setRequestProperty(
                "Authorization",
                "Token " + this.apiKey);
        connection.setConnectTimeout(SOCKET_TIMEOUT);
        connection.setReadTimeout(SOCKET_TIMEOUT);

        final int code = connection.getResponseCode();
        if (code == HttpURLConnection.HTTP_OK) {
            try (final InputStreamReader inputStreamReader =
                         new InputStreamReader(
                                 connection.getInputStream());
                 final BufferedReader reader =
                         new BufferedReader(
                                 inputStreamReader)) {
                responseStrategy.accept(IOUtils.toString(reader));
            }
        } else {
            LOG.warn("Failed to obtain a configuration from {}: {}",
                    configUrl,
                    code);
        }
    }
}