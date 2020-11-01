package mn.foreman.avalon;

import mn.foreman.cgminer.*;
import mn.foreman.cgminer.request.CgMinerCommand;
import mn.foreman.cgminer.request.CgMinerRequest;
import mn.foreman.model.Miner;
import mn.foreman.model.MinerFactory;

import com.google.common.collect.ImmutableMap;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * A {@link MinerFactory} implementation that parses a configuration and creates
 * a {@link Miner} that will query an Avalon.
 */
public class AvalonFactory
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
                                        AvalonUtils.updateSummary(
                                                values,
                                                builder),
                                "STATS",
                                (values, builder, context) ->
                                        AvalonUtils.updateStats(
                                                values,
                                                builder)),
                        () -> null,
                        cgContext);
        return new CgMiner.Builder(cgContext, statsWhitelist)
                .setApiIp(apiIp)
                .setApiPort(apiPort)
                .addRequest(
                        new CgMinerRequest.Builder()
                                .addCommand(CgMinerCommand.POOLS)
                                .addCommand(CgMinerCommand.SUMMARY)
                                .addCommand(CgMinerCommand.STATS)
                                .build(),
                        new MultiResponseStrategy(
                                Arrays.asList(
                                        new PoolsResponseStrategy(
                                                new MrrRigIdCallback(cgContext)),
                                        responseStrategy)))
                .setMacStrategy(
                        new AvalonMacStrategy(
                                apiIp,
                                Integer.parseInt(config.getOrDefault("port", "80").toString())))
                .build();
    }
}