package mn.foreman.io;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.ReadTimeoutHandler;
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
 * <p>{@link #query()} will block until the response has been fully received
 * and the connection is terminated.</p>
 */
public class SimpleApiConnection
        implements Connection {

    /** The logger for this class. */
    private static final Logger LOG =
            LoggerFactory.getLogger(SimpleApiConnection.class);

    /** The connection timeout. */
    private final int connectTimeout;

    /** The connection timeout units. */
    private final TimeUnit connectTimeoutUnits;

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
     * @param ip                  The IP.
     * @param port                The port.
     * @param request             The request to send.
     * @param handlers            The handlers.
     * @param eventLoopGroup      The event group.
     * @param connectTimeout      The connection timeout.
     * @param connectTimeoutUnits The connection timeout units.
     */
    SimpleApiConnection(
            final String ip,
            final int port,
            final String request,
            final List<ChannelHandler> handlers,
            final EventLoopGroup eventLoopGroup,
            final int connectTimeout,
            final TimeUnit connectTimeoutUnits) {
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
        Validate.isTrue(
                connectTimeout >= 0,
                "connectTimeout must be >= 0");
        Validate.notNull(
                connectTimeoutUnits,
                "connectTimeoutUnits cannot be null");
        this.ip = ip;
        this.port = port;
        this.request = request;
        this.handlers = new ArrayList<>(handlers);
        this.eventLoopGroup = eventLoopGroup;
        this.connectTimeout = connectTimeout;
        this.connectTimeoutUnits = connectTimeoutUnits;
    }

    @Override
    public void query() {
        final int connectTimeoutMillis =
                (int) this.connectTimeoutUnits.toMillis(
                        this.connectTimeout);
        final Bootstrap bootstrap = new Bootstrap();
        bootstrap
                .group(this.eventLoopGroup)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS,
                        connectTimeoutMillis)
                .handler(new ChannelInitializer<SocketChannel>() {

                    @Override
                    protected void initChannel(final SocketChannel channel) {
                        channel.pipeline().addLast(
                                new ReadTimeoutHandler(
                                        SimpleApiConnection.this.connectTimeout,
                                        SimpleApiConnection.this.connectTimeoutUnits));
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

            // Wait until the connection is closed - should be closed
            // immediately
            channel.closeFuture().sync();
        } catch (final InterruptedException ie) {
            LOG.debug("Exception occurred while communicating with {}:{}",
                    this.ip,
                    this.port,
                    ie);
        }
    }
}