package mn.foreman.iximiner;

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

/** Runs an integration tests using {@link Iximiner} against a fake API. */
public class IximinerGpuHexBusITest
        extends AbstractApiITest {

    /** Constructor. */
    public IximinerGpuHexBusITest() {
        super(
                new IximinerFactory().create(
                        ImmutableMap.of(
                                "apiIp",
                                "127.0.0.1",
                                "apiPort",
                                "4068")),
                new FakeHttpMinerServer(
                        4068,
                        ImmutableMap.of(
                                "/status",
                                new HttpHandler(
                                        "",
                                        "[\n" +
                                                "  {\n" +
                                                "    \"name\": \"miner\",\n" +
                                                "    \"block_height\": 571395,\n" +
                                                "    \"time_running\": 3855,\n" +
                                                "    \"total_blocks\": 34,\n" +
                                                "    \"shares\": 3287,\n" +
                                                "    \"rejects\": 632,\n" +
                                                "    \"earned\": 0,\n" +
                                                "    \"hashers\": [\n" +
                                                "      {\n" +
                                                "        \"type\": \"GPU\",\n" +
                                                "        \"subtype\": \"OPENCL\",\n" +
                                                "        \"devices\": [\n" +
                                                "          {\n" +
                                                "            \"id\": 1,\n" +
                                                "            \"bus_id\": \"0d:00.0\",\n" +
                                                "            \"name\": \"Advanced Micro Devices, Inc. - Radeon RX Vega (8GB)\",\n" +
                                                "            \"intensity\": 89,\n" +
                                                "            \"hashrate\": 106823\n" +
                                                "          }\n" +
                                                "        ]\n" +
                                                "      }\n" +
                                                "    ]\n" +
                                                "  }\n" +
                                                "]"))),
                new MinerStats.Builder()
                        .setApiIp("127.0.0.1")
                        .setApiPort(4068)
                        .addPool(
                                new Pool.Builder()
                                        .setName("not available")
                                        .setPriority(0)
                                        .setStatus(true, true)
                                        .setCounts(3287, 632, 0)
                                        .build())
                        .addRig(
                                new Rig.Builder()
                                        .setHashRate(new BigDecimal("106823"))
                                        .addGpu(
                                                new Gpu.Builder()
                                                        .setIndex(1)
                                                        .setBus(13)
                                                        .setName("AMD - Radeon RX Vega (8GB)")
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