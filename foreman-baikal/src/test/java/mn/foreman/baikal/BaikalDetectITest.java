package mn.foreman.baikal;

import mn.foreman.model.Detection;
import mn.foreman.util.AbstractDetectITest;
import mn.foreman.util.http.FakeHttpMinerServer;
import mn.foreman.util.http.HttpHandler;
import mn.foreman.util.http.ServerHandler;

import com.google.common.collect.ImmutableMap;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/** Tests detection of a Baikal. */
@RunWith(Parameterized.class)
public class BaikalDetectITest
        extends AbstractDetectITest {

    /**
     * Constructor.
     *
     * @param handlers The handlers.
     * @param type     The type.
     */
    public BaikalDetectITest(
            final Map<String, ServerHandler> handlers,
            final BaikalType type) {
        super(
                new BaikalDetectionStrategy("80"),
                "127.0.0.1",
                4028,
                args(),
                () -> new FakeHttpMinerServer(
                        8080,
                        handlers),
                Detection.builder()
                        .minerType(type)
                        .ipAddress("127.0.0.1")
                        .port(4028)
                        .parameters(args())
                        .build(),
                (integer, stringObjectMap) -> {
                    stringObjectMap.put(
                            "webPort",
                            (integer != 4028
                                    ? "8081"
                                    : "8080"));
                    return stringObjectMap;
                });
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
                                ImmutableMap.of(
                                        "/login.php",
                                        new HttpHandler(
                                                "",
                                                "<html></html>")),
                                BaikalType.GENERIC
                        }
                });
    }

    /**
     * Creates test args.
     *
     * @return Test args.
     */
    private static Map<String, Object> args() {
        final Map<String, Object> args = new HashMap<>(DEFAULT_ARGS);
        args.put("webPort", "8080");
        return args;
    }
}