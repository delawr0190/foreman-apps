package mn.foreman.castxmr.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import static junit.framework.TestCase.assertEquals;

/** Unit tests for {@link Response}. */
public class ResponseTest {

    /**
     * Tests JSON parsing.
     *
     * @throws IOException on failure to parse.
     */
    @Test
    public void testParse() throws IOException {
        final String json =
                "{\n" +
                        "  \"total_hash_rate\": 1857957,\n" +
                        "  \"total_hash_rate_avg\": 1812193,\n" +
                        "  \"pool\": {\n" +
                        "    \"server\": \"pool.supportxmr.com:7777\",\n" +
                        "    \"status\": \"connected\",\n" +
                        "    \"online\": 15,\n" +
                        "    \"offline\": 0,\n" +
                        "    \"reconnects\": 0,\n" +
                        "    \"time_connected\": \"2017-12-13 16:23:40\",\n" +
                        "    \"time_disconnected\": \"2017-12-13 16:23:40\"\n" +
                        "  },\n" +
                        "  \"job\": {\n" +
                        "    \"job_number\": 3,\n" +
                        "    \"difficulty\": 25000,\n" +
                        "    \"running\": 11,\n" +
                        "    \"job_time_avg\": 1.50\n" +
                        "  },\n" +
                        "  \"shares\": {\n" +
                        "    \"num_accepted\": 2,\n" +
                        "    \"num_rejected\": 0,\n" +
                        "    \"num_invalid\": 0,\n" +
                        "    \"num_network_fail\": 0,\n" +
                        "    \"num_outdated\": 0,\n" +
                        "    \"search_time_avg\": 5.00\n" +
                        "  },\n" +
                        "  \"devices\": [\n" +
                        "    {\n" +
                        "      \"device\": \"GPU0\",\n" +
                        "      \"device_id\": 0,\n" +
                        "      \"hash_rate\": 1857957,\n" +
                        "      \"hash_rate_avg\": 1812193,\n" +
                        "      \"gpu_temperature\": 40,\n" +
                        "      \"gpu_fan_rpm\": 3690\n" +
                        "    },\n" +
                        "    {\n" +
                        "      \"device\": \"GPU1\",\n" +
                        "      \"device_id\": 1,\n" +
                        "      \"hash_rate\": 1857957,\n" +
                        "      \"hash_rate_avg\": 1812193,\n" +
                        "      \"gpu_temperature\": 41,\n" +
                        "      \"gpu_fan_rpm\": 3691\n" +
                        "    },\n" +
                        "    {\n" +
                        "      \"device\": \"GPU2\",\n" +
                        "      \"device_id\": 2,\n" +
                        "      \"hash_rate\": 1857957,\n" +
                        "      \"hash_rate_avg\": 1812193,\n" +
                        "      \"gpu_temperature\": 42,\n" +
                        "      \"gpu_fan_rpm\": 3692\n" +
                        "    },\n" +
                        "    {\n" +
                        "      \"device\": \"GPU3\",\n" +
                        "      \"device_id\": 3,\n" +
                        "      \"hash_rate\": 1857957,\n" +
                        "      \"hash_rate_avg\": 1812193,\n" +
                        "      \"gpu_temperature\": 43,\n" +
                        "      \"gpu_fan_rpm\": 3693\n" +
                        "    },\n" +
                        "    {\n" +
                        "      \"device\": \"GPU4\",\n" +
                        "      \"device_id\": 4,\n" +
                        "      \"hash_rate\": 1857957,\n" +
                        "      \"hash_rate_avg\": 1812193,\n" +
                        "      \"gpu_temperature\": 44,\n" +
                        "      \"gpu_fan_rpm\": 3694\n" +
                        "    },\n" +
                        "    {\n" +
                        "      \"device\": \"GPU5\",\n" +
                        "      \"device_id\": 5,\n" +
                        "      \"hash_rate\": 1857957,\n" +
                        "      \"hash_rate_avg\": 1812193,\n" +
                        "      \"gpu_temperature\": 45,\n" +
                        "      \"gpu_fan_rpm\": 3695\n" +
                        "    }\n" +
                        "  ]\n" +
                        "}\n";

        final Response response =
                new ObjectMapper()
                        .readValue(
                                json,
                                Response.class);

        assertEquals(
                new BigDecimal(1857957),
                response.hashRate);

        final Response.Pool pool = response.pool;
        assertEquals(
                "pool.supportxmr.com:7777",
                pool.server);
        assertEquals(
                "connected",
                pool.status);

        final Response.Shares shares = response.shares;
        assertEquals(
                2,
                shares.accepted);
        assertEquals(
                0,
                shares.rejected);
        assertEquals(
                0,
                shares.invalid);

        final List<Response.Device> devices = response.devices;
        for (int i = 0; i < devices.size(); i++) {
            validateDevice(
                    devices.get(i),
                    i);
        }
    }

    /**
     * Validates the provided {@link Response.Device} based on the index
     * provided.
     *
     * @param device The {@link Response.Device}.
     * @param index  The index.
     */
    private static void validateDevice(
            final Response.Device device,
            int index) {
        assertEquals(
                "GPU" + index,
                device.device);
        assertEquals(
                index,
                device.deviceId);
        assertEquals(
                40 + index,
                device.temperature);
        assertEquals(
                3690 + index,
                device.fanRpm);
    }
}