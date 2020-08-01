package mn.foreman.dragonmint;

import mn.foreman.model.AsyncRebootStrategy;
import mn.foreman.model.MinerFactory;
import mn.foreman.model.RebootStrategy;
import mn.foreman.model.error.MinerException;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * A {@link DragonmintRebootStrategy} provides a {@link RebootStrategy}
 * implementation that will reboot a dragonmint device.
 */
public class DragonmintRebootStrategy
        extends AsyncRebootStrategy {

    /**
     * Constructor.
     *
     * @param delay        The delay.
     * @param interval     The interval.
     * @param units        The units.
     * @param threadPool   The thread pool.
     * @param minerFactory The miner factory.
     */
    public DragonmintRebootStrategy(
            final int delay,
            final int interval,
            final TimeUnit units,
            final ScheduledThreadPoolExecutor threadPool,
            final MinerFactory minerFactory) {
        super(
                delay,
                interval,
                units,
                threadPool,
                minerFactory);
    }

    @Override
    public boolean doReboot(
            final String ip,
            final int port,
            final Map<String, Object> parameters)
            throws MinerException {
        return DragonmintQuery.runQuery(
                ip,
                port,
                "/api/reboot",
                parameters,
                Collections.emptyList());
    }
}
