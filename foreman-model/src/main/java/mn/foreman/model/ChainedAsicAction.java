package mn.foreman.model;

import mn.foreman.model.error.MinerException;
import mn.foreman.model.error.NotAuthenticatedException;

import java.util.Map;

/**
 * A {@link ChainedAsicAction} provides an {@link AsicAction} that will
 * initially invoke the {@link #primary} before invoking the {@link
 * #secondary}.
 */
public class ChainedAsicAction
        implements AsicAction {

    /** The primary action. */
    private final AsicAction primary;

    /** The secondary action. */
    private final AsicAction secondary;

    /**
     * Constructor.
     *
     * @param primary   The primary action.
     * @param secondary The secondary action.
     */
    public ChainedAsicAction(
            final AsicAction primary,
            final AsicAction secondary) {
        this.primary = primary;
        this.secondary = secondary;
    }

    @Override
    public void runAction(
            final String ip,
            final int port,
            final Map<String, Object> args,
            final CompletionCallback initial)
            throws NotAuthenticatedException, MinerException {
        final CompletionCallback chainedCallback =
                new ChainedCallback(
                        ip,
                        port,
                        args,
                        initial);
        this.primary.runAction(
                ip,
                port,
                args,
                chainedCallback);
    }

    /**
     * A {@link CompletionCallback} for delegating through to the { @link
     * #secondary}.
     */
    private class ChainedCallback
            implements CompletionCallback {

        /** The args. */
        private final Map<String, Object> args;

        /** The initial callback. */
        private final CompletionCallback initial;

        /** The IP. */
        private final String ip;

        /** The port. */
        private final int port;

        /**
         * Constructor.
         *
         * @param ip      The IP.
         * @param port    The port.
         * @param args    The args.
         * @param initial The initial callback.
         */
        private ChainedCallback(
                final String ip,
                final int port,
                final Map<String, Object> args,
                final CompletionCallback initial) {
            this.ip = ip;
            this.port = port;
            this.args = args;
            this.initial = initial;
        }

        @Override
        public void failed(final String message) {
            this.initial.failed(message);
        }

        @Override
        public void success() {
            try {
                ChainedAsicAction.this.secondary.runAction(
                        this.ip,
                        this.port,
                        this.args,
                        this.initial);
            } catch (final Exception e) {
                this.initial.failed(e.getMessage());
            }
        }
    }
}
