package mn.foreman.minerva;

import mn.foreman.util.AbstractMacITest;
import mn.foreman.util.rpc.FakeRpcMinerServer;
import mn.foreman.util.rpc.RpcHandler;

import com.google.common.collect.ImmutableMap;

import java.util.Collections;

/** Tests obtaining a MAC address from a Miner-Va. */
public class MinerVaMacITest
        extends AbstractMacITest {

    /** Constructor. */
    public MinerVaMacITest() {
        super(
                new MinerVaFactory(),
                Collections.singletonList(
                        new FakeRpcMinerServer(
                                4028,
                                ImmutableMap.of(
                                        "{\"command\":\"summary\"}",
                                        new RpcHandler(
                                                "{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1613791837,\"Code\":11,\"Msg\":\"Summary\",\"Description\":\"cgminer 4.10.0\"}],\"SUMMARY\":[{\"Elapsed\":10336,\"MHS av\":90745295.34,\"MHS 5s\":87243238.61,\"MHS 1m\":90882682.54,\"MHS 5m\":90915724.07,\"MHS 15m\":90874515.22,\"Found Blocks\":0,\"Getworks\":432,\"Accepted\":489,\"Rejected\":1,\"Hardware Errors\":1351,\"Utility\":2.84,\"Discarded\":167025,\"Stale\":0,\"Get Failures\":0,\"Local Work\":1033261,\"Remote Failures\":0,\"Network Blocks\":20,\"Total MH\":937912088649.0000,\"Work Utility\":5659.43,\"Difficulty Accepted\":124583936.00000000,\"Difficulty Rejected\":262144.00000000,\"Difficulty Stale\":0.00000000,\"Best Share\":512124050,\"Device Hardware%\":0.1384,\"Device Rejected%\":26.8893,\"Pool Rejected%\":0.2100,\"Pool Stale%\":0.0000,\"Last getwork\":1613791837,\"Netid\":\"1CC0E102061A\"}],\"id\":1}")))),
                "1C:C0:E1:02:06:1A");
    }
}
