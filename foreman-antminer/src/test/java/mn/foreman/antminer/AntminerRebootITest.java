package mn.foreman.antminer;

import mn.foreman.util.AbstractRebootITest;
import mn.foreman.util.http.FakeHttpMinerServer;
import mn.foreman.util.http.HttpHandler;

import com.google.common.collect.ImmutableMap;
import org.apache.http.HttpStatus;

/** Test rebooting of an Antminer. */
public class AntminerRebootITest
        extends AbstractRebootITest {

    /** Constructor. */
    public AntminerRebootITest() {
        super(
                new AntminerRebootStrategy("antMiner Configuration"),
                () -> new FakeHttpMinerServer(
                        AbstractRebootITest.DEFAULT_PORT,
                        ImmutableMap.of(
                                "/cgi-bin/reboot.cgi",
                                new HttpHandler(
                                        "",
                                        "",
                                        exchange -> AntminerTestUtils.validateDigest(
                                                exchange,
                                                "antMiner Configuration"),
                                        HttpStatus.SC_INTERNAL_SERVER_ERROR))),
                true);
    }
}