package mn.foreman.cgminer;

/** All of the accepted context keys. */
public enum ContextKey {

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
