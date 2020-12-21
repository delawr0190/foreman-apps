package mn.foreman.whatsminer;

import mn.foreman.model.AsicAction;
import mn.foreman.model.error.MinerException;
import mn.foreman.whatsminer.latest.Command;
import mn.foreman.whatsminer.latest.WhatsminerApi;

import java.util.Collections;
import java.util.Map;

/** A strategy for rebooting a Whatsminer miners. */
public class WhatsminerRebootActionNew
        implements AsicAction.CompletableAction {

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
                    Collections.emptyMap());
        } catch (final Exception e) {
            throw new MinerException(e);
        }
    }
}