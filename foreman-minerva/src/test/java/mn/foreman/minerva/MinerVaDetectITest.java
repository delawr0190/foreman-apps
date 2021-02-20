package mn.foreman.minerva;

import mn.foreman.cgminer.CgMinerDetectionStrategy;
import mn.foreman.cgminer.NullPatchingStrategy;
import mn.foreman.cgminer.request.CgMinerCommand;
import mn.foreman.model.Detection;
import mn.foreman.util.AbstractDetectITest;
import mn.foreman.util.rpc.FakeRpcMinerServer;
import mn.foreman.util.rpc.HandlerInterface;
import mn.foreman.util.rpc.RpcHandler;

import com.google.common.collect.ImmutableMap;
import one.util.streamex.EntryStream;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

/** Tests miner-va detection. */
@RunWith(Parameterized.class)
public class MinerVaDetectITest
        extends AbstractDetectITest {

    /**
     * Constructor.
     *
     * @param handlers     The handlers.
     * @param expectedType The expected type.
     * @param expectedMac  The expected mac.
     */
    public MinerVaDetectITest(
            final Map<String, HandlerInterface> handlers,
            final MinerVaType expectedType,
            final String expectedMac) {
        super(
                new CgMinerDetectionStrategy(
                        CgMinerCommand.STATS,
                        new MinerVaTypeFactory(),
                        new MinerVaMacStrategy(
                                "127.0.0.1",
                                4028),
                        new NullPatchingStrategy()),
                () -> new FakeRpcMinerServer(
                        4028,
                        handlers),
                Detection.builder()
                        .minerType(expectedType)
                        .ipAddress("127.0.0.1")
                        .port(4028)
                        .parameters(
                                EntryStream
                                        .of(DEFAULT_ARGS)
                                        .append(
                                                "mac",
                                                expectedMac)
                                        .toImmutableMap())
                        .build());
    }

    /**
     * Test parameters
     *
     * @return The test parameters.
     */
    @Parameterized.Parameters
    public static Collection<Object[]> parameters() {
        return Arrays.asList(
                new Object[][]{
                        {
                                // MV8
                                ImmutableMap.of(
                                        "{\"command\":\"summary\"}",
                                        new RpcHandler(
                                                "{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1613791837,\"Code\":11,\"Msg\":\"Summary\",\"Description\":\"cgminer 4.10.0\"}],\"SUMMARY\":[{\"Elapsed\":10336,\"MHS av\":90745295.34,\"MHS 5s\":87243238.61,\"MHS 1m\":90882682.54,\"MHS 5m\":90915724.07,\"MHS 15m\":90874515.22,\"Found Blocks\":0,\"Getworks\":432,\"Accepted\":489,\"Rejected\":1,\"Hardware Errors\":1351,\"Utility\":2.84,\"Discarded\":167025,\"Stale\":0,\"Get Failures\":0,\"Local Work\":1033261,\"Remote Failures\":0,\"Network Blocks\":20,\"Total MH\":937912088649.0000,\"Work Utility\":5659.43,\"Difficulty Accepted\":124583936.00000000,\"Difficulty Rejected\":262144.00000000,\"Difficulty Stale\":0.00000000,\"Best Share\":512124050,\"Device Hardware%\":0.1384,\"Device Rejected%\":26.8893,\"Pool Rejected%\":0.2100,\"Pool Stale%\":0.0000,\"Last getwork\":1613791837,\"Netid\":\"1CC0E102061A\"}],\"id\":1}"),
                                        "{\"command\":\"stats\"}",
                                        new RpcHandler(
                                                "{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1613791837,\"Code\":70,\"Msg\":\"CGMiner stats\",\"Description\":\"cgminer 4.10.0\"}],\"STATS\":[{\"STATS\":0,\"ID\":\"C120\",\"Elapsed\":10336,\"Calls\":0,\"Wait\":0.000000,\"Max\":0.000000,\"Min\":99999999.000000,\"Chain ID\":\"1\",\"Name\":\"C12\",\"Enabled\":\"Y\",\"Status\":\"Alive\",\"MHS av\":22696536.82,\"MHS 5s\":22784265.78,\"MHS 1m\":22729722.46,\"MHS 5m\":22568275.30,\"MHS 15m\":22646855.44,\"Accepted\":110,\"Rejected\":0,\"Hardware Errors\":176,\"Diff1 Work\":243838,\"Difficulty Accepted\":27787264.00000000,\"Difficulty Rejected\":0.00000000,\"Last Share Difficulty\":262144.00000000,\"Last Valid Work\":1613791837,\"Device Hardware%\":0.0721,\"Device Rejected%\":0.0000,\"Device Elapsed\":10336,\"Chain Enabled\":\"Y\",\"Chip Count\":102,\"Device Diff\":128,\"Device Freq\":600,\"Temp Avg\":85.87,\"Voltage Avg\":345.190,\"Voltage\":12170.000,\"Fan Duty\":32.19,\"Fan0 Speed\":3090,\"Fan1 Speed\":3330},{\"STATS\":1,\"ID\":\"C121\",\"Elapsed\":10336,\"Calls\":0,\"Wait\":0.000000,\"Max\":0.000000,\"Min\":99999999.000000,\"Chain ID\":\"2\",\"Name\":\"C12\",\"Enabled\":\"Y\",\"Status\":\"Alive\",\"MHS av\":22738423.52,\"MHS 5s\":22420627.92,\"MHS 1m\":23225273.66,\"MHS 5m\":23158147.49,\"MHS 15m\":22986218.87,\"Accepted\":122,\"Rejected\":1,\"Hardware Errors\":224,\"Diff1 Work\":244286,\"Difficulty Accepted\":30670848.00000000,\"Difficulty Rejected\":262144.00000000,\"Last Share Difficulty\":262144.00000000,\"Last Valid Work\":1613791837,\"Device Hardware%\":0.0916,\"Device Rejected%\":107.3103,\"Device Elapsed\":10336,\"Chain Enabled\":\"Y\",\"Chip Count\":102,\"Device Diff\":128,\"Device Freq\":600,\"Temp Avg\":84.14,\"Voltage Avg\":345.436,\"Voltage\":12170.000,\"Fan Duty\":32.19,\"Fan0 Speed\":3090,\"Fan1 Speed\":3330},{\"STATS\":2,\"ID\":\"C122\",\"Elapsed\":10336,\"Calls\":0,\"Wait\":0.000000,\"Max\":0.000000,\"Min\":99999999.000000,\"Chain ID\":\"3\",\"Name\":\"C12\",\"Enabled\":\"Y\",\"Status\":\"Alive\",\"MHS av\":22604383.98,\"MHS 5s\":21289254.84,\"MHS 1m\":22528632.66,\"MHS 5m\":22504203.21,\"MHS 15m\":22539506.97,\"Accepted\":119,\"Rejected\":0,\"Hardware Errors\":669,\"Diff1 Work\":242838,\"Difficulty Accepted\":30408704.00000000,\"Difficulty Rejected\":0.00000000,\"Last Share Difficulty\":262144.00000000,\"Last Valid Work\":1613791837,\"Device Hardware%\":0.2747,\"Device Rejected%\":0.0000,\"Device Elapsed\":10336,\"Chain Enabled\":\"Y\",\"Chip Count\":102,\"Device Diff\":128,\"Device Freq\":600,\"Temp Avg\":83.13,\"Voltage Avg\":344.803,\"Voltage\":12170.000,\"Fan Duty\":32.19,\"Fan0 Speed\":3090,\"Fan1 Speed\":3330},{\"STATS\":3,\"ID\":\"C123\",\"Elapsed\":10336,\"Calls\":0,\"Wait\":0.000000,\"Max\":0.000000,\"Min\":99999999.000000,\"Chain ID\":\"4\",\"Name\":\"C12\",\"Enabled\":\"Y\",\"Status\":\"Alive\",\"MHS av\":22705750.57,\"MHS 5s\":21119100.88,\"MHS 1m\":22398119.60,\"MHS 5m\":22668656.96,\"MHS 15m\":22694833.69,\"Accepted\":138,\"Rejected\":0,\"Hardware Errors\":282,\"Diff1 Work\":243935,\"Difficulty Accepted\":35717120.00000000,\"Difficulty Rejected\":0.00000000,\"Last Share Difficulty\":262144.00000000,\"Last Valid Work\":1613791837,\"Device Hardware%\":0.1155,\"Device Rejected%\":0.0000,\"Device Elapsed\":10336,\"Chain Enabled\":\"Y\",\"Chip Count\":102,\"Device Diff\":128,\"Device Freq\":600,\"Temp Avg\":86.86,\"Voltage Avg\":344.561,\"Voltage\":12170.000,\"Fan Duty\":32.19,\"Fan0 Speed\":3090,\"Fan1 Speed\":3330},{\"STATS\":4,\"ID\":\"POOL0\",\"Elapsed\":10336,\"Calls\":0,\"Wait\":0.000000,\"Max\":0.000000,\"Min\":99999999.000000,\"Pool Calls\":0,\"Pool Attempts\":0,\"Pool Wait\":0.000000,\"Pool Max\":0.000000,\"Pool Min\":99999999.000000,\"Pool Av\":0.000000,\"Work Had Roll Time\":false,\"Work Can Roll\":false,\"Work Had Expire\":false,\"Work Roll Time\":0,\"Work Diff\":262144.00000000,\"Min Diff\":16384.00000000,\"Max Diff\":262144.00000000,\"Min Diff Count\":14119,\"Max Diff Count\":1001827,\"Times Sent\":552,\"Bytes Sent\":75953,\"Times Recv\":1008,\"Bytes Recv\":721284,\"Net Bytes Sent\":75953,\"Net Bytes Recv\":721284}],\"id\":1}"),
                                        "{\"command\":\"pools\"}",
                                        new RpcHandler(
                                                "{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1613791837,\"Code\":7,\"Msg\":\"1 Pool(s)\",\"Description\":\"cgminer 4.10.0\"}],\"POOLS\":[{\"POOL\":0,\"URL\":\"stratum+tcp://btc-us.f2pool.com:3333\",\"Status\":\"Alive\",\"Priority\":0,\"Quota\":1,\"Long Poll\":\"N\",\"Getworks\":432,\"Accepted\":489,\"Rejected\":1,\"Works\":894111,\"Discarded\":167025,\"Stale\":0,\"Get Failures\":0,\"Remote Failures\":0,\"User\":\"xxx.MV8\",\"Last Share Time\":1613791826,\"Diff1 Shares\":974897,\"Proxy Type\":\"\",\"Proxy\":\"\",\"Difficulty Accepted\":124583936.00000000,\"Difficulty Rejected\":262144.00000000,\"Difficulty Stale\":0.00000000,\"Last Share Difficulty\":262144.00000000,\"Work Difficulty\":262144.00000000,\"Has Stratum\":true,\"Stratum Active\":true,\"Stratum URL\":\"btc-us.f2pool.com\",\"Stratum Difficulty\":262144.00000000,\"Has Vmask\":true,\"Has GBT\":false,\"Best Share\":512124050,\"Pool Rejected%\":0.2100,\"Pool Stale%\":0.0000,\"Bad Work\":19,\"Current Block Height\":671360,\"Current Block Version\":536870912}],\"id\":1}")),
                                MinerVaType.MV8,
                                "1C:C0:E1:02:06:1A"
                        }
                });
    }
}