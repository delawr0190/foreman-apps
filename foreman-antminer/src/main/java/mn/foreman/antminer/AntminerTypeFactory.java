package mn.foreman.antminer;

import mn.foreman.cgminer.TypeFactory;
import mn.foreman.model.MinerType;
import mn.foreman.model.error.EmptySiteException;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * An {@link AntminerTypeFactory} provides a {@link TypeFactory} implementation
 * that will determine the {@link AntminerType} from the provided cgminer
 * response.
 */
public class AntminerTypeFactory
        implements TypeFactory {

    @Override
    public Optional<MinerType> toType(
            final Map<String, List<Map<String, String>>> responseValues)
            throws EmptySiteException {
        return AntminerType.forModel(
                responseValues
                        .entrySet()
                        .stream()
                        .filter(entry -> entry.getKey().equals("VERSION"))
                        .map(Map.Entry::getValue)
                        .flatMap(List::stream)
                        .filter(map -> map.containsKey("Type"))
                        .findFirst()
                        .orElseThrow(EmptySiteException::new)
                        .get("Type"))
                .map(type -> type);
    }
}