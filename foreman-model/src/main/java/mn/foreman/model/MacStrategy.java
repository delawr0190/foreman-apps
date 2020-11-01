package mn.foreman.model;

import java.util.Optional;

/** Obtains the MAC address. */
public interface MacStrategy {

    /**
     * Returns the MAC address, if present.
     *
     * @return The MAC address, if present.
     */
    Optional<String> getMacAddress();
}
