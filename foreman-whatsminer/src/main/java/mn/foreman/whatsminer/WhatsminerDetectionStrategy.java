package mn.foreman.whatsminer;

import mn.foreman.cgminer.CgMiner;
import mn.foreman.cgminer.Context;
import mn.foreman.cgminer.request.CgMinerCommand;
import mn.foreman.cgminer.request.CgMinerRequest;
import mn.foreman.model.*;
import mn.foreman.model.error.MinerException;
import mn.foreman.util.ArgUtils;

import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

/** A strategy for detecting Whatsminer miners. */
public class WhatsminerDetectionStrategy
        implements DetectionStrategy {

    /** The logger for this class. */
    private static final Logger LOG =
            LoggerFactory.getLogger(WhatsminerDetectionStrategy.class);

    /** The strategy. */
    private final MacStrategy macStrategy;

    /** The miner. */
    private final Miner miner;

    /**
     * Constructor.
     *
     * @param macStrategy The strategy.
     * @param miner       The miner.
     */
    public WhatsminerDetectionStrategy(
            final MacStrategy macStrategy,
            final Miner miner) {
        this.macStrategy = macStrategy;
        this.miner = miner;
    }

    @Override
    public Optional<Detection> detect(
            final String ip,
            final int port,
            final Map<String, Object> args) {
        Detection detection = null;

        final AtomicReference<WhatsminerType> typeRef =
                new AtomicReference<>();
        try {
            final AtomicBoolean foundCgminer = new AtomicBoolean(false);
            final CgMiner cgMiner =
                    new CgMiner.Builder(new Context(), Collections.emptyList())
                            .setApiIp(ip)
                            .setApiPort(4028)
                            .setConnectTimeout(
                                    1,
                                    TimeUnit.SECONDS)
                            .addRequest(
                                    new CgMinerRequest.Builder()
                                            .setCommand(CgMinerCommand.POOLS)
                                            .build(),
                                    (builder, response) -> foundCgminer.set(response != null))
                            .build();
            cgMiner.getStats();

            WhatsminerQuery.query(
                    ip,
                    Integer.parseInt(args.getOrDefault("webPort", "443").toString()),
                    args.getOrDefault("username", "").toString(),
                    args.getOrDefault("password", "").toString(),
                    2,
                    TimeUnit.SECONDS,
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
        } catch (final MinerException me) {
            LOG.debug("No miner found on {}:{}", ip, port, me);
        }

        final WhatsminerType whatsminerType =
                typeRef.get();
        if (whatsminerType != null) {
            final Map<String, Object> newArgs = new HashMap<>(args);
            this.macStrategy.getMacAddress()
                    .ifPresent(mac -> newArgs.put("mac", mac));

            if (ArgUtils.isWorkerPreferred(newArgs)) {
                DetectionUtils.addWorkerFromStats(
                        this.miner,
                        newArgs);
            }

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
}
