package mn.foreman.antminer;

import mn.foreman.model.MacStrategy;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

/** Obtains the MAC from a BraiinsOS miner. */
public class BraiinsMacStrategy
        implements MacStrategy {

    /** The logger for this class. */
    private static final Logger LOG =
            LoggerFactory.getLogger(BraiinsMacStrategy.class);

    /** The mapper for parsing json. */
    private static final ObjectMapper OBJECT_MAPPER =
            new ObjectMapper();

    /** The IP. */
    private final String ip;

    /** The password. */
    private final String password;

    /** The port. */
    private final int port;

    /** The username. */
    private final String username;

    /**
     * Constructor.
     *
     * @param ip       The IP.
     * @param port     The port.
     * @param username The username.
     * @param password The password.
     */
    public BraiinsMacStrategy(
            final String ip,
            final int port,
            final String username,
            final String password) {
        this.ip = ip;
        this.port = port;
        this.username = username;
        this.password = password;
    }

    @Override
    public Optional<String> getMacAddress() {
        final AtomicReference<String> mac = new AtomicReference<>();
        try {
            BraiinsUtils.query(
                    this.ip,
                    this.port,
                    this.username,
                    this.password,
                    "/cgi-bin/luci/admin/status/overview?hosts=1",
                    true,
                    Collections.emptyList(),
                    null,
                    (statusCode, response) -> {
                        if (statusCode == HttpStatus.SC_OK) {
                            try {
                                final Map<String, Map<String, String>> data =
                                        OBJECT_MAPPER.readValue(
                                                response,
                                                new TypeReference<Map<String, Map<String, String>>>() {
                                                });
                                // Find the MAC that contains a matching IP
                                findMac(this.ip, data).ifPresent(mac::set);
                            } catch (final Exception e) {
                                LOG.warn("Exception occurred while querying", e);
                            }
                        }
                    });
        } catch (final Exception e) {
            // Ignore if we can't get the MAC
        }
        return Optional.ofNullable(mac.get());
    }

    /**
     * Finds the MAC address.
     *
     * @param ip   The IP.
     * @param data The data.
     *
     * @return The IP address.
     */
    private static Optional<String> findMac(
            final String ip,
            final Map<String, Map<String, String>> data) {
        for (final Map.Entry<String, Map<String, String>> entry : data.entrySet()) {
            final Map<String, String> value = entry.getValue();
            if (value.getOrDefault("ipv4", "").equals(ip) || value.getOrDefault("ipv6", "").equals(ip)) {
                return Optional.of(entry.getKey());
            }
        }
        return Optional.empty();
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