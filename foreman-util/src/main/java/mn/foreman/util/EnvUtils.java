package mn.foreman.util;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/** Environmental utilites. */
public class EnvUtils {

    /**
     * Returns the hostname that was set when the application was ran.
     *
     * @return The hostname.
     */
    public static String getHostname() {
        String hostname = System.getenv("PICKAXE_HOST");
        if (hostname == null || hostname.isEmpty()) {
            try {
                hostname = InetAddress.getLocalHost().getHostName();
            } catch (final UnknownHostException e) {
                // Ignore
            }
        }
        return hostname;
    }

    /**
     * Returns the LAN ip address.
     *
     * @return The LAN ip address.
     */
    public static String getLanIp() {
        String ip = null;
        try (final Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress("google.com", 80));
            ip = socket.getLocalAddress().getHostAddress();
        } catch (final IOException e) {
            // Ignore
        }
        return ip;
    }
}
