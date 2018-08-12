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
public class TrexITest
        extends AbstractApiITest {

    /** Constructor. */
    public TrexITest() {
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
                                                "  \"accepted_count\": 44,\n" +
                                                "  \"active_pool\": {\n" +
                                                "    \"retries\": 0,\n" +
                                                "    \"url\": \"stratum+tcp://blockmasters.co:4553\",\n" +
                                                "    \"user\": \"DMbUXFZfpHpzEGjHMfJivjgNXswmrsdaqB\"\n" +
                                                "  },\n" +
                                                "  \"algorithm\": \"lyra2z\",\n" +
                                                "  \"api\": \"1.0\",\n" +
                                                "  \"cuda\": \"9.20\",\n" +
                                                "  \"description\": \"T-Rex NVIDIA GPU miner\",\n" +
                                                "  \"difficulty\": 53.78282292371265,\n" +
                                                "  \"gpu_total\": 8,\n" +
                                                "  \"gpus\": [\n" +
                                                "    {\n" +
                                                "      \"device_id\": 0,\n" +
                                                "      \"gpu_id\": 0,\n" +
                                                "      \"hashrate\": 3059637,\n" +
                                                "      \"intensity\": 20\n" +
                                                "    },\n" +
                                                "    {\n" +
                                                "      \"device_id\": 1,\n" +
                                                "      \"gpu_id\": 1,\n" +
                                                "      \"hashrate\": 3036386,\n" +
                                                "      \"intensity\": 20\n" +
                                                "    },\n" +
                                                "    {\n" +
                                                "      \"device_id\": 2,\n" +
                                                "      \"gpu_id\": 2,\n" +
                                                "      \"hashrate\": 3033945,\n" +
                                                "      \"intensity\": 20\n" +
                                                "    },\n" +
                                                "    {\n" +
                                                "      \"device_id\": 3,\n" +
                                                "      \"gpu_id\": 3,\n" +
                                                "      \"hashrate\": 3039842,\n" +
                                                "      \"intensity\": 20\n" +
                                                "    },\n" +
                                                "    {\n" +
                                                "      \"device_id\": 4,\n" +
                                                "      \"gpu_id\": 4,\n" +
                                                "      \"hashrate\": 3036523,\n" +
                                                "      \"intensity\": 20\n" +
                                                "    },\n" +
                                                "    {\n" +
                                                "      \"device_id\": 5,\n" +
                                                "      \"gpu_id\": 5,\n" +
                                                "      \"hashrate\": 3074756,\n" +
                                                "      \"intensity\": 20\n" +
                                                "    },\n" +
                                                "    {\n" +
                                                "      \"device_id\": 6,\n" +
                                                "      \"gpu_id\": 6,\n" +
                                                "      \"hashrate\": 3053700,\n" +
                                                "      \"intensity\": 20\n" +
                                                "    },\n" +
                                                "    {\n" +
                                                "      \"device_id\": 7,\n" +
                                                "      \"gpu_id\": 7,\n" +
                                                "      \"hashrate\": 2966131,\n" +
                                                "      \"intensity\": 20\n" +
                                                "    }\n" +
                                                "  ],\n" +
                                                "  \"hashrate\": 24300920,\n" +
                                                "  \"name\": \"t-rex\",\n" +
                                                "  \"os\": \"win\",\n" +
                                                "  \"rejected_count\": 0,\n" +
                                                "  \"ts\": 1533090180,\n" +
                                                "  \"uptime\": 290,\n" +
                                                "  \"version\": \"0.5.6\"\n" +
                                                "}\n"))),
                new MinerStats.Builder()
                        .setApiIp("127.0.0.1")
                        .setApiPort(4067)
                        .addPool(
                                new Pool.Builder()
                                        .setName("blockmasters.co:4553")
                                        .setPriority(0)
                                        .setStatus(true, true)
                                        .setCounts(44, 0, 0)
                                        .build())
                        .addRig(
                                new Rig.Builder()
                                        .setHashRate(new BigDecimal(24300920))
                                        .addGpu(
                                                new Gpu.Builder()
                                                        .setIndex(0)
                                                        .setBus(0)
                                                        .setName("GPU 0")
                                                        .setTemp(0)
                                                        .setFans(
                                                                new FanInfo.Builder()
                                                                        .setCount(0)
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
                                                        .setName("GPU 1")
                                                        .setTemp(0)
                                                        .setFans(
                                                                new FanInfo.Builder()
                                                                        .setCount(0)
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
                                                        .setName("GPU 2")
                                                        .setTemp(0)
                                                        .setFans(
                                                                new FanInfo.Builder()
                                                                        .setCount(0)
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
                                                        .setName("GPU 3")
                                                        .setTemp(0)
                                                        .setFans(
                                                                new FanInfo.Builder()
                                                                        .setCount(0)
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
                                                        .setName("GPU 4")
                                                        .setTemp(0)
                                                        .setFans(
                                                                new FanInfo.Builder()
                                                                        .setCount(0)
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
                                                        .setName("GPU 5")
                                                        .setTemp(0)
                                                        .setFans(
                                                                new FanInfo.Builder()
                                                                        .setCount(0)
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
                                                        .setBus(6)
                                                        .setName("GPU 6")
                                                        .setTemp(0)
                                                        .setFans(
                                                                new FanInfo.Builder()
                                                                        .setCount(0)
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
                                                        .setBus(7)
                                                        .setName("GPU 7")
                                                        .setTemp(0)
                                                        .setFans(
                                                                new FanInfo.Builder()
                                                                        .setCount(0)
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