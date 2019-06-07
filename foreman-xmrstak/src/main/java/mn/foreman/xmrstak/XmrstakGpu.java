package mn.foreman.xmrstak;

import mn.foreman.model.miners.FanInfo;
import mn.foreman.model.miners.MinerStats;
import mn.foreman.model.miners.rig.FreqInfo;
import mn.foreman.model.miners.rig.Gpu;
import mn.foreman.model.miners.rig.Rig;
import mn.foreman.xmrstak.json.Response;

/**
 * <h1>Overview</h1>
 *
 * An {@link XmrstakGpu} represents a remote xmrstak instance.
 *
 * <p>This class relies on the xmrstak-api being enabled and configured to
 * allow the server that this application is running on to access it.  If this
 * application is running on the rig server, only localhost connections need to
 * be allowed.</p>
 *
 * <p>This class currently queries:</p>
 *
 * <ul>
 * <li>http://{@link #apiIp}:{@link #apiPort}/api.json</li>
 * </ul>
 *
 * <h1>Limitations</h1>
 *
 * <p>The API for xmrstak is very limited.  Therefore, the GPUs that are
 * reported contain almost no information.</p>
 *
 * <p>The GPUs are reported with no information so that Foreman, at the very
 * least, can report the number of GPUs that were successfully identified in the
 * rig.</p>
 *
 * <p>If additional support isn't exposed via the API, an alternative program
 * will be provided by Foreman to provide a REST interface that will query and
 * provide temp, freq, and fan metrics from the native APIs.</p>
 *
 * <h2>Pools</h2>
 *
 * <h3>Stale shares</h3>
 *
 * <p>Stale shares aren't reported specifically.  Therefore, they can't be
 * reported.  They are, however, most likely included in the rejected
 * shares.</p>
 */
public class XmrstakGpu
        extends AbstractXmrstak {

    /**
     * Constructor.
     *
     * @param apiIp   The API IP.
     * @param apiPort The API port.
     */
    XmrstakGpu(
            final String apiIp,
            final int apiPort) {
        super(
                apiIp,
                apiPort);
    }

    @Override
    protected void addDevices(
            final MinerStats.Builder statsBuilder,
            final Response response) {
        final Rig.Builder rigBuilder =
                new Rig.Builder()
                        .setHashRate(response.hashrate.totals.get(0));
        for (int i = 0; i < response.hashrate.threads.size(); i++) {
            addGpuFromThread(
                    i,
                    rigBuilder);
        }
        statsBuilder.addRig(rigBuilder.build());
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
}