package mn.foreman.xmrig.current;

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
import mn.foreman.xmrig.current.json.Backend;
import mn.foreman.xmrig.current.json.Summary;

import com.fasterxml.jackson.core.type.TypeReference;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * <h1>Overview</h1>
 *
 * An {@link XmrigNew} represents a remote xmrig instance.
 *
 * <p>This class relies on the xmrig-api being enabled and configured to allow
 * the server that this application is running on to access it.  If this
 * application is running on the rig server, only localhost connections need to
 * be allowed.</p>
 *
 * <p>This class currently queries:</p>
 *
 * <ul>
 * <li>http://{@link #apiIp}:{@link #apiPort}/1/summary</li>
 * <li>http://{@link #apiIp}:{@link #apiPort}/2/backends</li>
 * </ul>
 *
 * <h1>Limitations</h1>
 *
 * <h2>Pools</h2>
 *
 * <h3>Stale shares</h3>
 *
 * <p>Stale shares aren't reported specifically.  Therefore, they can't be
 * reported.  They are, however, most likely included in the rejected
 * shares.</p>
 */
public class XmrigNew
        extends AbstractMiner {

    /** The access token. */
    private final String accessToken;

    /**
     * Constructor.
     *
     * @param apiIp       The API IP.
     * @param apiPort     The API port.
     * @param accessToken The access token.
     */
    public XmrigNew(
            final String apiIp,
            final int apiPort,
            final String accessToken) {
        super(
                apiIp,
                apiPort);
        this.accessToken = accessToken;
    }

    @Override
    public void addStats(final MinerStats.Builder statsBuilder)
            throws MinerException {
        addPool(statsBuilder);
        addRig(statsBuilder);
    }

    private static Backend getBackend(
            final List<Backend> backends,
            final String slug)
            throws MinerException {
        return backends
                .stream()
                .filter(backend -> slug.equals(backend.type))
                .findFirst()
                .orElseThrow(
                        () -> new MinerException("Missing " + slug + " backend"));
    }

    /**
     * Adds a {@link Pool}.
     *
     * @param statsBuilder The {@link MinerStats.Builder builder} to update.
     *
     * @throws MinerException on failure to query the miner.
     */
    private void addPool(final MinerStats.Builder statsBuilder)
            throws MinerException {
        final Summary summary =
                query(
                        "/1/summary",
                        new TypeReference<Summary>() {
                        });

        final Summary.Connection connection = summary.connection;
        final Summary.Results results = summary.results;
        statsBuilder
                .addPool(
                        new Pool.Builder()
                                .setName(
                                        PoolUtils.sanitizeUrl(
                                                connection.pool))
                                .setStatus(true, connection.uptime > 0)
                                .setPriority(0)
                                .setCounts(
                                        results.sharesGood,
                                        results.sharesTotal - results.sharesGood,
                                        0)
                                .build());
    }

    /**
     * Adds a {@link Rig}.
     *
     * @param statsBuilder The {@link MinerStats.Builder builder} to update.
     *
     * @throws MinerException on failure to query the miner.
     */
    private void addRig(final MinerStats.Builder statsBuilder)
            throws MinerException {
        final List<Backend> backends =
                query(
                        "/2/backends",
                        new TypeReference<List<Backend>>() {
                        });

        final Backend cpu =
                getBackend(
                        backends,
                        "cpu");
        final Backend openCl =
                getBackend(
                        backends,
                        "opencl");
        final Backend cuda =
                getBackend(
                        backends,
                        "cuda");

        statsBuilder.addRig(
                new Rig.Builder()
                        .setHashRate(
                                toHashRate(
                                        cpu,
                                        openCl,
                                        cuda))
                        .addGpus(
                                toGpus(
                                        openCl))
                        .addGpus(
                                toGpus(
                                        cuda))
                        .build());
    }

    /**
     * Queries the miner API.
     *
     * @param uri The URI.
     * @param <T> The response type.
     *
     * @return The response.
     *
     * @throws MinerException on failure to communicate.
     */
    private <T> T query(
            final String uri,
            final TypeReference<T> typeReference) throws MinerException {
        if (this.accessToken != null && !this.accessToken.isEmpty()) {
            return Query.restQueryBearer(
                    this.apiIp,
                    this.apiPort,
                    uri,
                    this.accessToken,
                    typeReference,
                    2,
                    TimeUnit.SECONDS);
        }
        return Query.restQuery(
                this.apiIp,
                this.apiPort,
                uri,
                typeReference);
    }

    /**
     * Converts the provided {@link Thread} to a {@link FanInfo}.
     *
     * @param thread The thread.
     *
     * @return The new info.
     */
    private FanInfo toFanInfo(final Backend.Thread thread) {
        final FanInfo.Builder builder =
                new FanInfo.Builder()
                        .setCount(0)
                        .setSpeedUnits("%");
        if (thread.health != null) {
            builder.setCount(thread.health.fanSpeeds.size());
            thread.health.fanSpeeds.forEach(builder::addSpeed);
        }
        return builder.build();
    }

    /**
     * Converts the provided {@link Thread} to a {@link FreqInfo}.
     *
     * @param thread The thread.
     *
     * @return The new info.
     */
    private FreqInfo toFreqInfo(final Backend.Thread thread) {
        final FreqInfo.Builder builder =
                new FreqInfo.Builder()
                        .setFreq(0)
                        .setMemFreq(0);
        if (thread.health != null) {
            builder.setFreq(thread.health.clock);
            builder.setMemFreq(thread.health.memory);
        }
        return builder.build();
    }

    /**
     * Converts the provided {@link Thread} to a {@link Gpu}.
     *
     * @param thread The thread.
     *
     * @return The new GPU.
     */
    private Gpu toGpu(final Backend.Thread thread) {
        return new Gpu.Builder()
                .setName(thread.name)
                .setIndex(thread.index)
                .setBus(thread.busId)
                .setTemp(thread.health != null ? thread.health.temperature : 0)
                .setFans(toFanInfo(thread))
                .setFreqInfo(toFreqInfo(thread))
                .build();
    }

    /**
     * Converts the {@link Backend} to GPUs.
     *
     * @param backend The {@link Backend}.
     *
     * @return The new {@link Gpu GPUs}.
     */
    private List<Gpu> toGpus(final Backend backend) {
        final List<Gpu> gpus = new LinkedList<>();
        if (backend.enabled) {
            backend.threads
                    .stream()
                    .map(this::toGpu)
                    .forEach(gpus::add);
        }
        return gpus;
    }

    /**
     * Converts the provided {@link Backend Backends} to a single hash rate.
     *
     * @param cpu    The CPU {@link Backend}.
     * @param openCl The OpenCL {@link Backend}.
     * @param cuda   The Cuda {@link Backend}.
     *
     * @return The hash rate.
     */
    private BigDecimal toHashRate(
            final Backend cpu,
            final Backend openCl,
            final Backend cuda) {
        BigDecimal openClRate = BigDecimal.ZERO;
        if (openCl.enabled) {
            openClRate =
                    openCl.threads
                            .stream()
                            .map(thread -> thread.hashrates.get(0))
                            .reduce(BigDecimal.ZERO, BigDecimal::add);
        }

        BigDecimal cudaRate = BigDecimal.ZERO;
        if (cuda.enabled) {
            cudaRate =
                    cuda.threads
                            .stream()
                            .map(thread -> thread.hashrates.get(0))
                            .reduce(BigDecimal.ZERO, BigDecimal::add);
        }

        // Return the cpu hash rate as a rig rate if no other backends are
        // being used
        BigDecimal cpuRate = BigDecimal.ZERO;
        if (!openCl.enabled && !cuda.enabled && cpu.enabled) {
            cpuRate =
                    cpu.threads
                            .stream()
                            .map(thread -> thread.hashrates.get(0))
                            .reduce(BigDecimal.ZERO, BigDecimal::add);
        }

        return openClRate.add(cudaRate).add(cpuRate);
    }
}