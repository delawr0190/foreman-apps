package mn.foreman.spondoolies;

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
import java.util.Arrays;

/** Runs an integration tests using {@link CgMiner} against a fake API. */
public class SpondooliesSP36StatsITest
        extends AbstractApiITest {

    /** Constructor. */
    public SpondooliesSP36StatsITest() {
        super(
                new SpondooliesFactory().create(
                        ImmutableMap.of(
                                "apiIp",
                                "127.0.0.1",
                                "apiPort",
                                "4028",
                                "statsWhitelist",
                                Arrays.asList(
                                        "STATS.0.json-stats",
                                        "STATS.0.Elapsed"))),
                new FakeRpcMinerServer(
                        4028,
                        ImmutableMap.of(
                                "{\"command\":\"summary\"}",
                                new RpcHandler(
                                        "{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1551286943,\"Code\":11,\"Msg\":\"Summary\",\"Description\":\"cgminer 4.10.0-spx\"}],\"SUMMARY\":[{\"Elapsed\":147407,\"MHS av\":512711.84,\"MHS 5s\":377763.37,\"MHS 1m\":509012.61,\"MHS 5m\":536603.26,\"MHS 15m\":536431.39,\"Found Blocks\":0,\"Getworks\":3180,\"Accepted\":17010,\"Rejected\":7,\"Hardware Errors\":3844,\"Utility\":6.92,\"Discarded\":1128923,\"Stale\":0,\"Get Failures\":0,\"Local Work\":2351678,\"Remote Failures\":0,\"Network Blocks\":941,\"Total MH\":75577469626.0000,\"Work Utility\":7162.52,\"Difficulty Accepted\":17381836.70500923,\"Difficulty Rejected\":7168.00000000,\"Difficulty Stale\":0.00000000,\"Best Share\":4852321,\"Device Hardware%\":0.0218,\"Device Rejected%\":0.0407,\"Pool Rejected%\":0.0412,\"Pool Stale%\":0.0000,\"Last getwork\":1551286943}],\"id\":1}"),
                                "{\"command\":\"stats\"}",
                                new RpcHandler(
                                        "{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1551286943,\"Code\":70,\"Msg\":\"CGMiner stats\",\"Description\":\"cgminer 4.10.0-spx\"}],\"STATS\":[{\"STATS\":0,\"ID\":\"SP360\",\"Elapsed\":147407,\"Calls\":0,\"Wait\":0.000000,\"Max\":0.000000,\"Min\":99999999.000000,\"json-stats\":\"{\\\"top-board\\\":{\\\"power\\\":2176,\\\"psu-temp\\\":47,\\\"psu-name\\\":\\\"Artesyn2000\\\",\\\"i2c-psu-error%\\\":0.28,\\\"reported-hashrate\\\":270005,\\\"avg-hashrate\\\":254783,\\\"hardware-error%\\\":2.02,\\\"min-temp\\\":47,\\\"max-temp\\\":97,\\\"rear-temp\\\":66,\\\"front-temp\\\":44,\\\"loops-enabled\\\":[true,true,true,true,true],\\\"working-asics\\\":75,\\\"enabled-engines\\\":441},\\\"bottom-board\\\":{\\\"power\\\":2144,\\\"psu-temp\\\":47,\\\"psu-name\\\":\\\"Artesyn2000\\\",\\\"i2c-psu-error%\\\":0.3,\\\"reported-hashrate\\\":269410,\\\"avg-hashrate\\\":257932,\\\"hardware-error%\\\":0.72,\\\"min-temp\\\":47,\\\"max-temp\\\":93,\\\"rear-temp\\\":65,\\\"front-temp\\\":43,\\\"loops-enabled\\\":[true,true,true,true,true],\\\"working-asics\\\":75,\\\"enabled-engines\\\":442},\\\"fan-speeds\\\":[12028,11094,11956,11094],\\\"fan-percent\\\":100}\"},{\"STATS\":1,\"ID\":\"POOL0\",\"Elapsed\":147407,\"Calls\":0,\"Wait\":0.000000,\"Max\":0.000000,\"Min\":99999999.000000,\"Pool Calls\":0,\"Pool Attempts\":0,\"Pool Wait\":0.000000,\"Pool Max\":0.000000,\"Pool Min\":99999999.000000,\"Pool Av\":0.000000,\"Work Had Roll Time\":false,\"Work Can Roll\":false,\"Work Had Expire\":false,\"Work Roll Time\":0,\"Work Diff\":1024.00000000,\"Min Diff\":256.00000000,\"Max Diff\":1024.00000000,\"Min Diff Count\":1463,\"Max Diff Count\":2344953,\"Times Sent\":17019,\"Bytes Sent\":2014090,\"Times Recv\":20203,\"Bytes Recv\":3233999,\"Net Bytes Sent\":2014090,\"Net Bytes Recv\":3233999},{\"STATS\":2,\"ID\":\"POOL1\",\"Elapsed\":147407,\"Calls\":0,\"Wait\":0.000000,\"Max\":0.000000,\"Min\":99999999.000000,\"Pool Calls\":0,\"Pool Attempts\":0,\"Pool Wait\":0.000000,\"Pool Max\":0.000000,\"Pool Min\":99999999.000000,\"Pool Av\":0.000000,\"Work Had Roll Time\":false,\"Work Can Roll\":false,\"Work Had Expire\":false,\"Work Roll Time\":0,\"Work Diff\":64.00000000,\"Min Diff\":64.00000000,\"Max Diff\":64.00000000,\"Min Diff Count\":2,\"Max Diff Count\":2,\"Times Sent\":3,\"Bytes Sent\":243,\"Times Recv\":6,\"Bytes Recv\":1292,\"Net Bytes Sent\":243,\"Net Bytes Recv\":1292}],\"id\":1}"),
                                "{\"command\":\"pools\"}",
                                new RpcHandler(
                                        "{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1551286943,\"Code\":7,\"Msg\":\"2 Pool(s)\",\"Description\":\"cgminer 4.10.0-spx\"}],\"POOLS\":[{\"POOL\":0,\"URL\":\"stratum+tcp://dash.coinmine.pl:6099\",\"Status\":\"Alive\",\"Priority\":0,\"Quota\":1,\"Long Poll\":\"N\",\"Getworks\":3178,\"Accepted\":17010,\"Rejected\":7,\"Works\":1221819,\"Discarded\":1128923,\"Stale\":0,\"Get Failures\":0,\"Remote Failures\":0,\"User\":\"xxx.001SPX36\",\"Last Share Time\":1551286940,\"Diff1 Shares\":17596800,\"Proxy Type\":\"\",\"Proxy\":\"\",\"Difficulty Accepted\":17381836.70500923,\"Difficulty Rejected\":7168.00000000,\"Difficulty Stale\":0.00000000,\"Last Share Difficulty\":1024.00000000,\"Work Difficulty\":1024.00000000,\"Has Stratum\":true,\"Stratum Active\":true,\"Stratum URL\":\"dash.coinmine.pl\",\"Stratum Difficulty\":1024.00000000,\"Has GBT\":false,\"Best Share\":4852321,\"Pool Rejected%\":0.0412,\"Pool Stale%\":0.0000,\"Bad Work\":75,\"Current Block Height\":1028421,\"Current Block Version\":536870912},{\"POOL\":1,\"URL\":\"stratum+tcp://x11.usa.nicehash.com:3336\",\"Status\":\"Alive\",\"Priority\":1,\"Quota\":1,\"Long Poll\":\"N\",\"Getworks\":2,\"Accepted\":0,\"Rejected\":0,\"Works\":0,\"Discarded\":0,\"Stale\":0,\"Get Failures\":0,\"Remote Failures\":0,\"User\":\"xxx.001SPX36\",\"Last Share Time\":0,\"Diff1 Shares\":0,\"Proxy Type\":\"\",\"Proxy\":\"\",\"Difficulty Accepted\":0.00000000,\"Difficulty Rejected\":0.00000000,\"Difficulty Stale\":0.00000000,\"Last Share Difficulty\":0.00000000,\"Work Difficulty\":64.00000000,\"Has Stratum\":true,\"Stratum Active\":false,\"Stratum URL\":\"\",\"Stratum Difficulty\":0.00000000,\"Has GBT\":false,\"Best Share\":0,\"Pool Rejected%\":0.0000,\"Pool Stale%\":0.0000,\"Bad Work\":2,\"Current Block Height\":534971,\"Current Block Version\":536870912}],\"id\":1}"))),
                new MinerStats.Builder()
                        .setApiIp("127.0.0.1")
                        .setApiPort(4028)
                        .addPool(
                                new Pool.Builder()
                                        .setName("dash.coinmine.pl:6099")
                                        .setWorker("xxx.001SPX36")
                                        .setPriority(0)
                                        .setStatus(
                                                true,
                                                true)
                                        .setCounts(
                                                17010,
                                                7,
                                                0)
                                        .build())
                        .addPool(
                                new Pool.Builder()
                                        .setName("x11.usa.nicehash.com:3336")
                                        .setWorker("xxx.001SPX36")
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
                                        .setHashRate(new BigDecimal("512711840000.00"))
                                        .setBoards(2)
                                        .setFanInfo(
                                                new FanInfo.Builder()
                                                        .setCount(4)
                                                        .addSpeed(12028)
                                                        .addSpeed(11094)
                                                        .addSpeed(11956)
                                                        .addSpeed(11094)
                                                        .setSpeedUnits("RPM")
                                                        .build())
                                        .addTemp(44)
                                        .addTemp(66)
                                        .addTemp(47)
                                        .addTemp(43)
                                        .addTemp(65)
                                        .addTemp(47)
                                        .addRawStats(
                                                ImmutableMap.of(
                                                        "STATS.0.json-stats",
                                                        "{\"top-board\":{\"power\":2176,\"psu-temp\":47,\"psu-name\":\"Artesyn2000\",\"i2c-psu-error%\":0.28,\"reported-hashrate\":270005,\"avg-hashrate\":254783,\"hardware-error%\":2.02,\"min-temp\":47,\"max-temp\":97,\"rear-temp\":66,\"front-temp\":44,\"loops-enabled\":[true,true,true,true,true],\"working-asics\":75,\"enabled-engines\":441},\"bottom-board\":{\"power\":2144,\"psu-temp\":47,\"psu-name\":\"Artesyn2000\",\"i2c-psu-error%\":0.3,\"reported-hashrate\":269410,\"avg-hashrate\":257932,\"hardware-error%\":0.72,\"min-temp\":47,\"max-temp\":93,\"rear-temp\":65,\"front-temp\":43,\"loops-enabled\":[true,true,true,true,true],\"working-asics\":75,\"enabled-engines\":442},\"fan-speeds\":[12028,11094,11956,11094],\"fan-percent\":100}",
                                                        "STATS.0.Elapsed",
                                                        new BigDecimal("147407")))
                                        .build())
                        .build());
    }
}