package mn.foreman.cgminer;

/**
 * A {@link NullPatchingStrategy} provides a null object {@link
 * ResponsePatchingStrategy} that does nothing.
 */
public class NullPatchingStrategy
        implements ResponsePatchingStrategy {

    @Override
    public String patch(final String json) {
        return json;
    }
}
