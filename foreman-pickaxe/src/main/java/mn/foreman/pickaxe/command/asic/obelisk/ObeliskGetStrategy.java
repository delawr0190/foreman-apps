package mn.foreman.pickaxe.command.asic.obelisk;

import mn.foreman.api.ForemanApi;
import mn.foreman.api.model.CommandDone;
import mn.foreman.api.model.CommandStart;
import mn.foreman.api.model.DoneStatus;
import mn.foreman.obelisk.ObeliskQuery;
import mn.foreman.pickaxe.command.CommandStrategy;

import com.google.common.collect.ImmutableMap;
import org.apache.commons.lang3.exception.ExceptionUtils;

import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

import static mn.foreman.pickaxe.command.util.CommandUtils.safeGet;

/** Performs an Obelisk GET. */
public class ObeliskGetStrategy
        implements CommandStrategy {

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
                    ObeliskQuery.Context
                            .builder()
                            .apiIp(ip)
                            .apiPort(port)
                            .uri(uri)
                            .method("GET")
                            .username(username)
                            .password(password)
                            .rawResponseCallback(data::set)
                            .responseClass(Object.class)
                            .responseCallback(dashboard -> {
                            })
                            .build());
        } catch (final Exception e) {
            data.set(ExceptionUtils.getStackTrace(e));
        }

        callback.done(
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
