package mn.foreman.blackminer;

import mn.foreman.cgminer.CgMinerDetectionStrategy;
import mn.foreman.cgminer.request.CgMinerCommand;
import mn.foreman.model.Detection;
import mn.foreman.util.AbstractDetectITest;
import mn.foreman.util.rpc.FakeRpcMinerServer;
import mn.foreman.util.rpc.HandlerInterface;
import mn.foreman.util.rpc.RpcHandler;

import com.google.common.collect.ImmutableMap;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

/** Tests detection of blackminer miners. */
@RunWith(Parameterized.class)
public class BlackminerDetectITest
        extends AbstractDetectITest {

    /**
     * Constructor.
     *
     * @param handlers     The handlers.
     * @param expectedType The expected type.
     */
    public BlackminerDetectITest(
            final Map<String, HandlerInterface> handlers,
            final BlackminerType expectedType) {
        super(
                new CgMinerDetectionStrategy(
                        CgMinerCommand.VERSION,
                        new BlackminerTypeFactory()),
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
     */
    @Parameterized.Parameters
    public static Collection parameters() {
        return Arrays.asList(
                new Object[][]{
                        {
                                // Blackminer F1
                                ImmutableMap.of(
                                        "{\"command\":\"version\"}",
                                        new RpcHandler(
                                                "{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1544107423,\"Code\":70,\"Msg\":\"CGMiner stats\",\"Description\":\"ccminer 2.3.3\"}],\"VERSION\":[{\"CGMiner\":\"2.3.3\",\"Miner\":\"1.3.0.8\",\"CompileTime\":\"2018-11-19 20-59-09 CST\",\"Type\":\"Blackminer F1\"}],\"id\":1}")),
                                BlackminerType.BLACKMINER_F1
                        },
                        {
                                // Blackminer F1 Mini
                                ImmutableMap.of(
                                        "{\"command\":\"version\"}",
                                        new RpcHandler(
                                                "{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1593395696,\"Code\":22,\"Msg\":\"CGMiner versions\",\"Description\":\"ccminer 2.3.3\"}],\"VERSION\":[{\"CGMiner\":\"2.3.3\",\"API\":\"3.1\",\"Miner\":\"1.3.0.8\",\"CompileTime\":\"2020-06-12 16-01-07 CST\",\"Type\":\"Blackminer F1-MINI\"}],\"id\":1}")),
                                BlackminerType.BLACKMINER_F1_MINI
                        },
                        {
                                // Blackminer F1 Ultra
                                ImmutableMap.of(
                                        "{\"command\":\"version\"}",
                                        new RpcHandler(
                                                "{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1590413031,\"Code\":22,\"Msg\":\"CGMiner versions\",\"Description\":\"ccminer 2.3.3\"}],\"VERSION\":[{\"CGMiner\":\"2.3.3\",\"API\":\"3.1\",\"Miner\":\"1.3.0.8\",\"CompileTime\":\"2020-04-05 16-27-23 CST\",\"Type\":\"Blackminer F1-ULTRA\"}],\"id\":1}")),
                                BlackminerType.BLACKMINER_F1_ULTRA
                        },
                        {
                                // Blackminer (unknown)
                                ImmutableMap.of(
                                        "{\"command\":\"version\"}",
                                        new RpcHandler(
                                                "{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1590413031,\"Code\":22,\"Msg\":\"CGMiner versions\",\"Description\":\"ccminer 2.3.3\"}],\"VERSION\":[{\"CGMiner\":\"2.3.3\",\"API\":\"3.1\",\"Miner\":\"1.3.0.8\",\"CompileTime\":\"2020-04-05 16-27-23 CST\",\"Type\":\"Blackminer F1-u\"}],\"id\":1}")),
                                BlackminerType.BLACKMINER_F1
                        }
                });
    }
}
