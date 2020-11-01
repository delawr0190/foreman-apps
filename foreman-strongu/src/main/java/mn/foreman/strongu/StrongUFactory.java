package mn.foreman.strongu;

import mn.foreman.antminer.StockMacStrategy;
import mn.foreman.cgminer.*;
import mn.foreman.cgminer.request.CgMinerCommand;
import mn.foreman.cgminer.request.CgMinerRequest;
import mn.foreman.model.Miner;
import mn.foreman.model.MinerFactory;

import com.google.common.collect.ImmutableMap;

import java.util.List;
import java.util.Map;

/**
 * A {@link MinerFactory} implementation that parses a configuration and creates
 * a {@link Miner} that will query a StrongU.
 */
public class StrongUFactory
        extends CgMinerFactory {

    @Override
    protected Miner create(
            final String apiIp,
            final String apiPort,
            final List<String> statsWhitelist,
            final Map<String, Object> config) {
        final Context cgContext = new Context();
        final ResponseStrategy responseStrategy =
                new AggregatingResponseStrategy<>(
                        ImmutableMap.of(
                                "SUMMARY",
                                (values, builder, context) ->
                                        StrongUUtils.updateSummary(
                                                values,
                                                builder),
                                "DEVS",
                                (values, builder, context) ->
                                        StrongUUtils.updateDevs(
                                                values,
                                                builder)),
                        () -> null,
                        cgContext);
        return new CgMiner.Builder(cgContext, statsWhitelist)
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
                                .setCommand(CgMinerCommand.DEVS)
                                .build(),
                        responseStrategy)
                .setMacStrategy(
                        new StockMacStrategy(
                                apiIp,
                                Integer.parseInt(config.getOrDefault("port", "80").toString()),
                                "stuMiner Configuration",
                                config.getOrDefault("username", "").toString(),
                                config.getOrDefault("password", "").toString()))
                .build();
    }
}
