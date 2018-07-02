package mn.foreman.excavator.method;

/** A {@link Method} represents a method action on excavator. */
public interface Method<T> {

    /**
     * Converts the {@link Method} to a {@link String}.
     *
     * @return The {@link String}.
     */
    String toMethod();
}