package mn.foreman.xmrstak;

import mn.foreman.model.miners.MinerStats;
import mn.foreman.model.miners.cpu.Cpu;
import mn.foreman.xmrstak.json.Response;

/**
 * <h1>Overview</h1>
 *
 * An {@link XmrstakCpu} represents a remote xmrstak instance.
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
 * <h2>Pools</h2>
 *
 * <h3>Stale shares</h3>
 *
 * <p>Stale shares aren't reported specifically.  Therefore, they can't be
 * reported.  They are, however, most likely included in the rejected
 * shares.</p>
 */
public class XmrstakCpu
        extends AbstractXmrstak {

    /**
     * Constructor.
     *
     * @param apiIp   The API IP.
     * @param apiPort The API port.
     */
    XmrstakCpu(
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
        final Cpu.Builder cpuBuilder =
                new Cpu.Builder()
                        .setName("CPU 0");
        response.hashrate.threads
                .stream()
                .map(integers -> integers.get(0))
                .forEachOrdered(cpuBuilder::addThread);
        statsBuilder.addCpu(cpuBuilder.build());
    }
}