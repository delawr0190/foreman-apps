package mn.foreman.nbminer;

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

/** Runs an integration tests using {@link Nbminer} against a fake API. */
public class NbminerITest
        extends AbstractApiITest {

    /** Constructor. */
    public NbminerITest() {
        super(
                new Nbminer(
                        "127.0.0.1",
                        22333),
                new FakeHttpMinerServer(
                        22333,
                        ImmutableMap.of(
                                "/api/v1/status",
                                new HttpHandler(
                                        "",
                                        "{\n" +
                                                "  \"miner\": {\n" +
                                                "    \"devices\": [\n" +
                                                "      {\n" +
                                                "        \"core_clock\": 1556,\n" +
                                                "        \"core_utilization\": 100,\n" +
                                                "        \"fan\": 36,\n" +
                                                "        \"hashrate\": 1499,\n" +
                                                "        \"hashrate2\": \"23.0 M\",\n" +
                                                "        \"hashrate_raw\": 1499,\n" +
                                                "        \"hashrate2_raw\": 23030000,\n" +
                                                "        \"id\": 0,\n" +
                                                "        \"info\": \"GeForce GTX 1080 Ti 11178 MB\",\n" +
                                                "        \"power\": 182,\n" +
                                                "        \"temperature\": 65\n" +
                                                "      },\n" +
                                                "      {\n" +
                                                "        \"core_clock\": 1518,\n" +
                                                "        \"core_utilization\": 100,\n" +
                                                "        \"fan\": 34,\n" +
                                                "        \"hashrate\": 1490,\n" +
                                                "        \"id\": 1,\n" +
                                                "        \"info\": \"GeForce GTX 1080 Ti 11178 MB\",\n" +
                                                "        \"power\": 172,\n" +
                                                "        \"temperature\": 62\n" +
                                                "      }\n" +
                                                "    ],\n" +
                                                "    \"total_hashrate\": 2989,\n" +
                                                "    \"total_hashrate_raw\": 2989,\n" +
                                                "    \"total_hashrate2\": \"48.3 M\",\n" +
                                                "    \"total_hashrate2_raw\": 48308746,\n" +
                                                "    \"total_power_consume\": 354\n" +
                                                "  },\n" +
                                                "  \"start_time\": 1532482659,\n" +
                                                "  \"stratum\": {\n" +
                                                "    \"accepted_share_rate\": 0.99,\n" +
                                                "    \"accepted_shares\": 99,\n" +
                                                "    \"password\": \"\",\n" +
                                                "    \"rejected_share_rate\": 0.01,\n" +
                                                "    \"rejected_shares\": 1,\n" +
                                                "    \"url\": \"btm.pool.zhizh.com:3859\",\n" +
                                                "    \"use_ssl\": false,\n" +
                                                "    \"user\": \"bmxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.test\",\n" +
                                                "    \"difficulty\": \"0003ffff\",\n" +
                                                "    \"latency\": 65\n" +
                                                "  },\n" +
                                                "  \"version\": \"v10.0\"\n" +
                                                "}"))),
                new MinerStats.Builder()
                        .setApiIp("127.0.0.1")
                        .setApiPort(22333)
                        .addPool(
                                new Pool.Builder()
                                        .setName("btm.pool.zhizh.com:3859")
                                        .setStatus(true, true)
                                        .setPriority(0)
                                        .setCounts(99, 1, 0)
                                        .build())
                        .addRig(
                                new Rig.Builder()
                                        .setHashRate(new BigDecimal("2989"))
                                        .addGpu(
                                                new Gpu.Builder()
                                                        .setName("GeForce GTX 1080 Ti 11178 MB")
                                                        .setTemp(65)
                                                        .setIndex(0)
                                                        .setBus(0)
                                                        .setFans(
                                                                new FanInfo.Builder()
                                                                        .setCount(1)
                                                                        .addSpeed(36)
                                                                        .setSpeedUnits("%")
                                                                        .build())
                                                        .setFreqInfo(
                                                                new FreqInfo.Builder()
                                                                        .setFreq(1556)
                                                                        .setMemFreq(0)
                                                                        .build())
                                                        .build())
                                        .addGpu(
                                                new Gpu.Builder()
                                                        .setName("GeForce GTX 1080 Ti 11178 MB")
                                                        .setTemp(62)
                                                        .setIndex(1)
                                                        .setBus(0)
                                                        .setFans(
                                                                new FanInfo.Builder()
                                                                        .setCount(1)
                                                                        .addSpeed(34)
                                                                        .setSpeedUnits("%")
                                                                        .build())
                                                        .setFreqInfo(
                                                                new FreqInfo.Builder()
                                                                        .setFreq(1518)
                                                                        .setMemFreq(0)
                                                                        .build())
                                                        .build())
                                        .build())
                        .build());
    }
}