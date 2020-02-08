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
import mn.foreman.xmrig.current.XmrigNew;

import com.google.common.collect.ImmutableMap;

import java.math.BigDecimal;

/**
 * Runs an integration tests using {@link XmrigNew} against a fake API.
 *
 * This tests xmrig-amd.
 */
public class XmrigNewITest
        extends AbstractApiITest {

    /** Constructor. */
    public XmrigNewITest() {
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
                                "/1/summary",
                                new HttpHandler(
                                        "",
                                        "{\n" +
                                                "    \"id\": \"bbcf7f6893360fb3\",\n" +
                                                "    \"worker_id\": \"LAPTOP-I9PPS5BU\",\n" +
                                                "    \"uptime\": 71,\n" +
                                                "    \"restricted\": true,\n" +
                                                "    \"resources\": {\n" +
                                                "        \"memory\": {\n" +
                                                "            \"free\": 6621306880,\n" +
                                                "            \"total\": 17047015424,\n" +
                                                "            \"resident_set_memory\": 2334564352\n" +
                                                "        },\n" +
                                                "        \"load_average\": [0.0, 0.0, 0.0],\n" +
                                                "        \"hardware_concurrency\": 12\n" +
                                                "    },\n" +
                                                "    \"features\": [\"api\", \"asm\", \"http\", \"hwloc\", \"tls\", \"opencl\", \"cuda\"],\n" +
                                                "    \"results\": {\n" +
                                                "        \"diff_current\": 1000225,\n" +
                                                "        \"shares_good\": 0,\n" +
                                                "        \"shares_total\": 0,\n" +
                                                "        \"avg_time\": 0,\n" +
                                                "        \"hashes_total\": 0,\n" +
                                                "        \"best\": [0, 0, 0, 0, 0, 0, 0, 0, 0, 0],\n" +
                                                "        \"error_log\": []\n" +
                                                "    },\n" +
                                                "    \"algo\": \"rx/0\",\n" +
                                                "    \"connection\": {\n" +
                                                "        \"pool\": \"donate.v2.xmrig.com:3333\",\n" +
                                                "        \"ip\": \"178.128.242.134\",\n" +
                                                "        \"uptime\": 71,\n" +
                                                "        \"ping\": 0,\n" +
                                                "        \"failures\": 0,\n" +
                                                "        \"tls\": null,\n" +
                                                "        \"tls-fingerprint\": null,\n" +
                                                "        \"error_log\": []\n" +
                                                "    },\n" +
                                                "    \"version\": \"5.5.3\",\n" +
                                                "    \"kind\": \"miner\",\n" +
                                                "    \"ua\": \"XMRig/5.5.3 (Windows NT 10.0; Win64; x64) libuv/1.34.0 msvc/2019\",\n" +
                                                "    \"cpu\": {\n" +
                                                "        \"brand\": \"Intel(R) Core(TM) i7-8750H CPU @ 2.20GHz\",\n" +
                                                "        \"aes\": true,\n" +
                                                "        \"avx2\": true,\n" +
                                                "        \"x64\": true,\n" +
                                                "        \"l2\": 1572864,\n" +
                                                "        \"l3\": 9437184,\n" +
                                                "        \"cores\": 6,\n" +
                                                "        \"threads\": 12,\n" +
                                                "        \"packages\": 1,\n" +
                                                "        \"nodes\": 1,\n" +
                                                "        \"backend\": \"hwloc/2.1.0\",\n" +
                                                "        \"assembly\": \"intel\"\n" +
                                                "    },\n" +
                                                "    \"donate_level\": 5,\n" +
                                                "    \"paused\": false,\n" +
                                                "    \"algorithms\": [\"cn/1\", \"cn/2\", \"cn/r\", \"cn/fast\", \"cn/half\", \"cn/xao\", \"cn/rto\", \"cn/rwz\", \"cn/zls\", \"cn/double\", \"cn/gpu\", \"cn-lite/1\", \"cn-heavy/0\", \"cn-heavy/tube\", \"cn-heavy/xhv\", \"cn-pico\", \"cn-pico/tlo\", \"rx/0\", \"rx/wow\", \"rx/loki\", \"rx/arq\", \"rx/sfx\", \"argon2/chukwa\", \"argon2/wrkz\"],\n" +
                                                "    \"hashrate\": {\n" +
                                                "        \"total\": [899.09, 953.1, null],\n" +
                                                "        \"highest\": 1100.45,\n" +
                                                "        \"threads\": [\n" +
                                                "            [147.4, 162.68, null],\n" +
                                                "            [144.15, 164.59, null],\n" +
                                                "            [147.93, 166.77, null],\n" +
                                                "            [153.16, 172.54, null],\n" +
                                                "            [148.21, 162.76, null],\n" +
                                                "            [158.22, 123.74, null]\n" +
                                                "        ]\n" +
                                                "    },\n" +
                                                "    \"hugepages\": false\n" +
                                                "}"))),
                new MinerStats.Builder()
                        .setApiIp("127.0.0.1")
                        .setApiPort(8080)
                        .addPool(
                                new Pool.Builder()
                                        .setName("donate.v2.xmrig.com:3333")
                                        .setStatus(true, true)
                                        .setPriority(0)
                                        .setCounts(
                                                0,
                                                0,
                                                0)
                                        .build())
                        .addRig(
                                new Rig.Builder()
                                        .setHashRate(new BigDecimal("899.09"))
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
                                                        .setIndex(5)
                                                        .setName("GPU 5")
                                                        .setTemp(0)
                                                        .build())
                                        .build())
                        .build());
    }
}