package mn.foreman.cgminer;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/** A collection of information related to cgminer processing. */
public class Context {

    /** The context. */
    private final Map<ContextKey, String> context = new HashMap<>();

    /**
     * Adds to the context.
     *
     * @param key   The key.
     * @param value The value.
     */
    public void add(
            final ContextKey key,
            final String value) {
        this.context.put(key, value);
    }

    /** Clears the context. */
    public void clear() {
        this.context.clear();
    }

    /**
     * Gets the value from the context.
     *
     * @param key The key.
     *
     * @return The value, if present.
     */
    public Optional<String> get(final ContextKey key) {
        return Optional.ofNullable(this.context.get(key));
    }
}