package mn.foreman.cgminer.connection;

import mn.foreman.cgminer.Connection;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.json.JsonObjectDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 * A {@link NettyConnection} provides a synchronous connection to the cgminer
 * API.
 *
 * <p>This class is thread safe.  No state is maintained across invocations of
 * {@link #query(String)}, and every request is blocking (invoking {@link
 * #query(String)} will hang until the response is received from cgminer).</p>
 *
 * <p>This class was implemented under the assumption that the cgminer API will
 * terminate the socket as soon as a response is sent.  If that guarantee ever
 * changes, this class will need to be revisited.</p>
 */
public class NettyConnection
        implements Connection {

    /** The logger for this class. */
    private static final Logger LOG =
            LoggerFactory.getLogger(NettyConnection.class);

    /** The thread pool for performing socket operations. */
    private final EventLoopGroup eventLoopGroup;

    /** The API IP address. */
    private final String ip;

    /** The API port. */
    private final int port;

    /**
     * Constructor.
     *
     * @param ip             The API IP address.
     * @param port           The API port.
     * @param eventLoopGroup The {@link EventLoopGroup}.
     */
    NettyConnection(
            final String ip,
            final int port,
            final EventLoopGroup eventLoopGroup) {
        Validate.notEmpty(
                ip,
                "API IP address cannot be empty");
        Validate.inclusiveBetween(
                0, Integer.MAX_VALUE, port,
                "API port must be positive");
        Validate.notNull(
                eventLoopGroup,
                "Thread pool cannot be null");
        this.ip = ip;
        this.port = port;
        this.eventLoopGroup = eventLoopGroup;
    }

    @Override
    public Optional<String> query(final String request) {
        final AtomicReference<String> response = new AtomicReference<>();

        final Bootstrap bootstrap = new Bootstrap();
        bootstrap
                .group(this.eventLoopGroup)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS,
                        (int) TimeUnit.SECONDS.toMillis(1))
                .handler(new ChannelInitializer<SocketChannel>() {

                    @Override
                    protected void initChannel(final SocketChannel channel) {
                        channel.pipeline()
                                .addLast(new StringEncoder())
                                .addLast(new JsonObjectDecoder())
                                .addLast(new StringDecoder())
                                .addLast(new ChannelInboundHandlerAdapter() {

                                    @Override
                                    public void channelRead(
                                            final ChannelHandlerContext context,
                                            final Object message) {
                                        response.set((String) message);
                                    }

                                    @Override
                                    public void exceptionCaught(
                                            final ChannelHandlerContext context,
                                            final Throwable cause)
                                            throws Exception {
                                        super.exceptionCaught(context, cause);
                                    }
                                });
                    }
                });

        try {
            final Channel channel =
                    bootstrap.connect(this.ip, this.port).sync().channel();

            // Send the request
            channel.writeAndFlush(request).sync();

            // Wait until the connection is closed (according to the cgminer
            // API-README, the connection will be closed immediately).
            channel.closeFuture().sync();
        } catch (final InterruptedException ie) {
            LOG.warn("Exception occurred while communicating with {}:{}",
                    this.ip,
                    this.port,
                    ie);
        }

        return Optional.ofNullable(response.get());
    }
}