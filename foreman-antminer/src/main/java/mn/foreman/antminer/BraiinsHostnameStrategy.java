package mn.foreman.antminer;

import mn.foreman.model.error.MinerException;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

/** Obtains the hostname from bOS. */
public class BraiinsHostnameStrategy
        implements HostnameStrategy {

    @Override
    public Optional<String> getHostname(
            final String ip,
            final int port,
            final Map<String, Object> args) throws MinerException {
        final AtomicReference<String> hostname = new AtomicReference<>();
        BraiinsUtils.runMinerCommand(
                ip,
                args.getOrDefault("username", "").toString(),
                args.getOrDefault("password", "").toString(),
                "echo $HOSTNAME",
                hostname::set);
        return Optional.ofNullable(hostname.get())
                .map(host -> host.replace("\n", ""));
    }

}
