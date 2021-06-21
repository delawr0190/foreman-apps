package mn.foreman.pickaxe.command.asic.scan;

import java.util.Map;
import java.util.concurrent.BlockingQueue;

/**
 * An {@link IpSourceStrategy} provides a mechanism for generating a range of
 * IPs to scan based on the provided command arguments.
 */
public interface IpSourceStrategy {

    /**
     * Generates a list of IPs to scan from the provided arguments.
     *
     * @param args The arguments.
     *
     * @return The list of IPs to scan.
     */
    BlockingQueue<String> toIps(Map<String, Object> args);
}
