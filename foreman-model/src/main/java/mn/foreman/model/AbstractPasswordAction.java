package mn.foreman.model;

import mn.foreman.model.error.MinerException;
import mn.foreman.model.error.NotAuthenticatedException;

import java.util.Map;

/** A {@link AsicAction.CompletableAction} for changing admin passwords. */
public abstract class AbstractPasswordAction
        implements AsicAction.CompletableAction {

    @Override
    public boolean run(
            final String ip,
            final int port,
            final Map<String, Object> args)
            throws NotAuthenticatedException, MinerException {
        if (args.containsKey("oldPassword") && args.containsKey("newPassword")) {
            return doChange(
                    ip,
                    port,
                    args,
                    args.get("oldPassword").toString(),
                    args.get("newPassword").toString());
        } else {
            throw new MinerException("Missing old or new password");
        }
    }

    /**
     * Performs the change.
     *
     * @param ip          The ip.
     * @param port        The port.
     * @param parameters  The parameters.
     * @param oldPassword The old password.
     * @param newPassword The new password.
     *
     * @return Whether or not the mode was changed.
     *
     * @throws MinerException on failure.
     */
    protected abstract boolean doChange(
            final String ip,
            final int port,
            final Map<String, Object> parameters,
            final String oldPassword,
            final String newPassword)
            throws MinerException;
}
