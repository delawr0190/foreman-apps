package mn.foreman.gminer;

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

/** Runs an integration tests using {@link Gminer} against a fake API. */
public class GminerITest
        extends AbstractApiITest {

    /** Constructor. */
    public GminerITest() {
        super(
                new GminerFactory().create(
                        ImmutableMap.of(
                                "apiIp",
                                "127.0.0.1",
                                "apiPort",
                                "10555")),
                new FakeHttpMinerServer(
                        10555,
                        ImmutableMap.of(
                                "/stat",
                                new HttpHandler(
                                        "",
                                        "\n" +
                                                "\n" +
                                                "{\n" +
                                                "  \"uptime\": 37,\n" +
                                                "  \"server\": \"stratum:\\/\\/eu.btgpool.pro:1445\",\n" +
                                                "  \"user\": \"GZdx44gPVFX7GfeWXA3kyiuXecym3CWGHi.rig0\",\n" +
                                                "  \"devices\": [\n" +
                                                "    {\n" +
                                                "      \"gpu_id\": 0,\n" +
                                                "      \"cuda_id\": 0,\n" +
                                                "      \"bus_id\": \"0000:01:00.0\",\n" +
                                                "      \"name\": \"EVGA GeForce GTX 1070 Ti 8GB\",\n" +
                                                "      \"speed\": 61,\n" +
                                                "      \"accepted_shares\": 0,\n" +
                                                "      \"rejected_shares\": 0,\n" +
                                                "      \"temperature\": 52,\n" +
                                                "      \"temperature_limit\": 90,\n" +
                                                "      \"power_usage\": 202\n" +
                                                "    },\n" +
                                                "    {\n" +
                                                "      \"gpu_id\": 1,\n" +
                                                "      \"cuda_id\": 1,\n" +
                                                "      \"bus_id\": \"0000:02:00.0\",\n" +
                                                "      \"name\": \"EVGA GeForce GTX 1070 Ti 8GB\",\n" +
                                                "      \"speed\": 65,\n" +
                                                "      \"accepted_shares\": 1,\n" +
                                                "      \"rejected_shares\": 0,\n" +
                                                "      \"temperature\": 48,\n" +
                                                "      \"temperature_limit\": 90,\n" +
                                                "      \"power_usage\": 202\n" +
                                                "    },\n" +
                                                "    {\n" +
                                                "      \"gpu_id\": 2,\n" +
                                                "      \"cuda_id\": 2,\n" +
                                                "      \"bus_id\": \"0000:03:00.0\",\n" +
                                                "      \"name\": \"EVGA GeForce GTX 1070 Ti 8GB\",\n" +
                                                "      \"speed\": 62,\n" +
                                                "      \"accepted_shares\": 2,\n" +
                                                "      \"rejected_shares\": 0,\n" +
                                                "      \"temperature\": 52,\n" +
                                                "      \"temperature_limit\": 90,\n" +
                                                "      \"power_usage\": 201\n" +
                                                "    },\n" +
                                                "    {\n" +
                                                "      \"gpu_id\": 3,\n" +
                                                "      \"cuda_id\": 3,\n" +
                                                "      \"bus_id\": \"0000:04:00.0\",\n" +
                                                "      \"name\": \"EVGA GeForce GTX 1070 Ti 8GB\",\n" +
                                                "      \"speed\": 65,\n" +
                                                "      \"accepted_shares\": 2,\n" +
                                                "      \"rejected_shares\": 0,\n" +
                                                "      \"temperature\": 51,\n" +
                                                "      \"temperature_limit\": 90,\n" +
                                                "      \"power_usage\": 201\n" +
                                                "    }\n" +
                                                "  ]\n" +
                                                "}\n" +
                                                "\n"))),
                new MinerStats.Builder()
                        .setApiIp("127.0.0.1")
                        .setApiPort(10555)
                        .addPool(
                                new Pool.Builder()
                                        .setName("eu.btgpool.pro:1445")
                                        .setPriority(0)
                                        .setStatus(true, true)
                                        .setCounts(5, 0, 0)
                                        .build())
                        .addRig(
                                new Rig.Builder()
                                        .setHashRate(new BigDecimal(253))
                                        .addGpu(
                                                new Gpu.Builder()
                                                        .setIndex(0)
                                                        .setBus(1)
                                                        .setName("EVGA GeForce GTX 1070 Ti 8GB")
                                                        .setTemp(52)
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
                                                        .setBus(2)
                                                        .setName("EVGA GeForce GTX 1070 Ti 8GB")
                                                        .setTemp(48)
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
                                                        .setIndex(2)
                                                        .setBus(3)
                                                        .setName("EVGA GeForce GTX 1070 Ti 8GB")
                                                        .setTemp(52)
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
                                                        .setIndex(3)
                                                        .setBus(4)
                                                        .setName("EVGA GeForce GTX 1070 Ti 8GB")
                                                        .setTemp(51)
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