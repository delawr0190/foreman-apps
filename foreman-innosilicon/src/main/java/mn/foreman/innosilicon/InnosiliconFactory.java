package mn.foreman.innosilicon;

import mn.foreman.cgminer.CgMiner;
import mn.foreman.cgminer.PoolsResponseStrategy;
import mn.foreman.cgminer.RateMultiplyingDecorator;
import mn.foreman.cgminer.ResponseStrategy;
import mn.foreman.cgminer.request.CgMinerCommand;
import mn.foreman.cgminer.request.CgMinerRequest;
import mn.foreman.model.Miner;
import mn.foreman.model.MinerFactory;

import java.util.Map;

/**
 * A {@link MinerFactory} implementation that parses a configuration and creates
 * a {@link Miner} that will query an Innosilicon.
 */
public class InnosiliconFactory
        implements MinerFactory {

    @Override
    public Miner create(final Map<String, String> config) {
        final ApiType apiType =
                ApiType.valueOf(config.get("type"));
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
                        createResponseStrategy(apiType))
                .build();
    }

    /**
     * Creates a chain of {@link RateMultiplyingDecorator decorators} around the
     * {@link StatsResponseStrategy} to properly convert all of the ASCIs.
     *
     * @param apiType The type.
     *
     * @return The strategy.
     */
    private static ResponseStrategy createResponseStrategy(
            final ApiType apiType) {
        ResponseStrategy responseStrategy =
                new StatsResponseStrategy();
        for (int i = 0; i < 20; i++) {
            responseStrategy =
                    new RateMultiplyingDecorator(
                            "STATS" + i,
                            "MHS av",
                            apiType.getMultiplier(),
                            responseStrategy);
        }
        return responseStrategy;
    }
}