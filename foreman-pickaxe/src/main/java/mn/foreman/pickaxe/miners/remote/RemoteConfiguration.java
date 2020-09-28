package mn.foreman.pickaxe.miners.remote;

import mn.foreman.chisel.ChiselMinerDecorator;
import mn.foreman.claymore.TypeMapping;
import mn.foreman.model.Miner;
import mn.foreman.model.MinerFactory;
import mn.foreman.pickaxe.miners.MinerConfiguration;
import mn.foreman.pickaxe.miners.remote.json.MinerConfig;
import mn.foreman.util.EnvUtils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * A {@link RemoteConfiguration} provides a factory that will create the list of
 * {@link Miner miners} to query based on the results of a query to FOREMAN's
 * configuration API.  This allows users to configure their pickaxe instances
 * from the dashboard.
 */
public class RemoteConfiguration
        implements MinerConfiguration {

    /** The hostname. */
    private static final String HOSTNAME = EnvUtils.getHostname();

    /** The local IP. */
    private static final String IP = EnvUtils.getLanIp();

    /** The logger for this class. */
    private static final Logger LOG =
            LoggerFactory.getLogger(RemoteConfiguration.class);

    /** How long to wait on socket operations before disconnecting. */
    private static final int SOCKET_TIMEOUT =
            (int) TimeUnit.SECONDS.toMillis(60);

    /** The autominer mapping URL. */
    private final String amMappingUrl;

    /** The API key. */
    private final String apiKey;

    /** The claymore mapping URL. */
    private final String claymoreMappingUrl;

    /** The config URL. */
    private final String configUrl;

    /** The nicehash config URL. */
    private final String nicehashConfigUrl;

    /**
     * Constructor.
     *
     * @param configUrl          The config URL.
     * @param nicehashConfigUrl  The nicehash config URL.
     * @param amMappingUrl       The autominer mapping URL.
     * @param claymoreMappingUrl The claymore mapping URL.
     * @param apiKey             The API key.
     */
    public RemoteConfiguration(
            final String configUrl,
            final String nicehashConfigUrl,
            final String amMappingUrl,
            final String claymoreMappingUrl,
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
                amMappingUrl,
                "amMappingUrl cannot be null");
        Validate.notEmpty(
                amMappingUrl,
                "amMappingUrl cannot be empty");
        Validate.notNull(
                claymoreMappingUrl,
                "claymoreMappingUrl cannot be null");
        Validate.notEmpty(
                claymoreMappingUrl,
                "claymoreMappingUrl cannot be empty");
        Validate.notNull(
                apiKey,
                "apiKey cannot be null");
        Validate.notEmpty(
                apiKey,
                "apiKey cannot be empty");
        this.configUrl = configUrl;
        this.nicehashConfigUrl = nicehashConfigUrl;
        this.amMappingUrl = amMappingUrl;
        this.claymoreMappingUrl = claymoreMappingUrl;
        this.apiKey = apiKey;
    }

    @Override
    public List<Miner> load()
            throws Exception {
        LOG.debug("Querying {} for miners", this.configUrl);

        final ObjectMapper objectMapper = new ObjectMapper();

        final List<MinerConfig> configs = new LinkedList<>();
        getConfig(
                String.format(
                        "%s?hostname=%s&ip=%s",
                        this.configUrl,
                        URLEncoder.encode(
                                HOSTNAME,
                                StandardCharsets.UTF_8.name()),
                        URLEncoder.encode(
                                IP,
                                StandardCharsets.UTF_8.name())),
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

        final List<ApiType> niceHashConfig = new LinkedList<>();
        getConfig(
                this.nicehashConfigUrl,
                response -> {
                    try {
                        niceHashConfig.addAll(
                                objectMapper.readValue(
                                        response,
                                        new TypeReference<List<ApiType>>() {
                                        }));
                    } catch (final IOException ioe) {
                        LOG.warn("Failed to parse response", ioe);
                    }
                });

        final Map<String, ApiType> amMappings = new HashMap<>();
        getConfig(
                this.amMappingUrl,
                response -> {
                    try {
                        amMappings.putAll(
                                objectMapper.readValue(
                                        response,
                                        new TypeReference<Map<String, ApiType>>() {
                                        }));
                    } catch (final IOException ioe) {
                        LOG.warn("Failed to parse response", ioe);
                    }
                });

        final Map<String, BigDecimal> claymoreMultipliers = new HashMap<>();
        getConfig(
                this.claymoreMappingUrl,
                response -> {
                    try {
                        claymoreMultipliers.putAll(
                                objectMapper.readValue(
                                        response,
                                        new TypeReference<Map<String, BigDecimal>>() {
                                        }));
                    } catch (final IOException ioe) {
                        LOG.warn("Failed to parse response", ioe);
                    }
                });

        final TypeMapping.Builder typeMappingBuilder =
                new TypeMapping.Builder();
        claymoreMultipliers.forEach(typeMappingBuilder::addMapping);

        return toMiners(
                configs,
                niceHashConfig,
                amMappings,
                typeMappingBuilder.build());
    }

    /**
     * Adds nicehash candidates to the dest {@link List}.
     *
     * @param config           The config.
     * @param portStart        The port start.
     * @param candidates       The candidates.
     * @param amMappings       The autominer mappings.
     * @param claymoreMappings The claymore mappings.
     * @param dest             The destination {@link List}.
     */
    private static void addNiceHashCandidates(
            final MinerConfig config,
            final int portStart,
            final List<ApiType> candidates,
            final Map<String, ApiType> amMappings,
            final TypeMapping claymoreMappings,
            final List<Miner> dest) {
        for (int i = 0; i < 5; i++) {
            final int port = portStart + i;
            dest.addAll(
                    candidates
                            .stream()
                            .map(apiType ->
                                    toMiner(
                                            apiType,
                                            port,
                                            config,
                                            candidates,
                                            amMappings,
                                            claymoreMappings))
                            .flatMap(List::stream)
                            .collect(Collectors.toList()));
        }
    }

    /**
     * Adds all of the params.
     *
     * @param dest   The destination.
     * @param params The params to add.
     */
    private static void addParams(
            final Map<String, Object> dest,
            final List<MinerConfig.Param> params) {
        params.forEach(param -> dest.put(param.key, param.value));
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
        final Map<String, Object> params = new HashMap<>();
        params.put(
                "apiIp",
                config.apiIp);
        params.put(
                "apiPort",
                Integer.toString(port));
        addParams(
                params,
                config.params);
        return minerFactory.create(params);
    }

    /**
     * Converts each {@link MinerConfig} to a {@link Miner}.
     *
     * @param apiType             The {@link ApiType}.
     * @param port                The port.
     * @param config              The {@link MinerConfig}.
     * @param niceHashCandidates  The NiceHash configuration.
     * @param amMappings          The autominer mappings.
     * @param claymoreMultipliers The claymore multipliers.
     *
     * @return The {@link Miner miners}.
     */
    private static List<Miner> toMiner(
            final ApiType apiType,
            final int port,
            final MinerConfig config,
            final List<ApiType> niceHashCandidates,
            final Map<String, ApiType> amMappings,
            final TypeMapping claymoreMultipliers) {
        LOG.debug("Adding miner for {}", config);

        final MinerFactory minerFactory =
                apiType.toFactory(
                        port,
                        config,
                        niceHashCandidates,
                        amMappings,
                        claymoreMultipliers,
                        (config1, nicehash, autominer, claymore) -> {
                            final List<Miner> miners = new LinkedList<>();
                            addNiceHashCandidates(
                                    config1,
                                    config1.apiPort,
                                    nicehash,
                                    autominer,
                                    claymore,
                                    miners);
                            return miners;
                        });

        final List<Miner> miners = new LinkedList<>();
        miners.add(toMiner(port, config, minerFactory));

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
     * @param configs             The configurations.
     * @param niceHashCandidates  The NiceHash configurations.
     * @param amMappings          The autominer mappings.
     * @param claymoreMultipliers The claymore multipliers.
     *
     * @return The {@link Miner miners}.
     */
    private static List<Miner> toMiners(
            final List<MinerConfig> configs,
            final List<ApiType> niceHashCandidates,
            final Map<String, ApiType> amMappings,
            final TypeMapping claymoreMultipliers) {
        return configs
                .stream()
                .filter(config -> config.apiType != null)
                .map(config ->
                        toMiner(
                                config.apiType,
                                config.apiPort,
                                config,
                                niceHashCandidates,
                                amMappings,
                                claymoreMultipliers))
                .flatMap(List::stream)
                .collect(Collectors.toList());
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
                final String response =
                        IOUtils.toString(reader);
                LOG.debug("Received response: {}", response);
                responseStrategy.accept(response);
            }
        } else {
            LOG.warn("Failed to obtain a configuration from {}: {}",
                    configUrl,
                    code);
        }
    }
}