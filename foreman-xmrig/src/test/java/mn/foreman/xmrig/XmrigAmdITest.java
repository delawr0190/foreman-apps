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

import com.google.common.collect.ImmutableMap;

import java.math.BigDecimal;

/**
 * Runs an integration tests using {@link Xmrig} against a fake API.
 *
 * This tests xmrig-amd.
 */
public class XmrigAmdITest
        extends AbstractApiITest {

    /** Constructor. */
    public XmrigAmdITest() {
        super(
                new Xmrig(
                        "127.0.0.1",
                        8080),
                new FakeHttpMinerServer(
                        8080,
                        ImmutableMap.of(
                                "/",
                                new HttpHandler(
                                        "",
                                        "{\n" +
                                                "    \"id\": \"150216641909d91c\",\n" +
                                                "    \"worker_id\": \"worker\",\n" +
                                                "    \"version\": \"2.4.0\",\n" +
                                                "    \"kind\": \"cpu\",\n" +
                                                "    \"ua\": \"XMRig/2.4.0-dev (Linux x86_64) libuv/1.8.0 gcc/5.4.0\",\n" +
                                                "    \"cpu\": {\n" +
                                                "        \"brand\": \"Intel(R) Core(TM) i7-6700 CPU @ 3.40GHz\",\n" +
                                                "        \"aes\": true,\n" +
                                                "        \"x64\": true,\n" +
                                                "        \"sockets\": 1\n" +
                                                "    },\n" +
                                                "    \"algo\": \"cryptonight\",\n" +
                                                "    \"hugepages\": true,\n" +
                                                "    \"donate\": 5,\n" +
                                                "    \"hashrate\": {\n" +
                                                "        \"total\": [\n" +
                                                "            232.1,\n" +
                                                "            252.6,\n" +
                                                "            252.8\n" +
                                                "        ],\n" +
                                                "        \"highest\": 274.1,\n" +
                                                "        \"threads\": [\n" +
                                                "            [\n" +
                                                "                57.1,\n" +
                                                "                62.5,\n" +
                                                "                62.9\n" +
                                                "            ],\n" +
                                                "            [\n" +
                                                "                58.8,\n" +
                                                "                62.5,\n" +
                                                "                63.1\n" +
                                                "            ],\n" +
                                                "            [\n" +
                                                "                57.2,\n" +
                                                "                63.7,\n" +
                                                "                63.5\n" +
                                                "            ],\n" +
                                                "            [\n" +
                                                "                58.9,\n" +
                                                "                63.7,\n" +
                                                "                63.2\n" +
                                                "            ]\n" +
                                                "        ]\n" +
                                                "    },\n" +
                                                "    \"results\": {\n" +
                                                "        \"diff_current\": 10000,\n" +
                                                "        \"shares_good\": 1849,\n" +
                                                "        \"shares_total\": 1849,\n" +
                                                "        \"avg_time\": 41,\n" +
                                                "        \"hashes_total\": 18490000,\n" +
                                                "        \"best\": [\n" +
                                                "            10674268,\n" +
                                                "            5505855,\n" +
                                                "            5346760,\n" +
                                                "            4975689,\n" +
                                                "            4011628,\n" +
                                                "            3687853,\n" +
                                                "            3148608,\n" +
                                                "            2876595,\n" +
                                                "            2619473,\n" +
                                                "            2451160\n" +
                                                "        ],\n" +
                                                "        \"error_log\": []\n" +
                                                "    },\n" +
                                                "    \"connection\": {\n" +
                                                "        \"pool\": \"pool.minemonero.pro:5555\",\n" +
                                                "        \"uptime\": 75884,\n" +
                                                "        \"ping\": 25,\n" +
                                                "        \"failures\": 0,\n" +
                                                "        \"error_log\": []\n" +
                                                "    }\n" +
                                                "}"))),
                new MinerStats.Builder()
                        .setApiIp("127.0.0.1")
                        .setApiPort(8080)
                        .addPool(
                                new Pool.Builder()
                                        .setName("pool.minemonero.pro:5555")
                                        .setStatus(true, true)
                                        .setPriority(0)
                                        .setCounts(
                                                1849,
                                                0,
                                                0)
                                        .build())
                        .addRig(
                                new Rig.Builder()
                                        .setHashRate(new BigDecimal("232.1"))
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
                                        .build())
                        .build());
    }
}