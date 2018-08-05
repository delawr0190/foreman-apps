package mn.foreman.baikal;

import mn.foreman.baikal.response.DevsResponseStrategy;
import mn.foreman.cgminer.CgMiner;
import mn.foreman.cgminer.PoolsResponseStrategy;
import mn.foreman.cgminer.request.CgMinerCommand;
import mn.foreman.cgminer.request.CgMinerRequest;
import mn.foreman.model.Miner;
import mn.foreman.model.MinerFactory;

import java.util.Map;

/**
 * A {@link MinerFactory} implementation that parses a configuration and creates
 * a {@link Miner} that will query a Baikal.
 */
public class BaikalFactory
        implements MinerFactory {

    @Override
    public Miner create(final Map<String, String> config) {
        return new CgMiner.Builder()
                .setName(config.get("name"))
                .setApiIp(config.get("apiIp"))
                .setApiPort(config.get("apiPort"))
                .addRequest(
                        new CgMinerRequest.Builder()
                                .setCommand(CgMinerCommand.POOLS)
                                .build(),
                        new PoolsResponseStrategy())
                .addRequest(
                        new CgMinerRequest.Builder()
                                .setCommand(CgMinerCommand.DEVS)
                                .build(),
                        new DevsResponseStrategy())
                .build();
    }
}
