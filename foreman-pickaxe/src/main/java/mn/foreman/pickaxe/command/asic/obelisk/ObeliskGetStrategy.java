package mn.foreman.pickaxe.command.asic.obelisk;

import mn.foreman.api.model.CommandDone;
import mn.foreman.api.model.CommandStart;
import mn.foreman.api.model.DoneStatus;
import mn.foreman.obelisk.ObeliskQuery;
import mn.foreman.pickaxe.command.CommandCompletionCallback;
import mn.foreman.pickaxe.command.CommandStrategy;

import com.google.common.collect.ImmutableMap;
import org.apache.commons.lang3.exception.ExceptionUtils;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import static mn.foreman.pickaxe.command.util.CommandUtils.safeGet;

/** Performs an Obelisk GET. */
public class ObeliskGetStrategy
        implements CommandStrategy {

    @Override
    public void runCommand(
            final CommandStart command,
            final CommandCompletionCallback commandCompletionCallback,
            final CommandDone.CommandDoneBuilder builder) {
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
        final String username =
                args.getOrDefault("username", "").toString();
        final String password =
                args.getOrDefault("password", "").toString();
        final String uri =
                safeGet(
                        args,
                        "uri");

        final AtomicReference<String> data = new AtomicReference<>();
        try {
            ObeliskQuery.runSessionQuery(
                    ip,
                    port,
                    uri,
                    false,
                    username,
                    password,
                    5,
                    TimeUnit.SECONDS,
                    Collections.emptyList(),
                    (code, body) -> null,
                    null,
                    data::set,
                    new HashMap<>());
        } catch (final Exception e1) {
            try {
                data.set(ExceptionUtils.getStackTrace(e1));
            } catch (final Exception e2) {
                data.set("Exception occurred: " + e2.getMessage());
            }
        }

        commandCompletionCallback.done(
                command.id,
                builder
                        .result(
                                ImmutableMap.of(
                                        "data",
                                        data.get()))
                        .status(
                                CommandDone.Status
                                        .builder()
                                        .type(DoneStatus.SUCCESS)
                                        .build())
                        .build());
    }
}
