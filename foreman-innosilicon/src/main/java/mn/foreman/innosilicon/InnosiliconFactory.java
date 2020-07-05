package mn.foreman.innosilicon;

import mn.foreman.cgminer.CgMiner;
import mn.foreman.cgminer.PoolsResponseStrategy;
import mn.foreman.cgminer.RateMultiplyingDecorator;
import mn.foreman.cgminer.ResponseStrategy;
import mn.foreman.cgminer.request.CgMinerCommand;
import mn.foreman.cgminer.request.CgMinerRequest;
import mn.foreman.innosilicon.response.SummaryAndStatsResponseStrategy;
import mn.foreman.model.Miner;
import mn.foreman.model.MinerFactory;

import java.util.Map;

/**
 * A {@link MinerFactory} implementation that parses a configuration and creates
 * a {@link Miner} that will query an Innosilicon.
 */
public class InnosiliconFactory
        implements MinerFactory {

    /** The api type. */
    private final ApiType apiType;

    /**
     * Constructor.
     *
     * @param apiType The api type.
     */
    public InnosiliconFactory(final ApiType apiType) {
        this.apiType = apiType;
    }

    @Override
    public Miner create(final Map<String, String> config) {
        final ResponseStrategy responseStrategy =
                createResponseStrategy(this.apiType);
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

    /**
     * Creates a chain of {@link RateMultiplyingDecorator decorators} around the
     * {@link SummaryAndStatsResponseStrategy} to properly convert all of the
     * ASCIs.
     *
     * @param apiType The type.
     *
     * @return The strategy.
     */
    private static ResponseStrategy createResponseStrategy(
            final ApiType apiType) {
        return new RateMultiplyingDecorator(
                "SUMMARY",
                "MHS av",
                apiType.getMultiplier(),
                new SummaryAndStatsResponseStrategy());
    }
}