package mn.foreman.pickaxe.command.asic.digest;

import mn.foreman.api.model.CommandDone;
import mn.foreman.api.model.CommandStart;
import mn.foreman.api.model.DoneStatus;
import mn.foreman.pickaxe.command.CommandCompletionCallback;
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
            final CommandCompletionCallback commandCompletionCallback,
            final CommandDone.CommandDoneBuilder builder) {
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
            commandCompletionCallback.done(
                    command.id,
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
