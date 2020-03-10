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
public class Ethminer19ITest
        extends AbstractApiITest {

    /** Constructor. */
    public Ethminer19ITest() {
        super(
                new Ethminer(
                        "127.0.0.1",
                        3333,
                        null),
                new FakeRpcMinerServer(
                        3333,
                        ImmutableMap.of(
                                "{\"id\":1,\"jsonrpc\":\"2.0\",\"method\":\"miner_getstat1\"}\n",
                                new RpcHandler("{\"id\":0,\"jsonrpc\":\"2.0\",\"result\":[\"0.19.0-beta.0+nhfix\",\"4\",\"266708;13;0\",\"23117;23100;19731;22266;21328;23708;23559;23766;22556;20847;20973;21756\",\"0;0;0\",\"off;off;off;off;off;off;off;off;off;off;off;off\",\"49;100;56;100;56;100;57;100;56;100;51;100;51;100;50;85;59;0;54;95;52;100;52;100\",\"asia.ethash-hub.miningpoolhub.com:17020\",\"0;0;0;0\"]}"))),
                new MinerStats.Builder()
                        .setApiIp("127.0.0.1")
                        .setApiPort(3333)
                        .addPool(
                                new Pool.Builder()
                                        .setName("asia.ethash-hub.miningpoolhub.com:17020")
                                        .setStatus(true, true)
                                        .setPriority(0)
                                        .setCounts(13, 0, 0)
                                        .build())
                        .addRig(
                                new Rig.Builder()
                                        .setHashRate(new BigDecimal("266708000"))
                                        .addGpu(
                                                new Gpu.Builder()
                                                        .setName("GPU 0")
                                                        .setIndex(0)
                                                        .setBus("0")
                                                        .setTemp(49)
                                                        .setFans(
                                                                new FanInfo.Builder()
                                                                        .setCount(1)
                                                                        .addSpeed(100)
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
                                                        .setTemp(56)
                                                        .setFans(
                                                                new FanInfo.Builder()
                                                                        .setCount(1)
                                                                        .addSpeed(100)
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
                                                                        .addSpeed(100)
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
                                                        .setTemp(57)
                                                        .setFans(
                                                                new FanInfo.Builder()
                                                                        .setCount(1)
                                                                        .addSpeed(100)
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
                                                        .setTemp(56)
                                                        .setFans(
                                                                new FanInfo.Builder()
                                                                        .setCount(1)
                                                                        .addSpeed(100)
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
                                                        .setTemp(51)
                                                        .setFans(
                                                                new FanInfo.Builder()
                                                                        .setCount(1)
                                                                        .addSpeed(100)
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
                                                        .setName("GPU 6")
                                                        .setIndex(6)
                                                        .setBus("0")
                                                        .setTemp(51)
                                                        .setFans(
                                                                new FanInfo.Builder()
                                                                        .setCount(1)
                                                                        .addSpeed(100)
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
                                                        .setName("GPU 7")
                                                        .setIndex(7)
                                                        .setBus("0")
                                                        .setTemp(50)
                                                        .setFans(
                                                                new FanInfo.Builder()
                                                                        .setCount(1)
                                                                        .addSpeed(85)
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
                                                        .setName("GPU 8")
                                                        .setIndex(8)
                                                        .setBus("0")
                                                        .setTemp(59)
                                                        .setFans(
                                                                new FanInfo.Builder()
                                                                        .setCount(1)
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
                                                        .setName("GPU 9")
                                                        .setIndex(9)
                                                        .setBus("0")
                                                        .setTemp(54)
                                                        .setFans(
                                                                new FanInfo.Builder()
                                                                        .setCount(1)
                                                                        .addSpeed(95)
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
                                                        .setName("GPU 10")
                                                        .setIndex(10)
                                                        .setBus("0")
                                                        .setTemp(52)
                                                        .setFans(
                                                                new FanInfo.Builder()
                                                                        .setCount(1)
                                                                        .addSpeed(100)
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
                                                        .setName("GPU 11")
                                                        .setIndex(11)
                                                        .setBus("0")
                                                        .setTemp(52)
                                                        .setFans(
                                                                new FanInfo.Builder()
                                                                        .setCount(1)
                                                                        .addSpeed(100)
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
