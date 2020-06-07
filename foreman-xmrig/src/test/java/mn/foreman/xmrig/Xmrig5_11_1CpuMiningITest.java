package mn.foreman.xmrig;

import mn.foreman.model.miners.MinerStats;
import mn.foreman.model.miners.Pool;
import mn.foreman.model.miners.rig.Rig;
import mn.foreman.util.AbstractApiITest;
import mn.foreman.util.http.FakeHttpMinerServer;
import mn.foreman.util.http.HttpHandler;
import mn.foreman.xmrig.current.XmrigNew;

import com.google.common.collect.ImmutableMap;

import java.math.BigDecimal;
import java.util.Collections;

/**
 * Runs an integration tests using {@link XmrigNew} against a fake API.
 *
 * This tests xmrig-amd.
 */
public class Xmrig5_11_1CpuMiningITest
        extends AbstractApiITest {

    /** Constructor. */
    public Xmrig5_11_1CpuMiningITest() {
        super(
                new XmrigFactory().create(
                        ImmutableMap.of(
                                "apiIp",
                                "127.0.0.1",
                                "apiPort",
                                "8080",
                                "accessToken",
                                "cafebabe")),
                new FakeHttpMinerServer(
                        8080,
                        ImmutableMap.of(
                                "/1/summary",
                                new HttpHandler(
                                        "",
                                        ImmutableMap.of(
                                                "Authorization",
                                                "Bearer cafebabe"),
                                        "{\n" +
                                                "    \"id\": \"bbcf7f6893360fb3\",\n" +
                                                "    \"worker_id\": \"LAPTOP-I9PPS5BU\",\n" +
                                                "    \"uptime\": 18,\n" +
                                                "    \"restricted\": true,\n" +
                                                "    \"resources\": {\n" +
                                                "        \"memory\": {\n" +
                                                "            \"free\": 6970064896,\n" +
                                                "            \"total\": 17047015424,\n" +
                                                "            \"resident_set_memory\": 11784192\n" +
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
                                                "        \"ip\": \"185.92.222.223\",\n" +
                                                "        \"uptime\": 17,\n" +
                                                "        \"ping\": 0,\n" +
                                                "        \"failures\": 0,\n" +
                                                "        \"tls\": null,\n" +
                                                "        \"tls-fingerprint\": null,\n" +
                                                "        \"algo\": \"rx/0\",\n" +
                                                "        \"diff\": 1000225,\n" +
                                                "        \"accepted\": 0,\n" +
                                                "        \"rejected\": 0,\n" +
                                                "        \"avg_time\": 0,\n" +
                                                "        \"hashes_total\": 0,\n" +
                                                "        \"error_log\": []\n" +
                                                "    },\n" +
                                                "    \"version\": \"5.11.1\",\n" +
                                                "    \"kind\": \"miner\",\n" +
                                                "    \"ua\": \"XMRig/5.11.1 (Windows NT 10.0; Win64; x64) libuv/1.34.0 msvc/2019\",\n" +
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
                                                "    \"algorithms\": [\"cn/1\", \"cn/2\", \"cn/r\", \"cn/fast\", \"cn/half\", \"cn/xao\", \"cn/rto\", \"cn/rwz\", \"cn/zls\", \"cn/double\", \"cn-lite/1\", \"cn-heavy/0\", \"cn-heavy/tube\", \"cn-heavy/xhv\", \"cn-pico\", \"cn-pico/tlo\", \"rx/0\", \"rx/wow\", \"rx/loki\", \"rx/arq\", \"rx/sfx\", \"rx/keva\", \"argon2/chukwa\", \"argon2/wrkz\", \"astrobwt\"],\n" +
                                                "    \"hashrate\": {\n" +
                                                "        \"total\": [445.81, null, null],\n" +
                                                "        \"highest\": null,\n" +
                                                "        \"threads\": [\n" +
                                                "            [null, null, null],\n" +
                                                "            [null, null, null],\n" +
                                                "            [null, null, null],\n" +
                                                "            [224.58, null, null],\n" +
                                                "            [221.23, null, null]\n" +
                                                "        ]\n" +
                                                "    },\n" +
                                                "    \"hugepages\": true\n" +
                                                "}",
                                        Collections.emptyMap()),
                                "/2/backends",
                                new HttpHandler(
                                        "",
                                        ImmutableMap.of(
                                                "Authorization",
                                                "Bearer cafebabe"),
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
                                                "        \"astrobwt-max-size\": 550,\n" +
                                                "        \"hugepages\": [1173, 1173],\n" +
                                                "        \"memory\": 10485760,\n" +
                                                "        \"hashrate\": [1104.88, null, null],\n" +
                                                "        \"threads\": [\n" +
                                                "            {\n" +
                                                "                \"intensity\": 1,\n" +
                                                "                \"affinity\": 0,\n" +
                                                "                \"av\": 1,\n" +
                                                "                \"hashrate\": [227.97, null, null]\n" +
                                                "            },\n" +
                                                "            {\n" +
                                                "                \"intensity\": 1,\n" +
                                                "                \"affinity\": 2,\n" +
                                                "                \"av\": 1,\n" +
                                                "                \"hashrate\": [219.61, null, null]\n" +
                                                "            },\n" +
                                                "            {\n" +
                                                "                \"intensity\": 1,\n" +
                                                "                \"affinity\": 4,\n" +
                                                "                \"av\": 1,\n" +
                                                "                \"hashrate\": [220.2, null, null]\n" +
                                                "            },\n" +
                                                "            {\n" +
                                                "                \"intensity\": 1,\n" +
                                                "                \"affinity\": 6,\n" +
                                                "                \"av\": 1,\n" +
                                                "                \"hashrate\": [220.26, null, null]\n" +
                                                "            },\n" +
                                                "            {\n" +
                                                "                \"intensity\": 1,\n" +
                                                "                \"affinity\": 8,\n" +
                                                "                \"av\": 1,\n" +
                                                "                \"hashrate\": [216.83, null, null]\n" +
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
                                                "        \"enabled\": false,\n" +
                                                "        \"algo\": null,\n" +
                                                "        \"profile\": null\n" +
                                                "    }\n" +
                                                "]",
                                        Collections.emptyMap()))),
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
                                        .setHashRate(BigDecimal.ZERO)
                                        .build())
                        .build());
    }
}