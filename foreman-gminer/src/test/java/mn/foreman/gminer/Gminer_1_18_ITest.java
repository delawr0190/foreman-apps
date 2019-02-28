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
public class Gminer1_18ITest
        extends AbstractApiITest {

    /** Constructor. */
    public Gminer1_18ITest() {
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
                                                "  \"uptime\": 94,\n" +
                                                "  \"server\": \"stratum://aion-eu.luxor.tech:3366\",\n" +
                                                "  \"user\": \"0xa07978089c76e2010720bec8400f05530cd946a567dc0fd9b862d83a45681f7c\",\n" +
                                                "  \"algorithm\": \"Equihash 210,9 \\\"AION0PoW\\\"\",\n" +
                                                "  \"electricity\": 0,\n" +
                                                "  \"devices\": [\n" +
                                                "    {\n" +
                                                "      \"gpu_id\": 0,\n" +
                                                "      \"cuda_id\": 0,\n" +
                                                "      \"bus_id\": \"0000:01:00.0\",\n" +
                                                "      \"name\": \"GeForce GTX 1050 Ti 4GB\",\n" +
                                                "      \"speed\": 68,\n" +
                                                "      \"accepted_shares\": 1,\n" +
                                                "      \"rejected_shares\": 0,\n" +
                                                "      \"temperature\": 55,\n" +
                                                "      \"temperature_limit\": 90,\n" +
                                                "      \"power_usage\": 0\n" +
                                                "    }\n" +
                                                "  ]\n" +
                                                "}"))),
                new MinerStats.Builder()
                        .setApiIp("127.0.0.1")
                        .setApiPort(10555)
                        .addPool(
                                new Pool.Builder()
                                        .setName("aion-eu.luxor.tech:3366")
                                        .setPriority(0)
                                        .setStatus(true, true)
                                        .setCounts(1, 0, 0)
                                        .build())
                        .addRig(
                                new Rig.Builder()
                                        .setHashRate(new BigDecimal(68))
                                        .addGpu(
                                                new Gpu.Builder()
                                                        .setIndex(0)
                                                        .setBus(1)
                                                        .setName("GeForce GTX 1050 Ti 4GB")
                                                        .setTemp(55)
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