package mn.foreman.honorknight;

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
 * a {@link Miner} that will query a honorknight.
 */
public class HonorKnightFactory
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
                                "STATS",
                                (values, builder, context) ->
                                        HonorKnightUtils.updateStats(
                                                cgContext,
                                                values,
                                                builder),
                                "DEVS",
                                (values, builder, context) ->
                                        HonorKnightUtils.updateEDevs(
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
                                new MrrRigIdCallback(
                                        cgContext)))
                .addRequest(
                        new CgMinerRequest.Builder()
                                .setCommand(CgMinerCommand.STATS)
                                .build(),
                        responseStrategy)
                .addRequest(
                        new CgMinerRequest.Builder()
                                .setCommand(CgMinerCommand.EDEVS)
                                .build(),
                        responseStrategy)
                .setMacStrategy(
                        new HonorKnightMacStrategy(
                                apiIp,
                                Integer.parseInt(
                                        config.getOrDefault(
                                                "testPort",
                                                "80").toString())))
                .build();
    }
}