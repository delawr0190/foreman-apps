package mn.foreman.claymore;

import mn.foreman.model.Miner;
import mn.foreman.model.MinerFactory;

import java.util.Map;

/**
 * A {@link MinerFactory} implementation that parses a configuration and creates
 * a {@link Miner} that will query an instance of claymore.
 */
public class ClaymoreFactory
        implements MinerFactory {

    /** The mappings. */
    private final TypeMapping typeMapping;

    /**
     * Constructor.
     *
     * @param typeMapping The mappings.
     */
    public ClaymoreFactory(final TypeMapping typeMapping) {
        this.typeMapping = typeMapping;
    }

    @Override
    public Miner create(final Map<String, String> config) {
        return new Claymore(
                config.get("apiIp"),
                Integer.parseInt(config.get("apiPort")),
                config.get("apiPassword"),
                this.typeMapping);
    }
}