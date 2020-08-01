package mn.foreman.antminer;

import mn.foreman.io.Query;
import mn.foreman.model.AsyncRebootStrategy;
import mn.foreman.model.MinerFactory;
import mn.foreman.model.error.MinerException;

import java.util.Map;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/** A strategy that performs an antminer reboot. */
public class AntminerRebootStrategy
        extends AsyncRebootStrategy {

    /** The realm. */
    private final String realm;

    /**
     * Constructor.
     *
     * @param delay        The delay.
     * @param interval     The interval.
     * @param units        The units.
     * @param threadPool   The thread pool.
     * @param minerFactory The factory.
     * @param realm        The realm.
     */
    public AntminerRebootStrategy(
            final long delay,
            final long interval,
            final TimeUnit units,
            final ScheduledThreadPoolExecutor threadPool,
            final MinerFactory minerFactory,
            final String realm) {
        super(
                delay,
                interval,
                units,
                threadPool,
                minerFactory);
        this.realm = realm;
    }

    @Override
    public boolean doReboot(
            final String ip,
            final int port,
            final Map<String, Object> parameters)
            throws MinerException {
        final AtomicReference<Integer> statusCode =
                new AtomicReference<>();

        try {
            final String username =
                    (String) parameters.getOrDefault("username", "");
            final String password =
                    (String) parameters.getOrDefault("password", "");

            Query.digestGet(
                    ip,
                    port,
                    this.realm,
                    "/cgi-bin/reboot.cgi",
                    username,
                    password,
                    (code, s) -> statusCode.set(code));
        } catch (final Exception e) {
            throw new MinerException(e);
        }

        final Integer code = statusCode.get();

        // Can't validate the code because the miner returns status 500, and it
        // seems like that will most likely change at some point or not be
        // cross-fork compatible
        return code != null;
    }
}
