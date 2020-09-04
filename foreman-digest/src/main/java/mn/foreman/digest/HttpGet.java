package mn.foreman.digest;

import mn.foreman.io.Query;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

/** A {@link DigestStrategy} that performs an HTTP GET. */
public class HttpGet
        implements DigestStrategy {

    @Override
    public Optional<String> runDigestOp(
            final String ip,
            final String port,
            final Map<String, Object> args) {
        final AtomicReference<String> response =
                new AtomicReference<>();
        try {
            Query.digestGet(
                    ip,
                    Integer.parseInt(port),
                    args.getOrDefault("realm", "").toString(),
                    args.getOrDefault("path", "").toString(),
                    args.getOrDefault("username", "").toString(),
                    args.getOrDefault("password", "").toString(),
                    (integer, s) -> response.set(s));
        } catch (final Exception e) {
            // Ignore
        }
        return Optional.ofNullable(response.get());
    }
}
