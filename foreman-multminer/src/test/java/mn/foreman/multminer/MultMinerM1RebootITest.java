package mn.foreman.multminer;

import mn.foreman.util.AbstractRebootITest;
import mn.foreman.util.http.FakeHttpMinerServer;
import mn.foreman.util.http.HttpHandler;

import com.google.common.collect.ImmutableMap;

/** Test rebooting a multminer. */
public class MultMinerM1RebootITest
        extends AbstractRebootITest {

    /** Constructor. */
    public MultMinerM1RebootITest() {
        super(
                new MultMinerRebootStrategy(),
                () -> new FakeHttpMinerServer(
                        8080,
                        ImmutableMap.of(
                                "/index.csp",
                                new HttpHandler(
                                        "act=cfg&reboot=%E7%B3%BB%E7%BB%9F%E9%87%8D%E5%90%AF",
                                        "ok"))),
                true);
    }
}