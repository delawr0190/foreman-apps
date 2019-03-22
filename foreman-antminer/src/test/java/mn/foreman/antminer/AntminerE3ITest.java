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
public class AntminerE3ITest
        extends AbstractApiITest {

    /** Constructor. */
    public AntminerE3ITest() {
        super(
                new AntminerFactory(AntminerType.ANTMINER_E3)
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
                                        "{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1533178278,\"Code\":70,\"Msg\":\"CGMiner stats\",\"Description\":\"cgminer 4.9.0\"}],\"STATS\":[{\"CGMiner\":\"4.9.0\",\"Miner\":\"\",\"CompileTime\":\"Wed Jul 4 18:21:00 CST 2018\",\"Type\":\"Antminer E3\"},{\"STATS\":0,\"ID\":\"BTM0\",\"Elapsed\":556,\"Calls\":0,\"Wait\":0.000000,\"Max\":0.000000,\"Min\":99999999.000000,\"GHS 5s\":\"197.30\",\"GHS av\":199.56,\"miner_count\":3,\"frequency\":\"1600M\",\"fan_num\":2,\"fan1\":0,\"fan2\":0,\"fan3\":0,\"fan4\":0,\"fan5\":3600,\"fan6\":3720,\"temp_num\":18,\"temp1\":49,\"temp8\":45,\"temp9\":39,\"temp2\":50,\"temp10\":46,\"temp11\":39,\"temp3\":50,\"temp12\":45,\"temp13\":39,\"temp2_1\":63,\"temp2_8\":63,\"temp2_9\":56,\"temp2_2\":68,\"temp2_10\":59,\"temp2_11\":56,\"temp2_3\":68,\"temp2_12\":63,\"temp2_13\":58,\"temp3_1\":48,\"temp3_8\":44,\"temp3_9\":37,\"temp3_2\":49,\"temp3_10\":44,\"temp3_11\":38,\"temp3_3\":48,\"temp3_12\":44,\"temp3_13\":37,\"temp4_1\":59,\"temp4_8\":61,\"temp4_9\":53,\"temp4_2\":65,\"temp4_10\":57,\"temp4_11\":56,\"temp4_3\":64,\"temp4_12\":62,\"temp4_13\":53,\"temp_max1\":0,\"temp_max8\":0,\"temp_max9\":0,\"temp_max2\":0,\"temp_max10\":0,\"temp_max11\":0,\"temp_max3\":48,\"temp_max12\":44,\"temp_max13\":37,\"temp_max2_1\":0,\"temp_max2_8\":0,\"temp_max2_9\":0,\"temp_max2_2\":0,\"temp_max2_10\":0,\"temp_max2_11\":0,\"temp_max2_3\":64,\"temp_max2_12\":62,\"temp_max2_13\":53,\"chain_acs1\":\"oo\",\"chain_acs8\":\"oo\",\"chain_acs9\":\"oo\",\"chain_acs2\":\"oo\",\"chain_acs10\":\"oo\",\"chain_acs11\":\"oo\",\"chain_acs3\":\"oo\",\"chain_acs12\":\"oo\",\"chain_acs13\":\"oo\",\"chain_acn1\":2,\"chain_acn8\":2,\"chain_acn9\":2,\"chain_acn2\":2,\"chain_acn10\":2,\"chain_acn11\":2,\"chain_acn3\":2,\"chain_acn12\":2,\"chain_acn13\":2,\"chain_hw1\":0,\"chain_hw8\":0,\"chain_hw9\":0,\"chain_hw2\":0,\"chain_hw10\":0,\"chain_hw11\":0,\"chain_hw3\":0,\"chain_hw12\":0,\"chain_hw13\":0,\"chain_rate1\":\"20.97\",\"chain_rate8\":\"21.81\",\"chain_rate9\":\"22.98\",\"chain_rate2\":\"24.49\",\"chain_rate10\":\"18.79\",\"chain_rate11\":\"22.98\",\"chain_rate3\":\"21.14\",\"chain_rate12\":\"23.99\",\"chain_rate13\":\"20.13\"}],\"id\":1}"),
                                "{\"command\":\"pools\"}",
                                new RpcHandler(
                                        "{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1533178352,\"Code\":7,\"Msg\":\"3 Pool(s)\",\"Description\":\"cgminer 4.9.0\"}],\"POOLS\":[{\"POOL\":0,\"URL\":\"stratum+tcp://us1.ethpool.org:3333\",\"Status\":\"Alive\",\"Priority\":0,\"Quota\":1,\"Long Poll\":\"N\",\"Getworks\":85,\"Accepted\":38,\"Rejected\":0,\"Discarded\":0,\"Stale\":0,\"Get Failures\":0,\"Remote Failures\":0,\"User\":\"#0x65CdB0EE9Ba1D9eFe2566965F8B089c2B9ffCE63.00B4C08382A1\",\"Last Share Time\":\"0:00:20\",\"Diff\":\"31\",\"Diff1 Shares\":0,\"Proxy Type\":\"\",\"Proxy\":\"\",\"Difficulty Accepted\":1259.00000000,\"Difficulty Rejected\":0.00000000,\"Difficulty Stale\":0.00000000,\"Last Share Difficulty\":32.00000000,\"Has Stratum\":true,\"Stratum Active\":true,\"Stratum URL\":\"us1.ethpool.org\",\"Has GBT\":false,\"Best Share\":0,\"Pool Rejected%\":0.0000,\"Pool Stale%\":0.0000},{\"POOL\":1,\"URL\":\"\",\"Status\":\"Dead\",\"Priority\":1,\"Quota\":1,\"Long Poll\":\"N\",\"Getworks\":0,\"Accepted\":0,\"Rejected\":0,\"Discarded\":0,\"Stale\":0,\"Get Failures\":0,\"Remote Failures\":0,\"User\":\"\",\"Last Share Time\":\"0\",\"Diff\":\"\",\"Diff1 Shares\":0,\"Proxy Type\":\"\",\"Proxy\":\"\",\"Difficulty Accepted\":0.00000000,\"Difficulty Rejected\":0.00000000,\"Difficulty Stale\":0.00000000,\"Last Share Difficulty\":0.00000000,\"Has Stratum\":false,\"Stratum Active\":false,\"Stratum URL\":\"\",\"Has GBT\":false,\"Best Share\":0,\"Pool Rejected%\":0.0000,\"Pool Stale%\":0.0000},{\"POOL\":2,\"URL\":\"\",\"Status\":\"Dead\",\"Priority\":2,\"Quota\":1,\"Long Poll\":\"N\",\"Getworks\":0,\"Accepted\":0,\"Rejected\":0,\"Discarded\":0,\"Stale\":0,\"Get Failures\":0,\"Remote Failures\":0,\"User\":\"\",\"Last Share Time\":\"0\",\"Diff\":\"\",\"Diff1 Shares\":0,\"Proxy Type\":\"\",\"Proxy\":\"\",\"Difficulty Accepted\":0.00000000,\"Difficulty Rejected\":0.00000000,\"Difficulty Stale\":0.00000000,\"Last Share Difficulty\":0.00000000,\"Has Stratum\":false,\"Stratum Active\":false,\"Stratum URL\":\"\",\"Has GBT\":false,\"Best Share\":0,\"Pool Rejected%\":0.0000,\"Pool Stale%\":0.0000}],\"id\":1}"))),
                new MinerStats.Builder()
                        .setApiIp("127.0.0.1")
                        .setApiPort(4028)
                        .addPool(
                                new Pool.Builder()
                                        .setName("us1.ethpool.org:3333")
                                        .setStatus(true, true)
                                        .setPriority(0)
                                        .setCounts(38, 0, 0)
                                        .build())
                        .addAsic(
                                new Asic.Builder()
                                        .setHashRate(new BigDecimal("197300000.00000000410713130172268847672967240214347839355468750000000000"))
                                        .setFanInfo(
                                                new FanInfo.Builder()
                                                        .setCount(2)
                                                        .addSpeed(3600)
                                                        .addSpeed(3720)
                                                        .setSpeedUnits("RPM")
                                                        .build())
                                        .addTemp(49)
                                        .addTemp(50)
                                        .addTemp(50)
                                        .addTemp(45)
                                        .addTemp(39)
                                        .addTemp(46)
                                        .addTemp(39)
                                        .addTemp(45)
                                        .addTemp(39)
                                        .addTemp(63)
                                        .addTemp(68)
                                        .addTemp(68)
                                        .addTemp(63)
                                        .addTemp(56)
                                        .addTemp(59)
                                        .addTemp(56)
                                        .addTemp(63)
                                        .addTemp(58)
                                        .addTemp(48)
                                        .addTemp(49)
                                        .addTemp(48)
                                        .addTemp(44)
                                        .addTemp(37)
                                        .addTemp(44)
                                        .addTemp(38)
                                        .addTemp(44)
                                        .addTemp(37)
                                        .addTemp(59)
                                        .addTemp(65)
                                        .addTemp(64)
                                        .addTemp(61)
                                        .addTemp(53)
                                        .addTemp(57)
                                        .addTemp(56)
                                        .addTemp(62)
                                        .addTemp(53)
                                        .hasErrors(false)
                                        .build())
                        .build());
    }
}
