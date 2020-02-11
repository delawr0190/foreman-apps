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
                                                "}"),
                                "/2/backends",
                                new HttpHandler(
                                        "",
                                        "[\n" +
                                                "    {\n" +
                                                "        \"type\": \"cpu\",\n" +
                                                "        \"enabled\": true,\n" +
                                                "        \"algo\": \"rx/0\",\n" +
                                                "        \"profile\": \"rx\",\n" +
                                                "        \"hw-aes\": true,\n" +
                                                "        \"priority\": -1,\n" +
                                                "        \"asm\": \"intel\",\n" +
                                                "        \"argon2-impl\": null,\n" +
                                                "        \"hugepages\": [133, 1173],\n" +
                                                "        \"memory\": 10485760,\n" +
                                                "        \"hashrate\": [924.59, null, null],\n" +
                                                "        \"threads\": [\n" +
                                                "            {\n" +
                                                "                \"intensity\": 1,\n" +
                                                "                \"affinity\": 0,\n" +
                                                "                \"av\": 1,\n" +
                                                "                \"hashrate\": [193.89, null, null]\n" +
                                                "            },\n" +
                                                "            {\n" +
                                                "                \"intensity\": 1,\n" +
                                                "                \"affinity\": 2,\n" +
                                                "                \"av\": 1,\n" +
                                                "                \"hashrate\": [184.06, null, null]\n" +
                                                "            },\n" +
                                                "            {\n" +
                                                "                \"intensity\": 1,\n" +
                                                "                \"affinity\": 4,\n" +
                                                "                \"av\": 1,\n" +
                                                "                \"hashrate\": [187.47, null, null]\n" +
                                                "            },\n" +
                                                "            {\n" +
                                                "                \"intensity\": 1,\n" +
                                                "                \"affinity\": 6,\n" +
                                                "                \"av\": 1,\n" +
                                                "                \"hashrate\": [179.77, null, null]\n" +
                                                "            },\n" +
                                                "            {\n" +
                                                "                \"intensity\": 1,\n" +
                                                "                \"affinity\": 8,\n" +
                                                "                \"av\": 1,\n" +
                                                "                \"hashrate\": [179.38, null, null]\n" +
                                                "            }\n" +
                                                "        ]\n" +
                                                "    },\n" +
                                                "    {\n" +
                                                "        \"type\": \"opencl\",\n" +
                                                "        \"enabled\": false,\n" +
                                                "        \"algo\": null,\n" +
                                                "        \"profile\": null,\n" +
                                                "        \"platform\": null\n" +
                                                "    },\n" +
                                                "    {\n" +
                                                "        \"type\": \"cuda\",\n" +
                                                "        \"enabled\": true,\n" +
                                                "        \"algo\": \"rx/0\",\n" +
                                                "        \"profile\": \"rx\",\n" +
                                                "        \"versions\": {\n" +
                                                "            \"cuda-runtime\": \"10.1\",\n" +
                                                "            \"cuda-driver\": \"10.1\",\n" +
                                                "            \"plugin\": \"2.1.0\",\n" +
                                                "            \"nvml\": \"10.426.00\",\n" +
                                                "            \"driver\": \"426.00\"\n" +
                                                "        },\n" +
                                                "        \"hashrate\": [161.95, null, null],\n" +
                                                "        \"threads\": [\n" +
                                                "            {\n" +
                                                "                \"index\": 0,\n" +
                                                "                \"threads\": 32,\n" +
                                                "                \"blocks\": 12,\n" +
                                                "                \"bfactor\": 8,\n" +
                                                "                \"bsleep\": 25,\n" +
                                                "                \"affinity\": -1,\n" +
                                                "                \"dataset_host\": false,\n" +
                                                "                \"hashrate\": [161.95, null, null],\n" +
                                                "                \"name\": \"GeForce GTX 1050 Ti\",\n" +
                                                "                \"bus_id\": \"01:00.0\",\n" +
                                                "                \"smx\": 6,\n" +
                                                "                \"arch\": 61,\n" +
                                                "                \"global_mem\": 4294967296,\n" +
                                                "                \"clock\": 1620,\n" +
                                                "                \"memory_clock\": 3504,\n" +
                                                "                \"health\": {\n" +
                                                "                    \"temperature\": 48,\n" +
                                                "                    \"power\": 0,\n" +
                                                "                    \"clock\": 1784,\n" +
                                                "                    \"mem_clock\": 2504,\n" +
                                                "                    \"fan_speed\": []\n" +
                                                "                }\n" +
                                                "            }\n" +
                                                "        ]\n" +
                                                "    }\n" +
                                                "]"))),
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
                                        .setHashRate(new BigDecimal("161.95"))
                                        .addGpu(
                                                new Gpu.Builder()
                                                        .setBus(1)
                                                        .setFans(
                                                                new FanInfo.Builder()
                                                                        .setCount(0)
                                                                        .setSpeedUnits("%")
                                                                        .build())
                                                        .setFreqInfo(
                                                                new FreqInfo.Builder()
                                                                        .setFreq(1784)
                                                                        .setMemFreq(2504)
                                                                        .build())
                                                        .setIndex(0)
                                                        .setName("GeForce GTX 1050 Ti")
                                                        .setTemp(48)
                                                        .build())
                                        .build())
                        .build());
    }
}