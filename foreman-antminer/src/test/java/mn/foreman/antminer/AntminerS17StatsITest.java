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
public class AntminerS17StatsITest
        extends AbstractApiITest {

    /** Constructor. */
    public AntminerS17StatsITest() {
        super(
                new AntminerFactory(BigDecimal.ONE)
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
                                        "{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1576765846,\"Code\":70,\"Msg\":\"CGMiner stats\",\"Description\":\"cgminer 1.0.0\"}],\"STATS\":[{\"BMMiner\":\"1.0.0\",\"Miner\":\"19.10.1.3\",\"CompileTime\":\"Tue Aug 20 10:37:07 CST 2019\",\"Type\":\"Antminer S17 Pro\"},{\"STATS\":0,\"ID\":\"BTM_SOC0\",\"Elapsed\":253447,\"Calls\":0,\"Wait\":0.000000,\"Max\":0.000000,\"Min\":99999999.000000,\"GHS 5s\":\"54997.01\",\"GHS av\":54512.07,\"GHS 30m\":54297.86,\"Mode\":1,\"miner_count\":3,\"frequency\":\"\",\"fan_num\":4,\"fan1\":2400,\"fan2\":3000,\"fan3\":3120,\"fan4\":2400,\"fan5\":0,\"fan6\":0,\"fan7\":0,\"fan8\":0,\"fan9\":0,\"fan10\":0,\"fan11\":0,\"fan12\":0,\"fan13\":0,\"fan14\":0,\"fan15\":0,\"fan16\":0,\"temp_num\":3,\"temp1\":50,\"temp2\":53,\"temp3\":45,\"temp4\":0,\"temp5\":0,\"temp6\":0,\"temp7\":0,\"temp8\":0,\"temp9\":0,\"temp10\":0,\"temp11\":0,\"temp12\":0,\"temp13\":0,\"temp14\":0,\"temp15\":0,\"temp16\":0,\"temp2_1\":80,\"temp2_2\":75,\"temp2_3\":63,\"temp2_4\":0,\"temp2_5\":0,\"temp2_6\":0,\"temp2_7\":0,\"temp2_8\":0,\"temp2_9\":0,\"temp2_10\":0,\"temp2_11\":0,\"temp2_12\":0,\"temp2_13\":0,\"temp2_14\":0,\"temp2_15\":0,\"temp2_16\":0,\"temp3_1\":80,\"temp3_2\":75,\"temp3_3\":63,\"temp3_4\":0,\"temp3_5\":0,\"temp3_6\":0,\"temp3_7\":0,\"temp3_8\":0,\"temp3_9\":0,\"temp3_10\":0,\"temp3_11\":0,\"temp3_12\":0,\"temp3_13\":0,\"temp3_14\":0,\"temp3_15\":0,\"temp3_16\":0,\"temp_pcb1\":\"22-53-21-50\",\"temp_pcb2\":\"22-53-19-46\",\"temp_pcb3\":\"15-45-12-37\",\"temp_pcb4\":\"0-0-0-0\",\"temp_pcb5\":\"0-0-0-0\",\"temp_pcb6\":\"0-0-0-0\",\"temp_pcb7\":\"0-0-0-0\",\"temp_pcb8\":\"0-0-0-0\",\"temp_pcb9\":\"0-0-0-0\",\"temp_pcb10\":\"0-0-0-0\",\"temp_pcb11\":\"0-0-0-0\",\"temp_pcb12\":\"0-0-0-0\",\"temp_pcb13\":\"0-0-0-0\",\"temp_pcb14\":\"0-0-0-0\",\"temp_pcb15\":\"0-0-0-0\",\"temp_pcb16\":\"0-0-0-0\",\"temp_chip1\":\"48-80-49-70\",\"temp_chip2\":\"46-75-43-66\",\"temp_chip3\":\"41-63-40-55\",\"temp_chip4\":\"0-0-0-0\",\"temp_chip5\":\"0-0-0-0\",\"temp_chip6\":\"0-0-0-0\",\"temp_chip7\":\"0-0-0-0\",\"temp_chip8\":\"0-0-0-0\",\"temp_chip9\":\"0-0-0-0\",\"temp_chip10\":\"0-0-0-0\",\"temp_chip11\":\"0-0-0-0\",\"temp_chip12\":\"0-0-0-0\",\"temp_chip13\":\"0-0-0-0\",\"temp_chip14\":\"0-0-0-0\",\"temp_chip15\":\"0-0-0-0\",\"temp_chip16\":\"0-0-0-0\",\"total_rateideal\":53619.00,\"rate_unit\":\"GH\",\"total_freqavg\":0.00,\"total_acn\":144,\"total_rate\":54997.00,\"chain_rateideal1\":0.00,\"chain_rateideal2\":0.00,\"chain_rateideal3\":0.00,\"chain_rateideal4\":0.00,\"chain_rateideal5\":0.00,\"chain_rateideal6\":0.00,\"chain_rateideal7\":0.00,\"chain_rateideal8\":0.00,\"chain_rateideal9\":0.00,\"chain_rateideal10\":0.00,\"chain_rateideal11\":0.00,\"chain_rateideal12\":0.00,\"chain_rateideal13\":0.00,\"chain_rateideal14\":0.00,\"chain_rateideal15\":0.00,\"chain_rateideal16\":0.00,\"temp_max\":53,\"no_matching_work\":2885,\"chain_acn1\":48,\"chain_acn2\":48,\"chain_acn3\":48,\"chain_acn4\":0,\"chain_acn5\":0,\"chain_acn6\":0,\"chain_acn7\":0,\"chain_acn8\":0,\"chain_acn9\":0,\"chain_acn10\":0,\"chain_acn11\":0,\"chain_acn12\":0,\"chain_acn13\":0,\"chain_acn14\":0,\"chain_acn15\":0,\"chain_acn16\":0,\"chain_acs1\":\" oooooooo oooooooo oooooooo oooooooo oooooooo oooooooo\",\"chain_acs2\":\" oooooooo oooooooo oooooooo oooooooo oooooooo oooooooo\",\"chain_acs3\":\" oooooooo oooooooo oooooooo oooooooo oooooooo oooooooo\",\"chain_acs4\":\"\",\"chain_acs5\":\"\",\"chain_acs6\":\"\",\"chain_acs7\":\"\",\"chain_acs8\":\"\",\"chain_acs9\":\"\",\"chain_acs10\":\"\",\"chain_acs11\":\"\",\"chain_acs12\":\"\",\"chain_acs13\":\"\",\"chain_acs14\":\"\",\"chain_acs15\":\"\",\"chain_acs16\":\"\",\"chain_hw1\":731,\"chain_hw2\":1074,\"chain_hw3\":1080,\"chain_hw4\":0,\"chain_hw5\":0,\"chain_hw6\":0,\"chain_hw7\":0,\"chain_hw8\":0,\"chain_hw9\":0,\"chain_hw10\":0,\"chain_hw11\":0,\"chain_hw12\":0,\"chain_hw13\":0,\"chain_hw14\":0,\"chain_hw15\":0,\"chain_hw16\":0,\"chain_rate1\":\"17968.26\",\"chain_rate2\":\"18418.00\",\"chain_rate3\":\"18610.75\",\"chain_rate4\":\"\",\"chain_rate5\":\"\",\"chain_rate6\":\"\",\"chain_rate7\":\"\",\"chain_rate8\":\"\",\"chain_rate9\":\"\",\"chain_rate10\":\"\",\"chain_rate11\":\"\",\"chain_rate12\":\"\",\"chain_rate13\":\"\",\"chain_rate14\":\"\",\"chain_rate15\":\"\",\"chain_rate16\":\"\",\"chain_xtime1\":\"{}\",\"chain_xtime2\":\"{}\",\"chain_xtime3\":\"{}\",\"chain_offside_1\":\"\",\"chain_offside_2\":\"\",\"chain_offside_3\":\"\",\"chain_opencore_1\":\"1\",\"chain_opencore_2\":\"1\",\"chain_opencore_3\":\"1\",\"freq1\":570,\"freq2\":560,\"freq3\":566,\"freq4\":0,\"freq5\":0,\"freq6\":0,\"freq7\":0,\"freq8\":0,\"freq9\":0,\"freq10\":0,\"freq11\":0,\"freq12\":0,\"freq13\":0,\"freq14\":0,\"freq15\":0,\"freq16\":0,\"miner_version\":\"19.10.1.3\",\"miner_id\":\"801085242b104814\"}],\"id\":1}"),
                                "{\"command\":\"pools\"}",
                                new RpcHandler(
                                        "{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1576765846,\"Code\":7,\"Msg\":\"3 Pool(s)\",\"Description\":\"cgminer 1.0.0\"}],\"POOLS\":[{\"POOL\":0,\"URL\":\"stratum+tcp://xxxxxx:3333\",\"Status\":\"Alive\",\"Priority\":0,\"Quota\":1,\"Long Poll\":\"N\",\"Getworks\":9487,\"Accepted\":86241,\"Rejected\":411,\"Discarded\":136597,\"Stale\":116,\"Get Failures\":5,\"Remote Failures\":4,\"User\":\"CosmosCapitalFund.COSMOSxCNKxS17Px00001\",\"Last Share Time\":\"0:00:03\",\"Diff\":\"39.5K\",\"Diff1 Shares\":0,\"Proxy Type\":\"\",\"Proxy\":\"\",\"Difficulty Accepted\":3210701589.00000000,\"Difficulty Rejected\":17465628.00000000,\"Difficulty Stale\":0.00000000,\"Last Share Difficulty\":39491.00000000,\"Has Stratum\":true,\"Stratum Active\":true,\"Stratum URL\":\"xxxxxx\",\"Has GBT\":false,\"Best Share\":1431836158,\"Pool Rejected%\":0.5410,\"Pool Stale%\":0.0000},{\"POOL\":1,\"URL\":\"stratum+tcp://xxxxxx:3333\",\"Status\":\"Alive\",\"Priority\":1,\"Quota\":1,\"Long Poll\":\"N\",\"Getworks\":8879,\"Accepted\":63,\"Rejected\":0,\"Discarded\":86,\"Stale\":2,\"Get Failures\":55,\"Remote Failures\":0,\"User\":\"CosmosCapitalFund.COSMOSxCNKxS17Px00001\",\"Last Share Time\":\"66:18:15\",\"Diff\":\"512\",\"Diff1 Shares\":0,\"Proxy Type\":\"\",\"Proxy\":\"\",\"Difficulty Accepted\":1901924.00000000,\"Difficulty Rejected\":0.00000000,\"Difficulty Stale\":0.00000000,\"Last Share Difficulty\":81558.00000000,\"Has Stratum\":true,\"Stratum Active\":true,\"Stratum URL\":\"stratum.slushpool.com\",\"Has GBT\":false,\"Best Share\":2237539,\"Pool Rejected%\":0.0000,\"Pool Stale%\":0.0000},{\"POOL\":2,\"URL\":\"stratum+tcp://stratum.slushpool.com:3333\",\"Status\":\"Alive\",\"Priority\":2,\"Quota\":1,\"Long Poll\":\"N\",\"Getworks\":8878,\"Accepted\":196,\"Rejected\":5,\"Discarded\":193,\"Stale\":4,\"Get Failures\":51,\"Remote Failures\":0,\"User\":\"CosmosCapitalFund.COSMOSxCNKxS17Px00001\",\"Last Share Time\":\"66:29:37\",\"Diff\":\"512\",\"Diff1 Shares\":0,\"Proxy Type\":\"\",\"Proxy\":\"\",\"Difficulty Accepted\":3690626.00000000,\"Difficulty Rejected\":40960.00000000,\"Difficulty Stale\":0.00000000,\"Last Share Difficulty\":28756.00000000,\"Has Stratum\":true,\"Stratum Active\":true,\"Stratum URL\":\"xxxxxx\",\"Has GBT\":false,\"Best Share\":1433415,\"Pool Rejected%\":1.0977,\"Pool Stale%\":0.0000}],\"id\":1}"))),
                new MinerStats.Builder()
                        .setApiIp("127.0.0.1")
                        .setApiPort(4028)
                        .addPool(
                                new Pool.Builder()
                                        .setName("xxxxxx:3333")
                                        .setStatus(true, true)
                                        .setPriority(0)
                                        .setCounts(86241, 411, 116)
                                        .build())
                        .addPool(
                                new Pool.Builder()
                                        .setName("xxxxxx:3333")
                                        .setStatus(true, true)
                                        .setPriority(1)
                                        .setCounts(63, 0, 2)
                                        .build())
                        .addPool(
                                new Pool.Builder()
                                        .setName("stratum.slushpool.com:3333")
                                        .setStatus(true, true)
                                        .setPriority(2)
                                        .setCounts(196, 5, 4)
                                        .build())
                        .addAsic(
                                new Asic.Builder()
                                        .setHashRate(new BigDecimal("54997010000000.00"))
                                        .setFanInfo(
                                                new FanInfo.Builder()
                                                        .setCount(4)
                                                        .addSpeed(2400)
                                                        .addSpeed(3000)
                                                        .addSpeed(3120)
                                                        .addSpeed(2400)
                                                        .setSpeedUnits("RPM")
                                                        .build())
                                        .addTemp(50)
                                        .addTemp(53)
                                        .addTemp(45)
                                        .addTemp(80)
                                        .addTemp(75)
                                        .addTemp(63)
                                        .addTemp(80)
                                        .addTemp(75)
                                        .addTemp(63)
                                        .hasErrors(false)
                                        .build())
                        .build());
    }
}
