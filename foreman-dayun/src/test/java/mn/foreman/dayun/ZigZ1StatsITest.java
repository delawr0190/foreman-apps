package mn.foreman.dayun;

import mn.foreman.cgminer.CgMiner;
import mn.foreman.model.miners.FanInfo;
import mn.foreman.model.miners.MinerStats;
import mn.foreman.model.miners.Pool;
import mn.foreman.model.miners.asic.Asic;
import mn.foreman.util.AbstractApiITest;
import mn.foreman.util.rpc.FakeRpcMinerServer;
import mn.foreman.util.rpc.RpcHandler;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;

/** Runs an integration tests using {@link CgMiner} against a fake API. */
public class ZigZ1StatsITest
        extends AbstractApiITest {

    /**
     * Constructor.
     *
     * @throws IOException never.
     */
    public ZigZ1StatsITest() throws IOException {
        super(
                new DayunFactory().create(
                        ImmutableMap.of(
                                "apiIp",
                                "127.0.0.1",
                                "apiPort",
                                "4028")),
                new FakeRpcMinerServer(
                        4028,
                        ImmutableMap.of(
                                "{\"command\":\"stats\"}",
                                new RpcHandler(
                                        "{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1538857703,\"Code\":70,\"Msg\":\"CGMiner stats\",\"Description\":\"cgminer 4.11.1\"}],\"STATS\":[{\"STATS\":0,\"ID\":\"lyra2rev20\",\"Elapsed\":60664275,\"Calls\":0,\"Wait\":0.000000,\"Max\":0.000000,\"Min\":99999999.000000,\"MHS 30S\":3355.44,\"MHS 5m\":4697.62,\"Fan Nunber\":2,\"Fan In\":3570,\"Fan Out\":3540,\"Frequency\":900,\"Temperature Core\":24,\"AutoFrequency\":false,\"CheckNonceSubmit\":true,\"AutoStopMiner_Enable\":false,\"AutoStopMiner_Temperature\":50,\"AutoStopMiner_Fan_In\":100,\"AutoStopMiner_Fan_Out\":100,\"AutoRestart_Enable\":false,\"AutoRestart_Hashrate\":7,\"AutoRestart_FailedRate\":100,\"CH1\":{\"Temperature\": 26, \"MHS 30S\": 2236, \"status\": [{\"x\": 0, \"y\": 0, \"accept\": 38, \"failed\": 0, \"reject\": 0}, {\"x\": 0, \"y\": 1, \"accept\": 48, \"failed\": 0, \"reject\": 1}, {\"x\": 0, \"y\": 2, \"accept\": 44, \"failed\": 0, \"reject\": 0}, {\"x\": 0, \"y\": 3, \"accept\": 59, \"failed\": 0, \"reject\": 2}, {\"x\": 0, \"y\": 4, \"accept\": 54, \"failed\": 0, \"reject\": 2}, {\"x\": 0, \"y\": 5, \"accept\": 43, \"failed\": 0, \"reject\": 1}, {\"x\": 0, \"y\": 6, \"accept\": 54, \"failed\": 0, \"reject\": 3}, {\"x\": 0, \"y\": 7, \"accept\": 51, \"failed\": 0, \"reject\": 1}, {\"x\": 0, \"y\": 8, \"accept\": 49, \"failed\": 0, \"reject\": 0}, {\"x\": 0, \"y\": 9, \"accept\": 58, \"failed\": 0, \"reject\": 2}, {\"x\": 0, \"y\": 10, \"accept\": 45, \"failed\": 0, \"reject\": 1}, {\"x\": 0, \"y\": 11, \"accept\": 36, \"failed\": 0, \"reject\": 0}, {\"x\": 0, \"y\": 12, \"accept\": 41, \"failed\": 0, \"reject\": 0}, {\"x\": 0, \"y\": 13, \"accept\": 66, \"failed\": 0, \"reject\": 1}, {\"x\": 0, \"y\": 14, \"accept\": 46, \"failed\": 0, \"reject\": 0}, {\"x\": 0, \"y\": 15, \"accept\": 34, \"failed\": 0, \"reject\": 0}, {\"x\": 0, \"y\": 16, \"accept\": 38, \"failed\": 0, \"reject\": 1}, {\"x\": 0, \"y\": 17, \"accept\": 44, \"failed\": 0, \"reject\": 0}, {\"x\": 0, \"y\": 18, \"accept\": 50, \"failed\": 0, \"reject\": 1}, {\"x\": 0, \"y\": 19, \"accept\": 41, \"failed\": 0, \"reject\": 1}, {\"x\": 0, \"y\": 20, \"accept\": 47, \"failed\": 0, \"reject\": 0}, {\"x\": 0, \"y\": 21, \"accept\": 39, \"failed\": 0, \"reject\": 0}, {\"x\": 0, \"y\": 22, \"accept\": 53, \"failed\": 0, \"reject\": 0}, {\"x\": 0, \"y\": 23, \"accept\": 47, \"failed\": 0, \"reject\": 0}]},\"CH2\":{\"Temperature\": 22, \"MHS 30S\": 1118, \"status\": [{\"x\": 1, \"y\": 0, \"accept\": 38, \"failed\": 0, \"reject\": 1}, {\"x\": 1, \"y\": 1, \"accept\": 33, \"failed\": 0, \"reject\": 0}, {\"x\": 1, \"y\": 2, \"accept\": 36, \"failed\": 0, \"reject\": 0}, {\"x\": 1, \"y\": 3, \"accept\": 54, \"failed\": 0, \"reject\": 2}, {\"x\": 1, \"y\": 4, \"accept\": 42, \"failed\": 0, \"reject\": 0}, {\"x\": 1, \"y\": 5, \"accept\": 51, \"failed\": 0, \"reject\": 0}, {\"x\": 1, \"y\": 6, \"accept\": 45, \"failed\": 0, \"reject\": 0}, {\"x\": 1, \"y\": 7, \"accept\": 42, \"failed\": 0, \"reject\": 1}, {\"x\": 1, \"y\": 8, \"accept\": 49, \"failed\": 0, \"reject\": 0}, {\"x\": 1, \"y\": 9, \"accept\": 49, \"failed\": 0, \"reject\": 0}, {\"x\": 1, \"y\": 10, \"accept\": 57, \"failed\": 0, \"reject\": 0}, {\"x\": 1, \"y\": 11, \"accept\": 57, \"failed\": 0, \"reject\": 0}, {\"x\": 1, \"y\": 12, \"accept\": 42, \"failed\": 0, \"reject\": 1}, {\"x\": 1, \"y\": 13, \"accept\": 53, \"failed\": 0, \"reject\": 1}, {\"x\": 1, \"y\": 14, \"accept\": 45, \"failed\": 0, \"reject\": 0}, {\"x\": 1, \"y\": 15, \"accept\": 41, \"failed\": 0, \"reject\": 0}, {\"x\": 1, \"y\": 16, \"accept\": 32, \"failed\": 0, \"reject\": 2}, {\"x\": 1, \"y\": 17, \"accept\": 42, \"failed\": 0, \"reject\": 1}, {\"x\": 1, \"y\": 18, \"accept\": 41, \"failed\": 0, \"reject\": 0}, {\"x\": 1, \"y\": 19, \"accept\": 42, \"failed\": 0, \"reject\": 1}, {\"x\": 1, \"y\": 20, \"accept\": 36, \"failed\": 0, \"reject\": 0}, {\"x\": 1, \"y\": 21, \"accept\": 46, \"failed\": 0, \"reject\": 1}, {\"x\": 1, \"y\": 22, \"accept\": 50, \"failed\": 0, \"reject\": 0}, {\"x\": 1, \"y\": 23, \"accept\": 36, \"failed\": 0, \"reject\": 0}]},\"CH3\":{\"Temperature\": 20, \"MHS 30S\": 0, \"status\": [{\"x\": 2, \"y\": 0, \"accept\": 4, \"failed\": 21, \"reject\": 0}, {\"x\": 2, \"y\": 1, \"accept\": 3, \"failed\": 14, \"reject\": 0}, {\"x\": 2, \"y\": 2, \"accept\": 6, \"failed\": 22, \"reject\": 0}, {\"x\": 2, \"y\": 3, \"accept\": 6, \"failed\": 21, \"reject\": 0}, {\"x\": 2, \"y\": 4, \"accept\": 7, \"failed\": 3, \"reject\": 0}, {\"x\": 2, \"y\": 5, \"accept\": 7, \"failed\": 18, \"reject\": 0}, {\"x\": 2, \"y\": 6, \"accept\": 7, \"failed\": 31, \"reject\": 0}, {\"x\": 2, \"y\": 7, \"accept\": 2, \"failed\": 27, \"reject\": 0}, {\"x\": 2, \"y\": 8, \"accept\": 4, \"failed\": 22, \"reject\": 0}, {\"x\": 2, \"y\": 9, \"accept\": 6, \"failed\": 0, \"reject\": 0}, {\"x\": 2, \"y\": 10, \"accept\": 2, \"failed\": 0, \"reject\": 0}, {\"x\": 2, \"y\": 11, \"accept\": 3, \"failed\": 0, \"reject\": 0}, {\"x\": 2, \"y\": 12, \"accept\": 0, \"failed\": 6, \"reject\": 0}, {\"x\": 2, \"y\": 13, \"accept\": 2, \"failed\": 0, \"reject\": 0}, {\"x\": 2, \"y\": 14, \"accept\": 3, \"failed\": 0, \"reject\": 0}, {\"x\": 2, \"y\": 15, \"accept\": 3, \"failed\": 0, \"reject\": 0}, {\"x\": 2, \"y\": 16, \"accept\": 3, \"failed\": 0, \"reject\": 0}, {\"x\": 2, \"y\": 17, \"accept\": 1, \"failed\": 0, \"reject\": 0}, {\"x\": 2, \"y\": 18, \"accept\": 4, \"failed\": 0, \"reject\": 0}, {\"x\": 2, \"y\": 19, \"accept\": 4, \"failed\": 0, \"reject\": 0}, {\"x\": 2, \"y\": 20, \"accept\": 8, \"failed\": 0, \"reject\": 0}, {\"x\": 2, \"y\": 21, \"accept\": 6, \"failed\": 0, \"reject\": 0}, {\"x\": 2, \"y\": 22, \"accept\": 4, \"failed\": 0, \"reject\": 0}, {\"x\": 2, \"y\": 23, \"accept\": 5, \"failed\": 0, \"reject\": 0}]},\"CH4\":{\"Temperature\": 18, \"MHS 30S\": 0, \"status\": [{\"x\": 3, \"y\": 0, \"accept\": 48, \"failed\": 0, \"reject\": 0}, {\"x\": 3, \"y\": 1, \"accept\": 39, \"failed\": 0, \"reject\": 0}, {\"x\": 3, \"y\": 2, \"accept\": 53, \"failed\": 0, \"reject\": 1}, {\"x\": 3, \"y\": 3, \"accept\": 42, \"failed\": 0, \"reject\": 3}, {\"x\": 3, \"y\": 4, \"accept\": 60, \"failed\": 0, \"reject\": 0}, {\"x\": 3, \"y\": 5, \"accept\": 42, \"failed\": 0, \"reject\": 0}, {\"x\": 3, \"y\": 6, \"accept\": 48, \"failed\": 0, \"reject\": 0}, {\"x\": 3, \"y\": 7, \"accept\": 41, \"failed\": 0, \"reject\": 1}, {\"x\": 3, \"y\": 8, \"accept\": 42, \"failed\": 0, \"reject\": 2}, {\"x\": 3, \"y\": 9, \"accept\": 47, \"failed\": 0, \"reject\": 1}, {\"x\": 3, \"y\": 10, \"accept\": 57, \"failed\": 0, \"reject\": 2}, {\"x\": 3, \"y\": 11, \"accept\": 49, \"failed\": 0, \"reject\": 1}, {\"x\": 3, \"y\": 12, \"accept\": 37, \"failed\": 0, \"reject\": 3}, {\"x\": 3, \"y\": 13, \"accept\": 33, \"failed\": 0, \"reject\": 0}, {\"x\": 3, \"y\": 14, \"accept\": 46, \"failed\": 0, \"reject\": 1}, {\"x\": 3, \"y\": 15, \"accept\": 59, \"failed\": 0, \"reject\": 1}, {\"x\": 3, \"y\": 16, \"accept\": 43, \"failed\": 0, \"reject\": 0}, {\"x\": 3, \"y\": 17, \"accept\": 47, \"failed\": 0, \"reject\": 0}, {\"x\": 3, \"y\": 18, \"accept\": 40, \"failed\": 0, \"reject\": 2}, {\"x\": 3, \"y\": 19, \"accept\": 51, \"failed\": 0, \"reject\": 0}, {\"x\": 3, \"y\": 20, \"accept\": 46, \"failed\": 0, \"reject\": 0}, {\"x\": 3, \"y\": 21, \"accept\": 49, \"failed\": 0, \"reject\": 2}, {\"x\": 3, \"y\": 22, \"accept\": 42, \"failed\": 0, \"reject\": 1}, {\"x\": 3, \"y\": 23, \"accept\": 44, \"failed\": 0, \"reject\": 0}]}},{\"STATS\":1,\"ID\":\"POOL0\",\"Elapsed\":60664275,\"Calls\":0,\"Wait\":0.000000,\"Max\":0.000000,\"Min\":99999999.000000,\"Pool Calls\":0,\"Pool Attempts\":0,\"Pool Wait\":0.000000,\"Pool Max\":0.000000,\"Pool Min\":99999999.000000,\"Pool Av\":0.000000,\"Work Had Roll Time\":false,\"Work Can Roll\":false,\"Work Had Expire\":false,\"Work Roll Time\":0,\"Work Diff\":0.00000000,\"Min Diff\":0.00000000,\"Max Diff\":0.00000000,\"Min Diff Count\":0,\"Max Diff Count\":0,\"Times Sent\":0,\"Bytes Sent\":0,\"Times Recv\":0,\"Bytes Recv\":0,\"Net Bytes Sent\":0,\"Net Bytes Recv\":0},{\"STATS\":2,\"ID\":\"POOL1\",\"Elapsed\":60664275,\"Calls\":0,\"Wait\":0.000000,\"Max\":0.000000,\"Min\":99999999.000000,\"Pool Calls\":0,\"Pool Attempts\":0,\"Pool Wait\":0.000000,\"Pool Max\":0.000000,\"Pool Min\":99999999.000000,\"Pool Av\":0.000000,\"Work Had Roll Time\":false,\"Work Can Roll\":false,\"Work Had Expire\":false,\"Work Roll Time\":0,\"Work Diff\":2048.00000000,\"Min Diff\":128.00000000,\"Max Diff\":2048.00000000,\"Min Diff Count\":6942,\"Max Diff Count\":1000,\"Times Sent\":3457,\"Bytes Sent\":505995,\"Times Recv\":3580,\"Bytes Recv\":184785,\"Net Bytes Sent\":505995,\"Net Bytes Recv\":184785},{\"STATS\":3,\"ID\":\"POOL2\",\"Elapsed\":60664275,\"Calls\":0,\"Wait\":0.000000,\"Max\":0.000000,\"Min\":99999999.000000,\"Pool Calls\":0,\"Pool Attempts\":0,\"Pool Wait\":0.000000,\"Pool Max\":0.000000,\"Pool Min\":99999999.000000,\"Pool Av\":0.000000,\"Work Had Roll Time\":false,\"Work Can Roll\":false,\"Work Had Expire\":false,\"Work Roll Time\":0,\"Work Diff\":0.00000000,\"Min Diff\":0.00000000,\"Max Diff\":0.00000000,\"Min Diff Count\":0,\"Max Diff Count\":0,\"Times Sent\":2,\"Bytes Sent\":168,\"Times Recv\":2,\"Bytes Recv\":135,\"Net Bytes Sent\":168,\"Net Bytes Recv\":135}],\"id\":1}"),
                                "{\"command\":\"pools\"}",
                                new RpcHandler(
                                        "{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1538857703,\"Code\":7,\"Msg\":\"3 Pool(s)\",\"Description\":\"cgminer 4.11.1\"}],\"POOLS\":[{\"POOL\":0,\"URL\":\"stratum+tcp://hub.1miningpoolhub.com:20593\",\"USER\":\"xxxxxxx.z1\",\"PASSWORD\":\"x\",\"Status\":\"Dead\",\"Priority\":0,\"Quota\":1,\"Long Poll\":\"N\",\"Getworks\":0,\"Accepted\":0,\"Rejected\":0,\"Works\":0,\"Discarded\":0,\"Stale\":0,\"Get Failures\":0,\"Remote Failures\":0,\"User\":\"xxxxxxx.z1\",\"Last Share Time\":0,\"Diff1 Shares\":0,\"Proxy Type\":\"\",\"Proxy\":\"\",\"Difficulty Accepted\":0.00000000,\"Difficulty Rejected\":0.00000000,\"Difficulty Stale\":0.00000000,\"Last Share Difficulty\":0.00000000,\"Has Stratum\":true,\"Stratum Active\":false,\"Stratum URL\":\"\",\"Has GBT\":false,\"Best Share\":0,\"Pool Rejected%\":0.0000,\"Pool Stale%\":0.0000},{\"POOL\":1,\"URL\":\"stratum+tcp://lyra2rev2.eu.nicehash.com:3347\",\"USER\":\"xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.z1\",\"PASSWORD\":\"3\",\"Status\":\"Alive\",\"Priority\":1,\"Quota\":1,\"Long Poll\":\"N\",\"Getworks\":101,\"Accepted\":3389,\"Rejected\":49,\"Works\":135,\"Discarded\":1357,\"Stale\":1,\"Get Failures\":2,\"Remote Failures\":4,\"User\":\"xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.z1\",\"Last Share Time\":1538857700,\"Diff1 Shares\":0,\"Proxy Type\":\"\",\"Proxy\":\"\",\"Difficulty Accepted\":680320.00000000,\"Difficulty Rejected\":16128.00000000,\"Difficulty Stale\":128.00000000,\"Last Share Difficulty\":2048.00000000,\"Has Stratum\":true,\"Stratum Active\":true,\"Stratum URL\":\"lyra2rev2.eu.nicehash.com\",\"Has GBT\":false,\"Best Share\":3081,\"Pool Rejected%\":2.3153,\"Pool Stale%\":0.0184},{\"POOL\":2,\"URL\":\"stratum+tcp://lyra2rev2.usa.nicehash.com:3347\",\"USER\":\"xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.z1\",\"PASSWORD\":\"3\",\"Status\":\"Alive\",\"Priority\":2,\"Quota\":1,\"Long Poll\":\"N\",\"Getworks\":0,\"Accepted\":0,\"Rejected\":0,\"Works\":0,\"Discarded\":0,\"Stale\":0,\"Get Failures\":0,\"Remote Failures\":0,\"User\":\"xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.z1\",\"Last Share Time\":0,\"Diff1 Shares\":0,\"Proxy Type\":\"\",\"Proxy\":\"\",\"Difficulty Accepted\":0.00000000,\"Difficulty Rejected\":0.00000000,\"Difficulty Stale\":0.00000000,\"Last Share Difficulty\":0.00000000,\"Has Stratum\":true,\"Stratum Active\":false,\"Stratum URL\":\"\",\"Has GBT\":false,\"Best Share\":0,\"Pool Rejected%\":0.0000,\"Pool Stale%\":0.0000}],\"id\":1}"))),
                new MinerStats.Builder()
                        .setApiIp("127.0.0.1")
                        .setApiPort(4028)
                        .addPool(
                                new Pool.Builder()
                                        .setName("hub.1miningpoolhub.com:20593")
                                        .setStatus(true, false)
                                        .setPriority(0)
                                        .setCounts(0, 0, 0)
                                        .build())
                        .addPool(
                                new Pool.Builder()
                                        .setName("lyra2rev2.eu.nicehash.com:3347")
                                        .setStatus(true, true)
                                        .setPriority(1)
                                        .setCounts(3389, 49, 1)
                                        .build())
                        .addPool(
                                new Pool.Builder()
                                        .setName("lyra2rev2.usa.nicehash.com:3347")
                                        .setStatus(true, true)
                                        .setPriority(2)
                                        .setCounts(0, 0, 0)
                                        .build())
                        .addAsic(
                                new Asic.Builder()
                                        .setHashRate(new BigDecimal("3355440000.00"))
                                        .setFanInfo(
                                                new FanInfo.Builder()
                                                        .setCount(2)
                                                        .addSpeed(3570)
                                                        .addSpeed(3540)
                                                        .setSpeedUnits("RPM")
                                                        .build())
                                        .addTemp(24)
                                        .addTemp(26)
                                        .addTemp(22)
                                        .addTemp(20)
                                        .addTemp(18)
                                        .hasErrors(false)
                                        .addFlatResponse(
                                                new ObjectMapper()
                                                        .readValue(
                                                                ZigZ1StatsITest.class.getResourceAsStream("/z1.json"),
                                                                new TypeReference<Map<String, Object>>() {
                                                                }))
                                        .build())
                        .build());
    }
}