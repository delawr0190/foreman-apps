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
public class JceminerITest
        extends AbstractApiITest {

    /** Constructor. */
    public JceminerITest() {
        super(
                new Jceminer(
                        "jceminer",
                        "127.0.0.1",
                        3434),
                new FakeHttpMinerServer(
                        3434,
                        ImmutableMap.of(
                                "/",
                                new HttpHandler(
                                        "",
                                        "{\n" +
                                                "  \"hashrate\":\n" +
                                                "  {\n" +
                                                "    \"thread_0\": 771.17,\n" +
                                                "    \"thread_1\": 801.35,\n" +
                                                "    \"thread_2\": 796.32,\n" +
                                                "    \"thread_3\": 742.88,\n" +
                                                "    \"thread_4\": 585.85,\n" +
                                                "    \"thread_5\": 585.85,\n" +
                                                "    \"thread_6\": 796.32,\n" +
                                                "    \"thread_7\": 796.32,\n" +
                                                "    \"thread_8\": 708.87,\n" +
                                                "    \"thread_9\": 801.69,\n" +
                                                "    \"thread_all\": [771.17, 801.35, 796.32, 742.88, 585.85, 585.85, 796.32, 796.32, 708.87, 801.69],\n" +
                                                "    \"thread_gpu\": [771.17, 801.35, 796.32, 742.88, 1171.70, 796.32, 796.32, 708.87, 801.69],\n" +
                                                "    \"total\": 7386.59,\n" +
                                                "    \"max\": 7390.37\n" +
                                                "  },\n" +
                                                "  \"result\":\n" +
                                                "  {\n" +
                                                "     \"wallet\": \"WALLET.219000\",\n" +
                                                "     \"pool\": \"bittube.miner.rocks:5555\",\n" +
                                                "     \"ssl\": false,\n" +
                                                "     \"currency\": \"BitTube (TUBE)\",\n" +
                                                "     \"difficulty\": 219008,\n" +
                                                "     \"shares\": 4161,\n" +
                                                "     \"hashes\": 911292288,\n" +
                                                "     \"uptime\": \"34:56:08\",\n" +
                                                "     \"effective\": 7245.82\n" +
                                                "  },\n" +
                                                "  \"gpu_status\":\n" +
                                                "  [\n" +
                                                "    { \"index\": 0, \"temperature\": 69, \"fan\": 78, \"processor\": \"Ellesmere\", \"memory\": 4096, \"good_shares\": 429, \"bad_shares\": 0 },\n" +
                                                "    { \"index\": 1, \"temperature\": 69, \"fan\": 78, \"processor\": \"Ellesmere\", \"memory\": 4096, \"good_shares\": 464, \"bad_shares\": 0 },\n" +
                                                "    { \"index\": 2, \"temperature\": 64, \"fan\": 74, \"processor\": \"Ellesmere\", \"memory\": 4096, \"good_shares\": 484, \"bad_shares\": 0 },\n" +
                                                "    { \"index\": 3, \"temperature\": 64, \"fan\": 74, \"processor\": \"Ellesmere\", \"memory\": 4096, \"good_shares\": 428, \"bad_shares\": 0 },\n" +
                                                "    { \"index\": 4, \"temperature\": 57, \"fan\": 66, \"processor\": \"Ellesmere\", \"memory\": 8192, \"good_shares\": 611, \"bad_shares\": 0 },\n" +
                                                "    { \"index\": 5, \"temperature\": 75, \"fan\": 88, \"processor\": \"Ellesmere\", \"memory\": 4096, \"good_shares\": 469, \"bad_shares\": 0 },\n" +
                                                "    { \"index\": 6, \"temperature\": 75, \"fan\": 88, \"processor\": \"Ellesmere\", \"memory\": 4096, \"good_shares\": 468, \"bad_shares\": 0 },\n" +
                                                "    { \"index\": 7, \"temperature\": 75, \"fan\": 86, \"processor\": \"Ellesmere\", \"memory\": 4096, \"good_shares\": 391, \"bad_shares\": 0 },\n" +
                                                "    { \"index\": 8, \"temperature\": 68, \"fan\": 78, \"processor\": \"Ellesmere\", \"memory\": 4096, \"good_shares\": 418, \"bad_shares\": 0 }\n" +
                                                "  ],\n" +
                                                "  \"miner\":\n" +
                                                "  {\n" +
                                                "     \"version\": \"jce/0.32e/gpu\",\n" +
                                                "     \"platform\": \"Intel(R) Pentium(R) CPU G4400 @ 3.30GHz\",\n" +
                                                "     \"system\": \"Windows 64-bits\",\n" +
                                                "     \"algorithm\": \"13\"\n" +
                                                "  }\n" +
                                                "}"))),
                new MinerStats.Builder()
                        .setName("jceminer")
                        .setApiIp("127.0.0.1")
                        .setApiPort(3434)
                        .addPool(
                                new Pool.Builder()
                                        .setName("bittube.miner.rocks:5555")
                                        .setStatus(true, true)
                                        .setPriority(0)
                                        .setCounts(4162, 0, 0)
                                        .build())
                        .addRig(
                                new Rig.Builder()
                                        .setName("jceminer_jce/0.32e/gpu")
                                        .setHashRate(new BigDecimal("7386.59"))
                                        .addGpu(
                                                new Gpu.Builder()
                                                        .setName("GPU 0")
                                                        .setIndex(0)
                                                        .setBus(0)
                                                        .setTemp(69)
                                                        .setFans(
                                                                new FanInfo.Builder()
                                                                        .setCount(1)
                                                                        .addSpeed(78)
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
                                                        .setTemp(69)
                                                        .setFans(
                                                                new FanInfo.Builder()
                                                                        .setCount(1)
                                                                        .addSpeed(78)
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
                                                        .setTemp(64)
                                                        .setFans(
                                                                new FanInfo.Builder()
                                                                        .setCount(1)
                                                                        .addSpeed(74)
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
                                                        .setTemp(64)
                                                        .setFans(
                                                                new FanInfo.Builder()
                                                                        .setCount(1)
                                                                        .addSpeed(74)
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
                                                        .setTemp(57)
                                                        .setFans(
                                                                new FanInfo.Builder()
                                                                        .setCount(1)
                                                                        .addSpeed(66)
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
                                                        .setTemp(75)
                                                        .setFans(
                                                                new FanInfo.Builder()
                                                                        .setCount(1)
                                                                        .addSpeed(88)
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
                                                        .setTemp(75)
                                                        .setFans(
                                                                new FanInfo.Builder()
                                                                        .setCount(1)
                                                                        .addSpeed(88)
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
                                                        .setTemp(75)
                                                        .setFans(
                                                                new FanInfo.Builder()
                                                                        .setCount(1)
                                                                        .addSpeed(86)
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
                                                        .setTemp(68)
                                                        .setFans(
                                                                new FanInfo.Builder()
                                                                        .setCount(1)
                                                                        .addSpeed(78)
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