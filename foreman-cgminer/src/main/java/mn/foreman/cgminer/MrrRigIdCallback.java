package mn.foreman.cgminer;

import mn.foreman.util.MrrUtils;

import java.util.Map;

/** A {@link PoolCallback} that obtains the MRR Rig ID, if present. */
public class MrrRigIdCallback
        implements PoolCallback {

    /** The miner context. */
    private final Context context;

    /**
     * Constructor.
     *
     * @param context The miner context.
     */
    public MrrRigIdCallback(final Context context) {
        this.context = context;
    }

    @Override
    public void foundPool(final Map<String, String> poolInfo) {
        final String pool = poolInfo.getOrDefault("URL", "");
        final String rigId =
                MrrUtils.getRigId(
                        pool,
                        poolInfo.getOrDefault("User", ""));
        if (rigId != null && !rigId.isEmpty()) {
            this.context.addSimple(
                    ContextKey.MRR_RIG_ID,
                    rigId);
        }
    }
}