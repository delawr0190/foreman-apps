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

/** Runs an integration tests using {@link Lolminer1_0ITest} against a fake API. */
public class Lolminer1_0ITest
        extends AbstractApiITest {

    /** Constructor. */
    public Lolminer1_0ITest() {
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
                                                "    \"Software\": \"lolMiner 1.0 a2\",\n" +
                                                "    \"Mining\": {\n" +
                                                "        \"Coin\": \"ZEL\",\n" +
                                                "        \"Algorithm\": \"ZelHash\"\n" +
                                                "    },\n" +
                                                "    \"Stratum\": {\n" +
                                                "        \"Current_Pool\": \"stratum.zel.cash:2001\",\n" +
                                                "        \"Current_User\": \"t1cLkQpx3kEJw2TjPnTmCpe4KqLvER8NknA.testWorker\",\n" +
                                                "        \"Average_Latency\": 0.0\n" +
                                                "    },\n" +
                                                "    \"Session\": {\n" +
                                                "        \"Startup\": 1591527060,\n" +
                                                "        \"Startup_String\": \"2020-06-07_06-51-00\",\n" +
                                                "        \"Uptime\": 10,\n" +
                                                "        \"Last_Update\": 1591527070,\n" +
                                                "        \"Active_GPUs\": 1,\n" +
                                                "        \"Performance_Summary\": 6.70,\n" +
                                                "        \"Performance_Unit\": \"sol\\/s\",\n" +
                                                "        \"Accepted\": 0,\n" +
                                                "        \"Submitted\": 0,\n" +
                                                "        \"TotalPower\": 0\n" +
                                                "    },\n" +
                                                "    \"GPUs\": [\n" +
                                                "        {\n" +
                                                "            \"Index\": 0,\n" +
                                                "            \"Name\": \"GeForce GTX 1050 Ti\",\n" +
                                                "            \"Performance\": 6.70,\n" +
                                                "            \"Consumption (W)\": 0,\n" +
                                                "            \"Fan Speed (%)\": 75,\n" +
                                                "            \"Temps (deg C)\": 49,\n" +
                                                "            \"Session_Accepted\": 0,\n" +
                                                "            \"Session_Submitted\": 0,\n" +
                                                "            \"PCIE_Address\": \"1:0\"\n" +
                                                "        }\n" +
                                                "    ]\n" +
                                                "}"))),
                new MinerStats.Builder()
                        .setApiIp("127.0.0.1")
                        .setApiPort(8080)
                        .addPool(
                                new Pool.Builder()
                                        .setName("stratum.zel.cash:2001")
                                        .setStatus(true, true)
                                        .setPriority(0)
                                        .setCounts(0, 0, 0)
                                        .build())
                        .addRig(
                                new Rig.Builder()
                                        .setHashRate(new BigDecimal("6.70"))
                                        .addGpu(
                                                new Gpu.Builder()
                                                        .setName("GeForce GTX 1050 Ti")
                                                        .setIndex(0)
                                                        .setBus(1)
                                                        .setTemp(49)
                                                        .setFans(
                                                                new FanInfo.Builder()
                                                                        .setCount(1)
                                                                        .addSpeed(75)
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
                                                "ZelHash")
                                        .addAttribute(
                                                "coin",
                                                "ZEL")
                                        .build())
                        .build());
    }
}