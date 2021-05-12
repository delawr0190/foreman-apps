package mn.foreman.blackminer;

import mn.foreman.antminer.StockMacStrategy;
import mn.foreman.cgminer.*;
import mn.foreman.cgminer.request.CgMinerCommand;
import mn.foreman.cgminer.request.CgMinerRequest;
import mn.foreman.model.AlternatingMiner;
import mn.foreman.model.Miner;
import mn.foreman.model.MinerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * A {@link MinerFactory} implementation that parses a configuration and creates
 * a {@link Miner} that will query a Blackminer.
 */
public class BlackminerFactory
        extends CgMinerFactory {

    /** The mapper for JSON. */
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Override
    protected Miner create(
            final String apiIp,
            final String apiPort,
            final List<String> statsWhitelist,
            final Map<String, Object> config) {
        final int apiPortInt = Integer.parseInt(apiPort);
        return new AlternatingMiner(
                apiIp,
                apiPortInt,
                toCgminer(
                        config,
                        apiIp,
                        apiPortInt,
                        statsWhitelist),
                toWeb(config));
    }

    /**
     * Creates a {@link Miner} that will query cgminer.
     *
     * @param config         The config.
     * @param apiIp          The API ip.
     * @param apiPort        The API port.
     * @param statsWhitelist The stats whitelist.
     *
     * @return The {@link Miner}.
     */
    private static Miner toCgminer(
            final Map<String, Object> config,
            final String apiIp,
            final int apiPort,
            final List<String> statsWhitelist) {
        final Context cgContext = new Context();
        final ResponseStrategy responseStrategy =
                new AggregatingResponseStrategy<>(
                        ImmutableMap.of(
                                "SUMMARY",
                                (values, builder, context) ->
                                        BlackminerUtils.updateSummary(
                                                values,
                                                builder),
                                "STATS",
                                (values, builder, context) ->
                                        BlackminerUtils.updateStats(
                                                values,
                                                builder)),
                        () -> null,
                        cgContext);

        final String username =
                config.getOrDefault("username", "").toString();
        final String password =
                config.getOrDefault("password", "").toString();
        final int port =
                Integer.parseInt(config.getOrDefault("port", "80").toString());

        return new CoinTypeDecorator(
                new CgMiner.Builder(cgContext, statsWhitelist)
                        .setApiIp(apiIp)
                        .setApiPort(apiPort)
                        .addRequest(
                                new CgMinerRequest.Builder()
                                        .setCommand(CgMinerCommand.POOLS)
                                        .build(),
                                new PoolsResponseStrategy(
                                        new MrrRigIdCallback(cgContext)))
                        .addRequest(
                                new CgMinerRequest.Builder()
                                        .setCommand(CgMinerCommand.SUMMARY)
                                        .build(),
                                responseStrategy)
                        .addRequest(
                                new CgMinerRequest.Builder()
                                        .setCommand(CgMinerCommand.STATS)
                                        .build(),
                                responseStrategy)
                        .setMacStrategy(
                                new StockMacStrategy(
                                        apiIp,
                                        port,
                                        "blackMiner Configuration",
                                        username,
                                        password))
                        .setConnectTimeout(
                                1,
                                TimeUnit.SECONDS)
                        .build(),
                username,
                password,
                port,
                OBJECT_MAPPER);
    }

    /**
     * Creates a {@link Miner} that will query the web.
     *
     * @param config The config.
     *
     * @return The {@link Miner}.
     */
    private static Miner toWeb(
            final Map<String, Object> config) {
        final String apiIp = config.get("apiIp").toString();
        final String username =
                config.getOrDefault("username", "").toString();
        final String password =
                config.getOrDefault("password", "").toString();
        final int port =
                Integer.parseInt(config.getOrDefault("port", "8080").toString());
        return new CoinTypeDecorator(
                new BlackminerWeb(
                        apiIp,
                        port,
                        username,
                        password,
                        new StockMacStrategy(
                                apiIp,
                                port,
                                "blackMiner Configuration",
                                username,
                                password)),
                username,
                password,
                port,
                OBJECT_MAPPER);
    }
}