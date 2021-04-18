package mn.foreman.goldshell;

import mn.foreman.model.AbstractPasswordAction;

import com.google.common.collect.ImmutableMap;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * A {@link GoldshellPasswordAction} provides an {@link AbstractPasswordAction}
 * implementation that will change the password.
 */
public class GoldshellPasswordAction
        extends AbstractPasswordAction {

    /** The socket timeout. */
    private final int socketTimeout;

    /** The socket timeout units. */
    private final TimeUnit socketTimeoutUnits;

    /**
     * Constructor.
     *
     * @param socketTimeout      The socket timeout.
     * @param socketTimeoutUnits The socket timeout units.
     */
    public GoldshellPasswordAction(
            final int socketTimeout,
            final TimeUnit socketTimeoutUnits) {
        this.socketTimeout = socketTimeout;
        this.socketTimeoutUnits = socketTimeoutUnits;
    }

    @Override
    protected boolean doChange(
            final String ip,
            final int port,
            final Map<String, Object> parameters,
            final String oldPassword,
            final String newPassword) {
        return GoldshellQuery.runPut(
                ip,
                port,
                "/user/updatepd",
                ImmutableMap.of(
                        "oldpassword",
                        oldPassword,
                        "newpassword",
                        newPassword,
                        "cfmpassword",
                        newPassword),
                this.socketTimeout,
                this.socketTimeoutUnits);
    }
}
