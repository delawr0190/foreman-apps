package mn.foreman.strongu;

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
public class StrongUStatsITest
        extends AbstractApiITest {

    /**
     * Constructor.
     *
     * @param handlers      The handlers.
     * @param expectedStats The expected stats.
     */
    public StrongUStatsITest(
            final Map<String, HandlerInterface> handlers,
            final MinerStats expectedStats) {
        super(
                new StrongUFactory().create(
                        ImmutableMap.of(
                                "apiIp",
                                "127.0.0.1",
                                "apiPort",
                                "4028",
                                "statsWhitelist",
                                Arrays.asList(
                                        "DEVS.0.volt",
                                        "DEVS.0.Temperature"))),
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
                                // StrongU STU 6
                                ImmutableMap.of(
                                        "{\"command\":\"summary\"}",
                                        new RpcHandler(
                                                "{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1592273807,\"Code\":11,\"Msg\":\"Summary\",\"Description\":\"cgminer 4.11.1\"}],\"SUMMARY\":[{\"Elapsed\":2416,\"MHS av\":387186.77,\"MHS 5s\":397759.08,\"MHS 1m\":405927.53,\"MHS 5m\":410826.48,\"MHS 15m\":378651.35,\"Miner type\":\"dash\",\"Fan Num\":2,\"Fan1\":7800,\"Fan2\":7800,\"Found Blocks\":4,\"Getworks\":85,\"Accepted\":162,\"Rejected\":4,\"Hardware Errors\":8061,\"Utility\":4.02,\"Discarded\":33856,\"Stale\":1,\"Get Failures\":0,\"Local Work\":66696,\"Remote Failures\":0,\"Network Blocks\":37,\"Total MH\":935262073.0000,\"Work Utility\":676.15,\"Difficulty Accepted\":192509.06250000,\"Difficulty Rejected\":4863.92578125,\"Difficulty Stale\":1023.98437500,\"Best Share\":8811756,\"Device Hardware%\":22.8473,\"Device Rejected%\":17.8683,\"Pool Rejected%\":2.4516,\"Pool Stale%\":0.5161,\"Last getwork\":1592273806}],\"id\":1}"),
                                        "{\"command\":\"devs\"}",
                                        new RpcHandler(
                                                "{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1592273807,\"Code\":9,\"Msg\":\"2 ASC(s)\",\"Description\":\"cgminer 4.11.1\"}],\"DEVS\":[{\"ASC\":0,\"Name\":\"u6\",\"ID\":0,\"Enabled\":\"Y\",\"Status\":\"Alive\",\"Temperature\":54.56,\"volt\":10.500,\"ChipNum\":70,\"Frequency\":550,\"MHS av\":190748.48,\"MHS 5s\":191920.49,\"MHS 1m\":194433.37,\"MHS 5m\":201864.17,\"MHS 15m\":186301.66,\"Accepted\":77,\"Rejected\":2,\"Hardware Errors\":4552,\"Utility\":1.91,\"Last Share Pool\":0,\"Last Share Time\":1592273789,\"Total MH\":460759065.0000,\"Diff1 Work\":13410,\"Difficulty Accepted\":91134.60937500,\"Difficulty Rejected\":2303.96484375,\"Last Share Difficulty\":2047.96875000,\"Last Valid Work\":1592273807,\"Device Hardware%\":25.3424,\"Device Rejected%\":17.1809,\"Device Elapsed\":2416,\"Reboot Count\":0},{\"ASC\":2,\"Name\":\"u6\",\"ID\":1,\"Enabled\":\"Y\",\"Status\":\"Alive\",\"Temperature\":55.62,\"volt\":12.700,\"ChipNum\":70,\"Frequency\":550,\"MHS av\":196438.30,\"MHS 5s\":208240.11,\"MHS 1m\":211732.23,\"MHS 5m\":208990.13,\"MHS 15m\":192341.76,\"Accepted\":85,\"Rejected\":2,\"Hardware Errors\":3509,\"Utility\":2.11,\"Last Share Pool\":0,\"Last Share Time\":1592273802,\"Total MH\":474503008.0000,\"Diff1 Work\":13811,\"Difficulty Accepted\":101374.45312500,\"Difficulty Rejected\":2559.96093750,\"Last Share Difficulty\":2047.96875000,\"Last Valid Work\":1592273807,\"Device Hardware%\":20.2598,\"Device Rejected%\":18.5357,\"Device Elapsed\":2416,\"Reboot Count\":0}],\"id\":1}"),
                                        "{\"command\":\"pools\"}",
                                        new RpcHandler(
                                                "{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1592273807,\"Code\":7,\"Msg\":\"3 Pool(s)\",\"Description\":\"cgminer 4.11.1\"}],\"POOLS\":[{\"POOL\":0,\"URL\":\"stratum+tcp://img.f2pool.com:4400\",\"Status\":\"Alive\",\"Priority\":0,\"Quota\":1,\"Long Poll\":\"N\",\"Getworks\":82,\"Accepted\":162,\"Rejected\":4,\"Works\":32793,\"Discarded\":33856,\"Stale\":1,\"Get Failures\":0,\"Remote Failures\":0,\"User\":\"xxx\",\"Last Share Time\":\"Tue Jun 16 10:16:42 2020\n\",\"Diff1 Shares\":27220,\"Proxy Type\":\"\",\"Proxy\":\"\",\"Difficulty Accepted\":192509.06250000,\"Difficulty Rejected\":4863.92578125,\"Difficulty Stale\":1023.98437500,\"Last Share Difficulty\":2047.96875000,\"Work Difficulty\":2047.96875000,\"Has Stratum\":true,\"Stratum Active\":true,\"Stratum URL\":\"img.f2pool.com\",\"Stratum Difficulty\":2047.96875000,\"Has Vmask\":false,\"Has GBT\":false,\"Best Share\":8811756,\"Pool Rejected%\":2.4516,\"Pool Stale%\":0.5161,\"Bad Work\":79,\"Current Block Height\":797103,\"Current Block Version\":536870912},{\"POOL\":1,\"URL\":\"stratum+tcp://img.f2pool.com:4400\",\"Status\":\"Alive\",\"Priority\":1,\"Quota\":1,\"Long Poll\":\"N\",\"Getworks\":1,\"Accepted\":0,\"Rejected\":0,\"Works\":0,\"Discarded\":0,\"Stale\":0,\"Get Failures\":0,\"Remote Failures\":0,\"User\":\"xxx\",\"Last Share Time\":\"Thu Jan  1 08:00:00 1970\n\",\"Diff1 Shares\":0,\"Proxy Type\":\"\",\"Proxy\":\"\",\"Difficulty Accepted\":0.00000000,\"Difficulty Rejected\":0.00000000,\"Difficulty Stale\":0.00000000,\"Last Share Difficulty\":0.00000000,\"Work Difficulty\":0.00000000,\"Has Stratum\":true,\"Stratum Active\":false,\"Stratum URL\":\"\",\"Stratum Difficulty\":0.00000000,\"Has Vmask\":false,\"Has GBT\":false,\"Best Share\":0,\"Pool Rejected%\":0.0000,\"Pool Stale%\":0.0000,\"Bad Work\":1,\"Current Block Height\":0,\"Current Block Version\":536870912},{\"POOL\":2,\"URL\":\"stratum+tcp://img.f2pool.com:4400\",\"Status\":\"Alive\",\"Priority\":2,\"Quota\":1,\"Long Poll\":\"N\",\"Getworks\":2,\"Accepted\":0,\"Rejected\":0,\"Works\":0,\"Discarded\":0,\"Stale\":0,\"Get Failures\":0,\"Remote Failures\":0,\"User\":\"xxx\",\"Last Share Time\":\"Thu Jan  1 08:00:00 1970\n\",\"Diff1 Shares\":0,\"Proxy Type\":\"\",\"Proxy\":\"\",\"Difficulty Accepted\":0.00000000,\"Difficulty Rejected\":0.00000000,\"Difficulty Stale\":0.00000000,\"Last Share Difficulty\":0.00000000,\"Work Difficulty\":255.99609375,\"Has Stratum\":true,\"Stratum Active\":false,\"Stratum URL\":\"\",\"Stratum Difficulty\":0.00000000,\"Has Vmask\":false,\"Has GBT\":false,\"Best Share\":0,\"Pool Rejected%\":0.0000,\"Pool Stale%\":0.0000,\"Bad Work\":2,\"Current Block Height\":797068,\"Current Block Version\":536870912}],\"id\":1}")),
                                new MinerStats.Builder()
                                        .setApiIp("127.0.0.1")
                                        .setApiPort(4028)
                                        .addPool(
                                                new Pool.Builder()
                                                        .setName("img.f2pool.com:4400")
                                                        .setPriority(0)
                                                        .setStatus(
                                                                true,
                                                                true)
                                                        .setCounts(
                                                                162,
                                                                4,
                                                                1)
                                                        .build())
                                        .addPool(
                                                new Pool.Builder()
                                                        .setName("img.f2pool.com:4400")
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
                                                        .setName("img.f2pool.com:4400")
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
                                                        .setHashRate(new BigDecimal("397759080000.00"))
                                                        .setFanInfo(
                                                                new FanInfo.Builder()
                                                                        .setCount(2)
                                                                        .addSpeed(7800)
                                                                        .addSpeed(7800)
                                                                        .setSpeedUnits("RPM")
                                                                        .build())
                                                        .addTemp(54)
                                                        .addTemp(55)
                                                        .addRawStats(
                                                                ImmutableMap.of(
                                                                        "DEVS.0.volt",
                                                                        new BigDecimal("10.5"),
                                                                        "DEVS.0.Temperature",
                                                                        new BigDecimal("54.56")))
                                                        .build())
                                        .build()
                        },
                        {
                                // StrongU STU 2
                                ImmutableMap.of(
                                        "{\"command\":\"summary\"}",
                                        new RpcHandler(
                                                "{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1539628509,\"Code\":11,\"Msg\":\"Summary\",\"Description\":\"cgminer 4.10.0\"}],\"SUMMARY\":[{\"Elapsed\":12350,\"MHS av\":6171606.17,\"MHS 5s\":6413329.18,\"MHS 1m\":6288698.46,\"MHS 5m\":6224238.28,\"MHS 15m\":6202629.23,\"Miner type\":\"sc\",\"Fan Num\":2,\"Fan1\":3300,\"Fan2\":3300,\"Found Blocks\":25352,\"Getworks\":58,\"Accepted\":25186,\"Rejected\":15,\"Hardware Errors\":200,\"Utility\":122.36,\"Discarded\":39158,\"Stale\":143,\"Get Failures\":0,\"Local Work\":161079,\"Remote Failures\":0,\"Network Blocks\":16,\"Total MH\":76220204904.0000,\"Work Utility\":123.17,\"Difficulty Accepted\":17630200.00000000,\"Difficulty Rejected\":10500.00000000,\"Difficulty Stale\":31500.00000000,\"Best Share\":18446744073709551615,\"Device Hardware%\":0.7827,\"Device Rejected%\":41.4169,\"Pool Rejected%\":0.0594,\"Pool Stale%\":0.1782,\"Last getwork\":1539628508}],\"id\":1}\"}"),
                                        "{\"command\":\"devs\"}",
                                        new RpcHandler(
                                                "{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1539628509,\"Code\":9,\"Msg\":\"3 ASC(s)\",\"Description\":\"cgminer 4.10.0\"}],\"DEVS\":[{\"ASC\":0,\"Name\":\"u2000\",\"ID\":0,\"Enabled\":\"Y\",\"Status\":\"Alive\",\"Temperature\":65.88,\"volt\":10.500,\"Frequency\":320,\"MHS av\":2064441.76,\"MHS 5s\":2223840.87,\"MHS 1m\":2299301.52,\"MHS 5m\":2172043.96,\"MHS 15m\":2127127.36,\"Accepted\":8437,\"Rejected\":7,\"Hardware Errors\":66,\"Utility\":40.99,\"Last Share Pool\":0,\"Last Share Time\":1539628508,\"Total MH\":25494924960.0000,\"Diff1 Work\":8480,\"Difficulty Accepted\":5905900.00000000,\"Difficulty Rejected\":4900.00000000,\"Last Share Difficulty\":700.00000000,\"Last Valid Work\":1539628508,\"Device Hardware%\":0.7723,\"Device Rejected%\":57.7830,\"Device Elapsed\":12350},{\"ASC\":1,\"Name\":\"u2000\",\"ID\":1,\"Enabled\":\"Y\",\"Status\":\"Alive\",\"Temperature\":70.44,\"volt\":11.600,\"Frequency\":320,\"MHS av\":2066632.80,\"MHS 5s\":2686531.81,\"MHS 1m\":2286023.12,\"MHS 5m\":2090038.23,\"MHS 15m\":2018880.25,\"Accepted\":8419,\"Rejected\":4,\"Hardware Errors\":68,\"Utility\":40.90,\"Last Share Pool\":0,\"Last Share Time\":1539628508,\"Total MH\":25521983253.0000,\"Diff1 Work\":8489,\"Difficulty Accepted\":5893300.00000000,\"Difficulty Rejected\":2800.00000000,\"Last Share Difficulty\":700.00000000,\"Last Valid Work\":1539628508,\"Device Hardware%\":0.7947,\"Device Rejected%\":32.9839,\"Device Elapsed\":12350},{\"ASC\":2,\"Name\":\"u2000\",\"ID\":2,\"Enabled\":\"Y\",\"Status\":\"Alive\",\"Temperature\":68.19,\"volt\":12.700,\"Frequency\":320,\"MHS av\":2040583.83,\"MHS 5s\":1644264.32,\"MHS 1m\":1736097.59,\"MHS 5m\":1970805.77,\"MHS 15m\":2059870.86,\"Accepted\":8330,\"Rejected\":4,\"Hardware Errors\":66,\"Utility\":40.47,\"Last Share Pool\":0,\"Last Share Time\":1539628505,\"Total MH\":25200290214.0000,\"Diff1 Work\":8382,\"Difficulty Accepted\":5831000.00000000,\"Difficulty Rejected\":2800.00000000,\"Last Share Difficulty\":700.00000000,\"Last Valid Work\":1539628505,\"Device Hardware%\":0.7812,\"Device Rejected%\":33.4049,\"Device Elapsed\":12350}],\"id\":1}"),
                                        "{\"command\":\"pools\"}",
                                        new RpcHandler(
                                                "{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1539628508,\"Code\":7,\"Msg\":\"3 Pool(s)\",\"Description\":\"cgminer 4.10.0\"}],\"POOLS\":[{\"POOL\":0,\"URL\":\"stratum+tcp://eu.siaprimestats.com:3355\",\"Status\":\"Alive\",\"Priority\":0,\"Quota\":1,\"Long Poll\":\"N\",\"Getworks\":55,\"Accepted\":25186,\"Rejected\":15,\"Works\":121805,\"Discarded\":39158,\"Stale\":143,\"Get Failures\":0,\"Remote Failures\":0,\"User\":\"xxx\",\"Last Share Time\":\"Tue Oct 16 02:35:08 2018n\",\"Diff1 Shares\":25351,\"Proxy Type\":\"\",\"Proxy\":\"\",\"Difficulty Accepted\":17630200.00000000,\"Difficulty Rejected\":10500.00000000,\"Difficulty Stale\":31500.00000000,\"Last Share Difficulty\":700.00000000,\"Work Difficulty\":700.00000000,\"Has Stratum\":true,\"Stratum Active\":true,\"Stratum URL\":\"eu.siaprimestats.com\",\"Stratum Difficulty\":700.00000000,\"Has Vmask\":false,\"Has GBT\":false,\"Best Share\":18446744073709551615,\"Pool Rejected%\":0.0594,\"Pool Stale%\":0.1782,\"Bad Work\":35,\"Current Block Height\":4294967295,\"Current Block Version\":0},{\"POOL\":1,\"URL\":\"stratum+tcp://eu.siamining.com:3366\",\"Status\":\"Alive\",\"Priority\":1,\"Quota\":1,\"Long Poll\":\"N\",\"Getworks\":2,\"Accepted\":0,\"Rejected\":0,\"Works\":64,\"Discarded\":0,\"Stale\":0,\"Get Failures\":0,\"Remote Failures\":0,\"User\":\"xxx\",\"Last Share Time\":\"Thu Jan  1 08:00:00 1970n\",\"Diff1 Shares\":0,\"Proxy Type\":\"\",\"Proxy\":\"\",\"Difficulty Accepted\":0.00000000,\"Difficulty Rejected\":0.00000000,\"Difficulty Stale\":0.00000000,\"Last Share Difficulty\":0.00000000,\"Work Difficulty\":255.99609375,\"Has Stratum\":true,\"Stratum Active\":false,\"Stratum URL\":\"\",\"Stratum Difficulty\":0.00000000,\"Has Vmask\":false,\"Has GBT\":false,\"Best Share\":0,\"Pool Rejected%\":0.0000,\"Pool Stale%\":0.0000,\"Bad Work\":0,\"Current Block Height\":4294967295,\"Current Block Version\":0},{\"POOL\":2,\"URL\":\"stratum+tcp://eu.siamining.com:3366\",\"Status\":\"Alive\",\"Priority\":2,\"Quota\":1,\"Long Poll\":\"N\",\"Getworks\":1,\"Accepted\":0,\"Rejected\":0,\"Works\":0,\"Discarded\":0,\"Stale\":0,\"Get Failures\":0,\"Remote Failures\":0,\"User\":\"xxx\",\"Last Share Time\":\"Thu Jan  1 08:00:00 1970n\",\"Diff1 Shares\":0,\"Proxy Type\":\"\",\"Proxy\":\"\",\"Difficulty Accepted\":0.00000000,\"Difficulty Rejected\":0.00000000,\"Difficulty Stale\":0.00000000,\"Last Share Difficulty\":0.00000000,\"Work Difficulty\":0.00000000,\"Has Stratum\":true,\"Stratum Active\":false,\"Stratum URL\":\"\",\"Stratum Difficulty\":0.00000000,\"Has Vmask\":false,\"Has GBT\":false,\"Best Share\":0,\"Pool Rejected%\":0.0000,\"Pool Stale%\":0.0000,\"Bad Work\":0,\"Current Block Height\":0,\"Current Block Version\":0}],\"id\":1}")),
                                new MinerStats.Builder()
                                        .setApiIp("127.0.0.1")
                                        .setApiPort(4028)
                                        .addPool(
                                                new Pool.Builder()
                                                        .setName("eu.siaprimestats.com:3355")
                                                        .setPriority(0)
                                                        .setStatus(
                                                                true,
                                                                true)
                                                        .setCounts(
                                                                25186,
                                                                15,
                                                                143)
                                                        .build())
                                        .addPool(
                                                new Pool.Builder()
                                                        .setName("eu.siamining.com:3366")
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
                                                        .setName("eu.siamining.com:3366")
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
                                                        .setHashRate(new BigDecimal("6413329180000.00"))
                                                        .setFanInfo(
                                                                new FanInfo.Builder()
                                                                        .setCount(2)
                                                                        .addSpeed(3300)
                                                                        .addSpeed(3300)
                                                                        .setSpeedUnits("RPM")
                                                                        .build())
                                                        .addTemp(65)
                                                        .addTemp(70)
                                                        .addTemp(68)
                                                        .addRawStats(
                                                                ImmutableMap.of(
                                                                        "DEVS.0.volt",
                                                                        new BigDecimal("10.5"),
                                                                        "DEVS.0.Temperature",
                                                                        new BigDecimal("65.88")))
                                                        .build())
                                        .build()
                        }
                });
    }
}