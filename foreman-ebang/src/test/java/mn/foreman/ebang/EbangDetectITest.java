package mn.foreman.ebang;

import mn.foreman.model.Detection;
import mn.foreman.util.AbstractDetectITest;
import mn.foreman.util.http.FakeHttpMinerServer;
import mn.foreman.util.http.HttpHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/** Tests detection of an Ebang. */
@RunWith(Parameterized.class)
public class EbangDetectITest
        extends AbstractDetectITest {

    /**
     * Constructor.
     *
     * @param workerPreferred Whether or not the workername is preferred.
     * @param detectionArgs   The arguments in the detection.
     */
    public EbangDetectITest(
            final boolean workerPreferred,
            final Map<String, Object> detectionArgs) {
        super(
                new EbangDetectionStrategy(
                        new EbangMacStrategy(
                                "127.0.0.1",
                                8080,
                                "username",
                                "password",
                                1,
                                TimeUnit.SECONDS,
                                new ObjectMapper()),
                        1,
                        TimeUnit.SECONDS,
                        new ObjectMapper()),
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
                                "/user/login",
                                new HttpHandler(
                                        "username=username&word=password&yuyan=1&login=Login&get_password=",
                                        Collections.emptyMap(),
                                        "<html></html>",
                                        ImmutableMap.of(
                                                "Set-Cookie",
                                                "-http-session-=1::http.session::647531ff4c6d47243a8ed9a41a26e473; path=/; domain=127.0.0.1; httponly")),
                                "/Cgminer/CgminerGetVal",
                                new HttpHandler(
                                        "",
                                        ImmutableMap.of(
                                                "Cookie",
                                                "-http-session-=1::http.session::647531ff4c6d47243a8ed9a41a26e473"),
                                        "{\"status\": 1, \"feedback\": {\"Mpassword3\":\"\",\"Mip3\":\"stratum+tcp://btc.ss.poolin.com:25\",\"Mwork3\":\"\",\"Mpassword2\":\"\",\"Mip2\":\"stratum+tcp://yyy.com:1883\",\"Mip1\":\"stratum+tcp://aaa.com:3001\",\"Mwork2\":\"\",\"Mpassword1\":\"\",\"Mwork1\":\"111\"}}",
                                        Collections.emptyMap()),
                                "/Status/getsystemstatus",
                                new HttpHandler(
                                        "",
                                        ImmutableMap.of(
                                                "Cookie",
                                                "-http-session-=1::http.session::647531ff4c6d47243a8ed9a41a26e473"),
                                        "{\"status\": 1, \"feedback\": {\"memoryfree\":74,\"CPUavailability\":2,\"systemsoftwareversion\":\"soft_v9.1.0.34\",\"devicefan\":5640,\"productname\":\"E9I\",\"devicefan2\":5640,\"device4temp\":65,\"device3temp\":0,\"bank1snmpversion\":\"soft_v9.1.0.34\",\"ethrate\":\"11754b/s\",\"bank2snmpversion\":\"soft_v9.1.0.34\",\"devicepn\":\"**********\",\"device1temp\":\"65,59|58,56\",\"fpgawareversion\":\"Fpga v1.0.13\",\"Productdescription\":\"The Miner Control Device\",\"hardwareversion\":\"EBAZ4303-13-1.1.1.1\",\"flashResidualspace\":57,\"device2temp\":\"61,57|62,58\"}}",
                                        Collections.emptyMap()),
                                "/admininfo/Getadmininfo",
                                new HttpHandler(
                                        "",
                                        ImmutableMap.of(
                                                "Cookie",
                                                "-http-session-=1::http.session::647531ff4c6d47243a8ed9a41a26e473"),
                                        "{\"status\": 1, \"feedback\": {\"newipaddr\":\"10.0.4.23\",\"macaddr\":\"8C:C7:D0:1B:76:F3\",\"dnsaddr\":\"10.0.0.1\",\"ipmask\":\"255.255.0.0\",\"geteway\":\"10.0.0.1\",\"dhcpstatus\":0,\"dhcpnc\":0,\"dhcpovertime\":10}}",
                                        Collections.emptyMap()))),
                Detection.builder()
                        .minerType(EbangType.E9I)
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
                                                "8c:c7:d0:1b:76:f3")
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
                                                "8c:c7:d0:1b:76:f3")
                                        .put(
                                                "workerPreferred",
                                                "true")
                                        .put(
                                                "worker",
                                                "111")
                                        .build()
                        }
                }
        );
    }
}