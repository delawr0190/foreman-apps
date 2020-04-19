package mn.foreman.antminer;

import mn.foreman.cgminer.CgMinerDetectionStrategy;
import mn.foreman.cgminer.request.CgMinerCommand;
import mn.foreman.model.Detection;
import mn.foreman.util.AbstractDetectITest;
import mn.foreman.util.rpc.FakeRpcMinerServer;
import mn.foreman.util.rpc.RpcHandler;

import com.google.common.collect.ImmutableMap;

/** Tests dection of an Antminer S9. */
public class BraainsS9DetectITest
        extends AbstractDetectITest {

    /** Constructor. */
    public BraainsS9DetectITest() {
        super(
                new CgMinerDetectionStrategy(
                        CgMinerCommand.VERSION,
                        new AntminerTypeFactory()),
                () -> new FakeRpcMinerServer(
                        4028,
                        ImmutableMap.of(
                                "{\"command\":\"version\"}",
                                new RpcHandler(
                                        "{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1545884756,\"Code\":22,\"Msg\":\"BMMiner versions\",\"Description\":\"bmminer bOS_am1-s9-20181127-0_c34516b0\"}],\"VERSION\":[{\"BMMiner\":\"bOS_am1-s9-20181127-0_c34516b0\",\"API\":\"3.1\",\"Miner\":\"bOS_am1-s9-20181127-0_c34516b0\",\"CompileTime\":\"\",\"Type\":\"braiins-am1-s9\"}],\"id\":1}"))),
                Detection.builder()
                        .minerType(AntminerType.BRAIINS_S9)
                        .ipAddress("127.0.0.1")
                        .port(4028)
                        .build());
    }
}