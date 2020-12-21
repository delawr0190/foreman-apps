package mn.foreman.whatsminer;

import mn.foreman.model.AsicAction;
import mn.foreman.model.error.MinerException;
import mn.foreman.whatsminer.latest.ApiException;
import mn.foreman.whatsminer.latest.Command;
import mn.foreman.whatsminer.latest.WhatsminerApi;

import java.util.Collections;
import java.util.Map;

/** Performs a factory reset of a Whatsminer. */
public class WhatsminerFactoryResetStrategy
        implements AsicAction.CompletableAction {

    @Override
    public boolean run(
            final String ip,
            final int port,
            final Map<String, Object> parameters)
            throws MinerException {
        try {
            return WhatsminerApi.runCommand(
                    ip,
                    // Test hook
                    (port == 8081 ? 4029 : 4028),
                    parameters.getOrDefault("password", "").toString(),
                    Command.FACTORY_RESET,
                    Collections.emptyMap());
        } catch (final ApiException e) {
            throw new MinerException("Firmware is too old or bad password", e);
        }
    }
}
