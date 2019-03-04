package mn.foreman.jceminer;

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

/** Runs an integration tests using {@link Jceminer} against a fake API. */
public class JceminerXmrstakITest
        extends AbstractApiITest {

    /** Constructor. */
    public JceminerXmrstakITest() {
        super(
                new JceminerFactory().create(
                        ImmutableMap.of(
                                "apiIp",
                                "127.0.0.1",
                                "apiPort",
                                "3434")),
                new FakeHttpMinerServer(
                        3434,
                        ImmutableMap.of(
                                "/api.json",
                                new HttpHandler(
                                        "",
                                        "{\n" +
                                                "  \"version\": \"jce\\/0.33b18\\/gpu\",\n" +
                                                "  \"hashrate\": {\n" +
                                                "    \"threads\": [\n" +
                                                "      [\n" +
                                                "        3590.6,\n" +
                                                "        3590.6,\n" +
                                                "        3590.6\n" +
                                                "      ],\n" +
                                                "      [\n" +
                                                "        3580.1,\n" +
                                                "        3580.1,\n" +
                                                "        3580.1\n" +
                                                "      ],\n" +
                                                "      [\n" +
                                                "        3582.9,\n" +
                                                "        3582.9,\n" +
                                                "        3582.9\n" +
                                                "      ],\n" +
                                                "      [\n" +
                                                "        3583.1,\n" +
                                                "        3583.1,\n" +
                                                "        3583.1\n" +
                                                "      ],\n" +
                                                "      [\n" +
                                                "        3582.2,\n" +
                                                "        3582.2,\n" +
                                                "        3582.2\n" +
                                                "      ],\n" +
                                                "      [\n" +
                                                "        3588.5,\n" +
                                                "        3588.5,\n" +
                                                "        3588.5\n" +
                                                "      ],\n" +
                                                "      [\n" +
                                                "        3482.5,\n" +
                                                "        3482.5,\n" +
                                                "        3482.5\n" +
                                                "      ],\n" +
                                                "      [\n" +
                                                "        3481.4,\n" +
                                                "        3481.4,\n" +
                                                "        3481.4\n" +
                                                "      ],\n" +
                                                "      [\n" +
                                                "        3596.3,\n" +
                                                "        3596.3,\n" +
                                                "        3596.3\n" +
                                                "      ],\n" +
                                                "      [\n" +
                                                "        3597.2,\n" +
                                                "        3597.2,\n" +
                                                "        3597.2\n" +
                                                "      ],\n" +
                                                "      [\n" +
                                                "        3596.3,\n" +
                                                "        3596.3,\n" +
                                                "        3596.3\n" +
                                                "      ],\n" +
                                                "      [\n" +
                                                "        3594.9,\n" +
                                                "        3594.9,\n" +
                                                "        3594.9\n" +
                                                "      ]\n" +
                                                "    ],\n" +
                                                "    \"total\": [\n" +
                                                "      42855.8,\n" +
                                                "      42855.8,\n" +
                                                "      42855.8\n" +
                                                "    ],\n" +
                                                "    \"highest\": 42930.7\n" +
                                                "  },\n" +
                                                "  \"results\": {\n" +
                                                "    \"diff_current\": 0,\n" +
                                                "    \"shares_good\": 0,\n" +
                                                "    \"shares_total\": 0,\n" +
                                                "    \"avg_time\": 0,\n" +
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
                                                "      \n" +
                                                "    ]\n" +
                                                "  },\n" +
                                                "  \"connection\": {\n" +
                                                "    \"pool\": \"uplexa.herominers.com:10471\",\n" +
                                                "    \"uptime\": 32,\n" +
                                                "    \"ping\": 0,\n" +
                                                "    \"error_log\": [\n" +
                                                "      {\n" +
                                                "        \"last_seen\": 1551671964,\n" +
                                                "        \"text\": \"Socket receive error: An existing connection was forcibly closed by the remote host. \"\n" +
                                                "      },\n" +
                                                "      {\n" +
                                                "        \"last_seen\": 0,\n" +
                                                "        \"text\": \"Socket receive error: An existing connection was forcibly closed by the remote host. \"\n" +
                                                "      },\n" +
                                                "      {\n" +
                                                "        \"last_seen\": 1780,\n" +
                                                "        \"text\": \"Pool response timeout.\"\n" +
                                                "      },\n" +
                                                "      {\n" +
                                                "        \"last_seen\": 1780,\n" +
                                                "        \"text\": \"Pool response timeout.\"\n" +
                                                "      },\n" +
                                                "      {\n" +
                                                "        \"last_seen\": 1778,\n" +
                                                "        \"text\": \"Pool response timeout.\"\n" +
                                                "      },\n" +
                                                "      {\n" +
                                                "        \"last_seen\": 0,\n" +
                                                "        \"text\": \"Pool response timeout.\"\n" +
                                                "      },\n" +
                                                "      {\n" +
                                                "        \"last_seen\": 1780,\n" +
                                                "        \"text\": \"Socket receive error: An existing connection was forcibly closed by the remote host. \"\n" +
                                                "      },\n" +
                                                "      {\n" +
                                                "        \"last_seen\": 1780,\n" +
                                                "        \"text\": \"Socket receive error: An existing connection was forcibly closed by the remote host. \"\n" +
                                                "      }\n" +
                                                "    ]\n" +
                                                "  }\n" +
                                                "}"))),
                new MinerStats.Builder()
                        .setApiIp("127.0.0.1")
                        .setApiPort(3434)
                        .addPool(
                                new Pool.Builder()
                                        .setName("uplexa.herominers.com:10471")
                                        .setStatus(true, true)
                                        .setPriority(0)
                                        .setCounts(0, 0, 0)
                                        .build())
                        .addRig(
                                new Rig.Builder()
                                        .setHashRate(new BigDecimal("42855.8"))
                                        .addGpu(
                                                new Gpu.Builder()
                                                        .setName("GPU 0")
                                                        .setIndex(0)
                                                        .setBus(0)
                                                        .setTemp(0)
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
                                                        .setName("GPU 1")
                                                        .setIndex(1)
                                                        .setBus(0)
                                                        .setTemp(0)
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
                                                        .setName("GPU 2")
                                                        .setIndex(2)
                                                        .setBus(0)
                                                        .setTemp(0)
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
                                                        .setName("GPU 3")
                                                        .setIndex(3)
                                                        .setBus(0)
                                                        .setTemp(0)
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
                                                        .setName("GPU 4")
                                                        .setIndex(4)
                                                        .setBus(0)
                                                        .setTemp(0)
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
                                                        .setName("GPU 5")
                                                        .setIndex(5)
                                                        .setBus(0)
                                                        .setTemp(0)
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
                                                        .setName("GPU 6")
                                                        .setIndex(6)
                                                        .setBus(0)
                                                        .setTemp(0)
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
                                                        .setName("GPU 7")
                                                        .setIndex(7)
                                                        .setBus(0)
                                                        .setTemp(0)
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
                                                        .setName("GPU 8")
                                                        .setIndex(8)
                                                        .setBus(0)
                                                        .setTemp(0)
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
                                                        .setName("GPU 9")
                                                        .setIndex(9)
                                                        .setBus(0)
                                                        .setTemp(0)
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
                                                        .setName("GPU 10")
                                                        .setIndex(10)
                                                        .setBus(0)
                                                        .setTemp(0)
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
                                                        .setName("GPU 11")
                                                        .setIndex(11)
                                                        .setBus(0)
                                                        .setTemp(0)
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