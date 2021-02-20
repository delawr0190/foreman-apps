package mn.foreman.minerva;

import mn.foreman.cgminer.*;
import mn.foreman.cgminer.request.CgMinerCommand;
import mn.foreman.cgminer.request.CgMinerRequest;
import mn.foreman.model.Miner;

import com.google.common.collect.ImmutableMap;

import java.util.List;
import java.util.Map;

/** A {@link CgMinerFactory} that will query and obtain stats from a miner-va. */
public class MinerVaFactory
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
                                        MinerVaUtils.updateSummary(
                                                values,
                                                builder,
                                                cgContext),
                                "STATS",
                                (values, builder, context) ->
                                        MinerVaUtils.updateStats(
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
                                .setCommand(CgMinerCommand.STATS)
                                .build(),
                        responseStrategy)
                .setMacStrategy(
                        new MinerVaMacStrategy(
                                apiIp,
                                Integer.parseInt(apiPort)))
                .build();
    }
}
