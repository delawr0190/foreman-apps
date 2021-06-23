package mn.foreman.antminer;

import mn.foreman.antminer.response.antminer.StatsResponseStrategy;
import mn.foreman.antminer.response.braiins.BraiinsResponseStrategy;
import mn.foreman.cgminer.*;
import mn.foreman.cgminer.request.CgMinerCommand;
import mn.foreman.model.ApplicationConfiguration;
import mn.foreman.model.MacStrategy;
import mn.foreman.model.Miner;
import mn.foreman.model.MinerFactory;

import com.google.common.collect.ImmutableMap;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * A {@link MinerFactory} implementation that parses a configuration and creates
 * a {@link Miner} that will query an Antminer running either stock firmware or
 * braiins.
 */
public class AntminerFactory
        extends CgMinerFactory {

    /** The configuration. */
    private final ApplicationConfiguration applicationConfiguration;

    /** The hash rate multiplier. */
    private final double multiplier;

    /**
     * Constructor.
     *
     * @param multiplier               The hash rate multiplier.
     * @param applicationConfiguration The configuration.
     */
    public AntminerFactory(
            final double multiplier,
            final ApplicationConfiguration applicationConfiguration) {
        this.multiplier = multiplier;
        this.applicationConfiguration = applicationConfiguration;
    }

    @Override
    protected Miner create(
            final String apiIp,
            final String apiPort,
            final List<String> statsWhitelist,
            final Map<String, Object> config) {
        final int port = Integer.parseInt(config.getOrDefault("port", "80").toString());
        final String username = config.getOrDefault("username", "").toString();
        final String password = config.getOrDefault("password", "").toString();

        final Context context = new Context();
        final CgMiner antminer =
                toMiner(
                        apiIp,
                        apiPort,
                        context,
                        statsWhitelist,
                        Arrays.asList(
                                ImmutableMap.of(
                                        CgMinerCommand.POOLS,
                                        new PoolsResponseStrategy(
                                                new MrrRigIdCallback(context),
                                                new LastShareTimeCallback(context))),
                                ImmutableMap.of(
                                        CgMinerCommand.STATS,
                                        new RateMultiplyingDecorator(
                                                "STATS",
                                                "GHS 5s",
                                                this.multiplier,
                                                new StatsResponseStrategy(
                                                        context,
                                                        new StockPowerModeStrategy(
                                                                apiIp,
                                                                port,
                                                                "antMiner Configuration",
                                                                username,
                                                                password,
                                                                this.applicationConfiguration))))),
                        new StockMacStrategy(
                                apiIp,
                                port,
                                "antMiner Configuration",
                                username,
                                password,
                                this.applicationConfiguration),
                        this.applicationConfiguration);

        final ResponseStrategy braiinsStrategy =
                new BraiinsResponseStrategy(
                        context);
        final CgMiner braiins =
                toMiner(
                        apiIp,
                        apiPort,
                        context,
                        statsWhitelist,
                        Arrays.asList(
                                ImmutableMap.of(
                                        CgMinerCommand.POOLS,
                                        new PoolsResponseStrategy(
                                                new MrrRigIdCallback(context))),
                                ImmutableMap.of(
                                        CgMinerCommand.SUMMARY,
                                        braiinsStrategy),
                                ImmutableMap.of(
                                        CgMinerCommand.FANS,
                                        braiinsStrategy),
                                ImmutableMap.of(
                                        CgMinerCommand.TEMPS,
                                        braiinsStrategy),
                                ImmutableMap.of(
                                        CgMinerCommand.DEVS,
                                        braiinsStrategy)),
                        new BraiinsMacStrategy(
                                apiIp,
                                port,
                                username,
                                password,
                                this.applicationConfiguration),
                        this.applicationConfiguration);

        return new VersionDecorator(
                apiIp,
                apiPort,
                Integer.toString(port),
                "antMiner Configuration",
                username,
                password,
                context,
                antminer,
                braiins,
                this.applicationConfiguration);
    }

    /**
     * Creates a miner with the provided configuration.
     *
     * @param apiIp          The API IP.
     * @param apiPort        The API port.
     * @param context        The context.
     * @param statsWhitelist The whitelist.
     * @param requests       The requests.
     * @param macStrategy    The MAC strategy.
     *
     * @return The new miner.
     */
    private static CgMiner toMiner(
            final String apiIp,
            final String apiPort,
            final Context context,
            final List<String> statsWhitelist,
            final List<Map<CgMinerCommand, ResponseStrategy>> requests,
            final MacStrategy macStrategy,
            final ApplicationConfiguration applicationConfiguration) {
        return new CgMiner.Builder(context, statsWhitelist)
                .setApiIp(apiIp)
                .setApiPort(apiPort)
                .setConnectTimeout(applicationConfiguration.getReadSocketTimeout())
                .addRequests(requests)
                .setMacStrategy(macStrategy)
                .build();
    }
}