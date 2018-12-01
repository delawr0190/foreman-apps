package mn.foreman.claymore;

import mn.foreman.claymore.json.Response;
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

import com.fasterxml.jackson.core.type.TypeReference;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

/**
 * <h1>Overview</h1>
 *
 * A {@link Claymore} represents a remote claymore instance.
 *
 * <p>This class relies on the claymore-api being enabled and configured to
 * allow the server that this application is running on to access it.  If this
 * application is running on the rig server, only localhost connections need to
 * be allowed.</p>
 *
 * <p>This class currently queries "miner_getstat1" via JSON RPC.</p>
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
 *
 * <h2>Pools</h2>
 *
 * <h3>Connection status</h3>
 *
 * <p>The API doesn't expose information related to when a connection to a pool
 * has been broken.  Therefore, the {@link Pool} will always appear to be
 * connected. Shares, however, will not increase, which can be used to infer a
 * problem.</p>
 */
public class Claymore
        extends AbstractMiner {

    /** The API password. */
    private final String apiPassword;

    /** The claymore type. */
    private final ClaymoreType claymoreType;

    /**
     * Constructor.
     *
     * @param apiIp        The API IP.
     * @param apiPort      The API port.
     * @param apiPassword  The API password.
     * @param claymoreType The claymore type.
     */
    Claymore(
            final String apiIp,
            final int apiPort,
            final String apiPassword,
            final ClaymoreType claymoreType) {
        super(
                apiIp,
                apiPort);
        this.apiPassword = apiPassword;
        this.claymoreType = claymoreType;
    }

    @Override
    public void addStats(final MinerStats.Builder statsBuilder)
            throws MinerException {
        final Response response =
                Query.jsonQuery(
                        this.apiIp,
                        this.apiPort,
                        makeCommand(),
                        new TypeReference<Response>() {
                        });
        final List<String> results = response.result;

        final String[] ethRateAndShares = results.get(2).split(";");
        final String ethHashRate = ethRateAndShares[0];
        final String ethTotalShares = ethRateAndShares[1];
        final String ethRejectedShares = ethRateAndShares[2];

        final String[] dcrRateAndShares = results.get(4).split(";");
        final String dcrHashRate = dcrRateAndShares[0];
        final String dcrTotalShares = dcrRateAndShares[1];
        final String dcrRejectedShares = dcrRateAndShares[2];

        final List<String> temps = new LinkedList<>();
        final List<String> fans = new LinkedList<>();
        final String[] tempsAndFans = results.get(6).split(";");
        for (int i = 0; i < tempsAndFans.length; i += 2) {
            temps.add(tempsAndFans[i]);
            fans.add(tempsAndFans[i + 1]);
        }

        final String[] pools = results.get(7).split(";");
        final String[] shares = results.get(8).split(";");

        addPools(
                pools,
                new String[]{ethTotalShares, dcrTotalShares},
                new String[]{ethRejectedShares, dcrRejectedShares},
                new String[]{shares[0], shares[2]},
                statsBuilder);
        addRig(
                ethHashRate,
                temps,
                fans,
                this.claymoreType,
                statsBuilder);
        addRig(
                dcrHashRate,
                temps,
                fans,
                this.claymoreType,
                statsBuilder);
    }

    @Override
    protected void addToEquals(
            final EqualsBuilder equalsBuilder,
            final AbstractMiner other) {
        final Claymore otherClaymore = (Claymore) other;
        equalsBuilder
                .append(this.apiPassword, otherClaymore.apiPassword)
                .append(this.claymoreType, otherClaymore.claymoreType);
    }

    @Override
    protected void addToHashCode(final HashCodeBuilder hashCodeBuilder) {
        hashCodeBuilder
                .append(this.apiPassword)
                .append(this.claymoreType);
    }

    @Override
    protected String addToString() {
        return String.format(
                ", apiPassword=%s, type=%s",
                this.apiPassword,
                this.claymoreType);
    }

    /**
     * Adds a {@link Pool} using the provided parameters.
     *
     * @param pools          The pools.
     * @param totalShares    The total shares.
     * @param rejectedShares The rejected shares.
     * @param invalidShares  The invalid shares.
     * @param builder        The builder to update.
     */
    private static void addPools(
            final String[] pools,
            final String[] totalShares,
            final String[] rejectedShares,
            final String[] invalidShares,
            final MinerStats.Builder builder) {
        for (int i = 0; i < pools.length; i++) {
            builder.addPool(
                    new Pool.Builder()
                            .setName(PoolUtils.sanitizeUrl(pools[i]))
                            .setStatus(true, true)
                            .setPriority(0)
                            .setCounts(
                                    toInt(totalShares[i]) - toInt(rejectedShares[i]),
                                    toInt(rejectedShares[i]),
                                    toInt(invalidShares[i]))
                            .build());
        }
    }

    /**
     * Adds a {@link Rig} using the provided parameters.
     *
     * @param hashRate     The hash rate.
     * @param temps        The temperatures.
     * @param fans         The fans.
     * @param claymoreType The claymore type.
     * @param builder      The builder to update.
     */
    private static void addRig(
            final String hashRate,
            final List<String> temps,
            final List<String> fans,
            final ClaymoreType claymoreType,
            final MinerStats.Builder builder) {
        final BigDecimal realHashRate =
                toHashRate(
                        hashRate,
                        claymoreType);
        if (realHashRate.compareTo(BigDecimal.ZERO) > 0) {
            final Rig.Builder rigBuilder =
                    new Rig.Builder()
                            .setHashRate(realHashRate);
            for (int i = 0; i < temps.size(); i++) {
                rigBuilder
                        .addGpu(
                                new Gpu.Builder()
                                        .setName("GPU " + i)
                                        .setIndex(Integer.toString(i))
                                        // Bus is not exposed in claymore
                                        .setBus("0")
                                        .setTemp(temps.get(i))
                                        .setFans(
                                                new FanInfo.Builder()
                                                        .setCount(1)
                                                        .addSpeed(fans.get(i))
                                                        .setSpeedUnits("%")
                                                        .build())
                                        // No frequencies in claymore
                                        .setFreqInfo(
                                                new FreqInfo.Builder()
                                                        .setFreq("0")
                                                        .setMemFreq("0")
                                                        .build())
                                        .build());
            }
            builder.addRig(rigBuilder.build());
        }
    }

    /**
     * Converts the hash rate to the desired type in Hs.
     *
     * @param hashRate     The ETH rate.
     * @param claymoreType The claymore type.
     *
     * @return The hash rate.
     */
    private static BigDecimal toHashRate(
            final String hashRate,
            final ClaymoreType claymoreType) {
        return new BigDecimal(hashRate).multiply(claymoreType.getMultiplier());
    }

    /**
     * Converts the provided {@link String} to an int.
     *
     * @param value The value to convert.
     *
     * @return The int.
     */
    private static int toInt(final String value) {
        if (!"off".equals(value)) {
            return Integer.parseInt(value);
        }
        return 0;
    }

    /**
     * Generates a JSON RPC command.
     *
     * @return The command.
     */
    private String makeCommand() {
        return String.format(
                "{\"id\":%d,\"jsonrpc\":\"%s\",\"method\":\"%s\"%s}\n",
                0,
                "2.0",
                "miner_getstat1",
                (this.apiPassword != null && !this.apiPassword.isEmpty())
                        ? ",\"psw\":\"" + this.apiPassword + "\""
                        : "");
    }
}