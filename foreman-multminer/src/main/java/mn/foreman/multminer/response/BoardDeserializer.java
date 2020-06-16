package mn.foreman.multminer.response;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * A {@link BoardDeserializer} provides a deserializer implementation that will
 * convert {@link Stats.Board} boards from the json from a multminer.
 *
 * <p>The multminer API does not return a standard object structure for the
 * boards (for some strange reason, the last object that's returned is an
 * integer instead of an object).  For that reason, this deserializer is needed
 * to allow for that structure to be parsed.</p>
 */
public class BoardDeserializer
        extends StdDeserializer<Stats.Board> {

    /** Constructor. */
    public BoardDeserializer() {
        this(null);
    }

    /**
     * Constructor.
     *
     * @param vc The class.
     */
    public BoardDeserializer(final Class<?> vc) {
        super(vc);
    }

    @Override
    public Stats.Board deserialize(
            final JsonParser jsonParser,
            final DeserializationContext context)
            throws IOException {
        int numChips = 0;
        final List<List<String>> cs = new LinkedList<>();
        final JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        if (node.has("cn")) {
            numChips = node.get("cn").intValue();

            final JsonNode chips = node.get("cs");

            final Iterator<JsonNode> chipsIterator = chips.elements();
            while (chipsIterator.hasNext()) {
                final List<String> chipStats = new LinkedList<>();

                final JsonNode chip = chipsIterator.next();
                final Iterator<JsonNode> chipValue = chip.elements();
                while (chipValue.hasNext()) {
                    final JsonNode value = chipValue.next();
                    chipStats.add(value.asText());
                }

                cs.add(chipStats);
            }
        }

        final Stats.Board board = new Stats.Board();
        board.chipNumber = numChips;
        board.chips = cs;
        return board;
    }
}
