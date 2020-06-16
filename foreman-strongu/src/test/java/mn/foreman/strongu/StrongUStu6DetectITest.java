package mn.foreman.strongu;

import mn.foreman.cgminer.CgMinerDetectionStrategy;
import mn.foreman.cgminer.request.CgMinerCommand;
import mn.foreman.model.Detection;
import mn.foreman.util.AbstractDetectITest;
import mn.foreman.util.rpc.FakeRpcMinerServer;
import mn.foreman.util.rpc.RpcHandler;

import com.google.common.collect.ImmutableMap;

/** Tests detection of a StrongU. */
public class StrongUStu6DetectITest
        extends AbstractDetectITest {

    /** Constructor. */
    public StrongUStu6DetectITest() {
        super(
                new CgMinerDetectionStrategy(
                        CgMinerCommand.DEVS,
                        new StrongUTypeFactory()),
                () -> new FakeRpcMinerServer(
                        4028,
                        ImmutableMap.of(
                                "{\"command\":\"devs\"}",
                                new RpcHandler(
                                        "{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1592273807,\"Code\":9,\"Msg\":\"2 ASC(s)\",\"Description\":\"cgminer 4.11.1\"}],\"DEVS\":[{\"ASC\":0,\"Name\":\"u6\",\"ID\":0,\"Enabled\":\"Y\",\"Status\":\"Alive\",\"Temperature\":54.56,\"volt\":10.500,\"ChipNum\":70,\"Frequency\":550,\"MHS av\":190748.48,\"MHS 5s\":191920.49,\"MHS 1m\":194433.37,\"MHS 5m\":201864.17,\"MHS 15m\":186301.66,\"Accepted\":77,\"Rejected\":2,\"Hardware Errors\":4552,\"Utility\":1.91,\"Last Share Pool\":0,\"Last Share Time\":1592273789,\"Total MH\":460759065.0000,\"Diff1 Work\":13410,\"Difficulty Accepted\":91134.60937500,\"Difficulty Rejected\":2303.96484375,\"Last Share Difficulty\":2047.96875000,\"Last Valid Work\":1592273807,\"Device Hardware%\":25.3424,\"Device Rejected%\":17.1809,\"Device Elapsed\":2416,\"Reboot Count\":0},{\"ASC\":2,\"Name\":\"u6\",\"ID\":1,\"Enabled\":\"Y\",\"Status\":\"Alive\",\"Temperature\":55.62,\"volt\":12.700,\"ChipNum\":70,\"Frequency\":550,\"MHS av\":196438.30,\"MHS 5s\":208240.11,\"MHS 1m\":211732.23,\"MHS 5m\":208990.13,\"MHS 15m\":192341.76,\"Accepted\":85,\"Rejected\":2,\"Hardware Errors\":3509,\"Utility\":2.11,\"Last Share Pool\":0,\"Last Share Time\":1592273802,\"Total MH\":474503008.0000,\"Diff1 Work\":13811,\"Difficulty Accepted\":101374.45312500,\"Difficulty Rejected\":2559.96093750,\"Last Share Difficulty\":2047.96875000,\"Last Valid Work\":1592273807,\"Device Hardware%\":20.2598,\"Device Rejected%\":18.5357,\"Device Elapsed\":2416,\"Reboot Count\":0}],\"id\":1}"))),
                Detection.builder()
                        .minerType(StrongUType.STU_U6)
                        .ipAddress("127.0.0.1")
                        .port(4028)
                        .build());
    }
}