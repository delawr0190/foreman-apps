package mn.foreman.antminer;

import mn.foreman.antminer.util.AntminerTestUtils;
import mn.foreman.model.ApplicationConfiguration;
import mn.foreman.util.AbstractBlinkActionITest;
import mn.foreman.util.http.FakeHttpMinerServer;
import mn.foreman.util.http.HttpHandler;
import mn.foreman.util.http.MultiHandler;
import mn.foreman.util.http.ServerHandler;

import com.google.common.collect.ImmutableMap;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

/** Tests blinking LEDs on Antminers. */
@RunWith(Parameterized.class)
public class AntminerBlinkITest
        extends AbstractBlinkActionITest {

    /**
     * Constructor.
     *
     * @param httpHandlers The HTTP handlers.
     */
    public AntminerBlinkITest(final Map<String, ServerHandler> httpHandlers) {
        super(
                8080,
                new StockBlinkStrategy(
                        "antMiner Configuration",
                        new ApplicationConfiguration()),
                Collections.singletonList(
                        () -> new FakeHttpMinerServer(
                                8080,
                                httpHandlers)),
                Collections.emptyMap(),
                true,
                false);
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
                                // Antminer S17+
                                ImmutableMap.of(
                                        "/cgi-bin/get_miner_conf.cgi",
                                        new HttpHandler(
                                                "",
                                                "{\n" +
                                                        "\"pools\" : [\n" +
                                                        "{\n" +
                                                        "\"url\" : \"btc-us.f2pool.com:1314\",\n" +
                                                        "\"user\" : \"xxx\",\n" +
                                                        "\"pass\" : \"x\"\n" +
                                                        "},\n" +
                                                        "{\n" +
                                                        "\"url\" : \"btc-us.f2pool.com:25\",\n" +
                                                        "\"user\" : \"xxx\",\n" +
                                                        "\"pass\" : \"x\"\n" +
                                                        "},\n" +
                                                        "{\n" +
                                                        "\"url\" : \"btc-us.f2pool.com:25\",\n" +
                                                        "\"user\" : \"xxx\",\n" +
                                                        "\"pass\" : \"x\"\n" +
                                                        "}\n" +
                                                        "]\n" +
                                                        ",\n" +
                                                        "\"api-listen\" : true,\n" +
                                                        "\"api-network\" : true,\n" +
                                                        "\"api-groups\" : \"A:stats:pools:devs:summary:version\",\n" +
                                                        "\"api-allow\" : \"A:0/0,W:*\",\n" +
                                                        "\"bitmain-use-vil\" : true,\n" +
                                                        "\"bitmain-work-mode\" : \"0\",\n" +
                                                        "\"bitmain-freq\" : \"O\",\n" +
                                                        "\"bitmain-voltage\" : \"1700\"\n" +
                                                        "}",
                                                AntminerTestUtils::validateDigest),
                                        "/cgi-bin/blink.cgi",
                                        new MultiHandler(
                                                new HttpHandler(
                                                        "action=startBlink",
                                                        "",
                                                        AntminerTestUtils::validateDigest),
                                                new HttpHandler(
                                                        "action=stopBlink",
                                                        "",
                                                        AntminerTestUtils::validateDigest)))
                        },
                        {
                                // Antminer S19 Pro
                                ImmutableMap.of(
                                        "/cgi-bin/get_miner_conf.cgi",
                                        new HttpHandler(
                                                "",
                                                "{\n" +
                                                        "\"pools\" : [\n" +
                                                        "{\n" +
                                                        "\"url\" : \"stratum+tcp://xxx:3333\",\n" +
                                                        "\"user\" : \"xxx\",\n" +
                                                        "\"pass\" : \"xxx\"\n" +
                                                        "},\n" +
                                                        "{\n" +
                                                        "\"url\" : \"stratum+tcp://xxx:25\",\n" +
                                                        "\"user\" : \"xxx\",\n" +
                                                        "\"pass\" : \"xxx\"\n" +
                                                        "},\n" +
                                                        "{\n" +
                                                        "\"url\" : \"stratum+tcp://xxx:443\",\n" +
                                                        "\"user\" : \"xxx\",\n" +
                                                        "\"pass\" : \"xxx\"\n" +
                                                        "}\n" +
                                                        "]\n" +
                                                        ",\n" +
                                                        "\"api-listen\" : true,\n" +
                                                        "\"api-network\" : true,\n" +
                                                        "\"api-groups\" : \"A:stats:pools:devs:summary:version\",\n" +
                                                        "\"api-allow\" : \"A:0/0,W:*\",\n" +
                                                        "\"bitmain-fan-ctrl\" : false,\n" +
                                                        "\"bitmain-fan-pwm\" : \"100\",\n" +
                                                        "\"bitmain-use-vil\" : true,\n" +
                                                        "\"bitmain-freq\" : \"500\",\n" +
                                                        "\"bitmain-voltage\" : \"1280\",\n" +
                                                        "\"bitmain-ccdelay\" : \"1\",\n" +
                                                        "\"bitmain-pwth\" : \"1\",\n" +
                                                        "\"bitmain-work-mode\" : \"0\",\n" +
                                                        "\"bitmain-freq-level\" : \"100\"\n" +
                                                        "}",
                                                AntminerTestUtils::validateDigest),
                                        "/cgi-bin/blink.cgi",
                                        new MultiHandler(
                                                new HttpHandler(
                                                        "{\"blink\":true}",
                                                        "{\"code\":\"B000\"}",
                                                        AntminerTestUtils::validateDigest),
                                                new HttpHandler(
                                                        "{\"blink\":false}",
                                                        "{\"code\":\"B100\"}",
                                                        AntminerTestUtils::validateDigest)))
                        }
                });
    }
}