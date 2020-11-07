package mn.foreman.cgminer;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/** A collection of information related to cgminer processing. */
public class Context {

    /** The multi context. */
    private final Map<ContextKey, Map<String, Object>> multiContext =
            new ConcurrentHashMap<>();

    /** The context. */
    private final Map<ContextKey, String> simpleContext =
            new ConcurrentHashMap<>();

    /**
     * Adds a multi-value to the context.
     *
     * @param key   The key.
     * @param value The values.
     */
    public void addMulti(
            final ContextKey key,
            final Map<String, Object> value) {
        final Map<String, Object> dest =
                this.multiContext.computeIfAbsent(
                        key,
                        key1 -> new LinkedHashMap<>());
        dest.putAll(value);
    }

    /**
     * Adds to the context.
     *
     * @param key   The key.
     * @param value The value.
     */
    public void addSimple(
            final ContextKey key,
            final String value) {
        if (value != null) {
            this.simpleContext.put(key, value);
        }
    }

    /** Clears the context. */
    public void clear() {
        this.simpleContext.clear();
    }

    /**
     * Returns the multi-value.
     *
     * @param key The key.
     *
     * @return The multi-value.
     */
    public Optional<Map<String, Object>> getMulti(final ContextKey key) {
        return Optional.ofNullable(this.multiContext.get(key));
    }

    /**
     * Gets the value from the context.
     *
     * @param key The key.
     *
     * @return The value, if present.
     */
    public Optional<String> getSimple(final ContextKey key) {
        return Optional.ofNullable(this.simpleContext.get(key));
    }
}