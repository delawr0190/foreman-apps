package mn.foreman.cgminer.connection;

import mn.foreman.cgminer.Connection;
import mn.foreman.cgminer.ConnectionFactory;

import io.netty.channel.nio.NioEventLoopGroup;

/**
 * A {@link ConnectionFactory} for creating {@link NettyConnection netty-based
 * connections}.
 */
public class NettyConnectionFactory
        implements ConnectionFactory {

    /**
     * {@inheritDoc}
     *
     * <p>Overridden to create a new {@link NettyConnection}.</p>
     */
    @Override
    public Connection create(
            final String ip,
            final int port) {
        return new NettyConnection(
                ip,
                port,
                new NioEventLoopGroup(1));
    }
}