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
    public Miner create(final Map<String, Object> config) {
        return new Claymore(
                config.get("apiIp").toString(),
                Integer.parseInt(config.get("apiPort").toString()),
                config.containsKey("apiPassword")
                        ? config.get("apiPassword").toString()
                        : null,
                this.typeMapping);
    }
}