package mn.foreman.multminer;

import mn.foreman.model.AsyncRebootStrategy;
import mn.foreman.model.MinerFactory;
import mn.foreman.model.RebootStrategy;
import mn.foreman.model.error.MinerException;

import com.google.common.collect.ImmutableMap;

import java.util.Map;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * A {@link MultMinerRebootStrategy} provides a {@link RebootStrategy}
 * implementation that will reboot a multminer device.
 */
public class MultMinerRebootStrategy
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
    public MultMinerRebootStrategy(
            final long delay,
            final long interval,
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
        return MultMinerQuery.query(
                ip,
                port,
                "cfg",
                content ->
                        content.add(
                                ImmutableMap.of(
                                        "key",
                                        "reboot",
                                        "value",
                                        "系统重启")),
                () -> true);
    }
}