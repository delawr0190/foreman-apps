package mn.foreman.bminer.json.devices;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.io.IOException;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/** Unit tests for {@link Devices} deserialization. */
public class DevicesTest {

    /** Tests device deserialization. */
    @Test
    public void testDeserialization() throws IOException {
        final int startingTemp = 83;
        final int startingPower = 199;
        final int startingFanSpeed = 74;
        final int startingGlobalMemoryUsed = 4385;
        final int startingUtilGpu = 99;
        final int startingUtilMemory = 73;
        final int startingClocksCore = 1809;
        final int startingClocksMemory = 5005;

        final String response = "{\n" +
                "  \"devices\": {\n" +
                "    \"0\": {\n" +
                "      \"temperature\": " + startingTemp + ",\n" +
                "      \"power\": " + startingPower + ",\n" +
                "      \"fan_speed\": " + startingFanSpeed + ",\n" +
                "      \"global_memory_used\": " + startingGlobalMemoryUsed + ",\n" +
                "      \"utilization\": {\n" +
                "        \"gpu\": " + startingUtilGpu + ",\n" +
                "        \"memory\": " + startingUtilMemory + "\n" +
                "      },\n" +
                "      \"clocks\": {\n" +
                "        \"core\": " + startingClocksCore + ",\n" +
                "        \"memory\": " + startingClocksMemory + "\n" +
                "      }\n" +
                "    },\n" +
                "    \"1\": {\n" +
                "      \"temperature\": " + (startingTemp + 1) + ",\n" +
                "      \"power\": " + (startingPower + 1) + ",\n" +
                "      \"fan_speed\": " + (startingFanSpeed + 1) + ",\n" +
                "      \"global_memory_used\": " + (startingGlobalMemoryUsed + 1) + ",\n" +
                "      \"utilization\": {\n" +
                "        \"gpu\": " + (startingUtilGpu + 1) + ",\n" +
                "        \"memory\": " + (startingUtilMemory + 1) + "\n" +
                "      },\n" +
                "      \"clocks\": {\n" +
                "        \"core\": " + (startingClocksCore + 1) + ",\n" +
                "        \"memory\": " + (startingClocksMemory + 1) + "\n" +
                "      }\n" +
                "    }\n" +
                "  }\n" +
                "}";

        final ObjectMapper objectMapper =
                new ObjectMapper();
        final Devices devices =
                objectMapper.readValue(
                        response,
                        Devices.class);

        final Map<String, Device> deviceMap = devices.devices;
        assertNotNull(deviceMap);
        assertEquals(
                2,
                deviceMap.size());

        for (int i = 0; i < deviceMap.size(); i++) {
            final Device device0 = deviceMap.get("" + i);
            assertEquals(
                    startingTemp + i,
                    device0.temperature);
            assertEquals(
                    startingFanSpeed + i,
                    device0.fanSpeed);
            assertEquals(
                    startingClocksCore + i,
                    device0.clocks.core);
            assertEquals(
                    startingClocksMemory + i,
                    device0.clocks.memory);
        }
    }
}