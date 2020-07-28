package mn.foreman.dragonmint;

import mn.foreman.util.AbstractRebootITest;
import mn.foreman.util.http.FakeHttpMinerServer;
import mn.foreman.util.http.HttpHandler;

import com.google.common.collect.ImmutableMap;

/** Test rebooting a Dragonmint. */
public class DragonmintRebootITest
        extends AbstractRebootITest {

    /** Constructor. */
    public DragonmintRebootITest() {
        super(
                new DragonmintRebootStrategy(),
                () -> new FakeHttpMinerServer(
                        8080,
                        ImmutableMap.of(
                                "/api/reboot",
                                new HttpHandler(
                                        "",
                                        "{\"success\": true}",
                                        DragonmintTestUtils::validateBasicAuth))),
                true);
    }
}
