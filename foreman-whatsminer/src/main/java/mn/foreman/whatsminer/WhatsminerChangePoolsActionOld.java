package mn.foreman.whatsminer;

import mn.foreman.api.model.Pool;
import mn.foreman.model.AbstractChangePoolsAction;
import mn.foreman.model.ApplicationConfiguration;
import mn.foreman.model.error.MinerException;

import com.google.common.collect.ImmutableMap;
import org.apache.http.HttpStatus;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

/** A strategy for changing pools on Whatsminer miners. */
public class WhatsminerChangePoolsActionOld
        extends AbstractChangePoolsAction {

    /** The configuration. */
    private final ApplicationConfiguration applicationConfiguration;

    /**
     * Constructor.
     *
     * @param applicationConfiguration The configuration.
     */
    public WhatsminerChangePoolsActionOld(
            final ApplicationConfiguration applicationConfiguration) {
        this.applicationConfiguration = applicationConfiguration;
    }

    @Override
    protected boolean doChange(
            final String ip,
            final int port,
            final Map<String, Object> parameters,
            final List<Pool> pools)
            throws MinerException {
        final AtomicBoolean success = new AtomicBoolean(false);

        final String username = parameters.get("username").toString();
        final String password = parameters.get("password").toString();

        final AtomicReference<String> tokenRef = new AtomicReference<>();

        final List<Map<String, Object>> poolParams = new LinkedList<>();
        poolParams.add(
                toMap(
                        "cbi.submit",
                        "1"));
        poolParams.add(
                toMap(
                        "cbid.pools.default.coin_type",
                        "BTC"));
        for (int i = 0; i < pools.size(); i++) {
            poolParams.addAll(
                    toPool(
                            pools.get(i),
                            i + 1));
        }
        poolParams.add(
                toMap(
                        "cbi.apply",
                        "Save & Apply"));

        WhatsminerQuery.query(
                ip,
                port,
                username,
                password,
                Arrays.asList(
                        WhatsminerUtils.queryToken(
                                "/cgi-bin/luci/admin/network/cgminer",
                                tokenRef),
                        WhatsminerQuery.Query
                                .builder()
                                .uri(
                                        parameters.containsKey("test")
                                                ? "/cgi-bin/luci/admin/network/cgminer/alt"
                                                : "/cgi-bin/luci/admin/network/cgminer")
                                .isGet(false)
                                .isMultipartForm(true)
                                .boundary(
                                        parameters.containsKey("boundary")
                                                ? parameters.get("boundary").toString()
                                                : null)
                                .urlParams(poolParams)
                                .paramEnricher(maps ->
                                        maps.add(
                                                toMap(
                                                        "token",
                                                        tokenRef.get())))
                                .callback((integer, s) ->
                                        success.set(integer == HttpStatus.SC_OK))
                                .build(),
                        WhatsminerQuery.Query
                                .builder()
                                .uri("/cgi-bin/luci/servicectl/restart/pools")
                                .isGet(false)
                                .isMultipartForm(false)
                                .urlParams(new LinkedList<>())
                                .paramEnricher(maps ->
                                        maps.add(
                                                toMap(
                                                        "token",
                                                        tokenRef.get())))
                                .callback((integer, s) ->
                                        success.set(integer == HttpStatus.SC_OK))
                                .timeout(e -> success.set(true))
                                .build()),
                this.applicationConfiguration.getWriteSocketTimeout());

        return success.get();
    }

    /**
     * Returns the arguments as a map.
     *
     * @param key   The key.
     * @param value The value.
     *
     * @return The arguments.
     */
    private static Map<String, Object> toMap(
            final String key,
            final String value) {
        return ImmutableMap.of(
                "key",
                key,
                "value",
                value);
    }

    /**
     * Creates pool arguments.
     *
     * @param pool  The pool.
     * @param index The index.
     *
     * @return The arguments.
     */
    private static List<Map<String, Object>> toPool(
            final Pool pool,
            final int index) {
        return Arrays.asList(
                toMap(
                        String.format(
                                "cbid.pools.default.pool%durl",
                                index),
                        pool.getUrl()),
                toMap(
                        String.format(
                                "cbid.pools.default.pool%duser",
                                index),
                        pool.getUsername()),
                toMap(
                        String.format(
                                "cbid.pools.default.pool%dpw",
                                index),
                        pool.getPassword()));
    }
}
