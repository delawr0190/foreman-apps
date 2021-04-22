package mn.foreman.pickaxe.command.asic.scan;

import mn.foreman.model.Detection;

import java.util.List;

/** Returns all of the detections. */
public class NullFilteringStrategy
        implements FilteringStrategy {

    @Override
    public boolean matches(
            final Detection detection,
            final List<String> macs) {
        return true;
    }
}
