package mn.foreman.whatsminer;

import mn.foreman.cgminer.CgMiner;
import mn.foreman.cgminer.Context;
import mn.foreman.cgminer.ContextKey;
import mn.foreman.cgminer.request.CgMinerCommand;
import mn.foreman.cgminer.request.CgMinerRequest;
import mn.foreman.model.MacStrategy;
import mn.foreman.model.miners.asic.Asic;

import java.util.Collections;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/** A strategy for obtaining MAC addresses from the new firmware. */
public class NewFirmwareMacStrategy
        implements MacStrategy {

    /** The IP. */
    private final String ip;

    /** The port. */
    private final int port;

    /**
     * Constructor.
     *
     * @param ip   The IP.
     * @param port The port.
     */
    NewFirmwareMacStrategy(
            final String ip,
            final int port) {
        this.ip = ip;
        this.port = port;
    }

    @Override
    public Optional<String> getMacAddress() {
        final Context context = new Context();
        final CgMiner miner =
                new CgMiner.Builder(context, Collections.emptyList())
                        .setApiIp(this.ip)
                        .setApiPort(this.port)
                        .setConnectTimeout(
                                1,
                                TimeUnit.SECONDS)
                        .addRequest(
                                new CgMinerRequest.Builder()
                                        .setCommand(CgMinerCommand.SUMMARY)
                                        .build(),
                                (builder, response) -> {
                                    WhatsminerUtils.updateSummary(
                                            response.getValues(),
                                            new Asic.Builder(),
                                            context);
                                })
                        .build();

        try {
            miner.getStats();
        } catch (final Exception e) {
            // Ignore
        }

        return context.getSimple(ContextKey.MAC);
    }
}
