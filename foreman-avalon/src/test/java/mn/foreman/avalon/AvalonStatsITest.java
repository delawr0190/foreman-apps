package mn.foreman.avalon;

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
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

/** Tests avalon stats obtaining. */
@RunWith(Parameterized.class)
public class AvalonStatsITest
        extends AbstractApiITest {

    /** The mapper for json. */
    private static final ObjectMapper MAPPER = new ObjectMapper();

    /**
     * Constructor.
     *
     * @param handlers      The handlers.
     * @param expectedStats The expected stats.
     */
    public AvalonStatsITest(
            final Map<String, HandlerInterface> handlers,
            final MinerStats expectedStats) {
        super(
                new AvalonFactory(0)
                        .create(
                                ImmutableMap.of(
                                        "apiIp",
                                        "127.0.0.1",
                                        "apiPort",
                                        "4028",
                                        "statsWhitelist",
                                        Arrays.asList(
                                                "summary.0.SUMMARY.0.Total MH",
                                                "summary.0.SUMMARY.0.Remote Failures"))),
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
     * @throws IOException never.
     */
    @Parameterized.Parameters
    public static Collection parameters()
            throws IOException {
        return Arrays.asList(
                new Object[][]{
                        {
                                // Avalon 7XX
                                ImmutableMap.of(
                                        "{\"command\":\"pools+summary+stats\"}",
                                        new RpcHandler(
                                                MAPPER.writeValueAsString(
                                                        ImmutableMap.of(
                                                                "summary",
                                                                Collections.singletonList(
                                                                        MAPPER.readValue(
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
                                                                                        "}",
                                                                                Map.class)),
                                                                "stats",
                                                                Collections.singletonList(
                                                                        MAPPER.readValue(
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
                                                                                        "}",
                                                                                Map.class)),
                                                                "pools",
                                                                Collections.singletonList(
                                                                        MAPPER.readValue(
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
                                                                                        "}",
                                                                                Map.class)))))),
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
                                                        .setHashRate(new BigDecimal("47071128800000.00"))
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
                                                        .addRawStats(
                                                                ImmutableMap.of(
                                                                        "summary.0.summary.0.remote_failures",
                                                                        new BigDecimal("0"),
                                                                        "summary.0.summary.0.total_mh",
                                                                        new BigDecimal("5537208847")))
                                                        .build())
                                        .build()
                        },
                        {
                                // Avalon 8XX
                                ImmutableMap.of(
                                        "{\"command\":\"pools+summary+stats\"}",
                                        new RpcHandler(
                                                MAPPER.writeValueAsString(
                                                        ImmutableMap.of(
                                                                "summary",
                                                                Collections.singletonList(
                                                                        MAPPER.readValue(
                                                                                "{\n" +
                                                                                        "  \"STATUS\": [\n" +
                                                                                        "    {\n" +
                                                                                        "      \"STATUS\": \"S\",\n" +
                                                                                        "      \"When\": 1502904710,\n" +
                                                                                        "      \"Code\": 11,\n" +
                                                                                        "      \"Msg\": \"Summary\",\n" +
                                                                                        "      \"Description\": \"cgminer 4.10.0\"\n" +
                                                                                        "    }\n" +
                                                                                        "  ],\n" +
                                                                                        "  \"SUMMARY\": [\n" +
                                                                                        "    {\n" +
                                                                                        "      \"Elapsed\": 63,\n" +
                                                                                        "      \"MHS av\": 36588896.32,\n" +
                                                                                        "      \"MHS 5s\": 38567094.38,\n" +
                                                                                        "      \"MHS 1m\": 24137604.01,\n" +
                                                                                        "      \"MHS 5m\": 6963354.58,\n" +
                                                                                        "      \"MHS 15m\": 2481506.1,\n" +
                                                                                        "      \"Found Blocks\": 0,\n" +
                                                                                        "      \"Getworks\": 4,\n" +
                                                                                        "      \"Accepted\": 49,\n" +
                                                                                        "      \"Rejected\": 0,\n" +
                                                                                        "      \"Hardware Errors\": 1,\n" +
                                                                                        "      \"Utility\": 46.55,\n" +
                                                                                        "      \"Discarded\": 34,\n" +
                                                                                        "      \"Stale\": 0,\n" +
                                                                                        "      \"Get Failures\": 0,\n" +
                                                                                        "      \"Local Work\": 2191,\n" +
                                                                                        "      \"Remote Failures\": 0,\n" +
                                                                                        "      \"Network Blocks\": 1,\n" +
                                                                                        "      \"Total MH\": 2310692276,\n" +
                                                                                        "      \"Work Utility\": 511141.01,\n" +
                                                                                        "      \"Difficulty Accepted\": 479464,\n" +
                                                                                        "      \"Difficulty Rejected\": 0,\n" +
                                                                                        "      \"Difficulty Stale\": 0,\n" +
                                                                                        "      \"Best Share\": 941891,\n" +
                                                                                        "      \"Device Hardware%\": 0.0002,\n" +
                                                                                        "      \"Device Rejected%\": 0,\n" +
                                                                                        "      \"Pool Rejected%\": 0,\n" +
                                                                                        "      \"Pool Stale%\": 0,\n" +
                                                                                        "      \"Last getwork\": 1502904709\n" +
                                                                                        "    }\n" +
                                                                                        "  ],\n" +
                                                                                        "  \"id\": 1\n" +
                                                                                        "}",
                                                                                Map.class)),
                                                                "stats",
                                                                Collections.singletonList(
                                                                        MAPPER.readValue(
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
                                                                                        "}",
                                                                                Map.class)),
                                                                "pools",
                                                                Collections.singletonList(
                                                                        MAPPER.readValue(
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
                                                                                        "}",
                                                                                Map.class)))))),
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
                                                        .setHashRate(new BigDecimal("36588896320000.000"))
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
                                                        .addRawStats(
                                                                ImmutableMap.of(
                                                                        "summary.0.summary.0.remote_failures",
                                                                        new BigDecimal("0"),
                                                                        "summary.0.summary.0.total_mh",
                                                                        new BigDecimal("2310692276")))
                                                        .build())
                                        .build()
                        },
                        {
                                // Avalon 9XX
                                ImmutableMap.of(
                                        "{\"command\":\"pools+summary+stats\"}",
                                        new RpcHandler(
                                                MAPPER.writeValueAsString(
                                                        ImmutableMap.of(
                                                                "summary",
                                                                Collections.singletonList(
                                                                        MAPPER.readValue(
                                                                                "{\n" +
                                                                                        "  \"STATUS\": [\n" +
                                                                                        "    {\n" +
                                                                                        "      \"STATUS\": \"S\",\n" +
                                                                                        "      \"When\": 1548764064,\n" +
                                                                                        "      \"Code\": 11,\n" +
                                                                                        "      \"Msg\": \"Summary\",\n" +
                                                                                        "      \"Description\": \"cgminer 4.10.0\"\n" +
                                                                                        "    }\n" +
                                                                                        "  ],\n" +
                                                                                        "  \"SUMMARY\": [\n" +
                                                                                        "    {\n" +
                                                                                        "      \"Elapsed\": 66007,\n" +
                                                                                        "      \"MHS av\": 420699742.36,\n" +
                                                                                        "      \"MHS 5s\": 473707649.51,\n" +
                                                                                        "      \"MHS 1m\": 431791164.82,\n" +
                                                                                        "      \"MHS 5m\": 420882582.93,\n" +
                                                                                        "      \"MHS 15m\": 419453495.55,\n" +
                                                                                        "      \"Found Blocks\": 0,\n" +
                                                                                        "      \"Getworks\": 2201,\n" +
                                                                                        "      \"Accepted\": 12757,\n" +
                                                                                        "      \"Rejected\": 32,\n" +
                                                                                        "      \"Hardware Errors\": 128,\n" +
                                                                                        "      \"Utility\": 11.6,\n" +
                                                                                        "      \"Discarded\": 35199,\n" +
                                                                                        "      \"Stale\": 781,\n" +
                                                                                        "      \"Get Failures\": 2,\n" +
                                                                                        "      \"Local Work\": 4482822,\n" +
                                                                                        "      \"Remote Failures\": 2,\n" +
                                                                                        "      \"Network Blocks\": 111,\n" +
                                                                                        "      \"Total MH\": 27769280354638,\n" +
                                                                                        "      \"Work Utility\": 5883832.25,\n" +
                                                                                        "      \"Difficulty Accepted\": 6436438016,\n" +
                                                                                        "      \"Difficulty Rejected\": 7389199,\n" +
                                                                                        "      \"Difficulty Stale\": 10829824,\n" +
                                                                                        "      \"Best Share\": 3540192838,\n" +
                                                                                        "      \"Device Hardware%\": 0,\n" +
                                                                                        "      \"Device Rejected%\": 0.1142,\n" +
                                                                                        "      \"Pool Rejected%\": 0.1145,\n" +
                                                                                        "      \"Pool Stale%\": 0.1678,\n" +
                                                                                        "      \"Last getwork\": 1548764060\n" +
                                                                                        "    }\n" +
                                                                                        "  ],\n" +
                                                                                        "  \"id\": 1\n" +
                                                                                        "}",
                                                                                Map.class)),
                                                                "stats",
                                                                Collections.singletonList(
                                                                        MAPPER.readValue(
                                                                                "{\n" +
                                                                                        "  \"STATUS\": [\n" +
                                                                                        "    {\n" +
                                                                                        "      \"STATUS\": \"S\",\n" +
                                                                                        "      \"When\": 1548764064,\n" +
                                                                                        "      \"Code\": 70,\n" +
                                                                                        "      \"Msg\": \"CGMiner stats\",\n" +
                                                                                        "      \"Description\": \"cgminer 4.10.0\"\n" +
                                                                                        "    }\n" +
                                                                                        "  ],\n" +
                                                                                        "  \"STATS\": [\n" +
                                                                                        "    {\n" +
                                                                                        "      \"STATS\": 0,\n" +
                                                                                        "      \"ID\": \"AV90\",\n" +
                                                                                        "      \"Elapsed\": 66007,\n" +
                                                                                        "      \"Calls\": 0,\n" +
                                                                                        "      \"Wait\": 0,\n" +
                                                                                        "      \"Max\": 0,\n" +
                                                                                        "      \"Min\": 99999999,\n" +
                                                                                        "      \"MM ID1\": \"Ver[9211809-b4b3d00] DNA[01327c4787a135a1] Elapsed[65996] MW[16620025 16617522 16615754 16613441] LW[66466742] MH[2 2 1 2] HW[7] DH[4.583%] Temp[23] TMax[94] Fan[4830] FanR[63%] Vi[1201 1201 1200 1200] Vo[4163 4153 4152 4156] GHSmm[21718.70] WU[288148.73] Freq[815.76] PG[15] Led[0] MW0[1102 1078 1005 1046 1052 1066 1047 1101 1062 1098 1026 1105 1025 1115 1106 1129 1050 1092 1125 1086 1014 1063 1097 1082 1073 1116] MW1[1080 1093 1036 1014 1082 1069 1035 1012 1003 1096 1019 1075 1065 1050 1044 1065 1017 1053 1009 1027 1052 1051 1062 1058 1064 991] MW2[1017 998 1036 1022 1055 1049 1111 1056 923 1101 966 1045 1044 1096 1040 1037 992 1045 1040 1133 986 1064 896 1028 466 1047] MW3[1056 1011 1111 1065 1001 1106 1086 1041 1029 945 1048 1055 970 1063 1057 1043 1111 1017 1134 1045 1131 1023 1128 1039 1076 1011] TA[104] ECHU[0 0 0 0] ECMM[0] FM[1] CRC[1 0 0 0]\",\n" +
                                                                                        "      \"MM ID2\": \"Ver[9211809-b4b3d00] DNA[01341eb2801a22f2] Elapsed[65995] MW[16629481 16616844 16613502 16629817] LW[66489644] MH[1 1 2 0] HW[4] DH[2.389%] Temp[24] TMax[94] Fan[4710] FanR[63%] Vi[1200 1200 1191 1191] Vo[4143 4114 4097 4131] GHSmm[22094.00] WU[299880.04] Freq[829.85] PG[15] Led[0] MW0[1125 1152 1148 1148 1106 1068 1094 1129 1096 1053 1078 1135 1126 1155 1038 1002 1057 1025 1067 1101 1091 1058 1122 1143 1117 1138] MW1[1130 1026 1059 1080 1124 1033 1059 1062 1098 1044 1086 1138 1070 1122 1121 1140 1036 1078 1106 1129 1094 1026 1109 1085 1075 1039] MW2[1092 1082 1064 1038 1049 1078 1142 1049 1078 1084 1082 1043 1126 1112 1049 1121 1087 1083 1157 1124 1026 1101 1115 1069 1051 1054] MW3[1070 1133 1088 1133 1091 1034 1135 1052 1100 1131 1113 1068 1077 1092 1100 1029 1050 1133 1108 1145 1097 1121 1017 1139 1036 1121] TA[104] ECHU[0 0 0 0] ECMM[0] FM[1] CRC[0 0 0 0]\",\n" +
                                                                                        "      \"MM ID3\": \"Ver[9211809-b4b3d00] DNA[01361f74612d6989] Elapsed[65995] MW[16631146 16621130 16625760 16633474] LW[66511510] MH[1 2 1 2] HW[6] DH[2.160%] Temp[23] TMax[94] Fan[5040] FanR[60%] Vi[1205 1205 1208 1208] Vo[4166 4163 4151 4155] GHSmm[22198.45] WU[304669.56] Freq[833.78] PG[15] Led[0] MW0[1154 1089 1096 1093 1050 1170 1083 1149 1141 1074 1097 1125 1083 1077 1121 1098 1142 1081 1075 1115 1141 1171 1108 1067 1154 1108] MW1[1083 1131 1096 1077 1014 1094 1108 1075 1180 1074 1098 1104 1116 1085 1137 1113 1078 1121 1086 1084 1116 1138 1092 1140 1105 1095] MW2[1070 1113 1062 1155 1157 1110 1108 1050 1119 1146 1128 1098 1162 1125 1078 1059 1091 1098 1044 1101 1070 1089 1136 1082 1055 1101] MW3[1118 1123 1143 1083 1090 1073 1090 1126 1151 1119 1167 1160 1103 1098 1100 1072 1126 1084 1115 1169 1095 1089 1091 1179 1104 1144] TA[104] ECHU[0 0 0 0] ECMM[0] FM[1] CRC[0 0 0 0]\",\n" +
                                                                                        "      \"MM ID4\": \"Ver[9211809-b4b3d00] DNA[0137d742c801ee6a] Elapsed[65995] MW[16622047 16621485 16617830 16625680] LW[66487042] MH[2 2 0 1] HW[5] DH[3.064%] Temp[24] TMax[94] Fan[4800] FanR[57%] Vi[1197 1197 1201 1201] Vo[4172 4149 4161 4154] GHSmm[22251.40] WU[300714.85] Freq[835.76] PG[15] Led[0] MW0[1127 1182 1105 1093 1099 1019 1112 1109 1159 1078 1093 1059 1115 1077 1078 1143 1165 1133 1040 1085 1074 1139 1118 1019 1127 1153] MW1[1097 1041 1067 1066 1123 1096 1049 1093 1141 1101 1067 1039 1095 1083 1076 990 1147 1060 935 1167 1026 1164 1104 1164 1074 1116] MW2[1084 1050 1070 1150 1052 1097 1097 1112 1068 1181 1029 1134 1114 1064 1101 1079 1140 1135 1101 1135 1064 1059 1092 1132 1118 1062] MW3[1086 1120 1107 1089 965 1135 1024 1081 992 1051 1106 1143 1035 1124 1076 1134 1114 1077 1128 1050 1063 1073 1106 1133 1140 1070] TA[104] ECHU[0 0 0 0] ECMM[0] FM[1] CRC[1 0 0 0]\",\n" +
                                                                                        "      \"MM ID5\": \"Ver[9211809-b4b3d00] DNA[013f6a5fe66cbf41] Elapsed[65994] MW[16622702 16624226 16626239 16613994] LW[66487161] MH[1 2 2 3] HW[8] DH[3.472%] Temp[24] TMax[94] Fan[4320] FanR[49%] Vi[1198 1198 1214 1214] Vo[4151 4181 4211 4228] GHSmm[21936.40] WU[296736.27] Freq[823.93] PG[15] Led[0] MW0[1104 1051 1085 1079 1039 1035 1077 1090 1069 1112 1159 1045 1065 1068 1020 1085 1101 1061 1031 1069 991 1032 1070 1081 1021 1082] MW1[1071 997 1058 1061 1056 1039 1053 1023 1049 1047 1019 1122 1092 1099 1087 1030 1115 1096 1098 1077 1100 1056 1084 1150 1174 1102] MW2[1075 1108 1141 1149 1026 1075 1012 1050 1087 1043 1076 1089 1021 1070 1067 1060 1075 1088 1119 1136 1015 1062 1073 1116 1096 1080] MW3[1086 1086 1095 1093 1091 1120 1077 1139 1141 1088 1102 1094 1067 1050 1090 1127 1107 1040 1057 1084 1118 1088 1083 1094 1166 1054] TA[104] ECHU[0 0 0 0] ECMM[0] FM[1] CRC[3 0 0 0]\",\n" +
                                                                                        "      \"MM Count\": 5,\n" +
                                                                                        "      \"Smart Speed\": 1,\n" +
                                                                                        "      \"Connecter\": \"AUC\",\n" +
                                                                                        "      \"AUC VER\": \"AUC-20151208\",\n" +
                                                                                        "      \"AUC I2C Speed\": 400000,\n" +
                                                                                        "      \"AUC I2C XDelay\": 24000,\n" +
                                                                                        "      \"AUC Sensor\": 18225,\n" +
                                                                                        "      \"AUC Temperature\": 19.76,\n" +
                                                                                        "      \"Connection Overloaded\": false,\n" +
                                                                                        "      \"Voltage Level Offset\": 0,\n" +
                                                                                        "      \"Nonce Mask\": 24,\n" +
                                                                                        "      \"USB Pipe\": \"0\",\n" +
                                                                                        "      \"USB Delay\": \"r0 0.000000 w0 0.000000\",\n" +
                                                                                        "      \"USB tmo\": \"0 0\"\n" +
                                                                                        "    },\n" +
                                                                                        "    {\n" +
                                                                                        "      \"STATS\": 1,\n" +
                                                                                        "      \"ID\": \"AV91\",\n" +
                                                                                        "      \"Elapsed\": 66007,\n" +
                                                                                        "      \"Calls\": 0,\n" +
                                                                                        "      \"Wait\": 0,\n" +
                                                                                        "      \"Max\": 0,\n" +
                                                                                        "      \"Min\": 99999999,\n" +
                                                                                        "      \"MM ID1\": \"Ver[9211809-b4b3d00] DNA[0130fba8458e2cec] Elapsed[65995] MW[14818596 14817146 14823226 14824500] LW[59283468] MH[1 3 2 4] HW[10] DH[2.602%] Temp[21] TMax[95] Fan[5610] FanR[68%] Vi[1202 1202 1191 1191] Vo[4128 4146 4129 4138] GHSmm[22224.65] WU[268500.98] Freq[834.76] PG[15] Led[0] MW0[913 904 961 976 993 973 978 961 992 981 964 964 1045 883 859 975 976 879 1049 1015 972 986 973 869 977 995] MW1[1035 1047 981 988 944 984 1018 1004 968 1009 962 956 918 964 936 1000 996 957 1021 1041 1018 988 1017 1028 1011 1067] MW2[981 1000 1022 1003 941 959 985 942 1000 985 1010 1009 955 1001 951 936 995 977 985 1026 965 1058 985 893 974 975] MW3[906 942 971 950 999 942 933 1006 1002 937 989 969 970 962 952 974 952 936 935 1051 958 932 969 1016 961 957] TA[104] ECHU[0 0 0 0] ECMM[0] FM[1] CRC[0 0 0 0]\",\n" +
                                                                                        "      \"MM ID2\": \"Ver[9211809-b4b3d00] DNA[013153fdcd0b5fd8] Elapsed[65995] MW[14814770 14808166 14820017 14823430] LW[59266383] MH[3 3 1 1] HW[8] DH[3.185%] Temp[24] TMax[95] Fan[5190] FanR[62%] Vi[1193 1193 1191 1191] Vo[4131 4101 4130 4087] GHSmm[21688.60] WU[260666.17] Freq[814.63] PG[15] Led[0] MW0[948 984 959 972 951 957 967 953 940 973 996 995 917 956 1047 999 975 953 954 1040 927 976 961 964 981 936] MW1[962 956 946 933 949 948 952 932 934 870 875 864 954 933 942 965 983 936 896 959 906 946 940 948 921 953] MW2[947 1001 958 934 952 973 987 914 987 961 993 980 989 978 953 967 975 907 943 942 984 946 993 949 926 923] MW3[928 963 938 880 973 962 903 880 967 969 911 999 894 933 943 914 918 916 895 864 874 957 953 882 954 877] TA[104] ECHU[0 0 0 0] ECMM[0] FM[1] CRC[2 0 0 0]\",\n" +
                                                                                        "      \"MM ID3\": \"Ver[9211809-b4b3d00] DNA[01353126ffee988b] Elapsed[65994] MW[14823142 14814697 14809399 14811476] LW[59258714] MH[3 3 2 2] HW[10] DH[3.726%] Temp[24] TMax[95] Fan[5250] FanR[63%] Vi[1198 1198 1200 1200] Vo[4147 4166 4135 4143] GHSmm[21555.15] WU[257552.79] Freq[809.61] PG[15] Led[0] MW0[930 938 940 847 955 860 914 908 945 975 881 918 969 885 862 946 923 926 946 917 898 899 981 926 895 976] MW1[957 970 996 971 903 892 955 892 948 873 871 927 994 949 873 887 961 719 908 881 996 970 963 892 967 985] MW2[929 923 875 941 967 903 978 991 967 918 975 1000 963 948 951 882 866 946 993 964 904 945 963 891 913 925] MW3[926 959 987 1018 971 884 938 995 994 924 927 976 905 938 925 944 929 965 941 946 963 992 991 955 950 992] TA[104] ECHU[0 0 0 0] ECMM[0] FM[1] CRC[0 0 0 0]\",\n" +
                                                                                        "      \"MM ID4\": \"Ver[9211809-b4b3d00] DNA[013cbe94d0402c2b] Elapsed[65994] MW[14818222 14819082 14819856 14823425] LW[59280585] MH[0 0 2 1] HW[3] DH[3.395%] Temp[25] TMax[95] Fan[4980] FanR[67%] Vi[1201 1201 1205 1205] Vo[4157 4166 4147 4183] GHSmm[22060.55] WU[264570.56] Freq[828.60] PG[15] Led[0] MW0[1024 986 1010 957 973 1023 938 924 1005 1022 933 943 946 959 968 971 926 902 939 1075 966 989 932 1063 1009 1010] MW1[1004 959 1008 966 979 935 931 942 949 954 950 943 951 937 919 914 903 963 976 956 969 1010 921 940 986 969] MW2[945 915 991 933 962 1005 945 964 1002 926 959 950 878 922 912 926 946 909 938 962 928 920 957 926 934 925] MW3[980 997 1001 946 1004 974 978 1053 977 908 944 992 960 1000 916 988 956 987 990 941 929 934 901 1015 990 999] TA[104] ECHU[0 0 0 0] ECMM[0] FM[1] CRC[0 0 0 0]\",\n" +
                                                                                        "      \"MM ID5\": \"Ver[9211809-b4b3d00] DNA[013e7fdfd11528f6] Elapsed[65994] MW[14821407 14809798 14811137 14822400] LW[59264742] MH[1 1 0 2] HW[4] DH[5.436%] Temp[22] TMax[95] Fan[5220] FanR[62%] Vi[1200 1200 1213 1213] Vo[4133 4136 4156 4152] GHSmm[21783.95] WU[255465.21] Freq[818.21] PG[15] Led[0] MW0[803 929 651 948 939 869 953 894 911 928 896 938 928 834 948 816 882 911 904 810 537 925 904 881 8 925] MW1[946 922 987 884 917 963 975 943 952 1015 921 1001 875 897 943 987 958 939 970 988 874 952 909 939 932 943] MW2[1007 986 1002 977 983 1037 951 1000 910 925 950 1014 990 1012 944 861 968 955 913 939 955 945 982 1001 945 964] MW3[1003 886 995 1009 973 904 1025 998 934 949 960 1033 956 949 972 918 932 959 951 958 957 911 927 1010 1007 930] TA[104] ECHU[0 0 0 0] ECMM[0] FM[1] CRC[3 0 0 0]\",\n" +
                                                                                        "      \"MM Count\": 5,\n" +
                                                                                        "      \"Smart Speed\": 1,\n" +
                                                                                        "      \"Connecter\": \"AUC\",\n" +
                                                                                        "      \"AUC VER\": \"AUC-20151208\",\n" +
                                                                                        "      \"AUC I2C Speed\": 400000,\n" +
                                                                                        "      \"AUC I2C XDelay\": 24000,\n" +
                                                                                        "      \"AUC Sensor\": 18161,\n" +
                                                                                        "      \"AUC Temperature\": 19.96,\n" +
                                                                                        "      \"Connection Overloaded\": false,\n" +
                                                                                        "      \"Voltage Level Offset\": 0,\n" +
                                                                                        "      \"Nonce Mask\": 24,\n" +
                                                                                        "      \"USB Pipe\": \"0\",\n" +
                                                                                        "      \"USB Delay\": \"r0 0.000000 w0 0.000000\",\n" +
                                                                                        "      \"USB tmo\": \"0 0\"\n" +
                                                                                        "    },\n" +
                                                                                        "    {\n" +
                                                                                        "      \"STATS\": 2,\n" +
                                                                                        "      \"ID\": \"AV92\",\n" +
                                                                                        "      \"Elapsed\": 66007,\n" +
                                                                                        "      \"Calls\": 0,\n" +
                                                                                        "      \"Wait\": 0,\n" +
                                                                                        "      \"Max\": 0,\n" +
                                                                                        "      \"Min\": 99999999,\n" +
                                                                                        "      \"MM ID1\": \"Ver[9211809-b4b3d00] DNA[013607fcf2c12b9c] Elapsed[65996] MW[2091919 2089709 2094579 2097411] LW[8373618] MH[0 0 0 0] HW[0] DH[3.871%] Temp[25] TMax[95] Fan[4740] FanR[62%] Vi[1207 1207 1209 1209] Vo[4153 4161 4156 4174] GHSmm[22223.00] WU[37411.17] Freq[834.70] PG[15] Led[0] MW0[124 146 138 148 141 157 140 170 130 138 103 140 148 135 125 157 133 118 128 143 149 123 137 155 143 125] MW1[135 128 132 138 146 166 160 142 155 135 157 133 153 131 128 137 144 140 135 125 155 138 122 120 146 153] MW2[141 147 137 92 140 149 122 21 118 127 127 151 152 131 149 142 134 133 104 136 145 130 128 114 138 132] MW3[130 163 123 127 123 139 133 141 110 131 123 124 162 143 130 143 147 154 146 131 156 144 119 139 122 146] TA[104] ECHU[0 0 0 0] ECMM[0] FM[1] CRC[0 0 0 0]\",\n" +
                                                                                        "      \"MM ID2\": \"Ver[9211809-b4b3d00] DNA[01361dad4573b165] Elapsed[65996] MW[2094865 2086606 2088359 2091932] LW[8361762] MH[1 0 1 0] HW[2] DH[2.605%] Temp[23] TMax[95] Fan[5880] FanR[71%] Vi[1205 1205 1200 1200] Vo[4165 4172 4128 4153] GHSmm[22193.65] WU[37898.36] Freq[833.60] PG[15] Led[0] MW0[119 125 138 140 144 143 141 130 138 143 112 126 146 132 140 128 142 141 140 147 140 137 138 147 114 141] MW1[142 158 124 141 141 143 124 124 127 130 142 134 132 121 139 136 133 135 139 130 138 150 127 142 153 137] MW2[141 138 172 137 124 113 133 150 123 132 149 153 144 140 131 151 148 129 136 141 142 133 145 161 145 133] MW3[131 143 139 133 142 117 127 144 129 144 151 136 147 164 133 132 143 130 143 154 119 154 111 160 134 142] TA[104] ECHU[0 0 0 0] ECMM[2] FM[1] CRC[0 0 0 0]\",\n" +
                                                                                        "      \"MM ID3\": \"Ver[9211809-b4b3d00] DNA[01399daf84698759] Elapsed[65995] MW[2095224 2087233 2094231 2091734] LW[8368422] MH[0 0 0 0] HW[0] DH[4.176%] Temp[24] TMax[95] Fan[5190] FanR[72%] Vi[1197 1197 1200 1200] Vo[4162 4155 4149 4147] GHSmm[21801.90] WU[36750.00] Freq[818.88] PG[15] Led[0] MW0[122 127 114 119 144 125 146 127 125 133 133 131 154 116 141 131 134 119 126 140 136 128 127 135 130 136] MW1[122 137 143 134 141 145 117 120 142 127 155 124 132 133 123 129 142 142 131 139 133 136 140 143 137 127] MW2[138 135 135 116 147 123 146 155 149 147 130 137 146 114 142 152 126 123 144 116 121 139 148 133 129 142] MW3[108 145 132 131 141 132 128 153 114 142 104 142 130 148 120 149 121 135 163 141 134 121 139 115 136 136] TA[104] ECHU[0 0 0 0] ECMM[0] FM[1] CRC[17 0 0 0]\",\n" +
                                                                                        "      \"MM ID4\": \"Ver[9211809-b4b3d00] DNA[013a9e6935923537] Elapsed[65995] MW[2095601 2091262 2090793 2092694] LW[8370350] MH[0 0 1 0] HW[1] DH[2.335%] Temp[22] TMax[95] Fan[6450] FanR[78%] Vi[1197 1197 1196 1196] Vo[4134 4130 4177 4154] GHSmm[22242.25] WU[38166.15] Freq[835.42] PG[15] Led[0] MW0[141 149 153 137 145 145 132 142 144 134 131 134 141 146 136 135 129 126 133 163 138 141 140 148 137 133] MW1[146 143 135 116 150 131 141 143 131 149 114 129 144 124 147 136 120 135 137 142 137 118 152 163 115 154] MW2[132 141 138 139 132 158 146 152 146 142 131 144 149 148 145 146 147 163 137 128 141 122 132 135 129 136] MW3[134 128 164 142 143 128 134 127 152 155 145 131 130 123 137 133 158 119 139 148 156 129 123 96 153 151] TA[104] ECHU[0 0 0 0] ECMM[0] FM[1] CRC[0 0 0 0]\",\n" +
                                                                                        "      \"MM ID5\": \"Ver[9211809-b4b3d00] DNA[013b25a19b69b193] Elapsed[65995] MW[2092424 2089240 2091549 2090442] LW[8363655] MH[0 2 0 0] HW[2] DH[3.406%] Temp[25] TMax[95] Fan[4860] FanR[64%] Vi[1192 1192 1202 1202] Vo[4124 4121 4138 4156] GHSmm[22108.80] WU[37465.04] Freq[830.41] PG[15] Led[0] MW0[147 123 124 156 145 134 145 136 150 129 109 145 147 128 143 126 114 144 133 132 130 140 130 133 137 152] MW1[148 140 130 143 140 121 133 141 135 138 157 162 116 146 148 98 129 119 135 150 140 141 120 149 150 141] MW2[136 129 172 127 134 132 122 151 131 123 135 143 136 134 149 132 142 132 114 116 151 152 146 109 122 129] MW3[135 137 146 138 151 156 129 132 139 145 135 156 138 127 149 145 144 118 125 116 141 128 140 126 126 133] TA[104] ECHU[0 0 0 0] ECMM[0] FM[1] CRC[1 0 0 0]\",\n" +
                                                                                        "      \"MM Count\": 5,\n" +
                                                                                        "      \"Smart Speed\": 1,\n" +
                                                                                        "      \"Connecter\": \"AUC\",\n" +
                                                                                        "      \"AUC VER\": \"AUC-20151208\",\n" +
                                                                                        "      \"AUC I2C Speed\": 400000,\n" +
                                                                                        "      \"AUC I2C XDelay\": 24000,\n" +
                                                                                        "      \"AUC Sensor\": 17580,\n" +
                                                                                        "      \"AUC Temperature\": 21.71,\n" +
                                                                                        "      \"Connection Overloaded\": false,\n" +
                                                                                        "      \"Voltage Level Offset\": 0,\n" +
                                                                                        "      \"Nonce Mask\": 24,\n" +
                                                                                        "      \"USB Pipe\": \"0\",\n" +
                                                                                        "      \"USB Delay\": \"r0 0.000000 w0 0.000000\",\n" +
                                                                                        "      \"USB tmo\": \"0 0\"\n" +
                                                                                        "    },\n" +
                                                                                        "    {\n" +
                                                                                        "      \"STATS\": 3,\n" +
                                                                                        "      \"ID\": \"AV93\",\n" +
                                                                                        "      \"Elapsed\": 66007,\n" +
                                                                                        "      \"Calls\": 0,\n" +
                                                                                        "      \"Wait\": 0,\n" +
                                                                                        "      \"Max\": 0,\n" +
                                                                                        "      \"Min\": 99999999,\n" +
                                                                                        "      \"MM ID1\": \"Ver[9211809-b4b3d00] DNA[01322652e94a1584] Elapsed[66011] MW[16616014 16602659 16600615 16597949] LW[66417237] MH[2 0 1 0] HW[3] DH[3.429%] Temp[22] TMax[95] Fan[4740] FanR[56%] Vi[1193 1193 1194 1194] Vo[4144 4151 4140 4184] GHSmm[22103.20] WU[295799.45] Freq[830.20] PG[15] Led[0] MW0[1071 1060 1054 1074 1074 1053 1098 1088 1096 1101 1118 1093 1067 1094 1167 1143 1001 1075 1075 1063 1106 1109 1110 1021 1102 1079] MW1[1059 1083 1089 1046 1053 1054 1059 1138 1082 1084 1111 1136 1017 1026 976 1117 1096 1045 1117 1088 1015 1148 1076 1148 1104 1059] MW2[1096 1024 1028 1081 1043 1056 1083 1087 1104 1084 1060 1009 1084 1052 1123 1079 1049 1057 1044 1040 1026 1099 1066 1030 1018 985] MW3[1098 1075 1111 1085 1033 1091 1076 1110 1029 1041 1087 1040 1112 1059 1097 1010 1096 1177 1069 1034 1034 1106 1102 1079 1084 1134] TA[104] ECHU[0 0 0 0] ECMM[0] FM[1] CRC[5 0 0 0]\",\n" +
                                                                                        "      \"MM ID2\": \"Ver[9211809-b4b3d00] DNA[013359de23c468e1] Elapsed[66010] MW[16587789 16572020 16592113 16589081] LW[66341003] MH[2 2 0 0] HW[4] DH[13.476%] Temp[22] TMax[94] Fan[4830] FanR[63%] Vi[1204 1204 1207 1207] Vo[4136 4179 4179 4159] GHSmm[21830.55] WU[263663.61] Freq[819.96] PG[15] Led[0] MW0[1093 1090 325 673 956 975 1081 1130 701 1045 1125 1093 1063 1097 1090 1047 1124 1018 1078 331 1018 1093 902 1118 979 1105] MW1[1094 1088 1146 1158 1123 1053 979 1140 1036 1099 1164 1100 1128 1031 1050 1056 1065 1085 1060 1028 1097 1117 1059 1057 1107 1108] MW2[1092 1109 958 9 1001 1068 6 1078 984 1065 1073 1029 1034 1110 1056 1128 1110 1096 1089 12 1060 994 12 1058 24 1057] MW3[1104 1064 1091 901 1075 1114 1083 1042 215 1022 1061 1083 1023 994 1065 1067 1066 1108 0 1005 0 1088 985 388 1028 1087] TA[104] ECHU[0 0 0 0] ECMM[0] FM[1] CRC[0 0 0 0]\",\n" +
                                                                                        "      \"MM ID3\": \"Ver[9211809-b4b3d00] DNA[0133faba158b411d] Elapsed[66010] MW[16612322 16613780 16591538 16596773] LW[66414413] MH[0 0 0 2] HW[2] DH[4.267%] Temp[22] TMax[94] Fan[5160] FanR[62%] Vi[1198 1198 1190 1190] Vo[4161 4164 4123 4147] GHSmm[21676.95] WU[290357.78] Freq[814.19] PG[15] Led[0] MW0[1086 1107 1074 1101 1133 1111 1055 1109 1112 1109 1095 1096 1100 1109 1109 1137 1105 1099 1141 1057 1051 1215 1049 1106 1110 1084] MW1[1105 1063 1053 1080 1098 1110 1049 1094 1124 1099 1073 1154 1092 1107 1062 1090 1138 1150 1132 1107 1054 1095 1069 1093 1111 1057] MW2[1019 1045 1007 1088 1067 1094 1060 1012 1099 1038 1087 1032 1067 986 1020 986 421 642 742 1018 916 1010 836 1000 894 918] MW3[1017 1024 1058 1050 1039 1099 1067 1084 1070 1052 1048 1109 1067 1049 1004 1040 1032 1125 1054 1088 1077 1077 1049 1012 1049 1073] TA[104] ECHU[0 0 0 0] ECMM[0] FM[1] CRC[0 0 0 0]\",\n" +
                                                                                        "      \"MM ID4\": \"Ver[9211809-b4b3d00] DNA[013cbd2d80d76ce3] Elapsed[66009] MW[16604374 16610990 16610153 16597460] LW[66422977] MH[2 3 2 4] HW[11] DH[2.438%] Temp[25] TMax[94] Fan[4980] FanR[59%] Vi[1192 1192 1201 1201] Vo[4131 4101 4172 4180] GHSmm[21963.45] WU[300033.33] Freq[824.95] PG[15] Led[0] MW0[1137 1176 1141 1104 1137 1087 1156 1101 1028 1105 1122 1137 1127 1105 1048 1078 1049 1160 1102 1125 1096 1115 1034 1129 1128 1119] MW1[1077 1114 1067 1122 1167 1033 1107 1104 1055 1141 1111 1129 1100 1074 1059 1027 1064 1039 1126 1022 1119 1050 1054 1107 1067 1075] MW2[1090 1065 1118 1010 1153 1076 1136 1053 1052 1065 1129 1080 1087 1123 1091 1073 1042 1100 1076 1015 1098 1066 1079 1110 1083 1082] MW3[1046 1058 1055 1153 1078 1110 1077 1072 1063 1079 1064 1060 1087 1085 1150 1102 1062 1016 1014 1065 1149 1066 1128 1089 1073 1184] TA[104] ECHU[0 0 0 0] ECMM[2] FM[1] CRC[0 0 0 0]\",\n" +
                                                                                        "      \"MM ID5\": \"Ver[9211809-b4b3d00] DNA[013f4a5e6e5a9907] Elapsed[66009] MW[16602954 16607045 16616508 16604313] LW[66430820] MH[1 4 0 1] HW[6] DH[3.095%] Temp[22] TMax[94] Fan[5130] FanR[61%] Vi[1214 1214 1208 1208] Vo[4159 4136 4112 4140] GHSmm[22103.75] WU[297346.89] Freq[830.22] PG[15] Led[0] MW0[1136 1051 1125 1047 1051 1118 1118 1163 1109 1136 1054 988 1095 1036 1086 1022 1083 1096 1027 1125 1131 1119 1097 1135 1033 1139] MW1[1107 1130 1088 1047 1116 1085 1064 1052 1045 1059 1049 1061 1143 1152 1036 1058 1077 1074 1077 1104 1053 1137 1039 1047 1064 1152] MW2[1066 1127 1049 1099 1062 1090 1068 1116 1088 1124 1095 1093 1075 1082 994 1084 1106 1018 1140 1081 1070 1053 1068 1059 1141 1075] MW3[1022 1038 1076 989 1087 1105 1043 1063 1146 1071 1062 1139 1076 1063 1048 1125 1033 1047 1062 1085 1073 1044 1049 1130 1074 1067] TA[104] ECHU[0 0 0 0] ECMM[2] FM[1] CRC[2 0 0 0]\",\n" +
                                                                                        "      \"MM Count\": 5,\n" +
                                                                                        "      \"Smart Speed\": 1,\n" +
                                                                                        "      \"Connecter\": \"AUC\",\n" +
                                                                                        "      \"AUC VER\": \"AUC-20151208\",\n" +
                                                                                        "      \"AUC I2C Speed\": 400000,\n" +
                                                                                        "      \"AUC I2C XDelay\": 24000,\n" +
                                                                                        "      \"AUC Sensor\": 18741,\n" +
                                                                                        "      \"AUC Temperature\": 18.22,\n" +
                                                                                        "      \"Connection Overloaded\": false,\n" +
                                                                                        "      \"Voltage Level Offset\": 0,\n" +
                                                                                        "      \"Nonce Mask\": 24,\n" +
                                                                                        "      \"USB Pipe\": \"0\",\n" +
                                                                                        "      \"USB Delay\": \"r0 0.000000 w0 0.000000\",\n" +
                                                                                        "      \"USB tmo\": \"0 0\"\n" +
                                                                                        "    },\n" +
                                                                                        "    {\n" +
                                                                                        "      \"STATS\": 4,\n" +
                                                                                        "      \"ID\": \"AV94\",\n" +
                                                                                        "      \"Elapsed\": 66007,\n" +
                                                                                        "      \"Calls\": 0,\n" +
                                                                                        "      \"Wait\": 0,\n" +
                                                                                        "      \"Max\": 0,\n" +
                                                                                        "      \"Min\": 99999999,\n" +
                                                                                        "      \"MM ID1\": \"Ver[9211809-b4b3d00] DNA[013607fcf2c12b9c] Elapsed[57612] MW[1407923 1410604 1408356 1406687] LW[5633570] MH[0 0 0 0] HW[0] DH[3.602%] Temp[24] TMax[96] Fan[4650] FanR[62%] Vi[1205 1205 1208 1208] Vo[4151 4158 4155 4170] GHSmm[22226.15] WU[29018.99] Freq[834.82] PG[15] Led[0] MW0[106 82 108 95 95 87 85 95 106 90 110 105 78 93 102 79 99 84 78 91 113 92 85 92 99 84] MW1[94 109 98 88 87 106 93 92 88 97 92 100 84 81 82 96 100 103 91 75 98 92 103 114 100 78] MW2[100 91 87 45 95 104 96 23 88 117 88 101 89 80 69 84 98 95 96 77 73 96 98 88 106 76] MW3[98 108 80 91 92 117 103 98 90 82 95 76 96 103 93 83 90 109 99 87 105 86 98 99 82 78] TA[104] ECHU[0 0 0 0] ECMM[0] FM[1] CRC[0 0 0 0]\",\n" +
                                                                                        "      \"MM ID2\": \"Ver[9211809-b4b3d00] DNA[01361dad4573b165] Elapsed[57612] MW[1408331 1407884 1409501 1409572] LW[5635288] MH[0 1 0 0] HW[1] DH[2.605%] Temp[23] TMax[95] Fan[5880] FanR[71%] Vi[1204 1204 1199 1199] Vo[4162 4172 4125 4149] GHSmm[22185.05] WU[29516.40] Freq[833.27] PG[15] Led[0] MW0[89 100 103 91 100 94 90 91 107 92 102 98 105 79 95 80 100 98 99 101 89 99 95 73 83 80] MW1[107 92 87 105 80 98 81 75 73 97 93 91 91 94 88 98 98 88 108 92 108 76 93 104 98 86] MW2[94 100 106 99 95 92 90 88 83 82 88 84 102 100 87 99 104 114 101 76 98 102 92 94 96 82] MW3[100 98 88 102 93 100 82 112 92 93 98 76 92 95 106 79 107 85 102 100 88 99 87 90 94 96] TA[104] ECHU[0 0 0 0] ECMM[2] FM[1] CRC[0 0 0 0]\",\n" +
                                                                                        "      \"MM ID3\": \"Ver[9211809-b4b3d00] DNA[01399daf84698759] Elapsed[57611] MW[1408167 1408901 1406765 1406752] LW[5630585] MH[1 0 1 1] HW[3] DH[4.097%] Temp[23] TMax[95] Fan[5190] FanR[73%] Vi[1196 1196 1199 1199] Vo[4159 4153 4146 4148] GHSmm[21797.60] WU[28076.56] Freq[818.72] PG[15] Led[0] MW0[109 81 114 89 81 88 91 103 92 103 89 97 98 95 92 89 74 86 93 79 79 92 82 77 84 89] MW1[88 87 91 84 94 107 91 86 96 79 97 90 85 69 89 90 88 84 97 107 71 103 89 86 93 87] MW2[80 88 99 101 94 84 104 102 101 80 82 93 102 73 80 101 93 103 98 85 87 86 88 87 80 86] MW3[77 87 80 91 86 97 85 83 88 81 97 89 80 83 91 88 84 74 80 75 89 96 100 97 79 73] TA[104] ECHU[0 0 0 0] ECMM[0] FM[1] CRC[0 0 0 1]\",\n" +
                                                                                        "      \"MM ID4\": \"Ver[9211809-b4b3d00] DNA[013a9e6935923537] Elapsed[57611] MW[1407755 1412569 1410056 1403520] LW[5633900] MH[1 0 0 0] HW[1] DH[2.330%] Temp[22] TMax[95] Fan[6480] FanR[78%] Vi[1195 1195 1196 1196] Vo[4129 4128 4174 4149] GHSmm[22238.85] WU[29465.29] Freq[835.29] PG[15] Led[0] MW0[80 86 89 98 113 88 85 94 98 102 77 88 92 105 99 94 86 91 90 88 82 88 100 81 82 114] MW1[86 99 118 80 107 86 99 102 107 95 85 96 91 93 90 98 89 91 92 89 101 86 90 91 91 104] MW2[90 89 91 92 95 89 103 103 93 103 86 82 109 77 89 104 95 106 81 102 90 88 102 108 100 93] MW3[76 99 98 97 86 102 75 107 95 85 98 87 93 104 88 93 81 98 91 90 110 96 86 87 83 108] TA[104] ECHU[0 0 0 0] ECMM[0] FM[1] CRC[0 0 0 0]\",\n" +
                                                                                        "      \"MM ID5\": \"Ver[9211809-b4b3d00] DNA[013b25a19b69b193] Elapsed[57610] MW[1408594 1408343 1408605 1410849] LW[5636391] MH[0 0 0 0] HW[0] DH[3.433%] Temp[24] TMax[95] Fan[4830] FanR[64%] Vi[1191 1191 1201 1201] Vo[4120 4119 4136 4153] GHSmm[22110.85] WU[28407.42] Freq[830.49] PG[15] Led[0] MW0[109 107 80 96 94 86 98 87 80 92 89 94 82 85 93 90 93 79 75 81 92 103 85 84 88 91] MW1[97 82 106 87 95 89 100 99 78 80 98 98 101 79 101 95 78 79 100 82 103 88 82 85 98 92] MW2[83 109 104 82 114 94 88 91 91 84 83 83 87 87 92 91 86 85 87 81 79 87 79 78 95 98] MW3[88 79 85 83 85 97 79 92 69 83 80 100 89 109 106 93 104 104 83 111 85 110 79 83 91 80] TA[104] ECHU[0 0 0 0] ECMM[0] FM[1] CRC[0 0 0 0]\",\n" +
                                                                                        "      \"MM Count\": 5,\n" +
                                                                                        "      \"Smart Speed\": 1,\n" +
                                                                                        "      \"Connecter\": \"AUC\",\n" +
                                                                                        "      \"AUC VER\": \"AUC-20151208\",\n" +
                                                                                        "      \"AUC I2C Speed\": 400000,\n" +
                                                                                        "      \"AUC I2C XDelay\": 24000,\n" +
                                                                                        "      \"AUC Sensor\": 17677,\n" +
                                                                                        "      \"AUC Temperature\": 21.41,\n" +
                                                                                        "      \"Connection Overloaded\": false,\n" +
                                                                                        "      \"Voltage Level Offset\": 0,\n" +
                                                                                        "      \"Nonce Mask\": 24,\n" +
                                                                                        "      \"USB Pipe\": \"0\",\n" +
                                                                                        "      \"USB Delay\": \"r0 0.000000 w0 0.000000\",\n" +
                                                                                        "      \"USB tmo\": \"0 0\"\n" +
                                                                                        "    },\n" +
                                                                                        "    {\n" +
                                                                                        "      \"STATS\": 5,\n" +
                                                                                        "      \"ID\": \"AV95\",\n" +
                                                                                        "      \"Elapsed\": 66007,\n" +
                                                                                        "      \"Calls\": 0,\n" +
                                                                                        "      \"Wait\": 0,\n" +
                                                                                        "      \"Max\": 0,\n" +
                                                                                        "      \"Min\": 99999999,\n" +
                                                                                        "      \"MM ID1\": \"Ver[9211809-b4b3d00] DNA[013607fcf2c12b9c] Elapsed[51984] MW[10318364 10311895 10302540 10316105] LW[41248904] MH[1 1 0 0] HW[2] DH[3.813%] Temp[24] TMax[95] Fan[4680] FanR[62%] Vi[1206 1206 1209 1209] Vo[4153 4161 4155 4172] GHSmm[22219.75] WU[234091.51] Freq[834.58] PG[15] Led[0] MW0[664 681 686 709 597 680 685 689 734 672 727 673 646 655 661 682 705 607 637 733 705 742 697 678 689 605] MW1[646 671 661 649 702 643 697 685 691 673 648 699 648 707 683 722 698 695 718 666 658 639 678 674 685 672] MW2[730 665 663 403 655 658 667 112 602 593 645 705 650 658 673 707 696 670 609 666 674 710 651 665 734 665] MW3[659 732 688 719 675 704 684 673 635 709 672 682 694 705 724 648 679 679 715 701 661 708 696 663 740 654] TA[104] ECHU[0 0 0 0] ECMM[2] FM[1] CRC[1 0 0 0]\",\n" +
                                                                                        "      \"MM ID2\": \"Ver[9211809-b4b3d00] DNA[01361dad4573b165] Elapsed[51983] MW[10307286 10314143 10319124 10316680] LW[41257233] MH[3 2 0 0] HW[5] DH[2.610%] Temp[23] TMax[95] Fan[5850] FanR[71%] Vi[1205 1205 1199 1199] Vo[4163 4172 4127 4153] GHSmm[22193.95] WU[238535.22] Freq[833.61] PG[15] Led[0] MW0[681 699 720 658 758 692 720 660 667 652 702 687 676 668 649 681 668 702 662 699 687 748 707 735 659 686] MW1[727 738 704 656 669 704 656 713 671 655 739 691 648 642 666 676 707 694 694 680 716 675 678 728 715 703] MW2[625 757 694 651 688 668 673 715 660 684 675 690 716 686 686 692 665 663 694 676 642 641 670 677 668 617] MW3[724 631 652 658 731 665 670 675 725 631 674 675 678 680 693 677 664 675 704 641 664 674 655 694 671 672] TA[104] ECHU[0 0 0 0] ECMM[2] FM[1] CRC[0 0 0 0]\",\n" +
                                                                                        "      \"MM ID3\": \"Ver[9211809-b4b3d00] DNA[01399daf84698759] Elapsed[51983] MW[10313237 10309027 10308542 10310547] LW[41241353] MH[0 1 1 0] HW[2] DH[4.124%] Temp[24] TMax[95] Fan[5310] FanR[75%] Vi[1197 1197 1200 1200] Vo[4161 4156 4149 4149] GHSmm[21798.75] WU[229065.40] Freq[818.76] PG[15] Led[0] MW0[679 648 659 600 668 637 690 670 621 631 653 611 632 681 640 640 632 660 658 656 659 647 635 684 604 635] MW1[671 648 641 692 660 722 704 681 670 687 676 708 674 630 666 683 681 689 642 674 720 688 699 637 656 631] MW2[678 658 676 658 728 578 663 661 700 676 630 608 622 643 611 659 697 665 643 634 667 603 662 641 621 665] MW3[573 645 662 678 684 624 654 633 660 656 598 737 616 644 643 635 664 642 662 656 652 606 678 634 684 648] TA[104] ECHU[0 0 0 0] ECMM[0] FM[1] CRC[31 5 13 6]\",\n" +
                                                                                        "      \"MM ID4\": \"Ver[9211809-b4b3d00] DNA[013a9e6935923537] Elapsed[51982] MW[10310256 10317271 10317777 10314893] LW[41260197] MH[0 1 2 0] HW[3] DH[2.336%] Temp[23] TMax[95] Fan[6570] FanR[79%] Vi[1196 1196 1197 1197] Vo[4133 4131 4176 4153] GHSmm[22235.15] WU[238243.36] Freq[835.15] PG[15] Led[0] MW0[679 676 665 648 635 699 670 671 644 685 726 696 693 686 669 671 761 644 639 703 597 674 661 687 680 686] MW1[732 683 693 687 684 717 669 622 635 637 686 694 672 698 699 693 698 679 730 670 666 648 661 697 663 654] MW2[639 669 671 719 722 702 662 694 660 683 673 668 672 642 640 721 708 712 618 695 679 755 686 715 700 684] MW3[662 673 708 662 664 684 685 694 682 698 696 703 683 716 712 707 639 683 683 679 690 687 699 664 745 707] TA[104] ECHU[0 0 0 0] ECMM[0] FM[1] CRC[1 0 0 0]\",\n" +
                                                                                        "      \"MM ID5\": \"Ver[9211809-b4b3d00] DNA[013b25a19b69b193] Elapsed[51982] MW[10319549 10309915 10313797 10316255] LW[41259516] MH[1 3 0 0] HW[4] DH[3.443%] Temp[24] TMax[95] Fan[4920] FanR[65%] Vi[1192 1192 1202 1202] Vo[4123 4121 4137 4154] GHSmm[22100.05] WU[234656.77] Freq[830.08] PG[15] Led[0] MW0[621 631 716 649 681 698 728 669 647 608 637 689 652 663 737 677 650 700 723 690 647 710 725 648 676 695] MW1[706 644 666 711 644 723 689 676 672 630 689 637 641 706 658 667 717 701 698 706 675 610 739 653 657 676] MW2[715 699 663 626 650 676 669 716 670 629 644 687 641 585 600 671 652 601 715 677 719 659 618 695 635 622] MW3[643 714 661 661 666 710 659 697 632 708 711 699 727 664 662 689 660 702 579 656 628 713 658 659 667 721] TA[104] ECHU[0 0 0 0] ECMM[0] FM[1] CRC[1 0 0 0]\",\n" +
                                                                                        "      \"MM Count\": 5,\n" +
                                                                                        "      \"Smart Speed\": 1,\n" +
                                                                                        "      \"Connecter\": \"AUC\",\n" +
                                                                                        "      \"AUC VER\": \"AUC-20151208\",\n" +
                                                                                        "      \"AUC I2C Speed\": 400000,\n" +
                                                                                        "      \"AUC I2C XDelay\": 24000,\n" +
                                                                                        "      \"AUC Sensor\": 17774,\n" +
                                                                                        "      \"AUC Temperature\": 21.12,\n" +
                                                                                        "      \"Connection Overloaded\": false,\n" +
                                                                                        "      \"Voltage Level Offset\": 0,\n" +
                                                                                        "      \"Nonce Mask\": 24,\n" +
                                                                                        "      \"USB Pipe\": \"0\",\n" +
                                                                                        "      \"USB Delay\": \"r0 0.000000 w0 0.000000\",\n" +
                                                                                        "      \"USB tmo\": \"0 0\"\n" +
                                                                                        "    },\n" +
                                                                                        "    {\n" +
                                                                                        "      \"STATS\": 6,\n" +
                                                                                        "      \"ID\": \"AV96\",\n" +
                                                                                        "      \"Elapsed\": 66007,\n" +
                                                                                        "      \"Calls\": 0,\n" +
                                                                                        "      \"Wait\": 0,\n" +
                                                                                        "      \"Max\": 0,\n" +
                                                                                        "      \"Min\": 99999999,\n" +
                                                                                        "      \"MM ID1\": \"Ver[9211809-b4b3d00] DNA[013607fcf2c12b9c] Elapsed[11029] MW[229217 229619 228293 228682] LW[915811] MH[0 0 0 0] HW[0] DH[3.880%] Temp[24] TMax[95] Fan[4770] FanR[62%] Vi[1207 1207 1209 1209] Vo[4153 4162 4155 4173] GHSmm[22220.60] WU[24262.40] Freq[834.61] PG[15] Led[0] MW0[12 13 12 20 9 6 16 16 18 11 17 18 7 16 14 19 17 10 16 11 13 21 18 12 14 13] MW1[12 15 15 16 15 16 16 16 14 20 13 17 12 18 12 17 17 8 8 16 11 10 16 17 15 14] MW2[10 11 17 3 16 19 14 1 14 18 11 19 8 13 24 17 15 14 18 22 21 20 16 13 10 12] MW3[18 12 16 18 12 15 15 18 11 19 21 14 13 22 12 15 9 18 13 23 11 13 20 27 15 11] TA[104] ECHU[0 0 0 0] ECMM[2] FM[1] CRC[0 0 0 0]\",\n" +
                                                                                        "      \"MM ID2\": \"Ver[9211809-b4b3d00] DNA[01361dad4573b165] Elapsed[11028] MW[228929 229470 228121 228541] LW[915061] MH[0 0 0 0] HW[0] DH[2.597%] Temp[24] TMax[95] Fan[5700] FanR[69%] Vi[1206 1206 1200 1200] Vo[4165 4172 4129 4153] GHSmm[22226.65] WU[24643.42] Freq[834.84] PG[15] Led[0] MW0[12 14 13 15 14 13 21 14 14 21 17 13 15 10 16 16 18 11 15 23 10 19 22 26 13 15] MW1[11 15 14 13 16 18 18 12 15 13 17 9 20 12 11 14 10 19 13 17 19 12 20 11 20 13] MW2[16 14 14 18 13 13 14 13 17 21 15 15 9 15 9 20 14 13 14 15 15 9 12 18 14 19] MW3[13 16 20 19 17 14 21 15 12 14 11 15 16 12 18 17 8 12 13 18 10 20 17 10 8 19] TA[104] ECHU[0 0 0 0] ECMM[2] FM[1] CRC[0 0 0 0]\",\n" +
                                                                                        "      \"MM ID3\": \"Ver[9211809-b4b3d00] DNA[01399daf84698759] Elapsed[11028] MW[228218 228640 229260 227795] LW[913913] MH[0 0 0 0] HW[0] DH[4.475%] Temp[24] TMax[95] Fan[5310] FanR[75%] Vi[1197 1197 1200 1200] Vo[4162 4156 4149 4151] GHSmm[21788.15] WU[23836.60] Freq[818.36] PG[15] Led[0] MW0[11 15 10 13 21 17 14 16 17 17 14 18 20 22 20 11 12 17 12 11 11 12 9 18 12 16] MW1[17 11 16 13 16 10 11 8 15 11 11 10 15 16 9 17 13 13 16 15 18 12 16 13 22 15] MW2[11 13 13 12 13 15 13 19 8 12 12 18 7 15 16 21 17 14 12 15 16 18 16 11 13 12] MW3[18 20 13 16 13 13 24 12 15 12 9 8 14 15 15 21 15 22 12 21 10 17 14 14 19 16] TA[104] ECHU[0 0 0 0] ECMM[0] FM[1] CRC[0 0 1 0]\",\n" +
                                                                                        "      \"MM ID4\": \"Ver[9211809-b4b3d00] DNA[013a9e6935923537] Elapsed[11027] MW[229255 227793 228378 228830] LW[914256] MH[0 0 0 0] HW[0] DH[2.325%] Temp[23] TMax[95] Fan[6690] FanR[81%] Vi[1196 1196 1197 1197] Vo[4133 4133 4177 4154] GHSmm[22235.10] WU[23742.47] Freq[835.15] PG[15] Led[0] MW0[11 15 18 8 22 14 14 20 14 18 7 20 10 16 15 14 15 12 11 9 13 15 19 14 19 15] MW1[26 14 7 18 18 10 10 10 12 15 8 13 14 17 17 10 10 9 14 11 20 11 14 19 15 17] MW2[11 15 8 16 15 12 19 17 18 12 11 12 15 13 6 18 17 13 10 9 13 19 16 11 23 18] MW3[12 13 15 9 15 15 13 13 13 15 15 13 17 19 12 17 13 18 18 13 19 25 18 16 16 13] TA[104] ECHU[0 0 0 0] ECMM[0] FM[1] CRC[0 0 0 0]\",\n" +
                                                                                        "      \"MM ID5\": \"Ver[9211809-b4b3d00] DNA[013b25a19b69b193] Elapsed[11027] MW[229687 228133 228639 229170] LW[915629] MH[0 0 1 0] HW[1] DH[3.428%] Temp[24] TMax[96] Fan[4920] FanR[65%] Vi[1192 1192 1202 1202] Vo[4124 4123 4135 4155] GHSmm[22116.60] WU[25137.24] Freq[830.70] PG[15] Led[0] MW0[15 15 11 24 16 14 17 14 20 16 13 13 9 12 18 15 14 12 16 16 15 17 14 9 7 13] MW1[16 18 12 16 22 15 11 4 18 14 16 14 16 18 11 21 22 11 16 14 10 26 12 13 14 12] MW2[16 20 14 18 16 16 18 14 10 22 22 20 17 17 17 22 15 14 21 16 14 17 14 10 14 19] MW3[11 15 20 12 16 15 8 14 20 9 9 20 19 14 13 18 19 16 20 17 14 21 15 10 12 10] TA[104] ECHU[0 0 0 0] ECMM[0] FM[1] CRC[0 0 0 0]\",\n" +
                                                                                        "      \"MM Count\": 5,\n" +
                                                                                        "      \"Smart Speed\": 1,\n" +
                                                                                        "      \"Connecter\": \"AUC\",\n" +
                                                                                        "      \"AUC VER\": \"AUC-20151208\",\n" +
                                                                                        "      \"AUC I2C Speed\": 400000,\n" +
                                                                                        "      \"AUC I2C XDelay\": 24000,\n" +
                                                                                        "      \"AUC Sensor\": 17774,\n" +
                                                                                        "      \"AUC Temperature\": 21.12,\n" +
                                                                                        "      \"Connection Overloaded\": false,\n" +
                                                                                        "      \"Voltage Level Offset\": 0,\n" +
                                                                                        "      \"Nonce Mask\": 24,\n" +
                                                                                        "      \"USB Pipe\": \"0\",\n" +
                                                                                        "      \"USB Delay\": \"r0 0.000000 w0 0.000000\",\n" +
                                                                                        "      \"USB tmo\": \"0 0\"\n" +
                                                                                        "    },\n" +
                                                                                        "    {\n" +
                                                                                        "      \"STATS\": 7,\n" +
                                                                                        "      \"ID\": \"AV97\",\n" +
                                                                                        "      \"Elapsed\": 66007,\n" +
                                                                                        "      \"Calls\": 0,\n" +
                                                                                        "      \"Wait\": 0,\n" +
                                                                                        "      \"Max\": 0,\n" +
                                                                                        "      \"Min\": 99999999,\n" +
                                                                                        "      \"MM ID1\": \"Ver[9211809-b4b3d00] DNA[013607fcf2c12b9c] Elapsed[10086] MW[1611999 1610771 1608354 1607948] LW[6439072] MH[0 0 0 0] HW[0] DH[3.738%] Temp[24] TMax[95] Fan[4740] FanR[62%] Vi[1206 1206 1209 1209] Vo[4152 4161 4156 4173] GHSmm[22215.50] WU[188934.06] Freq[834.42] PG[15] Led[0] MW0[107 113 107 126 87 123 94 99 113 112 107 104 111 112 103 106 118 98 93 112 106 103 105 113 110 78] MW1[109 108 129 115 98 99 117 110 109 114 97 113 90 108 102 116 109 104 109 89 99 93 117 106 122 99] MW2[92 94 86 60 120 105 104 10 90 125 124 104 103 101 102 84 93 124 91 100 72 111 116 106 116 111] MW3[105 109 108 105 103 99 112 122 88 95 100 142 132 102 109 104 121 97 113 80 117 114 107 120 111 110] TA[104] ECHU[0 0 0 0] ECMM[2] FM[1] CRC[0 0 0 0]\",\n" +
                                                                                        "      \"MM ID2\": \"Ver[9211809-b4b3d00] DNA[01361dad4573b165] Elapsed[10085] MW[1611764 1607099 1608843 1608432] LW[6436138] MH[0 0 0 0] HW[0] DH[2.614%] Temp[23] TMax[95] Fan[5760] FanR[70%] Vi[1205 1205 1200 1200] Vo[4164 4172 4127 4153] GHSmm[22202.80] WU[191764.74] Freq[833.94] PG[15] Led[0] MW0[101 89 117 102 106 112 108 86 98 101 112 103 117 130 106 102 98 98 95 118 118 129 101 103 105 98] MW1[118 118 105 106 108 109 110 99 106 84 114 89 115 100 105 104 99 110 123 114 115 106 105 112 129 107] MW2[103 110 120 85 91 106 116 109 110 114 94 141 95 108 102 87 139 99 105 110 114 105 90 98 97 119] MW3[104 102 91 92 110 126 106 118 113 105 93 112 100 106 117 108 87 99 114 107 114 111 100 100 89 119] TA[104] ECHU[0 0 0 0] ECMM[2] FM[1] CRC[0 0 0 0]\",\n" +
                                                                                        "      \"MM ID3\": \"Ver[9211809-b4b3d00] DNA[01399daf84698759] Elapsed[10085] MW[1608727 1612982 1608000 1608452] LW[6438161] MH[0 1 0 1] HW[2] DH[4.269%] Temp[24] TMax[95] Fan[5340] FanR[77%] Vi[1197 1197 1200 1200] Vo[4161 4156 4151 4151] GHSmm[21783.05] WU[183459.56] Freq[818.17] PG[15] Led[0] MW0[104 90 121 99 104 120 104 111 120 98 99 91 109 92 88 116 123 116 109 90 98 91 95 118 96 89] MW1[101 113 107 108 78 111 93 106 91 96 87 110 91 99 111 102 95 98 100 139 117 122 95 99 102 102] MW2[117 106 92 106 76 94 100 100 100 94 107 106 94 76 92 103 112 99 88 95 111 102 117 84 99 126] MW3[103 95 100 93 116 104 103 94 104 93 106 97 120 99 103 97 105 121 114 90 74 111 92 103 92 104] TA[104] ECHU[0 0 0 0] ECMM[0] FM[1] CRC[1 2 0 0]\",\n" +
                                                                                        "      \"MM ID4\": \"Ver[9211809-b4b3d00] DNA[013a9e6935923537] Elapsed[10084] MW[1608655 1610676 1609997 1612409] LW[6441737] MH[0 0 1 2] HW[3] DH[2.340%] Temp[22] TMax[95] Fan[6750] FanR[82%] Vi[1196 1196 1197 1197] Vo[4133 4133 4177 4154] GHSmm[22210.95] WU[194032.13] Freq[834.25] PG[15] Led[0] MW0[99 89 108 105 110 116 99 106 98 108 115 109 115 114 107 126 103 114 96 119 112 113 117 120 103 109] MW1[110 119 100 92 113 114 106 85 110 108 99 116 110 119 116 85 105 119 107 105 99 114 101 119 110 101] MW2[96 107 91 101 130 98 119 109 112 114 114 111 93 103 109 114 106 106 89 98 108 99 98 123 97 103] MW3[90 108 100 110 101 111 112 103 114 102 123 114 105 104 112 102 112 123 124 108 116 115 99 127 119 89] TA[104] ECHU[0 0 0 0] ECMM[0] FM[1] CRC[0 0 0 0]\",\n" +
                                                                                        "      \"MM ID5\": \"Ver[9211809-b4b3d00] DNA[013b25a19b69b193] Elapsed[10084] MW[1610180 1610066 1609591 1608183] LW[6438020] MH[0 0 0 0] HW[0] DH[3.432%] Temp[24] TMax[95] Fan[4860] FanR[64%] Vi[1191 1191 1202 1202] Vo[4123 4123 4137 4156] GHSmm[22095.65] WU[189779.34] Freq[829.91] PG[15] Led[0] MW0[96 93 124 102 122 107 100 93 88 92 114 106 102 104 113 106 99 128 112 125 101 117 107 104 125 111] MW1[93 104 99 114 110 103 100 109 105 115 121 90 103 90 115 105 102 100 118 97 100 114 110 108 127 108] MW2[95 104 106 116 113 99 113 121 93 93 103 111 101 107 108 103 99 101 107 112 103 80 89 110 97 101] MW3[99 108 100 98 114 99 105 90 112 98 110 112 103 102 107 100 109 111 97 107 105 116 89 109 121 100] TA[104] ECHU[0 0 0 0] ECMM[0] FM[1] CRC[1 0 0 0]\",\n" +
                                                                                        "      \"MM Count\": 5,\n" +
                                                                                        "      \"Smart Speed\": 1,\n" +
                                                                                        "      \"Connecter\": \"AUC\",\n" +
                                                                                        "      \"AUC VER\": \"AUC-20151208\",\n" +
                                                                                        "      \"AUC I2C Speed\": 400000,\n" +
                                                                                        "      \"AUC I2C XDelay\": 24000,\n" +
                                                                                        "      \"AUC Sensor\": 17903,\n" +
                                                                                        "      \"AUC Temperature\": 20.73,\n" +
                                                                                        "      \"Connection Overloaded\": false,\n" +
                                                                                        "      \"Voltage Level Offset\": 0,\n" +
                                                                                        "      \"Nonce Mask\": 24,\n" +
                                                                                        "      \"USB Pipe\": \"0\",\n" +
                                                                                        "      \"USB Delay\": \"r0 0.000000 w0 0.000000\",\n" +
                                                                                        "      \"USB tmo\": \"0 0\"\n" +
                                                                                        "    },\n" +
                                                                                        "    {\n" +
                                                                                        "      \"STATS\": 8,\n" +
                                                                                        "      \"ID\": \"AV98\",\n" +
                                                                                        "      \"Elapsed\": 66007,\n" +
                                                                                        "      \"Calls\": 0,\n" +
                                                                                        "      \"Wait\": 0,\n" +
                                                                                        "      \"Max\": 0,\n" +
                                                                                        "      \"Min\": 99999999,\n" +
                                                                                        "      \"MM ID1\": \"Ver[9211809-b4b3d00] DNA[0130fba8458e2cec] Elapsed[7051] MW[1770643 1770269 1769086 1772481] LW[7082479] MH[0 0 1 0] HW[1] DH[2.571%] Temp[21] TMax[95] Fan[5400] FanR[65%] Vi[1201 1201 1190 1190] Vo[4125 4144 4128 4137] GHSmm[22230.70] WU[301354.54] Freq[834.99] PG[15] Led[0] MW0[103 99 130 118 104 110 110 109 124 90 128 103 110 102 114 138 134 93 120 121 117 119 111 106 127 105] MW1[133 118 125 112 122 124 108 114 101 112 122 126 129 122 123 125 115 117 124 111 125 117 115 125 117 143] MW2[101 116 120 134 142 135 109 118 114 132 104 109 130 115 110 131 123 108 119 130 118 121 114 123 130 127] MW3[97 105 99 120 107 122 112 117 114 89 133 123 123 112 106 115 109 121 112 135 115 108 134 114 104 116] TA[104] ECHU[0 0 0 0] ECMM[0] FM[1] CRC[0 0 0 0]\",\n" +
                                                                                        "      \"MM ID2\": \"Ver[9211809-b4b3d00] DNA[013153fdcd0b5fd8] Elapsed[7050] MW[1768752 1772787 1769926 1770809] LW[7082274] MH[0 0 0 0] HW[0] DH[3.318%] Temp[25] TMax[95] Fan[4980] FanR[59%] Vi[1192 1192 1190 1190] Vo[4130 4097 4129 4087] GHSmm[21649.10] WU[296640.56] Freq[813.14] PG[15] Led[0] MW0[120 135 108 130 108 105 115 113 118 125 116 115 126 117 137 123 108 138 136 128 126 130 138 107 121 105] MW1[114 100 113 98 99 113 117 113 103 103 109 103 113 101 112 111 108 102 114 112 100 100 120 100 110 102] MW2[120 108 108 110 126 134 121 122 121 107 119 132 129 114 116 110 118 131 117 127 107 118 135 108 123 133] MW3[116 96 107 112 126 126 108 99 105 111 103 114 119 115 124 117 106 115 111 120 102 117 127 105 101 120] TA[104] ECHU[0 0 0 0] ECMM[0] FM[1] CRC[0 0 0 0]\",\n" +
                                                                                        "      \"MM ID3\": \"Ver[9211809-b4b3d00] DNA[01353126ffee988b] Elapsed[7050] MW[1770562 1771599 1771371 1768057] LW[7081589] MH[0 0 1 1] HW[2] DH[3.938%] Temp[23] TMax[95] Fan[5160] FanR[62%] Vi[1198 1198 1200 1200] Vo[4147 4165 4134 4140] GHSmm[21509.90] WU[284592.57] Freq[807.91] PG[15] Led[0] MW0[120 132 103 83 118 108 125 105 120 97 105 110 120 89 95 101 113 107 130 106 121 97 124 109 102 101] MW1[122 123 110 91 110 103 103 119 99 136 114 121 95 119 110 124 94 87 95 93 108 119 115 100 90 117] MW2[116 144 95 113 95 138 125 103 125 115 102 122 102 130 106 99 103 114 121 128 108 102 104 113 92 112] MW3[109 120 131 106 101 109 100 125 116 114 114 117 116 133 104 104 99 90 105 120 108 125 98 98 124 116] TA[104] ECHU[0 0 0 0] ECMM[0] FM[1] CRC[0 0 0 0]\",\n" +
                                                                                        "      \"MM ID4\": \"Ver[9211809-b4b3d00] DNA[013cbe94d0402c2b] Elapsed[7049] MW[1770518 1772997 1764959 1770864] LW[7079338] MH[0 0 0 0] HW[0] DH[3.396%] Temp[24] TMax[95] Fan[4800] FanR[64%] Vi[1200 1200 1204 1204] Vo[4157 4165 4145 4182] GHSmm[22052.25] WU[294049.29] Freq[828.28] PG[15] Led[0] MW0[117 113 111 100 132 112 107 91 117 120 121 125 122 110 104 111 103 106 116 121 119 138 112 107 127 133] MW1[124 117 107 138 108 134 121 117 100 90 110 106 112 101 109 108 112 117 115 113 127 116 94 96 111 105] MW2[105 134 119 118 106 109 135 120 110 131 109 119 116 104 125 108 131 113 130 98 115 103 91 100 105 113] MW3[103 103 110 106 121 107 132 122 127 134 112 92 104 111 122 124 118 136 120 108 113 113 121 124 117 98] TA[104] ECHU[0 0 0 0] ECMM[0] FM[1] CRC[0 0 0 0]\",\n" +
                                                                                        "      \"MM ID5\": \"Ver[9211809-b4b3d00] DNA[013e7fdfd11528f6] Elapsed[7049] MW[1770926 1769008 1767713 1767465] LW[7075112] MH[0 0 0 0] HW[0] DH[5.476%] Temp[23] TMax[95] Fan[5040] FanR[60%] Vi[1199 1199 1212 1212] Vo[4131 4134 4154 4155] GHSmm[21762.75] WU[283758.97] Freq[817.41] PG[15] Led[0] MW0[89 114 74 110 111 107 113 125 95 94 103 95 105 100 100 100 103 105 103 111 61 100 103 95 2 109] MW1[134 109 112 100 92 122 113 90 122 98 113 117 115 112 119 114 109 109 110 96 99 130 117 119 119 105] MW2[103 119 128 127 101 126 110 101 112 104 130 107 129 115 110 105 116 91 114 114 112 102 117 110 116 126] MW3[121 108 121 113 124 104 136 125 116 114 114 109 138 120 119 126 134 122 126 125 99 111 108 108 127 117] TA[104] ECHU[0 0 0 0] ECMM[2] FM[1] CRC[0 0 0 0]\",\n" +
                                                                                        "      \"MM Count\": 5,\n" +
                                                                                        "      \"Smart Speed\": 1,\n" +
                                                                                        "      \"Connecter\": \"AUC\",\n" +
                                                                                        "      \"AUC VER\": \"AUC-20151208\",\n" +
                                                                                        "      \"AUC I2C Speed\": 400000,\n" +
                                                                                        "      \"AUC I2C XDelay\": 24000,\n" +
                                                                                        "      \"AUC Sensor\": 18580,\n" +
                                                                                        "      \"AUC Temperature\": 18.7,\n" +
                                                                                        "      \"Connection Overloaded\": false,\n" +
                                                                                        "      \"Voltage Level Offset\": 0,\n" +
                                                                                        "      \"Nonce Mask\": 24,\n" +
                                                                                        "      \"USB Pipe\": \"0\",\n" +
                                                                                        "      \"USB Delay\": \"r0 0.000000 w0 0.000000\",\n" +
                                                                                        "      \"USB tmo\": \"0 0\"\n" +
                                                                                        "    },\n" +
                                                                                        "    {\n" +
                                                                                        "      \"STATS\": 9,\n" +
                                                                                        "      \"ID\": \"AV99\",\n" +
                                                                                        "      \"Elapsed\": 66007,\n" +
                                                                                        "      \"Calls\": 0,\n" +
                                                                                        "      \"Wait\": 0,\n" +
                                                                                        "      \"Max\": 0,\n" +
                                                                                        "      \"Min\": 99999999,\n" +
                                                                                        "      \"MM ID1\": \"Ver[9211809-b4b3d00] DNA[013607fcf2c12b9c] Elapsed[3628] MW[913748 912730 905982 907849] LW[3640309] MH[1 0 0 0] HW[1] DH[3.860%] Temp[23] TMax[94] Fan[4440] FanR[56%] Vi[1206 1206 1209 1209] Vo[4153 4162 4156 4173] GHSmm[22211.00] WU[293759.99] Freq[834.25] PG[15] Led[0] MW0[68 62 57 57 59 59 51 58 58 58 76 64 63 54 76 52 72 48 53 59 64 59 55 57 71 49] MW1[73 59 67 69 69 71 62 54 58 52 61 60 69 61 42 51 68 37 46 59 76 69 66 65 49 73] MW2[58 55 54 43 60 53 61 3 62 44 56 56 44 62 64 66 59 61 55 64 46 64 56 57 59 49] MW3[67 44 58 66 55 60 58 55 48 65 45 69 75 58 62 52 68 57 64 57 55 65 61 59 53 70] TA[104] ECHU[0 0 0 0] ECMM[2] FM[1] CRC[0 0 0 0]\",\n" +
                                                                                        "      \"MM ID2\": \"Ver[9211809-b4b3d00] DNA[01361dad4573b165] Elapsed[3628] MW[913866 907489 906992 912616] LW[3640963] MH[0 0 0 0] HW[0] DH[2.620%] Temp[23] TMax[94] Fan[5490] FanR[66%] Vi[1205 1205 1200 1200] Vo[4163 4171 4127 4154] GHSmm[22173.70] WU[298993.61] Freq[832.85] PG[15] Led[0] MW0[62 68 58 54 57 57 63 51 62 54 51 58 64 65 48 60 64 83 64 57 53 75 73 67 50 54] MW1[54 67 70 53 72 73 62 76 59 58 68 74 57 55 47 61 54 56 65 61 63 62 49 53 73 63] MW2[66 58 55 61 51 68 57 70 64 54 54 59 64 56 57 62 62 54 53 63 61 54 56 60 64 71] MW3[50 53 52 54 56 70 28 64 62 59 48 61 56 57 57 67 62 52 64 57 62 62 54 55 52 65] TA[104] ECHU[0 0 0 0] ECMM[2] FM[1] CRC[0 0 0 0]\",\n" +
                                                                                        "      \"MM ID3\": \"Ver[9211809-b4b3d00] DNA[01399daf84698759] Elapsed[3627] MW[912381 908285 908052 910444] LW[3639162] MH[0 0 0 0] HW[0] DH[4.509%] Temp[23] TMax[94] Fan[5070] FanR[69%] Vi[1197 1197 1200 1200] Vo[4162 4155 4149 4155] GHSmm[21737.30] WU[287182.39] Freq[816.46] PG[15] Led[0] MW0[58 42 51 62 54 60 54 63 59 60 60 57 65 64 66 55 60 51 63 55 53 50 60 42 45 57] MW1[58 53 57 53 69 61 62 58 52 73 66 77 53 65 54 49 62 66 65 51 61 51 48 63 66 58] MW2[58 48 60 53 67 52 50 50 72 68 47 50 62 52 38 50 68 61 54 71 50 52 50 45 58 64] MW3[55 64 52 59 51 64 55 70 50 62 51 54 55 62 54 64 51 53 45 57 68 52 61 55 76 57] TA[104] ECHU[0 0 0 0] ECMM[0] FM[1] CRC[1 9 0 0]\",\n" +
                                                                                        "      \"MM ID4\": \"Ver[9211809-b4b3d00] DNA[013a9e6935923537] Elapsed[3627] MW[909839 910998 910162 910622] LW[3641621] MH[0 1 0 0] HW[1] DH[2.333%] Temp[22] TMax[94] Fan[6210] FanR[75%] Vi[1196 1196 1197 1197] Vo[4134 4131 4176 4154] GHSmm[22200.80] WU[292031.19] Freq[833.86] PG[15] Led[0] MW0[60 61 58 52 59 51 56 60 49 62 62 62 67 53 53 64 38 59 54 74 60 59 55 62 76 71] MW1[58 56 46 55 63 49 44 60 69 59 59 48 48 59 58 56 56 52 52 44 59 55 72 69 53 60] MW2[62 58 71 71 49 52 47 66 72 57 57 53 53 68 50 73 56 78 50 40 60 53 63 57 56 43] MW3[57 61 60 73 75 62 56 73 57 58 59 59 46 49 59 70 56 69 49 60 58 54 58 55 64 56] TA[104] ECHU[0 0 0 0] ECMM[0] FM[1] CRC[0 0 0 0]\",\n" +
                                                                                        "      \"MM ID5\": \"Ver[9211809-b4b3d00] DNA[013b25a19b69b193] Elapsed[3626] MW[910331 911665 907575 908803] LW[3638374] MH[0 0 0 0] HW[0] DH[3.411%] Temp[24] TMax[94] Fan[4590] FanR[59%] Vi[1192 1192 1202 1202] Vo[4124 4124 4137 4156] GHSmm[22074.55] WU[289126.75] Freq[829.12] PG[15] Led[0] MW0[49 55 61 63 49 65 52 54 60 57 69 62 54 56 62 61 51 46 45 62 61 46 52 56 71 57] MW1[55 54 45 49 55 60 59 66 68 53 50 60 52 64 60 62 58 60 76 71 60 58 65 51 53 58] MW2[68 69 45 54 49 58 69 55 65 57 49 53 51 50 55 62 57 48 54 61 54 54 58 64 52 59] MW3[55 57 57 69 60 37 56 72 64 64 55 45 62 69 61 67 59 61 50 60 60 68 61 51 56 59] TA[104] ECHU[0 0 0 0] ECMM[0] FM[1] CRC[0 0 0 0]\",\n" +
                                                                                        "      \"MM Count\": 5,\n" +
                                                                                        "      \"Smart Speed\": 1,\n" +
                                                                                        "      \"Connecter\": \"AUC\",\n" +
                                                                                        "      \"AUC VER\": \"AUC-20151208\",\n" +
                                                                                        "      \"AUC I2C Speed\": 400000,\n" +
                                                                                        "      \"AUC I2C XDelay\": 24000,\n" +
                                                                                        "      \"AUC Sensor\": 18064,\n" +
                                                                                        "      \"AUC Temperature\": 20.25,\n" +
                                                                                        "      \"Connection Overloaded\": false,\n" +
                                                                                        "      \"Voltage Level Offset\": 0,\n" +
                                                                                        "      \"Nonce Mask\": 24,\n" +
                                                                                        "      \"USB Pipe\": \"0\",\n" +
                                                                                        "      \"USB Delay\": \"r0 0.000000 w0 0.000000\",\n" +
                                                                                        "      \"USB tmo\": \"0 0\"\n" +
                                                                                        "    },\n" +
                                                                                        "    {\n" +
                                                                                        "      \"STATS\": 10,\n" +
                                                                                        "      \"ID\": \"POOL0\",\n" +
                                                                                        "      \"Elapsed\": 66007,\n" +
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
                                                                                        "      \"Work Diff\": 524288,\n" +
                                                                                        "      \"Min Diff\": 1,\n" +
                                                                                        "      \"Max Diff\": 2097152,\n" +
                                                                                        "      \"Min Diff Count\": 15,\n" +
                                                                                        "      \"Max Diff Count\": 2136,\n" +
                                                                                        "      \"Times Sent\": 12960,\n" +
                                                                                        "      \"Bytes Sent\": 1493267,\n" +
                                                                                        "      \"Times Recv\": 15006,\n" +
                                                                                        "      \"Bytes Recv\": 3328778,\n" +
                                                                                        "      \"Net Bytes Sent\": 1493267,\n" +
                                                                                        "      \"Net Bytes Recv\": 3328778\n" +
                                                                                        "    },\n" +
                                                                                        "    {\n" +
                                                                                        "      \"STATS\": 11,\n" +
                                                                                        "      \"ID\": \"POOL1\",\n" +
                                                                                        "      \"Elapsed\": 66007,\n" +
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
                                                                                        "      \"Work Diff\": 16384,\n" +
                                                                                        "      \"Min Diff\": 16384,\n" +
                                                                                        "      \"Max Diff\": 16384,\n" +
                                                                                        "      \"Min Diff Count\": 1,\n" +
                                                                                        "      \"Max Diff Count\": 1,\n" +
                                                                                        "      \"Times Sent\": 2,\n" +
                                                                                        "      \"Bytes Sent\": 146,\n" +
                                                                                        "      \"Times Recv\": 5,\n" +
                                                                                        "      \"Bytes Recv\": 2914,\n" +
                                                                                        "      \"Net Bytes Sent\": 146,\n" +
                                                                                        "      \"Net Bytes Recv\": 2914\n" +
                                                                                        "    },\n" +
                                                                                        "    {\n" +
                                                                                        "      \"STATS\": 12,\n" +
                                                                                        "      \"ID\": \"POOL2\",\n" +
                                                                                        "      \"Elapsed\": 66007,\n" +
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
                                                                                        "      \"Work Diff\": 16384,\n" +
                                                                                        "      \"Min Diff\": 16384,\n" +
                                                                                        "      \"Max Diff\": 16384,\n" +
                                                                                        "      \"Min Diff Count\": 1,\n" +
                                                                                        "      \"Max Diff Count\": 1,\n" +
                                                                                        "      \"Times Sent\": 2,\n" +
                                                                                        "      \"Bytes Sent\": 146,\n" +
                                                                                        "      \"Times Recv\": 5,\n" +
                                                                                        "      \"Bytes Recv\": 2914,\n" +
                                                                                        "      \"Net Bytes Sent\": 146,\n" +
                                                                                        "      \"Net Bytes Recv\": 2914\n" +
                                                                                        "    }\n" +
                                                                                        "  ],\n" +
                                                                                        "  \"id\": 1\n" +
                                                                                        "}",
                                                                                Map.class)),
                                                                "pools",
                                                                Collections.singletonList(
                                                                        MAPPER.readValue(
                                                                                "{\n" +
                                                                                        "  \"STATUS\": [\n" +
                                                                                        "    {\n" +
                                                                                        "      \"STATUS\": \"S\",\n" +
                                                                                        "      \"When\": 1548764064,\n" +
                                                                                        "      \"Code\": 7,\n" +
                                                                                        "      \"Msg\": \"3 Pool(s)\",\n" +
                                                                                        "      \"Description\": \"cgminer 4.10.0\"\n" +
                                                                                        "    }\n" +
                                                                                        "  ],\n" +
                                                                                        "  \"POOLS\": [\n" +
                                                                                        "    {\n" +
                                                                                        "      \"POOL\": 0,\n" +
                                                                                        "      \"URL\": \"stratum+tcp:\\/\\/eu.ss.btc.com:1800\",\n" +
                                                                                        "      \"Status\": \"Alive\",\n" +
                                                                                        "      \"Priority\": 0,\n" +
                                                                                        "      \"Quota\": 1,\n" +
                                                                                        "      \"Long Poll\": \"N\",\n" +
                                                                                        "      \"Getworks\": 2197,\n" +
                                                                                        "      \"Accepted\": 12757,\n" +
                                                                                        "      \"Rejected\": 32,\n" +
                                                                                        "      \"Works\": 2223762,\n" +
                                                                                        "      \"Discarded\": 35199,\n" +
                                                                                        "      \"Stale\": 781,\n" +
                                                                                        "      \"Get Failures\": 2,\n" +
                                                                                        "      \"Remote Failures\": 2,\n" +
                                                                                        "      \"User\": \"WALLET.a2\",\n" +
                                                                                        "      \"Last Share Time\": 1548764054,\n" +
                                                                                        "      \"Diff1 Shares\": 6472946191,\n" +
                                                                                        "      \"Proxy Type\": \"\",\n" +
                                                                                        "      \"Proxy\": \"\",\n" +
                                                                                        "      \"Difficulty Accepted\": 6436438016,\n" +
                                                                                        "      \"Difficulty Rejected\": 7389199,\n" +
                                                                                        "      \"Difficulty Stale\": 10829824,\n" +
                                                                                        "      \"Last Share Difficulty\": 524288,\n" +
                                                                                        "      \"Work Difficulty\": 524288,\n" +
                                                                                        "      \"Has Stratum\": true,\n" +
                                                                                        "      \"Stratum Active\": true,\n" +
                                                                                        "      \"Stratum URL\": \"eu.ss.btc.com\",\n" +
                                                                                        "      \"Stratum Difficulty\": 524288,\n" +
                                                                                        "      \"Has GBT\": false,\n" +
                                                                                        "      \"Best Share\": 3540192838,\n" +
                                                                                        "      \"Pool Rejected%\": 0.1145,\n" +
                                                                                        "      \"Pool Stale%\": 0.1678,\n" +
                                                                                        "      \"Bad Work\": 93,\n" +
                                                                                        "      \"Current Block Height\": 560612,\n" +
                                                                                        "      \"Current Block Version\": 536870912\n" +
                                                                                        "    },\n" +
                                                                                        "    {\n" +
                                                                                        "      \"POOL\": 1,\n" +
                                                                                        "      \"URL\": \"stratum+tcp:\\/\\/eu.ss.btc.com:443\",\n" +
                                                                                        "      \"Status\": \"Alive\",\n" +
                                                                                        "      \"Priority\": 1,\n" +
                                                                                        "      \"Quota\": 1,\n" +
                                                                                        "      \"Long Poll\": \"N\",\n" +
                                                                                        "      \"Getworks\": 2,\n" +
                                                                                        "      \"Accepted\": 0,\n" +
                                                                                        "      \"Rejected\": 0,\n" +
                                                                                        "      \"Works\": 0,\n" +
                                                                                        "      \"Discarded\": 0,\n" +
                                                                                        "      \"Stale\": 0,\n" +
                                                                                        "      \"Get Failures\": 0,\n" +
                                                                                        "      \"Remote Failures\": 0,\n" +
                                                                                        "      \"User\": \"WALLET.a2\",\n" +
                                                                                        "      \"Last Share Time\": 0,\n" +
                                                                                        "      \"Diff1 Shares\": 0,\n" +
                                                                                        "      \"Proxy Type\": \"\",\n" +
                                                                                        "      \"Proxy\": \"\",\n" +
                                                                                        "      \"Difficulty Accepted\": 0,\n" +
                                                                                        "      \"Difficulty Rejected\": 0,\n" +
                                                                                        "      \"Difficulty Stale\": 0,\n" +
                                                                                        "      \"Last Share Difficulty\": 0,\n" +
                                                                                        "      \"Work Difficulty\": 16384,\n" +
                                                                                        "      \"Has Stratum\": true,\n" +
                                                                                        "      \"Stratum Active\": false,\n" +
                                                                                        "      \"Stratum URL\": \"\",\n" +
                                                                                        "      \"Stratum Difficulty\": 0,\n" +
                                                                                        "      \"Has GBT\": false,\n" +
                                                                                        "      \"Best Share\": 0,\n" +
                                                                                        "      \"Pool Rejected%\": 0,\n" +
                                                                                        "      \"Pool Stale%\": 0,\n" +
                                                                                        "      \"Bad Work\": 0,\n" +
                                                                                        "      \"Current Block Height\": 560502,\n" +
                                                                                        "      \"Current Block Version\": 536870912\n" +
                                                                                        "    },\n" +
                                                                                        "    {\n" +
                                                                                        "      \"POOL\": 2,\n" +
                                                                                        "      \"URL\": \"stratum+tcp:\\/\\/eu.ss.btc.com:25\",\n" +
                                                                                        "      \"Status\": \"Alive\",\n" +
                                                                                        "      \"Priority\": 2,\n" +
                                                                                        "      \"Quota\": 1,\n" +
                                                                                        "      \"Long Poll\": \"N\",\n" +
                                                                                        "      \"Getworks\": 2,\n" +
                                                                                        "      \"Accepted\": 0,\n" +
                                                                                        "      \"Rejected\": 0,\n" +
                                                                                        "      \"Works\": 0,\n" +
                                                                                        "      \"Discarded\": 0,\n" +
                                                                                        "      \"Stale\": 0,\n" +
                                                                                        "      \"Get Failures\": 0,\n" +
                                                                                        "      \"Remote Failures\": 0,\n" +
                                                                                        "      \"User\": \"WALLET.a2\",\n" +
                                                                                        "      \"Last Share Time\": 0,\n" +
                                                                                        "      \"Diff1 Shares\": 0,\n" +
                                                                                        "      \"Proxy Type\": \"\",\n" +
                                                                                        "      \"Proxy\": \"\",\n" +
                                                                                        "      \"Difficulty Accepted\": 0,\n" +
                                                                                        "      \"Difficulty Rejected\": 0,\n" +
                                                                                        "      \"Difficulty Stale\": 0,\n" +
                                                                                        "      \"Last Share Difficulty\": 0,\n" +
                                                                                        "      \"Work Difficulty\": 16384,\n" +
                                                                                        "      \"Has Stratum\": true,\n" +
                                                                                        "      \"Stratum Active\": false,\n" +
                                                                                        "      \"Stratum URL\": \"\",\n" +
                                                                                        "      \"Stratum Difficulty\": 0,\n" +
                                                                                        "      \"Has GBT\": false,\n" +
                                                                                        "      \"Best Share\": 0,\n" +
                                                                                        "      \"Pool Rejected%\": 0,\n" +
                                                                                        "      \"Pool Stale%\": 0,\n" +
                                                                                        "      \"Bad Work\": 0,\n" +
                                                                                        "      \"Current Block Height\": 560502,\n" +
                                                                                        "      \"Current Block Version\": 536870912\n" +
                                                                                        "    }\n" +
                                                                                        "  ],\n" +
                                                                                        "  \"id\": 1\n" +
                                                                                        "}",
                                                                                Map.class)))))),
                                new MinerStats.Builder()
                                        .setApiIp("127.0.0.1")
                                        .setApiPort(4028)
                                        .addPool(
                                                new Pool.Builder()
                                                        .setName("eu.ss.btc.com:1800")
                                                        .setPriority(0)
                                                        .setStatus(
                                                                true,
                                                                true)
                                                        .setCounts(
                                                                12757,
                                                                32,
                                                                781)
                                                        .build())
                                        .addPool(
                                                new Pool.Builder()
                                                        .setName("eu.ss.btc.com:443")
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
                                                        .setName("eu.ss.btc.com:25")
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
                                                        .setHashRate(new BigDecimal("420699742360000.000"))
                                                        .setFanInfo(
                                                                new FanInfo.Builder()
                                                                        .setCount(50)
                                                                        .addSpeed(63)
                                                                        .addSpeed(63)
                                                                        .addSpeed(60)
                                                                        .addSpeed(57)
                                                                        .addSpeed(49)
                                                                        .addSpeed(68)
                                                                        .addSpeed(62)
                                                                        .addSpeed(63)
                                                                        .addSpeed(67)
                                                                        .addSpeed(62)
                                                                        .addSpeed(62)
                                                                        .addSpeed(71)
                                                                        .addSpeed(72)
                                                                        .addSpeed(78)
                                                                        .addSpeed(64)
                                                                        .addSpeed(56)
                                                                        .addSpeed(63)
                                                                        .addSpeed(62)
                                                                        .addSpeed(59)
                                                                        .addSpeed(61)
                                                                        .addSpeed(62)
                                                                        .addSpeed(71)
                                                                        .addSpeed(73)
                                                                        .addSpeed(78)
                                                                        .addSpeed(64)
                                                                        .addSpeed(62)
                                                                        .addSpeed(71)
                                                                        .addSpeed(75)
                                                                        .addSpeed(79)
                                                                        .addSpeed(65)
                                                                        .addSpeed(62)
                                                                        .addSpeed(69)
                                                                        .addSpeed(75)
                                                                        .addSpeed(81)
                                                                        .addSpeed(65)
                                                                        .addSpeed(62)
                                                                        .addSpeed(70)
                                                                        .addSpeed(77)
                                                                        .addSpeed(82)
                                                                        .addSpeed(64)
                                                                        .addSpeed(65)
                                                                        .addSpeed(59)
                                                                        .addSpeed(62)
                                                                        .addSpeed(64)
                                                                        .addSpeed(60)
                                                                        .addSpeed(56)
                                                                        .addSpeed(66)
                                                                        .addSpeed(69)
                                                                        .addSpeed(75)
                                                                        .addSpeed(59)
                                                                        .setSpeedUnits("%")
                                                                        .build())
                                                        .addTemp(23)
                                                        .addTemp(94)
                                                        .addTemp(24)
                                                        .addTemp(94)
                                                        .addTemp(23)
                                                        .addTemp(94)
                                                        .addTemp(24)
                                                        .addTemp(94)
                                                        .addTemp(24)
                                                        .addTemp(94)
                                                        .addTemp(21)
                                                        .addTemp(95)
                                                        .addTemp(24)
                                                        .addTemp(95)
                                                        .addTemp(24)
                                                        .addTemp(95)
                                                        .addTemp(25)
                                                        .addTemp(95)
                                                        .addTemp(22)
                                                        .addTemp(95)
                                                        .addTemp(25)
                                                        .addTemp(95)
                                                        .addTemp(23)
                                                        .addTemp(95)
                                                        .addTemp(24)
                                                        .addTemp(95)
                                                        .addTemp(22)
                                                        .addTemp(95)
                                                        .addTemp(25)
                                                        .addTemp(95)
                                                        .addTemp(22)
                                                        .addTemp(95)
                                                        .addTemp(22)
                                                        .addTemp(94)
                                                        .addTemp(22)
                                                        .addTemp(94)
                                                        .addTemp(25)
                                                        .addTemp(94)
                                                        .addTemp(22)
                                                        .addTemp(94)
                                                        .addTemp(24)
                                                        .addTemp(96)
                                                        .addTemp(23)
                                                        .addTemp(95)
                                                        .addTemp(23)
                                                        .addTemp(95)
                                                        .addTemp(22)
                                                        .addTemp(95)
                                                        .addTemp(24)
                                                        .addTemp(95)
                                                        .addTemp(24)
                                                        .addTemp(95)
                                                        .addTemp(23)
                                                        .addTemp(95)
                                                        .addTemp(24)
                                                        .addTemp(95)
                                                        .addTemp(23)
                                                        .addTemp(95)
                                                        .addTemp(24)
                                                        .addTemp(95)
                                                        .addTemp(24)
                                                        .addTemp(95)
                                                        .addTemp(24)
                                                        .addTemp(95)
                                                        .addTemp(24)
                                                        .addTemp(95)
                                                        .addTemp(23)
                                                        .addTemp(95)
                                                        .addTemp(24)
                                                        .addTemp(96)
                                                        .addTemp(24)
                                                        .addTemp(95)
                                                        .addTemp(23)
                                                        .addTemp(95)
                                                        .addTemp(24)
                                                        .addTemp(95)
                                                        .addTemp(22)
                                                        .addTemp(95)
                                                        .addTemp(24)
                                                        .addTemp(95)
                                                        .addTemp(21)
                                                        .addTemp(95)
                                                        .addTemp(25)
                                                        .addTemp(95)
                                                        .addTemp(23)
                                                        .addTemp(95)
                                                        .addTemp(24)
                                                        .addTemp(95)
                                                        .addTemp(23)
                                                        .addTemp(95)
                                                        .addTemp(23)
                                                        .addTemp(94)
                                                        .addTemp(23)
                                                        .addTemp(94)
                                                        .addTemp(23)
                                                        .addTemp(94)
                                                        .addTemp(22)
                                                        .addTemp(94)
                                                        .addTemp(24)
                                                        .addTemp(94)
                                                        .addRawStats(
                                                                ImmutableMap.of(
                                                                        "summary.0.summary.0.remote_failures",
                                                                        new BigDecimal("2"),
                                                                        "summary.0.summary.0.total_mh",
                                                                        new BigDecimal("27769280354638")))
                                                        .build())
                                        .build()
                        },
                        {
                                // Avalon 10XX
                                ImmutableMap.of(
                                        "{\"command\":\"pools+summary+stats\"}",
                                        new RpcHandler(
                                                "{\"summary\":[{\"STATUS\":[{\"STATUS\":\"S\",\"When\":168,\"Code\":11,\"Msg\":\"Summary\",\"Description\":\"cgminer 4.11.1\"}],\"SUMMARY\":[{\"Elapsed\":155,\"MHS av\":25536751.74,\"MHS 30s\":32247416.81,\"MHS 1m\":28134393.97,\"MHS 5m\":10769774.69,\"MHS 15m\":4090974.03,\"Found Blocks\":0,\"Getworks\":13,\"Accepted\":6,\"Rejected\":0,\"Hardware Errors\":0,\"Utility\":2.32,\"Discarded\":3691802,\"Stale\":0,\"Get Failures\":0,\"Local Work\":1800,\"Remote Failures\":0,\"Network Blocks\":4,\"Total MH\":3940649502.0000,\"Work Utility\":354447.38,\"Difficulty Accepted\":794291.61511800,\"Difficulty Rejected\":0.00000000,\"Difficulty Stale\":0.00000000,\"Best Share\":222408,\"Device Hardware%\":0.0000,\"Device Rejected%\":0.0000,\"Pool Rejected%\":0.0000,\"Pool Stale%\":0.0000,\"Last getwork\":0}],\"id\":1}],\"stats\":[{\"STATUS\":[{\"STATUS\":\"S\",\"When\":168,\"Code\":70,\"Msg\":\"CGMiner stats\",\"Description\":\"cgminer 4.11.1\"}],\"STATS\":[{\"STATS\":0,\"ID\":\"AVA100\",\"Elapsed\":155,\"Calls\":0,\"Wait\":0.000000,\"Max\":0.000000,\"Min\":99999999.000000,\"MM ID0\":\"Ver[1047-20051101_85dc6f0_0332654] DNA[02010000cb231da8] MEMFREE[1413136.0] NETFAIL[0 0 0 0 0 0 0 0] SYSTEMSTATU[Work: In Work, Hash Board: 2 ] Elapsed[157] BOOTBY[0x0C.00000001] LW[37060] MH[0 0] HW[0] DH[6.118%] Temp[18] TMax[64] TAvg[57] Fan1[2896] Fan2[2927] FanR[31%] Vo[0] PS[0 0 0 0 0 0] PLL0[1925 1138 1411 2606] PLL1[1359 1377 1905 2439] GHSmm[37470.98] GHSavg[25174.76] WU[351687.39] Freq[661.56] Led[0] MGHS[12362.61 12812.16] MTmax[61 64] MTavg[55 58] TA[240] PING[100] ECHU[512 512] ECMM[0] SF0[612 637 662 687] SF1[637 662 687 712] PVT_T0[ 56  56  54  54  56  56  56  55  54  54  56  56  56  56  55  55  56  57  58  55  55  56  59  58  59  57  56  55  59  58  58  58  56  54  58  58  58  57  55  53  57  56  55  55  53  51  55  53  53  53  51  48  53  51  52  53  50  47  51  50  52  52  52  53  54  53  54  55  55  54  55  54  55  56  57  57  57  56  57  59  61  59  59  58  57  59  59  59  60  58  58  60  60  59  60  58  58  59  60  57  59  57  56  58  57  57  57  56  56  56  58  56  58  56  56  57  56  54  56  56] PVT_T1[ 58  59  56  56  60  59  58  58  58  56  58  59  58  58  56  56  59  58  59  59  59  59  61  60  61  61  60  59  61  62  62  61  59  58  61  62  62  59  58  56  59  61  59  58  55  52  56  56  56  55  53  52  54  55  55  54  52  51  53  54  54  56  56  58  58  56  57  59  60  59  59  58  57  59  59  60  60  58  61  63  61  61  63  62  62  62  62  63  63  62  62  62  62  62  64  62  60  62  61  59  62  60  58  62  60  59  61  58  59  60  60  59  61  59  60  62  59  57  59  60] PVT_V0[319 321 322 322 322 322 318 318 320 318 319 319 322 322 321 319 317 318 319 321 319 316 315 317 314 317 319 315 315 317 317 316 317 318 317 317 318 317 319 316 315 317 323 321 322 322 319 319 320 320 320 322 321 322 325 324 325 323 324 327 327 325 324 324 324 326 325 325 324 321 320 318 322 320 320 319 320 319 315 316 315 317 320 320 320 318 320 319 319 319 318 317 316 314 313 314 319 318 317 324 323 324 318 319 320 323 324 323 323 322 321 322 323 323 320 320 320 322 323 322] PVT_V1[317 320 324 317 317 319 323 323 322 329 331 329 317 318 318 315 316 317 332 332 331 323 325 323 318 318 320 319 319 319 315 315 315 314 311 311 319 319 318 317 322 320 319 321 327 317 318 320 322 321 322 319 319 319 322 321 324 322 325 327 327 324 322 319 319 321 320 321 321 319 318 318 325 321 320 323 320 320 315 317 315 317 317 319 311 311 313 311 313 313 317 317 316 318 316 316 318 316 316 323 321 322 318 318 318 318 319 322 322 323 323 321 321 321 325 323 325 319 318 316] MW[18785 18800] MW0[1 3 4 0 3 4 4 3 1 4 3 6 10 0 5 1 3 1 5 3 3 1 5 3 2 2 2 4 2 2 3 2 4 9 1 5 3 4 2 5 6 6 4 4 6 7 2 2 4 2 3 3 3 4 5 5 5 4 7 1 3 6 5 5 4 4 2 2 0 3 1 3 1 3 6 4 2 0 10 2 5 5 2 2 5 2 6 2 8 6 6 3 8 6 7 6 3 3 7 3 6 4 4 4 2 3 4 0 3 1 4 0 4 5 5 5 5 2 3 4] MW1[5 4 2 4 3 2 1 3 6 4 5 3 3 3 2 4 7 3 4 5 3 4 4 2 3 6 6 2 3 3 4 4 3 4 1 2 7 1 5 5 4 2 5 1 4 3 4 6 3 4 8 3 3 2 5 3 2 2 5 3 1 8 2 5 7 2 4 3 7 2 4 1 6 2 4 3 4 4 4 1 1 4 7 2 3 1 3 5 8 2 3 3 8 2 6 5 8 1 7 3 3 8 5 2 3 4 7 1 9 2 3 2 4 10 3 3 5 3 2 4] CRC[1 0] POW_I2C[FAILED] FACOPTS0[] FACOPTS1[] ATAOPTS0[--avalon10-freq 562:587:612:637 --avalon10-voltage-level 36 ] ATAOPTS1[--avalon10-freq 637:662:687:712 --avalon10-voltage-level 56 ] ADJ[1] MPO[2220] MVL[71] ATABD0[612 637 662 687] ATABD1[637 662 687 712] WORKMODE[1]\",\"MM Count\":1,\"Smart Speed\":1,\"Voltage Level Offset\":0,\"Nonce Mask\":25},{\"STATS\":1,\"ID\":\"POOL0\",\"Elapsed\":155,\"Calls\":0,\"Wait\":0.000000,\"Max\":0.000000,\"Min\":99999999.000000,\"Pool Calls\":0,\"Pool Attempts\":0,\"Pool Wait\":0.000000,\"Pool Max\":0.000000,\"Pool Min\":99999999.000000,\"Pool Av\":0.000000,\"Work Had Roll Time\":false,\"Work Can Roll\":false,\"Work Had Expire\":false,\"Work Roll Time\":0,\"Work Diff\":132381.93585300,\"Min Diff\":132381.93585300,\"Max Diff\":132381.93585300,\"Min Diff Count\":890,\"Max Diff Count\":890,\"Times Sent\":8,\"Bytes Sent\":1014,\"Times Recv\":26,\"Bytes Recv\":13164,\"Net Bytes Sent\":1014,\"Net Bytes Recv\":13164},{\"STATS\":2,\"ID\":\"POOL1\",\"Elapsed\":155,\"Calls\":0,\"Wait\":0.000000,\"Max\":0.000000,\"Min\":99999999.000000,\"Pool Calls\":0,\"Pool Attempts\":0,\"Pool Wait\":0.000000,\"Pool Max\":0.000000,\"Pool Min\":99999999.000000,\"Pool Av\":0.000000,\"Work Had Roll Time\":false,\"Work Can Roll\":false,\"Work Had Expire\":false,\"Work Roll Time\":0,\"Work Diff\":0.00000000,\"Min Diff\":0.00000000,\"Max Diff\":0.00000000,\"Min Diff Count\":0,\"Max Diff Count\":0,\"Times Sent\":4,\"Bytes Sent\":332,\"Times Recv\":5,\"Bytes Recv\":1553,\"Net Bytes Sent\":332,\"Net Bytes Recv\":1553},{\"STATS\":3,\"ID\":\"POOL2\",\"Elapsed\":155,\"Calls\":0,\"Wait\":0.000000,\"Max\":0.000000,\"Min\":99999999.000000,\"Pool Calls\":0,\"Pool Attempts\":0,\"Pool Wait\":0.000000,\"Pool Max\":0.000000,\"Pool Min\":99999999.000000,\"Pool Av\":0.000000,\"Work Had Roll Time\":false,\"Work Can Roll\":false,\"Work Had Expire\":false,\"Work Roll Time\":0,\"Work Diff\":132381.93585300,\"Min Diff\":132381.93585300,\"Max Diff\":132381.93585300,\"Min Diff Count\":2,\"Max Diff Count\":2,\"Times Sent\":4,\"Bytes Sent\":354,\"Times Recv\":11,\"Bytes Recv\":3704,\"Net Bytes Sent\":354,\"Net Bytes Recv\":3704}],\"id\":1}],\"pools\":[{\"STATUS\":[{\"STATUS\":\"S\",\"When\":168,\"Code\":7,\"Msg\":\"3 Pool(s)\",\"Description\":\"cgminer 4.11.1\"}],\"POOLS\":[{\"POOL\":0,\"URL\":\"stratum+tcp://sha256.mining-dutch.nl:9996\",\"Status\":\"Alive\",\"Priority\":0,\"Quota\":1,\"Long Poll\":\"N\",\"Getworks\":10,\"Accepted\":5,\"Rejected\":0,\"Works\":896,\"Discarded\":0,\"Stale\":0,\"Get Failures\":0,\"Remote Failures\":0,\"User\":\"xxx\",\"Last Share Time\":98,\"Diff1 Shares\":917504,\"Proxy Type\":\"\",\"Proxy\":\"\",\"Difficulty Accepted\":661909.67926500,\"Difficulty Rejected\":0.00000000,\"Difficulty Stale\":0.00000000,\"Last Share Difficulty\":132381.93585300,\"Work Difficulty\":132381.93585300,\"Has Stratum\":true,\"Stratum Active\":true,\"Stratum URL\":\"sha256asicboost.eu.nicehash.com\",\"Stratum Difficulty\":132381.93585300,\"Has Vmask\":true,\"Has GBT\":false,\"Best Share\":222408,\"Pool Rejected%\":0.0000,\"Pool Stale%\":0.0000,\"Bad Work\":3,\"Current Block Height\":654933,\"Current Block Version\":536870912},{\"POOL\":1,\"URL\":\"stratum+tcp://sha256.antpool.com:3333\",\"Status\":\"Alive\",\"Priority\":1,\"Quota\":1,\"Long Poll\":\"N\",\"Getworks\":1,\"Accepted\":0,\"Rejected\":0,\"Works\":0,\"Discarded\":0,\"Stale\":0,\"Get Failures\":0,\"Remote Failures\":0,\"User\":\"xxx\",\"Last Share Time\":0,\"Diff1 Shares\":0,\"Proxy Type\":\"\",\"Proxy\":\"\",\"Difficulty Accepted\":0.00000000,\"Difficulty Rejected\":0.00000000,\"Difficulty Stale\":0.00000000,\"Last Share Difficulty\":0.00000000,\"Work Difficulty\":0.00000000,\"Has Stratum\":true,\"Stratum Active\":false,\"Stratum URL\":\"\",\"Stratum Difficulty\":0.00000000,\"Has Vmask\":true,\"Has GBT\":false,\"Best Share\":0,\"Pool Rejected%\":0.0000,\"Pool Stale%\":0.0000,\"Bad Work\":0,\"Current Block Height\":0,\"Current Block Version\":536870912},{\"POOL\":2,\"URL\":\"stratum+tcp://sha256.mining-dutch.nl:9996\",\"Status\":\"Alive\",\"Priority\":2,\"Quota\":1,\"Long Poll\":\"N\",\"Getworks\":2,\"Accepted\":1,\"Rejected\":0,\"Works\":0,\"Discarded\":0,\"Stale\":0,\"Get Failures\":0,\"Remote Failures\":0,\"User\":\"xxx\",\"Last Share Time\":0,\"Diff1 Shares\":0,\"Proxy Type\":\"\",\"Proxy\":\"\",\"Difficulty Accepted\":132381.93585300,\"Difficulty Rejected\":0.00000000,\"Difficulty Stale\":0.00000000,\"Last Share Difficulty\":0.00000000,\"Work Difficulty\":132381.93585300,\"Has Stratum\":true,\"Stratum Active\":false,\"Stratum URL\":\"\",\"Stratum Difficulty\":0.00000000,\"Has Vmask\":true,\"Has GBT\":false,\"Best Share\":0,\"Pool Rejected%\":0.0000,\"Pool Stale%\":0.0000,\"Bad Work\":0,\"Current Block Height\":650502,\"Current Block Version\":536870912}],\"id\":1}],\"id\":1}")),
                                new MinerStats.Builder()
                                        .setApiIp("127.0.0.1")
                                        .setApiPort(4028)
                                        .addPool(
                                                new Pool.Builder()
                                                        .setName("sha256.mining-dutch.nl:9996")
                                                        .setPriority(0)
                                                        .setStatus(
                                                                true,
                                                                true)
                                                        .setCounts(
                                                                5,
                                                                0,
                                                                0)
                                                        .build())
                                        .addPool(
                                                new Pool.Builder()
                                                        .setName("sha256.antpool.com:3333")
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
                                                        .setName("sha256.mining-dutch.nl:9996")
                                                        .setPriority(2)
                                                        .setStatus(
                                                                true,
                                                                true)
                                                        .setCounts(
                                                                1,
                                                                0,
                                                                0)
                                                        .build())
                                        .addAsic(
                                                new Asic.Builder()
                                                        .setHashRate(new BigDecimal("25536751740000.000"))
                                                        .setFanInfo(
                                                                new FanInfo.Builder()
                                                                        .setCount(1)
                                                                        .addSpeed(31)
                                                                        .setSpeedUnits("%")
                                                                        .build())
                                                        .addTemp(18)
                                                        .addTemp(64)
                                                        .addRawStats(
                                                                ImmutableMap.of(
                                                                        "summary.0.summary.0.remote_failures",
                                                                        new BigDecimal("0"),
                                                                        "summary.0.summary.0.total_mh",
                                                                        new BigDecimal("3940649502")))
                                                        .build())
                                        .build()
                        }
                });
    }
}