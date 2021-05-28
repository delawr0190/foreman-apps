package mn.foreman.goldshell;

import mn.foreman.model.ApplicationConfiguration;
import mn.foreman.util.AbstractBlinkActionITest;
import mn.foreman.util.http.FakeHttpMinerServer;
import mn.foreman.util.http.HttpHandler;
import mn.foreman.util.http.MultiHandler;

import com.google.common.collect.ImmutableMap;

import java.util.Collections;

/** Tests blinking LEDs on Whatsminers. */
public class GoldshellBlinkITest
        extends AbstractBlinkActionITest {

    /** Constructor. */
    public GoldshellBlinkITest() {
        super(
                8080,
                new GoldshellBlinkStrategy(
                        new ApplicationConfiguration()),
                Collections.singletonList(
                        () -> new FakeHttpMinerServer(
                                8080,
                                ImmutableMap.of(
                                        "/mcb/setting",
                                        new MultiHandler(
                                                new HttpHandler(
                                                        "",
                                                        "{\"select\":0,\"tempcontrol\":true,\"ledcontrol\":false,\"manualPowerplan\":\"800 MHz 0.42 V 100 RPM 100 RPM\",\"name\":\"28:E2:97:4D:55:10\",\"powerplans\":[{\"info\":\"800 MHz 0.42 V 100 RPM 100 RPM\",\"level\":0}],\"manual\":false,\"version\":\"v1.0\"}"),
                                                new HttpHandler(
                                                        "{\"ledcontrol\":true,\"manual\":false,\"manualPowerplan\":\"800 MHz 0.42 V 100 RPM 100 RPM\",\"name\":\"28:E2:97:4D:55:10\",\"powerplans\":[{\"info\":\"800 MHz 0.42 V 100 RPM 100 RPM\",\"level\":0}],\"select\":0,\"tempcontrol\":true,\"version\":\"v1.0\"}",
                                                        "{}"),
                                                new HttpHandler(
                                                        "",
                                                        "{\"select\":0,\"tempcontrol\":true,\"ledcontrol\":true,\"manualPowerplan\":\"800 MHz 0.42 V 100 RPM 100 RPM\",\"name\":\"28:E2:97:4D:55:10\",\"powerplans\":[{\"info\":\"800 MHz 0.42 V 100 RPM 100 RPM\",\"level\":0}],\"manual\":false,\"version\":\"v1.0\"}"),
                                                new HttpHandler(
                                                        "{\"ledcontrol\":false,\"manual\":false,\"manualPowerplan\":\"800 MHz 0.42 V 100 RPM 100 RPM\",\"name\":\"28:E2:97:4D:55:10\",\"powerplans\":[{\"info\":\"800 MHz 0.42 V 100 RPM 100 RPM\",\"level\":0}],\"select\":0,\"tempcontrol\":true,\"version\":\"v1.0\"}",
                                                        "{}"))))),
                Collections.emptyMap(),
                true,
                false);
    }
}