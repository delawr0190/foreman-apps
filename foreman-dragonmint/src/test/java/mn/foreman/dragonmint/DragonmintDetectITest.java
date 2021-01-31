package mn.foreman.dragonmint;

import mn.foreman.model.Detection;
import mn.foreman.util.AbstractDetectITest;
import mn.foreman.util.http.FakeHttpMinerServer;
import mn.foreman.util.http.HttpHandler;

import com.google.common.collect.ImmutableMap;

/** Tests detection of a Dragonmint. */
public class DragonmintDetectITest
        extends AbstractDetectITest {

    /** Constructor. */
    public DragonmintDetectITest() {
        super(
                new DragonmintDetectionStrategy<>(
                        DragonmintType::forType,
                        "DragonMint",
                        new DragonmintMacStrategy(
                                "127.0.0.1",
                                8888,
                                "username",
                                "password")),
                "127.0.0.1",
                8888,
                ImmutableMap.of(
                        "workerPreferred",
                        "true",
                        "username",
                        "username",
                        "password",
                        "password"),
                () -> new FakeHttpMinerServer(
                        8888,
                        ImmutableMap.of(
                                "/api/overview",
                                new HttpHandler(
                                        "",
                                        "{\n" +
                                                "  \"success\": true,\n" +
                                                "  \"type\": \"T1\",\n" +
                                                "  \"hardware\": {\n" +
                                                "    \"status\": \"15:38:31 up 10 min, load average: 0.30, 0.23, 0.11\",\n" +
                                                "    \"memUsed\": 92300,\n" +
                                                "    \"memFree\": 158244,\n" +
                                                "    \"memTotal\": 250544,\n" +
                                                "    \"cacheUsed\": 72180,\n" +
                                                "    \"cacheFree\": 194592,\n" +
                                                "    \"cacheTotal\": 266772\n" +
                                                "  },\n" +
                                                "  \"network\": {\n" +
                                                "    \"dhcp\": \"static\",\n" +
                                                "    \"ipaddress\": \"192.168.0.151\",\n" +
                                                "    \"netmask\": \"255.255.255.0\",\n" +
                                                "    \"gateway\": \"192.168.0.1\",\n" +
                                                "    \"dns1\": \"8.8.8.8\",\n" +
                                                "    \"dns2\": \"8.8.4.4\"\n" +
                                                "  },\n" +
                                                "  \"version\": {\n" +
                                                "    \"hwver\": \"g19\",\n" +
                                                "    \"ethaddr\": \"00:0a:35:00:00:00\",\n" +
                                                "    \"build_date\": \"9th of April 2018 03:20 PM\",\n" +
                                                "    \"platform_v\": \"t1_20180409_152051\"\n" +
                                                "  }\n" +
                                                "}"),
                                "/api/summary",
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
                                                "}"))),
                Detection.builder()
                        .minerType(DragonmintType.DRAGONMINT_T1)
                        .ipAddress("127.0.0.1")
                        .port(8888)
                        .parameters(
                                ImmutableMap.of(
                                        "username",
                                        "username",
                                        "password",
                                        "password",
                                        "mac",
                                        "00:0a:35:00:00:00",
                                        "workerPreferred",
                                        "true",
                                        "worker",
                                        "brndnmtthws.dragon-0ade5"))
                        .build());
    }
}