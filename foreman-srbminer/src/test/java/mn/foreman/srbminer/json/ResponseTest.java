package mn.foreman.srbminer.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;

/** Unit tests for {@link Response}. */
public class ResponseTest {

    /** Tests JSON serialization. */
    @Test
    public void testParse() throws IOException {
        final String json =
                "{\n" +
                        "    \"rig_name\": \"SRBMiner-Rig\",\n" +
                        "    \"cryptonight_type\": \"normalv7\",\n" +
                        "    \"mining_time\": 10,\n" +
                        "    \"total_devices\": 1,\n" +
                        "    \"total_threads\": 2,\n" +
                        "    \"hashrate_total_now\": 12345678,\n" +
                        "    \"hashrate_total_1min\": 0,\n" +
                        "    \"hashrate_total_5min\": 0,\n" +
                        "    \"hashrate_total_30min\": 0,\n" +
                        "    \"hashrate_total_max\": 0,\n" +
                        "    \"pool\": {\n" +
                        "        \"pool\": \"xmr-eu1.nanopool.org:14444\",\n" +
                        "        \"difficulty\": 120001,\n" +
                        "        \"time_connected\": \"2018-07-29 22:12:41\",\n" +
                        "        \"uptime\": 10,\n" +
                        "        \"latency\": 0,\n" +
                        "        \"last_job_received\": 5\n" +
                        "    },\n" +
                        "    \"shares\": {\n" +
                        "        \"total\": 0,\n" +
                        "        \"accepted\": 0,\n" +
                        "        \"accepted_stale\": 0,\n" +
                        "        \"rejected\": 0,\n" +
                        "        \"avg_find_time\": 0\n" +
                        "    },\n" +
                        "    \"devices\": [\n" +
                        "        {\n" +
                        "            \"device\": \"GPU0\",\n" +
                        "            \"device_id\": 0,\n" +
                        "            \"model\": \"Radeon RX 560 Series\",\n" +
                        "            \"bus_id\": 2,\n" +
                        "            \"kernel_id\": 1,\n" +
                        "            \"hashrate\": 0,\n" +
                        "            \"core_clock\": 1187,\n" +
                        "            \"memory_clock\": 1500,\n" +
                        "            \"temperature\": 44,\n" +
                        "            \"fan_speed_rpm\": 1538\n" +
                        "        }\n" +
                        "    ]\n" +
                        "}";

        final Response response =
                new ObjectMapper()
                        .readValue(
                                json,
                                Response.class);
        assertEquals(
                "SRBMiner-Rig",
                response.rigName);
        assertEquals(
                "normalv7",
                response.cryptonightType);
        assertEquals(
                10,
                response.miningTime);
        assertEquals(
                1,
                response.totalDevices);
        assertEquals(
                2,
                response.totalThreads);
        assertEquals(
                new BigDecimal("12345678"),
                response.hashRate);
        assertEquals(
                BigDecimal.ZERO,
                response.hashRate1Min);
        assertEquals(
                BigDecimal.ZERO,
                response.hashRate5Min);
        assertEquals(
                BigDecimal.ZERO,
                response.hashRate30Min);
        assertEquals(
                BigDecimal.ZERO,
                response.hashRateMax);

        final Response.Pool pool = response.pool;
        assertNotNull(pool);
        assertEquals(
                "xmr-eu1.nanopool.org:14444",
                pool.pool);
        assertEquals(
                120001,
                pool.difficulty);
        assertEquals(
                "2018-07-29 22:12:41",
                pool.timeConnected);
        assertEquals(
                10,
                pool.uptime);
        assertEquals(
                0,
                pool.latency);
        assertEquals(
                5,
                pool.lastJobReceived);

        final Response.Shares shares = response.shares;
        assertNotNull(shares);
        assertEquals(
                0,
                shares.total);
        assertEquals(
                0,
                shares.accepted);
        assertEquals(
                0,
                shares.acceptedStale);
        assertEquals(
                0,
                shares.rejected);
        assertEquals(
                0,
                shares.avgFindTime);

        final List<Response.Device> devices = response.devices;
        assertEquals(
                1,
                devices.size());

        final Response.Device device = devices.get(0);
        assertEquals(
                "GPU0",
                device.device);
        assertEquals(
                0,
                device.deviceId);
        assertEquals(
                "Radeon RX 560 Series",
                device.model);
        assertEquals(
                2,
                device.busId);
        assertEquals(
                1,
                device.kernelId);
        assertEquals(
                0,
                device.hashRate);
        assertEquals(
                1187,
                device.coreClock);
        assertEquals(
                1500,
                device.memoryClock);
        assertEquals(
                44,
                device.temperature);
        assertEquals(
                1538,
                device.fanSpeedRpm);
    }
}