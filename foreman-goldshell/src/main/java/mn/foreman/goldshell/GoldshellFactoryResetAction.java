package mn.foreman.goldshell;

import mn.foreman.model.AbstractChangePoolsAction;
import mn.foreman.model.ApplicationConfiguration;
import mn.foreman.model.AsicAction;
import mn.foreman.model.error.MinerException;

import java.util.Map;

/**
 * A {@link GoldshellFactoryResetAction} provides an {@link
 * AbstractChangePoolsAction} implementation that will factory reset a goldshell
 * device.
 */
public class GoldshellFactoryResetAction
        implements AsicAction.CompletableAction {

    /** The configuration. */
    private final ApplicationConfiguration configuration;

    /**
     * Constructor.
     *
     * @param configuration The configuration.
     */
    public GoldshellFactoryResetAction(
            final ApplicationConfiguration configuration) {
        this.configuration = configuration;
    }

    @Override
    public boolean run(
            final String ip,
            final int port,
            final Map<String, Object> parameters)
            throws MinerException {
        return GoldshellQuery.runPut(
                ip,
                port,
                "/mcb/facrst",
                null,
                this.configuration);
    }
}