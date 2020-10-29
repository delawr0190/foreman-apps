package mn.foreman.dragonmint;

import mn.foreman.util.AbstractAsyncActionITest;
import mn.foreman.util.http.FakeHttpMinerServer;
import mn.foreman.util.http.HttpHandler;
import mn.foreman.util.http.SkipFirstHandler;

import com.google.common.collect.ImmutableMap;

import java.util.Collections;

/** Test rebooting a Dragonmint. */
public class DragonmintFactoryResetITest
        extends AbstractAsyncActionITest {

    /** Constructor. */
    public DragonmintFactoryResetITest() {
        super(
                8080,
                8080,
                new DragonmintFactoryResetAction(),
                Collections.singletonList(
                        () -> new FakeHttpMinerServer(
                                8080,
                                ImmutableMap.of(
                                        "/api/factoryReset",
                                        new HttpHandler(
                                                "",
                                                "{\"success\": true}",
                                                DragonmintTestUtils::validateBasicAuth),
                                        "/api/summary",
                                        new SkipFirstHandler(
                                                new HttpHandler(
                                                        "",
                                                        "{\n" +
                                                                "  \"success\": true,\n" +
                                                                "  \"DEVS\": [\n" +
                                                                "    {\n" +
                                                                "      \"ASC\": 0,\n" +
                                                                "      \"Name\": \"DT1\",\n" +
                                                                "      \"ID\": 0,\n" +
                                                                "      \"Enabled\": \"Y\",\n" +
                                                                "      \"Status\": \"Alive\",\n" +
                                                                "      \"Temperature\": 72,\n" +
                                                                "      \"MHS av\": 4904247.73,\n" +
                                                                "      \"MHS 5s\": 4745502.91,\n" +
                                                                "      \"MHS 1m\": 4830953.1,\n" +
                                                                "      \"MHS 5m\": 4923462.76,\n" +
                                                                "      \"MHS 15m\": 4932258.41,\n" +
                                                                "      \"Accepted\": 307,\n" +
                                                                "      \"Rejected\": 2,\n" +
                                                                "      \"Hardware Errors\": 6600,\n" +
                                                                "      \"Utility\": 3.84,\n" +
                                                                "      \"Last Share Pool\": 0,\n" +
                                                                "      \"Last Share Time\": 1529186657,\n" +
                                                                "      \"Total MH\": 23499497701,\n" +
                                                                "      \"Diff1 Work\": 5471148,\n" +
                                                                "      \"Difficulty Accepted\": 4733596,\n" +
                                                                "      \"Difficulty Rejected\": 31507,\n" +
                                                                "      \"Last Share Difficulty\": 16982,\n" +
                                                                "      \"Last Valid Work\": 1529186665,\n" +
                                                                "      \"Device Hardware%\": 0.1205,\n" +
                                                                "      \"Device Rejected%\": 0.5759,\n" +
                                                                "      \"Device Elapsed\": 4792,\n" +
                                                                "      \"Hash Rate\": 4932258.41\n" +
                                                                "    },\n" +
                                                                "    {\n" +
                                                                "      \"ASC\": 1,\n" +
                                                                "      \"Name\": \"DT1\",\n" +
                                                                "      \"ID\": 1,\n" +
                                                                "      \"Enabled\": \"Y\",\n" +
                                                                "      \"Status\": \"Alive\",\n" +
                                                                "      \"Temperature\": 67,\n" +
                                                                "      \"MHS av\": 4894650.23,\n" +
                                                                "      \"MHS 5s\": 4883086.81,\n" +
                                                                "      \"MHS 1m\": 4784555.03,\n" +
                                                                "      \"MHS 5m\": 4916279.62,\n" +
                                                                "      \"MHS 15m\": 4924235.45,\n" +
                                                                "      \"Accepted\": 378,\n" +
                                                                "      \"Rejected\": 1,\n" +
                                                                "      \"Hardware Errors\": 2380,\n" +
                                                                "      \"Utility\": 4.73,\n" +
                                                                "      \"Last Share Pool\": 0,\n" +
                                                                "      \"Last Share Time\": 1529186639,\n" +
                                                                "      \"Total MH\": 23453511280,\n" +
                                                                "      \"Diff1 Work\": 5460570,\n" +
                                                                "      \"Difficulty Accepted\": 5831923,\n" +
                                                                "      \"Difficulty Rejected\": 19325,\n" +
                                                                "      \"Last Share Difficulty\": 16982,\n" +
                                                                "      \"Last Valid Work\": 1529186665,\n" +
                                                                "      \"Device Hardware%\": 0.0436,\n" +
                                                                "      \"Device Rejected%\": 0.3539,\n" +
                                                                "      \"Device Elapsed\": 4792,\n" +
                                                                "      \"Hash Rate\": 4924235.45\n" +
                                                                "    },\n" +
                                                                "    {\n" +
                                                                "      \"ASC\": 2,\n" +
                                                                "      \"Name\": \"DT1\",\n" +
                                                                "      \"ID\": 2,\n" +
                                                                "      \"Enabled\": \"Y\",\n" +
                                                                "      \"Status\": \"Alive\",\n" +
                                                                "      \"Temperature\": 67,\n" +
                                                                "      \"MHS av\": 4892684.36,\n" +
                                                                "      \"MHS 5s\": 4446965.58,\n" +
                                                                "      \"MHS 1m\": 4976618.15,\n" +
                                                                "      \"MHS 5m\": 4976675.71,\n" +
                                                                "      \"MHS 15m\": 4931665.2,\n" +
                                                                "      \"Accepted\": 345,\n" +
                                                                "      \"Rejected\": 3,\n" +
                                                                "      \"Hardware Errors\": 7976,\n" +
                                                                "      \"Utility\": 4.32,\n" +
                                                                "      \"Last Share Pool\": 0,\n" +
                                                                "      \"Last Share Time\": 1529186642,\n" +
                                                                "      \"Total MH\": 23444092522,\n" +
                                                                "      \"Diff1 Work\": 5458635,\n" +
                                                                "      \"Difficulty Accepted\": 5405403,\n" +
                                                                "      \"Difficulty Rejected\": 32220,\n" +
                                                                "      \"Last Share Difficulty\": 16982,\n" +
                                                                "      \"Last Valid Work\": 1529186665,\n" +
                                                                "      \"Device Hardware%\": 0.1459,\n" +
                                                                "      \"Device Rejected%\": 0.5903,\n" +
                                                                "      \"Device Elapsed\": 4792,\n" +
                                                                "      \"Hash Rate\": 4931665.2\n" +
                                                                "    }\n" +
                                                                "  ],\n" +
                                                                "  \"POOLS\": [\n" +
                                                                "    {\n" +
                                                                "      \"POOL\": 0,\n" +
                                                                "      \"URL\": \"stratum+tcp://us-east.stratum.slushpool.com:3333\",\n" +
                                                                "      \"Status\": \"Alive\",\n" +
                                                                "      \"Priority\": 0,\n" +
                                                                "      \"Quota\": 1,\n" +
                                                                "      \"Long Poll\": \"N\",\n" +
                                                                "      \"Getworks\": 177,\n" +
                                                                "      \"Accepted\": 1030,\n" +
                                                                "      \"Rejected\": 6,\n" +
                                                                "      \"Works\": 4831931,\n" +
                                                                "      \"Discarded\": 2743,\n" +
                                                                "      \"Stale\": 0,\n" +
                                                                "      \"Get Failures\": 0,\n" +
                                                                "      \"Remote Failures\": 0,\n" +
                                                                "      \"User\": \"brndnmtthws.dragon-0ade5\",\n" +
                                                                "      \"Last Share Time\": 1529186657,\n" +
                                                                "      \"Diff1 Shares\": 16390353,\n" +
                                                                "      \"Proxy Type\": \"\",\n" +
                                                                "      \"Proxy\": \"\",\n" +
                                                                "      \"Difficulty Accepted\": 15970922,\n" +
                                                                "      \"Difficulty Rejected\": 83052,\n" +
                                                                "      \"Difficulty Stale\": 0,\n" +
                                                                "      \"Last Share Difficulty\": 16982,\n" +
                                                                "      \"Work Difficulty\": 16982,\n" +
                                                                "      \"Has Stratum\": true,\n" +
                                                                "      \"Stratum Active\": true,\n" +
                                                                "      \"Stratum URL\": \"us-east.stratum.slushpool.com\",\n" +
                                                                "      \"Stratum Difficulty\": 16982,\n" +
                                                                "      \"Has Vmask\": true,\n" +
                                                                "      \"Has GBT\": false,\n" +
                                                                "      \"Best Share\": 114372540,\n" +
                                                                "      \"Pool Rejected%\": 0.5173,\n" +
                                                                "      \"Pool Stale%\": 0,\n" +
                                                                "      \"Bad Work\": 483,\n" +
                                                                "      \"Current Block Height\": 527797,\n" +
                                                                "      \"Current Block Version\": 536870912\n" +
                                                                "    },\n" +
                                                                "    {\n" +
                                                                "      \"POOL\": 1,\n" +
                                                                "      \"URL\": \"stratum+tcp://pool.ckpool.org:3333\",\n" +
                                                                "      \"Status\": \"Alive\",\n" +
                                                                "      \"Priority\": 1,\n" +
                                                                "      \"Quota\": 1,\n" +
                                                                "      \"Long Poll\": \"N\",\n" +
                                                                "      \"Getworks\": 0,\n" +
                                                                "      \"Accepted\": 0,\n" +
                                                                "      \"Rejected\": 0,\n" +
                                                                "      \"Works\": 0,\n" +
                                                                "      \"Discarded\": 0,\n" +
                                                                "      \"Stale\": 0,\n" +
                                                                "      \"Get Failures\": 0,\n" +
                                                                "      \"Remote Failures\": 0,\n" +
                                                                "      \"User\": \"3GWdXx9dfLPvSe7d8UnxjnDnSAJodTTbrt.dragon-0ade5\",\n" +
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
                                                                "      \"Has Vmask\": true,\n" +
                                                                "      \"Has GBT\": false,\n" +
                                                                "      \"Best Share\": 0,\n" +
                                                                "      \"Pool Rejected%\": 0,\n" +
                                                                "      \"Pool Stale%\": 0,\n" +
                                                                "      \"Bad Work\": 0,\n" +
                                                                "      \"Current Block Height\": 0,\n" +
                                                                "      \"Current Block Version\": 536870912\n" +
                                                                "    }\n" +
                                                                "  ],\n" +
                                                                "  \"HARDWARE\": {\n" +
                                                                "    \"Fan duty\": 59\n" +
                                                                "  },\n" +
                                                                "  \"tuning\": false,\n" +
                                                                "  \"hashrates\": []\n" +
                                                                "}"))))),
                new DragonmintFactory(),
                Collections.emptyMap(),
                true);
    }
}