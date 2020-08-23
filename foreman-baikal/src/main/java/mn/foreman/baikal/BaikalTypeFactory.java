package mn.foreman.baikal;

import mn.foreman.cgminer.TypeFactory;
import mn.foreman.model.MinerType;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * A {@link BaikalTypeFactory} provides a {@link TypeFactory} implementation
 * that will determine the {@link BaikalType} from the provided cgminer
 * response.
 */
public class BaikalTypeFactory
        implements TypeFactory {

    @Override
    public Optional<MinerType> toType(
            final Map<String, List<Map<String, String>>> responseValues) {
        final String identifier =
                responseValues
                        .entrySet()
                        .stream()
                        .filter(entry -> entry.getKey().equals("DEVS"))
                        .map(Map.Entry::getValue)
                        .flatMap(List::stream)
                        .filter(map -> map.containsKey("Name"))
                        .filter(map -> map.get("Name").startsWith("BKL"))
                        .map(map -> map.get("Name"))
                        .findFirst()
                        .orElse(BaikalType.X.getIdentifier());
        return BaikalType.forIdentifier(identifier)
                .map(type -> type);
    }
}
