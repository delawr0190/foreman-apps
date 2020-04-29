package mn.foreman.pickaxe.command.discover;

import mn.foreman.api.ForemanApi;
import mn.foreman.discover.Discovery;
import mn.foreman.model.command.CommandDone;
import mn.foreman.model.command.CommandStart;
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
            final ForemanApi foremanApi,
            final CommandDone.CommandDoneBuilder builder) {
        final Map<String, String> args = command.args;

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
            builder.result(
                    ImmutableMap.of(
                            "discoveries",
                            discoveries));
        }
    }
}
