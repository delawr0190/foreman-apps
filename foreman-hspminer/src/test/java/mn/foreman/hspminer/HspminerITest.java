package mn.foreman.hspminer;

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

/** Runs an integration tests using {@link Hspminer} against a fake API. */
public class HspminerITest
        extends AbstractApiITest {

    /** Constructor. */
    public HspminerITest() {
        super(
                new HspminerFactory().create(
                        ImmutableMap.of(
                                "apiIp",
                                "127.0.0.1",
                                "apiPort",
                                "16666")),
                new FakeHttpMinerServer(
                        16666,
                        ImmutableMap.of(
                                "/api/v1",
                                new HttpHandler(
                                        "",
                                        "{\n" +
                                                "  \"report_time\": 1547553371,\n" +
                                                "  \"miner\": [\n" +
                                                "    {\n" +
                                                "      \"coin\": \"ae\",\n" +
                                                "      \"cur_time\": 1547553371,\n" +
                                                "      \"info\": \"\",\n" +
                                                "      \"reboot\": 0,\n" +
                                                "      \"start_time\": 1547553068,\n" +
                                                "      \"ae_worker\": \"Nvidia\",\n" +
                                                "      \"ae_pool\": \"ae.f2pool.com:7898\",\n" +
                                                "      \"aewallet\": \"ak\",\n" +
                                                "      \"ae_accept\": 38,\n" +
                                                "      \"ae_rejected\": 0,\n" +
                                                "      \"ae_giveup\": 0,\n" +
                                                "      \"ae_latency\": 375,\n" +
                                                "      \"ae_last_accept_time\": 1547553340,\n" +
                                                "      \"devices\": [\n" +
                                                "        {\n" +
                                                "          \"id\": 0,\n" +
                                                "          \"name\": \"GeForce GTX 1080 Ti\",\n" +
                                                "          \"pci\": \"1:0:0\",\n" +
                                                "          \"ae_hash\": 250,\n" +
                                                "          \"fan\": 49,\n" +
                                                "          \"core\": 1695,\n" +
                                                "          \"mem\": 5508,\n" +
                                                "          \"power\": 126,\n" +
                                                "          \"temp\": 50,\n" +
                                                "          \"submit\": 6,\n" +
                                                "          \"submit_time\": 1547553327,\n" +
                                                "          \"warning\": \"\"\n" +
                                                "        },\n" +
                                                "        {\n" +
                                                "          \"id\": 1,\n" +
                                                "          \"name\": \"GeForce GTX 1080 Ti\",\n" +
                                                "          \"pci\": \"2:0:0\",\n" +
                                                "          \"ae_hash\": 248,\n" +
                                                "          \"fan\": 53,\n" +
                                                "          \"core\": 1695,\n" +
                                                "          \"mem\": 5508,\n" +
                                                "          \"power\": 153,\n" +
                                                "          \"temp\": 51,\n" +
                                                "          \"submit\": 6,\n" +
                                                "          \"submit_time\": 1547553330,\n" +
                                                "          \"warning\": \"\"\n" +
                                                "        },\n" +
                                                "        {\n" +
                                                "          \"id\": 2,\n" +
                                                "          \"name\": \"GeForce GTX 1080 Ti\",\n" +
                                                "          \"pci\": \"3:0:0\",\n" +
                                                "          \"ae_hash\": 248,\n" +
                                                "          \"fan\": 53,\n" +
                                                "          \"core\": 1695,\n" +
                                                "          \"mem\": 5508,\n" +
                                                "          \"power\": 120,\n" +
                                                "          \"temp\": 51,\n" +
                                                "          \"submit\": 5,\n" +
                                                "          \"submit_time\": 1547553267,\n" +
                                                "          \"warning\": \"\"\n" +
                                                "        },\n" +
                                                "        {\n" +
                                                "          \"id\": 3,\n" +
                                                "          \"name\": \"GeForce GTX 1080 Ti\",\n" +
                                                "          \"pci\": \"5:0:0\",\n" +
                                                "          \"ae_hash\": 249,\n" +
                                                "          \"fan\": 47,\n" +
                                                "          \"core\": 1695,\n" +
                                                "          \"mem\": 5508,\n" +
                                                "          \"power\": 125,\n" +
                                                "          \"temp\": 48,\n" +
                                                "          \"submit\": 6,\n" +
                                                "          \"submit_time\": 1547553320,\n" +
                                                "          \"warning\": \"\"\n" +
                                                "        },\n" +
                                                "        {\n" +
                                                "          \"id\": 4,\n" +
                                                "          \"name\": \"GeForce GTX 1080 Ti\",\n" +
                                                "          \"pci\": \"6:0:0\",\n" +
                                                "          \"ae_hash\": 251,\n" +
                                                "          \"fan\": 49,\n" +
                                                "          \"core\": 1695,\n" +
                                                "          \"mem\": 5508,\n" +
                                                "          \"power\": 125,\n" +
                                                "          \"temp\": 50,\n" +
                                                "          \"submit\": 9,\n" +
                                                "          \"submit_time\": 1547553340,\n" +
                                                "          \"warning\": \"\"\n" +
                                                "        },\n" +
                                                "        {\n" +
                                                "          \"id\": 5,\n" +
                                                "          \"name\": \"GeForce GTX 1080 Ti\",\n" +
                                                "          \"pci\": \"7:0:0\",\n" +
                                                "          \"ae_hash\": 247,\n" +
                                                "          \"fan\": 46,\n" +
                                                "          \"core\": 1695,\n" +
                                                "          \"mem\": 5508,\n" +
                                                "          \"power\": 121,\n" +
                                                "          \"temp\": 47,\n" +
                                                "          \"submit\": 6,\n" +
                                                "          \"submit_time\": 1547553323,\n" +
                                                "          \"warning\": \"\"\n" +
                                                "        }\n" +
                                                "      ]\n" +
                                                "    }\n" +
                                                "  ]\n" +
                                                "}"))),
                new MinerStats.Builder()
                        .setApiIp("127.0.0.1")
                        .setApiPort(16666)
                        .addPool(
                                new Pool.Builder()
                                        .setName("ae.f2pool.com:7898")
                                        .setPriority(0)
                                        .setStatus(true, true)
                                        .setCounts(38, 0, 0)
                                        .build())
                        .addRig(
                                new Rig.Builder()
                                        .setHashRate(new BigDecimal(1493))
                                        .addGpu(
                                                new Gpu.Builder()
                                                        .setIndex(0)
                                                        .setBus(1)
                                                        .setName("GeForce GTX 1080 Ti")
                                                        .setTemp(50)
                                                        .setFans(
                                                                new FanInfo.Builder()
                                                                        .setCount(1)
                                                                        .addSpeed(49)
                                                                        .setSpeedUnits("%")
                                                                        .build())
                                                        .setFreqInfo(
                                                                new FreqInfo.Builder()
                                                                        .setFreq(1695)
                                                                        .setMemFreq(5508)
                                                                        .build())
                                                        .build())
                                        .addGpu(
                                                new Gpu.Builder()
                                                        .setIndex(1)
                                                        .setBus(2)
                                                        .setName("GeForce GTX 1080 Ti")
                                                        .setTemp(51)
                                                        .setFans(
                                                                new FanInfo.Builder()
                                                                        .setCount(1)
                                                                        .addSpeed(53)
                                                                        .setSpeedUnits("%")
                                                                        .build())
                                                        .setFreqInfo(
                                                                new FreqInfo.Builder()
                                                                        .setFreq(1695)
                                                                        .setMemFreq(5508)
                                                                        .build())
                                                        .build())
                                        .addGpu(
                                                new Gpu.Builder()
                                                        .setIndex(2)
                                                        .setBus(3)
                                                        .setName("GeForce GTX 1080 Ti")
                                                        .setTemp(51)
                                                        .setFans(
                                                                new FanInfo.Builder()
                                                                        .setCount(1)
                                                                        .addSpeed(53)
                                                                        .setSpeedUnits("%")
                                                                        .build())
                                                        .setFreqInfo(
                                                                new FreqInfo.Builder()
                                                                        .setFreq(1695)
                                                                        .setMemFreq(5508)
                                                                        .build())
                                                        .build())
                                        .addGpu(
                                                new Gpu.Builder()
                                                        .setIndex(3)
                                                        .setBus(5)
                                                        .setName("GeForce GTX 1080 Ti")
                                                        .setTemp(48)
                                                        .setFans(
                                                                new FanInfo.Builder()
                                                                        .setCount(1)
                                                                        .addSpeed(47)
                                                                        .setSpeedUnits("%")
                                                                        .build())
                                                        .setFreqInfo(
                                                                new FreqInfo.Builder()
                                                                        .setFreq(1695)
                                                                        .setMemFreq(5508)
                                                                        .build())
                                                        .build())
                                        .addGpu(
                                                new Gpu.Builder()
                                                        .setIndex(4)
                                                        .setBus(6)
                                                        .setName("GeForce GTX 1080 Ti")
                                                        .setTemp(50)
                                                        .setFans(
                                                                new FanInfo.Builder()
                                                                        .setCount(1)
                                                                        .addSpeed(49)
                                                                        .setSpeedUnits("%")
                                                                        .build())
                                                        .setFreqInfo(
                                                                new FreqInfo.Builder()
                                                                        .setFreq(1695)
                                                                        .setMemFreq(5508)
                                                                        .build())
                                                        .build())
                                        .addGpu(
                                                new Gpu.Builder()
                                                        .setIndex(5)
                                                        .setBus(7)
                                                        .setName("GeForce GTX 1080 Ti")
                                                        .setTemp(47)
                                                        .setFans(
                                                                new FanInfo.Builder()
                                                                        .setCount(1)
                                                                        .addSpeed(46)
                                                                        .setSpeedUnits("%")
                                                                        .build())
                                                        .setFreqInfo(
                                                                new FreqInfo.Builder()
                                                                        .setFreq(1695)
                                                                        .setMemFreq(5508)
                                                                        .build())
                                                        .build())
                                        .build())
                        .build());
    }
}