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
public class RhminerITest
        extends AbstractApiITest {

    /** Constructor. */
    public RhminerITest() {
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
                                                "      \"threads\": 50,\n" +
                                                "      \"speed\": 45,\n" +
                                                "      \"accepted\": 5,\n" +
                                                "      \"rejected\": 1,\n" +
                                                "      \"temp\": 0,\n" +
                                                "      \"fan\": 0\n" +
                                                "    },\n" +
                                                "    {\n" +
                                                "      \"name\": \"GPU2\",\n" +
                                                "      \"threads\": 262,\n" +
                                                "      \"speed\": 114,\n" +
                                                "      \"accepted\": 1,\n" +
                                                "      \"rejected\": 0,\n" +
                                                "      \"temp\": 0,\n" +
                                                "      \"fan\": 0\n" +
                                                "    },\n" +
                                                "    {\n" +
                                                "      \"name\": \"CPU\",\n" +
                                                "      \"threads\": 2,\n" +
                                                "      \"speed\": 266,\n" +
                                                "      \"accepted\": 3,\n" +
                                                "      \"rejected\": 0,\n" +
                                                "      \"temp\": 0,\n" +
                                                "      \"fan\": 0\n" +
                                                "    }\n" +
                                                "  ],\n" +
                                                "  \"speed\": 380,\n" +
                                                "  \"accepted\": 9,\n" +
                                                "  \"rejected\": 1,\n" +
                                                "  \"failed\": 0,\n" +
                                                "  \"uptime\": 91,\n" +
                                                "  \"extrapayload\": \"\",\n" +
                                                "  \"stratum.server\": \"pasc.f2pool.com:15555\",\n" +
                                                "  \"stratum.user\": \"\",\n" +
                                                "  \"diff\": 4.9e-7\n" +
                                                "}"))),
                new MinerStats.Builder()
                        .setApiIp("127.0.0.1")
                        .setApiPort(7111)
                        .addPool(
                                new Pool.Builder()
                                        .setName("pasc.f2pool.com:15555")
                                        .setStatus(true, true)
                                        .setPriority(0)
                                        .setCounts(9, 1, 0)
                                        .build())
                        .addRig(
                                new Rig.Builder()
                                        .setHashRate(new BigDecimal("159"))
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
                                        .addGpu(
                                                new Gpu.Builder()
                                                        .setName("GPU2")
                                                        .setTemp(0)
                                                        .setIndex(2)
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