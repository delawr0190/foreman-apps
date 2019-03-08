package mn.foreman.dstm;

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

/** Runs an integration tests using {@link Dstm} against a fake API. */
public class MiniZITest
        extends AbstractApiITest {

    /** Constructor. */
    public MiniZITest() {
        super(
                new DstmFactory().create(
                        ImmutableMap.of(
                                "apiIp",
                                "127.0.0.1",
                                "apiPort",
                                "20000")),
                new FakeRpcMinerServer(
                        20000,
                        ImmutableMap.of(
                                "{\"id\":1,\"method\":\"getstat\"}\n",
                                new RpcHandler(
                                        "{\n" +
                                                "  \"id\": 0,\n" +
                                                "  \"method\": \"getstat\",\n" +
                                                "  \"error\": null,\n" +
                                                "  \"start_time\": 1552015121,\n" +
                                                "  \"current_server\": \"fee.server:0\",\n" +
                                                "  \"server_latency\": 159,\n" +
                                                "  \"available_servers\": 0,\n" +
                                                "  \"server_status\": 1,\n" +
                                                "  \"result\": [\n" +
                                                "    {\n" +
                                                "      \"gpuid\": 0,\n" +
                                                "      \"cudaid\": 0,\n" +
                                                "      \"busid\": \"busid\",\n" +
                                                "      \"name\": \"GeForce GTX 1070 Ti\",\n" +
                                                "      \"gpu_status\": 2,\n" +
                                                "      \"solver\": -1,\n" +
                                                "      \"temperature\": 39,\n" +
                                                "      \"gpu_fan_speed\": 32,\n" +
                                                "      \"gpu_power_usage\": 49.7,\n" +
                                                "      \"gpu_clock_core_max\": 1607,\n" +
                                                "      \"gpu_clock_memory\": 4100,\n" +
                                                "      \"speed_sps\": 56.9,\n" +
                                                "      \"accepted_shares\": 4,\n" +
                                                "      \"rejected_shares\": 0,\n" +
                                                "      \"start_time\": 1552015121\n" +
                                                "    },\n" +
                                                "    {\n" +
                                                "      \"gpuid\": 1,\n" +
                                                "      \"cudaid\": 1,\n" +
                                                "      \"busid\": \"busid\",\n" +
                                                "      \"name\": \"GeForce GTX 1070 Ti\",\n" +
                                                "      \"gpu_status\": 2,\n" +
                                                "      \"solver\": -1,\n" +
                                                "      \"temperature\": 38,\n" +
                                                "      \"gpu_fan_speed\": 31,\n" +
                                                "      \"gpu_power_usage\": 50.1,\n" +
                                                "      \"gpu_clock_core_max\": 1607,\n" +
                                                "      \"gpu_clock_memory\": 4100,\n" +
                                                "      \"speed_sps\": 56.1,\n" +
                                                "      \"accepted_shares\": 4,\n" +
                                                "      \"rejected_shares\": 0,\n" +
                                                "      \"start_time\": 1552015121\n" +
                                                "    },\n" +
                                                "    {\n" +
                                                "      \"gpuid\": 2,\n" +
                                                "      \"cudaid\": 2,\n" +
                                                "      \"busid\": \"busid\",\n" +
                                                "      \"name\": \"GeForce GTX 1070 Ti\",\n" +
                                                "      \"gpu_status\": 2,\n" +
                                                "      \"solver\": -1,\n" +
                                                "      \"temperature\": 38,\n" +
                                                "      \"gpu_fan_speed\": 31,\n" +
                                                "      \"gpu_power_usage\": 52.1,\n" +
                                                "      \"gpu_clock_core_max\": 1607,\n" +
                                                "      \"gpu_clock_memory\": 4100,\n" +
                                                "      \"speed_sps\": 58.1,\n" +
                                                "      \"accepted_shares\": 4,\n" +
                                                "      \"rejected_shares\": 0,\n" +
                                                "      \"start_time\": 1552015121\n" +
                                                "    },\n" +
                                                "    {\n" +
                                                "      \"gpuid\": 3,\n" +
                                                "      \"cudaid\": 3,\n" +
                                                "      \"busid\": \"busid\",\n" +
                                                "      \"name\": \"GeForce GTX 1070 Ti\",\n" +
                                                "      \"gpu_status\": 2,\n" +
                                                "      \"solver\": -1,\n" +
                                                "      \"temperature\": 37,\n" +
                                                "      \"gpu_fan_speed\": 27,\n" +
                                                "      \"gpu_power_usage\": 39.6,\n" +
                                                "      \"gpu_clock_core_max\": 1607,\n" +
                                                "      \"gpu_clock_memory\": 4100,\n" +
                                                "      \"speed_sps\": 55.3,\n" +
                                                "      \"accepted_shares\": 0,\n" +
                                                "      \"rejected_shares\": 0,\n" +
                                                "      \"start_time\": 1552015121\n" +
                                                "    },\n" +
                                                "    {\n" +
                                                "      \"gpuid\": 4,\n" +
                                                "      \"cudaid\": 4,\n" +
                                                "      \"busid\": \"busid\",\n" +
                                                "      \"name\": \"GeForce GTX 1070 Ti\",\n" +
                                                "      \"gpu_status\": 2,\n" +
                                                "      \"solver\": -1,\n" +
                                                "      \"temperature\": 40,\n" +
                                                "      \"gpu_fan_speed\": 32,\n" +
                                                "      \"gpu_power_usage\": 50.6,\n" +
                                                "      \"gpu_clock_core_max\": 1607,\n" +
                                                "      \"gpu_clock_memory\": 4100,\n" +
                                                "      \"speed_sps\": 57,\n" +
                                                "      \"accepted_shares\": 0,\n" +
                                                "      \"rejected_shares\": 0,\n" +
                                                "      \"start_time\": 1552015121\n" +
                                                "    },\n" +
                                                "    {\n" +
                                                "      \"gpuid\": 5,\n" +
                                                "      \"cudaid\": 5,\n" +
                                                "      \"busid\": \"busid\",\n" +
                                                "      \"name\": \"GeForce GTX 1070 Ti\",\n" +
                                                "      \"gpu_status\": 2,\n" +
                                                "      \"solver\": -1,\n" +
                                                "      \"temperature\": 36,\n" +
                                                "      \"gpu_fan_speed\": 27,\n" +
                                                "      \"gpu_power_usage\": 35.1,\n" +
                                                "      \"gpu_clock_core_max\": 1607,\n" +
                                                "      \"gpu_clock_memory\": 4100,\n" +
                                                "      \"speed_sps\": 0,\n" +
                                                "      \"accepted_shares\": 0,\n" +
                                                "      \"rejected_shares\": 0,\n" +
                                                "      \"start_time\": 1552015121\n" +
                                                "    },\n" +
                                                "    {\n" +
                                                "      \"gpuid\": 6,\n" +
                                                "      \"cudaid\": 6,\n" +
                                                "      \"busid\": \"busid\",\n" +
                                                "      \"name\": \"GeForce GTX 1070 Ti\",\n" +
                                                "      \"gpu_status\": 2,\n" +
                                                "      \"solver\": -1,\n" +
                                                "      \"temperature\": 36,\n" +
                                                "      \"gpu_fan_speed\": 27,\n" +
                                                "      \"gpu_power_usage\": 35.4,\n" +
                                                "      \"gpu_clock_core_max\": 1607,\n" +
                                                "      \"gpu_clock_memory\": 4100,\n" +
                                                "      \"speed_sps\": 0,\n" +
                                                "      \"accepted_shares\": 0,\n" +
                                                "      \"rejected_shares\": 0,\n" +
                                                "      \"start_time\": 1552015121\n" +
                                                "    },\n" +
                                                "    {\n" +
                                                "      \"gpuid\": 7,\n" +
                                                "      \"cudaid\": 7,\n" +
                                                "      \"busid\": \"busid\",\n" +
                                                "      \"name\": \"GeForce GTX 1070 Ti\",\n" +
                                                "      \"gpu_status\": 2,\n" +
                                                "      \"solver\": -1,\n" +
                                                "      \"temperature\": 38,\n" +
                                                "      \"gpu_fan_speed\": 27,\n" +
                                                "      \"gpu_power_usage\": 37.5,\n" +
                                                "      \"gpu_clock_core_max\": 1607,\n" +
                                                "      \"gpu_clock_memory\": 4100,\n" +
                                                "      \"speed_sps\": 0,\n" +
                                                "      \"accepted_shares\": 0,\n" +
                                                "      \"rejected_shares\": 0,\n" +
                                                "      \"start_time\": 1552015121\n" +
                                                "    }\n" +
                                                "  ]\n" +
                                                "}"))),
                new MinerStats.Builder()
                        .setApiIp("127.0.0.1")
                        .setApiPort(20000)
                        .addPool(
                                new Pool.Builder()
                                        .setName("fee.server:0")
                                        .setPriority(0)
                                        .setStatus(true, true)
                                        .setCounts(12, 0, 0)
                                        .build())
                        .addRig(
                                new Rig.Builder()
                                        .setHashRate(new BigDecimal("283.3999999999999772626324556767940521240234375"))
                                        .addGpu(
                                                new Gpu.Builder()
                                                        .setName("GeForce GTX 1070 Ti")
                                                        .setIndex(0)
                                                        .setBus(0)
                                                        .setTemp(39)
                                                        .setFans(
                                                                new FanInfo.Builder()
                                                                        .setCount(1)
                                                                        .addSpeed(32)
                                                                        .setSpeedUnits("%")
                                                                        .build())
                                                        .setFreqInfo(
                                                                new FreqInfo.Builder()
                                                                        .setFreq(1607)
                                                                        .setMemFreq(4100)
                                                                        .build())
                                                        .build())
                                        .addGpu(
                                                new Gpu.Builder()
                                                        .setName("GeForce GTX 1070 Ti")
                                                        .setIndex(1)
                                                        .setBus(0)
                                                        .setTemp(38)
                                                        .setFans(
                                                                new FanInfo.Builder()
                                                                        .setCount(1)
                                                                        .addSpeed(31)
                                                                        .setSpeedUnits("%")
                                                                        .build())
                                                        .setFreqInfo(
                                                                new FreqInfo.Builder()
                                                                        .setFreq(1607)
                                                                        .setMemFreq(4100)
                                                                        .build())
                                                        .build())
                                        .addGpu(
                                                new Gpu.Builder()
                                                        .setName("GeForce GTX 1070 Ti")
                                                        .setIndex(2)
                                                        .setBus(0)
                                                        .setTemp(38)
                                                        .setFans(
                                                                new FanInfo.Builder()
                                                                        .setCount(1)
                                                                        .addSpeed(31)
                                                                        .setSpeedUnits("%")
                                                                        .build())
                                                        .setFreqInfo(
                                                                new FreqInfo.Builder()
                                                                        .setFreq(1607)
                                                                        .setMemFreq(4100)
                                                                        .build())
                                                        .build())
                                        .addGpu(
                                                new Gpu.Builder()
                                                        .setName("GeForce GTX 1070 Ti")
                                                        .setIndex(3)
                                                        .setBus(0)
                                                        .setTemp(37)
                                                        .setFans(
                                                                new FanInfo.Builder()
                                                                        .setCount(1)
                                                                        .addSpeed(27)
                                                                        .setSpeedUnits("%")
                                                                        .build())
                                                        .setFreqInfo(
                                                                new FreqInfo.Builder()
                                                                        .setFreq(1607)
                                                                        .setMemFreq(4100)
                                                                        .build())
                                                        .build())
                                        .addGpu(
                                                new Gpu.Builder()
                                                        .setName("GeForce GTX 1070 Ti")
                                                        .setIndex(4)
                                                        .setBus(0)
                                                        .setTemp(40)
                                                        .setFans(
                                                                new FanInfo.Builder()
                                                                        .setCount(1)
                                                                        .addSpeed(32)
                                                                        .setSpeedUnits("%")
                                                                        .build())
                                                        .setFreqInfo(
                                                                new FreqInfo.Builder()
                                                                        .setFreq(1607)
                                                                        .setMemFreq(4100)
                                                                        .build())
                                                        .build())
                                        .addGpu(
                                                new Gpu.Builder()
                                                        .setName("GeForce GTX 1070 Ti")
                                                        .setIndex(5)
                                                        .setBus(0)
                                                        .setTemp(36)
                                                        .setFans(
                                                                new FanInfo.Builder()
                                                                        .setCount(1)
                                                                        .addSpeed(27)
                                                                        .setSpeedUnits("%")
                                                                        .build())
                                                        .setFreqInfo(
                                                                new FreqInfo.Builder()
                                                                        .setFreq(1607)
                                                                        .setMemFreq(4100)
                                                                        .build())
                                                        .build())
                                        .addGpu(
                                                new Gpu.Builder()
                                                        .setName("GeForce GTX 1070 Ti")
                                                        .setIndex(6)
                                                        .setBus(0)
                                                        .setTemp(36)
                                                        .setFans(
                                                                new FanInfo.Builder()
                                                                        .setCount(1)
                                                                        .addSpeed(27)
                                                                        .setSpeedUnits("%")
                                                                        .build())
                                                        .setFreqInfo(
                                                                new FreqInfo.Builder()
                                                                        .setFreq(1607)
                                                                        .setMemFreq(4100)
                                                                        .build())
                                                        .build())
                                        .addGpu(
                                                new Gpu.Builder()
                                                        .setName("GeForce GTX 1070 Ti")
                                                        .setIndex(7)
                                                        .setBus(0)
                                                        .setTemp(38)
                                                        .setFans(
                                                                new FanInfo.Builder()
                                                                        .setCount(1)
                                                                        .addSpeed(27)
                                                                        .setSpeedUnits("%")
                                                                        .build())
                                                        .setFreqInfo(
                                                                new FreqInfo.Builder()
                                                                        .setFreq(1607)
                                                                        .setMemFreq(4100)
                                                                        .build())
                                                        .build())
                                        .build())
                        .build());
    }
}