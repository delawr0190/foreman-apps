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
public class Avalon7XXStatsITest
        extends AbstractApiITest {

    /** Constructor. */
    public Avalon7XXStatsITest() {
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
                                "{\"command\":\"summary\"}",
                                new RpcHandler(
                                        "{\n" +
                                                "  \"STATUS\": [\n" +
                                                "    {\n" +
                                                "      \"STATUS\": \"S\",\n" +
                                                "      \"When\": 1487889040,\n" +
                                                "      \"Code\": 11,\n" +
                                                "      \"Msg\": \"Summary\",\n" +
                                                "      \"Description\": \"cgminer 4.10.0\"\n" +
                                                "    }\n" +
                                                "  ],\n" +
                                                "  \"SUMMARY\": [\n" +
                                                "    {\n" +
                                                "      \"Elapsed\": 118,\n" +
                                                "      \"MHS av\": 47071128.8,\n" +
                                                "      \"MHS 5s\": 52126282.15,\n" +
                                                "      \"MHS 1m\": 42592214.65,\n" +
                                                "      \"MHS 5m\": 15441333.8,\n" +
                                                "      \"MHS 15m\": 5789185.8,\n" +
                                                "      \"Found Blocks\": 0,\n" +
                                                "      \"Getworks\": 8,\n" +
                                                "      \"Accepted\": 19,\n" +
                                                "      \"Rejected\": 1,\n" +
                                                "      \"Hardware Errors\": 3,\n" +
                                                "      \"Utility\": 9.69,\n" +
                                                "      \"Discarded\": 62,\n" +
                                                "      \"Stale\": 0,\n" +
                                                "      \"Get Failures\": 0,\n" +
                                                "      \"Local Work\": 5367,\n" +
                                                "      \"Remote Failures\": 0,\n" +
                                                "      \"Network Blocks\": 1,\n" +
                                                "      \"Total MH\": 5537208847,\n" +
                                                "      \"Work Utility\": 675309.63,\n" +
                                                "      \"Difficulty Accepted\": 819200,\n" +
                                                "      \"Difficulty Rejected\": 32768,\n" +
                                                "      \"Difficulty Stale\": 0,\n" +
                                                "      \"Best Share\": 1548937,\n" +
                                                "      \"Device Hardware%\": 0.0002,\n" +
                                                "      \"Device Rejected%\": 2.4749,\n" +
                                                "      \"Pool Rejected%\": 3.8462,\n" +
                                                "      \"Pool Stale%\": 0,\n" +
                                                "      \"Last getwork\": 1487889038\n" +
                                                "    }\n" +
                                                "  ],\n" +
                                                "  \"id\": 1\n" +
                                                "}"),
                                "{\"command\":\"stats\"}",
                                new RpcHandler(
                                        "{\n" +
                                                "  \"STATUS\": [\n" +
                                                "    {\n" +
                                                "      \"STATUS\": \"S\",\n" +
                                                "      \"When\": 1487889040,\n" +
                                                "      \"Code\": 70,\n" +
                                                "      \"Msg\": \"CGMiner stats\",\n" +
                                                "      \"Description\": \"cgminer 4.10.0\"\n" +
                                                "    }\n" +
                                                "  ],\n" +
                                                "  \"STATS\": [\n" +
                                                "    {\n" +
                                                "      \"STATS\": 0,\n" +
                                                "      \"ID\": \"AV70\",\n" +
                                                "      \"Elapsed\": 118,\n" +
                                                "      \"Calls\": 0,\n" +
                                                "      \"Wait\": 0,\n" +
                                                "      \"Max\": 0,\n" +
                                                "      \"Min\": 99999999,\n" +
                                                "      \"MM ID1\": \"Ver[7411706-3162860] DNA[0130b14ab881ad86] Elapsed[126] MW[1289 1298 1302 1245] LW[5134] MH[0 0 0 0] HW[0] DH[0.000%] Temp[38] TMax[89] Fan[2550] FanR[36%] Vi[1215 1216 1215 1215] Vo[4074 4069 4065 4078] GHSmm[6836.48] WU[75407.22] Freq[606.93] PG[15] Led[0] MW0[1 4 4 4 7 3 3 2 1 0 4 1 6 1 8 7 2 1 6 6 2 9] MW1[3 3 4 4 4 2 0 6 7 3 2 2 7 3 1 4 8 2 3 5 2 3] MW2[4 5 6 3 2 5 1 3 3 3 3 0 8 3 5 4 1 4 6 6 6 3] MW3[2 4 2 4 5 0 2 6 4 5 2 3 4 5 2 2 4 4 2 4 2 4] TA[88] ECHU[16 0 512 0] ECMM[0] FM[1] CRC[0 0 0 0] PAIRS[0 0 0] PVT_T[21-80\\/14-88\\/84 0-81\\/11-89\\/86 21-78\\/10-87\\/83 20-77\\/11-85\\/81]\",\n" +
                                                "      \"MM ID2\": \"Ver[7411706-3162860] DNA[0131718cc72fb662] Elapsed[91] MW[594 528 550 550] LW[2222] MH[88 0 0 0] HW[88] DH[0.000%] Temp[38] TMax[80] Fan[1440] FanR[16%] Vi[1222 1222 1224 1223] Vo[4069 4074 4055 4069] GHSmm[6801.85] WU[37979.97] Freq[603.86] PG[15] Led[0] MW0[2 1 0 1 2 0 1 1 3 1 4 1 1 2 1 1 4 0 1 2 2 3] MW1[0 4 1 0 3 0 2 1 4 1 1 2 1 2 0 1 1 2 1 1 1 0] MW2[1 1 2 0 2 1 0 2 2 5 0 2 2 1 2 1 0 1 0 1 3 0] MW3[1 1 1 2 1 3 2 1 1 0 2 1 0 0 0 0 1 0 3 2 1 0] TA[88] ECHU[16 512 512 512] ECMM[0] FM[1] CRC[0 0 0 0] PAIRS[0 0 0] PVT_T[0-74\\/17-80\\/77 18-72\\/17-79\\/77 19-71\\/6-76\\/77 0-75\\/14-79\\/77]\",\n" +
                                                "      \"MM ID3\": \"Ver[7411706-3162860] DNA[013260099eef155d] Elapsed[125] MW[1254 1254 1298 1320] LW[5126] MH[0 0 0 1] HW[1] DH[0.000%] Temp[43] TMax[92] Fan[2700] FanR[38%] Vi[1194 1194 1195 1195] Vo[4074 4055 4060 4065] GHSmm[6844.33] WU[70863.33] Freq[607.63] PG[15] Led[0] MW0[9 3 4 1 7 1 3 1 3 2 3 6 4 3 4 0 1 7 5 4 4 8] MW1[4 3 4 3 0 3 5 4 6 4 3 6 5 2 4 1 3 6 3 2 3 2] MW2[2 3 3 2 6 1 1 1 5 3 0 2 5 0 4 2 5 4 6 1 4 7] MW3[4 3 5 4 1 5 5 1 2 1 4 2 3 1 5 4 2 4 4 3 5 1] TA[88] ECHU[528 0 0 0] ECMM[0] FM[1] CRC[0 0 0 0] PAIRS[0 0 0] PVT_T[21-83\\/11-91\\/87 21-81\\/12-91\\/87 21-83\\/11-92\\/90 21-82\\/16-90\\/85]\",\n" +
                                                "      \"MM ID4\": \"Ver[7411706-3162860] DNA[013ad84934eaef0b] Elapsed[124] MW[1333 1276 1276 1298] LW[5183] MH[0 0 0 0] HW[0] DH[0.000%] Temp[40] TMax[87] Fan[2580] FanR[36%] Vi[1210 1210 1205 1205] Vo[4065 4046 4041 4046] GHSmm[6844.12] WU[67002.09] Freq[607.61] PG[15] Led[0] MW0[5 5 3 2 4 3 8 1 6 4 3 2 3 4 0 2 3 3 2 1 2 2] MW1[2 2 2 1 1 1 3 5 6 4 4 0 1 1 5 2 11 4 4 3 2 2] MW2[6 4 2 3 3 2 2 4 4 1 3 5 3 2 5 1 5 3 1 3 1 4] MW3[2 2 3 2 3 6 1 6 6 6 4 2 7 4 3 3 3 1 4 5 2 2] TA[88] ECHU[16 0 0 512] ECMM[0] FM[1] CRC[0 0 0 0] PAIRS[0 0 0] PVT_T[0-80\\/11-86\\/83 0-79\\/11-87\\/84 21-80\\/11-87\\/85 21-80\\/15-86\\/81]\",\n" +
                                                "      \"MM ID5\": \"Ver[7411706-3162860] DNA[013e59ac2d1dda2d] Elapsed[124] MW[1276 1289 1298 1298] LW[5161] MH[0 0 0 0] HW[0] DH[0.000%] Temp[37] TMax[88] Fan[2430] FanR[34%] Vi[1228 1228 1224 1224] Vo[4083 4060 4041 4046] GHSmm[6822.16] WU[64565.96] Freq[605.66] PG[15] Led[0] MW0[3 5 3 3 8 2 5 6 2 2 0 2 2 1 3 2 0 1 3 2 6 8] MW1[5 3 3 4 1 2 2 1 3 4 0 5 3 2 4 2 2 2 4 1 4 4] MW2[4 2 2 3 3 1 0 4 0 2 0 3 5 4 5 2 4 2 0 3 1 3] MW3[7 5 0 2 5 5 2 6 5 3 2 3 0 6 6 5 5 3 2 2 5 5] TA[88] ECHU[16 0 0 0] ECMM[0] FM[1] CRC[0 0 0 0] PAIRS[0 0 0] PVT_T[21-77\\/12-87\\/83 21-80\\/11-88\\/85 0-79\\/11-87\\/83 21-78\\/11-85\\/81]\",\n" +
                                                "      \"MM Count\": 5,\n" +
                                                "      \"Smart Speed\": 1,\n" +
                                                "      \"Connecter\": \"AUC\",\n" +
                                                "      \"AUC VER\": \"AUC-20151208\",\n" +
                                                "      \"AUC I2C Speed\": 400000,\n" +
                                                "      \"AUC I2C XDelay\": 19200,\n" +
                                                "      \"AUC Sensor\": 13806,\n" +
                                                "      \"AUC Temperature\": 33.61,\n" +
                                                "      \"Connection Overloaded\": false,\n" +
                                                "      \"Voltage Offset\": -2,\n" +
                                                "      \"Nonce Mask\": 29,\n" +
                                                "      \"USB Pipe\": \"0\",\n" +
                                                "      \"USB Delay\": \"r0 0.000000 w0 0.000000\",\n" +
                                                "      \"USB tmo\": \"0 0\"\n" +
                                                "    },\n" +
                                                "    {\n" +
                                                "      \"STATS\": 1,\n" +
                                                "      \"ID\": \"AV71\",\n" +
                                                "      \"Elapsed\": 118,\n" +
                                                "      \"Calls\": 0,\n" +
                                                "      \"Wait\": 0,\n" +
                                                "      \"Max\": 0,\n" +
                                                "      \"Min\": 99999999,\n" +
                                                "      \"MM ID1\": \"Ver[7411706-3162860] DNA[0130769f2af64e5b] Elapsed[123] MW[1333 1232 1276 1298] LW[5139] MH[0 0 0 0] HW[0] DH[0.000%] Temp[38] TMax[84] Fan[4440] FanR[54%] Vi[1202 1202 1209 1209] Vo[4014 4055 4037 4041] GHSmm[6839.72] WU[75991.53] Freq[607.22] PG[15] Led[0] MW0[4 1 6 6 3 2 2 2 2 3 3 1 3 5 1 6 4 6 6 1 2 3] MW1[4 3 1 6 4 4 5 3 6 0 1 6 0 2 2 1 4 6 4 4 4 6] MW2[5 2 1 3 2 3 8 7 2 5 3 3 4 5 4 6 5 3 5 2 3 4] MW3[6 2 2 1 4 0 2 7 3 2 1 6 2 5 7 3 2 4 7 5 4 4] TA[88] ECHU[16 0 512 512] ECMM[0] FM[1] CRC[0 0 0 0] PAIRS[0 0 0] PVT_T[21-70\\/11-81\\/74 0-74\\/13-84\\/78 21-74\\/13-82\\/77 21-74\\/11-81\\/78]\",\n" +
                                                "      \"MM ID2\": \"Ver[7411706-3162860] DNA[013160a63eb10cb3] Elapsed[123] MW[1270 1276 1298 1298] LW[5142] MH[0 0 0 1] HW[1] DH[0.000%] Temp[38] TMax[89] Fan[3900] FanR[61%] Vi[1185 1185 1193 1193] Vo[4037 4055 4074 4051] GHSmm[6824.96] WU[58163.66] Freq[605.91] PG[15] Led[0] MW0[5 6 3 2 1 4 0 1 2 1 4 0 6 0 1 1 2 3 2 8 6 5] MW1[1 5 4 0 0 0 4 0 2 1 1 2 1 3 0 3 5 5 4 6 4 4] MW2[3 4 5 0 5 0 2 4 4 0 1 0 0 4 5 0 7 4 3 3 6 6] MW3[3 0 0 5 3 5 0 0 3 3 0 2 5 3 1 2 2 0 5 8 1 3] TA[88] ECHU[16 0 0 0] ECMM[0] FM[1] CRC[0 0 0 0] PAIRS[0 0 0] PVT_T[0-72\\/10-83\\/74 0-75\\/5-89\\/79 21-75\\/10-88\\/76 0-74\\/11-86\\/78]\",\n" +
                                                "      \"MM ID3\": \"Ver[7411706-3162860] DNA[0131ce597bd2dfd4] Elapsed[122] MW[1320 1267 1276 1289] LW[5152] MH[0 0 0 0] HW[0] DH[0.000%] Temp[40] TMax[87] Fan[3150] FanR[33%] Vi[1211 1211 1200 1200] Vo[4074 4083 4078 4051] GHSmm[6798.86] WU[61058.09] Freq[603.59] PG[15] Led[0] MW0[2 3 5 5 3 1 3 1 1 1 3 0 3 1 0 0 4 1 7 5 3 5] MW1[2 1 6 1 2 3 1 1 7 6 0 1 0 0 1 2 3 1 4 4 4 4] MW2[0 5 4 1 4 11 2 5 5 8 2 0 6 2 5 2 5 5 4 5 6 9] MW3[3 1 3 3 0 4 2 1 2 0 0 0 4 3 3 0 2 2 1 1 5 2] TA[88] ECHU[528 512 0 0] ECMM[0] FM[1] CRC[0 0 0 0] PAIRS[0 0 0] PVT_T[21-76\\/15-86\\/82 0-80\\/15-86\\/83 20-77\\/10-87\\/83 20-77\\/10-83\\/78]\",\n" +
                                                "      \"MM ID4\": \"Ver[7411706-3162860] DNA[013916a9df2a8748] Elapsed[122] MW[1289 1298 1320 1254] LW[5161] MH[0 0 0 1] HW[1] DH[0.000%] Temp[42] TMax[90] Fan[2760] FanR[28%] Vi[1213 1213 1202 1202] Vo[4065 4078 4032 3991] GHSmm[6805.02] WU[72091.75] Freq[604.14] PG[15] Led[0] MW0[2 2 2 2 5 1 2 2 2 3 8 5 5 0 0 4 2 3 3 3 3 5] MW1[6 4 2 2 2 5 1 2 4 2 2 2 3 2 5 6 4 5 6 5 1 4] MW2[1 8 4 5 2 6 3 0 1 0 2 2 0 4 3 4 4 8 3 5 4 7] MW3[0 5 4 2 6 3 3 7 3 5 2 4 8 3 3 1 5 4 1 4 4 1] TA[88] ECHU[16 0 0 512] ECMM[0] FM[1] CRC[0 0 0 0] PAIRS[0 0 0] PVT_T[0-78\\/12-86\\/82 21-83\\/12-90\\/85 0-81\\/11-87\\/86 2-78\\/14-84\\/83]\",\n" +
                                                "      \"MM ID5\": \"Ver[7411706-3162860] DNA[013c309b35a2329a] Elapsed[122] MW[1320 1276 1289 1298] LW[5183] MH[0 0 0 0] HW[0] DH[0.000%] Temp[37] TMax[78] Fan[3810] FanR[44%] Vi[1188 1188 1196 1196] Vo[4055 4037 4065 4060] GHSmm[6826.01] WU[71843.85] Freq[606.00] PG[15] Led[0] MW0[2 4 1 7 5 4 1 5 3 1 7 3 5 0 6 3 5 2 3 4 5 3] MW1[7 2 3 5 1 6 6 1 3 1 4 1 3 4 4 0 3 3 2 3 4 2] MW2[4 6 5 1 5 6 2 4 1 3 2 1 5 5 3 6 1 3 4 2 2 2] MW3[3 1 7 4 5 3 3 2 3 3 2 6 1 6 1 4 4 1 2 6 2 2] TA[88] ECHU[16 0 0 0] ECMM[0] FM[1] CRC[0 0 0 0] PAIRS[0 0 0] PVT_T[21-69\\/9-78\\/73 0-68\\/13-77\\/73 21-68\\/10-76\\/70 21-67\\/9-76\\/72]\",\n" +
                                                "      \"MM Count\": 5,\n" +
                                                "      \"Smart Speed\": 1,\n" +
                                                "      \"Connecter\": \"AUC\",\n" +
                                                "      \"AUC VER\": \"AUC-20151208\",\n" +
                                                "      \"AUC I2C Speed\": 400000,\n" +
                                                "      \"AUC I2C XDelay\": 19200,\n" +
                                                "      \"AUC Sensor\": 14225,\n" +
                                                "      \"AUC Temperature\": 32.22,\n" +
                                                "      \"Connection Overloaded\": false,\n" +
                                                "      \"Voltage Offset\": -2,\n" +
                                                "      \"Nonce Mask\": 29,\n" +
                                                "      \"USB Pipe\": \"0\",\n" +
                                                "      \"USB Delay\": \"r0 0.000000 w0 0.000000\",\n" +
                                                "      \"USB tmo\": \"0 0\"\n" +
                                                "    },\n" +
                                                "    {\n" +
                                                "      \"STATS\": 2,\n" +
                                                "      \"ID\": \"POOL0\",\n" +
                                                "      \"Elapsed\": 118,\n" +
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
                                                "      \"Work Diff\": 131072,\n" +
                                                "      \"Min Diff\": 32768,\n" +
                                                "      \"Max Diff\": 131072,\n" +
                                                "      \"Min Diff Count\": 1296,\n" +
                                                "      \"Max Diff Count\": 1208,\n" +
                                                "      \"Times Sent\": 22,\n" +
                                                "      \"Bytes Sent\": 2579,\n" +
                                                "      \"Times Recv\": 29,\n" +
                                                "      \"Bytes Recv\": 6966,\n" +
                                                "      \"Net Bytes Sent\": 2579,\n" +
                                                "      \"Net Bytes Recv\": 6966\n" +
                                                "    },\n" +
                                                "    {\n" +
                                                "      \"STATS\": 3,\n" +
                                                "      \"ID\": \"POOL1\",\n" +
                                                "      \"Elapsed\": 118,\n" +
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
                                                "      \"Bytes Sent\": 145,\n" +
                                                "      \"Times Recv\": 6,\n" +
                                                "      \"Bytes Recv\": 3827,\n" +
                                                "      \"Net Bytes Sent\": 145,\n" +
                                                "      \"Net Bytes Recv\": 3827\n" +
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
                                                "      \"When\": 1487889040,\n" +
                                                "      \"Code\": 7,\n" +
                                                "      \"Msg\": \"2 Pool(s)\",\n" +
                                                "      \"Description\": \"cgminer 4.10.0\"\n" +
                                                "    }\n" +
                                                "  ],\n" +
                                                "  \"POOLS\": [\n" +
                                                "    {\n" +
                                                "      \"POOL\": 0,\n" +
                                                "      \"URL\": \"stratum+tcp:\\/\\/stratum.antpool.com:3333\",\n" +
                                                "      \"Status\": \"Alive\",\n" +
                                                "      \"Priority\": 0,\n" +
                                                "      \"Quota\": 1,\n" +
                                                "      \"Long Poll\": \"N\",\n" +
                                                "      \"Getworks\": 5,\n" +
                                                "      \"Accepted\": 19,\n" +
                                                "      \"Rejected\": 1,\n" +
                                                "      \"Works\": 2663,\n" +
                                                "      \"Discarded\": 62,\n" +
                                                "      \"Stale\": 0,\n" +
                                                "      \"Get Failures\": 0,\n" +
                                                "      \"Remote Failures\": 0,\n" +
                                                "      \"User\": \"Yop yop\",\n" +
                                                "      \"Last Share Time\": 1487889012,\n" +
                                                "      \"Diff1 Shares\": 1329000,\n" +
                                                "      \"Proxy Type\": \"\",\n" +
                                                "      \"Proxy\": \"\",\n" +
                                                "      \"Difficulty Accepted\": 819200,\n" +
                                                "      \"Difficulty Rejected\": 32768,\n" +
                                                "      \"Difficulty Stale\": 0,\n" +
                                                "      \"Last Share Difficulty\": 131072,\n" +
                                                "      \"Work Difficulty\": 131072,\n" +
                                                "      \"Has Stratum\": true,\n" +
                                                "      \"Stratum Active\": true,\n" +
                                                "      \"Stratum URL\": \"stratum.antpool.com\",\n" +
                                                "      \"Stratum Difficulty\": 131072,\n" +
                                                "      \"Has GBT\": false,\n" +
                                                "      \"Best Share\": 1548937,\n" +
                                                "      \"Pool Rejected%\": 3.8462,\n" +
                                                "      \"Pool Stale%\": 0,\n" +
                                                "      \"Bad Work\": 0,\n" +
                                                "      \"Current Block Height\": 556495,\n" +
                                                "      \"Current Block Version\": 536870912\n" +
                                                "    },\n" +
                                                "    {\n" +
                                                "      \"POOL\": 1,\n" +
                                                "      \"URL\": \"stratum+tcp:\\/\\/stratum.antpool.com:443\",\n" +
                                                "      \"Status\": \"Alive\",\n" +
                                                "      \"Priority\": 1,\n" +
                                                "      \"Quota\": 1,\n" +
                                                "      \"Long Poll\": \"N\",\n" +
                                                "      \"Getworks\": 3,\n" +
                                                "      \"Accepted\": 0,\n" +
                                                "      \"Rejected\": 0,\n" +
                                                "      \"Works\": 0,\n" +
                                                "      \"Discarded\": 0,\n" +
                                                "      \"Stale\": 0,\n" +
                                                "      \"Get Failures\": 0,\n" +
                                                "      \"Remote Failures\": 0,\n" +
                                                "      \"User\": \"Yop yop\",\n" +
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
                                        .setName("stratum.antpool.com:3333")
                                        .setPriority(0)
                                        .setStatus(
                                                true,
                                                true)
                                        .setCounts(
                                                19,
                                                1,
                                                0)
                                        .build())
                        .addPool(
                                new Pool.Builder()
                                        .setName("stratum.antpool.com:443")
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
                                        .setHashRate(new BigDecimal("52126282150000.000"))
                                        .setFanInfo(
                                                new FanInfo.Builder()
                                                        .setCount(10)
                                                        .addSpeed(36)
                                                        .addSpeed(16)
                                                        .addSpeed(38)
                                                        .addSpeed(36)
                                                        .addSpeed(34)
                                                        .addSpeed(54)
                                                        .addSpeed(61)
                                                        .addSpeed(33)
                                                        .addSpeed(28)
                                                        .addSpeed(44)
                                                        .setSpeedUnits("%")
                                                        .build())
                                        .addTemp(38)
                                        .addTemp(89)
                                        .addTemp(38)
                                        .addTemp(80)
                                        .addTemp(43)
                                        .addTemp(92)
                                        .addTemp(40)
                                        .addTemp(87)
                                        .addTemp(37)
                                        .addTemp(88)
                                        .addTemp(38)
                                        .addTemp(84)
                                        .addTemp(38)
                                        .addTemp(89)
                                        .addTemp(40)
                                        .addTemp(87)
                                        .addTemp(42)
                                        .addTemp(90)
                                        .addTemp(37)
                                        .addTemp(78)
                                        .build())
                        .build());
    }
}