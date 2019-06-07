package mn.foreman.xmrstak;

import mn.foreman.model.miners.MinerStats;
import mn.foreman.model.miners.Pool;
import mn.foreman.model.miners.cpu.Cpu;
import mn.foreman.util.AbstractApiITest;
import mn.foreman.util.http.FakeHttpMinerServer;
import mn.foreman.util.http.HttpHandler;

import com.google.common.collect.ImmutableMap;

import java.math.BigDecimal;

/** Runs an integration tests using {@link XmrstakCpu} against a fake API. */
public class XmrstakCpuITest
        extends AbstractApiITest {

    /** Constructor. */
    public XmrstakCpuITest() {
        super(
                new XmrstakFactory().create(
                        ImmutableMap.of(
                                "apiIp",
                                "127.0.0.1",
                                "apiPort",
                                "44444",
                                "type",
                                XmrstakType.CPU.name())),
                new FakeHttpMinerServer(
                        44444,
                        ImmutableMap.of(
                                "/api.json",
                                new HttpHandler(
                                        "",
                                        "{\n" +
                                                "  \"version\": \"jce\\/0.33q\\/cpu\",\n" +
                                                "  \"hashrate\": {\n" +
                                                "    \"threads\": [\n" +
                                                "      [\n" +
                                                "        587.9,\n" +
                                                "        587.9,\n" +
                                                "        587.9\n" +
                                                "      ],\n" +
                                                "      [\n" +
                                                "        601.2,\n" +
                                                "        601.2,\n" +
                                                "        601.2\n" +
                                                "      ],\n" +
                                                "      [\n" +
                                                "        600.1,\n" +
                                                "        600.1,\n" +
                                                "        600.1\n" +
                                                "      ]\n" +
                                                "    ],\n" +
                                                "    \"total\": [\n" +
                                                "      1789.1,\n" +
                                                "      1789.1,\n" +
                                                "      1789.1\n" +
                                                "    ],\n" +
                                                "    \"highest\": 1814.4\n" +
                                                "  },\n" +
                                                "  \"results\": {\n" +
                                                "    \"diff_current\": 114995,\n" +
                                                "    \"shares_good\": 3807,\n" +
                                                "    \"shares_total\": 3807,\n" +
                                                "    \"avg_time\": 46.3,\n" +
                                                "    \"hashes_total\": 310172988,\n" +
                                                "    \"best\": [\n" +
                                                "      862935495,\n" +
                                                "      505997320,\n" +
                                                "      353748848,\n" +
                                                "      76096311,\n" +
                                                "      74605012,\n" +
                                                "      71723261,\n" +
                                                "      69487839,\n" +
                                                "      67597059,\n" +
                                                "      52298881,\n" +
                                                "      41149431\n" +
                                                "    ],\n" +
                                                "    \"error_log\": [\n" +
                                                "      \n" +
                                                "    ]\n" +
                                                "  },\n" +
                                                "  \"connection\": {\n" +
                                                "    \"pool\": \"xtncelph.newpool.xyz:3333\",\n" +
                                                "    \"uptime\": 176251,\n" +
                                                "    \"ping\": 181,\n" +
                                                "    \"error_log\": [\n" +
                                                "      \n" +
                                                "    ]\n" +
                                                "  }\n" +
                                                "}"))),
                new MinerStats.Builder()
                        .setApiIp("127.0.0.1")
                        .setApiPort(44444)
                        .addPool(
                                new Pool.Builder()
                                        .setName("xtncelph.newpool.xyz:3333")
                                        .setStatus(true, true)
                                        .setPriority(0)
                                        .setCounts(
                                                3807,
                                                0,
                                                0)
                                        .build())
                        .addCpu(
                                new Cpu.Builder()
                                        .setName("CPU 0")
                                        .addThread(new BigDecimal("587.9"))
                                        .addThread(new BigDecimal("601.2"))
                                        .addThread(new BigDecimal("600.1"))
                                        .build())
                        .build());
    }
}