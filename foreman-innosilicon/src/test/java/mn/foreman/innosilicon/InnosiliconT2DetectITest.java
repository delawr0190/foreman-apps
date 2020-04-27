package mn.foreman.innosilicon;

import mn.foreman.model.Detection;
import mn.foreman.util.AbstractDetectITest;
import mn.foreman.util.http.FakeHttpMinerServer;
import mn.foreman.util.http.HttpHandler;

import com.google.common.collect.ImmutableMap;

/** Tests detection of an Innosilicon T2. */
public class InnosiliconT2DetectITest
        extends AbstractDetectITest {

    /** Constructor. */
    public InnosiliconT2DetectITest() {
        super(
                new InnosiliconDetectionStrategy(),
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
                                                "  \"type\": \"T2\",\n" +
                                                "  \"hardware\": {\n" +
                                                "    \"status\": \"06:32:15 up 11 days, 22:58, load average: 0.00, 0.05, 0.07\",\n" +
                                                "    \"memUsed\": 124468,\n" +
                                                "    \"memFree\": 126096,\n" +
                                                "    \"memTotal\": 250564,\n" +
                                                "    \"cacheUsed\": 37232,\n" +
                                                "    \"cacheFree\": 145460,\n" +
                                                "    \"cacheTotal\": 182692\n" +
                                                "  },\n" +
                                                "  \"network\": {\n" +
                                                "    \"dhcp\": \"dhcp\",\n" +
                                                "    \"ipaddress\": \"10.30.9.17\",\n" +
                                                "    \"netmask\": \"255.255.255.0\",\n" +
                                                "    \"gateway\": \"10.30.9.254\",\n" +
                                                "    \"dns1\": \"192.168.16.101\",\n" +
                                                "    \"dns2\": \"10.30.60.126\"\n" +
                                                "  },\n" +
                                                "  \"version\": {\n" +
                                                "    \"hwver\": \"g19\",\n" +
                                                "    \"ethaddr\": \"a0: b0:45:21:5b: f0\",\n" +
                                                "    \"build_date\": \"23rd of May 2018 07:17 AM\",\n" +
                                                "    \"platform_v\": \"t2_20180523_071756\"\n" +
                                                "  }\n" +
                                                "}"))),
                Detection.builder()
                        .minerType(InnosiliconType.T2)
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