package mn.foreman.bminer;

import mn.foreman.bminer.json.solver.Solvers;
import mn.foreman.bminer.json.status.Status;
import mn.foreman.bminer.json.stratum.Failover;
import mn.foreman.bminer.json.stratum.Stratum;
import mn.foreman.bminer.json.stratum.Stratums;
import mn.foreman.io.Query;
import mn.foreman.model.AbstractMiner;
import mn.foreman.model.error.MinerException;
import mn.foreman.model.miners.FanInfo;
import mn.foreman.model.miners.MinerStats;
import mn.foreman.model.miners.Pool;
import mn.foreman.model.miners.rig.FreqInfo;
import mn.foreman.model.miners.rig.Gpu;
import mn.foreman.model.miners.rig.Rig;
import mn.foreman.util.PoolUtils;

import com.fasterxml.jackson.core.type.TypeReference;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
 * <li>http://{@link #apiIp}:{@link #apiPort}/api/status</li>
 * <li>http://{@link #apiIp}:{@link #apiPort}/api/v1/status/solver</li>
 * <li>http://{@link #apiIp}:{@link #apiPort}/api/v1/status/stratum</li>
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
        extends AbstractMiner {

    /**
     * Constructor.
     *
     * @param apiIp   The API IP.
     * @param apiPort The API port.
     */
    Bminer(
            final String apiIp,
            final int apiPort) {
        super(
                apiIp,
                apiPort);
    }

    @Override
    protected void addStats(final MinerStats.Builder statsBuilder)
            throws MinerException {
        final Rig.Builder rigBuilder =
                new Rig.Builder();

        getStatus(rigBuilder);
        getSolvers(rigBuilder);
        getStratums(statsBuilder);

        statsBuilder.addRig(rigBuilder.build());
    }

    /**
     * Converts the provided device ID and {@link Status.Device} to a {@link
     * Gpu}.
     *
     * @param id     The Cuda ID.
     * @param device The device.
     *
     * @return The new {@link Gpu}.
     */
    private static Gpu toGpu(
            final String id,
            final Status.Device device) {
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
                .setName(PoolUtils.sanitizeUrl(failover.name))
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
     * Queries the solvers and updates the {@link Rig.Builder}.
     *
     * @param rigBuilder The builder to update.
     *
     * @throws MinerException on failure to query.
     */
    private void getSolvers(final Rig.Builder rigBuilder)
            throws MinerException {
        final List<Double> hashRates = new LinkedList<>();
        final mn.foreman.bminer.json.solver.Devices devices =
                Query.restQuery(
                        this.apiIp,
                        this.apiPort,
                        toUri("solver"),
                        new TypeReference<
                                mn.foreman.bminer.json.solver.Devices>() {
                        });
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

        rigBuilder.setHashRate(
                new BigDecimal(
                        hashRates
                                .stream()
                                .mapToDouble(Double::doubleValue)
                                .sum()));
    }

    /**
     * Queries the devices and updates the {@link Rig.Builder}.
     *
     * @param rigBuilder The builder to update.
     *
     * @throws MinerException on failure to query.
     */
    private void getStatus(final Rig.Builder rigBuilder)
            throws MinerException {
        final Status status =
                Query.restQuery(
                        this.apiIp,
                        this.apiPort,
                        "/api/status",
                        new TypeReference<Status>() {
                        });
        final Map<String, Status.Miner> deviceMap = status.miners;
        if (deviceMap != null) {
            for (final Map.Entry<String, Status.Miner> entry :
                    deviceMap.entrySet()) {
                rigBuilder.addGpu(
                        toGpu(
                                entry.getKey(),
                                entry.getValue().device));
            }
        }
    }

    /**
     * Queries the stratums and updates the {@link MinerStats.Builder}.
     *
     * @param statsBuilder The builder to update.
     *
     * @throws MinerException on failure to query.
     */
    private void getStratums(final MinerStats.Builder statsBuilder)
            throws MinerException {
        final Stratums stratums =
                Query.restQuery(
                        this.apiIp,
                        this.apiPort,
                        toUri("stratum"),
                        new TypeReference<Stratums>() {
                        });
        final Map<String, Stratum> stratumMap = stratums.stratums;
        if (stratumMap != null) {
            for (final Stratum stratum : stratumMap.values()) {
                statsBuilder.addPool(toPool(stratum));
            }
        }
    }
}