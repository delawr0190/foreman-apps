package mn.foreman.whatsminer;

import mn.foreman.model.ApplicationConfiguration;
import mn.foreman.model.AsicAction;
import mn.foreman.model.error.MinerException;

import com.google.common.collect.ImmutableMap;
import org.apache.http.HttpStatus;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

/** Performs a reboot of a Whatsminer miner. */
public class WhatsminerRebootActionOld
        implements AsicAction.CompletableAction {

    /** The configuration. */
    private final ApplicationConfiguration applicationConfiguration;

    /**
     * Constructor.
     *
     * @param applicationConfiguration The configuration.
     */
    public WhatsminerRebootActionOld(
            final ApplicationConfiguration applicationConfiguration) {
        this.applicationConfiguration = applicationConfiguration;
    }

    @Override
    public boolean run(
            final String ip,
            final int port,
            final Map<String, Object> args)
            throws MinerException {
        final AtomicBoolean success = new AtomicBoolean(false);

        final String username = args.get("username").toString();
        final String password = args.get("password").toString();

        final AtomicReference<String> tokenRef = new AtomicReference<>();

        WhatsminerQuery.query(
                ip,
                port,
                username,
                password,
                Arrays.asList(
                        WhatsminerUtils.queryToken(
                                "/cgi-bin/luci/admin/system/reboot",
                                tokenRef),
                        WhatsminerQuery.Query
                                .builder()
                                .uri("/cgi-bin/luci//admin/system/reboot/call")
                                .isGet(false)
                                .isMultipartForm(false)
                                .urlParams(new LinkedList<>())
                                .paramEnricher(maps ->
                                        maps.add(
                                                ImmutableMap.of(
                                                        "key",
                                                        "token",
                                                        "value",
                                                        tokenRef.get())))
                                .callback((integer, s) ->
                                        success.set(integer == HttpStatus.SC_OK))
                                .timeout(e -> success.set(true))
                                .build()),
                this.applicationConfiguration.getWriteSocketTimeout());

        return success.get();
    }
}
