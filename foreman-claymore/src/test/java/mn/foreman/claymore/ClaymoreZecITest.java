package mn.foreman.claymore;

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

/** Runs an integration tests using {@link Claymore} against a fake API. */
public class ClaymoreZecITest
        extends AbstractApiITest {

    /** Constructor. */
    public ClaymoreZecITest() {
        super(
                new Claymore(
                        "127.0.0.1",
                        3333,
                        "password",
                        ClaymoreType.ZEC),
                new FakeRpcMinerServer(
                        3333,
                        ImmutableMap.of(
                                "{\"id\":0,\"jsonrpc\":\"2.0\",\"method\":\"miner_getstat1\",\"psw\":\"password\"}\n",
                                new RpcHandler(
                                        "{\n\"id\":0,\n\"error\":null,\n\"result\":[\n\"12.6 - ZEC\",\n\"0\",\n\"128;0;0\",\n\"128\",\n\"0;0;0\",\n\"off\",\n\"60;33\",\n\"zec-eu1.nanopool.org:6633\",\n\"0;0;0;0\"\n]\n}\n"))),
                new MinerStats.Builder()
                        .setApiIp("127.0.0.1")
                        .setApiPort(3333)
                        .addPool(
                                new Pool.Builder()
                                        .setName("zec-eu1.nanopool.org:6633")
                                        .setStatus(true, true)
                                        .setPriority(0)
                                        .setCounts(0, 0, 0)
                                        .build())
                        .addRig(
                                new Rig.Builder()
                                        .setHashRate(new BigDecimal("128"))
                                        .addGpu(
                                                new Gpu.Builder()
                                                        .setName("GPU 0")
                                                        .setTemp(60)
                                                        .setIndex(0)
                                                        .setBus(0)
                                                        .setFans(
                                                                new FanInfo.Builder()
                                                                        .setCount(1)
                                                                        .addSpeed(33)
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