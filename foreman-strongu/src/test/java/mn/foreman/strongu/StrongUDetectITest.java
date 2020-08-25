package mn.foreman.strongu;

import mn.foreman.cgminer.CgMinerDetectionStrategy;
import mn.foreman.cgminer.request.CgMinerCommand;
import mn.foreman.model.Detection;
import mn.foreman.util.AbstractDetectITest;
import mn.foreman.util.rpc.FakeRpcMinerServer;
import mn.foreman.util.rpc.HandlerInterface;
import mn.foreman.util.rpc.RpcHandler;

import com.google.common.collect.ImmutableMap;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

/** Tests strongu detection. */
@RunWith(Parameterized.class)
public class StrongUDetectITest
        extends AbstractDetectITest {

    /**
     * Constructor.
     *
     * @param handlers     The handlers.
     * @param expectedType The expected type.
     */
    public StrongUDetectITest(
            final Map<String, HandlerInterface> handlers,
            final StrongUType expectedType) {
        super(
                new CgMinerDetectionStrategy(
                        CgMinerCommand.DEVS,
                        new StrongUTypeFactory()),
                () -> new FakeRpcMinerServer(
                        4028,
                        handlers),
                Detection.builder()
                        .minerType(expectedType)
                        .ipAddress("127.0.0.1")
                        .port(4028)
                        .parameters(DEFAULT_ARGS)
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
                                // StrongU STU 6
                                ImmutableMap.of(
                                        "{\"command\":\"devs\"}",
                                        new RpcHandler(
                                                "{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1592273807,\"Code\":9,\"Msg\":\"2 ASC(s)\",\"Description\":\"cgminer 4.11.1\"}],\"DEVS\":[{\"ASC\":0,\"Name\":\"u6\",\"ID\":0,\"Enabled\":\"Y\",\"Status\":\"Alive\",\"Temperature\":54.56,\"volt\":10.500,\"ChipNum\":70,\"Frequency\":550,\"MHS av\":190748.48,\"MHS 5s\":191920.49,\"MHS 1m\":194433.37,\"MHS 5m\":201864.17,\"MHS 15m\":186301.66,\"Accepted\":77,\"Rejected\":2,\"Hardware Errors\":4552,\"Utility\":1.91,\"Last Share Pool\":0,\"Last Share Time\":1592273789,\"Total MH\":460759065.0000,\"Diff1 Work\":13410,\"Difficulty Accepted\":91134.60937500,\"Difficulty Rejected\":2303.96484375,\"Last Share Difficulty\":2047.96875000,\"Last Valid Work\":1592273807,\"Device Hardware%\":25.3424,\"Device Rejected%\":17.1809,\"Device Elapsed\":2416,\"Reboot Count\":0},{\"ASC\":2,\"Name\":\"u6\",\"ID\":1,\"Enabled\":\"Y\",\"Status\":\"Alive\",\"Temperature\":55.62,\"volt\":12.700,\"ChipNum\":70,\"Frequency\":550,\"MHS av\":196438.30,\"MHS 5s\":208240.11,\"MHS 1m\":211732.23,\"MHS 5m\":208990.13,\"MHS 15m\":192341.76,\"Accepted\":85,\"Rejected\":2,\"Hardware Errors\":3509,\"Utility\":2.11,\"Last Share Pool\":0,\"Last Share Time\":1592273802,\"Total MH\":474503008.0000,\"Diff1 Work\":13811,\"Difficulty Accepted\":101374.45312500,\"Difficulty Rejected\":2559.96093750,\"Last Share Difficulty\":2047.96875000,\"Last Valid Work\":1592273807,\"Device Hardware%\":20.2598,\"Device Rejected%\":18.5357,\"Device Elapsed\":2416,\"Reboot Count\":0}],\"id\":1}")),
                                StrongUType.STU_U6
                        },
                        {
                                // StrongU STU 2
                                ImmutableMap.of(
                                        "{\"command\":\"devs\"}",
                                        new RpcHandler(
                                                "{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1539628509,\"Code\":9,\"Msg\":\"3 ASC(s)\",\"Description\":\"cgminer 4.10.0\"}],\"DEVS\":[{\"ASC\":0,\"Name\":\"u2000\",\"ID\":0,\"Enabled\":\"Y\",\"Status\":\"Alive\",\"Temperature\":65.88,\"volt\":10.500,\"Frequency\":320,\"MHS av\":2064441.76,\"MHS 5s\":2223840.87,\"MHS 1m\":2299301.52,\"MHS 5m\":2172043.96,\"MHS 15m\":2127127.36,\"Accepted\":8437,\"Rejected\":7,\"Hardware Errors\":66,\"Utility\":40.99,\"Last Share Pool\":0,\"Last Share Time\":1539628508,\"Total MH\":25494924960.0000,\"Diff1 Work\":8480,\"Difficulty Accepted\":5905900.00000000,\"Difficulty Rejected\":4900.00000000,\"Last Share Difficulty\":700.00000000,\"Last Valid Work\":1539628508,\"Device Hardware%\":0.7723,\"Device Rejected%\":57.7830,\"Device Elapsed\":12350},{\"ASC\":1,\"Name\":\"u2000\",\"ID\":1,\"Enabled\":\"Y\",\"Status\":\"Alive\",\"Temperature\":70.44,\"volt\":11.600,\"Frequency\":320,\"MHS av\":2066632.80,\"MHS 5s\":2686531.81,\"MHS 1m\":2286023.12,\"MHS 5m\":2090038.23,\"MHS 15m\":2018880.25,\"Accepted\":8419,\"Rejected\":4,\"Hardware Errors\":68,\"Utility\":40.90,\"Last Share Pool\":0,\"Last Share Time\":1539628508,\"Total MH\":25521983253.0000,\"Diff1 Work\":8489,\"Difficulty Accepted\":5893300.00000000,\"Difficulty Rejected\":2800.00000000,\"Last Share Difficulty\":700.00000000,\"Last Valid Work\":1539628508,\"Device Hardware%\":0.7947,\"Device Rejected%\":32.9839,\"Device Elapsed\":12350},{\"ASC\":2,\"Name\":\"u2000\",\"ID\":2,\"Enabled\":\"Y\",\"Status\":\"Alive\",\"Temperature\":68.19,\"volt\":12.700,\"Frequency\":320,\"MHS av\":2040583.83,\"MHS 5s\":1644264.32,\"MHS 1m\":1736097.59,\"MHS 5m\":1970805.77,\"MHS 15m\":2059870.86,\"Accepted\":8330,\"Rejected\":4,\"Hardware Errors\":66,\"Utility\":40.47,\"Last Share Pool\":0,\"Last Share Time\":1539628505,\"Total MH\":25200290214.0000,\"Diff1 Work\":8382,\"Difficulty Accepted\":5831000.00000000,\"Difficulty Rejected\":2800.00000000,\"Last Share Difficulty\":700.00000000,\"Last Valid Work\":1539628505,\"Device Hardware%\":0.7812,\"Device Rejected%\":33.4049,\"Device Elapsed\":12350}],\"id\":1}")),
                                StrongUType.STU_U2
                        }
                });
    }
}