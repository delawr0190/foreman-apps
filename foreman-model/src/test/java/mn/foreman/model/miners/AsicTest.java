package mn.foreman.model.miners;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Collections;

import static org.junit.Assert.assertEquals;

/** Unit tests for {@link Asic}. */
public class AsicTest {

    /**
     * Verifies that {@link Asic ASICs} can be serialized.
     *
     * @throws IOException On failure to (de)serialize.
     */
    @Test
    @SuppressWarnings("ConstantConditions")
    public void testSerialization()
            throws IOException {
        final String name = "name";
        final SpeedInfo speedInfo =
                new SpeedInfo.Builder()
                        .setAvgHashRate(new BigDecimal(1))
                        .setAvgHashRate5s(new BigDecimal(2))
                        .build();
        final FanInfo fanInfo =
                new FanInfo.Builder()
                        .setCount(2)
                        .addSpeed(42)
                        .addSpeed(43)
                        .build();
        final int temp = 32;
        final Boolean hasErrors = true;

        final Asic asic =
                new Asic.Builder()
                        .setName(name)
                        .setSpeedInfo(speedInfo)
                        .setFanInfo(fanInfo)
                        .addTemp(temp)
                        .hasErrors(hasErrors)
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
                speedInfo,
                newAsic.getSpeedInfo());
        assertEquals(
                fanInfo,
                newAsic.getFans());
        assertEquals(
                Collections.singletonList(temp),
                newAsic.getTemps());
        assertEquals(
                hasErrors,
                newAsic.getHasErrors());
    }
}