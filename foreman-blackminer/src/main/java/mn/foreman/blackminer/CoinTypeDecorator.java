package mn.foreman.blackminer;

import mn.foreman.io.Query;
import mn.foreman.model.Miner;
import mn.foreman.model.MinerID;
import mn.foreman.model.error.MinerException;
import mn.foreman.model.miners.MinerStats;
import mn.foreman.model.miners.asic.Asic;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

/**
 * A {@link CoinTypeDecorator} provides a {@link Miner} decorator that adds the
 * 'coin-type' to the {@link Asic#powerState} so power draw can be measured on
 * the Foreman dashboard.
 */
public class CoinTypeDecorator
        implements Miner {

    /** The logger for this class. */
    private static final Logger LOG =
            LoggerFactory.getLogger(CoinTypeDecorator.class);

    /** The mapper for reading JSON. */
    private final ObjectMapper objectMapper;

    /** The web password. */
    private final String password;

    /** The real. */
    private final Miner real;

    /** The web username. */
    private final String username;

    /** The web port. */
    private final int webPort;

    /**
     * Constructor.
     *
     * @param real         The real.
     * @param username     The web username.
     * @param password     The web password.
     * @param webPort      The web port.
     * @param objectMapper The mapper for reading JSON.
     */
    public CoinTypeDecorator(
            final Miner real,
            final String username,
            final String password,
            final int webPort,
            final ObjectMapper objectMapper) {
        this.real = real;
        this.username = username;
        this.password = password;
        this.webPort = webPort;
        this.objectMapper = objectMapper;
    }

    @Override
    public int getApiPort() {
        return this.real.getApiPort();
    }

    @Override
    public String getIp() {
        return this.real.getIp();
    }

    @Override
    public Optional<String> getMacAddress() {
        return this.real.getMacAddress();
    }

    @Override
    public MinerID getMinerID() {
        return this.real.getMinerID();
    }

    @Override
    public MinerStats getStats() throws MinerException {
        MinerStats stats = this.real.getStats();
        if (hasWebCredentials()) {
            try {
                stats = addCoinType(stats);
            } catch (final Exception e) {
                LOG.warn("Exception occurred while adding coin-type", e);
            }
        }
        return stats;
    }

    /**
     * Converts the provided ASICs to new ASICs with a power state set.
     *
     * @param asics    The ASICs.
     * @param coinType The coin type.
     *
     * @return The new ASICs.
     */
    private static List<Asic> toAsics(
            final List<Asic> asics,
            final Object coinType) {
        final String coinTypeString = coinType.toString();
        return asics
                .stream()
                .map(asic ->
                        new Asic.Builder()
                                .fromAsic(asic)
                                .setPowerState(coinTypeString)
                                .build())
                .collect(Collectors.toList());
    }

    /**
     * Adds the coin-type to the stats.
     *
     * @param stats The original stats.
     *
     * @return The new stats.
     *
     * @throws Exception on failure to add stats.
     */
    private MinerStats addCoinType(final MinerStats stats)
            throws Exception {
        // Obtain the coin-type, if possible, from the miner config
        final AtomicReference<MinerStats> statsRef = new AtomicReference<>(stats);
        Query.digestGet(
                this.real.getIp(),
                this.webPort,
                "blackMiner Configuration",
                "/cgi-bin/get_miner_conf.cgi",
                this.username,
                this.password,
                (code, s) -> {
                    try {
                        final Map<String, Object> params =
                                this.objectMapper.readValue(
                                        s,
                                        new TypeReference<Map<String, Object>>() {
                                        });
                        if (params.containsKey("coin-type")) {
                            final List<Asic> asics = stats.getAsics();
                            statsRef.set(
                                    new MinerStats.Builder()
                                            .fromStats(stats)
                                            .setAsics(
                                                    toAsics(
                                                            asics,
                                                            params.get("coin-type")))
                                            .build());
                        }
                    } catch (final IOException e) {
                        LOG.warn("Exception occurred while querying", e);
                    }
                });

        return statsRef.get();
    }

    /**
     * Checks to see if web credentials are present.
     *
     * @return The web credentials.
     */
    private boolean hasWebCredentials() {
        return ((this.username != null && !this.username.isEmpty()) &&
                (this.password != null && !this.password.isEmpty()));
    }
}
