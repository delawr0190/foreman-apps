package mn.foreman.io;

import mn.foreman.model.error.MinerException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;

import java.util.Base64;
import java.util.concurrent.TimeUnit;

/** Provides utility methods for querying APIs. */
public class Query {

    /**
     * Constructor.
     *
     * Note: intentionally hidden.
     */
    private Query() {
        // Do nothing
    }

    /**
     * Utility method to perform a query against a delimiter-based API.
     *
     * @param apiIp   The API IP.
     * @param apiPort The API port.
     * @param command The command.
     *
     * @return The response.
     *
     * @throws MinerException on failure to query.
     */
    public static String delimiterQuery(
            final String apiIp,
            final int apiPort,
            final String command)
            throws MinerException {
        final ApiRequest request =
                new ApiRequestImpl(
                        apiIp,
                        apiPort,
                        command);
        final Connection connection =
                ConnectionFactory.createDelimiterConnection(
                        request);
        connection.query();

        final boolean completed =
                request.waitForCompletion(
                        10,
                        TimeUnit.SECONDS);
        final String response =
                request.getResponse();
        if (!completed || response == null) {
            throw new MinerException("Failed to obtain a response");
        }

        return response;
    }

    /**
     * Utility method to perform a query against a JSON RPC API.
     *
     * @param apiIp   The API IP.
     * @param apiPort The API port.
     * @param command The command.
     * @param clazz   The response class.
     * @param <T>     The response type.
     *
     * @return The response.
     *
     * @throws MinerException on failure to query.
     */
    public static <T> T jsonQuery(
            final String apiIp,
            final int apiPort,
            final String command,
            final Class<T> clazz)
            throws MinerException {
        final ApiRequest request =
                new ApiRequestImpl(
                        apiIp,
                        apiPort,
                        command);
        final Connection connection =
                ConnectionFactory.createJsonConnection(
                        request);
        connection.query();

        return
                query(
                        request,
                        clazz);
    }

    /**
     * Utility method to perform a query against a REST API.
     *
     * @param apiIp    The API IP.
     * @param apiPort  The API port.
     * @param uri      The URI.
     * @param username The username.
     * @param password The password.
     * @param clazz    The response class.
     * @param <T>      The response type.
     *
     * @return The response.
     *
     * @throws MinerException on failure to query.
     */
    public static <T> T restQuery(
            final String apiIp,
            final int apiPort,
            final String uri,
            final String username,
            final String password,
            final Class<T> clazz)
            throws MinerException {
        final String auth =
                Base64.getEncoder().encodeToString(
                        (username + ":" + password).getBytes());
        final ApiRequest request =
                new ApiRequestImpl(
                        apiIp,
                        apiPort,
                        uri,
                        ImmutableMap.of(
                                "Authorization",
                                "Basic " + auth));

        final Connection connection =
                ConnectionFactory.createRestConnection(
                        request,
                        "POST");
        connection.query();

        return
                query(
                        request,
                        clazz);
    }

    /**
     * Utility method to perform a query against a REST API.
     *
     * @param apiIp   The API IP.
     * @param apiPort The API port.
     * @param uri     The URI.
     * @param clazz   The response class.
     * @param <T>     The response type.
     *
     * @return The response.
     *
     * @throws MinerException on failure to query.
     */
    public static <T> T restQuery(
            final String apiIp,
            final int apiPort,
            final String uri,
            final Class<T> clazz)
            throws MinerException {
        final ApiRequest request =
                new ApiRequestImpl(
                        apiIp,
                        apiPort,
                        uri);

        final Connection connection =
                ConnectionFactory.createRestConnection(
                        request,
                        "GET");
        connection.query();

        return
                query(
                        request,
                        clazz);
    }

    /**
     * Runs the query.
     *
     * @param request The request.
     * @param clazz   The response class.
     * @param <T>     The response type.
     *
     * @return The response.
     *
     * @throws MinerException on failure to query.
     */
    private static <T> T query(
            final ApiRequest request,
            final Class<T> clazz)
            throws MinerException {
        T response;
        if (request.waitForCompletion(
                10,
                TimeUnit.SECONDS)) {
            final ObjectMapper objectMapper =
                    new ObjectMapper();
            try {
                response =
                        objectMapper.readValue(
                                request.getResponse(),
                                clazz);
            } catch (final Exception e) {
                throw new MinerException(e);
            }
        } else {
            throw new MinerException("Failed to obtain a response");
        }
        return response;
    }
}
