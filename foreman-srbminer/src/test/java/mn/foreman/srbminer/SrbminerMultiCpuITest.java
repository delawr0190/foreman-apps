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

/** Runs an integration tests using {@link SrbminerNew} against a fake API. */
public class SrbminerMultiCpuITest
        extends AbstractApiITest {

    /** Constructor. */
    public SrbminerMultiCpuITest() {
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
                                                "\"rig_name\": \"SRBMiner-Multi-Rig\",\n" +
                                                "    \"miner_version\": \"0.5.6\",\n" +
                                                "    \"mining_time\": 1368,\n" +
                                                "    \"total_cpu_workers\": 1,\n" +
                                                "    \"total_gpu_workers\": 0,\n" +
                                                "    \"total_workers\": 1,\n" +
                                                "    \"cpu_devices\": [\n" +
                                                "        {\n" +
                                                "            \"id\": 0,\n" +
                                                "            \"device\": \"cpu0\",\n" +
                                                "            \"model\": \"Intel Core Processor (Haswell, no TSX, IBRS)\",\n" +
                                                "            \"L3\": 16777216,\n" +
                                                "            \"L2\": 4194304,\n" +
                                                "            \"L1\": 32768\n" +
                                                "        }\n" +
                                                "    ],\n" +
                                                "    \"gpu_devices\": [],\n" +
                                                "    \"algorithms\": [\n" +
                                                "        {\n" +
                                                "            \"id\": 0,\n" +
                                                "            \"name\": \"verushash\",\n" +
                                                "            \"pool\": {\n" +
                                                "                \"pool\": \"verus.alphatechit.co.uk:9997\",\n" +
                                                "                \"time_connected\": \"2020-11-23 08:47:31\",\n" +
                                                "                \"uptime\": 1366,\n" +
                                                "                \"difficulty\": 0.0,\n" +
                                                "                \"last_job_received\": 45,\n" +
                                                "                \"latency\": 88\n" +
                                                "            },\n" +
                                                "            \"shares\": {\n" +
                                                "                \"total\": 15,\n" +
                                                "                \"accepted\": 15,\n" +
                                                "                \"rejected\": 0,\n" +
                                                "                \"avg_find_time\": 91\n" +
                                                "            },\n" +
                                                "            \"hashrate\": {\n" +
                                                "                \"now\": 206111.5149227829,\n" +
                                                "                \"1min\": 197190.38067646,\n" +
                                                "                \"5min\": 198834.8169509847,\n" +
                                                "                \"30min\": 0.0,\n" +
                                                "                \"max\": 222583.5616438356,\n" +
                                                "                \"cpu\": {\n" +
                                                "                    \"thread0\": 206111.5149227829,\n" +
                                                "                    \"total\": 206111.5149227829\n" +
                                                "                },\n" +
                                                "                \"gpu\": {\n" +
                                                "                    \"total\": 0.0\n" +
                                                "                }\n" +
                                                "            }\n" +
                                                "        }\n" +
                                                "    ]" +
                                                "}"))),
                new MinerStats.Builder()
                        .setApiIp("127.0.0.1")
                        .setApiPort(21555)
                        .addPool(
                                new Pool.Builder()
                                        .setName("verus.alphatechit.co.uk:9997")
                                        .setStatus(true, true)
                                        .setPriority(0)
                                        .setCounts(
                                                15,
                                                0,
                                                0)
                                        .build())
                        .addRig(
                                new Rig.Builder()
                                        .setHashRate(new BigDecimal("206111.5149227829"))
                                        .addGpu(
                                                new Gpu.Builder()
                                                        .setName("Intel Core Processor (Haswell, no TSX, IBRS)")
                                                        .setIndex(0)
                                                        .setBus(0)
                                                        .setTemp(0)
                                                        .setFans(
                                                                new FanInfo.Builder()
                                                                        .setCount(0)
                                                                        .setSpeedUnits("RPM")
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