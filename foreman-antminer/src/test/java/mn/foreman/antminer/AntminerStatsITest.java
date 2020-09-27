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
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

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
                                                "{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1533152695,\"Code\":7,\"Msg\":\"5 Pool(s)\",\"Description\":\"cgminer 4.10.0\"}],\"POOLS\":[{\"POOL\":0,\"URL\":\"stratum+tcp://dash-eu.coinmine.pl:6099\",\"Status\":\"Alive\",\"Priority\":0,\"Quota\":0,\"Long Poll\":\"N\",\"Getworks\":1167,\"Accepted\":3038,\"Rejected\":8,\"Discarded\":28157,\"Stale\":5,\"Get Failures\":1,\"Remote Failures\":0,\"User\":\"Dash.default\",\"Last Share Time\":\"0:00:00\",\"Diff\":\" 69.2461\",\"Diff1 Shares\":6475798,\"Proxy Type\":\"\",\"Proxy\":\"\",\"Difficulty Accepted\":198412.94104085,\"Difficulty Rejected\":604.97361199,\"Difficulty Stale\":0.00000000,\"Last Share Difficulty\":69.24612531,\"Has Stratum\":true,\"Stratum Active\":true,\"Stratum URL\":\"dash-eu.coinmine.pl\",\"Has GBT\":false,\"Best Share\":\"  0.0000\",\"Pool Rejected%\":0.3040,\"Pool Stale%\":0.0000},{\"POOL\":1,\"URL\":\"\",\"Status\":\"Dead\",\"Priority\":1,\"Quota\":0,\"Long Poll\":\"N\",\"Getworks\":0,\"Accepted\":0,\"Rejected\":0,\"Discarded\":0,\"Stale\":0,\"Get Failures\":0,\"Remote Failures\":0,\"User\":\"\",\"Last Share Time\":\"0\",\"Diff\":\"  0.0000\",\"Diff1 Shares\":0,\"Proxy Type\":\"\",\"Proxy\":\"\",\"Difficulty Accepted\":0.00000000,\"Difficulty Rejected\":0.00000000,\"Difficulty Stale\":0.00000000,\"Last Share Difficulty\":0.00000000,\"Has Stratum\":false,\"Stratum Active\":false,\"Stratum URL\":\"\",\"Has GBT\":false,\"Best Share\":\"  0.0000\",\"Pool Rejected%\":0.0000,\"Pool Stale%\":0.0000},{\"POOL\":2,\"URL\":\"\",\"Status\":\"Dead\",\"Priority\":2,\"Quota\":0,\"Long Poll\":\"N\",\"Getworks\":0,\"Accepted\":0,\"Rejected\":0,\"Discarded\":0,\"Stale\":0,\"Get Failures\":0,\"Remote Failures\":0,\"User\":\"\",\"Last Share Time\":\"0\",\"Diff\":\"  0.0000\",\"Diff1 Shares\":0,\"Proxy Type\":\"\",\"Proxy\":\"\",\"Difficulty Accepted\":0.00000000,\"Difficulty Rejected\":0.00000000,\"Difficulty Stale\":0.00000000,\"Last Share Difficulty\":0.00000000,\"Has Stratum\":false,\"Stratum Active\":false,\"Stratum URL\":\"\",\"Has GBT\":false,\"Best Share\":\"  0.0000\",\"Pool Rejected%\":0.0000,\"Pool Stale%\":0.0000},\n{\"POOL\":3,\"URL\":\"stratum+tcp://dash.suprnova.cc:9991\",\"Status\":\"Alive\",\"Priority\":9998,\"Quota\":0,\"Long Poll\":\"N\",\"Getworks\":1,\"Accepted\":0,\"Rejected\":0,\"Discarded\":0,\"Stale\":0,\"Get Failures\":0,\"Remote Failures\":0,\"User\":\"devFeeMiner.9\",\"Last Share Time\":\"0\",\"Diff\":\" 64.0000\",\"Diff1 Shares\":0,\"Proxy Type\":\"\",\"Proxy\":\"\",\"Difficulty Accepted\":0.00000000,\"Difficulty Rejected\":0.00000000,\"Difficulty Stale\":0.00000000,\"Last Share Difficulty\":0.00000000,\"Has Stratum\":true,\"Stratum Active\":false,\"Stratum URL\":\"\",\"Has GBT\":false,\"Best Share\":\" 0.0000\",\"Pool Rejected%\":0.0000,\"Pool Stale%\":0.0000},{\"POOL\":4,\"URL\":\"stratum+tcp://dash-eu.coinmine.pl:6099\",\"Status\":\"Alive\",\"Priority\":9999,\"Quota\":0,\"Long Poll\":\"N\",\"Getworks\":1,\"Accepted\":0,\"Rejected\":0,\"Discarded\":0,\"Stale\":0,\"Get Failures\":0,\"Remote Failures\":0,\"User\":\"devFeeMiner.1\",\"Last Share Time\":\"0\",\"Diff\":\" 20.0000\",\"Diff1 Shares\":0,\"Proxy Type\":\"\",\"Proxy\":\"\",\"Difficulty Accepted\":0.00000000,\"Difficulty Rejected\":0.00000000,\"Difficulty Stale\":0.00000000,\"Last Share Difficulty\":0.00000000,\"Has Stratum\":true,\"Stratum Active\":false,\"Stratum URL\":\"\",\"Has GBT\":false,\"Best Share\":\" 0.0000\",\"Pool Rejected%\":0.0000,\"Pool Stale%\":0.0000}],\"id\":1}")),
                                Arrays.asList(
                                        "STATS.0.temp1",
                                        "STATS.0.temp2"),
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
                                                        .setHashRate(new BigDecimal("16346640000.00000"))
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
                                                        .setStatus(true, true)
                                                        .setPriority(0)
                                                        .setCounts(38, 0, 0)
                                                        .build())
                                        .addAsic(
                                                new Asic.Builder()
                                                        .setHashRate(new BigDecimal("197300000.00000"))
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
                                                        .build())
                                        .build()
                        },
                        {
                                // Antminer L3
                                new BigDecimal("0.001"),
                                ImmutableMap.of(
                                        "{\"command\":\"version\"}",
                                        new RpcHandler(
                                                "{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1526315502,\"Code\":22,\"Msg\":\"CGMiner versions\",\"Description\":\"cgminer 4.9.0\"}],\"VERSION\":[{\"CGMiner\":\"4.9.0\",\"API\":\"3.1\",\"Miner\":\"1.0.1.3\",\"CompileTime\":\"Fri Aug 25 17:28:57 CST 2017\",\"Type\":\"Antminer D3\"}],\"id\":1}"),
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
                                                        .setStatus(true, true)
                                                        .setPriority(0)
                                                        .setCounts(47384, 212, 15)
                                                        .build())
                                        .addAsic(
                                                new Asic.Builder()
                                                        .setHashRate(new BigDecimal("504560000.00000"))
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
                                                        .build())
                                        .build()
                        },
                        {
                                // Antminer L3
                                new BigDecimal("0.001"),
                                ImmutableMap.of(
                                        "{\"command\":\"version\"}",
                                        new RpcHandler(
                                                "{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1526315502,\"Code\":22,\"Msg\":\"CGMiner versions\",\"Description\":\"cgminer 4.9.0\"}],\"VERSION\":[{\"CGMiner\":\"4.9.0\",\"API\":\"3.1\",\"Miner\":\"1.0.1.3\",\"CompileTime\":\"Fri Aug 25 17:28:57 CST 2017\",\"Type\":\"Antminer D3\"}],\"id\":1}"),
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
                                                        .setStatus(true, true)
                                                        .setPriority(0)
                                                        .setCounts(47384, 212, 15)
                                                        .build())
                                        .addAsic(
                                                new Asic.Builder()
                                                        .setHashRate(new BigDecimal("0.00"))
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
                                                        .addRawStats(
                                                                ImmutableMap.of(
                                                                        "STATS.1.temp1",
                                                                        new BigDecimal("50"),
                                                                        "STATS.1.temp2",
                                                                        new BigDecimal("53")))
                                                        .build())
                                        .build()
                        },
                        {
                                // Antminer S9 (bOS+)
                                BigDecimal.ONE,
                                ImmutableMap.of(
                                        "{\"command\":\"version\"}",
                                        new RpcHandler(
                                                "{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1597187881,\"Code\":22,\"Msg\":\"BOSminer+ versions\",\"Description\":\"BOSminer+ 0.2.0-36c56a9363\"}],\"VERSION\":[{\"API\":\"3.7\",\"BOSminer+\":\"0.2.0-36c56a9363\"}],\"id\":1}"),
                                        "{\"command\":\"pools\"}",
                                        new RpcHandler(
                                                "{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1597187909,\"Code\":7,\"Msg\":\"3 Pool(s)\",\"Description\":\"BOSminer+ 0.2.0-36c56a9363\"}],\"POOLS\":[{\"Accepted\":152,\"AsicBoost\":true,\"Bad Work\":0,\"Best Share\":65536,\"Current Block Height\":0,\"Current Block Version\":536870912,\"Diff1 Shares\":10984863,\"Difficulty Accepted\":9961472.0,\"Difficulty Rejected\":0.0,\"Difficulty Stale\":0.0,\"Discarded\":0,\"Get Failures\":0,\"Getworks\":106,\"Has GBT\":false,\"Has Stratum\":true,\"Has Vmask\":true,\"Last Share Difficulty\":65536.0,\"Last Share Time\":1597187897,\"Long Poll\":\"N\",\"POOL\":0,\"Pool Rejected%\":0.0,\"Pool Stale%\":0.0,\"Priority\":0,\"Proxy\":\"\",\"Proxy Type\":\"\",\"Quota\":1,\"Rejected\":0,\"Remote Failures\":0,\"Stale\":0,\"Status\":\"Alive\",\"Stratum Active\":true,\"Stratum Difficulty\":65536.0,\"Stratum URL\":\"stratum.antpool.com:3333\",\"URL\":\"stratum+tcp://stratum.antpool.com:3333\",\"User\":\"antminer_1\",\"Work Difficulty\":65536.0,\"Works\":16655828},{\"Accepted\":0,\"AsicBoost\":true,\"Bad Work\":0,\"Best Share\":0,\"Current Block Height\":0,\"Current Block Version\":536870912,\"Diff1 Shares\":0,\"Difficulty Accepted\":0.0,\"Difficulty Rejected\":0.0,\"Difficulty Stale\":0.0,\"Discarded\":0,\"Get Failures\":0,\"Getworks\":3,\"Has GBT\":false,\"Has Stratum\":true,\"Has Vmask\":true,\"Last Share Difficulty\":0.0,\"Last Share Time\":0,\"Long Poll\":\"N\",\"POOL\":1,\"Pool Rejected%\":0.0,\"Pool Stale%\":0.0,\"Priority\":1,\"Proxy\":\"\",\"Proxy Type\":\"\",\"Quota\":1,\"Rejected\":0,\"Remote Failures\":0,\"Stale\":0,\"Status\":\"Alive\",\"Stratum Active\":false,\"Stratum Difficulty\":65536.0,\"Stratum URL\":\"stratum.antpool.com:443\",\"URL\":\"stratum+tcp://stratum.antpool.com:443\",\"User\":\"antminer_1\",\"Work Difficulty\":65536.0,\"Works\":0},{\"Accepted\":0,\"AsicBoost\":true,\"Bad Work\":0,\"Best Share\":0,\"Current Block Height\":0,\"Current Block Version\":0,\"Diff1 Shares\":0,\"Difficulty Accepted\":0.0,\"Difficulty Rejected\":0.0,\"Difficulty Stale\":0.0,\"Discarded\":0,\"Get Failures\":0,\"Getworks\":0,\"Has GBT\":false,\"Has Stratum\":true,\"Has Vmask\":true,\"Last Share Difficulty\":0.0,\"Last Share Time\":0,\"Long Poll\":\"N\",\"POOL\":2,\"Pool Rejected%\":0.0,\"Pool Stale%\":0.0,\"Priority\":2,\"Proxy\":\"\",\"Proxy Type\":\"\",\"Quota\":1,\"Rejected\":0,\"Remote Failures\":0,\"Stale\":0,\"Status\":\"Alive\",\"Stratum Active\":false,\"Stratum Difficulty\":0.0,\"Stratum URL\":\"stratum.antpool.com:25\",\"URL\":\"stratum+tcp://stratum.antpool.com:25\",\"User\":\"antminer_1\",\"Work Difficulty\":0.0,\"Works\":0}],\"id\":1}"),
                                        "{\"command\":\"summary\"}",
                                        new RpcHandler(
                                                "{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1597187939,\"Code\":11,\"Msg\":\"Summary\",\"Description\":\"BOSminer+ 0.2.0-36c56a9363\"}],\"SUMMARY\":[{\"Accepted\":153,\"Best Share\":65536,\"Device Hardware%\":0.001644850292093576,\"Device Rejected%\":0.0,\"Difficulty Accepted\":10027008.0,\"Difficulty Rejected\":0.0,\"Difficulty Stale\":0.0,\"Discarded\":0,\"Elapsed\":3680,\"Found Blocks\":0,\"Get Failures\":0,\"Getworks\":110,\"Hardware Errors\":186,\"Last getwork\":1597187939,\"Local Work\":17131300,\"MHS 15m\":14199249.521457672,\"MHS 1m\":13967276.98800245,\"MHS 24h\":562115.4771588066,\"MHS 5m\":14257979.893394984,\"MHS 5s\":15740915.842676325,\"MHS av\":11988299.650652435,\"Network Blocks\":0,\"Pool Rejected%\":0.0,\"Pool Stale%\":0.0,\"Rejected\":0,\"Remote Failures\":0,\"Stale\":0,\"Total MH\":44121202599.395325,\"Utility\":2.4945652173913047,\"Work Utility\":184349.05814696933}],\"id\":1}"),
                                        "{\"command\":\"fans\"}",
                                        new RpcHandler(
                                                "{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1597187973,\"Code\":202,\"Msg\":\"4 Fan(s)\",\"Description\":\"BOSminer+ 0.2.0-36c56a9363\"}],\"FANS\":[{\"FAN\":0,\"ID\":0,\"RPM\":2820,\"Speed\":40},{\"FAN\":1,\"ID\":1,\"RPM\":3780,\"Speed\":40},{\"FAN\":2,\"ID\":2,\"RPM\":0,\"Speed\":40},{\"FAN\":3,\"ID\":3,\"RPM\":0,\"Speed\":40}],\"id\":1}"),
                                        "{\"command\":\"temps\"}",
                                        new RpcHandler(
                                                "{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1597187996,\"Code\":201,\"Msg\":\"3 Temp(s)\",\"Description\":\"BOSminer+ 0.2.0-36c56a9363\"}],\"TEMPS\":[{\"Board\":75.0,\"Chip\":0.0,\"ID\":6,\"TEMP\":0},{\"Board\":63.0,\"Chip\":0.0,\"ID\":7,\"TEMP\":1},{\"Board\":70.0,\"Chip\":0.0,\"ID\":8,\"TEMP\":2}],\"id\":1}")),
                                Arrays.asList(
                                        "FANS.0.Speed",
                                        "FANS.1.Speed"),
                                new MinerStats.Builder()
                                        .setApiIp("127.0.0.1")
                                        .setApiPort(4028)
                                        .addPool(
                                                new Pool.Builder()
                                                        .setName("stratum.antpool.com:3333")
                                                        .setStatus(true, true)
                                                        .setPriority(0)
                                                        .setCounts(152, 0, 0)
                                                        .build())
                                        .addPool(
                                                new Pool.Builder()
                                                        .setName("stratum.antpool.com:443")
                                                        .setStatus(true, true)
                                                        .setPriority(1)
                                                        .setCounts(0, 0, 0)
                                                        .build())
                                        .addPool(
                                                new Pool.Builder()
                                                        .setName("stratum.antpool.com:25")
                                                        .setStatus(true, true)
                                                        .setPriority(2)
                                                        .setCounts(0, 0, 0)
                                                        .build())
                                        .addAsic(
                                                new Asic.Builder()
                                                        .setHashRate(new BigDecimal("15740915842676.325000000"))
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
                                ImmutableMap.of(
                                        "{\"command\":\"version\"}",
                                        new RpcHandler(
                                                "{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1597196862,\"Code\":22,\"Msg\":\"BOSminer versions\",\"Description\":\"BOSminer 0.2.0-d360818\"}],\"VERSION\":[{\"API\":\"3.7\",\"BOSminer\":\"0.2.0-d360818\"}],\"id\":1}"),
                                        "{\"command\":\"pools\"}",
                                        new RpcHandler(
                                                "{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1597197657,\"Code\":7,\"Msg\":\"2 Pool(s)\",\"Description\":\"BOSminer 0.2.0-d360818\"}],\"POOLS\":[{\"Accepted\":68,\"AsicBoost\":true,\"Bad Work\":0,\"Best Share\":65536,\"Current Block Height\":0,\"Current Block Version\":536870912,\"Diff1 Shares\":6027,\"Difficulty Accepted\":359936.0,\"Difficulty Rejected\":1024.0,\"Difficulty Stale\":0.0,\"Discarded\":0,\"Get Failures\":0,\"Getworks\":8,\"Has GBT\":false,\"Has Stratum\":true,\"Has Vmask\":true,\"Last Share Difficulty\":65536.0,\"Last Share Time\":1597197610,\"Long Poll\":\"N\",\"POOL\":0,\"Pool Rejected%\":0.28368794326241137,\"Pool Stale%\":0.0,\"Priority\":0,\"Proxy\":\"\",\"Proxy Type\":\"\",\"Quota\":1,\"Rejected\":2,\"Remote Failures\":0,\"Stale\":0,\"Status\":\"Alive\",\"Stratum Active\":true,\"Stratum Difficulty\":65536.0,\"Stratum URL\":\"stratum.antpool.com:3333\",\"URL\":\"stratum+tcp://stratum.antpool.com:3333\",\"User\":\"antminer_1\",\"Work Difficulty\":65536.0,\"Works\":483100},{\"Accepted\":0,\"AsicBoost\":true,\"Bad Work\":0,\"Best Share\":0,\"Current Block Height\":0,\"Current Block Version\":0,\"Diff1 Shares\":0,\"Difficulty Accepted\":0.0,\"Difficulty Rejected\":0.0,\"Difficulty Stale\":0.0,\"Discarded\":0,\"Get Failures\":0,\"Getworks\":0,\"Has GBT\":false,\"Has Stratum\":true,\"Has Vmask\":true,\"Last Share Difficulty\":0.0,\"Last Share Time\":0,\"Long Poll\":\"N\",\"POOL\":1,\"Pool Rejected%\":0.0,\"Pool Stale%\":0.0,\"Priority\":1,\"Proxy\":\"\",\"Proxy Type\":\"\",\"Quota\":1,\"Rejected\":0,\"Remote Failures\":0,\"Stale\":0,\"Status\":\"Dead\",\"Stratum Active\":false,\"Stratum Difficulty\":0.0,\"Stratum URL\":\"stratum.slushpool.com:3333\",\"URL\":\"stratum+tcp://stratum.slushpool.com:3333\",\"User\":\"!non-existent-user!\",\"Work Difficulty\":0.0,\"Works\":0}],\"id\":1}"),
                                        "{\"command\":\"summary\"}",
                                        new RpcHandler(
                                                "{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1597197626,\"Code\":11,\"Msg\":\"Summary\",\"Description\":\"BOSminer 0.2.0-d360818\"}],\"SUMMARY\":[{\"Accepted\":68,\"Best Share\":65536,\"Device Hardware%\":0.0,\"Device Rejected%\":22.852041954920775,\"Difficulty Accepted\":359936.0,\"Difficulty Rejected\":1024.0,\"Difficulty Stale\":0.0,\"Discarded\":0,\"Elapsed\":92,\"Found Blocks\":0,\"Get Failures\":0,\"Getworks\":7,\"Hardware Errors\":0,\"Last getwork\":1597197626,\"Local Work\":357660,\"MHS 15m\":1368586.556684464,\"MHS 1m\":14194945.838709138,\"MHS 24h\":14256.109965463167,\"MHS 5m\":4105759.670053392,\"MHS 5s\":14274425.317759488,\"MHS av\":16698158.653278796,\"Network Blocks\":0,\"Pool Rejected%\":2.857142857142857,\"Pool Stale%\":0.0,\"Rejected\":2,\"Remote Failures\":0,\"Stale\":0,\"Total MH\":1550311395.16416,\"Utility\":44.347826086956516,\"Work Utility\":185334.3043014206}],\"id\":1}"),
                                        "{\"command\":\"fans\"}",
                                        new RpcHandler(
                                                "{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1597197416,\"Code\":202,\"Msg\":\"4 Fan(s)\",\"Description\":\"BOSminer 0.2.0-d360818\"}],\"FANS\":[{\"FAN\":0,\"ID\":0,\"RPM\":1560,\"Speed\":1},{\"FAN\":1,\"ID\":1,\"RPM\":1560,\"Speed\":1},{\"FAN\":2,\"ID\":2,\"RPM\":0,\"Speed\":1},{\"FAN\":3,\"ID\":3,\"RPM\":0,\"Speed\":1}],\"id\":1}"),
                                        "{\"command\":\"temps\"}",
                                        new RpcHandler(
                                                "{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1597197430,\"Code\":201,\"Msg\":\"3 Temp(s)\",\"Description\":\"BOSminer 0.2.0-d360818\"}],\"TEMPS\":[{\"Board\":53.0,\"Chip\":0.0,\"ID\":6,\"TEMP\":0},{\"Board\":46.0,\"Chip\":0.0,\"ID\":7,\"TEMP\":1},{\"Board\":52.0,\"Chip\":0.0,\"ID\":8,\"TEMP\":2}],\"id\":1}")),
                                Arrays.asList(
                                        "FANS.0.Speed",
                                        "FANS.1.Speed"),
                                new MinerStats.Builder()
                                        .setApiIp("127.0.0.1")
                                        .setApiPort(4028)
                                        .addPool(
                                                new Pool.Builder()
                                                        .setName("stratum.antpool.com:3333")
                                                        .setStatus(true, true)
                                                        .setPriority(0)
                                                        .setCounts(68, 2, 0)
                                                        .build())
                                        .addPool(
                                                new Pool.Builder()
                                                        .setName("stratum.slushpool.com:3333")
                                                        .setStatus(true, false)
                                                        .setPriority(1)
                                                        .setCounts(0, 0, 0)
                                                        .build())
                                        .addAsic(
                                                new Asic.Builder()
                                                        .setHashRate(new BigDecimal("14274425317759.488000000"))
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
                                                        .setHashRate(new BigDecimal("11781400000000.00"))
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
                                                        .setStatus(true, true)
                                                        .setPriority(0)
                                                        .setCounts(135, 0, 0)
                                                        .build())
                                        .addPool(
                                                new Pool.Builder()
                                                        .setName("stratum.antpool.com:3333")
                                                        .setStatus(true, true)
                                                        .setPriority(1)
                                                        .setCounts(0, 0, 0)
                                                        .build())
                                        .addPool(
                                                new Pool.Builder()
                                                        .setName("cn.ss.btc.com:3333")
                                                        .setStatus(true, false)
                                                        .setPriority(2)
                                                        .setCounts(0, 0, 0)
                                                        .build())
                                        .addPool(
                                                new Pool.Builder()
                                                        .setName("devfee")
                                                        .setStatus(true, true)
                                                        .setPriority(993)
                                                        .setCounts(166, 0, 0)
                                                        .build())
                                        .addAsic(
                                                new Asic.Builder()
                                                        .setHashRate(new BigDecimal("12795480000000.00"))
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
                                                        .setStatus(true, true)
                                                        .setPriority(3)
                                                        .setCounts(1, 0, 0)
                                                        .build())
                                        .addPool(
                                                new Pool.Builder()
                                                        .setName("btc-us.f2pool.com:1314")
                                                        .setStatus(true, true)
                                                        .setPriority(4)
                                                        .setCounts(0, 0, 0)
                                                        .build())
                                        .addPool(
                                                new Pool.Builder()
                                                        .setName("btc.viabtc.com:3333")
                                                        .setStatus(true, true)
                                                        .setPriority(5)
                                                        .setCounts(0, 0, 0)
                                                        .build())
                                        .addAsic(
                                                new Asic.Builder()
                                                        .setHashRate(new BigDecimal("13402140000000.00"))
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
                                                        .build())
                                        .build()
                        }
                });
    }
}
