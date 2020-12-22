package mn.foreman.whatsminer;

import mn.foreman.model.AsicAction;
import mn.foreman.model.error.MinerException;
import mn.foreman.model.error.NotAuthenticatedException;
import mn.foreman.whatsminer.latest.error.PermissionDeniedException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * A completable action that will determine the appropriate action handler and
 * use it.
 */
public class WhatsminerFirmwareAwareAction
        implements AsicAction.CompletableAction {

    /** The logger for this class. */
    private static final Logger LOG =
            LoggerFactory.getLogger(WhatsminerFirmwareAwareAction.class);

    /** The new firmware handler. */
    private final AsicAction.CompletableAction newFirmware;

    /** The old firmware handler. */
    private final AsicAction.CompletableAction oldFirmware;

    /**
     * Constructor.
     *
     * @param oldFirmware The old firmware handler.
     * @param newFirmware The new firmware handler.
     */
    public WhatsminerFirmwareAwareAction(
            final AsicAction.CompletableAction oldFirmware,
            final AsicAction.CompletableAction newFirmware) {
        this.oldFirmware = oldFirmware;
        this.newFirmware = newFirmware;
    }

    @Override
    public boolean run(
            final String ip,
            final int port,
            final Map<String, Object> args)
            throws NotAuthenticatedException, MinerException {
        try {
            return this.newFirmware.run(
                    ip,
                    port,
                    args);
        } catch (final MinerException e) {
            final Throwable cause = e.getCause();
            if (cause instanceof PermissionDeniedException) {
                LOG.warn("Write API isn't enabled");
                throw e;
            } else {
                LOG.info("Not new firmware");
                return this.oldFirmware.run(
                        ip,
                        port,
                        args);
            }
        }
    }
}
