package mn.foreman.whatsminer;

import mn.foreman.cgminer.CgMinerDetectionStrategy;
import mn.foreman.cgminer.request.CgMinerCommand;
import mn.foreman.model.Detection;
import mn.foreman.util.AbstractDetectITest;
import mn.foreman.util.rpc.FakeRpcMinerServer;
import mn.foreman.util.rpc.RpcHandler;

import com.google.common.collect.ImmutableMap;
import org.apache.commons.io.IOUtils;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

/** Tests detection of whatsminer miners. */
@RunWith(Parameterized.class)
public class WhatsminerDetectITest
        extends AbstractDetectITest {

    /**
     * Constructor.
     *
     * @param handlers     The handlers.
     * @param expectedType The expected type.
     */
    public WhatsminerDetectITest(
            final Map<String, RpcHandler> handlers,
            final WhatsminerType expectedType) {
        super(
                new CgMinerDetectionStrategy(
                        CgMinerCommand.STATS,
                        new WhatsminerTypeFactory()),
                () -> new FakeRpcMinerServer(
                        4028,
                        handlers),
                Detection.builder()
                        .minerType(expectedType)
                        .ipAddress("127.0.0.1")
                        .port(4028)
                        .build());
    }

    /**
     * Test parameters
     *
     * @return The test parameters.
     *
     * @throws IOException on failure to open file.
     */
    @Parameterized.Parameters
    public static Collection parameters()
            throws IOException {
        return Arrays.asList(
                new Object[][]{
                        {
                                // Whatsminer M3
                                ImmutableMap.of(
                                        "{\"command\":\"stats\"}",
                                        new RpcHandler(
                                                IOUtils.toString(
                                                        WhatsminerDetectITest.class.getResourceAsStream(
                                                                "/m3.stats.json"),
                                                        Charset.defaultCharset()))),
                                WhatsminerType.WHATSMINER_M3V1
                        },
                        {
                                // Whatsminer M20S
                                ImmutableMap.of(
                                        "{\"command\":\"stats\"}",
                                        new RpcHandler(
                                                IOUtils.toString(
                                                        WhatsminerDetectITest.class.getResourceAsStream(
                                                                "/m20s.stats.json"),
                                                        Charset.defaultCharset()))),
                                WhatsminerType.WHATSMINER_M20S
                        }
                });
    }
}
