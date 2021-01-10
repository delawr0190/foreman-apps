package mn.foreman.discover;

import mn.foreman.cgminer.request.CgMinerCommand;
import mn.foreman.io.ApiRequest;
import mn.foreman.io.ApiRequestImpl;
import mn.foreman.io.Connection;
import mn.foreman.io.ConnectionFactory;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * A {@link CgminerDiscoverStrategy} provides a {@link DiscoverStrategy}
 * implementation that obtains responses from a running cgminer without using
 * the cgminer miner to guarantee that responses are obtained.
 */
public class CgminerDiscoverStrategy
        implements DiscoverStrategy {

    /** The logger for this class. */
    private static final Logger LOG =
            LoggerFactory.getLogger(CgminerDiscoverStrategy.class);

    /**
     * {@inheritDoc}
     *
     * <p>Note: intentionally don't use our cgminer code so we are guaranteed
     * to always get a raw response.</p>
     */
    @Override
    public List<Discovery> discover(
            final String ip,
            final int port) {
        final List<Discovery> discoveries = new LinkedList<>();

        for (final CgMinerCommand command : CgMinerCommand.values()) {
            final String query =
                    String.format(
                            "{\"command\":\"%s\"}",
                            command.getCommand());

            final Discovery.DiscoveryBuilder builder =
                    Discovery
                            .builder()
                            .query(query);

            try {
                final ApiRequest request =
                        new ApiRequestImpl(
                                ip,
                                port,
                                query);
                final Connection connection =
                        ConnectionFactory.createJsonConnection(
                                request,
                                1,
                                TimeUnit.SECONDS);
                connection.query();

                if (request.waitForCompletion(
                        10,
                        TimeUnit.SECONDS)) {
                    final String response = request.getResponse();
                    builder
                            .success(true)
                            .response(response != null ? response : "");
                } else {
                    LOG.info("Failed to connect to {}:{}", ip, port);
                    builder
                            .success(false)
                            .response(
                                    String.format(
                                            "Failed to connect to %s:%d",
                                            ip,
                                            port));
                }
            } catch (final Exception e) {
                LOG.info("Exception occurred while querying", e);
                builder
                        .success(false)
                        .response(ExceptionUtils.getStackTrace(e));
            }

            discoveries.add(builder.build());
        }

        return discoveries;
    }
}
