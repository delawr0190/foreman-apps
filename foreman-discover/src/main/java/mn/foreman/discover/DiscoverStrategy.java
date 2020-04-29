package mn.foreman.discover;

import java.util.List;

public interface DiscoverStrategy {

    /**
     * Queries the provided ip and port for a response.
     *
     * @param ip   The ip.
     * @param port The port.
     *
     * @return Any {@link Discovery discoveries} that were found.
     */
    List<Discovery> discover(
            String ip,
            int port);
}