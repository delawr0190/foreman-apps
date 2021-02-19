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
import java.util.*;

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
                                                        .setWorker("xxxxxxxxxx")
                                                        .setStatus(true, false)
                                                        .setPriority(0)
                                                        .setCounts(0, 0, 0)
                                                        .build())
                                        .addPool(
                                                new Pool.Builder()
                                                        .setName("sha256.eu.nicehash.com:3334")
                                                        .setWorker("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx")
                                                        .setStatus(true, true)
                                                        .setPriority(1)
                                                        .setCounts(2628, 1, 2)
                                                        .build())
                                        .addPool(
                                                new Pool.Builder()
                                                        .setName("sha256.usa.nicehash.com:3334")
                                                        .setWorker("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx")
                                                        .setStatus(true, true)
                                                        .setPriority(2)
                                                        .setCounts(0, 0, 0)
                                                        .build())
                                        .addAsic(
                                                new Asic.Builder()
                                                        .setHashRate(new BigDecimal("11763615450000.000"))
                                                        .setBoards(3)
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
                                                        .setWorker("002m20s")
                                                        .setStatus(true, true)
                                                        .setPriority(0)
                                                        .setCounts(33825, 185, 27)
                                                        .build())
                                        .addPool(
                                                new Pool.Builder()
                                                        .setName("us-central01.miningrigrentals.com:50194")
                                                        .setWorker("xxxx.yyyy")
                                                        .setStatus(true, true)
                                                        .setPriority(1)
                                                        .setCounts(0, 0, 0)
                                                        .build())
                                        .addAsic(
                                                new Asic.Builder()
                                                        .setHashRate(new BigDecimal("65492089330000.000"))
                                                        .setBoards(3)
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
                                                        .setWorker("002m20s")
                                                        .setStatus(true, true)
                                                        .setPriority(0)
                                                        .setCounts(33825, 185, 27)
                                                        .build())
                                        .addPool(
                                                new Pool.Builder()
                                                        .setName("us-central01.miningrigrentals.com:50194")
                                                        .setWorker("xxxx.yyyy")
                                                        .setStatus(true, true)
                                                        .setPriority(1)
                                                        .setCounts(0, 0, 0)
                                                        .build())
                                        .addAsic(
                                                new Asic.Builder()
                                                        .setHashRate(new BigDecimal("68710597190000.000"))
                                                        .setBoards(3)
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
                        },
                        {
                                // Whatsminer M31S (new firmware)
                                ImmutableMap.of(
                                        "{\"cmd\":\"summary\"}",
                                        new RpcHandler(
                                                "{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1606349344,\"Code\":11,\"Msg\":\"Summary\",\"Description\":\"cgminer 4.9.2\"}],\"SUMMARY\":[{\"Elapsed\":271950,\"MHS av\":78130188.72,\"MHS 5s\":78398752.21,\"MHS 1m\":78226283.81,\"MHS 5m\":78062260.38,\"MHS 15m\":78035812.17,\"Found Blocks\":0,\"Getworks\":9983,\"Accepted\":57681,\"Rejected\":23,\"Hardware Errors\":9653,\"Utility\":12.73,\"Discarded\":373254,\"Stale\":0,\"Get Failures\":0,\"Local Work\":2771704359,\"Remote Failures\":0,\"Network Blocks\":462,\"Total MH\":21247477854851.0000,\"Work Utility\":17054.14,\"Difficulty Accepted\":4940471970.00000000,\"Difficulty Rejected\":1908584.00000000,\"Difficulty Stale\":0.00000000,\"Best Share\":3846739891,\"Temperature\":70.00,\"freq_avg\":715,\"Fan Speed In\":3330,\"Fan Speed Out\":2760,\"Voltage\":1202,\"Power\":3583,\"Power_RT\":3584,\"Device Hardware%\":0.0125,\"Device Rejected%\":2.4691,\"Pool Rejected%\":0.0386,\"Pool Stale%\":0.0000,\"Last getwork\":0,\"Uptime\":273220,\"Chip Data\":\"HPAP04-20062018 BINV02-193004G\",\"Power Current\":284500,\"Power Fanspeed\":7520,\"Error Code Count\":0,\"Factory Error Code Count\":0,\"Security Mode\":0,\"Liquid Cooling\":false,\"Hash Stable\":true,\"Hash Stable Cost Seconds\":2171,\"Hash Deviation%\":-0.1253,\"Target Freq\":652,\"Target MHS\":71472240,\"Env Temp\":16.00,\"Power Mode\":\"Normal\",\"Firmware Version\":\"'20200722.19.REL'\",\"MAC\":\"C6:06:12:00:BB:AA\",\"Factory GHS\":75993,\"Power Limit\":3600,\"Power Voltage Input\":231.50,\"Power Current Input\":15.58,\"Chip Temp Min\":49.00,\"Chip Temp Max\":94.39,\"Chip Temp Avg\":78.49,\"Debug\":\"14.0_3564_344\"}],\"id\":1}"),
                                        "{\"cmd\":\"edevs\"}",
                                        new RpcHandler(
                                                "{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1606349344,\"Code\":9,\"Msg\":\"3 ASC(s)\",\"Description\":\"cgminer 4.9.2\"}],\"DEVS\":[{\"ASC\":0,\"Name\":\"SM\",\"ID\":0,\"Slot\":0,\"Enabled\":\"Y\",\"Status\":\"Alive\",\"Temperature\":65.50,\"Chip Frequency\":718,\"Fan Speed In\":3330,\"Fan Speed Out\":2760,\"MHS av\":26128368.77,\"MHS 5s\":25432047.72,\"MHS 1m\":26121012.12,\"MHS 5m\":26082216.82,\"MHS 15m\":26075094.58,\"Accepted\":19306,\"Rejected\":5,\"Hardware Errors\":3449,\"Utility\":4.26,\"Last Share Pool\":0,\"Last Share Time\":1606349340,\"Total MH\":7105605360540.0000,\"Diff1 Work\":25850013,\"Difficulty Accepted\":1656092402.00000000,\"Difficulty Rejected\":426521.00000000,\"Last Share Difficulty\":87022.00000000,\"Last Valid Work\":1606349344,\"Device Hardware%\":0.0133,\"Device Rejected%\":1.6500,\"Device Elapsed\":271950,\"Upfreq Complete\":1,\"Effective Chips\":105,\"PCB SN\":\"BAM1FS69630713X30153\",\"Chip Data\":\"HPAP04-20062018 BINV02-193004G\",\"Chip Temp Min\":56.00,\"Chip Temp Max\":86.83,\"Chip Temp Avg\":75.96,\"chip_vol_diff\":14},{\"ASC\":1,\"Name\":\"SM\",\"ID\":1,\"Slot\":1,\"Enabled\":\"Y\",\"Status\":\"Alive\",\"Temperature\":65.50,\"Chip Frequency\":708,\"Fan Speed In\":3330,\"Fan Speed Out\":2760,\"MHS av\":25775347.26,\"MHS 5s\":25589195.43,\"MHS 1m\":25597242.82,\"MHS 5m\":25678145.03,\"MHS 15m\":25720275.43,\"Accepted\":18941,\"Rejected\":7,\"Hardware Errors\":3198,\"Utility\":4.18,\"Last Share Pool\":0,\"Last Share Time\":1606349340,\"Total MH\":7009601228708.0000,\"Diff1 Work\":25500772,\"Difficulty Accepted\":1617996433.00000000,\"Difficulty Rejected\":573971.00000000,\"Last Share Difficulty\":87022.00000000,\"Last Valid Work\":1606349344,\"Device Hardware%\":0.0125,\"Device Rejected%\":2.2508,\"Device Elapsed\":271950,\"Upfreq Complete\":1,\"Effective Chips\":105,\"PCB SN\":\"BAM1FS69630713X30150\",\"Chip Data\":\"HPAP04-20062018 BINV02-193004G\",\"Chip Temp Min\":56.00,\"Chip Temp Max\":85.67,\"Chip Temp Avg\":75.17,\"chip_vol_diff\":13},{\"ASC\":2,\"Name\":\"SM\",\"ID\":2,\"Slot\":2,\"Enabled\":\"Y\",\"Status\":\"Alive\",\"Temperature\":70.00,\"Chip Frequency\":719,\"Fan Speed In\":3330,\"Fan Speed Out\":2760,\"MHS av\":26226459.61,\"MHS 5s\":26729036.38,\"MHS 1m\":26446959.21,\"MHS 5m\":26290334.58,\"MHS 15m\":26236481.74,\"Accepted\":19434,\"Rejected\":11,\"Hardware Errors\":3006,\"Utility\":4.29,\"Last Share Pool\":0,\"Last Share Time\":1606349335,\"Total MH\":7132281161207.0000,\"Diff1 Work\":25947054,\"Difficulty Accepted\":1666383135.00000000,\"Difficulty Rejected\":908092.00000000,\"Last Share Difficulty\":87022.00000000,\"Last Valid Work\":1606349344,\"Device Hardware%\":0.0116,\"Device Rejected%\":3.4998,\"Device Elapsed\":271950,\"Upfreq Complete\":1,\"Effective Chips\":105,\"PCB SN\":\"B5M1FS69630717K31306\",\"Chip Data\":\"HP5A04-20070734 BINV02-193004G\",\"Chip Temp Min\":49.00,\"Chip Temp Max\":94.39,\"Chip Temp Avg\":84.34,\"chip_vol_diff\":14}],\"id\":1}"),
                                        "{\"cmd\":\"pools\"}",
                                        new RpcHandler(
                                                "{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1606349344,\"Code\":7,\"Msg\":\"3 Pool(s)\",\"Description\":\"cgminer 4.9.2\"}],\"POOLS\":[{\"POOL\":0,\"URL\":\"stratum+tcp://ru-west.stratum.slushpool.com:3333\",\"Status\":\"Alive\",\"Priority\":0,\"Quota\":1,\"Long Poll\":\"N\",\"Getworks\":9983,\"Accepted\":57681,\"Rejected\":23,\"Works\":-1520006932,\"Discarded\":373254,\"Stale\":0,\"Get Failures\":0,\"Remote Failures\":0,\"User\":\"xxx\",\"Last Share Time\":1606349340,\"Diff1 Shares\":77297833,\"Proxy Type\":\"\",\"Proxy\":\"\",\"Difficulty Accepted\":4940471970.00000000,\"Difficulty Rejected\":1908584.00000000,\"Difficulty Stale\":0.00000000,\"Last Share Difficulty\":87022.00000000,\"Work Difficulty\":0.00000000,\"Has Stratum\":true,\"Stratum Active\":true,\"Stratum URL\":\"ru-west.stratum.slushpool.com\",\"Stratum Difficulty\":87022.00000000,\"Has GBT\":false,\"Best Share\":3846739891,\"Pool Rejected%\":0.0386,\"Pool Stale%\":0.0000,\"Bad Work\":462,\"Current Block Height\":658678,\"Current Block Version\":536870912},{\"POOL\":1,\"URL\":\"stratum+tcp://eu.stratum.slushpool.com:3333\",\"Status\":\"Alive\",\"Priority\":1,\"Quota\":1,\"Long Poll\":\"N\",\"Getworks\":0,\"Accepted\":0,\"Rejected\":0,\"Works\":0,\"Discarded\":0,\"Stale\":0,\"Get Failures\":0,\"Remote Failures\":0,\"User\":\"xxx\",\"Last Share Time\":0,\"Diff1 Shares\":0,\"Proxy Type\":\"\",\"Proxy\":\"\",\"Difficulty Accepted\":0.00000000,\"Difficulty Rejected\":0.00000000,\"Difficulty Stale\":0.00000000,\"Last Share Difficulty\":0.00000000,\"Work Difficulty\":0.00000000,\"Has Stratum\":true,\"Stratum Active\":false,\"Stratum URL\":\"\",\"Stratum Difficulty\":0.00000000,\"Has GBT\":false,\"Best Share\":0,\"Pool Rejected%\":0.0000,\"Pool Stale%\":0.0000,\"Bad Work\":0,\"Current Block Height\":0,\"Current Block Version\":536870912},{\"POOL\":2,\"URL\":\"stratum+tcp://eu.stratum.slushpool.com:3333\",\"Status\":\"Alive\",\"Priority\":2,\"Quota\":1,\"Long Poll\":\"N\",\"Getworks\":0,\"Accepted\":0,\"Rejected\":0,\"Works\":0,\"Discarded\":0,\"Stale\":0,\"Get Failures\":0,\"Remote Failures\":0,\"User\":\"xxx\",\"Last Share Time\":0,\"Diff1 Shares\":0,\"Proxy Type\":\"\",\"Proxy\":\"\",\"Difficulty Accepted\":0.00000000,\"Difficulty Rejected\":0.00000000,\"Difficulty Stale\":0.00000000,\"Last Share Difficulty\":0.00000000,\"Work Difficulty\":0.00000000,\"Has Stratum\":true,\"Stratum Active\":false,\"Stratum URL\":\"\",\"Stratum Difficulty\":0.00000000,\"Has GBT\":false,\"Best Share\":0,\"Pool Rejected%\":0.0000,\"Pool Stale%\":0.0000,\"Bad Work\":0,\"Current Block Height\":0,\"Current Block Version\":536870912}],\"id\":1}")),
                                Collections.emptyList(),
                                new MinerStats.Builder()
                                        .setApiIp("127.0.0.1")
                                        .setApiPort(4028)
                                        .addPool(
                                                new Pool.Builder()
                                                        .setName("ru-west.stratum.slushpool.com:3333")
                                                        .setWorker("xxx")
                                                        .setStatus(true, true)
                                                        .setPriority(0)
                                                        .setCounts(57681, 23, 0)
                                                        .build())
                                        .addPool(
                                                new Pool.Builder()
                                                        .setName("eu.stratum.slushpool.com:3333")
                                                        .setWorker("xxx")
                                                        .setStatus(true, true)
                                                        .setPriority(1)
                                                        .setCounts(0, 0, 0)
                                                        .build())
                                        .addPool(
                                                new Pool.Builder()
                                                        .setName("eu.stratum.slushpool.com:3333")
                                                        .setWorker("xxx")
                                                        .setStatus(true, true)
                                                        .setPriority(2)
                                                        .setCounts(0, 0, 0)
                                                        .build())
                                        .addAsic(
                                                new Asic.Builder()
                                                        .setHashRate(new BigDecimal("78130188720000.000"))
                                                        .setBoards(3)
                                                        .setFanInfo(
                                                                new FanInfo.Builder()
                                                                        .setCount(2)
                                                                        .addSpeed(3330)
                                                                        .addSpeed(2760)
                                                                        .setSpeedUnits("RPM")
                                                                        .build())
                                                        .addTemp(65)
                                                        .addTemp(65)
                                                        .addTemp(70)
                                                        .hasErrors(false)
                                                        .build())
                                        .build()
                        },
                        {
                                // Whatsminer M30S (new firmware)
                                toM30Handlers(),
                                Collections.emptyList(),
                                new MinerStats.Builder()
                                        .setApiIp("127.0.0.1")
                                        .setApiPort(4028)
                                        .addPool(
                                                new Pool.Builder()
                                                        .setName("sha256asicboost.eu.nicehash.com:3368")
                                                        .setWorker("xxx")
                                                        .setStatus(true, true)
                                                        .setPriority(0)
                                                        .setCounts(1739, 1, 0)
                                                        .build())
                                        .addPool(
                                                new Pool.Builder()
                                                        .setName("sha256asicboost.hk.nicehash.com:3368")
                                                        .setWorker("xxx")
                                                        .setStatus(true, true)
                                                        .setPriority(1)
                                                        .setCounts(0, 0, 0)
                                                        .build())
                                        .addPool(
                                                new Pool.Builder()
                                                        .setName("eu.stratum.slushpool.com:3333")
                                                        .setWorker("xxx")
                                                        .setStatus(true, true)
                                                        .setPriority(2)
                                                        .setCounts(0, 0, 0)
                                                        .build())
                                        .addAsic(
                                                new Asic.Builder()
                                                        .setHashRate(new BigDecimal("85106399370000.000"))
                                                        .setBoards(3)
                                                        .setFanInfo(
                                                                new FanInfo.Builder()
                                                                        .setCount(2)
                                                                        .addSpeed(2370)
                                                                        .addSpeed(2430)
                                                                        .setSpeedUnits("RPM")
                                                                        .build())
                                                        .addTemp(80)
                                                        .addTemp(72)
                                                        .addTemp(72)
                                                        .hasErrors(false)
                                                        .build())
                                        .build()
                        },
                        {
                                // Whatsminer M31S (new firmware)
                                ImmutableMap.of(
                                        "{\"command\":\"summary\"}",
                                        new RpcHandler(
                                                "{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1612175432,\"Code\":11,\"Msg\":\"Summary\",\"Description\":\"btminer\"}],\"SUMMARY\":[{\"Elapsed\":33102,\"MHS av\":76718604.38,\"MHS 5s\":92768288.94,\"MHS 1m\":77909448.60,\"MHS 5m\":76943560.94,\"MHS 15m\":76766726.75,\"HS RT\":76690808.44,\"Found Blocks\":0,\"Getworks\":1367,\"Accepted\":2296,\"Rejected\":1,\"Hardware Errors\":0,\"Utility\":4.16,\"Discarded\":0,\"Stale\":0,\"Get Failures\":0,\"Local Work\":629698680,\"Remote Failures\":0,\"Network Blocks\":170,\"Total MH\":2539516358593.0000,\"Work Utility\":0.00,\"Difficulty Accepted\":588906496.00000000,\"Difficulty Rejected\":262144.00000000,\"Difficulty Stale\":0.00000000,\"Best Share\":1320888597,\"Temperature\":72.00,\"freq_avg\":710,\"Fan Speed In\":5700,\"Fan Speed Out\":0,\"Voltage\":1206,\"Power\":3553,\"Power_RT\":3552,\"Power Rate\":46.31,\"Device Hardware%\":0.0000,\"Device Rejected%\":0.0000,\"Pool Rejected%\":0.0445,\"Pool Stale%\":0.0000,\"Last getwork\":0,\"Uptime\":1405529,\"Chip Data\":\"HPJA05-20070601 BINV02-193005F\",\"Power Current\":276.626346,\"Power Fanspeed\":7608,\"Error Code 0\":110,\"Error 0 Time\":\"\",\"Error Code 1\":111,\"Error 1 Time\":\"\",\"Error Code 2\":131,\"Error 2 Time\":\"\",\"Factory Error Code 0\":208,\"Error 3 Time\":\"\",\"Factory Error Code 1\":2310,\"Error 4 Time\":\"\",\"Error Code Count\":3,\"Factory Error Code Count\":2,\"Security Mode\":0,\"Liquid Cooling\":false,\"Hash Stable\":true,\"Hash Stable Cost Seconds\":1373628,\"Hash Deviation%\":0.0086,\"Target Freq\":652,\"Target MHS\":71472240,\"Env Temp\":7.00,\"Power Mode\":\"High\",\"Firmware Version\":\"'20201202.22.REL'\",\"CB Platform\":\"ALLWINNER_H6OS\",\"CB Version\":\"V10\",\"MAC\":\"C6:06:12:00:2D:63\",\"Factory GHS\":75697,\"Power Limit\":3600,\"Power Voltage Input\":229.50,\"Power Current Input\":15.58,\"Chip Temp Min\":48.00,\"Chip Temp Max\":93.00,\"Chip Temp Avg\":73.87,\"Debug\":\"76.5/77.9T_46.4W_100.3/98.5_-3\"}],\"id\":1}"),
                                        "{\"command\":\"edevs\"}",
                                        new RpcHandler(
                                                "{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1612175433,\"Code\":9,\"Msg\":\"3 ASC(s)\",\"Description\":\"btminer\"}],\"DEVS\":[{\"ASC\":0,\"Name\":\"SM\",\"ID\":0,\"Slot\":0,\"Enabled\":\"Y\",\"Status\":\"Alive\",\"Temperature\":57.50,\"Chip Frequency\":686,\"Fan Speed In\":5700,\"Fan Speed Out\":0,\"MHS av\":24731566.18,\"MHS 5s\":25012892.74,\"MHS 1m\":24720718.63,\"MHS 5m\":24741199.08,\"MHS 15m\":24731544.59,\"Accepted\":756,\"Rejected\":0,\"Hardware Errors\":0,\"Utility\":1.37,\"Last Share Pool\":0,\"Last Share Time\":1612175429,\"Total MH\":818666585854.0000,\"Diff1 Work\":0,\"Difficulty Accepted\":194772992.00000000,\"Difficulty Rejected\":0.00000000,\"Last Share Difficulty\":262144.00000000,\"Last Valid Work\":1612175430,\"Device Hardware%\":0.0000,\"Device Rejected%\":0.0000,\"Device Elapsed\":33102,\"Upfreq Complete\":1,\"Effective Chips\":105,\"PCB SN\":\"BJM1FS69630721K31122\",\"Chip Data\":\"HPJA05-20070601 BINV02-193005F\",\"Chip Temp Min\":48.00,\"Chip Temp Max\":77.44,\"Chip Temp Avg\":66.30,\"chip_vol_diff\":11},{\"ASC\":1,\"Name\":\"SM\",\"ID\":1,\"Slot\":1,\"Enabled\":\"Y\",\"Status\":\"Alive\",\"Temperature\":72.00,\"Chip Frequency\":726,\"Fan Speed In\":5700,\"Fan Speed Out\":0,\"MHS av\":26094168.35,\"MHS 5s\":26032487.18,\"MHS 1m\":26026621.49,\"MHS 5m\":26091737.63,\"MHS 15m\":26087691.64,\"Accepted\":777,\"Rejected\":1,\"Hardware Errors\":0,\"Utility\":1.41,\"Last Share Pool\":0,\"Last Share Time\":1612175356,\"Total MH\":863771576430.0000,\"Diff1 Work\":0,\"Difficulty Accepted\":199229440.00000000,\"Difficulty Rejected\":262144.00000000,\"Last Share Difficulty\":262144.00000000,\"Last Valid Work\":1612175433,\"Device Hardware%\":0.0000,\"Device Rejected%\":0.0000,\"Device Elapsed\":33102,\"Upfreq Complete\":1,\"Effective Chips\":105,\"PCB SN\":\"BJM1FS69630721K31108\",\"Chip Data\":\"HPJA05-20070601 BINV02-193005F\",\"Chip Temp Min\":60.00,\"Chip Temp Max\":93.00,\"Chip Temp Avg\":79.89,\"chip_vol_diff\":19},{\"ASC\":2,\"Name\":\"SM\",\"ID\":2,\"Slot\":2,\"Enabled\":\"Y\",\"Status\":\"Alive\",\"Temperature\":68.00,\"Chip Frequency\":718,\"Fan Speed In\":5700,\"Fan Speed Out\":0,\"MHS av\":25891964.07,\"MHS 5s\":25944861.70,\"MHS 1m\":25862805.06,\"MHS 5m\":25851462.21,\"MHS 15m\":25861873.60,\"Accepted\":763,\"Rejected\":0,\"Hardware Errors\":0,\"Utility\":1.38,\"Last Share Pool\":0,\"Last Share Time\":1612175363,\"Total MH\":857078196309.0000,\"Diff1 Work\":0,\"Difficulty Accepted\":194904064.00000000,\"Difficulty Rejected\":0.00000000,\"Last Share Difficulty\":262144.00000000,\"Last Valid Work\":1612175432,\"Device Hardware%\":0.0000,\"Device Rejected%\":0.0000,\"Device Elapsed\":33102,\"Upfreq Complete\":1,\"Effective Chips\":105,\"PCB SN\":\"x\",\"Chip Data\":\"HPJA05-20070601 BINV02-193005F\",\"Chip Temp Min\":54.00,\"Chip Temp Max\":89.17,\"Chip Temp Avg\":75.43,\"chip_vol_diff\":12}],\"id\":1}"),
                                        "{\"command\":\"pools\"}",
                                        new RpcHandler(
                                                "{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1612175433,\"Code\":7,\"Msg\":\"3 Pool(s)\",\"Description\":\"btminer\"}],\"POOLS\":[{\"POOL\":1,\"URL\":\"stratum+tcp://btc.ss.poolin.com:443\",\"Status\":\"Alive\",\"Priority\":0,\"Quota\":1,\"Long Poll\":\"N\",\"Getworks\":1367,\"Accepted\":2296,\"Rejected\":1,\"Works\":897985052,\"Discarded\":0,\"Stale\":0,\"Get Failures\":0,\"Remote Failures\":0,\"User\":\"x.12x22\",\"Last Share Time\":1612175429,\"Diff1 Shares\":0,\"Proxy Type\":\"\",\"Proxy\":\"\",\"Difficulty Accepted\":588906496.00000000,\"Difficulty Rejected\":262144.00000000,\"Difficulty Stale\":0.00000000,\"Last Share Difficulty\":262144.00000000,\"Work Difficulty\":0.00000000,\"Has Stratum\":true,\"Stratum Active\":true,\"Stratum URL\":\"x.ss.poolin.com\",\"Stratum Difficulty\":262144.00000000,\"Has GBT\":false,\"Best Share\":1320888597,\"Pool Rejected%\":0.0445,\"Pool Stale%\":0.0000,\"Bad Work\":188,\"Current Block Height\":668600,\"Current Block Version\":536870912},{\"POOL\":2,\"URL\":\"stratum+tcp://btc.ss.poolin.com:1883\",\"Status\":\"Alive\",\"Priority\":1,\"Quota\":1,\"Long Poll\":\"N\",\"Getworks\":0,\"Accepted\":0,\"Rejected\":0,\"Works\":0,\"Discarded\":0,\"Stale\":0,\"Get Failures\":0,\"Remote Failures\":0,\"User\":\"x.12x22\",\"Last Share Time\":0,\"Diff1 Shares\":0,\"Proxy Type\":\"\",\"Proxy\":\"\",\"Difficulty Accepted\":0.00000000,\"Difficulty Rejected\":0.00000000,\"Difficulty Stale\":0.00000000,\"Last Share Difficulty\":0.00000000,\"Work Difficulty\":0.00000000,\"Has Stratum\":true,\"Stratum Active\":false,\"Stratum URL\":\"\",\"Stratum Difficulty\":0.00000000,\"Has GBT\":false,\"Best Share\":0,\"Pool Rejected%\":0.0000,\"Pool Stale%\":0.0000,\"Bad Work\":0,\"Current Block Height\":0,\"Current Block Version\":536870912},{\"POOL\":3,\"URL\":\"stratum+tcp://btc.ss.poolin.com:25\",\"Status\":\"Alive\",\"Priority\":2,\"Quota\":1,\"Long Poll\":\"N\",\"Getworks\":0,\"Accepted\":0,\"Rejected\":0,\"Works\":0,\"Discarded\":0,\"Stale\":0,\"Get Failures\":0,\"Remote Failures\":0,\"User\":\"x.12x22\",\"Last Share Time\":0,\"Diff1 Shares\":0,\"Proxy Type\":\"\",\"Proxy\":\"\",\"Difficulty Accepted\":0.00000000,\"Difficulty Rejected\":0.00000000,\"Difficulty Stale\":0.00000000,\"Last Share Difficulty\":0.00000000,\"Work Difficulty\":0.00000000,\"Has Stratum\":true,\"Stratum Active\":false,\"Stratum URL\":\"\",\"Stratum Difficulty\":0.00000000,\"Has GBT\":false,\"Best Share\":0,\"Pool Rejected%\":0.0000,\"Pool Stale%\":0.0000,\"Bad Work\":0,\"Current Block Height\":0,\"Current Block Version\":536870912}],\"id\":1}")),
                                Collections.emptyList(),
                                new MinerStats.Builder()
                                        .setApiIp("127.0.0.1")
                                        .setApiPort(4028)
                                        .addPool(
                                                new Pool.Builder()
                                                        .setName("btc.ss.poolin.com:443")
                                                        .setWorker("x.12x22")
                                                        .setStatus(true, true)
                                                        .setPriority(0)
                                                        .setCounts(2296, 1, 0)
                                                        .build())
                                        .addPool(
                                                new Pool.Builder()
                                                        .setName("btc.ss.poolin.com:1883")
                                                        .setWorker("x.12x22")
                                                        .setStatus(true, true)
                                                        .setPriority(1)
                                                        .setCounts(0, 0, 0)
                                                        .build())
                                        .addPool(
                                                new Pool.Builder()
                                                        .setName("btc.ss.poolin.com:25")
                                                        .setWorker("x.12x22")
                                                        .setStatus(true, true)
                                                        .setPriority(2)
                                                        .setCounts(0, 0, 0)
                                                        .build())
                                        .addAsic(
                                                new Asic.Builder()
                                                        .setHashRate(new BigDecimal("76718604380000.000"))
                                                        .setBoards(3)
                                                        .setPowerMode(Asic.PowerMode.HIGH)
                                                        .setFanInfo(
                                                                new FanInfo.Builder()
                                                                        .setCount(2)
                                                                        .addSpeed(5700)
                                                                        .setSpeedUnits("RPM")
                                                                        .build())
                                                        .addTemp(57)
                                                        .addTemp(72)
                                                        .addTemp(68)
                                                        .hasErrors(false)
                                                        .build())
                                        .build()
                        },
                        {
                                // Whatsminer M31S (sleeping)
                                ImmutableMap.of(
                                        "{\"cmd\":\"get_token\"}",
                                        new RpcHandler(
                                                "{\"STATUS\":\"S\",\"When\":1608417125,\"Code\":134,\"Msg\":{\"time\":\"6915\",\"salt\":\"BQ5hoXV9\",\"newsalt\":\"a5TtWui2\"},\"Description\":\"whatsminer v1.1\"}"),
                                        "{\"enc\":1,\"data\":\"3HjZpFnhS5g7OS3RRLzgaMipdasSiL3vVkn9nuJfQANZx8ucVffVFrwa0QxM8GxeZnz4wRGOwv9EXSwX+shB4w==\"}",
                                        new RpcHandler(
                                                "{\"enc\":\"e+iVEHt3XaqHeCA8EK8LNJYHagIBVlZBKWRLDLZ129gdwfRg5OKIwYRqIf7tATRDtBTFC21dQ1R1MaV8rA3QAirirR/h2mk8tmq9/fd+/kZooJBpUfNkKk3C/AcNe7b/ChH9cP3Z6gHu23+PYM1Uksdwhchdpb4xLhmZjyEzTaqpPcn3RoQepra8PL9bwOjL\"}")),
                                Collections.emptyList(),
                                new MinerStats.Builder()
                                        .setApiIp("127.0.0.1")
                                        .setApiPort(4028)
                                        .addAsic(
                                                new Asic.Builder()
                                                        .setHashRate(BigDecimal.ZERO)
                                                        .setPowerMode(Asic.PowerMode.SLEEPING)
                                                        .setFanInfo(
                                                                new FanInfo.Builder()
                                                                        .setCount(0)
                                                                        .setSpeedUnits("RPM")
                                                                        .build())
                                                        .build())
                                        .build()
                        }
                });
    }

    /**
     * Creates the handlers for the M30S test.
     *
     * @return The handlers.
     */
    private static Map<String, RpcHandler> toM30Handlers() {
        final Map<String, RpcHandler> handlerMap = new HashMap<>();
        handlerMap.put(
                "{\"cmd\":\"summary\"}",
                new RpcHandler(
                        "{\"STATUS\":[{\"STATUS\":\"E\",\"When\":1606354149,\"Code\":24,\"Msg\":\"Missing JSON 'command'\",\"Description\":\"cgminer 4.9.2\"}],\"id\":1}"));
        handlerMap.put(
                "{\"cmd\":\"edevs\"}",
                new RpcHandler(
                        "{\"STATUS\":[{\"STATUS\":\"E\",\"When\":1606354149,\"Code\":24,\"Msg\":\"Missing JSON 'command'\",\"Description\":\"cgminer 4.9.2\"}],\"id\":1}"));
        handlerMap.put(
                "{\"cmd\":\"pools\"}",
                new RpcHandler(
                        "{\"STATUS\":[{\"STATUS\":\"E\",\"When\":1606354149,\"Code\":24,\"Msg\":\"Missing JSON 'command'\",\"Description\":\"cgminer 4.9.2\"}],\"id\":1}"));
        handlerMap.put(
                "{\"command\":\"summary\"}",
                new RpcHandler(
                        "{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1606354856,\"Code\":11,\"Msg\":\"Summary\",\"Description\":\"cgminer 4.9.2\"}],\"SUMMARY\":[{\"Elapsed\":29600,\"MHS av\":85106399.37,\"MHS 5s\":85038430.01,\"MHS 1m\":85163473.24,\"MHS 5m\":84990167.25,\"MHS 15m\":84931083.51,\"Found Blocks\":0,\"Getworks\":1093,\"Accepted\":1739,\"Rejected\":1,\"Hardware Errors\":800,\"Utility\":3.53,\"Discarded\":803946,\"Stale\":0,\"Get Failures\":0,\"Local Work\":297895844,\"Remote Failures\":0,\"Network Blocks\":52,\"Total MH\":2519134032188.0000,\"Work Utility\":18576.89,\"Difficulty Accepted\":619526749.59468699,\"Difficulty Rejected\":537084.30827500,\"Difficulty Stale\":0.00000000,\"Best Share\":887052932,\"Temperature\":80.00,\"freq_avg\":528,\"Fan Speed In\":2370,\"Fan Speed Out\":2430,\"Voltage\":1238,\"Power\":3230,\"Power_RT\":3232,\"Device Hardware%\":0.0087,\"Device Rejected%\":5.8605,\"Pool Rejected%\":0.0866,\"Pool Stale%\":0.0000,\"Last getwork\":0,\"Uptime\":43805,\"Chip Data\":\"HP5A05-20043018 D02 BINV02-193005B\",\"Power Current\":249500,\"Power Fanspeed\":7576,\"Factory Error Code 0\":202,\"Error Code 0\":205,\"Factory Error Code 1\":2310,\"Error Code Count\":1,\"Factory Error Code Count\":2,\"Security Mode\":0,\"Liquid Cooling\":false,\"Hash Stable\":true,\"Hash Stable Cost Seconds\":15106,\"Hash Deviation%\":-0.0214,\"Target Freq\":527,\"Target MHS\":85829328,\"Env Temp\":11.00,\"Power Mode\":\"Normal\",\"Firmware Version\":\"'20200512.13.REL'\",\"MAC\":\"C6:02:14:00:1F:B3\",\"Factory GHS\":86472,\"Power Limit\":3600,\"Power Voltage Input\":228.75,\"Power Current Input\":14.23,\"Chip Temp Min\":50.00,\"Chip Temp Max\":99.79,\"Chip Temp Avg\":76.95}],\"id\":1}"));
        handlerMap.put(
                "{\"command\":\"edevs\"}",
                new RpcHandler(
                        "{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1606354856,\"Code\":9,\"Msg\":\"3 ASC(s)\",\"Description\":\"cgminer 4.9.2\"}],\"DEVS\":[{\"ASC\":0,\"Name\":\"SM\",\"ID\":0,\"Slot\":0,\"Enabled\":\"Y\",\"Status\":\"Alive\",\"Temperature\":80.00,\"Chip Frequency\":546,\"Fan Speed In\":2370,\"Fan Speed Out\":2430,\"MHS av\":29376598.53,\"MHS 5s\":29637403.56,\"MHS 1m\":29648873.76,\"MHS 5m\":29454749.23,\"MHS 15m\":29380183.23,\"Accepted\":608,\"Rejected\":1,\"Hardware Errors\":396,\"Utility\":1.23,\"Last Share Pool\":0,\"Last Share Time\":1606354768,\"Total MH\":869545124659.0000,\"Diff1 Work\":3163380,\"Difficulty Accepted\":211879759.61430547,\"Difficulty Rejected\":537084.30827500,\"Last Share Difficulty\":537084.30827500,\"Last Valid Work\":1606354856,\"Device Hardware%\":0.0125,\"Device Rejected%\":16.9782,\"Device Elapsed\":29600,\"Upfreq Complete\":1,\"Effective Chips\":156,\"PCB SN\":\"H5M1ES9C200507X10110\",\"Chip Temp Min\":55.00,\"Chip Temp Max\":99.79,\"Chip Temp Avg\":82.49,\"chip_vol_diff\":11},{\"ASC\":1,\"Name\":\"SM\",\"ID\":1,\"Slot\":1,\"Enabled\":\"Y\",\"Status\":\"Alive\",\"Temperature\":72.00,\"Chip Frequency\":519,\"Fan Speed In\":2370,\"Fan Speed Out\":2430,\"MHS av\":27875946.58,\"MHS 5s\":27335216.82,\"MHS 1m\":27740213.77,\"MHS 5m\":27850351.14,\"MHS 15m\":27803222.73,\"Accepted\":554,\"Rejected\":0,\"Hardware Errors\":195,\"Utility\":1.12,\"Last Share Pool\":0,\"Last Share Time\":1606354804,\"Total MH\":825125955055.0000,\"Diff1 Work\":3001794,\"Difficulty Accepted\":201540886.68002751,\"Difficulty Rejected\":0.00000000,\"Last Share Difficulty\":537084.30827500,\"Last Valid Work\":1606354856,\"Device Hardware%\":0.0065,\"Device Rejected%\":0.0000,\"Device Elapsed\":29600,\"Upfreq Complete\":1,\"Effective Chips\":156,\"PCB SN\":\"H5M1ES9C200507X10273\",\"Chip Temp Min\":50.00,\"Chip Temp Max\":89.14,\"Chip Temp Avg\":73.46,\"chip_vol_diff\":11},{\"ASC\":2,\"Name\":\"SM\",\"ID\":2,\"Slot\":2,\"Enabled\":\"Y\",\"Status\":\"Alive\",\"Temperature\":72.50,\"Chip Frequency\":518,\"Fan Speed In\":2370,\"Fan Speed Out\":2430,\"MHS av\":27853546.90,\"MHS 5s\":26400703.96,\"MHS 1m\":27622875.12,\"MHS 5m\":27665444.89,\"MHS 15m\":27744325.03,\"Accepted\":577,\"Rejected\":0,\"Hardware Errors\":209,\"Utility\":1.17,\"Last Share Pool\":0,\"Last Share Time\":1606354806,\"Total MH\":824462952474.0000,\"Diff1 Work\":2999391,\"Difficulty Accepted\":206106103.30035469,\"Difficulty Rejected\":0.00000000,\"Last Share Difficulty\":537084.30827500,\"Last Valid Work\":1606354856,\"Device Hardware%\":0.0070,\"Device Rejected%\":0.0000,\"Device Elapsed\":29600,\"Upfreq Complete\":1,\"Effective Chips\":156,\"PCB SN\":\"H5M1ES9C200507X10272\",\"Chip Temp Min\":52.00,\"Chip Temp Max\":90.64,\"Chip Temp Avg\":74.90,\"chip_vol_diff\":10}],\"id\":1}"));
        handlerMap.put(
                "{\"command\":\"pools\"}",
                new RpcHandler(
                        "{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1606354856,\"Code\":7,\"Msg\":\"3 Pool(s)\",\"Description\":\"cgminer 4.9.2\"}],\"POOLS\":[{\"POOL\":0,\"URL\":\"stratum+tcp://sha256asicboost.eu.nicehash.com:3368\",\"Status\":\"Alive\",\"Priority\":0,\"Quota\":1,\"Long Poll\":\"N\",\"Getworks\":1093,\"Accepted\":1739,\"Rejected\":1,\"Works\":300434278,\"Discarded\":803946,\"Stale\":0,\"Get Failures\":0,\"Remote Failures\":0,\"User\":\"xxx\",\"Last Share Time\":1606354806,\"Diff1 Shares\":9164560,\"Proxy Type\":\"\",\"Proxy\":\"\",\"Difficulty Accepted\":619526749.59468699,\"Difficulty Rejected\":537084.30827500,\"Difficulty Stale\":0.00000000,\"Last Share Difficulty\":537084.30827500,\"Work Difficulty\":0.00000000,\"Has Stratum\":true,\"Stratum Active\":true,\"Stratum URL\":\"sha256asicboost.eu.nicehash.com\",\"Stratum Difficulty\":537084.30827500,\"Has GBT\":false,\"Best Share\":887052932,\"Pool Rejected%\":0.0866,\"Pool Stale%\":0.0000,\"Bad Work\":1134,\"Current Block Height\":662908,\"Current Block Version\":536870912},{\"POOL\":1,\"URL\":\"stratum+tcp://sha256asicboost.hk.nicehash.com:3368\",\"Status\":\"Alive\",\"Priority\":1,\"Quota\":1,\"Long Poll\":\"N\",\"Getworks\":0,\"Accepted\":0,\"Rejected\":0,\"Works\":0,\"Discarded\":0,\"Stale\":0,\"Get Failures\":0,\"Remote Failures\":0,\"User\":\"xxx\",\"Last Share Time\":0,\"Diff1 Shares\":0,\"Proxy Type\":\"\",\"Proxy\":\"\",\"Difficulty Accepted\":0.00000000,\"Difficulty Rejected\":0.00000000,\"Difficulty Stale\":0.00000000,\"Last Share Difficulty\":0.00000000,\"Work Difficulty\":0.00000000,\"Has Stratum\":true,\"Stratum Active\":false,\"Stratum URL\":\"\",\"Stratum Difficulty\":0.00000000,\"Has GBT\":false,\"Best Share\":0,\"Pool Rejected%\":0.0000,\"Pool Stale%\":0.0000,\"Bad Work\":2,\"Current Block Height\":662857,\"Current Block Version\":536870912},{\"POOL\":2,\"URL\":\"stratum+tcp://eu.stratum.slushpool.com:3333\",\"Status\":\"Alive\",\"Priority\":2,\"Quota\":1,\"Long Poll\":\"N\",\"Getworks\":0,\"Accepted\":0,\"Rejected\":0,\"Works\":0,\"Discarded\":0,\"Stale\":0,\"Get Failures\":0,\"Remote Failures\":0,\"User\":\"xxx\",\"Last Share Time\":0,\"Diff1 Shares\":0,\"Proxy Type\":\"\",\"Proxy\":\"\",\"Difficulty Accepted\":0.00000000,\"Difficulty Rejected\":0.00000000,\"Difficulty Stale\":0.00000000,\"Last Share Difficulty\":0.00000000,\"Work Difficulty\":0.00000000,\"Has Stratum\":true,\"Stratum Active\":false,\"Stratum URL\":\"\",\"Stratum Difficulty\":0.00000000,\"Has GBT\":false,\"Best Share\":0,\"Pool Rejected%\":0.0000,\"Pool Stale%\":0.0000,\"Bad Work\":0,\"Current Block Height\":0,\"Current Block Version\":536870912}],\"id\":1}"));
        return handlerMap;
    }
}
