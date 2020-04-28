package mn.foreman.dragonmint;

import mn.foreman.model.Detection;
import mn.foreman.util.AbstractDetectITest;
import mn.foreman.util.http.FakeHttpMinerServer;
import mn.foreman.util.http.HttpHandler;

import com.google.common.collect.ImmutableMap;

/** Tests detection of a Dragonmint. */
public class DragonmintDetectITest
        extends AbstractDetectITest {

    /** Constructor. */
    public DragonmintDetectITest() {
        super(
                new DragonmintDetectionStrategy<>(
                        DragonmintType::forType,
                        "DragonMint"),
                "127.0.0.1",
                8888,
                ImmutableMap.of(
                        "username",
                        "username",
                        "password",
                        "password"),
                () -> new FakeHttpMinerServer(
                        8888,
                        ImmutableMap.of(
                                "/api/overview",
                                new HttpHandler(
                                        "",
                                        "{\n" +
                                                "  \"success\": true,\n" +
                                                "  \"type\": \"T1\",\n" +
                                                "  \"hardware\": {\n" +
                                                "    \"status\": \"15:38:31 up 10 min, load average: 0.30, 0.23, 0.11\",\n" +
                                                "    \"memUsed\": 92300,\n" +
                                                "    \"memFree\": 158244,\n" +
                                                "    \"memTotal\": 250544,\n" +
                                                "    \"cacheUsed\": 72180,\n" +
                                                "    \"cacheFree\": 194592,\n" +
                                                "    \"cacheTotal\": 266772\n" +
                                                "  },\n" +
                                                "  \"network\": {\n" +
                                                "    \"dhcp\": \"static\",\n" +
                                                "    \"ipaddress\": \"192.168.0.151\",\n" +
                                                "    \"netmask\": \"255.255.255.0\",\n" +
                                                "    \"gateway\": \"192.168.0.1\",\n" +
                                                "    \"dns1\": \"8.8.8.8\",\n" +
                                                "    \"dns2\": \"8.8.4.4\"\n" +
                                                "  },\n" +
                                                "  \"version\": {\n" +
                                                "    \"hwver\": \"g19\",\n" +
                                                "    \"ethaddr\": \"00:0a:35:00:00:00\",\n" +
                                                "    \"build_date\": \"9th of April 2018 03:20 PM\",\n" +
                                                "    \"platform_v\": \"t1_20180409_152051\"\n" +
                                                "  }\n" +
                                                "}"))),
                Detection.builder()
                        .minerType(DragonmintType.DRAGONMINT_T1)
                        .ipAddress("127.0.0.1")
                        .port(8888)
                        .parameters(
                                ImmutableMap.of(
                                        "username",
                                        "username",
                                        "password",
                                        "password"))
                        .build());
    }
}