package mn.foreman.cgminer;

import mn.foreman.model.MinerType;
import mn.foreman.model.error.EmptySiteException;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * A {@link TypeFactory} provides a mechanism for converting cgminer responses
 * to known {@link MinerType miner types}.
 */
public interface TypeFactory {

    /**
     * Converts the provided cgminer response values to a {@link MinerType}.
     *
     * @param responseValues The response.
     *
     * @return The type, if one exists.
     *
     * @throws EmptySiteException on an invalid type.
     */
    Optional<MinerType> toType(
            Map<String, List<Map<String, String>>> responseValues)
            throws EmptySiteException;
}
