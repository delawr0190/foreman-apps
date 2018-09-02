package mn.foreman.trex;

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

/** Runs an integration tests using {@link Trex} against a fake API. */
public class Api1_1TrexITest
        extends AbstractApiITest {

    /** Constructor. */
    public Api1_1TrexITest() {
        super(
                new Trex(
                        "127.0.0.1",
                        4067),
                new FakeHttpMinerServer(
                        4067,
                        ImmutableMap.of(
                                "/summary",
                                new HttpHandler(
                                        "",
                                        "{\n" +
                                                "  \"accepted_count\": 10,\n" +
                                                "  \"active_pool\": {\n" +
                                                "    \"difficulty\": 8,\n" +
                                                "    \"ping\": 140,\n" +
                                                "    \"retries\": 0,\n" +
                                                "    \"url\": \"stratum+tcp://us.ravenminer.com:4567\",\n" +
                                                "    \"user\": \"xxxx\"\n" +
                                                "  },\n" +
                                                "  \"algorithm\": \"x16r\",\n" +
                                                "  \"api\": \"1.1\",\n" +
                                                "  \"cuda\": \"9.20\",\n" +
                                                "  \"description\": \"T-Rex NVIDIA GPU miner\",\n" +
                                                "  \"difficulty\": 32129.671888325327,\n" +
                                                "  \"gpu_total\": 8,\n" +
                                                "  \"gpus\": [\n" +
                                                "    {\n" +
                                                "      \"HW\": 98971.9378238342,\n" +
                                                "      \"device_id\": 0,\n" +
                                                "      \"fan_speed\": 56,\n" +
                                                "      \"gpu_id\": 0,\n" +
                                                "      \"hashrate\": 19101584,\n" +
                                                "      \"hashrate_day\": 0,\n" +
                                                "      \"hashrate_hour\": 0,\n" +
                                                "      \"hashrate_minute\": 19257829,\n" +
                                                "      \"intensity\": 20,\n" +
                                                "      \"name\": \"GeForce GTX 1070 Ti\",\n" +
                                                "      \"power\": 193,\n" +
                                                "      \"temperature\": 57,\n" +
                                                "      \"vendor\": \"EVGA\"\n" +
                                                "    },\n" +
                                                "    {\n" +
                                                "      \"HW\": 96907.94329896907,\n" +
                                                "      \"device_id\": 1,\n" +
                                                "      \"fan_speed\": 52,\n" +
                                                "      \"gpu_id\": 1,\n" +
                                                "      \"hashrate\": 18800141,\n" +
                                                "      \"hashrate_day\": 0,\n" +
                                                "      \"hashrate_hour\": 0,\n" +
                                                "      \"hashrate_minute\": 18770078,\n" +
                                                "      \"intensity\": 20,\n" +
                                                "      \"name\": \"GeForce GTX 1070 Ti\",\n" +
                                                "      \"power\": 194,\n" +
                                                "      \"temperature\": 53,\n" +
                                                "      \"vendor\": \"EVGA\"\n" +
                                                "    },\n" +
                                                "    {\n" +
                                                "      \"HW\": 97651.11917098446,\n" +
                                                "      \"device_id\": 2,\n" +
                                                "      \"fan_speed\": 56,\n" +
                                                "      \"gpu_id\": 2,\n" +
                                                "      \"hashrate\": 18846666,\n" +
                                                "      \"hashrate_day\": 0,\n" +
                                                "      \"hashrate_hour\": 0,\n" +
                                                "      \"hashrate_minute\": 18902532,\n" +
                                                "      \"intensity\": 20,\n" +
                                                "      \"name\": \"GeForce GTX 1070 Ti\",\n" +
                                                "      \"power\": 193,\n" +
                                                "      \"temperature\": 57,\n" +
                                                "      \"vendor\": \"EVGA\"\n" +
                                                "    },\n" +
                                                "    {\n" +
                                                "      \"HW\": 95317.5,\n" +
                                                "      \"device_id\": 3,\n" +
                                                "      \"fan_speed\": 53,\n" +
                                                "      \"gpu_id\": 3,\n" +
                                                "      \"hashrate\": 18872865,\n" +
                                                "      \"hashrate_day\": 0,\n" +
                                                "      \"hashrate_hour\": 0,\n" +
                                                "      \"hashrate_minute\": 18925389,\n" +
                                                "      \"intensity\": 20,\n" +
                                                "      \"name\": \"GeForce GTX 1070 Ti\",\n" +
                                                "      \"power\": 198,\n" +
                                                "      \"temperature\": 55,\n" +
                                                "      \"vendor\": \"EVGA\"\n" +
                                                "    },\n" +
                                                "    {\n" +
                                                "      \"HW\": 100180.74074074074,\n" +
                                                "      \"device_id\": 4,\n" +
                                                "      \"fan_speed\": 55,\n" +
                                                "      \"gpu_id\": 4,\n" +
                                                "      \"hashrate\": 18934160,\n" +
                                                "      \"hashrate_day\": 0,\n" +
                                                "      \"hashrate_hour\": 0,\n" +
                                                "      \"hashrate_minute\": 19111002,\n" +
                                                "      \"intensity\": 20,\n" +
                                                "      \"name\": \"GeForce GTX 1070 Ti\",\n" +
                                                "      \"power\": 189,\n" +
                                                "      \"temperature\": 56,\n" +
                                                "      \"vendor\": \"EVGA\"\n" +
                                                "    },\n" +
                                                "    {\n" +
                                                "      \"HW\": 99529.24736842106,\n" +
                                                "      \"device_id\": 5,\n" +
                                                "      \"fan_speed\": 56,\n" +
                                                "      \"gpu_id\": 5,\n" +
                                                "      \"hashrate\": 18910557,\n" +
                                                "      \"hashrate_day\": 0,\n" +
                                                "      \"hashrate_hour\": 0,\n" +
                                                "      \"hashrate_minute\": 18905498,\n" +
                                                "      \"intensity\": 20,\n" +
                                                "      \"name\": \"GeForce GTX 1070 Ti\",\n" +
                                                "      \"power\": 190,\n" +
                                                "      \"temperature\": 57,\n" +
                                                "      \"vendor\": \"EVGA\"\n" +
                                                "    },\n" +
                                                "    {\n" +
                                                "      \"HW\": 97652.86082474227,\n" +
                                                "      \"device_id\": 6,\n" +
                                                "      \"fan_speed\": 53,\n" +
                                                "      \"gpu_id\": 6,\n" +
                                                "      \"hashrate\": 18944655,\n" +
                                                "      \"hashrate_day\": 0,\n" +
                                                "      \"hashrate_hour\": 0,\n" +
                                                "      \"hashrate_minute\": 18968503,\n" +
                                                "      \"intensity\": 20,\n" +
                                                "      \"name\": \"GeForce GTX 1070 Ti\",\n" +
                                                "      \"power\": 194,\n" +
                                                "      \"temperature\": 54,\n" +
                                                "      \"vendor\": \"EVGA\"\n" +
                                                "    },\n" +
                                                "    {\n" +
                                                "      \"HW\": 100744.32044198895,\n" +
                                                "      \"device_id\": 7,\n" +
                                                "      \"fan_speed\": 57,\n" +
                                                "      \"gpu_id\": 7,\n" +
                                                "      \"hashrate\": 18234722,\n" +
                                                "      \"hashrate_day\": 0,\n" +
                                                "      \"hashrate_hour\": 0,\n" +
                                                "      \"hashrate_minute\": 18167319,\n" +
                                                "      \"intensity\": 20,\n" +
                                                "      \"name\": \"GeForce GTX 1070 Ti\",\n" +
                                                "      \"power\": 181,\n" +
                                                "      \"temperature\": 58,\n" +
                                                "      \"vendor\": \"EVGA\"\n" +
                                                "    }\n" +
                                                "  ],\n" +
                                                "  \"hashrate\": 150645350,\n" +
                                                "  \"hashrate_day\": 0,\n" +
                                                "  \"hashrate_hour\": 0,\n" +
                                                "  \"hashrate_minute\": 151008150,\n" +
                                                "  \"name\": \"t-rex\",\n" +
                                                "  \"os\": \"win\",\n" +
                                                "  \"rejected_count\": 0,\n" +
                                                "  \"ts\": 1535896052,\n" +
                                                "  \"uptime\": 31,\n" +
                                                "  \"version\": \"0.6.3\"\n" +
                                                "}"))),
                new MinerStats.Builder()
                        .setApiIp("127.0.0.1")
                        .setApiPort(4067)
                        .addPool(
                                new Pool.Builder()
                                        .setName("us.ravenminer.com:4567")
                                        .setPriority(0)
                                        .setStatus(true, true)
                                        .setCounts(10, 0, 0)
                                        .build())
                        .addRig(
                                new Rig.Builder()
                                        .setHashRate(new BigDecimal(150645350))
                                        .addGpu(
                                                new Gpu.Builder()
                                                        .setIndex(0)
                                                        .setBus(0)
                                                        .setName("GeForce GTX 1070 Ti")
                                                        .setTemp(57)
                                                        .setFans(
                                                                new FanInfo.Builder()
                                                                        .setCount(1)
                                                                        .addSpeed(56)
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
                                                        .setBus(0)
                                                        .setName("GeForce GTX 1070 Ti")
                                                        .setTemp(53)
                                                        .setFans(
                                                                new FanInfo.Builder()
                                                                        .setCount(1)
                                                                        .addSpeed(52)
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
                                                        .setBus(0)
                                                        .setName("GeForce GTX 1070 Ti")
                                                        .setTemp(57)
                                                        .setFans(
                                                                new FanInfo.Builder()
                                                                        .setCount(1)
                                                                        .addSpeed(56)
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
                                                        .setBus(0)
                                                        .setName("GeForce GTX 1070 Ti")
                                                        .setTemp(55)
                                                        .setFans(
                                                                new FanInfo.Builder()
                                                                        .setCount(1)
                                                                        .addSpeed(53)
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
                                                        .setBus(0)
                                                        .setName("GeForce GTX 1070 Ti")
                                                        .setTemp(56)
                                                        .setFans(
                                                                new FanInfo.Builder()
                                                                        .setCount(1)
                                                                        .addSpeed(55)
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
                                                        .setBus(0)
                                                        .setName("GeForce GTX 1070 Ti")
                                                        .setTemp(57)
                                                        .setFans(
                                                                new FanInfo.Builder()
                                                                        .setCount(1)
                                                                        .addSpeed(56)
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
                                                        .setIndex(6)
                                                        .setBus(0)
                                                        .setName("GeForce GTX 1070 Ti")
                                                        .setTemp(54)
                                                        .setFans(
                                                                new FanInfo.Builder()
                                                                        .setCount(1)
                                                                        .addSpeed(53)
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
                                                        .setIndex(7)
                                                        .setBus(0)
                                                        .setName("GeForce GTX 1070 Ti")
                                                        .setTemp(58)
                                                        .setFans(
                                                                new FanInfo.Builder()
                                                                        .setCount(1)
                                                                        .addSpeed(57)
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