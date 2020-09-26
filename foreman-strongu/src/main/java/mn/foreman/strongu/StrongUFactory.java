package mn.foreman.strongu;

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
public class StrongUFactory
        implements MinerFactory {

    @Override
    public Miner create(final Map<String, String> config) {
        final Context cgContext = new Context();
        final ObjectMapper objectMapper = new ObjectMapper();
        final ResponseStrategy responseStrategy =
                new RawStoringDecorator(
                        cgContext,
                        objectMapper,
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
                                cgContext));
        return new CgMiner.Builder()
                .setApiIp(config.get("apiIp"))
                .setApiPort(config.get("apiPort"))
                .addRequest(
                        new CgMinerRequest.Builder()
                                .setCommand(CgMinerCommand.POOLS)
                                .build(),
                        new RawStoringDecorator(
                                cgContext,
                                objectMapper,
                                new PoolsResponseStrategy(
                                        new MrrRigIdCallback(cgContext))))
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
                .build();
    }
}
