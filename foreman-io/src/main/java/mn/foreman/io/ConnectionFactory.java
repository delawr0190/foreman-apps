package mn.foreman.io;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.handler.codec.json.JsonObjectDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import org.apache.http.client.CookieStore;
import org.apache.http.impl.client.BasicCookieStore;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

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
     * @param request             The request.
     * @param connectTimeout      The connection timeout.
     * @param connectTimeoutUnits The connection timeout (units).
     *
     * @return The new {@link Connection}.
     */
    public static Connection createDelimiterConnection(
            final ApiRequest request,
            final int connectTimeout,
            final TimeUnit connectTimeoutUnits) {
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
                DEFAULT_GROUP,
                connectTimeout,
                connectTimeoutUnits);
    }

    /**
     * Creates a {@link Connection} to a miner that accepts RPC calls that are
     * JSON based.
     *
     * @param request             The request.
     * @param connectTimeout      The connection timeout.
     * @param connectTimeoutUnits The connection timeout units.
     *
     * @return The new {@link Connection}.
     */
    public static Connection createJsonConnection(
            final ApiRequest request,
            final int connectTimeout,
            final TimeUnit connectTimeoutUnits) {
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
                                request.completed();
                                context.close();
                            }
                        }),
                DEFAULT_GROUP,
                connectTimeout,
                connectTimeoutUnits);
    }

    /**
     * Creates a raw java socket connection.
     *
     * @param request             The request.
     * @param connectTimeout      The connect timeout.
     * @param connectTimeoutUnits The connect timeout (units).
     *
     * @return The connection.
     */
    public static Connection createRawConnection(
            final ApiRequest request,
            final int connectTimeout,
            final TimeUnit connectTimeoutUnits) {
        return new SocketApiConnection(
                request,
                connectTimeout,
                connectTimeoutUnits);
    }

    /**
     * Creates a {@link Connection} to a miner that has a REST interface.
     *
     * @param request             The request.
     * @param method              The method.
     * @param connectTimeout      The connection timeout.
     * @param connectTimeoutUnits The connection timeout units.
     * @param cookieStore         The cookie store.
     *
     * @return The new {@link Connection}.
     */
    public static Connection createRestConnection(
            final ApiRequest request,
            final String method,
            final int connectTimeout,
            final TimeUnit connectTimeoutUnits,
            final CookieStore cookieStore) {
        return new RestConnection(
                String.format(
                        "http://%s:%d%s",
                        request.getIp(),
                        request.getPort(),
                        request.getRequest()),
                method,
                request,
                connectTimeout,
                connectTimeoutUnits,
                cookieStore);
    }

    /**
     * Creates a {@link Connection} to a miner that has a REST interface.
     *
     * @param request             The request.
     * @param method              The method.
     * @param connectTimeout      The connection timeout.
     * @param connectTimeoutUnits The connection timeout units.
     *
     * @return The new {@link Connection}.
     */
    public static Connection createRestConnection(
            final ApiRequest request,
            final String method,
            final int connectTimeout,
            final TimeUnit connectTimeoutUnits) {
        return createRestConnection(
                request,
                method,
                connectTimeout,
                connectTimeoutUnits,
                new BasicCookieStore());
    }
}