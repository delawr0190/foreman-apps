package mn.foreman.model.mac;

import lombok.Builder;
import lombok.Data;

/** An update request to update a miner's MAC address. */
@Data
@Builder
public class MacUpdate {

    /** The miner API port. */
    private final int apiPort;

    /** The miner IP. */
    private final String ip;

    /** The new MAC address. */
    private final String mac;
}
