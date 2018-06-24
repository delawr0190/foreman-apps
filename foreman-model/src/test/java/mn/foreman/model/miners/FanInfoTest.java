package mn.foreman.model.miners;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static junit.framework.TestCase.assertEquals;

/** Unit tests for {@link FanInfo}. */
public class FanInfoTest {

    /**
     * Verifies that {@link FanInfo fan infos} can be serialized.
     *
     * @throws IOException On failure to (de)serialize.
     */
    @Test
    public void testSerialization()
            throws IOException {
        final int count = 2;
        final List<Integer> speeds = Arrays.asList(3000, 4000);

        final FanInfo fanInfo =
                new FanInfo.Builder()
                        .setCount(count)
                        .addSpeed(speeds.get(0))
                        .addSpeed(speeds.get(1))
                        .setSpeedUnits("RPM")
                        .build();

        final ObjectMapper objectMapper =
                new ObjectMapper();

        final String json =
                objectMapper.writeValueAsString(fanInfo);

        final FanInfo newFanInfo =
                objectMapper.readValue(
                        json,
                        FanInfo.class);

        assertEquals(
                fanInfo,
                newFanInfo);
        assertEquals(
                count,
                newFanInfo.getCount());
        assertEquals(
                speeds,
                newFanInfo.getSpeeds());
    }
}