package mn.foreman.cgminer;

import java.util.HashMap;
import java.util.Map;

/** A {@link PoolCallback} that obtains and stores the last share time. */
public class LastShareTimeCallback
        implements PoolCallback {

    /** The miner context. */
    private final Context context;

    /**
     * Constructor.
     *
     * @param context The miner context.
     */
    public LastShareTimeCallback(final Context context) {
        this.context = context;
    }

    @Override
    public void foundPool(final Map<String, String> poolInfo) {
        final String pool = poolInfo.getOrDefault("URL", "");
        final String lastShareTime = poolInfo.getOrDefault("Last Share Time", "");
        if (pool != null && lastShareTime != null && !lastShareTime.isEmpty()) {
            final Map<String, Object> lastShareTimes =
                    this.context
                            .getMulti(ContextKey.LAST_SHARE_TIME)
                            .map(HashMap::new)
                            .orElse(new HashMap<>());
            if ("0".equals(lastShareTimes.getOrDefault(pool, "0"))) {
                lastShareTimes.put(
                        pool,
                        lastShareTime);
            }
            this.context.addMulti(
                    ContextKey.LAST_SHARE_TIME,
                    lastShareTimes);
        }
    }
}