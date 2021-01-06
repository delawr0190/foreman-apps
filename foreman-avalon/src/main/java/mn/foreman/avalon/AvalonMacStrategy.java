package mn.foreman.avalon;

import mn.foreman.model.MacStrategy;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Optional;

/** A strategy for obtaining MACs from an Avalon. */
public class AvalonMacStrategy
        implements MacStrategy {

    /** The logger for this class. */
    private static final Logger LOG =
            LoggerFactory.getLogger(AvalonMacStrategy.class);

    /** The MAC key. */
    private static final String MAC_KEY = "\"mac\":";

    /** The IP. */
    private final String ip;

    /** The port. */
    private final int port;

    /**
     * Constructor.
     *
     * @param ip   The IP.
     * @param port The port.
     */
    public AvalonMacStrategy(
            final String ip,
            final int port) {
        this.ip = ip;
        this.port = port;
    }

    @Override
    public Optional<String> getMacAddress() {
        Optional<String> mac = query("/get_minerinfo.cgi");
        if (!mac.isPresent()) {
            mac = query("/updatedashboard.cgi");
        }
        return mac;
    }

    /**
     * Converts the response to a MAC.
     *
     * @param response The response.
     *
     * @return The MAC.
     */
    private static String toMac(final String response) {
        String macString = response;
        final int macStart = macString.indexOf(MAC_KEY);

        // Remove up to the start of the MAC value
        macString =
                macString.substring(macStart + MAC_KEY.length());

        // Remove after the end of the MAC value
        macString =
                macString.substring(
                        0,
                        macString.indexOf(","));

        return macString.replace("\"", "");
    }

    /**
     * Queries for the MAC.
     *
     * @param uri The URI.
     *
     * @return The MAC.
     */
    private Optional<String> query(final String uri) {
        String mac = null;
        try {
            final URL url =
                    new URL(
                            String.format(
                                    "http://%s:%d%s",
                                    this.ip,
                                    this.port,
                                    uri));
            final HttpURLConnection connection =
                    (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(100);
            connection.setReadTimeout(1000);

            final int code = connection.getResponseCode();
            if (code == HttpURLConnection.HTTP_OK) {
                try (final InputStreamReader inputStreamReader =
                             new InputStreamReader(
                                     connection.getInputStream());
                     final BufferedReader reader =
                             new BufferedReader(
                                     inputStreamReader)) {
                    final String response =
                            IOUtils.toString(reader);
                    LOG.info("Obtained Avalon response: {}", response);
                    mac = toMac(response);
                }
            } else {
                LOG.warn("Failed to obtain MAC: {}", code);
            }
        } catch (final Exception e) {
            LOG.warn("Failed to obtain MAC", e);
        }
        return Optional.ofNullable(mac);
    }
}
