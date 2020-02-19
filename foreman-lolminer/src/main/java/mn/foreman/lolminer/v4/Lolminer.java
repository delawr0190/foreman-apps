package mn.foreman.lolminer.v4;

import mn.foreman.io.Query;
import mn.foreman.lolminer.v4.json.Response;
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

import java.util.concurrent.atomic.AtomicInteger;

/**
 * <h1>Overview</h1>
 *
 * A {@link Lolminer} represents a remote lolminer instance.
 *
 * <p>This class relies on the lolminer-api being enabled and configured to
 * allow the server that this application is running on to access it.  If this
 * application is running on the rig server, only localhost connections need to
 * be allowed.</p>
 *
 * <p>This class currently queries lolminer by opening a connection to the API
 * interface.</p>
 *
 * <h1>Limitations</h1>
 *
 * <p>lolminer only exposes GPU names, total hash rate, and the pool URL.
 * Nothing else can be extracted from this miner, so nothing else can be sent to
 * Foreman.</p>
 *
 * <p>Why must metrics and monitoring always be everybody's last thought?
 * :(</p>
 */
public class Lolminer
        extends AbstractMiner {

    /**
     * Use a 0-up counter to force it to look like shares are being accepted.
     * This will keep alerts from triggering due to the fact that lolminer
     * doesn't report shares.
     */
    private final AtomicInteger acceptCount = new AtomicInteger(0);

    /**
     * Constructor.
     *
     * @param apiIp   The API IP.
     * @param apiPort The API port.
     */
    public Lolminer(
            final String apiIp,
            final int apiPort) {
        super(
                apiIp,
                apiPort);
    }

    @Override
    protected void addStats(
            final MinerStats.Builder statsBuilder)
            throws MinerException {
        final Response response =
                Query.jsonQuery(
                        this.apiIp,
                        this.apiPort,
                        "",
                        new TypeReference<Response>() {
                        });

        if (!response.pool.trim().isEmpty()) {
            statsBuilder.addPool(
                    new Pool.Builder()
                            .setName(
                                    PoolUtils.sanitizeUrl(
                                            response.pool))
                            // lolminer doesn't expose anything other than
                            // the pool URL
                            .setStatus(true, true)
                            .setPriority(0)
                            .setCounts(
                                    this.acceptCount.getAndIncrement(),
                                    0,
                                    0)
                            .build());
        }

        final Rig.Builder rigBuilder =
                new Rig.Builder()
                        .setHashRate(response.totalSpeed)
                        .addAttribute(
                                "gpu_algo",
                                response.algorithm)
                        .addAttribute(
                                "coin",
                                response.coin);

        int index = 0;
        addGpu(
                index++,
                response.gpu0,
                rigBuilder);
        addGpu(
                index++,
                response.gpu1,
                rigBuilder);
        addGpu(
                index++,
                response.gpu2,
                rigBuilder);
        addGpu(
                index++,
                response.gpu3,
                rigBuilder);
        addGpu(
                index++,
                response.gpu4,
                rigBuilder);
        addGpu(
                index++,
                response.gpu5,
                rigBuilder);
        addGpu(
                index++,
                response.gpu6,
                rigBuilder);
        addGpu(
                index++,
                response.gpu7,
                rigBuilder);
        addGpu(
                index++,
                response.gpu8,
                rigBuilder);
        addGpu(
                index++,
                response.gpu9,
                rigBuilder);
        addGpu(
                index++,
                response.gpu10,
                rigBuilder);
        addGpu(
                index++,
                response.gpu11,
                rigBuilder);
        addGpu(
                index++,
                response.gpu12,
                rigBuilder);
        addGpu(
                index++,
                response.gpu13,
                rigBuilder);
        addGpu(
                index,
                response.gpu14,
                rigBuilder);
        statsBuilder.addRig(rigBuilder.build());
    }

    /**
     * Adds the provided {@link Gpu}, assuming it's not null, to the {@link
     * Rig.Builder rig builder}.
     *
     * @param index      The index.
     * @param gpu        The {@link Gpu}.
     * @param rigBuilder The builder.
     */
    private static void addGpu(
            final int index,
            final Response.Gpu gpu,
            final Rig.Builder rigBuilder) {
        if (gpu != null) {
            rigBuilder.addGpu(
                    new Gpu.Builder()
                            .setName(gpu.name)
                            .setIndex(index)
                            // lolminer doesn't expose bus
                            .setBus(0)
                            // lolminer doesn't expose temp
                            .setTemp(0)
                            // lolminer doesn't expose fans
                            .setFans(
                                    new FanInfo.Builder()
                                            .setCount(0)
                                            .setSpeedUnits("%")
                                            .build())
                            // lolminer doesn't expose clocks
                            .setFreqInfo(
                                    new FreqInfo.Builder()
                                            .setFreq(0)
                                            .setMemFreq(0)
                                            .build())
                            .build());
        }
    }
}