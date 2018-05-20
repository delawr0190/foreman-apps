package mn.foreman.io;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.handler.codec.json.JsonObjectDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.util.Arrays;

public class ConnectionFactory {

    /** A default thread pool. */
    private static final EventLoopGroup DEFAULT_GROUP =
            new NioEventLoopGroup(1);

    /**
     * Constructor.
     *
     * <p>Note: intentionally hidden.</p>
     */
    private ConnectionFactory() {
        // Do nothing
    }

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
}
