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
import mn.foreman.xmrig.current.json.Summary;

import com.fasterxml.jackson.core.type.TypeReference;

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

    /**
     * Constructor.
     *
     * @param apiIp   The API IP.
     * @param apiPort The API port.
     */
    public XmrigNew(
            final String apiIp,
            final int apiPort) {
        super(
                apiIp,
                apiPort);
    }

    @Override
    public void addStats(final MinerStats.Builder statsBuilder)
            throws MinerException {
        final Summary summary =
                Query.restQuery(
                        this.apiIp,
                        this.apiPort,
                        "/1/summary",
                        new TypeReference<Summary>() {
                        });
        addPool(
                summary,
                statsBuilder);
        addRig(
                summary,
                statsBuilder);
    }

    /**
     * Adds a {@link Gpu} to the provided {@link Rig.Builder builder}.
     *
     * @param index      The index.
     * @param rigBuilder The builder.
     */
    private void addGpuFromThread(
            final int index,
            final Rig.Builder rigBuilder) {
        rigBuilder
                .addGpu(
                        new Gpu.Builder()
                                .setName("GPU " + index)
                                .setIndex(index)
                                // No bus in API
                                .setBus(0)
                                // No temp in API
                                .setTemp(0)
                                // No fans in API
                                .setFans(
                                        new FanInfo.Builder()
                                                .setCount(0)
                                                .setSpeedUnits("%")
                                                .build())
                                // No freq info in API
                                .setFreqInfo(
                                        new FreqInfo.Builder()
                                                .setFreq(0)
                                                .setMemFreq(0)
                                                .build())
                                .build());
    }

    /**
     * Adds a {@link Pool} from the {@link Summary}.
     *
     * @param summary      The {@link Summary}.
     * @param statsBuilder The {@link MinerStats.Builder builder} to update.
     */
    private void addPool(
            final Summary summary,
            final MinerStats.Builder statsBuilder) {
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
     * Adds a {@link Rig} from the {@link Summary}.
     *
     * @param summary      The {@link Summary}.
     * @param statsBuilder The {@link MinerStats.Builder builder} to update.
     */
    private void addRig(
            final Summary summary,
            final MinerStats.Builder statsBuilder) {
        final Rig.Builder rigBuilder =
                new Rig.Builder()
                        .setHashRate(summary.hashrate.totals.get(0));
        for (int i = 0; i < summary.hashrate.threads.size(); i++) {
            addGpuFromThread(
                    i,
                    rigBuilder);
        }
        statsBuilder.addRig(rigBuilder.build());
    }
}