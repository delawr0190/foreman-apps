package mn.foreman.swarm;

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

/** Runs an integration tests using {@link Swarm} against a fake API. */
public class SwarmITest
        extends AbstractApiITest {

    /** Constructor. */
    public SwarmITest() {
        super(
                new SwarmFactory()
                        .create(
                                ImmutableMap.of(
                                        "apiIp",
                                        "127.0.0.1",
                                        "apiPort",
                                        "6099")),
                new FakeRpcMinerServer(
                        6099,
                        ImmutableMap.of(
                                "stats",
                                new RpcHandler(
                                        "{\n" +
                                                "\"hsu\":\"khs\"," +
                                                "\"fans\":[\n" +
                                                "        \"85\",\n" +
                                                "        \"80\",\n" +
                                                "        \"80\",\n" +
                                                "        \"80\",\n" +
                                                "        \"80\",\n" +
                                                "        \"85\",\n" +
                                                "        \"80\",\n" +
                                                "        \"80\"\n" +
                                                "        ],\n" +
                                                "\"asic_total\":null,\n" +
                                                "\"gpus\":[\n" +
                                                "        \"21751.1100\",\n" +
                                                "        \"21691.3850\",\n" +
                                                "        \"21872.1230\",\n" +
                                                "        \"14172.0000\",\n" +
                                                "        \"22028.8420\",\n" +
                                                "        \"21450.7080\",\n" +
                                                "        \"0.0008\",\n" +
                                                "        \"0.0008\"\n" +
                                                "        ],\n" +
                                                "\"uptime\":384.0,\n" +
                                                "\"power\":[\n" +
                                                "         \"170\",\n" +
                                                "         \"173\",\n" +
                                                "         \"171\",\n" +
                                                "         \"75\",\n" +
                                                "         \"170\",\n" +
                                                "         \"169\",\n" +
                                                "         \"140\",\n" +
                                                "         \"154\"\n" +
                                                "         ],\n" +
                                                "\"accepted\":275,\n" +
                                                "\"gpu_total\":122966.1697,\n" +
                                                "\"asics\":null,\n" +
                                                "\"cpu_total\":137.82,\n" +
                                                "\"algo\":\"x16rt\",\n" +
                                                "\"rejected\":1,\n" +
                                                "\"cpus\":[\n" +
                                                "        \"0.0490\"\n" +
                                                "       ],\n" +
                                                "\"temps\":[\n" +
                                                "        \"70\",\n" +
                                                "        \"59\",\n" +
                                                "        \"62\",\n" +
                                                "        \"47\",\n" +
                                                "        \"65\",\n" +
                                                "        \"69\",\n" +
                                                "        \"58\",\n" +
                                                "        \"54\"\n" +
                                                "        ],\n" +
                                                "\"stratum\": \"stratum+tcp://daggerhashimoto.usa.nicehash.com:3353\"\n" +
                                                "}\n"))),
                new MinerStats.Builder()
                        .setApiIp("127.0.0.1")
                        .setApiPort(6099)
                        .addPool(
                                new Pool.Builder()
                                        .setName("daggerhashimoto.usa.nicehash.com:3353")
                                        .setStatus(true, true)
                                        .setPriority(0)
                                        .setCounts(275, 1, 0)
                                        .build())
                        .addRig(
                                new Rig.Builder()
                                        .setHashRate(new BigDecimal("122966169.7000"))
                                        .addGpu(
                                                new Gpu.Builder()
                                                        .setName("GPU 0")
                                                        .setTemp(70)
                                                        .setIndex(0)
                                                        .setBus(0)
                                                        .setFans(
                                                                new FanInfo.Builder()
                                                                        .setCount(1)
                                                                        .addSpeed(85)
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
                                                        .setTemp(59)
                                                        .setIndex(1)
                                                        .setBus(0)
                                                        .setFans(
                                                                new FanInfo.Builder()
                                                                        .setCount(1)
                                                                        .addSpeed(80)
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
                                                        .setTemp(62)
                                                        .setIndex(2)
                                                        .setBus(0)
                                                        .setFans(
                                                                new FanInfo.Builder()
                                                                        .setCount(1)
                                                                        .addSpeed(80)
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
                                                        .setTemp(47)
                                                        .setIndex(3)
                                                        .setBus(0)
                                                        .setFans(
                                                                new FanInfo.Builder()
                                                                        .setCount(1)
                                                                        .addSpeed(80)
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
                                                        .setTemp(65)
                                                        .setIndex(4)
                                                        .setBus(0)
                                                        .setFans(
                                                                new FanInfo.Builder()
                                                                        .setCount(1)
                                                                        .addSpeed(80)
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
                                                        .setTemp(69)
                                                        .setIndex(5)
                                                        .setBus(0)
                                                        .setFans(
                                                                new FanInfo.Builder()
                                                                        .setCount(1)
                                                                        .addSpeed(85)
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
                                                        .setTemp(58)
                                                        .setIndex(6)
                                                        .setBus(0)
                                                        .setFans(
                                                                new FanInfo.Builder()
                                                                        .setCount(1)
                                                                        .addSpeed(80)
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
                                                        .setTemp(54)
                                                        .setIndex(7)
                                                        .setBus(0)
                                                        .setFans(
                                                                new FanInfo.Builder()
                                                                        .setCount(1)
                                                                        .addSpeed(80)
                                                                        .setSpeedUnits("%")
                                                                        .build())
                                                        .setFreqInfo(
                                                                new FreqInfo.Builder()
                                                                        .setFreq(0)
                                                                        .setMemFreq(0)
                                                                        .build())
                                                        .build())
                                        .addAttribute(
                                                "uptime",
                                                "384")
                                        .addAttribute(
                                                "algo",
                                                "x16rt")
                                        .build())
                        .build());
    }
}