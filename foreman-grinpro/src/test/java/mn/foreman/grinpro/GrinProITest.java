package mn.foreman.grinpro;

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

/** Runs an integration tests using {@link GrinPro} against a fake API. */
public class GrinProITest
        extends AbstractApiITest {

    /** Constructor. */
    public GrinProITest() {
        super(
                new GrinProFactory().create(
                        ImmutableMap.of(
                                "apiIp",
                                "127.0.0.1",
                                "apiPort",
                                "5777")),
                new FakeHttpMinerServer(
                        5777,
                        ImmutableMap.of(
                                "/api/status",
                                new HttpHandler(
                                        "",
                                        "{\n" +
                                                "  \"connectionAddress\": \"us-east-stratum.grinmint.com:4416\",\n" +
                                                "  \"connectionStatus\": \"Connected\",\n" +
                                                "  \"lastJob\": \"2019-02-24T09:54:18-05:00\",\n" +
                                                "  \"lastShare\": \"2019-02-24T09:54:08-05:00\",\n" +
                                                "  \"shares\": {\n" +
                                                "    \"found\": 30,\n" +
                                                "    \"submitted\": 5,\n" +
                                                "    \"accepted\": 5,\n" +
                                                "    \"tooLate\": 0,\n" +
                                                "    \"failedToValidate\": 0\n" +
                                                "  },\n" +
                                                "  \"workers\": [\n" +
                                                "    {\n" +
                                                "      \"id\": 0,\n" +
                                                "      \"platform\": \"0\",\n" +
                                                "      \"gpuName\": \"GeForce GTX 1070 Ti\",\n" +
                                                "      \"status\": \"ONLINE\",\n" +
                                                "      \"graphsPerSecond\": 4.190313,\n" +
                                                "      \"totalSols\": 3,\n" +
                                                "      \"fidelity\": 0.65968585,\n" +
                                                "      \"lastSolution\": \"2019-02-24T09:54:16-05:00\"\n" +
                                                "    },\n" +
                                                "    {\n" +
                                                "      \"id\": 1,\n" +
                                                "      \"platform\": \"0\",\n" +
                                                "      \"gpuName\": \"GeForce GTX 1070 Ti\",\n" +
                                                "      \"status\": \"ONLINE\",\n" +
                                                "      \"graphsPerSecond\": 4.174586,\n" +
                                                "      \"totalSols\": 3,\n" +
                                                "      \"fidelity\": 0.875,\n" +
                                                "      \"lastSolution\": \"2019-02-24T09:54:04-05:00\"\n" +
                                                "    },\n" +
                                                "    {\n" +
                                                "      \"id\": 2,\n" +
                                                "      \"platform\": \"0\",\n" +
                                                "      \"gpuName\": \"GeForce GTX 1070 Ti\",\n" +
                                                "      \"status\": \"ONLINE\",\n" +
                                                "      \"graphsPerSecond\": 4.248762,\n" +
                                                "      \"totalSols\": 4,\n" +
                                                "      \"fidelity\": 1.2352941,\n" +
                                                "      \"lastSolution\": \"2019-02-24T09:54:02-05:00\"\n" +
                                                "    },\n" +
                                                "    {\n" +
                                                "      \"id\": 3,\n" +
                                                "      \"platform\": \"0\",\n" +
                                                "      \"gpuName\": \"GeForce GTX 1070 Ti\",\n" +
                                                "      \"status\": \"ONLINE\",\n" +
                                                "      \"graphsPerSecond\": 4.14898825,\n" +
                                                "      \"totalSols\": 5,\n" +
                                                "      \"fidelity\": 1.05527639,\n" +
                                                "      \"lastSolution\": \"2019-02-24T09:54:18-05:00\"\n" +
                                                "    },\n" +
                                                "    {\n" +
                                                "      \"id\": 4,\n" +
                                                "      \"platform\": \"0\",\n" +
                                                "      \"gpuName\": \"GeForce GTX 1070 Ti\",\n" +
                                                "      \"status\": \"ONLINE\",\n" +
                                                "      \"graphsPerSecond\": 4.15632439,\n" +
                                                "      \"totalSols\": 3,\n" +
                                                "      \"fidelity\": 0.763636351,\n" +
                                                "      \"lastSolution\": \"2019-02-24T09:54:09-05:00\"\n" +
                                                "    },\n" +
                                                "    {\n" +
                                                "      \"id\": 5,\n" +
                                                "      \"platform\": \"0\",\n" +
                                                "      \"gpuName\": \"GeForce GTX 1070 Ti\",\n" +
                                                "      \"status\": \"ONLINE\",\n" +
                                                "      \"graphsPerSecond\": 4.15957069,\n" +
                                                "      \"totalSols\": 5,\n" +
                                                "      \"fidelity\": 1.28834355,\n" +
                                                "      \"lastSolution\": \"2019-02-24T09:54:09-05:00\"\n" +
                                                "    },\n" +
                                                "    {\n" +
                                                "      \"id\": 6,\n" +
                                                "      \"platform\": \"0\",\n" +
                                                "      \"gpuName\": \"GeForce GTX 1070 Ti\",\n" +
                                                "      \"status\": \"ONLINE\",\n" +
                                                "      \"graphsPerSecond\": 4.191264,\n" +
                                                "      \"totalSols\": 4,\n" +
                                                "      \"fidelity\": 0.815534,\n" +
                                                "      \"lastSolution\": \"2019-02-24T09:54:19-05:00\"\n" +
                                                "    },\n" +
                                                "    {\n" +
                                                "      \"id\": 7,\n" +
                                                "      \"platform\": \"0\",\n" +
                                                "      \"gpuName\": \"GeForce GTX 1070 Ti\",\n" +
                                                "      \"status\": \"ONLINE\",\n" +
                                                "      \"graphsPerSecond\": 4.229688,\n" +
                                                "      \"totalSols\": 3,\n" +
                                                "      \"fidelity\": 0.7875,\n" +
                                                "      \"lastSolution\": \"2019-02-24T09:54:08-05:00\"\n" +
                                                "    }\n" +
                                                "  ]\n" +
                                                "}"))),
                new MinerStats.Builder()
                        .setApiIp("127.0.0.1")
                        .setApiPort(5777)
                        .addPool(
                                new Pool.Builder()
                                        .setName("us-east-stratum.grinmint.com:4416")
                                        .setPriority(0)
                                        .setStatus(true, true)
                                        .setCounts(5, 0, 0)
                                        .build())
                        .addRig(
                                new Rig.Builder()
                                        .setHashRate(new BigDecimal("33.49949633"))
                                        .addGpu(
                                                new Gpu.Builder()
                                                        .setIndex(0)
                                                        .setBus(0)
                                                        .setName("GeForce GTX 1070 Ti")
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
                                                        .setName("GeForce GTX 1070 Ti")
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
                                                        .setBus(0)
                                                        .setName("GeForce GTX 1070 Ti")
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
                                                        .setBus(0)
                                                        .setName("GeForce GTX 1070 Ti")
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
                                                        .setBus(0)
                                                        .setName("GeForce GTX 1070 Ti")
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
                                                        .setBus(0)
                                                        .setName("GeForce GTX 1070 Ti")
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
                                                        .setBus(0)
                                                        .setName("GeForce GTX 1070 Ti")
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
                                                        .setBus(0)
                                                        .setName("GeForce GTX 1070 Ti")
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