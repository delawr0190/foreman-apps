package mn.foreman.whatsminer;

import mn.foreman.model.AbstractNetworkAction;
import mn.foreman.model.Network;
import mn.foreman.model.error.MinerException;
import mn.foreman.whatsminer.latest.Command;
import mn.foreman.whatsminer.latest.WhatsminerApi;
import mn.foreman.whatsminer.latest.error.ApiException;
import mn.foreman.whatsminer.latest.error.PermissionDeniedException;

import com.google.common.collect.ImmutableMap;

import java.util.Map;

/** Changes Whatsminer network settings. */
public class WhatsminerNetworkAction
        extends AbstractNetworkAction {

    @Override
    protected boolean doChange(
            final String ip,
            final int port,
            final Map<String, Object> parameters,
            final Network network)
            throws MinerException {
        try {
            return WhatsminerApi.runCommand(
                    ip,
                    // Test hook
                    (port == 8081 ? 4029 : 4028),
                    parameters.getOrDefault("password", "").toString(),
                    Command.NETWORK,
                    ImmutableMap.<String, String>builder()
                            .put(
                                    "ip",
                                    network.ipAddress)
                            .put(
                                    "mask",
                                    network.netmask)
                            .put(
                                    "gate",
                                    network.gateway)
                            .put(
                                    "dns",
                                    network.dns)
                            .put(
                                    "host",
                                    network.hostname)
                            .build());
        } catch (final ApiException ae) {
            throw new MinerException("Firmware outdated or bad password", ae);
        } catch (final PermissionDeniedException pde) {
            throw new MinerException("Write API must be enabled", pde);
        }
    }
}
