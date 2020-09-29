package mn.foreman.avalon;

import mn.foreman.cgminer.*;
import mn.foreman.cgminer.request.CgMinerCommand;
import mn.foreman.cgminer.request.CgMinerRequest;
import mn.foreman.model.Miner;
import mn.foreman.model.MinerFactory;
import mn.foreman.model.SkipOnFailMiner;

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

    /** The maximum number of interations to skip when failing. */
    private final int maxSkipCount;

    /** Constructor. */
    public AvalonFactory() {
        this(10);
    }

    /**
     * Constructor.
     *
     * @param maxSkipCount The maximum number of iterations to skip when
     *                     failing.
     */
    public AvalonFactory(final int maxSkipCount) {
        this.maxSkipCount = maxSkipCount;
    }

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
        return new SkipOnFailMiner(
                new CgMiner.Builder(cgContext, statsWhitelist)
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
                        .build(),
                this.maxSkipCount);
    }
}