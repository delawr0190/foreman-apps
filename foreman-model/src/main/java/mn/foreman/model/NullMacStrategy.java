package mn.foreman.model;

import java.util.Optional;

/** Returns no MAC address. */
public class NullMacStrategy
        implements MacStrategy {

    @Override
    public Optional<String> getMacAddress() {
        return Optional.empty();
    }
}
