package mn.foreman.goldshell;

import mn.foreman.model.AbstractPasswordAction;
import mn.foreman.model.ApplicationConfiguration;

import com.google.common.collect.ImmutableMap;

import java.util.Map;

/**
 * A {@link GoldshellPasswordAction} provides an {@link AbstractPasswordAction}
 * implementation that will change the password.
 */
public class GoldshellPasswordAction
        extends AbstractPasswordAction {

    /** The configuration. */
    private final ApplicationConfiguration configuration;

    /**
     * Constructor.
     *
     * @param configuration The configuration.
     */
    public GoldshellPasswordAction(
            final ApplicationConfiguration configuration) {
        this.configuration = configuration;
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
                this.configuration);
    }
}
