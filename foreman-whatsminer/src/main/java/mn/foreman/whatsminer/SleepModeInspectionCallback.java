package mn.foreman.whatsminer;

import mn.foreman.cgminer.RequestFailureCallback;
import mn.foreman.model.error.MinerException;
import mn.foreman.model.miners.FanInfo;
import mn.foreman.model.miners.MinerStats;
import mn.foreman.model.miners.asic.Asic;
import mn.foreman.whatsminer.latest.Command;
import mn.foreman.whatsminer.latest.WhatsminerApi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * A {@link SleepModeInspectionCallback} provides a {@link
 * RequestFailureCallback} that enables a Whatsminer to be inspected for sleep
 * mode prior to aborting metrics detection against it.
 */
public class SleepModeInspectionCallback
        implements RequestFailureCallback {

    /** The logger for this class. */
    private static final Logger LOG =
            LoggerFactory.getLogger(SleepModeInspectionCallback.class);

    /** The IP. */
    private final String ip;

    /** The parameters. */
    private final Map<String, Object> parameters;

    /** The port. */
    private final int port;

    /**
     * Constructor.
     *
     * @param ip         The IP.
     * @param port       The port.
     * @param parameters The parameters.
     */
    SleepModeInspectionCallback(
            final String ip,
            final String port,
            final Map<String, Object> parameters) {
        this.ip = ip;
        this.port = (Integer.parseInt(port) == 8081 ? 4029 : 4028);
        this.parameters = parameters;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void failed(
            final MinerStats.Builder builder,
            final MinerException minerException)
            throws MinerException {
        final AtomicBoolean isSleepMode = new AtomicBoolean(false);
        try {
            if (WhatsminerApi.runCommand(
                    this.ip,
                    this.port,
                    this.parameters.getOrDefault(
                            "password",
                            "").toString(),
                    Command.STATUS,
                    Collections.emptyMap(),
                    5,
                    TimeUnit.SECONDS,
                    response -> {
                        final Map<String, String> msg =
                                (Map<String, String>) response;
                        LOG.debug("Checking if sleep mode - {}", msg.get("btmineroff"));
                        isSleepMode.set(
                                Boolean.parseBoolean(
                                        msg.getOrDefault(
                                                "btmineroff",
                                                "false")));
                    })) {
                if (isSleepMode.get()) {
                    LOG.debug("Sleep mode detected");
                    builder.addAsic(
                            new Asic.Builder()
                                    .setHashRate(BigDecimal.ZERO)
                                    .setFanInfo(
                                            new FanInfo.Builder()
                                                    .setCount(0)
                                                    .setSpeedUnits("RPM")
                                                    .build())
                                    .setPowerMode(Asic.PowerMode.SLEEPING)
                                    .build());
                }
            }
        } catch (final Exception e) {
            LOG.warn("Exception occurred", e);
            throw minerException;
        }
    }
}