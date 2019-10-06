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

/** Runs an integration tests using {@link TrexHttp} against a fake API. */
public class TrexUnknownGpuITest
        extends AbstractApiITest {

    /** Constructor. */
    public TrexUnknownGpuITest() {
        super(
                new TrexFactory().create(
                        ImmutableMap.of(
                                "apiIp",
                                "127.0.0.1",
                                "apiPort",
                                "4067")),
                new FakeHttpMinerServer(
                        4067,
                        ImmutableMap.of(
                                "/summary",
                                new HttpHandler(
                                        "",
                                        "{\n" +
                                                "  \"accepted_count\": 126,\n" +
                                                "  \"active_pool\": {\n" +
                                                "    \"difficulty\": 46,\n" +
                                                "    \"ping\": 17,\n" +
                                                "    \"retries\": 0,\n" +
                                                "    \"url\": \"stratum+tcp:\\/\\/x16s.na.mine.zpool.ca:3663\",\n" +
                                                "    \"user\": \"xxxx\"\n" +
                                                "  },\n" +
                                                "  \"algorithm\": \"x16s\",\n" +
                                                "  \"api\": \"3.1\",\n" +
                                                "  \"build_date\": \"Aug  6 2019 20:47:32\",\n" +
                                                "  \"cuda\": \"10.0\",\n" +
                                                "  \"description\": \"T-Rex NVIDIA GPU miner\",\n" +
                                                "  \"difficulty\": 1622.7745886201,\n" +
                                                "  \"gpu_total\": 2,\n" +
                                                "  \"gpus\": [\n" +
                                                "    {\n" +
                                                "      \"device_id\": 0,\n" +
                                                "      \"gpu_id\": 0,\n" +
                                                "      \"gpu_user_id\": 0,\n" +
                                                "      \"hashrate\": 22767767,\n" +
                                                "      \"hashrate_day\": 0,\n" +
                                                "      \"hashrate_hour\": 22426050,\n" +
                                                "      \"hashrate_instant\": 23817650,\n" +
                                                "      \"hashrate_minute\": 22764070,\n" +
                                                "      \"intensity\": 20,\n" +
                                                "      \"name\": \"\",\n" +
                                                "      \"vendor\": \"\"\n" +
                                                "    },\n" +
                                                "    {\n" +
                                                "      \"device_id\": 1,\n" +
                                                "      \"gpu_id\": 1,\n" +
                                                "      \"gpu_user_id\": 1,\n" +
                                                "      \"hashrate\": 23387488,\n" +
                                                "      \"hashrate_day\": 0,\n" +
                                                "      \"hashrate_hour\": 22873350,\n" +
                                                "      \"hashrate_instant\": 24272221,\n" +
                                                "      \"hashrate_minute\": 23388001,\n" +
                                                "      \"intensity\": 20,\n" +
                                                "      \"name\": \"\",\n" +
                                                "      \"vendor\": \"\"\n" +
                                                "    }\n" +
                                                "  ],\n" +
                                                "  \"hashrate\": 46155255,\n" +
                                                "  \"hashrate_day\": 0,\n" +
                                                "  \"hashrate_hour\": 45299400,\n" +
                                                "  \"hashrate_minute\": 46152071,\n" +
                                                "  \"name\": \"t-rex\",\n" +
                                                "  \"os\": \"win\",\n" +
                                                "  \"rejected_count\": 0,\n" +
                                                "  \"revision\": \"8985a5dea707\",\n" +
                                                "  \"sharerate\": 3.618,\n" +
                                                "  \"sharerate_average\": 8.981,\n" +
                                                "  \"solved_count\": 0,\n" +
                                                "  \"success\": 1,\n" +
                                                "  \"ts\": 1569357820,\n" +
                                                "  \"uptime\": 870,\n" +
                                                "  \"version\": \"0.13.2\"\n" +
                                                "}"))),
                new MinerStats.Builder()
                        .setApiIp("127.0.0.1")
                        .setApiPort(4067)
                        .addPool(
                                new Pool.Builder()
                                        .setName("x16s.na.mine.zpool.ca:3663")
                                        .setPriority(0)
                                        .setStatus(true, true)
                                        .setCounts(126, 0, 0)
                                        .build())
                        .addRig(
                                new Rig.Builder()
                                        .setHashRate(new BigDecimal(46155255))
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
                                                        .setBus(0)
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
                                        .build())
                        .build());
    }
}