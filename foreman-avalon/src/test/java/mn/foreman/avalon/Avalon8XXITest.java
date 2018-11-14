package mn.foreman.avalon;

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
public class Avalon8XXITest
        extends AbstractApiITest {

    /** Constructor. */
    public Avalon8XXITest() {
        super(
                new AvalonFactory()
                        .create(
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
                                        "{\n" +
                                                "  \"STATUS\": [\n" +
                                                "    {\n" +
                                                "      \"STATUS\": \"S\",\n" +
                                                "      \"When\": 1502904710,\n" +
                                                "      \"Code\": 70,\n" +
                                                "      \"Msg\": \"CGMiner stats\",\n" +
                                                "      \"Description\": \"cgminer 4.10.0\"\n" +
                                                "    }\n" +
                                                "  ],\n" +
                                                "  \"STATS\": [\n" +
                                                "    {\n" +
                                                "      \"STATS\": 0,\n" +
                                                "      \"ID\": \"AV80\",\n" +
                                                "      \"Elapsed\": 63,\n" +
                                                "      \"Calls\": 0,\n" +
                                                "      \"Wait\": 0,\n" +
                                                "      \"Max\": 0,\n" +
                                                "      \"Min\": 99999999,\n" +
                                                "      \"MM ID1\": \"Ver[8411803-14b0f10] DNA[0137dace492a2ec1] Elapsed[66] MW[18528 18830 19159 17631] LW[74148] MH[0 1 0 0] HW[1] Temp[36] TMax[84] Fan[5730] FanR[100%] Vi[1197 1197 1209 1209] Vo[3893 3889 3932 3890] GHSmm[13646.20] WU[183226.76] Freq[762.87] PG[15] Led[0] MW0[3 4 0 2 3 4 3 1 4 5 2 4 3 5 4 4 5 7 3 3 4 2 2 2 2 5] MW1[2 8 3 5 6 4 5 5 7 5 5 7 3 6 0 3 4 5 4 4 7 3 6 6 0 2] MW2[3 2 5 5 2 3 1 4 3 6 5 2 7 7 5 3 3 6 3 2 5 4 3 6 4 4] MW3[5 1 6 2 4 3 4 3 1 2 5 2 6 6 1 5 5 4 6 5 6 5 3 1 2 6] TA[104] ECHU[0 0 0 0] ECMM[0] FM[1] CRC[0 0 0 0]\",\n" +
                                                "      \"MM ID2\": \"Ver[8211803-14b0f10] DNA[013a7366855f4c56] Elapsed[66] MW[18509 18976 17286 17815] LW[72586] MH[0 0 0 0] HW[0] Temp[36] TMax[85] Fan[5850] FanR[100%] Vi[1199 1199 1198 1198] Vo[3930 3955 3997 3961] GHSmm[11609.65] WU[156933.29] Freq[649.02] PG[15] Led[0] MW0[3 2 4 4 1 4 2 4 2 4 1 1 4 3 5 3 2 1 3 5 4 3 2 3 4 4] MW1[1 3 5 1 5 5 3 3 1 4 4 3 4 2 3 1 5 5 5 4 4 5 2 3 3 2] MW2[4 3 6 4 6 4 5 4 2 4 1 3 3 3 2 4 2 4 3 5 8 2 3 4 5 3] MW3[2 1 2 2 6 6 5 5 3 4 2 4 3 5 4 7 3 1 5 2 1 3 0 2 3 1] TA[104] ECHU[0 0 0 0] ECMM[0] FM[1] CRC[0 0 0 0]\",\n" +
                                                "      \"MM ID3\": \"Ver[8211803-14b0f10] DNA[013b8d95d80b8043] Elapsed[65] MW[18708 19175 17876 18406] LW[74165] MH[0 0 0 0] HW[0] Temp[37] TMax[83] Fan[5610] FanR[100%] Vi[1198 1198 1198 1198] Vo[3939 3913 3898 3909] GHSmm[11513.45] WU[153327.38] Freq[643.64] PG[15] Led[0] MW0[3 5 1 3 2 2 5 4 5 0 4 2 3 3 2 3 7 4 4 4 4 4 4 2 1 4] MW1[1 0 4 2 3 6 3 7 4 3 3 4 2 4 2 3 4 7 1 8 2 5 2 3 6 5] MW2[3 2 1 3 2 3 4 4 1 2 2 4 1 4 2 6 6 2 3 3 4 3 2 6 1 1] MW3[2 1 3 2 4 3 5 6 2 3 5 2 2 6 2 2 2 5 1 4 4 2 4 2 2 3] TA[104] ECHU[0 0 0 0] ECMM[0] FM[1] CRC[0 0 0 0]\",\n" +
                                                "      \"MM Count\": 3,\n" +
                                                "      \"Smart Speed\": 1,\n" +
                                                "      \"Connecter\": \"AUC\",\n" +
                                                "      \"AUC VER\": \"AUC-20151208\",\n" +
                                                "      \"AUC I2C Speed\": 400000,\n" +
                                                "      \"AUC I2C XDelay\": 19200,\n" +
                                                "      \"AUC Sensor\": 13870,\n" +
                                                "      \"AUC Temperature\": 33.4,\n" +
                                                "      \"Connection Overloaded\": false,\n" +
                                                "      \"Voltage Level Offset\": 0,\n" +
                                                "      \"Nonce Mask\": 24,\n" +
                                                "      \"USB Pipe\": \"0\",\n" +
                                                "      \"USB Delay\": \"r0 0.000000 w0 0.000000\",\n" +
                                                "      \"USB tmo\": \"0 0\"\n" +
                                                "    },\n" +
                                                "    {\n" +
                                                "      \"STATS\": 1,\n" +
                                                "      \"ID\": \"POOL0\",\n" +
                                                "      \"Elapsed\": 63,\n" +
                                                "      \"Calls\": 0,\n" +
                                                "      \"Wait\": 0,\n" +
                                                "      \"Max\": 0,\n" +
                                                "      \"Min\": 99999999,\n" +
                                                "      \"Pool Calls\": 0,\n" +
                                                "      \"Pool Attempts\": 0,\n" +
                                                "      \"Pool Wait\": 0,\n" +
                                                "      \"Pool Max\": 0,\n" +
                                                "      \"Pool Min\": 99999999,\n" +
                                                "      \"Pool Av\": 0,\n" +
                                                "      \"Work Had Roll Time\": false,\n" +
                                                "      \"Work Can Roll\": false,\n" +
                                                "      \"Work Had Expire\": false,\n" +
                                                "      \"Work Roll Time\": 0,\n" +
                                                "      \"Work Diff\": 27706,\n" +
                                                "      \"Min Diff\": 8192,\n" +
                                                "      \"Max Diff\": 27706,\n" +
                                                "      \"Min Diff Count\": 588,\n" +
                                                "      \"Max Diff Count\": 489,\n" +
                                                "      \"Times Sent\": 51,\n" +
                                                "      \"Bytes Sent\": 5978,\n" +
                                                "      \"Times Recv\": 56,\n" +
                                                "      \"Bytes Recv\": 6394,\n" +
                                                "      \"Net Bytes Sent\": 5978,\n" +
                                                "      \"Net Bytes Recv\": 6394\n" +
                                                "    },\n" +
                                                "    {\n" +
                                                "      \"STATS\": 2,\n" +
                                                "      \"ID\": \"POOL1\",\n" +
                                                "      \"Elapsed\": 63,\n" +
                                                "      \"Calls\": 0,\n" +
                                                "      \"Wait\": 0,\n" +
                                                "      \"Max\": 0,\n" +
                                                "      \"Min\": 99999999,\n" +
                                                "      \"Pool Calls\": 0,\n" +
                                                "      \"Pool Attempts\": 0,\n" +
                                                "      \"Pool Wait\": 0,\n" +
                                                "      \"Pool Max\": 0,\n" +
                                                "      \"Pool Min\": 99999999,\n" +
                                                "      \"Pool Av\": 0,\n" +
                                                "      \"Work Had Roll Time\": false,\n" +
                                                "      \"Work Can Roll\": false,\n" +
                                                "      \"Work Had Expire\": false,\n" +
                                                "      \"Work Roll Time\": 0,\n" +
                                                "      \"Work Diff\": 0,\n" +
                                                "      \"Min Diff\": 0,\n" +
                                                "      \"Max Diff\": 0,\n" +
                                                "      \"Min Diff Count\": 0,\n" +
                                                "      \"Max Diff Count\": 0,\n" +
                                                "      \"Times Sent\": 2,\n" +
                                                "      \"Bytes Sent\": 153,\n" +
                                                "      \"Times Recv\": 4,\n" +
                                                "      \"Bytes Recv\": 1659,\n" +
                                                "      \"Net Bytes Sent\": 153,\n" +
                                                "      \"Net Bytes Recv\": 1659\n" +
                                                "    }\n" +
                                                "  ],\n" +
                                                "  \"id\": 1\n" +
                                                "}"),
                                "{\"command\":\"pools\"}",
                                new RpcHandler(
                                        "{\n" +
                                                "  \"STATUS\": [\n" +
                                                "    {\n" +
                                                "      \"STATUS\": \"S\",\n" +
                                                "      \"When\": 1502904710,\n" +
                                                "      \"Code\": 7,\n" +
                                                "      \"Msg\": \"2 Pool(s)\",\n" +
                                                "      \"Description\": \"cgminer 4.10.0\"\n" +
                                                "    }\n" +
                                                "  ],\n" +
                                                "  \"POOLS\": [\n" +
                                                "    {\n" +
                                                "      \"POOL\": 0,\n" +
                                                "      \"URL\": \"stratum+tcp:\\/\\/eu.stratum.slushpool.com:3333\",\n" +
                                                "      \"Status\": \"Alive\",\n" +
                                                "      \"Priority\": 0,\n" +
                                                "      \"Quota\": 1,\n" +
                                                "      \"Long Poll\": \"N\",\n" +
                                                "      \"Getworks\": 3,\n" +
                                                "      \"Accepted\": 49,\n" +
                                                "      \"Rejected\": 0,\n" +
                                                "      \"Works\": 1083,\n" +
                                                "      \"Discarded\": 34,\n" +
                                                "      \"Stale\": 0,\n" +
                                                "      \"Get Failures\": 0,\n" +
                                                "      \"Remote Failures\": 0,\n" +
                                                "      \"User\": \"Foreman is great!\",\n" +
                                                "      \"Last Share Time\": 1502904704,\n" +
                                                "      \"Diff1 Shares\": 540000,\n" +
                                                "      \"Proxy Type\": \"\",\n" +
                                                "      \"Proxy\": \"\",\n" +
                                                "      \"Difficulty Accepted\": 479464,\n" +
                                                "      \"Difficulty Rejected\": 0,\n" +
                                                "      \"Difficulty Stale\": 0,\n" +
                                                "      \"Last Share Difficulty\": 27706,\n" +
                                                "      \"Work Difficulty\": 27706,\n" +
                                                "      \"Has Stratum\": true,\n" +
                                                "      \"Stratum Active\": true,\n" +
                                                "      \"Stratum URL\": \"eu.stratum.slushpool.com\",\n" +
                                                "      \"Stratum Difficulty\": 27706,\n" +
                                                "      \"Has GBT\": false,\n" +
                                                "      \"Best Share\": 941891,\n" +
                                                "      \"Pool Rejected%\": 0,\n" +
                                                "      \"Pool Stale%\": 0,\n" +
                                                "      \"Bad Work\": 0,\n" +
                                                "      \"Current Block Height\": 549924,\n" +
                                                "      \"Current Block Version\": 536870912\n" +
                                                "    },\n" +
                                                "    {\n" +
                                                "      \"POOL\": 1,\n" +
                                                "      \"URL\": \"stratum+tcp:\\/\\/eu.stratum.slushpool.com:3333\",\n" +
                                                "      \"Status\": \"Alive\",\n" +
                                                "      \"Priority\": 1,\n" +
                                                "      \"Quota\": 1,\n" +
                                                "      \"Long Poll\": \"N\",\n" +
                                                "      \"Getworks\": 1,\n" +
                                                "      \"Accepted\": 0,\n" +
                                                "      \"Rejected\": 0,\n" +
                                                "      \"Works\": 0,\n" +
                                                "      \"Discarded\": 0,\n" +
                                                "      \"Stale\": 0,\n" +
                                                "      \"Get Failures\": 0,\n" +
                                                "      \"Remote Failures\": 0,\n" +
                                                "      \"User\": \"Foreman is great!\",\n" +
                                                "      \"Last Share Time\": 0,\n" +
                                                "      \"Diff1 Shares\": 0,\n" +
                                                "      \"Proxy Type\": \"\",\n" +
                                                "      \"Proxy\": \"\",\n" +
                                                "      \"Difficulty Accepted\": 0,\n" +
                                                "      \"Difficulty Rejected\": 0,\n" +
                                                "      \"Difficulty Stale\": 0,\n" +
                                                "      \"Last Share Difficulty\": 0,\n" +
                                                "      \"Work Difficulty\": 0,\n" +
                                                "      \"Has Stratum\": true,\n" +
                                                "      \"Stratum Active\": false,\n" +
                                                "      \"Stratum URL\": \"\",\n" +
                                                "      \"Stratum Difficulty\": 0,\n" +
                                                "      \"Has GBT\": false,\n" +
                                                "      \"Best Share\": 0,\n" +
                                                "      \"Pool Rejected%\": 0,\n" +
                                                "      \"Pool Stale%\": 0,\n" +
                                                "      \"Bad Work\": 0,\n" +
                                                "      \"Current Block Height\": 0,\n" +
                                                "      \"Current Block Version\": 536870912\n" +
                                                "    }\n" +
                                                "  ],\n" +
                                                "  \"id\": 1\n" +
                                                "}"))),
                new MinerStats.Builder()
                        .setApiIp("127.0.0.1")
                        .setApiPort(4028)
                        .addPool(
                                new Pool.Builder()
                                        .setName("eu.stratum.slushpool.com:3333")
                                        .setPriority(0)
                                        .setStatus(
                                                true,
                                                true)
                                        .setCounts(
                                                49,
                                                0,
                                                0)
                                        .build())
                        .addPool(
                                new Pool.Builder()
                                        .setName("eu.stratum.slushpool.com:3333")
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
                                        .setHashRate(new BigDecimal("36769300000000.00"))
                                        .setFanInfo(
                                                new FanInfo.Builder()
                                                        .setCount(3)
                                                        .addSpeed(100)
                                                        .addSpeed(100)
                                                        .addSpeed(100)
                                                        .setSpeedUnits("%")
                                                        .build())
                                        .addTemp(36)
                                        .addTemp(84)
                                        .addTemp(36)
                                        .addTemp(85)
                                        .addTemp(37)
                                        .addTemp(83)
                                        .build())
                        .build());
    }
}