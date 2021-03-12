package mn.foreman.openminer;

import mn.foreman.util.AbstractAsyncActionITest;
import mn.foreman.util.http.FakeHttpMinerServer;
import mn.foreman.util.http.HttpHandler;

import com.google.common.collect.ImmutableMap;

import java.util.Collections;

/** Test rebooting an OpenMiner. */
public class BitfuryB8RebootITest
        extends AbstractAsyncActionITest {

    /** Constructor. */
    public BitfuryB8RebootITest() {
        super(
                8080,
                8080,
                new OpenMinerRebootAction(),
                Collections.singletonList(
                        () -> new FakeHttpMinerServer(
                                8080,
                                ImmutableMap.of(
                                        "/api/login",
                                        new HttpHandler(
                                                "{\"email\":\"my-auth-username\",\"password\":\"my-auth-password\"}",
                                                "{\"token\":\"eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJpYXQiOjE2MTU1MTI2OTIsImV4cCI6MTYxNTUxNjI5Miwicm9sZXMiOlsiUk9MRV9BRE1JTiJdLCJ1c2VybmFtZSI6InRlc3RAZXhhbXBsZS5jb20ifQ.W-0J1htmayQQ-FbDdmSi0Lejl1nKo-SjIMZ8Vu6GlaVSAW67mUb_5176ug7U38yhySDTcteu4YCsRwJcECdBDBwe9lS7VHBBXjuH4qUdpke34vyjD0wr04S1PNgOB7gYU7sYdahIoQZGIy8ISqORctQAtDDH2g_94ba9EYWJgkINx9t6H-BD2KNSEiP3gWDmB7zEdN7xMQIe93XMU6_Mc5BNS36Nd3nDAt2X3B37XFJ_cWTZuceEc1wYAbDmZszLJXkIykPrY83NSgzSMOOPY9vIpFnbfFM30RWJJ8A6-JFrhegFoWriY9fcsmUoIrkVWab03bcrn8rbXcOJbN6iHI3PSOIyH45StYHukMOhPikUG-TOATHG58-u0qxiBNw06hgZKV_ILxN0tCKleUKuztR2qTCEOIx5dJH5M58a0_nku3YEWnuOJ8LvVa_zHlfEJ1BLbw9SG0rBeLv02QYp9D6WtlUIfMY2IVWcQ6yk76gqCD0Sp7K1nDELTTZcpb-2CqbX3z4f8E-tv3BRffPjpZL35jx4k6ZAp4sW6JUso0a7RgFjjE7V898JPX7P8rHiQthCRkC2kfoGkfxuLGnWETXtlyP1Ac5QHUzH9-KqUc_zXLUqM8fhQ8cBpOkvN0qTTJUtyMv4frI6HfPKHsOidk0oDT2F1nl6G2Pf026m6GQ\"}"),
                                        "/api/om/miner/action",
                                        new HttpHandler(
                                                "{\"action\":\"reboot\",\"parameters\":{}}",
                                                "{\"action\":null,\"parameters\":[]}"),
                                        "/api/om/miner/stat/agg",
                                        new HttpHandler(
                                                "",
                                                ImmutableMap.of(
                                                        "Authorization",
                                                        "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJpYXQiOjE2MTU1MTI2OTIsImV4cCI6MTYxNTUxNjI5Miwicm9sZXMiOlsiUk9MRV9BRE1JTiJdLCJ1c2VybmFtZSI6InRlc3RAZXhhbXBsZS5jb20ifQ.W-0J1htmayQQ-FbDdmSi0Lejl1nKo-SjIMZ8Vu6GlaVSAW67mUb_5176ug7U38yhySDTcteu4YCsRwJcECdBDBwe9lS7VHBBXjuH4qUdpke34vyjD0wr04S1PNgOB7gYU7sYdahIoQZGIy8ISqORctQAtDDH2g_94ba9EYWJgkINx9t6H-BD2KNSEiP3gWDmB7zEdN7xMQIe93XMU6_Mc5BNS36Nd3nDAt2X3B37XFJ_cWTZuceEc1wYAbDmZszLJXkIykPrY83NSgzSMOOPY9vIpFnbfFM30RWJJ8A6-JFrhegFoWriY9fcsmUoIrkVWab03bcrn8rbXcOJbN6iHI3PSOIyH45StYHukMOhPikUG-TOATHG58-u0qxiBNw06hgZKV_ILxN0tCKleUKuztR2qTCEOIx5dJH5M58a0_nku3YEWnuOJ8LvVa_zHlfEJ1BLbw9SG0rBeLv02QYp9D6WtlUIfMY2IVWcQ6yk76gqCD0Sp7K1nDELTTZcpb-2CqbX3z4f8E-tv3BRffPjpZL35jx4k6ZAp4sW6JUso0a7RgFjjE7V898JPX7P8rHiQthCRkC2kfoGkfxuLGnWETXtlyP1Ac5QHUzH9-KqUc_zXLUqM8fhQ8cBpOkvN0qTTJUtyMv4frI6HfPKHsOidk0oDT2F1nl6G2Pf026m6GQ"),
                                                "{\n" +
                                                        "  \"pwr\": {\n" +
                                                        "    \"stateId\": 1,\n" +
                                                        "    \"stateMs\": 4019946,\n" +
                                                        "    \"noTx\": 0,\n" +
                                                        "    \"state\": \"FULL_PWER\",\n" +
                                                        "    \"setVoltage\": \"50.900000\",\n" +
                                                        "    \"powerOn\": 1\n" +
                                                        "  },\n" +
                                                        "  \"fansRpm\": [\n" +
                                                        "    5886,\n" +
                                                        "    5921,\n" +
                                                        "    5886,\n" +
                                                        "    5921,\n" +
                                                        "    6030,\n" +
                                                        "    5993\n" +
                                                        "  ],\n" +
                                                        "  \"mac\": \"16:bf:01:7f:aa:28\",\n" +
                                                        "  \"fan\": {\n" +
                                                        "    \"fanI\": \"13.596\",\n" +
                                                        "    \"fanU\": \"11.949\",\n" +
                                                        "    \"cfgU\": \"12.000\"\n" +
                                                        "  },\n" +
                                                        "  \"slavePingAvg\": 808,\n" +
                                                        "  \"date\": \"2021-03-11 23:57:09\",\n" +
                                                        "  \"slavePingMax\": 808,\n" +
                                                        "  \"slots\": {\n" +
                                                        "    \"0\": {\n" +
                                                        "      \"pwrNum\": 1,\n" +
                                                        "      \"spiLen\": 12,\n" +
                                                        "      \"isTempValid\": 1,\n" +
                                                        "      \"voltage\": 49200,\n" +
                                                        "      \"btcNum\": 8,\n" +
                                                        "      \"osc\": 48,\n" +
                                                        "      \"currents\": [\n" +
                                                        "        18320\n" +
                                                        "      ],\n" +
                                                        "      \"rfidUid\": \"xxx\",\n" +
                                                        "      \"overheatsTime\": 0,\n" +
                                                        "      \"errors\": \"580\",\n" +
                                                        "      \"temperature\": 59,\n" +
                                                        "      \"heaterErrNum\": 0,\n" +
                                                        "      \"isTemp1Valid\": 1,\n" +
                                                        "      \"lowCurrRst\": 0,\n" +
                                                        "      \"temperature1\": 57,\n" +
                                                        "      \"ocp\": 96,\n" +
                                                        "      \"heaterErr\": 0,\n" +
                                                        "      \"revision\": 41,\n" +
                                                        "      \"chipRestarts\": \"0\",\n" +
                                                        "      \"tmpRepair\": \"orig\",\n" +
                                                        "      \"rfidMem\": \"xxx\",\n" +
                                                        "      \"thermometers\": [\n" +
                                                        "        {\n" +
                                                        "          \"temp\": 59,\n" +
                                                        "          \"isValid\": 1,\n" +
                                                        "          \"alertHi\": 93,\n" +
                                                        "          \"numWrite\": 0,\n" +
                                                        "          \"location\": \"hs_hot\",\n" +
                                                        "          \"alertLo\": 93\n" +
                                                        "        },\n" +
                                                        "        {\n" +
                                                        "          \"temp\": 57,\n" +
                                                        "          \"isValid\": 1,\n" +
                                                        "          \"alertHi\": 93,\n" +
                                                        "          \"numWrite\": 0,\n" +
                                                        "          \"location\": \"hs_hot\",\n" +
                                                        "          \"alertLo\": 93\n" +
                                                        "        }\n" +
                                                        "      ],\n" +
                                                        "      \"tempNum\": 2,\n" +
                                                        "      \"rfidMemSt\": 1,\n" +
                                                        "      \"inOHOsc\": 0,\n" +
                                                        "      \"tmpAlert\": [\n" +
                                                        "        {\n" +
                                                        "          \"alertHi\": 93,\n" +
                                                        "          \"numWrite\": 0,\n" +
                                                        "          \"alertLo\": 93\n" +
                                                        "        },\n" +
                                                        "        {\n" +
                                                        "          \"alertHi\": 93,\n" +
                                                        "          \"numWrite\": 0,\n" +
                                                        "          \"alertLo\": 93\n" +
                                                        "        }\n" +
                                                        "      ],\n" +
                                                        "      \"chips\": 96,\n" +
                                                        "      \"specVoltage\": 48,\n" +
                                                        "      \"revAdc\": 520,\n" +
                                                        "      \"pwrLen\": 12,\n" +
                                                        "      \"ohOscTime\": 0,\n" +
                                                        "      \"pwrOn\": 1,\n" +
                                                        "      \"ghs\": \"8042.8\",\n" +
                                                        "      \"pwrOnTarget\": 1,\n" +
                                                        "      \"offReason\": \"n/a\",\n" +
                                                        "      \"wattPerGHs\": \"0.112025\",\n" +
                                                        "      \"oscStopChip\": \"N/A\",\n" +
                                                        "      \"brokenPwc\": 0,\n" +
                                                        "      \"errorRate\": \"1.0\",\n" +
                                                        "      \"ohOscNum\": 0,\n" +
                                                        "      \"spiNum\": 1,\n" +
                                                        "      \"overheats\": 0,\n" +
                                                        "      \"solutions\": \"56178\"\n" +
                                                        "    },\n" +
                                                        "    \"1\": {\n" +
                                                        "      \"pwrNum\": 1,\n" +
                                                        "      \"spiLen\": 12,\n" +
                                                        "      \"isTempValid\": 1,\n" +
                                                        "      \"voltage\": 49488,\n" +
                                                        "      \"btcNum\": 8,\n" +
                                                        "      \"osc\": 48,\n" +
                                                        "      \"currents\": [\n" +
                                                        "        17900\n" +
                                                        "      ],\n" +
                                                        "      \"rfidUid\": \"xxx\",\n" +
                                                        "      \"overheatsTime\": 0,\n" +
                                                        "      \"errors\": \"12374\",\n" +
                                                        "      \"temperature\": 57,\n" +
                                                        "      \"heaterErrNum\": 0,\n" +
                                                        "      \"isTemp1Valid\": 1,\n" +
                                                        "      \"lowCurrRst\": 0,\n" +
                                                        "      \"temperature1\": 55,\n" +
                                                        "      \"ocp\": 96,\n" +
                                                        "      \"heaterErr\": 0,\n" +
                                                        "      \"revision\": 41,\n" +
                                                        "      \"chipRestarts\": \"18\",\n" +
                                                        "      \"tmpRepair\": \"orig\",\n" +
                                                        "      \"rfidMem\": \"xxx\",\n" +
                                                        "      \"thermometers\": [\n" +
                                                        "        {\n" +
                                                        "          \"temp\": 57,\n" +
                                                        "          \"isValid\": 1,\n" +
                                                        "          \"alertHi\": 93,\n" +
                                                        "          \"numWrite\": 0,\n" +
                                                        "          \"location\": \"hs_hot\",\n" +
                                                        "          \"alertLo\": 93\n" +
                                                        "        },\n" +
                                                        "        {\n" +
                                                        "          \"temp\": 55,\n" +
                                                        "          \"isValid\": 1,\n" +
                                                        "          \"alertHi\": 93,\n" +
                                                        "          \"numWrite\": 0,\n" +
                                                        "          \"location\": \"hs_hot\",\n" +
                                                        "          \"alertLo\": 93\n" +
                                                        "        }\n" +
                                                        "      ],\n" +
                                                        "      \"tempNum\": 2,\n" +
                                                        "      \"rfidMemSt\": 1,\n" +
                                                        "      \"inOHOsc\": 0,\n" +
                                                        "      \"tmpAlert\": [\n" +
                                                        "        {\n" +
                                                        "          \"alertHi\": 93,\n" +
                                                        "          \"numWrite\": 0,\n" +
                                                        "          \"alertLo\": 93\n" +
                                                        "        },\n" +
                                                        "        {\n" +
                                                        "          \"alertHi\": 93,\n" +
                                                        "          \"numWrite\": 0,\n" +
                                                        "          \"alertLo\": 93\n" +
                                                        "        }\n" +
                                                        "      ],\n" +
                                                        "      \"chips\": 96,\n" +
                                                        "      \"specVoltage\": 48,\n" +
                                                        "      \"revAdc\": 525,\n" +
                                                        "      \"pwrLen\": 12,\n" +
                                                        "      \"ohOscTime\": 0,\n" +
                                                        "      \"pwrOn\": 1,\n" +
                                                        "      \"ghs\": \"7804.2\",\n" +
                                                        "      \"pwrOnTarget\": 1,\n" +
                                                        "      \"offReason\": \"n/a\",\n" +
                                                        "      \"wattPerGHs\": \"0.113401\",\n" +
                                                        "      \"oscStopChip\": \"N/A\",\n" +
                                                        "      \"brokenPwc\": 0,\n" +
                                                        "      \"errorRate\": \"18.5\",\n" +
                                                        "      \"ohOscNum\": 0,\n" +
                                                        "      \"spiNum\": 1,\n" +
                                                        "      \"overheats\": 0,\n" +
                                                        "      \"solutions\": \"54511\"\n" +
                                                        "    },\n" +
                                                        "    \"2\": {\n" +
                                                        "      \"pwrNum\": 1,\n" +
                                                        "      \"spiLen\": 12,\n" +
                                                        "      \"isTempValid\": 1,\n" +
                                                        "      \"voltage\": 49392,\n" +
                                                        "      \"btcNum\": 8,\n" +
                                                        "      \"osc\": 48,\n" +
                                                        "      \"currents\": [\n" +
                                                        "        17780\n" +
                                                        "      ],\n" +
                                                        "      \"rfidUid\": \"xxx\",\n" +
                                                        "      \"overheatsTime\": 0,\n" +
                                                        "      \"errors\": \"680\",\n" +
                                                        "      \"temperature\": 57,\n" +
                                                        "      \"heaterErrNum\": 0,\n" +
                                                        "      \"isTemp1Valid\": 1,\n" +
                                                        "      \"lowCurrRst\": 0,\n" +
                                                        "      \"temperature1\": 55,\n" +
                                                        "      \"ocp\": 96,\n" +
                                                        "      \"heaterErr\": 0,\n" +
                                                        "      \"revision\": 41,\n" +
                                                        "      \"chipRestarts\": \"60\",\n" +
                                                        "      \"tmpRepair\": \"orig\",\n" +
                                                        "      \"rfidMem\": \"xxx\",\n" +
                                                        "      \"thermometers\": [\n" +
                                                        "        {\n" +
                                                        "          \"temp\": 57,\n" +
                                                        "          \"isValid\": 1,\n" +
                                                        "          \"alertHi\": 93,\n" +
                                                        "          \"numWrite\": 0,\n" +
                                                        "          \"location\": \"hs_hot\",\n" +
                                                        "          \"alertLo\": 93\n" +
                                                        "        },\n" +
                                                        "        {\n" +
                                                        "          \"temp\": 55,\n" +
                                                        "          \"isValid\": 1,\n" +
                                                        "          \"alertHi\": 93,\n" +
                                                        "          \"numWrite\": 0,\n" +
                                                        "          \"location\": \"hs_hot\",\n" +
                                                        "          \"alertLo\": 93\n" +
                                                        "        }\n" +
                                                        "      ],\n" +
                                                        "      \"tempNum\": 2,\n" +
                                                        "      \"rfidMemSt\": 1,\n" +
                                                        "      \"inOHOsc\": 0,\n" +
                                                        "      \"tmpAlert\": [\n" +
                                                        "        {\n" +
                                                        "          \"alertHi\": 93,\n" +
                                                        "          \"numWrite\": 0,\n" +
                                                        "          \"alertLo\": 93\n" +
                                                        "        },\n" +
                                                        "        {\n" +
                                                        "          \"alertHi\": 93,\n" +
                                                        "          \"numWrite\": 0,\n" +
                                                        "          \"alertLo\": 93\n" +
                                                        "        }\n" +
                                                        "      ],\n" +
                                                        "      \"chips\": 96,\n" +
                                                        "      \"specVoltage\": 48,\n" +
                                                        "      \"revAdc\": 525,\n" +
                                                        "      \"pwrLen\": 12,\n" +
                                                        "      \"ohOscTime\": 0,\n" +
                                                        "      \"pwrOn\": 1,\n" +
                                                        "      \"ghs\": \"7744.2\",\n" +
                                                        "      \"pwrOnTarget\": 1,\n" +
                                                        "      \"offReason\": \"n/a\",\n" +
                                                        "      \"wattPerGHs\": \"0.113376\",\n" +
                                                        "      \"oscStopChip\": \"N/A\",\n" +
                                                        "      \"brokenPwc\": 0,\n" +
                                                        "      \"errorRate\": \"1.2\",\n" +
                                                        "      \"ohOscNum\": 0,\n" +
                                                        "      \"spiNum\": 1,\n" +
                                                        "      \"overheats\": 0,\n" +
                                                        "      \"solutions\": \"54092\"\n" +
                                                        "    },\n" +
                                                        "    \"3\": {\n" +
                                                        "      \"pwrNum\": 1,\n" +
                                                        "      \"spiLen\": 12,\n" +
                                                        "      \"isTempValid\": 1,\n" +
                                                        "      \"voltage\": 49296,\n" +
                                                        "      \"btcNum\": 8,\n" +
                                                        "      \"osc\": 48,\n" +
                                                        "      \"currents\": [\n" +
                                                        "        18310\n" +
                                                        "      ],\n" +
                                                        "      \"rfidUid\": \"xxx\",\n" +
                                                        "      \"overheatsTime\": 0,\n" +
                                                        "      \"errors\": \"610\",\n" +
                                                        "      \"temperature\": 58,\n" +
                                                        "      \"heaterErrNum\": 0,\n" +
                                                        "      \"isTemp1Valid\": 1,\n" +
                                                        "      \"lowCurrRst\": 0,\n" +
                                                        "      \"temperature1\": 55,\n" +
                                                        "      \"ocp\": 96,\n" +
                                                        "      \"heaterErr\": 0,\n" +
                                                        "      \"revision\": 41,\n" +
                                                        "      \"chipRestarts\": \"0\",\n" +
                                                        "      \"tmpRepair\": \"orig\",\n" +
                                                        "      \"rfidMem\": \"xxx\",\n" +
                                                        "      \"thermometers\": [\n" +
                                                        "        {\n" +
                                                        "          \"temp\": 58,\n" +
                                                        "          \"isValid\": 1,\n" +
                                                        "          \"alertHi\": 93,\n" +
                                                        "          \"numWrite\": 0,\n" +
                                                        "          \"location\": \"hs_hot\",\n" +
                                                        "          \"alertLo\": 93\n" +
                                                        "        },\n" +
                                                        "        {\n" +
                                                        "          \"temp\": 55,\n" +
                                                        "          \"isValid\": 1,\n" +
                                                        "          \"alertHi\": 93,\n" +
                                                        "          \"numWrite\": 0,\n" +
                                                        "          \"location\": \"hs_hot\",\n" +
                                                        "          \"alertLo\": 93\n" +
                                                        "        }\n" +
                                                        "      ],\n" +
                                                        "      \"tempNum\": 2,\n" +
                                                        "      \"rfidMemSt\": 1,\n" +
                                                        "      \"inOHOsc\": 0,\n" +
                                                        "      \"tmpAlert\": [\n" +
                                                        "        {\n" +
                                                        "          \"alertHi\": 93,\n" +
                                                        "          \"numWrite\": 0,\n" +
                                                        "          \"alertLo\": 93\n" +
                                                        "        },\n" +
                                                        "        {\n" +
                                                        "          \"alertHi\": 93,\n" +
                                                        "          \"numWrite\": 0,\n" +
                                                        "          \"alertLo\": 93\n" +
                                                        "        }\n" +
                                                        "      ],\n" +
                                                        "      \"chips\": 96,\n" +
                                                        "      \"specVoltage\": 48,\n" +
                                                        "      \"revAdc\": 525,\n" +
                                                        "      \"pwrLen\": 12,\n" +
                                                        "      \"ohOscTime\": 0,\n" +
                                                        "      \"pwrOn\": 1,\n" +
                                                        "      \"ghs\": \"8794.4\",\n" +
                                                        "      \"pwrOnTarget\": 1,\n" +
                                                        "      \"offReason\": \"n/a\",\n" +
                                                        "      \"wattPerGHs\": \"0.102565\",\n" +
                                                        "      \"oscStopChip\": \"N/A\",\n" +
                                                        "      \"brokenPwc\": 0,\n" +
                                                        "      \"errorRate\": \"1.0\",\n" +
                                                        "      \"ohOscNum\": 0,\n" +
                                                        "      \"spiNum\": 1,\n" +
                                                        "      \"overheats\": 0,\n" +
                                                        "      \"solutions\": \"61428\"\n" +
                                                        "    },\n" +
                                                        "    \"4\": {\n" +
                                                        "      \"pwrNum\": 1,\n" +
                                                        "      \"spiLen\": 12,\n" +
                                                        "      \"isTempValid\": 1,\n" +
                                                        "      \"voltage\": 49440,\n" +
                                                        "      \"btcNum\": 8,\n" +
                                                        "      \"osc\": 48,\n" +
                                                        "      \"currents\": [\n" +
                                                        "        17020\n" +
                                                        "      ],\n" +
                                                        "      \"rfidUid\": \"xxx\",\n" +
                                                        "      \"overheatsTime\": 0,\n" +
                                                        "      \"errors\": \"632\",\n" +
                                                        "      \"temperature\": 55,\n" +
                                                        "      \"heaterErrNum\": 0,\n" +
                                                        "      \"isTemp1Valid\": 1,\n" +
                                                        "      \"lowCurrRst\": 0,\n" +
                                                        "      \"temperature1\": 53,\n" +
                                                        "      \"ocp\": 96,\n" +
                                                        "      \"heaterErr\": 0,\n" +
                                                        "      \"revision\": 41,\n" +
                                                        "      \"chipRestarts\": \"87\",\n" +
                                                        "      \"tmpRepair\": \"orig\",\n" +
                                                        "      \"rfidMem\": \"xxx\",\n" +
                                                        "      \"thermometers\": [\n" +
                                                        "        {\n" +
                                                        "          \"temp\": 55,\n" +
                                                        "          \"isValid\": 1,\n" +
                                                        "          \"alertHi\": 93,\n" +
                                                        "          \"numWrite\": 0,\n" +
                                                        "          \"location\": \"hs_hot\",\n" +
                                                        "          \"alertLo\": 93\n" +
                                                        "        },\n" +
                                                        "        {\n" +
                                                        "          \"temp\": 53,\n" +
                                                        "          \"isValid\": 1,\n" +
                                                        "          \"alertHi\": 93,\n" +
                                                        "          \"numWrite\": 0,\n" +
                                                        "          \"location\": \"hs_hot\",\n" +
                                                        "          \"alertLo\": 93\n" +
                                                        "        }\n" +
                                                        "      ],\n" +
                                                        "      \"tempNum\": 2,\n" +
                                                        "      \"rfidMemSt\": 1,\n" +
                                                        "      \"inOHOsc\": 0,\n" +
                                                        "      \"tmpAlert\": [\n" +
                                                        "        {\n" +
                                                        "          \"alertHi\": 93,\n" +
                                                        "          \"numWrite\": 0,\n" +
                                                        "          \"alertLo\": 93\n" +
                                                        "        },\n" +
                                                        "        {\n" +
                                                        "          \"alertHi\": 93,\n" +
                                                        "          \"numWrite\": 0,\n" +
                                                        "          \"alertLo\": 93\n" +
                                                        "        }\n" +
                                                        "      ],\n" +
                                                        "      \"chips\": 96,\n" +
                                                        "      \"specVoltage\": 48,\n" +
                                                        "      \"revAdc\": 524,\n" +
                                                        "      \"pwrLen\": 12,\n" +
                                                        "      \"ohOscTime\": 0,\n" +
                                                        "      \"pwrOn\": 1,\n" +
                                                        "      \"ghs\": \"7597.3\",\n" +
                                                        "      \"pwrOnTarget\": 1,\n" +
                                                        "      \"offReason\": \"n/a\",\n" +
                                                        "      \"wattPerGHs\": \"0.110697\",\n" +
                                                        "      \"oscStopChip\": \"N/A\",\n" +
                                                        "      \"brokenPwc\": 0,\n" +
                                                        "      \"errorRate\": \"1.2\",\n" +
                                                        "      \"ohOscNum\": 0,\n" +
                                                        "      \"spiNum\": 1,\n" +
                                                        "      \"overheats\": 0,\n" +
                                                        "      \"solutions\": \"53066\"\n" +
                                                        "    },\n" +
                                                        "    \"5\": {\n" +
                                                        "      \"pwrNum\": 1,\n" +
                                                        "      \"spiLen\": 12,\n" +
                                                        "      \"isTempValid\": 1,\n" +
                                                        "      \"voltage\": 49232,\n" +
                                                        "      \"btcNum\": 8,\n" +
                                                        "      \"osc\": 48,\n" +
                                                        "      \"currents\": [\n" +
                                                        "        17730\n" +
                                                        "      ],\n" +
                                                        "      \"rfidUid\": \"xxx\",\n" +
                                                        "      \"overheatsTime\": 0,\n" +
                                                        "      \"errors\": \"679\",\n" +
                                                        "      \"temperature\": 56,\n" +
                                                        "      \"heaterErrNum\": 0,\n" +
                                                        "      \"isTemp1Valid\": 1,\n" +
                                                        "      \"lowCurrRst\": 0,\n" +
                                                        "      \"temperature1\": 54,\n" +
                                                        "      \"ocp\": 96,\n" +
                                                        "      \"heaterErr\": 0,\n" +
                                                        "      \"revision\": 41,\n" +
                                                        "      \"chipRestarts\": \"9\",\n" +
                                                        "      \"tmpRepair\": \"orig\",\n" +
                                                        "      \"rfidMem\": \"xxx\",\n" +
                                                        "      \"thermometers\": [\n" +
                                                        "        {\n" +
                                                        "          \"temp\": 56,\n" +
                                                        "          \"isValid\": 1,\n" +
                                                        "          \"alertHi\": 93,\n" +
                                                        "          \"numWrite\": 0,\n" +
                                                        "          \"location\": \"hs_hot\",\n" +
                                                        "          \"alertLo\": 93\n" +
                                                        "        },\n" +
                                                        "        {\n" +
                                                        "          \"temp\": 54,\n" +
                                                        "          \"isValid\": 1,\n" +
                                                        "          \"alertHi\": 93,\n" +
                                                        "          \"numWrite\": 0,\n" +
                                                        "          \"location\": \"hs_hot\",\n" +
                                                        "          \"alertLo\": 93\n" +
                                                        "        }\n" +
                                                        "      ],\n" +
                                                        "      \"tempNum\": 2,\n" +
                                                        "      \"rfidMemSt\": 1,\n" +
                                                        "      \"inOHOsc\": 0,\n" +
                                                        "      \"tmpAlert\": [\n" +
                                                        "        {\n" +
                                                        "          \"alertHi\": 93,\n" +
                                                        "          \"numWrite\": 0,\n" +
                                                        "          \"alertLo\": 93\n" +
                                                        "        },\n" +
                                                        "        {\n" +
                                                        "          \"alertHi\": 93,\n" +
                                                        "          \"numWrite\": 0,\n" +
                                                        "          \"alertLo\": 93\n" +
                                                        "        }\n" +
                                                        "      ],\n" +
                                                        "      \"chips\": 96,\n" +
                                                        "      \"specVoltage\": 48,\n" +
                                                        "      \"revAdc\": 524,\n" +
                                                        "      \"pwrLen\": 12,\n" +
                                                        "      \"ohOscTime\": 0,\n" +
                                                        "      \"pwrOn\": 1,\n" +
                                                        "      \"ghs\": \"8462.6\",\n" +
                                                        "      \"pwrOnTarget\": 1,\n" +
                                                        "      \"offReason\": \"n/a\",\n" +
                                                        "      \"wattPerGHs\": \"0.103042\",\n" +
                                                        "      \"oscStopChip\": \"N/A\",\n" +
                                                        "      \"brokenPwc\": 0,\n" +
                                                        "      \"errorRate\": \"1.1\",\n" +
                                                        "      \"ohOscNum\": 0,\n" +
                                                        "      \"spiNum\": 1,\n" +
                                                        "      \"overheats\": 0,\n" +
                                                        "      \"solutions\": \"59110\"\n" +
                                                        "    }\n" +
                                                        "  },\n" +
                                                        "  \"pool\": {\n" +
                                                        "    \"userName\": \"xx.yy\",\n" +
                                                        "    \"diff\": 51717,\n" +
                                                        "    \"host\": \"ca.stratum.slushpool.com\",\n" +
                                                        "    \"intervals\": {\n" +
                                                        "      \"0\": {\n" +
                                                        "        \"reconnectionsOnErrors\": 0,\n" +
                                                        "        \"minRespTime\": \"102\",\n" +
                                                        "        \"sharesAccepted\": 896,\n" +
                                                        "        \"statOverflow\": 0,\n" +
                                                        "        \"reconnections\": 0,\n" +
                                                        "        \"lowDifficultyShares\": 0,\n" +
                                                        "        \"inService\": 4023,\n" +
                                                        "        \"pwcSharesDropped\": 0,\n" +
                                                        "        \"defaultJobShares\": 0,\n" +
                                                        "        \"solutionsAccepted\": \"47294996\",\n" +
                                                        "        \"sharesRejected\": 0,\n" +
                                                        "        \"maxRespTime\": \"16657\",\n" +
                                                        "        \"pwcRestart\": 0,\n" +
                                                        "        \"staleJobShares\": 1,\n" +
                                                        "        \"jobs\": 141,\n" +
                                                        "        \"poolTotal\": 4023,\n" +
                                                        "        \"avgRespTime\": \"255\",\n" +
                                                        "        \"shareLoss\": \"0.000000\",\n" +
                                                        "        \"subscribeError\": 0,\n" +
                                                        "        \"name\": \"total\",\n" +
                                                        "        \"duplicateShares\": 0,\n" +
                                                        "        \"cleanFlags\": 7,\n" +
                                                        "        \"interval\": 4029,\n" +
                                                        "        \"pwcSharesSent\": 896,\n" +
                                                        "        \"sharesSent\": 896,\n" +
                                                        "        \"diffChanges\": 4\n" +
                                                        "      },\n" +
                                                        "      \"30\": {\n" +
                                                        "        \"reconnectionsOnErrors\": 0,\n" +
                                                        "        \"minRespTime\": \"107\",\n" +
                                                        "        \"sharesAccepted\": 12,\n" +
                                                        "        \"statOverflow\": 0,\n" +
                                                        "        \"reconnections\": 0,\n" +
                                                        "        \"lowDifficultyShares\": 0,\n" +
                                                        "        \"inService\": 30,\n" +
                                                        "        \"pwcSharesDropped\": 0,\n" +
                                                        "        \"defaultJobShares\": 0,\n" +
                                                        "        \"solutionsAccepted\": \"620604\",\n" +
                                                        "        \"sharesRejected\": 0,\n" +
                                                        "        \"maxRespTime\": \"125\",\n" +
                                                        "        \"pwcRestart\": 0,\n" +
                                                        "        \"staleJobShares\": 0,\n" +
                                                        "        \"jobs\": 1,\n" +
                                                        "        \"poolTotal\": 30,\n" +
                                                        "        \"avgRespTime\": \"114\",\n" +
                                                        "        \"shareLoss\": \"0.000000\",\n" +
                                                        "        \"subscribeError\": 0,\n" +
                                                        "        \"name\": \"30 sec\",\n" +
                                                        "        \"duplicateShares\": 0,\n" +
                                                        "        \"cleanFlags\": 0,\n" +
                                                        "        \"interval\": 30,\n" +
                                                        "        \"pwcSharesSent\": 12,\n" +
                                                        "        \"sharesSent\": 12,\n" +
                                                        "        \"diffChanges\": 0\n" +
                                                        "      },\n" +
                                                        "      \"300\": {\n" +
                                                        "        \"reconnectionsOnErrors\": 0,\n" +
                                                        "        \"minRespTime\": \"102\",\n" +
                                                        "        \"sharesAccepted\": 72,\n" +
                                                        "        \"statOverflow\": 0,\n" +
                                                        "        \"reconnections\": 0,\n" +
                                                        "        \"lowDifficultyShares\": 0,\n" +
                                                        "        \"inService\": 300,\n" +
                                                        "        \"pwcSharesDropped\": 0,\n" +
                                                        "        \"defaultJobShares\": 0,\n" +
                                                        "        \"solutionsAccepted\": \"3723624\",\n" +
                                                        "        \"sharesRejected\": 0,\n" +
                                                        "        \"maxRespTime\": \"131\",\n" +
                                                        "        \"pwcRestart\": 0,\n" +
                                                        "        \"staleJobShares\": 0,\n" +
                                                        "        \"jobs\": 10,\n" +
                                                        "        \"poolTotal\": 300,\n" +
                                                        "        \"avgRespTime\": \"113\",\n" +
                                                        "        \"shareLoss\": \"0.000000\",\n" +
                                                        "        \"subscribeError\": 0,\n" +
                                                        "        \"name\": \"5 min\",\n" +
                                                        "        \"duplicateShares\": 0,\n" +
                                                        "        \"cleanFlags\": 0,\n" +
                                                        "        \"interval\": 300,\n" +
                                                        "        \"pwcSharesSent\": 71,\n" +
                                                        "        \"sharesSent\": 72,\n" +
                                                        "        \"diffChanges\": 0\n" +
                                                        "      },\n" +
                                                        "      \"900\": {\n" +
                                                        "        \"reconnectionsOnErrors\": 0,\n" +
                                                        "        \"minRespTime\": \"102\",\n" +
                                                        "        \"sharesAccepted\": 209,\n" +
                                                        "        \"statOverflow\": 0,\n" +
                                                        "        \"reconnections\": 0,\n" +
                                                        "        \"lowDifficultyShares\": 0,\n" +
                                                        "        \"inService\": 900,\n" +
                                                        "        \"pwcSharesDropped\": 0,\n" +
                                                        "        \"defaultJobShares\": 0,\n" +
                                                        "        \"solutionsAccepted\": \"10808853\",\n" +
                                                        "        \"sharesRejected\": 0,\n" +
                                                        "        \"maxRespTime\": \"169\",\n" +
                                                        "        \"pwcRestart\": 0,\n" +
                                                        "        \"staleJobShares\": 0,\n" +
                                                        "        \"jobs\": 30,\n" +
                                                        "        \"poolTotal\": 900,\n" +
                                                        "        \"avgRespTime\": \"116\",\n" +
                                                        "        \"shareLoss\": \"0.000000\",\n" +
                                                        "        \"subscribeError\": 0,\n" +
                                                        "        \"name\": \"15 min\",\n" +
                                                        "        \"duplicateShares\": 0,\n" +
                                                        "        \"cleanFlags\": 0,\n" +
                                                        "        \"interval\": 900,\n" +
                                                        "        \"pwcSharesSent\": 208,\n" +
                                                        "        \"sharesSent\": 209,\n" +
                                                        "        \"diffChanges\": 0\n" +
                                                        "      },\n" +
                                                        "      \"3600\": {\n" +
                                                        "        \"reconnectionsOnErrors\": 0,\n" +
                                                        "        \"minRespTime\": \"102\",\n" +
                                                        "        \"sharesAccepted\": 748,\n" +
                                                        "        \"statOverflow\": 0,\n" +
                                                        "        \"reconnections\": 0,\n" +
                                                        "        \"lowDifficultyShares\": 0,\n" +
                                                        "        \"inService\": 3603,\n" +
                                                        "        \"pwcSharesDropped\": 0,\n" +
                                                        "        \"defaultJobShares\": 0,\n" +
                                                        "        \"solutionsAccepted\": \"42529256\",\n" +
                                                        "        \"sharesRejected\": 0,\n" +
                                                        "        \"maxRespTime\": \"16657\",\n" +
                                                        "        \"pwcRestart\": 0,\n" +
                                                        "        \"staleJobShares\": 0,\n" +
                                                        "        \"jobs\": 126,\n" +
                                                        "        \"poolTotal\": 3603,\n" +
                                                        "        \"avgRespTime\": \"282\",\n" +
                                                        "        \"shareLoss\": \"0.000000\",\n" +
                                                        "        \"subscribeError\": 0,\n" +
                                                        "        \"name\": \"1 hour\",\n" +
                                                        "        \"duplicateShares\": 0,\n" +
                                                        "        \"cleanFlags\": 4,\n" +
                                                        "        \"interval\": 3603,\n" +
                                                        "        \"pwcSharesSent\": 749,\n" +
                                                        "        \"sharesSent\": 748,\n" +
                                                        "        \"diffChanges\": 1\n" +
                                                        "      }\n" +
                                                        "    },\n" +
                                                        "    \"port\": \"3333\"\n" +
                                                        "  },\n" +
                                                        "  \"psu\": [\n" +
                                                        "    {\n" +
                                                        "      \"ledR\": 0,\n" +
                                                        "      \"defV\": \"45.0\",\n" +
                                                        "      \"vOut\": \"49.0\",\n" +
                                                        "      \"pOut\": \"2692\",\n" +
                                                        "      \"ut\": 0,\n" +
                                                        "      \"iOut\": \"54.9\",\n" +
                                                        "      \"vIn\": \"191.0\",\n" +
                                                        "      \"fsr\": 100,\n" +
                                                        "      \"lstStT\": 4027211,\n" +
                                                        "      \"fs\": 98,\n" +
                                                        "      \"ledY\": 1,\n" +
                                                        "      \"tIn\": 61,\n" +
                                                        "      \"minAlarm\": 128,\n" +
                                                        "      \"cond\": 2,\n" +
                                                        "      \"ledG\": 1,\n" +
                                                        "      \"numSt\": 4134,\n" +
                                                        "      \"tOut\": 37,\n" +
                                                        "      \"serial\": \"xxx\",\n" +
                                                        "      \"prod\": {\n" +
                                                        "        \"date\": \"2018-05-19\",\n" +
                                                        "        \"part\": \"xx.xx\",\n" +
                                                        "        \"ver\": \"1\",\n" +
                                                        "        \"desc\": \"xx 48/3000 HE\"\n" +
                                                        "      },\n" +
                                                        "      \"id\": 1,\n" +
                                                        "      \"majAlarm\": 0\n" +
                                                        "    },\n" +
                                                        "    {\n" +
                                                        "      \"ledR\": 0,\n" +
                                                        "      \"defV\": \"45.0\",\n" +
                                                        "      \"vOut\": \"49.1\",\n" +
                                                        "      \"pOut\": \"2729\",\n" +
                                                        "      \"ut\": 0,\n" +
                                                        "      \"iOut\": \"55.6\",\n" +
                                                        "      \"vIn\": \"191.0\",\n" +
                                                        "      \"fsr\": 100,\n" +
                                                        "      \"lstStT\": 4026671,\n" +
                                                        "      \"fs\": 98,\n" +
                                                        "      \"ledY\": 1,\n" +
                                                        "      \"tIn\": 60,\n" +
                                                        "      \"minAlarm\": 128,\n" +
                                                        "      \"cond\": 2,\n" +
                                                        "      \"ledG\": 1,\n" +
                                                        "      \"numSt\": 4133,\n" +
                                                        "      \"tOut\": 36,\n" +
                                                        "      \"serial\": \"xx\",\n" +
                                                        "      \"prod\": {\n" +
                                                        "        \"date\": \"2018-05-17\",\n" +
                                                        "        \"part\": \"xx.xx\",\n" +
                                                        "        \"ver\": \"1\",\n" +
                                                        "        \"desc\": \"xx 48/3000 HE\"\n" +
                                                        "      },\n" +
                                                        "      \"id\": 2,\n" +
                                                        "      \"majAlarm\": 0\n" +
                                                        "    }\n" +
                                                        "  ],\n" +
                                                        "  \"temperature\": {\n" +
                                                        "    \"count\": 12,\n" +
                                                        "    \"max\": 59,\n" +
                                                        "    \"cold\": 0,\n" +
                                                        "    \"avr\": 55,\n" +
                                                        "    \"min\": 53\n" +
                                                        "  },\n" +
                                                        "  \"versions\": {\n" +
                                                        "    \"minerDebug\": \"0\",\n" +
                                                        "    \"minerDate\": \"2020-07-07\",\n" +
                                                        "    \"softwareVersion\": \"1.3.7\",\n" +
                                                        "    \"miner\": \"v8.16.66\",\n" +
                                                        "    \"mspVer\": \"xx\"\n" +
                                                        "  },\n" +
                                                        "  \"hostname\": \"777\",\n" +
                                                        "  \"temperatureFanHot\": 36,\n" +
                                                        "  \"statVersion\": \"1.2\",\n" +
                                                        "  \"fans\": {\n" +
                                                        "    \"0\": {\n" +
                                                        "      \"power\": 100\n" +
                                                        "    },\n" +
                                                        "    \"1\": {\n" +
                                                        "      \"power\": 100\n" +
                                                        "    },\n" +
                                                        "    \"2\": {\n" +
                                                        "      \"power\": 100\n" +
                                                        "    },\n" +
                                                        "    \"3\": {\n" +
                                                        "      \"power\": 100\n" +
                                                        "    },\n" +
                                                        "    \"4\": {\n" +
                                                        "      \"power\": 100\n" +
                                                        "    },\n" +
                                                        "    \"5\": {\n" +
                                                        "      \"power\": 100\n" +
                                                        "    }\n" +
                                                        "  },\n" +
                                                        "  \"master\": {\n" +
                                                        "    \"upTime\": 4029,\n" +
                                                        "    \"boards\": 6,\n" +
                                                        "    \"psuW\": \"5421.2\",\n" +
                                                        "    \"ipGray\": \"0.0.0.0\",\n" +
                                                        "    \"mbHwVer\": 15,\n" +
                                                        "    \"boardsI\": \"107.1\",\n" +
                                                        "    \"boardsW\": \"5279.0\",\n" +
                                                        "    \"wattPerGHs\": \"0.112\",\n" +
                                                        "    \"voltSet\": \"51.0\",\n" +
                                                        "    \"ipWhite\": \"10.1.20.118\",\n" +
                                                        "    \"intervals\": {\n" +
                                                        "      \"0\": {\n" +
                                                        "        \"byDiff\": \"50395.0\",\n" +
                                                        "        \"byJobs\": \"49327.1\",\n" +
                                                        "        \"chipRestarts\": \"24101\",\n" +
                                                        "        \"errors\": \"1407409\",\n" +
                                                        "        \"name\": \"total\",\n" +
                                                        "        \"bySol\": \"48395.4\",\n" +
                                                        "        \"interval\": 4029,\n" +
                                                        "        \"byPool\": \"50417.5\",\n" +
                                                        "        \"solutions\": \"45398135\",\n" +
                                                        "        \"chipSpeed\": \"84.02\",\n" +
                                                        "        \"errorRate\": \"3.0\"\n" +
                                                        "      },\n" +
                                                        "      \"30\": {\n" +
                                                        "        \"byDiff\": \"88849.8\",\n" +
                                                        "        \"byJobs\": \"49343.0\",\n" +
                                                        "        \"chipRestarts\": \"174\",\n" +
                                                        "        \"errors\": \"15555\",\n" +
                                                        "        \"name\": \"30 sec\",\n" +
                                                        "        \"bySol\": \"48445.5\",\n" +
                                                        "        \"interval\": 30,\n" +
                                                        "        \"byPool\": \"88849.8\",\n" +
                                                        "        \"solutions\": \"338385\",\n" +
                                                        "        \"chipSpeed\": \"84.11\",\n" +
                                                        "        \"errorRate\": \"4.4\"\n" +
                                                        "      },\n" +
                                                        "      \"300\": {\n" +
                                                        "        \"byDiff\": \"52569.5\",\n" +
                                                        "        \"byJobs\": \"49741.5\",\n" +
                                                        "        \"chipRestarts\": \"1786\",\n" +
                                                        "        \"errors\": \"105438\",\n" +
                                                        "        \"name\": \"5 min\",\n" +
                                                        "        \"bySol\": \"48766.4\",\n" +
                                                        "        \"interval\": 300,\n" +
                                                        "        \"byPool\": \"53309.9\",\n" +
                                                        "        \"solutions\": \"3406269\",\n" +
                                                        "        \"chipSpeed\": \"84.66\",\n" +
                                                        "        \"errorRate\": \"3.0\"\n" +
                                                        "      },\n" +
                                                        "      \"900\": {\n" +
                                                        "        \"byDiff\": \"51335.4\",\n" +
                                                        "        \"byJobs\": \"49757.0\",\n" +
                                                        "        \"chipRestarts\": \"5335\",\n" +
                                                        "        \"errors\": \"313627\",\n" +
                                                        "        \"name\": \"15 min\",\n" +
                                                        "        \"bySol\": \"48804.6\",\n" +
                                                        "        \"interval\": 900,\n" +
                                                        "        \"byPool\": \"51582.2\",\n" +
                                                        "        \"solutions\": \"10226805\",\n" +
                                                        "        \"chipSpeed\": \"84.73\",\n" +
                                                        "        \"errorRate\": \"3.0\"\n" +
                                                        "      },\n" +
                                                        "      \"3600\": {\n" +
                                                        "        \"byDiff\": \"50800.8\",\n" +
                                                        "        \"byJobs\": \"49733.2\",\n" +
                                                        "        \"chipRestarts\": \"21359\",\n" +
                                                        "        \"errors\": \"1259271\",\n" +
                                                        "        \"name\": \"1 hour\",\n" +
                                                        "        \"bySol\": \"48788.2\",\n" +
                                                        "        \"interval\": 3603,\n" +
                                                        "        \"byPool\": \"50697.5\",\n" +
                                                        "        \"solutions\": \"40927588\",\n" +
                                                        "        \"chipSpeed\": \"84.70\",\n" +
                                                        "        \"errorRate\": \"3.0\"\n" +
                                                        "      }\n" +
                                                        "    },\n" +
                                                        "    \"psuI\": \"110.5\",\n" +
                                                        "    \"hwAddr\": \"16:bf:01:7f:aa:28\",\n" +
                                                        "    \"diff\": 51717,\n" +
                                                        "    \"osc\": 48,\n" +
                                                        "    \"errorSpi\": 0\n" +
                                                        "  },\n" +
                                                        "  \"temperatureFanCold\": 35,\n" +
                                                        "  \"slaves\": [\n" +
                                                        "    {\n" +
                                                        "      \"ver\": \"xx\",\n" +
                                                        "      \"uid\": \"xx\",\n" +
                                                        "      \"err\": 0,\n" +
                                                        "      \"ping\": 808,\n" +
                                                        "      \"hsi\": {\n" +
                                                        "        \"trim\": 18,\n" +
                                                        "        \"diff\": \"-2.7\",\n" +
                                                        "        \"mode\": 2\n" +
                                                        "      },\n" +
                                                        "      \"rx\": 10112,\n" +
                                                        "      \"time\": 4027,\n" +
                                                        "      \"id\": 0\n" +
                                                        "    }\n" +
                                                        "  ],\n" +
                                                        "  \"slavePingMin\": 808\n" +
                                                        "}",
                                                Collections.emptyMap())))),
                new OpenMinerFactory(),
                Collections.emptyMap(),
                true);
    }
}
