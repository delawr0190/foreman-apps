package mn.foreman.openminer.response;


import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** A deserializer for boards. */
public class SlotDeserializer
        extends JsonDeserializer<Map<String, Agg.Slot>> {

    @Override
    public Map<String, Agg.Slot> deserialize(
            final JsonParser jsonParser,
            final DeserializationContext deserializationContext) {
        final Map<String, Agg.Slot> slots = new HashMap<>();
        try {
            if (jsonParser.currentToken() == JsonToken.START_OBJECT) {
                slots.putAll(
                        jsonParser.readValueAs(
                                new TypeReference<Map<String, Agg.Slot>>() {
                                }));
            } else {
                jsonParser.readValueAs(
                        new TypeReference<List<Object>>() {
                        });
            }
        } catch (final Exception e) {
            // Ignore and let it be empty
        }
        return slots;
    }
}
