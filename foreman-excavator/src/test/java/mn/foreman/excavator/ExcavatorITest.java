package mn.foreman.excavator;

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

/** Runs an integration tests using {@link Excavator} against a fake API. */
public class ExcavatorITest
        extends AbstractApiITest {

    /** Constructor. */
    public ExcavatorITest() {
        super(
                new Excavator(
                        "excavator",
                        "127.0.0.1",
                        3456),
                new FakeRpcMinerServer(
                        3456,
                        ImmutableMap.of(
                                "{\"id\":1,\"method\":\"subscribe.info\",\"params\":[]}",
                                new RpcHandler(
                                        "{  \n" +
                                                "  \"id\":1,\n" +
                                                "  \"address\":\"nhmp.eu.nicehash.com:3200\",\n" +
                                                "  \"login\":\"34HKWdzLxWBduUfJE9JxaFhoXnfC6gmePG.test2:x\",\n" +
                                                "  \"connected\":true,\n" +
                                                "  \"server_status\":\"Subscribed\"\n" +
                                                "}\n"),
                                "{\"id\":1,\"method\":\"devices.get\",\"params\":[]}",
                                new RpcHandler(
                                        "{\n" +
                                                "   \"devices\":[\n" +
                                                "      {\n" +
                                                "          \"device_id\":0,\n" +
                                                "          \"name\":\"GeForce GTX 1080 Ti\",\n" +
                                                "          \"gpgpu_type\":1,\n" +
                                                "          \"subvendor\":\"10de\",\n" +
                                                "          \"details\":{\n" +
                                                "             \"cuda_id\":0,\n" +
                                                "             \"sm_major\":6,\n" +
                                                "             \"sm_minor\":1,\n" +
                                                "             \"bus_id\":5\n" +
                                                "          },\n" +
                                                "          \"uuid\":\"GPU-8f6552ba-76e8-4e86-c2bb-53b69fb685ef\",\n" +
                                                "          \"gpu_temp\":28,\n" +
                                                "          \"gpu_load\":0,\n" +
                                                "          \"gpu_load_memctrl\":0,\n" +
                                                "          \"gpu_power_mode\":80.0,\n" +
                                                "          \"gpu_power_usage\":56.340999603271487,\n" +
                                                "          \"gpu_power_limit_current\":250.0,\n" +
                                                "          \"gpu_power_limit_min\":125.0,\n" +
                                                "          \"gpu_power_limit_max\":300.0,\n" +
                                                "          \"gpu_tdp_current\":100.0,\n" +
                                                "          \"gpu_clock_core_max\":1911,\n" +
                                                "          \"gpu_clock_memory\":5005,\n" +
                                                "          \"gpu_fan_speed\":23,\n" +
                                                "          \"gpu_fan_speed_rpm\":1036,\n" +
                                                "          \"gpu_memory_free\":10753101824,\n" +
                                                "          \"gpu_memory_used\":1058058240\n" +
                                                "      },\n" +
                                                "      {\n" +
                                                "          \"device_id\":1,\n" +
                                                "          \"name\":\"GeForce GTX 1080\",\n" +
                                                "          \"gpgpu_type\":1,\n" +
                                                "          \"subvendor\":\"3842\",\n" +
                                                "          \"details\":{\n" +
                                                "             \"cuda_id\":1,\n" +
                                                "             \"sm_major\":6,\n" +
                                                "             \"sm_minor\":1,\n" +
                                                "             \"bus_id\":7\n" +
                                                "          },\n" +
                                                "          \"uuid\":\"GPU-c108e737-1a9a-2302-c878-402608fd4535\",\n" +
                                                "          \"gpu_temp\":35,\n" +
                                                "          \"gpu_load\":0,\n" +
                                                "          \"gpu_load_memctrl\":0,\n" +
                                                "          \"gpu_power_mode\":-1.0,\n" +
                                                "          \"gpu_power_usage\":6.573999881744385,\n" +
                                                "          \"gpu_power_limit_current\":180.0,\n" +
                                                "          \"gpu_power_limit_min\":90.0,\n" +
                                                "          \"gpu_power_limit_max\":217.0,\n" +
                                                "          \"gpu_tdp_current\":100.0,\n" +
                                                "          \"gpu_clock_core_max\":2012,\n" +
                                                "          \"gpu_clock_memory\":4513,\n" +
                                                "          \"gpu_fan_speed\":0,\n" +
                                                "          \"gpu_fan_speed_rpm\":0,\n" +
                                                "          \"gpu_memory_free\":8471445504,\n" +
                                                "          \"gpu_memory_used\":118489088\n" +
                                                "      }\n" +
                                                "   ],\n" +
                                                "   \"id\":1,\n" +
                                                "   \"error\":null\n" +
                                                "}"),
                                "{\"id\":1,\"method\":\"algorithm.list\",\"params\":[]}",
                                new RpcHandler(
                                        "{\n" +
                                                "   \"algorithms\": [\n" +
                                                "      {\n" +
                                                "         \"algorithm_id\": 20,\n" +
                                                "         \"name\": \"daggerhashimoto\",\n" +
                                                "         \"speed\": 9198932.692307692,\n" +
                                                "         \"uptime\": 19.18597412109375,\n" +
                                                "         \"benchmark\": false,\n" +
                                                "         \"accepted_shares\": 0,\n" +
                                                "         \"rejected_shares\": 0,\n" +
                                                "         \"got_job\": true,\n" +
                                                "         \"received_jobs\": 4,\n" +
                                                "         \"current_job_difficulty\": 0.5\n" +
                                                "      },\n" +
                                                "      {\n" +
                                                "         \"algorithm_id\": 21,\n" +
                                                "         \"name\": \"decred\",\n" +
                                                "         \"speed\": 5078989989.082617,\n" +
                                                "         \"uptime\": 19.18767738342285,\n" +
                                                "         \"benchmark\": false,\n" +
                                                "         \"sent_shares\": 1,\n" +
                                                "         \"accepted_shares\": 1,\n" +
                                                "         \"rejected_shares\": 0,\n" +
                                                "         \"got_job\": true,\n" +
                                                "         \"received_jobs\": 1,\n" +
                                                "         \"current_job_difficulty\": 4\n" +
                                                "      },\n" +
                                                "      {\n" +
                                                "         \"algorithm_id\": 24,\n" +
                                                "         \"name\": \"equihash\",\n" +
                                                "         \"speed\": 441.36426519101843,\n" +
                                                "         \"uptime\": 19.18423080444336,\n" +
                                                "         \"benchmark\": false,\n" +
                                                "         \"sent_shares\": 0,\n" +
                                                "         \"accepted_shares\": 0,\n" +
                                                "         \"rejected_shares\": 0,\n" +
                                                "         \"got_job\": true,\n" +
                                                "         \"received_jobs\": 2,\n" +
                                                "         \"current_job_difficulty\": 1024\n" +
                                                "      }\n" +
                                                "   ],\n" +
                                                "   \"id\": 1,\n" +
                                                "   \"error\": null\n" +
                                                "}"))),
                new MinerStats.Builder()
                        .setName("excavator")
                        .setApiIp("127.0.0.1")
                        .setApiPort(3456)
                        .addPool(
                                new Pool.Builder()
                                        .setName("nhmp.eu.nicehash.com:3200")
                                        .setStatus(true, true)
                                        .setPriority(0)
                                        .setCounts(1, 0, 0)
                                        .build())
                        .addRig(
                                new Rig.Builder()
                                        .setName("excavator")
                                        .setHashRate(new BigDecimal("5088189363.13918988301843"))
                                        .addGpu(
                                                new Gpu.Builder()
                                                        .setBus(5)
                                                        .setFans(
                                                                new FanInfo.Builder()
                                                                        .setCount(1)
                                                                        .addSpeed(23)
                                                                        .setSpeedUnits("%")
                                                                        .build())
                                                        .setFreqInfo(
                                                                new FreqInfo.Builder()
                                                                        .setFreq(1911)
                                                                        .setMemFreq(5005)
                                                                        .build())
                                                        .setIndex(0)
                                                        .setName("GeForce GTX 1080 Ti")
                                                        .setTemp(28)
                                                        .build())
                                        .addGpu(
                                                new Gpu.Builder()
                                                        .setBus(7)
                                                        .setFans(
                                                                new FanInfo.Builder()
                                                                        .setCount(1)
                                                                        .setSpeedUnits("%")
                                                                        .build())
                                                        .setFreqInfo(
                                                                new FreqInfo.Builder()
                                                                        .setFreq(2012)
                                                                        .setMemFreq(4513)
                                                                        .build())
                                                        .setIndex(1)
                                                        .setName("GeForce GTX 1080")
                                                        .setTemp(35)
                                                        .build())
                                        .build())
                        .build());
    }
}