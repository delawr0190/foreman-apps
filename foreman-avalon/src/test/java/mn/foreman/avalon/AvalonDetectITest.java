package mn.foreman.avalon;

import mn.foreman.cgminer.CgMinerDetectionStrategy;
import mn.foreman.cgminer.request.CgMinerCommand;
import mn.foreman.model.ApplicationConfiguration;
import mn.foreman.model.Detection;
import mn.foreman.util.AbstractDetectITest;
import mn.foreman.util.rpc.FakeRpcMinerServer;
import mn.foreman.util.rpc.HandlerInterface;
import mn.foreman.util.rpc.RpcHandler;

import com.google.common.collect.ImmutableMap;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/** Tests detection of avalon miners. */
@RunWith(Parameterized.class)
public class AvalonDetectITest
        extends AbstractDetectITest {

    /**
     * Constructor.
     *
     * @param handlers     The handlers.
     * @param expectedType The expected type.
     */
    public AvalonDetectITest(
            final Map<String, HandlerInterface> handlers,
            final AvalonType expectedType) {
        super(
                new CgMinerDetectionStrategy(
                        CgMinerCommand.STATS,
                        new AvalonTypeFactory(),
                        new ApplicationConfiguration()),
                () -> new FakeRpcMinerServer(
                        4028,
                        handlers),
                Detection.builder()
                        .minerType(expectedType)
                        .ipAddress("127.0.0.1")
                        .port(4028)
                        .parameters(DEFAULT_ARGS)
                        .build());
    }

    /**
     * Test parameters
     *
     * @return The test parameters.
     */
    @SuppressWarnings("DuplicateExpressions")
    @Parameterized.Parameters
    public static List<Object[]> parameters() {
        return Arrays.asList(
                new Object[][]{
                        {
                                // Avalon 7XX
                                ImmutableMap.of(
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
                                                        "}")),
                                AvalonType.AVALON_741
                        },
                        {
                                // Avalon 8XX
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
                                                        "}")),
                                AvalonType.AVALON_841
                        },
                        {
                                // Avalon 10XX
                                ImmutableMap.of(
                                        "{\"command\":\"stats\"}",
                                        new RpcHandler(
                                                "{\n" +
                                                        "  \"STATUS\": [\n" +
                                                        "    {\n" +
                                                        "      \"STATUS\": \"S\",\n" +
                                                        "      \"When\": 103858,\n" +
                                                        "      \"Code\": 70,\n" +
                                                        "      \"Msg\": \"CGMiner stats\",\n" +
                                                        "      \"Description\": \"cgminer 4.11.1\"\n" +
                                                        "    }\n" +
                                                        "  ],\n" +
                                                        "  \"STATS\": [\n" +
                                                        "    {\n" +
                                                        "      \"STATS\": 0,\n" +
                                                        "      \"ID\": \"AVA100\",\n" +
                                                        "      \"Elapsed\": 103808,\n" +
                                                        "      \"Calls\": 0,\n" +
                                                        "      \"Wait\": 0.0,\n" +
                                                        "      \"Max\": 0.0,\n" +
                                                        "      \"Min\": 99999999.0,\n" +
                                                        "      \"MM ID0\": \"Ver[1047-19120401_d46bc64_0367a8b] DNA[0201000017cdf409] MEMFREE[2601024 B] NETFAIL[0 0 0 0 0 0 0 0] SYSTEMSTATU[Work: In Work, Hash Board: 2 ] Elapsed[103840]BOOTBY[1] LW[80542068] MH[61 74] HW[135] DH[1.063%] Temp[28] TMax[75] TAvg[64] Fan1[2984] Fan2[2972] FanR[47%] Vo[328] PS[0 1212 1315 174 2288 1320] PLL0[1107 955 1414 3604] PLL1[1555 1162 1463 2900] GHSmm[37768.67] GHSavg[37235.34] WU[520171.72] Freq[666.82] Led[0] MGHS[18727.58 18507.76] MTmax[75 70] MTavg[66 62] TA[240] ECHU[512 512] ECMM[0] SF0[612 637 662 687] SF1[612 637 662 687] PVT_T0[ 70  69  67  66  68  70  69  67  66  63  67  69  67  68  63  63  68  68  69  67  64  62  68  68  71  67  65  65  67  70  68  66  64  64  65  67  66  64  62  59  64  66  64  60  55  55  60  63  61  60  56  56  59  61  60  59  56  56  58  59  61  62  61  62  63  62  62  64  64  64  65  63  64  64  64  66  67  65  69  71  70  69  71  70  70  71  70  69  72  71  71  73  71  71  73  71  71  73  72  73  74  71  71  73  72  73  75  71  70  75  73  73  75  71  73  75  73  70  73  72] PVT_T1[ 66  66  63  61  65  65  64  62  59  58  61  63  63  60  56  56  60  63  64  63  58  60  64  65  65  64  60  61  64  66  66  64  61  61  64  66  64  63  59  58  61  64  60  59  55  54  57  58  57  56  54  53  56  57  57  56  54  53  54  55  55  57  55  57  58  57  59  59  59  60  60  60  60  62  61  62  64  61  65  68  67  67  68  66  66  68  68  68  67  66  66  69  67  66  68  66  67  70  67  66  67  66  66  67  65  66  67  66  65  66  67  67  68  66  66  68  66  65  67  66] PVT_V0[311 314 314 317 317 315 312 312 312 314 313 312 316 313 315 315 312 312 315 314 312 314 313 312 313 311 312 315 317 315 313 312 312 317 317 318 314 315 315 322 323 322 322 323 323 328 327 327 323 322 324 322 323 321 325 324 324 324 326 329 329 325 325 333 334 336 323 323 322 326 327 327 318 319 319 321 322 323 315 315 316 323 324 322 313 312 310 315 313 313 311 314 316 312 313 313 315 313 314 307 307 309 313 313 314 315 315 318 308 310 312 310 312 313 311 314 316 319 323 325] PVT_V1[319 322 325 315 315 316 318 319 319 317 318 319 314 315 317 325 325 325 320 320 321 320 319 320 322 322 321 316 315 315 312 311 313 313 315 313 317 318 318 323 321 322 325 325 327 326 326 327 327 328 329 330 332 333 331 333 333 326 328 331 332 327 326 323 325 326 325 324 327 322 323 322 320 320 321 315 316 317 317 313 311 316 315 314 312 314 314 309 310 312 308 309 312 306 309 309 323 320 323 312 313 310 319 318 319 319 319 316 321 317 316 317 318 319 310 310 313 314 318 321] MW[40271276 40271213] MW0[3527 3387 3807 3538 3462 3676 3617 3308 3781 3525 3591 3693 3574 3827 3386 3517 3721 3681 3672 3568 3647 3326 3742 3610 3679 3641 3512 3529 3487 3764 3692 3699 3588 3643 3747 3858 3709 3585 3682 3767 3764 3626 3755 3764 3664 3835 3880 3894 3733 3938 3708 3786 3712 3863 3794 3745 3742 3854 3869 3773 3790 3858 3755 3801 3766 3737 3649 3702 3775 3816 3751 3753 3677 3903 3728 3912 3848 3790 3566 3588 3527 3873 3731 3831 3643 3574 3681 3767 3750 3687 3778 3780 3687 3626 3716 3483 3602 3530 3562 3498 3742 3604 3763 3817 3496 3783 3674 3660 3607 3593 3537 3536 3568 3798 3593 3580 3739 3696 3718 3708] MW1[3644 3763 3805 3515 3792 3573 3805 3698 3685 3604 3752 3862 3735 3788 3476 3827 3665 3767 3877 3803 3549 3547 3797 3766 3689 3749 3555 3295 3619 3799 3628 3659 3504 3792 3461 3771 3815 3538 3514 3532 3720 3929 3797 3863 3757 3758 3817 3807 3799 3927 3514 3866 3853 3604 3747 3794 3761 3766 3844 3907 3779 3783 3721 3621 3754 3754 3808 3781 3765 3660 3706 3809 3462 3588 3547 3644 3517 3534 3427 3611 3675 3648 3480 3477 3132 3387 3458 3491 3291 2979 3499 3666 3463 3129 3574 3395 3563 3714 3598 3370 3448 3592 3723 3533 3606 3688 3560 3764 3488 3647 3700 3728 3652 3511 3324 3552 3541 3671 3682 3463] CRC[8 7] POW_I2C_CONN[SUCCES] HS_RCD0[Tenv:28 Tavg:65 Fan1:3171 Fan2:3183 V12:1212 Vout:1315 P_I:174 P_P:2288] HS_RCD1[Tenv:28 Tavg:65 Fan1:3176 Fan2:3188 V12:1214 Vout:1314 P_I:174 P_P:2288] HS_RCD2[Tenv:29 Tavg:65 Fan1:3207 Fan2:3183 V12:1214 Vout:1315 P_I:174 P_P:2288] HS_RCD3[Tenv:29 Tavg:65 Fan1:3204 Fan2:3204 V12:1212 Vout:1315 P_I:174 P_P:2301] HS_RCD4[Tenv:28 Tavg:65 Fan1:3210 Fan2:3198 V12:1212 Vout:1315 P_I:174 P_P:2288] FACOPTS0[] FACOPTS1[] WORKMODE[1]\",\n" +
                                                        "      \"MM Count\": 1,\n" +
                                                        "      \"Smart Speed\": 1,\n" +
                                                        "      \"Connection Overloaded\": false,\n" +
                                                        "      \"Voltage Level Offset\": 0,\n" +
                                                        "      \"Nonce Mask\": 25\n" +
                                                        "    },\n" +
                                                        "    {\n" +
                                                        "      \"STATS\": 1,\n" +
                                                        "      \"ID\": \"POOL0\",\n" +
                                                        "      \"Elapsed\": 103808,\n" +
                                                        "      \"Calls\": 0,\n" +
                                                        "      \"Wait\": 0.0,\n" +
                                                        "      \"Max\": 0.0,\n" +
                                                        "      \"Min\": 99999999.0,\n" +
                                                        "      \"Pool Calls\": 0,\n" +
                                                        "      \"Pool Attempts\": 0,\n" +
                                                        "      \"Pool Wait\": 0.0,\n" +
                                                        "      \"Pool Max\": 0.0,\n" +
                                                        "      \"Pool Min\": 99999999.0,\n" +
                                                        "      \"Pool Av\": 0.0,\n" +
                                                        "      \"Work Had Roll Time\": false,\n" +
                                                        "      \"Work Can Roll\": false,\n" +
                                                        "      \"Work Had Expire\": false,\n" +
                                                        "      \"Work Roll Time\": 0,\n" +
                                                        "      \"Work Diff\": 65536.0,\n" +
                                                        "      \"Min Diff\": 32768.0,\n" +
                                                        "      \"Max Diff\": 79868.0,\n" +
                                                        "      \"Min Diff Count\": 40435,\n" +
                                                        "      \"Max Diff Count\": 9193,\n" +
                                                        "      \"Times Sent\": 14625,\n" +
                                                        "      \"Bytes Sent\": 1999329,\n" +
                                                        "      \"Times Recv\": 23527,\n" +
                                                        "      \"Bytes Recv\": 6525984,\n" +
                                                        "      \"Net Bytes Sent\": 1999329,\n" +
                                                        "      \"Net Bytes Recv\": 6525984\n" +
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
                                                        "}")),
                                AvalonType.AVALON_1047
                        }
                });
    }
}
