package mn.foreman.aixin;

import mn.foreman.cgminer.TypeFactory;
import mn.foreman.model.MinerType;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * An {@link AixinTypeFactory} provides a {@link TypeFactory} implementation
 * that will determine the {@link AixinType} from the provided cgminer
 * response.
 */
public class AixinTypeFactory
        implements TypeFactory {

    @Override
    public Optional<MinerType> toType(
            final Map<String, List<Map<String, String>>> responseValues) {
        MinerType minerType = null;
        final List<Map<String, String>> values =
                responseValues.getOrDefault(
                        "DEVS",
                        Collections.emptyList());
        if (!values.isEmpty() &&
                values.get(0).containsKey("fminerled")) {
            minerType = AixinType.AIXIN_A1;
        }
        return Optional.ofNullable(minerType);
    }
}
