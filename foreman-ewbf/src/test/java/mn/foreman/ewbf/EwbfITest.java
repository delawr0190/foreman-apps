package mn.foreman.ewbf;

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

/** Runs an integration tests using {@link Ewbf} against a fake API. */
public class EwbfITest
        extends AbstractApiITest {

    /** Constructor. */
    public EwbfITest() {
        super(
                new Ewbf(
                        "ewbf",
                        "127.0.0.1",
                        42000),
                new FakeHttpMinerServer(
                        42000,
                        ImmutableMap.of(
                                "/getstat",
                                new HttpHandler(
                                        "",
                                        "{\n" +
                                                "  \"method\": \"getstat\",\n" +
                                                "  \"error\": null,\n" +
                                                "  \"start_time\": 1530826989,\n" +
                                                "  \"current_server\": \"zec-eu1.nanopool.org:6666\",\n" +
                                                "  \"available_servers\": 1,\n" +
                                                "  \"server_status\": 2,\n" +
                                                "  \"result\": [\n" +
                                                "    {\n" +
                                                "      \"gpuid\": 0,\n" +
                                                "      \"cudaid\": 0,\n" +
                                                "      \"busid\": \"0000:01:00.0\",\n" +
                                                "      \"name\": \"GeForce GTX 1070 Ti\",\n" +
                                                "      \"gpu_status\": 2,\n" +
                                                "      \"solver\": 0,\n" +
                                                "      \"temperature\": 71,\n" +
                                                "      \"gpu_power_usage\": 188,\n" +
                                                "      \"speed_sps\": 508,\n" +
                                                "      \"accepted_shares\": 1,\n" +
                                                "      \"rejected_shares\": 0,\n" +
                                                "      \"start_time\": 1530826991\n" +
                                                "    },\n" +
                                                "    {\n" +
                                                "      \"gpuid\": 1,\n" +
                                                "      \"cudaid\": 1,\n" +
                                                "      \"busid\": \"0000:02:00.0\",\n" +
                                                "      \"name\": \"GeForce GTX 1070 Ti\",\n" +
                                                "      \"gpu_status\": 2,\n" +
                                                "      \"solver\": 0,\n" +
                                                "      \"temperature\": 67,\n" +
                                                "      \"gpu_power_usage\": 181,\n" +
                                                "      \"speed_sps\": 503,\n" +
                                                "      \"accepted_shares\": 0,\n" +
                                                "      \"rejected_shares\": 0,\n" +
                                                "      \"start_time\": 1530826991\n" +
                                                "    },\n" +
                                                "    {\n" +
                                                "      \"gpuid\": 2,\n" +
                                                "      \"cudaid\": 2,\n" +
                                                "      \"busid\": \"0000:03:00.0\",\n" +
                                                "      \"name\": \"GeForce GTX 1070 Ti\",\n" +
                                                "      \"gpu_status\": 2,\n" +
                                                "      \"solver\": 0,\n" +
                                                "      \"temperature\": 68,\n" +
                                                "      \"gpu_power_usage\": 174,\n" +
                                                "      \"speed_sps\": 498,\n" +
                                                "      \"accepted_shares\": 0,\n" +
                                                "      \"rejected_shares\": 0,\n" +
                                                "      \"start_time\": 1530826991\n" +
                                                "    },\n" +
                                                "    {\n" +
                                                "      \"gpuid\": 3,\n" +
                                                "      \"cudaid\": 3,\n" +
                                                "      \"busid\": \"0000:04:00.0\",\n" +
                                                "      \"name\": \"GeForce GTX 1070 Ti\",\n" +
                                                "      \"gpu_status\": 2,\n" +
                                                "      \"solver\": 0,\n" +
                                                "      \"temperature\": 68,\n" +
                                                "      \"gpu_power_usage\": 181,\n" +
                                                "      \"speed_sps\": 504,\n" +
                                                "      \"accepted_shares\": 0,\n" +
                                                "      \"rejected_shares\": 0,\n" +
                                                "      \"start_time\": 1530826991\n" +
                                                "    },\n" +
                                                "    {\n" +
                                                "      \"gpuid\": 4,\n" +
                                                "      \"cudaid\": 4,\n" +
                                                "      \"busid\": \"0000:06:00.0\",\n" +
                                                "      \"name\": \"GeForce GTX 1070 Ti\",\n" +
                                                "      \"gpu_status\": 2,\n" +
                                                "      \"solver\": 0,\n" +
                                                "      \"temperature\": 69,\n" +
                                                "      \"gpu_power_usage\": 186,\n" +
                                                "      \"speed_sps\": 510,\n" +
                                                "      \"accepted_shares\": 2,\n" +
                                                "      \"rejected_shares\": 0,\n" +
                                                "      \"start_time\": 1530826991\n" +
                                                "    },\n" +
                                                "    {\n" +
                                                "      \"gpuid\": 5,\n" +
                                                "      \"cudaid\": 5,\n" +
                                                "      \"busid\": \"0000:09:00.0\",\n" +
                                                "      \"name\": \"GeForce GTX 1070 Ti\",\n" +
                                                "      \"gpu_status\": 2,\n" +
                                                "      \"solver\": 0,\n" +
                                                "      \"temperature\": 69,\n" +
                                                "      \"gpu_power_usage\": 186,\n" +
                                                "      \"speed_sps\": 511,\n" +
                                                "      \"accepted_shares\": 1,\n" +
                                                "      \"rejected_shares\": 0,\n" +
                                                "      \"start_time\": 1530826991\n" +
                                                "    },\n" +
                                                "    {\n" +
                                                "      \"gpuid\": 6,\n" +
                                                "      \"cudaid\": 6,\n" +
                                                "      \"busid\": \"0000:0A:00.0\",\n" +
                                                "      \"name\": \"GeForce GTX 1070 Ti\",\n" +
                                                "      \"gpu_status\": 2,\n" +
                                                "      \"solver\": 0,\n" +
                                                "      \"temperature\": 69,\n" +
                                                "      \"gpu_power_usage\": 185,\n" +
                                                "      \"speed_sps\": 504,\n" +
                                                "      \"accepted_shares\": 0,\n" +
                                                "      \"rejected_shares\": 0,\n" +
                                                "      \"start_time\": 1530826991\n" +
                                                "    },\n" +
                                                "    {\n" +
                                                "      \"gpuid\": 7,\n" +
                                                "      \"cudaid\": 7,\n" +
                                                "      \"busid\": \"0000:0B:00.0\",\n" +
                                                "      \"name\": \"GeForce GTX 1070 Ti\",\n" +
                                                "      \"gpu_status\": 2,\n" +
                                                "      \"solver\": 0,\n" +
                                                "      \"temperature\": 66,\n" +
                                                "      \"gpu_power_usage\": 168,\n" +
                                                "      \"speed_sps\": 436,\n" +
                                                "      \"accepted_shares\": 1,\n" +
                                                "      \"rejected_shares\": 0,\n" +
                                                "      \"start_time\": 1530826992\n" +
                                                "    }\n" +
                                                "  ]\n" +
                                                "}\n"))),
                new MinerStats.Builder()
                        .setName("ewbf")
                        .setApiIp("127.0.0.1")
                        .setApiPort(42000)
                        .addPool(
                                new Pool.Builder()
                                        .setName("zec-eu1.nanopool.org:6666")
                                        .setPriority(0)
                                        .setStatus(true, true)
                                        .setCounts(5, 0, 0)
                                        .build())
                        .addRig(
                                new Rig.Builder()
                                        .setName("ewbf")
                                        .setHashRate(new BigDecimal(3974))
                                        .addGpu(
                                                new Gpu.Builder()
                                                        .setIndex(0)
                                                        .setBus(1)
                                                        .setName("GeForce GTX 1070 Ti")
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
                                        .addGpu(
                                                new Gpu.Builder()
                                                        .setIndex(1)
                                                        .setBus(2)
                                                        .setName("GeForce GTX 1070 Ti")
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
                                                        .setIndex(2)
                                                        .setBus(3)
                                                        .setName("GeForce GTX 1070 Ti")
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
                                                        .setIndex(3)
                                                        .setBus(4)
                                                        .setName("GeForce GTX 1070 Ti")
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
                                                        .setIndex(4)
                                                        .setBus(6)
                                                        .setName("GeForce GTX 1070 Ti")
                                                        .setTemp(69)
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
                                                        .setName("GeForce GTX 1070 Ti")
                                                        .setTemp(69)
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
                                                        .setBus(10)
                                                        .setName("GeForce GTX 1070 Ti")
                                                        .setTemp(69)
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
                                                        .setBus(11)
                                                        .setName("GeForce GTX 1070 Ti")
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
                                        .build())
                        .build());
    }
}