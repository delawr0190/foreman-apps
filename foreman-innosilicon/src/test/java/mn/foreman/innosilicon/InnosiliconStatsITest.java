package mn.foreman.innosilicon;

import mn.foreman.model.miners.FanInfo;
import mn.foreman.model.miners.MinerStats;
import mn.foreman.model.miners.Pool;
import mn.foreman.model.miners.asic.Asic;
import mn.foreman.util.AbstractApiITest;
import mn.foreman.util.rpc.FakeRpcMinerServer;
import mn.foreman.util.rpc.RpcHandler;

import com.google.common.collect.ImmutableMap;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

/** Tests innosilicon stats obtaining. */
@RunWith(Parameterized.class)
public class InnosiliconStatsITest
        extends AbstractApiITest {

    /**
     * Constructor.
     *
     * @param apiType       The API type.
     * @param handlers      The handlers.
     * @param expectedStats The expected stats.
     */
    public InnosiliconStatsITest(
            final ApiType apiType,
            final Map<String, RpcHandler> handlers,
            final MinerStats expectedStats) {
        super(
                new InnosiliconFactory(apiType)
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
     */
    @Parameterized.Parameters
    public static Collection parameters() {
        return Arrays.asList(
                new Object[][]{
                        {
                                // Innosilicon A9 (old)
                                ApiType.HS_API,
                                ImmutableMap.of(
                                        "{\"command\":\"summary\"}",
                                        new RpcHandler(
                                                "{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1593966947,\"Code\":11,\"Msg\":\"Summary\",\"Description\":\"innominer a9-1.1.0\"}],\"SUMMARY\":[{\"Elapsed\":48768,\"MHS av\":51274.60,\"MHS 5s\":51451.63,\"MHS 1m\":51451.63,\"MHS 5m\":51451.63,\"MHS 15m\":51451.63,\"Found Blocks\":0,\"Getworks\":3131,\"Accepted\":5128,\"Rejected\":26,\"Hardware Errors\":806,\"Utility\":6.31,\"Discarded\":3437209,\"Stale\":0,\"Get Failures\":1,\"Local Work\":3854000,\"Remote Failures\":0,\"Network Blocks\":1169,\"Total MH\":2500575892.0000,\"Work Utility\":415483.33,\"Difficulty Accepted\":15.84000000,\"Difficulty Rejected\":15.84000000,\"Difficulty Stale\":15.84000000,\"Best Share\":15.840000,\"Device Hardware%\":1584.0000,\"Device Rejected%\":1584.0000,\"Pool Rejected%\":1584.0000,\"Pool Stale%\":1584.0000,\"Last getwork\":1593966937}],\"id\":1}"),
                                        "{\"command\":\"stats\"}",
                                        new RpcHandler(
                                                "{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1536018636,\"Code\":70,\"Msg\":\"CGMiner stats\",\"Description\":\"sgminer 4.4.2\"}],\"STATS0\":[{\"STATS\":0,\"Num chips\":12,\"Num active chips\":12,\"Fan duty\":100,\"MHS av\":16500.000000,\"Temp\":47.000000}],\"STATS1\":[{\"STATS\":1,\"Num chips\":12,\"Num active chips\":12,\"Fan duty\":100,\"MHS av\":16500.000000,\"Temp\":48.000000}],\"STATS2\":[{\"STATS\":2,\"Num chips\":12,\"Num active chips\":12,\"Fan duty\":100,\"MHS av\":16500.000000,\"Temp\":45.000000}],\"STATS3\":[{\"STATS\":3}],\"id\":1}"),
                                        "{\"command\":\"pools\"}",
                                        new RpcHandler(
                                                "{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1536018726,\"Code\":7,\"Msg\":\"1 Pool(s)\",\"Description\":\"sgminer 4.4.2\"}],\"POOL0\":[{\"POOL\":0,\"URL\":\"stratum+tcp://zcl.suprnova.cc:4042\",\"Status\":\"Alive\",\"Priority\":0,\"Accepted\":2113,\"Rejected\":27,\"Stale\":1}],\"POOL1\":[{\"POOL\":1,\"URL\":\"stratum+tcp://zcl.suprnova.cc:4043\",\"Status\":\"Alive\",\"Priority\":1,\"Accepted\":3,\"Rejected\":2,\"Stale\":1}],\"id\":1}")),
                                new MinerStats.Builder()
                                        .setApiIp("127.0.0.1")
                                        .setApiPort(4028)
                                        .addPool(
                                                new Pool.Builder()
                                                        .setName("zcl.suprnova.cc:4042")
                                                        .setPriority(0)
                                                        .setStatus(
                                                                true,
                                                                true)
                                                        .setCounts(
                                                                2113,
                                                                27,
                                                                1)
                                                        .build())
                                        .addPool(
                                                new Pool.Builder()
                                                        .setName("zcl.suprnova.cc:4043")
                                                        .setPriority(1)
                                                        .setStatus(
                                                                true,
                                                                true)
                                                        .setCounts(
                                                                3,
                                                                2,
                                                                1)
                                                        .build())
                                        .addAsic(
                                                new Asic.Builder()
                                                        .setHashRate(new BigDecimal("51274.5999999999976797275346275875596013804624817566946148872375488281250000000"))
                                                        .setFanInfo(
                                                                new FanInfo.Builder()
                                                                        .setCount(3)
                                                                        .addSpeed(100)
                                                                        .addSpeed(100)
                                                                        .addSpeed(100)
                                                                        .setSpeedUnits("%")
                                                                        .build())
                                                        .addTemp(47)
                                                        .addTemp(48)
                                                        .addTemp(45)
                                                        .build())
                                        .build()
                        },
                        {
                                // Innosilicon A9 (new)
                                ApiType.HS_API,
                                ImmutableMap.of(
                                        "{\"command\":\"summary\"}",
                                        new RpcHandler(
                                                "{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1593966947,\"Code\":11,\"Msg\":\"Summary\",\"Description\":\"innominer a9-1.1.0\"}],\"SUMMARY\":[{\"Elapsed\":48768,\"MHS av\":51274.60,\"MHS 5s\":51451.63,\"MHS 1m\":51451.63,\"MHS 5m\":51451.63,\"MHS 15m\":51451.63,\"Found Blocks\":0,\"Getworks\":3131,\"Accepted\":5128,\"Rejected\":26,\"Hardware Errors\":806,\"Utility\":6.31,\"Discarded\":3437209,\"Stale\":0,\"Get Failures\":1,\"Local Work\":3854000,\"Remote Failures\":0,\"Network Blocks\":1169,\"Total MH\":2500575892.0000,\"Work Utility\":415483.33,\"Difficulty Accepted\":15.84000000,\"Difficulty Rejected\":15.84000000,\"Difficulty Stale\":15.84000000,\"Best Share\":15.840000,\"Device Hardware%\":1584.0000,\"Device Rejected%\":1584.0000,\"Pool Rejected%\":1584.0000,\"Pool Stale%\":1584.0000,\"Last getwork\":1593966937}],\"id\":1}"),
                                        "{\"command\":\"stats\"}",
                                        new RpcHandler(
                                                "{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1593966947,\"Code\":70,\"Msg\":\"CGMiner stats\",\"Description\":\"innominer a9-1.1.0\"}],\"STATS\":[{\"STATS\":0,\"ID\":\"HLT0\",\"Elapsed\":48768,\"Chain ID\":0,\"Num chips\":12,\"Num cores\":384,\"Num active chips\":12,\"Temp max\":71.000000,\"Temp min\":57.000000,\"Temp\":62.000000,\"Fan duty\":35,\"iVid\":165,\"PLL\":358,\"Voltage Max\":868,\"Voltage Min\":859,\"Voltage Avg\":863,\"Chain num\":0,\"MHS av\":995328.000000,\"Enabled chips\":\"fff\",\"00 HW errors\":10,\"00 Stales\":0,\"00 Nonces found\":155,\"00 Nonce ranges\":0,\"00 Fail count\":0,\"00 cores\":32,\"00 Temp\":0,\"00 nVol\":865,\"00 PLL\":0,\"01 HW errors\":18,\"01 Stales\":0,\"01 Nonces found\":140,\"01 Nonce ranges\":0,\"01 Fail count\":1,\"01 cores\":32,\"01 Temp\":0,\"01 nVol\":865,\"01 PLL\":0,\"02 HW errors\":8,\"02 Stales\":2,\"02 Nonces found\":159,\"02 Nonce ranges\":0,\"02 Fail count\":0,\"02 cores\":32,\"02 Temp\":0,\"02 nVol\":861,\"02 PLL\":0,\"03 HW errors\":11,\"03 Stales\":2,\"03 Nonces found\":148,\"03 Nonce ranges\":0,\"03 Fail count\":1,\"03 cores\":32,\"03 Temp\":0,\"03 nVol\":862,\"03 PLL\":0,\"04 HW errors\":18,\"04 Stales\":1,\"04 Nonces found\":162,\"04 Nonce ranges\":0,\"04 Fail count\":0,\"04 cores\":32,\"04 Temp\":0,\"04 nVol\":862,\"04 PLL\":0,\"05 HW errors\":7,\"05 Stales\":1,\"05 Nonces found\":179,\"05 Nonce ranges\":0,\"05 Fail count\":1,\"05 cores\":32,\"05 Temp\":0,\"05 nVol\":859,\"05 PLL\":0,\"06 HW errors\":14,\"06 Stales\":1,\"06 Nonces found\":151,\"06 Nonce ranges\":0,\"06 Fail count\":0,\"06 cores\":32,\"06 Temp\":0,\"06 nVol\":860,\"06 PLL\":0,\"07 HW errors\":10,\"07 Stales\":3,\"07 Nonces found\":148,\"07 Nonce ranges\":0,\"07 Fail count\":0,\"07 cores\":32,\"07 Temp\":0,\"07 nVol\":860,\"07 PLL\":0,\"08 HW errors\":19,\"08 Stales\":1,\"08 Nonces found\":168,\"08 Nonce ranges\":0,\"08 Fail count\":1,\"08 cores\":32,\"08 Temp\":0,\"08 nVol\":867,\"08 PLL\":0,\"09 HW errors\":17,\"09 Stales\":1,\"09 Nonces found\":166,\"09 Nonce ranges\":0,\"09 Fail count\":0,\"09 cores\":32,\"09 Temp\":0,\"09 nVol\":868,\"09 PLL\":0,\"10 HW errors\":18,\"10 Stales\":1,\"10 Nonces found\":165,\"10 Nonce ranges\":0,\"10 Fail count\":1,\"10 cores\":32,\"10 Temp\":0,\"10 nVol\":866,\"10 PLL\":0,\"11 HW errors\":20,\"11 Stales\":1,\"11 Nonces found\":144,\"11 Nonce ranges\":0,\"11 Fail count\":2,\"11 cores\":32,\"11 Temp\":0,\"11 nVol\":868,\"11 PLL\":0},{\"STATS\":1,\"ID\":\"HLT1\",\"Elapsed\":48768,\"Chain ID\":1,\"Num chips\":12,\"Num cores\":379,\"Num active chips\":12,\"Temp max\":72.000000,\"Temp min\":61.000000,\"Temp\":64.000000,\"Fan duty\":35,\"iVid\":165,\"PLL\":358,\"Voltage Max\":872,\"Voltage Min\":860,\"Voltage Avg\":864,\"Chain num\":1,\"MHS av\":982368.000000,\"Enabled chips\":\"fff\",\"00 HW errors\":53,\"00 Stales\":2,\"00 Nonces found\":98,\"00 Nonce ranges\":0,\"00 Fail count\":0,\"00 cores\":31,\"00 Temp\":0,\"00 nVol\":865,\"00 PLL\":0,\"01 HW errors\":53,\"01 Stales\":0,\"01 Nonces found\":109,\"01 Nonce ranges\":0,\"01 Fail count\":2,\"01 cores\":31,\"01 Temp\":0,\"01 nVol\":861,\"01 PLL\":0,\"02 HW errors\":18,\"02 Stales\":2,\"02 Nonces found\":167,\"02 Nonce ranges\":0,\"02 Fail count\":0,\"02 cores\":32,\"02 Temp\":0,\"02 nVol\":860,\"02 PLL\":0,\"03 HW errors\":100,\"03 Stales\":2,\"03 Nonces found\":55,\"03 Nonce ranges\":0,\"03 Fail count\":0,\"03 cores\":31,\"03 Temp\":0,\"03 nVol\":862,\"03 PLL\":0,\"04 HW errors\":43,\"04 Stales\":0,\"04 Nonces found\":111,\"04 Nonce ranges\":0,\"04 Fail count\":0,\"04 cores\":31,\"04 Temp\":0,\"04 nVol\":867,\"04 PLL\":0,\"05 HW errors\":18,\"05 Stales\":1,\"05 Nonces found\":156,\"05 Nonce ranges\":0,\"05 Fail count\":0,\"05 cores\":32,\"05 Temp\":0,\"05 nVol\":867,\"05 PLL\":0,\"06 HW errors\":19,\"06 Stales\":1,\"06 Nonces found\":154,\"06 Nonce ranges\":0,\"06 Fail count\":0,\"06 cores\":32,\"06 Temp\":0,\"06 nVol\":871,\"06 PLL\":0,\"07 HW errors\":10,\"07 Stales\":0,\"07 Nonces found\":144,\"07 Nonce ranges\":0,\"07 Fail count\":2,\"07 cores\":32,\"07 Temp\":0,\"07 nVol\":872,\"07 PLL\":0,\"08 HW errors\":91,\"08 Stales\":0,\"08 Nonces found\":56,\"08 Nonce ranges\":0,\"08 Fail count\":0,\"08 cores\":31,\"08 Temp\":0,\"08 nVol\":862,\"08 PLL\":0,\"09 HW errors\":15,\"09 Stales\":1,\"09 Nonces found\":153,\"09 Nonce ranges\":0,\"09 Fail count\":0,\"09 cores\":32,\"09 Temp\":0,\"09 nVol\":866,\"09 PLL\":0,\"10 HW errors\":14,\"10 Stales\":2,\"10 Nonces found\":159,\"10 Nonce ranges\":0,\"10 Fail count\":2,\"10 cores\":32,\"10 Temp\":0,\"10 nVol\":862,\"10 PLL\":0,\"11 HW errors\":13,\"11 Stales\":0,\"11 Nonces found\":172,\"11 Nonce ranges\":0,\"11 Fail count\":1,\"11 cores\":32,\"11 Temp\":0,\"11 nVol\":862,\"11 PLL\":0},{\"STATS\":2,\"ID\":\"HLT2\",\"Elapsed\":48768,\"Chain ID\":2,\"Num chips\":12,\"Num cores\":384,\"Num active chips\":12,\"Temp max\":67.000000,\"Temp min\":56.000000,\"Temp\":60.000000,\"Fan duty\":35,\"iVid\":165,\"PLL\":358,\"Voltage Max\":873,\"Voltage Min\":862,\"Voltage Avg\":867,\"Chain num\":2,\"MHS av\":995328.000000,\"Enabled chips\":\"fff\",\"00 HW errors\":14,\"00 Stales\":0,\"00 Nonces found\":148,\"00 Nonce ranges\":0,\"00 Fail count\":0,\"00 cores\":32,\"00 Temp\":0,\"00 nVol\":868,\"00 PLL\":0,\"01 HW errors\":18,\"01 Stales\":0,\"01 Nonces found\":126,\"01 Nonce ranges\":0,\"01 Fail count\":0,\"01 cores\":32,\"01 Temp\":0,\"01 nVol\":868,\"01 PLL\":0,\"02 HW errors\":12,\"02 Stales\":1,\"02 Nonces found\":133,\"02 Nonce ranges\":0,\"02 Fail count\":1,\"02 cores\":32,\"02 Temp\":0,\"02 nVol\":866,\"02 PLL\":0,\"03 HW errors\":9,\"03 Stales\":1,\"03 Nonces found\":155,\"03 Nonce ranges\":0,\"03 Fail count\":0,\"03 cores\":32,\"03 Temp\":0,\"03 nVol\":862,\"03 PLL\":0,\"04 HW errors\":11,\"04 Stales\":0,\"04 Nonces found\":140,\"04 Nonce ranges\":0,\"04 Fail count\":2,\"04 cores\":32,\"04 Temp\":0,\"04 nVol\":873,\"04 PLL\":0,\"05 HW errors\":11,\"05 Stales\":0,\"05 Nonces found\":153,\"05 Nonce ranges\":0,\"05 Fail count\":0,\"05 cores\":32,\"05 Temp\":0,\"05 nVol\":870,\"05 PLL\":0,\"06 HW errors\":16,\"06 Stales\":0,\"06 Nonces found\":175,\"06 Nonce ranges\":0,\"06 Fail count\":1,\"06 cores\":32,\"06 Temp\":0,\"06 nVol\":866,\"06 PLL\":0,\"07 HW errors\":10,\"07 Stales\":1,\"07 Nonces found\":152,\"07 Nonce ranges\":0,\"07 Fail count\":3,\"07 cores\":32,\"07 Temp\":0,\"07 nVol\":871,\"07 PLL\":0,\"08 HW errors\":50,\"08 Stales\":4,\"08 Nonces found\":138,\"08 Nonce ranges\":0,\"08 Fail count\":1,\"08 cores\":32,\"08 Temp\":0,\"08 nVol\":864,\"08 PLL\":0,\"09 HW errors\":12,\"09 Stales\":2,\"09 Nonces found\":138,\"09 Nonce ranges\":0,\"09 Fail count\":0,\"09 cores\":32,\"09 Temp\":0,\"09 nVol\":865,\"09 PLL\":0,\"10 HW errors\":10,\"10 Stales\":2,\"10 Nonces found\":160,\"10 Nonce ranges\":0,\"10 Fail count\":0,\"10 cores\":32,\"10 Temp\":0,\"10 nVol\":865,\"10 PLL\":0,\"11 HW errors\":16,\"11 Stales\":1,\"11 Nonces found\":116,\"11 Nonce ranges\":0,\"11 Fail count\":1,\"11 cores\":32,\"11 Temp\":0,\"11 nVol\":868,\"11 PLL\":0},{\"STATS\":3,\"ID\":\"POOL0\",\"Elapsed\":48768,\"Pool Calls\":0,\"Pool Attempts\":0,\"Pool Wait\":0.000000,\"Pool Max\":0.000000,\"Pool Min\":99999999.000000,\"Pool Av\":0.000000,\"Work Had Roll Time\":false,\"Work Can Roll\":false,\"Work Had Expire\":false,\"Work Roll Time\":0,\"Work Diff\":573494.00000000,\"Min Diff\":81920.00000000,\"Max Diff\":573494.00000000,\"Min Diff Count\":159,\"Max Diff Count\":3244375,\"Times Sent\":5156,\"Bytes Sent\":14654268,\"Times Recv\":9030,\"Bytes Recv\":1200893,\"Net Bytes Sent\":14654268,\"Net Bytes Recv\":1200893},{\"STATS\":4,\"ID\":\"POOL1\",\"Elapsed\":48768,\"Pool Calls\":0,\"Pool Attempts\":0,\"Pool Wait\":0.000000,\"Pool Max\":0.000000,\"Pool Min\":99999999.000000,\"Pool Av\":0.000000,\"Work Had Roll Time\":false,\"Work Can Roll\":false,\"Work Had Expire\":false,\"Work Roll Time\":0,\"Work Diff\":278528.00000000,\"Min Diff\":278528.00000000,\"Max Diff\":278528.00000000,\"Min Diff Count\":2,\"Max Diff Count\":2,\"Times Sent\":6,\"Bytes Sent\":485,\"Times Recv\":10,\"Bytes Recv\":1127,\"Net Bytes Sent\":485,\"Net Bytes Recv\":1127},{\"STATS\":5,\"ID\":\"POOL2\",\"Elapsed\":48768,\"Pool Calls\":0,\"Pool Attempts\":0,\"Pool Wait\":0.000000,\"Pool Max\":0.000000,\"Pool Min\":99999999.000000,\"Pool Av\":0.000000,\"Work Had Roll Time\":false,\"Work Can Roll\":false,\"Work Had Expire\":false,\"Work Roll Time\":0,\"Work Diff\":0.00000000,\"Min Diff\":0.00000000,\"Max Diff\":0.00000000,\"Min Diff Count\":0,\"Max Diff Count\":0,\"Times Sent\":3,\"Bytes Sent\":240,\"Times Recv\":5,\"Bytes Recv\":558,\"Net Bytes Sent\":240,\"Net Bytes Recv\":558}],\"id\":1}"),
                                        "{\"command\":\"pools\"}",
                                        new RpcHandler(
                                                "{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1593966947,\"Code\":7,\"Msg\":\"3 Pool(s)\",\"Description\":\"innominer a9-1.1.0\"}],\"POOLS\":[{\"POOL\":0,\"URL\":\"stratum+tcp://americas.equihash.mining-dutch.nl:6663\",\"User\":\"xxx\",\"Status\":\"Alive\",\"Priority\":0,\"Quota\":1,\"Long Poll\":\"N\",\"Getworks\":3128,\"Accepted\":5128,\"Rejected\":26,\"Works\":413652,\"Discarded\":3437209,\"Stale\":0,\"Get Failures\":0,\"Remote Failures\":0,\"Last Share Time\":1593966945,\"Proxy Type\":\"\",\"Proxy\":\"\",\"Difficulty Accepted\":2487729810.00000000,\"Difficulty Rejected\":12551116.00000000,\"Difficulty Stale\":0.00000000,\"Last Share Difficulty\":573494.00000000,\"Has Stratum\":true,\"Stratum Active\":true,\"Stratum URL\":\"americas.equihash.mining-dutch.nl\",\"Has GBT\":false,\"Pool Rejected%\":0.5020,\"Pool Stale%\":0.0000},{\"POOL\":1,\"URL\":\"stratum+tcp://solo-zen.2miners.com:7070\",\"User\":\"xxx\",\"Status\":\"Alive\",\"Priority\":1,\"Quota\":1,\"Long Poll\":\"N\",\"Getworks\":2,\"Accepted\":0,\"Rejected\":0,\"Works\":0,\"Discarded\":0,\"Stale\":0,\"Get Failures\":1,\"Remote Failures\":0,\"Last Share Time\":0,\"Proxy Type\":\"\",\"Proxy\":\"\",\"Difficulty Accepted\":0.00000000,\"Difficulty Rejected\":0.00000000,\"Difficulty Stale\":0.00000000,\"Last Share Difficulty\":0.00000000,\"Has Stratum\":true,\"Stratum Active\":false,\"Stratum URL\":\"\",\"Has GBT\":false,\"Pool Rejected%\":0.0000,\"Pool Stale%\":0.0000},{\"POOL\":2,\"URL\":\"stratum+tcp://us1.zhash.pro:8059\",\"User\":\"xxx\",\"Status\":\"Alive\",\"Priority\":2,\"Quota\":1,\"Long Poll\":\"N\",\"Getworks\":1,\"Accepted\":0,\"Rejected\":0,\"Works\":0,\"Discarded\":0,\"Stale\":0,\"Get Failures\":0,\"Remote Failures\":0,\"Last Share Time\":0,\"Proxy Type\":\"\",\"Proxy\":\"\",\"Difficulty Accepted\":0.00000000,\"Difficulty Rejected\":0.00000000,\"Difficulty Stale\":0.00000000,\"Last Share Difficulty\":0.00000000,\"Has Stratum\":true,\"Stratum Active\":false,\"Stratum URL\":\"\",\"Has GBT\":false,\"Pool Rejected%\":0.0000,\"Pool Stale%\":0.0000}],\"id\":1}")),
                                new MinerStats.Builder()
                                        .setApiIp("127.0.0.1")
                                        .setApiPort(4028)
                                        .addPool(
                                                new Pool.Builder()
                                                        .setName("americas.equihash.mining-dutch.nl:6663")
                                                        .setPriority(0)
                                                        .setStatus(
                                                                true,
                                                                true)
                                                        .setCounts(
                                                                5128,
                                                                26,
                                                                0)
                                                        .build())
                                        .addPool(
                                                new Pool.Builder()
                                                        .setName("solo-zen.2miners.com:7070")
                                                        .setPriority(1)
                                                        .setStatus(
                                                                true,
                                                                true)
                                                        .setCounts(
                                                                0,
                                                                0,
                                                                0)
                                                        .build())
                                        .addPool(
                                                new Pool.Builder()
                                                        .setName("us1.zhash.pro:8059")
                                                        .setPriority(2)
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
                                                        .setHashRate(new BigDecimal("51274.5999999999976797275346275875596013804624817566946148872375488281250000000"))
                                                        .setFanInfo(
                                                                new FanInfo.Builder()
                                                                        .setCount(3)
                                                                        .addSpeed(35)
                                                                        .addSpeed(35)
                                                                        .addSpeed(35)
                                                                        .setSpeedUnits("%")
                                                                        .build())
                                                        .addTemp(62)
                                                        .addTemp(64)
                                                        .addTemp(60)
                                                        .build())
                                        .build()
                        }
                });
    }
}
