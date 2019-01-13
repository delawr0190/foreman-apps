package mn.foreman.lolminer;

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

/** Runs an integration tests using {@link Lolminer0_6ITest} against a fake API. */
public class Lolminer0_6ITest
        extends AbstractApiITest {

    /** Constructor. */
    public Lolminer0_6ITest() {
        super(
                new LolminerFactory().create(
                        ImmutableMap.of(
                                "apiIp",
                                "127.0.0.1",
                                "apiPort",
                                "8080")),
                new FakeHttpMinerServer(
                        8080,
                        ImmutableMap.of(
                                "/summary",
                                new HttpHandler(
                                        "",
                                        "{\n" +
                                                "    \"Software\": \"lolMiner 0.6\",\n" +
                                                "    \"Mining\": {\n" +
                                                "        \"Coin\": \"AION\",\n" +
                                                "        \"Algorithm\": \"Equihash 210\\/9\"\n" +
                                                "    },\n" +
                                                "    \"Stratum\": {\n" +
                                                "        \"Current_Pool\": \"na.aionmine.org:3333\",\n" +
                                                "        \"Current_User\": \"0xa0e1ff18f69eac5d17fc8c5ac078739d64cc0a8ae2f84b7ca6d55c33bff8dc52.lolMiner\",\n" +
                                                "        \"Average_Latency\": 0.0\n" +
                                                "    },\n" +
                                                "    \"Session\": {\n" +
                                                "        \"Startup\": 1547410837,\n" +
                                                "        \"Startup_String\": \"2019-01-13_15-20-37\",\n" +
                                                "        \"Uptime\": 140,\n" +
                                                "        \"Last_Update\": 1547410977,\n" +
                                                "        \"Active_GPUs\": 1,\n" +
                                                "        \"Performance_Summary\": 65.1,\n" +
                                                "        \"Accepted\": 4,\n" +
                                                "        \"Submitted\": 4\n" +
                                                "    },\n" +
                                                "    \"GPUs\": [\n" +
                                                "        {\n" +
                                                "            \"Index\": 0,\n" +
                                                "            \"Name\": \"GeForce GTX 1050 Ti\",\n" +
                                                "            \"Performance\": 65.1,\n" +
                                                "            \"Session_Accepted\": 4,\n" +
                                                "            \"Session_Submitted\": 4,\n" +
                                                "            \"PCIE_Address\": \"1:0\"\n" +
                                                "        }\n" +
                                                "    ]\n" +
                                                "}"))),
                new MinerStats.Builder()
                        .setApiIp("127.0.0.1")
                        .setApiPort(8080)
                        .addPool(
                                new Pool.Builder()
                                        .setName("na.aionmine.org:3333")
                                        .setStatus(true, true)
                                        .setPriority(0)
                                        .setCounts(4, 0, 0)
                                        .build())
                        .addRig(
                                new Rig.Builder()
                                        .setHashRate(new BigDecimal("65.1"))
                                        .addGpu(
                                                new Gpu.Builder()
                                                        .setName("GeForce GTX 1050 Ti")
                                                        .setIndex(0)
                                                        .setBus(1)
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