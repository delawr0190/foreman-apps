package mn.foreman.model;

import java.math.BigDecimal;

/**
 * An abstract builder for creating JSON POJOs.
 *
 * @param <T> The type.
 */
public abstract class AbstractBuilder<T> {

    /** A default value for an undefined boolean. */
    protected static final boolean UNDEFINED_BOOL = false;

    /** A default value for an undefined decimal. */
    protected static final BigDecimal UNDEFINED_DECIMAL = new BigDecimal(0);

    /** A default value for an undefined int. */
    protected static final int UNDEFINED_INT = 0;

    /** A default value for an undefined long. */
    protected static final long UNDEFINED_LONG = 0L;

    /** A default value for an undefined {@link String}. */
    protected static final String UNDEFINED_STRING = "undefined";

    /**
     * Creates a new {@link T}.
     *
     * @return The new {@link T}.
     */
    public abstract T build();
}