package mn.foreman.epic;

import mn.foreman.io.Query;
import mn.foreman.model.error.MinerException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;

import java.util.concurrent.atomic.AtomicReference;

/** Utilities for querying an epic. */
public class EpicQuery {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    /**
     * Runs an epic query.
     *
     * @param ip       The IP.
     * @param port     The port.
     * @param password The password.
     * @param uri      The URI.
     * @param param    The param.
     *
     * @return Whether or not the query was successful.
     *
     * @throws MinerException on failure to query.
     */
    public static boolean runQuery(
            final String ip,
            final int port,
            final String password,
            final String uri,
            final Object param) throws MinerException {
        boolean success;

        try {
            final AtomicReference<String> responseRef =
                    new AtomicReference<>();
            Query.post(
                    ip,
                    port,
                    uri,
                    null,
                    OBJECT_MAPPER.writeValueAsString(
                            ImmutableMap.of(
                                    "param",
                                    param,
                                    "password",
                                    password)),
                    (integer, s) -> responseRef.set(s));

            final String response = responseRef.get();
            success = response != null && response.toLowerCase().contains("true");
        } catch (final Exception e) {
            throw new MinerException(e);
        }

        return success;
    }
}
