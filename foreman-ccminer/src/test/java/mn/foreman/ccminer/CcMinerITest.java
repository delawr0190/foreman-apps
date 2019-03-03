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
public class CcMinerITest
        extends AbstractApiITest {

    /** Constructor. */
    public CcMinerITest() {
        super(
                new CcMiner(
                        "127.0.0.1",
                        4068),
                new FakeRpcMinerServer(
                        4068,
                        ImmutableMap.of(
                                "summary|",
                                new RpcHandler(
                                        "NAME=nevermore;VER=0.2.3;API=1.9;ALGO=x16r;GPUS=8;KHS=1.23;SOLV=0;ACC=0;REJ=0;ACCMN=0.000;DIFF=0.000000;NETKHS=0;POOLS=2;WAIT=3;UPTIME=6;TS=1530729709|"),
                                "pool|",
                                new RpcHandler(
                                        "POOL=us.ravenminer.com:4567;ALGO=x16r;URL=stratum+tcp://us.ravenminer.com:4567;USER=RL3ssvQ3toqZFsNvPhZXawscGUpd1xGgW7;SOLV=0;ACC=25;REJ=0;STALE=0;H=284642;JOB=2c2;DIFF=16.000000;BEST=0.628730;N2SZ=4;N2=0x03000000;PING=131;DISCO=0;WAIT=7;UPTIME=31;LAST=7|"),
                                "hwinfo|",
                                new RpcHandler(
                                        "GPU=0;BUS=1;CARD=EVGA GTX 1070 Ti;SM=610;MEM=8192;TEMP=82.0;FAN=84;RPM=3113;FREQ=1683;MEMFREQ=4004;GPUF=1886;MEMF=4156;PST=P2;POWER=185125;PLIM=216000;VID=3842;PID=5670;NVML=0;NVAPI=0;SN=9111845740;BIOS=86.04.85.00.70|GPU=1;BUS=2;CARD=EVGA GTX 1070 Ti;SM=610;MEM=8192;TEMP=79.0;FAN=78;RPM=2888;FREQ=1683;MEMFREQ=4004;GPUF=0;MEMF=0;PST=P2;POWER=194306;PLIM=216000;VID=3842;PID=5670;NVML=1;NVAPI=1;SN=9119080740;BIOS=86.04.85.00.70|GPU=2;BUS=3;CARD=EVGA GTX 1070 Ti;SM=610;MEM=8192;TEMP=80.0;FAN=79;RPM=2920;FREQ=1683;MEMFREQ=4004;GPUF=0;MEMF=0;PST=P2;POWER=171981;PLIM=216000;VID=3842;PID=5670;NVML=2;NVAPI=2;SN=9115865744;BIOS=86.04.8d.00.70|GPU=3;BUS=4;CARD=EVGA GTX 1070 Ti;SM=610;MEM=8192;TEMP=80.0;FAN=79;RPM=2928;FREQ=1683;MEMFREQ=4004;GPUF=0;MEMF=0;PST=P2;POWER=192943;PLIM=216000;VID=3842;PID=5670;NVML=3;NVAPI=3;SN=9105400740;BIOS=86.04.85.00.70|GPU=4;BUS=6;CARD=EVGA GTX 1070 Ti;SM=610;MEM=8192;TEMP=80.0;FAN=80;RPM=2956;FREQ=1683;MEMFREQ=4004;GPUF=0;MEMF=0;PST=P2;POWER=176852;PLIM=216000;VID=3842;PID=5670;NVML=4;NVAPI=4;SN=9105400740;BIOS=86.04.85.00.70|GPU=5;BUS=9;CARD=EVGA GTX 1070 Ti;SM=610;MEM=8192;TEMP=79.0;FAN=80;RPM=2955;FREQ=1683;MEMFREQ=4004;GPUF=0;MEMF=0;PST=P2;POWER=181187;PLIM=216000;VID=3842;PID=5670;NVML=5;NVAPI=5;SN=9105090740;BIOS=86.04.85.00.70|GPU=6;BUS=10;CARD=EVGA GTX 1070 Ti;SM=610;MEM=8192;TEMP=82.0;FAN=82;RPM=3023;FREQ=1683;MEMFREQ=4004;GPUF=0;MEMF=0;PST=P2;POWER=198631;PLIM=216000;VID=3842;PID=5670;NVML=6;NVAPI=6;SN=9105090740;BIOS=86.04.85.00.70|GPU=7;BUS=11;CARD=EVGA GTX 1070 Ti;SM=610;MEM=8192;TEMP=76.0;FAN=75;RPM=2776;FREQ=1683;MEMFREQ=4004;GPUF=0;MEMF=0;PST=P2;POWER=140837;PLIM=216000;VID=3842;PID=5670;NVML=7;NVAPI=7;SN=9115865744;BIOS=86.04.8d.00.70|OS=windows;NVDRIVER=397.64;CPUS=8;CPUTEMP=0;CPUFREQ=0|"))),
                new MinerStats.Builder()
                        .setApiIp("127.0.0.1")
                        .setApiPort(4068)
                        .addPool(
                                new Pool.Builder()
                                        .setName("us.ravenminer.com:4567")
                                        .setStatus(true, true)
                                        .setPriority(0)
                                        .setCounts(25, 0, 0)
                                        .build())
                        .addRig(
                                new Rig.Builder()
                                        .setHashRate(new BigDecimal("1230.00"))
                                        .addGpu(
                                                new Gpu.Builder()
                                                        .setName("EVGA GTX 1070 Ti")
                                                        .setTemp(82)
                                                        .setIndex(0)
                                                        .setBus(1)
                                                        .setFans(
                                                                new FanInfo.Builder()
                                                                        .setCount(1)
                                                                        .addSpeed(84)
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
                                                        .setTemp(79)
                                                        .setIndex(1)
                                                        .setBus(2)
                                                        .setFans(
                                                                new FanInfo.Builder()
                                                                        .setCount(1)
                                                                        .addSpeed(78)
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
                                                        .setTemp(80)
                                                        .setIndex(2)
                                                        .setBus(3)
                                                        .setFans(
                                                                new FanInfo.Builder()
                                                                        .setCount(1)
                                                                        .addSpeed(79)
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
                                                        .setTemp(80)
                                                        .setIndex(3)
                                                        .setBus(4)
                                                        .setFans(
                                                                new FanInfo.Builder()
                                                                        .setCount(1)
                                                                        .addSpeed(79)
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
                                                        .setTemp(80)
                                                        .setIndex(4)
                                                        .setBus(6)
                                                        .setFans(
                                                                new FanInfo.Builder()
                                                                        .setCount(1)
                                                                        .addSpeed(80)
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
                                                        .setTemp(79)
                                                        .setIndex(5)
                                                        .setBus(9)
                                                        .setFans(
                                                                new FanInfo.Builder()
                                                                        .setCount(1)
                                                                        .addSpeed(80)
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
                                                        .setTemp(82)
                                                        .setIndex(6)
                                                        .setBus(10)
                                                        .setFans(
                                                                new FanInfo.Builder()
                                                                        .setCount(1)
                                                                        .addSpeed(82)
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
                                                        .setTemp(76)
                                                        .setIndex(7)
                                                        .setBus(11)
                                                        .setFans(
                                                                new FanInfo.Builder()
                                                                        .setCount(1)
                                                                        .addSpeed(75)
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