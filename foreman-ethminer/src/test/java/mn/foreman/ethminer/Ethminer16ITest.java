package mn.foreman.ethminer;

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

/** Runs an integration tests using {@link Ethminer} against a fake API. */
public class Ethminer16ITest
        extends AbstractApiITest {

    /** Constructor. */
    public Ethminer16ITest() {
        super(
                new Ethminer(
                        "127.0.0.1",
                        3333,
                        null),
                new FakeRpcMinerServer(
                        3333,
                        ImmutableMap.of(
                                "{\"id\":1,\"jsonrpc\":\"2.0\",\"method\":\"miner_getstat1\"}\n",
                                new RpcHandler("{\n" +
                                        "  \"id\": 1,\n" +
                                        "  \"jsonrpc\": \"2.0\",\n" +
                                        "  \"result\": [\n" +
                                        "    \"ethminer-0.16.0.dev0+commit.41639944\",\n" +
                                        "    \"48\",\n" +
                                        "    \"87221;54;0\",\n" +
                                        "    \"14683;14508;14508;14508;14508;14508\",\n" +
                                        "    \"0;0;0\",\n" +
                                        "    \"off;off;off;off;off;off\",\n" +
                                        "    \"53;90;50;90;56;90;58;90;61;90;60;90\",\n" +
                                        "    \"eu1.ethermine.org:4444\",\n" +
                                        "    \"0;0;0;0\"\n" +
                                        "  ]\n" +
                                        "}"))),
                new MinerStats.Builder()
                        .setApiIp("127.0.0.1")
                        .setApiPort(3333)
                        .addPool(
                                new Pool.Builder()
                                        .setName("eu1.ethermine.org:4444")
                                        .setStatus(true, true)
                                        .setPriority(0)
                                        .setCounts(54, 0, 0)
                                        .build())
                        .addRig(
                                new Rig.Builder()
                                        .setHashRate(new BigDecimal("87221000"))
                                        .addGpu(
                                                new Gpu.Builder()
                                                        .setName("GPU 0")
                                                        .setIndex(0)
                                                        .setBus("0")
                                                        .setTemp(53)
                                                        .setFans(
                                                                new FanInfo.Builder()
                                                                        .setCount(1)
                                                                        .addSpeed(90)
                                                                        .setSpeedUnits("%")
                                                                        .build())
                                                        .setFreqInfo(
                                                                new FreqInfo.Builder()
                                                                        .setFreq("0")
                                                                        .setMemFreq("0")
                                                                        .build())
                                                        .build())
                                        .addGpu(
                                                new Gpu.Builder()
                                                        .setName("GPU 1")
                                                        .setIndex(1)
                                                        .setBus("0")
                                                        .setTemp(50)
                                                        .setFans(
                                                                new FanInfo.Builder()
                                                                        .setCount(1)
                                                                        .addSpeed(90)
                                                                        .setSpeedUnits("%")
                                                                        .build())
                                                        .setFreqInfo(
                                                                new FreqInfo.Builder()
                                                                        .setFreq("0")
                                                                        .setMemFreq("0")
                                                                        .build())
                                                        .build())
                                        .addGpu(
                                                new Gpu.Builder()
                                                        .setName("GPU 2")
                                                        .setIndex(2)
                                                        .setBus("0")
                                                        .setTemp(56)
                                                        .setFans(
                                                                new FanInfo.Builder()
                                                                        .setCount(1)
                                                                        .addSpeed(90)
                                                                        .setSpeedUnits("%")
                                                                        .build())
                                                        .setFreqInfo(
                                                                new FreqInfo.Builder()
                                                                        .setFreq("0")
                                                                        .setMemFreq("0")
                                                                        .build())
                                                        .build())
                                        .addGpu(
                                                new Gpu.Builder()
                                                        .setName("GPU 3")
                                                        .setIndex(3)
                                                        .setBus("0")
                                                        .setTemp(58)
                                                        .setFans(
                                                                new FanInfo.Builder()
                                                                        .setCount(1)
                                                                        .addSpeed(90)
                                                                        .setSpeedUnits("%")
                                                                        .build())
                                                        .setFreqInfo(
                                                                new FreqInfo.Builder()
                                                                        .setFreq("0")
                                                                        .setMemFreq("0")
                                                                        .build())
                                                        .build())
                                        .addGpu(
                                                new Gpu.Builder()
                                                        .setName("GPU 4")
                                                        .setIndex(4)
                                                        .setBus("0")
                                                        .setTemp(61)
                                                        .setFans(
                                                                new FanInfo.Builder()
                                                                        .setCount(1)
                                                                        .addSpeed(90)
                                                                        .setSpeedUnits("%")
                                                                        .build())
                                                        .setFreqInfo(
                                                                new FreqInfo.Builder()
                                                                        .setFreq("0")
                                                                        .setMemFreq("0")
                                                                        .build())
                                                        .build())
                                        .addGpu(
                                                new Gpu.Builder()
                                                        .setName("GPU 5")
                                                        .setIndex(5)
                                                        .setBus("0")
                                                        .setTemp(60)
                                                        .setFans(
                                                                new FanInfo.Builder()
                                                                        .setCount(1)
                                                                        .addSpeed(90)
                                                                        .setSpeedUnits("%")
                                                                        .build())
                                                        .setFreqInfo(
                                                                new FreqInfo.Builder()
                                                                        .setFreq("0")
                                                                        .setMemFreq("0")
                                                                        .build())
                                                        .build())
                                        .build())
                        .build());
    }
}
