package mn.foreman.pickaxe.command.asic.scan;

import mn.foreman.model.Detection;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/** Finds the detection by MAC. */
public class MacFilteringStrategy
        implements FilteringStrategy {

    @Override
    public boolean matches(
            final Detection detection,
            final List<String> macs) {
        final Map<String, Object> args = detection.getParameters();
        return macs
                .stream()
                .map(String::toLowerCase)
                .collect(Collectors.toList())
                .contains(
                        args
                                .getOrDefault("mac", "")
                                .toString()
                                .toLowerCase());
    }
}
