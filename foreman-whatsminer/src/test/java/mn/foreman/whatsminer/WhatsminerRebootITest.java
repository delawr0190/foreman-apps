package mn.foreman.whatsminer;

import mn.foreman.util.AbstractAsyncActionITest;
import mn.foreman.util.TestUtils;
import mn.foreman.util.http.FakeHttpMinerServer;
import mn.foreman.util.http.HttpHandler;
import mn.foreman.util.http.ServerHandler;
import mn.foreman.util.rpc.FakeRpcMinerServer;
import mn.foreman.util.rpc.HandlerInterface;
import mn.foreman.util.rpc.RpcHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

/** Tests rebooting a Whatsminer miner. */
@RunWith(Parameterized.class)
public class WhatsminerRebootITest
        extends AbstractAsyncActionITest {

    /** The mapper for this class. */
    private static final ObjectMapper MAPPER = new ObjectMapper();

    /**
     * Constructor.
     *
     * @param rpcHandlers  The RPC handlers.
     * @param httpHandlers The HTTP handlers.
     */
    public WhatsminerRebootITest(
            final Map<String, HandlerInterface> rpcHandlers,
            final Map<String, ServerHandler> httpHandlers) {
        super(
                8080,
                4028,
                new WhatsminerFirmwareAwareAction(
                        new WhatsminerRebootActionOld(),
                        new WhatsminerRebootActionNew()),
                Arrays.asList(
                        () -> new FakeRpcMinerServer(
                                4028,
                                rpcHandlers),
                        () -> new FakeHttpMinerServer(
                                8080,
                                httpHandlers)),
                new WhatsminerFactory(),
                TestUtils.toPoolJson(),
                true);
    }

    /**
     * Test parameters
     *
     * @return The test parameters.
     *
     * @throws IOException on failure.
     */
    @Parameterized.Parameters
    public static Collection<Object[]> parameters() throws IOException {
        return Arrays.asList(
                new Object[][]{
                        {
                                // Whatsminer M20S
                                ImmutableMap.of(
                                        "{\"command\":\"summary\"}",
                                        new RpcHandler(
                                                "{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1604757733,\"Code\":11,\"Msg\":\"Summary\",\"Description\":\"cgminer 4.9.2\"}],\"SUMMARY\":[{\"Elapsed\":66295,\"MHS av\":68710597.19,\"MHS 5s\":64143777.95,\"MHS 1m\":67266447.69,\"MHS 5m\":67827446.38,\"MHS 15m\":68290183.07,\"Found Blocks\":25,\"Getworks\":10780,\"Accepted\":5018,\"Rejected\":29,\"Hardware Errors\":912,\"Utility\":4.54,\"Discarded\":8046935,\"Stale\":0,\"Get Failures\":0,\"Local Work\":527721521,\"Remote Failures\":0,\"Network Blocks\":4206,\"Total MH\":4555143176276.0000,\"Work Utility\":3749.51,\"Difficulty Accepted\":1071653490.00000000,\"Difficulty Rejected\":5623144.00000000,\"Difficulty Stale\":0.00000000,\"Best Share\":1003626349,\"Temperature\":72.00,\"freq_avg\":622,\"Fan Speed In\":4950,\"Fan Speed Out\":4920,\"Voltage\":1300,\"Power\":3491,\"Power_RT\":3482,\"Device Hardware%\":0.0220,\"Device Rejected%\":135.7304,\"Pool Rejected%\":0.5220,\"Pool Stale%\":0.0000,\"Last getwork\":0,\"Uptime\":66892,\"Chip Data\":\"HP3D04-20010214 C BINV04-192104B\",\"Power Current\":245,\"Power Fanspeed\":8880,\"Error Code Count\":0,\"Factory Error Code Count\":0,\"Security Mode\":0,\"Liquid Cooling\":false,\"Hash Stable\":true,\"Hash Stable Cost Seconds\":1498,\"Hash Deviation%\":-0.3372,\"Target Freq\":622,\"Target MHS\":68765832,\"Env Temp\":28.50,\"Power Mode\":\"Normal\",\"Firmware Version\":\"\\'20200917.22.REL\\'\",\"CB Platform\":\"ALLWINNER_H3\",\"CB Version\":\"V10\",\"MAC\":\"C4:11:04:01:3C:75\",\"Factory GHS\":67051,\"Power Limit\":3500,\"Chip Temp Min\":65.00,\"Chip Temp Max\":87.70,\"Chip Temp Avg\":79.34}],\"id\":1}"),
                                        "{\"command\":\"devs\"}",
                                        new RpcHandler(
                                                "{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1604757733,\"Code\":9,\"Msg\":\"3 ASC(s)\",\"Description\":\"cgminer 4.9.2\"}],\"DEVS\":[{\"ASC\":0,\"Name\":\"SM\",\"ID\":0,\"Slot\":0,\"Enabled\":\"Y\",\"Status\":\"Alive\",\"Temperature\":71.50,\"Chip Frequency\":617,\"Fan Speed In\":4950,\"Fan Speed Out\":4920,\"MHS av\":22711971.14,\"MHS 5s\":19540157.81,\"MHS 1m\":22079665.63,\"MHS 5m\":22534937.07,\"MHS 15m\":22613625.46,\"Accepted\":1626,\"Rejected\":9,\"Hardware Errors\":324,\"Utility\":1.47,\"Last Share Pool\":0,\"Last Share Time\":1604757724,\"Total MH\":1505682090973.0000,\"Diff1 Work\":1369410,\"Difficulty Accepted\":345293896.00000000,\"Difficulty Rejected\":1622162.00000000,\"Last Share Difficulty\":158554.00000000,\"Last Valid Work\":1604757733,\"Device Hardware%\":0.0237,\"Device Rejected%\":118.4570,\"Device Elapsed\":66295,\"Upfreq Complete\":1,\"Effective Chips\":111,\"PCB SN\":\"H3M14S6F200114K10102\",\"Chip Temp Min\":65.00,\"Chip Temp Max\":87.70,\"Chip Temp Avg\":80.36},{\"ASC\":1,\"Name\":\"SM\",\"ID\":1,\"Slot\":1,\"Enabled\":\"Y\",\"Status\":\"Alive\",\"Temperature\":68.50,\"Chip Frequency\":632,\"Fan Speed In\":4950,\"Fan Speed Out\":4920,\"MHS av\":23220108.38,\"MHS 5s\":23015815.92,\"MHS 1m\":23470927.44,\"MHS 5m\":22962220.49,\"MHS 15m\":23064765.42,\"Accepted\":1793,\"Rejected\":13,\"Hardware Errors\":277,\"Utility\":1.62,\"Last Share Pool\":0,\"Last Share Time\":1604757702,\"Total MH\":1539368891060.0000,\"Diff1 Work\":1400047,\"Difficulty Accepted\":384509060.00000000,\"Difficulty Rejected\":2765638.00000000,\"Last Share Difficulty\":158554.00000000,\"Last Valid Work\":1604757733,\"Device Hardware%\":0.0198,\"Device Rejected%\":197.5389,\"Device Elapsed\":66295,\"Upfreq Complete\":1,\"Effective Chips\":111,\"PCB SN\":\"H3M14S6F200114K10107\",\"Chip Temp Min\":66.00,\"Chip Temp Max\":84.50,\"Chip Temp Avg\":76.77},{\"ASC\":2,\"Name\":\"SM\",\"ID\":2,\"Slot\":2,\"Enabled\":\"Y\",\"Status\":\"Alive\",\"Temperature\":72.00,\"Chip Frequency\":619,\"Fan Speed In\":4950,\"Fan Speed Out\":4920,\"MHS av\":22778493.47,\"MHS 5s\":21747874.70,\"MHS 1m\":21710305.01,\"MHS 5m\":22328696.24,\"MHS 15m\":22611254.21,\"Accepted\":1599,\"Rejected\":7,\"Hardware Errors\":311,\"Utility\":1.45,\"Last Share Pool\":0,\"Last Share Time\":1604757717,\"Total MH\":1510092194243.0000,\"Diff1 Work\":1373421,\"Difficulty Accepted\":341850534.00000000,\"Difficulty Rejected\":1235344.00000000,\"Last Share Difficulty\":158554.00000000,\"Last Valid Work\":1604757733,\"Device Hardware%\":0.0226,\"Device Rejected%\":89.9465,\"Device Elapsed\":66295,\"Upfreq Complete\":1,\"Effective Chips\":111,\"PCB SN\":\"H3M14S6F200114K10115\",\"Chip Temp Min\":68.00,\"Chip Temp Max\":87.20,\"Chip Temp Avg\":80.90}],\"id\":1}"),
                                        "{\"command\":\"pools\"}",
                                        new RpcHandler(
                                                "{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1560974720,\"Code\":7,\"Msg\":\"2 Pool(s)\",\"Description\":\"cgminer 4.9.2\"}],\"POOLS\":[{\"POOL\":0,\"URL\":\"stratum+tcp://us-east.stratum.slushpool.com:3333\",\"Status\":\"Alive\",\"Priority\":0,\"Quota\":1,\"Long Poll\":\"N\",\"Getworks\":3512,\"Accepted\":33825,\"Rejected\":185,\"Works\":788544801,\"Discarded\":2580368,\"Stale\":27,\"Get Failures\":1,\"Remote Failures\":0,\"User\":\"002m20s\",\"Last Share Time\":1560974720,\"Diff1 Shares\":1481234400,\"Proxy Type\":\"\",\"Proxy\":\"\",\"Difficulty Accepted\":1473980432.00000000,\"Difficulty Rejected\":8081331.00000000,\"Difficulty Stale\":0.00000000,\"Last Share Difficulty\":49174.00000000,\"Work Difficulty\":49174.00000000,\"Has Stratum\":true,\"Stratum Active\":true,\"Stratum URL\":\"us-east.stratum.slushpool.com\",\"Stratum Difficulty\":49174.00000000,\"Has GBT\":false,\"Best Share\":5452261792,\"Pool Rejected%\":0.5453,\"Pool Stale%\":0.0000,\"Bad Work\":183,\"Current Block Height\":581482,\"Current Block Version\":536870912},{\"POOL\":1,\"URL\":\"stratum+tcp://us-central01.miningrigrentals.com:50194\",\"Status\":\"Alive\",\"Priority\":1,\"Quota\":1,\"Long Poll\":\"N\",\"Getworks\":0,\"Accepted\":0,\"Rejected\":0,\"Works\":0,\"Discarded\":0,\"Stale\":0,\"Get Failures\":0,\"Remote Failures\":0,\"User\":\"xxxx.yyyy\",\"Last Share Time\":0,\"Diff1 Shares\":0,\"Proxy Type\":\"\",\"Proxy\":\"\",\"Difficulty Accepted\":0.00000000,\"Difficulty Rejected\":0.00000000,\"Difficulty Stale\":0.00000000,\"Last Share Difficulty\":0.00000000,\"Work Difficulty\":0.00000000,\"Has Stratum\":true,\"Stratum Active\":false,\"Stratum URL\":\"\",\"Stratum Difficulty\":0.00000000,\"Has GBT\":false,\"Best Share\":0,\"Pool Rejected%\":0.0000,\"Pool Stale%\":0.0000,\"Bad Work\":0,\"Current Block Height\":0,\"Current Block Version\":536870912}],\"id\":1}")),
                                ImmutableMap.of(
                                        "/cgi-bin/luci/",
                                        new HttpHandler(
                                                "luci_username=my-auth-username&luci_password=my-auth-password",
                                                Collections.emptyMap(),
                                                "",
                                                ImmutableMap.of(
                                                        "Set-Cookie",
                                                        "sysauth=c57ede0698febf098dca307d106376d0")),
                                        "/cgi-bin/luci/admin/system/reboot",
                                        new HttpHandler(
                                                "",
                                                ImmutableMap.of(
                                                        "Cookie",
                                                        "sysauth=c57ede0698febf098dca307d106376d0"),
                                                "<!DOCTYPE html>\n" +
                                                        "<html lang=\"en\">\n" +
                                                        "\t<head>\n" +
                                                        "\t\t<meta charset=\"utf-8\">\n" +
                                                        "\t\t<title>WhatsMiner - Pool - LuCI</title>\n" +
                                                        "\t\t<!--[if lt IE 9]><script src=\"/luci-static/bootstrap/html5.js?v=git-16.336.70424-1fd43b4\"></script><![endif]-->\n" +
                                                        "\t\t<meta name=\"viewport\" content=\"initial-scale=1.0\">\n" +
                                                        "\t\t<link rel=\"stylesheet\" href=\"/luci-static/bootstrap/cascade.css?v=git-16.336.70424-1fd43b4\">\n" +
                                                        "\t\t<link rel=\"stylesheet\" media=\"only screen and (max-device-width: 854px)\" href=\"/luci-static/bootstrap/mobile.css?v=git-16.336.70424-1fd43b4\" type=\"text/css\" />\n" +
                                                        "\t\t<link rel=\"shortcut icon\" href=\"/luci-static/bootstrap/favicon.ico\">\n" +
                                                        "\t\t<script src=\"/luci-static/resources/xhr.js?v=git-16.336.70424-1fd43b4\"></script>\n" +
                                                        "\t</head>\n" +
                                                        "\n" +
                                                        "\t<body class=\"lang_enPool\">\n" +
                                                        "\t\t<header>\n" +
                                                        "\t\t\t<div class=\"fill\">\n" +
                                                        "\t\t\t\t<div class=\"container\">\n" +
                                                        "\t\t\t\t\t<a class=\"brand\" href=\"#\">WhatsMiner</a>\n" +
                                                        "\t\t\t\t\t<ul class=\"nav\"><li class=\"dropdown\"><a class=\"menu\" href=\"#\">Status</a><ul class=\"dropdown-menu\"><li><a href=\"/cgi-bin/luci/admin/status/cgminerstatus\">CGMiner Status</a></li><li><a href=\"/cgi-bin/luci/admin/status/cgminerapi\">CGMiner API Log</a></li><li><a href=\"/cgi-bin/luci/admin/status/syslog\">System Log</a></li><li><a href=\"/cgi-bin/luci/admin/status/minerlog\">Miner Log</a></li><li><a href=\"/cgi-bin/luci/admin/status/processes\">Processes</a></li><li><a href=\"/cgi-bin/luci/admin/status/overview\">Overview</a></li></ul></li><li class=\"dropdown\"><a class=\"menu\" href=\"#\">System</a><ul class=\"dropdown-menu\"><li><a href=\"/cgi-bin/luci/admin/system/system\">System</a></li><li><a href=\"/cgi-bin/luci/admin/system/admin\">Administration</a></li><li><a href=\"/cgi-bin/luci/admin/system/reboot\">Reboot</a></li></ul></li><li class=\"dropdown\"><a class=\"menu\" href=\"#\">Configuration</a><ul class=\"dropdown-menu\"><li><a href=\"/cgi-bin/luci/admin/network/network\">Interfaces</a></li><li><a href=\"/cgi-bin/luci/admin/network/cgminer\">CGMiner Configuration</a></li></ul></li><li><a href=\"/cgi-bin/luci/admin/logout\">Logout</a></li></ul>\n" +
                                                        "\t\t\t\t\t<div class=\"pull-right\">\n" +
                                                        "\t\t\t\t\t\t\n" +
                                                        "\t\t\t\t\t\t<span id=\"xhr_poll_status\" style=\"display:none\" onclick=\"XHR.running() ? XHR.halt() : XHR.run()\">\n" +
                                                        "\t\t\t\t\t\t\t<span class=\"label success\" id=\"xhr_poll_status_on\">Auto Refresh on</span>\n" +
                                                        "\t\t\t\t\t\t\t<span class=\"label\" id=\"xhr_poll_status_off\" style=\"display:none\">Auto Refresh off</span>\n" +
                                                        "\t\t\t\t\t\t</span>\n" +
                                                        "\t\t\t\t\t</div>\n" +
                                                        "\t\t\t\t</div>\n" +
                                                        "\t\t\t</div>\n" +
                                                        "\t\t</header><div id=\"maincontent\" class=\"container\">\n" +
                                                        "\t\t\t<ul class=\"tabs\"><li class=\"tabmenu-item-pool active\"><a href=\"/cgi-bin/luci/admin/network/cgminer/pool\">Pool</a></li><li class=\"tabmenu-item-power \"><a href=\"/cgi-bin/luci/admin/network/cgminer/power\">Power</a></li></ul>\n" +
                                                        "\n" +
                                                        "\n" +
                                                        "<form method=\"post\" name=\"cbi\" action=\"/cgi-bin/luci/admin/network/cgminer\" enctype=\"multipart/form-data\" onreset=\"return cbi_validate_reset(this)\" onsubmit=\"return cbi_validate_form(this, 'Some fields are invalid, cannot save values!')\">\n" +
                                                        "\t<div>\n" +
                                                        "\t\t<script type=\"text/javascript\" src=\"/luci-static/resources/cbi.js?v=git-16.336.70424-1fd43b4\" data-strings=\"{&#34;path&#34;:{&#34;resource&#34;:&#34;\\/luci-static\\/resources&#34;,&#34;browser&#34;:&#34;\\/cgi-bin\\/luci\\/admin\\/filebrowser&#34;},&#34;label&#34;:{&#34;choose&#34;:&#34;-- Please choose --&#34;,&#34;custom&#34;:&#34;-- custom --&#34;}}\"></script>\n" +
                                                        "\t\t<input type=\"hidden\" name=\"token\" value=\"dfa7be2b0bde2c5270e5d1ebf4f48ef6\" />\n" +
                                                        "\t\t<input type=\"hidden\" name=\"cbi.submit\" value=\"1\" />\n" +
                                                        "\t\t<input type=\"submit\" value=\"Save\" class=\"hidden\" />\n" +
                                                        "\t</div>\n" +
                                                        "<div class=\"cbi-map\" id=\"cbi-pools\">\n" +
                                                        "\t<h2 name=\"content\">Configuration</h2>\n" +
                                                        "\t<fieldset class=\"cbi-section\" id=\"cbi-pools-pools\">\n" +
                                                        "\t\n" +
                                                        "\t\n" +
                                                        "\t\n" +
                                                        "\n" +
                                                        "\t\t\n" +
                                                        "\n" +
                                                        "\t\t<fieldset class=\"cbi-section-node\" id=\"cbi-pools-default\">\n" +
                                                        "\t\t\t\n" +
                                                        "\n" +
                                                        "\n" +
                                                        "\t\n" +
                                                        "\n" +
                                                        "<div class=\"cbi-value\" id=\"cbi-pools-default-coin_type\" data-index=\"1\" data-depends=\"[]\"><label class=\"cbi-value-title\" for=\"cbid.pools.default.coin_type\">Coin Type</label>\n" +
                                                        "\t\t<div class=\"cbi-value-field\">\n" +
                                                        "\n" +
                                                        "\n" +
                                                        "\t<select class=\"cbi-input-select\" data-update=\"change\" id=\"cbid.pools.default.coin_type\" name=\"cbid.pools.default.coin_type\" size=\"1\">\n" +
                                                        "\t\t<option id=\"cbid.pools.default.coin_type-BTC\" value=\"BTC\" data-index=\"1\" data-depends=\"[]\" selected=\"selected\">BTC</option>\n" +
                                                        "\t</select>\n" +
                                                        "\n" +
                                                        "\t\t</div></div>\n" +
                                                        "\n" +
                                                        "<div class=\"cbi-value\" id=\"cbi-pools-default-pool1url\" data-index=\"2\" data-depends=\"[]\"><label class=\"cbi-value-title\" for=\"cbid.pools.default.pool1url\">Pool 1</label>\n" +
                                                        "\t\t<div class=\"cbi-value-field\">\n" +
                                                        "\n" +
                                                        "\t<input data-update=\"change\" id=\"cbid.pools.default.pool1url\" name=\"cbid.pools.default.pool1url\" type=\"text\" class=\"cbi-input-text\" value=\"stratum+tcp://sha256.eu.mine.zpool.ca:3333\" data-type=\"string\" data-optional=\"true\" data-choices=\"[[&#34;stratum+tcp:\\/\\/btc.ss.poolin.com:443&#34;,&#34;stratum+tcp:\\/\\/stratum.f2pool.com:3333&#34;,&#34;stratum+tcp:\\/\\/stratum.bixin.com:443&#34;],[&#34;stratum+tcp:\\/\\/btc.ss.poolin.com:443&#34;,&#34;stratum+tcp:\\/\\/stratum.f2pool.com:3333&#34;,&#34;stratum+tcp:\\/\\/stratum.bixin.com:443&#34;]]\" />\n" +
                                                        "\t\n" +
                                                        "\t\t</div></div>\n" +
                                                        "\n" +
                                                        "<div class=\"cbi-value\" id=\"cbi-pools-default-pool1user\" data-index=\"3\" data-depends=\"[]\"><label class=\"cbi-value-title\" for=\"cbid.pools.default.pool1user\">Pool1 worker</label>\n" +
                                                        "\t\t<div class=\"cbi-value-field\">\n" +
                                                        "\n" +
                                                        "\t<input data-update=\"change\" id=\"cbid.pools.default.pool1user\" name=\"cbid.pools.default.pool1user\" type=\"text\" class=\"cbi-input-text\" value=\"aaa\" />\n" +
                                                        "\t\n" +
                                                        "\t\t</div></div>\n" +
                                                        "\n" +
                                                        "<div class=\"cbi-value\" id=\"cbi-pools-default-pool1pw\" data-index=\"4\" data-depends=\"[]\"><label class=\"cbi-value-title\" for=\"cbid.pools.default.pool1pw\">Pool1 password</label>\n" +
                                                        "\t\t<div class=\"cbi-value-field\">\n" +
                                                        "\n" +
                                                        "\t<input data-update=\"change\" id=\"cbid.pools.default.pool1pw\" name=\"cbid.pools.default.pool1pw\" type=\"text\" class=\"cbi-input-text\" value=\"c=BTC,sd=65536\" />\n" +
                                                        "\t\n" +
                                                        "\t\t</div></div>\n" +
                                                        "\n" +
                                                        "<div class=\"cbi-value\" id=\"cbi-pools-default-pool2url\" data-index=\"5\" data-depends=\"[]\"><label class=\"cbi-value-title\" for=\"cbid.pools.default.pool2url\">Pool 2</label>\n" +
                                                        "\t\t<div class=\"cbi-value-field\">\n" +
                                                        "\n" +
                                                        "\t<input data-update=\"change\" id=\"cbid.pools.default.pool2url\" name=\"cbid.pools.default.pool2url\" type=\"text\" class=\"cbi-input-text\" value=\"\" data-type=\"string\" data-optional=\"true\" data-choices=\"[[&#34;stratum+tcp:\\/\\/btc.ss.poolin.com:443&#34;,&#34;stratum+tcp:\\/\\/stratum.f2pool.com:3333&#34;,&#34;stratum+tcp:\\/\\/stratum.bixin.com:443&#34;],[&#34;stratum+tcp:\\/\\/btc.ss.poolin.com:443&#34;,&#34;stratum+tcp:\\/\\/stratum.f2pool.com:3333&#34;,&#34;stratum+tcp:\\/\\/stratum.bixin.com:443&#34;]]\" />\n" +
                                                        "\t\n" +
                                                        "\t\t</div></div>\n" +
                                                        "\n" +
                                                        "<div class=\"cbi-value\" id=\"cbi-pools-default-pool2user\" data-index=\"6\" data-depends=\"[]\"><label class=\"cbi-value-title\" for=\"cbid.pools.default.pool2user\">Pool2 worker</label>\n" +
                                                        "\t\t<div class=\"cbi-value-field\">\n" +
                                                        "\n" +
                                                        "\t<input data-update=\"change\" id=\"cbid.pools.default.pool2user\" name=\"cbid.pools.default.pool2user\" type=\"text\" class=\"cbi-input-text\" value=\"\" />\n" +
                                                        "\t\n" +
                                                        "\t\t</div></div>\n" +
                                                        "\n" +
                                                        "<div class=\"cbi-value\" id=\"cbi-pools-default-pool2pw\" data-index=\"7\" data-depends=\"[]\"><label class=\"cbi-value-title\" for=\"cbid.pools.default.pool2pw\">Pool2 password</label>\n" +
                                                        "\t\t<div class=\"cbi-value-field\">\n" +
                                                        "\n" +
                                                        "\t<input data-update=\"change\" id=\"cbid.pools.default.pool2pw\" name=\"cbid.pools.default.pool2pw\" type=\"text\" class=\"cbi-input-text\" value=\"\" />\n" +
                                                        "\t\n" +
                                                        "\t\t</div></div>\n" +
                                                        "\n" +
                                                        "<div class=\"cbi-value\" id=\"cbi-pools-default-pool3url\" data-index=\"8\" data-depends=\"[]\"><label class=\"cbi-value-title\" for=\"cbid.pools.default.pool3url\">Pool 3</label>\n" +
                                                        "\t\t<div class=\"cbi-value-field\">\n" +
                                                        "\n" +
                                                        "\t<input data-update=\"change\" id=\"cbid.pools.default.pool3url\" name=\"cbid.pools.default.pool3url\" type=\"text\" class=\"cbi-input-text\" value=\"\" data-type=\"string\" data-optional=\"true\" data-choices=\"[[&#34;stratum+tcp:\\/\\/btc.ss.poolin.com:443&#34;,&#34;stratum+tcp:\\/\\/stratum.f2pool.com:3333&#34;,&#34;stratum+tcp:\\/\\/stratum.bixin.com:443&#34;],[&#34;stratum+tcp:\\/\\/btc.ss.poolin.com:443&#34;,&#34;stratum+tcp:\\/\\/stratum.f2pool.com:3333&#34;,&#34;stratum+tcp:\\/\\/stratum.bixin.com:443&#34;]]\" />\n" +
                                                        "\t\n" +
                                                        "\t\t</div></div>\n" +
                                                        "\n" +
                                                        "<div class=\"cbi-value\" id=\"cbi-pools-default-pool3user\" data-index=\"9\" data-depends=\"[]\"><label class=\"cbi-value-title\" for=\"cbid.pools.default.pool3user\">Pool3 worker</label>\n" +
                                                        "\t\t<div class=\"cbi-value-field\">\n" +
                                                        "\n" +
                                                        "\t<input data-update=\"change\" id=\"cbid.pools.default.pool3user\" name=\"cbid.pools.default.pool3user\" type=\"text\" class=\"cbi-input-text\" value=\"\" />\n" +
                                                        "\t\n" +
                                                        "\t\t</div></div>\n" +
                                                        "\n" +
                                                        "<div class=\"cbi-value cbi-value-last\" id=\"cbi-pools-default-pool3pw\" data-index=\"10\" data-depends=\"[]\"><label class=\"cbi-value-title\" for=\"cbid.pools.default.pool3pw\">Pool3 password</label>\n" +
                                                        "\t\t<div class=\"cbi-value-field\">\n" +
                                                        "\n" +
                                                        "\t<input data-update=\"change\" id=\"cbid.pools.default.pool3pw\" name=\"cbid.pools.default.pool3pw\" type=\"text\" class=\"cbi-input-text\" value=\"\" />\n" +
                                                        "\t\n" +
                                                        "\t\t</div></div>\n" +
                                                        "\n" +
                                                        "\n" +
                                                        "\n" +
                                                        "\n" +
                                                        "\n" +
                                                        "\n" +
                                                        "\n" +
                                                        "\n" +
                                                        "\t\t</fieldset>\n" +
                                                        "\t\t<br />\n" +
                                                        "\n" +
                                                        "\t\n" +
                                                        "\n" +
                                                        "\t\n" +
                                                        "</fieldset>\n" +
                                                        "\t\n" +
                                                        "\n" +
                                                        "\t<br />\n" +
                                                        "</div>\n" +
                                                        "<div class=\"cbi-page-actions\">\n" +
                                                        "\t\t\n" +
                                                        "\n" +
                                                        "\t\t\n" +
                                                        "\t\t\n" +
                                                        "\t\t\t<input class=\"cbi-button cbi-button-apply\" type=\"submit\" name=\"cbi.apply\" value=\"Save &#38; Apply\" />\n" +
                                                        "\t\t\n" +
                                                        "        <!--\n" +
                                                        "\t\t\n" +
                                                        "\t\t\t<input class=\"cbi-button cbi-button-save\" type=\"submit\" value=\"Save\" />\n" +
                                                        "\t\t\n" +
                                                        "\t\t\n" +
                                                        "\t\t\t<input class=\"cbi-button cbi-button-reset\" type=\"button\" value=\"Reset\" onclick=\"location.href='/cgi-bin/luci/admin/network/cgminer'\" />\n" +
                                                        "\t\t\n" +
                                                        "        -->\n" +
                                                        "\t</div></form>\n" +
                                                        "\n" +
                                                        "<script type=\"text/javascript\">cbi_init();</script>\n" +
                                                        "\n" +
                                                        "\n" +
                                                        "   <footer>\n" +
                                                        "    <a href=\"https://github.com/openwrt/luci\">Powered by LuCI Master (git-16.336.70424-1fd43b4)</a> / OpenWrt Designated Driver 50045\n" +
                                                        "    \n" +
                                                        "   </footer>\n" +
                                                        "   </div>\n" +
                                                        "  </div>\n" +
                                                        " </body>\n" +
                                                        "</html>",
                                                Collections.emptyMap()),
                                        "/cgi-bin/luci//admin/system/reboot/call",
                                        new HttpHandler(
                                                "token=dfa7be2b0bde2c5270e5d1ebf4f48ef6",
                                                ImmutableMap.of(
                                                        "Cookie",
                                                        "sysauth=c57ede0698febf098dca307d106376d0"),
                                                "",
                                                Collections.emptyMap()))
                        },
                        {
                                // Whatsminer M31S
                                ImmutableMap.of(
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
                                                                                Map.class)))))),
                                ImmutableMap.of(
                                        "/cgi-bin/luci/",
                                        new HttpHandler(
                                                "luci_username=my-auth-username&luci_password=my-auth-password",
                                                Collections.emptyMap(),
                                                "",
                                                ImmutableMap.of(
                                                        "Set-Cookie",
                                                        "sysauth=c57ede0698febf098dca307d106376d0")),
                                        "/cgi-bin/luci/admin/system/reboot",
                                        new HttpHandler(
                                                "",
                                                ImmutableMap.of(
                                                        "Cookie",
                                                        "sysauth=c57ede0698febf098dca307d106376d0"),
                                                "<!DOCTYPE html>\n" +
                                                        "<html lang=\"en\">\n" +
                                                        "\t<head>\n" +
                                                        "\t\t<meta charset=\"utf-8\">\n" +
                                                        "\t\t<title>WhatsMiner_38db - Reboot - LuCI</title>\n" +
                                                        "\t\t<!--[if lt IE 9]><script src=\"/luci-static/bootstrap/html5.js?v=git-16.336.70424-1fd43b4\"></script><![endif]-->\n" +
                                                        "\t\t<meta name=\"viewport\" content=\"initial-scale=1.0\">\n" +
                                                        "\t\t<link rel=\"stylesheet\" href=\"/luci-static/bootstrap/cascade.css?v=git-16.336.70424-1fd43b4\">\n" +
                                                        "\t\t<link rel=\"stylesheet\" media=\"only screen and (max-device-width: 854px)\" href=\"/luci-static/bootstrap/mobile.css?v=git-16.336.70424-1fd43b4\" type=\"text/css\" />\n" +
                                                        "\t\t<link rel=\"shortcut icon\" href=\"/luci-static/bootstrap/favicon.ico\">\n" +
                                                        "\t\t<script src=\"/luci-static/resources/xhr.js?v=git-16.336.70424-1fd43b4\"></script>\n" +
                                                        "\t</head>\n" +
                                                        "\n" +
                                                        "\t<body class=\"lang_enReboot\">\n" +
                                                        "\t\t<header>\n" +
                                                        "\t\t\t<div class=\"fill\">\n" +
                                                        "\t\t\t\t<div class=\"container\">\n" +
                                                        "\t\t\t\t\t<a class=\"brand\" href=\"#\">WhatsMiner_38db</a>\n" +
                                                        "\t\t\t\t\t<ul class=\"nav\"><li class=\"dropdown\"><a class=\"menu\" href=\"#\">Status</a><ul class=\"dropdown-menu\"><li><a href=\"/cgi-bin/luci/admin/status/cgminerstatus\">CGMiner Status</a></li><li><a href=\"/cgi-bin/luci/admin/status/cgminerapi\">CGMiner API Log</a></li><li><a href=\"/cgi-bin/luci/admin/status/syslog\">System Log</a></li><li><a href=\"/cgi-bin/luci/admin/status/minerlog\">Miner Log</a></li><li><a href=\"/cgi-bin/luci/admin/status/processes\">Processes</a></li><li><a href=\"/cgi-bin/luci/admin/status/overview\">Overview</a></li></ul></li><li class=\"dropdown\"><a class=\"menu\" href=\"#\">System</a><ul class=\"dropdown-menu\"><li><a href=\"/cgi-bin/luci/admin/system/system\">System</a></li><li><a href=\"/cgi-bin/luci/admin/system/admin\">Administration</a></li><li><a href=\"/cgi-bin/luci/admin/system/reboot\">Reboot</a></li></ul></li><li class=\"dropdown\"><a class=\"menu\" href=\"#\">Configuration</a><ul class=\"dropdown-menu\"><li><a href=\"/cgi-bin/luci/admin/network/network\">Interfaces</a></li><li><a href=\"/cgi-bin/luci/admin/network/cgminer\">CGMiner Configuration</a></li></ul></li><li><a href=\"/cgi-bin/luci/admin/logout\">Logout</a></li></ul>\n" +
                                                        "\t\t\t\t\t<div class=\"pull-right\">\n" +
                                                        "\t\t\t\t\t\t\n" +
                                                        "\t\t\t\t\t\t<span id=\"xhr_poll_status\" style=\"display:none\" onclick=\"XHR.running() ? XHR.halt() : XHR.run()\">\n" +
                                                        "\t\t\t\t\t\t\t<span class=\"label success\" id=\"xhr_poll_status_on\">Auto Refresh on</span>\n" +
                                                        "\t\t\t\t\t\t\t<span class=\"label\" id=\"xhr_poll_status_off\" style=\"display:none\">Auto Refresh off</span>\n" +
                                                        "\t\t\t\t\t\t</span>\n" +
                                                        "\t\t\t\t\t</div>\n" +
                                                        "\t\t\t\t</div>\n" +
                                                        "\t\t\t</div>\n" +
                                                        "\t\t</header><div id=\"maincontent\" class=\"container\">\n" +
                                                        "\t\t\t\n" +
                                                        "\n" +
                                                        "\n" +
                                                        "\n" +
                                                        "<h2 name=\"content\">Reboot</h2>\n" +
                                                        "<br />\n" +
                                                        "\n" +
                                                        "<p>Reboots the operating system of your device</p><hr />\n" +
                                                        "\n" +
                                                        "<script type=\"text/javascript\">//<![CDATA[\n" +
                                                        "\tvar tries = 0;\n" +
                                                        "\n" +
                                                        "\tfunction ok() {\n" +
                                                        "\t\twindow.location = '/cgi-bin/luci//admin';\n" +
                                                        "\t}\n" +
                                                        "\n" +
                                                        "\tfunction check() {\n" +
                                                        "\t\tif (tries++ < 12)\n" +
                                                        "\t\t\twindow.setTimeout(ping, 5000);\n" +
                                                        "\t\telse\n" +
                                                        "\t\t\talert('Device unreachable');\n" +
                                                        "\t}\n" +
                                                        "\n" +
                                                        "\tfunction ping() {\n" +
                                                        "\t\tvar img = document.createElement('img');\n" +
                                                        "\n" +
                                                        "\t\timg.onload = ok;\n" +
                                                        "\t\timg.onerror = check;\n" +
                                                        "\t\timg.src = '/luci-static/resources/icons/loading.gif?' + Math.random();\n" +
                                                        "\n" +
                                                        "\t\tdocument.getElementById('reboot-message').innerHTML = 'Waiting for device...';\n" +
                                                        "\t}\n" +
                                                        "\n" +
                                                        "\tfunction reboot(button) {\n" +
                                                        "\t\tbutton.style.display = 'none';\n" +
                                                        "\t\tdocument.getElementById('reboot-message').parentNode.style.display = '';\n" +
                                                        "\n" +
                                                        "\t\t(new XHR()).post('/cgi-bin/luci//admin/system/reboot/call', { token: 'e82ebba30c29bf4e6b493684192c44a7' }, check);\n" +
                                                        "\t}\n" +
                                                        "//]]></script>\n" +
                                                        "\n" +
                                                        "<input class=\"cbi-button cbi-button-apply\" type=\"button\" value=\"Perform reboot\" onclick=\"reboot(this)\" />\n" +
                                                        "\n" +
                                                        "<p class=\"alert-message\" style=\"display:none\">\n" +
                                                        "\t<img src=\"/luci-static/resources/icons/loading.gif\" alt=\"Loading\" style=\"vertical-align:middle\" />\n" +
                                                        "\t<span id=\"reboot-message\">Device is rebooting...</span>\n" +
                                                        "</p>\n" +
                                                        "\n" +
                                                        "\n" +
                                                        "   <footer>\n" +
                                                        "    <a href=\"https://github.com/openwrt/luci\">Powered by LuCI Master (git-16.336.70424-1fd43b4)</a> / LEDE Reboot 17.01.4 r3560-79f57e422d\n" +
                                                        "    \n" +
                                                        "   </footer>\n" +
                                                        "   </div>\n" +
                                                        "  </div>\n" +
                                                        " </body>\n" +
                                                        "</html>\n" +
                                                        "\n" +
                                                        "\n",
                                                Collections.emptyMap()),
                                        "/cgi-bin/luci//admin/system/reboot/call",
                                        new HttpHandler(
                                                "token=e82ebba30c29bf4e6b493684192c44a7",
                                                ImmutableMap.of(
                                                        "Cookie",
                                                        "sysauth=c57ede0698febf098dca307d106376d0"),
                                                "",
                                                Collections.emptyMap()))
                        },
                        {
                                // Whatsminer M31S (new firmware)
                                ImmutableMap.of(
                                        "{\"cmd\":\"get_token\"}",
                                        new RpcHandler(
                                                "{\"STATUS\":\"S\",\"When\":1608417125,\"Code\":134,\"Msg\":{\"time\":\"6915\",\"salt\":\"BQ5hoXV9\",\"newsalt\":\"a5TtWui2\"},\"Description\":\"whatsminer v1.1\"}"),
                                        "{\"enc\":1,\"data\":\"MJV7XUNNUwcdojW1WvDrguoJ4Jd05ZIMAiOINboOTESntZQKCfEufFWD85OlIohvGB1cSXiCejkvXuMl3bS0Jw==\"}",
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
                                                                                Map.class)))))),
                                Collections.emptyMap()
                        }
                });
    }
}
