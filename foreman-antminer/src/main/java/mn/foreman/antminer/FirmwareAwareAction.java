package mn.foreman.antminer;

import mn.foreman.model.AsicAction;
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

    /** The realm. */
    private final String realm;

    /**
     * Constructor.
     *
     * @param realm          The realm.
     * @param antminerAction The antminer action.
     * @param braiinsAction  The braiins action.
     */
    public FirmwareAwareAction(
            final String realm,
            final AsicAction.CompletableAction antminerAction,
            final AsicAction.CompletableAction braiinsAction) {
        this.realm = realm;
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

        final AtomicBoolean completed = new AtomicBoolean(false);
        final AtomicReference<AntminerType> typeReference =
                new AtomicReference<>();

        AntminerUtils.getType(
                ip,
                Integer.parseInt(args.getOrDefault("apiPort", "4028").toString()),
                port,
                args.getOrDefault("username", "root").toString(),
                args.getOrDefault("password", "root").toString(),
                this.realm,
                s -> completed.set(true))
                .ifPresent(typeReference::set);

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