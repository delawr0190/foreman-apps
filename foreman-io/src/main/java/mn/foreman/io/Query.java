package mn.foreman.io;

import mn.foreman.model.error.MinerException;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
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

        if (request.waitForCompletion(
                10,
                TimeUnit.SECONDS)) {
            return request.getResponse();
        } else {
            throw new MinerException("Failed to obtain a response");
        }
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
                        request);
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
            } catch (final IOException ioe) {
                throw new MinerException(ioe);
            }
        } else {
            throw new MinerException("Failed to obtain a response");
        }
        return response;
    }
}
