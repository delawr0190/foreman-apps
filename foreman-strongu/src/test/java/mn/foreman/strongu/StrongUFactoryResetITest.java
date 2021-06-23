package mn.foreman.strongu;

import mn.foreman.antminer.StockFactoryResetAction;
import mn.foreman.antminer.util.AntminerAsyncActionITest;
import mn.foreman.antminer.util.AntminerTestUtils;
import mn.foreman.model.ApplicationConfiguration;
import mn.foreman.util.rpc.RpcHandler;
import mn.foreman.util.rpc.SkipFirstDecorator;

import com.google.common.collect.ImmutableMap;

import java.util.Collections;

/** Tests rebooting of a stu. */
public class StrongUFactoryResetITest
        extends AntminerAsyncActionITest {

    /** Constructor. */
    public StrongUFactoryResetITest() {
        super(
                Collections.emptyMap(),
                new StrongUFactory(new ApplicationConfiguration()),
                new StockFactoryResetAction(
                        "stuMiner Configuration",
                        new ApplicationConfiguration()),
                AntminerTestUtils.toFactoryResetHandlers("stuMiner Configuration"),
                ImmutableMap.of(
                        "{\"command\":\"summary\"}",
                        new SkipFirstDecorator(
                                new RpcHandler(
                                        "{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1592273807,\"Code\":11,\"Msg\":\"Summary\",\"Description\":\"cgminer 4.11.1\"}],\"SUMMARY\":[{\"Elapsed\":2416,\"MHS av\":387186.77,\"MHS 5s\":397759.08,\"MHS 1m\":405927.53,\"MHS 5m\":410826.48,\"MHS 15m\":378651.35,\"Miner type\":\"dash\",\"Fan Num\":2,\"Fan1\":7800,\"Fan2\":7800,\"Found Blocks\":4,\"Getworks\":85,\"Accepted\":162,\"Rejected\":4,\"Hardware Errors\":8061,\"Utility\":4.02,\"Discarded\":33856,\"Stale\":1,\"Get Failures\":0,\"Local Work\":66696,\"Remote Failures\":0,\"Network Blocks\":37,\"Total MH\":935262073.0000,\"Work Utility\":676.15,\"Difficulty Accepted\":192509.06250000,\"Difficulty Rejected\":4863.92578125,\"Difficulty Stale\":1023.98437500,\"Best Share\":8811756,\"Device Hardware%\":22.8473,\"Device Rejected%\":17.8683,\"Pool Rejected%\":2.4516,\"Pool Stale%\":0.5161,\"Last getwork\":1592273806}],\"id\":1}")),
                        "{\"command\":\"devs\"}",
                        new SkipFirstDecorator(
                                new RpcHandler(
                                        "{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1592273807,\"Code\":9,\"Msg\":\"2 ASC(s)\",\"Description\":\"cgminer 4.11.1\"}],\"DEVS\":[{\"ASC\":0,\"Name\":\"u6\",\"ID\":0,\"Enabled\":\"Y\",\"Status\":\"Alive\",\"Temperature\":54.56,\"volt\":10.500,\"ChipNum\":70,\"Frequency\":550,\"MHS av\":190748.48,\"MHS 5s\":191920.49,\"MHS 1m\":194433.37,\"MHS 5m\":201864.17,\"MHS 15m\":186301.66,\"Accepted\":77,\"Rejected\":2,\"Hardware Errors\":4552,\"Utility\":1.91,\"Last Share Pool\":0,\"Last Share Time\":1592273789,\"Total MH\":460759065.0000,\"Diff1 Work\":13410,\"Difficulty Accepted\":91134.60937500,\"Difficulty Rejected\":2303.96484375,\"Last Share Difficulty\":2047.96875000,\"Last Valid Work\":1592273807,\"Device Hardware%\":25.3424,\"Device Rejected%\":17.1809,\"Device Elapsed\":2416,\"Reboot Count\":0},{\"ASC\":2,\"Name\":\"u6\",\"ID\":1,\"Enabled\":\"Y\",\"Status\":\"Alive\",\"Temperature\":55.62,\"volt\":12.700,\"ChipNum\":70,\"Frequency\":550,\"MHS av\":196438.30,\"MHS 5s\":208240.11,\"MHS 1m\":211732.23,\"MHS 5m\":208990.13,\"MHS 15m\":192341.76,\"Accepted\":85,\"Rejected\":2,\"Hardware Errors\":3509,\"Utility\":2.11,\"Last Share Pool\":0,\"Last Share Time\":1592273802,\"Total MH\":474503008.0000,\"Diff1 Work\":13811,\"Difficulty Accepted\":101374.45312500,\"Difficulty Rejected\":2559.96093750,\"Last Share Difficulty\":2047.96875000,\"Last Valid Work\":1592273807,\"Device Hardware%\":20.2598,\"Device Rejected%\":18.5357,\"Device Elapsed\":2416,\"Reboot Count\":0}],\"id\":1}")),
                        "{\"command\":\"pools\"}",
                        new SkipFirstDecorator(
                                new RpcHandler(
                                        "{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1592273807,\"Code\":7,\"Msg\":\"3 Pool(s)\",\"Description\":\"cgminer 4.11.1\"}],\"POOLS\":[{\"POOL\":0,\"URL\":\"stratum+tcp://img.f2pool.com:4400\",\"Status\":\"Alive\",\"Priority\":0,\"Quota\":1,\"Long Poll\":\"N\",\"Getworks\":82,\"Accepted\":162,\"Rejected\":4,\"Works\":32793,\"Discarded\":33856,\"Stale\":1,\"Get Failures\":0,\"Remote Failures\":0,\"User\":\"xxx\",\"Last Share Time\":\"Tue Jun 16 10:16:42 2020\n\",\"Diff1 Shares\":27220,\"Proxy Type\":\"\",\"Proxy\":\"\",\"Difficulty Accepted\":192509.06250000,\"Difficulty Rejected\":4863.92578125,\"Difficulty Stale\":1023.98437500,\"Last Share Difficulty\":2047.96875000,\"Work Difficulty\":2047.96875000,\"Has Stratum\":true,\"Stratum Active\":true,\"Stratum URL\":\"img.f2pool.com\",\"Stratum Difficulty\":2047.96875000,\"Has Vmask\":false,\"Has GBT\":false,\"Best Share\":8811756,\"Pool Rejected%\":2.4516,\"Pool Stale%\":0.5161,\"Bad Work\":79,\"Current Block Height\":797103,\"Current Block Version\":536870912},{\"POOL\":1,\"URL\":\"stratum+tcp://img.f2pool.com:4400\",\"Status\":\"Alive\",\"Priority\":1,\"Quota\":1,\"Long Poll\":\"N\",\"Getworks\":1,\"Accepted\":0,\"Rejected\":0,\"Works\":0,\"Discarded\":0,\"Stale\":0,\"Get Failures\":0,\"Remote Failures\":0,\"User\":\"xxx\",\"Last Share Time\":\"Thu Jan  1 08:00:00 1970\n\",\"Diff1 Shares\":0,\"Proxy Type\":\"\",\"Proxy\":\"\",\"Difficulty Accepted\":0.00000000,\"Difficulty Rejected\":0.00000000,\"Difficulty Stale\":0.00000000,\"Last Share Difficulty\":0.00000000,\"Work Difficulty\":0.00000000,\"Has Stratum\":true,\"Stratum Active\":false,\"Stratum URL\":\"\",\"Stratum Difficulty\":0.00000000,\"Has Vmask\":false,\"Has GBT\":false,\"Best Share\":0,\"Pool Rejected%\":0.0000,\"Pool Stale%\":0.0000,\"Bad Work\":1,\"Current Block Height\":0,\"Current Block Version\":536870912},{\"POOL\":2,\"URL\":\"stratum+tcp://img.f2pool.com:4400\",\"Status\":\"Alive\",\"Priority\":2,\"Quota\":1,\"Long Poll\":\"N\",\"Getworks\":2,\"Accepted\":0,\"Rejected\":0,\"Works\":0,\"Discarded\":0,\"Stale\":0,\"Get Failures\":0,\"Remote Failures\":0,\"User\":\"xxx\",\"Last Share Time\":\"Thu Jan  1 08:00:00 1970\n\",\"Diff1 Shares\":0,\"Proxy Type\":\"\",\"Proxy\":\"\",\"Difficulty Accepted\":0.00000000,\"Difficulty Rejected\":0.00000000,\"Difficulty Stale\":0.00000000,\"Last Share Difficulty\":0.00000000,\"Work Difficulty\":255.99609375,\"Has Stratum\":true,\"Stratum Active\":false,\"Stratum URL\":\"\",\"Stratum Difficulty\":0.00000000,\"Has Vmask\":false,\"Has GBT\":false,\"Best Share\":0,\"Pool Rejected%\":0.0000,\"Pool Stale%\":0.0000,\"Bad Work\":2,\"Current Block Height\":797068,\"Current Block Version\":536870912}],\"id\":1}"))),
                true,
                true);
    }
}