package mn.foreman.pickaxe.itest.miner;

import mn.foreman.pickaxe.itest.model.ExpectedMessages;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.json.JsonObjectDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * A {@link FakeCgMiner} provides a fake cgminer API interface that accepts
 * similarly formatted JSON requests and responds with predefined responses.
 *
 * <p>Similar to the cgminer API, this will terminate client connections
 * immediately after the response has been sent.</p>
 */
public class FakeCgMiner
        implements FakeMiner {

    /** The logger for this class. */
    private static final Logger LOG =
            LoggerFactory.getLogger(FakeCgMiner.class);

    /**
     * The expected messages (expected requests mapped to responses to send).
     */
    private final ExpectedMessages expectedMessages;

    /** Whether or not errors were observed (ex: unexpected request). */
    private final AtomicBoolean hasErrors = new AtomicBoolean(false);

    /** The port for accepting connections. */
    private final int port;

    /** The thread pool to use for running the server. */
    private final Executor threadPool;

    /** The bound {@link Channel}. */
    private Channel channel;

    /**
     * Constructor.
     *
     * @param port             The port for accepting connections.
     * @param expectedMessages The expected messages.
     * @param threadPool       The thread pool for running the server.
     */
    public FakeCgMiner(
            final int port,
            final ExpectedMessages expectedMessages,
            final Executor threadPool) {
        this.port = port;
        this.expectedMessages = expectedMessages;
        this.threadPool = threadPool;
    }

    @Override
    public void close() {
        stop();
    }

    @Override
    public boolean start() {
        final CountDownLatch startLatch = new CountDownLatch(1);

        this.threadPool.execute(() -> {
            final EventLoopGroup bossGroup = new NioEventLoopGroup(1);
            final EventLoopGroup workerGroup = new NioEventLoopGroup(1);
            try {
                final ServerBootstrap bootstrap = new ServerBootstrap();
                bootstrap
                        .group(bossGroup, workerGroup)
                        .channel(NioServerSocketChannel.class)
                        .childHandler(new ChannelInitializer<SocketChannel>() {

                            @Override
                            protected void initChannel(
                                    final SocketChannel channel) {
                                channel.pipeline()
                                        .addLast(new StringEncoder())
                                        .addLast(new JsonObjectDecoder())
                                        .addLast(new StringDecoder())
                                        .addLast(new ResponseHandler());
                            }
                        })
                        .option(ChannelOption.SO_BACKLOG, 128)
                        .childOption(ChannelOption.SO_KEEPALIVE, true);

                final ChannelFuture channelFuture =
                        bootstrap.bind(FakeCgMiner.this.port).sync();

                startLatch.countDown();

                this.channel = channelFuture.channel();
                this.channel.closeFuture().sync();
            } catch (final InterruptedException ie) {
                LOG.warn("Exception occurred while running", ie);
            } finally {
                workerGroup.shutdownGracefully();
                bossGroup.shutdownGracefully();
            }
        });

        boolean started = false;
        try {
            started = startLatch.await(5, TimeUnit.SECONDS);
        } catch (final InterruptedException ie) {
            LOG.warn("Exception occurred while starting", ie);
        }

        return started;
    }

    @Override
    public void stop() {
        if (this.channel != null) {
            this.channel.close();
        }
    }

    /** A custom handler for responding to expected requests. */
    private class ResponseHandler
            extends ChannelInboundHandlerAdapter {

        @Override
        public void channelRead(
                final ChannelHandlerContext context,
                final Object message) {
            try {
                final Optional<String> responseOptional =
                        FakeCgMiner.this.expectedMessages.getResponse(
                                (String) message);
                if (responseOptional.isPresent()) {
                    context.writeAndFlush(responseOptional.get()).sync();
                } else {
                    LOG.warn("Received a request that contained no " +
                            "corresponding response");
                    FakeCgMiner.this.hasErrors.set(true);
                }
            } catch (final InterruptedException e) {
                LOG.warn("Exception occurred while responding", e);
            } finally {
                context.close();
            }
        }

        @Override
        public void exceptionCaught(
                final ChannelHandlerContext context,
                final Throwable cause)
                throws Exception {
            super.exceptionCaught(context, cause);
        }
    }
}