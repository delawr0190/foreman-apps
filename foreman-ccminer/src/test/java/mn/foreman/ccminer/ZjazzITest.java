package mn.foreman.ccminer;

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

/** Runs an integration tests using {@link CcMiner} against a fake API. */
public class ZjazzITest
        extends AbstractApiITest {

    /** Constructor. */
    public ZjazzITest() {
        super(
                new CcMiner(
                        "127.0.0.1",
                        4011),
                new FakeRpcMinerServer(
                        4011,
                        ImmutableMap.of(
                                "summary|",
                                new RpcHandler(
                                        "NAME=zjazz_cuda_win;VER=0.971;API=1.0;ALGO=bitcash;GPUS=8;KHS=2689.64;HS=2.6896;SOLV=25;ACC=25;REJ=0;ACCMN=0.000;DIFF=0.000002;NETKHS=0;POOLS=1;UPTIME=37;TS=1537841271|"),
                                "hwinfo|",
                                new RpcHandler(""),
                                "pool|",
                                new RpcHandler(
                                        "POOL=stratum+tcp://mine.icemining.ca:3343;ALGO=bitcash;URL=stratum+tcp://mine.icemining.ca:3343;USER=18Ly1UN7RUojCvJiGABWGmmLrbLXdffTsjA31NReGnzRJ7HAqeF;SOLV=18;ACC=18;REJ=0;STALE=0;DIFF=1.000000;BEST=20.507002;DISCO=0;UPTIME=5;LAST=0|"),
                                "gpus|",
                                new RpcHandler(
                                        "GPU=0;KHS=0.00;ACC=2;REJ=0;HS=0.0000|GPU=1;KHS=0.00;ACC=1;REJ=0;HS=0.0000|GPU=2;KHS=0.00;ACC=0;REJ=0;HS=0.0000|GPU=3;KHS=0.00;ACC=1;REJ=0;HS=0.0000|GPU=4;KHS=0.00;ACC=1;REJ=0;HS=0.0000|GPU=5;KHS=0.00;ACC=3;REJ=0;HS=0.0000|GPU=6;KHS=0.00;ACC=3;REJ=0;HS=0.0000|GPU=7;KHS=0.00;ACC=0;REJ=0;HS=0.0000|"))),
                new MinerStats.Builder()
                        .setApiIp("127.0.0.1")
                        .setApiPort(4011)
                        .addPool(
                                new Pool.Builder()
                                        .setName("mine.icemining.ca:3343")
                                        .setStatus(true, true)
                                        .setPriority(1)
                                        .setCounts(18, 0, 0)
                                        .build())
                        .addRig(
                                new Rig.Builder()
                                        .setHashRate(new BigDecimal("2689640.00"))
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
                                                        .setName("GPU 2")
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
                                                        .setName("GPU 3")
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
                                                        .setName("GPU 4")
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
                                                        .setName("GPU 5")
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
                                                        .setName("GPU 6")
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
                                                        .setName("GPU 7")
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
                                        .build())
                        .build());
    }
}