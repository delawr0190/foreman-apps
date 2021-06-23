package mn.foreman.antminer;

import mn.foreman.antminer.error.NotAuthorizedException;
import mn.foreman.io.Query;
import mn.foreman.model.ApplicationConfiguration;
import mn.foreman.model.MacStrategy;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

/** Obtains the MAC from stock antminer firmware. */
public class StockMacStrategy
        implements MacStrategy {

    /** The logger for this class. */
    private static final Logger LOG =
            LoggerFactory.getLogger(StockMacStrategy.class);

    /** The mapper for parsing json. */
    private static final ObjectMapper OBJECT_MAPPER =
            new ObjectMapper();

    /** The configuration. */
    private final ApplicationConfiguration applicationConfiguration;

    /** The IP. */
    private final String ip;

    /** The password. */
    private final String password;

    /** The port. */
    private final int port;

    /** The realm. */
    private final String realm;

    /** The username. */
    private final String username;

    /**
     * Constructor.
     *
     * @param ip                       The IP.
     * @param port                     The port.
     * @param realm                    The realm.
     * @param username                 The username.
     * @param password                 The password.
     * @param applicationConfiguration The configuration.
     */
    public StockMacStrategy(
            final String ip,
            final int port,
            final String realm,
            final String username,
            final String password,
            final ApplicationConfiguration applicationConfiguration) {
        this.ip = ip;
        this.port = port;
        this.realm = realm;
        this.username = username;
        this.password = password;
        this.applicationConfiguration = applicationConfiguration;
    }

    @Override
    public Optional<String> getMacAddress() {
        final AtomicBoolean unauthorized = new AtomicBoolean(false);
        final AtomicReference<String> mac = new AtomicReference<>();
        try {
            Query.digestGet(
                    this.ip,
                    this.port,
                    this.realm,
                    "/cgi-bin/get_network_info.cgi",
                    this.username,
                    this.password,
                    (code, s) -> {
                        try {
                            if (s != null) {
                                unauthorized.set(s.toLowerCase().contains("unauthorized"));
                                final Map<String, Object> conf =
                                        OBJECT_MAPPER.readValue(
                                                s,
                                                new TypeReference<Map<String, Object>>() {
                                                });
                                mac.set(toMac(conf));
                            }
                        } catch (final IOException e) {
                            LOG.warn("Exception occurred while querying", e);
                        }
                    },
                    this.applicationConfiguration.getReadSocketTimeout());
        } catch (final Exception e) {
            // Ignore if we can't get the MAC
        }

        if (unauthorized.get()) {
            throw new NotAuthorizedException("Invalid credentials");
        }

        return Optional.ofNullable(mac.get());
    }

    /**
     * Gets the MAC if it exists.
     *
     * @param values The values.
     *
     * @return The MAC.
     */
    private static String toMac(final Map<String, Object> values) {
        if (values.containsKey("macaddr")) {
            return values.get("macaddr").toString();
        } else if (values.containsKey("conf_macaddr")) {
            return values.get("conf_macaddr").toString();
        } else {
            return null;
        }
    }
}