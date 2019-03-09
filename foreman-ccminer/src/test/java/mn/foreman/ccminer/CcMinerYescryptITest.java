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
public class CcMinerYescryptITest
        extends AbstractApiITest {

    /** Constructor. */
    public CcMinerYescryptITest() {
        super(
                new CcMiner(
                        "127.0.0.1",
                        4068),
                new FakeRpcMinerServer(
                        4068,
                        ImmutableMap.of(
                                "summary|",
                                new RpcHandler(
                                        "NAME=ccminer;VER=Yescrypt/R16/R32.V10;API=1.3;ALGO=yescrypt;GPUS=1;KHS=3.37;ACC=1;REJ=0;ACCMN=3.529;DIFF=1.000000;UPTIME=17;TS=1552144105| "),
                                "pool|",
                                new RpcHandler(
                                        "URL=stratum+tcp://yescrypt.easymine.online:6000;USER=MScsE76AoD2oUBojhyJMLSBrQXUY8hg9Lj.obm;H=2726002;JOB=f2dc;DIFF=1.000000;N2SZ=4;N2=0x01000000;PING=81;DISCO=0;UPTIME=37| "),
                                "hwinfo|",
                                new RpcHandler(
                                        "GPU=0;BUS=1;CARD=GeForce GTX 1050 Ti;SM=610;MEM=0;TEMP=55.0;FAN=0;RPM=0;FREQ=1620;MEMFREQ=3504;GPUF=0;MEMF=0;PST=P3;POWER=0;PLIM=0;VID=17aa;PID=39fd;NVML=0;NVAPI=0;SN=C41D087370;BIOS=86.07.57.00.42|OS=windows;NVDRIVER=416.34;CPUS=12;CPUTEMP=0;CPUFREQ=0| "))),
                new MinerStats.Builder()
                        .setApiIp("127.0.0.1")
                        .setApiPort(4068)
                        .addPool(
                                new Pool.Builder()
                                        .setName("yescrypt.easymine.online:6000")
                                        .setStatus(true, true)
                                        .setPriority(0)
                                        .setCounts(1, 0, 0)
                                        .build())
                        .addRig(
                                new Rig.Builder()
                                        .setHashRate(new BigDecimal("3370.00"))
                                        .addGpu(
                                                new Gpu.Builder()
                                                        .setName("GeForce GTX 1050 Ti")
                                                        .setTemp(55)
                                                        .setIndex(0)
                                                        .setBus(1)
                                                        .setFans(
                                                                new FanInfo.Builder()
                                                                        .setCount(1)
                                                                        .setSpeedUnits("%")
                                                                        .build())
                                                        .setFreqInfo(
                                                                new FreqInfo.Builder()
                                                                        .setFreq(1620)
                                                                        .setMemFreq(3504)
                                                                        .build())
                                                        .build())
                                        .build())
                        .build());
    }
}