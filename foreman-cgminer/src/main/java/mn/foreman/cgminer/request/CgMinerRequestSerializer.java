package mn.foreman.cgminer.request;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/** A custom serializer to produce JSON that cgminer expects. */
public class CgMinerRequestSerializer
        extends StdSerializer<CgMinerRequest> {

    /** Constructor. */
    public CgMinerRequestSerializer() {
        this(null);
    }

    /**
     * Constructor.
     *
     * @param t The type.
     */
    public CgMinerRequestSerializer(
            final Class<CgMinerRequest> t) {
        super(t);
    }

    @Override
    public void serialize(
            final CgMinerRequest value,
            final JsonGenerator gen,
            final SerializerProvider provider)
            throws IOException {
        gen.writeStartObject();
        gen.writeStringField(
                "command",
                value.getCommand().getCommand());

        // Only write out comma-separate parameters if they exist
        final List<String> parameters = value.getParameters();
        if (!parameters.isEmpty()) {
            gen.writeStringField(
                    "parameter",
                    parameters.stream().collect(
                            Collectors.joining(",")));
        }

        gen.writeEndObject();
    }
}
