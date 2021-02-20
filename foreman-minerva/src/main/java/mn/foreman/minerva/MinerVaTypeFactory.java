package mn.foreman.minerva;

import mn.foreman.cgminer.TypeFactory;
import mn.foreman.model.MinerType;
import mn.foreman.model.error.EmptySiteException;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * A {@link MinerVaTypeFactory} provides a {@link TypeFactory} implementation
 * that will determine the {@link MinerVaType} from the provided cgminer
 * response.
 */
public class MinerVaTypeFactory
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
                        .filter(map -> map.containsKey("Name"))
                        .findFirst()
                        .orElseThrow(EmptySiteException::new);
        return MinerVaType
                .forModel(stats.get("Name"))
                .map(type -> type);
    }
}
