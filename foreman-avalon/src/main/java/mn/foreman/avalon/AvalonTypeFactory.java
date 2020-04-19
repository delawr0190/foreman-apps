package mn.foreman.avalon;

import mn.foreman.cgminer.TypeFactory;
import mn.foreman.model.MinerType;
import mn.foreman.model.error.EmptySiteException;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * An {@link AvalonTypeFactory} provides a {@link TypeFactory} implementation
 * that will determine the {@link AvalonType} from the provided cgminer
 * response.
 */
public class AvalonTypeFactory
        implements TypeFactory {

    @Override
    public Optional<MinerType> toType(
            final Map<String, List<Map<String, String>>> responseValues)
            throws EmptySiteException {
        final Map<String, String> stats =
                responseValues
                        .entrySet()
                        .stream()
                        .filter(entry -> entry.getKey().equals("STATS"))
                        .map(Map.Entry::getValue)
                        .flatMap(List::stream)
                        .filter(map -> map.containsKey("MM ID0") || map.containsKey("MM ID1"))
                        .findFirst()
                        .orElseThrow(EmptySiteException::new);
        return AvalonType.forIdentifier(
                stats.getOrDefault(
                        "MM ID0",
                        stats.get("MM ID1")))
                .map(type -> type);
    }
}
