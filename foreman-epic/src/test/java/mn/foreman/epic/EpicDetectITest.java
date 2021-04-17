package mn.foreman.epic;

import mn.foreman.model.Detection;
import mn.foreman.model.NullMacStrategy;
import mn.foreman.util.AbstractDetectITest;
import mn.foreman.util.http.FakeHttpMinerServer;
import mn.foreman.util.http.HttpHandler;

import com.google.common.collect.ImmutableMap;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

/** Tests detection of an epic. */
@RunWith(Parameterized.class)
public class EpicDetectITest
        extends AbstractDetectITest {

    /**
     * Constructor.
     *
     * @param workerPreferred Whether or not the worker is preferred.
     * @param detectionArgs   The arguments in the detection.
     */
    public EpicDetectITest(
            final boolean workerPreferred,
            final Map<String, Object> detectionArgs) {
        super(
                new EpicDetectionStrategy(new NullMacStrategy()),
                "127.0.0.1",
                8888,
                ImmutableMap.of(
                        "workerPreferred",
                        Boolean.toString(workerPreferred),
                        "username",
                        "username",
                        "password",
                        "password"),
                () -> new FakeHttpMinerServer(
                        8888,
                        ImmutableMap.of(
                                "/summary",
                                new HttpHandler(
                                        "",
                                        "{\n" +
                                                "  \"Hostname\": \"epicminer43003a\",\n" +
                                                "  \"Preset\": \"Efficiency\",\n" +
                                                "  \"Software\": \"epic-miner v2.0.6\",\n" +
                                                "  \"Mining\": {\n" +
                                                "    \"Coin\": \"Sia\",\n" +
                                                "    \"Algorithm\": \"Blake2b\"\n" +
                                                "  },\n" +
                                                "  \"Stratum\": {\n" +
                                                "    \"Current Pool\": \"sc.luxor.tech:700\",\n" +
                                                "    \"Current User\": \"earlmai.apitesting-43003a\",\n" +
                                                "    \"Average Latency\": 0.23497768\n" +
                                                "  },\n" +
                                                "  \"Session\": {\n" +
                                                "    \"Startup Timestamp\": 1617918043,\n" +
                                                "    \"Startup String\": \"Thu, 08 Apr 2021 21:40:43 +0000\",\n" +
                                                "    \"Uptime\": 749962,\n" +
                                                "    \"Last Work Timestamp\": 1618667989,\n" +
                                                "    \"WorkReceived\": 25641,\n" +
                                                "    \"Active HBs\": 3,\n" +
                                                "    \"Average MHs\": 1828940.2402133332,\n" +
                                                "    \"LastAverageMHs\": {\n" +
                                                "      \"Hashrate\": 1815381.2670691558,\n" +
                                                "      \"Timestamp\": 1618664400\n" +
                                                "    },\n" +
                                                "    \"Accepted\": 195336,\n" +
                                                "    \"Rejected\": 165,\n" +
                                                "    \"Submitted\": 195501,\n" +
                                                "    \"Last Accepted Share Timestamp\": 1618667999,\n" +
                                                "    \"Difficulty\": 1750.0\n" +
                                                "  },\n" +
                                                "  \"HBs\": [\n" +
                                                "    {\n" +
                                                "      \"Index\": 0,\n" +
                                                "      \"Input Voltage\": 12.234,\n" +
                                                "      \"Output Voltage\": 0.625,\n" +
                                                "      \"Input Current\": 22.237791666666666,\n" +
                                                "      \"Output Current\": 349.71,\n" +
                                                "      \"Input Power\": 272.02066666666667,\n" +
                                                "      \"Output Power\": 218.23066666666668,\n" +
                                                "      \"Temperature\": 59,\n" +
                                                "      \"Core Clock\": 380.0\n" +
                                                "    },\n" +
                                                "    {\n" +
                                                "      \"Index\": 1,\n" +
                                                "      \"Input Voltage\": 12.234,\n" +
                                                "      \"Output Voltage\": 0.625,\n" +
                                                "      \"Input Current\": 23.290875,\n" +
                                                "      \"Output Current\": 356.987,\n" +
                                                "      \"Input Power\": 285.184,\n" +
                                                "      \"Output Power\": 222.68566666666666,\n" +
                                                "      \"Temperature\": 62,\n" +
                                                "      \"Core Clock\": 380.0\n" +
                                                "    },\n" +
                                                "    {\n" +
                                                "      \"Index\": 2,\n" +
                                                "      \"Input Voltage\": 12.203,\n" +
                                                "      \"Output Voltage\": 0.635,\n" +
                                                "      \"Input Current\": 23.446875,\n" +
                                                "      \"Output Current\": 364.118,\n" +
                                                "      \"Input Power\": 286.432,\n" +
                                                "      \"Output Power\": 230.92466666666667,\n" +
                                                "      \"Temperature\": 56,\n" +
                                                "      \"Core Clock\": 380.0\n" +
                                                "    }\n" +
                                                "  ],\n" +
                                                "  \"Fans\": {\n" +
                                                "    \"Fans Speed\": 50\n" +
                                                "  }\n" +
                                                "}"))),
                Detection.builder()
                        .minerType(EpicType.SC200)
                        .ipAddress("127.0.0.1")
                        .port(8888)
                        .parameters(detectionArgs)
                        .build());
    }

    /**
     * Test parameters
     *
     * @return The test parameters.
     */
    @Parameterized.Parameters
    public static Collection<Object[]> parameters() {
        return Arrays.asList(
                new Object[][]{
                        {
                                // Don't prefer worker name
                                false,
                                ImmutableMap.<String, Object>builder()
                                        .put(
                                                "username",
                                                "username")
                                        .put(
                                                "password",
                                                "password")
                                        .put(
                                                "workerPreferred",
                                                "false")
                                        .build()
                        },
                        {
                                // Prefer worker name
                                true,
                                ImmutableMap.<String, Object>builder()
                                        .put(
                                                "username",
                                                "username")
                                        .put(
                                                "password",
                                                "password")
                                        .put(
                                                "workerPreferred",
                                                "true")
                                        .put(
                                                "worker",
                                                "earlmai.apitesting-43003a")
                                        .build()
                        }
                }
        );
    }
}