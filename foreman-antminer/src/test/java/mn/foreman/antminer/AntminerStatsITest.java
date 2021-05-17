package mn.foreman.antminer;

import mn.foreman.cgminer.CgMiner;
import mn.foreman.model.miners.FanInfo;
import mn.foreman.model.miners.MinerStats;
import mn.foreman.model.miners.Pool;
import mn.foreman.model.miners.asic.Asic;
import mn.foreman.util.AbstractApiITest;
import mn.foreman.util.rpc.FakeRpcMinerServer;
import mn.foreman.util.rpc.HandlerInterface;
import mn.foreman.util.rpc.RpcHandler;

import com.google.common.collect.ImmutableMap;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.math.BigDecimal;
import java.util.*;

/** Runs an integration tests using {@link CgMiner} against a fake API. */
@RunWith(Parameterized.class)
public class AntminerStatsITest
        extends AbstractApiITest {

    /**
     * Constructor.
     *
     * @param multiplier     The multiplier.
     * @param handlers       The handlers.
     * @param statsWhitelist The stats whitelist.
     * @param expectedStats  The expected stats.
     */
    public AntminerStatsITest(
            final BigDecimal multiplier,
            final Map<String, HandlerInterface> handlers,
            final List<String> statsWhitelist,
            final MinerStats expectedStats) {
        super(
                new AntminerFactory(multiplier).create(
                        ImmutableMap.of(
                                "apiIp",
                                "127.0.0.1",
                                "apiPort",
                                "4028",
                                "statsWhitelist",
                                statsWhitelist)),
                new FakeRpcMinerServer(
                        4028,
                        handlers),
                expectedStats);
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
                                // Antminer D3
                                new BigDecimal("0.001"),
                                ImmutableMap.of(
                                        "{\"command\":\"version\"}",
                                        new RpcHandler(
                                                "{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1526315502,\"Code\":22,\"Msg\":\"CGMiner versions\",\"Description\":\"cgminer 4.9.0\"}],\"VERSION\":[{\"CGMiner\":\"4.9.0\",\"API\":\"3.1\",\"Miner\":\"1.0.1.3\",\"CompileTime\":\"Fri Aug 25 17:28:57 CST 2017\",\"Type\":\"Antminer D3\"}],\"id\":1}"),
                                        "{\"command\":\"stats\"}",
                                        new RpcHandler(
                                                "{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1533152610,\"Code\":70,\"Msg\":\"CGMiner stats\",\"Description\":\"cgminer 4.10.0\"}],\"STATS\":[{\"CGMiner\":\"4.10.0\",\"Miner\":\"1.0.0.9\",\"CompileTime\":\"Fri Jan 05 16:48:56 CST 2018\",\"Type\":\"Antminer D3 Blissz v2.06 beta\"}{\"STATS\":0,\"ID\":\"D10\",\"Elapsed\":54422,\"Calls\":0,\"Wait\":0.000000,\"Max\":0.000000,\"Min\":99999999.000000,\"GHS 5s\":\"16346.64\",\"GHS av\":16868.38,\"miner_count\":3,\"frequency1\":\"450\",\"frequency2\":\"450\",\"frequency3\":\"450\",\"frequency4\":\"450\",\"fan_num\":2,\"fan1\":6030,\"fan2\":5790,\"temp_num\":3,\"temp1\":46,\"temp2\":46,\"temp3\":48,\"temp4\":0,\"temp2_1\":69,\"temp2_2\":67,\"temp2_3\":62,\"temp2_4\":0,\"temp_max\":48,\"Device Hardware%\":0.0001,\"no_matching_work\":4,\"chain_acn1\":60,\"chain_acn2\":60,\"chain_acn3\":60,\"chain_acn4\":0,\"chain_acs1\":\" oooooooo oooooooo oooooooo oooooooo oooooooo oooooooo oooooooo oooo\",\"chain_acs2\":\" oooooooo oooooooo oooooooo oooooooo oooooooo oooooooo oooooooo oooo\",\"chain_acs3\":\" oooooooo oooooooo oooooooo oooooooo oooooooo oooooooo oooooooo oooo\",\"chain_acs4\":\"\",\"chain_hw1\":0,\"chain_hw2\":0,\"chain_hw3\":0,\"chain_hw4\":0,\"chain_rate1\":\"5287.83\",\"chain_rate2\":\"5631.41\",\"chain_rate3\":\"5427.41\",\"chain_rate4\":\"\"}],\"id\":1}"),
                                        "{\"command\":\"pools\"}",
                                        new RpcHandler(
                                                "{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1533152695,\"Code\":7,\"Msg\":\"5 Pool(s)\",\"Description\":\"cgminer 4.10.0\"}],\"POOLS\":[{\"POOL\":0,\"URL\":\"stratum+tcp://dash-eu.coinmine.pl:6099\",\"Status\":\"Alive\",\"Priority\":0,\"Quota\":0,\"Long Poll\":\"N\",\"Getworks\":1167,\"Accepted\":3038,\"Rejected\":8,\"Discarded\":28157,\"Stale\":5,\"Get Failures\":1,\"Remote Failures\":0,\"User\":\"Dash.default\",\"Last Share Time\":\"0:00:00\",\"Diff\":\" 69.2461\",\"Diff1 Shares\":6475798,\"Proxy Type\":\"\",\"Proxy\":\"\",\"Difficulty Accepted\":198412.94104085,\"Difficulty Rejected\":604.97361199,\"Difficulty Stale\":0.00000000,\"Last Share Difficulty\":69.24612531,\"Has Stratum\":true,\"Stratum Active\":true,\"Stratum URL\":\"dash-eu.coinmine.pl\",\"Has GBT\":false,\"Best Share\":\"  0.0000\",\"Pool Rejected%\":0.3040,\"Pool Stale%\":0.0000},{\"POOL\":1,\"URL\":\"\",\"Status\":\"Dead\",\"Priority\":1,\"Quota\":0,\"Long Poll\":\"N\",\"Getworks\":0,\"Accepted\":0,\"Rejected\":0,\"Discarded\":0,\"Stale\":0,\"Get Failures\":0,\"Remote Failures\":0,\"User\":\"xxx\",\"Last Share Time\":\"0\",\"Diff\":\"  0.0000\",\"Diff1 Shares\":0,\"Proxy Type\":\"\",\"Proxy\":\"\",\"Difficulty Accepted\":0.00000000,\"Difficulty Rejected\":0.00000000,\"Difficulty Stale\":0.00000000,\"Last Share Difficulty\":0.00000000,\"Has Stratum\":false,\"Stratum Active\":false,\"Stratum URL\":\"\",\"Has GBT\":false,\"Best Share\":\"  0.0000\",\"Pool Rejected%\":0.0000,\"Pool Stale%\":0.0000},{\"POOL\":2,\"URL\":\"\",\"Status\":\"Dead\",\"Priority\":2,\"Quota\":0,\"Long Poll\":\"N\",\"Getworks\":0,\"Accepted\":0,\"Rejected\":0,\"Discarded\":0,\"Stale\":0,\"Get Failures\":0,\"Remote Failures\":0,\"User\":\"\",\"Last Share Time\":\"0\",\"Diff\":\"  0.0000\",\"Diff1 Shares\":0,\"Proxy Type\":\"\",\"Proxy\":\"\",\"Difficulty Accepted\":0.00000000,\"Difficulty Rejected\":0.00000000,\"Difficulty Stale\":0.00000000,\"Last Share Difficulty\":0.00000000,\"Has Stratum\":false,\"Stratum Active\":false,\"Stratum URL\":\"\",\"Has GBT\":false,\"Best Share\":\"  0.0000\",\"Pool Rejected%\":0.0000,\"Pool Stale%\":0.0000},\n{\"POOL\":3,\"URL\":\"stratum+tcp://dash.suprnova.cc:9991\",\"Status\":\"Alive\",\"Priority\":9998,\"Quota\":0,\"Long Poll\":\"N\",\"Getworks\":1,\"Accepted\":0,\"Rejected\":0,\"Discarded\":0,\"Stale\":0,\"Get Failures\":0,\"Remote Failures\":0,\"User\":\"devFeeMiner.9\",\"Last Share Time\":\"0\",\"Diff\":\" 64.0000\",\"Diff1 Shares\":0,\"Proxy Type\":\"\",\"Proxy\":\"\",\"Difficulty Accepted\":0.00000000,\"Difficulty Rejected\":0.00000000,\"Difficulty Stale\":0.00000000,\"Last Share Difficulty\":0.00000000,\"Has Stratum\":true,\"Stratum Active\":false,\"Stratum URL\":\"\",\"Has GBT\":false,\"Best Share\":\" 0.0000\",\"Pool Rejected%\":0.0000,\"Pool Stale%\":0.0000},{\"POOL\":4,\"URL\":\"stratum+tcp://dash-eu.coinmine.pl:6099\",\"Status\":\"Alive\",\"Priority\":9999,\"Quota\":0,\"Long Poll\":\"N\",\"Getworks\":1,\"Accepted\":0,\"Rejected\":0,\"Discarded\":0,\"Stale\":0,\"Get Failures\":0,\"Remote Failures\":0,\"User\":\"devFeeMiner.1\",\"Last Share Time\":\"0\",\"Diff\":\" 20.0000\",\"Diff1 Shares\":0,\"Proxy Type\":\"\",\"Proxy\":\"\",\"Difficulty Accepted\":0.00000000,\"Difficulty Rejected\":0.00000000,\"Difficulty Stale\":0.00000000,\"Last Share Difficulty\":0.00000000,\"Has Stratum\":true,\"Stratum Active\":false,\"Stratum URL\":\"\",\"Has GBT\":false,\"Best Share\":\" 0.0000\",\"Pool Rejected%\":0.0000,\"Pool Stale%\":0.0000}],\"id\":1}")),
                                Arrays.asList(
                                        "stats.0.temp1",
                                        "stats.0.temp2"),
                                new MinerStats.Builder()
                                        .setApiIp("127.0.0.1")
                                        .setApiPort(4028)
                                        .addPool(
                                                new Pool.Builder()
                                                        .setName("dash-eu.coinmine.pl:6099")
                                                        .setWorker("Dash.default")
                                                        .setStatus(true, true)
                                                        .setPriority(0)
                                                        .setCounts(3038, 8, 5)
                                                        .build())
                                        .addPool(
                                                new Pool.Builder()
                                                        .setName("dash.suprnova.cc:9991")
                                                        .setWorker("devFeeMiner.9")
                                                        .setStatus(true, true)
                                                        .setPriority(9998)
                                                        .setCounts(0, 0, 0)
                                                        .build())
                                        .addPool(
                                                new Pool.Builder()
                                                        .setName("dash-eu.coinmine.pl:6099")
                                                        .setWorker("devFeeMiner.1")
                                                        .setWorker("devFeeMiner.1")
                                                        .setStatus(true, true)
                                                        .setPriority(9999)
                                                        .setCounts(0, 0, 0)
                                                        .build())
                                        .addAsic(
                                                new Asic.Builder()
                                                        .setHashRate(new BigDecimal("16346640000.00000"))
                                                        .setBoards(3)
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
                                                        .addRawStats(
                                                                ImmutableMap.of(
                                                                        "STATS.0.temp1",
                                                                        new BigDecimal("46"),
                                                                        "STATS.0.temp2",
                                                                        new BigDecimal("46")))
                                                        .setMinerType("Antminer D3")
                                                        .setCompileTime("Fri Aug 25 17:28:57 CST 2017")
                                                        .build())
                                        .build()
                        },
                        {
                                // Antminer E3
                                new BigDecimal("0.001"),
                                ImmutableMap.of(
                                        "{\"command\":\"version\"}",
                                        new RpcHandler(
                                                "{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1526315502,\"Code\":22,\"Msg\":\"CGMiner versions\",\"Description\":\"cgminer 4.9.0\"}],\"VERSION\":[{\"CGMiner\":\"4.9.0\",\"API\":\"3.1\",\"Miner\":\"1.0.1.3\",\"CompileTime\":\"Fri Aug 25 17:28:57 CST 2017\",\"Type\":\"Antminer E3\"}],\"id\":1}"),
                                        "{\"command\":\"stats\"}",
                                        new RpcHandler(
                                                "{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1533178278,\"Code\":70,\"Msg\":\"CGMiner stats\",\"Description\":\"cgminer 4.9.0\"}],\"STATS\":[{\"CGMiner\":\"4.9.0\",\"Miner\":\"\",\"CompileTime\":\"Wed Jul 4 18:21:00 CST 2018\",\"Type\":\"Antminer E3\"},{\"STATS\":0,\"ID\":\"BTM0\",\"Elapsed\":556,\"Calls\":0,\"Wait\":0.000000,\"Max\":0.000000,\"Min\":99999999.000000,\"GHS 5s\":\"197.30\",\"GHS av\":199.56,\"miner_count\":3,\"frequency\":\"1600M\",\"fan_num\":2,\"fan1\":0,\"fan2\":0,\"fan3\":0,\"fan4\":0,\"fan5\":3600,\"fan6\":3720,\"temp_num\":18,\"temp1\":49,\"temp8\":45,\"temp9\":39,\"temp2\":50,\"temp10\":46,\"temp11\":39,\"temp3\":50,\"temp12\":45,\"temp13\":39,\"temp2_1\":63,\"temp2_8\":63,\"temp2_9\":56,\"temp2_2\":68,\"temp2_10\":59,\"temp2_11\":56,\"temp2_3\":68,\"temp2_12\":63,\"temp2_13\":58,\"temp3_1\":48,\"temp3_8\":44,\"temp3_9\":37,\"temp3_2\":49,\"temp3_10\":44,\"temp3_11\":38,\"temp3_3\":48,\"temp3_12\":44,\"temp3_13\":37,\"temp4_1\":59,\"temp4_8\":61,\"temp4_9\":53,\"temp4_2\":65,\"temp4_10\":57,\"temp4_11\":56,\"temp4_3\":64,\"temp4_12\":62,\"temp4_13\":53,\"temp_max1\":0,\"temp_max8\":0,\"temp_max9\":0,\"temp_max2\":0,\"temp_max10\":0,\"temp_max11\":0,\"temp_max3\":48,\"temp_max12\":44,\"temp_max13\":37,\"temp_max2_1\":0,\"temp_max2_8\":0,\"temp_max2_9\":0,\"temp_max2_2\":0,\"temp_max2_10\":0,\"temp_max2_11\":0,\"temp_max2_3\":64,\"temp_max2_12\":62,\"temp_max2_13\":53,\"chain_acs1\":\"oo\",\"chain_acs8\":\"oo\",\"chain_acs9\":\"oo\",\"chain_acs2\":\"oo\",\"chain_acs10\":\"oo\",\"chain_acs11\":\"oo\",\"chain_acs3\":\"oo\",\"chain_acs12\":\"oo\",\"chain_acs13\":\"oo\",\"chain_acn1\":2,\"chain_acn8\":2,\"chain_acn9\":2,\"chain_acn2\":2,\"chain_acn10\":2,\"chain_acn11\":2,\"chain_acn3\":2,\"chain_acn12\":2,\"chain_acn13\":2,\"chain_hw1\":0,\"chain_hw8\":0,\"chain_hw9\":0,\"chain_hw2\":0,\"chain_hw10\":0,\"chain_hw11\":0,\"chain_hw3\":0,\"chain_hw12\":0,\"chain_hw13\":0,\"chain_rate1\":\"20.97\",\"chain_rate8\":\"21.81\",\"chain_rate9\":\"22.98\",\"chain_rate2\":\"24.49\",\"chain_rate10\":\"18.79\",\"chain_rate11\":\"22.98\",\"chain_rate3\":\"21.14\",\"chain_rate12\":\"23.99\",\"chain_rate13\":\"20.13\"}],\"id\":1}"),
                                        "{\"command\":\"pools\"}",
                                        new RpcHandler(
                                                "{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1533178352,\"Code\":7,\"Msg\":\"3 Pool(s)\",\"Description\":\"cgminer 4.9.0\"}],\"POOLS\":[{\"POOL\":0,\"URL\":\"stratum+tcp://us1.ethpool.org:3333\",\"Status\":\"Alive\",\"Priority\":0,\"Quota\":1,\"Long Poll\":\"N\",\"Getworks\":85,\"Accepted\":38,\"Rejected\":0,\"Discarded\":0,\"Stale\":0,\"Get Failures\":0,\"Remote Failures\":0,\"User\":\"#0x65CdB0EE9Ba1D9eFe2566965F8B089c2B9ffCE63.00B4C08382A1\",\"Last Share Time\":\"0:00:20\",\"Diff\":\"31\",\"Diff1 Shares\":0,\"Proxy Type\":\"\",\"Proxy\":\"\",\"Difficulty Accepted\":1259.00000000,\"Difficulty Rejected\":0.00000000,\"Difficulty Stale\":0.00000000,\"Last Share Difficulty\":32.00000000,\"Has Stratum\":true,\"Stratum Active\":true,\"Stratum URL\":\"us1.ethpool.org\",\"Has GBT\":false,\"Best Share\":0,\"Pool Rejected%\":0.0000,\"Pool Stale%\":0.0000},{\"POOL\":1,\"URL\":\"\",\"Status\":\"Dead\",\"Priority\":1,\"Quota\":1,\"Long Poll\":\"N\",\"Getworks\":0,\"Accepted\":0,\"Rejected\":0,\"Discarded\":0,\"Stale\":0,\"Get Failures\":0,\"Remote Failures\":0,\"User\":\"\",\"Last Share Time\":\"0\",\"Diff\":\"\",\"Diff1 Shares\":0,\"Proxy Type\":\"\",\"Proxy\":\"\",\"Difficulty Accepted\":0.00000000,\"Difficulty Rejected\":0.00000000,\"Difficulty Stale\":0.00000000,\"Last Share Difficulty\":0.00000000,\"Has Stratum\":false,\"Stratum Active\":false,\"Stratum URL\":\"\",\"Has GBT\":false,\"Best Share\":0,\"Pool Rejected%\":0.0000,\"Pool Stale%\":0.0000},{\"POOL\":2,\"URL\":\"\",\"Status\":\"Dead\",\"Priority\":2,\"Quota\":1,\"Long Poll\":\"N\",\"Getworks\":0,\"Accepted\":0,\"Rejected\":0,\"Discarded\":0,\"Stale\":0,\"Get Failures\":0,\"Remote Failures\":0,\"User\":\"\",\"Last Share Time\":\"0\",\"Diff\":\"\",\"Diff1 Shares\":0,\"Proxy Type\":\"\",\"Proxy\":\"\",\"Difficulty Accepted\":0.00000000,\"Difficulty Rejected\":0.00000000,\"Difficulty Stale\":0.00000000,\"Last Share Difficulty\":0.00000000,\"Has Stratum\":false,\"Stratum Active\":false,\"Stratum URL\":\"\",\"Has GBT\":false,\"Best Share\":0,\"Pool Rejected%\":0.0000,\"Pool Stale%\":0.0000}],\"id\":1}")),
                                Arrays.asList(
                                        "STATS.1.temp1",
                                        "STATS.1.temp2"),
                                new MinerStats.Builder()
                                        .setApiIp("127.0.0.1")
                                        .setApiPort(4028)
                                        .addPool(
                                                new Pool.Builder()
                                                        .setName("us1.ethpool.org:3333")
                                                        .setWorker("#0x65CdB0EE9Ba1D9eFe2566965F8B089c2B9ffCE63.00B4C08382A1")
                                                        .setStatus(true, true)
                                                        .setPriority(0)
                                                        .setCounts(38, 0, 0)
                                                        .build())
                                        .addAsic(
                                                new Asic.Builder()
                                                        .setHashRate(new BigDecimal("197300000.00000"))
                                                        .setBoards(3)
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
                                                        .addRawStats(
                                                                ImmutableMap.of(
                                                                        "STATS.1.temp1",
                                                                        new BigDecimal("49"),
                                                                        "STATS.1.temp2",
                                                                        new BigDecimal("50")))
                                                        .setMinerType("Antminer E3")
                                                        .setCompileTime("Fri Aug 25 17:28:57 CST 2017")
                                                        .build())
                                        .build()
                        },
                        {
                                // Antminer L3
                                new BigDecimal("0.001"),
                                ImmutableMap.of(
                                        "{\"command\":\"version\"}",
                                        new RpcHandler(
                                                "{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1526315502,\"Code\":22,\"Msg\":\"CGMiner versions\",\"Description\":\"cgminer 4.9.0\"}],\"VERSION\":[{\"CGMiner\":\"4.9.0\",\"API\":\"3.1\",\"Miner\":\"1.0.1.3\",\"CompileTime\":\"Fri Aug 25 17:28:57 CST 2017\",\"Type\":\"Antminer L3\"}],\"id\":1}"),
                                        "{\"command\":\"stats\"}",
                                        new RpcHandler(
                                                "{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1526315384,\"Code\":70,\"Msg\":\"CGMiner stats\",\"Description\":\"cgminer 4.9.0\"}],\"STATS\":[{\"CGMiner\":\"4.9.0\",\"Miner\":\"1.0.1.3\",\"CompileTime\":\"Fri Aug 25 17:28:57 CST 2017\",\"Type\":\"Antminer L3+\"}{\"STATS\":0,\"ID\":\"L30\",\"Elapsed\":393983,\"Calls\":0,\"Wait\":0.000000,\"Max\":0.000000,\"Min\":99999999.000000,\"GHS 5s\":\"504.56\",\"GHS av\":500.28,\"miner_count\":4,\"frequency\":\"384\",\"fan_num\":2,\"fan1\":3840,\"fan2\":3810,\"temp_num\":4,\"temp1\":46,\"temp2\":46,\"temp3\":46,\"temp4\":45,\"temp2_1\":54,\"temp2_2\":56,\"temp2_3\":56,\"temp2_4\":53,\"temp31\":0,\"temp32\":0,\"temp33\":0,\"temp34\":0,\"temp4_1\":0,\"temp4_2\":0,\"temp4_3\":0,\"temp4_4\":0,\"temp_max\":46,\"Device Hardware%\":0.0000,\"no_matching_work\":152,\"chain_acn1\":72,\"chain_acn2\":72,\"chain_acn3\":72,\"chain_acn4\":72,\"chain_acs1\":\" oooooooo oooooooo oooooooo oooooooo oooooooo oooooooo oooooooo oooooooo oooooooo\",\"chain_acs2\":\" oooooooo oooooooo oooooooo oooooooo oooooooo oooooooo oooooooo oooooooo oooooooo\",\"chain_acs3\":\" oooooooo oooooooo oooooooo oooooooo oooooooo oooooooo oooooooo oooooooo oooooooo\",\"chain_acs4\":\" oooooooo oooooooo oooooooo oooooooo oooooooo oooooooo oooooooo oooooooo oooooooo\",\"chain_hw1\":0,\"chain_hw2\":0,\"chain_hw3\":153,\"chain_hw4\":0,\"chain_rate1\":\"126.12\",\"chain_rate2\":\"125.79\",\"chain_rate3\":\"126.06\",\"chain_rate4\":\"126.59\"}],\"id\":1}"),
                                        "{\"command\":\"pools\"}",
                                        new RpcHandler(
                                                "{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1526315222,\"Code\":7,\"Msg\":\"3 Pool(s)\",\"Description\":\"cgminer 4.9.0\"}],\"POOLS\":[{\"POOL\":0,\"URL\":\"stratum+tcp://us.litecoinpool.org:3333\",\"Status\":\"Alive\",\"Priority\":0,\"Quota\":1,\"Long Poll\":\"N\",\"Getworks\":24933,\"Accepted\":47384,\"Rejected\":212,\"Discarded\":230740,\"Stale\":15,\"Get Failures\":1,\"Remote Failures\":0,\"User\":\"obmllc.l3_1\",\"Last Share Time\":\"0:00:23\",\"Diff\":\"65.5K\",\"Diff1 Shares\":11805080,\"Proxy Type\":\"\",\"Proxy\":\"\",\"Difficulty Accepted\":2988769280.00000000,\"Difficulty Rejected\":13254656.00000000,\"Difficulty Stale\":0.00000000,\"Last Share Difficulty\":65536.00000000,\"Has Stratum\":true,\"Stratum Active\":true,\"Stratum URL\":\"us.litecoinpool.org\",\"Has GBT\":false,\"Best Share\":11224839560,\"Pool Rejected%\":0.4415,\"Pool Stale%\":0.0000},{\"POOL\":1,\"URL\":\"\",\"Status\":\"Dead\",\"Priority\":1,\"Quota\":1,\"Long Poll\":\"N\",\"Getworks\":0,\"Accepted\":0,\"Rejected\":0,\"Discarded\":0,\"Stale\":0,\"Get Failures\":0,\"Remote Failures\":0,\"User\":\"\",\"Last Share Time\":\"0\",\"Diff\":\"\",\"Diff1 Shares\":0,\"Proxy Type\":\"\",\"Proxy\":\"\",\"Difficulty Accepted\":0.00000000,\"Difficulty Rejected\":0.00000000,\"Difficulty Stale\":0.00000000,\"Last Share Difficulty\":0.00000000,\"Has Stratum\":false,\"Stratum Active\":false,\"Stratum URL\":\"\",\"Has GBT\":false,\"Best Share\":0,\"Pool Rejected%\":0.0000,\"Pool Stale%\":0.0000},{\"POOL\":2,\"URL\":\"\",\"Status\":\"Dead\",\"Priority\":2,\"Quota\":1,\"Long Poll\":\"N\",\"Getworks\":0,\"Accepted\":0,\"Rejected\":0,\"Discarded\":0,\"Stale\":0,\"Get Failures\":0,\"Remote Failures\":0,\"User\":\"\",\"Last Share Time\":\"0\",\"Diff\":\"\",\"Diff1 Shares\":0,\"Proxy Type\":\"\",\"Proxy\":\"\",\"Difficulty Accepted\":0.00000000,\"Difficulty Rejected\":0.00000000,\"Difficulty Stale\":0.00000000,\"Last Share Difficulty\":0.00000000,\"Has Stratum\":false,\"Stratum Active\":false,\"Stratum URL\":\"\",\"Has GBT\":false,\"Best Share\":0,\"Pool Rejected%\":0.0000,\"Pool Stale%\":0.0000}],\"id\":1}")),
                                Arrays.asList(
                                        "STATS.0.temp1",
                                        "STATS.0.temp2"),
                                new MinerStats.Builder()
                                        .setApiIp("127.0.0.1")
                                        .setApiPort(4028)
                                        .addPool(
                                                new Pool.Builder()
                                                        .setName("us.litecoinpool.org:3333")
                                                        .setWorker("obmllc.l3_1")
                                                        .setStatus(true, true)
                                                        .setPriority(0)
                                                        .setCounts(47384, 212, 15)
                                                        .build())
                                        .addAsic(
                                                new Asic.Builder()
                                                        .setHashRate(new BigDecimal("504560000.00000"))
                                                        .setBoards(4)
                                                        .setFanInfo(
                                                                new FanInfo.Builder()
                                                                        .setCount(2)
                                                                        .addSpeed(3840)
                                                                        .addSpeed(3810)
                                                                        .setSpeedUnits("RPM")
                                                                        .build())
                                                        .addTemp(46)
                                                        .addTemp(46)
                                                        .addTemp(46)
                                                        .addTemp(45)
                                                        .addTemp(54)
                                                        .addTemp(56)
                                                        .addTemp(56)
                                                        .addTemp(53)
                                                        .hasErrors(false)
                                                        .addRawStats(
                                                                ImmutableMap.of(
                                                                        "STATS.0.temp1",
                                                                        new BigDecimal("46"),
                                                                        "STATS.0.temp2",
                                                                        new BigDecimal("46")))
                                                        .setMinerType("Antminer L3")
                                                        .setCompileTime("Fri Aug 25 17:28:57 CST 2017")
                                                        .build())
                                        .build()
                        },
                        {
                                // Antminer L3
                                new BigDecimal("0.001"),
                                ImmutableMap.of(
                                        "{\"command\":\"version\"}",
                                        new RpcHandler(
                                                "{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1526315502,\"Code\":22,\"Msg\":\"CGMiner versions\",\"Description\":\"cgminer 4.9.0\"}],\"VERSION\":[{\"CGMiner\":\"4.9.0\",\"API\":\"3.1\",\"Miner\":\"1.0.1.3\",\"CompileTime\":\"Fri Aug 25 17:28:57 CST 2017\",\"Type\":\"Antminer L3\"}],\"id\":1}"),
                                        "{\"command\":\"stats\"}",
                                        new RpcHandler(
                                                "{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1526315384,\"Code\":70,\"Msg\":\"CGMiner stats\",\"Description\":\"cgminer 4.9.0\"}],\"STATS\":[{\"CGMiner\":\"4.9.0\",\"Miner\":\"1.0.1.3\",\"CompileTime\":\"Fri Aug 25 17:28:57 CST 2017\",\"Type\":\"Antminer L3+\"}{\"STATS\":0,\"ID\":\"L30\",\"Elapsed\":393983,\"Calls\":0,\"Wait\":0.000000,\"Max\":0.000000,\"Min\":99999999.000000,\"GHS 5s\":\"0.00\",\"GHS av\":500.28,\"miner_count\":4,\"frequency\":\"384\",\"fan_num\":2,\"fan1\":3840,\"fan2\":3810,\"temp_num\":4,\"temp1\":46,\"temp2\":46,\"temp3\":46,\"temp4\":45,\"temp2_1\":54,\"temp2_2\":56,\"temp2_3\":56,\"temp2_4\":53,\"temp31\":0,\"temp32\":0,\"temp33\":0,\"temp34\":0,\"temp4_1\":0,\"temp4_2\":0,\"temp4_3\":0,\"temp4_4\":0,\"temp_max\":46,\"Device Hardware%\":0.0000,\"no_matching_work\":152,\"chain_acn1\":72,\"chain_acn2\":72,\"chain_acn3\":72,\"chain_acn4\":72,\"chain_acs1\":\" oooooooo oooooooo oooooooo oooooooo oooooooo oooooooo oooooooo oooooooo oooooooo\",\"chain_acs2\":\" oooooooo oooooooo oooooooo oooooooo oooooooo oooooooo oooooooo oooooooo oooooooo\",\"chain_acs3\":\" oooooooo oooooooo oooooooo oooooooo oooooooo oooooooo oooooooo oooooooo oooooooo\",\"chain_acs4\":\" oooooooo oooooooo oooooooo oooooooo oooooooo oooooooo oooooooo oooooooo oooooooo\",\"chain_hw1\":0,\"chain_hw2\":0,\"chain_hw3\":153,\"chain_hw4\":0,\"chain_rate1\":\"126.12\",\"chain_rate2\":\"125.79\",\"chain_rate3\":\"126.06\",\"chain_rate4\":\"126.59\"}],\"id\":1}"),
                                        "{\"command\":\"pools\"}",
                                        new RpcHandler(
                                                "{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1526315222,\"Code\":7,\"Msg\":\"3 Pool(s)\",\"Description\":\"cgminer 4.9.0\"}],\"POOLS\":[{\"POOL\":0,\"URL\":\"stratum+tcp://us.litecoinpool.org:3333\",\"Status\":\"Alive\",\"Priority\":0,\"Quota\":1,\"Long Poll\":\"N\",\"Getworks\":24933,\"Accepted\":47384,\"Rejected\":212,\"Discarded\":230740,\"Stale\":15,\"Get Failures\":1,\"Remote Failures\":0,\"User\":\"obmllc.l3_1\",\"Last Share Time\":\"0:00:23\",\"Diff\":\"65.5K\",\"Diff1 Shares\":11805080,\"Proxy Type\":\"\",\"Proxy\":\"\",\"Difficulty Accepted\":2988769280.00000000,\"Difficulty Rejected\":13254656.00000000,\"Difficulty Stale\":0.00000000,\"Last Share Difficulty\":65536.00000000,\"Has Stratum\":true,\"Stratum Active\":true,\"Stratum URL\":\"us.litecoinpool.org\",\"Has GBT\":false,\"Best Share\":11224839560,\"Pool Rejected%\":0.4415,\"Pool Stale%\":0.0000},{\"POOL\":1,\"URL\":\"\",\"Status\":\"Dead\",\"Priority\":1,\"Quota\":1,\"Long Poll\":\"N\",\"Getworks\":0,\"Accepted\":0,\"Rejected\":0,\"Discarded\":0,\"Stale\":0,\"Get Failures\":0,\"Remote Failures\":0,\"User\":\"\",\"Last Share Time\":\"0\",\"Diff\":\"\",\"Diff1 Shares\":0,\"Proxy Type\":\"\",\"Proxy\":\"\",\"Difficulty Accepted\":0.00000000,\"Difficulty Rejected\":0.00000000,\"Difficulty Stale\":0.00000000,\"Last Share Difficulty\":0.00000000,\"Has Stratum\":false,\"Stratum Active\":false,\"Stratum URL\":\"\",\"Has GBT\":false,\"Best Share\":0,\"Pool Rejected%\":0.0000,\"Pool Stale%\":0.0000},{\"POOL\":2,\"URL\":\"\",\"Status\":\"Dead\",\"Priority\":2,\"Quota\":1,\"Long Poll\":\"N\",\"Getworks\":0,\"Accepted\":0,\"Rejected\":0,\"Discarded\":0,\"Stale\":0,\"Get Failures\":0,\"Remote Failures\":0,\"User\":\"\",\"Last Share Time\":\"0\",\"Diff\":\"\",\"Diff1 Shares\":0,\"Proxy Type\":\"\",\"Proxy\":\"\",\"Difficulty Accepted\":0.00000000,\"Difficulty Rejected\":0.00000000,\"Difficulty Stale\":0.00000000,\"Last Share Difficulty\":0.00000000,\"Has Stratum\":false,\"Stratum Active\":false,\"Stratum URL\":\"\",\"Has GBT\":false,\"Best Share\":0,\"Pool Rejected%\":0.0000,\"Pool Stale%\":0.0000}],\"id\":1}")),
                                Arrays.asList(
                                        "STATS.0.temp1",
                                        "STATS.0.temp2"),
                                new MinerStats.Builder()
                                        .setApiIp("127.0.0.1")
                                        .setApiPort(4028)
                                        .addPool(
                                                new Pool.Builder()
                                                        .setName("us.litecoinpool.org:3333")
                                                        .setWorker("obmllc.l3_1")
                                                        .setStatus(true, true)
                                                        .setPriority(0)
                                                        .setCounts(47384, 212, 15)
                                                        .build())
                                        .addAsic(
                                                new Asic.Builder()
                                                        .setHashRate(new BigDecimal("0.00"))
                                                        .setBoards(4)
                                                        .setFanInfo(
                                                                new FanInfo.Builder()
                                                                        .setCount(2)
                                                                        .addSpeed(3840)
                                                                        .addSpeed(3810)
                                                                        .setSpeedUnits("RPM")
                                                                        .build())
                                                        .addTemp(46)
                                                        .addTemp(46)
                                                        .addTemp(46)
                                                        .addTemp(45)
                                                        .addTemp(54)
                                                        .addTemp(56)
                                                        .addTemp(56)
                                                        .addTemp(53)
                                                        .hasErrors(false)
                                                        .addRawStats(
                                                                ImmutableMap.of(
                                                                        "STATS.0.temp1",
                                                                        new BigDecimal("46"),
                                                                        "STATS.0.temp2",
                                                                        new BigDecimal("46")))
                                                        .setMinerType("Antminer L3")
                                                        .setCompileTime("Fri Aug 25 17:28:57 CST 2017")
                                                        .build())
                                        .build()
                        },
                        {
                                // Antminer S17
                                BigDecimal.ONE,
                                ImmutableMap.of(
                                        "{\"command\":\"version\"}",
                                        new RpcHandler(
                                                "{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1526315502,\"Code\":22,\"Msg\":\"CGMiner versions\",\"Description\":\"cgminer 4.9.0\"}],\"VERSION\":[{\"CGMiner\":\"4.9.0\",\"API\":\"3.1\",\"Miner\":\"1.0.1.3\",\"CompileTime\":\"Fri Aug 25 17:28:57 CST 2017\",\"Type\":\"Antminer S17\"}],\"id\":1}"),
                                        "{\"command\":\"stats\"}",
                                        new RpcHandler(
                                                "{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1576765846,\"Code\":70,\"Msg\":\"CGMiner stats\",\"Description\":\"cgminer 1.0.0\"}],\"STATS\":[{\"BMMiner\":\"1.0.0\",\"Miner\":\"19.10.1.3\",\"CompileTime\":\"Tue Aug 20 10:37:07 CST 2019\",\"Type\":\"Antminer S17 Pro\"},{\"STATS\":0,\"ID\":\"BTM_SOC0\",\"Elapsed\":253447,\"Calls\":0,\"Wait\":0.000000,\"Max\":0.000000,\"Min\":99999999.000000,\"GHS 5s\":\"54997.01\",\"GHS av\":54512.07,\"GHS 30m\":54297.86,\"Mode\":1,\"miner_count\":3,\"frequency\":\"\",\"fan_num\":4,\"fan1\":2400,\"fan2\":3000,\"fan3\":3120,\"fan4\":2400,\"fan5\":0,\"fan6\":0,\"fan7\":0,\"fan8\":0,\"fan9\":0,\"fan10\":0,\"fan11\":0,\"fan12\":0,\"fan13\":0,\"fan14\":0,\"fan15\":0,\"fan16\":0,\"temp_num\":3,\"temp1\":50,\"temp2\":53,\"temp3\":45,\"temp4\":0,\"temp5\":0,\"temp6\":0,\"temp7\":0,\"temp8\":0,\"temp9\":0,\"temp10\":0,\"temp11\":0,\"temp12\":0,\"temp13\":0,\"temp14\":0,\"temp15\":0,\"temp16\":0,\"temp2_1\":80,\"temp2_2\":75,\"temp2_3\":63,\"temp2_4\":0,\"temp2_5\":0,\"temp2_6\":0,\"temp2_7\":0,\"temp2_8\":0,\"temp2_9\":0,\"temp2_10\":0,\"temp2_11\":0,\"temp2_12\":0,\"temp2_13\":0,\"temp2_14\":0,\"temp2_15\":0,\"temp2_16\":0,\"temp3_1\":80,\"temp3_2\":75,\"temp3_3\":63,\"temp3_4\":0,\"temp3_5\":0,\"temp3_6\":0,\"temp3_7\":0,\"temp3_8\":0,\"temp3_9\":0,\"temp3_10\":0,\"temp3_11\":0,\"temp3_12\":0,\"temp3_13\":0,\"temp3_14\":0,\"temp3_15\":0,\"temp3_16\":0,\"temp_pcb1\":\"22-53-21-50\",\"temp_pcb2\":\"22-53-19-46\",\"temp_pcb3\":\"15-45-12-37\",\"temp_pcb4\":\"0-0-0-0\",\"temp_pcb5\":\"0-0-0-0\",\"temp_pcb6\":\"0-0-0-0\",\"temp_pcb7\":\"0-0-0-0\",\"temp_pcb8\":\"0-0-0-0\",\"temp_pcb9\":\"0-0-0-0\",\"temp_pcb10\":\"0-0-0-0\",\"temp_pcb11\":\"0-0-0-0\",\"temp_pcb12\":\"0-0-0-0\",\"temp_pcb13\":\"0-0-0-0\",\"temp_pcb14\":\"0-0-0-0\",\"temp_pcb15\":\"0-0-0-0\",\"temp_pcb16\":\"0-0-0-0\",\"temp_chip1\":\"48-80-49-70\",\"temp_chip2\":\"46-75-43-66\",\"temp_chip3\":\"41-63-40-55\",\"temp_chip4\":\"0-0-0-0\",\"temp_chip5\":\"0-0-0-0\",\"temp_chip6\":\"0-0-0-0\",\"temp_chip7\":\"0-0-0-0\",\"temp_chip8\":\"0-0-0-0\",\"temp_chip9\":\"0-0-0-0\",\"temp_chip10\":\"0-0-0-0\",\"temp_chip11\":\"0-0-0-0\",\"temp_chip12\":\"0-0-0-0\",\"temp_chip13\":\"0-0-0-0\",\"temp_chip14\":\"0-0-0-0\",\"temp_chip15\":\"0-0-0-0\",\"temp_chip16\":\"0-0-0-0\",\"total_rateideal\":53619.00,\"rate_unit\":\"GH\",\"total_freqavg\":0.00,\"total_acn\":144,\"total_rate\":54997.00,\"chain_rateideal1\":0.00,\"chain_rateideal2\":0.00,\"chain_rateideal3\":0.00,\"chain_rateideal4\":0.00,\"chain_rateideal5\":0.00,\"chain_rateideal6\":0.00,\"chain_rateideal7\":0.00,\"chain_rateideal8\":0.00,\"chain_rateideal9\":0.00,\"chain_rateideal10\":0.00,\"chain_rateideal11\":0.00,\"chain_rateideal12\":0.00,\"chain_rateideal13\":0.00,\"chain_rateideal14\":0.00,\"chain_rateideal15\":0.00,\"chain_rateideal16\":0.00,\"temp_max\":53,\"no_matching_work\":2885,\"chain_acn1\":48,\"chain_acn2\":48,\"chain_acn3\":48,\"chain_acn4\":0,\"chain_acn5\":0,\"chain_acn6\":0,\"chain_acn7\":0,\"chain_acn8\":0,\"chain_acn9\":0,\"chain_acn10\":0,\"chain_acn11\":0,\"chain_acn12\":0,\"chain_acn13\":0,\"chain_acn14\":0,\"chain_acn15\":0,\"chain_acn16\":0,\"chain_acs1\":\" oooooooo oooooooo oooooooo oooooooo oooooooo oooooooo\",\"chain_acs2\":\" oooooooo oooooooo oooooooo oooooooo oooooooo oooooooo\",\"chain_acs3\":\" oooooooo oooooooo oooooooo oooooooo oooooooo oooooooo\",\"chain_acs4\":\"\",\"chain_acs5\":\"\",\"chain_acs6\":\"\",\"chain_acs7\":\"\",\"chain_acs8\":\"\",\"chain_acs9\":\"\",\"chain_acs10\":\"\",\"chain_acs11\":\"\",\"chain_acs12\":\"\",\"chain_acs13\":\"\",\"chain_acs14\":\"\",\"chain_acs15\":\"\",\"chain_acs16\":\"\",\"chain_hw1\":731,\"chain_hw2\":1074,\"chain_hw3\":1080,\"chain_hw4\":0,\"chain_hw5\":0,\"chain_hw6\":0,\"chain_hw7\":0,\"chain_hw8\":0,\"chain_hw9\":0,\"chain_hw10\":0,\"chain_hw11\":0,\"chain_hw12\":0,\"chain_hw13\":0,\"chain_hw14\":0,\"chain_hw15\":0,\"chain_hw16\":0,\"chain_rate1\":\"17968.26\",\"chain_rate2\":\"18418.00\",\"chain_rate3\":\"18610.75\",\"chain_rate4\":\"\",\"chain_rate5\":\"\",\"chain_rate6\":\"\",\"chain_rate7\":\"\",\"chain_rate8\":\"\",\"chain_rate9\":\"\",\"chain_rate10\":\"\",\"chain_rate11\":\"\",\"chain_rate12\":\"\",\"chain_rate13\":\"\",\"chain_rate14\":\"\",\"chain_rate15\":\"\",\"chain_rate16\":\"\",\"chain_xtime1\":\"{}\",\"chain_xtime2\":\"{}\",\"chain_xtime3\":\"{}\",\"chain_offside_1\":\"\",\"chain_offside_2\":\"\",\"chain_offside_3\":\"\",\"chain_opencore_1\":\"1\",\"chain_opencore_2\":\"1\",\"chain_opencore_3\":\"1\",\"freq1\":570,\"freq2\":560,\"freq3\":566,\"freq4\":0,\"freq5\":0,\"freq6\":0,\"freq7\":0,\"freq8\":0,\"freq9\":0,\"freq10\":0,\"freq11\":0,\"freq12\":0,\"freq13\":0,\"freq14\":0,\"freq15\":0,\"freq16\":0,\"miner_version\":\"19.10.1.3\",\"miner_id\":\"801085242b104814\"}],\"id\":1}"),
                                        "{\"command\":\"pools\"}",
                                        new RpcHandler(
                                                "{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1576765846,\"Code\":7,\"Msg\":\"3 Pool(s)\",\"Description\":\"cgminer 1.0.0\"}],\"POOLS\":[{\"POOL\":0,\"URL\":\"stratum+tcp://xxxxxx:3333\",\"Status\":\"Alive\",\"Priority\":0,\"Quota\":1,\"Long Poll\":\"N\",\"Getworks\":9487,\"Accepted\":86241,\"Rejected\":411,\"Discarded\":136597,\"Stale\":116,\"Get Failures\":5,\"Remote Failures\":4,\"User\":\"CosmosCapitalFund.COSMOSxCNKxS17Px00001\",\"Last Share Time\":\"0:00:03\",\"Diff\":\"39.5K\",\"Diff1 Shares\":0,\"Proxy Type\":\"\",\"Proxy\":\"\",\"Difficulty Accepted\":3210701589.00000000,\"Difficulty Rejected\":17465628.00000000,\"Difficulty Stale\":0.00000000,\"Last Share Difficulty\":39491.00000000,\"Has Stratum\":true,\"Stratum Active\":true,\"Stratum URL\":\"xxxxxx\",\"Has GBT\":false,\"Best Share\":1431836158,\"Pool Rejected%\":0.5410,\"Pool Stale%\":0.0000},{\"POOL\":1,\"URL\":\"stratum+tcp://xxxxxx:3333\",\"Status\":\"Alive\",\"Priority\":1,\"Quota\":1,\"Long Poll\":\"N\",\"Getworks\":8879,\"Accepted\":63,\"Rejected\":0,\"Discarded\":86,\"Stale\":2,\"Get Failures\":55,\"Remote Failures\":0,\"User\":\"CosmosCapitalFund.COSMOSxCNKxS17Px00001\",\"Last Share Time\":\"66:18:15\",\"Diff\":\"512\",\"Diff1 Shares\":0,\"Proxy Type\":\"\",\"Proxy\":\"\",\"Difficulty Accepted\":1901924.00000000,\"Difficulty Rejected\":0.00000000,\"Difficulty Stale\":0.00000000,\"Last Share Difficulty\":81558.00000000,\"Has Stratum\":true,\"Stratum Active\":true,\"Stratum URL\":\"stratum.slushpool.com\",\"Has GBT\":false,\"Best Share\":2237539,\"Pool Rejected%\":0.0000,\"Pool Stale%\":0.0000},{\"POOL\":2,\"URL\":\"stratum+tcp://stratum.slushpool.com:3333\",\"Status\":\"Alive\",\"Priority\":2,\"Quota\":1,\"Long Poll\":\"N\",\"Getworks\":8878,\"Accepted\":196,\"Rejected\":5,\"Discarded\":193,\"Stale\":4,\"Get Failures\":51,\"Remote Failures\":0,\"User\":\"CosmosCapitalFund.COSMOSxCNKxS17Px00001\",\"Last Share Time\":\"66:29:37\",\"Diff\":\"512\",\"Diff1 Shares\":0,\"Proxy Type\":\"\",\"Proxy\":\"\",\"Difficulty Accepted\":3690626.00000000,\"Difficulty Rejected\":40960.00000000,\"Difficulty Stale\":0.00000000,\"Last Share Difficulty\":28756.00000000,\"Has Stratum\":true,\"Stratum Active\":true,\"Stratum URL\":\"xxxxxx\",\"Has GBT\":false,\"Best Share\":1433415,\"Pool Rejected%\":1.0977,\"Pool Stale%\":0.0000}],\"id\":1}")),
                                Arrays.asList(
                                        "STATS.1.temp1",
                                        "STATS.1.temp2"),
                                new MinerStats.Builder()
                                        .setApiIp("127.0.0.1")
                                        .setApiPort(4028)
                                        .addPool(
                                                new Pool.Builder()
                                                        .setName("xxxxxx:3333")
                                                        .setWorker("CosmosCapitalFund.COSMOSxCNKxS17Px00001")
                                                        .setStatus(true, true)
                                                        .setPriority(0)
                                                        .setCounts(86241, 411, 116)
                                                        .build())
                                        .addPool(
                                                new Pool.Builder()
                                                        .setName("xxxxxx:3333")
                                                        .setWorker("CosmosCapitalFund.COSMOSxCNKxS17Px00001")
                                                        .setStatus(true, true)
                                                        .setPriority(1)
                                                        .setCounts(63, 0, 2)
                                                        .build())
                                        .addPool(
                                                new Pool.Builder()
                                                        .setName("stratum.slushpool.com:3333")
                                                        .setWorker("CosmosCapitalFund.COSMOSxCNKxS17Px00001")
                                                        .setStatus(true, true)
                                                        .setPriority(2)
                                                        .setCounts(196, 5, 4)
                                                        .build())
                                        .addAsic(
                                                new Asic.Builder()
                                                        .setHashRate(new BigDecimal("54997010000000.00"))
                                                        .setBoards(3)
                                                        .setFanInfo(
                                                                new FanInfo.Builder()
                                                                        .setCount(4)
                                                                        .addSpeed(2400)
                                                                        .addSpeed(3000)
                                                                        .addSpeed(3120)
                                                                        .addSpeed(2400)
                                                                        .setSpeedUnits("RPM")
                                                                        .build())
                                                        .addTemp(22)
                                                        .addTemp(53)
                                                        .addTemp(21)
                                                        .addTemp(50)
                                                        .addTemp(22)
                                                        .addTemp(53)
                                                        .addTemp(19)
                                                        .addTemp(46)
                                                        .addTemp(15)
                                                        .addTemp(45)
                                                        .addTemp(12)
                                                        .addTemp(37)
                                                        .addTemp(48)
                                                        .addTemp(80)
                                                        .addTemp(49)
                                                        .addTemp(70)
                                                        .addTemp(46)
                                                        .addTemp(75)
                                                        .addTemp(43)
                                                        .addTemp(66)
                                                        .addTemp(41)
                                                        .addTemp(63)
                                                        .addTemp(40)
                                                        .addTemp(55)
                                                        .hasErrors(false)
                                                        .addRawStats(
                                                                ImmutableMap.of(
                                                                        "STATS.1.temp1",
                                                                        new BigDecimal("50"),
                                                                        "STATS.1.temp2",
                                                                        new BigDecimal("53")))
                                                        .setMinerType("Antminer S17")
                                                        .setCompileTime("Fri Aug 25 17:28:57 CST 2017")
                                                        .build())
                                        .build()
                        },
                        {
                                // Antminer S9 (bOS+)
                                BigDecimal.ONE,
                                ImmutableMap.builder()
                                        .put(
                                                "{\"command\":\"version\"}",
                                                new RpcHandler(
                                                        "{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1597187881,\"Code\":22,\"Msg\":\"BOSminer+ versions\",\"Description\":\"BOSminer+ 0.2.0-36c56a9363\"}],\"VERSION\":[{\"API\":\"3.7\",\"BOSminer+\":\"0.2.0-36c56a9363\"}],\"id\":1}"))
                                        .put(
                                                "{\"command\":\"pools\"}",
                                                new RpcHandler(
                                                        "{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1597187909,\"Code\":7,\"Msg\":\"3 Pool(s)\",\"Description\":\"BOSminer+ 0.2.0-36c56a9363\"}],\"POOLS\":[{\"Accepted\":152,\"AsicBoost\":true,\"Bad Work\":0,\"Best Share\":65536,\"Current Block Height\":0,\"Current Block Version\":536870912,\"Diff1 Shares\":10984863,\"Difficulty Accepted\":9961472.0,\"Difficulty Rejected\":0.0,\"Difficulty Stale\":0.0,\"Discarded\":0,\"Get Failures\":0,\"Getworks\":106,\"Has GBT\":false,\"Has Stratum\":true,\"Has Vmask\":true,\"Last Share Difficulty\":65536.0,\"Last Share Time\":1597187897,\"Long Poll\":\"N\",\"POOL\":0,\"Pool Rejected%\":0.0,\"Pool Stale%\":0.0,\"Priority\":0,\"Proxy\":\"\",\"Proxy Type\":\"\",\"Quota\":1,\"Rejected\":0,\"Remote Failures\":0,\"Stale\":0,\"Status\":\"Alive\",\"Stratum Active\":true,\"Stratum Difficulty\":65536.0,\"Stratum URL\":\"stratum.antpool.com:3333\",\"URL\":\"stratum+tcp://stratum.antpool.com:3333\",\"User\":\"antminer_1\",\"Work Difficulty\":65536.0,\"Works\":16655828},{\"Accepted\":0,\"AsicBoost\":true,\"Bad Work\":0,\"Best Share\":0,\"Current Block Height\":0,\"Current Block Version\":536870912,\"Diff1 Shares\":0,\"Difficulty Accepted\":0.0,\"Difficulty Rejected\":0.0,\"Difficulty Stale\":0.0,\"Discarded\":0,\"Get Failures\":0,\"Getworks\":3,\"Has GBT\":false,\"Has Stratum\":true,\"Has Vmask\":true,\"Last Share Difficulty\":0.0,\"Last Share Time\":0,\"Long Poll\":\"N\",\"POOL\":1,\"Pool Rejected%\":0.0,\"Pool Stale%\":0.0,\"Priority\":1,\"Proxy\":\"\",\"Proxy Type\":\"\",\"Quota\":1,\"Rejected\":0,\"Remote Failures\":0,\"Stale\":0,\"Status\":\"Alive\",\"Stratum Active\":false,\"Stratum Difficulty\":65536.0,\"Stratum URL\":\"stratum.antpool.com:443\",\"URL\":\"stratum+tcp://stratum.antpool.com:443\",\"User\":\"antminer_1\",\"Work Difficulty\":65536.0,\"Works\":0},{\"Accepted\":0,\"AsicBoost\":true,\"Bad Work\":0,\"Best Share\":0,\"Current Block Height\":0,\"Current Block Version\":0,\"Diff1 Shares\":0,\"Difficulty Accepted\":0.0,\"Difficulty Rejected\":0.0,\"Difficulty Stale\":0.0,\"Discarded\":0,\"Get Failures\":0,\"Getworks\":0,\"Has GBT\":false,\"Has Stratum\":true,\"Has Vmask\":true,\"Last Share Difficulty\":0.0,\"Last Share Time\":0,\"Long Poll\":\"N\",\"POOL\":2,\"Pool Rejected%\":0.0,\"Pool Stale%\":0.0,\"Priority\":2,\"Proxy\":\"\",\"Proxy Type\":\"\",\"Quota\":1,\"Rejected\":0,\"Remote Failures\":0,\"Stale\":0,\"Status\":\"Alive\",\"Stratum Active\":false,\"Stratum Difficulty\":0.0,\"Stratum URL\":\"stratum.antpool.com:25\",\"URL\":\"stratum+tcp://stratum.antpool.com:25\",\"User\":\"antminer_1\",\"Work Difficulty\":0.0,\"Works\":0}],\"id\":1}"))
                                        .put(
                                                "{\"command\":\"summary\"}",
                                                new RpcHandler(
                                                        "{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1597187939,\"Code\":11,\"Msg\":\"Summary\",\"Description\":\"BOSminer+ 0.2.0-36c56a9363\"}],\"SUMMARY\":[{\"Accepted\":153,\"Best Share\":65536,\"Device Hardware%\":0.001644850292093576,\"Device Rejected%\":0.0,\"Difficulty Accepted\":10027008.0,\"Difficulty Rejected\":0.0,\"Difficulty Stale\":0.0,\"Discarded\":0,\"Elapsed\":3680,\"Found Blocks\":0,\"Get Failures\":0,\"Getworks\":110,\"Hardware Errors\":186,\"Last getwork\":1597187939,\"Local Work\":17131300,\"MHS 15m\":14199249.521457672,\"MHS 1m\":13967276.98800245,\"MHS 24h\":562115.4771588066,\"MHS 5m\":14257979.893394984,\"MHS 5s\":15740915.842676325,\"MHS av\":11988299.650652435,\"Network Blocks\":0,\"Pool Rejected%\":0.0,\"Pool Stale%\":0.0,\"Rejected\":0,\"Remote Failures\":0,\"Stale\":0,\"Total MH\":44121202599.395325,\"Utility\":2.4945652173913047,\"Work Utility\":184349.05814696933}],\"id\":1}"))
                                        .put(
                                                "{\"command\":\"fans\"}",
                                                new RpcHandler(
                                                        "{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1597187973,\"Code\":202,\"Msg\":\"4 Fan(s)\",\"Description\":\"BOSminer+ 0.2.0-36c56a9363\"}],\"FANS\":[{\"FAN\":0,\"ID\":0,\"RPM\":2820,\"Speed\":40},{\"FAN\":1,\"ID\":1,\"RPM\":3780,\"Speed\":40},{\"FAN\":2,\"ID\":2,\"RPM\":0,\"Speed\":40},{\"FAN\":3,\"ID\":3,\"RPM\":0,\"Speed\":40}],\"id\":1}"))
                                        .put(
                                                "{\"command\":\"temps\"}",
                                                new RpcHandler(
                                                        "{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1597187996,\"Code\":201,\"Msg\":\"3 Temp(s)\",\"Description\":\"BOSminer+ 0.2.0-36c56a9363\"}],\"TEMPS\":[{\"Board\":75.0,\"Chip\":0.0,\"ID\":6,\"TEMP\":0},{\"Board\":63.0,\"Chip\":0.0,\"ID\":7,\"TEMP\":1},{\"Board\":70.0,\"Chip\":0.0,\"ID\":8,\"TEMP\":2}],\"id\":1}"))
                                        .put(
                                                "{\"command\":\"devs\"}",
                                                new RpcHandler(
                                                        "{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1612231142,\"Code\":9,\"Msg\":\"3 ASC(s)\",\"Description\":\"BOSminer bosminer-plus-am1-s9 0.4.0-908ca41d\"}],\"DEVS\":[{\"ASC\":0,\"Accepted\":0,\"Device Elapsed\":55,\"Device Hardware%\":0.0,\"Device Rejected%\":0.0,\"Diff1 Work\":784,\"Difficulty Accepted\":0.0,\"Difficulty Rejected\":0.0,\"Enabled\":\"Y\",\"Hardware Error MHS 15m\":0.0,\"Hardware Errors\":0,\"ID\":6,\"Last Share Difficulty\":5376.0,\"Last Share Pool\":-1,\"Last Share Time\":1612231141,\"Last Valid Work\":1612231142,\"MHS 15m\":239449.19893788462,\"MHS 1m\":3591737.9840682694,\"MHS 5m\":718347.5968136538,\"MHS 5s\":4683070.74742169,\"MHS av\":3728777.765879786,\"Name\":\"\",\"Nominal MHS\":4668300.0,\"Rejected\":0,\"Status\":\"Alive\",\"Temperature\":0.0,\"Total MH\":208357453.463552,\"Utility\":0.0},{\"ASC\":1,\"Accepted\":0,\"Device Elapsed\":55,\"Device Hardware%\":0.0,\"Device Rejected%\":0.0,\"Diff1 Work\":819,\"Difficulty Accepted\":0.0,\"Difficulty Rejected\":0.0,\"Enabled\":\"Y\",\"Hardware Error MHS 15m\":0.0,\"Hardware Errors\":0,\"ID\":7,\"Last Share Difficulty\":4480.0,\"Last Share Pool\":-1,\"Last Share Time\":1612231129,\"Last Valid Work\":1612231142,\"MHS 15m\":250138.8953190402,\"MHS 1m\":3752083.429785603,\"MHS 5m\":750416.6859571205,\"MHS 5s\":4687913.424447901,\"MHS av\":2489177.916456381,\"Name\":\"\",\"Nominal MHS\":4668300.0,\"Rejected\":0,\"Status\":\"Alive\",\"Temperature\":0.0,\"Total MH\":139088220.91366398,\"Utility\":0.0},{\"ASC\":2,\"Accepted\":0,\"Device Elapsed\":55,\"Device Hardware%\":0.12626262626262627,\"Device Rejected%\":0.0,\"Diff1 Work\":791,\"Difficulty Accepted\":0.0,\"Difficulty Rejected\":0.0,\"Enabled\":\"Y\",\"Hardware Error MHS 15m\":305.41989660444443,\"Hardware Errors\":1,\"ID\":8,\"Last Share Difficulty\":5120.0,\"Last Share Pool\":-1,\"Last Share Time\":1612231139,\"Last Valid Work\":1612231142,\"MHS 15m\":241587.13821411575,\"MHS 1m\":3623807.073211736,\"MHS 5m\":724761.4146423473,\"MHS 5s\":4213098.705765925,\"MHS av\":3453411.084511585,\"Name\":\"\",\"Nominal MHS\":4668300.0,\"Rejected\":0,\"Status\":\"Alive\",\"Temperature\":0.0,\"Total MH\":192964290.67468798,\"Utility\":0.0}],\"id\":1}"))
                                        .build(),
                                Arrays.asList(
                                        "FANS.0.Speed",
                                        "FANS.1.Speed"),
                                new MinerStats.Builder()
                                        .setApiIp("127.0.0.1")
                                        .setApiPort(4028)
                                        .addPool(
                                                new Pool.Builder()
                                                        .setName("stratum.antpool.com:3333")
                                                        .setWorker("antminer_1")
                                                        .setStatus(true, true)
                                                        .setPriority(0)
                                                        .setCounts(152, 0, 0)
                                                        .build())
                                        .addPool(
                                                new Pool.Builder()
                                                        .setName("stratum.antpool.com:443")
                                                        .setWorker("antminer_1")
                                                        .setStatus(true, true)
                                                        .setPriority(1)
                                                        .setCounts(0, 0, 0)
                                                        .build())
                                        .addPool(
                                                new Pool.Builder()
                                                        .setName("stratum.antpool.com:25")
                                                        .setWorker("antminer_1")
                                                        .setStatus(true, true)
                                                        .setPriority(2)
                                                        .setCounts(0, 0, 0)
                                                        .build())
                                        .addAsic(
                                                new Asic.Builder()
                                                        .setHashRate(new BigDecimal("15740915842676.325000000"))
                                                        .setBoards(3)
                                                        .setFanInfo(
                                                                new FanInfo.Builder()
                                                                        .setCount(2)
                                                                        .addSpeed(2820)
                                                                        .addSpeed(3780)
                                                                        .setSpeedUnits("RPM")
                                                                        .build())
                                                        .addTemp(75)
                                                        .addTemp(63)
                                                        .addTemp(70)
                                                        .hasErrors(false)
                                                        .addRawStats(
                                                                ImmutableMap.of(
                                                                        "FANS.0.Speed",
                                                                        new BigDecimal("40"),
                                                                        "FANS.1.Speed",
                                                                        new BigDecimal("40")))
                                                        .build())
                                        .build()
                        },
                        {
                                // Antminer S9 (bOS)
                                BigDecimal.ONE,
                                ImmutableMap.builder()
                                        .put(
                                                "{\"command\":\"version\"}",
                                                new RpcHandler(
                                                        "{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1597196862,\"Code\":22,\"Msg\":\"BOSminer versions\",\"Description\":\"BOSminer 0.2.0-d360818\"}],\"VERSION\":[{\"API\":\"3.7\",\"BOSminer\":\"0.2.0-d360818\"}],\"id\":1}"))
                                        .put(
                                                "{\"command\":\"pools\"}",
                                                new RpcHandler(
                                                        "{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1597197657,\"Code\":7,\"Msg\":\"2 Pool(s)\",\"Description\":\"BOSminer 0.2.0-d360818\"}],\"POOLS\":[{\"Accepted\":68,\"AsicBoost\":true,\"Bad Work\":0,\"Best Share\":65536,\"Current Block Height\":0,\"Current Block Version\":536870912,\"Diff1 Shares\":6027,\"Difficulty Accepted\":359936.0,\"Difficulty Rejected\":1024.0,\"Difficulty Stale\":0.0,\"Discarded\":0,\"Get Failures\":0,\"Getworks\":8,\"Has GBT\":false,\"Has Stratum\":true,\"Has Vmask\":true,\"Last Share Difficulty\":65536.0,\"Last Share Time\":1597197610,\"Long Poll\":\"N\",\"POOL\":0,\"Pool Rejected%\":0.28368794326241137,\"Pool Stale%\":0.0,\"Priority\":0,\"Proxy\":\"\",\"Proxy Type\":\"\",\"Quota\":1,\"Rejected\":2,\"Remote Failures\":0,\"Stale\":0,\"Status\":\"Alive\",\"Stratum Active\":true,\"Stratum Difficulty\":65536.0,\"Stratum URL\":\"stratum.antpool.com:3333\",\"URL\":\"stratum+tcp://stratum.antpool.com:3333\",\"User\":\"antminer_1\",\"Work Difficulty\":65536.0,\"Works\":483100},{\"Accepted\":0,\"AsicBoost\":true,\"Bad Work\":0,\"Best Share\":0,\"Current Block Height\":0,\"Current Block Version\":0,\"Diff1 Shares\":0,\"Difficulty Accepted\":0.0,\"Difficulty Rejected\":0.0,\"Difficulty Stale\":0.0,\"Discarded\":0,\"Get Failures\":0,\"Getworks\":0,\"Has GBT\":false,\"Has Stratum\":true,\"Has Vmask\":true,\"Last Share Difficulty\":0.0,\"Last Share Time\":0,\"Long Poll\":\"N\",\"POOL\":1,\"Pool Rejected%\":0.0,\"Pool Stale%\":0.0,\"Priority\":1,\"Proxy\":\"\",\"Proxy Type\":\"\",\"Quota\":1,\"Rejected\":0,\"Remote Failures\":0,\"Stale\":0,\"Status\":\"Dead\",\"Stratum Active\":false,\"Stratum Difficulty\":0.0,\"Stratum URL\":\"stratum.slushpool.com:3333\",\"URL\":\"stratum+tcp://stratum.slushpool.com:3333\",\"User\":\"!non-existent-user!\",\"Work Difficulty\":0.0,\"Works\":0}],\"id\":1}"))
                                        .put(
                                                "{\"command\":\"summary\"}",
                                                new RpcHandler(
                                                        "{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1597197626,\"Code\":11,\"Msg\":\"Summary\",\"Description\":\"BOSminer 0.2.0-d360818\"}],\"SUMMARY\":[{\"Accepted\":68,\"Best Share\":65536,\"Device Hardware%\":0.0,\"Device Rejected%\":22.852041954920775,\"Difficulty Accepted\":359936.0,\"Difficulty Rejected\":1024.0,\"Difficulty Stale\":0.0,\"Discarded\":0,\"Elapsed\":92,\"Found Blocks\":0,\"Get Failures\":0,\"Getworks\":7,\"Hardware Errors\":0,\"Last getwork\":1597197626,\"Local Work\":357660,\"MHS 15m\":1368586.556684464,\"MHS 1m\":14194945.838709138,\"MHS 24h\":14256.109965463167,\"MHS 5m\":4105759.670053392,\"MHS 5s\":14274425.317759488,\"MHS av\":16698158.653278796,\"Network Blocks\":0,\"Pool Rejected%\":2.857142857142857,\"Pool Stale%\":0.0,\"Rejected\":2,\"Remote Failures\":0,\"Stale\":0,\"Total MH\":1550311395.16416,\"Utility\":44.347826086956516,\"Work Utility\":185334.3043014206}],\"id\":1}"))
                                        .put(
                                                "{\"command\":\"fans\"}",
                                                new RpcHandler(
                                                        "{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1597197416,\"Code\":202,\"Msg\":\"4 Fan(s)\",\"Description\":\"BOSminer 0.2.0-d360818\"}],\"FANS\":[{\"FAN\":0,\"ID\":0,\"RPM\":1560,\"Speed\":1},{\"FAN\":1,\"ID\":1,\"RPM\":1560,\"Speed\":1},{\"FAN\":2,\"ID\":2,\"RPM\":0,\"Speed\":1},{\"FAN\":3,\"ID\":3,\"RPM\":0,\"Speed\":1}],\"id\":1}"))
                                        .put(
                                                "{\"command\":\"temps\"}",
                                                new RpcHandler(
                                                        "{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1597197430,\"Code\":201,\"Msg\":\"3 Temp(s)\",\"Description\":\"BOSminer 0.2.0-d360818\"}],\"TEMPS\":[{\"Board\":53.0,\"Chip\":0.0,\"ID\":6,\"TEMP\":0},{\"Board\":46.0,\"Chip\":0.0,\"ID\":7,\"TEMP\":1},{\"Board\":52.0,\"Chip\":0.0,\"ID\":8,\"TEMP\":2}],\"id\":1}"))
                                        .put(
                                                "{\"command\":\"devs\"}",
                                                new RpcHandler(
                                                        "{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1612231142,\"Code\":9,\"Msg\":\"3 ASC(s)\",\"Description\":\"BOSminer bosminer-plus-am1-s9 0.4.0-908ca41d\"}],\"DEVS\":[{\"ASC\":0,\"Accepted\":0,\"Device Elapsed\":55,\"Device Hardware%\":0.0,\"Device Rejected%\":0.0,\"Diff1 Work\":784,\"Difficulty Accepted\":0.0,\"Difficulty Rejected\":0.0,\"Enabled\":\"Y\",\"Hardware Error MHS 15m\":0.0,\"Hardware Errors\":0,\"ID\":6,\"Last Share Difficulty\":5376.0,\"Last Share Pool\":-1,\"Last Share Time\":1612231141,\"Last Valid Work\":1612231142,\"MHS 15m\":239449.19893788462,\"MHS 1m\":3591737.9840682694,\"MHS 5m\":718347.5968136538,\"MHS 5s\":4683070.74742169,\"MHS av\":3728777.765879786,\"Name\":\"\",\"Nominal MHS\":4668300.0,\"Rejected\":0,\"Status\":\"Alive\",\"Temperature\":0.0,\"Total MH\":208357453.463552,\"Utility\":0.0},{\"ASC\":1,\"Accepted\":0,\"Device Elapsed\":55,\"Device Hardware%\":0.0,\"Device Rejected%\":0.0,\"Diff1 Work\":819,\"Difficulty Accepted\":0.0,\"Difficulty Rejected\":0.0,\"Enabled\":\"Y\",\"Hardware Error MHS 15m\":0.0,\"Hardware Errors\":0,\"ID\":7,\"Last Share Difficulty\":4480.0,\"Last Share Pool\":-1,\"Last Share Time\":1612231129,\"Last Valid Work\":1612231142,\"MHS 15m\":250138.8953190402,\"MHS 1m\":3752083.429785603,\"MHS 5m\":750416.6859571205,\"MHS 5s\":4687913.424447901,\"MHS av\":2489177.916456381,\"Name\":\"\",\"Nominal MHS\":4668300.0,\"Rejected\":0,\"Status\":\"Alive\",\"Temperature\":0.0,\"Total MH\":139088220.91366398,\"Utility\":0.0},{\"ASC\":2,\"Accepted\":0,\"Device Elapsed\":55,\"Device Hardware%\":0.12626262626262627,\"Device Rejected%\":0.0,\"Diff1 Work\":791,\"Difficulty Accepted\":0.0,\"Difficulty Rejected\":0.0,\"Enabled\":\"Y\",\"Hardware Error MHS 15m\":305.41989660444443,\"Hardware Errors\":1,\"ID\":8,\"Last Share Difficulty\":5120.0,\"Last Share Pool\":-1,\"Last Share Time\":1612231139,\"Last Valid Work\":1612231142,\"MHS 15m\":241587.13821411575,\"MHS 1m\":3623807.073211736,\"MHS 5m\":724761.4146423473,\"MHS 5s\":4213098.705765925,\"MHS av\":3453411.084511585,\"Name\":\"\",\"Nominal MHS\":4668300.0,\"Rejected\":0,\"Status\":\"Alive\",\"Temperature\":0.0,\"Total MH\":192964290.67468798,\"Utility\":0.0}],\"id\":1}"))
                                        .build(),
                                Arrays.asList(
                                        "FANS.0.Speed",
                                        "FANS.1.Speed"),
                                new MinerStats.Builder()
                                        .setApiIp("127.0.0.1")
                                        .setApiPort(4028)
                                        .addPool(
                                                new Pool.Builder()
                                                        .setName("stratum.antpool.com:3333")
                                                        .setWorker("antminer_1")
                                                        .setStatus(true, true)
                                                        .setPriority(0)
                                                        .setCounts(68, 2, 0)
                                                        .build())
                                        .addPool(
                                                new Pool.Builder()
                                                        .setName("stratum.slushpool.com:3333")
                                                        .setWorker("!non-existent-user!")
                                                        .setStatus(true, false)
                                                        .setPriority(1)
                                                        .setCounts(0, 0, 0)
                                                        .build())
                                        .addAsic(
                                                new Asic.Builder()
                                                        .setHashRate(new BigDecimal("14274425317759.488000000"))
                                                        .setBoards(3)
                                                        .setFanInfo(
                                                                new FanInfo.Builder()
                                                                        .setCount(2)
                                                                        .addSpeed(1560)
                                                                        .addSpeed(1560)
                                                                        .setSpeedUnits("RPM")
                                                                        .build())
                                                        .addTemp(53)
                                                        .addTemp(46)
                                                        .addTemp(52)
                                                        .hasErrors(false)
                                                        .addRawStats(
                                                                ImmutableMap.of(
                                                                        "FANS.0.Speed",
                                                                        new BigDecimal("1"),
                                                                        "FANS.1.Speed",
                                                                        new BigDecimal("1")))
                                                        .build())
                                        .build()
                        },
                        {
                                // Antminer S9 (Hiveon)
                                BigDecimal.ONE,
                                ImmutableMap.of(
                                        "{\"command\":\"version\"}",
                                        new RpcHandler(
                                                "{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1600293257,\"Code\":22,\"Msg\":\"BMMiner versions\",\"Description\":\"bmminer 1.0.0\"}],\"VERSION\":[{\"BMMiner\":\"2.0.0 rwglr\",\"API\":\"3.1\",\"Miner\":\"30.0.1.3\",\"CompileTime\":\"Tue Nov 20 10:12:30 UTC 2019\",\"Type\":\"Antminer S9 Hiveon\"}],\"id\":1}"),
                                        "{\"command\":\"stats\"}",
                                        new RpcHandler(
                                                "{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1600293501,\"Code\":70,\"Msg\":\"BMMiner stats\",\"Description\":\"bmminer 1.0.0\"}],\"STATS\":[{\"BMMiner\":\"2.0.0 rwglr\",\"Miner\":\"30.0.1.3\",\"CompileTime\":\"Tue Nov 20 10:12:30 UTC 2019\",\"Type\":\"Antminer S9 Hiveon\"}{\"STATS\":0,\"ID\":\"BC50\",\"Elapsed\":69,\"Calls\":0,\"Wait\":0.000000,\"Max\":0.000000,\"Min\":99999999.000000,\"GHS 5s\":\"11781.40\",\"GHS av\":11609.55,\"miner_count\":3,\"frequency\":\"550\",\"fan_num\":2,\"fan1\":0,\"fan2\":0,\"fan3\":0,\"fan4\":0,\"fan5\":3840,\"fan6\":4920,\"fan7\":0,\"fan8\":0,\"temp_num\":3,\"temp1\":0,\"temp2\":0,\"temp3\":0,\"temp4\":0,\"temp5\":0,\"temp6\":59,\"temp7\":50,\"temp8\":57,\"temp9\":0,\"temp10\":0,\"temp11\":0,\"temp12\":0,\"temp13\":0,\"temp14\":0,\"temp15\":0,\"temp16\":0,\"temp2_1\":0,\"temp2_2\":0,\"temp2_3\":0,\"temp2_4\":0,\"temp2_5\":0,\"temp2_6\":74,\"temp2_7\":65,\"temp2_8\":72,\"temp2_9\":0,\"temp2_10\":0,\"temp2_11\":0,\"temp2_12\":0,\"temp2_13\":0,\"temp2_14\":0,\"temp2_15\":0,\"temp2_16\":0,\"temp3_1\":0,\"temp3_2\":0,\"temp3_3\":0,\"temp3_4\":0,\"temp3_5\":0,\"temp3_6\":0,\"temp3_7\":0,\"temp3_8\":0,\"temp3_9\":0,\"temp3_10\":0,\"temp3_11\":0,\"temp3_12\":0,\"temp3_13\":0,\"temp3_14\":0,\"temp3_15\":0,\"temp3_16\":0,\"freq_avg1\":0.00,\"freq_avg2\":0.00,\"freq_avg3\":0.00,\"freq_avg4\":0.00,\"freq_avg5\":0.00,\"freq_avg6\":550.00,\"freq_avg7\":550.00,\"freq_avg8\":550.00,\"freq_avg9\":0.00,\"freq_avg10\":0.00,\"freq_avg11\":0.00,\"freq_avg12\":0.00,\"freq_avg13\":0.00,\"freq_avg14\":0.00,\"freq_avg15\":0.00,\"freq_avg16\":0.00,\"total_rateideal\":11850.30,\"total_freqavg\":550.00,\"total_acn\":189,\"total_rate\":11781.39,\"chain_rateideal1\":0.00,\"chain_rateideal2\":0.00,\"chain_rateideal3\":0.00,\"chain_rateideal4\":0.00,\"chain_rateideal5\":0.00,\"chain_rateideal6\":3950.10,\"chain_rateideal7\":3950.10,\"chain_rateideal8\":3950.10,\"chain_rateideal9\":0.00,\"chain_rateideal10\":0.00,\"chain_rateideal11\":0.00,\"chain_rateideal12\":0.00,\"chain_rateideal13\":0.00,\"chain_rateideal14\":0.00,\"chain_rateideal15\":0.00,\"chain_rateideal16\":0.00,\"temp_max\":74,\"Device Hardware%\":0.0000,\"no_matching_work\":0,\"chain_acn1\":0,\"chain_acn2\":0,\"chain_acn3\":0,\"chain_acn4\":0,\"chain_acn5\":0,\"chain_acn6\":63,\"chain_acn7\":63,\"chain_acn8\":63,\"chain_acn9\":0,\"chain_acn10\":0,\"chain_acn11\":0,\"chain_acn12\":0,\"chain_acn13\":0,\"chain_acn14\":0,\"chain_acn15\":0,\"chain_acn16\":0,\"chain_acs1\":\"\",\"chain_acs2\":\"\",\"chain_acs3\":\"\",\"chain_acs4\":\"\",\"chain_acs5\":\"\",\"chain_acs6\":\" oooooooo oooooooo oooooooo oooooooo oooooooo oooooooo oooooooo ooooooo\",\"chain_acs7\":\" oooooooo oooooooo oooooooo oooooooo oooooooo oooooooo oooooooo ooooooo\",\"chain_acs8\":\" oooooooo oooooooo oooooooo oooooooo oooooooo oooooooo oooooooo ooooooo\",\"chain_acs9\":\"\",\"chain_acs10\":\"\",\"chain_acs11\":\"\",\"chain_acs12\":\"\",\"chain_acs13\":\"\",\"chain_acs14\":\"\",\"chain_acs15\":\"\",\"chain_acs16\":\"\",\"chain_hw1\":0,\"chain_hw2\":0,\"chain_hw3\":0,\"chain_hw4\":0,\"chain_hw5\":0,\"chain_hw6\":0,\"chain_hw7\":0,\"chain_hw8\":0,\"chain_hw9\":0,\"chain_hw10\":0,\"chain_hw11\":0,\"chain_hw12\":0,\"chain_hw13\":0,\"chain_hw14\":0,\"chain_hw15\":0,\"chain_hw16\":0,\"chain_rate1\":\"\",\"chain_rate2\":\"\",\"chain_rate3\":\"\",\"chain_rate4\":\"\",\"chain_rate5\":\"\",\"chain_rate6\":\"3911.64\",\"chain_rate7\":\"3913.70\",\"chain_rate8\":\"3956.05\",\"chain_rate9\":\"\",\"chain_rate10\":\"\",\"chain_rate11\":\"\",\"chain_rate12\":\"\",\"chain_rate13\":\"\",\"chain_rate14\":\"\",\"chain_rate15\":\"\",\"chain_rate16\":\"\",\"chain_xtime6\":\"{}\",\"chain_xtime7\":\"{}\",\"chain_xtime8\":\"{}\",\"chain_offside_6\":\"0\",\"chain_offside_7\":\"0\",\"chain_offside_8\":\"0\",\"chain_opencore_6\":\"0\",\"chain_opencore_7\":\"0\",\"chain_opencore_8\":\"0\",\"miner_version\":\"30.0.1.3\",\"chain_power1\":0.00,\"chain_power2\":0.00,\"chain_power3\":0.00,\"chain_power4\":0.00,\"chain_power5\":0.00,\"chain_power6\":11.55,\"chain_power7\":11.55,\"chain_power8\":11.55,\"chain_power9\":0.00,\"chain_power10\":0.00,\"chain_power11\":0.00,\"chain_power12\":0.00,\"chain_power13\":0.00,\"chain_power14\":0.00,\"chain_power15\":0.00,\"chain_power16\":0.00,\"chain_power\":\"34.65 (AB)\"}],\"id\":1}"),
                                        "{\"command\":\"pools\"}",
                                        new RpcHandler(
                                                "{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1533152695,\"Code\":7,\"Msg\":\"5 Pool(s)\",\"Description\":\"cgminer 4.10.0\"}],\"POOLS\":[{\"POOL\":0,\"URL\":\"stratum+tcp://dash-eu.coinmine.pl:6099\",\"Status\":\"Alive\",\"Priority\":0,\"Quota\":0,\"Long Poll\":\"N\",\"Getworks\":1167,\"Accepted\":3038,\"Rejected\":8,\"Discarded\":28157,\"Stale\":5,\"Get Failures\":1,\"Remote Failures\":0,\"User\":\"Dash.default\",\"Last Share Time\":\"0:00:00\",\"Diff\":\" 69.2461\",\"Diff1 Shares\":6475798,\"Proxy Type\":\"\",\"Proxy\":\"\",\"Difficulty Accepted\":198412.94104085,\"Difficulty Rejected\":604.97361199,\"Difficulty Stale\":0.00000000,\"Last Share Difficulty\":69.24612531,\"Has Stratum\":true,\"Stratum Active\":true,\"Stratum URL\":\"dash-eu.coinmine.pl\",\"Has GBT\":false,\"Best Share\":\"  0.0000\",\"Pool Rejected%\":0.3040,\"Pool Stale%\":0.0000},{\"POOL\":1,\"URL\":\"\",\"Status\":\"Dead\",\"Priority\":1,\"Quota\":0,\"Long Poll\":\"N\",\"Getworks\":0,\"Accepted\":0,\"Rejected\":0,\"Discarded\":0,\"Stale\":0,\"Get Failures\":0,\"Remote Failures\":0,\"User\":\"\",\"Last Share Time\":\"0\",\"Diff\":\"  0.0000\",\"Diff1 Shares\":0,\"Proxy Type\":\"\",\"Proxy\":\"\",\"Difficulty Accepted\":0.00000000,\"Difficulty Rejected\":0.00000000,\"Difficulty Stale\":0.00000000,\"Last Share Difficulty\":0.00000000,\"Has Stratum\":false,\"Stratum Active\":false,\"Stratum URL\":\"\",\"Has GBT\":false,\"Best Share\":\"  0.0000\",\"Pool Rejected%\":0.0000,\"Pool Stale%\":0.0000},{\"POOL\":2,\"URL\":\"\",\"Status\":\"Dead\",\"Priority\":2,\"Quota\":0,\"Long Poll\":\"N\",\"Getworks\":0,\"Accepted\":0,\"Rejected\":0,\"Discarded\":0,\"Stale\":0,\"Get Failures\":0,\"Remote Failures\":0,\"User\":\"\",\"Last Share Time\":\"0\",\"Diff\":\"  0.0000\",\"Diff1 Shares\":0,\"Proxy Type\":\"\",\"Proxy\":\"\",\"Difficulty Accepted\":0.00000000,\"Difficulty Rejected\":0.00000000,\"Difficulty Stale\":0.00000000,\"Last Share Difficulty\":0.00000000,\"Has Stratum\":false,\"Stratum Active\":false,\"Stratum URL\":\"\",\"Has GBT\":false,\"Best Share\":\"  0.0000\",\"Pool Rejected%\":0.0000,\"Pool Stale%\":0.0000},\n{\"POOL\":3,\"URL\":\"stratum+tcp://dash.suprnova.cc:9991\",\"Status\":\"Alive\",\"Priority\":9998,\"Quota\":0,\"Long Poll\":\"N\",\"Getworks\":1,\"Accepted\":0,\"Rejected\":0,\"Discarded\":0,\"Stale\":0,\"Get Failures\":0,\"Remote Failures\":0,\"User\":\"devFeeMiner.9\",\"Last Share Time\":\"0\",\"Diff\":\" 64.0000\",\"Diff1 Shares\":0,\"Proxy Type\":\"\",\"Proxy\":\"\",\"Difficulty Accepted\":0.00000000,\"Difficulty Rejected\":0.00000000,\"Difficulty Stale\":0.00000000,\"Last Share Difficulty\":0.00000000,\"Has Stratum\":true,\"Stratum Active\":false,\"Stratum URL\":\"\",\"Has GBT\":false,\"Best Share\":\" 0.0000\",\"Pool Rejected%\":0.0000,\"Pool Stale%\":0.0000},{\"POOL\":4,\"URL\":\"stratum+tcp://dash-eu.coinmine.pl:6099\",\"Status\":\"Alive\",\"Priority\":9999,\"Quota\":0,\"Long Poll\":\"N\",\"Getworks\":1,\"Accepted\":0,\"Rejected\":0,\"Discarded\":0,\"Stale\":0,\"Get Failures\":0,\"Remote Failures\":0,\"User\":\"devFeeMiner.1\",\"Last Share Time\":\"0\",\"Diff\":\" 20.0000\",\"Diff1 Shares\":0,\"Proxy Type\":\"\",\"Proxy\":\"\",\"Difficulty Accepted\":0.00000000,\"Difficulty Rejected\":0.00000000,\"Difficulty Stale\":0.00000000,\"Last Share Difficulty\":0.00000000,\"Has Stratum\":true,\"Stratum Active\":false,\"Stratum URL\":\"\",\"Has GBT\":false,\"Best Share\":\" 0.0000\",\"Pool Rejected%\":0.0000,\"Pool Stale%\":0.0000}],\"id\":1}")),
                                Arrays.asList(
                                        "STATS.0.temp6",
                                        "STATS.0.temp7"),
                                new MinerStats.Builder()
                                        .setApiIp("127.0.0.1")
                                        .setApiPort(4028)
                                        .addPool(
                                                new Pool.Builder()
                                                        .setName("dash-eu.coinmine.pl:6099")
                                                        .setWorker("Dash.default")
                                                        .setStatus(true, true)
                                                        .setPriority(0)
                                                        .setCounts(3038, 8, 5)
                                                        .build())
                                        .addPool(
                                                new Pool.Builder()
                                                        .setName("dash.suprnova.cc:9991")
                                                        .setWorker("devFeeMiner.9")
                                                        .setStatus(true, true)
                                                        .setPriority(9998)
                                                        .setCounts(0, 0, 0)
                                                        .build())
                                        .addPool(
                                                new Pool.Builder()
                                                        .setName("dash-eu.coinmine.pl:6099")
                                                        .setWorker("devFeeMiner.1")
                                                        .setStatus(true, true)
                                                        .setPriority(9999)
                                                        .setCounts(0, 0, 0)
                                                        .build())
                                        .addAsic(
                                                new Asic.Builder()
                                                        .setHashRate(new BigDecimal("11781400000000.00"))
                                                        .setBoards(3)
                                                        .setFanInfo(
                                                                new FanInfo.Builder()
                                                                        .setCount(2)
                                                                        .addSpeed(3840)
                                                                        .addSpeed(4920)
                                                                        .setSpeedUnits("RPM")
                                                                        .build())
                                                        .addTemp(59)
                                                        .addTemp(50)
                                                        .addTemp(57)
                                                        .addTemp(74)
                                                        .addTemp(65)
                                                        .addTemp(72)
                                                        .hasErrors(false)
                                                        .addRawStats(
                                                                ImmutableMap.of(
                                                                        "STATS.0.temp6",
                                                                        new BigDecimal("59"),
                                                                        "STATS.0.temp7",
                                                                        new BigDecimal("50")))
                                                        .setMinerType("Antminer S9 Hiveon")
                                                        .setCompileTime("Tue Nov 20 10:12:30 UTC 2019")
                                                        .build())
                                        .build()
                        },
                        {
                                // Antminer S9 (NiceHash)
                                BigDecimal.ONE,
                                ImmutableMap.of(
                                        "{\"command\":\"version\"}",
                                        new RpcHandler(
                                                "{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1600299177,\"Code\":22,\"Msg\":\"CGMiner versions\",\"Description\":\"cgminer 4.11.1\"}],\"VERSION\":[{\"CGMiner\":\"4.11.1\",\"API\":\"3.7\",\"Miner\":\"26.0.1.3\",\"CompileTime\":\"Fri May  8 15:27:59 CST 2020\",\"Type\":\"Antminer S9 (vnish 3.8.6)\"}],\"id\":1}"),
                                        "{\"command\":\"stats\"}",
                                        new RpcHandler(
                                                "{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1600299244,\"Code\":70,\"Msg\":\"CGMiner stats\",\"Description\":\"cgminer 4.11.1\"}],\"STATS\":[{\"Cgminer\":\"4.11.1\",\"Miner\":\"26.0.1.3\",\"CompileTime\":\"Fri May  8 15:27:59 CST 2020\",\"Type\":\"Antminer S9 (vnish 3.8.6)\"}{\"STATS\":0,\"ID\":\"BC50\",\"Elapsed\":3617,\"Calls\":0,\"Wait\":0.000000,\"Max\":0.000000,\"Min\":99999999.000000,\"GHS 5s\":\"12795.48\",\"GHS av\":12900.71,\"miner_count\":3,\"frequency\":\"600\",\"fan_num\":2,\"fan1\":0,\"fan2\":0,\"fan3\":0,\"fan4\":0,\"fan5\":5400,\"fan6\":5880,\"fan7\":0,\"fan8\":0,\"temp_num\":3,\"temp1\":0,\"temp2\":0,\"temp3\":0,\"temp4\":0,\"temp5\":0,\"temp6\":65,\"temp7\":56,\"temp8\":63,\"temp9\":0,\"temp10\":0,\"temp11\":0,\"temp12\":0,\"temp13\":0,\"temp14\":0,\"temp15\":0,\"temp16\":0,\"temp2_1\":0,\"temp2_2\":0,\"temp2_3\":0,\"temp2_4\":0,\"temp2_5\":0,\"temp2_6\":80,\"temp2_7\":71,\"temp2_8\":78,\"temp2_9\":0,\"temp2_10\":0,\"temp2_11\":0,\"temp2_12\":0,\"temp2_13\":0,\"temp2_14\":0,\"temp2_15\":0,\"temp2_16\":0,\"temp3_1\":0,\"temp3_2\":0,\"temp3_3\":0,\"temp3_4\":0,\"temp3_5\":0,\"temp3_6\":0,\"temp3_7\":0,\"temp3_8\":0,\"temp3_9\":0,\"temp3_10\":0,\"temp3_11\":0,\"temp3_12\":0,\"temp3_13\":0,\"temp3_14\":0,\"temp3_15\":0,\"temp3_16\":0,\"freq_avg1\":0.00,\"freq_avg2\":0.00,\"freq_avg3\":0.00,\"freq_avg4\":0.00,\"freq_avg5\":0.00,\"freq_avg6\":600.00,\"freq_avg7\":600.00,\"freq_avg8\":600.00,\"freq_avg9\":0.00,\"freq_avg10\":0.00,\"freq_avg11\":0.00,\"freq_avg12\":0.00,\"freq_avg13\":0.00,\"freq_avg14\":0.00,\"freq_avg15\":0.00,\"freq_avg16\":0.00,\"total_rateideal\":12927.60,\"total_freqavg\":600.00,\"total_acn\":189,\"total_rate\":12795.47,\"chain_rateideal1\":0.00,\"chain_rateideal2\":0.00,\"chain_rateideal3\":0.00,\"chain_rateideal4\":0.00,\"chain_rateideal5\":0.00,\"chain_rateideal6\":4309.20,\"chain_rateideal7\":4309.20,\"chain_rateideal8\":4309.20,\"chain_rateideal9\":0.00,\"chain_rateideal10\":0.00,\"chain_rateideal11\":0.00,\"chain_rateideal12\":0.00,\"chain_rateideal13\":0.00,\"chain_rateideal14\":0.00,\"chain_rateideal15\":0.00,\"chain_rateideal16\":0.00,\"temp_max\":80,\"Device Hardware%\":0.0034,\"no_matching_work\":297,\"chain_acn1\":0,\"chain_acn2\":0,\"chain_acn3\":0,\"chain_acn4\":0,\"chain_acn5\":0,\"chain_acn6\":63,\"chain_acn7\":63,\"chain_acn8\":63,\"chain_acn9\":0,\"chain_acn10\":0,\"chain_acn11\":0,\"chain_acn12\":0,\"chain_acn13\":0,\"chain_acn14\":0,\"chain_acn15\":0,\"chain_acn16\":0,\"chain_acs1\":\"\",\"chain_acs2\":\"\",\"chain_acs3\":\"\",\"chain_acs4\":\"\",\"chain_acs5\":\"\",\"chain_acs6\":\" oooooooo oooooooo oooooooo oooooooo oooooooo oooooooo oooooooo ooooooo\",\"chain_acs7\":\" oooooooo oooooooo oooooooo oooooooo oooooooo oooooooo oooooooo ooooooo\",\"chain_acs8\":\" oooooooo oooooooo oooooooo oooooooo oooooooo oooooooo oooooooo ooooooo\",\"chain_acs9\":\"\",\"chain_acs10\":\"\",\"chain_acs11\":\"\",\"chain_acs12\":\"\",\"chain_acs13\":\"\",\"chain_acs14\":\"\",\"chain_acs15\":\"\",\"chain_acs16\":\"\",\"chain_hw1\":0,\"chain_hw2\":0,\"chain_hw3\":0,\"chain_hw4\":0,\"chain_hw5\":0,\"chain_hw6\":1,\"chain_hw7\":3,\"chain_hw8\":293,\"chain_hw9\":0,\"chain_hw10\":0,\"chain_hw11\":0,\"chain_hw12\":0,\"chain_hw13\":0,\"chain_hw14\":0,\"chain_hw15\":0,\"chain_hw16\":0,\"chain_rate1\":\"\",\"chain_rate2\":\"\",\"chain_rate3\":\"\",\"chain_rate4\":\"\",\"chain_rate5\":\"\",\"chain_rate6\":\"4264.48\",\"chain_rate7\":\"4298.32\",\"chain_rate8\":\"4232.67\",\"chain_rate9\":\"\",\"chain_rate10\":\"\",\"chain_rate11\":\"\",\"chain_rate12\":\"\",\"chain_rate13\":\"\",\"chain_rate14\":\"\",\"chain_rate15\":\"\",\"chain_rate16\":\"\",\"chain_bchips6\":\"Warm up\",\"chain_bchips7\":\"Warm up\",\"chain_bchips8\":\"Warm up\",\"chain_xtime6\":\"{}\",\"chain_xtime7\":\"{}\",\"chain_xtime8\":\"{}\",\"chain_offside_6\":\"0\",\"chain_offside_7\":\"0\",\"chain_offside_8\":\"0\",\"chain_opencore_6\":\"0\",\"chain_opencore_7\":\"0\",\"chain_opencore_8\":\"0\",\"chain_vol6\":920,\"chain_vol7\":920,\"chain_vol8\":920,\"chain_consumption6\":495,\"chain_consumption7\":495,\"chain_consumption8\":495,\"miner_version\":\"26.0.1.3\",\"manual_fan_mode\":false,\"build_version\":\"3.8.6\"}],\"id\":1}"),
                                        "{\"command\":\"pools\"}",
                                        new RpcHandler(
                                                "{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1600299268,\"Code\":7,\"Msg\":\"6 Pool(s)\",\"Description\":\"cgminer 4.11.1\"}],\"POOLS\":[{\"POOL\":0,\"URL\":\"stratum+tcp://solo.antpool.com:3333\",\"Status\":\"Alive\",\"Priority\":0,\"Quota\":1,\"Long Poll\":\"N\",\"Getworks\":81,\"Accepted\":135,\"Rejected\":0,\"Discarded\":3311,\"Stale\":0,\"Get Failures\":0,\"Remote Failures\":0,\"User\":\"antminer_1\",\"Last Share Time\":\"0:00:03\",\"Diff\":\"65.5K\",\"Diff1 Shares\":0,\"Proxy Type\":\"\",\"Proxy\":\"\",\"Difficulty Accepted\":8650752.00000000,\"Difficulty Rejected\":0.00000000,\"Difficulty Stale\":0.00000000,\"Last Share Difficulty\":65536.00000000,\"Has Stratum\":true,\"Stratum Active\":true,\"Stratum URL\":\"solo.antpool.com\",\"Has GBT\":false,\"Best Share\":47581166,\"Pool Rejected%\":0.0000,\"Pool Stale%\":0.0000,\"Boost\":\"Normal\"},{\"POOL\":1,\"URL\":\"stratum+tcp://stratum.antpool.com:3333\",\"Status\":\"Alive\",\"Priority\":1,\"Quota\":1,\"Long Poll\":\"N\",\"Getworks\":2,\"Accepted\":0,\"Rejected\":0,\"Discarded\":0,\"Stale\":0,\"Get Failures\":0,\"Remote Failures\":0,\"User\":\"antminer_1\",\"Last Share Time\":\"0\",\"Diff\":\"\",\"Diff1 Shares\":0,\"Proxy Type\":\"\",\"Proxy\":\"\",\"Difficulty Accepted\":0.00000000,\"Difficulty Rejected\":0.00000000,\"Difficulty Stale\":0.00000000,\"Last Share Difficulty\":0.00000000,\"Has Stratum\":true,\"Stratum Active\":false,\"Stratum URL\":\"\",\"Has GBT\":false,\"Best Share\":0,\"Pool Rejected%\":0.0000,\"Pool Stale%\":0.0000,\"Boost\":\"Normal\"},{\"POOL\":2,\"URL\":\"stratum+tcp://cn.ss.btc.com:3333\",\"Status\":\"Dead\",\"Priority\":2,\"Quota\":1,\"Long Poll\":\"N\",\"Getworks\":0,\"Accepted\":0,\"Rejected\":0,\"Discarded\":0,\"Stale\":0,\"Get Failures\":0,\"Remote Failures\":0,\"User\":\"antminer.1\",\"Last Share Time\":\"0\",\"Diff\":\"\",\"Diff1 Shares\":0,\"Proxy Type\":\"\",\"Proxy\":\"\",\"Difficulty Accepted\":0.00000000,\"Difficulty Rejected\":0.00000000,\"Difficulty Stale\":0.00000000,\"Last Share Difficulty\":0.00000000,\"Has Stratum\":true,\"Stratum Active\":false,\"Stratum URL\":\"\",\"Has GBT\":false,\"Best Share\":0,\"Pool Rejected%\":0.0000,\"Pool Stale%\":0.0000,\"Boost\":\"Normal\"},{\"POOL\":3,\"URL\":\"DevFee\",\"Status\":\"Alive\",\"Priority\":993,\"Quota\":1,\"Long Poll\":\"N\",\"Getworks\":132,\"Accepted\":166,\"Rejected\":0,\"Discarded\":0,\"Stale\":0,\"Get Failures\":0,\"Remote Failures\":0,\"User\":\"DevFee\",\"Last Share Time\":\"0:03:06\",\"Diff\":\"512\",\"Diff1 Shares\":0,\"Proxy Type\":\"\",\"Proxy\":\"\",\"Difficulty Accepted\":84992.00000000,\"Difficulty Rejected\":0.00000000,\"Difficulty Stale\":0.00000000,\"Last Share Difficulty\":512.00000000,\"Has Stratum\":true,\"Stratum Active\":false,\"Stratum URL\":\"\",\"Has GBT\":false,\"Best Share\":25363,\"Pool Rejected%\":0.0000,\"Pool Stale%\":0.0000,\"Boost\":\"Normal\"}],\"id\":1}")),
                                Arrays.asList(
                                        "STATS.0.temp6",
                                        "STATS.0.temp7"),
                                new MinerStats.Builder()
                                        .setApiIp("127.0.0.1")
                                        .setApiPort(4028)
                                        .addPool(
                                                new Pool.Builder()
                                                        .setName("solo.antpool.com:3333")
                                                        .setWorker("antminer_1")
                                                        .setStatus(true, true)
                                                        .setPriority(0)
                                                        .setCounts(135, 0, 0)
                                                        .build())
                                        .addPool(
                                                new Pool.Builder()
                                                        .setName("stratum.antpool.com:3333")
                                                        .setWorker("antminer_1")
                                                        .setStatus(true, true)
                                                        .setPriority(1)
                                                        .setCounts(0, 0, 0)
                                                        .build())
                                        .addPool(
                                                new Pool.Builder()
                                                        .setName("cn.ss.btc.com:3333")
                                                        .setWorker("antminer.1")
                                                        .setStatus(true, false)
                                                        .setPriority(2)
                                                        .setCounts(0, 0, 0)
                                                        .build())
                                        .addPool(
                                                new Pool.Builder()
                                                        .setName("devfee")
                                                        .setWorker("DevFee")
                                                        .setStatus(true, true)
                                                        .setPriority(993)
                                                        .setCounts(166, 0, 0)
                                                        .build())
                                        .addAsic(
                                                new Asic.Builder()
                                                        .setHashRate(new BigDecimal("12795480000000.00"))
                                                        .setBoards(3)
                                                        .setFanInfo(
                                                                new FanInfo.Builder()
                                                                        .setCount(2)
                                                                        .addSpeed(5400)
                                                                        .addSpeed(5880)
                                                                        .setSpeedUnits("RPM")
                                                                        .build())
                                                        .addTemp(65)
                                                        .addTemp(56)
                                                        .addTemp(63)
                                                        .addTemp(80)
                                                        .addTemp(71)
                                                        .addTemp(78)
                                                        .hasErrors(false)
                                                        .addRawStats(
                                                                ImmutableMap.of(
                                                                        "STATS.0.temp6",
                                                                        new BigDecimal("65"),
                                                                        "STATS.0.temp7",
                                                                        new BigDecimal("56")))
                                                        .setMinerType("Antminer S9 (vnish 3.8.6)")
                                                        .setCompileTime("Fri May  8 15:27:59 CST 2020")
                                                        .build())
                                        .build()
                        },
                        {
                                // Antminer S9 (asicseer)
                                BigDecimal.ONE,
                                ImmutableMap.of(
                                        "{\"command\":\"version\"}",
                                        new RpcHandler(
                                                "{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1600709575,\"Code\":22,\"Msg\":\"BMMiner versions\",\"Description\":\"bmminer 2.4.0-asicseer-1.1.9-f7b7\"}],\"VERSION\":[{\"BMMiner\":\"2.4.0-asicseer-1.1.9-f7b7\",\"Miner\":\"30.0.1.3\",\"CompileTime\":\"Mon Nov 11 17:07:20 CST 2019\",\"Type\":\"Antminer S9\",\"API\":\"3.1\"}],\"id\":1}"),
                                        "{\"command\":\"stats\"}",
                                        new RpcHandler(
                                                "{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1600725796,\"Code\":70,\"Msg\":\"BMMiner stats\",\"Description\":\"bmminer 2.4.0-asicseer-1.1.9-f7b7\"}],\"STATS\":[{\"BMMiner\":\"2.4.0-asicseer-1.1.9-f7b7\",\"Miner\":\"30.0.1.3\",\"CompileTime\":\"Mon Nov 11 17:07:20 CST 2019\",\"Type\":\"Antminer S9\"},{\"STATS\":0,\"ID\":\"BC50\",\"Elapsed\":102,\"Calls\":0,\"Wait\":0.000000,\"Max\":0.000000,\"Min\":99999999.000000,\"GHS 5s\":\"13402.14\",\"GHS av\":12743.34,\"Found Blocks\":0,\"Getworks\":30,\"Accepted\":1,\"Rejected\":0,\"Hardware Errors\":2,\"Utility\":0.59,\"Discarded\":55,\"Stale\":0,\"Get Failures\":0,\"Local Work\":4985,\"Remote Failures\":0,\"Network Blocks\":3,\"Total MH\":1274333711.0000,\"Work Utility\":77864.55,\"Difficulty Accepted\":131072.00000000,\"Difficulty Rejected\":0.00000000,\"Difficulty Stale\":0.00000000,\"Best Share\":141658,\"Device Rejected%\":0.0000,\"Pool Rejected%\":0.0000,\"Pool Stale%\":0.0000,\"Last getwork\":1600725796,\"miner_count\":3,\"frequency\":\"625\",\"fan_num\":2,\"fan1\":0,\"fan2\":0,\"fan3\":0,\"fan4\":0,\"fan5\":2880,\"fan6\":3840,\"fan7\":0,\"fan8\":0,\"temp_num\":3,\"temp1\":0,\"temp2\":0,\"temp3\":0,\"temp4\":0,\"temp5\":0,\"temp6\":57,\"temp7\":49,\"temp8\":56,\"temp9\":0,\"temp10\":0,\"temp11\":0,\"temp12\":0,\"temp13\":0,\"temp14\":0,\"temp15\":0,\"temp16\":0,\"temp2_1\":0,\"temp2_2\":0,\"temp2_3\":0,\"temp2_4\":0,\"temp2_5\":0,\"temp2_6\":87,\"temp2_7\":74,\"temp2_8\":86,\"temp2_9\":0,\"temp2_10\":0,\"temp2_11\":0,\"temp2_12\":0,\"temp2_13\":0,\"temp2_14\":0,\"temp2_15\":0,\"temp2_16\":0,\"temp3_1\":0,\"temp3_2\":0,\"temp3_3\":0,\"temp3_4\":0,\"temp3_5\":0,\"temp3_6\":0,\"temp3_7\":0,\"temp3_8\":0,\"temp3_9\":0,\"temp3_10\":0,\"temp3_11\":0,\"temp3_12\":0,\"temp3_13\":0,\"temp3_14\":0,\"temp3_15\":0,\"temp3_16\":0,\"freq_avg1\":0.00,\"freq_avg2\":0.00,\"freq_avg3\":0.00,\"freq_avg4\":0.00,\"freq_avg5\":0.00,\"freq_avg6\":625.00,\"freq_avg7\":625.00,\"freq_avg8\":625.00,\"freq_avg9\":0.00,\"freq_avg10\":0.00,\"freq_avg11\":0.00,\"freq_avg12\":0.00,\"freq_avg13\":0.00,\"freq_avg14\":0.00,\"freq_avg15\":0.00,\"freq_avg16\":0.00,\"voltage6\":880,\"voltage7\":880,\"voltage8\":880,\"freqs6\":\"625,625,625,625,625,625,625,625,625,625,625,625,625,625,625,625,625,625,625,625,625,625,625,625,625,625,625,625,625,625,625,625,625,625,625,625,625,625,625,625,625,625,625,625,625,625,625,625,625,625,625,625,625,625,625,625,625,625,625,625,625,625,625\",\"freqs7\":\"625,625,625,625,625,625,625,625,625,625,625,625,625,625,625,625,625,625,625,625,625,625,625,625,625,625,625,625,625,625,625,625,625,625,625,625,625,625,625,625,625,625,625,625,625,625,625,625,625,625,625,625,625,625,625,625,625,625,625,625,625,625,625\",\"freqs8\":\"625,625,625,625,625,625,625,625,625,625,625,625,625,625,625,625,625,625,625,625,625,625,625,625,625,625,625,625,625,625,625,625,625,625,625,625,625,625,625,625,625,625,625,625,625,625,625,625,625,625,625,625,625,625,625,625,625,625,625,625,625,625,625\",\"chip_hashrate6\":\"65.33,71.34,72.04,68.80,69.19,72.93,72.21,68.52,72.76,69.36,67.51,71.67,70.48,73.03,76.87,69.71,72.56,69.99,70.11,70.70,73.94,68.42,69.19,71.05,72.02,69.84,76.72,69.94,66.69,67.76,70.78,70.98,68.22,70.13,67.85,77.33,72.76,74.84,71.30,70.23,73.32,75.16,72.43,73.08,70.43,68.00,69.34,73.55,72.85,70.98,73.95,70.50,68.84,69.74,67.63,73.97,64.29,72.80,77.36,73.20,68.85,64.96,67.29\",\"chip_hashrate7\":\"78.00,70.28,70.82,74.64,69.59,68.10,65.14,73.08,70.48,71.10,68.30,70.63,71.74,71.99,66.69,70.82,70.14,67.98,70.41,71.40,70.40,70.04,69.84,69.66,71.39,71.54,65.60,67.33,78.69,66.86,70.53,72.16,66.05,65.16,63.60,67.34,73.67,66.76,75.55,71.45,73.53,70.65,72.73,69.58,68.23,70.46,70.45,68.85,75.01,71.07,68.05,74.00,69.67,68.72,73.18,68.64,68.89,68.05,71.35,74.02,72.28,71.10,67.85\",\"chip_hashrate8\":\"72.02,72.29,70.53,69.52,70.65,67.01,72.34,68.42,69.52,64.88,68.65,72.59,72.69,75.36,75.38,71.62,73.92,67.01,74.62,77.69,70.48,77.56,66.94,70.23,73.47,75.36,70.50,70.63,71.72,73.43,72.04,70.25,69.19,65.45,66.96,76.62,70.33,71.55,74.41,73.55,73.70,75.93,72.56,75.26,67.83,68.55,71.70,76.08,65.98,67.16,68.79,72.44,74.10,73.11,71.81,67.96,72.88,71.82,71.97,66.81,71.84,77.56,69.84\",\"total_rateideal\":13466.25,\"total_freqavg\":625.00,\"total_acn\":189,\"total_rate\":13402.14,\"chain_rateideal1\":0.00,\"chain_rateideal2\":0.00,\"chain_rateideal3\":0.00,\"chain_rateideal4\":0.00,\"chain_rateideal5\":0.00,\"chain_rateideal6\":4488.75,\"chain_rateideal7\":4488.75,\"chain_rateideal8\":4488.75,\"chain_rateideal9\":0.00,\"chain_rateideal10\":0.00,\"chain_rateideal11\":0.00,\"chain_rateideal12\":0.00,\"chain_rateideal13\":0.00,\"chain_rateideal14\":0.00,\"chain_rateideal15\":0.00,\"chain_rateideal16\":0.00,\"temp_max\":87,\"Device Hardware%\":0.0015,\"no_matching_work\":2,\"chain_acn1\":0,\"chain_acn2\":0,\"chain_acn3\":0,\"chain_acn4\":0,\"chain_acn5\":0,\"chain_acn6\":63,\"chain_acn7\":63,\"chain_acn8\":63,\"chain_acn9\":0,\"chain_acn10\":0,\"chain_acn11\":0,\"chain_acn12\":0,\"chain_acn13\":0,\"chain_acn14\":0,\"chain_acn15\":0,\"chain_acn16\":0,\"chain_acs1\":\"\",\"chain_acs2\":\"\",\"chain_acs3\":\"\",\"chain_acs4\":\"\",\"chain_acs5\":\"\",\"chain_acs6\":\" oooooooo oooooooo oooooooo oooooooo oooooooo oooooooo oooooooo ooooooo\",\"chain_acs7\":\" oooooooo oooooooo oooooooo oooooooo oooooooo oooooooo oooooooo ooooooo\",\"chain_acs8\":\" oooooooo oooooooo oooooooo oooooooo oooooooo oooooooo oooooooo ooooooo\",\"chain_acs9\":\"\",\"chain_acs10\":\"\",\"chain_acs11\":\"\",\"chain_acs12\":\"\",\"chain_acs13\":\"\",\"chain_acs14\":\"\",\"chain_acs15\":\"\",\"chain_acs16\":\"\",\"chain_hw1\":0,\"chain_hw2\":0,\"chain_hw3\":0,\"chain_hw4\":0,\"chain_hw5\":0,\"chain_hw6\":0,\"chain_hw7\":2,\"chain_hw8\":0,\"chain_hw9\":0,\"chain_hw10\":0,\"chain_hw11\":0,\"chain_hw12\":0,\"chain_hw13\":0,\"chain_hw14\":0,\"chain_hw15\":0,\"chain_hw16\":0,\"chain_rate1\":\"0.00000\",\"chain_rate2\":\"0.00000\",\"chain_rate3\":\"0.00000\",\"chain_rate4\":\"0.00000\",\"chain_rate5\":\"0.00000\",\"chain_rate6\":\"4467.65\",\"chain_rate7\":\"4431.33\",\"chain_rate8\":\"4503.15\",\"chain_rate9\":\"0.00000\",\"chain_rate10\":\"0.00000\",\"chain_rate11\":\"0.00000\",\"chain_rate12\":\"0.00000\",\"chain_rate13\":\"0.00000\",\"chain_rate14\":\"0.00000\",\"chain_rate15\":\"0.00000\",\"chain_rate16\":\"0.00000\",\"chain_xtime6\":\"{}\",\"chain_xtime7\":\"{}\",\"chain_xtime8\":\"{}\",\"chain_offside_6\":\"0\",\"chain_offside_7\":\"0\",\"chain_offside_8\":\"0\",\"chain_opencore_6\":\"0\",\"chain_opencore_7\":\"0\",\"chain_opencore_8\":\"0\",\"Rolling Reject%\":0.0000,\"temp1_ok\":0,\"temp2_ok\":0,\"temp3_ok\":0,\"temp4_ok\":0,\"temp5_ok\":0,\"temp6_ok\":1,\"temp7_ok\":1,\"temp8_ok\":1,\"temp9_ok\":0,\"temp10_ok\":0,\"temp11_ok\":0,\"temp12_ok\":0,\"temp13_ok\":0,\"temp14_ok\":0,\"temp15_ok\":0,\"temp16_ok\":0,\"temp2_1_ok\":0,\"temp2_2_ok\":0,\"temp2_3_ok\":0,\"temp2_4_ok\":0,\"temp2_5_ok\":0,\"temp2_6_ok\":1,\"temp2_7_ok\":1,\"temp2_8_ok\":1,\"temp2_9_ok\":0,\"temp2_10_ok\":0,\"temp2_11_ok\":0,\"temp2_12_ok\":0,\"temp2_13_ok\":0,\"temp2_14_ok\":0,\"temp2_15_ok\":0,\"temp2_16_ok\":0,\"temp3_1_ok\":0,\"temp3_2_ok\":0,\"temp3_3_ok\":0,\"temp3_4_ok\":0,\"temp3_5_ok\":0,\"temp3_6_ok\":0,\"temp3_7_ok\":0,\"temp3_8_ok\":0,\"temp3_9_ok\":0,\"temp3_10_ok\":0,\"temp3_11_ok\":0,\"temp3_12_ok\":0,\"temp3_13_ok\":0,\"temp3_14_ok\":0,\"temp3_15_ok\":0,\"temp3_16_ok\":0,\"miner_version\":\"30.0.1.3\",\"asicid\":\"dcc9a2\",\"miner_id\":\"dcc9a2\",\"hostname\":\"dcc9a2\",\"ip\":\"192.168.1.195\",\"location\":\"\",\"worker\":\"dcc9a2\"}],\"id\":1}"),
                                        "{\"command\":\"pools\"}",
                                        new RpcHandler(
                                                "{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1600709581,\"Code\":7,\"Msg\":\"3 Pool(s)\",\"Description\":\"bmminer 2.4.0-asicseer-1.1.9-f7b7\"}],\"POOLS\":[{\"POOL\":0,\"URL\":\"stratum+tcp://btc-us.f2pool.com:3333\",\"Status\":\"Alive\",\"Priority\":3,\"Quota\":1,\"Long Poll\":\"N\",\"Getworks\":3,\"Accepted\":1,\"Rejected\":0,\"Discarded\":9,\"Stale\":0,\"Get Failures\":0,\"Remote Failures\":0,\"User\":\"asicseer.dcc9a2\",\"Last Share Time\":\"0:00:17\",\"Diff\":\"65.5K\",\"Diff1 Shares\":0,\"Proxy Type\":\"\",\"Proxy\":\"\",\"Difficulty Accepted\":65536.00000000,\"Difficulty Rejected\":0.00000000,\"Difficulty Stale\":0.00000000,\"Last Share Difficulty\":65536.00000000,\"Has Stratum\":true,\"Asicboost\":true,\"Stratum Active\":false,\"Stratum URL\":\"\",\"Has GBT\":false,\"Best Share\":132090,\"Pool Rejected%\":0.0000,\"Pool Stale%\":0.0000},{\"POOL\":1,\"URL\":\"stratum+tcp://btc-us.f2pool.com:1314\",\"Status\":\"Alive\",\"Priority\":4,\"Quota\":1,\"Long Poll\":\"N\",\"Getworks\":2,\"Accepted\":0,\"Rejected\":0,\"Discarded\":0,\"Stale\":0,\"Get Failures\":0,\"Remote Failures\":0,\"User\":\"asicseer.dcc9a2\",\"Last Share Time\":\"0\",\"Diff\":\"65.5K\",\"Diff1 Shares\":0,\"Proxy Type\":\"\",\"Proxy\":\"\",\"Difficulty Accepted\":0.00000000,\"Difficulty Rejected\":0.00000000,\"Difficulty Stale\":0.00000000,\"Last Share Difficulty\":0.00000000,\"Has Stratum\":true,\"Asicboost\":true,\"Stratum Active\":false,\"Stratum URL\":\"\",\"Has GBT\":false,\"Best Share\":0,\"Pool Rejected%\":0.0000,\"Pool Stale%\":0.0000},{\"POOL\":2,\"URL\":\"stratum+tcp://btc.viabtc.com:3333\",\"Status\":\"Alive\",\"Priority\":5,\"Quota\":1,\"Long Poll\":\"N\",\"Getworks\":1,\"Accepted\":0,\"Rejected\":0,\"Discarded\":0,\"Stale\":0,\"Get Failures\":0,\"Remote Failures\":0,\"User\":\"asicseer.dcc9a2\",\"Last Share Time\":\"0\",\"Diff\":\"4.1K\",\"Diff1 Shares\":0,\"Proxy Type\":\"\",\"Proxy\":\"\",\"Difficulty Accepted\":0.00000000,\"Difficulty Rejected\":0.00000000,\"Difficulty Stale\":0.00000000,\"Last Share Difficulty\":0.00000000,\"Has Stratum\":true,\"Asicboost\":true,\"Stratum Active\":false,\"Stratum URL\":\"\",\"Has GBT\":false,\"Best Share\":0,\"Pool Rejected%\":0.0000,\"Pool Stale%\":0.0000}],\"id\":1}")),
                                Arrays.asList(
                                        "STATS.1.temp6",
                                        "STATS.1.temp7"),
                                new MinerStats.Builder()
                                        .setApiIp("127.0.0.1")
                                        .setApiPort(4028)
                                        .addPool(
                                                new Pool.Builder()
                                                        .setName("btc-us.f2pool.com:3333")
                                                        .setWorker("asicseer.dcc9a2")
                                                        .setStatus(true, true)
                                                        .setPriority(3)
                                                        .setCounts(1, 0, 0)
                                                        .build())
                                        .addPool(
                                                new Pool.Builder()
                                                        .setName("btc-us.f2pool.com:1314")
                                                        .setWorker("asicseer.dcc9a2")
                                                        .setStatus(true, true)
                                                        .setPriority(4)
                                                        .setCounts(0, 0, 0)
                                                        .build())
                                        .addPool(
                                                new Pool.Builder()
                                                        .setName("btc.viabtc.com:3333")
                                                        .setWorker("asicseer.dcc9a2")
                                                        .setStatus(true, true)
                                                        .setPriority(5)
                                                        .setCounts(0, 0, 0)
                                                        .build())
                                        .addAsic(
                                                new Asic.Builder()
                                                        .setHashRate(new BigDecimal("13402140000000.00"))
                                                        .setBoards(3)
                                                        .setFanInfo(
                                                                new FanInfo.Builder()
                                                                        .setCount(2)
                                                                        .addSpeed(2880)
                                                                        .addSpeed(3840)
                                                                        .setSpeedUnits("RPM")
                                                                        .build())
                                                        .addTemp(57)
                                                        .addTemp(49)
                                                        .addTemp(56)
                                                        .addTemp(87)
                                                        .addTemp(74)
                                                        .addTemp(86)
                                                        .hasErrors(false)
                                                        .addRawStats(
                                                                ImmutableMap.of(
                                                                        "STATS.1.temp6",
                                                                        new BigDecimal("57"),
                                                                        "STATS.1.temp7",
                                                                        new BigDecimal("49")))
                                                        .setMinerType("Antminer S9")
                                                        .setCompileTime("Mon Nov 11 17:07:20 CST 2019")
                                                        .build())
                                        .build()
                        },
                        {
                                // Antminer S9 (bad temps)
                                BigDecimal.ONE,
                                ImmutableMap.of(
                                        "{\"command\":\"version\"}",
                                        new RpcHandler(
                                                "{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1605578750,\"Code\":22,\"Msg\":\"BMMiner versions\",\"Description\":\"bmminer 1.0.0\"}],\"VERSION\":[{\"BMMiner\":\"2.0.0\",\"API\":\"3.1\",\"Miner\":\"30.0.1.3\",\"CompileTime\":\"Wed Jul 31 16:18:27 CST 2019\",\"Type\":\"Antminer S9\\nV1.3.58\",\"VER\":\"200215\"}],\"id\":1}"),
                                        "{\"command\":\"stats\"}",
                                        new RpcHandler(
                                                "{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1605578750,\"Code\":70,\"Msg\":\"BMMiner stats\",\"Description\":\"bmminer 1.0.0\"}],\"STATS\":[{\"BMMiner\":\"2.0.0\",\"Miner\":\"30.0.1.3\",\"CompileTime\":\"Wed Jul 31 16:18:27 CST 2019\",\"Type\":\"Antminer S9\\nV1.3.58\"}{\"STATS\":0,\"ID\":\"BC50\",\"Elapsed\":293628,\"Calls\":0,\"Wait\":0.000000,\"Max\":0.000000,\"Min\":99999999.000000,\"GHS 5s\":\"13883.81\",\"GHS av\":13890.36,\"miner_count\":3,\"frequency\":645,\"fan_num\":2,\"fan1\":0,\"fan2\":0,\"fan3\":0,\"fan4\":0,\"fan5\":3720,\"fan6\":2400,\"fan7\":0,\"fan8\":0,\"temp_num\":3,\"temp1\":0,\"temp2\":0,\"temp3\":0,\"temp4\":0,\"temp5\":0,\"temp6\":75,\"temp7\":0,\"temp8\":73,\"temp9\":0,\"temp10\":0,\"temp11\":0,\"temp12\":0,\"temp13\":0,\"temp14\":0,\"temp15\":0,\"temp16\":0,\"temp2_1\":0,\"temp2_2\":0,\"temp2_3\":0,\"temp2_4\":0,\"temp2_5\":0,\"temp2_6\":62,\"temp2_7\":-83,\"temp2_8\":62,\"temp2_9\":0,\"temp2_10\":0,\"temp2_11\":0,\"temp2_12\":0,\"temp2_13\":0,\"temp2_14\":0,\"temp2_15\":0,\"temp2_16\":0,\"temp3_1\":0,\"temp3_2\":0,\"temp3_3\":0,\"temp3_4\":0,\"temp3_5\":0,\"temp3_6\":0,\"temp3_7\":0,\"temp3_8\":0,\"temp3_9\":0,\"temp3_10\":0,\"temp3_11\":0,\"temp3_12\":0,\"temp3_13\":0,\"temp3_14\":0,\"temp3_15\":0,\"temp3_16\":0,\"freq_avg1\":0.00,\"freq_avg2\":0.00,\"freq_avg3\":0.00,\"freq_avg4\":0.00,\"freq_avg5\":0.00,\"freq_avg6\":649.10,\"freq_avg7\":650.40,\"freq_avg8\":647.00,\"freq_avg9\":0.00,\"freq_avg10\":0.00,\"freq_avg11\":0.00,\"freq_avg12\":0.00,\"freq_avg13\":0.00,\"freq_avg14\":0.00,\"freq_avg15\":0.00,\"freq_avg16\":0.00,\"total_rateideal\":13862.52,\"total_freqavg\":645.90,\"total_acn\":189,\"total_rate\":13883.81,\"chain_rateideal1\":0.00,\"chain_rateideal2\":0.00,\"chain_rateideal3\":0.00,\"chain_rateideal4\":0.00,\"chain_rateideal5\":0.00,\"chain_rateideal6\":4620.34,\"chain_rateideal7\":4620.87,\"chain_rateideal8\":4621.31,\"chain_rateideal9\":0.00,\"chain_rateideal10\":0.00,\"chain_rateideal11\":0.00,\"chain_rateideal12\":0.00,\"chain_rateideal13\":0.00,\"chain_rateideal14\":0.00,\"chain_rateideal15\":0.00,\"chain_rateideal16\":0.00,\"temp_max\":62,\"Device Hardware%\":0.0184,\"no_matching_work\":178540,\"chain_acn1\":0,\"chain_acn2\":0,\"chain_acn3\":0,\"chain_acn4\":0,\"chain_acn5\":0,\"chain_acn6\":63,\"chain_acn7\":63,\"chain_acn8\":63,\"chain_acn9\":0,\"chain_acn10\":0,\"chain_acn11\":0,\"chain_acn12\":0,\"chain_acn13\":0,\"chain_acn14\":0,\"chain_acn15\":0,\"chain_acn16\":0,\"chain_acs1\":\"\",\"chain_acs2\":\"\",\"chain_acs3\":\"\",\"chain_acs4\":\"\",\"chain_acs5\":\"\",\"chain_acs6\":\" oooooooo oooooooo oooooooo oooooooo oooooooo oooooooo oooooooo ooooooo\",\"chain_acs7\":\" oooooooo oooooooo oooooooo oooooooo oooooooo oooooooo oooooooo ooooooo\",\"chain_acs8\":\" oooooooo oooooooo oooooooo oooooooo oooooooo oooooooo oooooooo ooooooo\",\"chain_acs9\":\"\",\"chain_acs10\":\"\",\"chain_acs11\":\"\",\"chain_acs12\":\"\",\"chain_acs13\":\"\",\"chain_acs14\":\"\",\"chain_acs15\":\"\",\"chain_acs16\":\"\",\"chain_hw1\":0,\"chain_hw2\":0,\"chain_hw3\":0,\"chain_hw4\":0,\"chain_hw5\":0,\"chain_hw6\":3085,\"chain_hw7\":289,\"chain_hw8\":175165,\"chain_hw9\":0,\"chain_hw10\":0,\"chain_hw11\":0,\"chain_hw12\":0,\"chain_hw13\":0,\"chain_hw14\":0,\"chain_hw15\":0,\"chain_hw16\":0,\"chain_rate1\":\"\",\"chain_rate2\":\"\",\"chain_rate3\":\"\",\"chain_rate4\":\"\",\"chain_rate5\":\"\",\"chain_rate6\":\"4618.85\",\"chain_rate7\":\"4624.55\",\"chain_rate8\":\"4640.41\",\"chain_rate9\":\"\",\"chain_rate10\":\"\",\"chain_rate11\":\"\",\"chain_rate12\":\"\",\"chain_rate13\":\"\",\"chain_rate14\":\"\",\"chain_rate15\":\"\",\"chain_rate16\":\"\",\"chain_xtime6\":\"{X0=58,X1=59,X2=58,X3=60,X4=61,X5=58,X6=58,X7=58,X8=60,X9=59,X10=60,X11=55,X12=60,X13=58,X14=58,X15=58,X16=57,X17=59,X18=57,X19=59,X20=58,X21=59,X22=56,X23=57,X24=59,X25=59,X26=58,X27=60,X28=57,X29=55,X30=56,X31=59,X32=59,X33=60,X34=59,X35=58,X36=56,X37=59,X38=60,X39=56,X40=58,X41=58,X42=59,X43=58,X44=58,X45=58,X46=59,X47=58,X48=59,X49=59,X50=57,X51=58,X52=57,X53=58,X54=60,X55=59,X56=59,X57=57,X58=58,X59=59,X60=56,X61=59,X62=57}\",\"chain_xtime7\":\"{X0=58,X1=59,X2=57,X3=59,X4=58,X5=57,X6=66,X7=59,X8=58,X9=58,X10=58,X11=60,X12=58,X13=59,X14=58,X15=58,X16=57,X17=59,X18=59,X19=59,X20=57,X21=58,X22=56,X23=59,X24=58,X25=58,X26=57,X27=57,X28=59,X29=59,X30=58,X31=58,X32=59,X33=56,X34=58,X35=58,X36=57,X37=58,X38=59,X39=58,X40=57,X41=59,X42=59,X43=58,X44=56,X45=59,X46=60,X47=60,X48=56,X49=59,X50=57,X51=60,X52=58,X53=57,X54=60,X55=58,X56=58,X57=57,X58=58,X59=59,X60=59,X61=58,X62=58}\",\"chain_xtime8\":\"{X0=60,X1=57,X2=57,X3=58,X4=57,X5=59,X6=60,X7=58,X8=58,X9=60,X10=59,X11=58,X12=58,X13=60,X14=57,X15=60,X16=59,X17=56,X18=57,X19=59,X20=58,X21=60,X22=58,X23=58,X24=58,X25=60,X26=57,X27=59,X28=58,X29=56,X30=58,X31=58,X32=57,X33=59,X34=58,X35=60,X36=59,X37=56,X38=58,X39=59,X40=58,X41=58,X42=59,X43=58,X44=58,X45=56,X46=58,X47=58,X48=61,X49=55,X50=59,X51=59,X52=57,X53=58,X54=60,X55=57,X56=59,X57=57,X58=58,X59=57,X60=56,X61=58,X62=58}\",\"chain_offside_6\":\"0\",\"chain_offside_7\":\"0\",\"chain_offside_8\":\"0\",\"chain_opencore_6\":\"0\",\"chain_opencore_7\":\"0\",\"chain_opencore_8\":\"0\",\"miner_version\":\"30.0.1.3\"}],\"id\":1}"),
                                        "{\"command\":\"pools\"}",
                                        new RpcHandler(
                                                "{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1605578750,\"Code\":7,\"Msg\":\"3 Pool(s)\",\"Description\":\"bmminer 1.0.0\"}],\"POOLS\":[{\"POOL\":0,\"URL\":\"stratum+tcp://ss.antpool.com:3333\",\"Status\":\"Alive\",\"Priority\":0,\"Quota\":1,\"Long Poll\":\"N\",\"Getworks\":6639,\"Accepted\":22673,\"Rejected\":8,\"Discarded\":136532,\"Stale\":0,\"Get Failures\":0,\"Remote Failures\":0,\"User\":\"xxx\",\"Last Share Time\":\"0:00:05\",\"Diff\":\"32.8K\",\"Diff1 Shares\":0,\"Proxy Type\":\"\",\"Proxy\":\"\",\"Difficulty Accepted\":859832320.00000000,\"Difficulty Rejected\":393216.00000000,\"Difficulty Stale\":0.00000000,\"Last Share Difficulty\":32768.00000000,\"Has Stratum\":true,\"Stratum Active\":true,\"Stratum URL\":\"ss.antpool.com\",\"Has GBT\":false,\"Best Share\":970349963,\"Pool Rejected%\":0.0457,\"Pool Stale%\":0.0000},{\"POOL\":1,\"URL\":\"stratum+tcp://ss.antpool.com:443\",\"Status\":\"Alive\",\"Priority\":1,\"Quota\":1,\"Long Poll\":\"N\",\"Getworks\":1761,\"Accepted\":0,\"Rejected\":0,\"Discarded\":0,\"Stale\":0,\"Get Failures\":0,\"Remote Failures\":0,\"User\":\"xxx\",\"Last Share Time\":\"0\",\"Diff\":\"32.8K\",\"Diff1 Shares\":0,\"Proxy Type\":\"\",\"Proxy\":\"\",\"Difficulty Accepted\":0.00000000,\"Difficulty Rejected\":0.00000000,\"Difficulty Stale\":0.00000000,\"Last Share Difficulty\":0.00000000,\"Has Stratum\":true,\"Stratum Active\":false,\"Stratum URL\":\"\",\"Has GBT\":false,\"Best Share\":0,\"Pool Rejected%\":0.0000,\"Pool Stale%\":0.0000},{\"POOL\":2,\"URL\":\"\",\"Status\":\"Dead\",\"Priority\":2,\"Quota\":1,\"Long Poll\":\"N\",\"Getworks\":0,\"Accepted\":0,\"Rejected\":0,\"Discarded\":0,\"Stale\":0,\"Get Failures\":0,\"Remote Failures\":0,\"User\":\"\",\"Last Share Time\":\"0\",\"Diff\":\"\",\"Diff1 Shares\":0,\"Proxy Type\":\"\",\"Proxy\":\"\",\"Difficulty Accepted\":0.00000000,\"Difficulty Rejected\":0.00000000,\"Difficulty Stale\":0.00000000,\"Last Share Difficulty\":0.00000000,\"Has Stratum\":false,\"Stratum Active\":false,\"Stratum URL\":\"\",\"Has GBT\":false,\"Best Share\":0,\"Pool Rejected%\":0.0000,\"Pool Stale%\":0.0000}],\"id\":1}")),
                                Collections.emptyList(),
                                new MinerStats.Builder()
                                        .setApiIp("127.0.0.1")
                                        .setApiPort(4028)
                                        .addPool(
                                                new Pool.Builder()
                                                        .setName("ss.antpool.com:3333")
                                                        .setWorker("xxx")
                                                        .setStatus(true, true)
                                                        .setPriority(0)
                                                        .setCounts(22673, 8, 0)
                                                        .build())
                                        .addPool(
                                                new Pool.Builder()
                                                        .setName("ss.antpool.com:443")
                                                        .setWorker("xxx")
                                                        .setStatus(true, true)
                                                        .setPriority(1)
                                                        .setCounts(0, 0, 0)
                                                        .build())
                                        .addAsic(
                                                new Asic.Builder()
                                                        .setHashRate(new BigDecimal("13883810000000.00"))
                                                        .setBoards(3)
                                                        .setFanInfo(
                                                                new FanInfo.Builder()
                                                                        .setCount(2)
                                                                        .addSpeed(3720)
                                                                        .addSpeed(2400)
                                                                        .setSpeedUnits("RPM")
                                                                        .build())
                                                        .addTemp(75)
                                                        .addTemp(73)
                                                        .addTemp(62)
                                                        .addTemp(-83)
                                                        .addTemp(62)
                                                        .hasErrors(false)
                                                        .setMinerType("Antminer S9\n" +
                                                                "V1.3.58")
                                                        .setCompileTime("Wed Jul 31 16:18:27 CST 2019")
                                                        .build())
                                        .build()
                        },
                        {
                                // Antminer S9 (mskminer)
                                BigDecimal.ONE,
                                ImmutableMap.of(
                                        "{\"command\":\"version\"}",
                                        new RpcHandler(
                                                "{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1605971972,\"Code\":22,\"Msg\":\"BMMiner versions\",\"Description\":\"bmminer 1.0.0\"}],\"VERSION\":[{\"BMMiner\":\"2.0.0 rwglr\",\"API\":\"3.1\",\"Miner\":\"30.0.1.3\",\"CompileTime\":\"Mon Nov 25 09:56:24 PST 2019\",\"Type\":\"Antminer S9 Pro (v5.1) \"}],\"id\":1}"),
                                        "{\"command\":\"stats\"}",
                                        new RpcHandler(
                                                "{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1605972626,\"Code\":70,\"Msg\":\"BMMiner stats\",\"Description\":\"bmminer 1.0.0\"}],\"STATS\":[{\"BMMiner\":\"2.0.0 rwglr\",\"Miner\":\"30.0.1.3\",\"CompileTime\":\"Mon Nov 25 09:56:24 PST 2019\",\"Type\":\"Antminer S9 Pro (v5.1) \"}{\"STATS\":0,\"ID\":\"BC50\",\"Elapsed\":16,\"Calls\":0,\"Wait\":0.000000,\"Max\":0.000000,\"Min\":99999999.000000,\"GHS 5s\":\"9039.731\",\"GHS av\":12461.13,\"miner_count\":3,\"frequency\":\"500\",\"fan_num\":1,\"fan1\":0,\"fan2\":0,\"fan3\":0,\"fan4\":0,\"fan5\":0,\"fan6\":5280,\"fan7\":0,\"fan8\":0,\"temp_num\":3,\"temp1\":0,\"temp2\":0,\"temp3\":0,\"temp4\":0,\"temp5\":0,\"temp6\":37,\"temp7\":30,\"temp8\":33,\"temp9\":0,\"temp10\":0,\"temp11\":0,\"temp12\":0,\"temp13\":0,\"temp14\":0,\"temp15\":0,\"temp16\":0,\"temp2_1\":0,\"temp2_2\":0,\"temp2_3\":0,\"temp2_4\":0,\"temp2_5\":0,\"temp2_6\":52,\"temp2_7\":45,\"temp2_8\":48,\"temp2_9\":0,\"temp2_10\":0,\"temp2_11\":0,\"temp2_12\":0,\"temp2_13\":0,\"temp2_14\":0,\"temp2_15\":0,\"temp2_16\":0,\"temp3_1\":0,\"temp3_2\":0,\"temp3_3\":0,\"temp3_4\":0,\"temp3_5\":0,\"temp3_6\":0,\"temp3_7\":0,\"temp3_8\":0,\"temp3_9\":0,\"temp3_10\":0,\"temp3_11\":0,\"temp3_12\":0,\"temp3_13\":0,\"temp3_14\":0,\"temp3_15\":0,\"temp3_16\":0,\"freq_avg1\":0.00,\"freq_avg2\":0.00,\"freq_avg3\":0.00,\"freq_avg4\":0.00,\"freq_avg5\":0.00,\"freq_avg6\":500.00,\"freq_avg7\":500.00,\"freq_avg8\":500.00,\"freq_avg9\":0.00,\"freq_avg10\":0.00,\"freq_avg11\":0.00,\"freq_avg12\":0.00,\"freq_avg13\":0.00,\"freq_avg14\":0.00,\"freq_avg15\":0.00,\"freq_avg16\":0.00,\"total_rateideal\":10773.00,\"total_freqavg\":500.00,\"total_acn\":189,\"total_rate\":9039.73,\"chain_rateideal1\":0.00,\"chain_rateideal2\":0.00,\"chain_rateideal3\":0.00,\"chain_rateideal4\":0.00,\"chain_rateideal5\":0.00,\"chain_rateideal6\":3591.00,\"chain_rateideal7\":3591.00,\"chain_rateideal8\":3591.00,\"chain_rateideal9\":0.00,\"chain_rateideal10\":0.00,\"chain_rateideal11\":0.00,\"chain_rateideal12\":0.00,\"chain_rateideal13\":0.00,\"chain_rateideal14\":0.00,\"chain_rateideal15\":0.00,\"chain_rateideal16\":0.00,\"temp_max\":48,\"Device Hardware%\":0.0000,\"no_matching_work\":0,\"chain_acn1\":0,\"chain_acn2\":0,\"chain_acn3\":0,\"chain_acn4\":0,\"chain_acn5\":0,\"chain_acn6\":63,\"chain_acn7\":63,\"chain_acn8\":63,\"chain_acn9\":0,\"chain_acn10\":0,\"chain_acn11\":0,\"chain_acn12\":0,\"chain_acn13\":0,\"chain_acn14\":0,\"chain_acn15\":0,\"chain_acn16\":0,\"chain_acs1\":\"\",\"chain_acs2\":\"\",\"chain_acs3\":\"\",\"chain_acs4\":\"\",\"chain_acs5\":\"\",\"chain_acs6\":\" oooooooo oooooooo oooooooo oooooooo oooooooo oooooooo oooooooo ooooooo\",\"chain_acs7\":\" oooooooo oooooooo oooooooo oooooooo oooooooo oooooooo oooooooo ooooooo\",\"chain_acs8\":\" oooooooo oooooooo oooooooo oooooooo oooooooo oooooooo oooooooo ooooooo\",\"chain_acs9\":\"\",\"chain_acs10\":\"\",\"chain_acs11\":\"\",\"chain_acs12\":\"\",\"chain_acs13\":\"\",\"chain_acs14\":\"\",\"chain_acs15\":\"\",\"chain_acs16\":\"\",\"chain_hw1\":0,\"chain_hw2\":0,\"chain_hw3\":0,\"chain_hw4\":0,\"chain_hw5\":0,\"chain_hw6\":0,\"chain_hw7\":0,\"chain_hw8\":0,\"chain_hw9\":0,\"chain_hw10\":0,\"chain_hw11\":0,\"chain_hw12\":0,\"chain_hw13\":0,\"chain_hw14\":0,\"chain_hw15\":0,\"chain_hw16\":0,\"chain_rate1\":\"\",\"chain_rate2\":\"\",\"chain_rate3\":\"\",\"chain_rate4\":\"\",\"chain_rate5\":\"\",\"chain_rate6\":\"2856.42\",\"chain_rate7\":\"3054.06\",\"chain_rate8\":\"3129.25\",\"chain_rate9\":\"\",\"chain_rate10\":\"\",\"chain_rate11\":\"\",\"chain_rate12\":\"\",\"chain_rate13\":\"\",\"chain_rate14\":\"\",\"chain_rate15\":\"\",\"chain_rate16\":\"\",\"chain_xtime6\":\"{}\",\"chain_xtime7\":\"{}\",\"chain_xtime8\":\"{}\",\"chain_offside_6\":\"0\",\"chain_offside_7\":\"0\",\"chain_offside_8\":\"0\",\"chain_opencore_6\":\"0\",\"chain_opencore_7\":\"0\",\"chain_opencore_8\":\"0\",\"miner_version\":\"30.0.1.3\",\"chain_power1\":0.00,\"chain_power2\":0.00,\"chain_power3\":0.00,\"chain_power4\":0.00,\"chain_power5\":0.00,\"chain_power6\":322.50,\"chain_power7\":322.50,\"chain_power8\":322.50,\"chain_power9\":0.00,\"chain_power10\":0.00,\"chain_power11\":0.00,\"chain_power12\":0.00,\"chain_power13\":0.00,\"chain_power14\":0.00,\"chain_power15\":0.00,\"chain_power16\":0.00,\"chain_power\":\"967.50 (AB)\"}],\"id\":1}"),
                                        "{\"command\":\"pools\"}",
                                        new RpcHandler(
                                                "{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1605972166,\"Code\":7,\"Msg\":\"4 Pool(s)\",\"Description\":\"bmminer 1.0.0\"}],\"POOLS\":[{\"POOL\":0,\"URL\":\"stratum+tcp://stratum.antpool.com:3333\",\"Status\":\"Alive\",\"Priority\":0,\"Quota\":1,\"Long Poll\":\"N\",\"Getworks\":15,\"Accepted\":0,\"Rejected\":0,\"Discarded\":162,\"Stale\":0,\"Get Failures\":0,\"Remote Failures\":0,\"User\":\"antminer_1\",\"Last Share Time\":\"0\",\"Diff\":\"65.5K\",\"Diff1 Shares\":0,\"Proxy Type\":\"\",\"Proxy\":\"\",\"Difficulty Accepted\":0.00000000,\"Difficulty Rejected\":0.00000000,\"Difficulty Stale\":0.00000000,\"Last Share Difficulty\":0.00000000,\"Has Stratum\":true,\"Stratum Active\":true,\"Stratum URL\":\"stratum.antpool.com\",\"Has GBT\":false,\"Best Share\":0,\"Pool Rejected%\":0.0000,\"Pool Stale%\":0.0000},{\"POOL\":1,\"URL\":\"stratum+tcp://stratum.antpool.com:443\",\"Status\":\"Alive\",\"Priority\":1,\"Quota\":1,\"Long Poll\":\"N\",\"Getworks\":1,\"Accepted\":0,\"Rejected\":0,\"Discarded\":0,\"Stale\":0,\"Get Failures\":0,\"Remote Failures\":0,\"User\":\"antminer_1\",\"Last Share Time\":\"0\",\"Diff\":\"\",\"Diff1 Shares\":0,\"Proxy Type\":\"\",\"Proxy\":\"\",\"Difficulty Accepted\":0.00000000,\"Difficulty Rejected\":0.00000000,\"Difficulty Stale\":0.00000000,\"Last Share Difficulty\":0.00000000,\"Has Stratum\":true,\"Stratum Active\":false,\"Stratum URL\":\"\",\"Has GBT\":false,\"Best Share\":0,\"Pool Rejected%\":0.0000,\"Pool Stale%\":0.0000},{\"POOL\":2,\"URL\":\"stratum+tcp://stratum.antpool.com:25\",\"Status\":\"Dead\",\"Priority\":2,\"Quota\":1,\"Long Poll\":\"N\",\"Getworks\":0,\"Accepted\":0,\"Rejected\":0,\"Discarded\":0,\"Stale\":0,\"Get Failures\":0,\"Remote Failures\":0,\"User\":\"antminer_1\",\"Last Share Time\":\"0\",\"Diff\":\"\",\"Diff1 Shares\":0,\"Proxy Type\":\"\",\"Proxy\":\"\",\"Difficulty Accepted\":0.00000000,\"Difficulty Rejected\":0.00000000,\"Difficulty Stale\":0.00000000,\"Last Share Difficulty\":0.00000000,\"Has Stratum\":true,\"Stratum Active\":false,\"Stratum URL\":\"\",\"Has GBT\":false,\"Best Share\":0,\"Pool Rejected%\":0.0000,\"Pool Stale%\":0.0000},{\"POOL\":3,\"URL\":\"*\",\"Status\":\"Alive\",\"Priority\":998,\"Quota\":1,\"Long Poll\":\"N\",\"Getworks\":14,\"Accepted\":0,\"Rejected\":0,\"Discarded\":2,\"Stale\":0,\"Get Failures\":0,\"Remote Failures\":0,\"User\":\"*\",\"Last Share Time\":\"0\",\"Diff\":\"512\",\"Diff1 Shares\":0,\"Proxy Type\":\"\",\"Proxy\":\"\",\"Difficulty Accepted\":0.00000000,\"Difficulty Rejected\":0.00000000,\"Difficulty Stale\":0.00000000,\"Last Share Difficulty\":0.00000000,\"Has Stratum\":true,\"Stratum Active\":true,\"Stratum URL\":\"*\",\"Has GBT\":false,\"Best Share\":0,\"Pool Rejected%\":0.0000,\"Pool Stale%\":0.0000}],\"id\":1}")),
                                Collections.emptyList(),
                                new MinerStats.Builder()
                                        .setApiIp("127.0.0.1")
                                        .setApiPort(4028)
                                        .addPool(
                                                new Pool.Builder()
                                                        .setName("stratum.antpool.com:3333")
                                                        .setWorker("antminer_1")
                                                        .setStatus(true, true)
                                                        .setPriority(0)
                                                        .setCounts(0, 0, 0)
                                                        .build())
                                        .addPool(
                                                new Pool.Builder()
                                                        .setName("stratum.antpool.com:443")
                                                        .setWorker("antminer_1")
                                                        .setStatus(true, true)
                                                        .setPriority(1)
                                                        .setCounts(0, 0, 0)
                                                        .build())
                                        .addPool(
                                                new Pool.Builder()
                                                        .setName("stratum.antpool.com:25")
                                                        .setWorker("antminer_1")
                                                        .setStatus(true, false)
                                                        .setPriority(2)
                                                        .setCounts(0, 0, 0)
                                                        .build())
                                        .addPool(
                                                new Pool.Builder()
                                                        .setName("*")
                                                        .setWorker("*")
                                                        .setStatus(true, true)
                                                        .setPriority(998)
                                                        .setCounts(0, 0, 0)
                                                        .build())
                                        .addAsic(
                                                new Asic.Builder()
                                                        .setHashRate(new BigDecimal("9039731000000.000"))
                                                        .setBoards(3)
                                                        .setFanInfo(
                                                                new FanInfo.Builder()
                                                                        .setCount(1)
                                                                        .addSpeed(5280)
                                                                        .setSpeedUnits("RPM")
                                                                        .build())
                                                        .addTemp(37)
                                                        .addTemp(30)
                                                        .addTemp(33)
                                                        .addTemp(52)
                                                        .addTemp(45)
                                                        .addTemp(48)
                                                        .hasErrors(false)
                                                        .setMinerType("Antminer S9 Pro (v5.1) ")
                                                        .setCompileTime("Mon Nov 25 09:56:24 PST 2019")
                                                        .build())
                                        .build()
                        },
                        {
                                // Antminer T15
                                BigDecimal.ONE,
                                ImmutableMap.of(
                                        "{\"command\":\"version\"}",
                                        new RpcHandler(
                                                "{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1612226597,\"Code\":22,\"Msg\":\"CGMiner versions\",\"Description\":\"cgminer 4.9.0\"}],\"VERSION\":[{\"CGMiner\":\"4.9.0\",\"API\":\"3.1\",\"Miner\":\"27.10.1.3\",\"CompileTime\":\"Fri Dec 13 15:46:16 CST 2019\",\"Type\":\"Antminer T15\"}],\"id\":1}"),
                                        "{\"command\":\"stats\"}",
                                        new RpcHandler(
                                                "{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1612226597,\"Code\":70,\"Msg\":\"CGMiner stats\",\"Description\":\"cgminer 4.9.0\"}],\"STATS\":[{\"BMMiner\":\"4.9.0\",\"Miner\":\"27.10.1.3\",\"CompileTime\":\"Fri Dec 13 15:46:16 CST 2019\",\"Type\":\"Antminer T15\"},{\"STATS\":0,\"ID\":\"BTM_SOC0\",\"Elapsed\":13263,\"Calls\":0,\"Wait\":0.000000,\"Max\":0.000000,\"Min\":99999999.000000,\"GHS 5s\":\"21615.78\",\"GHS av\":22411.07,\"GHS 30m\":22210.70,\"Voltage\":\"\",\"Mode\":\"H\",\"miner_count\":3,\"frequency\":\"\",\"freq1\":\"520\",\"freq2\":\"420\",\"freq3\":\"519\",\"freq4\":\"0\",\"freq5\":\"0\",\"freq6\":\"0\",\"freq7\":\"0\",\"freq8\":\"0\",\"freq9\":\"0\",\"freq10\":\"0\",\"freq11\":\"0\",\"freq12\":\"0\",\"freq13\":\"0\",\"freq14\":\"0\",\"freq15\":\"0\",\"freq16\":\"0\",\"fan_num\":2,\"fan1\":2880,\"fan2\":2880,\"fan3\":0,\"fan4\":0,\"fan5\":0,\"fan6\":0,\"fan7\":0,\"fan8\":0,\"temp_num\":3,\"temp1\":40,\"temp2\":35,\"temp3\":39,\"temp4\":0,\"temp5\":0,\"temp6\":0,\"temp7\":0,\"temp8\":0,\"temp9\":0,\"temp10\":0,\"temp11\":0,\"temp12\":0,\"temp13\":0,\"temp14\":0,\"temp15\":0,\"temp16\":0,\"temp2_1\":29,\"temp2_2\":29,\"temp2_3\":28,\"temp2_4\":0,\"temp2_5\":0,\"temp2_6\":0,\"temp2_7\":0,\"temp2_8\":0,\"temp2_9\":0,\"temp2_10\":0,\"temp2_11\":0,\"temp2_12\":0,\"temp2_13\":0,\"temp2_14\":0,\"temp2_15\":0,\"temp2_16\":0,\"temp_pcb1\":\"14-39-15-40\",\"temp_pcb2\":\"14-35-15-35\",\"temp_pcb3\":\"13-36-14-39\",\"temp_pcb4\":\"-\",\"temp_pcb5\":\"-\",\"temp_pcb6\":\"-\",\"temp_pcb7\":\"-\",\"temp_pcb8\":\"-\",\"temp_pcb9\":\"-\",\"temp_pcb10\":\"-\",\"temp_pcb11\":\"-\",\"temp_pcb12\":\"-\",\"temp_pcb13\":\"-\",\"temp_pcb14\":\"-\",\"temp_pcb15\":\"-\",\"temp_pcb16\":\"-\",\"temp_chip1\":\"29-54-30-55\",\"temp_chip2\":\"29-50-30-50\",\"temp_chip3\":\"28-51-29-54\",\"temp_chip4\":\"-\",\"temp_chip5\":\"-\",\"temp_chip6\":\"-\",\"temp_chip7\":\"-\",\"temp_chip8\":\"-\",\"temp_chip9\":\"-\",\"temp_chip10\":\"-\",\"temp_chip11\":\"-\",\"temp_chip12\":\"-\",\"temp_chip13\":\"-\",\"temp_chip14\":\"-\",\"temp_chip15\":\"-\",\"temp_chip16\":\"-\",\"freq_avg1\":0.00,\"freq_avg2\":0.00,\"freq_avg3\":0.00,\"freq_avg4\":0.00,\"freq_avg5\":0.00,\"freq_avg6\":0.00,\"freq_avg7\":0.00,\"freq_avg8\":0.00,\"freq_avg9\":0.00,\"freq_avg10\":0.00,\"freq_avg11\":0.00,\"freq_avg12\":0.00,\"freq_avg13\":0.00,\"freq_avg14\":0.00,\"freq_avg15\":0.00,\"freq_avg16\":0.00,\"total_rateideal\":21000.00,\"rate_unit\":\"GH\",\"total_freqavg\":0.00,\"total_acn\":180,\"total_rate\":21615.77,\"chain_rateideal1\":0.00,\"chain_rateideal2\":0.00,\"chain_rateideal3\":0.00,\"chain_rateideal4\":0.00,\"chain_rateideal5\":0.00,\"chain_rateideal6\":0.00,\"chain_rateideal7\":0.00,\"chain_rateideal8\":0.00,\"chain_rateideal9\":0.00,\"chain_rateideal10\":0.00,\"chain_rateideal11\":0.00,\"chain_rateideal12\":0.00,\"chain_rateideal13\":0.00,\"chain_rateideal14\":0.00,\"chain_rateideal15\":0.00,\"chain_rateideal16\":0.00,\"temp_max\":40,\"Device Hardware%\":0.0000,\"no_matching_work\":30,\"chain_acn1\":60,\"chain_acn2\":60,\"chain_acn3\":60,\"chain_acn4\":0,\"chain_acn5\":0,\"chain_acn6\":0,\"chain_acn7\":0,\"chain_acn8\":0,\"chain_acn9\":0,\"chain_acn10\":0,\"chain_acn11\":0,\"chain_acn12\":0,\"chain_acn13\":0,\"chain_acn14\":0,\"chain_acn15\":0,\"chain_acn16\":0,\"chain_acs1\":\" oooooooo oooooooo oooooooo oooooooo oooooooo oooooooo oooooooo oooo\",\"chain_acs2\":\" oooooooo oooooooo oooooooo oooooooo oooooooo oooooooo oooooooo oooo\",\"chain_acs3\":\" oooooooo oooooooo oooooooo oooooooo oooooooo oooooooo oooooooo oooo\",\"chain_acs4\":\"\",\"chain_acs5\":\"\",\"chain_acs6\":\"\",\"chain_acs7\":\"\",\"chain_acs8\":\"\",\"chain_acs9\":\"\",\"chain_acs10\":\"\",\"chain_acs11\":\"\",\"chain_acs12\":\"\",\"chain_acs13\":\"\",\"chain_acs14\":\"\",\"chain_acs15\":\"\",\"chain_acs16\":\"\",\"chain_hw1\":7,\"chain_hw2\":17,\"chain_hw3\":6,\"chain_hw4\":0,\"chain_hw5\":0,\"chain_hw6\":0,\"chain_hw7\":0,\"chain_hw8\":0,\"chain_hw9\":0,\"chain_hw10\":0,\"chain_hw11\":0,\"chain_hw12\":0,\"chain_hw13\":0,\"chain_hw14\":0,\"chain_hw15\":0,\"chain_hw16\":0,\"chain_rate1\":\"8048.19\",\"chain_rate2\":\"6574.89\",\"chain_rate3\":\"6992.69\",\"chain_rate4\":\"\",\"chain_rate5\":\"\",\"chain_rate6\":\"\",\"chain_rate7\":\"\",\"chain_rate8\":\"\",\"chain_rate9\":\"\",\"chain_rate10\":\"\",\"chain_rate11\":\"\",\"chain_rate12\":\"\",\"chain_rate13\":\"\",\"chain_rate14\":\"\",\"chain_rate15\":\"\",\"chain_rate16\":\"\",\"chain_xtime1\":\"{}\",\"chain_xtime2\":\"{}\",\"chain_xtime3\":\"{}\",\"chain_offside_1\":\"0\",\"chain_offside_2\":\"0\",\"chain_offside_3\":\"0\",\"chain_opencore_1\":\"0\",\"chain_opencore_2\":\"0\",\"chain_opencore_3\":\"0\",\"miner_version\":\"27.10.1.3\",\"miner_id\":\"804454217b808854\"}],\"id\":1}"),
                                        "{\"command\":\"pools\"}",
                                        new RpcHandler(
                                                "{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1612226597,\"Code\":7,\"Msg\":\"3 Pool(s)\",\"Description\":\"cgminer 4.9.0\"}],\"POOLS\":[{\"POOL\":0,\"URL\":\"stratum+tcp://x.pool.bitcoin.com:3333\",\"Status\":\"Alive\",\"Priority\":0,\"Quota\":1,\"Long Poll\":\"N\",\"Getworks\":449,\"Accepted\":777,\"Rejected\":0,\"Discarded\":7102,\"Stale\":0,\"Get Failures\":0,\"Remote Failures\":0,\"User\":\"x\",\"Last Share Time\":\"0:00:06\",\"Diff\":\"65.5K\",\"Diff1 Shares\":0,\"Proxy Type\":\"\",\"Proxy\":\"\",\"Difficulty Accepted\":72548352.00000000,\"Difficulty Rejected\":0.00000000,\"Difficulty Stale\":0.00000000,\"Last Share Difficulty\":65536.00000000,\"Has Stratum\":true,\"Stratum Active\":true,\"Stratum URL\":\"x.pool.bitcoin.com\",\"Has GBT\":false,\"Best Share\":42091586,\"Pool Rejected%\":0.0000,\"Pool Stale%\":0.0000},{\"POOL\":1,\"URL\":\"\",\"Status\":\"Dead\",\"Priority\":1,\"Quota\":1,\"Long Poll\":\"N\",\"Getworks\":0,\"Accepted\":0,\"Rejected\":0,\"Discarded\":0,\"Stale\":0,\"Get Failures\":0,\"Remote Failures\":0,\"User\":\"\",\"Last Share Time\":\"0\",\"Diff\":\"\",\"Diff1 Shares\":0,\"Proxy Type\":\"\",\"Proxy\":\"\",\"Difficulty Accepted\":0.00000000,\"Difficulty Rejected\":0.00000000,\"Difficulty Stale\":0.00000000,\"Last Share Difficulty\":0.00000000,\"Has Stratum\":false,\"Stratum Active\":false,\"Stratum URL\":\"\",\"Has GBT\":false,\"Best Share\":0,\"Pool Rejected%\":0.0000,\"Pool Stale%\":0.0000},{\"POOL\":2,\"URL\":\"\",\"Status\":\"Dead\",\"Priority\":2,\"Quota\":1,\"Long Poll\":\"N\",\"Getworks\":0,\"Accepted\":0,\"Rejected\":0,\"Discarded\":0,\"Stale\":0,\"Get Failures\":0,\"Remote Failures\":0,\"User\":\"\",\"Last Share Time\":\"0\",\"Diff\":\"\",\"Diff1 Shares\":0,\"Proxy Type\":\"\",\"Proxy\":\"\",\"Difficulty Accepted\":0.00000000,\"Difficulty Rejected\":0.00000000,\"Difficulty Stale\":0.00000000,\"Last Share Difficulty\":0.00000000,\"Has Stratum\":false,\"Stratum Active\":false,\"Stratum URL\":\"\",\"Has GBT\":false,\"Best Share\":0,\"Pool Rejected%\":0.0000,\"Pool Stale%\":0.0000}],\"id\":1}")),
                                Arrays.asList(
                                        "STATS.1.temp6",
                                        "STATS.1.temp7"),
                                new MinerStats.Builder()
                                        .setApiIp("127.0.0.1")
                                        .setApiPort(4028)
                                        .addPool(
                                                new Pool.Builder()
                                                        .setName("x.pool.bitcoin.com:3333")
                                                        .setWorker("x")
                                                        .setStatus(true, true)
                                                        .setPriority(0)
                                                        .setCounts(777, 0, 0)
                                                        .build())
                                        .addAsic(
                                                new Asic.Builder()
                                                        .setHashRate(new BigDecimal("21615780000000.00"))
                                                        .setBoards(3)
                                                        .setFanInfo(
                                                                new FanInfo.Builder()
                                                                        .setCount(2)
                                                                        .addSpeed(2880)
                                                                        .addSpeed(2880)
                                                                        .setSpeedUnits("RPM")
                                                                        .build())
                                                        .addTemp(14)
                                                        .addTemp(39)
                                                        .addTemp(15)
                                                        .addTemp(40)
                                                        .addTemp(14)
                                                        .addTemp(35)
                                                        .addTemp(15)
                                                        .addTemp(35)
                                                        .addTemp(13)
                                                        .addTemp(36)
                                                        .addTemp(14)
                                                        .addTemp(39)
                                                        .addTemp(29)
                                                        .addTemp(54)
                                                        .addTemp(30)
                                                        .addTemp(55)
                                                        .addTemp(29)
                                                        .addTemp(50)
                                                        .addTemp(30)
                                                        .addTemp(50)
                                                        .addTemp(28)
                                                        .addTemp(51)
                                                        .addTemp(29)
                                                        .addTemp(54)
                                                        .hasErrors(false)
                                                        .addRawStats(
                                                                ImmutableMap.of(
                                                                        "STATS.1.temp6",
                                                                        new BigDecimal("0"),
                                                                        "STATS.1.temp7",
                                                                        new BigDecimal("0")))
                                                        .setMinerType("Antminer T15")
                                                        .setCompileTime("Fri Dec 13 15:46:16 CST 2019")
                                                        .build())
                                        .build()
                        },
                        {
                                // Antminer Z11
                                new BigDecimal(0.000000001),
                                ImmutableMap.of(
                                        "{\"command\":\"version\"}",
                                        new RpcHandler(
                                                "{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1612234363,\"Code\":22,\"Msg\":\"CGMiner versions\",\"Description\":\"cgminer 4.9.0\"}],\"VERSION\":[{\"CGMiner\":\"4.9.0\",\"API\":\"3.1\",\"Miner\":\"9.0.0.5\",\"CompileTime\":\"Sat May 23 19:07:17 CST 2020 (Chipless v3.0 Auto Paid)\",\"Type\":\"Antminer Z11\"}],\"id\":1}"),
                                        "{\"command\":\"stats\"}",
                                        new RpcHandler(
                                                "{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1612234362,\"Code\":70,\"Msg\":\"CGMiner stats\",\"Description\":\"cgminer 4.9.0\"}],\"STATS\":[{\"CGMiner\":\"4.9.0\",\"Miner\":\"9.0.0.5\",\"CompileTime\":\"Sat May 23 19:07:17 CST 2020 (Chipless v3.0 Auto Paid)\",\"Type\":\"Antminer Z11\"}{\"STATS\":0,\"ID\":\"ZCASH0\",\"Elapsed\":20578,\"Calls\":0,\"Wait\":0.000000,\"Max\":0.000000,\"Min\":99999999.000000,\"GHS 5s\":\"145474.39\",\"GHS av\":143682.17,\"miner_count\":3,\"fan_num\":2,\"fan1\":3000,\"fan2\":3000,\"fan3\":0,\"fan4\":0,\"fan5\":0,\"fan6\":0,\"temp_num\":3,\"temp1\":37,\"temp2\":38,\"temp3\":34,\"temp4\":0,\"temp2_1\":56,\"temp2_2\":60,\"temp2_3\":54,\"temp2_4\":0,\"temp_max\":38,\"Device Hardware%\":0.0000,\"no_matching_work\":0,\"chain_acn1\":3,\"chain_acn2\":3,\"chain_acn3\":3,\"chain_acn4\":0,\"chain_acs1\":\" ooo\",\"chain_acs2\":\" ooo\",\"chain_acs3\":\" ooo\",\"chain_acs4\":\"\",\"chain_hw1\":0,\"chain_hw2\":0,\"chain_hw3\":0,\"chain_hw4\":0,\"frequency1\":747,\"frequency2\":750,\"frequency3\":750,\"frequency4\":0,\"chain_rate1\":\"42.59\",\"chain_rate2\":\"47.84\",\"chain_rate3\":\"55.04\",\"chain_rate4\":\"\"}],\"id\":1}"),
                                        "{\"command\":\"pools\"}",
                                        new RpcHandler(
                                                "{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1612234361,\"Code\":7,\"Msg\":\"3 Pool(s)\",\"Description\":\"cgminer 4.9.0\"}],\"POOLS\":[{\"POOL\":0,\"URL\":\"stratum+tcp://equihash.luxor.tech:700\",\"Status\":\"Alive\",\"Priority\":0,\"Quota\":1,\"Long Poll\":\"N\",\"Getworks\":1061,\"Accepted\":2848,\"Rejected\":20,\"Discarded\":11751,\"Stale\":0,\"Get Failures\":0,\"Remote Failures\":0,\"User\":\"x.001\",\"Last Share Time\":\"0:00:02\",\"Diff\":\"21\",\"Diff1 Shares\":0,\"Proxy Type\":\"\",\"Proxy\":\"\",\"Difficulty Accepted\":53843.00000000,\"Difficulty Rejected\":350.00000000,\"Difficulty Stale\":0.00000000,\"Last Share Difficulty\":21.00000000,\"Has Stratum\":true,\"Stratum Active\":true,\"Stratum URL\":\"equihash.luxor.tech\",\"Has GBT\":false,\"Best Share\":0,\"Pool Rejected%\":0.6458,\"Pool Stale%\":0.0000},{\"POOL\":1,\"URL\":\"\",\"Status\":\"Dead\",\"Priority\":1,\"Quota\":1,\"Long Poll\":\"N\",\"Getworks\":0,\"Accepted\":0,\"Rejected\":0,\"Discarded\":0,\"Stale\":0,\"Get Failures\":0,\"Remote Failures\":0,\"User\":\"\",\"Last Share Time\":\"0\",\"Diff\":\"\",\"Diff1 Shares\":0,\"Proxy Type\":\"\",\"Proxy\":\"\",\"Difficulty Accepted\":0.00000000,\"Difficulty Rejected\":0.00000000,\"Difficulty Stale\":0.00000000,\"Last Share Difficulty\":0.00000000,\"Has Stratum\":false,\"Stratum Active\":false,\"Stratum URL\":\"\",\"Has GBT\":false,\"Best Share\":0,\"Pool Rejected%\":0.0000,\"Pool Stale%\":0.0000},{\"POOL\":2,\"URL\":\"\",\"Status\":\"Dead\",\"Priority\":2,\"Quota\":1,\"Long Poll\":\"N\",\"Getworks\":0,\"Accepted\":0,\"Rejected\":0,\"Discarded\":0,\"Stale\":0,\"Get Failures\":0,\"Remote Failures\":0,\"User\":\"\",\"Last Share Time\":\"0\",\"Diff\":\"\",\"Diff1 Shares\":0,\"Proxy Type\":\"\",\"Proxy\":\"\",\"Difficulty Accepted\":0.00000000,\"Difficulty Rejected\":0.00000000,\"Difficulty Stale\":0.00000000,\"Last Share Difficulty\":0.00000000,\"Has Stratum\":false,\"Stratum Active\":false,\"Stratum URL\":\"\",\"Has GBT\":false,\"Best Share\":0,\"Pool Rejected%\":0.0000,\"Pool Stale%\":0.0000}],\"id\":1}")),
                                Arrays.asList(
                                        "STATS.1.temp6",
                                        "STATS.1.temp7"),
                                new MinerStats.Builder()
                                        .setApiIp("127.0.0.1")
                                        .setApiPort(4028)
                                        .addPool(
                                                new Pool.Builder()
                                                        .setName("equihash.luxor.tech:700")
                                                        .setWorker("x.001")
                                                        .setStatus(true, true)
                                                        .setPriority(0)
                                                        .setCounts(2848, 20, 0)
                                                        .build())
                                        .addAsic(
                                                new Asic.Builder()
                                                        .setHashRate(new BigDecimal("145474.390000000009060376525549735366826635540871137663998524658381938934326171875000000000"))
                                                        .setBoards(3)
                                                        .setFanInfo(
                                                                new FanInfo.Builder()
                                                                        .setCount(2)
                                                                        .addSpeed(3000)
                                                                        .addSpeed(3000)
                                                                        .setSpeedUnits("RPM")
                                                                        .build())
                                                        .addTemp(37)
                                                        .addTemp(38)
                                                        .addTemp(34)
                                                        .addTemp(56)
                                                        .addTemp(60)
                                                        .addTemp(54)
                                                        .hasErrors(false)
                                                        .setMinerType("Antminer Z11")
                                                        .setCompileTime("Sat May 23 19:07:17 CST 2020 (Chipless v3.0 Auto Paid)")
                                                        .build())
                                        .build()
                        },
                        {
                                // Antminer S19 (really cold)
                                BigDecimal.ONE,
                                ImmutableMap.of(
                                        "{\"command\":\"version\"}",
                                        new RpcHandler(
                                                "{\"STATUS\": [{\"STATUS\": \"S\", \"When\": 1612807334, \"Code\": 22, \"Msg\": \"CGMiner versions\", \"Description\": \"cgminer 1.0.0\"}], \"VERSION\": [{\"BMMiner\": \"1.0.0\", \"API\": \"3.1\", \"Miner\": \"49.0.1.3\", \"CompileTime\": \"Wed Oct 21 10:18:10 CST 2020\", \"Type\": \"Antminer S19 Pro\"}], \"id\": 1}"),
                                        "{\"command\":\"stats\"}",
                                        new RpcHandler(
                                                "{\"STATUS\": [{\"STATUS\": \"S\", \"When\": 1612807334, \"Code\": 70, \"Msg\": \"CGMiner stats\", \"Description\": \"cgminer 1.0.0\"}], \"STATS\": [{\"BMMiner\": \"1.0.0\", \"Miner\": \"49.0.1.3\", \"CompileTime\": \"Wed Oct 21 10:18:10 CST 2020\", \"Type\": \"Antminer S19 Pro\"}, {\"STATS\": 0, \"ID\": \"BTM_SOC0\", \"Elapsed\": 82852, \"Calls\": 0, \"Wait\": 0, \"Max\": 0, \"Min\": 99999999, \"GHS 5s\": 110786.79, \"GHS av\": 111730.11, \"rate_30m\": 111975.81, \"Mode\": 2, \"miner_count\": 3, \"frequency\": 525, \"fan_num\": 4, \"fan1\": 5520, \"fan2\": 5400, \"fan3\": 5040, \"fan4\": 5040, \"temp_num\": 3, \"temp1\": 22, \"temp2_1\": 27, \"temp2\": 20, \"temp2_2\": 25, \"temp3\": 21, \"temp2_3\": 26, \"temp_pcb1\": \"2--1-20-22\", \"temp_pcb2\": \"2-1-20-19\", \"temp_pcb3\": \"1--2-21-19\", \"temp_pcb4\": \"0-0-0-0\", \"temp_chip1\": \"7-4-25-27\", \"temp_chip2\": \"7-6-25-24\", \"temp_chip3\": \"6-3-26-24\", \"temp_chip4\": \"0-0-0-0\", \"temp_pic1\": \"-8--11-10-12\", \"temp_pic2\": \"-8--9-10-9\", \"temp_pic3\": \"-9--12-11-9\", \"temp_pic4\": \"0-0-0-0\", \"total_rateideal\": 111859.0, \"rate_unit\": \"GH\", \"total_freqavg\": 525, \"total_acn\": 342, \"total rate\": 111730.11, \"temp_max\": 0, \"no_matching_work\": 4548, \"chain_acn1\": 114, \"chain_acn2\": 114, \"chain_acn3\": 114, \"chain_acn4\": 0, \"chain_acs1\": \" ooo ooo ooo ooo ooo ooo ooo ooo ooo ooo ooo ooo ooo ooo ooo ooo ooo ooo ooo ooo ooo ooo ooo ooo ooo ooo ooo ooo ooo ooo ooo ooo ooo ooo ooo ooo ooo ooo\", \"chain_acs2\": \" ooo ooo ooo ooo ooo ooo ooo ooo ooo ooo ooo ooo ooo ooo ooo ooo ooo ooo ooo ooo ooo ooo ooo ooo ooo ooo ooo ooo ooo ooo ooo ooo ooo ooo ooo ooo ooo ooo\", \"chain_acs3\": \" ooo ooo ooo ooo ooo ooo ooo ooo ooo ooo ooo ooo ooo ooo ooo ooo ooo ooo ooo ooo ooo ooo ooo ooo ooo ooo ooo ooo ooo ooo ooo ooo ooo ooo ooo ooo ooo ooo\", \"chain_acs4\": \"\", \"chain_hw1\": 1527, \"chain_hw2\": 2752, \"chain_hw3\": 269, \"chain_hw4\": 0, \"chain_rate1\": \"37735.24\", \"chain_rate2\": \"38109.07\", \"chain_rate3\": \"34942.48\", \"chain_rate4\": \"\", \"freq1\": 525, \"freq2\": 525, \"freq3\": 525, \"freq4\": 0, \"miner_version\": \"49.0.1.3\", \"miner_id\": \"8060350c2b10481c\"}], \"id\": 1}"),
                                        "{\"command\":\"pools\"}",
                                        new RpcHandler(
                                                "{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1612234361,\"Code\":7,\"Msg\":\"3 Pool(s)\",\"Description\":\"cgminer 4.9.0\"}],\"POOLS\":[{\"POOL\":0,\"URL\":\"stratum+tcp://equihash.luxor.tech:700\",\"Status\":\"Alive\",\"Priority\":0,\"Quota\":1,\"Long Poll\":\"N\",\"Getworks\":1061,\"Accepted\":2848,\"Rejected\":20,\"Discarded\":11751,\"Stale\":0,\"Get Failures\":0,\"Remote Failures\":0,\"User\":\"x.001\",\"Last Share Time\":\"0:00:02\",\"Diff\":\"21\",\"Diff1 Shares\":0,\"Proxy Type\":\"\",\"Proxy\":\"\",\"Difficulty Accepted\":53843.00000000,\"Difficulty Rejected\":350.00000000,\"Difficulty Stale\":0.00000000,\"Last Share Difficulty\":21.00000000,\"Has Stratum\":true,\"Stratum Active\":true,\"Stratum URL\":\"equihash.luxor.tech\",\"Has GBT\":false,\"Best Share\":0,\"Pool Rejected%\":0.6458,\"Pool Stale%\":0.0000},{\"POOL\":1,\"URL\":\"\",\"Status\":\"Dead\",\"Priority\":1,\"Quota\":1,\"Long Poll\":\"N\",\"Getworks\":0,\"Accepted\":0,\"Rejected\":0,\"Discarded\":0,\"Stale\":0,\"Get Failures\":0,\"Remote Failures\":0,\"User\":\"\",\"Last Share Time\":\"0\",\"Diff\":\"\",\"Diff1 Shares\":0,\"Proxy Type\":\"\",\"Proxy\":\"\",\"Difficulty Accepted\":0.00000000,\"Difficulty Rejected\":0.00000000,\"Difficulty Stale\":0.00000000,\"Last Share Difficulty\":0.00000000,\"Has Stratum\":false,\"Stratum Active\":false,\"Stratum URL\":\"\",\"Has GBT\":false,\"Best Share\":0,\"Pool Rejected%\":0.0000,\"Pool Stale%\":0.0000},{\"POOL\":2,\"URL\":\"\",\"Status\":\"Dead\",\"Priority\":2,\"Quota\":1,\"Long Poll\":\"N\",\"Getworks\":0,\"Accepted\":0,\"Rejected\":0,\"Discarded\":0,\"Stale\":0,\"Get Failures\":0,\"Remote Failures\":0,\"User\":\"\",\"Last Share Time\":\"0\",\"Diff\":\"\",\"Diff1 Shares\":0,\"Proxy Type\":\"\",\"Proxy\":\"\",\"Difficulty Accepted\":0.00000000,\"Difficulty Rejected\":0.00000000,\"Difficulty Stale\":0.00000000,\"Last Share Difficulty\":0.00000000,\"Has Stratum\":false,\"Stratum Active\":false,\"Stratum URL\":\"\",\"Has GBT\":false,\"Best Share\":0,\"Pool Rejected%\":0.0000,\"Pool Stale%\":0.0000}],\"id\":1}")),
                                Arrays.asList(
                                        "STATS.1.temp6",
                                        "STATS.1.temp7"),
                                new MinerStats.Builder()
                                        .setApiIp("127.0.0.1")
                                        .setApiPort(4028)
                                        .addPool(
                                                new Pool.Builder()
                                                        .setName("equihash.luxor.tech:700")
                                                        .setWorker("x.001")
                                                        .setStatus(true, true)
                                                        .setPriority(0)
                                                        .setCounts(2848, 20, 0)
                                                        .build())
                                        .addAsic(
                                                new Asic.Builder()
                                                        .setHashRate(new BigDecimal("110786790000000.00"))
                                                        .setBoards(3)
                                                        .setFanInfo(
                                                                new FanInfo.Builder()
                                                                        .setCount(4)
                                                                        .addSpeed(5520)
                                                                        .addSpeed(5400)
                                                                        .addSpeed(5040)
                                                                        .addSpeed(5040)
                                                                        .setSpeedUnits("RPM")
                                                                        .build())
                                                        .addTemp(2)
                                                        .addTemp(-1)
                                                        .addTemp(20)
                                                        .addTemp(22)
                                                        .addTemp(2)
                                                        .addTemp(1)
                                                        .addTemp(20)
                                                        .addTemp(19)
                                                        .addTemp(1)
                                                        .addTemp(-2)
                                                        .addTemp(21)
                                                        .addTemp(19)
                                                        .addTemp(7)
                                                        .addTemp(4)
                                                        .addTemp(25)
                                                        .addTemp(27)
                                                        .addTemp(7)
                                                        .addTemp(6)
                                                        .addTemp(25)
                                                        .addTemp(24)
                                                        .addTemp(6)
                                                        .addTemp(3)
                                                        .addTemp(26)
                                                        .addTemp(24)
                                                        .hasErrors(false)
                                                        .setMinerType("Antminer S19 Pro")
                                                        .setCompileTime("Wed Oct 21 10:18:10 CST 2020")
                                                        .build())
                                        .build()
                        },
                        {
                                // Antminer S9k
                                BigDecimal.ONE,
                                ImmutableMap.of(
                                        "{\"command\":\"version\"}",
                                        new RpcHandler(
                                                "{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1616818105,\"Code\":22,\"Msg\":\"CGMiner versions\",\"Description\":\"cgminer 4.9.0\"}],\"VERSION\":[{\"CGMiner\":\"4.9.0\",\"API\":\"3.1\",\"Miner\":\"13.10.1.3\",\"CompileTime\":\"Sun Sep 29 15:25:10 CST 2019\",\"Type\":\"Antminer S9k\"}],\"id\":1}\"}"),
                                        "{\"command\":\"stats\"}",
                                        new RpcHandler(
                                                "{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1616818105,\"Code\":70,\"Msg\":\"CGMiner stats\",\"Description\":\"cgminer 4.9.0\"}],\"STATS\":[{\"BMMiner\":\"4.9.0\",\"Miner\":\"13.10.1.3\",\"CompileTime\":\"Sun Sep 29 15:25:10 CST 2019\",\"Type\":\"Antminer S9k\"},{\"STATS\":0,\"ID\":\"BTM_SOC0\",\"Elapsed\":367506,\"Calls\":0,\"Wait\":0.000000,\"Max\":0.000000,\"Min\":99999999.000000,\"GHS 5s\":\"14359.19\",\"GHS av\":14252.76,\"Voltage\":\"9.90\",\"Mode\":\"H\",\"miner_count\":3,\"frequency\":\"300\",\"freq1\":\"375\",\"freq2\":\"385\",\"freq3\":\"385\",\"freq4\":\"0\",\"freq5\":\"0\",\"freq6\":\"0\",\"freq7\":\"0\",\"freq8\":\"0\",\"fan_num\":2,\"fan1\":2280,\"fan2\":3720,\"fan3\":0,\"fan4\":0,\"fan5\":0,\"fan6\":0,\"fan7\":0,\"fan8\":0,\"temp_num\":6,\"temp1\":61,\"temp2\":60,\"temp3\":58,\"temp4\":0,\"temp5\":0,\"temp6\":0,\"temp7\":0,\"temp8\":0,\"temp9\":0,\"temp10\":0,\"temp11\":0,\"temp12\":0,\"temp13\":0,\"temp14\":0,\"temp15\":0,\"temp16\":0,\"temp2_1\":51,\"temp2_2\":56,\"temp2_3\":48,\"temp2_4\":0,\"temp2_5\":0,\"temp2_6\":0,\"temp2_7\":0,\"temp2_8\":0,\"temp2_9\":0,\"temp2_10\":0,\"temp2_11\":0,\"temp2_12\":0,\"temp2_13\":0,\"temp2_14\":0,\"temp2_15\":0,\"temp2_16\":0,\"temp_pcb1\":\"25-61\",\"temp_pcb2\":\"28-60\",\"temp_pcb3\":\"23-58\",\"temp_pcb4\":\"-\",\"temp_pcb5\":\"-\",\"temp_pcb6\":\"-\",\"temp_pcb7\":\"-\",\"temp_pcb8\":\"-\",\"temp_pcb9\":\"-\",\"temp_pcb10\":\"-\",\"temp_pcb11\":\"-\",\"temp_pcb12\":\"-\",\"temp_pcb13\":\"-\",\"temp_pcb14\":\"-\",\"temp_pcb15\":\"-\",\"temp_pcb16\":\"-\",\"temp_chip1\":\"51-85\",\"temp_chip2\":\"56-84\",\"temp_chip3\":\"48-78\",\"temp_chip4\":\"-\",\"temp_chip5\":\"-\",\"temp_chip6\":\"-\",\"temp_chip7\":\"-\",\"temp_chip8\":\"-\",\"temp_chip9\":\"-\",\"temp_chip10\":\"-\",\"temp_chip11\":\"-\",\"temp_chip12\":\"-\",\"temp_chip13\":\"-\",\"temp_chip14\":\"-\",\"temp_chip15\":\"-\",\"temp_chip16\":\"-\",\"freq_avg1\":0.00,\"freq_avg2\":0.00,\"freq_avg3\":0.00,\"freq_avg4\":0.00,\"freq_avg5\":0.00,\"freq_avg6\":0.00,\"freq_avg7\":0.00,\"freq_avg8\":0.00,\"freq_avg9\":0.00,\"freq_avg10\":0.00,\"freq_avg11\":0.00,\"freq_avg12\":0.00,\"freq_avg13\":0.00,\"freq_avg14\":0.00,\"freq_avg15\":0.00,\"freq_avg16\":0.00,\"total_rateideal\":14000.00,\"total_freqavg\":0.00,\"total_acn\":180,\"total_rate\":14359.20,\"chain_rateideal1\":0.00,\"chain_rateideal2\":0.00,\"chain_rateideal3\":0.00,\"chain_rateideal4\":0.00,\"chain_rateideal5\":0.00,\"chain_rateideal6\":0.00,\"chain_rateideal7\":0.00,\"chain_rateideal8\":0.00,\"chain_rateideal9\":0.00,\"chain_rateideal10\":0.00,\"chain_rateideal11\":0.00,\"chain_rateideal12\":0.00,\"chain_rateideal13\":0.00,\"chain_rateideal14\":0.00,\"chain_rateideal15\":0.00,\"chain_rateideal16\":0.00,\"temp_max\":61,\"Device Hardware%\":0.0010,\"no_matching_work\":11456,\"chain_acn1\":60,\"chain_acn2\":60,\"chain_acn3\":60,\"chain_acn4\":0,\"chain_acn5\":0,\"chain_acn6\":0,\"chain_acn7\":0,\"chain_acn8\":0,\"chain_acn9\":0,\"chain_acn10\":0,\"chain_acn11\":0,\"chain_acn12\":0,\"chain_acn13\":0,\"chain_acn14\":0,\"chain_acn15\":0,\"chain_acn16\":0,\"chain_acs1\":\" oooooooo oooooooo oooooooo oooooooo oooooooo oooooooo oooooooo oooo\",\"chain_acs2\":\" oooooooo oooooooo oooooooo oooooooo oooooooo oooooooo oooooooo oooo\",\"chain_acs3\":\" oooooooo oooooooo oooooooo oooooooo oooooooo oooooooo oooooooo oooo\",\"chain_acs4\":\"\",\"chain_acs5\":\"\",\"chain_acs6\":\"\",\"chain_acs7\":\"\",\"chain_acs8\":\"\",\"chain_acs9\":\"\",\"chain_acs10\":\"\",\"chain_acs11\":\"\",\"chain_acs12\":\"\",\"chain_acs13\":\"\",\"chain_acs14\":\"\",\"chain_acs15\":\"\",\"chain_acs16\":\"\",\"chain_hw1\":6738,\"chain_hw2\":1841,\"chain_hw3\":2789,\"chain_hw4\":0,\"chain_hw5\":0,\"chain_hw6\":0,\"chain_hw7\":0,\"chain_hw8\":0,\"chain_hw9\":0,\"chain_hw10\":0,\"chain_hw11\":0,\"chain_hw12\":0,\"chain_hw13\":0,\"chain_hw14\":0,\"chain_hw15\":0,\"chain_hw16\":0,\"chain_rate1\":\"4661.79\",\"chain_rate2\":\"4815.72\",\"chain_rate3\":\"4881.69\",\"chain_rate4\":\"\",\"chain_rate5\":\"\",\"chain_rate6\":\"\",\"chain_rate7\":\"\",\"chain_rate8\":\"\",\"chain_rate9\":\"\",\"chain_rate10\":\"\",\"chain_rate11\":\"\",\"chain_rate12\":\"\",\"chain_rate13\":\"\",\"chain_rate14\":\"\",\"chain_rate15\":\"\",\"chain_rate16\":\"\",\"chain_xtime1\":\"{}\",\"chain_xtime2\":\"{}\",\"chain_xtime3\":\"{X6=1}\",\"chain_offside_1\":\"0\",\"chain_offside_2\":\"0\",\"chain_offside_3\":\"0\",\"chain_opencore_1\":\"0\",\"chain_opencore_2\":\"0\",\"chain_opencore_3\":\"0\",\"miner_version\":\"13.10.1.3\",\"miner_id\":\"810875025c20885c\"}],\"id\":1}"),
                                        "{\"command\":\"pools\"}",
                                        new RpcHandler(
                                                "{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1533152695,\"Code\":7,\"Msg\":\"5 Pool(s)\",\"Description\":\"cgminer 4.10.0\"}],\"POOLS\":[{\"POOL\":0,\"URL\":\"stratum+tcp://dash-eu.coinmine.pl:6099\",\"Status\":\"Alive\",\"Priority\":0,\"Quota\":0,\"Long Poll\":\"N\",\"Getworks\":1167,\"Accepted\":3038,\"Rejected\":8,\"Discarded\":28157,\"Stale\":5,\"Get Failures\":1,\"Remote Failures\":0,\"User\":\"Dash.default\",\"Last Share Time\":\"0:00:00\",\"Diff\":\" 69.2461\",\"Diff1 Shares\":6475798,\"Proxy Type\":\"\",\"Proxy\":\"\",\"Difficulty Accepted\":198412.94104085,\"Difficulty Rejected\":604.97361199,\"Difficulty Stale\":0.00000000,\"Last Share Difficulty\":69.24612531,\"Has Stratum\":true,\"Stratum Active\":true,\"Stratum URL\":\"dash-eu.coinmine.pl\",\"Has GBT\":false,\"Best Share\":\"  0.0000\",\"Pool Rejected%\":0.3040,\"Pool Stale%\":0.0000},{\"POOL\":1,\"URL\":\"\",\"Status\":\"Dead\",\"Priority\":1,\"Quota\":0,\"Long Poll\":\"N\",\"Getworks\":0,\"Accepted\":0,\"Rejected\":0,\"Discarded\":0,\"Stale\":0,\"Get Failures\":0,\"Remote Failures\":0,\"User\":\"\",\"Last Share Time\":\"0\",\"Diff\":\"  0.0000\",\"Diff1 Shares\":0,\"Proxy Type\":\"\",\"Proxy\":\"\",\"Difficulty Accepted\":0.00000000,\"Difficulty Rejected\":0.00000000,\"Difficulty Stale\":0.00000000,\"Last Share Difficulty\":0.00000000,\"Has Stratum\":false,\"Stratum Active\":false,\"Stratum URL\":\"\",\"Has GBT\":false,\"Best Share\":\"  0.0000\",\"Pool Rejected%\":0.0000,\"Pool Stale%\":0.0000},{\"POOL\":2,\"URL\":\"\",\"Status\":\"Dead\",\"Priority\":2,\"Quota\":0,\"Long Poll\":\"N\",\"Getworks\":0,\"Accepted\":0,\"Rejected\":0,\"Discarded\":0,\"Stale\":0,\"Get Failures\":0,\"Remote Failures\":0,\"User\":\"\",\"Last Share Time\":\"0\",\"Diff\":\"  0.0000\",\"Diff1 Shares\":0,\"Proxy Type\":\"\",\"Proxy\":\"\",\"Difficulty Accepted\":0.00000000,\"Difficulty Rejected\":0.00000000,\"Difficulty Stale\":0.00000000,\"Last Share Difficulty\":0.00000000,\"Has Stratum\":false,\"Stratum Active\":false,\"Stratum URL\":\"\",\"Has GBT\":false,\"Best Share\":\"  0.0000\",\"Pool Rejected%\":0.0000,\"Pool Stale%\":0.0000},\n{\"POOL\":3,\"URL\":\"stratum+tcp://dash.suprnova.cc:9991\",\"Status\":\"Alive\",\"Priority\":9998,\"Quota\":0,\"Long Poll\":\"N\",\"Getworks\":1,\"Accepted\":0,\"Rejected\":0,\"Discarded\":0,\"Stale\":0,\"Get Failures\":0,\"Remote Failures\":0,\"User\":\"devFeeMiner.9\",\"Last Share Time\":\"0\",\"Diff\":\" 64.0000\",\"Diff1 Shares\":0,\"Proxy Type\":\"\",\"Proxy\":\"\",\"Difficulty Accepted\":0.00000000,\"Difficulty Rejected\":0.00000000,\"Difficulty Stale\":0.00000000,\"Last Share Difficulty\":0.00000000,\"Has Stratum\":true,\"Stratum Active\":false,\"Stratum URL\":\"\",\"Has GBT\":false,\"Best Share\":\" 0.0000\",\"Pool Rejected%\":0.0000,\"Pool Stale%\":0.0000},{\"POOL\":4,\"URL\":\"stratum+tcp://dash-eu.coinmine.pl:6099\",\"Status\":\"Alive\",\"Priority\":9999,\"Quota\":0,\"Long Poll\":\"N\",\"Getworks\":1,\"Accepted\":0,\"Rejected\":0,\"Discarded\":0,\"Stale\":0,\"Get Failures\":0,\"Remote Failures\":0,\"User\":\"devFeeMiner.1\",\"Last Share Time\":\"0\",\"Diff\":\" 20.0000\",\"Diff1 Shares\":0,\"Proxy Type\":\"\",\"Proxy\":\"\",\"Difficulty Accepted\":0.00000000,\"Difficulty Rejected\":0.00000000,\"Difficulty Stale\":0.00000000,\"Last Share Difficulty\":0.00000000,\"Has Stratum\":true,\"Stratum Active\":false,\"Stratum URL\":\"\",\"Has GBT\":false,\"Best Share\":\" 0.0000\",\"Pool Rejected%\":0.0000,\"Pool Stale%\":0.0000}],\"id\":1}")),
                                Arrays.asList(
                                        "STATS.0.temp6",
                                        "STATS.0.temp7"),
                                new MinerStats.Builder()
                                        .setApiIp("127.0.0.1")
                                        .setApiPort(4028)
                                        .addPool(
                                                new Pool.Builder()
                                                        .setName("dash-eu.coinmine.pl:6099")
                                                        .setWorker("Dash.default")
                                                        .setStatus(true, true)
                                                        .setPriority(0)
                                                        .setCounts(3038, 8, 5)
                                                        .build())
                                        .addPool(
                                                new Pool.Builder()
                                                        .setName("dash.suprnova.cc:9991")
                                                        .setWorker("devFeeMiner.9")
                                                        .setStatus(true, true)
                                                        .setPriority(9998)
                                                        .setCounts(0, 0, 0)
                                                        .build())
                                        .addPool(
                                                new Pool.Builder()
                                                        .setName("dash-eu.coinmine.pl:6099")
                                                        .setWorker("devFeeMiner.1")
                                                        .setStatus(true, true)
                                                        .setPriority(9999)
                                                        .setCounts(0, 0, 0)
                                                        .build())
                                        .addAsic(
                                                new Asic.Builder()
                                                        .setHashRate(new BigDecimal("14359190000000.00"))
                                                        .setBoards(3)
                                                        .setFanInfo(
                                                                new FanInfo.Builder()
                                                                        .setCount(2)
                                                                        .addSpeed(2280)
                                                                        .addSpeed(3720)
                                                                        .setSpeedUnits("RPM")
                                                                        .build())
                                                        .addTemp(25)
                                                        .addTemp(61)
                                                        .addTemp(28)
                                                        .addTemp(60)
                                                        .addTemp(23)
                                                        .addTemp(58)
                                                        .addTemp(51)
                                                        .addTemp(85)
                                                        .addTemp(56)
                                                        .addTemp(84)
                                                        .addTemp(48)
                                                        .addTemp(78)
                                                        .hasErrors(false)
                                                        .setMinerType("Antminer S9k")
                                                        .setCompileTime("Sun Sep 29 15:25:10 CST 2019")
                                                        .build())
                                        .build()
                        },
                        {
                                // Antminer S19 (sleeping)
                                BigDecimal.ONE,
                                ImmutableMap.of(
                                        "{\"command\":\"version\"}",
                                        new RpcHandler(
                                                "{\"STATUS\": [{\"STATUS\": \"S\", \"When\": 1621205582, \"Code\": 22, \"Msg\": \"CGMiner versions\", \"Description\": \"cgminer 1.0.0\"}], \"VERSION\": [{\"BMMiner\": \"1.0.0\", \"API\": \"3.1\", \"Miner\": \"49.0.1.3\", \"CompileTime\": \"Fri Dec 11 11:15:40 CST 2020\", \"Type\": \"Antminer S19\"}], \"id\": 1}"),
                                        "{\"command\":\"stats\"}",
                                        new RpcHandler(
                                                "{\"STATUS\": [{\"STATUS\": \"S\", \"When\": 1621205793, \"Code\": 70, \"Msg\": \"CGMiner stats\", \"Description\": \"cgminer 1.0.0\"}], \"STATS\": [{\"BMMiner\": \"1.0.0\", \"Miner\": \"49.0.1.3\", \"CompileTime\": \"Fri Dec 11 11:15:40 CST 2020\", \"Type\": \"Antminer S19\"}, {\"STATS\": 0, \"ID\": \"BTM_SOC0\", \"Elapsed\": 735, \"Calls\": 0, \"Wait\": 0, \"Max\": 0, \"Min\": 99999999, \"GHS 5s\": 0.0, \"GHS av\": 0.0, \"rate_30m\": 0.0, \"Mode\": 2, \"miner_count\": 2, \"frequency\": 675, \"fan_num\": 4, \"fan1\": 1560, \"fan2\": 1560, \"fan3\": 600, \"fan4\": 720, \"temp_num\": 2, \"temp2\": 40, \"temp2_2\": 40, \"temp3\": 40, \"temp2_3\": 40, \"temp_pcb1\": \"0-0-0-0\", \"temp_pcb2\": \"39-37-40-39\", \"temp_pcb3\": \"38-38-40-40\", \"temp_pcb4\": \"0-0-0-0\", \"temp_chip1\": \"0-0-0-0\", \"temp_chip2\": \"39-37-40-39\", \"temp_chip3\": \"38-38-40-40\", \"temp_chip4\": \"0-0-0-0\", \"temp_pic1\": \"0-0-0-0\", \"temp_pic2\": \"39-37-40-39\", \"temp_pic3\": \"38-38-40-40\", \"temp_pic4\": \"0-0-0-0\", \"total_rateideal\": 63919.0, \"rate_unit\": \"GH\", \"total_freqavg\": 675, \"total_acn\": 152, \"total rate\": 0.0, \"temp_max\": 0, \"no_matching_work\": 0, \"chain_acn1\": 0, \"chain_acn2\": 76, \"chain_acn3\": 76, \"chain_acn4\": 0, \"chain_acs1\": \"\", \"chain_acs2\": \" xx xx xx xx xx xx xx xx xx xx xx xx xx xx xx xx xx xx xx xx xx xx xx xx xx xx xx xx xx xx xx xx xx xx xx xx xx xx\", \"chain_acs3\": \" xx xx xx xx xx xx xx xx xx xx xx xx xx xx xx xx xx xx xx xx xx xx xx xx xx xx xx xx xx xx xx xx xx xx xx xx xx xx\", \"chain_acs4\": \"\", \"chain_hw1\": 0, \"chain_hw2\": 0, \"chain_hw3\": 0, \"chain_hw4\": 0, \"chain_rate1\": \"\", \"chain_rate2\": \"0.00\", \"chain_rate3\": \"0.00\", \"chain_rate4\": \"\", \"freq1\": 0, \"freq2\": 675, \"freq3\": 675, \"freq4\": 0, \"miner_version\": \"49.0.1.3\", \"miner_id\": \"803c2c867510481c\"}], \"id\": 1}"),
                                        "{\"command\":\"pools\"}",
                                        new RpcHandler(
                                                "{\"STATUS\": [{\"STATUS\": \"S\", \"When\": 1621205615, \"Code\": 7, \"Msg\": \"3 Pool(s)\", \"Description\": \"cgminer 1.0.0\"}], \"POOLS\": [{\"POOL\": 0, \"URL\": \"stratum+tcp://aaa:700\", \"Status\": \"Alive\", \"Priority\": 0, \"Quota\": 1, \"Long Poll\": \"N\", \"Getworks\": 22, \"Accepted\": 0, \"Rejected\": 0, \"Discarded\": 296, \"Stale\": 0, \"Get Failures\": 2, \"Remote Failures\": 0, \"User\": \"aaa.226x94\", \"Last Share Time\": \"0\", \"Diff\": \"32.8K\", \"Diff1 Shares\": 0, \"Proxy Type\": \"\", \"Proxy\": \"\", \"Difficulty Accepted\": 0.0, \"Difficulty Rejected\": 0.0, \"Difficulty Stale\": 0.0, \"Last Share Difficulty\": 0.0, \"Has Stratum\": true, \"Stratum Active\": true, \"Stratum URL\": \"aaa\", \"Has GBT\": false, \"Best Share\": 0.0, \"Pool Rejected%\": 0.0, \"Pool Stale%%\": 0.0}, {\"POOL\": 1, \"URL\": \"stratum+tcp://bbb:6000\", \"Status\": \"Alive\", \"Priority\": 1, \"Quota\": 1, \"Long Poll\": \"N\", \"Getworks\": 5, \"Accepted\": 0, \"Rejected\": 0, \"Discarded\": 2, \"Stale\": 0, \"Get Failures\": 0, \"Remote Failures\": 0, \"User\": \"bbb.226x94\", \"Last Share Time\": \"0\", \"Diff\": \"42K\", \"Diff1 Shares\": 0, \"Proxy Type\": \"\", \"Proxy\": \"\", \"Difficulty Accepted\": 0.0, \"Difficulty Rejected\": 0.0, \"Difficulty Stale\": 0.0, \"Last Share Difficulty\": 0.0, \"Has Stratum\": true, \"Stratum Active\": false, \"Stratum URL\": \"\", \"Has GBT\": false, \"Best Share\": 0.0, \"Pool Rejected%\": 0.0, \"Pool Stale%%\": 0.0}, {\"POOL\": 2, \"URL\": \"stratum+tcp://ccc:443\", \"Status\": \"Deed\", \"Priority\": 2, \"Quota\": 1, \"Long Poll\": \"N\", \"Getworks\": 0, \"Accepted\": 0, \"Rejected\": 0, \"Discarded\": 0, \"Stale\": 0, \"Get Failures\": 0, \"Remote Failures\": 0, \"User\": \"ccc.226x94\", \"Last Share Time\": \"0\", \"Diff\": \"\", \"Diff1 Shares\": 0, \"Proxy Type\": \"\", \"Proxy\": \"\", \"Difficulty Accepted\": 0.0, \"Difficulty Rejected\": 0.0, \"Difficulty Stale\": 0.0, \"Last Share Difficulty\": 0.0, \"Has Stratum\": true, \"Stratum Active\": false, \"Stratum URL\": \"\", \"Has GBT\": false, \"Best Share\": 0.0, \"Pool Rejected%\": 0.0, \"Pool Stale%%\": 0.0}], \"id\": 1}")),
                                Arrays.asList(
                                        "STATS.1.temp6",
                                        "STATS.1.temp7"),
                                new MinerStats.Builder()
                                        .setApiIp("127.0.0.1")
                                        .setApiPort(4028)
                                        .addPool(
                                                new Pool.Builder()
                                                        .setName("aaa:700")
                                                        .setWorker("aaa.226x94")
                                                        .setStatus(true, true)
                                                        .setPriority(0)
                                                        .setCounts(0, 0, 0)
                                                        .build())
                                        .addPool(
                                                new Pool.Builder()
                                                        .setName("bbb:6000")
                                                        .setWorker("bbb.226x94")
                                                        .setStatus(true, true)
                                                        .setPriority(1)
                                                        .setCounts(0, 0, 0)
                                                        .build())
                                        .addPool(
                                                new Pool.Builder()
                                                        .setName("ccc:443")
                                                        .setWorker("ccc.226x94")
                                                        .setStatus(true, false)
                                                        .setPriority(2)
                                                        .setCounts(0, 0, 0)
                                                        .build())
                                        .addAsic(
                                                new Asic.Builder()
                                                        .setHashRate(new BigDecimal("0.0"))
                                                        .setBoards(2)
                                                        .setPowerMode(Asic.PowerMode.SLEEPING)
                                                        .setFanInfo(
                                                                new FanInfo.Builder()
                                                                        .setCount(4)
                                                                        .addSpeed(1560)
                                                                        .addSpeed(1560)
                                                                        .addSpeed(600)
                                                                        .addSpeed(720)
                                                                        .setSpeedUnits("RPM")
                                                                        .build())
                                                        .addTemp(39)
                                                        .addTemp(37)
                                                        .addTemp(40)
                                                        .addTemp(39)
                                                        .addTemp(38)
                                                        .addTemp(38)
                                                        .addTemp(40)
                                                        .addTemp(40)
                                                        .addTemp(39)
                                                        .addTemp(37)
                                                        .addTemp(40)
                                                        .addTemp(39)
                                                        .addTemp(38)
                                                        .addTemp(38)
                                                        .addTemp(40)
                                                        .addTemp(40)
                                                        .hasErrors(false)
                                                        .setMinerType("Antminer S19")
                                                        .setCompileTime("Fri Dec 11 11:15:40 CST 2020")
                                                        .build())
                                        .build()
                        }
                });
    }
}
