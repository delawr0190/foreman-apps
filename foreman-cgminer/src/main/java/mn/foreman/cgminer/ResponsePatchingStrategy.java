package mn.foreman.cgminer;

import java.io.IOException;

/**
 * A {@link ResponsePatchingStrategy} provides a mechanism for sanitizing JSON
 * responses from a cgminer down to a common structure.  If only everybody
 * interpreted the specification similarly.  :)
 */
public interface ResponsePatchingStrategy {

    /**
     * Patches the provided JSON.
     *
     * @param json The JSON to patch.
     *
     * @return The patched JSON.
     */
    String patch(String json) throws IOException;
}