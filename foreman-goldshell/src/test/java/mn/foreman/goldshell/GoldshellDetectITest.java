package mn.foreman.goldshell;

import mn.foreman.model.Detection;
import mn.foreman.util.AbstractDetectITest;
import mn.foreman.util.http.FakeHttpMinerServer;
import mn.foreman.util.http.HttpHandler;

import com.google.common.collect.ImmutableMap;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/** Tests detection of a goldshell. */
@RunWith(Parameterized.class)
public class GoldshellDetectITest
        extends AbstractDetectITest {

    /**
     * Constructor.
     *
     * @param workerPreferred Whether or not the worker is preferred.
     * @param detectionArgs   The arguments in the detection.
     */
    public GoldshellDetectITest(
            final boolean workerPreferred,
            final Map<String, Object> detectionArgs) {
        super(
                new GoldshellDetectionStrategy(
                        new GoldshellMacStrategy(
                                "127.0.0.1",
                                8080,
                                1,
                                TimeUnit.SECONDS),
                        1,
                        TimeUnit.SECONDS),
                "127.0.0.1",
                8080,
                ImmutableMap.of(
                        "workerPreferred",
                        Boolean.toString(workerPreferred),
                        "username",
                        "username",
                        "password",
                        "password"),
                () -> new FakeHttpMinerServer(
                        8080,
                        ImmutableMap.of(
                                "/mcb/status",
                                new HttpHandler(
                                        "",
                                        "{\n" +
                                                "    \"hardware\": \"10.40.HA\",\n" +
                                                "    \"model\": \"Goldshell-CK5\",\n" +
                                                "    \"firmware\": \"2.0.1\"\n" +
                                                "}"),
                                "/mcb/pools",
                                new HttpHandler(
                                        "",
                                        "[\n" +
                                                "    {\n" +
                                                "        \"url\": \"stratum+tcp://us-ckb.2miners.com:6464\",\n" +
                                                "        \"legal\": true,\n" +
                                                "        \"active\": true,\n" +
                                                "        \"dragid\": 0,\n" +
                                                "        \"user\": \"[REDACTED]\",\n" +
                                                "        \"pool-priority\": 0,\n" +
                                                "        \"pass\": \"[REDACTED]\"\n" +
                                                "    },\n" +
                                                "    {\n" +
                                                "        \"url\": \"stratum+tcp://ckb.f2pool.com:4300\",\n" +
                                                "        \"legal\": true,\n" +
                                                "        \"active\": false,\n" +
                                                "        \"dragid\": 1,\n" +
                                                "        \"user\": \"[REDACTED]\",\n" +
                                                "        \"pool-priority\": 1,\n" +
                                                "        \"pass\": \"[REDACTED]\"\n" +
                                                "    }\n" +
                                                "]"),
                                "/mcb/setting",
                                new HttpHandler(
                                        "",
                                        "{\"select\":0,\"tempcontrol\":true,\"ledcontrol\":false,\"manualPowerplan\":\"800 MHz 0.42 V 100 RPM 100 RPM\",\"name\":\"28:E2:97:4D:55:10\",\"powerplans\":[{\"info\":\"800 MHz 0.42 V 100 RPM 100 RPM\",\"level\":0}],\"manual\":false,\"version\":\"v1.0\"}"))),
                Detection.builder()
                        .minerType(GoldshellType.CK5)
                        .ipAddress("127.0.0.1")
                        .port(8080)
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
                                                "mac",
                                                "28:e2:97:4d:55:10")
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
                                                "mac",
                                                "28:e2:97:4d:55:10")
                                        .put(
                                                "workerPreferred",
                                                "true")
                                        .put(
                                                "worker",
                                                "[REDACTED]")
                                        .build()
                        }
                }
        );
    }
}