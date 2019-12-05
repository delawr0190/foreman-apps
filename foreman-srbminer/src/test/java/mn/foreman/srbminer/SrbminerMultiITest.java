package mn.foreman.srbminer;

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

/** Runs an integration tests using {@link Srbminer} against a fake API. */
public class SrbminerMultiITest
        extends AbstractApiITest {

    /** Constructor. */
    public SrbminerMultiITest() {
        super(
                new Srbminer(
                        "127.0.0.1",
                        21555),
                new FakeHttpMinerServer(
                        21555,
                        ImmutableMap.of(
                                "/",
                                new HttpHandler(
                                        "",
                                        "{\n" +
                                                "  \"rig_name\": \"SRBMiner-Multi-Rig\",\n" +
                                                "  \"algorithm\": \"kadena\",\n" +
                                                "  \"miner_version\": \"0.1.8\",\n" +
                                                "  \"driver_version\": \"26.20.13025.10004\",\n" +
                                                "  \"mining_time\": 18359,\n" +
                                                "  \"gpu_threads\": 6,\n" +
                                                "  \"total_threads\": 6,\n" +
                                                "  \"hashrate_total_now\": 6290860755.620301,\n" +
                                                "  \"hashrate_total_1min\": 6285763733.150456,\n" +
                                                "  \"hashrate_total_5min\": 6285510817.402596,\n" +
                                                "  \"hashrate_total_30min\": 6285116014.409216,\n" +
                                                "  \"hashrate_total_max\": 6298711252.984357,\n" +
                                                "  \"gpu_devices\": [\n" +
                                                "    {\n" +
                                                "      \"device\": \"GPU0\",\n" +
                                                "      \"device_id\": 0,\n" +
                                                "      \"model\": \"radeon_rx_vega\",\n" +
                                                "      \"bus_id\": 3,\n" +
                                                "      \"core_clock\": 1408,\n" +
                                                "      \"memory_clock\": 500,\n" +
                                                "      \"temperature\": 55,\n" +
                                                "      \"fan_speed_rpm\": 3239,\n" +
                                                "      \"compute_errors\": 0\n" +
                                                "    }\n" +
                                                "  ],\n" +
                                                "  \"gpu_hashrate\": [\n" +
                                                "    {\n" +
                                                "      \"GPU0\": 1052373939.1692787,\n" +
                                                "      \"GPU1\": 1052535911.4670709,\n" +
                                                "      \"GPU2\": 1038264624.0793476,\n" +
                                                "      \"GPU3\": 1053226731.9473168,\n" +
                                                "      \"GPU4\": 1046890553.2711909,\n" +
                                                "      \"GPU5\": 1042258405.1337745,\n" +
                                                "      \"total\": 6285550165.06798\n" +
                                                "    }\n" +
                                                "  ],\n" +
                                                "  \"shares\": {\n" +
                                                "    \"total\": 2190,\n" +
                                                "    \"accepted\": 2171,\n" +
                                                "    \"rejected\": 19,\n" +
                                                "    \"avg_find_time\": 7\n" +
                                                "  },\n" +
                                                "  \"pool\": {\n" +
                                                "    \"pool\": \"kda-us.icemining.ca:3700\",\n" +
                                                "    \"difficulty\": 11.641354547,\n" +
                                                "    \"time_connected\": \"2019-12-03 19:10:34\",\n" +
                                                "    \"uptime\": 18345,\n" +
                                                "    \"latency\": 258,\n" +
                                                "    \"last_job_received\": 1\n" +
                                                "  }\n" +
                                                "}"))),
                new MinerStats.Builder()
                        .setApiIp("127.0.0.1")
                        .setApiPort(21555)
                        .addPool(
                                new Pool.Builder()
                                        .setName("kda-us.icemining.ca:3700")
                                        .setStatus(true, true)
                                        .setPriority(0)
                                        .setCounts(
                                                2171,
                                                19,
                                                0)
                                        .build())
                        .addRig(
                                new Rig.Builder()
                                        .setHashRate(new BigDecimal("6290860755.620301"))
                                        .addGpu(
                                                new Gpu.Builder()
                                                        .setName("radeon_rx_vega")
                                                        .setIndex(0)
                                                        .setBus(3)
                                                        .setTemp(55)
                                                        .setFans(
                                                                new FanInfo.Builder()
                                                                        .setCount(1)
                                                                        .addSpeed(3239)
                                                                        .setSpeedUnits("RPM")
                                                                        .build())
                                                        .setFreqInfo(
                                                                new FreqInfo.Builder()
                                                                        .setFreq(1408)
                                                                        .setMemFreq(500)
                                                                        .build())
                                                        .build())
                                        .build())
                        .build());
    }
}