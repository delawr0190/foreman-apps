package mn.foreman.srbminer;

import mn.foreman.io.Query;
import mn.foreman.model.AbstractMiner;
import mn.foreman.model.error.MinerException;
import mn.foreman.model.miners.FanInfo;
import mn.foreman.model.miners.MinerStats;
import mn.foreman.model.miners.Pool;
import mn.foreman.model.miners.rig.FreqInfo;
import mn.foreman.model.miners.rig.Gpu;
import mn.foreman.model.miners.rig.Rig;
import mn.foreman.srbminer.json.multi.Response;
import mn.foreman.util.PoolUtils;

import com.fasterxml.jackson.core.type.TypeReference;

import java.math.BigDecimal;
import java.util.List;

/**
 * <h1>Overview</h1>
 *
 * A {@link SrbminerNew} represents a remote srbminer instance.
 *
 * <p>This class relies on the srbminer-api being enabled and configured to
 * allow the server that this application is running on to access it.  If this
 * application is running on the rig server, only localhost connections need to
 * be allowed.</p>
 *
 * <p>This class currently queries:</p>
 *
 * <ul>
 * <li>http://{@link #apiIp}:{@link #apiPort}/</li>
 * </ul>
 */
public class SrbminerNew
        extends AbstractMiner {

    /**
     * Constructor.
     *
     * @param apiIp   The API IP.
     * @param apiPort The API port.
     */
    SrbminerNew(
            final String apiIp,
            final int apiPort) {
        super(
                apiIp,
                apiPort);
    }

    @Override
    public void addStats(final MinerStats.Builder statsBuilder)
            throws MinerException {
        final Response response =
                Query.restQuery(
                        this.apiIp,
                        this.apiPort,
                        "/",
                        new TypeReference<Response>() {
                        });
        addPools(
                statsBuilder,
                response.algorithms);
        if (response.gpuDevices != null && !response.gpuDevices.isEmpty()) {
            addGpuRig(
                    statsBuilder,
                    response.gpuDevices,
                    response.algorithms);
        } else {
            // Must be CPU mining
            addCpuRig(
                    statsBuilder,
                    response.cpuDevices,
                    response.algorithms);
        }
    }

    /**
     * Adds a rig to the provided builder.
     *
     * @param builder    The builder.
     * @param devices    The devices.
     * @param algorithms The algos.
     */
    private static void addCpuRig(
            final MinerStats.Builder builder,
            final List<Response.CpuDevice> devices,
            final List<Response.Algorithm> algorithms) {
        final Rig.Builder rigBuilder =
                new Rig.Builder()
                        .setHashRate(
                                algorithms
                                        .stream()
                                        .map(algorithm -> algorithm.hashrate.cpu.total)
                                        .reduce(BigDecimal.ZERO, BigDecimal::add));
        for (final Response.CpuDevice device : devices) {
            rigBuilder.addGpu(
                    new Gpu.Builder()
                            .setName(device.model)
                            .setIndex(device.id)
                            .setTemp(0)
                            .setFreqInfo(
                                    new FreqInfo.Builder()
                                            .setFreq(0)
                                            .setMemFreq(0)
                                            .build())
                            .setFans(
                                    new FanInfo.Builder()
                                            .setCount(0)
                                            .setSpeedUnits("RPM")
                                            .build())
                            .build());
        }
        builder.addRig(rigBuilder.build());
    }

    /**
     * Adds a rig to the provided builder.
     *
     * @param builder    The builder.
     * @param devices    The devices.
     * @param algorithms The algos.
     */
    private static void addGpuRig(
            final MinerStats.Builder builder,
            final List<Response.GpuDevice> devices,
            final List<Response.Algorithm> algorithms) {
        final Rig.Builder rigBuilder =
                new Rig.Builder()
                        .setHashRate(
                                algorithms
                                        .stream()
                                        .map(algorithm -> algorithm.hashrate.gpu.total)
                                        .reduce(BigDecimal.ZERO, BigDecimal::add));
        for (final Response.GpuDevice device : devices) {
            rigBuilder.addGpu(
                    new Gpu.Builder()
                            .setName(device.model)
                            .setIndex(device.deviceId)
                            .setBus(device.busId)
                            .setTemp(device.temperature)
                            .setFreqInfo(
                                    new FreqInfo.Builder()
                                            .setFreq(device.coreClock)
                                            .setMemFreq(device.memoryClock)
                                            .build())
                            .setFans(
                                    new FanInfo.Builder()
                                            .setCount(1)
                                            .addSpeed(device.fanSpeedRpm)
                                            .setSpeedUnits("RPM")
                                            .build())
                            .build());
        }
        builder.addRig(rigBuilder.build());
    }

    /**
     * Adds a pool to the provided builder.
     *
     * @param builder    The builder.
     * @param algorithms The algos.
     */
    private static void addPools(
            final MinerStats.Builder builder,
            final List<Response.Algorithm> algorithms) {
        algorithms.forEach(algorithm ->
                builder.addPool(
                        new Pool.Builder()
                                .setName(PoolUtils.sanitizeUrl(algorithm.pool.pool))
                                .setPriority(0)
                                .setStatus(
                                        true,
                                        true)
                                .setCounts(
                                        algorithm.shares.accepted,
                                        algorithm.shares.rejected,
                                        0)
                                .build()));
    }
}