package mn.foreman.whatsminer;

import mn.foreman.cgminer.TypeFactory;
import mn.foreman.model.MinerType;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * A {@link WhatsminerTypeFactory} provides a {@link TypeFactory} implementation
 * that will determine the {@link WhatsminerType} from the provided cgminer
 * response.
 */
public class WhatsminerTypeFactory
        implements TypeFactory {

    @Override
    public Optional<MinerType> toType(
            final Map<String, List<Map<String, String>>> responseValues) {
        final List<Map<String, String>> stats =
                responseValues
                        .entrySet()
                        .stream()
                        .filter(entry -> entry.getKey().equals("STATS"))
                        .map(Map.Entry::getValue)
                        .flatMap(List::stream)
                        .filter(map -> map.containsKey("chip_num"))
                        .collect(Collectors.toList());
        final int numChips =
                stats
                        .stream()
                        .mapToInt(map -> Integer.parseInt(map.get("chip_num")))
                        .sum();
        final boolean hasPowerVersion =
                stats
                        .stream()
                        .anyMatch(map -> map.containsKey("power_version"));
        return WhatsminerType
                .fromChips(numChips, hasPowerVersion)
                .map(type -> type);
    }
}