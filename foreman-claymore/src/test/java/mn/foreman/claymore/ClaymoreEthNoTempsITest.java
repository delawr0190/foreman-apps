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
public class ClaymoreEthNoTempsITest
        extends AbstractApiITest {

    /** Constructor. */
    public ClaymoreEthNoTempsITest() {
        super(
                new Claymore(
                        "127.0.0.1",
                        3333,
                        null,
                        ClaymoreType.ETH),
                new FakeRpcMinerServer(
                        3333,
                        ImmutableMap.of(
                                "{\"id\":0,\"jsonrpc\":\"2.0\",\"method\":\"miner_getstat1\"}\n",
                                new RpcHandler(
                                        "{\"id\": 0, \"result\": [\"12.0 - ETH\", \"1\", \"160715;2;0\", \"77582;83133\", \"0;0;0\", \"off;off\", \"\", \"us1.ethermine.org:4444\", \"0;0;0;0\"], \"error\": null}"))),
                new MinerStats.Builder()
                        .setApiIp("127.0.0.1")
                        .setApiPort(3333)
                        .addPool(
                                new Pool.Builder()
                                        .setName("us1.ethermine.org:4444")
                                        .setStatus(true, true)
                                        .setPriority(0)
                                        .setCounts(2, 0, 0)
                                        .build())
                        .addRig(
                                new Rig.Builder()
                                        .setHashRate(new BigDecimal("160715000"))
                                        .addGpu(
                                                new Gpu.Builder()
                                                        .setName("GPU 0")
                                                        .setTemp(0)
                                                        .setIndex(0)
                                                        .setBus(0)
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
                                                        .setName("GPU 1")
                                                        .setTemp(0)
                                                        .setIndex(1)
                                                        .setBus(0)
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
                                        .addAttribute(
                                                "is_xmr",
                                                "false")
                                        .addAttribute(
                                                "is_zec",
                                                "false")
                                        .addAttribute(
                                                "is_pm",
                                                "false")
                                        .addAttribute(
                                                "is_tt",
                                                "false")
                                        .addAttribute(
                                                "is_ethminer",
                                                "false")
                                        .addAttribute(
                                                "is_clay",
                                                "true")
                                        .build())
                        .build());
    }
}