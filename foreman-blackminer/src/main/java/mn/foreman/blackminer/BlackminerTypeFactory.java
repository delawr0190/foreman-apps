package mn.foreman.blackminer;

import mn.foreman.cgminer.TypeFactory;
import mn.foreman.model.MinerType;
import mn.foreman.model.error.EmptySiteException;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * A {@link BlackminerTypeFactory} provides a {@link TypeFactory} implementation
 * that will determine the {@link BlackminerType} from the provided cgminer
 * response.
 */
public class BlackminerTypeFactory
        implements TypeFactory {

    @Override
    public Optional<MinerType> toType(
            final Map<String, List<Map<String, String>>> responseValues)
            throws EmptySiteException {
        final Map<String, String> versions =
                responseValues
                        .entrySet()
                        .stream()
                        .filter(entry -> entry.getKey().equals("VERSION"))
                        .map(Map.Entry::getValue)
                        .flatMap(List::stream)
                        .filter(map -> map.containsKey("Type"))
                        .findFirst()
                        .orElseThrow(EmptySiteException::new);
        return BlackminerType
                .forModel(versions.get("Type"))
                .map(type -> type);
    }
}
