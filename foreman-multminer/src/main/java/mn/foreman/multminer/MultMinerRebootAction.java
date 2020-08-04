package mn.foreman.multminer;

import mn.foreman.model.AsicAction;
import mn.foreman.model.error.MinerException;

import com.google.common.collect.ImmutableMap;

import java.util.Map;

/** Reboots a multminer. */
public class MultMinerRebootAction
        implements AsicAction.CompletableAction {

    @Override
    public boolean run(
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