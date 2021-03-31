package mn.foreman.whatsminer;

import mn.foreman.model.BlinkStrategy;
import mn.foreman.model.error.MinerException;
import mn.foreman.whatsminer.latest.Command;
import mn.foreman.whatsminer.latest.WhatsminerApi;
import mn.foreman.whatsminer.latest.error.ApiException;
import mn.foreman.whatsminer.latest.error.PermissionDeniedException;

import com.google.common.collect.ImmutableMap;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/** A {@link BlinkStrategy} for blinking LEDs on a Whatsminer. */
public class WhatsminerBlinkStrategy
        implements BlinkStrategy {

    @Override
    public boolean startBlink(
            final String ip,
            final int port,
            final Map<String, Object> parameters) throws MinerException {
        return runLedCommand(
                ip,
                port,
                parameters,
                ImmutableMap.of(
                        "color",
                        "red",
                        "period",
                        Long.toString(TimeUnit.SECONDS.toMillis(2)),
                        "duration",
                        Long.toString(TimeUnit.SECONDS.toMillis(1)),
                        "start",
                        "0"));
    }

    @Override
    public boolean stopBlink(
            final String ip,
            final int port,
            final Map<String, Object> parameters) throws MinerException {
        return runLedCommand(
                ip,
                port,
                parameters,
                ImmutableMap.of(
                        "param",
                        "auto"));
    }

    /**
     * Starts or stops the LED flashing cycle.
     *
     * @param ip         The ip.
     * @param port       The port.
     * @param parameters The parameters.
     * @param args       The args.
     *
     * @return Whether or not the command was successful.
     *
     * @throws MinerException on failure.
     */
    private boolean runLedCommand(
            final String ip,
            final int port,
            final Map<String, Object> parameters,
            final Map<String, String> args)
            throws MinerException {
        try {
            return WhatsminerApi.runCommand(
                    ip,
                    // Test hook
                    (port == 8081 || port == 4029 ? 4029 : 4028),
                    parameters.getOrDefault("password", "").toString(),
                    Command.SET_LED,
                    args);
        } catch (final ApiException ae) {
            throw new MinerException("Firmware outdated or bad password", ae);
        } catch (final PermissionDeniedException pde) {
            throw new MinerException("Write API must be enabled", pde);
        }
    }
}