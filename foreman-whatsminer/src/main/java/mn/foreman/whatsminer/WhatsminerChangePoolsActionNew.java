package mn.foreman.whatsminer;

import mn.foreman.model.AbstractChangePoolsAction;
import mn.foreman.model.Pool;
import mn.foreman.model.error.MinerException;
import mn.foreman.whatsminer.latest.Command;
import mn.foreman.whatsminer.latest.WhatsminerApi;
import mn.foreman.whatsminer.latest.error.ApiException;
import mn.foreman.whatsminer.latest.error.PermissionDeniedException;

import com.google.common.collect.ImmutableMap;

import java.util.List;
import java.util.Map;

/** A strategy for changing pools on Whatsminer miners. */
public class WhatsminerChangePoolsActionNew
        extends AbstractChangePoolsAction {

    @Override
    protected boolean doChange(
            final String ip,
            final int port,
            final Map<String, Object> parameters,
            final List<Pool> pools)
            throws MinerException {
        final Pool pool1 = pools.get(0);
        final Pool pool2 = pools.get(1);
        final Pool pool3 = pools.get(2);
        try {
            return WhatsminerApi.runCommand(
                    ip,
                    // Test hook
                    (port == 8081 ? 4029 : 4028),
                    parameters.getOrDefault("password", "").toString(),
                    Command.UPDATE_POOLS,
                    ImmutableMap.<String, String>builder()
                            .put(
                                    "pool1",
                                    pool1.getUrl())
                            .put(
                                    "worker1",
                                    pool1.getUsername())
                            .put(
                                    "passwd1",
                                    pool1.getPassword())
                            .put(
                                    "pool2",
                                    pool2.getUrl())
                            .put(
                                    "worker2",
                                    pool2.getUsername())
                            .put(
                                    "passwd2",
                                    pool2.getPassword())
                            .put(
                                    "pool3",
                                    pool3.getUrl())
                            .put(
                                    "worker3",
                                    pool3.getUsername())
                            .put(
                                    "passwd3",
                                    pool3.getPassword())
                            .build());
        } catch (final ApiException ae) {
            throw new MinerException("Firmware outdated or bad password", ae);
        } catch (final PermissionDeniedException pde) {
            throw new MinerException("Write API must be enabled", pde);
        }
    }
}