package mn.foreman.antminer.util;

import mn.foreman.antminer.AntminerRebootStrategy;
import mn.foreman.antminer.util.AntminerTestUtils;
import mn.foreman.model.MinerFactory;
import mn.foreman.util.AbstractRebootITest;
import mn.foreman.util.http.FakeHttpMinerServer;
import mn.foreman.util.http.HttpHandler;
import mn.foreman.util.rpc.FakeRpcMinerServer;
import mn.foreman.util.rpc.HandlerInterface;

import com.google.common.collect.ImmutableMap;
import org.apache.http.HttpStatus;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Base class for the testing of miners that have similar web interfaces as the
 * Antminer.
 */
public abstract class AntminerRebootITest
        extends AbstractRebootITest {

    /**
     * Constructor.
     *
     * @param factory  The factory.
     * @param realm    The realm.
     * @param handlers The handlers.
     */
    public AntminerRebootITest(
            final MinerFactory factory,
            final String realm,
            final Map<String, HandlerInterface> handlers) {
        super(
                8080,
                4028,
                new AntminerRebootStrategy(
                        1,
                        1,
                        TimeUnit.SECONDS,
                        THREAD_POOL,
                        factory,
                        realm),
                Arrays.asList(
                        () -> new FakeHttpMinerServer(
                                8080,
                                ImmutableMap.of(
                                        "/cgi-bin/reboot.cgi",
                                        new HttpHandler(
                                                "",
                                                "",
                                                exchange -> AntminerTestUtils.validateDigest(
                                                        exchange,
                                                        realm),
                                                HttpStatus.SC_INTERNAL_SERVER_ERROR))),
                        () -> new FakeRpcMinerServer(
                                4028,
                                handlers)),
                true);
    }
}