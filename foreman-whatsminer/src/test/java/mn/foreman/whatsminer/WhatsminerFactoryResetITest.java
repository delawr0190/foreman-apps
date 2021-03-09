package mn.foreman.whatsminer;

import mn.foreman.util.AbstractAsyncActionITest;
import mn.foreman.util.rpc.FakeRpcMinerServer;
import mn.foreman.util.rpc.RpcHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;

/** Test factory resetting a Whatsminer. */
public class WhatsminerFactoryResetITest
        extends AbstractAsyncActionITest {

    /** The mapper for this class. */
    private static final ObjectMapper MAPPER = new ObjectMapper();

    /** Constructor. */
    public WhatsminerFactoryResetITest() {
        super(
                8080,
                4028,
                new WhatsminerFactoryResetStrategy(),
                Collections.singletonList(
                        () -> {
                            try {
                                return new FakeRpcMinerServer(
                                        4028,
                                        ImmutableMap.of(
                                                "{\"cmd\":\"get_token\"}",
                                                new RpcHandler(
                                                        "{\"STATUS\":\"S\",\"When\":1608417125,\"Code\":134,\"Msg\":{\"time\":\"6915\",\"salt\":\"BQ5hoXV9\",\"newsalt\":\"a5TtWui2\"},\"Description\":\"whatsminer v1.1\"}"),
                                                "{\"enc\":1,\"data\":\"0WVQDDRWGQiJWbt6DMM/aMdYeBcVaIAhjd2OQG7KdCJMgbCWJ/ULUk6ojNiLpcCbKrc2kbKZMCBtKQVn7CqCLg==\"}",
                                                new RpcHandler(
                                                        "{\"enc\":\"99J8w83zIqB3/n9c5GlK0Ms8rMLrtR4I9L3mL3ihmpoy4J0v+AHcKtijbyE92q22MIKC6VKEUcYL2KQS50euQWMqSBlxCPx8YYy4oRkOE4C7kjyvgs9rRMH/mhiVH9k+\"}"),
                                                "{\"cmd\":\"pools+summary+edevs\"}",
                                                new RpcHandler(
                                                        MAPPER.writeValueAsString(
                                                                ImmutableMap.of(
                                                                        "pools",
                                                                        Collections.singletonList(
                                                                                MAPPER.readValue(
                                                                                        "{\n" +
                                                                                                "      \"STATUS\": [\n" +
                                                                                                "        {\n" +
                                                                                                "          \"STATUS\": \"S\",\n" +
                                                                                                "          \"When\": 1615249622,\n" +
                                                                                                "          \"Code\": 7,\n" +
                                                                                                "          \"Msg\": \"1 Pool(s)\",\n" +
                                                                                                "          \"Description\": \"btminer\"\n" +
                                                                                                "        }\n" +
                                                                                                "      ],\n" +
                                                                                                "      \"POOLS\": [\n" +
                                                                                                "        {\n" +
                                                                                                "          \"POOL\": 1,\n" +
                                                                                                "          \"URL\": \"stratum+tcp://btc.luxor.tech:700\",\n" +
                                                                                                "          \"Status\": \"Alive\",\n" +
                                                                                                "          \"Priority\": 0,\n" +
                                                                                                "          \"Quota\": 1,\n" +
                                                                                                "          \"Long Poll\": \"N\",\n" +
                                                                                                "          \"Getworks\": 56850,\n" +
                                                                                                "          \"Accepted\": 457459,\n" +
                                                                                                "          \"Rejected\": 518,\n" +
                                                                                                "          \"Works\": 1868213477,\n" +
                                                                                                "          \"Discarded\": 0,\n" +
                                                                                                "          \"Stale\": 95,\n" +
                                                                                                "          \"Get Failures\": 7,\n" +
                                                                                                "          \"Remote Failures\": 1,\n" +
                                                                                                "          \"User\": \"xxx\",\n" +
                                                                                                "          \"Last Share Time\": 1615249620,\n" +
                                                                                                "          \"Diff1 Shares\": 0,\n" +
                                                                                                "          \"Proxy Type\": \"\",\n" +
                                                                                                "          \"Proxy\": \"\",\n" +
                                                                                                "          \"Difficulty Accepted\": 28279321096,\n" +
                                                                                                "          \"Difficulty Rejected\": 31537951,\n" +
                                                                                                "          \"Difficulty Stale\": 0,\n" +
                                                                                                "          \"Last Share Difficulty\": 54954,\n" +
                                                                                                "          \"Work Difficulty\": 0,\n" +
                                                                                                "          \"Has Stratum\": true,\n" +
                                                                                                "          \"Stratum Active\": true,\n" +
                                                                                                "          \"Stratum URL\": \"btc.luxor.tech\",\n" +
                                                                                                "          \"Stratum Difficulty\": 54954,\n" +
                                                                                                "          \"Has GBT\": false,\n" +
                                                                                                "          \"Best Share\": 54727919944,\n" +
                                                                                                "          \"Pool Rejected%\": 0.1114,\n" +
                                                                                                "          \"Pool Stale%\": 0,\n" +
                                                                                                "          \"Bad Work\": 3126,\n" +
                                                                                                "          \"Current Block Height\": 673786,\n" +
                                                                                                "          \"Current Block Version\": 536870912\n" +
                                                                                                "        }\n" +
                                                                                                "      ],\n" +
                                                                                                "      \"id\": 1\n" +
                                                                                                "    }",
                                                                                        Map.class)),
                                                                        "summary",
                                                                        Collections.singletonList(
                                                                                MAPPER.readValue(
                                                                                        "{\n" +
                                                                                                "      \"STATUS\": [\n" +
                                                                                                "        {\n" +
                                                                                                "          \"STATUS\": \"S\",\n" +
                                                                                                "          \"When\": 1615249622,\n" +
                                                                                                "          \"Code\": 11,\n" +
                                                                                                "          \"Msg\": \"Summary\",\n" +
                                                                                                "          \"Description\": \"btminer\"\n" +
                                                                                                "        }\n" +
                                                                                                "      ],\n" +
                                                                                                "      \"SUMMARY\": [\n" +
                                                                                                "        {\n" +
                                                                                                "          \"Elapsed\": 1216997,\n" +
                                                                                                "          \"MHS av\": 99793646.89,\n" +
                                                                                                "          \"MHS 5s\": 116472006.19,\n" +
                                                                                                "          \"MHS 1m\": 97878725.92,\n" +
                                                                                                "          \"MHS 5m\": 96877159.81,\n" +
                                                                                                "          \"MHS 15m\": 96805940.25,\n" +
                                                                                                "          \"HS RT\": 96417231.65,\n" +
                                                                                                "          \"Found Blocks\": 0,\n" +
                                                                                                "          \"Getworks\": 56850,\n" +
                                                                                                "          \"Accepted\": 457459,\n" +
                                                                                                "          \"Rejected\": 518,\n" +
                                                                                                "          \"Hardware Errors\": 0,\n" +
                                                                                                "          \"Utility\": 22.55,\n" +
                                                                                                "          \"Discarded\": 0,\n" +
                                                                                                "          \"Stale\": 95,\n" +
                                                                                                "          \"Get Failures\": 7,\n" +
                                                                                                "          \"Local Work\": 622329437,\n" +
                                                                                                "          \"Remote Failures\": 1,\n" +
                                                                                                "          \"Network Blocks\": 2090,\n" +
                                                                                                "          \"Total MH\": 121448583250369,\n" +
                                                                                                "          \"Work Utility\": 0,\n" +
                                                                                                "          \"Difficulty Accepted\": 28279321096,\n" +
                                                                                                "          \"Difficulty Rejected\": 31537951,\n" +
                                                                                                "          \"Difficulty Stale\": 0,\n" +
                                                                                                "          \"Best Share\": 54727919944,\n" +
                                                                                                "          \"Temperature\": 80,\n" +
                                                                                                "          \"freq_avg\": 438,\n" +
                                                                                                "          \"Fan Speed In\": 3870,\n" +
                                                                                                "          \"Fan Speed Out\": 3930,\n" +
                                                                                                "          \"Voltage\": 1288,\n" +
                                                                                                "          \"Power\": 3496,\n" +
                                                                                                "          \"Power_RT\": 3488,\n" +
                                                                                                "          \"Power Rate\": 35.03,\n" +
                                                                                                "          \"Device Hardware%\": 0,\n" +
                                                                                                "          \"Device Rejected%\": 0,\n" +
                                                                                                "          \"Pool Rejected%\": 0.1114,\n" +
                                                                                                "          \"Pool Stale%\": 0,\n" +
                                                                                                "          \"Last getwork\": 0,\n" +
                                                                                                "          \"Uptime\": 1224515,\n" +
                                                                                                "          \"Power Current\": 254.172494,\n" +
                                                                                                "          \"Power Fanspeed\": 8784,\n" +
                                                                                                "          \"Error Code 0\": 206,\n" +
                                                                                                "          \"Error 0 Time\": \"\",\n" +
                                                                                                "          \"Error Code 1\": 2320,\n" +
                                                                                                "          \"Error 1 Time\": \"\",\n" +
                                                                                                "          \"Factory Error Code 0\": 2340,\n" +
                                                                                                "          \"Error 2 Time\": \"\",\n" +
                                                                                                "          \"Error Code Count\": 2,\n" +
                                                                                                "          \"Factory Error Code Count\": 1,\n" +
                                                                                                "          \"Security Mode\": 0,\n" +
                                                                                                "          \"Liquid Cooling\": false,\n" +
                                                                                                "          \"Hash Stable\": true,\n" +
                                                                                                "          \"Hash Stable Cost Seconds\": 8718,\n" +
                                                                                                "          \"Hash Deviation%\": -2.1361,\n" +
                                                                                                "          \"Target Freq\": 437,\n" +
                                                                                                "          \"Target MHS\": 98089020,\n" +
                                                                                                "          \"Power Mode\": \"High\",\n" +
                                                                                                "          \"Firmware Version\": \"'20210109.22.REL'\",\n" +
                                                                                                "          \"CB Platform\": \"ALLWINNER_H6\",\n" +
                                                                                                "          \"CB Version\": \"V2\",\n" +
                                                                                                "          \"MAC\": \"C4:05:09:00:02:9B\",\n" +
                                                                                                "          \"Factory GHS\": 100999,\n" +
                                                                                                "          \"Power Limit\": 3600,\n" +
                                                                                                "          \"Power Voltage Input\": 199.25,\n" +
                                                                                                "          \"Power Current Input\": 17.53,\n" +
                                                                                                "          \"Chip Temp Min\": 43,\n" +
                                                                                                "          \"Chip Temp Max\": 94.95,\n" +
                                                                                                "          \"Chip Temp Avg\": 82.17,\n" +
                                                                                                "          \"Debug\": \"102.3/103.7T_34.5W_94.6/93.3_31\"\n" +
                                                                                                "        }\n" +
                                                                                                "      ],\n" +
                                                                                                "      \"id\": 1\n" +
                                                                                                "    }",
                                                                                        Map.class)),
                                                                        "edevs",
                                                                        Collections.singletonList(
                                                                                MAPPER.readValue(
                                                                                        "{\n" +
                                                                                                "      \"STATUS\": [\n" +
                                                                                                "        {\n" +
                                                                                                "          \"STATUS\": \"S\",\n" +
                                                                                                "          \"When\": 1615249622,\n" +
                                                                                                "          \"Code\": 9,\n" +
                                                                                                "          \"Msg\": \"3 ASC(s)\",\n" +
                                                                                                "          \"Description\": \"btminer\"\n" +
                                                                                                "        }\n" +
                                                                                                "      ],\n" +
                                                                                                "      \"DEVS\": [\n" +
                                                                                                "        {\n" +
                                                                                                "          \"ASC\": 0,\n" +
                                                                                                "          \"Name\": \"SM\",\n" +
                                                                                                "          \"ID\": 0,\n" +
                                                                                                "          \"Slot\": 0,\n" +
                                                                                                "          \"Enabled\": \"Y\",\n" +
                                                                                                "          \"Status\": \"Alive\",\n" +
                                                                                                "          \"Temperature\": 79.5,\n" +
                                                                                                "          \"Chip Frequency\": 414,\n" +
                                                                                                "          \"Fan Speed In\": 3870,\n" +
                                                                                                "          \"Fan Speed Out\": 3930,\n" +
                                                                                                "          \"MHS av\": 32682582.82,\n" +
                                                                                                "          \"MHS 5s\": 31120845.74,\n" +
                                                                                                "          \"MHS 1m\": 30322743.05,\n" +
                                                                                                "          \"MHS 5m\": 30595972.39,\n" +
                                                                                                "          \"MHS 15m\": 30608365.4,\n" +
                                                                                                "          \"Accepted\": 149860,\n" +
                                                                                                "          \"Rejected\": 165,\n" +
                                                                                                "          \"Hardware Errors\": 0,\n" +
                                                                                                "          \"Utility\": 7.39,\n" +
                                                                                                "          \"Last Share Pool\": 0,\n" +
                                                                                                "          \"Last Share Time\": 1615249612,\n" +
                                                                                                "          \"Total MH\": 39774630449612,\n" +
                                                                                                "          \"Diff1 Work\": 0,\n" +
                                                                                                "          \"Difficulty Accepted\": 9263607043,\n" +
                                                                                                "          \"Difficulty Rejected\": 10018230,\n" +
                                                                                                "          \"Last Share Difficulty\": 54954,\n" +
                                                                                                "          \"Last Valid Work\": 1615249621,\n" +
                                                                                                "          \"Device Hardware%\": 0,\n" +
                                                                                                "          \"Device Rejected%\": 0,\n" +
                                                                                                "          \"Device Elapsed\": 1216998,\n" +
                                                                                                "          \"Upfreq Complete\": 1,\n" +
                                                                                                "          \"Effective Chips\": 215,\n" +
                                                                                                "          \"PCB SN\": \"xxx\",\n" +
                                                                                                "          \"Chip Data\": \"HP2A02-20021812 BINV02-193002B\",\n" +
                                                                                                "          \"Chip Temp Min\": 63,\n" +
                                                                                                "          \"Chip Temp Max\": 94.95,\n" +
                                                                                                "          \"Chip Temp Avg\": 83.68,\n" +
                                                                                                "          \"chip_vol_diff\": 14\n" +
                                                                                                "        },\n" +
                                                                                                "        {\n" +
                                                                                                "          \"ASC\": 1,\n" +
                                                                                                "          \"Name\": \"SM\",\n" +
                                                                                                "          \"ID\": 1,\n" +
                                                                                                "          \"Slot\": 1,\n" +
                                                                                                "          \"Enabled\": \"Y\",\n" +
                                                                                                "          \"Status\": \"Alive\",\n" +
                                                                                                "          \"Temperature\": 80,\n" +
                                                                                                "          \"Chip Frequency\": 444,\n" +
                                                                                                "          \"Fan Speed In\": 3870,\n" +
                                                                                                "          \"Fan Speed Out\": 3930,\n" +
                                                                                                "          \"MHS av\": 33607194.59,\n" +
                                                                                                "          \"MHS 5s\": 33993448.66,\n" +
                                                                                                "          \"MHS 1m\": 32639168.66,\n" +
                                                                                                "          \"MHS 5m\": 32602309.25,\n" +
                                                                                                "          \"MHS 15m\": 32647354.85,\n" +
                                                                                                "          \"Accepted\": 154032,\n" +
                                                                                                "          \"Rejected\": 194,\n" +
                                                                                                "          \"Hardware Errors\": 0,\n" +
                                                                                                "          \"Utility\": 7.59,\n" +
                                                                                                "          \"Last Share Pool\": 0,\n" +
                                                                                                "          \"Last Share Time\": 1615249600,\n" +
                                                                                                "          \"Total MH\": 40899880920562,\n" +
                                                                                                "          \"Diff1 Work\": 0,\n" +
                                                                                                "          \"Difficulty Accepted\": 9520424805,\n" +
                                                                                                "          \"Difficulty Rejected\": 11733656,\n" +
                                                                                                "          \"Last Share Difficulty\": 54954,\n" +
                                                                                                "          \"Last Valid Work\": 1615249622,\n" +
                                                                                                "          \"Device Hardware%\": 0,\n" +
                                                                                                "          \"Device Rejected%\": 0,\n" +
                                                                                                "          \"Device Elapsed\": 1216998,\n" +
                                                                                                "          \"Upfreq Complete\": 1,\n" +
                                                                                                "          \"Effective Chips\": 215,\n" +
                                                                                                "          \"PCB SN\": \"xxx\",\n" +
                                                                                                "          \"Chip Data\": \"HP2A02-20021812 BINV02-193002B\",\n" +
                                                                                                "          \"Chip Temp Min\": 62,\n" +
                                                                                                "          \"Chip Temp Max\": 94.9,\n" +
                                                                                                "          \"Chip Temp Avg\": 81.74,\n" +
                                                                                                "          \"chip_vol_diff\": 13\n" +
                                                                                                "        },\n" +
                                                                                                "        {\n" +
                                                                                                "          \"ASC\": 2,\n" +
                                                                                                "          \"Name\": \"SM\",\n" +
                                                                                                "          \"ID\": 2,\n" +
                                                                                                "          \"Slot\": 2,\n" +
                                                                                                "          \"Enabled\": \"Y\",\n" +
                                                                                                "          \"Status\": \"Alive\",\n" +
                                                                                                "          \"Temperature\": 79.5,\n" +
                                                                                                "          \"Chip Frequency\": 455,\n" +
                                                                                                "          \"Fan Speed In\": 3870,\n" +
                                                                                                "          \"Fan Speed Out\": 3930,\n" +
                                                                                                "          \"MHS av\": 33503818.03,\n" +
                                                                                                "          \"MHS 5s\": 34677087.2,\n" +
                                                                                                "          \"MHS 1m\": 33440753.88,\n" +
                                                                                                "          \"MHS 5m\": 33372073.04,\n" +
                                                                                                "          \"MHS 15m\": 33427466.67,\n" +
                                                                                                "          \"Accepted\": 153560,\n" +
                                                                                                "          \"Rejected\": 159,\n" +
                                                                                                "          \"Hardware Errors\": 0,\n" +
                                                                                                "          \"Utility\": 7.57,\n" +
                                                                                                "          \"Last Share Pool\": 0,\n" +
                                                                                                "          \"Last Share Time\": 1615249620,\n" +
                                                                                                "          \"Total MH\": 40774071880195,\n" +
                                                                                                "          \"Diff1 Work\": 0,\n" +
                                                                                                "          \"Difficulty Accepted\": 9494995248,\n" +
                                                                                                "          \"Difficulty Rejected\": 9786065,\n" +
                                                                                                "          \"Last Share Difficulty\": 54954,\n" +
                                                                                                "          \"Last Valid Work\": 1615249622,\n" +
                                                                                                "          \"Device Hardware%\": 0,\n" +
                                                                                                "          \"Device Rejected%\": 0,\n" +
                                                                                                "          \"Device Elapsed\": 1216998,\n" +
                                                                                                "          \"Upfreq Complete\": 1,\n" +
                                                                                                "          \"Effective Chips\": 215,\n" +
                                                                                                "          \"PCB SN\": \"xxx\",\n" +
                                                                                                "          \"Chip Data\": \"HP2A02-20021812 BINV02-193002B\",\n" +
                                                                                                "          \"Chip Temp Min\": 43,\n" +
                                                                                                "          \"Chip Temp Max\": 92.9,\n" +
                                                                                                "          \"Chip Temp Avg\": 81.1,\n" +
                                                                                                "          \"chip_vol_diff\": 14\n" +
                                                                                                "        }\n" +
                                                                                                "      ],\n" +
                                                                                                "      \"id\": 1\n" +
                                                                                                "    }",
                                                                                        Map.class)))))));
                            } catch (final IOException ope) {
                                // Let it fail
                            }
                            return null;
                        }),
                new WhatsminerFactory(),
                Collections.emptyMap(),
                true);
    }
}