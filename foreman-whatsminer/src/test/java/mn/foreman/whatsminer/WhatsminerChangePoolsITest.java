package mn.foreman.whatsminer;

import mn.foreman.util.AbstractAsyncActionITest;
import mn.foreman.util.TestUtils;
import mn.foreman.util.http.FakeHttpMinerServer;
import mn.foreman.util.http.HttpHandler;
import mn.foreman.util.http.ServerHandler;
import mn.foreman.util.rpc.FakeRpcMinerServer;
import mn.foreman.util.rpc.HandlerInterface;
import mn.foreman.util.rpc.RpcHandler;

import com.google.common.collect.ImmutableMap;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

/** Tests changing pools on a Whatsminer. */
@RunWith(Parameterized.class)
public class WhatsminerChangePoolsITest
        extends AbstractAsyncActionITest {

    /**
     * Constructor.
     *
     * @param rpcHandlers  The RPC handlers.
     * @param httpHandlers The HTTP handlers.
     * @param json         The test json.
     */
    public WhatsminerChangePoolsITest(
            final Map<String, HandlerInterface> rpcHandlers,
            final Map<String, ServerHandler> httpHandlers,
            final Map<String, Object> json) {
        super(
                8080,
                4028,
                new WhatsminerFirmwareAwareAction(
                        new WhatsminerChangePoolsActionOld(),
                        new WhatsminerChangePoolsActionNew()),
                Arrays.asList(
                        () -> new FakeRpcMinerServer(
                                4028,
                                rpcHandlers),
                        () -> new FakeHttpMinerServer(
                                8080,
                                httpHandlers)),
                new WhatsminerFactory(),
                json,
                true);
    }

    /**
     * Test parameters
     *
     * @return The test parameters.
     */
    @Parameterized.Parameters
    public static Collection<Object[]> parameters() {
        return Arrays.asList(
                new Object[][]{
                        {
                                // Old firmware
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
                                        "/cgi-bin/luci/admin/network/cgminer",
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
                                        "/cgi-bin/luci/admin/network/cgminer/alt",
                                        new HttpHandler(
                                                "--FE88TWd2BJhcA6OQo2vVeNnHCsE_7l58U6UyXVZF\r\n" +
                                                        "Content-Disposition: form-data; name=\"cbi.submit\"\r\n" +
                                                        "Content-Type: text/plain; charset=ISO-8859-1\r\n" +
                                                        "Content-Transfer-Encoding: 8bit\r\n" +
                                                        "\r\n" +
                                                        "1\r\n" +
                                                        "--FE88TWd2BJhcA6OQo2vVeNnHCsE_7l58U6UyXVZF\r\n" +
                                                        "Content-Disposition: form-data; name=\"cbid.pools.default.coin_type\"\r\n" +
                                                        "Content-Type: text/plain; charset=ISO-8859-1\r\n" +
                                                        "Content-Transfer-Encoding: 8bit\r\n" +
                                                        "\r\n" +
                                                        "BTC\r\n" +
                                                        "--FE88TWd2BJhcA6OQo2vVeNnHCsE_7l58U6UyXVZF\r\n" +
                                                        "Content-Disposition: form-data; name=\"cbid.pools.default.pool1url\"\r\n" +
                                                        "Content-Type: text/plain; charset=ISO-8859-1\r\n" +
                                                        "Content-Transfer-Encoding: 8bit\r\n" +
                                                        "\r\n" +
                                                        "stratum+tcp://my-test-pool1.com:5588\r\n" +
                                                        "--FE88TWd2BJhcA6OQo2vVeNnHCsE_7l58U6UyXVZF\r\n" +
                                                        "Content-Disposition: form-data; name=\"cbid.pools.default.pool1user\"\r\n" +
                                                        "Content-Type: text/plain; charset=ISO-8859-1\r\n" +
                                                        "Content-Transfer-Encoding: 8bit\r\n" +
                                                        "\r\n" +
                                                        "my-test-username1\r\n" +
                                                        "--FE88TWd2BJhcA6OQo2vVeNnHCsE_7l58U6UyXVZF\r\n" +
                                                        "Content-Disposition: form-data; name=\"cbid.pools.default.pool1pw\"\r\n" +
                                                        "Content-Type: text/plain; charset=ISO-8859-1\r\n" +
                                                        "Content-Transfer-Encoding: 8bit\r\n" +
                                                        "\r\n" +
                                                        "my-test-password1\r\n" +
                                                        "--FE88TWd2BJhcA6OQo2vVeNnHCsE_7l58U6UyXVZF\r\n" +
                                                        "Content-Disposition: form-data; name=\"cbid.pools.default.pool2url\"\r\n" +
                                                        "Content-Type: text/plain; charset=ISO-8859-1\r\n" +
                                                        "Content-Transfer-Encoding: 8bit\r\n" +
                                                        "\r\n" +
                                                        "stratum+tcp://my-test-pool2.com:5588\r\n" +
                                                        "--FE88TWd2BJhcA6OQo2vVeNnHCsE_7l58U6UyXVZF\r\n" +
                                                        "Content-Disposition: form-data; name=\"cbid.pools.default.pool2user\"\r\n" +
                                                        "Content-Type: text/plain; charset=ISO-8859-1\r\n" +
                                                        "Content-Transfer-Encoding: 8bit\r\n" +
                                                        "\r\n" +
                                                        "my-test-username2\r\n" +
                                                        "--FE88TWd2BJhcA6OQo2vVeNnHCsE_7l58U6UyXVZF\r\n" +
                                                        "Content-Disposition: form-data; name=\"cbid.pools.default.pool2pw\"\r\n" +
                                                        "Content-Type: text/plain; charset=ISO-8859-1\r\n" +
                                                        "Content-Transfer-Encoding: 8bit\r\n" +
                                                        "\r\n" +
                                                        "my-test-password2\r\n" +
                                                        "--FE88TWd2BJhcA6OQo2vVeNnHCsE_7l58U6UyXVZF\r\n" +
                                                        "Content-Disposition: form-data; name=\"cbid.pools.default.pool3url\"\r\n" +
                                                        "Content-Type: text/plain; charset=ISO-8859-1\r\n" +
                                                        "Content-Transfer-Encoding: 8bit\r\n" +
                                                        "\r\n" +
                                                        "stratum+tcp://my-test-pool3.com:5588\r\n" +
                                                        "--FE88TWd2BJhcA6OQo2vVeNnHCsE_7l58U6UyXVZF\r\n" +
                                                        "Content-Disposition: form-data; name=\"cbid.pools.default.pool3user\"\r\n" +
                                                        "Content-Type: text/plain; charset=ISO-8859-1\r\n" +
                                                        "Content-Transfer-Encoding: 8bit\r\n" +
                                                        "\r\n" +
                                                        "my-test-username3\r\n" +
                                                        "--FE88TWd2BJhcA6OQo2vVeNnHCsE_7l58U6UyXVZF\r\n" +
                                                        "Content-Disposition: form-data; name=\"cbid.pools.default.pool3pw\"\r\n" +
                                                        "Content-Type: text/plain; charset=ISO-8859-1\r\n" +
                                                        "Content-Transfer-Encoding: 8bit\r\n" +
                                                        "\r\n" +
                                                        "my-test-password3\r\n" +
                                                        "--FE88TWd2BJhcA6OQo2vVeNnHCsE_7l58U6UyXVZF\r\n" +
                                                        "Content-Disposition: form-data; name=\"cbi.apply\"\r\n" +
                                                        "Content-Type: text/plain; charset=ISO-8859-1\r\n" +
                                                        "Content-Transfer-Encoding: 8bit\r\n" +
                                                        "\r\n" +
                                                        "Save & Apply\r\n" +
                                                        "--FE88TWd2BJhcA6OQo2vVeNnHCsE_7l58U6UyXVZF\r\n" +
                                                        "Content-Disposition: form-data; name=\"token\"\r\n" +
                                                        "Content-Type: text/plain; charset=ISO-8859-1\r\n" +
                                                        "Content-Transfer-Encoding: 8bit\r\n" +
                                                        "\r\n" +
                                                        "dfa7be2b0bde2c5270e5d1ebf4f48ef6\r\n" +
                                                        "--FE88TWd2BJhcA6OQo2vVeNnHCsE_7l58U6UyXVZF--\r\n",
                                                ImmutableMap.of(
                                                        "Cookie",
                                                        "sysauth=c57ede0698febf098dca307d106376d0"),
                                                "",
                                                Collections.emptyMap()),
                                        "/cgi-bin/luci/servicectl/restart/pools",
                                        new HttpHandler(
                                                "token=dfa7be2b0bde2c5270e5d1ebf4f48ef6",
                                                ImmutableMap.of(
                                                        "Cookie",
                                                        "sysauth=c57ede0698febf098dca307d106376d0"),
                                                "",
                                                Collections.emptyMap())),
                                TestUtils.toPoolJson(
                                        ImmutableMap.of(
                                                "test",
                                                true,
                                                "boundary",
                                                "FE88TWd2BJhcA6OQo2vVeNnHCsE_7l58U6UyXVZF"))
                        },
                        {
                                // New firmware
                                ImmutableMap.of(
                                        "{\"cmd\":\"get_token\"}",
                                        new RpcHandler(
                                                "{\"STATUS\":\"S\",\"When\":1608417125,\"Code\":134,\"Msg\":{\"time\":\"6915\",\"salt\":\"BQ5hoXV9\",\"newsalt\":\"a5TtWui2\"},\"Description\":\"whatsminer v1.1\"}"),
                                        "{\"enc\":1,\"data\":\"R5OGMYYYx2LtfDiwrwsQbdfSHzqnQdVxlDVmQFgdObhwa3zyWqoVkYxngY74kXe2goWcX8cTc8GcjaoREMBsE0aZTUO9DsdZLMdTaucglOTfd+/VGqiR4C3iiIwH4Edf6np14rTtVZlA2+eo0vVj03D8aMu3AcSns2n0wLjoBEyPZ1R6IPFfu83/DEDDOYd/aL7YbQfjUSeqSYaZAhEMpD0BUut62qJZTXPHdGWXrpqnLBj4Z9kvXB7uBR+7ThuFoHv3byowrVg8JAv4bz3RpPfn7Y9M55zkrkuUWOdMi4gq3RdxhOcLOMAxv2TnPQFaSB8dZwXBoa8vVwi2AJ7djN2VPiFxi2YCRIl+31bUKw1eh2q8+y5li9+j5PtU5dyF9C+TRv0B1X+JJr/neoS7tDXgq0Qn8JGMCoTdn6PsJm5+SIj61qtHlGyq3aalpbb5OENSNUQ/ROVi2N/YD/0tVUHLSqkq2Mnio4Vuv/KkBRFTxo0JOly1mcEw6iUdamMt\"}",
                                        new RpcHandler(
                                                "{\"enc\":\"99J8w83zIqB3/n9c5GlK0Ms8rMLrtR4I9L3mL3ihmpoy4J0v+AHcKtijbyE92q22MIKC6VKEUcYL2KQS50euQWMqSBlxCPx8YYy4oRkOE4C7kjyvgs9rRMH/mhiVH9k+\"}"),
                                        "{\"cmd\":\"summary\"}",
                                        new RpcHandler(
                                                "{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1606349344,\"Code\":11,\"Msg\":\"Summary\",\"Description\":\"cgminer 4.9.2\"}],\"SUMMARY\":[{\"Elapsed\":271950,\"MHS av\":78130188.72,\"MHS 5s\":78398752.21,\"MHS 1m\":78226283.81,\"MHS 5m\":78062260.38,\"MHS 15m\":78035812.17,\"Found Blocks\":0,\"Getworks\":9983,\"Accepted\":57681,\"Rejected\":23,\"Hardware Errors\":9653,\"Utility\":12.73,\"Discarded\":373254,\"Stale\":0,\"Get Failures\":0,\"Local Work\":2771704359,\"Remote Failures\":0,\"Network Blocks\":462,\"Total MH\":21247477854851.0000,\"Work Utility\":17054.14,\"Difficulty Accepted\":4940471970.00000000,\"Difficulty Rejected\":1908584.00000000,\"Difficulty Stale\":0.00000000,\"Best Share\":3846739891,\"Temperature\":70.00,\"freq_avg\":715,\"Fan Speed In\":3330,\"Fan Speed Out\":2760,\"Voltage\":1202,\"Power\":3583,\"Power_RT\":3584,\"Device Hardware%\":0.0125,\"Device Rejected%\":2.4691,\"Pool Rejected%\":0.0386,\"Pool Stale%\":0.0000,\"Last getwork\":0,\"Uptime\":273220,\"Chip Data\":\"HPAP04-20062018 BINV02-193004G\",\"Power Current\":284500,\"Power Fanspeed\":7520,\"Error Code Count\":0,\"Factory Error Code Count\":0,\"Security Mode\":0,\"Liquid Cooling\":false,\"Hash Stable\":true,\"Hash Stable Cost Seconds\":2171,\"Hash Deviation%\":-0.1253,\"Target Freq\":652,\"Target MHS\":71472240,\"Env Temp\":16.00,\"Power Mode\":\"Normal\",\"Firmware Version\":\"'20200722.19.REL'\",\"MAC\":\"C6:06:12:00:BB:AA\",\"Factory GHS\":75993,\"Power Limit\":3600,\"Power Voltage Input\":231.50,\"Power Current Input\":15.58,\"Chip Temp Min\":49.00,\"Chip Temp Max\":94.39,\"Chip Temp Avg\":78.49,\"Debug\":\"14.0_3564_344\"}],\"id\":1}"),
                                        "{\"cmd\":\"edevs\"}",
                                        new RpcHandler(
                                                "{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1606349344,\"Code\":9,\"Msg\":\"3 ASC(s)\",\"Description\":\"cgminer 4.9.2\"}],\"DEVS\":[{\"ASC\":0,\"Name\":\"SM\",\"ID\":0,\"Slot\":0,\"Enabled\":\"Y\",\"Status\":\"Alive\",\"Temperature\":65.50,\"Chip Frequency\":718,\"Fan Speed In\":3330,\"Fan Speed Out\":2760,\"MHS av\":26128368.77,\"MHS 5s\":25432047.72,\"MHS 1m\":26121012.12,\"MHS 5m\":26082216.82,\"MHS 15m\":26075094.58,\"Accepted\":19306,\"Rejected\":5,\"Hardware Errors\":3449,\"Utility\":4.26,\"Last Share Pool\":0,\"Last Share Time\":1606349340,\"Total MH\":7105605360540.0000,\"Diff1 Work\":25850013,\"Difficulty Accepted\":1656092402.00000000,\"Difficulty Rejected\":426521.00000000,\"Last Share Difficulty\":87022.00000000,\"Last Valid Work\":1606349344,\"Device Hardware%\":0.0133,\"Device Rejected%\":1.6500,\"Device Elapsed\":271950,\"Upfreq Complete\":1,\"Effective Chips\":105,\"PCB SN\":\"BAM1FS69630713X30153\",\"Chip Data\":\"HPAP04-20062018 BINV02-193004G\",\"Chip Temp Min\":56.00,\"Chip Temp Max\":86.83,\"Chip Temp Avg\":75.96,\"chip_vol_diff\":14},{\"ASC\":1,\"Name\":\"SM\",\"ID\":1,\"Slot\":1,\"Enabled\":\"Y\",\"Status\":\"Alive\",\"Temperature\":65.50,\"Chip Frequency\":708,\"Fan Speed In\":3330,\"Fan Speed Out\":2760,\"MHS av\":25775347.26,\"MHS 5s\":25589195.43,\"MHS 1m\":25597242.82,\"MHS 5m\":25678145.03,\"MHS 15m\":25720275.43,\"Accepted\":18941,\"Rejected\":7,\"Hardware Errors\":3198,\"Utility\":4.18,\"Last Share Pool\":0,\"Last Share Time\":1606349340,\"Total MH\":7009601228708.0000,\"Diff1 Work\":25500772,\"Difficulty Accepted\":1617996433.00000000,\"Difficulty Rejected\":573971.00000000,\"Last Share Difficulty\":87022.00000000,\"Last Valid Work\":1606349344,\"Device Hardware%\":0.0125,\"Device Rejected%\":2.2508,\"Device Elapsed\":271950,\"Upfreq Complete\":1,\"Effective Chips\":105,\"PCB SN\":\"BAM1FS69630713X30150\",\"Chip Data\":\"HPAP04-20062018 BINV02-193004G\",\"Chip Temp Min\":56.00,\"Chip Temp Max\":85.67,\"Chip Temp Avg\":75.17,\"chip_vol_diff\":13},{\"ASC\":2,\"Name\":\"SM\",\"ID\":2,\"Slot\":2,\"Enabled\":\"Y\",\"Status\":\"Alive\",\"Temperature\":70.00,\"Chip Frequency\":719,\"Fan Speed In\":3330,\"Fan Speed Out\":2760,\"MHS av\":26226459.61,\"MHS 5s\":26729036.38,\"MHS 1m\":26446959.21,\"MHS 5m\":26290334.58,\"MHS 15m\":26236481.74,\"Accepted\":19434,\"Rejected\":11,\"Hardware Errors\":3006,\"Utility\":4.29,\"Last Share Pool\":0,\"Last Share Time\":1606349335,\"Total MH\":7132281161207.0000,\"Diff1 Work\":25947054,\"Difficulty Accepted\":1666383135.00000000,\"Difficulty Rejected\":908092.00000000,\"Last Share Difficulty\":87022.00000000,\"Last Valid Work\":1606349344,\"Device Hardware%\":0.0116,\"Device Rejected%\":3.4998,\"Device Elapsed\":271950,\"Upfreq Complete\":1,\"Effective Chips\":105,\"PCB SN\":\"B5M1FS69630717K31306\",\"Chip Data\":\"HP5A04-20070734 BINV02-193004G\",\"Chip Temp Min\":49.00,\"Chip Temp Max\":94.39,\"Chip Temp Avg\":84.34,\"chip_vol_diff\":14}],\"id\":1}"),
                                        "{\"cmd\":\"pools\"}",
                                        new RpcHandler(
                                                "{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1606349344,\"Code\":7,\"Msg\":\"3 Pool(s)\",\"Description\":\"cgminer 4.9.2\"}],\"POOLS\":[{\"POOL\":0,\"URL\":\"stratum+tcp://ru-west.stratum.slushpool.com:3333\",\"Status\":\"Alive\",\"Priority\":0,\"Quota\":1,\"Long Poll\":\"N\",\"Getworks\":9983,\"Accepted\":57681,\"Rejected\":23,\"Works\":-1520006932,\"Discarded\":373254,\"Stale\":0,\"Get Failures\":0,\"Remote Failures\":0,\"User\":\"xxx\",\"Last Share Time\":1606349340,\"Diff1 Shares\":77297833,\"Proxy Type\":\"\",\"Proxy\":\"\",\"Difficulty Accepted\":4940471970.00000000,\"Difficulty Rejected\":1908584.00000000,\"Difficulty Stale\":0.00000000,\"Last Share Difficulty\":87022.00000000,\"Work Difficulty\":0.00000000,\"Has Stratum\":true,\"Stratum Active\":true,\"Stratum URL\":\"ru-west.stratum.slushpool.com\",\"Stratum Difficulty\":87022.00000000,\"Has GBT\":false,\"Best Share\":3846739891,\"Pool Rejected%\":0.0386,\"Pool Stale%\":0.0000,\"Bad Work\":462,\"Current Block Height\":658678,\"Current Block Version\":536870912},{\"POOL\":1,\"URL\":\"stratum+tcp://eu.stratum.slushpool.com:3333\",\"Status\":\"Alive\",\"Priority\":1,\"Quota\":1,\"Long Poll\":\"N\",\"Getworks\":0,\"Accepted\":0,\"Rejected\":0,\"Works\":0,\"Discarded\":0,\"Stale\":0,\"Get Failures\":0,\"Remote Failures\":0,\"User\":\"xxx\",\"Last Share Time\":0,\"Diff1 Shares\":0,\"Proxy Type\":\"\",\"Proxy\":\"\",\"Difficulty Accepted\":0.00000000,\"Difficulty Rejected\":0.00000000,\"Difficulty Stale\":0.00000000,\"Last Share Difficulty\":0.00000000,\"Work Difficulty\":0.00000000,\"Has Stratum\":true,\"Stratum Active\":false,\"Stratum URL\":\"\",\"Stratum Difficulty\":0.00000000,\"Has GBT\":false,\"Best Share\":0,\"Pool Rejected%\":0.0000,\"Pool Stale%\":0.0000,\"Bad Work\":0,\"Current Block Height\":0,\"Current Block Version\":536870912},{\"POOL\":2,\"URL\":\"stratum+tcp://eu.stratum.slushpool.com:3333\",\"Status\":\"Alive\",\"Priority\":2,\"Quota\":1,\"Long Poll\":\"N\",\"Getworks\":0,\"Accepted\":0,\"Rejected\":0,\"Works\":0,\"Discarded\":0,\"Stale\":0,\"Get Failures\":0,\"Remote Failures\":0,\"User\":\"xxx\",\"Last Share Time\":0,\"Diff1 Shares\":0,\"Proxy Type\":\"\",\"Proxy\":\"\",\"Difficulty Accepted\":0.00000000,\"Difficulty Rejected\":0.00000000,\"Difficulty Stale\":0.00000000,\"Last Share Difficulty\":0.00000000,\"Work Difficulty\":0.00000000,\"Has Stratum\":true,\"Stratum Active\":false,\"Stratum URL\":\"\",\"Stratum Difficulty\":0.00000000,\"Has GBT\":false,\"Best Share\":0,\"Pool Rejected%\":0.0000,\"Pool Stale%\":0.0000,\"Bad Work\":0,\"Current Block Height\":0,\"Current Block Version\":536870912}],\"id\":1}")),
                                Collections.emptyMap(),
                                TestUtils.toPoolJson(
                                        ImmutableMap.of(
                                                "test",
                                                true,
                                                "boundary",
                                                "FE88TWd2BJhcA6OQo2vVeNnHCsE_7l58U6UyXVZF"))
                        }
                });
    }
}
