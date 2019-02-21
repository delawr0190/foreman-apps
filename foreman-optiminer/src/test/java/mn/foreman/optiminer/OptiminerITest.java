package mn.foreman.optiminer;

import mn.foreman.model.miners.FanInfo;
import mn.foreman.model.miners.MinerStats;
import mn.foreman.model.miners.Pool;
import mn.foreman.model.miners.rig.FreqInfo;
import mn.foreman.model.miners.rig.Gpu;
import mn.foreman.model.miners.rig.Rig;
import mn.foreman.util.AbstractApiITest;
import mn.foreman.util.http.FakeHttpMinerServer;
import mn.foreman.util.http.HttpHandler;

import com.google.common.collect.ImmutableMap;

import java.math.BigDecimal;

/** Runs an integration tests using {@link Optiminer} against a fake API. */
public class OptiminerITest
        extends AbstractApiITest {

    /** Constructor. */
    public OptiminerITest() {
        super(
                new OptiminerFactory().create(
                        ImmutableMap.of(
                                "apiIp",
                                "127.0.0.1",
                                "apiPort",
                                "8080")),
                new FakeHttpMinerServer(
                        8080,
                        ImmutableMap.of(
                                "/",
                                new HttpHandler(
                                        "",
                                        "{\n" +
                                                "   \"iteration_rate\" : {\n" +
                                                "      \"GPU0\" : {\n" +
                                                "         \"5s\" : 50,\n" +
                                                "         \"60s\" : 51.083333333333336\n" +
                                                "      },\n" +
                                                "      \"GPU1\" : {\n" +
                                                "         \"5s\" : 51,\n" +
                                                "         \"60s\" : 52.083333333333336\n" +
                                                "      },\n" +
                                                "      \"Total\" : {\n" +
                                                "         \"5s\" : 101,\n" +
                                                "         \"60s\" : 103.166666666666672\n" +
                                                "      }\n" +
                                                "   },\n" +
                                                "   \"os\" : \"windows\",\n" +
                                                "   \"share\" : {\n" +
                                                "      \"accepted\" : 2,\n" +
                                                "      \"rejected\" : 0\n" +
                                                "   },\n" +
                                                "   \"solution_rate\" : {\n" +
                                                "      \"GPU0\" : {\n" +
                                                "         \"5s\" : 86.799999999999997,\n" +
                                                "         \"60s\" : 95.400000000000006\n" +
                                                "      },\n" +
                                                "      \"GPU1\" : {\n" +
                                                "         \"5s\" : 87.799999999999997,\n" +
                                                "         \"60s\" : 96.400000000000006\n" +
                                                "      },\n" +
                                                "      \"Total\" : {\n" +
                                                "         \"5s\" : 174.599999999999994,\n" +
                                                "         \"60s\" : 191.800000000000012\n" +
                                                "      }\n" +
                                                "   },\n" +
                                                "   \"stratum\" : {\n" +
                                                "      \"connected\" : true,\n" +
                                                "      \"connection_failures\" : 0,\n" +
                                                "      \"host\" : \"eu1-zcash.flypool.org\",\n" +
                                                "      \"port\" : 3443,\n" +
                                                "      \"target\" : \"0000aec33e1f671529a485cd7b900aec33e1f671529a485cd7b900aec33e1f67\"\n" +
                                                "   },\n" +
                                                "   \"uptime\" : 272,\n" +
                                                "   \"version\" : \"1.7.0\"\n" +
                                                "}"))),
                new MinerStats.Builder()
                        .setApiIp("127.0.0.1")
                        .setApiPort(8080)
                        .addPool(
                                new Pool.Builder()
                                        .setName("eu1-zcash.flypool.org:3443")
                                        .setPriority(0)
                                        .setStatus(true, true)
                                        .setCounts(2, 0, 0)
                                        .build())
                        .addRig(
                                new Rig.Builder()
                                        .setHashRate(new BigDecimal("191.800000000000012"))
                                        .addGpu(
                                                new Gpu.Builder()
                                                        .setIndex(0)
                                                        .setBus(0)
                                                        .setName("GPU0")
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
                                        .addGpu(
                                                new Gpu.Builder()
                                                        .setIndex(1)
                                                        .setBus(0)
                                                        .setName("GPU1")
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