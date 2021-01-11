package mn.foreman.antminer;

import mn.foreman.model.error.MinerException;

import java.util.Map;
import java.util.Optional;

/** Obtains a hostname. */
public interface HostnameStrategy {

    /**
     * Obtains the hostname.
     *
     * @param ip   The IP.
     * @param port The port.
     * @param args The args.
     *
     * @return The hostname.
     *
     * @throws MinerException on failure.
     */
    Optional<String> getHostname(
            final String ip,
            final int port,
            final Map<String, Object> args)
            throws MinerException;
}
