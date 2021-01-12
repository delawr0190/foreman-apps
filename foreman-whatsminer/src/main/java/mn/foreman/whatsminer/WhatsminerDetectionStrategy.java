package mn.foreman.whatsminer;

import mn.foreman.model.Detection;
import mn.foreman.model.DetectionStrategy;
import mn.foreman.model.MacStrategy;

import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

/** A strategy for detecting Whatsminer miners. */
public class WhatsminerDetectionStrategy
        implements DetectionStrategy {

    /** The logger for this class. */
    private static final Logger LOG =
            LoggerFactory.getLogger(WhatsminerDetectionStrategy.class);

    /** The strategy. */
    private final MacStrategy macStrategy;

    /**
     * Constructor.
     *
     * @param macStrategy The strategy.
     */
    public WhatsminerDetectionStrategy(final MacStrategy macStrategy) {
        this.macStrategy = macStrategy;
    }

    @Override
    public Optional<Detection> detect(
            final String ip,
            final int port,
            final Map<String, Object> args) {
        Detection detection = null;

        final AtomicReference<WhatsminerType> typeRef =
                new AtomicReference<>();
        query(
                ip,
                args,
                "443",
                typeRef);
        if (typeRef.get() == null) {
            query(
                    ip,
                    args,
                    "80",
                    typeRef);
        }

        final WhatsminerType whatsminerType =
                typeRef.get();
        if (whatsminerType != null) {
            final Map<String, Object> newArgs = new HashMap<>(args);
            this.macStrategy.getMacAddress()
                    .ifPresent(mac -> newArgs.put("mac", mac));
            detection =
                    Detection
                            .builder()
                            .ipAddress(ip)
                            .port(port)
                            .minerType(whatsminerType)
                            .parameters(newArgs)
                            .build();
        }

        return Optional.ofNullable(detection);
    }

    /**
     * Query the miner interface.
     *
     * @param ip      The ip.
     * @param args    The args.
     * @param webPort The default web port.
     * @param typeRef The type to update.
     */
    private static void query(
            final String ip,
            final Map<String, Object> args,
            final String webPort,
            final AtomicReference<WhatsminerType> typeRef) {
        try {
            WhatsminerQuery.query(
                    ip,
                    Integer.parseInt(args.getOrDefault("webPort", webPort).toString()),
                    args.getOrDefault("username", "").toString(),
                    args.getOrDefault("password", "").toString(),
                    Collections.singletonList(
                            WhatsminerQuery.Query
                                    .builder()
                                    .uri("/cgi-bin/luci/admin/status/overview")
                                    .isGet(true)
                                    .isMultipartForm(false)
                                    .urlParams(Collections.emptyList())
                                    .callback((statusCode, data) -> {
                                        if (statusCode == HttpStatus.SC_OK) {
                                            final int versionStart = data.indexOf("WhatsMiner M");
                                            final String version =
                                                    data.substring(
                                                            versionStart,
                                                            data.indexOf(
                                                                    "<",
                                                                    versionStart));
                                            WhatsminerType.fromVersion(version)
                                                    .ifPresent(typeRef::set);
                                        }
                                    })
                                    .build()));
        } catch (final Exception e) {
            LOG.debug("No miner found on {}:{}", ip, webPort, e);
        }
    }
}
