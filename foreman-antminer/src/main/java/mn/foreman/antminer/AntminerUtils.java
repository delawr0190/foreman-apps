package mn.foreman.antminer;

import mn.foreman.model.error.EmptySiteException;

import java.util.*;
import java.util.stream.Collectors;

/** A collection of utility functions for antminer processing. */
public class AntminerUtils {

    /** The known bOS types. */
    private static final List<AntminerType> BRAIINS_TYPES;

    /** The type key. */
    private static String TYPE = "Type";

    static {
        final List<AntminerType> braiinsTypes =
                Arrays.stream(AntminerType.values())
                        .filter(AntminerType::isBraiins)
                        .collect(Collectors.toList());
        BRAIINS_TYPES = Collections.unmodifiableList(braiinsTypes);
    }

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
                        .filter(AntminerUtils::isKnown)
                        .findFirst()
                        .orElseThrow(EmptySiteException::new);
        Optional<AntminerType> type = toBraiinsType(values);
        if (!type.isPresent()) {
            type = AntminerType.forModel(TYPE, values.get(TYPE));
        }
        return type;
    }

    /**
     * Checks to see if the type is known.
     *
     * @param values The values to inspect.
     *
     * @return The type.
     */
    private static boolean isKnown(final Map<String, String> values) {
        return values.containsKey(TYPE) || toBraiinsType(values).isPresent();

    }

    /**
     * Checks to see if the provided values are braiins.
     *
     * @param values The values.
     *
     * @return The type, if found.
     */
    private static Optional<AntminerType> toBraiinsType(final Map<String, String> values) {
        return BRAIINS_TYPES
                .stream()
                .filter(type -> values.containsKey(type.getIdentifier()))
                .findFirst();
    }
}
