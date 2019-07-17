package mn.foreman.nicehash;

import mn.foreman.bminer.BminerFactory;
import mn.foreman.claymore.ClaymoreFactory;
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
import java.util.Arrays;

/** Runs an integration tests using {@link NiceHashMiner} against a fake API. */
public class NiceHashMinerITest
        extends AbstractApiITest {

    /** Constructor. */
    public NiceHashMinerITest() {
        super(
                new NiceHashMinerFactory(
                        Arrays.asList(
                                new BminerFactory().create(
                                        ImmutableMap.of(
                                                "apiIp",
                                                "127.0.0.1",
                                                "apiPort",
                                                "4000")),
                                new ClaymoreFactory().create(
                                        ImmutableMap.of(
                                                "apiIp",
                                                "127.0.0.1",
                                                "apiPort",
                                                "4005"))))
                        .create(
                                ImmutableMap.of(
                                        "apiIp",
                                        "127.0.0.1",
                                        "apiPort",
                                        "4000")),
                new FakeRpcMinerServer(
                        4005,
                        ImmutableMap.of(
                                "{\"id\":0,\"jsonrpc\":\"2.0\",\"method\":\"miner_getstat1\"}\n",
                                new RpcHandler(
                                        "{\"id\": 0, \"error\": null, \"result\": [\"11.8 - ETH\", \"0\", \"235321;2;0\", \"29482;29474;29427;29477;29523;29523;29533;28878\", \"0;0;0\", \"off;off;off;off;off;off;off;off\", \"60;60;56;56;57;57;57;57;58;58;59;58;57;57;58;58\", \"eth-eu1.nanopool.org:9999\", \"0;0;0;0\"]}"))),
                new MinerStats.Builder()
                        .setApiIp("127.0.0.1")
                        .setApiPort(4000)
                        .addPool(
                                new Pool.Builder()
                                        .setName("eth-eu1.nanopool.org:9999")
                                        .setStatus(true, true)
                                        .setPriority(0)
                                        .setCounts(2, 0, 0)
                                        .build())
                        .addRig(
                                new Rig.Builder()
                                        .setHashRate(new BigDecimal("235321000"))
                                        .addGpu(
                                                new Gpu.Builder()
                                                        .setName("GPU 0")
                                                        .setTemp(60)
                                                        .setIndex(0)
                                                        .setBus(0)
                                                        .setFans(
                                                                new FanInfo.Builder()
                                                                        .setCount(1)
                                                                        .addSpeed(60)
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
                                                        .setTemp(56)
                                                        .setIndex(1)
                                                        .setBus(0)
                                                        .setFans(
                                                                new FanInfo.Builder()
                                                                        .setCount(1)
                                                                        .addSpeed(56)
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
                                                        .setName("GPU 2")
                                                        .setTemp(57)
                                                        .setIndex(2)
                                                        .setBus(0)
                                                        .setFans(
                                                                new FanInfo.Builder()
                                                                        .setCount(1)
                                                                        .addSpeed(57)
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
                                                        .setName("GPU 3")
                                                        .setTemp(57)
                                                        .setIndex(3)
                                                        .setBus(0)
                                                        .setFans(
                                                                new FanInfo.Builder()
                                                                        .setCount(1)
                                                                        .addSpeed(57)
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
                                                        .setName("GPU 4")
                                                        .setTemp(58)
                                                        .setIndex(4)
                                                        .setBus(0)
                                                        .setFans(
                                                                new FanInfo.Builder()
                                                                        .setCount(1)
                                                                        .addSpeed(58)
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
                                                        .setName("GPU 5")
                                                        .setTemp(59)
                                                        .setIndex(5)
                                                        .setBus(0)
                                                        .setFans(
                                                                new FanInfo.Builder()
                                                                        .setCount(1)
                                                                        .addSpeed(58)
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
                                                        .setName("GPU 6")
                                                        .setTemp(57)
                                                        .setIndex(6)
                                                        .setBus(0)
                                                        .setFans(
                                                                new FanInfo.Builder()
                                                                        .setCount(1)
                                                                        .addSpeed(57)
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
                                                        .setName("GPU 7")
                                                        .setTemp(58)
                                                        .setIndex(7)
                                                        .setBus(0)
                                                        .setFans(
                                                                new FanInfo.Builder()
                                                                        .setCount(1)
                                                                        .addSpeed(58)
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
                                        .addAttribute(
                                                "api_port",
                                                "4005")
                                        .build())
                        .build());
    }
}