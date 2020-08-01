package mn.foreman.pickaxe.command.asic.pools;

import mn.foreman.api.ForemanApi;
import mn.foreman.model.Pool;
import mn.foreman.model.command.CommandDone;
import mn.foreman.model.command.CommandStart;
import mn.foreman.model.command.DoneStatus;
import mn.foreman.model.error.MinerException;
import mn.foreman.model.error.NotAuthenticatedException;
import mn.foreman.pickaxe.command.CommandStrategy;
import mn.foreman.pickaxe.command.asic.Manufacturer;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static mn.foreman.pickaxe.command.util.CommandUtils.safeGet;

/** Changes pools for the provided miner. */
public class ChangePoolsStrategy
        implements CommandStrategy {

    /** The logger for this class. */
    private static final Logger LOG =
            LoggerFactory.getLogger(ChangePoolsStrategy.class);

    @Override
    public void runCommand(
            final CommandStart command,
            final ForemanApi foremanApi,
            final CommandDone.CommandDoneBuilder builder,
            final Callback callback) {
        final Map<String, Object> args = command.args;

        final String type = safeGet(args, "type");
        final String ip = safeGet(args, "ip");
        final String port = safeGet(args, "port");
        final List<Pool> pools = toPools(args);

        switch (type) {
            case "asic":
                runAsicChangePools(
                        ip,
                        port,
                        pools,
                        args,
                        builder,
                        callback);
                break;
            default:
                break;
        }
    }

    /**
     * Converts the provided command args to a list of new pools.
     *
     * @param args The arguments to inspect.
     *
     * @return The new pools.
     */
    @SuppressWarnings("unchecked")
    private static List<Pool> toPools(final Map<String, Object> args) {
        final List<Map<String, String>> pools =
                (List<Map<String, String>>) args.get("pools");
        return pools
                .stream()
                .map(pool -> Pool
                        .builder()
                        .url(pool.getOrDefault("url", ""))
                        .username(pool.getOrDefault("user", ""))
                        .password(pool.getOrDefault("pass", ""))
                        .build())
                .collect(Collectors.toList());
    }

    /**
     * Changes the pools on an ASIC.
     *
     * @param ip       The ip.
     * @param port     The port.
     * @param pools    The pools.
     * @param args     The arguments.
     * @param builder  The builder to use for creating the final result.
     * @param callback The callback.
     */
    private void runAsicChangePools(
            final String ip,
            final String port,
            final List<Pool> pools,
            final Map<String, Object> args,
            final CommandDone.CommandDoneBuilder builder,
            final Callback callback) {
        final String manufacturer = safeGet(args, "manufacturer");
        final Optional<Manufacturer> type =
                Manufacturer.fromName(manufacturer);
        if (type.isPresent()) {
            final Manufacturer manufacturerType =
                    type.get();
            final mn.foreman.model.ChangePoolsStrategy strategy =
                    manufacturerType.getChangePoolsStrategy();

            LOG.info("Changing pools of {} ({}:{}) to {}",
                    manufacturer,
                    ip,
                    port,
                    pools);

            try {
                if (strategy.change(
                        ip,
                        Integer.parseInt(port),
                        args,
                        pools)) {
                    LOG.info("Pools were changed!");
                    builder.status(
                            CommandDone.Status
                                    .builder()
                                    .type(DoneStatus.SUCCESS)
                                    .build());
                } else {
                    LOG.warn("Failed to change pools");
                    builder.status(
                            CommandDone.Status
                                    .builder()
                                    .type(DoneStatus.FAILED)
                                    .message("Failed to change pools")
                                    .build());
                }
            } catch (final NotAuthenticatedException nae) {
                LOG.warn("Failed to authentication with the miner", nae);
                builder.status(
                        CommandDone.Status
                                .builder()
                                .type(DoneStatus.FAILED)
                                .message("Failed to authenticate with the miner")
                                .details(ExceptionUtils.getStackTrace(nae))
                                .build());
            } catch (final MinerException me) {
                LOG.warn("An unexpected exception occurred", me);
                builder.status(
                        CommandDone.Status
                                .builder()
                                .type(DoneStatus.FAILED)
                                .message("An unexpected exception occurred")
                                .details(ExceptionUtils.getStackTrace(me))
                                .build());
            }

            callback.done(builder.build());
        }
    }
}
