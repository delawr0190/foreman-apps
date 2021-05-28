package mn.foreman.model;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/** The current Pickaxe configuration. */
public class ApplicationConfiguration {

    /** The read socket configuration. */
    private final AtomicReference<SocketConfig> readSocketConfig =
            new AtomicReference<>(
                    new SocketConfig(
                            1,
                            TimeUnit.SECONDS));

    /** The write socket configuration. */
    private final AtomicReference<SocketConfig> writeSocketConfig =
            new AtomicReference<>(
                    new SocketConfig(
                            1,
                            TimeUnit.SECONDS));

    /**
     * Returns the socket configuration.
     *
     * @return The socket configuration.
     */
    public SocketConfig getReadSocketTimeout() {
        return this.readSocketConfig.get();
    }

    /**
     * Returns the write socket configuration.
     *
     * @return The write socket configuration.
     */
    public SocketConfig getWriteSocketTimeout() {
        return this.writeSocketConfig.get();
    }

    /**
     * Sets the read socket timeout.
     *
     * @param socketTimeout      The socket timeout.
     * @param socketTimeoutUnits The socket timeout (units).
     */
    public void setReadSocketTimeout(
            final int socketTimeout,
            final TimeUnit socketTimeoutUnits) {
        this.readSocketConfig.set(
                new SocketConfig(
                        socketTimeout,
                        socketTimeoutUnits));
    }

    /**
     * Sets the write socket timeout.
     *
     * @param socketTimeout      The socket timeout.
     * @param socketTimeoutUnits The socket timeout (units).
     */
    public void setWriteSocketTimeout(
            final int socketTimeout,
            final TimeUnit socketTimeoutUnits) {
        this.writeSocketConfig.set(
                new SocketConfig(
                        socketTimeout,
                        socketTimeoutUnits));
    }

    @Override
    public String toString() {
        return String.format(
                "%s [ " +
                        "readConfig=%s, " +
                        "writeConfig=%s" +
                        " ]",
                getClass().getSimpleName(),
                this.readSocketConfig,
                this.writeSocketConfig);
    }

    /** A socket configuration. */
    public static class SocketConfig {

        /** The socket timeout. */
        private final int socketTimeout;

        /** The socket timeout (units). */
        private final TimeUnit socketTimeoutUnits;

        /**
         * Constructor.
         *
         * @param socketTimeout      The socket timeout.
         * @param socketTimeoutUnits The socket timeout (units).
         */
        SocketConfig(
                final int socketTimeout,
                final TimeUnit socketTimeoutUnits) {
            this.socketTimeout = socketTimeout;
            this.socketTimeoutUnits = socketTimeoutUnits;
        }

        /**
         * Returns the socket timeout.
         *
         * @return The socket timeout.
         */
        public int getSocketTimeout() {
            return this.socketTimeout;
        }

        /**
         * Returns the socket timeout (units).
         *
         * @return The socket timeout (units).
         */
        public TimeUnit getSocketTimeoutUnits() {
            return this.socketTimeoutUnits;
        }

        @Override
        public String toString() {
            return String.format(
                    "%s [ " +
                            "socketTimeout=%s, " +
                            "socketTimeoutUnits=%s" +
                            " ]",
                    getClass().getSimpleName(),
                    this.socketTimeout,
                    this.socketTimeoutUnits);
        }
    }
}
