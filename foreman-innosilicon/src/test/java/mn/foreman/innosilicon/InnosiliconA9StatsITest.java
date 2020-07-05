package mn.foreman.innosilicon;

import mn.foreman.cgminer.CgMiner;
import mn.foreman.model.miners.FanInfo;
import mn.foreman.model.miners.MinerStats;
import mn.foreman.model.miners.Pool;
import mn.foreman.model.miners.asic.Asic;
import mn.foreman.util.AbstractApiITest;
import mn.foreman.util.rpc.FakeRpcMinerServer;
import mn.foreman.util.rpc.RpcHandler;

import com.google.common.collect.ImmutableMap;

import java.math.BigDecimal;

/** Runs an integration tests using {@link CgMiner} against a fake API. */
public class InnosiliconA9StatsITest
        extends AbstractApiITest {

    /** Constructor. */
    public InnosiliconA9StatsITest() {
        super(
                new InnosiliconFactory(ApiType.HS_API)
                        .create(
                                ImmutableMap.of(
                                        "apiIp",
                                        "127.0.0.1",
                                        "apiPort",
                                        "4028")),
                new FakeRpcMinerServer(
                        4028,
                        ImmutableMap.of(
                                "{\"command\":\"summary\"}",
                                new RpcHandler(
                                        "{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1593966947,\"Code\":11,\"Msg\":\"Summary\",\"Description\":\"innominer a9-1.1.0\"}],\"SUMMARY\":[{\"Elapsed\":48768,\"MHS av\":51274.60,\"MHS 5s\":51451.63,\"MHS 1m\":51451.63,\"MHS 5m\":51451.63,\"MHS 15m\":51451.63,\"Found Blocks\":0,\"Getworks\":3131,\"Accepted\":5128,\"Rejected\":26,\"Hardware Errors\":806,\"Utility\":6.31,\"Discarded\":3437209,\"Stale\":0,\"Get Failures\":1,\"Local Work\":3854000,\"Remote Failures\":0,\"Network Blocks\":1169,\"Total MH\":2500575892.0000,\"Work Utility\":415483.33,\"Difficulty Accepted\":15.84000000,\"Difficulty Rejected\":15.84000000,\"Difficulty Stale\":15.84000000,\"Best Share\":15.840000,\"Device Hardware%\":1584.0000,\"Device Rejected%\":1584.0000,\"Pool Rejected%\":1584.0000,\"Pool Stale%\":1584.0000,\"Last getwork\":1593966937}],\"id\":1}"),
                                "{\"command\":\"stats\"}",
                                new RpcHandler(
                                        "{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1536018636,\"Code\":70,\"Msg\":\"CGMiner stats\",\"Description\":\"sgminer 4.4.2\"}],\"STATS0\":[{\"STATS\":0,\"Num chips\":12,\"Num active chips\":12,\"Fan duty\":100,\"MHS av\":16500.000000,\"Temp\":47.000000}],\"STATS1\":[{\"STATS\":1,\"Num chips\":12,\"Num active chips\":12,\"Fan duty\":100,\"MHS av\":16500.000000,\"Temp\":48.000000}],\"STATS2\":[{\"STATS\":2,\"Num chips\":12,\"Num active chips\":12,\"Fan duty\":100,\"MHS av\":16500.000000,\"Temp\":45.000000}],\"STATS3\":[{\"STATS\":3}],\"id\":1}"),
                                "{\"command\":\"pools\"}",
                                new RpcHandler(
                                        "{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1536018726,\"Code\":7,\"Msg\":\"1 Pool(s)\",\"Description\":\"sgminer 4.4.2\"}],\"POOL0\":[{\"POOL\":0,\"URL\":\"stratum+tcp://zcl.suprnova.cc:4042\",\"Status\":\"Alive\",\"Priority\":0,\"Accepted\":2113,\"Rejected\":27,\"Stale\":1}],\"POOL1\":[{\"POOL\":1,\"URL\":\"stratum+tcp://zcl.suprnova.cc:4043\",\"Status\":\"Alive\",\"Priority\":1,\"Accepted\":3,\"Rejected\":2,\"Stale\":1}],\"id\":1}"))),
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
                        .build());
    }
}