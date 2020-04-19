package mn.foreman.antminer;

import mn.foreman.cgminer.CgMinerDetectionStrategy;
import mn.foreman.cgminer.request.CgMinerCommand;
import mn.foreman.model.Detection;
import mn.foreman.util.AbstractDetectITest;
import mn.foreman.util.rpc.FakeRpcMinerServer;
import mn.foreman.util.rpc.RpcHandler;

import com.google.common.collect.ImmutableMap;

/** Tests dection of an Antminer S17. */
public class AntminerS17DetectITest
        extends AbstractDetectITest {

    /** Constructor. */
    public AntminerS17DetectITest() {
        super(
                new CgMinerDetectionStrategy(
                        CgMinerCommand.VERSION,
                        new AntminerTypeFactory()),
                () -> new FakeRpcMinerServer(
                        4028,
                        ImmutableMap.of(
                                "{\"command\":\"version\"}",
                                new RpcHandler(
                                        "{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1576765845,\"Code\":22,\"Msg\":\"CGMiner versions\",\"Description\":\"cgminer 1.0.0\"}],\"VERSION\":[{\"BMMiner\":\"1.0.0\",\"API\":\"3.1\",\"Miner\":\"19.10.1.3\",\"CompileTime\":\"Tue Aug 20 10:37:07 CST 2019\",\"Type\":\"Antminer S17 Pro\"}],\"id\":1}"))),
                Detection.builder()
                        .minerType(AntminerType.ANTMINER_S17)
                        .ipAddress("127.0.0.1")
                        .port(4028)
                        .build());
    }
}