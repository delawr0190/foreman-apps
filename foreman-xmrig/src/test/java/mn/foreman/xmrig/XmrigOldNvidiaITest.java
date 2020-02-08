package mn.foreman.xmrig;

import mn.foreman.model.miners.FanInfo;
import mn.foreman.model.miners.MinerStats;
import mn.foreman.model.miners.Pool;
import mn.foreman.model.miners.rig.FreqInfo;
import mn.foreman.model.miners.rig.Gpu;
import mn.foreman.model.miners.rig.Rig;
import mn.foreman.util.AbstractApiITest;
import mn.foreman.util.http.FakeHttpMinerServer;
import mn.foreman.util.http.HttpHandler;
import mn.foreman.xmrig.old.XmrigOld;

import com.google.common.collect.ImmutableMap;

import java.math.BigDecimal;

/**
 * Runs an integration tests using {@link XmrigOld} against a fake API.
 *
 * This tests xmrig-nvidia.
 */
public class XmrigOldNvidiaITest
        extends AbstractApiITest {

    /** Constructor. */
    public XmrigOldNvidiaITest() {
        super(
                new XmrigFactory().create(
                        ImmutableMap.of(
                                "apiIp",
                                "127.0.0.1",
                                "apiPort",
                                "8080")),
                new FakeHttpMinerServer(
                        8080,
                        ImmutableMap.of(
                                "/",
                                new HttpHandler(
                                        "",
                                        "{\n" +
                                                "    \"id\": \"83de12bb926e280b\",\n" +
                                                "    \"worker_id\": \"DESKTOP-P3EFAF7\",\n" +
                                                "    \"version\": \"2.6.1\",\n" +
                                                "    \"kind\": \"nvidia\",\n" +
                                                "    \"ua\": \"XMRig/2.6.1 (Windows NT 10.0; Win64; x64) libuv/1.19.2 CUDA/9.10 msvc/2015\",\n" +
                                                "    \"cpu\": {\n" +
                                                "        \"brand\": \"Intel(R) Core(TM) i7-7700K CPU @ 4.20GHz\",\n" +
                                                "        \"aes\": true,\n" +
                                                "        \"x64\": true,\n" +
                                                "        \"sockets\": 1\n" +
                                                "    },\n" +
                                                "    \"algo\": \"cryptonight\",\n" +
                                                "    \"hugepages\": false,\n" +
                                                "    \"donate_level\": 5,\n" +
                                                "    \"hashrate\": {\n" +
                                                "        \"total\": [\n" +
                                                "            3522.59,\n" +
                                                "            3526.79,\n" +
                                                "            0.0\n" +
                                                "        ],\n" +
                                                "        \"highest\": 3557.57,\n" +
                                                "        \"threads\": [\n" +
                                                "            [\n" +
                                                "                477.26,\n" +
                                                "                479.49,\n" +
                                                "                0.0\n" +
                                                "            ],\n" +
                                                "            [\n" +
                                                "                433.49,\n" +
                                                "                439.47,\n" +
                                                "                0.0\n" +
                                                "            ],\n" +
                                                "            [\n" +
                                                "                506.3,\n" +
                                                "                504.16,\n" +
                                                "                0.0\n" +
                                                "            ],\n" +
                                                "            [\n" +
                                                "                471.32,\n" +
                                                "                468.27,\n" +
                                                "                0.0\n" +
                                                "            ],\n" +
                                                "            [\n" +
                                                "                449.21,\n" +
                                                "                444.38,\n" +
                                                "                0.0\n" +
                                                "            ],\n" +
                                                "            [\n" +
                                                "                449.26,\n" +
                                                "                446.61,\n" +
                                                "                0.0\n" +
                                                "            ],\n" +
                                                "            [\n" +
                                                "                449.4,\n" +
                                                "                446.04,\n" +
                                                "                0.0\n" +
                                                "            ],\n" +
                                                "            [\n" +
                                                "                286.31,\n" +
                                                "                298.34,\n" +
                                                "                0.0\n" +
                                                "            ]\n" +
                                                "        ]\n" +
                                                "    },\n" +
                                                "    \"health\": [\n" +
                                                "        {\n" +
                                                "            \"name\": \"GeForce GTX 1070 Ti\",\n" +
                                                "            \"clock\": 1961,\n" +
                                                "            \"mem_clock\": 4158,\n" +
                                                "            \"power\": 93,\n" +
                                                "            \"temp\": 54,\n" +
                                                "            \"fan\": 53\n" +
                                                "        },\n" +
                                                "        {\n" +
                                                "            \"name\": \"GeForce GTX 1070 Ti\",\n" +
                                                "            \"clock\": 1936,\n" +
                                                "            \"mem_clock\": 4158,\n" +
                                                "            \"power\": 76,\n" +
                                                "            \"temp\": 50,\n" +
                                                "            \"fan\": 50\n" +
                                                "        },\n" +
                                                "        {\n" +
                                                "            \"name\": \"GeForce GTX 1070 Ti\",\n" +
                                                "            \"clock\": 1936,\n" +
                                                "            \"mem_clock\": 4158,\n" +
                                                "            \"power\": 77,\n" +
                                                "            \"temp\": 50,\n" +
                                                "            \"fan\": 50\n" +
                                                "        },\n" +
                                                "        {\n" +
                                                "            \"name\": \"GeForce GTX 1070 Ti\",\n" +
                                                "            \"clock\": 1936,\n" +
                                                "            \"mem_clock\": 4158,\n" +
                                                "            \"power\": 76,\n" +
                                                "            \"temp\": 50,\n" +
                                                "            \"fan\": 50\n" +
                                                "        },\n" +
                                                "        {\n" +
                                                "            \"name\": \"GeForce GTX 1070 Ti\",\n" +
                                                "            \"clock\": 1936,\n" +
                                                "            \"mem_clock\": 4158,\n" +
                                                "            \"power\": 75,\n" +
                                                "            \"temp\": 51,\n" +
                                                "            \"fan\": 51\n" +
                                                "        },\n" +
                                                "        {\n" +
                                                "            \"name\": \"GeForce GTX 1070 Ti\",\n" +
                                                "            \"clock\": 1949,\n" +
                                                "            \"mem_clock\": 4158,\n" +
                                                "            \"power\": 77,\n" +
                                                "            \"temp\": 52,\n" +
                                                "            \"fan\": 52\n" +
                                                "        },\n" +
                                                "        {\n" +
                                                "            \"name\": \"GeForce GTX 1070 Ti\",\n" +
                                                "            \"clock\": 1936,\n" +
                                                "            \"mem_clock\": 4158,\n" +
                                                "            \"power\": 76,\n" +
                                                "            \"temp\": 52,\n" +
                                                "            \"fan\": 52\n" +
                                                "        },\n" +
                                                "        {\n" +
                                                "            \"name\": \"GeForce GTX 1070 Ti\",\n" +
                                                "            \"clock\": 1949,\n" +
                                                "            \"mem_clock\": 4158,\n" +
                                                "            \"power\": 53,\n" +
                                                "            \"temp\": 50,\n" +
                                                "            \"fan\": 50\n" +
                                                "        }\n" +
                                                "    ],\n" +
                                                "    \"results\": {\n" +
                                                "        \"diff_current\": 80761,\n" +
                                                "        \"shares_good\": 4,\n" +
                                                "        \"shares_total\": 4,\n" +
                                                "        \"avg_time\": 19,\n" +
                                                "        \"hashes_total\": 207331,\n" +
                                                "        \"best\": [\n" +
                                                "            717724,\n" +
                                                "            93840,\n" +
                                                "            91003,\n" +
                                                "            49818,\n" +
                                                "            0,\n" +
                                                "            0,\n" +
                                                "            0,\n" +
                                                "            0,\n" +
                                                "            0,\n" +
                                                "            0\n" +
                                                "        ],\n" +
                                                "        \"error_log\": []\n" +
                                                "    },\n" +
                                                "    \"connection\": {\n" +
                                                "        \"pool\": \"pool.supportxmr.com:7777\",\n" +
                                                "        \"uptime\": 79,\n" +
                                                "        \"ping\": 98,\n" +
                                                "        \"failures\": 0,\n" +
                                                "        \"error_log\": []\n" +
                                                "    }\n" +
                                                "}"))),
                new MinerStats.Builder()
                        .setApiIp("127.0.0.1")
                        .setApiPort(8080)
                        .addPool(
                                new Pool.Builder()
                                        .setName("pool.supportxmr.com:7777")
                                        .setStatus(true, true)
                                        .setPriority(0)
                                        .setCounts(
                                                4,
                                                0,
                                                0)
                                        .build())
                        .addRig(
                                new Rig.Builder()
                                        .setHashRate(new BigDecimal("3522.59"))
                                        .addGpu(
                                                new Gpu.Builder()
                                                        .setBus(0)
                                                        .setFans(
                                                                new FanInfo.Builder()
                                                                        .setCount(1)
                                                                        .addSpeed(53)
                                                                        .setSpeedUnits("%")
                                                                        .build())
                                                        .setFreqInfo(
                                                                new FreqInfo.Builder()
                                                                        .setFreq(1961)
                                                                        .setMemFreq(4158)
                                                                        .build())
                                                        .setIndex(0)
                                                        .setName("GeForce GTX 1070 Ti")
                                                        .setTemp(54)
                                                        .build())
                                        .addGpu(
                                                new Gpu.Builder()
                                                        .setBus(0)
                                                        .setFans(
                                                                new FanInfo.Builder()
                                                                        .setCount(1)
                                                                        .addSpeed(50)
                                                                        .setSpeedUnits("%")
                                                                        .build())
                                                        .setFreqInfo(
                                                                new FreqInfo.Builder()
                                                                        .setFreq(1936)
                                                                        .setMemFreq(4158)
                                                                        .build())
                                                        .setIndex(1)
                                                        .setName("GeForce GTX 1070 Ti")
                                                        .setTemp(50)
                                                        .build())
                                        .addGpu(
                                                new Gpu.Builder()
                                                        .setBus(0)
                                                        .setFans(
                                                                new FanInfo.Builder()
                                                                        .setCount(1)
                                                                        .addSpeed(50)
                                                                        .setSpeedUnits("%")
                                                                        .build())
                                                        .setFreqInfo(
                                                                new FreqInfo.Builder()
                                                                        .setFreq(1936)
                                                                        .setMemFreq(4158)
                                                                        .build())
                                                        .setIndex(2)
                                                        .setName("GeForce GTX 1070 Ti")
                                                        .setTemp(50)
                                                        .build())
                                        .addGpu(
                                                new Gpu.Builder()
                                                        .setBus(0)
                                                        .setFans(
                                                                new FanInfo.Builder()
                                                                        .setCount(1)
                                                                        .addSpeed(50)
                                                                        .setSpeedUnits("%")
                                                                        .build())
                                                        .setFreqInfo(
                                                                new FreqInfo.Builder()
                                                                        .setFreq(1936)
                                                                        .setMemFreq(4158)
                                                                        .build())
                                                        .setIndex(3)
                                                        .setName("GeForce GTX 1070 Ti")
                                                        .setTemp(50)
                                                        .build())
                                        .addGpu(
                                                new Gpu.Builder()
                                                        .setBus(0)
                                                        .setFans(
                                                                new FanInfo.Builder()
                                                                        .setCount(1)
                                                                        .addSpeed(51)
                                                                        .setSpeedUnits("%")
                                                                        .build())
                                                        .setFreqInfo(
                                                                new FreqInfo.Builder()
                                                                        .setFreq(1936)
                                                                        .setMemFreq(4158)
                                                                        .build())
                                                        .setIndex(4)
                                                        .setName("GeForce GTX 1070 Ti")
                                                        .setTemp(51)
                                                        .build())
                                        .addGpu(
                                                new Gpu.Builder()
                                                        .setBus(0)
                                                        .setFans(
                                                                new FanInfo.Builder()
                                                                        .setCount(1)
                                                                        .addSpeed(52)
                                                                        .setSpeedUnits("%")
                                                                        .build())
                                                        .setFreqInfo(
                                                                new FreqInfo.Builder()
                                                                        .setFreq(1949)
                                                                        .setMemFreq(4158)
                                                                        .build())
                                                        .setIndex(5)
                                                        .setName("GeForce GTX 1070 Ti")
                                                        .setTemp(52)
                                                        .build())
                                        .addGpu(
                                                new Gpu.Builder()
                                                        .setBus(0)
                                                        .setFans(
                                                                new FanInfo.Builder()
                                                                        .setCount(1)
                                                                        .addSpeed(52)
                                                                        .setSpeedUnits("%")
                                                                        .build())
                                                        .setFreqInfo(
                                                                new FreqInfo.Builder()
                                                                        .setFreq(1936)
                                                                        .setMemFreq(4158)
                                                                        .build())
                                                        .setIndex(6)
                                                        .setName("GeForce GTX 1070 Ti")
                                                        .setTemp(52)
                                                        .build())
                                        .addGpu(
                                                new Gpu.Builder()
                                                        .setBus(0)
                                                        .setFans(
                                                                new FanInfo.Builder()
                                                                        .setCount(1)
                                                                        .addSpeed(50)
                                                                        .setSpeedUnits("%")
                                                                        .build())
                                                        .setFreqInfo(
                                                                new FreqInfo.Builder()
                                                                        .setFreq(1949)
                                                                        .setMemFreq(4158)
                                                                        .build())
                                                        .setIndex(7)
                                                        .setName("GeForce GTX 1070 Ti")
                                                        .setTemp(50)
                                                        .build())
                                        .build())
                        .build());
    }
}