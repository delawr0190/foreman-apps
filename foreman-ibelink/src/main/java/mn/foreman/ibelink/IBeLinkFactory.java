package mn.foreman.ibelink;

import mn.foreman.cgminer.*;
import mn.foreman.cgminer.request.CgMinerCommand;
import mn.foreman.cgminer.request.CgMinerRequest;
import mn.foreman.model.ApplicationConfiguration;
import mn.foreman.model.Miner;

import com.google.common.collect.ImmutableMap;

import java.util.List;
import java.util.Map;

/**
 * A factory for creating {@link CgMiner cgminers} that will query an iBeLink
 * miner.
 */
public class IBeLinkFactory
        extends CgMinerFactory {

    /** The configuration. */
    private final ApplicationConfiguration configuration;

    /**
     * Constructor.
     *
     * @param configuration The configuration.
     */
    public IBeLinkFactory(final ApplicationConfiguration configuration) {
        this.configuration = configuration;
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
                                "DEVS",
                                (values, builder, context) ->
                                        IBeLinkUtils.updateDevs(
                                                values,
                                                builder),
                                "SUMMARY",
                                (values, builder, context) ->
                                        IBeLinkUtils.updateSummary(
                                                cgContext,
                                                values,
                                                builder)),
                        () -> null,
                        cgContext);

        return new CgMiner.Builder(cgContext, statsWhitelist)
                .setApiIp(apiIp)
                .setApiPort(apiPort)
                .setConnectTimeout(
                        this.configuration.getReadSocketTimeout())
                .addRequest(
                        new CgMinerRequest.Builder()
                                .setCommand(CgMinerCommand.POOLS)
                                .build(),
                        new PoolsResponseStrategy(
                                new MrrRigIdCallback(cgContext)))
                .addRequest(
                        new CgMinerRequest.Builder()
                                .setCommand(CgMinerCommand.DEVS)
                                .build(),
                        responseStrategy)
                .addRequest(
                        new CgMinerRequest.Builder()
                                .setCommand(CgMinerCommand.SUMMARY)
                                .build(),
                        responseStrategy)
                .setMacStrategy(
                        new IBeLinkMacStrategy(
                                apiIp,
                                Integer.parseInt(apiPort),
                                this.configuration))
                .build();
    }
}
