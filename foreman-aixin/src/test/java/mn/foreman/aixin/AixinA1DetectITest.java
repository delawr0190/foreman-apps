package mn.foreman.aixin;

import mn.foreman.cgminer.CgMinerDetectionStrategy;
import mn.foreman.cgminer.request.CgMinerCommand;
import mn.foreman.model.Detection;
import mn.foreman.util.AbstractDetectITest;
import mn.foreman.util.rpc.FakeRpcMinerServer;
import mn.foreman.util.rpc.RpcHandler;

import com.google.common.collect.ImmutableMap;

/** Tests dection of an Aixin A1. */
public class AixinA1DetectITest
        extends AbstractDetectITest {

    /** Constructor. */
    public AixinA1DetectITest() {
        super(
                new CgMinerDetectionStrategy(
                        CgMinerCommand.DEVS,
                        new AixinTypeFactory()),
                () -> new FakeRpcMinerServer(
                        4028,
                        ImmutableMap.of(
                                "{\"command\":\"devs\"}",
                                new RpcHandler(
                                        "{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1586623417,\"Code\":9,\"Msg\":\"4 ASC(s)\",\"Description\":\"cgminer 4.10.0\"}],\"DEVS\":[{\"ASC\":0,\"Name\":\"IT1\",\"ID\":0,\"Enabled\":\"Y\",\"Status\":\"Alive\",\"Temperature\":73.00,\"MHS av\":5500742.15,\"MHS 5s\":7411951.60,\"MHS 1m\":6258409.32,\"MHS 5m\":5948469.16,\"MHS 15m\":5726065.81,\"Accepted\":855,\"Rejected\":6,\"Hardware Errors\":938,\"Utility\":4.28,\"Last Share Pool\":0,\"Last Share Time\":1586623415,\"Total MH\":65895924130.0000,\"Diff1 Work\":15342592,\"Difficulty Accepted\":15644672.00000000,\"Difficulty Rejected\":30720.00000000,\"Last Share Difficulty\":16384.00000000,\"Last Valid Work\":1586623417,\"Device Hardware%\":0.0061,\"Device Rejected%\":0.2002,\"Device Elapsed\":11979,\"fminerled\":0},{\"ASC\":1,\"Name\":\"IT1\",\"ID\":1,\"Enabled\":\"Y\",\"Status\":\"Alive\",\"Temperature\":73.00,\"MHS av\":5647227.92,\"MHS 5s\":5384127.29,\"MHS 1m\":5084304.27,\"MHS 5m\":5376539.74,\"MHS 15m\":5587520.52,\"Accepted\":841,\"Rejected\":5,\"Hardware Errors\":690,\"Utility\":4.21,\"Last Share Pool\":0,\"Last Share Time\":1586623407,\"Total MH\":67650744564.0000,\"Diff1 Work\":15751168,\"Difficulty Accepted\":15849472.00000000,\"Difficulty Rejected\":14336.00000000,\"Last Share Difficulty\":16384.00000000,\"Last Valid Work\":1586623414,\"Device Hardware%\":0.0044,\"Device Rejected%\":0.0910,\"Device Elapsed\":11979,\"fminerled\":0},{\"ASC\":2,\"Name\":\"IT1\",\"ID\":2,\"Enabled\":\"Y\",\"Status\":\"Alive\",\"Temperature\":73.00,\"MHS av\":5324885.82,\"MHS 5s\":6123225.77,\"MHS 1m\":5763196.92,\"MHS 5m\":5785709.44,\"MHS 15m\":5576075.49,\"Accepted\":819,\"Rejected\":6,\"Hardware Errors\":1394,\"Utility\":4.10,\"Last Share Pool\":0,\"Last Share Time\":1586623396,\"Total MH\":63789260071.0000,\"Diff1 Work\":14852096,\"Difficulty Accepted\":14985216.00000000,\"Difficulty Rejected\":26624.00000000,\"Last Share Difficulty\":16384.00000000,\"Last Valid Work\":1586623417,\"Device Hardware%\":0.0094,\"Device Rejected%\":0.1793,\"Device Elapsed\":11979,\"fminerled\":0},{\"ASC\":3,\"Name\":\"IT1\",\"ID\":3,\"Enabled\":\"Y\",\"Status\":\"Alive\",\"Temperature\":74.00,\"MHS av\":5437962.55,\"MHS 5s\":6944681.28,\"MHS 1m\":6104675.74,\"MHS 5m\":5612685.64,\"MHS 15m\":5502303.13,\"Accepted\":839,\"Rejected\":8,\"Hardware Errors\":1093,\"Utility\":4.20,\"Last Share Pool\":0,\"Last Share Time\":1586623416,\"Total MH\":65143858324.0000,\"Diff1 Work\":15168512,\"Difficulty Accepted\":15192064.00000000,\"Difficulty Rejected\":20480.00000000,\"Last Share Difficulty\":16384.00000000,\"Last Valid Work\":1586623417,\"Device Hardware%\":0.0072,\"Device Rejected%\":0.1350,\"Device Elapsed\":11979,\"fminerled\":0}],\"id\":1}"))),
                Detection.builder()
                        .minerType(AixinType.AIXIN_A1)
                        .ipAddress("127.0.0.1")
                        .port(4028)
                        .parameters(DEFAULT_ARGS)
                        .build());
    }
}
