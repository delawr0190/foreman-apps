package mn.foreman.whatsminer;

import mn.foreman.model.miners.FanInfo;
import mn.foreman.model.miners.MinerStats;
import mn.foreman.model.miners.Pool;
import mn.foreman.model.miners.asic.Asic;
import mn.foreman.util.AbstractApiITest;
import mn.foreman.util.rpc.FakeRpcMinerServer;
import mn.foreman.util.rpc.HandlerInterface;
import mn.foreman.util.rpc.RpcHandler;

import com.google.common.collect.ImmutableMap;
import org.apache.commons.io.IOUtils;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

/** Tests whatsminer stats obtaining. */
@RunWith(Parameterized.class)
public class WhatsminerStatsITest
        extends AbstractApiITest {

    /**
     * Constructor.
     *
     * @param handlers      The handlers.
     * @param expectedStats The expected stats.
     */
    public WhatsminerStatsITest(
            final Map<String, HandlerInterface> handlers,
            final MinerStats expectedStats) {
        super(
                new WhatsminerFactory()
                        .create(
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
     *
     * @throws IOException on failure to open file.
     */
    @Parameterized.Parameters
    public static Collection parameters()
            throws IOException {
        return Arrays.asList(
                new Object[][]{
                        {
                                // Whatsminer M3
                                ImmutableMap.of(
                                        "{\"command\":\"summary\"}",
                                        new RpcHandler(
                                                "{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1538960082,\"Code\":11,\"Msg\":\"Summary\",\"Description\":\"cgminer 4.9.2\"}],\"SUMMARY\":[{\"Elapsed\":43685,\"MHS av\":11763615.45,\"MHS 5s\":12286819.78,\"MHS 1m\":12239079.72,\"MHS 5m\":11927279.30,\"MHS 15m\":11820625.45,\"Found Blocks\":0,\"Getworks\":1683,\"Accepted\":218,\"Rejected\":0,\"Hardware Errors\":394,\"Utility\":0.30,\"Discarded\":311865,\"Stale\":0,\"Get Failures\":52,\"Local Work\":161170250,\"Remote Failures\":0,\"Network Blocks\":390,\"Total MH\":513892815043.0000,\"Work Utility\":641.94,\"Difficulty Accepted\":123000000.00000000,\"Difficulty Rejected\":0.00000000,\"Difficulty Stale\":0.00000000,\"Best Share\":371521357,\"Temperature\":85.50,\"freq_max\":636,\"freq_min\":635,\"freq_avg\":635,\"Fan Speed In\":5730,\"Fan Speed Out\":5880,\"fan_stop_turn_off\":false,\"fan_stop_count\":0,\"fan_stop_turnoff_threshold\":5,\"fan_stop_turnoff_times\":0,\"fan_speed_set\":5502,\"fan_speed_set_limit\":6299,\"fan_over_fast_count\":0,\"fan_set_get_err_turnoff_count\":0,\"fan_set_get_err_turnoff_threshold\":15,\"Voltage\":8,\"Device Hardware%\":0.0842,\"Device Rejected%\":0.0000,\"Pool Rejected%\":0.0000,\"Pool Stale%\":0.0000,\"Last getwork\":1538960082}],\"id\":1}"),
                                        "{\"command\":\"stats\"}",
                                        new RpcHandler(
                                                IOUtils.toString(
                                                        WhatsminerStatsITest.class.getResourceAsStream(
                                                                "/m3.stats.json"),
                                                        Charset.defaultCharset())),
                                        "{\"command\":\"pools\"}",
                                        new RpcHandler(
                                                "{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1538857307,\"Code\":7,\"Msg\":\"3 Pool(s)\",\"Description\":\"cgminer 4.9.2\"}],\"POOLS\":[{\"POOL\":0,\"URL\":\"stratum+tcp://bitcoin.1viabtc.com:3333\",\"Status\":\"Dead\",\"Priority\":0,\"Quota\":1,\"Long Poll\":\"N\",\"Getworks\":0,\"Accepted\":0,\"Rejected\":0,\"Works\":0,\"Discarded\":0,\"Stale\":0,\"Get Failures\":0,\"Remote Failures\":0,\"User\":\"xxxxxxxxxx\",\"Last Share Time\":0,\"Diff1 Shares\":0,\"Proxy Type\":\"\",\"Proxy\":\"\",\"Difficulty Accepted\":0.00000000,\"Difficulty Rejected\":0.00000000,\"Difficulty Stale\":0.00000000,\"Last Share Difficulty\":0.00000000,\"Work Difficulty\":0.00000000,\"Has Stratum\":true,\"Stratum Active\":false,\"Stratum URL\":\"\",\"Stratum Difficulty\":0.00000000,\"Has GBT\":false,\"Best Share\":0,\"Pool Rejected%\":0.0000,\"Pool Stale%\":0.0000,\"Bad Work\":0,\"Current Block Height\":0,\"Current Block Version\":0},{\"POOL\":1,\"URL\":\"stratum+tcp://sha256.eu.nicehash.com:3334\",\"Status\":\"Alive\",\"Priority\":1,\"Quota\":1,\"Long Poll\":\"N\",\"Getworks\":21837,\"Accepted\":2628,\"Rejected\":1,\"Works\":2027198831,\"Discarded\":4045504,\"Stale\":2,\"Get Failures\":942,\"Remote Failures\":0,\"User\":\"xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx\",\"Last Share Time\":1538857024,\"Diff1 Shares\":5857645,\"Proxy Type\":\"\",\"Proxy\":\"\",\"Difficulty Accepted\":1514000000.00000000,\"Difficulty Rejected\":1000000.00000000,\"Difficulty Stale\":1500000.00000000,\"Last Share Difficulty\":500000.00000000,\"Work Difficulty\":500000.00000000,\"Has Stratum\":true,\"Stratum Active\":true,\"Stratum URL\":\"sha256.eu.nicehash.com\",\"Stratum Difficulty\":500000.00000000,\"Has GBT\":false,\"Best Share\":4233816245,\"Pool Rejected%\":0.0659,\"Pool Stale%\":0.0989,\"Bad Work\":4593,\"Current Block Height\":544658,\"Current Block Version\":536870912},{\"POOL\":2,\"URL\":\"stratum+tcp://sha256.usa.nicehash.com:3334\",\"Status\":\"Alive\",\"Priority\":2,\"Quota\":1,\"Long Poll\":\"N\",\"Getworks\":0,\"Accepted\":0,\"Rejected\":0,\"Works\":0,\"Discarded\":0,\"Stale\":0,\"Get Failures\":0,\"Remote Failures\":0,\"User\":\"xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx\",\"Last Share Time\":0,\"Diff1 Shares\":0,\"Proxy Type\":\"\",\"Proxy\":\"\",\"Difficulty Accepted\":0.00000000,\"Difficulty Rejected\":0.00000000,\"Difficulty Stale\":0.00000000,\"Last Share Difficulty\":0.00000000,\"Work Difficulty\":0.00000000,\"Has Stratum\":true,\"Stratum Active\":false,\"Stratum URL\":\"\",\"Stratum Difficulty\":0.00000000,\"Has GBT\":false,\"Best Share\":0,\"Pool Rejected%\":0.0000,\"Pool Stale%\":0.0000,\"Bad Work\":0,\"Current Block Height\":0,\"Current Block Version\":0}],\"id\":1}")),
                                new MinerStats.Builder()
                                        .setApiIp("127.0.0.1")
                                        .setApiPort(4028)
                                        .addPool(
                                                new Pool.Builder()
                                                        .setName("bitcoin.1viabtc.com:3333")
                                                        .setStatus(true, false)
                                                        .setPriority(0)
                                                        .setCounts(0, 0, 0)
                                                        .build())
                                        .addPool(
                                                new Pool.Builder()
                                                        .setName("sha256.eu.nicehash.com:3334")
                                                        .setStatus(true, true)
                                                        .setPriority(1)
                                                        .setCounts(2628, 1, 2)
                                                        .build())
                                        .addPool(
                                                new Pool.Builder()
                                                        .setName("sha256.usa.nicehash.com:3334")
                                                        .setStatus(true, true)
                                                        .setPriority(2)
                                                        .setCounts(0, 0, 0)
                                                        .build())
                                        .addAsic(
                                                new Asic.Builder()
                                                        .setHashRate(new BigDecimal("12286819780000.00"))
                                                        .setFanInfo(
                                                                new FanInfo.Builder()
                                                                        .setCount(2)
                                                                        .addSpeed(5730)
                                                                        .addSpeed(5880)
                                                                        .setSpeedUnits("RPM")
                                                                        .build())
                                                        .addTemp(85)
                                                        .addTemp(75)
                                                        .addTemp(75)
                                                        .hasErrors(true)
                                                        .build())
                                        .build()
                        },
                        {
                                // Whatsminer M20S
                                ImmutableMap.of(
                                        "{\"command\":\"summary\"}",
                                        new RpcHandler(
                                                "{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1560974720,\"Code\":11,\"Msg\":\"Summary\",\"Description\":\"cgminer 4.9.2\"}],\"SUMMARY\":[{\"Elapsed\":97139,\"MHS av\":65492089.33,\"MHS 5s\":65371302.78,\"MHS 1m\":65463822.08,\"MHS 5m\":65599323.93,\"MHS 15m\":65496891.24,\"Found Blocks\":0,\"Getworks\":3512,\"Accepted\":33825,\"Rejected\":185,\"Hardware Errors\":1601,\"Utility\":20.89,\"Discarded\":2580546,\"Stale\":27,\"Get Failures\":1,\"Local Work\":788804989,\"Remote Failures\":0,\"Network Blocks\":181,\"Total MH\":6361827211172.0000,\"Work Utility\":914916.96,\"Difficulty Accepted\":1473980432.00000000,\"Difficulty Rejected\":8081331.00000000,\"Difficulty Stale\":0.00000000,\"Best Share\":5452261792,\"Temperature\":75.00,\"freq_max\":656,\"freq_min\":616,\"freq_avg\":636,\"Fan Speed In\":4590,\"Fan Speed Out\":4560,\"Voltage\":1170,\"Device Hardware%\":0.0001,\"Device Rejected%\":0.5456,\"Pool Rejected%\":0.5453,\"Pool Stale%\":0.0000,\"Last getwork\":0,\"Uptime\":98801,\"Chip Data\":\"K46K241-1918 BIN2-B\",\"Power Current\":258,\"Power Fanspeed\":8160,\"Error Code Count\":0,\"Factory Error Code Count\":0,\"Security Mode\":0,\"Asicboost\":true,\"Asicboost Works\":4,\"Liquid Cooling\":false}],\"id\":1}"),
                                        "{\"command\":\"stats\"}",
                                        new RpcHandler(
                                                IOUtils.toString(
                                                        WhatsminerStatsITest.class.getResourceAsStream(
                                                                "/m20s.stats.json"),
                                                        Charset.defaultCharset())),
                                        "{\"command\":\"pools\"}",
                                        new RpcHandler(
                                                "{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1560974720,\"Code\":7,\"Msg\":\"2 Pool(s)\",\"Description\":\"cgminer 4.9.2\"}],\"POOLS\":[{\"POOL\":0,\"URL\":\"stratum+tcp://us-east.stratum.slushpool.com:3333\",\"Status\":\"Alive\",\"Priority\":0,\"Quota\":1,\"Long Poll\":\"N\",\"Getworks\":3512,\"Accepted\":33825,\"Rejected\":185,\"Works\":788544801,\"Discarded\":2580368,\"Stale\":27,\"Get Failures\":1,\"Remote Failures\":0,\"User\":\"002m20s\",\"Last Share Time\":1560974720,\"Diff1 Shares\":1481234400,\"Proxy Type\":\"\",\"Proxy\":\"\",\"Difficulty Accepted\":1473980432.00000000,\"Difficulty Rejected\":8081331.00000000,\"Difficulty Stale\":0.00000000,\"Last Share Difficulty\":49174.00000000,\"Work Difficulty\":49174.00000000,\"Has Stratum\":true,\"Stratum Active\":true,\"Stratum URL\":\"us-east.stratum.slushpool.com\",\"Stratum Difficulty\":49174.00000000,\"Has GBT\":false,\"Best Share\":5452261792,\"Pool Rejected%\":0.5453,\"Pool Stale%\":0.0000,\"Bad Work\":183,\"Current Block Height\":581482,\"Current Block Version\":536870912},{\"POOL\":1,\"URL\":\"stratum+tcp://us-central01.miningrigrentals.com:50194\",\"Status\":\"Alive\",\"Priority\":1,\"Quota\":1,\"Long Poll\":\"N\",\"Getworks\":0,\"Accepted\":0,\"Rejected\":0,\"Works\":0,\"Discarded\":0,\"Stale\":0,\"Get Failures\":0,\"Remote Failures\":0,\"User\":\"xxxx.yyyy\",\"Last Share Time\":0,\"Diff1 Shares\":0,\"Proxy Type\":\"\",\"Proxy\":\"\",\"Difficulty Accepted\":0.00000000,\"Difficulty Rejected\":0.00000000,\"Difficulty Stale\":0.00000000,\"Last Share Difficulty\":0.00000000,\"Work Difficulty\":0.00000000,\"Has Stratum\":true,\"Stratum Active\":false,\"Stratum URL\":\"\",\"Stratum Difficulty\":0.00000000,\"Has GBT\":false,\"Best Share\":0,\"Pool Rejected%\":0.0000,\"Pool Stale%\":0.0000,\"Bad Work\":0,\"Current Block Height\":0,\"Current Block Version\":536870912}],\"id\":1}")),
                                new MinerStats.Builder()
                                        .setApiIp("127.0.0.1")
                                        .setApiPort(4028)
                                        .addPool(
                                                new Pool.Builder()
                                                        .setName("us-east.stratum.slushpool.com:3333")
                                                        .setStatus(true, true)
                                                        .setPriority(0)
                                                        .setCounts(33825, 185, 27)
                                                        .build())
                                        .addPool(
                                                new Pool.Builder()
                                                        .setName("us-central01.miningrigrentals.com:50194")
                                                        .setStatus(true, true)
                                                        .setPriority(1)
                                                        .setCounts(0, 0, 0)
                                                        .build())
                                        .addAsic(
                                                new Asic.Builder()
                                                        .setHashRate(new BigDecimal("65371302780000.00"))
                                                        .setFanInfo(
                                                                new FanInfo.Builder()
                                                                        .setCount(2)
                                                                        .addSpeed(4590)
                                                                        .addSpeed(4560)
                                                                        .setSpeedUnits("RPM")
                                                                        .build())
                                                        .addTemp(75)
                                                        .addTemp(73)
                                                        .addTemp(72)
                                                        .hasErrors(false)
                                                        .addAttribute(
                                                                "mrr_rig_id",
                                                                "yyyy")
                                                        .build())
                                        .build()
                        }
                });
    }
}
