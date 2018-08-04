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
public class ClaymoreXmrITest
        extends AbstractApiITest {

    /** Constructor. */
    public ClaymoreXmrITest() {
        super(
                new Claymore(
                        "claymore",
                        "127.0.0.1",
                        3333,
                        null,
                        ClaymoreType.XMR),
                new FakeRpcMinerServer(
                        3333,
                        ImmutableMap.of(
                                "{\"id\":0,\"jsonrpc\":\"2.0\",\"method\":\"miner_getstat1\"}\n",
                                new RpcHandler(
                                        "{\n\"id\":0,\n\"error\":null,\n\"result\":[\n\"11.3 - XMR\",\n\"2\",\n\"339;0;0\",\n\"339\",\n\"0;0;0\",\n\"off\",\n\"61;44\",\n\"xmr-eu1.nanopool.org:14433\",\n\"0;0;0;0\"\n]\n}\n"))),
                new MinerStats.Builder()
                        .setName("claymore")
                        .setApiIp("127.0.0.1")
                        .setApiPort(3333)
                        .addPool(
                                new Pool.Builder()
                                        .setName("xmr-eu1.nanopool.org:14433")
                                        .setStatus(true, true)
                                        .setPriority(0)
                                        .setCounts(0, 0, 0)
                                        .build())
                        .addRig(
                                new Rig.Builder()
                                        .setName("claymore_11.3 - XMR")
                                        .setHashRate(new BigDecimal("339"))
                                        .addGpu(
                                                new Gpu.Builder()
                                                        .setName("GPU 0")
                                                        .setTemp(61)
                                                        .setIndex(0)
                                                        .setBus(0)
                                                        .setFans(
                                                                new FanInfo.Builder()
                                                                        .setCount(1)
                                                                        .addSpeed(44)
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