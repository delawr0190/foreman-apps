package mn.foreman.ibelink;

import mn.foreman.cgminer.CgMinerDetectionStrategy;
import mn.foreman.cgminer.NullPatchingStrategy;
import mn.foreman.cgminer.request.CgMinerCommand;
import mn.foreman.model.ApplicationConfiguration;
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

/** Tests iBeLink detection. */
@RunWith(Parameterized.class)
public class IBeLinkDetectITest
        extends AbstractDetectITest {

    /**
     * Constructor.
     *
     * @param handlers     The handlers.
     * @param expectedType The expected type.
     * @param expectedMac  The expected mac.
     */
    public IBeLinkDetectITest(
            final Map<String, HandlerInterface> handlers,
            final IBeLinkType expectedType,
            final String expectedMac) {
        super(
                new CgMinerDetectionStrategy(
                        CgMinerCommand.DEVS,
                        new IBeLinkTypeFactory(),
                        new IBeLinkMacStrategy(
                                "127.0.0.1",
                                4029,
                                new ApplicationConfiguration()),
                        new NullPatchingStrategy(),
                        new ApplicationConfiguration()),
                "127.0.0.1",
                4029,
                DEFAULT_ARGS,
                () -> new FakeRpcMinerServer(
                        4029,
                        handlers),
                Detection.builder()
                        .minerType(expectedType)
                        .ipAddress("127.0.0.1")
                        .port(4029)
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
                                // N1Max
                                ImmutableMap.of(
                                        "{\"command\":\"summary\"}",
                                        new RpcHandler(
                                                "{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1626315828,\"Code\":11,\"Msg\":\"Summary\",\"Description\":\"cgminer 5.4.8\"}],\"SUMMARY\":[{\"Elapsed\":20576,\"MHS av\":10636758.32,\"MHS 5s\":10601538.46,\"Found Blocks\":0,\"Getworks\":2346,\"Received\":54446.000000,\"Accepted\":53438,\"Rejected\":957,\"Hardware Errors\":51,\"Utility\":155.83,\"Discarded\":4692,\"Stale\":0,\"Get Failures\":0,\"Local Work\":14080,\"Remote Failures\":0,\"Network Blocks\":0,\"Total MH\":218858228437.5000,\"Work Utility\":158.62,\"Difficulty Accepted\":4768.29882246,\"Difficulty Rejected\":912652.39461791,\"Difficulty Stale\":0.00000000,\"Best Share\":0,\"Device Hardware%\":0.0937,\"Device Rejected%\":1677.8241,\"Pool Rejected%\":99.4802,\"Pool Stale%\":0.0000,\"Temp1\":57.75,\"Temp2\":58.25,\"Temp3\":57.62,\"Temp4\":0.00,\"Temp5\":0.00,\"Temp6\":0.00,\"Temp7\":0.00,\"Temp8\":0.00,\"PLL\":455,\"PWM\":2520,\"PWM1\":2520,\"PWM2\":2490,\"PWM3\":2460,\"VOLTAGESET\":135,\"VOLTAGE\":135,\"FPGAVer\":\"\",\"Hash Method\":\"9102\",\"Error\":0.000000,\"Same\":0.000000,\"Power Type\":\"QianBen3600\",\"macaddress\":\"1001291b0206\"}],\"id\":1}"),
                                        "{\"command\":\"devs\"}",
                                        new RpcHandler(
                                                "{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1626315828,\"Code\":9,\"Msg\":\" - 3 PGA(s) - \",\"Description\":\"cgminer 5.4.8\"}],\"DEVS\":[{\"PGA\":0,\"PLL\":455,\"Baudrate\":1500000,\"H3Baudrate\":1500000,\"chipBaudrate\":1500000,\"CurPool\":0,\"ActivePer\":0.180556,\"ActiveNum\":26,\"Active\":4011027440002501224,\"ActiveStr\":\"37aa083491b18a68\",\"Badnonce\":0,\"TestPll\":0,\"TestPllStr\":\"ffffffffffffffff\",\"RunTime\":20575,\"IsTest\":false,\"Version\":\"cgminer 5.4.8\",\"Asics\":144,\"Temperature\":0.00,\"Name\":\"BM-N1Max\",\"ID\":0,\"Enabled\":\"Y\",\"Status\":\"Alive\",\"MHS av\":3580422.87,\"MHS 5s\":3731149.63,\"Send\":2347,\"SendErr\":0,\"Recieved\":18293,\"Accepted\":17989,\"Rejected\":303,\"Hardware Errors\":1,\"bistchip\":0,\"Utility\":52.46,\"Last Share Pool\":0,\"Last Share Time\":1626315828,\"Total MH\":0.0000,\"Frequency\":0.00,\"Diff1 Work\":18292,\"Difficulty Accepted\":2860.97929347,\"Difficulty Rejected\":288958.90864078,\"Last Share Difficulty\":953.65976449,\"Last Valid Work\":1626315828,\"Device Hardware%\":0.0055,\"Device Rejected%\":1579.7010,\"Device Elapsed\":20576,\"Chain SN\":\"00233591022042311\"},{\"PGA\":1,\"PLL\":450,\"Baudrate\":1500000,\"H3Baudrate\":1500000,\"chipBaudrate\":1500000,\"CurPool\":0,\"ActivePer\":0.166667,\"ActiveNum\":24,\"Active\":12440731282391493698,\"ActiveStr\":\"aca65b300108ec42\",\"Badnonce\":0,\"TestPll\":0,\"TestPllStr\":\"ffffffffffffffff\",\"RunTime\":20575,\"IsTest\":false,\"Version\":\"cgminer 5.4.8\",\"Asics\":144,\"Temperature\":0.00,\"Name\":\"BM-N1Max\",\"ID\":1,\"Enabled\":\"Y\",\"Status\":\"Alive\",\"MHS av\":3504976.40,\"MHS 5s\":3424624.59,\"Send\":2346,\"SendErr\":0,\"Recieved\":17938,\"Accepted\":17545,\"Rejected\":355,\"Hardware Errors\":38,\"bistchip\":0,\"Utility\":51.16,\"Last Share Pool\":0,\"Last Share Time\":1626315828,\"Total MH\":0.0000,\"Frequency\":0.00,\"Diff1 Work\":17965,\"Difficulty Accepted\":953.65976449,\"Difficulty Rejected\":340456.53592329,\"Last Share Difficulty\":953.65976449,\"Last Valid Work\":1626315828,\"Device Hardware%\":0.2111,\"Device Rejected%\":1895.1101,\"Device Elapsed\":20576,\"Chain SN\":\"00217191022042311\"},{\"PGA\":2,\"PLL\":455,\"Baudrate\":1500000,\"H3Baudrate\":1500000,\"chipBaudrate\":1500000,\"CurPool\":0,\"ActivePer\":0.159722,\"ActiveNum\":23,\"Active\":15925053910352440067,\"ActiveStr\":\"dd0128282b80ab03\",\"Badnonce\":0,\"TestPll\":0,\"TestPllStr\":\"ffffffffffffffff\",\"RunTime\":20575,\"IsTest\":false,\"Version\":\"cgminer 5.4.8\",\"Asics\":144,\"Temperature\":0.00,\"Name\":\"BM-N1Max\",\"ID\":2,\"Enabled\":\"Y\",\"Status\":\"Alive\",\"MHS av\":3551359.06,\"MHS 5s\":3445764.25,\"Send\":2347,\"SendErr\":0,\"Recieved\":18140,\"Accepted\":17841,\"Rejected\":297,\"Hardware Errors\":2,\"bistchip\":0,\"Utility\":52.03,\"Last Share Pool\":0,\"Last Share Time\":1626315827,\"Total MH\":0.0000,\"Frequency\":0.00,\"Diff1 Work\":18138,\"Difficulty Accepted\":953.65976449,\"Difficulty Rejected\":283236.95005383,\"Last Share Difficulty\":953.65976449,\"Last Valid Work\":1626315827,\"Device Hardware%\":0.0110,\"Device Rejected%\":1561.5666,\"Device Elapsed\":20576,\"Chain SN\":\"00233491022042311\"}],\"id\":1}"),
                                        "{\"command\":\"pools\"}",
                                        new RpcHandler(
                                                "{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1626315828,\"Code\":7,\"Msg\":\"2 Pool(s)\",\"Description\":\"cgminer 5.4.8\"}],\"POOLS\":[{\"POOL\":0,\"URL\":\"stratum+tcp://ckb.2miners.com:6565\",\"Status\":\"Alive\",\"Priority\":0,\"Quota\":1,\"Long Poll\":\"N\",\"Getworks\":2346,\"Accepted\":53438,\"Rejected\":957,\"Works\":7042,\"Discarded\":4692,\"Stale\":0,\"Get Failures\":0,\"Remote Failures\":0,\"User\":\"xxx.I2\",\"Passwd\":\"x\",\"Last Share Time\":1626315828,\"Diff1 Shares\":54395,\"Proxy Type\":\"\",\"Proxy\":\"\",\"Difficulty Accepted\":50961670.49487121,\"Difficulty Rejected\":912652.39461791,\"Difficulty Stale\":0.00000000,\"Last Share Difficulty\":953.65976449,\"Has Stratum\":true,\"Stratum Active\":true,\"Stratum URL\":\"ckb.2miners.com\",\"Has GBT\":false,\"Best Share\":0,\"Pool Rejected%\":1.7594,\"Pool Stale%\":0.0000},{\"POOL\":1,\"URL\":\"stratum+tcp://ckb.2miners.com:6565\",\"Status\":\"Alive\",\"Priority\":1,\"Quota\":1,\"Long Poll\":\"N\",\"Getworks\":0,\"Accepted\":0,\"Rejected\":0,\"Works\":0,\"Discarded\":0,\"Stale\":0,\"Get Failures\":0,\"Remote Failures\":0,\"User\":\"xxx.I2\",\"Passwd\":\"x\",\"Last Share Time\":0,\"Diff1 Shares\":0,\"Proxy Type\":\"\",\"Proxy\":\"\",\"Difficulty Accepted\":0.00000000,\"Difficulty Rejected\":0.00000000,\"Difficulty Stale\":0.00000000,\"Last Share Difficulty\":0.00000000,\"Has Stratum\":true,\"Stratum Active\":false,\"Stratum URL\":\"\",\"Has GBT\":false,\"Best Share\":0,\"Pool Rejected%\":0.0000,\"Pool Stale%\":0.0000}],\"id\":1}")),
                                IBeLinkType.BM_N1_MAX,
                                "10:01:29:1b:02:06"
                        }
                });
    }
}