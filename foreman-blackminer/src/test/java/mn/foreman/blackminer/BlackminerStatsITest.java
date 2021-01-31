package mn.foreman.blackminer;

import mn.foreman.model.miners.FanInfo;
import mn.foreman.model.miners.MinerStats;
import mn.foreman.model.miners.Pool;
import mn.foreman.model.miners.asic.Asic;
import mn.foreman.util.AbstractApiITest;
import mn.foreman.util.FakeMinerServer;
import mn.foreman.util.http.FakeHttpMinerServer;
import mn.foreman.util.http.HttpHandler;
import mn.foreman.util.rpc.FakeRpcMinerServer;
import mn.foreman.util.rpc.RpcHandler;

import com.google.common.collect.ImmutableMap;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/** Tests blackminer stats obtaining. */
@RunWith(Parameterized.class)
public class BlackminerStatsITest
        extends AbstractApiITest {

    /**
     * Constructor.
     *
     * @param servers       The miner servers.
     * @param expectedStats The expected stats.
     */
    public BlackminerStatsITest(
            final List<FakeMinerServer> servers,
            final MinerStats expectedStats) {
        super(
                new BlackminerFactory()
                        .create(
                                ImmutableMap.<String, Object>builder()
                                        .put(
                                                "apiIp",
                                                "127.0.0.1")
                                        .put(
                                                "apiPort",
                                                "4028")
                                        .put(
                                                "username",
                                                "username")
                                        .put(
                                                "password",
                                                "password")
                                        .put(
                                                "port",
                                                "8080")
                                        .put(
                                                "statsWhitelist",
                                                Arrays.asList(
                                                        "SUMMARY.0.Last getwork",
                                                        "STATS.0.GHS av"))
                                        .build()),
                servers,
                expectedStats);
    }

    /**
     * Test parameters
     *
     * @return The test parameters.
     */
    @Parameterized.Parameters
    public static Collection parameters() {
        return Arrays.asList(
                new Object[][]{
                        {
                                // Blackminer F1 Mini
                                Arrays.asList(
                                        new FakeRpcMinerServer(
                                                4028,
                                                ImmutableMap.of(
                                                        "{\"command\":\"summary\"}",
                                                        new RpcHandler(
                                                                "{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1593395696,\"Code\":11,\"Msg\":\"Summary\",\"Description\":\"ccminer 2.3.3\"}],\"SUMMARY\":[{\"Elapsed\":20261,\"GHS 5s\":\"2.2329\",\"GHS av\":\"1.5698\",\"Found Blocks\":0,\"Getworks\":820,\"Accepted\":\"  6.0000\",\"Rejected\":\"  6.0000\",\"Hardware Errors\":1531,\"Utility\":2.68,\"Discarded\":12216,\"Stale\":0,\"Get Failures\":0,\"Local Work\":380193,\"Remote Failures\":0,\"Network Blocks\":671,\"Total MH\":31805691.0000,\"Work Utility\":21.75,\"Difficulty Accepted\":7296.00000000,\"Difficulty Rejected\":48.00000000,\"Difficulty Stale\":0.00000000,\"Best Share\":1797,\"Device Hardware%\":17.2507,\"Device Rejected%\":0.6536,\"Pool Rejected%\":0.6536,\"Pool Stale%\":0.0000,\"Last getwork\":1593395696}],\"id\":1}"),
                                                        "{\"command\":\"stats\"}",
                                                        new RpcHandler(
                                                                "{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1593395695,\"Code\":70,\"Msg\":\"CGMiner stats\",\"Description\":\"ccminer 2.3.3\"}],\"STATS\":[{\"CGMiner\":\"2.3.3\",\"Miner\":\"1.3.0.8\",\"CompileTime\":\"2020-06-12 16-01-07 CST\",\"Type\":\"Blackminer F1-MINI\"}{\"STATS\":0,\"ID\":\"A30\",\"Elapsed\":20261,\"Calls\":0,\"Wait\":0.000000,\"Max\":0.000000,\"Min\":99999999.000000,\"GHS 5s\":\"2.2329\",\"GHS av\":\"1.5698\",\"miner_count\":1,\"frequency\":\"490\",\"fan_num\":2,\"fan1\":1500,\"fan2\":1500,\"temp_num\":1,\"temp1\":44,\"chipTemp1\":\"[67.30, 0.00, 0.00, 0.00, -73364045383872769689863497711616.00, 0.13]\",\"temp2\":0,\"chipTemp2\":\"[0.00, 0.00, 0.00, -73364045383872769689863497711616.00, 0.13, 6300255232.00]\",\"temp3\":0,\"chipTemp3\":\"[0.00, 0.00, -73364045383872769689863497711616.00, 0.13, 6300255232.00, 37.20]\",\"temp4\":0,\"chipTemp4\":\"[0.00, -73364045383872769689863497711616.00, 0.13, 6300255232.00, 37.20, 0.00]\",\"temp_max\":44,\"Device Hardware%\":0.0000,\"no_matching_work\":1531,\"chain_acn1\":1,\"chain_acn2\":0,\"chain_acn3\":0,\"chain_acn4\":0,\"chain_acs1\":\" o\",\"chain_acs2\":\"\",\"chain_acs3\":\"\",\"chain_acs4\":\"\",\"chain_hw1\":1531,\"chain_hw2\":0,\"chain_hw3\":0,\"chain_hw4\":0,\"chain_rate1\":\"2.2329\",\"chain_rate2\":\"\",\"chain_rate3\":\"\",\"chain_rate4\":\"\"}],\"id\":1}"),
                                                        "{\"command\":\"pools\"}",
                                                        new RpcHandler(
                                                                "{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1593395695,\"Code\":7,\"Msg\":\"3 Pool(s)\",\"Description\":\"ccminer 2.3.3\"}],\"POOLS\":[{\"POOL\":0,\"URL\":\"xxx:8080\",\"Status\":\"Alive\",\"Priority\":0,\"Quota\":0,\"Long Poll\":\"N\",\"Getworks\":820,\"Accepted\":906,\"Rejected\":6,\"Discarded\":12216,\"Stale\":0,\"Get Failures\":0,\"Remote Failures\":0,\"User\":\"xxx\",\"Last Share Time\":\"0:00:08\",\"Diff\":\"  8.0000\",\"Diff1 Shares\":7407,\"Proxy Type\":\"\",\"Proxy\":\"\",\"Difficulty Accepted\":7296.00000000,\"Difficulty Rejected\":48.00000000,\"Difficulty Stale\":0.00000000,\"Last Share Difficulty\":8.00000000,\"Has Stratum\":true,\"Stratum Active\":true,\"Stratum URL\":\"xxx.com\",\"Has GBT\":false,\"Best Share\":\"  0.0000\",\"Pool Rejected%\":0.6536,\"Pool Stale%\":0.0000},{\"POOL\":1,\"URL\":\"\",\"Status\":\"Dead\",\"Priority\":1,\"Quota\":0,\"Long Poll\":\"N\",\"Getworks\":0,\"Accepted\":0,\"Rejected\":0,\"Discarded\":0,\"Stale\":0,\"Get Failures\":0,\"Remote Failures\":0,\"User\":\"\",\"Last Share Time\":\"0\",\"Diff\":\"  0.0000\",\"Diff1 Shares\":0,\"Proxy Type\":\"\",\"Proxy\":\"\",\"Difficulty Accepted\":0.00000000,\"Difficulty Rejected\":0.00000000,\"Difficulty Stale\":0.00000000,\"Last Share Difficulty\":0.00000000,\"Has Stratum\":false,\"Stratum Active\":false,\"Stratum URL\":\"\",\"Has GBT\":false,\"Best Share\":\"  0.0000\",\"Pool Rejected%\":0.0000,\"Pool Stale%\":0.0000},{\"POOL\":2,\"URL\":\"\",\"Status\":\"Dead\",\"Priority\":2,\"Quota\":0,\"Long Poll\":\"N\",\"Getworks\":0,\"Accepted\":0,\"Rejected\":0,\"Discarded\":0,\"Stale\":0,\"Get Failures\":0,\"Remote Failures\":0,\"User\":\"\",\"Last Share Time\":\"0\",\"Diff\":\"  0.0000\",\"Diff1 Shares\":0,\"Proxy Type\":\"\",\"Proxy\":\"\",\"Difficulty Accepted\":0.00000000,\"Difficulty Rejected\":0.00000000,\"Difficulty Stale\":0.00000000,\"Last Share Difficulty\":0.00000000,\"Has Stratum\":false,\"Stratum Active\":false,\"Stratum URL\":\"\",\"Has GBT\":false,\"Best Share\":\"  0.0000\",\"Pool Rejected%\":0.0000,\"Pool Stale%\":0.0000}],\"id\":1}"))),
                                        new FakeHttpMinerServer(
                                                8080,
                                                ImmutableMap.of(
                                                        "/cgi-bin/get_miner_conf.cgi",
                                                        new HttpHandler(
                                                                "",
                                                                "{\n" +
                                                                        "\"pools\" : [\n" +
                                                                        "{\n" +
                                                                        "\"url\" : \"stratum+tcp://dash.f2pool.com:5588\",\n" +
                                                                        "\"user\" : \"xxx\",\n" +
                                                                        "\"pass\" : \"X\"\n" +
                                                                        "},\n" +
                                                                        "{\n" +
                                                                        "\"url\" : \"stratum+tcp://dash.f2pool.com:5588\",\n" +
                                                                        "\"user\" : \"xxx\",\n" +
                                                                        "\"pass\" : \"X\"\n" +
                                                                        "},\n" +
                                                                        "{\n" +
                                                                        "\"url\" : \"stratum+tcp://dash.f2pool.com:5588\",\n" +
                                                                        "\"user\" : \"xxx\",\n" +
                                                                        "\"pass\" : \"X\"\n" +
                                                                        "}\n" +
                                                                        "]\n" +
                                                                        ",\n" +
                                                                        "\"api-listen\": true,\n" +
                                                                        "\"api-network\": true,\n" +
                                                                        "\"freq\": \"400\",\n" +
                                                                        "\"coin-type\": \"ckb\",\n" +
                                                                        "\"api-groups\": \"A:stats:pools:devs:summary:version\",\n" +
                                                                        "\"api-allow\": \"R:0/0,W:127.0.0.1\"\n" +
                                                                        "}",
                                                                BlackminerStatsITest::validateDigest)))),
                                new MinerStats.Builder()
                                        .setApiIp("127.0.0.1")
                                        .setApiPort(4028)
                                        .addPool(
                                                new Pool.Builder()
                                                        .setName("xxx:8080")
                                                        .setWorker("xxx")
                                                        .setPriority(0)
                                                        .setStatus(
                                                                true,
                                                                true)
                                                        .setCounts(
                                                                906,
                                                                6,
                                                                0)
                                                        .build())
                                        .addAsic(
                                                new Asic.Builder()
                                                        .setHashRate(new BigDecimal("2232900000.0000"))
                                                        .setFanInfo(
                                                                new FanInfo.Builder()
                                                                        .setCount(2)
                                                                        .addSpeed(1500)
                                                                        .addSpeed(1500)
                                                                        .setSpeedUnits("RPM")
                                                                        .build())
                                                        .addTemp(44)
                                                        .setPowerState("ckb")
                                                        .addRawStats(
                                                                ImmutableMap.of(
                                                                        "SUMMARY.0.Last getwork",
                                                                        new BigDecimal("1593395696"),
                                                                        "STATS.0.GHS av",
                                                                        "1.5698"))
                                                        .build())
                                        .build()
                        },
                        {
                                // Blackminer F1
                                Arrays.asList(
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
                                        new FakeHttpMinerServer(
                                                8080,
                                                ImmutableMap.of(
                                                        "/cgi-bin/get_miner_conf.cgi",
                                                        new HttpHandler(
                                                                "",
                                                                "{\n" +
                                                                        "\"pools\" : [\n" +
                                                                        "{\n" +
                                                                        "\"url\" : \"stratum+tcp://dash.f2pool.com:5588\",\n" +
                                                                        "\"user\" : \"xxx\",\n" +
                                                                        "\"pass\" : \"X\"\n" +
                                                                        "},\n" +
                                                                        "{\n" +
                                                                        "\"url\" : \"stratum+tcp://dash.f2pool.com:5588\",\n" +
                                                                        "\"user\" : \"xxx\",\n" +
                                                                        "\"pass\" : \"X\"\n" +
                                                                        "},\n" +
                                                                        "{\n" +
                                                                        "\"url\" : \"stratum+tcp://dash.f2pool.com:5588\",\n" +
                                                                        "\"user\" : \"xxx\",\n" +
                                                                        "\"pass\" : \"X\"\n" +
                                                                        "}\n" +
                                                                        "]\n" +
                                                                        ",\n" +
                                                                        "\"api-listen\": true,\n" +
                                                                        "\"api-network\": true,\n" +
                                                                        "\"freq\": \"400\",\n" +
                                                                        "\"coin-type\": \"tellor\",\n" +
                                                                        "\"api-groups\": \"A:stats:pools:devs:summary:version\",\n" +
                                                                        "\"api-allow\": \"R:0/0,W:127.0.0.1\"\n" +
                                                                        "}",
                                                                BlackminerStatsITest::validateDigest)))),
                                new MinerStats.Builder()
                                        .setApiIp("127.0.0.1")
                                        .setApiPort(4028)
                                        .addPool(
                                                new Pool.Builder()
                                                        .setName("my.pool.com:18888")
                                                        .setWorker("x")
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
                                                        .setWorker("x")
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
                                                        .setWorker("x")
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
                                                        .setPowerState("tellor")
                                                        .addRawStats(
                                                                ImmutableMap.of(
                                                                        "SUMMARY.0.Last getwork",
                                                                        new BigDecimal("1544107423"),
                                                                        "STATS.0.GHS av",
                                                                        "14.6359"))
                                                        .build())
                                        .build()
                        },
                        {
                                // Blackminer F1 Ultra
                                Arrays.asList(
                                        new FakeRpcMinerServer(
                                                4028,
                                                ImmutableMap.of(
                                                        "{\"command\":\"summary\"}",
                                                        new RpcHandler(
                                                                "{ \"STATUS\": [ { \"STATUS\": \"S\", \"When\": 1590413031, \"Code\": 11, \"Msg\": \"Summary\", \"Description\": \"ccminer 2.3.3\" } ], \"SUMMARY\": [ { \"Elapsed\": 298732, \"GHS 5s\": \"4.2088\", \"GHS av\": \"4.5009\", \"Found Blocks\": 0, \"Getworks\": 11954, \"Accepted\": \" 12.0000\", \"Rejected\": \" 12.0000\", \"Hardware Errors\": 0, \"Utility\": 2.19, \"Discarded\": 172324, \"Stale\": 0, \"Get Failures\": 0, \"Local Work\": 2309127, \"Remote Failures\": 0, \"Network Blocks\": 515, \"Total MH\": 1344557110, \"Work Utility\": 63.33, \"Difficulty Accepted\": 314944, \"Difficulty Rejected\": 368, \"Difficulty Stale\": 0, \"Best Share\": 17859103, \"Device Hardware%\": 0, \"Device Rejected%\": 0.1167, \"Pool Rejected%\": 0.1167, \"Pool Stale%\": 0, \"Last getwork\": 1590413031 } ], \"id\": 1 }"),
                                                        "{\"command\":\"stats\"}",
                                                        new RpcHandler(
                                                                "{ \"STATUS\": [ { \"STATUS\": \"S\", \"When\": 1590413031, \"Code\": 70, \"Msg\": \"CGMiner stats\", \"Description\": \"ccminer 2.3.3\" } ], \"STATS\": [ { \"CGMiner\": \"2.3.3\", \"Miner\": \"1.3.0.8\", \"CompileTime\": \"2020-04-05 16-27-23 CST\", \"Type\": \"Blackminer F1-ULTRA\" }{ \"STATS\": 0, \"ID\": \"A30\", \"Elapsed\": 298732, \"Calls\": 0, \"Wait\": 0, \"Max\": 0, \"Min\": 99999999, \"GHS 5s\": \"4.2088\", \"GHS av\": \"4.5009\", \"miner_count\": 2, \"frequency\": \"380\", \"fan_num\": 2, \"fan1\": 2820, \"fan2\": 2850, \"temp_num\": 2, \"temp1\": 52, \"chipTemp1\": \"[65.21, 64.10, 62.38, 62.26, 67.06, 69.64]\", \"temp2\": 52, \"chipTemp2\": \"[69.40, 64.35, 63.12, 61.89, 67.55, 71.73]\", \"temp3\": 0, \"chipTemp3\": \"[0.00, 0.00, 0.00, 0.00, 0.00, 0.00]\", \"temp4\": 0, \"chipTemp4\": \"[0.00, 0.00, 0.00, 0.00, 0.00, 0.00]\", \"temp_max\": 52, \"Device Hardware%\": 0, \"no_matching_work\": 0, \"chain_acn1\": 6, \"chain_acn2\": 6, \"chain_acn3\": 0, \"chain_acn4\": 0, \"chain_acs1\": \" oooooo\", \"chain_acs2\": \" oooooo\", \"chain_acs3\": \"\", \"chain_acs4\": \"\", \"chain_hw1\": 0, \"chain_hw2\": 0, \"chain_hw3\": 0, \"chain_hw4\": 0, \"chain_rate1\": \"1.8897\", \"chain_rate2\": \"2.3191\", \"chain_rate3\": \"\", \"chain_rate4\": \"\" } ], \"id\": 1 }"),
                                                        "{\"command\":\"pools\"}",
                                                        new RpcHandler(
                                                                "{ \"STATUS\": [ { \"STATUS\": \"S\", \"When\": 1590413031, \"Code\": 7, \"Msg\": \"3 Pool(s)\", \"Description\": \"ccminer 2.3.3\" } ], \"POOLS\": [ { \"POOL\": 0, \"URL\": \"stratum+tcp://hns.pool.blackminer.com:9052\", \"Status\": \"Alive\", \"Priority\": 0, \"Quota\": 0, \"Long Poll\": \"N\", \"Getworks\": 11952, \"Accepted\": 10921, \"Rejected\": 12, \"Discarded\": 172324, \"Stale\": 0, \"Get Failures\": 0, \"Remote Failures\": 0, \"User\": \"xxx\", \"Last Share Time\": \"0:00:55\", \"Diff\": \" 32.0000\", \"Diff1 Shares\": 313122, \"Proxy Type\": \"\", \"Proxy\": \"\", \"Difficulty Accepted\": 314944.0, \"Difficulty Rejected\": 368.0, \"Difficulty Stale\": 0.0, \"Last Share Difficulty\": 32.0, \"Has Stratum\": true, \"Stratum Active\": true, \"Stratum URL\": \"hns.pool.blackminer.com\", \"Has GBT\": false, \"Best Share\": \" 0.0000\", \"Pool Rejected%\": 0.1167, \"Pool Stale%\": 0.0 }, { \"POOL\": 1, \"URL\": \"stratum+tcp://stratum-us.hnspool.com:3001\", \"Status\": \"Alive\", \"Priority\": 1, \"Quota\": 0, \"Long Poll\": \"N\", \"Getworks\": 2, \"Accepted\": 0, \"Rejected\": 0, \"Discarded\": 0, \"Stale\": 0, \"Get Failures\": 0, \"Remote Failures\": 0, \"User\": \"xxx\", \"Last Share Time\": \"0\", \"Diff\": \" 5.0000\", \"Diff1 Shares\": 1, \"Proxy Type\": \"\", \"Proxy\": \"\", \"Difficulty Accepted\": 0.0, \"Difficulty Rejected\": 0.0, \"Difficulty Stale\": 0.0, \"Last Share Difficulty\": 0.0, \"Has Stratum\": true, \"Stratum Active\": false, \"Stratum URL\": \"\", \"Has GBT\": false, \"Best Share\": \" 0.0000\", \"Pool Rejected%\": 0.0, \"Pool Stale%\": 0.0 }, { \"POOL\": 2, \"URL\": \"\", \"Status\": \"Dead\", \"Priority\": 2, \"Quota\": 0, \"Long Poll\": \"N\", \"Getworks\": 0, \"Accepted\": 0, \"Rejected\": 0, \"Discarded\": 0, \"Stale\": 0, \"Get Failures\": 0, \"Remote Failures\": 0, \"User\": \"\", \"Last Share Time\": \"0\", \"Diff\": \" 0.0000\", \"Diff1 Shares\": 0, \"Proxy Type\": \"\", \"Proxy\": \"\", \"Difficulty Accepted\": 0.0, \"Difficulty Rejected\": 0.0, \"Difficulty Stale\": 0.0, \"Last Share Difficulty\": 0.0, \"Has Stratum\": false, \"Stratum Active\": false, \"Stratum URL\": \"\", \"Has GBT\": false, \"Best Share\": \" 0.0000\", \"Pool Rejected%\": 0.0, \"Pool Stale%\": 0.0 } ], \"id\": 1 }"))),
                                        new FakeHttpMinerServer(
                                                8080,
                                                ImmutableMap.of(
                                                        "/cgi-bin/get_miner_conf.cgi",
                                                        new HttpHandler(
                                                                "",
                                                                "{\n" +
                                                                        "\"pools\" : [\n" +
                                                                        "{\n" +
                                                                        "\"url\" : \"stratum+tcp://dash.f2pool.com:5588\",\n" +
                                                                        "\"user\" : \"xxx\",\n" +
                                                                        "\"pass\" : \"X\"\n" +
                                                                        "},\n" +
                                                                        "{\n" +
                                                                        "\"url\" : \"stratum+tcp://dash.f2pool.com:5588\",\n" +
                                                                        "\"user\" : \"xxx\",\n" +
                                                                        "\"pass\" : \"X\"\n" +
                                                                        "},\n" +
                                                                        "{\n" +
                                                                        "\"url\" : \"stratum+tcp://dash.f2pool.com:5588\",\n" +
                                                                        "\"user\" : \"xxx\",\n" +
                                                                        "\"pass\" : \"X\"\n" +
                                                                        "}\n" +
                                                                        "]\n" +
                                                                        ",\n" +
                                                                        "\"api-listen\": true,\n" +
                                                                        "\"api-network\": true,\n" +
                                                                        "\"freq\": \"400\",\n" +
                                                                        "\"coin-type\": \"tellor\",\n" +
                                                                        "\"api-groups\": \"A:stats:pools:devs:summary:version\",\n" +
                                                                        "\"api-allow\": \"R:0/0,W:127.0.0.1\"\n" +
                                                                        "}",
                                                                BlackminerStatsITest::validateDigest)))),
                                new MinerStats.Builder()
                                        .setApiIp("127.0.0.1")
                                        .setApiPort(4028)
                                        .addPool(
                                                new Pool.Builder()
                                                        .setName("hns.pool.blackminer.com:9052")
                                                        .setWorker("xxx")
                                                        .setPriority(0)
                                                        .setStatus(
                                                                true,
                                                                true)
                                                        .setCounts(
                                                                10921,
                                                                12,
                                                                0)
                                                        .build())
                                        .addPool(
                                                new Pool.Builder()
                                                        .setName("stratum-us.hnspool.com:3001")
                                                        .setWorker("xxx")
                                                        .setPriority(1)
                                                        .setStatus(
                                                                true,
                                                                true)
                                                        .setCounts(
                                                                0,
                                                                0,
                                                                0)
                                                        .build())
                                        .addAsic(
                                                new Asic.Builder()
                                                        .setHashRate(new BigDecimal("4208800000.0000"))
                                                        .setFanInfo(
                                                                new FanInfo.Builder()
                                                                        .setCount(2)
                                                                        .addSpeed(2820)
                                                                        .addSpeed(2850)
                                                                        .setSpeedUnits("RPM")
                                                                        .build())
                                                        .addTemp(52)
                                                        .addTemp(52)
                                                        .addTemp(65)
                                                        .addTemp(64)
                                                        .addTemp(62)
                                                        .addTemp(62)
                                                        .addTemp(67)
                                                        .addTemp(69)
                                                        .addTemp(69)
                                                        .addTemp(64)
                                                        .addTemp(63)
                                                        .addTemp(61)
                                                        .addTemp(67)
                                                        .addTemp(71)
                                                        .setPowerState("tellor")
                                                        .addRawStats(
                                                                ImmutableMap.of(
                                                                        "SUMMARY.0.Last getwork",
                                                                        new BigDecimal("1590413031"),
                                                                        "STATS.0.GHS av",
                                                                        "4.5009"))
                                                        .build())
                                        .build()
                        }
                });
    }

    /**
     * Validates the digest authentication.
     *
     * @param exchange The exchange to validate.
     *
     * @return Whether or not the auth was valid.
     */
    private static boolean validateDigest(final HttpExchange exchange) {
        final Headers headers = exchange.getRequestHeaders();
        return headers
                .entrySet()
                .stream()
                .filter(entry -> "Authorization".equals(entry.getKey()))
                .map(Map.Entry::getValue)
                .anyMatch(header -> {
                    final String headerString = header.get(0);
                    return headerString.contains(
                            "realm=\"blackMiner Configuration\"") &&
                            headerString.contains("nonce");
                });
    }
}
