package mn.foreman.io;

import mn.foreman.model.error.MinerException;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.common.collect.ImmutableMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Base64;
import java.util.concurrent.TimeUnit;

/** Provides utility methods for querying APIs. */
public class Query {

    /** The logger for this class. */
    private static final Logger LOG =
            LoggerFactory.getLogger(Query.class);

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
        return delimiterQuery(
                apiIp,
                apiPort,
                command,
                15,
                TimeUnit.SECONDS);
    }

    /**
     * Utility method to perform a query against a delimiter-based API.
     *
     * @param apiIp               The API IP.
     * @param apiPort             The API port.
     * @param command             The command.
     * @param connectTimeout      The connection timeout.
     * @param connectTimeoutUnits The connection timeout units.
     *
     * @return The response.
     *
     * @throws MinerException on failure to query.
     */
    public static String delimiterQuery(
            final String apiIp,
            final int apiPort,
            final String command,
            final int connectTimeout,
            final TimeUnit connectTimeoutUnits)
            throws MinerException {
        final ApiRequest request =
                new ApiRequestImpl(
                        apiIp,
                        apiPort,
                        command);
        final Connection connection =
                ConnectionFactory.createDelimiterConnection(
                        request,
                        connectTimeout,
                        connectTimeoutUnits);
        connection.query();

        final boolean completed =
                request.waitForCompletion(
                        connectTimeout,
                        connectTimeoutUnits);
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
     * @param type    The response class.
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
            final TypeReference<T> type)
            throws MinerException {
        return jsonQuery(
                apiIp,
                apiPort,
                command,
                type,
                10,
                TimeUnit.SECONDS);
    }

    /**
     * Utility method to perform a query against a JSON RPC API.
     *
     * @param apiIp               The API IP.
     * @param apiPort             The API port.
     * @param command             The command.
     * @param type                The response class.
     * @param connectTimeout      The connection timeout.
     * @param connectTimeoutUnits The connection timeout units.
     * @param <T>                 The response type.
     *
     * @return The response.
     *
     * @throws MinerException on failure to query.
     */
    public static <T> T jsonQuery(
            final String apiIp,
            final int apiPort,
            final String command,
            final TypeReference<T> type,
            final int connectTimeout,
            final TimeUnit connectTimeoutUnits)
            throws MinerException {
        final ApiRequest request =
                new ApiRequestImpl(
                        apiIp,
                        apiPort,
                        command);
        final Connection connection =
                ConnectionFactory.createJsonConnection(
                        request,
                        connectTimeout,
                        connectTimeoutUnits);
        connection.query();

        return query(
                request,
                type,
                connectTimeout,
                connectTimeoutUnits);
    }

    /**
     * Utility method to perform a query against a REST API.
     *
     * @param apiIp    The API IP.
     * @param apiPort  The API port.
     * @param uri      The URI.
     * @param username The username.
     * @param password The password.
     * @param type     The response class.
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
            final TypeReference<T> type)
            throws MinerException {
        return restQuery(
                apiIp,
                apiPort,
                uri,
                username,
                password,
                type,
                10,
                TimeUnit.SECONDS);
    }

    /**
     * Utility method to perform a query against a REST API.
     *
     * @param apiIp   The API IP.
     * @param apiPort The API port.
     * @param uri     The URI.
     * @param command The command.
     * @param type    The response class.
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
            final String command,
            final TypeReference<T> type)
            throws MinerException {
        return restQuery(
                apiIp,
                apiPort,
                uri,
                command,
                type,
                10,
                TimeUnit.SECONDS);
    }

    /**
     * Utility method to perform a query against a REST API.
     *
     * @param apiIp               The API IP.
     * @param apiPort             The API port.
     * @param uri                 The URI.
     * @param username            The username.
     * @param password            The password.
     * @param type                The response class.
     * @param connectTimeout      The connection timeout.
     * @param connectTimeoutUnits The connection timeout units.
     * @param <T>                 The response type.
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
            final TypeReference<T> type,
            final int connectTimeout,
            final TimeUnit connectTimeoutUnits)
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
                        "POST",
                        connectTimeout,
                        connectTimeoutUnits);
        connection.query();

        return query(
                request,
                type,
                connectTimeout,
                connectTimeoutUnits);
    }

    /**
     * Utility method to perform a query against a REST API.
     *
     * @param apiIp   The API IP.
     * @param apiPort The API port.
     * @param uri     The URI.
     * @param type    The response class.
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
            final TypeReference<T> type)
            throws MinerException {
        return restQuery(
                apiIp,
                apiPort,
                uri,
                "GET",
                type,
                2,
                TimeUnit.SECONDS);
    }

    /**
     * Utility method to perform a query against a REST API.
     *
     * @param apiIp               The API IP.
     * @param apiPort             The API port.
     * @param uri                 The URI.
     * @param command             The command.
     * @param type                The response class.
     * @param connectTimeout      The connection timeout.
     * @param connectTimeoutUnits The connection timeout units.
     * @param <T>                 The response type.
     *
     * @return The response.
     *
     * @throws MinerException on failure to query.
     */
    public static <T> T restQuery(
            final String apiIp,
            final int apiPort,
            final String uri,
            final String command,
            final TypeReference<T> type,
            final int connectTimeout,
            final TimeUnit connectTimeoutUnits)
            throws MinerException {
        final ApiRequest request =
                new ApiRequestImpl(
                        apiIp,
                        apiPort,
                        uri);

        final Connection connection =
                ConnectionFactory.createRestConnection(
                        request,
                        command,
                        connectTimeout,
                        connectTimeoutUnits);
        connection.query();

        return query(
                request,
                type,
                connectTimeout,
                connectTimeoutUnits);
    }

    /**
     * Runs the query.
     *
     * @param request             The request.
     * @param type                The response class.
     * @param connectTimeout      The connection timeout.
     * @param connectTimeoutUnits The connection timeout units.
     * @param <T>                 The response type.
     *
     * @return The response.
     *
     * @throws MinerException on failure to query.
     */
    private static <T> T query(
            final ApiRequest request,
            final TypeReference<T> type,
            final int connectTimeout,
            final TimeUnit connectTimeoutUnits)
            throws MinerException {
        T response;
        if (request.waitForCompletion(
                connectTimeout,
                connectTimeoutUnits)) {
            final ObjectMapper objectMapper =
                    new ObjectMapper()
                            .registerModule(new JavaTimeModule());
            try {
                final String responseJson = request.getResponse();
                LOG.debug("Received API response: {}", responseJson);
                response =
                        objectMapper.readValue(
                                request.getResponse(),
                                type);
            } catch (final Exception e) {
                throw new MinerException(e);
            }
        } else {
            throw new MinerException("Failed to obtain a response");
        }
        return response;
    }
}
