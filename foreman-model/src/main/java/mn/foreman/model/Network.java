package mn.foreman.model;

import lombok.Builder;
import lombok.Data;

/** A network configuration. */
@Data
@Builder
public class Network {

    /** The DNS entry. */
    public final String dns;

    /** The gateway. */
    public final String gateway;

    /** The hostname. */
    public final String hostname;

    /** The IP address. */
    public final String ipAddress;

    /** The netmask. */
    public final String netmask;
}
