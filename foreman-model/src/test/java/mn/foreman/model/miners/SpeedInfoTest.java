package mn.foreman.model.miners;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class SpeedInfoTest {

    /**
     * Verifies that {@link SpeedInfo speed infos} can be serialized.
     *
     * @throws IOException On failure to (de)serialize.
     */
    @Test
    public void testSerialization()
            throws IOException {
        final String hashRate = "13.5 TH/s";
        final String avgHashRate = "12.99 TH/s";

        final SpeedInfo speedInfo =
                new SpeedInfo.Builder()
                        .setHashRate(hashRate)
                        .setAvgHashRate(avgHashRate)
                        .build();

        final ObjectMapper objectMapper =
                new ObjectMapper();

        final String json =
                objectMapper.writeValueAsString(speedInfo);

        final SpeedInfo newSpeedInfo =
                objectMapper.readValue(
                        json,
                        SpeedInfo.class);

        assertEquals(
                speedInfo,
                newSpeedInfo);
        assertEquals(
                hashRate,
                newSpeedInfo.getHashRate());
        assertEquals(
                avgHashRate,
                newSpeedInfo.getAvgHashRate());
    }
}