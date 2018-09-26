package mn.foreman.io;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.handler.codec.json.JsonObjectDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.util.Arrays;

/**
 * A {@link ConnectionFactory} provides a factory to creating {@link Connection
 * connections} to remote miner APIs.
 */
public class ConnectionFactory {

    /** A default thread pool. */
    private static final EventLoopGroup DEFAULT_GROUP =
            new NioEventLoopGroup();

    /**
     * Constructor.
     *
     * <p>Note: intentionally hidden.</p>
     */
    private ConnectionFactory() {
        // Do nothing
    }

    /**
     * Creates a {@link Connection} to a miner that accepts RPC calls that are
     * delimiter based.
     *
     * @param request The request.
     *
     * @return The new {@link Connection}.
     */
    public static Connection createDelimiterConnection(
            final ApiRequest request) {
        final StringBuilder stringBuilder = new StringBuilder();
        return new SimpleApiConnection(
                request.getIp(),
                request.getPort(),
                request.getRequest(),
                Arrays.asList(
                        new StringEncoder(),
                        new StringDecoder(),
                        new ChannelInboundHandlerAdapter() {

                            @Override
                            public void channelInactive(
                                    final ChannelHandlerContext context) {
                                request.setResponse(stringBuilder.toString());
                                request.completed();
                            }

                            @Override
                            public void channelRead(
                                    final ChannelHandlerContext context,
                                    final Object msg) {
                                stringBuilder.append((String) msg);
                            }

                            @Override
                            public void exceptionCaught(
                                    final ChannelHandlerContext context,
                                    final Throwable cause) {
                                context.close();
                            }
                        }),
                DEFAULT_GROUP);
    }

    /**
     * Creates a {@link Connection} to a miner that accepts RPC calls that are
     * JSON based.
     *
     * @param request The request.
     *
     * @return The new {@link Connection}.
     */
    public static Connection createJsonConnection(
            final ApiRequest request) {
        return new SimpleApiConnection(
                request.getIp(),
                request.getPort(),
                request.getRequest(),
                Arrays.asList(
                        new StringEncoder(),
                        new JsonObjectDecoder(),
                        new StringDecoder(),
                        new ChannelInboundHandlerAdapter() {

                            @Override
                            public void channelInactive(
                                    final ChannelHandlerContext context) {
                                request.completed();
                            }

                            @Override
                            public void channelRead(
                                    final ChannelHandlerContext context,
                                    final Object msg) {
                                request.setResponse((String) msg);
                                context.close();
                            }

                            @Override
                            public void exceptionCaught(
                                    final ChannelHandlerContext context,
                                    final Throwable cause) {
                                context.close();
                            }
                        }),
                DEFAULT_GROUP);
    }

    /**
     * Creates a {@link Connection} to a miner that has a REST interface.
     *
     * @param request The request.
     * @param method  The method.
     *
     * @return The new {@link Connection}.
     */
    public static Connection createRestConnection(
            final ApiRequest request,
            final String method) {
        return new RestConnection(
                String.format(
                        "http://%s:%d%s",
                        request.getIp(),
                        request.getPort(),
                        request.getRequest()),
                method,
                request);
    }
}