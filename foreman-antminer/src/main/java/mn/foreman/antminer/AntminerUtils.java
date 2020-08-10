package mn.foreman.antminer;

import mn.foreman.model.error.EmptySiteException;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/** A collection of utility functions for antminer processing. */
public class AntminerUtils {

    /** The bOS key. */
    private static String BRAINS_OS_P = "BOSminer+";

    /** The type key. */
    private static String TYPE = "Type";

    /**
     * Finds the {@link AntminerType} from the provided versions response.
     *
     * @param responseValues The response values.
     *
     * @return The type.
     *
     * @throws EmptySiteException if type couldn't be determined.
     */
    public static Optional<AntminerType> toType(
            final Map<String, List<Map<String, String>>> responseValues)
            throws EmptySiteException {
        final Map<String, String> values =
                responseValues
                        .entrySet()
                        .stream()
                        .filter(entry -> entry.getKey().equals("VERSION"))
                        .map(Map.Entry::getValue)
                        .flatMap(List::stream)
                        .filter(map -> map.containsKey(TYPE) || map.containsKey(BRAINS_OS_P))
                        .findFirst()
                        .orElseThrow(EmptySiteException::new);
        final String key =
                values.containsKey(TYPE)
                        ? TYPE
                        : BRAINS_OS_P;
        return AntminerType.forModel(key, values.get(key));
    }
}
