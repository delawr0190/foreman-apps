package mn.foreman.obelisk;

import mn.foreman.io.*;
import mn.foreman.model.AbstractMiner;
import mn.foreman.model.error.MinerException;
import mn.foreman.model.miners.FanInfo;
import mn.foreman.model.miners.MinerStats;
import mn.foreman.model.miners.Pool;
import mn.foreman.model.miners.asic.Asic;
import mn.foreman.obelisk.json.Dashboard;
import mn.foreman.obelisk.json.Info;
import mn.foreman.util.PoolUtils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.http.Header;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * <h1>Overview</h1>
 *
 * A {@link Obelisk} represents a remote obelisk instance.
 *
 * <p>This class will query the <pre>/api/info</pre> and then interpret
 * the metrics according the the miner generation that's discovered.</p>
 */
public class Obelisk
        extends AbstractMiner {

    /** The API password. */
    private final String password;

    /** The API username. */
    private final String username;

    /** The session ID. */
    private String sessionId;

    /**
     * Constructor.
     *
     * @param apiIp    The API IP.
     * @param apiPort  The API port.
     * @param username The username.
     * @param password The password.
     */
    Obelisk(
            final String apiIp,
            final int apiPort,
            final String username,
            final String password) {
        super(
                apiIp,
                apiPort);
        this.username = username;
        this.password = password;
    }

    @Override
    protected void addStats(
            final MinerStats.Builder statsBuilder)
            throws MinerException {
        final Info info =
                Query.restQuery(
                        this.apiIp,
                        this.apiPort,
                        "/api/info",
                        new TypeReference<Info>() {
                        });
        final Optional<ObeliskType> type =
                ObeliskType.forType(
                        info.model);
        if (type.isPresent()) {
            final ObeliskType obeliskType =
                    type.get();
            try {
                addStatsForGen(
                        obeliskType.getGen(),
                        statsBuilder);
            } catch (final Exception e) {
                throw new MinerException(e);
            }
        } else {
            throw new MinerException("No obelisk found");
        }
    }

    @Override
    protected void addToEquals(
            final EqualsBuilder equalsBuilder,
            final AbstractMiner other) {
        final Obelisk otherMiner = (Obelisk) other;
        equalsBuilder
                .append(this.username, otherMiner.username)
                .append(this.password, otherMiner.password);
    }

    @Override
    protected void addToHashCode(
            final HashCodeBuilder hashCodeBuilder) {
        hashCodeBuilder
                .append(this.username)
                .append(this.password);
    }

    @Override
    protected String addToString() {
        return String.format(
                ", username=%s, password=%s",
                this.username,
                this.password);
    }

    /**
     * Adds an ASIC from the provided devices and hardware.
     *
     * @param statsBuilder The stats builder.
     * @param dashboard    The dashboard stats.
     * @param gen          The generation.
     */
    private static void addAsics(
            final MinerStats.Builder statsBuilder,
            final Dashboard dashboard,
            final ObeliskGen gen) {
        final Asic.Builder asicBuilder =
                new Asic.Builder();
        asicBuilder.setHashRate(toHashRate(dashboard));

        // Fans
        final FanInfo.Builder fanBuilder =
                new FanInfo.Builder()
                        .setCount(gen.getNumFans())
                        .setSpeedUnits(gen.getFanSpeedUnits());
        for (final String fanKey : gen.getFanKeys()) {
            dashboard
                    .systemInfo
                    .stream()
                    .filter(info -> fanKey.equals(info.name))
                    .findFirst()
                    .ifPresent(info -> fanBuilder.addSpeed(info.value));
        }
        asicBuilder.setFanInfo(fanBuilder.build());

        // Temps
        dashboard
                .hashboards
                .forEach(board -> {
                    asicBuilder.addTemp(board.temp1);
                    asicBuilder.addTemp(board.temp2);
                });

        statsBuilder.addAsic(asicBuilder.build());
    }

    /**
     * Adds the {@link Pool}.
     *
     * @param statsBuilder The builder to update.
     * @param pool         The {@link Pool}.
     */
    private static void addPool(
            final MinerStats.Builder statsBuilder,
            final Dashboard.Pool pool) {
        statsBuilder.addPool(
                new Pool.Builder()
                        .setName(PoolUtils.sanitizeUrl(pool.url))
                        .setStatus(
                                !"Disabled".equals(pool.status),
                                "Alive".equals(pool.status))
                        .setCounts(
                                pool.accepted,
                                pool.rejected,
                                0)
                        .build());
    }

    /**
     * Adds the {@link Pool Pools}.
     *
     * @param statsBuilder The builder to update.
     * @param dashboard    The dashboard stats.
     */
    private static void addPools(
            final MinerStats.Builder statsBuilder,
            final Dashboard dashboard) {
        dashboard
                .pools
                .forEach(
                        pool ->
                                addPool(
                                        statsBuilder,
                                        pool));
    }

    /**
     * Adds up the hash rates from the provided devices.
     *
     * @param dashboard The dashboard stats.
     *
     * @return The hash rate.
     */
    private static BigDecimal toHashRate(
            final Dashboard dashboard) {
        return dashboard
                .hashboards
                .stream()
                .map(hashboard -> hashboard.hashrate)
                .map((value) ->
                        value.multiply(
                                new BigDecimal(1000 * 1000)))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    /**
     * Adds stats for the provided obelisk generation.
     *
     * @param gen          The generation.
     * @param statsBuilder The stats.
     *
     * @throws MinerException on failure to query.
     */
    private void addStatsForGen(
            final ObeliskGen gen,
            final MinerStats.Builder statsBuilder)
            throws Exception {
        try {
            // Login first
            query(
                    "/api/login",
                    true,
                    Object.class);

            // Get the dashboard stats
            final Optional<Dashboard> dashboardOptional =
                    query(
                            "/api/status/dashboard",
                            false,
                            Dashboard.class);
            if (dashboardOptional.isPresent()) {
                final Dashboard dashboard = dashboardOptional.get();
                addAsics(
                        statsBuilder,
                        dashboard,
                        gen);
                addPools(
                        statsBuilder,
                        dashboard);
            } else {
                throw new MinerException("Failed to obtain dashboard status");
            }
        } finally {
            // Logout
            query(
                    "/api/logout",
                    false,
                    Object.class);
        }
    }

    /**
     * Processes all of the obelisk response headers.
     *
     * @param headers The Obelisk response headers.
     */
    private void processHeaders(final Map<String, String> headers) {
        // Update the session cookie
        for (final Map.Entry<String, String> entry : headers.entrySet()) {
            if ("set-cookie".equals(entry.getKey().toLowerCase())) {
                final String cookie = entry.getValue();
                if (cookie.contains("sessionid")) {
                    this.sessionId =
                            cookie
                                    .split(";")[0]
                                    .replace("sessionid=", "");
                }
            }
        }
    }

    /**
     * Queries an Obelisk.
     *
     * @param uri     The API URI.
     * @param isLogin Whether or not the request is a login attempt.
     * @param clazz   The class of the response.
     * @param <T>     The response type.
     *
     * @return The response, if present.
     *
     * @throws IOException    on failure to parse JSON.
     * @throws MinerException on failure to communicate with the miner.
     */
    private <T> Optional<T> query(
            final String uri,
            final boolean isLogin,
            final Class<T> clazz)
            throws
            IOException,
            MinerException {
        T result = null;

        final ObjectMapper objectMapper =
                new ObjectMapper();

        final String json;
        if (isLogin) {
            // Only include username and password on login
            final Map<String, String> content =
                    ImmutableMap.of(
                            "username",
                            username,
                            "password",
                            password);
            json = objectMapper.writeValueAsString(content);
        } else {
            json = null;
        }

        final ApiRequest apiRequest =
                new ApiRequestImpl(
                        this.apiIp,
                        this.apiPort,
                        uri,
                        toCookies(),
                        json);

        final Map<String, String> headerMap = new HashMap<>();

        final Connection connection =
                ConnectionFactory.createRestConnection(
                        apiRequest,
                        "POST",
                        5,
                        TimeUnit.SECONDS,
                        headers -> {
                            for (final Header header : headers) {
                                headerMap.put(
                                        header.getName(),
                                        header.getValue());
                            }
                        });
        connection.query();

        if (apiRequest.waitForCompletion(
                5,
                TimeUnit.SECONDS)) {
            processHeaders(headerMap);
            final String response = apiRequest.getResponse();
            if (response != null && !response.isEmpty()) {
                result =
                        objectMapper.readValue(
                                response,
                                clazz);
            }
        } else {
            throw new MinerException("Failed to obtain response from obelisk");
        }

        if (isLogin && this.sessionId == null) {
            throw new MinerException("Failed to login to obelisk");
        }

        return Optional.ofNullable(result);
    }

    /**
     * Creates an HTTP cookie map.
     *
     * @return The cookies.
     */
    private Map<String, String> toCookies() {
        return this.sessionId != null
                ? ImmutableMap.of("Cookie", "sessionid=" + this.sessionId)
                : Collections.emptyMap();
    }
}