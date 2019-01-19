package mn.foreman.nanominer;

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

/** Runs an integration tests using {@link Nanominer} against a fake API. */
public class NanominerITest
        extends AbstractApiITest {

    /** Constructor. */
    public NanominerITest() {
        super(
                new NanominerFactory().create(
                        ImmutableMap.of(
                                "apiIp",
                                "127.0.0.1",
                                "apiPort",
                                "9090")),
                new FakeHttpMinerServer(
                        9090,
                        ImmutableMap.of(
                                "/stats",
                                new HttpHandler(
                                        "",
                                        "{\n" +
                                                "  \"Algorithms\": [\n" +
                                                "    {\n" +
                                                "      \"Ethash\": {\n" +
                                                "        \"CurrentPool\": \"eth-us-east1.nanopool.org:9999\",\n" +
                                                "        \"GPU 0\": {\n" +
                                                "          \"Accepted\": 3,\n" +
                                                "          \"Denied\": 0,\n" +
                                                "          \"Hashrate\": \"4.591157e+06\"\n" +
                                                "        },\n" +
                                                "        \"ReconnectionCount\": 0,\n" +
                                                "        \"Total\": {\n" +
                                                "          \"Accepted\": 3,\n" +
                                                "          \"Denied\": 0,\n" +
                                                "          \"Hashrate\": \"4.591157e+06\"\n" +
                                                "        }\n" +
                                                "      }\n" +
                                                "    }\n" +
                                                "  ],\n" +
                                                "  \"Devices\": [\n" +
                                                "    {\n" +
                                                "      \"GPU 0\": {\n" +
                                                "        \"Name\": \"GeForce GTX 1050 Ti\",\n" +
                                                "        \"Platform\": \"CUDA\",\n" +
                                                "        \"Pci\": \"01:00.0\",\n" +
                                                "        \"Temperature\": 46\n" +
                                                "      }\n" +
                                                "    }\n" +
                                                "  ],\n" +
                                                "  \"WorkTime\": 898\n" +
                                                "}"))),
                new MinerStats.Builder()
                        .setApiIp("127.0.0.1")
                        .setApiPort(9090)
                        .addPool(
                                new Pool.Builder()
                                        .setName("eth-us-east1.nanopool.org:9999")
                                        .setPriority(0)
                                        .setStatus(true, true)
                                        .setCounts(3, 0, 0)
                                        .build())
                        .addRig(
                                new Rig.Builder()
                                        .setHashRate(new BigDecimal(4591157))
                                        .addGpu(
                                                new Gpu.Builder()
                                                        .setIndex(0)
                                                        .setBus(1)
                                                        .setName("GeForce GTX 1050 Ti")
                                                        .setTemp(46)
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