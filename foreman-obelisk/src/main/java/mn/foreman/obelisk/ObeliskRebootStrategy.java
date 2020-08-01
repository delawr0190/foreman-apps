package mn.foreman.obelisk;

import mn.foreman.model.AsyncRebootStrategy;
import mn.foreman.model.MinerFactory;
import mn.foreman.model.RebootStrategy;
import mn.foreman.model.error.MinerException;
import mn.foreman.obelisk.json.Dashboard;

import java.util.Map;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * An {@link ObeliskRebootStrategy} provides a {@link RebootStrategy}
 * implementation that will reboot an obelisk device.
 */
public class ObeliskRebootStrategy
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
    public ObeliskRebootStrategy(
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
        try {
            final String username =
                    (String) parameters.getOrDefault("username", "");
            final String password =
                    (String) parameters.getOrDefault("password", "");
            ObeliskQuery.runSessionQuery(
                    ObeliskQuery.Context
                            .<Dashboard>builder()
                            .apiIp(ip)
                            .apiPort(port)
                            .uri("/api/action/reboot")
                            .method("POST")
                            .username(username)
                            .password(password)
                            .isReboot(true)
                            .build());
        } catch (final Exception e) {
            throw new MinerException(e);
        }
        return true;
    }
}