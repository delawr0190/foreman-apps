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
public class Gminer_1_34_ITest
        extends AbstractApiITest {

    /** Constructor. */
    public Gminer_1_34_ITest() {
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
                                        "{\n" +
                                                "  \"uptime\": 15,\n" +
                                                "  \"server\": \"stratum+ssl://beam.sparkpool.com:2222\",\n" +
                                                "  \"user\": \"100fd5cb7f85d33f74ef78f89e78cd0d5a63fc4965476eea4f77f024a57d99fa55.rig0\",\n" +
                                                "  \"algorithm\": \"Equihash 150,5 \\\"Beam-PoW\\\"\",\n" +
                                                "  \"electricity\": 0.005,\n" +
                                                "  \"total_accepted_shares\": 12,\n" +
                                                "  \"total_rejected_shares\": 0,\n" +
                                                "  \"devices\": [\n" +
                                                "    {\n" +
                                                "      \"gpu_id\": 0,\n" +
                                                "      \"cuda_id\": 0,\n" +
                                                "      \"bus_id\": \"0000:01:00.0\",\n" +
                                                "      \"name\": \"EVGA GeForce GTX 1070 Ti 8GB\",\n" +
                                                "      \"speed\": 21,\n" +
                                                "      \"temperature\": 67,\n" +
                                                "      \"temperature_limit\": 90,\n" +
                                                "      \"power_usage\": 169\n" +
                                                "    },\n" +
                                                "    {\n" +
                                                "      \"gpu_id\": 1,\n" +
                                                "      \"cuda_id\": 1,\n" +
                                                "      \"bus_id\": \"0000:02:00.0\",\n" +
                                                "      \"name\": \"EVGA GeForce GTX 1070 Ti 8GB\",\n" +
                                                "      \"speed\": 22,\n" +
                                                "      \"temperature\": 64,\n" +
                                                "      \"temperature_limit\": 90,\n" +
                                                "      \"power_usage\": 165\n" +
                                                "    },\n" +
                                                "    {\n" +
                                                "      \"gpu_id\": 2,\n" +
                                                "      \"cuda_id\": 2,\n" +
                                                "      \"bus_id\": \"0000:03:00.0\",\n" +
                                                "      \"name\": \"EVGA GeForce GTX 1070 Ti 8GB\",\n" +
                                                "      \"speed\": 23,\n" +
                                                "      \"temperature\": 67,\n" +
                                                "      \"temperature_limit\": 90,\n" +
                                                "      \"power_usage\": 175\n" +
                                                "    },\n" +
                                                "    {\n" +
                                                "      \"gpu_id\": 3,\n" +
                                                "      \"cuda_id\": 3,\n" +
                                                "      \"bus_id\": \"0000:04:00.0\",\n" +
                                                "      \"name\": \"EVGA GeForce GTX 1070 Ti 8GB\",\n" +
                                                "      \"speed\": 21,\n" +
                                                "      \"temperature\": 66,\n" +
                                                "      \"temperature_limit\": 90,\n" +
                                                "      \"power_usage\": 168\n" +
                                                "    },\n" +
                                                "    {\n" +
                                                "      \"gpu_id\": 4,\n" +
                                                "      \"cuda_id\": 4,\n" +
                                                "      \"bus_id\": \"0000:06:00.0\",\n" +
                                                "      \"name\": \"EVGA GeForce GTX 1070 Ti 8GB\",\n" +
                                                "      \"speed\": 23,\n" +
                                                "      \"temperature\": 67,\n" +
                                                "      \"temperature_limit\": 90,\n" +
                                                "      \"power_usage\": 172\n" +
                                                "    },\n" +
                                                "    {\n" +
                                                "      \"gpu_id\": 5,\n" +
                                                "      \"cuda_id\": 5,\n" +
                                                "      \"bus_id\": \"0000:09:00.0\",\n" +
                                                "      \"name\": \"EVGA GeForce GTX 1070 Ti 8GB\",\n" +
                                                "      \"speed\": 24,\n" +
                                                "      \"temperature\": 68,\n" +
                                                "      \"temperature_limit\": 90,\n" +
                                                "      \"power_usage\": 169\n" +
                                                "    },\n" +
                                                "    {\n" +
                                                "      \"gpu_id\": 6,\n" +
                                                "      \"cuda_id\": 6,\n" +
                                                "      \"bus_id\": \"0000:0A:00.0\",\n" +
                                                "      \"name\": \"GeForce GTX 1070 Ti 8GB\",\n" +
                                                "      \"speed\": 22,\n" +
                                                "      \"temperature\": 66,\n" +
                                                "      \"temperature_limit\": 90,\n" +
                                                "      \"power_usage\": 172\n" +
                                                "    },\n" +
                                                "    {\n" +
                                                "      \"gpu_id\": 7,\n" +
                                                "      \"cuda_id\": 7,\n" +
                                                "      \"bus_id\": \"0000:0B:00.0\",\n" +
                                                "      \"name\": \"GeForce GTX 1070 Ti 8GB\",\n" +
                                                "      \"speed\": 24,\n" +
                                                "      \"temperature\": 71,\n" +
                                                "      \"temperature_limit\": 90,\n" +
                                                "      \"power_usage\": 170\n" +
                                                "    }\n" +
                                                "  ]\n" +
                                                "}"))),
                new MinerStats.Builder()
                        .setApiIp("127.0.0.1")
                        .setApiPort(10555)
                        .addPool(
                                new Pool.Builder()
                                        .setName("beam.sparkpool.com:2222")
                                        .setPriority(0)
                                        .setStatus(true, true)
                                        .setCounts(12, 0, 0)
                                        .build())
                        .addRig(
                                new Rig.Builder()
                                        .setHashRate(new BigDecimal(180))
                                        .addGpu(
                                                new Gpu.Builder()
                                                        .setIndex(0)
                                                        .setBus(1)
                                                        .setName("EVGA GeForce GTX 1070 Ti 8GB")
                                                        .setTemp(67)
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
                                                        .setTemp(64)
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
                                                        .setTemp(67)
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
                                                        .setTemp(66)
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
                                                        .setIndex(4)
                                                        .setBus(6)
                                                        .setName("EVGA GeForce GTX 1070 Ti 8GB")
                                                        .setTemp(67)
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
                                                        .setIndex(5)
                                                        .setBus(9)
                                                        .setName("EVGA GeForce GTX 1070 Ti 8GB")
                                                        .setTemp(68)
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
                                                        .setIndex(6)
                                                        .setBus(0xa)
                                                        .setName("GeForce GTX 1070 Ti 8GB")
                                                        .setTemp(66)
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
                                                        .setIndex(7)
                                                        .setBus(0xb)
                                                        .setName("GeForce GTX 1070 Ti 8GB")
                                                        .setTemp(71)
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