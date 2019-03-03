package mn.foreman.autominer;

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
import java.util.Arrays;

/** Runs an integration tests using {@link AutoMiner} against a fake API. */
public class AutoMinerGpuMismatchITest
        extends AbstractApiITest {

    /** Constructor. */
    public AutoMinerGpuMismatchITest() {
        super(
                new AutoMinerFactory().create(
                        ImmutableMap.of(
                                "apiIp",
                                "127.0.0.1",
                                "apiPort",
                                "1234")),
                Arrays.asList(
                        new FakeHttpMinerServer(
                                1234,
                                ImmutableMap.of(
                                        "/summary",
                                        new HttpHandler(
                                                "",
                                                "{\n" +
                                                        "  \"GPUMiner\": \"trex\",\n" +
                                                        "  \"GPUAlgo\": \"x16rt\",\n" +
                                                        "  \"GPUApiPort\": 4067,\n" +
                                                        "  \"CPUMiner\": \"NONE\",\n" +
                                                        "  \"CPUAlgo\": \"NONE\",\n" +
                                                        "  \"CPUApiPort\": 0,\n" +
                                                        "  \"RigGroupGPU\": 17,\n" +
                                                        "  \"RigGroupCPU\": 18,\n" +
                                                        "  \"GPUList\": [\n" +
                                                        "    {\n" +
                                                        "      \"Id\": 0,\n" +
                                                        "      \"Utilization\": 99,\n" +
                                                        "      \"Temperature\": 70,\n" +
                                                        "      \"FanSpeed\": \" 47\",\n" +
                                                        "      \"MemoryClock\": 5005,\n" +
                                                        "      \"CoreClock\": 1733\n" +
                                                        "    },\n" +
                                                        "    {\n" +
                                                        "      \"Id\": 1,\n" +
                                                        "      \"Utilization\": 99,\n" +
                                                        "      \"Temperature\": 65,\n" +
                                                        "      \"FanSpeed\": \" 35\",\n" +
                                                        "      \"MemoryClock\": 5005,\n" +
                                                        "      \"CoreClock\": 1784\n" +
                                                        "    }" +
                                                        "  ]\n" +
                                                        "}"))),
                        new FakeHttpMinerServer(
                                4067,
                                ImmutableMap.of(
                                        "/summary",
                                        new HttpHandler(
                                                "",
                                                "{\n" +
                                                        "  \"accepted_count\": 1,\n" +
                                                        "  \"active_pool\": {\n" +
                                                        "    \"difficulty\": 16,\n" +
                                                        "    \"ping\": 347,\n" +
                                                        "    \"retries\": 0,\n" +
                                                        "    \"url\": \"stratum+tcp:\\/\\/pool.minermore.com:4501\",\n" +
                                                        "    \"user\": \"1MKrTujwHxda6knL8hHrPMi7Git4upX56E\"\n" +
                                                        "  },\n" +
                                                        "  \"algorithm\": \"x16r\",\n" +
                                                        "  \"api\": \"3.0\",\n" +
                                                        "  \"cuda\": \"9.20\",\n" +
                                                        "  \"description\": \"T-Rex NVIDIA GPU miner\",\n" +
                                                        "  \"difficulty\": 51151.705016436,\n" +
                                                        "  \"gpu_total\": 1,\n" +
                                                        "  \"gpus\": [\n" +
                                                        "    {\n" +
                                                        "      \"device_id\": 0,\n" +
                                                        "      \"gpu_id\": 0,\n" +
                                                        "      \"hashrate\": 7748158,\n" +
                                                        "      \"hashrate_day\": 0,\n" +
                                                        "      \"hashrate_hour\": 0,\n" +
                                                        "      \"hashrate_instant\": 9208880,\n" +
                                                        "      \"hashrate_minute\": 7748158,\n" +
                                                        "      \"intensity\": 20,\n" +
                                                        "      \"name\": \"GeForce GTX 1050 Ti\",\n" +
                                                        "      \"temperature\": 68,\n" +
                                                        "      \"vendor\": \"NVIDIA\"\n" +
                                                        "    }\n" +
                                                        "  ],\n" +
                                                        "  \"hashrate\": 7748158,\n" +
                                                        "  \"hashrate_day\": 0,\n" +
                                                        "  \"hashrate_hour\": 0,\n" +
                                                        "  \"hashrate_minute\": 7748158,\n" +
                                                        "  \"name\": \"t-rex\",\n" +
                                                        "  \"os\": \"win\",\n" +
                                                        "  \"rejected_count\": 0,\n" +
                                                        "  \"sharerate\": 1,\n" +
                                                        "  \"sharerate_average\": 1.361,\n" +
                                                        "  \"solved_count\": 0,\n" +
                                                        "  \"success\": 1,\n" +
                                                        "  \"ts\": 1547424889,\n" +
                                                        "  \"uptime\": 56,\n" +
                                                        "  \"version\": \"0.9.1\"\n" +
                                                        "}")))),
                new MinerStats.Builder()
                        .setApiIp("127.0.0.1")
                        .setApiPort(1234)
                        .addPool(
                                new Pool.Builder()
                                        .setName("pool.minermore.com:4501")
                                        .setPriority(0)
                                        .setStatus(true, true)
                                        .setCounts(1, 0, 0)
                                        .build())
                        .addRig(
                                new Rig.Builder()
                                        .setHashRate(new BigDecimal(7748158))
                                        .addGpu(
                                                new Gpu.Builder()
                                                        .setIndex(0)
                                                        .setBus(0)
                                                        .setName("GeForce GTX 1050 Ti")
                                                        .setTemp(68)
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
                                        .addAttribute(
                                                "mrr_group",
                                                "17")
                                        .build())
                        .build());
    }
}