package mn.foreman.pickaxe.command.asic.scan;

import mn.foreman.model.Detection;

/** Returns all of the detections. */
public class NullFilteringStrategy
        implements FilteringStrategy {

    @Override
    public boolean matches(
            final Detection detection,
            final String mac) {
        return true;
    }
}
