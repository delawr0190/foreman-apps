package mn.foreman.model;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

/** A {@link Detection} represents a fully identified miner. */
@Data
@Builder
public class Detection {

    /** The miner IP address. */
    private final String ipAddress;

    /** The type. */
    private final MinerType minerType;

    /** The parameters. */
    private final Map<String, String> parameters;

    /** The port. */
    private final int port;
}