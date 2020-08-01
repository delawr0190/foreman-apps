package mn.foreman.blackminer;

import mn.foreman.antminer.util.AntminerRebootITest;
import mn.foreman.util.rpc.RpcHandler;
import mn.foreman.util.rpc.SkipFirstDecorator;

import com.google.common.collect.ImmutableMap;

/** Tests rebooting of a blackminer. */
public class BlackminerRebootITest
        extends AntminerRebootITest {

    /** Constructor. */
    public BlackminerRebootITest() {
        super(
                new BlackminerFactory(),
                "blackMiner Configuration",
                ImmutableMap.of(
                        "{\"command\":\"summary\"}",
                        new SkipFirstDecorator(
                                new RpcHandler(
                                        "{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1593395696,\"Code\":11,\"Msg\":\"Summary\",\"Description\":\"ccminer 2.3.3\"}],\"SUMMARY\":[{\"Elapsed\":20261,\"GHS 5s\":\"2.2329\",\"GHS av\":\"1.5698\",\"Found Blocks\":0,\"Getworks\":820,\"Accepted\":\"  6.0000\",\"Rejected\":\"  6.0000\",\"Hardware Errors\":1531,\"Utility\":2.68,\"Discarded\":12216,\"Stale\":0,\"Get Failures\":0,\"Local Work\":380193,\"Remote Failures\":0,\"Network Blocks\":671,\"Total MH\":31805691.0000,\"Work Utility\":21.75,\"Difficulty Accepted\":7296.00000000,\"Difficulty Rejected\":48.00000000,\"Difficulty Stale\":0.00000000,\"Best Share\":1797,\"Device Hardware%\":17.2507,\"Device Rejected%\":0.6536,\"Pool Rejected%\":0.6536,\"Pool Stale%\":0.0000,\"Last getwork\":1593395696}],\"id\":1}")),
                        "{\"command\":\"stats\"}",
                        new SkipFirstDecorator(
                                new RpcHandler(
                                        "{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1593395695,\"Code\":70,\"Msg\":\"CGMiner stats\",\"Description\":\"ccminer 2.3.3\"}],\"STATS\":[{\"CGMiner\":\"2.3.3\",\"Miner\":\"1.3.0.8\",\"CompileTime\":\"2020-06-12 16-01-07 CST\",\"Type\":\"Blackminer F1-MINI\"}{\"STATS\":0,\"ID\":\"A30\",\"Elapsed\":20261,\"Calls\":0,\"Wait\":0.000000,\"Max\":0.000000,\"Min\":99999999.000000,\"GHS 5s\":\"2.2329\",\"GHS av\":\"1.5698\",\"miner_count\":1,\"frequency\":\"490\",\"fan_num\":2,\"fan1\":1500,\"fan2\":1500,\"temp_num\":1,\"temp1\":44,\"chipTemp1\":\"[67.30, 0.00, 0.00, 0.00, -73364045383872769689863497711616.00, 0.13]\",\"temp2\":0,\"chipTemp2\":\"[0.00, 0.00, 0.00, -73364045383872769689863497711616.00, 0.13, 6300255232.00]\",\"temp3\":0,\"chipTemp3\":\"[0.00, 0.00, -73364045383872769689863497711616.00, 0.13, 6300255232.00, 37.20]\",\"temp4\":0,\"chipTemp4\":\"[0.00, -73364045383872769689863497711616.00, 0.13, 6300255232.00, 37.20, 0.00]\",\"temp_max\":44,\"Device Hardware%\":0.0000,\"no_matching_work\":1531,\"chain_acn1\":1,\"chain_acn2\":0,\"chain_acn3\":0,\"chain_acn4\":0,\"chain_acs1\":\" o\",\"chain_acs2\":\"\",\"chain_acs3\":\"\",\"chain_acs4\":\"\",\"chain_hw1\":1531,\"chain_hw2\":0,\"chain_hw3\":0,\"chain_hw4\":0,\"chain_rate1\":\"2.2329\",\"chain_rate2\":\"\",\"chain_rate3\":\"\",\"chain_rate4\":\"\"}],\"id\":1}")),
                        "{\"command\":\"pools\"}",
                        new SkipFirstDecorator(
                                new RpcHandler(
                                        "{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1593395695,\"Code\":7,\"Msg\":\"3 Pool(s)\",\"Description\":\"ccminer 2.3.3\"}],\"POOLS\":[{\"POOL\":0,\"URL\":\"xxx:8080\",\"Status\":\"Alive\",\"Priority\":0,\"Quota\":0,\"Long Poll\":\"N\",\"Getworks\":820,\"Accepted\":906,\"Rejected\":6,\"Discarded\":12216,\"Stale\":0,\"Get Failures\":0,\"Remote Failures\":0,\"User\":\"xxx\",\"Last Share Time\":\"0:00:08\",\"Diff\":\"  8.0000\",\"Diff1 Shares\":7407,\"Proxy Type\":\"\",\"Proxy\":\"\",\"Difficulty Accepted\":7296.00000000,\"Difficulty Rejected\":48.00000000,\"Difficulty Stale\":0.00000000,\"Last Share Difficulty\":8.00000000,\"Has Stratum\":true,\"Stratum Active\":true,\"Stratum URL\":\"xxx.com\",\"Has GBT\":false,\"Best Share\":\"  0.0000\",\"Pool Rejected%\":0.6536,\"Pool Stale%\":0.0000},{\"POOL\":1,\"URL\":\"\",\"Status\":\"Dead\",\"Priority\":1,\"Quota\":0,\"Long Poll\":\"N\",\"Getworks\":0,\"Accepted\":0,\"Rejected\":0,\"Discarded\":0,\"Stale\":0,\"Get Failures\":0,\"Remote Failures\":0,\"User\":\"\",\"Last Share Time\":\"0\",\"Diff\":\"  0.0000\",\"Diff1 Shares\":0,\"Proxy Type\":\"\",\"Proxy\":\"\",\"Difficulty Accepted\":0.00000000,\"Difficulty Rejected\":0.00000000,\"Difficulty Stale\":0.00000000,\"Last Share Difficulty\":0.00000000,\"Has Stratum\":false,\"Stratum Active\":false,\"Stratum URL\":\"\",\"Has GBT\":false,\"Best Share\":\"  0.0000\",\"Pool Rejected%\":0.0000,\"Pool Stale%\":0.0000},{\"POOL\":2,\"URL\":\"\",\"Status\":\"Dead\",\"Priority\":2,\"Quota\":0,\"Long Poll\":\"N\",\"Getworks\":0,\"Accepted\":0,\"Rejected\":0,\"Discarded\":0,\"Stale\":0,\"Get Failures\":0,\"Remote Failures\":0,\"User\":\"\",\"Last Share Time\":\"0\",\"Diff\":\"  0.0000\",\"Diff1 Shares\":0,\"Proxy Type\":\"\",\"Proxy\":\"\",\"Difficulty Accepted\":0.00000000,\"Difficulty Rejected\":0.00000000,\"Difficulty Stale\":0.00000000,\"Last Share Difficulty\":0.00000000,\"Has Stratum\":false,\"Stratum Active\":false,\"Stratum URL\":\"\",\"Has GBT\":false,\"Best Share\":\"  0.0000\",\"Pool Rejected%\":0.0000,\"Pool Stale%\":0.0000}],\"id\":1}"))));
    }

}
