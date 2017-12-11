package mn.foreman.model.miners;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.io.IOException;
import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

/** Unit tests for {@link Asic}. */
public class AsicTest {

    /**
     * Verifies that {@link Asic ASICs} can be serialized.
     *
     * @throws IOException On failure to (de)serialize.
     */
    @Test
    public void testSerialization()
            throws IOException {
        final String name = "name";
        final BigDecimal temperature = new BigDecimal(69);

        final Asic asic =
                new Asic.Builder()
                        .setName(name)
                        .setTemperature(temperature)
                        .build();

        final ObjectMapper objectMapper =
                new ObjectMapper();

        final String json =
                objectMapper.writeValueAsString(asic);

        final Asic newAsic =
                objectMapper.readValue(
                        json,
                        Asic.class);

        assertEquals(
                asic,
                newAsic);
        assertEquals(
                name,
                newAsic.getName());
        assertEquals(
                temperature,
                newAsic.getTemperature());
    }
}