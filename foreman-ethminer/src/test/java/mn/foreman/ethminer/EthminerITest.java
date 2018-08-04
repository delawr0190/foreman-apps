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
public class EthminerITest
        extends AbstractApiITest {

    /** Constructor. */
    public EthminerITest() {
        super(
                new Ethminer(
                        "ethminer",
                        "127.0.0.1",
                        3333,
                        null),
                new FakeRpcMinerServer(
                        3333,
                        ImmutableMap.of(
                                "{\"id\":1,\"jsonrpc\":\"2.0\",\"method\":\"miner_getstathr\"}\n",
                                new RpcHandler("{\"id\":1,\"jsonrpc\":\"2.0\",\"result\":{\"ethhashrate\":73056881,\"ethhashrates\":[14681287,14506510,14681287,14506510,0,14681287],\"ethinvalid\":0,\"ethpoolsw\":0,\"ethrejected\":0,\"ethshares\":64,\"fanpercentages\":[90,90,90,90,100,90],\"pooladdrs\":\"eu1.ethermine.org:4444\",\"powerusages\":[0.0,0.0,0.0,0.0,0.0,0.0],\"runtime\":\"59\",\"temperatures\":[53,50,56,58,68,60],\"ispaused\":[false,false,false,true,false],\"version\": \"ethminer-0.16.0.dev0+commit.41639944\"}}"))),
                new MinerStats.Builder()
                        .setName("ethminer")
                        .setApiIp("127.0.0.1")
                        .setApiPort(3333)
                        .addPool(
                                new Pool.Builder()
                                        .setName("eu1.ethermine.org:4444")
                                        .setStatus(true, true)
                                        .setPriority(0)
                                        .setCounts(64, 0, 0)
                                        .build())
                        .addRig(
                                new Rig.Builder()
                                        .setName("ethminer_ethminer-0.16.0.dev0+commit.41639944")
                                        .setHashRate(new BigDecimal("73056881"))
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
                                                        .setTemp(68)
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
