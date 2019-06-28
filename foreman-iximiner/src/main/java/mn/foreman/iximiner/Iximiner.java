package mn.foreman.iximiner;

import mn.foreman.io.Query;
import mn.foreman.iximiner.json.Status;
import mn.foreman.model.AbstractMiner;
import mn.foreman.model.error.MinerException;
import mn.foreman.model.miners.FanInfo;
import mn.foreman.model.miners.MinerStats;
import mn.foreman.model.miners.Pool;
import mn.foreman.model.miners.cpu.Cpu;
import mn.foreman.model.miners.rig.FreqInfo;
import mn.foreman.model.miners.rig.Gpu;
import mn.foreman.model.miners.rig.Rig;

import com.fasterxml.jackson.core.type.TypeReference;
import org.apache.commons.lang3.Validate;

import java.math.BigDecimal;
import java.util.List;

/**
 * <h1>Overview</h1>
 *
 * An {@link Iximiner} represents a remote iximiner instance.
 *
 * <p>This class relies on the iximiner api being enabled and configured to
 * allow the server that this application is running on to access it.  If this
 * application is running on the rig server, only localhost connections need to
 * be allowed.</p>
 *
 * <p>The iximiner API, at the time of this writing, was very new and lacked
 * detailed information outside of basic pool statistics and hash rate.</p>
 *
 * <p>This class currently queries:</p>
 *
 * <pre>
 *     GET /status
 * </pre>
 *
 * <h1>Limitations</h1>
 *
 * <h2>GPUs</h2>
 *
 * <p>Clocks are not exposed via the API. Therefore, they aren't reported to
 * Foreman.</p>
 *
 * <h2>Pools</h2>
 *
 * <p>Stale shares and pool urls are not reported.</p>
 */
public class Iximiner
        extends AbstractMiner {

    /**
     * Constructor.
     *
     * @param apiIp   The API IP.
     * @param apiPort The API port.
     */
    Iximiner(
            final String apiIp,
            final int apiPort) {
        super(
                apiIp,
                apiPort);
    }

    @Override
    public void addStats(final MinerStats.Builder statsBuilder)
            throws MinerException {
        try {
            final List<Status> statuses =
                    Query.restQuery(
                            this.apiIp,
                            this.apiPort,
                            "/status",
                            new TypeReference<List<Status>>() {
                            });
            Validate.notEmpty(
                    statuses,
                    "statuses cannot be empty");

            final Status status = statuses.get(0);

            statsBuilder.addPool(
                    new Pool.Builder()
                            .setName("not available")
                            .setStatus(
                                    true,
                                    true)
                            .setPriority(0)
                            .setCounts(
                                    status.shares,
                                    status.rejects,
                                    0)
                            .build());

            final List<Status.Hasher> hashers = status.hashers;
            Validate.isTrue(
                    !hashers.isEmpty(),
                    "hashers cannot be empty");
            final Status.Hasher hasher = hashers.get(0);

            if (isGpu(hasher)) {
                final Rig.Builder rigBuilder =
                        new Rig.Builder()
                                .setHashRate(
                                        hasher.devices
                                                .stream()
                                                .map(device -> device.hashRate)
                                                .reduce(
                                                        BigDecimal.ZERO,
                                                        BigDecimal::add));
                hasher.devices.forEach(device -> addGpu(rigBuilder, device));
                statsBuilder.addRig(rigBuilder.build());
            } else {
                // Must be CPU
                final List<Status.Device> devices = hasher.devices;
                Validate.isTrue(
                        !devices.isEmpty(),
                        "devices cannot be empty");
                final Status.Device device = devices.get(0);

                statsBuilder.addCpu(
                        new Cpu.Builder()
                                .setName(device.name)
                                .setFanSpeed(0)
                                .setFrequency(BigDecimal.ZERO)
                                .addThread(device.hashRate)
                                .setTemp(0)
                                .build());
            }
        } catch (final Exception e) {
            throw new MinerException(
                    "Possibly not an iximiner",
                    e);
        }
    }

    /**
     * Adds a GPU from the provided {@link Status.Device}.
     *
     * @param rigBuilder The builder to update.
     * @param device     The {@link Status.Device}.
     */
    private static void addGpu(
            final Rig.Builder rigBuilder,
            final Status.Device device) {
        rigBuilder.addGpu(
                new Gpu.Builder()
                        .setIndex(device.id)
                        .setBus(device.busId.split(":")[0])
                        .setName(device.name)
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

    /**
     * Checks to see if a GPU response.
     *
     * @param hasher The hasher.
     *
     * @return True if GPU; false otherwise.
     */
    private static boolean isGpu(final Status.Hasher hasher) {
        return "GPU".equals(hasher.type);
    }
}
