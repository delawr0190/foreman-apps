package mn.foreman.baikal;

import mn.foreman.baikal.response.DevsResponseStrategy;
import mn.foreman.cgminer.*;
import mn.foreman.cgminer.request.CgMinerCommand;
import mn.foreman.cgminer.request.CgMinerRequest;
import mn.foreman.model.Miner;
import mn.foreman.model.MinerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * A {@link MinerFactory} implementation that parses a configuration and creates
 * a {@link Miner} that will query a Baikal.
 */
public class BaikalFactory
        implements MinerFactory {

    @Override
    public Miner create(final Map<String, Object> config) {
        final ConcurrentMap<String, String> poolAlgos =
                new ConcurrentHashMap<>();
        final Context context = new Context();
        final PoolCallback mrrRigIdCallback =
                new MrrRigIdCallback(context);
        return new CgMiner.Builder()
                .setApiIp(config.get("apiIp").toString())
                .setApiPort(config.get("apiPort").toString())
                .addRequest(
                        new CgMinerRequest.Builder()
                                .setCommand(CgMinerCommand.POOLS)
                                .build(),
                        new PoolsResponseStrategy(
                                poolInfo -> {
                                    mrrRigIdCallback.foundPool(poolInfo);
                                    poolAlgos.put(
                                            poolInfo.get("POOL"),
                                            poolInfo.getOrDefault(
                                                    "Algorithm",
                                                    ""));
                                }))
                .addRequest(
                        new CgMinerRequest.Builder()
                                .setCommand(CgMinerCommand.DEVS)
                                .build(),
                        new DevsResponseStrategy(
                                "Temperature",
                                poolAlgos,
                                context))
                .build();
    }
}
