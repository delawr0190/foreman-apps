package mn.foreman.autominer;

import mn.foreman.model.miners.FanInfo;
import mn.foreman.model.miners.MinerStats;
import mn.foreman.model.miners.Pool;
import mn.foreman.model.miners.rig.FreqInfo;
import mn.foreman.model.miners.rig.Gpu;
import mn.foreman.model.miners.rig.Rig;
import mn.foreman.util.AbstractApiITest;
import mn.foreman.util.http.FakeHttpMinerServer;
import mn.foreman.util.http.HttpHandler;
import mn.foreman.util.rpc.FakeRpcMinerServer;
import mn.foreman.util.rpc.RpcHandler;

import com.google.common.collect.ImmutableMap;

import java.math.BigDecimal;
import java.util.Arrays;

/** Runs an integration tests using {@link AutoMiner} against a fake API. */
public class AutoMinerCcminerITest
        extends AbstractApiITest {

    /** Constructor. */
    public AutoMinerCcminerITest() {
        super(
                new AutoMinerFactory().create(
                        ImmutableMap.of(
                                "apiIp",
                                "127.0.0.1",
                                "apiPort",
                                "1234")),
                Arrays.asList(
                        new FakeHttpMinerServer(
                                1234,
                                ImmutableMap.of(
                                        "/summary",
                                        new HttpHandler(
                                                "",
                                                "{\n" +
                                                        "  \"StartTime\": \"2019-03-05T00:01:24.2177796Z\"," +
                                                        "  \"GPUMiner\": \"ccminer\",\n" +
                                                        "  \"GPUAlgo\": \"x16rt\",\n" +
                                                        "  \"GPUApiPort\": 4068,\n" +
                                                        "  \"CPUMiner\": \"NONE\",\n" +
                                                        "  \"CPUAlgo\": \"NONE\",\n" +
                                                        "  \"CPUApiPort\": 0,\n" +
                                                        "  \"RigGroupGPU\": 17,\n" +
                                                        "  \"RigGroupCPU\": 18,\n" +
                                                        "  \"GPUList\": [\n" +
                                                        "    {\n" +
                                                        "      \"Id\": 0,\n" +
                                                        "      \"Utilization\": 99,\n" +
                                                        "      \"Temperature\": 70,\n" +
                                                        "      \"FanSpeed\": \" 47\",\n" +
                                                        "      \"MemoryClock\": 5005,\n" +
                                                        "      \"CoreClock\": 1733\n" +
                                                        "    }\n" +
                                                        "  ]\n" +
                                                        "}"))),
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
                                                "GPU=0;BUS=1;CARD=EVGA GTX 1070 Ti;SM=610;MEM=8192;TEMP=70;FAN=70;RPM=1759;FREQ=1683000;MEMFREQ=4004000;PST=P2;POWER=0;VID=3842;PID=5670;NVML=-1;NVAPI=0;SN=;BIOS=86.04.85.00.70|")))),
                new MinerStats.Builder()
                        .setApiIp("127.0.0.1")
                        .setApiPort(1234)
                        .addPool(
                                new Pool.Builder()
                                        .setName("ravenminer.com:3636")
                                        .setPriority(0)
                                        .setStatus(true, true)
                                        .setCounts(3, 0, 0)
                                        .build())
                        .addRig(
                                new Rig.Builder()
                                        .setHashRate(new BigDecimal("64879730.00"))
                                        .addGpu(
                                                new Gpu.Builder()
                                                        .setIndex(0)
                                                        .setBus(1)
                                                        .setName("EVGA GTX 1070 Ti")
                                                        .setTemp(70)
                                                        .setFans(
                                                                new FanInfo.Builder()
                                                                        .setCount(1)
                                                                        .addSpeed(47)
                                                                        .setSpeedUnits("%")
                                                                        .build())
                                                        .setFreqInfo(
                                                                new FreqInfo.Builder()
                                                                        .setFreq(1733)
                                                                        .setMemFreq(5005)
                                                                        .build())
                                                        .build())
                                        .addAttribute(
                                                "mrr_group",
                                                "17")
                                        .addAttribute(
                                                "start_time",
                                                "2019-03-05T00:01:24.217779600Z[UTC]")
                                        .addAttribute(
                                                "gpu_algo",
                                                "x16rt")
                                        .build())
                        .build());
    }
}