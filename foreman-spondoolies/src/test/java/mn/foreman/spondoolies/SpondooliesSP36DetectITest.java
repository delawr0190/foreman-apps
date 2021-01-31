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
                                        "{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1551286943,\"Code\":11,\"Msg\":\"Summary\",\"Description\":\"cgminer 4.10.0-spx\"}],\"SUMMARY\":[{\"Elapsed\":147407,\"MHS av\":512711.84,\"MHS 5s\":377763.37,\"MHS 1m\":509012.61,\"MHS 5m\":536603.26,\"MHS 15m\":536431.39,\"Found Blocks\":0,\"Getworks\":3180,\"Accepted\":17010,\"Rejected\":7,\"Hardware Errors\":3844,\"Utility\":6.92,\"Discarded\":1128923,\"Stale\":0,\"Get Failures\":0,\"Local Work\":2351678,\"Remote Failures\":0,\"Network Blocks\":941,\"Total MH\":75577469626.0000,\"Work Utility\":7162.52,\"Difficulty Accepted\":17381836.70500923,\"Difficulty Rejected\":7168.00000000,\"Difficulty Stale\":0.00000000,\"Best Share\":4852321,\"Device Hardware%\":0.0218,\"Device Rejected%\":0.0407,\"Pool Rejected%\":0.0412,\"Pool Stale%\":0.0000,\"Last getwork\":1551286943}],\"id\":1}"),
                                "{\"command\":\"pools\"}",
                                new RpcHandler(
                                        "{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1551286943,\"Code\":7,\"Msg\":\"2 Pool(s)\",\"Description\":\"cgminer 4.10.0-spx\"}],\"POOLS\":[{\"POOL\":0,\"URL\":\"stratum+tcp://dash.coinmine.pl:6099\",\"Status\":\"Alive\",\"Priority\":0,\"Quota\":1,\"Long Poll\":\"N\",\"Getworks\":3178,\"Accepted\":17010,\"Rejected\":7,\"Works\":1221819,\"Discarded\":1128923,\"Stale\":0,\"Get Failures\":0,\"Remote Failures\":0,\"User\":\"xxx.001SPX36\",\"Last Share Time\":1551286940,\"Diff1 Shares\":17596800,\"Proxy Type\":\"\",\"Proxy\":\"\",\"Difficulty Accepted\":17381836.70500923,\"Difficulty Rejected\":7168.00000000,\"Difficulty Stale\":0.00000000,\"Last Share Difficulty\":1024.00000000,\"Work Difficulty\":1024.00000000,\"Has Stratum\":true,\"Stratum Active\":true,\"Stratum URL\":\"dash.coinmine.pl\",\"Stratum Difficulty\":1024.00000000,\"Has GBT\":false,\"Best Share\":4852321,\"Pool Rejected%\":0.0412,\"Pool Stale%\":0.0000,\"Bad Work\":75,\"Current Block Height\":1028421,\"Current Block Version\":536870912},{\"POOL\":1,\"URL\":\"stratum+tcp://x11.usa.nicehash.com:3336\",\"Status\":\"Alive\",\"Priority\":1,\"Quota\":1,\"Long Poll\":\"N\",\"Getworks\":2,\"Accepted\":0,\"Rejected\":0,\"Works\":0,\"Discarded\":0,\"Stale\":0,\"Get Failures\":0,\"Remote Failures\":0,\"User\":\"xxx.001SPX36\",\"Last Share Time\":0,\"Diff1 Shares\":0,\"Proxy Type\":\"\",\"Proxy\":\"\",\"Difficulty Accepted\":0.00000000,\"Difficulty Rejected\":0.00000000,\"Difficulty Stale\":0.00000000,\"Last Share Difficulty\":0.00000000,\"Work Difficulty\":64.00000000,\"Has Stratum\":true,\"Stratum Active\":false,\"Stratum URL\":\"\",\"Stratum Difficulty\":0.00000000,\"Has GBT\":false,\"Best Share\":0,\"Pool Rejected%\":0.0000,\"Pool Stale%\":0.0000,\"Bad Work\":2,\"Current Block Height\":534971,\"Current Block Version\":536870912}],\"id\":1}"))),
                Detection.builder()
                        .minerType(SpondooliesType.SPONDOOLIES_SPX36)
                        .ipAddress("127.0.0.1")
                        .port(4028)
                        .parameters(DEFAULT_ARGS)
                        .build());
    }
}