package mn.foreman.antminer;

import mn.foreman.cgminer.CgMiner;
import mn.foreman.model.miners.FanInfo;
import mn.foreman.model.miners.MinerStats;
import mn.foreman.model.miners.Pool;
import mn.foreman.model.miners.asic.Asic;
import mn.foreman.util.AbstractApiITest;
import mn.foreman.util.rpc.FakeRpcMinerServer;
import mn.foreman.util.rpc.RpcHandler;

import com.google.common.collect.ImmutableMap;

import java.math.BigDecimal;

/** Runs an integration tests using {@link CgMiner} against a fake API. */
public class AntminerD3StatsITest
        extends AbstractApiITest {

    /** Constructor. */
    public AntminerD3StatsITest() {
        super(
                new AntminerFactory(new BigDecimal(0.001))
                        .create(
                                ImmutableMap.of(
                                        "apiIp",
                                        "127.0.0.1",
                                        "apiPort",
                                        "4028")),
                new FakeRpcMinerServer(
                        4028,
                        ImmutableMap.of(
                                "{\"command\":\"stats\"}",
                                new RpcHandler(
                                        "{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1533152610,\"Code\":70,\"Msg\":\"CGMiner stats\",\"Description\":\"cgminer 4.10.0\"}],\"STATS\":[{\"CGMiner\":\"4.10.0\",\"Miner\":\"1.0.0.9\",\"CompileTime\":\"Fri Jan 05 16:48:56 CST 2018\",\"Type\":\"Antminer D3 Blissz v2.06 beta\"}{\"STATS\":0,\"ID\":\"D10\",\"Elapsed\":54422,\"Calls\":0,\"Wait\":0.000000,\"Max\":0.000000,\"Min\":99999999.000000,\"GHS 5s\":\"16346.64\",\"GHS av\":16868.38,\"miner_count\":3,\"frequency1\":\"450\",\"frequency2\":\"450\",\"frequency3\":\"450\",\"frequency4\":\"450\",\"fan_num\":2,\"fan1\":6030,\"fan2\":5790,\"temp_num\":3,\"temp1\":46,\"temp2\":46,\"temp3\":48,\"temp4\":0,\"temp2_1\":69,\"temp2_2\":67,\"temp2_3\":62,\"temp2_4\":0,\"temp_max\":48,\"Device Hardware%\":0.0001,\"no_matching_work\":4,\"chain_acn1\":60,\"chain_acn2\":60,\"chain_acn3\":60,\"chain_acn4\":0,\"chain_acs1\":\" oooooooo oooooooo oooooooo oooooooo oooooooo oooooooo oooooooo oooo\",\"chain_acs2\":\" oooooooo oooooooo oooooooo oooooooo oooooooo oooooooo oooooooo oooo\",\"chain_acs3\":\" oooooooo oooooooo oooooooo oooooooo oooooooo oooooooo oooooooo oooo\",\"chain_acs4\":\"\",\"chain_hw1\":0,\"chain_hw2\":0,\"chain_hw3\":0,\"chain_hw4\":0,\"chain_rate1\":\"5287.83\",\"chain_rate2\":\"5631.41\",\"chain_rate3\":\"5427.41\",\"chain_rate4\":\"\"}],\"id\":1}"),
                                "{\"command\":\"pools\"}",
                                new RpcHandler(
                                        "{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1533152695,\"Code\":7,\"Msg\":\"5 Pool(s)\",\"Description\":\"cgminer 4.10.0\"}],\"POOLS\":[{\"POOL\":0,\"URL\":\"stratum+tcp://dash-eu.coinmine.pl:6099\",\"Status\":\"Alive\",\"Priority\":0,\"Quota\":0,\"Long Poll\":\"N\",\"Getworks\":1167,\"Accepted\":3038,\"Rejected\":8,\"Discarded\":28157,\"Stale\":5,\"Get Failures\":1,\"Remote Failures\":0,\"User\":\"Dash.default\",\"Last Share Time\":\"0:00:00\",\"Diff\":\" 69.2461\",\"Diff1 Shares\":6475798,\"Proxy Type\":\"\",\"Proxy\":\"\",\"Difficulty Accepted\":198412.94104085,\"Difficulty Rejected\":604.97361199,\"Difficulty Stale\":0.00000000,\"Last Share Difficulty\":69.24612531,\"Has Stratum\":true,\"Stratum Active\":true,\"Stratum URL\":\"dash-eu.coinmine.pl\",\"Has GBT\":false,\"Best Share\":\"  0.0000\",\"Pool Rejected%\":0.3040,\"Pool Stale%\":0.0000},{\"POOL\":1,\"URL\":\"\",\"Status\":\"Dead\",\"Priority\":1,\"Quota\":0,\"Long Poll\":\"N\",\"Getworks\":0,\"Accepted\":0,\"Rejected\":0,\"Discarded\":0,\"Stale\":0,\"Get Failures\":0,\"Remote Failures\":0,\"User\":\"\",\"Last Share Time\":\"0\",\"Diff\":\"  0.0000\",\"Diff1 Shares\":0,\"Proxy Type\":\"\",\"Proxy\":\"\",\"Difficulty Accepted\":0.00000000,\"Difficulty Rejected\":0.00000000,\"Difficulty Stale\":0.00000000,\"Last Share Difficulty\":0.00000000,\"Has Stratum\":false,\"Stratum Active\":false,\"Stratum URL\":\"\",\"Has GBT\":false,\"Best Share\":\"  0.0000\",\"Pool Rejected%\":0.0000,\"Pool Stale%\":0.0000},{\"POOL\":2,\"URL\":\"\",\"Status\":\"Dead\",\"Priority\":2,\"Quota\":0,\"Long Poll\":\"N\",\"Getworks\":0,\"Accepted\":0,\"Rejected\":0,\"Discarded\":0,\"Stale\":0,\"Get Failures\":0,\"Remote Failures\":0,\"User\":\"\",\"Last Share Time\":\"0\",\"Diff\":\"  0.0000\",\"Diff1 Shares\":0,\"Proxy Type\":\"\",\"Proxy\":\"\",\"Difficulty Accepted\":0.00000000,\"Difficulty Rejected\":0.00000000,\"Difficulty Stale\":0.00000000,\"Last Share Difficulty\":0.00000000,\"Has Stratum\":false,\"Stratum Active\":false,\"Stratum URL\":\"\",\"Has GBT\":false,\"Best Share\":\"  0.0000\",\"Pool Rejected%\":0.0000,\"Pool Stale%\":0.0000},\n" +
                                                "{\"POOL\":3,\"URL\":\"stratum+tcp://dash.suprnova.cc:9991\",\"Status\":\"Alive\",\"Priority\":9998,\"Quota\":0,\"Long Poll\":\"N\",\"Getworks\":1,\"Accepted\":0,\"Rejected\":0,\"Discarded\":0,\"Stale\":0,\"Get Failures\":0,\"Remote Failures\":0,\"User\":\"devFeeMiner.9\",\"Last Share Time\":\"0\",\"Diff\":\" 64.0000\",\"Diff1 Shares\":0,\"Proxy Type\":\"\",\"Proxy\":\"\",\"Difficulty Accepted\":0.00000000,\"Difficulty Rejected\":0.00000000,\"Difficulty Stale\":0.00000000,\"Last Share Difficulty\":0.00000000,\"Has Stratum\":true,\"Stratum Active\":false,\"Stratum URL\":\"\",\"Has GBT\":false,\"Best Share\":\" 0.0000\",\"Pool Rejected%\":0.0000,\"Pool Stale%\":0.0000},{\"POOL\":4,\"URL\":\"stratum+tcp://dash-eu.coinmine.pl:6099\",\"Status\":\"Alive\",\"Priority\":9999,\"Quota\":0,\"Long Poll\":\"N\",\"Getworks\":1,\"Accepted\":0,\"Rejected\":0,\"Discarded\":0,\"Stale\":0,\"Get Failures\":0,\"Remote Failures\":0,\"User\":\"devFeeMiner.1\",\"Last Share Time\":\"0\",\"Diff\":\" 20.0000\",\"Diff1 Shares\":0,\"Proxy Type\":\"\",\"Proxy\":\"\",\"Difficulty Accepted\":0.00000000,\"Difficulty Rejected\":0.00000000,\"Difficulty Stale\":0.00000000,\"Last Share Difficulty\":0.00000000,\"Has Stratum\":true,\"Stratum Active\":false,\"Stratum URL\":\"\",\"Has GBT\":false,\"Best Share\":\" 0.0000\",\"Pool Rejected%\":0.0000,\"Pool Stale%\":0.0000}],\"id\":1}"))),
                new MinerStats.Builder()
                        .setApiIp("127.0.0.1")
                        .setApiPort(4028)
                        .addPool(
                                new Pool.Builder()
                                        .setName("dash-eu.coinmine.pl:6099")
                                        .setStatus(true, true)
                                        .setPriority(0)
                                        .setCounts(3038, 8, 5)
                                        .build())
                        .addPool(
                                new Pool.Builder()
                                        .setName("dash.suprnova.cc:9991")
                                        .setStatus(true, true)
                                        .setPriority(9998)
                                        .setCounts(0, 0, 0)
                                        .build())
                        .addPool(
                                new Pool.Builder()
                                        .setName("dash-eu.coinmine.pl:6099")
                                        .setStatus(true, true)
                                        .setPriority(9999)
                                        .setCounts(0, 0, 0)
                                        .build())
                        .addAsic(
                                new Asic.Builder()
                                        .setHashRate(new BigDecimal("16346640000.00000034028280193609816706157289445400238037109375000000000000"))
                                        .setFanInfo(
                                                new FanInfo.Builder()
                                                        .setCount(2)
                                                        .addSpeed(6030)
                                                        .addSpeed(5790)
                                                        .setSpeedUnits("RPM")
                                                        .build())
                                        .addTemp(46)
                                        .addTemp(46)
                                        .addTemp(48)
                                        .addTemp(69)
                                        .addTemp(67)
                                        .addTemp(62)
                                        .hasErrors(false)
                                        .build())
                        .build());
    }
}
