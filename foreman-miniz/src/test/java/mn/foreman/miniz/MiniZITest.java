package mn.foreman.miniz;

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

/** Runs an integration tests using {@link Miniz} against a fake API. */
public class MiniZITest
        extends AbstractApiITest {

    /** Constructor. */
    public MiniZITest() {
        super(
                new MinizFactory().create(
                        ImmutableMap.of(
                                "apiIp",
                                "127.0.0.1",
                                "apiPort",
                                "20000")),
                new FakeHttpMinerServer(
                        20000,
                        ImmutableMap.of(
                                "/",
                                new HttpHandler(
                                        "",
                                        "{\n" +
                                                "  \"id\": 0,\n" +
                                                "  \"method\": \"getstat\",\n" +
                                                "  \"error\": null,\n" +
                                                "  \"start_time\": 0,\n" +
                                                "  \"current_server\": \"us-btg.2miners.com:4040\",\n" +
                                                "  \"server_latency\": 22.1,\n" +
                                                "  \"available_servers\": 1,\n" +
                                                "  \"server_status\": 1,\n" +
                                                "  \"result\": [\n" +
                                                "    {\n" +
                                                "      \"gpuid\": 0,\n" +
                                                "      \"cudaid\": 0,\n" +
                                                "      \"busid\": \"busid\",\n" +
                                                "      \"name\": \"GeForce GTX 1050 Ti\",\n" +
                                                "      \"gpu_status\": 2,\n" +
                                                "      \"solver\": -1,\n" +
                                                "      \"temperature\": 46,\n" +
                                                "      \"gpu_fan_speed\": 0,\n" +
                                                "      \"gpu_power_usage\": 0,\n" +
                                                "      \"gpu_clock_core_max\": 278,\n" +
                                                "      \"gpu_clock_memory\": 2504,\n" +
                                                "      \"speed_sps\": 0,\n" +
                                                "      \"accepted_shares\": 0,\n" +
                                                "      \"rejected_shares\": 0,\n" +
                                                "      \"start_time\": 0\n" +
                                                "    }\n" +
                                                "  ]\n" +
                                                "}"))),
                new MinerStats.Builder()
                        .setApiIp("127.0.0.1")
                        .setApiPort(20000)
                        .addPool(
                                new Pool.Builder()
                                        .setName("us-btg.2miners.com:4040")
                                        .setPriority(0)
                                        .setStatus(true, true)
                                        .setCounts(0, 0, 0)
                                        .build())
                        .addRig(
                                new Rig.Builder()
                                        .setHashRate(new BigDecimal("0"))
                                        .addGpu(
                                                new Gpu.Builder()
                                                        .setName("GeForce GTX 1050 Ti")
                                                        .setIndex(0)
                                                        .setBus(0)
                                                        .setTemp(46)
                                                        .setFans(
                                                                new FanInfo.Builder()
                                                                        .setCount(1)
                                                                        .addSpeed(0)
                                                                        .setSpeedUnits("%")
                                                                        .build())
                                                        .setFreqInfo(
                                                                new FreqInfo.Builder()
                                                                        .setFreq(278)
                                                                        .setMemFreq(2504)
                                                                        .build())
                                                        .build())
                                        .build())
                        .build());
    }
}