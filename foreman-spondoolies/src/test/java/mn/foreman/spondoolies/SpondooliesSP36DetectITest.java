package mn.foreman.spondoolies;

import mn.foreman.cgminer.CgMinerDetectionStrategy;
import mn.foreman.cgminer.request.CgMinerCommand;
import mn.foreman.model.Detection;
import mn.foreman.util.AbstractDetectITest;
import mn.foreman.util.rpc.FakeRpcMinerServer;
import mn.foreman.util.rpc.RpcHandler;

import com.google.common.collect.ImmutableMap;

/** Tests detection of a Spondoolies. */
public class SpondooliesSP36DetectITest
        extends AbstractDetectITest {

    /** Constructor. */
    public SpondooliesSP36DetectITest() {
        super(
                new CgMinerDetectionStrategy(
                        CgMinerCommand.SUMMARY,
                        new SpondooliesTypeFactory()),
                () -> new FakeRpcMinerServer(
                        4028,
                        ImmutableMap.of(
                                "{\"command\":\"summary\"}",
                                new RpcHandler(
                                        "{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1551286943,\"Code\":11,\"Msg\":\"Summary\",\"Description\":\"cgminer 4.10.0-spx\"}],\"SUMMARY\":[{\"Elapsed\":147407,\"MHS av\":512711.84,\"MHS 5s\":377763.37,\"MHS 1m\":509012.61,\"MHS 5m\":536603.26,\"MHS 15m\":536431.39,\"Found Blocks\":0,\"Getworks\":3180,\"Accepted\":17010,\"Rejected\":7,\"Hardware Errors\":3844,\"Utility\":6.92,\"Discarded\":1128923,\"Stale\":0,\"Get Failures\":0,\"Local Work\":2351678,\"Remote Failures\":0,\"Network Blocks\":941,\"Total MH\":75577469626.0000,\"Work Utility\":7162.52,\"Difficulty Accepted\":17381836.70500923,\"Difficulty Rejected\":7168.00000000,\"Difficulty Stale\":0.00000000,\"Best Share\":4852321,\"Device Hardware%\":0.0218,\"Device Rejected%\":0.0407,\"Pool Rejected%\":0.0412,\"Pool Stale%\":0.0000,\"Last getwork\":1551286943}],\"id\":1}"))),
                Detection.builder()
                        .minerType(SpondooliesType.SPONDOOLIES_SPX36)
                        .ipAddress("127.0.0.1")
                        .port(4028)
                        .build());
    }
}