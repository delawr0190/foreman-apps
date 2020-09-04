package mn.foreman.digest;

import java.util.Map;
import java.util.Optional;

/**
 * A {@link DigestStrategy} provides a mechanism for performing an HTTP
 * operation that requires digest auth.
 */
public interface DigestStrategy {

    /**
     * Runs an HTTP operation that requires digest auth.
     *
     * @param ip   The ip.
     * @param port The port.
     * @param args The args.
     *
     * @return The response, if present.
     */
    Optional<String> runDigestOp(
            String ip,
            String port,
            Map<String, Object> args);
}
