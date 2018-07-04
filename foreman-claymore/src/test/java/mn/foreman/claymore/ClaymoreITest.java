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
public class ClaymoreITest
        extends AbstractApiITest {

    /** Constructor. */
    public ClaymoreITest() {
        super(
                new Claymore(
                        "claymore",
                        "127.0.0.1",
                        3333,
                        null),
                new FakeRpcMinerServer(
                        3333,
                        ImmutableMap.of(
                                "{\"id\":0,\"jsonrpc\":\"2.0\",\"method\":\"miner_getstat1\"}",
                                new RpcHandler(
                                        "{\"error\": null, \"id\": 0, \"result\": [\"10.0 - ETH\", \"0\", \"10;0;0\", \"10\", \"20;0;0\", \"20\", \"52;47\", \"daggerhashimoto.eu.nicehash.com:3353;decred.eu.nicehash.com:3354\", \"0;0;0;0\"]}"))),
                new MinerStats.Builder()
                        .setName("claymore")
                        .setApiIp("127.0.0.1")
                        .setApiPort(3333)
                        .addPool(
                                new Pool.Builder()
                                        .setName("daggerhashimoto.eu.nicehash.com:3353")
                                        .setStatus(true, true)
                                        .setPriority(0)
                                        .setCounts(0, 0, 0)
                                        .build())
                        .addPool(
                                new Pool.Builder()
                                        .setName("decred.eu.nicehash.com:3354")
                                        .setStatus(true, true)
                                        .setPriority(0)
                                        .setCounts(0, 0, 0)
                                        .build())
                        .addRig(
                                new Rig.Builder()
                                        .setName("claymore_10.0 - ETH")
                                        .setHashRate(new BigDecimal("30"))
                                        .addGpu(
                                                new Gpu.Builder()
                                                        .setName("GPU 0")
                                                        .setTemp(52)
                                                        .setIndex(0)
                                                        .setBus(0)
                                                        .setFans(
                                                                new FanInfo.Builder()
                                                                        .setCount(1)
                                                                        .addSpeed(47)
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