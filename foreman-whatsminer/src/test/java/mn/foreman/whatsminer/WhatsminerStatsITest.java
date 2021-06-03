package mn.foreman.whatsminer;

import mn.foreman.model.ApplicationConfiguration;
import mn.foreman.model.miners.FanInfo;
import mn.foreman.model.miners.MinerStats;
import mn.foreman.model.miners.Pool;
import mn.foreman.model.miners.asic.Asic;
import mn.foreman.util.AbstractApiITest;
import mn.foreman.util.rpc.FakeRpcMinerServer;
import mn.foreman.util.rpc.HandlerInterface;
import mn.foreman.util.rpc.RpcHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
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

    /** The mapper for creating JSON. */
    private static final ObjectMapper MAPPER = new ObjectMapper();

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
                new WhatsminerFactory(new ApplicationConfiguration())
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
                                                                        .setCount(3)
                                                                        .addSpeed(4590)
                                                                        .addSpeed(4560)
                                                                        .addSpeed(8160)
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
                                                                        .setCount(3)
                                                                        .addSpeed(4950)
                                                                        .addSpeed(4920)
                                                                        .addSpeed(8880)
                                                                        .setSpeedUnits("RPM")
                                                                        .build())
                                                        .addTemp(28)
                                                        .addTemp(71)
                                                        .addTemp(80)
                                                        .addTemp(68)
                                                        .addTemp(76)
                                                        .addTemp(72)
                                                        .addTemp(80)
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
                                        "{\"cmd\":\"pools+summary+edevs\"}",
                                        new RpcHandler(
                                                MAPPER.writeValueAsString(
                                                        ImmutableMap.of(
                                                                "pools",
                                                                Collections.singletonList(
                                                                        MAPPER.readValue(
                                                                                "{\n" +
                                                                                        "      \"STATUS\": [\n" +
                                                                                        "        {\n" +
                                                                                        "          \"STATUS\": \"S\",\n" +
                                                                                        "          \"When\": 1615249622,\n" +
                                                                                        "          \"Code\": 7,\n" +
                                                                                        "          \"Msg\": \"1 Pool(s)\",\n" +
                                                                                        "          \"Description\": \"btminer\"\n" +
                                                                                        "        }\n" +
                                                                                        "      ],\n" +
                                                                                        "      \"POOLS\": [\n" +
                                                                                        "        {\n" +
                                                                                        "          \"POOL\": 1,\n" +
                                                                                        "          \"URL\": \"stratum+tcp://btc.luxor.tech:700\",\n" +
                                                                                        "          \"Status\": \"Alive\",\n" +
                                                                                        "          \"Priority\": 0,\n" +
                                                                                        "          \"Quota\": 1,\n" +
                                                                                        "          \"Long Poll\": \"N\",\n" +
                                                                                        "          \"Getworks\": 56850,\n" +
                                                                                        "          \"Accepted\": 457459,\n" +
                                                                                        "          \"Rejected\": 518,\n" +
                                                                                        "          \"Works\": 1868213477,\n" +
                                                                                        "          \"Discarded\": 0,\n" +
                                                                                        "          \"Stale\": 95,\n" +
                                                                                        "          \"Get Failures\": 7,\n" +
                                                                                        "          \"Remote Failures\": 1,\n" +
                                                                                        "          \"User\": \"xxx\",\n" +
                                                                                        "          \"Last Share Time\": 1615249620,\n" +
                                                                                        "          \"Diff1 Shares\": 0,\n" +
                                                                                        "          \"Proxy Type\": \"\",\n" +
                                                                                        "          \"Proxy\": \"\",\n" +
                                                                                        "          \"Difficulty Accepted\": 28279321096,\n" +
                                                                                        "          \"Difficulty Rejected\": 31537951,\n" +
                                                                                        "          \"Difficulty Stale\": 0,\n" +
                                                                                        "          \"Last Share Difficulty\": 54954,\n" +
                                                                                        "          \"Work Difficulty\": 0,\n" +
                                                                                        "          \"Has Stratum\": true,\n" +
                                                                                        "          \"Stratum Active\": true,\n" +
                                                                                        "          \"Stratum URL\": \"btc.luxor.tech\",\n" +
                                                                                        "          \"Stratum Difficulty\": 54954,\n" +
                                                                                        "          \"Has GBT\": false,\n" +
                                                                                        "          \"Best Share\": 54727919944,\n" +
                                                                                        "          \"Pool Rejected%\": 0.1114,\n" +
                                                                                        "          \"Pool Stale%\": 0,\n" +
                                                                                        "          \"Bad Work\": 3126,\n" +
                                                                                        "          \"Current Block Height\": 673786,\n" +
                                                                                        "          \"Current Block Version\": 536870912\n" +
                                                                                        "        }\n" +
                                                                                        "      ],\n" +
                                                                                        "      \"id\": 1\n" +
                                                                                        "    }",
                                                                                Map.class)),
                                                                "summary",
                                                                Collections.singletonList(
                                                                        MAPPER.readValue(
                                                                                "{\n" +
                                                                                        "      \"STATUS\": [\n" +
                                                                                        "        {\n" +
                                                                                        "          \"STATUS\": \"S\",\n" +
                                                                                        "          \"When\": 1615249622,\n" +
                                                                                        "          \"Code\": 11,\n" +
                                                                                        "          \"Msg\": \"Summary\",\n" +
                                                                                        "          \"Description\": \"btminer\"\n" +
                                                                                        "        }\n" +
                                                                                        "      ],\n" +
                                                                                        "      \"SUMMARY\": [\n" +
                                                                                        "        {\n" +
                                                                                        "          \"Elapsed\": 1216997,\n" +
                                                                                        "          \"MHS av\": 99793646.89,\n" +
                                                                                        "          \"MHS 5s\": 116472006.19,\n" +
                                                                                        "          \"MHS 1m\": 97878725.92,\n" +
                                                                                        "          \"MHS 5m\": 96877159.81,\n" +
                                                                                        "          \"MHS 15m\": 96805940.25,\n" +
                                                                                        "          \"HS RT\": 96417231.65,\n" +
                                                                                        "          \"Found Blocks\": 0,\n" +
                                                                                        "          \"Getworks\": 56850,\n" +
                                                                                        "          \"Accepted\": 457459,\n" +
                                                                                        "          \"Rejected\": 518,\n" +
                                                                                        "          \"Hardware Errors\": 0,\n" +
                                                                                        "          \"Utility\": 22.55,\n" +
                                                                                        "          \"Discarded\": 0,\n" +
                                                                                        "          \"Stale\": 95,\n" +
                                                                                        "          \"Get Failures\": 7,\n" +
                                                                                        "          \"Local Work\": 622329437,\n" +
                                                                                        "          \"Remote Failures\": 1,\n" +
                                                                                        "          \"Network Blocks\": 2090,\n" +
                                                                                        "          \"Total MH\": 121448583250369,\n" +
                                                                                        "          \"Work Utility\": 0,\n" +
                                                                                        "          \"Difficulty Accepted\": 28279321096,\n" +
                                                                                        "          \"Difficulty Rejected\": 31537951,\n" +
                                                                                        "          \"Difficulty Stale\": 0,\n" +
                                                                                        "          \"Best Share\": 54727919944,\n" +
                                                                                        "          \"Temperature\": 80,\n" +
                                                                                        "          \"freq_avg\": 438,\n" +
                                                                                        "          \"Fan Speed In\": 3870,\n" +
                                                                                        "          \"Fan Speed Out\": 3930,\n" +
                                                                                        "          \"Voltage\": 1288,\n" +
                                                                                        "          \"Power\": 3496,\n" +
                                                                                        "          \"Power_RT\": 3488,\n" +
                                                                                        "          \"Power Rate\": 35.03,\n" +
                                                                                        "          \"Device Hardware%\": 0,\n" +
                                                                                        "          \"Device Rejected%\": 0,\n" +
                                                                                        "          \"Pool Rejected%\": 0.1114,\n" +
                                                                                        "          \"Pool Stale%\": 0,\n" +
                                                                                        "          \"Last getwork\": 0,\n" +
                                                                                        "          \"Uptime\": 1224515,\n" +
                                                                                        "          \"Power Current\": 254.172494,\n" +
                                                                                        "          \"Power Fanspeed\": 8784,\n" +
                                                                                        "          \"Error Code 0\": 206,\n" +
                                                                                        "          \"Error 0 Time\": \"\",\n" +
                                                                                        "          \"Error Code 1\": 2320,\n" +
                                                                                        "          \"Error 1 Time\": \"\",\n" +
                                                                                        "          \"Factory Error Code 0\": 2340,\n" +
                                                                                        "          \"Error 2 Time\": \"\",\n" +
                                                                                        "          \"Error Code Count\": 2,\n" +
                                                                                        "          \"Factory Error Code Count\": 1,\n" +
                                                                                        "          \"Security Mode\": 0,\n" +
                                                                                        "          \"Liquid Cooling\": false,\n" +
                                                                                        "          \"Hash Stable\": true,\n" +
                                                                                        "          \"Hash Stable Cost Seconds\": 8718,\n" +
                                                                                        "          \"Hash Deviation%\": -2.1361,\n" +
                                                                                        "          \"Target Freq\": 437,\n" +
                                                                                        "          \"Target MHS\": 98089020,\n" +
                                                                                        "          \"Power Mode\": \"High\",\n" +
                                                                                        "          \"Firmware Version\": \"'20210109.22.REL'\",\n" +
                                                                                        "          \"CB Platform\": \"ALLWINNER_H6\",\n" +
                                                                                        "          \"CB Version\": \"V2\",\n" +
                                                                                        "          \"MAC\": \"C4:05:09:00:02:9B\",\n" +
                                                                                        "          \"Factory GHS\": 100999,\n" +
                                                                                        "          \"Power Limit\": 3600,\n" +
                                                                                        "          \"Power Voltage Input\": 199.25,\n" +
                                                                                        "          \"Power Current Input\": 17.53,\n" +
                                                                                        "          \"Chip Temp Min\": 43,\n" +
                                                                                        "          \"Chip Temp Max\": 94.95,\n" +
                                                                                        "          \"Chip Temp Avg\": 82.17,\n" +
                                                                                        "          \"Debug\": \"102.3/103.7T_34.5W_94.6/93.3_31\"\n" +
                                                                                        "        }\n" +
                                                                                        "      ],\n" +
                                                                                        "      \"id\": 1\n" +
                                                                                        "    }",
                                                                                Map.class)),
                                                                "edevs",
                                                                Collections.singletonList(
                                                                        MAPPER.readValue(
                                                                                "{\n" +
                                                                                        "      \"STATUS\": [\n" +
                                                                                        "        {\n" +
                                                                                        "          \"STATUS\": \"S\",\n" +
                                                                                        "          \"When\": 1615249622,\n" +
                                                                                        "          \"Code\": 9,\n" +
                                                                                        "          \"Msg\": \"3 ASC(s)\",\n" +
                                                                                        "          \"Description\": \"btminer\"\n" +
                                                                                        "        }\n" +
                                                                                        "      ],\n" +
                                                                                        "      \"DEVS\": [\n" +
                                                                                        "        {\n" +
                                                                                        "          \"ASC\": 0,\n" +
                                                                                        "          \"Name\": \"SM\",\n" +
                                                                                        "          \"ID\": 0,\n" +
                                                                                        "          \"Slot\": 0,\n" +
                                                                                        "          \"Enabled\": \"Y\",\n" +
                                                                                        "          \"Status\": \"Alive\",\n" +
                                                                                        "          \"Temperature\": 79.5,\n" +
                                                                                        "          \"Chip Frequency\": 414,\n" +
                                                                                        "          \"Fan Speed In\": 3870,\n" +
                                                                                        "          \"Fan Speed Out\": 3930,\n" +
                                                                                        "          \"MHS av\": 32682582.82,\n" +
                                                                                        "          \"MHS 5s\": 31120845.74,\n" +
                                                                                        "          \"MHS 1m\": 30322743.05,\n" +
                                                                                        "          \"MHS 5m\": 30595972.39,\n" +
                                                                                        "          \"MHS 15m\": 30608365.4,\n" +
                                                                                        "          \"Accepted\": 149860,\n" +
                                                                                        "          \"Rejected\": 165,\n" +
                                                                                        "          \"Hardware Errors\": 0,\n" +
                                                                                        "          \"Utility\": 7.39,\n" +
                                                                                        "          \"Last Share Pool\": 0,\n" +
                                                                                        "          \"Last Share Time\": 1615249612,\n" +
                                                                                        "          \"Total MH\": 39774630449612,\n" +
                                                                                        "          \"Diff1 Work\": 0,\n" +
                                                                                        "          \"Difficulty Accepted\": 9263607043,\n" +
                                                                                        "          \"Difficulty Rejected\": 10018230,\n" +
                                                                                        "          \"Last Share Difficulty\": 54954,\n" +
                                                                                        "          \"Last Valid Work\": 1615249621,\n" +
                                                                                        "          \"Device Hardware%\": 0,\n" +
                                                                                        "          \"Device Rejected%\": 0,\n" +
                                                                                        "          \"Device Elapsed\": 1216998,\n" +
                                                                                        "          \"Upfreq Complete\": 1,\n" +
                                                                                        "          \"Effective Chips\": 215,\n" +
                                                                                        "          \"PCB SN\": \"xxx\",\n" +
                                                                                        "          \"Chip Data\": \"HP2A02-20021812 BINV02-193002B\",\n" +
                                                                                        "          \"Chip Temp Min\": 63,\n" +
                                                                                        "          \"Chip Temp Max\": 94.95,\n" +
                                                                                        "          \"Chip Temp Avg\": 83.68,\n" +
                                                                                        "          \"chip_vol_diff\": 14\n" +
                                                                                        "        },\n" +
                                                                                        "        {\n" +
                                                                                        "          \"ASC\": 1,\n" +
                                                                                        "          \"Name\": \"SM\",\n" +
                                                                                        "          \"ID\": 1,\n" +
                                                                                        "          \"Slot\": 1,\n" +
                                                                                        "          \"Enabled\": \"Y\",\n" +
                                                                                        "          \"Status\": \"Alive\",\n" +
                                                                                        "          \"Temperature\": 80,\n" +
                                                                                        "          \"Chip Frequency\": 444,\n" +
                                                                                        "          \"Fan Speed In\": 3870,\n" +
                                                                                        "          \"Fan Speed Out\": 3930,\n" +
                                                                                        "          \"MHS av\": 33607194.59,\n" +
                                                                                        "          \"MHS 5s\": 33993448.66,\n" +
                                                                                        "          \"MHS 1m\": 32639168.66,\n" +
                                                                                        "          \"MHS 5m\": 32602309.25,\n" +
                                                                                        "          \"MHS 15m\": 32647354.85,\n" +
                                                                                        "          \"Accepted\": 154032,\n" +
                                                                                        "          \"Rejected\": 194,\n" +
                                                                                        "          \"Hardware Errors\": 0,\n" +
                                                                                        "          \"Utility\": 7.59,\n" +
                                                                                        "          \"Last Share Pool\": 0,\n" +
                                                                                        "          \"Last Share Time\": 1615249600,\n" +
                                                                                        "          \"Total MH\": 40899880920562,\n" +
                                                                                        "          \"Diff1 Work\": 0,\n" +
                                                                                        "          \"Difficulty Accepted\": 9520424805,\n" +
                                                                                        "          \"Difficulty Rejected\": 11733656,\n" +
                                                                                        "          \"Last Share Difficulty\": 54954,\n" +
                                                                                        "          \"Last Valid Work\": 1615249622,\n" +
                                                                                        "          \"Device Hardware%\": 0,\n" +
                                                                                        "          \"Device Rejected%\": 0,\n" +
                                                                                        "          \"Device Elapsed\": 1216998,\n" +
                                                                                        "          \"Upfreq Complete\": 1,\n" +
                                                                                        "          \"Effective Chips\": 215,\n" +
                                                                                        "          \"PCB SN\": \"xxx\",\n" +
                                                                                        "          \"Chip Data\": \"HP2A02-20021812 BINV02-193002B\",\n" +
                                                                                        "          \"Chip Temp Min\": 62,\n" +
                                                                                        "          \"Chip Temp Max\": 94.9,\n" +
                                                                                        "          \"Chip Temp Avg\": 81.74,\n" +
                                                                                        "          \"chip_vol_diff\": 13\n" +
                                                                                        "        },\n" +
                                                                                        "        {\n" +
                                                                                        "          \"ASC\": 2,\n" +
                                                                                        "          \"Name\": \"SM\",\n" +
                                                                                        "          \"ID\": 2,\n" +
                                                                                        "          \"Slot\": 2,\n" +
                                                                                        "          \"Enabled\": \"Y\",\n" +
                                                                                        "          \"Status\": \"Alive\",\n" +
                                                                                        "          \"Temperature\": 79.5,\n" +
                                                                                        "          \"Chip Frequency\": 455,\n" +
                                                                                        "          \"Fan Speed In\": 3870,\n" +
                                                                                        "          \"Fan Speed Out\": 3930,\n" +
                                                                                        "          \"MHS av\": 33503818.03,\n" +
                                                                                        "          \"MHS 5s\": 34677087.2,\n" +
                                                                                        "          \"MHS 1m\": 33440753.88,\n" +
                                                                                        "          \"MHS 5m\": 33372073.04,\n" +
                                                                                        "          \"MHS 15m\": 33427466.67,\n" +
                                                                                        "          \"Accepted\": 153560,\n" +
                                                                                        "          \"Rejected\": 159,\n" +
                                                                                        "          \"Hardware Errors\": 0,\n" +
                                                                                        "          \"Utility\": 7.57,\n" +
                                                                                        "          \"Last Share Pool\": 0,\n" +
                                                                                        "          \"Last Share Time\": 1615249620,\n" +
                                                                                        "          \"Total MH\": 40774071880195,\n" +
                                                                                        "          \"Diff1 Work\": 0,\n" +
                                                                                        "          \"Difficulty Accepted\": 9494995248,\n" +
                                                                                        "          \"Difficulty Rejected\": 9786065,\n" +
                                                                                        "          \"Last Share Difficulty\": 54954,\n" +
                                                                                        "          \"Last Valid Work\": 1615249622,\n" +
                                                                                        "          \"Device Hardware%\": 0,\n" +
                                                                                        "          \"Device Rejected%\": 0,\n" +
                                                                                        "          \"Device Elapsed\": 1216998,\n" +
                                                                                        "          \"Upfreq Complete\": 1,\n" +
                                                                                        "          \"Effective Chips\": 215,\n" +
                                                                                        "          \"PCB SN\": \"xxx\",\n" +
                                                                                        "          \"Chip Data\": \"HP2A02-20021812 BINV02-193002B\",\n" +
                                                                                        "          \"Chip Temp Min\": 43,\n" +
                                                                                        "          \"Chip Temp Max\": 92.9,\n" +
                                                                                        "          \"Chip Temp Avg\": 81.1,\n" +
                                                                                        "          \"chip_vol_diff\": 14\n" +
                                                                                        "        }\n" +
                                                                                        "      ],\n" +
                                                                                        "      \"id\": 1\n" +
                                                                                        "    }",
                                                                                Map.class)))))),
                                Collections.emptyList(),
                                new MinerStats.Builder()
                                        .setApiIp("127.0.0.1")
                                        .setApiPort(4028)
                                        .addPool(
                                                new Pool.Builder()
                                                        .setName("btc.luxor.tech:700")
                                                        .setWorker("xxx")
                                                        .setStatus(true, true)
                                                        .setPriority(0)
                                                        .setCounts(457459, 518, 95)
                                                        .build())
                                        .addAsic(
                                                new Asic.Builder()
                                                        .setHashRate(new BigDecimal("99793646890000.000"))
                                                        .setPowerMode(Asic.PowerMode.HIGH)
                                                        .setBoards(3)
                                                        .setFanInfo(
                                                                new FanInfo.Builder()
                                                                        .setCount(3)
                                                                        .addSpeed(3870)
                                                                        .addSpeed(3930)
                                                                        .addSpeed(8784)
                                                                        .setSpeedUnits("RPM")
                                                                        .build())
                                                        .addTemp(79)
                                                        .addTemp(83)
                                                        .addTemp(80)
                                                        .addTemp(81)
                                                        .addTemp(79)
                                                        .addTemp(81)
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
                                                                        .setCount(3)
                                                                        .addSpeed(2370)
                                                                        .addSpeed(2430)
                                                                        .addSpeed(7576)
                                                                        .setSpeedUnits("RPM")
                                                                        .build())
                                                        .addTemp(11)
                                                        .addTemp(80)
                                                        .addTemp(82)
                                                        .addTemp(72)
                                                        .addTemp(73)
                                                        .addTemp(72)
                                                        .addTemp(74)
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
                                                                        .addSpeed(7608)
                                                                        .setSpeedUnits("RPM")
                                                                        .build())
                                                        .addTemp(7)
                                                        .addTemp(57)
                                                        .addTemp(66)
                                                        .addTemp(72)
                                                        .addTemp(79)
                                                        .addTemp(68)
                                                        .addTemp(75)
                                                        .hasErrors(false)
                                                        .build())
                                        .build()
                        },
                        {
                                // Whatsminer M31S (sleeping)
                                ImmutableMap.of(
                                        "{\"cmd\":\"status\"}",
                                        new RpcHandler(
                                                "{\"STATUS\":\"S\",\"When\":1613790817,\"Code\":131,\"Msg\":{\"btmineroff\":\"true\",\"Firmware Version\":\"'20210109.22.REL'\"},\"Description\":\"whatsminer v1.3\"}")),
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
                "{\"cmd\":\"pools+summary+edevs\"}",
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
