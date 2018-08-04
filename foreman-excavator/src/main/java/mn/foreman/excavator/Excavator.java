package mn.foreman.excavator;

import mn.foreman.excavator.json.Algorithms;
import mn.foreman.excavator.json.Devices;
import mn.foreman.excavator.json.Request;
import mn.foreman.excavator.json.Subscribe;
import mn.foreman.excavator.method.AlgorithmMethod;
import mn.foreman.excavator.method.DevicesMethod;
import mn.foreman.excavator.method.Method;
import mn.foreman.excavator.method.SubscribeMethod;
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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.math.BigDecimal;
import java.util.Arrays;

/**
 * <h1>Overview</h1>
 *
 * <p>An {@link Excavator} represents a remote excavator instance.</p>
 *
 * <p>This class relies on the excavator-api being enabled and configured to
 * allow the server that this application is running on to access it.  If this
 * application is running on the rig server, only localhost connections need to
 * be allowed.</p>
 *
 * <p>This class currently queries the following methods via JSON RPC over TCP
 * (the HTTP interface is not used):</p>
 *
 * <ul>
 *     <li>subscribe.info</li>
 *     <li>devices.get</li>
 *     <li>algorithm.list</li>
 * </ul>
 */
public class Excavator
        extends AbstractMiner {

    /**
     * Constructor.
     *
     * @param name    The name.
     * @param apiIp   The API IP.
     * @param apiPort The API port.
     */
    Excavator(
            final String name,
            final String apiIp,
            final int apiPort) {
        super(
                name,
                apiIp,
                apiPort);
    }

    @Override
    public void addStats(final MinerStats.Builder statsBuilder)
            throws MinerException {
        try {
            addRig(
                    statsBuilder,
                    addPools(statsBuilder));
        } catch (final JsonProcessingException e) {
            throw new MinerException(e);
        }
    }

    /**
     * Creates a JSON command from the provided parameters.
     *
     * @param method The method.
     * @param params The parameters.
     *
     * @return The JSON request.
     *
     * @throws JsonProcessingException on failure to serialize.
     */
    private static String makeCommand(
            final Method method,
            final String... params)
            throws JsonProcessingException {
        final ObjectMapper objectMapper =
                new ObjectMapper();
        return objectMapper.writeValueAsString(
                new Request(
                        1,
                        method.toMethod(),
                        Arrays.asList(params))) + "\n";
    }

    /**
     * Adds the provided {@link Devices.Device} as a {@link Gpu} to the {@link
     * Rig.Builder builder}.
     *
     * @param builder The builder to update.
     * @param device  The {@link Devices.Device}.
     */
    private void addGpu(
            final Rig.Builder builder,
            final Devices.Device device) {
        builder
                .addGpu(
                        new Gpu.Builder()
                                .setName(device.name)
                                .setIndex(device.deviceId)
                                .setBus(device.details.busId)
                                .setTemp(device.gpuTemp)
                                .setFans(
                                        new FanInfo.Builder()
                                                .setCount(1)
                                                .addSpeed(device.gpuFanSpeed)
                                                .setSpeedUnits("%")
                                                .build())
                                .setFreqInfo(
                                        new FreqInfo.Builder()
                                                .setFreq(device.gpuClockCoreMax)
                                                .setMemFreq(device.gpuClockMemory)
                                                .build())
                                .build());
    }

    /**
     * Adds a {@link Pool} to the provided {@link MinerStats.Builder} using the
     * provided {@link Subscribe} and {@link Algorithms}.
     *
     * @param builder    The builder to update.
     * @param subscribe  The {@link Subscribe}.
     * @param algorithms The {@link Algorithms}.
     *
     * @return The hash rate.
     */
    private BigDecimal addPools(
            final MinerStats.Builder builder,
            final Subscribe subscribe,
            final Algorithms algorithms) {
        int totalAccepted = 0;
        int totalRejected = 0;
        BigDecimal hashRate = BigDecimal.ZERO;
        for (final Algorithms.Algorithm algorithm : algorithms.algorithms) {
            totalAccepted += algorithm.acceptedShares;
            totalRejected += algorithm.rejectedShares;
            hashRate = hashRate.add(algorithm.speed);
        }
        builder.addPool(
                new Pool.Builder()
                        .setName(PoolUtils.sanitizeUrl(subscribe.address))
                        .setPriority(0)
                        .setStatus(
                                true,
                                subscribe.connected)
                        .setCounts(
                                totalAccepted,
                                totalRejected,
                                0)
                        .build());
        return hashRate;
    }

    /**
     * Queries for and adds {@link Pool pools} to the provided {@link
     * MinerStats.Builder}.
     *
     * @param builder The builder to update.
     *
     * @return The current hash rate.
     *
     * @throws MinerException          on failure to query.
     * @throws JsonProcessingException on failure to serialize.
     */
    private BigDecimal addPools(final MinerStats.Builder builder)
            throws MinerException, JsonProcessingException {
        final Subscribe subscribe =
                Query.jsonQuery(
                        this.apiIp,
                        this.apiPort,
                        makeCommand(SubscribeMethod.INFO),
                        Subscribe.class);
        final Algorithms algorithms =
                Query.jsonQuery(
                        this.apiIp,
                        this.apiPort,
                        makeCommand(AlgorithmMethod.LIST),
                        Algorithms.class);
        return addPools(
                builder,
                subscribe,
                algorithms);
    }

    /**
     * Queries for and adds a {@link Rig} to the provided {@link
     * MinerStats.Builder}.
     *
     * @param builder  The builder to update.
     * @param hashRate The hash rate.
     *
     * @throws MinerException          on failure to query.
     * @throws JsonProcessingException on failure to serialize.
     */
    private void addRig(
            final MinerStats.Builder builder,
            final BigDecimal hashRate)
            throws MinerException, JsonProcessingException {
        final Devices devices =
                Query.jsonQuery(
                        this.apiIp,
                        this.apiPort,
                        makeCommand(DevicesMethod.GET),
                        Devices.class);
        final Rig.Builder rigBuilder =
                new Rig.Builder()
                        .setName("excavator")
                        .setHashRate(hashRate);
        devices.devices.forEach(
                (device) ->
                        addGpu(
                                rigBuilder,
                                device));
        builder.addRig(rigBuilder.build());
    }
}