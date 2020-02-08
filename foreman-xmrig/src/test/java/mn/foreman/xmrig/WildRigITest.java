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
 * Runs an integration tests using {@link XmrigOld}, but WildRig, against a fake
 * API.
 */
public class WildRigITest
        extends AbstractApiITest {

    /** Constructor. */
    public WildRigITest() {
        super(
                new XmrigOld(
                        "127.0.0.1",
                        44444),
                new FakeHttpMinerServer(
                        44444,
                        ImmutableMap.of(
                                "/",
                                new HttpHandler(
                                        "",
                                        "{\n" +
                                                "    \"id\": \"\",\n" +
                                                "    \"worker_id\": \"Dan-PC\",\n" +
                                                "    \"version\": \"0.10.0\",\n" +
                                                "    \"kind\": \"opencl/cuda\",\n" +
                                                "    \"ua\": \"WildRig/0.10.0 (Windows NT 6.1; Win64; x64) libuv/1.21.0 msvc/2015\",\n" +
                                                "    \"cpu\": {\n" +
                                                "        \"brand\": \"Intel(R) Core(TM) i7 CPU         965  @ 3.20GHz\",\n" +
                                                "        \"aes\": false,\n" +
                                                "        \"x64\": true,\n" +
                                                "        \"sockets\": 1\n" +
                                                "    },\n" +
                                                "    \"algo\": \"wildkeccak\",\n" +
                                                "    \"donate_level\": 2,\n" +
                                                "    \"uptime\": 370,\n" +
                                                "    \"hashrate\": {\n" +
                                                "        \"total\": [\n" +
                                                "            693.13,\n" +
                                                "            693.71,\n" +
                                                "            0.0\n" +
                                                "        ],\n" +
                                                "        \"highest\": 696.4,\n" +
                                                "        \"threads\": [\n" +
                                                "            [\n" +
                                                "                693.13,\n" +
                                                "                693.71,\n" +
                                                "                0.0\n" +
                                                "            ]\n" +
                                                "        ]\n" +
                                                "    },\n" +
                                                "    \"results\": {\n" +
                                                "        \"diff_current\": 128888516,\n" +
                                                "        \"shares_good\": 3,\n" +
                                                "        \"shares_total\": 3,\n" +
                                                "        \"avg_time\": 122,\n" +
                                                "        \"hashes_total\": 213008131,\n" +
                                                "        \"best\": [\n" +
                                                "            0,\n" +
                                                "            0,\n" +
                                                "            0,\n" +
                                                "            0,\n" +
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
                                                "        \"pool\": \"bbr.luckypool.io:5577\",\n" +
                                                "        \"uptime\": 368,\n" +
                                                "        \"ping\": 88,\n" +
                                                "        \"failures\": 0,\n" +
                                                "        \"error_log\": []\n" +
                                                "    }\n" +
                                                "}"))),
                new MinerStats.Builder()
                        .setApiIp("127.0.0.1")
                        .setApiPort(44444)
                        .addPool(
                                new Pool.Builder()
                                        .setName("bbr.luckypool.io:5577")
                                        .setStatus(true, true)
                                        .setPriority(0)
                                        .setCounts(
                                                3,
                                                0,
                                                0)
                                        .build())
                        .addRig(
                                new Rig.Builder()
                                        .setHashRate(new BigDecimal("693.13"))
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
                                        .build())
                        .build());
    }
}