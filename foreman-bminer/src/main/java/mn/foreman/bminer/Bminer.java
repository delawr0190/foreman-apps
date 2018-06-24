package mn.foreman.bminer;

import mn.foreman.bminer.json.devices.Device;
import mn.foreman.bminer.json.devices.Devices;
import mn.foreman.bminer.json.solver.Solvers;
import mn.foreman.bminer.json.stratum.Failover;
import mn.foreman.bminer.json.stratum.Stratum;
import mn.foreman.bminer.json.stratum.Stratums;
import mn.foreman.io.ApiRequest;
import mn.foreman.io.ApiRequestImpl;
import mn.foreman.io.Connection;
import mn.foreman.io.ConnectionFactory;
import mn.foreman.model.Miner;
import mn.foreman.model.miners.FanInfo;
import mn.foreman.model.miners.MinerStats;
import mn.foreman.model.miners.Pool;
import mn.foreman.model.miners.rig.FreqInfo;
import mn.foreman.model.miners.rig.Gpu;
import mn.foreman.model.miners.rig.Rig;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * <h1>Overview</h1>
 *
 * A {@link Bminer} represents a remote bminer instance.
 *
 * <p>This class relies on the bminer-api being enabled and configured to allow
 * the server that this application is running on to access it.  If this
 * application is running on the rig server, only localhost connections need to
 * be allowed.</p>
 *
 * <p>This class currently queries:</p>
 *
 * <ul>
 *     <li>http://{@link #apiIp}:{@link #apiPort}/api/v1/status/solver</li>
 *     <li>http://{@link #apiIp}:{@link #apiPort}/api/v1/status/device</li>
 *     <li>http://{@link #apiIp}:{@link #apiPort}/api/v1/status/stratum</li>
 * </ul>
 *
 * <h1>Limitations</h1>
 *
 * <h2>GPUs</h2>
 *
 * <p>GPU bus ID isn't exposed via the API.  Therefore, it can't be
 * reported.</p>
 *
 * <p>The GPU name, as well, isn't exposed via the API.  Therefore, an
 * artificial name is generated that matches the format: "GPU &lt;id&gt;"</p>
 *
 * <h2>Pools</h2>
 *
 * <h3>Failover pools</h3>
 *
 * <p>In the stratum URI, bminer only exposes aggregated accepted and rejected
 * shares for all pools.  Due to the inability to tie those shares to a pool,
 * only the active pool is reported via pickaxe with those aggregated shares.
 * When a pool goes down and another pool becomes active, that pool will then
 * show with the aggregated shares.</p>
 *
 * <h3>Stale shares</h3>
 *
 * <p>Stale shares aren't reported specifically.  Therefore, they can't be
 * reported.  They are, however, most likely included in the rejected
 * shares.</p>
 */
