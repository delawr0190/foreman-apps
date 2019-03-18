package mn.foreman.miniz;

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

/** Runs an integration tests using {@link Miniz} against a fake API. */
public class MiniZITest
        extends AbstractApiITest {

    /** Constructor. */
    public MiniZITest() {
        super(
                new MinizFactory().create(
                        ImmutableMap.of(
                                "apiIp",
                                "127.0.0.1",
                                "apiPort",
                                "20000")),
                new FakeRpcMinerServer(
                        20000,
                        ImmutableMap.of(
                                "{\"id\":\"0\", \"method\":\"getstat\"}",
                                new RpcHandler(
                                        "{\n" +
                                                "  \"id\": 0,\n" +
                                                "  \"method\": \"getstat\",\n" +
                                                "  \"error\": null,\n" +
                                                "  \"start_time\": 1552868166,\n" +
                                                "  \"current_server\": \"us-btg.2miners.com:4040\",\n" +
                                                "  \"server_latency\": 24.1,\n" +
                                                "  \"available_servers\": 1,\n" +
                                                "  \"server_status\": 1,\n" +
                                                "  \"result\": [\n" +
                                                "    {\n" +
                                                "      \"gpuid\": 0,\n" +
                                                "      \"cudaid\": 0,\n" +
                                                "      \"busid\": \"busid\",\n" +
                                                "      \"name\": \"GeForce GTX 1050 Ti\",\n" +
                                                "      \"gpu_status\": 2,\n" +
                                                "      \"solver\": -1,\n" +
                                                "      \"temperature\": 68,\n" +
                                                "      \"gpu_fan_speed\": 0,\n" +
                                                "      \"gpu_power_usage\": 0,\n" +
                                                "      \"gpu_clock_core_max\": 1746,\n" +
                                                "      \"gpu_clock_memory\": 3504,\n" +
                                                "      \"speed_sps\": 20.3,\n" +
                                                "      \"accepted_shares\": 3,\n" +
                                                "      \"rejected_shares\": 0,\n" +
                                                "      \"start_time\": 1552868166\n" +
                                                "    }\n" +
                                                "  ]\n" +
                                                "}"))),
                new MinerStats.Builder()
                        .setApiIp("127.0.0.1")
                        .setApiPort(20000)
                        .addPool(
                                new Pool.Builder()
                                        .setName("us-btg.2miners.com:4040")
                                        .setPriority(0)
                                        .setStatus(true, true)
                                        .setCounts(3, 0, 0)
                                        .build())
                        .addRig(
                                new Rig.Builder()
                                        .setHashRate(new BigDecimal("20.300000000000000710542735760100185871124267578125"))
                                        .addGpu(
                                                new Gpu.Builder()
                                                        .setName("GeForce GTX 1050 Ti")
                                                        .setIndex(0)
                                                        .setBus(0)
                                                        .setTemp(68)
                                                        .setFans(
                                                                new FanInfo.Builder()
                                                                        .setCount(1)
                                                                        .addSpeed(0)
                                                                        .setSpeedUnits("%")
                                                                        .build())
                                                        .setFreqInfo(
                                                                new FreqInfo.Builder()
                                                                        .setFreq(1746)
                                                                        .setMemFreq(3504)
                                                                        .build())
                                                        .build())
                                        .build())
                        .build());
    }
}