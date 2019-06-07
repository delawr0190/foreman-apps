package mn.foreman.xmrstak;

import mn.foreman.model.Miner;
import mn.foreman.model.MinerFactory;

import java.util.Map;

/**
 * A {@link MinerFactory} implementation that parses a configuration and creates
 * a {@link Miner} that will query an instance of xmrstak.
 */
public class XmrstakFactory
        implements MinerFactory {

    @Override
    public Miner create(final Map<String, String> config) {
        final Miner miner;

        final String apiIp = config.get("apiIp");
        final int apiPort = Integer.parseInt(config.get("apiPort"));

        final XmrstakType type =
                XmrstakType.valueOf(
                        config.getOrDefault(
                                "type",
                                XmrstakType.GPU.name()).toUpperCase());

        switch (type) {
            case CPU:
                miner =
                        new XmrstakCpu(
                                apiIp,
                                apiPort);
                break;
            case GPU:
                // Fall through
            default:
                miner =
                        new XmrstakGpu(
                                apiIp,
                                apiPort);
                break;
        }

        return miner;
    }
}