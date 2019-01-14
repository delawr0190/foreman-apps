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
public class Api3_0TrexITest
        extends AbstractApiITest {

    /** Constructor. */
    public Api3_0TrexITest() {
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
                                                "}"))),
                new MinerStats.Builder()
                        .setApiIp("127.0.0.1")
                        .setApiPort(4067)
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
                                        .build())
                        .build());
    }
}