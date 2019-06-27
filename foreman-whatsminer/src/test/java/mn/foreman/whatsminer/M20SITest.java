package mn.foreman.whatsminer;

import mn.foreman.cgminer.CgMiner;
import mn.foreman.model.miners.FanInfo;
import mn.foreman.model.miners.MinerStats;
import mn.foreman.model.miners.Pool;
import mn.foreman.model.miners.asic.Asic;
import mn.foreman.util.AbstractApiITest;
import mn.foreman.util.rpc.FakeRpcMinerServer;
import mn.foreman.util.rpc.RpcHandler;

import com.google.common.collect.ImmutableMap;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.Charset;

/** Runs an integration tests using {@link CgMiner} against a fake API. */
public class M20SITest
        extends AbstractApiITest {

    /**
     * Constructor.
     *
     * @throws IOException on failure to read stats file.
     */
    public M20SITest()
            throws IOException {
        super(
                new WhatsminerFactory().create(
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
                                        "{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1560974720,\"Code\":11,\"Msg\":\"Summary\",\"Description\":\"cgminer 4.9.2\"}],\"SUMMARY\":[{\"Elapsed\":97139,\"MHS av\":65492089.33,\"MHS 5s\":65371302.78,\"MHS 1m\":65463822.08,\"MHS 5m\":65599323.93,\"MHS 15m\":65496891.24,\"Found Blocks\":0,\"Getworks\":3512,\"Accepted\":33825,\"Rejected\":185,\"Hardware Errors\":1601,\"Utility\":20.89,\"Discarded\":2580546,\"Stale\":27,\"Get Failures\":1,\"Local Work\":788804989,\"Remote Failures\":0,\"Network Blocks\":181,\"Total MH\":6361827211172.0000,\"Work Utility\":914916.96,\"Difficulty Accepted\":1473980432.00000000,\"Difficulty Rejected\":8081331.00000000,\"Difficulty Stale\":0.00000000,\"Best Share\":5452261792,\"Temperature\":75.00,\"freq_max\":656,\"freq_min\":616,\"freq_avg\":636,\"Fan Speed In\":4590,\"Fan Speed Out\":4560,\"Voltage\":1170,\"Device Hardware%\":0.0001,\"Device Rejected%\":0.5456,\"Pool Rejected%\":0.5453,\"Pool Stale%\":0.0000,\"Last getwork\":0,\"Uptime\":98801,\"Chip Data\":\"K46K241-1918 BIN2-B\",\"Power Current\":258,\"Power Fanspeed\":8160,\"Error Code Count\":0,\"Factory Error Code Count\":0,\"Security Mode\":0,\"Asicboost\":true,\"Asicboost Works\":4,\"Liquid Cooling\":false}],\"id\":1}"),
                                "{\"command\":\"stats\"}",
                                new RpcHandler(
                                        IOUtils.toString(
                                                M20SITest.class.getResourceAsStream(
                                                        "/m20s.stats.json"),
                                                Charset.defaultCharset())),
                                "{\"command\":\"pools\"}",
                                new RpcHandler(
                                        "{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1560974720,\"Code\":7,\"Msg\":\"2 Pool(s)\",\"Description\":\"cgminer 4.9.2\"}],\"POOLS\":[{\"POOL\":0,\"URL\":\"stratum+tcp://us-east.stratum.slushpool.com:3333\",\"Status\":\"Alive\",\"Priority\":0,\"Quota\":1,\"Long Poll\":\"N\",\"Getworks\":3512,\"Accepted\":33825,\"Rejected\":185,\"Works\":788544801,\"Discarded\":2580368,\"Stale\":27,\"Get Failures\":1,\"Remote Failures\":0,\"User\":\"002m20s\",\"Last Share Time\":1560974720,\"Diff1 Shares\":1481234400,\"Proxy Type\":\"\",\"Proxy\":\"\",\"Difficulty Accepted\":1473980432.00000000,\"Difficulty Rejected\":8081331.00000000,\"Difficulty Stale\":0.00000000,\"Last Share Difficulty\":49174.00000000,\"Work Difficulty\":49174.00000000,\"Has Stratum\":true,\"Stratum Active\":true,\"Stratum URL\":\"us-east.stratum.slushpool.com\",\"Stratum Difficulty\":49174.00000000,\"Has GBT\":false,\"Best Share\":5452261792,\"Pool Rejected%\":0.5453,\"Pool Stale%\":0.0000,\"Bad Work\":183,\"Current Block Height\":581482,\"Current Block Version\":536870912},{\"POOL\":1,\"URL\":\"stratum+tcp://us-central01.miningrigrentals.com:50194\",\"Status\":\"Alive\",\"Priority\":1,\"Quota\":1,\"Long Poll\":\"N\",\"Getworks\":0,\"Accepted\":0,\"Rejected\":0,\"Works\":0,\"Discarded\":0,\"Stale\":0,\"Get Failures\":0,\"Remote Failures\":0,\"User\":\"2\",\"Last Share Time\":0,\"Diff1 Shares\":0,\"Proxy Type\":\"\",\"Proxy\":\"\",\"Difficulty Accepted\":0.00000000,\"Difficulty Rejected\":0.00000000,\"Difficulty Stale\":0.00000000,\"Last Share Difficulty\":0.00000000,\"Work Difficulty\":0.00000000,\"Has Stratum\":true,\"Stratum Active\":false,\"Stratum URL\":\"\",\"Stratum Difficulty\":0.00000000,\"Has GBT\":false,\"Best Share\":0,\"Pool Rejected%\":0.0000,\"Pool Stale%\":0.0000,\"Bad Work\":0,\"Current Block Height\":0,\"Current Block Version\":536870912}],\"id\":1}"))),
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
                                        .build())
                        .build());
    }
}