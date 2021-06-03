package mn.foreman.whatsminer;

import mn.foreman.model.ApplicationConfiguration;
import mn.foreman.model.AsicAction;
import mn.foreman.model.error.MinerException;
import mn.foreman.whatsminer.latest.Command;
import mn.foreman.whatsminer.latest.WhatsminerApi;
import mn.foreman.whatsminer.latest.error.ApiException;
import mn.foreman.whatsminer.latest.error.PermissionDeniedException;

import java.util.Collections;
import java.util.Map;

/** A strategy for rebooting a Whatsminer miners. */
public class WhatsminerRebootActionNew
        implements AsicAction.CompletableAction {

    /** The configuration. */
    private final ApplicationConfiguration applicationConfiguration;

    /**
     * Constructor.
     *
     * @param applicationConfiguration The configuration.
     */
    public WhatsminerRebootActionNew(
            final ApplicationConfiguration applicationConfiguration) {
        this.applicationConfiguration = applicationConfiguration;
    }

    @Override
    public boolean run(
            final String ip,
            final int port,
            final Map<String, Object> args)
            throws MinerException {
        try {
            return WhatsminerApi.runCommand(
                    ip,
                    // Test hook
                    (port == 8081 ? 4029 : 4028),
                    args.getOrDefault("password", "").toString(),
                    Command.REBOOT,
                    Collections.emptyMap(),
                    this.applicationConfiguration);
        } catch (final ApiException ae) {
            throw new MinerException("Firmware outdated or bad password", ae);
        } catch (final PermissionDeniedException pde) {
            throw new MinerException("Write API must be enabled", pde);
        }
    }
}