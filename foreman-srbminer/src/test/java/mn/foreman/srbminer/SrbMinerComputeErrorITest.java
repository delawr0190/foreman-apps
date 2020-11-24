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

/** Runs an integration tests using {@link SrbminerOld} against a fake API. */
public class SrbMinerComputeErrorITest
        extends AbstractApiITest {

    /** Constructor. */
    public SrbMinerComputeErrorITest() {
        super(
                new SrbminerFactory().create(
                        ImmutableMap.of(
                                "apiIp",
                                "127.0.0.1",
                                "apiPort",
                                "21555")),
                new FakeHttpMinerServer(
                        21555,
                        ImmutableMap.of(
                                "/",
                                new HttpHandler(
                                        "",
                                        "{\n" +
                                                "  \"rig_name\": \"myrig\",\n" +
                                                "  \"cryptonight_type\": \"mox\",\n" +
                                                "  \"miner_version\": \"1.9.2\",\n" +
                                                "  \"driver_version\": \"24.20.11021.1000\",\n" +
                                                "  \"mining_time\": 48936,\n" +
                                                "  \"total_devices\": 6,\n" +
                                                "  \"total_threads\": 12,\n" +
                                                "  \"hashrate_total_now\": 27643,\n" +
                                                "  \"hashrate_total_1min\": 27639,\n" +
                                                "  \"hashrate_total_5min\": 27640,\n" +
                                                "  \"hashrate_total_30min\": 27638,\n" +
                                                "  \"hashrate_total_max\": 27672,\n" +
                                                "  \"devices\": [\n" +
                                                "    {\n" +
                                                "      \"device\": \"GPU3\",\n" +
                                                "      \"device_id\": 3,\n" +
                                                "      \"model\": \"Radeon RX Vega\",\n" +
                                                "      \"bus_id\": 13,\n" +
                                                "      \"hashrate\": 4549,\n" +
                                                "      \"core_clock\": 1408,\n" +
                                                "      \"memory_clock\": 1050,\n" +
                                                "      \"temperature\": 65,\n" +
                                                "      \"fan_speed_rpm\": 4266,\n" +
                                                "      \"compute_errors\": 1\n" +
                                                "    }\n" +
                                                " ],\n" +
                                                "  \"shares\": {\n" +
                                                "    \"total\": 546,\n" +
                                                "    \"accepted\": 546,\n" +
                                                "    \"rejected\": 0,\n" +
                                                "    \"avg_find_time\": 89\n" +
                                                "  },\n" +
                                                "  \"pool\": {\n" +
                                                "    \"pool\": \"mox.optimusblue.com:4444\",\n" +
                                                "    \"difficulty\": 2117833,\n" +
                                                "    \"time_connected\": \"2019-07-18 09:35:44\",\n" +
                                                "    \"uptime\": 48934,\n" +
                                                "    \"latency\": 267,\n" +
                                                "    \"last_job_received\": 88\n" +
                                                "  }\n" +
                                                "}"))),
                new MinerStats.Builder()
                        .setApiIp("127.0.0.1")
                        .setApiPort(21555)
                        .addPool(
                                new Pool.Builder()
                                        .setName("mox.optimusblue.com:4444")
                                        .setStatus(true, true)
                                        .setPriority(0)
                                        .setCounts(
                                                546,
                                                1,
                                                0)
                                        .build())
                        .addRig(
                                new Rig.Builder()
                                        .setHashRate(new BigDecimal("27643"))
                                        .addGpu(
                                                new Gpu.Builder()
                                                        .setName("Radeon RX Vega")
                                                        .setIndex(3)
                                                        .setBus(13)
                                                        .setTemp(65)
                                                        .setFans(
                                                                new FanInfo.Builder()
                                                                        .setCount(1)
                                                                        .addSpeed(4266)
                                                                        .setSpeedUnits("RPM")
                                                                        .build())
                                                        .setFreqInfo(
                                                                new FreqInfo.Builder()
                                                                        .setFreq(1408)
                                                                        .setMemFreq(1050)
                                                                        .build())
                                                        .build())

                                        .build())
                        .build());
    }
}