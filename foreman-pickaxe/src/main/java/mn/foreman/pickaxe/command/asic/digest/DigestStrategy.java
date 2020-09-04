package mn.foreman.pickaxe.command.asic.digest;

import mn.foreman.api.ForemanApi;
import mn.foreman.model.command.CommandDone;
import mn.foreman.model.command.CommandStart;
import mn.foreman.model.command.DoneStatus;
import mn.foreman.pickaxe.command.CommandStrategy;

import com.google.common.collect.ImmutableMap;

import java.util.Map;
import java.util.Optional;

import static mn.foreman.pickaxe.command.util.CommandUtils.safeGet;

/**
 * Performs an HTTP operation against an endpoint that requires digest
 * authentication.
 */
public class DigestStrategy
        implements CommandStrategy {

    @Override
    public void runCommand(
            final CommandStart command,
            final ForemanApi foremanApi,
            final CommandDone.CommandDoneBuilder builder,
            final Callback callback) {
        final Map<String, Object> args = command.args;

        final Optional<DigestType> typeOptional =
                DigestType.forType(
                        safeGet(
                                args,
                                "type"));
        if (typeOptional.isPresent()) {
            final String ip =
                    safeGet(
                            args,
                            "ip");
            final String port =
                    safeGet(
                            args,
                            "port");

            final DigestType type =
                    typeOptional.get();

            final mn.foreman.digest.DigestStrategy digestStrategy =
                    type.getStrategy();
            final Optional<String> response =
                    digestStrategy.runDigestOp(
                            ip,
                            port,
                            args);
            callback.done(
                    builder
                            .result(
                                    ImmutableMap.of(
                                            "response",
                                            response.orElse("")))
                            .status(
                                    CommandDone.Status
                                            .builder()
                                            .type(DoneStatus.SUCCESS)
                                            .build())
                            .build());
        }
    }
}
