package mn.foreman.multiminer;

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

/** Runs an integration tests using {@link Multiminer} against a fake API. */
public class MultiminerITest
        extends AbstractApiITest {

    /** Constructor. */
    public MultiminerITest() {
        super(
                new Multiminer(
                        "127.0.0.1",
                        4048),
                new FakeRpcMinerServer(
                        4048,
                        ImmutableMap.of(
                                "summary|",
                                new RpcHandler(
                                        "NAME=multiminer;VER=1.0.0.0;API=1.0;ALGO=argon2d250;CPUS=2;URL=us.gos.cx:4534;HS=40263.47;KHS=40.26;ACC=21;REJ=0;SOL=0;ACCMN=8.571;DIFF=1.167453;TEMP=0.0;FAN=0;FREQ=0;UPTIME=147;TS=1547425991|"))),
                new MinerStats.Builder()
                        .setApiIp("127.0.0.1")
                        .setApiPort(4048)
                        .addPool(
                                new Pool.Builder()
                                        .setName("us.gos.cx:4534")
                                        .setStatus(true, true)
                                        .setPriority(0)
                                        .setCounts(21, 0, 0)
                                        .build())
                        .addRig(
                                new Rig.Builder()
                                        .setHashRate(new BigDecimal("40263.47"))
                                        .addGpu(
                                                new Gpu.Builder()
                                                        .setName("Mining Device 0")
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
                                                        .setName("Mining Device 1")
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
                                        .build())
                        .build());
    }
}