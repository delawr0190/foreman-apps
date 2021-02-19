package mn.foreman.whatsminer;

import mn.foreman.cgminer.*;
import mn.foreman.cgminer.request.CgMinerCommand;
import mn.foreman.cgminer.request.CgMinerRequest;
import mn.foreman.model.Miner;
import mn.foreman.model.MinerFactory;

import com.google.common.collect.ImmutableMap;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * A {@link MinerFactory} implementation that parses a configuration and creates
 * a {@link Miner} that will query a Whatsminer miner.
 */
public class WhatsminerFactory
        extends CgMinerFactory {

    @Override
    protected Miner create(
            final String apiIp,
            final String apiPort,
            final List<String> statsWhitelist,
            final Map<String, Object> config) {
        final Context cgContext = new Context();
        final ResponseStrategy oldFirmwareStrategy =
                new AggregatingResponseStrategy<>(
                        ImmutableMap.of(
                                "SUMMARY",
                                (values, builder, context) ->
                                        WhatsminerUtils.updateSummary(
                                                values,
                                                builder,
                                                cgContext),
                                "STATS",
                                (values, builder, context) ->
                                        WhatsminerUtils.updateStats(
                                                values,
                                                builder)),
                        () -> null,
                        cgContext);

        final ResponseStrategy newFirmwareStrategy =
                new AggregatingResponseStrategy<>(
                        ImmutableMap.of(
                                "SUMMARY",
                                (values, builder, context) ->
                                        WhatsminerUtils.updateSummary(
                                                values,
                                                builder,
                                                cgContext),
                                "DEVS",
                                (values, builder, context) ->
                                        WhatsminerUtils.updateDevs(
                                                values,
                                                builder)),
                        () -> null,
                        cgContext);

        return new FirmwareAwareMiner(
                // 202009
                new CgMiner.Builder(cgContext, statsWhitelist)
                        .setApiIp(apiIp)
                        .setApiPort(apiPort)
                        .setConnectTimeout(
                                1,
                                TimeUnit.SECONDS)
                        .setCommandKey("cmd")
                        .addRequest(
                                new CgMinerRequest.Builder()
                                        .setCommand(CgMinerCommand.POOLS)
                                        .build(),
                                new PoolsResponseStrategy(
                                        new MrrRigIdCallback(cgContext)))
                        .addRequest(
                                new CgMinerRequest.Builder()
                                        .setCommand(CgMinerCommand.SUMMARY)
                                        .build(),
                                newFirmwareStrategy)
                        .addRequest(
                                new CgMinerRequest.Builder()
                                        .setCommand(CgMinerCommand.EDEVS)
                                        .build(),
                                newFirmwareStrategy)
                        .setMacStrategy(
                                new NewFirmwareMacStrategy(
                                        apiIp,
                                        Integer.parseInt(apiPort)))
                        .setFailureCallback(
                                new SleepModeInspectionCallback(
                                        apiIp,
                                        apiPort,
                                        config))
                        .build(),
                // 202008
                new CgMiner.Builder(cgContext, statsWhitelist)
                        .setApiIp(apiIp)
                        .setApiPort(apiPort)
                        .setConnectTimeout(
                                1,
                                TimeUnit.SECONDS)
                        .addRequest(
                                new CgMinerRequest.Builder()
                                        .setCommand(CgMinerCommand.POOLS)
                                        .build(),
                                new PoolsResponseStrategy(
                                        new MrrRigIdCallback(cgContext)))
                        .addRequest(
                                new CgMinerRequest.Builder()
                                        .setCommand(CgMinerCommand.SUMMARY)
                                        .build(),
                                newFirmwareStrategy)
                        .addRequest(
                                new CgMinerRequest.Builder()
                                        .setCommand(CgMinerCommand.EDEVS)
                                        .build(),
                                newFirmwareStrategy)
                        .setMacStrategy(
                                new NewFirmwareMacStrategy(
                                        apiIp,
                                        Integer.parseInt(apiPort)))
                        .setFailureCallback(
                                new SleepModeInspectionCallback(
                                        apiIp,
                                        apiPort,
                                        config))
                        .build(),
                // 202007
                new CgMiner.Builder(cgContext, statsWhitelist)
                        .setApiIp(apiIp)
                        .setApiPort(apiPort)
                        .setConnectTimeout(
                                1,
                                TimeUnit.SECONDS)
                        .addRequest(
                                new CgMinerRequest.Builder()
                                        .setCommand(CgMinerCommand.POOLS)
                                        .build(),
                                new PoolsResponseStrategy(
                                        new MrrRigIdCallback(cgContext)))
                        .addRequest(
                                new CgMinerRequest.Builder()
                                        .setCommand(CgMinerCommand.SUMMARY)
                                        .build(),
                                newFirmwareStrategy)
                        .addRequest(
                                new CgMinerRequest.Builder()
                                        .setCommand(CgMinerCommand.DEVS)
                                        .build(),
                                newFirmwareStrategy)
                        .setMacStrategy(
                                new NewFirmwareMacStrategy(
                                        apiIp,
                                        Integer.parseInt(apiPort)))
                        .build(),
                // Old firmware
                new CgMiner.Builder(cgContext, statsWhitelist)
                        .setApiIp(apiIp)
                        .setApiPort(apiPort)
                        .setConnectTimeout(
                                1,
                                TimeUnit.SECONDS)
                        .addRequest(
                                new CgMinerRequest.Builder()
                                        .setCommand(CgMinerCommand.POOLS)
                                        .build(),
                                new PoolsResponseStrategy(
                                        new MrrRigIdCallback(cgContext)))
                        .addRequest(
                                new CgMinerRequest.Builder()
                                        .setCommand(CgMinerCommand.SUMMARY)
                                        .build(),
                                oldFirmwareStrategy)
                        .addRequest(
                                new CgMinerRequest.Builder()
                                        .setCommand(CgMinerCommand.STATS)
                                        .build(),
                                oldFirmwareStrategy)
                        .build());
    }
}