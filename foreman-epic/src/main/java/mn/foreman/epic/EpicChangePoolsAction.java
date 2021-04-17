package mn.foreman.epic;

import mn.foreman.api.model.Pool;
import mn.foreman.model.AbstractChangePoolsAction;
import mn.foreman.model.error.MinerException;

import com.google.common.collect.ImmutableMap;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * A {@link EpicChangePoolsAction} provides an {@link AbstractChangePoolsAction}
 * implementation that will change the pools in use by an epic device.
 */
public class EpicChangePoolsAction
        extends AbstractChangePoolsAction {

    @Override
    public boolean doChange(
            final String ip,
            final int port,
            final Map<String, Object> parameters,
            final List<Pool> pools)
            throws MinerException {
        boolean success;

        final Pool pool =
                pools.get(0);
        final String password =
                parameters.getOrDefault(
                        "password",
                        "letmein").toString();

        if ((success =
                EpicQuery.runQuery(
                        ip,
                        port,
                        password,
                        "/pool",
                        pool.getUrl()))) {
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (final InterruptedException e) {
                // Ignore
            }
            success =
                    EpicQuery.runQuery(
                            ip,
                            port,
                            password,
                            "/login",
                            ImmutableMap.of(
                                    "login",
                                    pool.getUsername(),
                                    "password",
                                    pool.getPassword()));
        }

        return success;
    }
}
