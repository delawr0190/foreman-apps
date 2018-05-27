package mn.foreman.model.miners;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.math.BigDecimal;

/**
 * A {@link BigDecimalSerializer} provides a mechanism for serializing {@link
 * BigDecimal} values into a form that the API accepts.
 */
public class BigDecimalSerializer
        extends JsonSerializer<BigDecimal> {

    /** The maximum precision. */
    private static final int MAX_PRECISION = 10;

    @Override
    public void serialize(
            final BigDecimal bigDecimal,
            final JsonGenerator generator,
            final SerializerProvider serializers) throws IOException {
        BigDecimal value = bigDecimal;
        if (value.precision() > MAX_PRECISION) {
            value = value.setScale(MAX_PRECISION, BigDecimal.ROUND_UP);
        }
        generator.writeString(value.toPlainString());
    }
}