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
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;

/** Runs an integration tests using {@link Lolminer1_0ITest} against a fake API. */
@RunWith(Parameterized.class)
public class Lolminer1_0ITest
        extends AbstractApiITest {

    /** Constructor. */
    public Lolminer1_0ITest(
            final String summaryResponse,
            final MinerStats expectedStats) {
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
                                        summaryResponse))),
                expectedStats);
    }

    /**
     * Test parameters
     *
     * @return The test parameters.
     */
    @Parameterized.Parameters
    public static Collection<Object[]> parameters() {
        return Arrays.asList(
                new Object[][]{
                        {
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
                                        "}",
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
                                        .build()
                        },
                        {
                                "{\n" +
                                        "    \"Software\": \"lolMiner 1.16a\",\n" +
                                        "    \"Mining\": {\n" +
                                        "        \"Algorithm\": \"Etchash\"\n" +
                                        "    },\n" +
                                        "    \"Stratum\": {\n" +
                                        "        \"Current_Pool\": \"hk.etc.herominers.com:10160\",\n" +
                                        "        \"Current_User\": \"0x155da78b788ab54bea1340c10a5422a8ae88142f.lolMinerWorker\",\n" +
                                        "        \"Average_Latency\": 0.0\n" +
                                        "    },\n" +
                                        "    \"Session\": {\n" +
                                        "        \"Startup\": 1606949275,\n" +
                                        "        \"Startup_String\": \"2020-12-02_17-47-55\",\n" +
                                        "        \"Uptime\": 10,\n" +
                                        "        \"Last_Update\": 1606949285,\n" +
                                        "        \"Active_GPUs\": 4,\n" +
                                        "        \"Performance_Summary\": 6.08,\n" +
                                        "        \"Performance_Unit\": \"mh\\/s\",\n" +
                                        "        \"Accepted\": 0,\n" +
                                        "        \"Submitted\": 0,\n" +
                                        "        \"TotalPower\": 0\n" +
                                        "    },\n" +
                                        "    \"GPUs\": [\n" +
                                        "        {\n" +
                                        "            \"Index\": 0,\n" +
                                        "            \"Name\": \"AMD Radeon VII\",\n" +
                                        "            \"Performance\": 0.00,\n" +
                                        "            \"Consumption (W)\": 0,\n" +
                                        "            \"Fan Speed (%)\": 0,\n" +
                                        "            \"Temp (deg C)\": 0,\n" +
                                        "            \"Mem Temp (deg C)\": 0,\n" +
                                        "            \"Session_Accepted\": 0,\n" +
                                        "            \"Session_Submitted\": 0,\n" +
                                        "            \"Session_HWErr\": 0,\n" +
                                        "            \"Session_BestShare\": 0,\n" +
                                        "            \"PCIE_Address\": \"3:0\"\n" +
                                        "        },\n" +
                                        "        {\n" +
                                        "            \"Index\": 1,\n" +
                                        "            \"Name\": \"AMD Radeon VII\",\n" +
                                        "            \"Performance\": 3.87,\n" +
                                        "            \"Consumption (W)\": 0,\n" +
                                        "            \"Fan Speed (%)\": 0,\n" +
                                        "            \"Temp (deg C)\": 0,\n" +
                                        "            \"Mem Temp (deg C)\": 0,\n" +
                                        "            \"Session_Accepted\": 0,\n" +
                                        "            \"Session_Submitted\": 0,\n" +
                                        "            \"Session_HWErr\": 0,\n" +
                                        "            \"Session_BestShare\": 0,\n" +
                                        "            \"PCIE_Address\": \"16:0\"\n" +
                                        "        },\n" +
                                        "        {\n" +
                                        "            \"Index\": 2,\n" +
                                        "            \"Name\": \"AMD Radeon VII\",\n" +
                                        "            \"Performance\": 0.00,\n" +
                                        "            \"Consumption (W)\": 0,\n" +
                                        "            \"Fan Speed (%)\": 0,\n" +
                                        "            \"Temp (deg C)\": 0,\n" +
                                        "            \"Mem Temp (deg C)\": 0,\n" +
                                        "            \"Session_Accepted\": 0,\n" +
                                        "            \"Session_Submitted\": 0,\n" +
                                        "            \"Session_HWErr\": 0,\n" +
                                        "            \"Session_BestShare\": 0,\n" +
                                        "            \"PCIE_Address\": \"19:0\"\n" +
                                        "        },\n" +
                                        "        {\n" +
                                        "            \"Index\": 3,\n" +
                                        "            \"Name\": \"AMD Radeon VII\",\n" +
                                        "            \"Performance\": 2.21,\n" +
                                        "            \"Consumption (W)\": 0,\n" +
                                        "            \"Fan Speed (%)\": 0,\n" +
                                        "            \"Temp (deg C)\": 0,\n" +
                                        "            \"Mem Temp (deg C)\": 0,\n" +
                                        "            \"Session_Accepted\": 0,\n" +
                                        "            \"Session_Submitted\": 0,\n" +
                                        "            \"Session_HWErr\": 0,\n" +
                                        "            \"Session_BestShare\": 0,\n" +
                                        "            \"PCIE_Address\": \"22:0\"\n" +
                                        "        }\n" +
                                        "    ]\n" +
                                        "}",
                                new MinerStats.Builder()
                                        .setApiIp("127.0.0.1")
                                        .setApiPort(8080)
                                        .addPool(
                                                new Pool.Builder()
                                                        .setName("hk.etc.herominers.com:10160")
                                                        .setStatus(true, true)
                                                        .setPriority(0)
                                                        .setCounts(0, 0, 0)
                                                        .build())
                                        .addRig(
                                                new Rig.Builder()
                                                        .setHashRate(new BigDecimal("6080000.00"))
                                                        .addGpu(
                                                                new Gpu.Builder()
                                                                        .setName("AMD Radeon VII")
                                                                        .setIndex(0)
                                                                        .setBus(3)
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
                                                                        .setName("AMD Radeon VII")
                                                                        .setIndex(1)
                                                                        .setBus(22)
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
                                                                        .setName("AMD Radeon VII")
                                                                        .setIndex(2)
                                                                        .setBus(25)
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
                                                                        .setName("AMD Radeon VII")
                                                                        .setIndex(3)
                                                                        .setBus(34)
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
                                                                "Etchash")
                                                        .build())
                                        .build()
                        },
                        {
                                "{\n" +
                                        "    \"Software\": \"lolMiner 1.16a\",\n" +
                                        "    \"Mining\": {\n" +
                                        "        \"Algorithm\": \"Ethash\"\n" +
                                        "    },\n" +
                                        "    \"Stratum\": {\n" +
                                        "        \"Current_Pool\": \"eth.2miners.com:2020\",\n" +
                                        "        \"Current_User\": \"0x155da78b788ab54bea1340c10a5422a8ae88142f.lolMinerWorker\",\n" +
                                        "        \"Average_Latency\": 0.0\n" +
                                        "    },\n" +
                                        "    \"Session\": {\n" +
                                        "        \"Startup\": 1606949972,\n" +
                                        "        \"Startup_String\": \"2020-12-02_17-59-32\",\n" +
                                        "        \"Uptime\": 90,\n" +
                                        "        \"Last_Update\": 1606950062,\n" +
                                        "        \"Active_GPUs\": 4,\n" +
                                        "        \"Performance_Summary\": 302.40,\n" +
                                        "        \"Performance_Unit\": \"mh\\/s\",\n" +
                                        "        \"Accepted\": 3,\n" +
                                        "        \"Submitted\": 3,\n" +
                                        "        \"TotalPower\": 659.45099999999991\n" +
                                        "    },\n" +
                                        "    \"GPUs\": [\n" +
                                        "        {\n" +
                                        "            \"Index\": 0,\n" +
                                        "            \"Name\": \"AMD Radeon VII\",\n" +
                                        "            \"Performance\": 75.86,\n" +
                                        "            \"Consumption (W)\": 160.631,\n" +
                                        "            \"Fan Speed (%)\": 0,\n" +
                                        "            \"Temp (deg C)\": 47,\n" +
                                        "            \"Mem Temp (deg C)\": 58,\n" +
                                        "            \"Session_Accepted\": 0,\n" +
                                        "            \"Session_Submitted\": 0,\n" +
                                        "            \"Session_HWErr\": 0,\n" +
                                        "            \"Session_BestShare\": 0,\n" +
                                        "            \"PCIE_Address\": \"3:0\"\n" +
                                        "        },\n" +
                                        "        {\n" +
                                        "            \"Index\": 1,\n" +
                                        "            \"Name\": \"AMD Radeon VII\",\n" +
                                        "            \"Performance\": 74.51,\n" +
                                        "            \"Consumption (W)\": 163.95599999999999,\n" +
                                        "            \"Fan Speed (%)\": 0,\n" +
                                        "            \"Temp (deg C)\": 65,\n" +
                                        "            \"Mem Temp (deg C)\": 76,\n" +
                                        "            \"Session_Accepted\": 0,\n" +
                                        "            \"Session_Submitted\": 0,\n" +
                                        "            \"Session_HWErr\": 0,\n" +
                                        "            \"Session_BestShare\": 0,\n" +
                                        "            \"PCIE_Address\": \"16:0\"\n" +
                                        "        },\n" +
                                        "        {\n" +
                                        "            \"Index\": 2,\n" +
                                        "            \"Name\": \"AMD Radeon VII\",\n" +
                                        "            \"Performance\": 75.34,\n" +
                                        "            \"Consumption (W)\": 158.39599999999999,\n" +
                                        "            \"Fan Speed (%)\": 0,\n" +
                                        "            \"Temp (deg C)\": 50,\n" +
                                        "            \"Mem Temp (deg C)\": 64,\n" +
                                        "            \"Session_Accepted\": 1,\n" +
                                        "            \"Session_Submitted\": 1,\n" +
                                        "            \"Session_HWErr\": 0,\n" +
                                        "            \"Session_BestShare\": 29171005585,\n" +
                                        "            \"PCIE_Address\": \"19:0\"\n" +
                                        "        },\n" +
                                        "        {\n" +
                                        "            \"Index\": 3,\n" +
                                        "            \"Name\": \"AMD Radeon VII\",\n" +
                                        "            \"Performance\": 76.69,\n" +
                                        "            \"Consumption (W)\": 176.46799999999999,\n" +
                                        "            \"Fan Speed (%)\": 0,\n" +
                                        "            \"Temp (deg C)\": 54,\n" +
                                        "            \"Mem Temp (deg C)\": 66,\n" +
                                        "            \"Session_Accepted\": 2,\n" +
                                        "            \"Session_Submitted\": 2,\n" +
                                        "            \"Session_HWErr\": 0,\n" +
                                        "            \"Session_BestShare\": 14040464008,\n" +
                                        "            \"PCIE_Address\": \"22:0\"\n" +
                                        "        }\n" +
                                        "    ]\n" +
                                        "}",
                                new MinerStats.Builder()
                                        .setApiIp("127.0.0.1")
                                        .setApiPort(8080)
                                        .addPool(
                                                new Pool.Builder()
                                                        .setName("eth.2miners.com:2020")
                                                        .setStatus(true, true)
                                                        .setPriority(0)
                                                        .setCounts(3, 0, 0)
                                                        .build())
                                        .addRig(
                                                new Rig.Builder()
                                                        .setHashRate(new BigDecimal("302400000.00"))
                                                        .addGpu(
                                                                new Gpu.Builder()
                                                                        .setName("AMD Radeon VII")
                                                                        .setIndex(0)
                                                                        .setBus(3)
                                                                        .setTemp(47)
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
                                                                        .setName("AMD Radeon VII")
                                                                        .setIndex(1)
                                                                        .setBus(22)
                                                                        .setTemp(65)
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
                                                                        .setName("AMD Radeon VII")
                                                                        .setIndex(2)
                                                                        .setBus(25)
                                                                        .setTemp(50)
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
                                                                        .setName("AMD Radeon VII")
                                                                        .setIndex(3)
                                                                        .setBus(34)
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
                                                                "Ethash")
                                                        .build())
                                        .build()
                        }
                });
    }
}