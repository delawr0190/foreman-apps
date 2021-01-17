package mn.foreman.pickaxe.command.asic.rawstats;

import mn.foreman.api.ForemanApi;
import mn.foreman.api.model.CommandDone;
import mn.foreman.api.model.CommandStart;
import mn.foreman.api.model.DoneStatus;
import mn.foreman.claymore.TypeMapping;
import mn.foreman.model.Miner;
import mn.foreman.model.MinerFactory;
import mn.foreman.model.miners.MinerStats;
import mn.foreman.model.miners.asic.Asic;
import mn.foreman.pickaxe.command.CommandStrategy;
import mn.foreman.pickaxe.miners.remote.ApiType;
import mn.foreman.pickaxe.miners.remote.json.MinerConfig;
import mn.foreman.pickaxe.util.MinerUtils;

import com.google.common.collect.ImmutableMap;
import org.apache.commons.lang3.exception.ExceptionUtils;

import java.util.*;

/**
 * A {@link RawStatsStrategy} provides a mechanism to push all of the raw,
 * flattended stats that can be obtained from a miner to the Foreman dashboard
 * so that they can be used by a user to construct a custom trigger.
 */
public class RawStatsStrategy
        implements CommandStrategy {

    @Override
    public void runCommand(
            final CommandStart start,
            final ForemanApi foremanApi,
            final CommandDone.CommandDoneBuilder builder,
            final Callback callback) {
        try {
            doRunCommand(
                    start,
                    builder,
                    callback);
        } catch (final Exception e) {
            callback.done(
                    builder.status(
                            CommandDone.Status
                                    .builder()
                                    .type(DoneStatus.FAILED)
                                    .message(e.getMessage())
                                    .details(ExceptionUtils.getStackTrace(e))
                                    .build())
                            .build());
        }
    }

    /**
     * Converts the provided configuration to a config.
     *
     * @param apiIp   The API IP.
     * @param apiPort The API port.
     * @param apiType The API type.
     * @param params  The params.
     *
     * @return The config.
     */
    private static MinerConfig toConfig(
            final String apiIp,
            final int apiPort,
            final ApiType apiType,
            final List<Map<String, Object>> params) {
        final MinerConfig minerConfig = new MinerConfig();
        minerConfig.apiIp = apiIp;
        minerConfig.apiPort = apiPort;
        minerConfig.apiType = apiType;
        minerConfig.params = toParams(params);
        return minerConfig;
    }

    /**
     * Converts the provided params to config params.
     *
     * @param params The params.
     *
     * @return The config params.
     */
    private static List<MinerConfig.Param> toParams(
            final List<Map<String, Object>> params) {
        final List<MinerConfig.Param> newParams = new LinkedList<>();
        params
                .stream()
                .map(map -> {
                    final MinerConfig.Param param =
                            new MinerConfig.Param();
                    param.key = map.get("key").toString();
                    param.value = map.get("value");
                    return param;
                })
                .forEach(newParams::add);

        final MinerConfig.Param whitelist = new MinerConfig.Param();
        whitelist.key = "statsWhitelist";
        whitelist.value = Collections.singletonList("all");
        newParams.add(whitelist);

        return newParams;
    }

    /**
     * Runs the command.
     *
     * @param start    The command to run.
     * @param builder  The done builder.
     * @param callback The callback for when the command is done.
     *
     * @throws Exception on failure.
     */
    @SuppressWarnings("unchecked")
    private void doRunCommand(
            final CommandStart start,
            final CommandDone.CommandDoneBuilder builder,
            final Callback callback) throws Exception {
        final Map<String, Object> args = start.args;

        final String apiIp =
                args.get("apiIp").toString();
        final Integer apiPort =
                Integer.parseInt(args.get("apiPort").toString());
        final ApiType apiType =
                ApiType.forValue(
                        Integer.parseInt(
                                args.get("apiType").toString()));
        final List<Map<String, Object>> params =
                (List<Map<String, Object>>) args.get("params");

        final MinerConfig minerConfig =
                toConfig(
                        apiIp,
                        apiPort,
                        apiType,
                        params);

        final MinerFactory minerFactory =
                apiType.toFactory(
                        apiPort,
                        minerConfig,
                        Collections.emptyList(),
                        Collections.emptyMap(),
                        new TypeMapping.Builder().build(),
                        (config, nicehash, autominer, claymore) ->
                                Collections.emptyList());

        final Miner miner =
                MinerUtils.toMiner(
                        apiPort,
                        minerConfig,
                        minerFactory);
        final MinerStats minerStats =
                miner.getStats();

        // For now, only ASICs have stats
        final Map<String, Object> rawStats =
                minerStats
                        .getAsics()
                        .stream()
                        .map(Asic::getRawStats)
                        .collect(HashMap::new, Map::putAll, Map::putAll);
        callback.done(
                builder
                        .result(
                                ImmutableMap.of(
                                        "stats",
                                        rawStats))
                        .status(
                                CommandDone.Status
                                        .builder()
                                        .type(DoneStatus.SUCCESS)
                                        .build())
                        .build());
    }
}