package mn.foreman.openminer;

import mn.foreman.model.Miner;
import mn.foreman.model.MinerFactory;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * A {@link MinerFactory} implementation for parsing stats from a miner running
 * OpenMiner.
 */
public class OpenMinerFactory
        implements MinerFactory {

    @SuppressWarnings("unchecked")
    @Override
    public Miner create(final Map<String, Object> config) {
        final String ip = config.get("apiIp").toString();
        final String username = config.get("username").toString();
        final String password = config.get("password").toString();
        int port =
                Integer.parseInt(
                        config.getOrDefault(
                                "apiPort",
                                "80").toString());
        // Test hook
        if (port != 8080 && port != 8081) {
            port = 80;
        }
        return new OpenMiner(
                ip,
                port,
                username,
                password,
                (List<String>) config.getOrDefault(
                        "statsWhitelist",
                        Collections.emptyList()),
                new OpenMinerMacStrategy(
                        ip,
                        port,
                        username,
                        password));
    }
}
