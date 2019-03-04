package mn.foreman.trex;

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

/** Runs an integration tests using {@link TrexHttp} against a fake API. */
public class TrexCcminerITest
        extends AbstractApiITest {

    /** Constructor. */
    public TrexCcminerITest() {
        super(
                new TrexFactory().create(
                        ImmutableMap.of(
                                "apiIp",
                                "127.0.0.1",
                                "apiPort",
                                "4068")),
                new FakeRpcMinerServer(
                        4068,
                        ImmutableMap.of(
                                "summary",
                                new RpcHandler(
                                        "NAME=t-rex;VER=0.9.2;API=3.1;ALGO=x16r;GPUS=1;KHS=5798.28;SOLV=0;ACC=5;REJ=0;DIFF=82589.499837;UPTIME=45;TS=1551674517|"))),
                new MinerStats.Builder()
                        .setApiIp("127.0.0.1")
                        .setApiPort(4068)
                        .addPool(
                                new Pool.Builder()
                                        .setName("no.pool.reported:1234")
                                        .setPriority(0)
                                        .setStatus(true, true)
                                        .setCounts(5, 0, 0)
                                        .build())
                        .addRig(
                                new Rig.Builder()
                                        .setHashRate(new BigDecimal("5798280.00"))
                                        .addGpu(
                                                new Gpu.Builder()
                                                        .setIndex(0)
                                                        .setBus(0)
                                                        .setName("GPU 0")
                                                        .setTemp(0)
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