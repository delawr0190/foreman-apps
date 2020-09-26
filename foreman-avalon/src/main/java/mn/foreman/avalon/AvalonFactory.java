package mn.foreman.avalon;

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
 * a {@link Miner} that will query an Avalon.
 */
public class AvalonFactory
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
                                                AvalonUtils.updateSummary(
                                                        values,
                                                        builder),
                                        "STATS",
                                        (values, builder, context) ->
                                                AvalonUtils.updateStats(
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
                                .setCommand(CgMinerCommand.STATS)
                                .build(),
                        responseStrategy)
                .build();
    }


}