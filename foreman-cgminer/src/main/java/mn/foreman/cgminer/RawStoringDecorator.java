package mn.foreman.cgminer;

import mn.foreman.cgminer.response.CgMinerResponse;
import mn.foreman.model.error.MinerException;
import mn.foreman.model.miners.MinerStats;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.wnameless.json.flattener.FlattenMode;
import com.github.wnameless.json.flattener.JsonFlattener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

/**
 * A {@link RawStoringDecorator} provides a {@link ResponseStrategy}
 * implementation that will store the flattended response json into the provided
 * {@link Context}.
 */
public class RawStoringDecorator
        implements ResponseStrategy {

    /** The logger for this class. */
    private static final Logger LOG =
            LoggerFactory.getLogger(RawStoringDecorator.class);

    /** The context. */
    private final Context context;

    /** The mapper for marshalling json. */
    private final ObjectMapper objectMapper;

    /** The real strategy. */
    private final ResponseStrategy real;

    /**
     * Constructor.
     *
     * @param context      The context.
     * @param objectMapper The mapper.
     * @param real         The real.
     */
    public RawStoringDecorator(
            final Context context,
            final ObjectMapper objectMapper,
            final ResponseStrategy real) {
        this.context = context;
        this.objectMapper = objectMapper;
        this.real = real;
    }

    @Override
    public void processResponse(
            final MinerStats.Builder builder,
            final CgMinerResponse response) throws MinerException {
        try {
            final Map<String, List<Map<String, String>>> values =
                    response.getValues();
            final String json = this.objectMapper.writeValueAsString(values);
            final Map<String, Object> flattened =
                    new JsonFlattener(json)
                            .withFlattenMode(FlattenMode.MONGODB)
                            .flattenAsMap();
            LOG.info("Flattended response to: {}", flattened);
            this.context.addMulti(
                    ContextKey.RAW_STATS,
                    flattened);
        } catch (final Exception e) {
            LOG.warn("Failed to flatten json - skipping", e);
        } finally {
            this.real.processResponse(
                    builder,
                    response);
        }
    }
}
