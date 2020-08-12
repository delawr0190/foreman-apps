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
import java.util.Map;

/** Runs an integration tests using {@link CgMiner} against a fake API. */
@RunWith(Parameterized.class)
public class AntminerStatsITest
        extends AbstractApiITest {

    /**
     * Constructor.
     *
     * @param multiplier    The multiplier.
     * @param handlers      The handlers.
     * @param expectedStats The expected stats.
     */
    public AntminerStatsITest(
            final BigDecimal multiplier,
            final Map<String, HandlerInterface> handlers,
            final MinerStats expectedStats) {
        super(
                new AntminerFactory(multiplier).create(
                        ImmutableMap.of(
                                "apiIp",
                                "127.0.0.1",
                                "apiPort",
                                "4028")),
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
                                                        .build())
                                        .build()
                        }
                });
    }
}
