package mn.foreman.castxmr;

import mn.foreman.model.miners.FanInfo;
import mn.foreman.model.miners.MinerStats;
import mn.foreman.model.miners.Pool;
import mn.foreman.model.miners.rig.FreqInfo;
import mn.foreman.model.miners.rig.Gpu;
import mn.foreman.model.miners.rig.Rig;
import mn.foreman.util.AbstractApiITest;
import mn.foreman.util.http.FakeHttpMinerServer;
import mn.foreman.util.http.HttpHandler;

import com.google.common.collect.ImmutableMap;

import java.math.BigDecimal;

/** Runs an integration tests using {@link Castxmr} against a fake API. */
public class CastxmrITest
        extends AbstractApiITest {

    /** Constructor. */
    public CastxmrITest() {
        super(
                new Castxmr(
                        "127.0.0.1",
                        7777),
                new FakeHttpMinerServer(
                        7777,
                        ImmutableMap.of(
                                "/",
                                new HttpHandler(
                                        "",
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
                                                "}"))),
                new MinerStats.Builder()
                        .setApiIp("127.0.0.1")
                        .setApiPort(7777)
                        .addPool(
                                new Pool.Builder()
                                        .setName("pool.supportxmr.com:7777")
                                        .setPriority(0)
                                        .setStatus(true, true)
                                        .setCounts(2, 0, 0)
                                        .build())
                        .addRig(
                                new Rig.Builder()
                                        .setHashRate(new BigDecimal(1857957))
                                        .addGpu(
                                                new Gpu.Builder()
                                                        .setIndex(0)
                                                        .setBus(0)
                                                        .setName("GPU0")
                                                        .setTemp(40)
                                                        .setFans(
                                                                new FanInfo.Builder()
                                                                        .setCount(1)
                                                                        .addSpeed(3690)
                                                                        .setSpeedUnits("%")
                                                                        .build())
                                                        .setFreqInfo(
                                                                new FreqInfo.Builder()
                                                                        .setFreq(0)
                                                                        .setMemFreq(0)
                                                                        .build())
                                                        .build())
                                        .addGpu(
                                                new Gpu.Builder()
                                                        .setIndex(1)
                                                        .setBus(1)
                                                        .setName("GPU1")
                                                        .setTemp(41)
                                                        .setFans(
                                                                new FanInfo.Builder()
                                                                        .setCount(1)
                                                                        .addSpeed(3691)
                                                                        .setSpeedUnits("%")
                                                                        .build())
                                                        .setFreqInfo(
                                                                new FreqInfo.Builder()
                                                                        .setFreq(0)
                                                                        .setMemFreq(0)
                                                                        .build())
                                                        .build())
                                        .addGpu(
                                                new Gpu.Builder()
                                                        .setIndex(2)
                                                        .setBus(2)
                                                        .setName("GPU2")
                                                        .setTemp(42)
                                                        .setFans(
                                                                new FanInfo.Builder()
                                                                        .setCount(1)
                                                                        .addSpeed(3692)
                                                                        .setSpeedUnits("%")
                                                                        .build())
                                                        .setFreqInfo(
                                                                new FreqInfo.Builder()
                                                                        .setFreq(0)
                                                                        .setMemFreq(0)
                                                                        .build())
                                                        .build())
                                        .addGpu(
                                                new Gpu.Builder()
                                                        .setIndex(3)
                                                        .setBus(3)
                                                        .setName("GPU3")
                                                        .setTemp(43)
                                                        .setFans(
                                                                new FanInfo.Builder()
                                                                        .setCount(1)
                                                                        .addSpeed(3693)
                                                                        .setSpeedUnits("%")
                                                                        .build())
                                                        .setFreqInfo(
                                                                new FreqInfo.Builder()
                                                                        .setFreq(0)
                                                                        .setMemFreq(0)
                                                                        .build())
                                                        .build())
                                        .addGpu(
                                                new Gpu.Builder()
                                                        .setIndex(4)
                                                        .setBus(4)
                                                        .setName("GPU4")
                                                        .setTemp(44)
                                                        .setFans(
                                                                new FanInfo.Builder()
                                                                        .setCount(1)
                                                                        .addSpeed(3694)
                                                                        .setSpeedUnits("%")
                                                                        .build())
                                                        .setFreqInfo(
                                                                new FreqInfo.Builder()
                                                                        .setFreq(0)
                                                                        .setMemFreq(0)
                                                                        .build())
                                                        .build())
                                        .addGpu(
                                                new Gpu.Builder()
                                                        .setIndex(5)
                                                        .setBus(5)
                                                        .setName("GPU5")
                                                        .setTemp(45)
                                                        .setFans(
                                                                new FanInfo.Builder()
                                                                        .setCount(1)
                                                                        .addSpeed(3695)
                                                                        .setSpeedUnits("%")
                                                                        .build())
                                                        .setFreqInfo(
                                                                new FreqInfo.Builder()
                                                                        .setFreq(0)
                                                                        .setMemFreq(0)
                                                                        .build())
                                                        .build())
                                        .build())
                        .build());
    }
}