package mn.foreman.antminer;

import mn.foreman.cgminer.CgMinerDetectionStrategy;
import mn.foreman.cgminer.request.CgMinerCommand;
import mn.foreman.model.Detection;
import mn.foreman.util.AbstractDetectITest;
import mn.foreman.util.rpc.FakeRpcMinerServer;
import mn.foreman.util.rpc.RpcHandler;

import com.google.common.collect.ImmutableMap;

/** Tests dection of an Antminer B3. */
public class AntminerB3DetectITest
        extends AbstractDetectITest {

    /** Constructor. */
    public AntminerB3DetectITest() {
        super(
                new CgMinerDetectionStrategy(
                        CgMinerCommand.VERSION,
                        new AntminerTypeFactory()),
                () -> new FakeRpcMinerServer(
                        4028,
                        ImmutableMap.of(
                                "{\"command\":\"version\"}",
                                new RpcHandler(
                                        "{\"STATUS\":[{\"STATUS\":\"S\",\"When\":395315,\"Code\":22,\"Msg\":\"CGMiner versions\",\"Description\":\"bmminer 1.0.0\"}],\"VERSION\":[{\"CGMiner\":\"2.0.0\",\"API\":\"3.1\",\"Miner\":\"A.0.0.1\",\"CompileTime\":\"Wed Apr 11 22:21:40 CST 2018\",\"Type\":\"Antminer B3\"}],\"id\":1}"))),
                Detection.builder()
                        .minerType(AntminerType.ANTMINER_B3)
                        .ipAddress("127.0.0.1")
                        .port(4028)
                        .build());
    }
}