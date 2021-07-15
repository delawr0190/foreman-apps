package mn.foreman.ibelink;

import mn.foreman.cgminer.TypeFactory;
import mn.foreman.model.MinerType;
import mn.foreman.model.error.EmptySiteException;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * An {@link IBeLinkTypeFactory} provides a {@link TypeFactory} implementation
 * that will determine the {@link IBeLinkType} from the provided cgminer
 * response.
 */
public class IBeLinkTypeFactory
        implements TypeFactory {

    @Override
    public Optional<MinerType> toType(
            final Map<String, List<Map<String, String>>> responseValues)
            throws EmptySiteException {
        final Map<String, String> stats =
                responseValues
                        .entrySet()
                        .stream()
                        .filter(entry -> entry.getKey().equals("DEVS"))
                        .map(Map.Entry::getValue)
                        .flatMap(List::stream)
                        .filter(map -> map.containsKey("Name"))
                        .findFirst()
                        .orElseThrow(EmptySiteException::new);
        return IBeLinkType.forName(stats.get("Name"))
                .map(type -> type);
    }
}
