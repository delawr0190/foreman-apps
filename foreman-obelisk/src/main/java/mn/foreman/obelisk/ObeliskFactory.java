package mn.foreman.obelisk;

import mn.foreman.model.Miner;
import mn.foreman.model.MinerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * A {@link MinerFactory} implementation that parses a configuration and creates
 * a {@link Miner} that will query an instance of an obelisk.
 */
public class ObeliskFactory
        implements MinerFactory {

    /** The json mapper. */
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @SuppressWarnings("unchecked")
    @Override
    public Miner create(final Map<String, Object> config) {
        final String ip = config.get("apiIp").toString();
        return new Obelisk(
                ip,
                Integer.parseInt(config.get("apiPort").toString()),
                config.get("username").toString(),
                config.get("password").toString(),
                (List<String>) config.getOrDefault(
                        "statsWhitelist",
                        Collections.emptyList()),
                new ObeliskMacStrategy(
                        ip,
                        Integer.parseInt(config.getOrDefault("port", "80").toString()),
                        2,
                        TimeUnit.SECONDS,
                        OBJECT_MAPPER),
                2,
                TimeUnit.SECONDS,
                OBJECT_MAPPER);
    }
}
