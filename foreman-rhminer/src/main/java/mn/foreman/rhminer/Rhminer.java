package mn.foreman.rhminer;

import mn.foreman.io.Query;
import mn.foreman.model.AbstractMiner;
import mn.foreman.model.error.MinerException;
import mn.foreman.model.miners.FanInfo;
import mn.foreman.model.miners.MinerStats;
import mn.foreman.model.miners.Pool;
import mn.foreman.model.miners.rig.FreqInfo;
import mn.foreman.model.miners.rig.Gpu;
import mn.foreman.model.miners.rig.Rig;
import mn.foreman.rhminer.json.Response;

import com.fasterxml.jackson.core.type.TypeReference;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A {@link Rhminer} represents a remote rhminer instance.
 *
 * <p>This class relies on the rhminer-api being enabled and configured to
 * allow the server that this application is running on to access it.  If this
 * applications is running on the rig server, only localhost connections need to
 * be allowed.</p>
 */
public class Rhminer
        extends AbstractMiner {

    /**
     * Constructor.
     *
     * @param apiIp   The api IP.
     * @param apiPort The api port.
     */
    Rhminer(
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
                Query.jsonQuery(
                        this.apiIp,
                        this.apiPort,
                        " ",
                        new TypeReference<Response>() {
                        });

        // Add the pool
        statsBuilder.addPool(
                new Pool.Builder()
                        .setName(response.pool)
                        .setPriority(0)
                        .setStatus(
                                true,
                                response.uptime > 0)
                        .setCounts(
                                response.accepted,
                                response.rejected,
                                0)
                        .build());

        // Add the rig
        final List<Response.Info> gpus =
                response.infos
                        .stream()
                        .filter(info -> info.name.contains("GPU"))
                        .collect(Collectors.toList());
        final Rig.Builder rigBuilder =
                new Rig.Builder()
                        .setHashRate(gpus
                                .stream()
                                .map(info -> info.hashRate)
                                .reduce(BigDecimal.ZERO, BigDecimal::add));
        gpus.forEach(gpu -> addGpu(gpu, rigBuilder));

        statsBuilder.addRig(rigBuilder.build());
    }

    /**
     * Adds the provided GPU.
     *
     * @param info       The GPU to add.
     * @param rigBuilder The builder to update.
     */
    private static void addGpu(
            final Response.Info info,
            final Rig.Builder rigBuilder) {
        rigBuilder.addGpu(
                new Gpu.Builder()
                        .setName(info.name)
                        .setIndex(info.name.replace("GPU", ""))
                        .setBus(0)
                        .setTemp(info.temp)
                        .setFans(
                                new FanInfo.Builder()
                                        .setCount(1)
                                        .addSpeed(info.fan)
                                        .setSpeedUnits("RPM")
                                        .build())
                        .setFreqInfo(
                                new FreqInfo.Builder()
                                        .setFreq(0)
                                        .setMemFreq(0)
                                        .build())
                        .build());
    }
}