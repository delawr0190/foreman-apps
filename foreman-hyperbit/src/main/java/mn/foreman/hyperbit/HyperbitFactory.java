package mn.foreman.hyperbit;

import mn.foreman.baikal.response.DevsResponseStrategy;
import mn.foreman.cgminer.CgMiner;
import mn.foreman.cgminer.Context;
import mn.foreman.cgminer.MrrRigIdCallback;
import mn.foreman.cgminer.PoolsResponseStrategy;
import mn.foreman.cgminer.request.CgMinerCommand;
import mn.foreman.cgminer.request.CgMinerRequest;
import mn.foreman.model.Miner;
import mn.foreman.model.MinerFactory;

import java.util.Map;

/**
 * A {@link MinerFactory} implementation that parses a configuration and creates
 * a {@link Miner} that will query a hyperbit.
 *
 * <p>Note: this factory leverages the Baikal classes since the miners return
 * similar responses.</p>
 */
public class HyperbitFactory
        implements MinerFactory {

    @Override
    public Miner create(final Map<String, String> config) {
        final Context context = new Context();
        return new CgMiner.Builder()
                .setApiIp(config.get("apiIp"))
                .setApiPort(config.get("apiPort"))
                .addRequest(
                        new CgMinerRequest.Builder()
                                .setCommand(CgMinerCommand.POOLS)
                                .build(),
                        new PoolsResponseStrategy(
                                new MrrRigIdCallback(context)))
                .addRequest(
                        new CgMinerRequest.Builder()
                                .setCommand(CgMinerCommand.DEVS)
                                .build(),
                        new DevsResponseStrategy(
                                "temperature",
                                context))
                .build();
    }
}
