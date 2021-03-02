package mn.foreman.avalon;

import mn.foreman.util.AbstractSyncActionITest;
import mn.foreman.util.http.FakeHttpMinerServer;
import mn.foreman.util.http.HttpHandler;
import mn.foreman.util.http.ServerHandler;

import com.google.common.collect.ImmutableMap;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

/** Tests changing passwords on an Avalon miner. */
@RunWith(Parameterized.class)
public class AvalonPasswordITest
        extends AbstractSyncActionITest {

    /**
     * Constructor.
     *
     * @param httpsHandlers The HTTP handlers.
     */
    public AvalonPasswordITest(
            final Map<String, ServerHandler> httpsHandlers) {
        super(
                8080,
                new AvalonPasswordAction(),
                Collections.singletonList(
                        () -> new FakeHttpMinerServer(
                                8080,
                                httpsHandlers)),
                ImmutableMap.of(
                        "oldPassword",
                        "old",
                        "newPassword",
                        "new"),
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
                                // Avalon 1047
                                ImmutableMap.of(
                                        "/admin.cgi",
                                        new HttpHandler(
                                                "passwd=new&confirm=new",
                                                ""))
                        }
                });
    }
}