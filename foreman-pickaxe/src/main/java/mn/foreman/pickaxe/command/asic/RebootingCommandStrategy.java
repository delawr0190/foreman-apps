package mn.foreman.pickaxe.command.asic;

import mn.foreman.api.model.CommandDone;
import mn.foreman.api.model.CommandStart;
import mn.foreman.api.model.DoneStatus;
import mn.foreman.model.AsicAction;
import mn.foreman.model.CompletionCallback;
import mn.foreman.model.error.MinerException;
import mn.foreman.model.error.NotAuthenticatedException;
import mn.foreman.pickaxe.command.CommandCompletionCallback;
import mn.foreman.pickaxe.command.CommandStrategy;
import mn.foreman.pickaxe.command.PostCommandProcessor;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import static mn.foreman.pickaxe.command.util.CommandUtils.safeGet;

/**
 * A {@link RebootingCommandStrategy} provides a {@link CommandStrategy}
 * implementation that will require a reboot to the miner.
 */
public class RebootingCommandStrategy
        implements CommandStrategy {

    /** The logger for this class. */
    private static final Logger LOG =
            LoggerFactory.getLogger(RebootingCommandStrategy.class);

    /** The processor for what to do one the action is completed. */
    private final PostCommandProcessor postProcessor;

    /** The supplier for the action to perform. */
    private final Function<Manufacturer, AsicAction> supplier;

    /**
     * Constructor.
     *
     * @param postProcessor The post processor.
     * @param supplier      The supplier.
     */
    RebootingCommandStrategy(
            final PostCommandProcessor postProcessor,
            final Function<Manufacturer, AsicAction> supplier) {
        this.postProcessor = postProcessor;
        this.supplier = supplier;
    }

    @Override
    public void runCommand(
            final CommandStart start,
            final CommandCompletionCallback commandCompletionCallback,
            final CommandDone.CommandDoneBuilder builder) {
        final Map<String, Object> args = start.args;

        final String type = safeGet(args, "type");
        final String ip = safeGet(args, "ip");
        final String port = safeGet(args, "port");

        switch (type) {
            case "asic":
                runAsicAction(
                        ip,
                        port,
                        start,
                        args,
                        builder,
                        commandCompletionCallback);
                break;
            default:
                break;
        }
    }

    /**
     * Performs the ASIC action.
     *
     * @param ip                        The ip.
     * @param port                      The port.
     * @param start                     The start command.
     * @param args                      The arguments.
     * @param builder                   The builder to use for creating the
     *                                  final result.
     * @param commandCompletionCallback The completion callback.
     */
    private void runAsicAction(
            final String ip,
            final String port,
            final CommandStart start,
            final Map<String, Object> args,
            final CommandDone.CommandDoneBuilder builder,
            final CommandCompletionCallback commandCompletionCallback) {
        final String manufacturer = safeGet(args, "manufacturer");
        final Optional<Manufacturer> type =
                Manufacturer.fromName(manufacturer);
        if (type.isPresent()) {
            final Manufacturer manufacturerType =
                    type.get();

            LOG.info("Running action {} for {} ({}:{})",
                    start,
                    manufacturer,
                    ip,
                    port);

            try {
                final CompletionCallback completionCallback =
                        new CallbackImpl(
                                start,
                                builder,
                                commandCompletionCallback,
                                this.postProcessor);
                final AsicAction asicAction =
                        this.supplier.apply(manufacturerType);
                asicAction.runAction(
                        ip,
                        Integer.parseInt(port),
                        args,
                        completionCallback);
            } catch (final NotAuthenticatedException nae) {
                LOG.warn("Failed to authentication with the miner", nae);
                commandCompletionCallback.done(
                        start.id,
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
                commandCompletionCallback.done(
                        start.id,
                        builder
                                .status(
                                        CommandDone.Status
                                                .builder()
                                                .type(DoneStatus.FAILED)
                                                .message(me.getMessage())
                                                .details(ExceptionUtils.getStackTrace(me))
                                                .build())
                                .build());
            }
        }
    }

    /** A callback for handling async reboot operations. */
    private static class CallbackImpl
            implements CompletionCallback {

        /** The done builder. */
        private final CommandDone.CommandDoneBuilder builder;

        /** The callback. */
        private final CommandCompletionCallback commandCompletionCallback;

        /** The process for post command completion. */
        private final PostCommandProcessor postProcessor;

        /** The start command. */
        private final CommandStart start;

        /**
         * Constructor.
         *
         * @param start                     The start.
         * @param builder                   The builder.
         * @param commandCompletionCallback The callback.
         * @param postProcessor             The processor for post command
         *                                  completion.
         */
        CallbackImpl(
                final CommandStart start,
                final CommandDone.CommandDoneBuilder builder,
                final CommandCompletionCallback commandCompletionCallback,
                final PostCommandProcessor postProcessor) {
            this.start = start;
            this.builder = builder;
            this.commandCompletionCallback = commandCompletionCallback;
            this.postProcessor = postProcessor;
        }

        @Override
        public void failed(final String message) {
            LOG.warn("Failed to complete action");
            this.commandCompletionCallback.done(
                    this.start.id,
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
            LOG.info("Action completed!");
            this.commandCompletionCallback.done(
                    this.start.id,
                    this.builder
                            .status(
                                    CommandDone.Status
                                            .builder()
                                            .type(DoneStatus.SUCCESS)
                                            .build())
                            .build());
            this.postProcessor.completed(this.start);
        }
    }
}