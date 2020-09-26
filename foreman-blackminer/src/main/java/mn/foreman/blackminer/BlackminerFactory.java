package mn.foreman.blackminer;

import mn.foreman.cgminer.*;
import mn.foreman.cgminer.request.CgMinerCommand;
import mn.foreman.cgminer.request.CgMinerRequest;
import mn.foreman.model.Miner;
import mn.foreman.model.MinerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;

import java.util.Map;

/**
 * A {@link MinerFactory} implementation that parses a configuration and creates
 * a {@link Miner} that will query a Blackminer.
 */
public class BlackminerFactory
        implements MinerFactory {

    @Override
    public Miner create(final Map<String, Object> config) {
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
        return new CoinTypeDecorator(
                new CgMiner.Builder()
                        .setApiIp(config.get("apiIp").toString())
                        .setApiPort(config.get("apiPort").toString())
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
                        .build(),
                config.getOrDefault("username", "").toString(),
                config.getOrDefault("password", "").toString(),
                Integer.parseInt(config.getOrDefault("port", "80").toString()),
                new ObjectMapper());
    }
}