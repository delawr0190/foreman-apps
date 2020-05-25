package mn.foreman.blackminer;

import mn.foreman.cgminer.CgMinerDetectionStrategy;
import mn.foreman.cgminer.request.CgMinerCommand;
import mn.foreman.model.Detection;
import mn.foreman.util.AbstractDetectITest;
import mn.foreman.util.rpc.FakeRpcMinerServer;
import mn.foreman.util.rpc.RpcHandler;

import com.google.common.collect.ImmutableMap;

/** Tests detection of a Blackminer. */
public class BlackminerF1UltraDetectITest
        extends AbstractDetectITest {

    /** Constructor. */
    public BlackminerF1UltraDetectITest() {
        super(
                new CgMinerDetectionStrategy(
                        CgMinerCommand.VERSION,
                        new BlackminerTypeFactory()),
                () -> new FakeRpcMinerServer(
                        4028,
                        ImmutableMap.of(
                                "{\"command\":\"version\"}",
                                new RpcHandler(
                                        "{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1590413031,\"Code\":22,\"Msg\":\"CGMiner versions\",\"Description\":\"ccminer 2.3.3\"}],\"VERSION\":[{\"CGMiner\":\"2.3.3\",\"API\":\"3.1\",\"Miner\":\"1.3.0.8\",\"CompileTime\":\"2020-04-05 16-27-23 CST\",\"Type\":\"Blackminer F1-ULTRA\"}],\"id\":1}"))),
                Detection.builder()
                        .minerType(BlackminerType.BLACKMINER_F1_ULTRA)
                        .ipAddress("127.0.0.1")
                        .port(4028)
                        .build());
    }
}