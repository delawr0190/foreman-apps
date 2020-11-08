package mn.foreman.whatsminer;

import mn.foreman.model.Detection;
import mn.foreman.model.DetectionStrategy;
import mn.foreman.model.error.MinerException;

import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

/** A strategy for detecting Whatsminer miners. */
public class WhatsminerDetectionStrategy
        implements DetectionStrategy {

    /** The logger for this class. */
    private static final Logger LOG =
            LoggerFactory.getLogger(WhatsminerDetectionStrategy.class);

    @Override
    public Optional<Detection> detect(
            final String ip,
            final int port,
            final Map<String, Object> args) {
        Detection detection = null;

        final AtomicReference<WhatsminerType> typeRef =
                new AtomicReference<>();
        try {
            WhatsminerQuery.query(
                    ip,
                    Integer.parseInt(args.getOrDefault("webPort", "443").toString()),
                    args.getOrDefault("username", "").toString(),
                    args.getOrDefault("password", "").toString(),
                    "/cgi-bin/luci/admin/status/overview",
                    true,
                    Collections.emptyList(),
                    (statusCode, data) -> {
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
                    });
        } catch (final MinerException me) {
            LOG.debug("No miner found on {}:{}", ip, port, me);
        }

        final WhatsminerType whatsminerType =
                typeRef.get();
        if (whatsminerType != null) {
            detection =
                    Detection
                            .builder()
                            .ipAddress(ip)
                            .port(port)
                            .minerType(whatsminerType)
                            .parameters(args)
                            .build();
        }

        return Optional.ofNullable(detection);
    }
}
