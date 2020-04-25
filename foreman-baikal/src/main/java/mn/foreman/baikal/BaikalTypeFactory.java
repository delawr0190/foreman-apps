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
        if (responseValues
                .entrySet()
                .stream()
                .filter(entry -> entry.getKey().equals("DEVS"))
                .map(Map.Entry::getValue)
                .flatMap(List::stream)
                .anyMatch(map ->
                        map.getOrDefault("Name", "").startsWith("BKL"))) {
            return Optional.of(BaikalType.BAIKAL);
        }
        return Optional.empty();
    }
}
