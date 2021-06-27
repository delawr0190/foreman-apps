package mn.foreman.pickaxe.command.asic.discover;

import mn.foreman.api.model.CommandDone;
import mn.foreman.api.model.CommandStart;
import mn.foreman.api.model.DoneStatus;
import mn.foreman.discover.Discovery;
import mn.foreman.pickaxe.command.CommandCompletionCallback;
import mn.foreman.pickaxe.command.CommandStrategy;

import com.google.common.collect.ImmutableMap;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static mn.foreman.pickaxe.command.util.CommandUtils.safeGet;

/**
 * Obtains cgminer responses from the desired miner and returns them to Foreman
 * so that the miner can be added to the list of supported miners.
 */
public class DiscoverStrategy
        implements CommandStrategy {

    @Override
    public void runCommand(
            final CommandStart command,
            final CommandCompletionCallback commandCompletionCallback,
            final CommandDone.CommandDoneBuilder builder) {
        final Map<String, Object> args = command.args;

        final Optional<DiscoverType> typeOptional =
                DiscoverType.forType(
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

            final DiscoverType type =
                    typeOptional.get();
            final mn.foreman.discover.DiscoverStrategy discoverStrategy =
                    type.getStrategy();

            final List<Discovery> discoveries =
                    discoverStrategy.discover(
                            ip,
                            Integer.parseInt(port));
            commandCompletionCallback.done(
                    command.id,
                    builder
                            .result(
                                    ImmutableMap.of(
                                            "discoveries",
                                            discoveries))
                            .status(
                                    CommandDone.Status
                                            .builder()
                                            .type(DoneStatus.SUCCESS)
                                            .build())
                            .build());
        }
    }
}
