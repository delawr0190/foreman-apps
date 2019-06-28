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
public class IximinerGpuITest
        extends AbstractApiITest {

    /** Constructor. */
    public IximinerGpuITest() {
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
                                                "    \"block_height\": 522514,\n" +
                                                "    \"time_running\": 29231,\n" +
                                                "    \"total_blocks\": 159,\n" +
                                                "    \"shares\": 23938,\n" +
                                                "    \"rejects\": 6884,\n" +
                                                "    \"earned\": 0,\n" +
                                                "    \"hashers\": [\n" +
                                                "      {\n" +
                                                "        \"type\": \"GPU\",\n" +
                                                "        \"subtype\": \"OPENCL\",\n" +
                                                "        \"devices\": [\n" +
                                                "          {\n" +
                                                "            \"id\": 0,\n" +
                                                "            \"bus_id\": \"13:00.0\",\n" +
                                                "            \"name\": \"Advanced Micro Devices, Inc. - Radeon RX Vega (8GB)\",\n" +
                                                "            \"intensity\": 88,\n" +
                                                "            \"hashrate\": 115418\n" +
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
                                        .setCounts(23938, 6884, 0)
                                        .build())
                        .addRig(
                                new Rig.Builder()
                                        .setHashRate(new BigDecimal("115418"))
                                        .addGpu(
                                                new Gpu.Builder()
                                                        .setIndex(0)
                                                        .setBus(13)
                                                        .setName("Advanced Micro Devices, Inc. - Radeon RX Vega (8GB)")
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