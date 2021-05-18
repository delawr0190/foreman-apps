package mn.foreman.cgminer;

/** All of the accepted context keys. */
public enum ContextKey {

    /** Raw json stats. */
    RAW_STATS("raw_stats"),

    /** The mac address. */
    MAC("mac"),

    /** The miner type. */
    MINER_TYPE("type"),

    /** The compile time. */
    COMPILE_TIME("compile_time"),

    /** The last share time. */
    LAST_SHARE_TIME("last_share_time"),

    /** MRR rig id. */
    MRR_RIG_ID("mrr_rig_id");

    /** The key. */
    private final String key;

    /**
     * Constructor.
     *
     * @param key The key.
     */
    ContextKey(final String key) {
        this.key = key;
    }

    /**
     * Returns the key.
     *
     * @return The key.
     */
    public String getKey() {
        return this.key;
    }
}