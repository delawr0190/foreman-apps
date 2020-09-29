package mn.foreman.cgminer;

import mn.foreman.util.Flatten;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

    /** The stats whitelist. */
    private final List<String> statsWhitelist;

    /**
     * Constructor.
     *
     * @param context        The context.
     * @param statsWhitelist The stats whitelist.
     * @param real           The real.
     */
    public RawStatsInterceptingStrategy(
            final Context context,
            final List<String> statsWhitelist,
            final ResponsePatchingStrategy real) {
        this.context = context;
        this.statsWhitelist = new ArrayList<>(statsWhitelist);
        this.real = real;
    }

    @Override
    public String patch(final String json) throws IOException {
        final String patched = this.real.patch(json);
        if (!this.statsWhitelist.isEmpty()) {
            try {
                this.context.addMulti(
                        ContextKey.RAW_STATS,
                        Flatten.flattenAndFilter(
                                json,
                                this.statsWhitelist));
            } catch (final Exception e) {
                LOG.warn("Failed to flatten json - skipping", e);
            }
        }
        return patched;
    }
}