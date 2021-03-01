package mn.foreman.whatsminer;

import mn.foreman.model.AbstractPasswordAction;
import mn.foreman.model.error.MinerException;
import mn.foreman.whatsminer.latest.Command;
import mn.foreman.whatsminer.latest.WhatsminerApi;
import mn.foreman.whatsminer.latest.error.ApiException;
import mn.foreman.whatsminer.latest.error.PermissionDeniedException;

import com.google.common.collect.ImmutableMap;

import java.util.Map;

/** Changes the Whatsminer password. */
public class WhatsminerPasswordAction
        extends AbstractPasswordAction {

    @Override
    protected boolean doChange(
            final String ip,
            final int port,
            final Map<String, Object> parameters,
            final String oldPassword,
            final String newPassword) throws MinerException {
        try {
            return WhatsminerApi.runCommand(
                    ip,
                    // Test hook
                    (port == 8081 || port == 4029 ? 4029 : 4028),
                    oldPassword,
                    Command.UPDATE_PWD,
                    ImmutableMap.of(
                            "old",
                            oldPassword,
                            "new",
                            newPassword));
        } catch (final ApiException ae) {
            throw new MinerException("Firmware outdated or bad password", ae);
        } catch (final PermissionDeniedException pde) {
            throw new MinerException("Write API must be enabled", pde);
        }
    }
}
