package mn.foreman.trex;

import mn.foreman.io.Query;
import mn.foreman.model.AbstractMiner;
import mn.foreman.model.error.MinerException;
import mn.foreman.model.miners.FanInfo;
import mn.foreman.model.miners.MinerStats;
import mn.foreman.model.miners.Pool;
import mn.foreman.model.miners.rig.FreqInfo;
import mn.foreman.model.miners.rig.Gpu;
import mn.foreman.model.miners.rig.Rig;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * <h1>Overview</h1>
 *
 * A {@link TrexCcminer} represents a remote t-rex instance.
 *
 * <p>This class relies on the trex-telnet-api being enabled and configured to
 * allow the server that this application is running on to access it.  If this
 * application is running on the rig server, only localhost connections need to
 * be allowed.</p>
 *
 * <p>The t-rex API, at the time of this writing, was very new and lacked
 * detailed information outside of basic pool statistics and hash rate.</p>
 *
 * <p>This class currently queries:</p>
 *
 * <pre>
 *     summary
 * </pre>
 *
 * <h1>Limitations</h1>
 *
 * <p>The stratum URL and GPU-specific metrics aren't exposed via this API.</p>
 */
public class TrexCcminer
        extends AbstractMiner {

    /**
     * Constructor.
     *
     * @param apiIp   The API IP.
     * @param apiPort The API port.
     */
    TrexCcminer(
            final String apiIp,
            final int apiPort) {
        super(
                apiIp,
                apiPort);
    }

    @Override
    public void addStats(final MinerStats.Builder statsBuilder)
            throws MinerException {
        final String summary =
                Query.delimiterQuery(
                        this.apiIp,
                        this.apiPort,
                        "summary",
                        1,
                        TimeUnit.SECONDS);
        final Map<String, String> stats =
                Arrays
                        .stream(summary.split(";"))
                        .collect(Collectors.toMap(
                                value -> value.split("=")[0],
                                value -> value.split("=")[1]));

        statsBuilder.addPool(
                new Pool.Builder()
                        .setName("no.pool.reported:1234")
                        .setStatus(
                                true,
                                true)
                        .setPriority(0)
                        .setCounts(
                                stats.get("ACC"),
                                stats.get("REJ"),
                                "0")
                        .build());

        final Rig.Builder rigBuilder =
                new Rig.Builder()
                        .setHashRate(
                                new BigDecimal(stats.get("KHS"))
                                        .multiply(new BigDecimal(1000)));
        for (int i = 0; i < Integer.parseInt(stats.get("GPUS")); i++) {
            rigBuilder.addGpu(
                    new Gpu.Builder()
                            .setIndex(i)
                            .setBus(0)
                            .setName("GPU " + i)
                            .setTemp(0)
                            .setFans(
                                    new FanInfo.Builder()
                                            .setCount(0)
                                            .setSpeedUnits("%")
                                            .build())
                            .setFreqInfo(
                                    new FreqInfo.Builder()
                                            .setFreq(0)
                                            .setMemFreq(0)
                                            .build())
                            .build());
        }
        statsBuilder.addRig(rigBuilder.build());
    }
}