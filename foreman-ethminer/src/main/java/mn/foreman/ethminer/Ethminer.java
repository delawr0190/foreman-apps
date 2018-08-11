package mn.foreman.ethminer;

import mn.foreman.ethminer.json.Response;
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

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.math.BigDecimal;
import java.util.List;

/**
 * <h1>Overview</h1>
 *
 * An {@link Ethminer} represents a remote ethminer instance.
 *
 * <p>This class relies on the ethminer-api being enabled and configured to
 * allow the server that this application is running on to access it.  If this
 * application is running on the rig server, only localhost connections need to
 * be allowed.</p>
 *
 * <p>This class currently queries "miner_getstathr" via JSON RPC.</p>
 *
 * <h1>Limitations</h1>
 *
 * <h2>GPUs</h2>
 *
 * <p>GPU bus ID isn't exposed via the API.  Therefore, it can't be
 * reported.</p>
 *
 * <p>The GPU name, as well, isn't exposed via the API.  Therefore, an
 * artificial name is generated that matches the format: "GPU &lt;id&gt;"</p>
 *
 * <p>Also, the {@link FreqInfo} for each GPU isn't exposed.  The values that
 * are reported to Foreman are 0.</p>
 */
public class Ethminer
        extends AbstractMiner {

    /** The API password. */
    private final String apiPassword;

    /**
     * Constructor.
     *
     * @param name        The name.
     * @param apiIp       The API IP.
     * @param apiPort     The API port.
     * @param apiPassword The API password.
     */
    Ethminer(
            final String name,
            final String apiIp,
            final int apiPort,
            final String apiPassword) {
        super(
                name,
                apiIp,
                apiPort);
        this.apiPassword = apiPassword;
    }

    @Override
    public void addStats(final MinerStats.Builder statsBuilder)
            throws MinerException {
        final Response response =
                Query.jsonQuery(
                        this.apiIp,
                        this.apiPort,
                        makeCommand(),
                        Response.class);
        final Response.Result result = response.result;
        addPool(
                statsBuilder,
                result);
        addRig(
                statsBuilder,
                result);
    }

    @Override
    public String addToString() {
        return String.format(
                ", apiPassword=%s",
                this.apiPassword);
    }

    @Override
    protected void addToEquals(
            final EqualsBuilder equalsBuilder,
            final AbstractMiner other) {
        final Ethminer otherMiner = (Ethminer) other;
        equalsBuilder.append(this.apiPassword, otherMiner.apiPassword);
    }

    @Override
    protected void addToHashCode(
            final HashCodeBuilder hashCodeBuilder) {
        hashCodeBuilder.append(this.apiPassword);
    }

    /**
     * Adds the {@link Pool} from the {@link Response.Result}.
     *
     * @param builder The builder.
     * @param result  The result.
     */
    private static void addPool(
            final MinerStats.Builder builder,
            final Response.Result result) {
        builder
                .addPool(
                        new Pool.Builder()
                                .setName(
                                        PoolUtils.sanitizeUrl(
                                                result.poolAddress))
                                .setStatus(
                                        true,
                                        (Integer.parseInt(result.runtime) > 0))
                                .setPriority(0)
                                .setCounts(
                                        result.ethShares,
                                        result.ethRejected,
                                        result.ethInvalid)
                                .build());
    }

    /**
     * Adds the {@link Rig} from the {@link Response.Result}.
     *
     * @param builder The builder.
     * @param result  The result.
     *
     * @throws MinerException on failure to parse the rig.
     */
    private static void addRig(
            final MinerStats.Builder builder,
            final Response.Result result)
            throws MinerException {
        final Rig.Builder rigBuilder =
                new Rig.Builder()
                        .setName("ethminer_" + result.version)
                        .setHashRate(new BigDecimal(result.ethHashRate));
        final List<Integer> temperatures = result.temperatures;
        final List<Integer> fans = result.fanPercentages;
        if (temperatures.size() == fans.size()) {
            for (int i = 0; i < temperatures.size(); i++) {
                rigBuilder
                        .addGpu(
                                new Gpu.Builder()
                                        .setName("GPU " + i)
                                        .setIndex(Integer.toString(i))
                                        // Bus is not exposed in ethminer
                                        .setBus("0")
                                        .setTemp(temperatures.get(i))
                                        .setFans(
                                                new FanInfo.Builder()
                                                        .setCount(1)
                                                        .addSpeed(fans.get(i))
                                                        .setSpeedUnits("%")
                                                        .build())
                                        // No frequencies in ethminer
                                        .setFreqInfo(
                                                new FreqInfo.Builder()
                                                        .setFreq("0")
                                                        .setMemFreq("0")
                                                        .build())
                                        .build());
            }
        } else {
            throw new MinerException("Temps and fans should be the same size");
        }
        builder.addRig(rigBuilder.build());
    }

    /**
     * Generates a JSON RPC command.
     *
     * @return The command.
     */
    private String makeCommand() {
        final String password =
                String.format(
                        ",\"params\": {\"psw\": \"%s\"}",
                        this.apiPassword);
        return String.format(
                "{\"id\":%d,\"jsonrpc\":\"%s\",\"method\":\"%s\"%s}\n",
                1,
                "2.0",
                "miner_getstathr",
                (this.apiPassword != null && !this.apiPassword.isEmpty())
                        ? password
                        : "");
    }
}