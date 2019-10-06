package mn.foreman.rhminer;

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

/** Runs an integration tests using {@link Rhminer} against a fake API. */
public class RhminerCpuAndGpuITest
        extends AbstractApiITest {

    /** Constructor. */
    public RhminerCpuAndGpuITest() {
        super(
                new RhminerFactory().create(
                        ImmutableMap.of(
                                "apiIp",
                                "127.0.0.1",
                                "apiPort",
                                "7111")),
                new FakeRpcMinerServer(
                        7111,
                        ImmutableMap.of(
                                " ",
                                new RpcHandler(
                                        "{\n" +
                                                "  \"infos\": [\n" +
                                                "    {\n" +
                                                "      \"name\": \"GPU0\",\n" +
                                                "      \"threads\": 100,\n" +
                                                "      \"speed\": 130,\n" +
                                                "      \"accepted\": 0,\n" +
                                                "      \"rejected\": 0,\n" +
                                                "      \"temp\": 0,\n" +
                                                "      \"fan\": 0\n" +
                                                "    },\n" +
                                                "    {\n" +
                                                "      \"name\": \"CPU\",\n" +
                                                "      \"threads\": 2,\n" +
                                                "      \"speed\": 596,\n" +
                                                "      \"accepted\": 0,\n" +
                                                "      \"rejected\": 0,\n" +
                                                "      \"temp\": 0,\n" +
                                                "      \"fan\": 0\n" +
                                                "    }\n" +
                                                "  ],\n" +
                                                "  \"speed\": 726,\n" +
                                                "  \"accepted\": 0,\n" +
                                                "  \"rejected\": 0,\n" +
                                                "  \"failed\": 0,\n" +
                                                "  \"uptime\": 10,\n" +
                                                "  \"extrapayload\": \"\",\n" +
                                                "  \"stratum.server\": \"mine.pool.pascalpool.org:3334\",\n" +
                                                "  \"stratum.user\": \"1300378-87.0.Donations\",\n" +
                                                "  \"diff\": 7.0e-6\n" +
                                                "}"))),
                new MinerStats.Builder()
                        .setApiIp("127.0.0.1")
                        .setApiPort(7111)
                        .addPool(
                                new Pool.Builder()
                                        .setName("mine.pool.pascalpool.org:3334")
                                        .setStatus(true, true)
                                        .setPriority(0)
                                        .setCounts(0, 0, 0)
                                        .build())
                        .addRig(
                                new Rig.Builder()
                                        .setHashRate(new BigDecimal("130"))
                                        .addGpu(
                                                new Gpu.Builder()
                                                        .setName("GPU0")
                                                        .setTemp(0)
                                                        .setIndex(0)
                                                        .setBus(0)
                                                        .setFans(
                                                                new FanInfo.Builder()
                                                                        .setCount(1)
                                                                        .addSpeed(0)
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