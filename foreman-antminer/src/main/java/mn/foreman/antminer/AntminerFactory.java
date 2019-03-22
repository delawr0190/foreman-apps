package mn.foreman.antminer;

import mn.foreman.antminer.response.StatsResponseStrategy;
import mn.foreman.cgminer.CgMiner;
import mn.foreman.cgminer.PoolsResponseStrategy;
import mn.foreman.cgminer.RateMultiplyingDecorator;
import mn.foreman.cgminer.request.CgMinerCommand;
import mn.foreman.cgminer.request.CgMinerRequest;
import mn.foreman.model.Miner;
import mn.foreman.model.MinerFactory;

import java.util.Map;

/**
 * A {@link MinerFactory} implementation that parses a configuration and creates
 * a {@link Miner} that will query an Antminer.
 */
public class AntminerFactory
        implements MinerFactory {

    /** The miner type. */
    private final AntminerType type;

    /**
     * Constructor.
     *
     * @param type The miner type.
     */
    public AntminerFactory(final AntminerType type) {
        this.type = type;
    }

    @Override
    public Miner create(final Map<String, String> config) {
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
                                .setCommand(CgMinerCommand.STATS)
                                .build(),
                        new RateMultiplyingDecorator(
                                "STATS",
                                "GHS av",
                                type.getMultiplier(),
                                new RateMultiplyingDecorator(
                                        "STATS",
                                        "GHS 5s",
                                        type.getMultiplier(),
                                        new StatsResponseStrategy())))
                .build();
    }
}