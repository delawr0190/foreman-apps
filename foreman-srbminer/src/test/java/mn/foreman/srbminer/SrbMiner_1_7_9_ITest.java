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
public class SrbMiner_1_7_9_ITest
        extends AbstractApiITest {

    /** Constructor. */
    public SrbMiner_1_7_9_ITest() {
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
                                                "    \"rig_name\": \"SRBMiner-Rig\",\n" +
                                                "    \"cryptonight_type\": \"normalv8\",\n" +
                                                "    \"miner_version\": \"1.7.9\",\n" +
                                                "    \"driver_version\": \"24.20.11021.1000\",\n" +
                                                "    \"mining_time\": 132,\n" +
                                                "    \"total_devices\": 1,\n" +
                                                "    \"total_threads\": 2,\n" +
                                                "    \"hashrate_total_now\": 343,\n" +
                                                "    \"hashrate_total_1min\": 339,\n" +
                                                "    \"hashrate_total_5min\": 0,\n" +
                                                "    \"hashrate_total_30min\": 0,\n" +
                                                "    \"hashrate_total_max\": 349,\n" +
                                                "    \"devices\": [\n" +
                                                "        {\n" +
                                                "            \"device\": \"GPU0\",\n" +
                                                "            \"device_id\": 0,\n" +
                                                "            \"model\": \"Radeon RX 560 Series\",\n" +
                                                "            \"bus_id\": 2,\n" +
                                                "            \"hashrate\": 343,\n" +
                                                "            \"core_clock\": 1187,\n" +
                                                "            \"memory_clock\": 1500,\n" +
                                                "            \"temperature\": 63,\n" +
                                                "            \"fan_speed_rpm\": 1652,\n" +
                                                "            \"compute_errors\": 0\n" +
                                                "        }\n" +
                                                "    ],\n" +
                                                "    \"shares\": {\n" +
                                                "        \"total\": 0,\n" +
                                                "        \"accepted\": 0,\n" +
                                                "        \"accepted_stale\": 0,\n" +
                                                "        \"rejected\": 0,\n" +
                                                "        \"avg_find_time\": 0\n" +
                                                "    },\n" +
                                                "    \"pool\": {\n" +
                                                "        \"pool\": \"xmr-eu1.nanopool.org:14444\",\n" +
                                                "        \"difficulty\": 120001,\n" +
                                                "        \"time_connected\": \"2019-02-27 22:31:07\",\n" +
                                                "        \"uptime\": 131,\n" +
                                                "        \"latency\": 0,\n" +
                                                "        \"last_job_received\": 37\n" +
                                                "    },\n" +
                                                "    \"algo_switching\": {\n" +
                                                "        \"total_switches\": 0,\n" +
                                                "        \"last_switch\": 131\n" +
                                                "    }\n" +
                                                "}"))),
                new MinerStats.Builder()
                        .setApiIp("127.0.0.1")
                        .setApiPort(21555)
                        .addPool(
                                new Pool.Builder()
                                        .setName("xmr-eu1.nanopool.org:14444")
                                        .setStatus(true, true)
                                        .setPriority(0)
                                        .setCounts(
                                                0,
                                                0,
                                                0)
                                        .build())
                        .addRig(
                                new Rig.Builder()
                                        .setHashRate(new BigDecimal("343"))
                                        .addGpu(
                                                new Gpu.Builder()
                                                        .setName("Radeon RX 560 Series")
                                                        .setIndex(0)
                                                        .setBus(2)
                                                        .setTemp(63)
                                                        .setFans(
                                                                new FanInfo.Builder()
                                                                        .setCount(1)
                                                                        .addSpeed(1652)
                                                                        .setSpeedUnits("RPM")
                                                                        .build())
                                                        .setFreqInfo(
                                                                new FreqInfo.Builder()
                                                                        .setFreq(1187)
                                                                        .setMemFreq(1500)
                                                                        .build())
                                                        .build())

                                        .build())
                        .build());
    }
}