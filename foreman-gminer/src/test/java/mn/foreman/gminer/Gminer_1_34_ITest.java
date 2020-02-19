package mn.foreman.gminer;

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

/** Runs an integration tests using {@link Gminer} against a fake API. */
public class Gminer_1_34_ITest
        extends AbstractApiITest {

    /** Constructor. */
    public Gminer_1_34_ITest() {
        super(
                new GminerFactory().create(
                        ImmutableMap.of(
                                "apiIp",
                                "127.0.0.1",
                                "apiPort",
                                "10555")),
                new FakeHttpMinerServer(
                        10555,
                        ImmutableMap.of(
                                "/stat",
                                new HttpHandler(
                                        "",
                                        "{\n" +
                                                "  \"miner\": \"GMiner 1.34\",\n" +
                                                "  \"uptime\": 88,\n" +
                                                "  \"server\": \"stratum+ssl:\\/\\/beam.sparkpool.com:2222\",\n" +
                                                "  \"user\": \"100fd5cb7f85d33f74ef78f89e78cd0d5a63fc4965476eea4f77f024a57d99fa55.rig0\",\n" +
                                                "  \"algorithm\": \"Equihash 150,5 \\\"Beam-PoW\\\"\",\n" +
                                                "  \"electricity\": 0.001,\n" +
                                                "  \"total_accepted_shares\": 0,\n" +
                                                "  \"total_rejected_shares\": 0,\n" +
                                                "  \"devices\": [\n" +
                                                "    {\n" +
                                                "      \"gpu_id\": 0,\n" +
                                                "      \"bus_id\": \"0000:02:00.0\",\n" +
                                                "      \"name\": \"ASUS Radeon RX 560 Series 4GB\",\n" +
                                                "      \"speed\": 1.2,\n" +
                                                "      \"accepted_shares\": 0,\n" +
                                                "      \"rejected_shares\": 0,\n" +
                                                "      \"temperature\": 54,\n" +
                                                "      \"temperature_limit\": 90,\n" +
                                                "      \"power_usage\": 32\n" +
                                                "    }\n" +
                                                "  ],\n" +
                                                "  \"speedRatePrecision\": 1,\n" +
                                                "  \"speedUnit\": \"Sol\\/s\",\n" +
                                                "  \"powerUnit\": \"Sol\\/W\"\n" +
                                                "}"))),
                new MinerStats.Builder()
                        .setApiIp("127.0.0.1")
                        .setApiPort(10555)
                        .addPool(
                                new Pool.Builder()
                                        .setName("beam.sparkpool.com:2222")
                                        .setPriority(0)
                                        .setStatus(true, true)
                                        .setCounts(0, 0, 0)
                                        .build())
                        .addRig(
                                new Rig.Builder()
                                        .setHashRate(new BigDecimal("1.2"))
                                        .addGpu(
                                                new Gpu.Builder()
                                                        .setIndex(0)
                                                        .setBus(2)
                                                        .setName("ASUS Radeon RX 560 Series 4GB")
                                                        .setTemp(54)
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
                                                "gpu_algo",
                                                "Equihash 150,5 \"Beam-PoW\"")
                                        .build())
                        .build());
    }
}