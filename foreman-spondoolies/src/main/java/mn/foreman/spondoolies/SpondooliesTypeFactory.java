package mn.foreman.spondoolies;

import mn.foreman.cgminer.TypeFactory;
import mn.foreman.model.MinerType;
import mn.foreman.model.error.EmptySiteException;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * A {@link SpondooliesTypeFactory} provides a {@link TypeFactory}
 * implementation that will determine the {@link SpondooliesTypeFactory} from
 * the provided cgminer response.
 */
public class SpondooliesTypeFactory
        implements TypeFactory {

    @Override
    public Optional<MinerType> toType(
            final Map<String, List<Map<String, String>>> responseValues)
            throws EmptySiteException {
        final Map<String, String> versions =
                responseValues
                        .entrySet()
                        .stream()
                        .filter(entry -> entry.getKey().equals("STATUS"))
                        .map(Map.Entry::getValue)
                        .flatMap(List::stream)
                        .filter(map -> map.containsKey("Description"))
                        .findFirst()
                        .orElseThrow(EmptySiteException::new);
        return SpondooliesType
                .forIdentifier(versions.get("Description"))
                .map(type -> type);
    }
}
