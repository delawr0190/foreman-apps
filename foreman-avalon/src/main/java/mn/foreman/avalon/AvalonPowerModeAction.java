package mn.foreman.avalon;

import mn.foreman.model.AbstractPowerModeAction;
import mn.foreman.model.ApplicationConfiguration;
import mn.foreman.model.AsicAction;
import mn.foreman.model.error.MinerException;
import mn.foreman.model.error.NotAuthenticatedException;

import java.util.Map;

/**
 * A {@link AvalonPowerModeAction} provides an action implementation that will
 * perform a power mode change against an Avalon.
 */
public class AvalonPowerModeAction
        extends AbstractPowerModeAction {

    /** The configuration. */
    private final ApplicationConfiguration applicationConfiguration;

    /** The action to run when performing a reboot. */
    private final AsicAction.CompletableAction rebootAction;

    /**
     * Constructor.
     *
     * @param applicationConfiguration The configuration.
     * @param rebootAction             The action to run when performing a
     *                                 reboot.
     */
    public AvalonPowerModeAction(
            final ApplicationConfiguration applicationConfiguration,
            final AsicAction.CompletableAction rebootAction) {
        this.applicationConfiguration = applicationConfiguration;
        this.rebootAction = rebootAction;
    }

    @Override
    protected boolean doChange(
            final String ip,
            final int port,
            final Map<String, Object> parameters,
            final PowerMode mode)
            throws MinerException {
        boolean success;
        try {
            if (mode == PowerMode.SLEEPING) {
                if (success = AvalonUtils.query(
                        ip,
                        port,
                        "ascset|0,hashpower,0",
                        (s, request) -> request.connected(),
                        this.applicationConfiguration.getWriteSocketTimeout())) {
                    success =
                            this.rebootAction.run(
                                    ip,
                                    port,
                                    parameters);
                }
            } else {
                success =
                        this.rebootAction.run(
                                ip,
                                port,
                                parameters);
            }
        } catch (final NotAuthenticatedException nae) {
            throw new MinerException(nae);
        }
        return success;
    }
}