public class Bminer
        implements Miner {

    /** The logger for this class. */
    private static final Logger LOG =
            LoggerFactory.getLogger(Bminer.class);

    /** The API IP. */
    private final String apiIp;

    /** The API port. */
    private final int apiPort;

    /** The miner name. */
    private final String name;

    /**
     * Constructor.
     *
     * @param name    The name.
     * @param apiIp   The API IP.
     * @param apiPort The API port.
     */
    Bminer(
            final String name,
            final String apiIp,
            final int apiPort) {
        Validate.notEmpty(
                name,
                "name cannot be empty");
        Validate.notEmpty(
                apiIp,
                "apiIp cannot be empty");
        Validate.isTrue(
                apiPort > 0,
                "apiPort must be > 0");
        this.name = name;
        this.apiIp = apiIp;
        this.apiPort = apiPort;
    }

    @Override
    public MinerStats getStats() {
        LOG.debug("Obtaining stats from {}-{}:{}",
                this.name,
                this.apiIp,
                this.apiPort);

        final MinerStats.Builder statsBuilder =
                new MinerStats.Builder()
                        .setApiIp(this.apiIp)
                        .setApiPort(this.apiPort)
                        .setName(this.name);

        final Rig.Builder rigBuilder =
                new Rig.Builder()
                        .setName("bminer");
        getDevices(rigBuilder);
        getSolvers(rigBuilder);
        getStratums(statsBuilder);

        return statsBuilder
                .addRig(rigBuilder.build())
                .build();
    }

    @Override
    public String toString() {
        return String.format(
                "%s [ name=%s, apiIp=%s, apiPort=%d ]",
                getClass().getSimpleName(),
                this.name,
                this.apiIp,
                this.apiPort);
    }

    /**
     * Converts the provided device ID and {@link Device} to a {@link Gpu}.
     *
     * @param id     The Cuda ID.
     * @param device The device.
     *
     * @return The new {@link Gpu}.
     */
    private static Gpu toGpu(
            final String id,
            final Device device) {
        return new Gpu.Builder()
                .setName("GPU " + id)
                .setIndex(id)
                // Bus not exposed in bminer
                .setBus("0")
                .setTemp(Integer.toString(device.temperature))
                .setFans(
                        new FanInfo.Builder()
                                .setCount(1)
                                .addSpeed(device.fanSpeed)
                                .setSpeedUnits("%")
                                .build())
                .setFreqInfo(
                        new FreqInfo.Builder()
                                .setFreq(Integer.toString(device.clocks.core))
                                .setMemFreq(Integer.toString(device.clocks.memory))
                                .build())
                .build();
    }

    /**
     * Converts the pool name to the pool URL.
     *
     * @param name The name.
     *
     * @return The URL.
     */
    private static String toPool(final String name) {
        return name.split("@")[1];
    }

    /**
     * Converts the provided {@link Stratum} to a {@link Pool}.
     *
     * @param stratum The {@link Stratum}.
     *
     * @return The new {@link Pool}.
     */
    private static Pool toPool(
            final Stratum stratum) {
        final Pool.Builder poolBuilder =
                new Pool.Builder()
                        .setCounts(
                                stratum.acceptedShares,
                                stratum.rejectedShares,
                                0)
                        .setPriority(0);

        final Optional<Failover> failoverOptional =
                stratum.failovers
                        .stream()
                        .filter((failover1 -> failover1.active))
                        .findFirst();
        final Failover failover =
                failoverOptional.orElseGet(
                        () -> stratum.failovers.get(0));

        return poolBuilder
                .setStatus(
                        true,
                        failover.active)
                .setName(toPool(failover.name))
                .build();
    }

    /**
     * Utility method to create an API URI.
     *
     * @param handler The handler.
     *
     * @return The URI.
     */
    private static String toUri(
            final String handler) {
        return String.format(
                "/api/v1/status/%s",
                handler);
    }

    /**
     * Queries the devices and updates the {@link Rig.Builder}.
     *
     * @param rigBuilder The builder to update.
     */
    private void getDevices(final Rig.Builder rigBuilder) {
        query(toUri("device"), Devices.class)
                .ifPresent((devices) -> {
                    final Map<String, Device> deviceMap = devices.devices;
                    if (deviceMap != null) {
                        for (final Map.Entry<String, Device> entry :
                                deviceMap.entrySet()) {
                            rigBuilder.addGpu(
                                    toGpu(
                                            entry.getKey(),
                                            entry.getValue()));
                        }
                    }
                });
    }

    /**
     * Queries the solvers and updates the {@link Rig.Builder}.
     *
     * @param rigBuilder The builder to update.
     */
    private void getSolvers(final Rig.Builder rigBuilder) {
        final List<Double> hashRates = new LinkedList<>();
        query(toUri("solver"), mn.foreman.bminer.json.solver.Devices.class)
                .ifPresent(devices -> {
                    final Map<String, Solvers> solversMap = devices.devices;
                    if (solversMap != null) {
                        solversMap
                                .values()
                                .stream()
                                .map(solvers -> solvers.solvers)
                                .flatMap(List::stream)
                                .forEach(solver ->
                                        hashRates.add(
                                                solver.speedInfo.hashRate));
                    }
                });

        rigBuilder.setHashRate(
                new BigDecimal(
                        hashRates
                                .stream()
                                .mapToDouble(Double::doubleValue)
                                .sum()));
    }

    /**
     * Queries the stratums and updates the {@link MinerStats.Builder}.
     *
     * @param statsBuilder The builder to update.
     */
    private void getStratums(final MinerStats.Builder statsBuilder) {
        query(toUri("stratum"), Stratums.class)
                .ifPresent((stratums) -> {
                    final Map<String, Stratum> stratumMap = stratums.stratums;
                    if (stratumMap != null) {
                        for (final Stratum stratum : stratumMap.values()) {
                            statsBuilder.addPool(toPool(stratum));
                        }
                    }
                });
    }

    /**
     * Queries the REST interface.
     *
     * @param uri   The URI.
     * @param clazz The class to be deserialized.
     * @param <T>   The type.
     *
     * @return The response.
     */
    private <T> Optional<T> query(
            final String uri,
            final Class<T> clazz) {
        T response = null;

        final ApiRequest request =
                new ApiRequestImpl(
                        this.apiIp,
                        this.apiPort,
                        uri);

        final Connection connection =
                ConnectionFactory.createRestConnection(
                        request);
        connection.query();

        if (request.waitForCompletion(
                10,
                TimeUnit.SECONDS)) {
            // Got a response
            final ObjectMapper objectMapper =
                    new ObjectMapper();
            try {
                response =
                        objectMapper.readValue(
                                request.getResponse(),
                                clazz);
            } catch (final IOException ioe) {
                LOG.warn("Exception occurred while querying", ioe);
            }
        }

        return Optional.ofNullable(response);
    }
}