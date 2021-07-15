package mn.foreman.ibelink;

import mn.foreman.cgminer.CgMiner;
import mn.foreman.cgminer.Context;
import mn.foreman.cgminer.ContextKey;
import mn.foreman.cgminer.request.CgMinerCommand;
import mn.foreman.cgminer.request.CgMinerRequest;
import mn.foreman.model.ApplicationConfiguration;
import mn.foreman.model.MacStrategy;
import mn.foreman.model.miners.asic.Asic;

import java.util.Collections;
import java.util.Optional;

/** A strategy for obtaining MAC addresses from iBeLink miners. */
public class IBeLinkMacStrategy
        implements MacStrategy {

    /** The configuration. */
    private final ApplicationConfiguration configuration;

    /** The IP. */
    private final String ip;

    /** The port. */
    private final int port;

    /**
     * Constructor.
     *
     * @param ip            The IP.
     * @param port          The port.
     * @param configuration The configuration.
     */
    public IBeLinkMacStrategy(
            final String ip,
            final int port,
            final ApplicationConfiguration configuration) {
        this.ip = ip;
        this.port = port;
        this.configuration = configuration;
    }

    @Override
    public Optional<String> getMacAddress() {
        final Context context = new Context();
        final CgMiner miner =
                new CgMiner.Builder(context, Collections.emptyList())
                        .setApiIp(this.ip)
                        .setApiPort(this.port)
                        .setConnectTimeout(
                                this.configuration.getReadSocketTimeout())
                        .addRequest(
                                new CgMinerRequest.Builder()
                                        .setCommand(CgMinerCommand.SUMMARY)
                                        .build(),
                                (builder, response) ->
                                        IBeLinkUtils.updateSummary(
                                                context,
                                                response.getValues(),
                                                new Asic.Builder()))
                        .build();

        try {
            miner.getStats();
        } catch (final Exception e) {
            // Ignore
        }

        return context.getSimple(ContextKey.MAC);
    }
}
