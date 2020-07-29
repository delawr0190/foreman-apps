package mn.foreman.multminer;

import mn.foreman.model.RebootStrategy;
import mn.foreman.model.error.MinerException;

import com.google.common.collect.ImmutableMap;

import java.util.Map;

/**
 * A {@link MultMinerRebootStrategy} provides a {@link RebootStrategy}
 * implementation that will reboot a multminer device.
 */
public class MultMinerRebootStrategy
        implements RebootStrategy {

    @Override
    public boolean reboot(
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