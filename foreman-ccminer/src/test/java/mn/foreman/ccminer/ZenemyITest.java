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
public class ZenemyITest
        extends AbstractApiITest {

    /** Constructor. */
    public ZenemyITest() {
        super(
                new CcMiner(
                        "127.0.0.1",
                        4068),
                new FakeRpcMinerServer(
                        4068,
                        ImmutableMap.of(
                                "summary|",
                                new RpcHandler(
                                        "NAME=zealot;VER=enemy-1.17;API=1.8;ALGO=x16r;GPUS=8;KHS=64879.73;SOLV=0;ACC=0;REJ=0;ACCMN=0.000;POOLS=1;WAIT=14;UPTIME=26;TS=1535939106|"),
                                "pool|",
                                new RpcHandler(
                                        "POOL=ravenminer.com:3636;ALGO=x16r;URL=stratum+tcp://ravenminer.com:3636;USER=xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx;SOLV=0;ACC=3;REJ=0;STALE=0;H=344474;JOB=e;BEST=1.039566;N2SZ=4;N2=0x02000000;PING=125;DISCO=0;WAIT=14;UPTIME=12;LAST=1|"),
                                "hwinfo|",
                                new RpcHandler(
                                        "GPU=0;BUS=1;CARD=EVGA GTX 1070 Ti;SM=610;MEM=8192;TEMP=70;FAN=70;RPM=1759;FREQ=1683000;MEMFREQ=4004000;PST=P2;POWER=0;VID=3842;PID=5670;NVML=-1;NVAPI=0;SN=;BIOS=86.04.85.00.70|GPU=1;BUS=2;CARD=EVGA GTX 1070 Ti;SM=610;MEM=8192;TEMP=66;FAN=69;RPM=1738;FREQ=1683000;MEMFREQ=4004000;PST=P2;POWER=0;VID=3842;PID=5670;NVML=-1;NVAPI=1;SN=;BIOS=86.04.85.00.70|GPU=2;BUS=3;CARD=EVGA GTX 1070 Ti;SM=610;MEM=8192;TEMP=67;FAN=67;RPM=1688;FREQ=1683000;MEMFREQ=4004000;PST=P2;POWER=0;VID=3842;PID=5670;NVML=-1;NVAPI=2;SN=;BIOS=86.04.8d.00.70|GPU=3;BUS=4;CARD=EVGA GTX 1070 Ti;SM=610;MEM=8192;TEMP=65;FAN=68;RPM=1712;FREQ=1683000;MEMFREQ=4004000;PST=P2;POWER=0;VID=3842;PID=5670;NVML=-1;NVAPI=3;SN=;BIOS=86.04.85.00.70|GPU=4;BUS=6;CARD=EVGA GTX 1070 Ti;SM=610;MEM=8192;TEMP=64;FAN=69;RPM=1729;FREQ=1683000;MEMFREQ=4004000;PST=P2;POWER=0;VID=3842;PID=5670;NVML=-1;NVAPI=4;SN=;BIOS=86.04.85.00.70|GPU=5;BUS=9;CARD=EVGA GTX 1070 Ti;SM=610;MEM=8192;TEMP=56;FAN=69;RPM=1741;FREQ=1683000;MEMFREQ=4004000;PST=P2;POWER=0;VID=3842;PID=5670;NVML=-1;NVAPI=5;SN=;BIOS=86.04.85.00.70|GPU=6;BUS=10;CARD=EVGA GTX 1070 Ti;SM=610;MEM=8192;TEMP=58;FAN=71;RPM=1790;FREQ=1683000;MEMFREQ=4004000;PST=P2;POWER=0;VID=3842;PID=5670;NVML=-1;NVAPI=6;SN=;BIOS=86.04.85.00.70|GPU=7;BUS=11;CARD=EVGA GTX 1070 Ti;SM=610;MEM=8192;TEMP=38;FAN=39;RPM=996;FREQ=1683000;MEMFREQ=4004000;PST=P2;POWER=0;VID=3842;PID=5670;NVML=-1;NVAPI=7;SN=;BIOS=86.04.8d.00.70|OS=windows;NVDRIVER=397.64;CPUS=8;CPUTEMP=0;CPUFREQ=0|"))),
                new MinerStats.Builder()
                        .setApiIp("127.0.0.1")
                        .setApiPort(4068)
                        .addPool(
                                new Pool.Builder()
                                        .setName("ravenminer.com:3636")
                                        .setStatus(true, true)
                                        .setPriority(1)
                                        .setCounts(3, 0, 0)
                                        .build())
                        .addRig(
                                new Rig.Builder()
                                        .setHashRate(new BigDecimal("64879730.00"))
                                        .addGpu(
                                                new Gpu.Builder()
                                                        .setName("EVGA GTX 1070 Ti")
                                                        .setTemp(70)
                                                        .setIndex(0)
                                                        .setBus(1)
                                                        .setFans(
                                                                new FanInfo.Builder()
                                                                        .setCount(1)
                                                                        .addSpeed(70)
                                                                        .setSpeedUnits("%")
                                                                        .build())
                                                        .setFreqInfo(
                                                                new FreqInfo.Builder()
                                                                        .setFreq(1683)
                                                                        .setMemFreq(4004)
                                                                        .build())
                                                        .build())
                                        .addGpu(
                                                new Gpu.Builder()
                                                        .setName("EVGA GTX 1070 Ti")
                                                        .setTemp(66)
                                                        .setIndex(1)
                                                        .setBus(2)
                                                        .setFans(
                                                                new FanInfo.Builder()
                                                                        .setCount(1)
                                                                        .addSpeed(69)
                                                                        .setSpeedUnits("%")
                                                                        .build())
                                                        .setFreqInfo(
                                                                new FreqInfo.Builder()
                                                                        .setFreq(1683)
                                                                        .setMemFreq(4004)
                                                                        .build())
                                                        .build())
                                        .addGpu(
                                                new Gpu.Builder()
                                                        .setName("EVGA GTX 1070 Ti")
                                                        .setTemp(67)
                                                        .setIndex(2)
                                                        .setBus(3)
                                                        .setFans(
                                                                new FanInfo.Builder()
                                                                        .setCount(1)
                                                                        .addSpeed(67)
                                                                        .setSpeedUnits("%")
                                                                        .build())
                                                        .setFreqInfo(
                                                                new FreqInfo.Builder()
                                                                        .setFreq(1683)
                                                                        .setMemFreq(4004)
                                                                        .build())
                                                        .build())
                                        .addGpu(
                                                new Gpu.Builder()
                                                        .setName("EVGA GTX 1070 Ti")
                                                        .setTemp(65)
                                                        .setIndex(3)
                                                        .setBus(4)
                                                        .setFans(
                                                                new FanInfo.Builder()
                                                                        .setCount(1)
                                                                        .addSpeed(68)
                                                                        .setSpeedUnits("%")
                                                                        .build())
                                                        .setFreqInfo(
                                                                new FreqInfo.Builder()
                                                                        .setFreq(1683)
                                                                        .setMemFreq(4004)
                                                                        .build())
                                                        .build())
                                        .addGpu(
                                                new Gpu.Builder()
                                                        .setName("EVGA GTX 1070 Ti")
                                                        .setTemp(64)
                                                        .setIndex(4)
                                                        .setBus(6)
                                                        .setFans(
                                                                new FanInfo.Builder()
                                                                        .setCount(1)
                                                                        .addSpeed(69)
                                                                        .setSpeedUnits("%")
                                                                        .build())
                                                        .setFreqInfo(
                                                                new FreqInfo.Builder()
                                                                        .setFreq(1683)
                                                                        .setMemFreq(4004)
                                                                        .build())
                                                        .build())
                                        .addGpu(
                                                new Gpu.Builder()
                                                        .setName("EVGA GTX 1070 Ti")
                                                        .setTemp(56)
                                                        .setIndex(5)
                                                        .setBus(9)
                                                        .setFans(
                                                                new FanInfo.Builder()
                                                                        .setCount(1)
                                                                        .addSpeed(69)
                                                                        .setSpeedUnits("%")
                                                                        .build())
                                                        .setFreqInfo(
                                                                new FreqInfo.Builder()
                                                                        .setFreq(1683)
                                                                        .setMemFreq(4004)
                                                                        .build())
                                                        .build())
                                        .addGpu(
                                                new Gpu.Builder()
                                                        .setName("EVGA GTX 1070 Ti")
                                                        .setTemp(58)
                                                        .setIndex(6)
                                                        .setBus(10)
                                                        .setFans(
                                                                new FanInfo.Builder()
                                                                        .setCount(1)
                                                                        .addSpeed(71)
                                                                        .setSpeedUnits("%")
                                                                        .build())
                                                        .setFreqInfo(
                                                                new FreqInfo.Builder()
                                                                        .setFreq(1683)
                                                                        .setMemFreq(4004)
                                                                        .build())
                                                        .build())
                                        .addGpu(
                                                new Gpu.Builder()
                                                        .setName("EVGA GTX 1070 Ti")
                                                        .setTemp(38)
                                                        .setIndex(7)
                                                        .setBus(11)
                                                        .setFans(
                                                                new FanInfo.Builder()
                                                                        .setCount(1)
                                                                        .addSpeed(39)
                                                                        .setSpeedUnits("%")
                                                                        .build())
                                                        .setFreqInfo(
                                                                new FreqInfo.Builder()
                                                                        .setFreq(1683)
                                                                        .setMemFreq(4004)
                                                                        .build())
                                                        .build())
                                        .build())
                        .build());
    }
}