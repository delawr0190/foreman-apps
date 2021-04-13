package mn.foreman.pickaxe.command.asic.scan;

import mn.foreman.model.Detection;

import java.util.Map;

/** Finds the detection by MAC. */
public class MacFilteringStrategy
        implements FilteringStrategy {

    @Override
    public boolean matches(
            final Detection detection,
            final String mac) {
        final Map<String, Object> args = detection.getParameters();
        return args
                .getOrDefault("mac", "")
                .toString()
                .equalsIgnoreCase(mac);
    }
}
