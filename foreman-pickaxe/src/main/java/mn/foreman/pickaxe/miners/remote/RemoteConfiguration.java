package mn.foreman.pickaxe.miners.remote;

import mn.foreman.antminer.AntminerFactory;
import mn.foreman.antminer.AntminerType;
import mn.foreman.baikal.BaikalFactory;
import mn.foreman.bminer.BminerFactory;
import mn.foreman.castxmr.CastxmrFactory;
import mn.foreman.ccminer.CcminerFactory;
import mn.foreman.claymore.ClaymoreFactory;
import mn.foreman.claymore.ClaymoreType;
import mn.foreman.dstm.DstmFactory;
import mn.foreman.ethminer.EthminerFactory;
import mn.foreman.ewbf.EwbfFactory;
import mn.foreman.excavator.ExcavatorFactory;
import mn.foreman.jceminer.JceminerFactory;
import mn.foreman.lolminer.LolminerFactory;
import mn.foreman.model.Miner;
import mn.foreman.pickaxe.miners.MinerConfiguration;
import mn.foreman.pickaxe.miners.remote.json.MinerConfig;
import mn.foreman.sgminer.SgminerFactory;
import mn.foreman.srbminer.SrbminerFactory;
import mn.foreman.trex.TrexFactory;
import mn.foreman.xmrig.XmrigFactory;
import mn.foreman.xmrstak.XmrstakFactory;

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

    /** The API key. */
    private final String apiKey;

    /** The URL. */
    private final String url;

    /**
     * Constructor.
     *
     * @param url The URL.
     */
    public RemoteConfiguration(
            final String url,
            final String apiKey) {
        Validate.notNull(
                url,
                "url cannot be null");
        Validate.notEmpty(
                url,
                "url cannot be empty");
        Validate.notNull(
                apiKey,
                "apiKey cannot be null");
        Validate.notEmpty(
                apiKey,
                "apiKey cannot be empty");
        this.url = url;
        this.apiKey = apiKey;
    }

    @Override
    public List<Miner> load() {
        LOG.debug("Querying {} for miners", this.url);

        final List<Miner> miners = new LinkedList<>();

        try {
            final List<MinerConfig> configs = getConfigs();
            LOG.info("Downloaded configuration: {} miners", configs.size());
            miners.addAll(
                    toMiners(
                            configs));
        } catch (final IOException ioe) {
            LOG.warn("Exception occurred while downloading configuration", ioe);
        }

        return miners;
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
     * Creates an Antminer {@link Miner} from the provided config.
     *
     * @param config The config.
     *
     * @return The {@link Miner}.
     */
    private static Miner toAntminer(
            final MinerConfig config) {
        Miner miner = null;

        final String type = toAntminerType(config.apiType);
        if (type != null) {
            miner =
                    new AntminerFactory().create(
                            ImmutableMap.of(
                                    "type",
                                    type,
                                    "apiIp",
                                    config.apiIp,
                                    "apiPort",
                                    Integer.toString(config.apiPort)));
        }

        return miner;
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
     * Creates a claymore {@link Miner} from the config.
     *
     * @param config The config.
     *
     * @return The {@link Miner}.
     */
    private static Miner toClaymore(
            final MinerConfig config) {
        final Map<String, String> attributes = new HashMap<>();
        attributes.put("type", toClaymoreType(config.apiType));
        attributes.put("apiIp", config.apiIp);
        attributes.put("apiPort", Integer.toString(config.apiPort));
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
     * Creates an ethminer {@link Miner} from the config.
     *
     * @param config The config.
     *
     * @return The {@link Miner}.
     */
    private static Miner toEthminerApi(
            final MinerConfig config) {
        final Map<String, String> attributes = new HashMap<>();
        attributes.put("apiIp", config.apiIp);
        attributes.put("apiPort", Integer.toString(config.apiPort));
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
     * Converts the provided config to a {@link Miner}.
     *
     * @param config       The config.
     * @param minerFactory The factory.
     *
     * @return The {@link Miner}.
     */
    private static Miner toMiner(
            final MinerConfig config,
            final mn.foreman.model.MinerFactory minerFactory) {
        return minerFactory.create(
                ImmutableMap.of(
                        "apiIp",
                        config.apiIp,
                        "apiPort",
                        Integer.toString(config.apiPort)));
    }

    /**
     * Converts each {@link MinerConfig} to a {@link Miner}.
     *
     * @param config The {@link MinerConfig}.
     *
     * @return The {@link Miner}.
     */
    private static Optional<Miner> toMiner(final MinerConfig config) {
        Miner miner = null;
        switch (config.apiType) {
            case ANTMINER_GHS_API:
                // Fall through
            case ANTMINER_HS_API:
                // Fall through
            case ANTMINER_KHS_API:
                // Fall through
            case ANTMINER_MHS_API:
                miner = toAntminer(config);
                break;
            case BAIKAL_API:
                miner = toMiner(config, new BaikalFactory());
                break;
            case BMINER_API:
                miner = toMiner(config, new BminerFactory());
                break;
            case CASTXMR_API:
                miner = toMiner(config, new CastxmrFactory());
                break;
            case CCMINER_API:
                miner = toMiner(config, new CcminerFactory());
                break;
            case CLAYMORE_ETH_API:
                // Fall through
            case CLAYMORE_ZEC_API:
                miner = toClaymore(config);
                break;
            case DSTM_API:
                miner = toMiner(config, new DstmFactory());
                break;
            case ETHMINER_API:
                miner = toEthminerApi(config);
                break;
            case EWBF_API:
                miner = toMiner(config, new EwbfFactory());
                break;
            case EXCAVATOR_API:
                miner = toMiner(config, new ExcavatorFactory());
                break;
            case JCEMINER_API:
                miner = toMiner(config, new JceminerFactory());
                break;
            case LOLMINER_API:
                miner = toMiner(config, new LolminerFactory());
                break;
            case SGMINER_API:
                miner = toMiner(config, new SgminerFactory());
                break;
            case SRBMINER_API:
                miner = toMiner(config, new SrbminerFactory());
                break;
            case TREX_API:
                miner = toMiner(config, new TrexFactory());
                break;
            case XMRIG_API:
                miner = toMiner(config, new XmrigFactory());
                break;
            case XMRSTAK_API:
                miner = toMiner(config, new XmrstakFactory());
                break;
        }
        return Optional.ofNullable(miner);
    }

    /**
     * Creates a {@link Miner} from every miner in the {@link MinerConfig
     * configs}.
     *
     * @param configs The configurations.
     *
     * @return The {@link Miner miners}.
     */
    private static List<Miner> toMiners(
            final List<MinerConfig> configs) {
        return configs
                .stream()
                .map(RemoteConfiguration::toMiner)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

    /**
     * Downloads the configurations.
     *
     * @return The configs.
     *
     * @throws IOException on failure to download configs.
     */
    private List<MinerConfig> getConfigs() throws IOException {
        final List<MinerConfig> configs = new LinkedList<>();

        final URL url = new URL(this.url);
        final HttpURLConnection connection =
                (HttpURLConnection) url.openConnection();

        connection.setRequestMethod("GET");
        connection.setRequestProperty(
                "Authorization",
                "Token " + this.apiKey);

        final int code = connection.getResponseCode();
        if (code == HttpURLConnection.HTTP_OK) {
            try (final InputStreamReader inputStreamReader =
                         new InputStreamReader(
                                 connection.getInputStream());
                 final BufferedReader reader =
                         new BufferedReader(
                                 inputStreamReader)) {
                configs.addAll(
                        Arrays.asList(
                                new ObjectMapper()
                                        .readValue(
                                                IOUtils.toString(reader),
                                                MinerConfig[].class)));
            }
        } else {
            LOG.warn("Failed to obtain configuration: {}", code);
        }

        return configs;
    }
}