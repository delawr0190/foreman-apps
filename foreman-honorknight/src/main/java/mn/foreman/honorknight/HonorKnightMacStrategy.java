package mn.foreman.honorknight;

import mn.foreman.honorknight.response.Overview;
import mn.foreman.model.MacStrategy;

import java.util.Optional;

/** Strategy for obtaining a MAC from a HonorKnight. */
public class HonorKnightMacStrategy
        implements MacStrategy {

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
    public HonorKnightMacStrategy(
            final String ip,
            final int port) {
        this.ip = ip;
        this.port = port;
    }

    @Override
    public Optional<String> getMacAddress() {
        Optional<String> mac = Optional.empty();
        final Optional<Overview> overview =
                HonorKnightQuery.overview(
                        this.ip,
                        this.port);
        if (overview.isPresent()) {
            mac =
                    Optional.of(
                            overview.get().minerInfo.mac
                                    .replace(
                                            "\n",
                                            ""));
        }
        return mac;
    }
}