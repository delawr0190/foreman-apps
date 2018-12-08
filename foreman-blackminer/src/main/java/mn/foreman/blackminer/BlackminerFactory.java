package mn.foreman.blackminer;

import mn.foreman.blackminer.response.SummaryAndStatsResponseStrategy;
import mn.foreman.cgminer.CgMiner;
import mn.foreman.cgminer.PoolsResponseStrategy;
import mn.foreman.cgminer.ResponseStrategy;
import mn.foreman.cgminer.request.CgMinerCommand;
import mn.foreman.cgminer.request.CgMinerRequest;
import mn.foreman.model.Miner;
import mn.foreman.model.MinerFactory;

import java.util.Map;

/**
 * A {@link MinerFactory} implementation that parses a configuration and creates
 * a {@link Miner} that will query a Blackminer.
 */
public class BlackminerFactory
        implements MinerFactory {

    @Override
    public Miner create(final Map<String, String> config) {
        final ResponseStrategy responseStrategy =
                new SummaryAndStatsResponseStrategy();
        return new CgMiner.Builder()
                .setApiIp(config.get("apiIp"))
                .setApiPort(config.get("apiPort"))
                .addRequest(
                        new CgMinerRequest.Builder()
                                .setCommand(CgMinerCommand.POOLS)
                                .build(),
                        new PoolsResponseStrategy())
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
