package mn.foreman.whatsminer;

import mn.foreman.model.AbstractPowerModeAction;
import mn.foreman.model.error.MinerException;
import mn.foreman.whatsminer.latest.Command;
import mn.foreman.whatsminer.latest.WhatsminerApi;
import mn.foreman.whatsminer.latest.error.ApiException;
import mn.foreman.whatsminer.latest.error.PermissionDeniedException;

import com.google.common.collect.ImmutableMap;

import java.util.Collections;
import java.util.Map;

/** Sets the Whatsminer power mode. */
public class WhatsminerPowerModeAction
        extends AbstractPowerModeAction {

    @Override
    protected boolean doChange(
            final String ip,
            final int port,
            final Map<String, Object> parameters,
            final PowerMode mode) throws MinerException {
        try {
            final String password =
                    parameters.getOrDefault(
                            "password",
                            "").toString();
            final int realPort = (port == 8081 ? 4029 : 4028);
            final Command command = toCommand(mode);
            return WhatsminerApi.runCommand(
                    ip,
                    // Test hook
                    realPort,
                    password,
                    command,
                    command == Command.POWER_OFF
                            ? ImmutableMap.of("respbefore", "true")
                            : Collections.emptyMap());
        } catch (final ApiException ae) {
            throw new MinerException("Firmware outdated or bad password", ae);
        } catch (final PermissionDeniedException pde) {
            throw new MinerException("Write API must be enabled", pde);
        }
    }

    /**
     * Converts the provided value to a power mode command.
     *
     * @param mode The mode.
     *
     * @return The command.
     */
    private static Command toCommand(final PowerMode mode) {
        if (mode == PowerMode.SLEEPING) {
            return Command.POWER_OFF;
        }
        return Command.POWER_ON;
    }
}
