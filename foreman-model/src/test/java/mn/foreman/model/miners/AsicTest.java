package mn.foreman.model.miners;

import mn.foreman.model.miners.asic.Asic;

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
    @SuppressWarnings("ConstantConditions")
    public void testSerialization()
            throws IOException {
        final String powerState = "powerState";
        final BigDecimal hashRate = new BigDecimal(1);
        final FanInfo fanInfo =
                new FanInfo.Builder()
                        .setCount(2)
                        .addSpeed(42)
                        .addSpeed(43)
                        .setSpeedUnits("RPM")
                        .build();
        final int temp = 32;
        final Boolean hasErrors = true;

        final Asic asic =
                new Asic.Builder()
                        .setHashRate(hashRate)
                        .setFanInfo(fanInfo)
                        .addTemp(temp)
                        .setPowerState(powerState)
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
    }
}