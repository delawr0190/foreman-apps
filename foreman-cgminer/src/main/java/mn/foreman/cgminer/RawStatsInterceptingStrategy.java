package mn.foreman.cgminer;

import com.github.wnameless.json.flattener.FlattenMode;
import com.github.wnameless.json.flattener.JsonFlattener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;

/**
 * A {@link RawStatsInterceptingStrategy} provides a {@link ResponseStrategy}
 * implementation that will store the flattended response json into the provided
 * {@link Context}.
 */
public class RawStatsInterceptingStrategy
        implements ResponsePatchingStrategy {

    /** The logger for this class. */
    private static final Logger LOG =
            LoggerFactory.getLogger(RawStatsInterceptingStrategy.class);

    /** The context. */
    private final Context context;

    /** The real strategy. */
    private final ResponsePatchingStrategy real;

    /**
     * Constructor.
     *
     * @param context The context.
     * @param real    The real.
     */
    public RawStatsInterceptingStrategy(
            final Context context,
            final ResponsePatchingStrategy real) {
        this.context = context;
        this.real = real;
    }

    @Override
    public String patch(final String json) throws IOException {
        final String patched = this.real.patch(json);
        try {
            final Map<String, Object> flattened =
                    new JsonFlattener(patched)
                            .withFlattenMode(FlattenMode.MONGODB)
                            .flattenAsMap();
            LOG.info("Flattended response to: {}", flattened);
            this.context.addMulti(
                    ContextKey.RAW_STATS,
                    flattened);
        } catch (final Exception e) {
            LOG.warn("Failed to flatten json - skipping", e);
        }
        return patched;
    }
}