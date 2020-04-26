package mn.foreman.dayun.response;

import mn.foreman.cgminer.ResponsePatchingStrategy;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A {@link StatsPatchingStrategy} provides a response strategy that will
 * sanitize the Z1 response to look similar to other cgminer forks. This
 * involves moving the chip statuses and temps up to the "STATS" level, so that
 * "STATS" is a list of objects of key to string values.
 */
public class StatsPatchingStrategy
        implements ResponsePatchingStrategy {

    @Override
    @SuppressWarnings("unchecked")
    public String patch(final String json)
            throws IOException {
        final Map<String, Object> sanitized =
                new HashMap<>();

        final ObjectMapper objectMapper =
                new ObjectMapper();

        final Map<String, List<Map<String, Object>>> response =
                objectMapper.readValue(
                        json,
                        new TypeReference<
                                Map<String, List<Map<String, Object>>>>() {
                        });
        sanitized.put("STATUS", response.get("STATUS"));

        final Map<String, Object> stats = response.get("STATS").get(0);
        final Map<String, String> sanitizedStats = new HashMap<>();
        sanitizedStats.put("ID",
                stats.get("ID").toString());
        sanitizedStats.put("MHS 30S",
                stats.get("MHS 30S").toString());
        sanitizedStats.put("MHS 5m",
                stats.get("MHS 5m").toString());
        sanitizedStats.put("Fan Nunber",
                stats.get("Fan Nunber").toString());
        sanitizedStats.put("Fan In",
                stats.get("Fan In").toString());
        sanitizedStats.put("Fan Out",
                stats.get("Fan Out").toString());
        sanitizedStats.put("Temperature Core",
                stats.get("Temperature Core").toString());
        sanitizedStats.put("CH1 Temp",
                String.valueOf(
                        ((Map<String, Object>) stats.get("CH1"))
                                .get("Temperature")));
        sanitizedStats.put("CH2 Temp",
                String.valueOf(
                        ((Map<String, Object>) stats.get("CH2"))
                                .get("Temperature")));
        sanitizedStats.put("CH3 Temp",
                String.valueOf(
                        ((Map<String, Object>) stats.get("CH3"))
                                .get("Temperature")));
        sanitizedStats.put("CH4 Temp",
                String.valueOf(
                        ((Map<String, Object>) stats.get("CH4"))
                                .get("Temperature")));

        sanitized.put("STATS", Collections.singletonList(sanitizedStats));

        return objectMapper.writeValueAsString(sanitized);
    }
}
