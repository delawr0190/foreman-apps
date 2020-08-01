package mn.foreman.pickaxe.command.asic.reboot;

import mn.foreman.api.ForemanApi;
import mn.foreman.model.command.CommandDone;
import mn.foreman.model.command.CommandStart;
import mn.foreman.model.command.DoneStatus;
import mn.foreman.model.error.MinerException;
import mn.foreman.model.error.NotAuthenticatedException;
import mn.foreman.pickaxe.command.CommandStrategy;
import mn.foreman.pickaxe.command.asic.Manufacturer;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Optional;

import static mn.foreman.pickaxe.command.util.CommandUtils.safeGet;

/** A {@link CommandStrategy} that will perform a miner reboot. */
public class RebootStrategy
        implements CommandStrategy {

    /** The logger for this class. */
    private static final Logger LOG =
            LoggerFactory.getLogger(RebootStrategy.class);

    @Override
    public void runCommand(
            final CommandStart command,
            final ForemanApi foremanApi,
            final CommandDone.CommandDoneBuilder builder,
            final Callback callback) {
        final Map<String, Object> args = command.args;

        final String type = safeGet(args, "type");
        final String ip = safeGet(args, "ip");
        final String port = safeGet(args, "port");

        switch (type) {
            case "asic":
                runAsicReboot(
                        ip,
                        port,
                        args,
                        builder,
                        callback);
                break;
            default:
                break;
        }
    }

    /**
     * Reboots ASICs.
     *
     * @param ip       The ip.
     * @param port     The port.
     * @param args     The arguments.
     * @param builder  The builder to use for creating the final result.
     * @param callback The completion callback.
     */
    private void runAsicReboot(
            final String ip,
            final String port,
            final Map<String, Object> args,
            final CommandDone.CommandDoneBuilder builder,
            final Callback callback) {
        final String manufacturer = safeGet(args, "manufacturer");
        final Optional<Manufacturer> type =
                Manufacturer.fromName(manufacturer);
        if (type.isPresent()) {
            final Manufacturer manufacturerType =
                    type.get();
            final mn.foreman.model.RebootStrategy strategy =
                    manufacturerType.getRebootStrategy();

            LOG.info("Rebooting {} ({}:{})",
                    manufacturer,
                    ip,
                    port);

            try {
                final RebootCallback rebootCallback =
                        new RebootCallback(
                                builder,
                                callback);
                strategy.reboot(
                        ip,
                        Integer.parseInt(port),
                        args,
                        rebootCallback);
            } catch (final NotAuthenticatedException nae) {
                LOG.warn("Failed to authentication with the miner", nae);
                callback.done(
                        builder
                                .status(
                                        CommandDone.Status
                                                .builder()
                                                .type(DoneStatus.FAILED)
                                                .message("Failed to authenticate with the miner")
                                                .details(ExceptionUtils.getStackTrace(nae))
                                                .build())
                                .build());
            } catch (final MinerException me) {
                LOG.warn("An unexpected exception occurred", me);
                callback.done(
                        builder
                                .status(
                                        CommandDone.Status
                                                .builder()
                                                .type(DoneStatus.FAILED)
                                                .message("An unexpected exception occurred")
                                                .details(ExceptionUtils.getStackTrace(me))
                                                .build())
                                .build());
            }
        }
    }

    /** A callback for handling async reboot operations. */
    private static class RebootCallback
            implements mn.foreman.model.RebootStrategy.Callback {

        /** The done builder. */
        private final CommandDone.CommandDoneBuilder builder;

        /** The callback. */
        private final Callback callback;

        /**
         * Constructor.
         *
         * @param builder  The builder.
         * @param callback The callback.
         */
        RebootCallback(
                final CommandDone.CommandDoneBuilder builder,
                final Callback callback) {
            this.builder = builder;
            this.callback = callback;
        }

        @Override
        public void failed(final String message) {
            LOG.warn("Failed to reboot");
            this.callback.done(
                    this.builder
                            .status(
                                    CommandDone.Status
                                            .builder()
                                            .type(DoneStatus.FAILED)
                                            .message(message)
                                            .build())
                            .build());
        }

        @Override
        public void success() {
            LOG.info("Reboot completed!");
            this.callback.done(
                    this.builder
                            .status(
                                    CommandDone.Status
                                            .builder()
                                            .type(DoneStatus.SUCCESS)
                                            .build())
                            .build());
        }
    }
}