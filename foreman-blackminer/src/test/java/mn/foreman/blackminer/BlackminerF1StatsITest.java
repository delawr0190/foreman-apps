package mn.foreman.blackminer;

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
public class BlackminerF1StatsITest
        extends AbstractApiITest {

    /** Constructor. */
    public BlackminerF1StatsITest() {
        super(
                new BlackminerFactory()
                        .create(
                                ImmutableMap.of(
                                        "apiIp",
                                        "127.0.0.1",
                                        "apiPort",
                                        "4028")),
                new FakeRpcMinerServer(
                        4028,
                        ImmutableMap.of(
                                "{\"command\":\"summary\"}",
                                new RpcHandler(
                                        "{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1544107423,\"Code\":11,\"Msg\":\"Summary\",\"Description\":\"ccminer 2.3.3\"}],\"SUMMARY\":[{\"Elapsed\":780,\"GHS 5s\":\"16.0032\",\"GHS av\":\"14.6359\",\"Found Blocks\":0,\"Getworks\":21,\"Accepted\":\"  0.0000\",\"Rejected\":\"  0.0000\",\"Hardware Errors\":0,\"Utility\":11.78,\"Discarded\":409,\"Stale\":0,\"Get Failures\":0,\"Local Work\":55481,\"Remote Failures\":0,\"Network Blocks\":14,\"Total MH\":42589751.0000,\"Work Utility\":11.78,\"Difficulty Accepted\":153.00000000,\"Difficulty Rejected\":0.00000000,\"Difficulty Stale\":0.00000000,\"Best Share\":105614,\"Device Hardware%\":0.0000,\"Device Rejected%\":0.0000,\"Pool Rejected%\":0.0000,\"Pool Stale%\":0.0000,\"Last getwork\":1544107423}],\"id\":1}"),
                                "{\"command\":\"stats\"}",
                                new RpcHandler(
                                        "{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1544107423,\"Code\":70,\"Msg\":\"CGMiner stats\",\"Description\":\"ccminer 2.3.3\"}],\"STATS\":[{\"CGMiner\":\"2.3.3\",\"Miner\":\"1.3.0.8\",\"CompileTime\":\"2018-11-19 20-59-09 CST\",\"Type\":\"Blackminer F1\"}{\"STATS\":0,\"ID\":\"A30\",\"Elapsed\":780,\"Calls\":0,\"Wait\":0.000000,\"Max\":0.000000,\"Min\":99999999.000000,\"GHS 5s\":\"16.0032\",\"GHS av\":\"14.6359\",\"miner_count\":2,\"frequency\":\"460\",\"fan_num\":2,\"fan1\":3270,\"fan2\":3600,\"temp_num\":2,\"temp1\":58,\"temp2\":51,\"temp3\":0,\"temp4\":0,\"temp_max\":58,\"Device Hardware%\":0.0000,\"no_matching_work\":0,\"chain_acn1\":6,\"chain_acn2\":6,\"chain_acn3\":0,\"chain_acn4\":0,\"chain_acs1\":\" xooxxo\",\"chain_acs2\":\" ooooxx\",\"chain_acs3\":\"\",\"chain_acs4\":\"\",\"chain_hw1\":0,\"chain_hw2\":0,\"chain_hw3\":0,\"chain_hw4\":0,\"chain_rate1\":\"28.0016\",\"chain_rate2\":\"28.0016\",\"chain_rate3\":\"\",\"chain_rate4\":\"\"}],\"id\":1}"),
                                "{\"command\":\"pools\"}",
                                new RpcHandler(
                                        "{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1544102496,\"Code\":7,\"Msg\":\"3 Pool(s)\",\"Description\":\"ccminer 2.3.3\"}],\"POOLS\":[{\"POOL\":0,\"URL\":\"stratum+tcp://my.pool.com:18888\",\"Status\":\"Alive\",\"Priority\":0,\"Quota\":0,\"Long Poll\":\"N\",\"Getworks\":6692,\"Accepted\":18844,\"Rejected\":0,\"Discarded\":122140,\"Stale\":166,\"Get Failures\":98,\"Remote Failures\":32,\"User\":\"x\",\"Last Share Time\":\"0:00:03\",\"Diff\":\"  1.0000\",\"Diff1 Shares\":2769349,\"Proxy Type\":\"\",\"Proxy\":\"\",\"Difficulty Accepted\":18844.00000000,\"Difficulty Rejected\":0.00000000,\"Difficulty Stale\":145.00000000,\"Last Share Difficulty\":1.00000000,\"Has Stratum\":true,\"Stratum Active\":true,\"Stratum URL\":\"pool.vrsc.52hash.com\",\"Has GBT\":false,\"Best Share\":\"  0.0000\",\"Pool Rejected%\":0.0000,\"Pool Stale%\":0.7636},{\"POOL\":1,\"URL\":\"stratum+tcp://my.pool.org:8888\",\"Status\":\"Alive\",\"Priority\":1,\"Quota\":0,\"Long Poll\":\"N\",\"Getworks\":241,\"Accepted\":1747,\"Rejected\":0,\"Discarded\":4500,\"Stale\":5,\"Get Failures\":2,\"Remote Failures\":1,\"User\":\"x\",\"Last Share Time\":\"27:27:20\",\"Diff\":\"  1.0000\",\"Diff1 Shares\":96714,\"Proxy Type\":\"\",\"Proxy\":\"\",\"Difficulty Accepted\":1747.00000000,\"Difficulty Rejected\":0.00000000,\"Difficulty Stale\":5.00000000,\"Last Share Difficulty\":1.00000000,\"Has Stratum\":true,\"Stratum Active\":false,\"Stratum URL\":\"\",\"Has GBT\":false,\"Best Share\":\"  0.0000\",\"Pool Rejected%\":0.0000,\"Pool Stale%\":0.2854},{\"POOL\":2,\"URL\":\"stratum+tcp://my.pool.net:4646\",\"Status\":\"Dead\",\"Priority\":2,\"Quota\":0,\"Long Poll\":\"N\",\"Getworks\":2884,\"Accepted\":4117,\"Rejected\":0,\"Discarded\":1739,\"Stale\":335,\"Get Failures\":177,\"Remote Failures\":9,\"User\":\"x\",\"Last Share Time\":\"19:10:24\",\"Diff\":\"  1.0000\",\"Diff1 Shares\":38403,\"Proxy Type\":\"\",\"Proxy\":\"\",\"Difficulty Accepted\":4117.00000000,\"Difficulty Rejected\":0.00000000,\"Difficulty Stale\":306.00000000,\"Last Share Difficulty\":1.00000000,\"Has Stratum\":true,\"Stratum Active\":false,\"Stratum URL\":\"\",\"Has GBT\":false,\"Best Share\":\"  0.0000\",\"Pool Rejected%\":0.0000,\"Pool Stale%\":6.9184}],\"id\":1}"))),
                new MinerStats.Builder()
                        .setApiIp("127.0.0.1")
                        .setApiPort(4028)
                        .addPool(
                                new Pool.Builder()
                                        .setName("my.pool.com:18888")
                                        .setPriority(0)
                                        .setStatus(
                                                true,
                                                true)
                                        .setCounts(
                                                18844,
                                                0,
                                                166)
                                        .build())
                        .addPool(
                                new Pool.Builder()
                                        .setName("my.pool.org:8888")
                                        .setPriority(1)
                                        .setStatus(
                                                true,
                                                true)
                                        .setCounts(
                                                1747,
                                                0,
                                                5)
                                        .build())
                        .addPool(
                                new Pool.Builder()
                                        .setName("my.pool.net:4646")
                                        .setPriority(2)
                                        .setStatus(
                                                true,
                                                false)
                                        .setCounts(
                                                4117,
                                                0,
                                                335)
                                        .build())
                        .addAsic(
                                new Asic.Builder()
                                        .setHashRate(new BigDecimal("16003200000.0000"))
                                        .setFanInfo(
                                                new FanInfo.Builder()
                                                        .setCount(2)
                                                        .addSpeed(3270)
                                                        .addSpeed(3600)
                                                        .setSpeedUnits("RPM")
                                                        .build())
                                        .addTemp(58)
                                        .addTemp(51)
                                        .build())
                        .build());
    }
}