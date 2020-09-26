package mn.foreman.sgminer;

import mn.foreman.cgminer.CgMiner;
import mn.foreman.cgminer.CgMinerFactory;
import mn.foreman.cgminer.PoolsResponseStrategy;
import mn.foreman.cgminer.request.CgMinerCommand;
import mn.foreman.cgminer.request.CgMinerRequest;
import mn.foreman.model.Miner;
import mn.foreman.model.MinerFactory;
import mn.foreman.sgminer.response.DevsResponseStrategy;
import mn.foreman.sgminer.response.ResponseStrategyImpl;
import mn.foreman.sgminer.response.SgminerResponseStrategy;

import java.util.List;
import java.util.Map;

/**
 * A {@link MinerFactory} implementation that parses a configuration and creates
 * a {@link Miner} that will query an sgminer.
 */
public class SgminerFactory
        extends CgMinerFactory {

    @Override
    protected Miner create(
            final String apiIp,
            final String apiPort,
            final List<String> statsWhitelist,
            final Map<String, Object> config) {
        final SgminerResponseStrategy responseStrategy =
                new ResponseStrategyImpl(
                        new PoolsResponseStrategy(),
                        new DevsResponseStrategy());
        return new CgMiner.Builder()
                .setApiIp(apiIp)
                .setApiPort(apiPort)
                .addRequest(
                        new CgMinerRequest.Builder()
                                .setCommand(CgMinerCommand.DEVS)
                                .build(),
                        responseStrategy::processDevs)
                .addRequest(
                        new CgMinerRequest.Builder()
                                .setCommand(CgMinerCommand.POOLS)
                                .build(),
                        responseStrategy::processPools)
                .build();
    }
}