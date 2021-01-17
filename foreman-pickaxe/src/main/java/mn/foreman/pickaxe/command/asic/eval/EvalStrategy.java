package mn.foreman.pickaxe.command.asic.eval;

import mn.foreman.api.ForemanApi;
import mn.foreman.api.model.CommandDone;
import mn.foreman.api.model.CommandStart;
import mn.foreman.api.model.DoneStatus;
import mn.foreman.io.Query;
import mn.foreman.pickaxe.command.CommandStrategy;

import com.google.common.collect.ImmutableMap;
import org.apache.commons.lang3.exception.ExceptionUtils;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static mn.foreman.pickaxe.command.util.CommandUtils.safeGet;

/** Sends commands to a remote miner, used for debugging purposes. */
public class EvalStrategy
        implements CommandStrategy {

    @SuppressWarnings("unchecked")
    @Override
    public void runCommand(
            final CommandStart command,
            final ForemanApi foremanApi,
            final CommandDone.CommandDoneBuilder builder,
            final Callback callback) {
        final Map<String, Object> args = command.args;

        final String ip =
                safeGet(
                        args,
                        "ip");
        final int port =
                Integer.parseInt(
                        safeGet(
                                args,
                                "port"));
        final List<String> commands =
                (List<String>) args.getOrDefault(
                        "commands",
                        Collections.emptyList());

        final List<Map<String, String>> results = new LinkedList<>();

        for (final String toRun : commands) {
            String response;
            try {
                response =
                        Query.delimiterQuery(
                                ip,
                                port,
                                toRun,
                                5,
                                TimeUnit.MINUTES);
            } catch (final Exception e) {
                response = ExceptionUtils.getStackTrace(e);
            }
            results.add(
                    ImmutableMap.of(
                            "command",
                            toRun,
                            "response",
                            response));
        }

        callback.done(
                builder
                        .result(
                                ImmutableMap.of(
                                        "results",
                                        results))
                        .status(
                                CommandDone.Status
                                        .builder()
                                        .type(DoneStatus.SUCCESS)
                                        .build())
                        .build());
    }
}
