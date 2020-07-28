package mn.foreman.dragonmint;

import mn.foreman.io.Query;
import mn.foreman.model.error.MinerException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

/** Utilities for querying a dragonmint. */
public class DragonmintQuery {

    /**
     * Runs a dragonmint query.
     *
     * @param ip         The IP.
     * @param port       The port.
     * @param uri        The URI.
     * @param parameters The parameters.
     * @param content    The content.
     *
     * @return Whether or not the query was successful.
     *
     * @throws MinerException on failure to query.
     */
    public static boolean runQuery(
            final String ip,
            final int port,
            final String uri,
            final Map<String, Object> parameters,
            final List<Map<String, Object>> content) throws MinerException {
        boolean success;

        try {
            final String username =
                    (String) parameters.getOrDefault("username", "");
            final String password =
                    (String) parameters.getOrDefault("password", "");

            final AtomicReference<String> responseRef =
                    new AtomicReference<>();
            Query.restQuery(
                    ip,
                    port,
                    uri,
                    username,
                    password,
                    content,
                    responseRef::set);

            final String response = responseRef.get();
            success = response != null && response.contains("true");
        } catch (final IOException | URISyntaxException e) {
            throw new MinerException(e);
        }

        return success;
    }
}
