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
import java.util.Collections;
import java.util.List;
import java.util.Map;

/** Tests whatsminer stats obtaining. */
@RunWith(Parameterized.class)
public class WhatsminerStatsITest
        extends AbstractApiITest {

    /**
     * Constructor.
     *
     * @param handlers       The handlers.
     * @param statsWhitelist The whitelisted stats keys.
     * @param expectedStats  The expected stats.
     */
    public WhatsminerStatsITest(
            final Map<String, HandlerInterface> handlers,
            final List<String> statsWhitelist,
            final MinerStats expectedStats) {
        super(
                new WhatsminerFactory()
                        .create(
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
     *
     * @throws IOException on failure to open file.
     */
    @Parameterized.Parameters
    public static List<Object[]> parameters()
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
                                Arrays.asList(
                                        "STATS.1.chip_freqs_tested_8",
                                        "STATS.1.chip_freqs_tested_9"),
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
                                                        .setHashRate(new BigDecimal("12286819780000.000"))
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
                                                        .addRawStats(
                                                                ImmutableMap.of(
                                                                        "STATS.1.chip_freqs_tested_8",
                                                                        new BigDecimal("636"),
                                                                        "STATS.1.chip_freqs_tested_9",
                                                                        new BigDecimal("636")))
                                                        .build())
                                        .build()
                        },
                        {
                                // Whatsminer M20S (old firmware)
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
                                Arrays.asList(
                                        "STATS.1.chip_freqs_0",
                                        "STATS.1.chip_freqs_1"),
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
                                                        .setHashRate(new BigDecimal("65371302780000.000"))
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
                                                        .addRawStats(
                                                                ImmutableMap.of(
                                                                        "STATS.1.chip_freqs_0",
                                                                        new BigDecimal("648"),
                                                                        "STATS.1.chip_freqs_1",
                                                                        new BigDecimal("660")))
                                                        .build())
                                        .build()
                        },
                        {
                                // Whatsminer M20S (new firmware)
                                ImmutableMap.of(
                                        "{\"command\":\"summary\"}",
                                        new RpcHandler(
                                                "{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1604757733,\"Code\":11,\"Msg\":\"Summary\",\"Description\":\"cgminer 4.9.2\"}],\"SUMMARY\":[{\"Elapsed\":66295,\"MHS av\":68710597.19,\"MHS 5s\":64143777.95,\"MHS 1m\":67266447.69,\"MHS 5m\":67827446.38,\"MHS 15m\":68290183.07,\"Found Blocks\":25,\"Getworks\":10780,\"Accepted\":5018,\"Rejected\":29,\"Hardware Errors\":912,\"Utility\":4.54,\"Discarded\":8046935,\"Stale\":0,\"Get Failures\":0,\"Local Work\":527721521,\"Remote Failures\":0,\"Network Blocks\":4206,\"Total MH\":4555143176276.0000,\"Work Utility\":3749.51,\"Difficulty Accepted\":1071653490.00000000,\"Difficulty Rejected\":5623144.00000000,\"Difficulty Stale\":0.00000000,\"Best Share\":1003626349,\"Temperature\":72.00,\"freq_avg\":622,\"Fan Speed In\":4950,\"Fan Speed Out\":4920,\"Voltage\":1300,\"Power\":3491,\"Power_RT\":3482,\"Device Hardware%\":0.0220,\"Device Rejected%\":135.7304,\"Pool Rejected%\":0.5220,\"Pool Stale%\":0.0000,\"Last getwork\":0,\"Uptime\":66892,\"Chip Data\":\"HP3D04-20010214 C BINV04-192104B\",\"Power Current\":245,\"Power Fanspeed\":8880,\"Error Code Count\":0,\"Factory Error Code Count\":0,\"Security Mode\":0,\"Liquid Cooling\":false,\"Hash Stable\":true,\"Hash Stable Cost Seconds\":1498,\"Hash Deviation%\":-0.3372,\"Target Freq\":622,\"Target MHS\":68765832,\"Env Temp\":28.50,\"Power Mode\":\"Normal\",\"Firmware Version\":\"\\'20200917.22.REL\\'\",\"CB Platform\":\"ALLWINNER_H3\",\"CB Version\":\"V10\",\"MAC\":\"C4:11:04:01:3C:75\",\"Factory GHS\":67051,\"Power Limit\":3500,\"Chip Temp Min\":65.00,\"Chip Temp Max\":87.70,\"Chip Temp Avg\":79.34}],\"id\":1}"),
                                        "{\"command\":\"devs\"}",
                                        new RpcHandler(
                                                "{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1604757733,\"Code\":9,\"Msg\":\"3 ASC(s)\",\"Description\":\"cgminer 4.9.2\"}],\"DEVS\":[{\"ASC\":0,\"Name\":\"SM\",\"ID\":0,\"Slot\":0,\"Enabled\":\"Y\",\"Status\":\"Alive\",\"Temperature\":71.50,\"Chip Frequency\":617,\"Fan Speed In\":4950,\"Fan Speed Out\":4920,\"MHS av\":22711971.14,\"MHS 5s\":19540157.81,\"MHS 1m\":22079665.63,\"MHS 5m\":22534937.07,\"MHS 15m\":22613625.46,\"Accepted\":1626,\"Rejected\":9,\"Hardware Errors\":324,\"Utility\":1.47,\"Last Share Pool\":0,\"Last Share Time\":1604757724,\"Total MH\":1505682090973.0000,\"Diff1 Work\":1369410,\"Difficulty Accepted\":345293896.00000000,\"Difficulty Rejected\":1622162.00000000,\"Last Share Difficulty\":158554.00000000,\"Last Valid Work\":1604757733,\"Device Hardware%\":0.0237,\"Device Rejected%\":118.4570,\"Device Elapsed\":66295,\"Upfreq Complete\":1,\"Effective Chips\":111,\"PCB SN\":\"H3M14S6F200114K10102\",\"Chip Temp Min\":65.00,\"Chip Temp Max\":87.70,\"Chip Temp Avg\":80.36},{\"ASC\":1,\"Name\":\"SM\",\"ID\":1,\"Slot\":1,\"Enabled\":\"Y\",\"Status\":\"Alive\",\"Temperature\":68.50,\"Chip Frequency\":632,\"Fan Speed In\":4950,\"Fan Speed Out\":4920,\"MHS av\":23220108.38,\"MHS 5s\":23015815.92,\"MHS 1m\":23470927.44,\"MHS 5m\":22962220.49,\"MHS 15m\":23064765.42,\"Accepted\":1793,\"Rejected\":13,\"Hardware Errors\":277,\"Utility\":1.62,\"Last Share Pool\":0,\"Last Share Time\":1604757702,\"Total MH\":1539368891060.0000,\"Diff1 Work\":1400047,\"Difficulty Accepted\":384509060.00000000,\"Difficulty Rejected\":2765638.00000000,\"Last Share Difficulty\":158554.00000000,\"Last Valid Work\":1604757733,\"Device Hardware%\":0.0198,\"Device Rejected%\":197.5389,\"Device Elapsed\":66295,\"Upfreq Complete\":1,\"Effective Chips\":111,\"PCB SN\":\"H3M14S6F200114K10107\",\"Chip Temp Min\":66.00,\"Chip Temp Max\":84.50,\"Chip Temp Avg\":76.77},{\"ASC\":2,\"Name\":\"SM\",\"ID\":2,\"Slot\":2,\"Enabled\":\"Y\",\"Status\":\"Alive\",\"Temperature\":72.00,\"Chip Frequency\":619,\"Fan Speed In\":4950,\"Fan Speed Out\":4920,\"MHS av\":22778493.47,\"MHS 5s\":21747874.70,\"MHS 1m\":21710305.01,\"MHS 5m\":22328696.24,\"MHS 15m\":22611254.21,\"Accepted\":1599,\"Rejected\":7,\"Hardware Errors\":311,\"Utility\":1.45,\"Last Share Pool\":0,\"Last Share Time\":1604757717,\"Total MH\":1510092194243.0000,\"Diff1 Work\":1373421,\"Difficulty Accepted\":341850534.00000000,\"Difficulty Rejected\":1235344.00000000,\"Last Share Difficulty\":158554.00000000,\"Last Valid Work\":1604757733,\"Device Hardware%\":0.0226,\"Device Rejected%\":89.9465,\"Device Elapsed\":66295,\"Upfreq Complete\":1,\"Effective Chips\":111,\"PCB SN\":\"H3M14S6F200114K10115\",\"Chip Temp Min\":68.00,\"Chip Temp Max\":87.20,\"Chip Temp Avg\":80.90}],\"id\":1}"),
                                        "{\"command\":\"pools\"}",
                                        new RpcHandler(
                                                "{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1560974720,\"Code\":7,\"Msg\":\"2 Pool(s)\",\"Description\":\"cgminer 4.9.2\"}],\"POOLS\":[{\"POOL\":0,\"URL\":\"stratum+tcp://us-east.stratum.slushpool.com:3333\",\"Status\":\"Alive\",\"Priority\":0,\"Quota\":1,\"Long Poll\":\"N\",\"Getworks\":3512,\"Accepted\":33825,\"Rejected\":185,\"Works\":788544801,\"Discarded\":2580368,\"Stale\":27,\"Get Failures\":1,\"Remote Failures\":0,\"User\":\"002m20s\",\"Last Share Time\":1560974720,\"Diff1 Shares\":1481234400,\"Proxy Type\":\"\",\"Proxy\":\"\",\"Difficulty Accepted\":1473980432.00000000,\"Difficulty Rejected\":8081331.00000000,\"Difficulty Stale\":0.00000000,\"Last Share Difficulty\":49174.00000000,\"Work Difficulty\":49174.00000000,\"Has Stratum\":true,\"Stratum Active\":true,\"Stratum URL\":\"us-east.stratum.slushpool.com\",\"Stratum Difficulty\":49174.00000000,\"Has GBT\":false,\"Best Share\":5452261792,\"Pool Rejected%\":0.5453,\"Pool Stale%\":0.0000,\"Bad Work\":183,\"Current Block Height\":581482,\"Current Block Version\":536870912},{\"POOL\":1,\"URL\":\"stratum+tcp://us-central01.miningrigrentals.com:50194\",\"Status\":\"Alive\",\"Priority\":1,\"Quota\":1,\"Long Poll\":\"N\",\"Getworks\":0,\"Accepted\":0,\"Rejected\":0,\"Works\":0,\"Discarded\":0,\"Stale\":0,\"Get Failures\":0,\"Remote Failures\":0,\"User\":\"xxxx.yyyy\",\"Last Share Time\":0,\"Diff1 Shares\":0,\"Proxy Type\":\"\",\"Proxy\":\"\",\"Difficulty Accepted\":0.00000000,\"Difficulty Rejected\":0.00000000,\"Difficulty Stale\":0.00000000,\"Last Share Difficulty\":0.00000000,\"Work Difficulty\":0.00000000,\"Has Stratum\":true,\"Stratum Active\":false,\"Stratum URL\":\"\",\"Stratum Difficulty\":0.00000000,\"Has GBT\":false,\"Best Share\":0,\"Pool Rejected%\":0.0000,\"Pool Stale%\":0.0000,\"Bad Work\":0,\"Current Block Height\":0,\"Current Block Version\":536870912}],\"id\":1}")),
                                Collections.emptyList(),
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
                                                        .setHashRate(new BigDecimal("64143777950000.000"))
                                                        .setFanInfo(
                                                                new FanInfo.Builder()
                                                                        .setCount(2)
                                                                        .addSpeed(4950)
                                                                        .addSpeed(4920)
                                                                        .setSpeedUnits("RPM")
                                                                        .build())
                                                        .addTemp(71)
                                                        .addTemp(68)
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
