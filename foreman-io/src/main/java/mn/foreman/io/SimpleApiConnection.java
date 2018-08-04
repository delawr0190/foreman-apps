package mn.foreman.io;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * A {@link SimpleApiConnection} provides a blocking, Netty-based connection to
 * a remote miner instance.
 *
 * <p>{@link #query()} will block until the response has been fully received and
 * the connection is terminated.</p>
 */
public class SimpleApiConnection
        implements Connection {

    /** The logger for this class. */
    private static final Logger LOG =
            LoggerFactory.getLogger(SimpleApiConnection.class);

    /** The event group. */
    private final EventLoopGroup eventLoopGroup;

    /** The channel handlers. */
    private final List<ChannelHandler> handlers;

    /** The ip. */
    private final String ip;

    /** The port. */
    private final int port;

    /** The request. */
    private final String request;

    /**
     * Constructor.
     *
     * @param ip             The IP.
     * @param port           The port.
     * @param request        The request to send.
     * @param handlers       The handlers.
     * @param eventLoopGroup The event group.
     */
    SimpleApiConnection(
            final String ip,
            final int port,
            final String request,
            final List<ChannelHandler> handlers,
            final EventLoopGroup eventLoopGroup) {
        Validate.notNull(
                ip,
                "IP cannot be null");
        Validate.notEmpty(
                ip,
                "IP cannot be empty");
        Validate.isTrue(
                port > 0,
                "Port must be > 0");
        Validate.notNull(
                request,
                "Request cannot be null");
        Validate.notNull(
                handlers,
                "Channel handlers cannot be null");
        Validate.notNull(
                eventLoopGroup,
                "Event group cannot be null");
        this.ip = ip;
        this.port = port;
        this.request = request;
        this.handlers = new ArrayList<>(handlers);
        this.eventLoopGroup = eventLoopGroup;
    }

    @Override
    public void query() {
        final Bootstrap bootstrap = new Bootstrap();
        bootstrap
                .group(this.eventLoopGroup)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS,
                        (int) TimeUnit.SECONDS.toMillis(5))
                .handler(new ChannelInitializer<SocketChannel>() {

                    @Override
                    protected void initChannel(final SocketChannel channel) {
                        SimpleApiConnection.this.handlers
                                .forEach((handler) ->
                                        channel.pipeline().addLast(handler));
                    }
                });

        try {
            final Channel channel =
                    bootstrap.connect(
                            this.ip,
                            this.port).sync().channel();

            // Send the request
            if (!this.request.isEmpty()) {
                channel.writeAndFlush(this.request).sync();
            }

            // Wait until the connection is closed - should be closed immediately
            channel.closeFuture().sync();
        } catch (final InterruptedException ie) {
            LOG.warn("Exception occurred while communicating with {}:{}",
                    this.ip,
                    this.port,
                    ie);
        }
    }
}