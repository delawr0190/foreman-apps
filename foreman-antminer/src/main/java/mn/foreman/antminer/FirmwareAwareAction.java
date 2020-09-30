package mn.foreman.antminer;

import mn.foreman.cgminer.CgMiner;
import mn.foreman.cgminer.request.CgMinerCommand;
import mn.foreman.cgminer.request.CgMinerRequest;
import mn.foreman.model.AsicAction;
import mn.foreman.model.error.EmptySiteException;
import mn.foreman.model.error.MinerException;
import mn.foreman.model.error.NotAuthenticatedException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

/**
 * An {@link FirmwareAwareAction} provides a {@link AsicAction.CompletableAction}
 * implementation that will change the pools in use by an antminer device.
 */
public class FirmwareAwareAction
        implements AsicAction.CompletableAction {

    /** The logger for this class. */
    private static final Logger LOG =
            LoggerFactory.getLogger(FirmwareAwareAction.class);

    /** The action for stock firmware antminers. */
    private final AsicAction.CompletableAction antminerAction;

    /** The action for braiins firmware antminers. */
    private final AsicAction.CompletableAction braiinsAction;

    /**
     * Constructor.
     *
     * @param antminerAction The antminer action.
     * @param braiinsAction  The braiins action.
     */
    public FirmwareAwareAction(
            final AsicAction.CompletableAction antminerAction,
            final AsicAction.CompletableAction braiinsAction) {
        this.antminerAction = antminerAction;
        this.braiinsAction = braiinsAction;
    }

    @Override
    public boolean run(
            final String ip,
            final int port,
            final Map<String, Object> args)
            throws NotAuthenticatedException, MinerException {
        boolean success;

        final AtomicReference<AntminerType> typeReference =
                new AtomicReference<>();
        final AtomicBoolean completed = new AtomicBoolean(false);

        final CgMiner cgMiner =
                new CgMiner.Builder()
                        .setApiIp(ip)
                        .setApiPort(args.get("apiPort").toString())
                        .addRequest(
                                new CgMinerRequest.Builder()
                                        .setCommand(CgMinerCommand.VERSION)
                                        .build(),
                                (builder, response) -> {
                                    try {
                                        AntminerUtils.toType(response.getValues())
                                                .ifPresent(typeReference::set);
                                    } catch (final EmptySiteException e) {
                                        throw new MinerException(e);
                                    }
                                })
                        .build();

        try {
            cgMiner.getStats();
            completed.set(true);
        } catch (final Exception e) {
            // Ignore - expected
        }

        if (completed.get()) {
            final AntminerType type = typeReference.get();
            if (type != null) {
                if (type.isBraiins()) {
                    LOG.info("Miner is bOS firmware");
                    success =
                            this.braiinsAction.run(
                                    ip,
                                    port,
                                    args);
                } else {
                    LOG.info("Miner is stock firmware");
                    success =
                            this.antminerAction.run(
                                    ip,
                                    port,
                                    args);
                }
            } else {
                throw new MinerException("Unexpected Antminer type");
            }
        } else {
            throw new MinerException("Miner is unreachable");
        }

        return success;
    }
}