package mn.foreman.dstm;

import mn.foreman.model.miners.FanInfo;
import mn.foreman.model.miners.MinerStats;
import mn.foreman.model.miners.Pool;
import mn.foreman.model.miners.rig.FreqInfo;
import mn.foreman.model.miners.rig.Gpu;
import mn.foreman.model.miners.rig.Rig;
import mn.foreman.util.AbstractApiITest;
import mn.foreman.util.rpc.FakeRpcMinerServer;
import mn.foreman.util.rpc.RpcHandler;

import com.google.common.collect.ImmutableMap;

import java.math.BigDecimal;

/** Runs an integration tests using {@link Dstm} against a fake API. */
public class DstmITest
        extends AbstractApiITest {

    /** Constructor. */
    public DstmITest() {
        super(
                new Dstm(
                        "dstm",
                        "127.0.0.1",
                        2222),
                new FakeRpcMinerServer(
                        2222,
                        ImmutableMap.of(
                                "{\"id\":1,\"method\":\"getstat\"}",
                                new RpcHandler(
                                        "{\n" +
                                                "    \"id\": 1,\n" +
                                                "    \"result\": [\n" +
                                                "      {\n" +
                                                "        \"gpu_id\":            0,\n" +
                                                "        \"gpu_name\":          \"My GPU 1\",\n" +
                                                "        \"gpu_pci_bus_id\":    0,\n" +
                                                "        \"gpu_pci_device_id\": 1,\n" +
                                                "        \"gpu_uuid\":          \"string\",\n" +
                                                "        \"temperature\":       50,\n" +
                                                "        \"fan_speed\":         51,\n" +
                                                "        \"sol_ps\":            123.45,\n" +
                                                "        \"avg_sol_ps\":        234.56,\n" +
                                                "        \"sol_pw\":            345.67,\n" +
                                                "        \"avg_sol_pw\":        456.78,\n" +
                                                "        \"power_usage\":       567.89,\n" +
                                                "        \"avg_power_usage\":   678.90,\n" +
                                                "        \"accepted_shares\":   100,\n" +
                                                "        \"rejected_shares\":   50,\n" +
                                                "        \"latency\":           100\n" +
                                                "      },\n" +
                                                "      {\n" +
                                                "        \"gpu_id\":            1,\n" +
                                                "        \"gpu_name\":          \"My GPU 2\",\n" +
                                                "        \"gpu_pci_bus_id\":    2,\n" +
                                                "        \"gpu_pci_device_id\": 3,\n" +
                                                "        \"gpu_uuid\":          \"string\",\n" +
                                                "        \"temperature\":       51,\n" +
                                                "        \"fan_speed\":         52,\n" +
                                                "        \"sol_ps\":            1123.45,\n" +
                                                "        \"avg_sol_ps\":        1234.56,\n" +
                                                "        \"sol_pw\":            1345.67,\n" +
                                                "        \"avg_sol_pw\":        1456.78,\n" +
                                                "        \"power_usage\":       1567.89,\n" +
                                                "        \"avg_power_usage\":   1678.90,\n" +
                                                "        \"accepted_shares\":   1100,\n" +
                                                "        \"rejected_shares\":   510,\n" +
                                                "        \"latency\":           200\n" +
                                                "      }\n" +
                                                "    ],\n" +
                                                "    \"uptime\":   300,\n" +
                                                "    \"contime\":  400,\n" +
                                                "    \"server\":   \"myserver.somewhere.mn:7777\",\n" +
                                                "    \"port\":     2345,\n" +
                                                "    \"user\":     \"my_user\",\n" +
                                                "    \"version\":  \"some_version\",\n" +
                                                "    \"error\":    null\n" +
                                                "}"))),
                new MinerStats.Builder()
                        .setName("dstm")
                        .setApiIp("127.0.0.1")
                        .setApiPort(2222)
                        .addPool(
                                new Pool.Builder()
                                        .setName("myserver.somewhere.mn:7777")
                                        .setPriority(0)
                                        .setStatus(true, true)
                                        .setCounts(1200, 560, 0)
                                        .build())
                        .addRig(
                                new Rig.Builder()
                                        .setName("dstm")
                                        .setHashRate(new BigDecimal("1246.90000000000009094947017729282379150390625"))
                                        .addGpu(
                                                new Gpu.Builder()
                                                        .setBus(0)
                                                        .setFans(
                                                                new FanInfo.Builder()
                                                                        .setCount(1)
                                                                        .addSpeed(51)
                                                                        .setSpeedUnits("%")
                                                                        .build())
                                                        .setFreqInfo(
                                                                new FreqInfo.Builder()
                                                                        .setFreq(0)
                                                                        .setMemFreq(0)
                                                                        .build())
                                                        .setIndex(1)
                                                        .setName("My GPU 1")
                                                        .setTemp(50)
                                                        .build())
                                        .addGpu(
                                                new Gpu.Builder()
                                                        .setBus(2)
                                                        .setFans(
                                                                new FanInfo.Builder()
                                                                        .setCount(1)
                                                                        .addSpeed(52)
                                                                        .setSpeedUnits("%")
                                                                        .build())
                                                        .setFreqInfo(
                                                                new FreqInfo.Builder()
                                                                        .setFreq(0)
                                                                        .setMemFreq(0)
                                                                        .build())
                                                        .setIndex(3)
                                                        .setName("My GPU 2")
                                                        .setTemp(51)
                                                        .build())
                                        .build())
                        .build());
    }
}