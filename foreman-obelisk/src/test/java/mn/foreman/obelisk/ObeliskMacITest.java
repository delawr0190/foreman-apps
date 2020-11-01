package mn.foreman.obelisk;

import mn.foreman.util.AbstractMacITest;
import mn.foreman.util.http.FakeHttpMinerServer;
import mn.foreman.util.http.HttpHandler;

import com.google.common.collect.ImmutableMap;

import java.util.Collections;

/** Tests obtaining a MAC address from an Obelisk. */
public class ObeliskMacITest
        extends AbstractMacITest {

    /** Constructor. */
    public ObeliskMacITest() {
        super(
                new ObeliskFactory(),
                Collections.singletonList(
                        new FakeHttpMinerServer(
                                8080,
                                ImmutableMap.of(
                                        "/api/info",
                                        new HttpHandler(
                                                "",
                                                "{\n" +
                                                        "    \"macAddress\": \"80:00:27:2e:bc:69\",\n" +
                                                        "    \"ipAddress\": \"192.168.1.123\",\n" +
                                                        "    \"model\": \"SC1\",\n" +
                                                        "    \"vendor\": \"Obelisk\"\n" +
                                                        "}\n")))),
                "80:00:27:2e:bc:69");
    }
}
