package mn.foreman.whatsminer;

import mn.foreman.cgminer.CgMinerDetectionStrategy;
import mn.foreman.cgminer.request.CgMinerCommand;
import mn.foreman.model.Detection;
import mn.foreman.util.AbstractDetectITest;
import mn.foreman.util.rpc.FakeRpcMinerServer;
import mn.foreman.util.rpc.RpcHandler;

import com.google.common.collect.ImmutableMap;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.nio.charset.Charset;

/** Tests detection of a Whatsminer. */
public class M3DetectITest
        extends AbstractDetectITest {

    /** Constructor. */
    public M3DetectITest() {
        super(
                new CgMinerDetectionStrategy(
                        CgMinerCommand.STATS,
                        new WhatsminerTypeFactory()),
                () -> {
                    try {
                        return new FakeRpcMinerServer(
                                4028,
                                ImmutableMap.of(
                                        "{\"command\":\"stats\"}",
                                        new RpcHandler(
                                                IOUtils.toString(
                                                        M3StatsITest.class.getResourceAsStream(
                                                                "/m3.stats.json"),
                                                        Charset.defaultCharset()))));
                    } catch (final IOException e) {
                        throw new AssertionError(e);
                    }
                },
                Detection.builder()
                        .minerType(WhatsminerType.WHATSMINER_M3V1)
                        .ipAddress("127.0.0.1")
                        .port(4028)
                        .build());
    }
}