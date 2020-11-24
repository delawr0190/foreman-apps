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
public class SrbminerOldITest
        extends AbstractApiITest {

    /** Constructor. */
    public SrbminerOldITest() {
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
                                                "    \"cryptonight_type\": \"normalv7\",\n" +
                                                "    \"mining_time\": 10,\n" +
                                                "    \"total_devices\": 1,\n" +
                                                "    \"total_threads\": 2,\n" +
                                                "    \"hashrate_total_now\": 1234.5678,\n" +
                                                "    \"hashrate_total_1min\": 0,\n" +
                                                "    \"hashrate_total_5min\": 0,\n" +
                                                "    \"hashrate_total_30min\": 0,\n" +
                                                "    \"hashrate_total_max\": 0,\n" +
                                                "    \"pool\": {\n" +
                                                "        \"pool\": \"stratum+tcp://xmr-eu1.nanopool.org:14444\",\n" +
                                                "        \"difficulty\": 120001,\n" +
                                                "        \"time_connected\": \"2018-07-29 22:12:41\",\n" +
                                                "        \"uptime\": 10,\n" +
                                                "        \"latency\": 0,\n" +
                                                "        \"last_job_received\": 5\n" +
                                                "    },\n" +
                                                "    \"shares\": {\n" +
                                                "        \"total\": 12,\n" +
                                                "        \"accepted\": 5,\n" +
                                                "        \"accepted_stale\": 3,\n" +
                                                "        \"rejected\": 4,\n" +
                                                "        \"avg_find_time\": 0\n" +
                                                "    },\n" +
                                                "    \"devices\": [\n" +
                                                "        {\n" +
                                                "            \"device\": \"GPU0\",\n" +
                                                "            \"device_id\": 0,\n" +
                                                "            \"model\": \"Radeon RX 560 Series\",\n" +
                                                "            \"bus_id\": 2,\n" +
                                                "            \"kernel_id\": 1,\n" +
                                                "            \"hashrate\": 0,\n" +
                                                "            \"core_clock\": 1187,\n" +
                                                "            \"memory_clock\": 1500,\n" +
                                                "            \"temperature\": 44,\n" +
                                                "            \"fan_speed_rpm\": 1538\n" +
                                                "        },\n" +
                                                "        {\n" +
                                                "            \"device\": \"GPU1\",\n" +
                                                "            \"device_id\": 1,\n" +
                                                "            \"model\": \"Radeon RX 560 Series\",\n" +
                                                "            \"bus_id\": 4,\n" +
                                                "            \"kernel_id\": 1,\n" +
                                                "            \"hashrate\": 0,\n" +
                                                "            \"core_clock\": 1187,\n" +
                                                "            \"memory_clock\": 1500,\n" +
                                                "            \"temperature\": 54,\n" +
                                                "            \"fan_speed_rpm\": 1540\n" +
                                                "        }\n" +
                                                "    ]\n" +
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
                                                5,
                                                4,
                                                0)
                                        .build())
                        .addRig(
                                new Rig.Builder()
                                        .setHashRate(new BigDecimal("1234.5678"))
                                        .addGpu(
                                                new Gpu.Builder()
                                                        .setName("Radeon RX 560 Series")
                                                        .setIndex(0)
                                                        .setBus(2)
                                                        .setTemp(44)
                                                        .setFans(
                                                                new FanInfo.Builder()
                                                                        .setCount(1)
                                                                        .addSpeed(1538)
                                                                        .setSpeedUnits("RPM")
                                                                        .build())
                                                        .setFreqInfo(
                                                                new FreqInfo.Builder()
                                                                        .setFreq(1187)
                                                                        .setMemFreq(1500)
                                                                        .build())
                                                        .build())
                                        .addGpu(
                                                new Gpu.Builder()
                                                        .setName("Radeon RX 560 Series")
                                                        .setIndex(1)
                                                        .setBus(4)
                                                        .setTemp(54)
                                                        .setFans(
                                                                new FanInfo.Builder()
                                                                        .setCount(1)
                                                                        .addSpeed(1540)
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