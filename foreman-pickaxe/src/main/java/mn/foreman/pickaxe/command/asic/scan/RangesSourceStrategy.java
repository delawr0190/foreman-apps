package mn.foreman.pickaxe.command.asic.scan;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * An {@link IpSourceStrategy} implementation that will return a list of IPs to
 * scan that matches the provided IP ranges sent in the command.
 */
public class RangesSourceStrategy
        implements IpSourceStrategy {

    @SuppressWarnings("unchecked")
    @Override
    public BlockingQueue<String> toIps(final Map<String, Object> args) {
        final List<String> ranges =
                (List<String>) args.getOrDefault(
                        "ranges",
                        new LinkedList<String>());
        return new LinkedBlockingDeque<>(ranges);
    }
}
