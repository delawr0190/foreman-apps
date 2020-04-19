package mn.foreman.antminer;

import mn.foreman.cgminer.CgMinerDetectionStrategy;
import mn.foreman.cgminer.request.CgMinerCommand;
import mn.foreman.model.Detection;
import mn.foreman.util.AbstractDetectITest;
import mn.foreman.util.rpc.FakeRpcMinerServer;
import mn.foreman.util.rpc.RpcHandler;

import com.google.common.collect.ImmutableMap;

/** Tests dection of an Antminer S9. */
public class AntminerS9DetectITest
        extends AbstractDetectITest {

    /** Constructor. */
    public AntminerS9DetectITest() {
        super(
                new CgMinerDetectionStrategy(
                        CgMinerCommand.VERSION,
                        new AntminerTypeFactory()),
                () -> new FakeRpcMinerServer(
                        4028,
                        ImmutableMap.of(
                                "{\"command\":\"version\"}",
                                new RpcHandler(
                                        "{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1516328450,\"Code\":22,\"Msg\":\"BMMiner versions\",\"Description\":\"bmminer 1.0.0\"}],\"VERSION\":[{\"BMMiner\":\"2.0.0\",\"API\":\"3.1\",\"Miner\":\"16.8.1.3\",\"CompileTime\":\"Fri Nov 17 17:37:49 CST 2017\",\"Type\":\"Antminer S9\"}],\"id\":1}"))),
                Detection.builder()
                        .minerType(AntminerType.ANTMINER_S9)
                        .ipAddress("127.0.0.1")
                        .port(4028)
                        .build());
    }
}