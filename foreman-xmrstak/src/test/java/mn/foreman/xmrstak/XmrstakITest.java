package mn.foreman.xmrstak;

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

/** Runs an integration tests using {@link Xmrstak} against a fake API. */
public class XmrstakITest
        extends AbstractApiITest {

    /** Constructor. */
    public XmrstakITest() {
        super(
                new Xmrstak(
                        "127.0.0.1",
                        44444),
                new FakeHttpMinerServer(
                        44444,
                        ImmutableMap.of(
                                "/api.json",
                                new HttpHandler(
                                        "",
                                        "{\n" +
                                                "  \"version\": \"xmr-stak/2.4.7/c5f0505d/master/win/nvidia-amd-cpu/20\",\n" +
                                                "  \"hashrate\": {\n" +
                                                "    \"threads\": [\n" +
                                                "      [\n" +
                                                "        322.8,\n" +
                                                "        321.6,\n" +
                                                "        null\n" +
                                                "      ],\n" +
                                                "      [\n" +
                                                "        13.4,\n" +
                                                "        13.6,\n" +
                                                "        null\n" +
                                                "      ],\n" +
                                                "      [\n" +
                                                "        13.4,\n" +
                                                "        13.6,\n" +
                                                "        null\n" +
                                                "      ],\n" +
                                                "      [\n" +
                                                "        13.4,\n" +
                                                "        13.6,\n" +
                                                "        null\n" +
                                                "      ],\n" +
                                                "      [\n" +
                                                "        13.8,\n" +
                                                "        14,\n" +
                                                "        null\n" +
                                                "      ]\n" +
                                                "    ],\n" +
                                                "    \"total\": [\n" +
                                                "      376.8,\n" +
                                                "      376.6,\n" +
                                                "      null\n" +
                                                "    ],\n" +
                                                "    \"highest\": 385.5\n" +
                                                "  },\n" +
                                                "  \"results\": {\n" +
                                                "    \"diff_current\": 120001,\n" +
                                                "    \"shares_good\": 0,\n" +
                                                "    \"shares_total\": 1,\n" +
                                                "    \"avg_time\": 272,\n" +
                                                "    \"hashes_total\": 0,\n" +
                                                "    \"best\": [\n" +
                                                "      0,\n" +
                                                "      0,\n" +
                                                "      0,\n" +
                                                "      0,\n" +
                                                "      0,\n" +
                                                "      0,\n" +
                                                "      0,\n" +
                                                "      0,\n" +
                                                "      0,\n" +
                                                "      0\n" +
                                                "    ],\n" +
                                                "    \"error_log\": [\n" +
                                                "      {\n" +
                                                "        \"count\": 1,\n" +
                                                "        \"last_seen\": 1533769528,\n" +
                                                "        \"text\": \"Low difficulty share\"\n" +
                                                "      }\n" +
                                                "    ]\n" +
                                                "  },\n" +
                                                "  \"connection\": {\n" +
                                                "    \"pool\": \"xmr-us-east1.nanopool.org:14444\",\n" +
                                                "    \"uptime\": 272,\n" +
                                                "    \"ping\": 0,\n" +
                                                "    \"error_log\": []\n" +
                                                "  }\n" +
                                                "}\n"))),
                new MinerStats.Builder()
                        .setApiIp("127.0.0.1")
                        .setApiPort(44444)
                        .addPool(
                                new Pool.Builder()
                                        .setName("xmr-us-east1.nanopool.org:14444")
                                        .setStatus(true, true)
                                        .setPriority(0)
                                        .setCounts(
                                                0,
                                                1,
                                                0)
                                        .build())
                        .addRig(
                                new Rig.Builder()
                                        .setHashRate(new BigDecimal("376.8"))
                                        .addGpu(
                                                new Gpu.Builder()
                                                        .setBus(0)
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
                                                        .setIndex(0)
                                                        .setName("GPU 0")
                                                        .setTemp(0)
                                                        .build())
                                        .addGpu(
                                                new Gpu.Builder()
                                                        .setBus(0)
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
                                                        .setIndex(1)
                                                        .setName("GPU 1")
                                                        .setTemp(0)
                                                        .build())
                                        .addGpu(
                                                new Gpu.Builder()
                                                        .setBus(0)
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
                                                        .setIndex(2)
                                                        .setName("GPU 2")
                                                        .setTemp(0)
                                                        .build())
                                        .addGpu(
                                                new Gpu.Builder()
                                                        .setBus(0)
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
                                                        .setIndex(3)
                                                        .setName("GPU 3")
                                                        .setTemp(0)
                                                        .build())
                                        .addGpu(
                                                new Gpu.Builder()
                                                        .setBus(0)
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
                                                        .setIndex(4)
                                                        .setName("GPU 4")
                                                        .setTemp(0)
                                                        .build())
                                        .build())
                        .build());
    }
}