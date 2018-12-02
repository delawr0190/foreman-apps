package mn.foreman.mkxminer;

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

/** Runs an integration test using {@link Mkxminer} against a fake API. */
public class MkxminerITest
        extends AbstractApiITest {

    /** Constructor. */
    public MkxminerITest() {
        super(
                new MkxminerFactory().create(
                        ImmutableMap.of(
                                "apiIp",
                                "127.0.0.1",
                                "apiPort",
                                "5008")),
                new FakeHttpMinerServer(
                        5008,
                        ImmutableMap.of(
                                "/stats",
                                new HttpHandler(
                                        "",
                                        "{\n" +
                                                "  \"main\": {\n" +
                                                "      \"minerVersion\": \"4.2.0\",\n" +
                                                "      \"apiVersion\": 1,\n" +
                                                "      \"uptime\": 20\n" +
                                                "  },\n" +
                                                "  \"pool\": {\n" +
                                                "      \"address\": \"stratum+tcp://lyra2z.eu.nicehash.com:3365\",\n" +
                                                "      \"username\": \"1N2vf6Jm9jmScDUfwPeiicHNsXRSmvnjpL\",\n" +
                                                "      \"algorithm\": \"lyra2rev2\"\n" +
                                                "  },\n" +
                                                "  \"gpus\": {\n" +
                                                "      \"quantity\": 2,\n" +
                                                "      \"hashrate\": 0.24,\n" +
                                                "      \"alive\": 2,\n" +
                                                "      \"sick\": 0,\n" +
                                                "      \"dead\": 0\n" +
                                                "  },\n" +
                                                "  \"gpu\": [\n" +
                                                "    {\n" +
                                                "      \"name\": \"Radeon RX 580 Series\",\n" +
                                                "      \"status\": 0,\n" +
                                                "      \"hashrate\": 0.11,\n" +
                                                "      \"hwErrors\": 0,\n" +
                                                "      \"temperature\": 55.00,\n" +
                                                "      \"fan\": 37,\n" +
                                                "      \"gpuClock\": 1360,\n" +
                                                "      \"memClock\": 2000,\n" +
                                                "      \"activity\": 100,\n" +
                                                "      \"powertune\": -30\n" +
                                                "    },\n" +
                                                "    {\n" +
                                                "      \"name\": \"Radeon (TM) RX 480 Graphics\",\n" +
                                                "      \"status\": 0,\n" +
                                                "      \"hashrate\": 0.13,\n" +
                                                "      \"hwErrors\": 0,\n" +
                                                "      \"temperature\": 55.00,\n" +
                                                "      \"fan\": 28,\n" +
                                                "      \"gpuClock\": 1303,\n" +
                                                "      \"memClock\": 1750,\n" +
                                                "      \"activity\": 100,\n" +
                                                "      \"powertune\": -30\n" +
                                                "    }\n" +
                                                "  ]\n" +
                                                "}"))),
                new MinerStats.Builder()
                        .setApiIp("127.0.0.1")
                        .setApiPort(5008)
                        .addPool(
                                new Pool.Builder()
                                        .setName("lyra2z.eu.nicehash.com:3365")
                                        .setPriority(0)
                                        .setStatus(true, true)
                                        .setCounts(0, 0, 0)
                                        .build())
                        .addRig(
                                new Rig.Builder()
                                        .setHashRate(
                                                new BigDecimal("240000.00"))
                                        .addGpu(
                                                new Gpu.Builder()
                                                        .setIndex(0)
                                                        .setName("Radeon RX 580 Series")
                                                        .setTemp(55)
                                                        .setFans(
                                                                new FanInfo.Builder()
                                                                        .setCount(1)
                                                                        .addSpeed(37)
                                                                        .setSpeedUnits("%")
                                                                        .build())
                                                        .setFreqInfo(
                                                                new FreqInfo.Builder()
                                                                        .setFreq(1360)
                                                                        .setMemFreq(2000)
                                                                        .build())
                                                        .build())
                                        .addGpu(
                                                new Gpu.Builder()
                                                        .setIndex(1)
                                                        .setName("Radeon (TM) RX 480 Graphics")
                                                        .setTemp(55)
                                                        .setFans(
                                                                new FanInfo.Builder()
                                                                        .setCount(1)
                                                                        .addSpeed(28)
                                                                        .setSpeedUnits("%")
                                                                        .build())
                                                        .setFreqInfo(
                                                                new FreqInfo.Builder()
                                                                        .setFreq(1303)
                                                                        .setMemFreq(1750)
                                                                        .build())
                                                        .build())
                                        .build())
                        .build());
    }
}