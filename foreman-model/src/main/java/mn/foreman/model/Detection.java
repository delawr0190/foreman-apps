package mn.foreman.model;

import lombok.Builder;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/** A {@link Detection} represents a fully identified miner. */
@Data
@Builder
public class Detection {

    /** The miner IP address. */
    private final String ipAddress;

    /** The MAC address. */
    private final String macAddress;

    /** The type. */
    private final MinerType minerType;

    /** The port. */
    private final int port;

    /** The parameters. */
    @Builder.Default
    private Map<String, Object> parameters = new HashMap<>();
}