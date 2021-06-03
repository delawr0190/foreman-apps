package mn.foreman.whatsminer;

import mn.foreman.model.ApplicationConfiguration;
import mn.foreman.util.AbstractMacITest;
import mn.foreman.util.rpc.FakeRpcMinerServer;
import mn.foreman.util.rpc.RpcHandler;

import com.google.common.collect.ImmutableMap;

import java.util.Collections;

/** Tests obtaining a MAC from a Whatsminer miner. */
public class WhatsminerMacITest
        extends AbstractMacITest {

    /** Constructor. */
    public WhatsminerMacITest() {
        super(
                new WhatsminerFactory(
                        new ApplicationConfiguration()),
                Collections.singletonList(
                        new FakeRpcMinerServer(
                                4028,
                                ImmutableMap.of(
                                        "{\"command\":\"summary\"}",
                                        new RpcHandler(
                                                "{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1604757733,\"Code\":11,\"Msg\":\"Summary\",\"Description\":\"cgminer 4.9.2\"}],\"SUMMARY\":[{\"Elapsed\":66295,\"MHS av\":68710597.19,\"MHS 5s\":64143777.95,\"MHS 1m\":67266447.69,\"MHS 5m\":67827446.38,\"MHS 15m\":68290183.07,\"Found Blocks\":25,\"Getworks\":10780,\"Accepted\":5018,\"Rejected\":29,\"Hardware Errors\":912,\"Utility\":4.54,\"Discarded\":8046935,\"Stale\":0,\"Get Failures\":0,\"Local Work\":527721521,\"Remote Failures\":0,\"Network Blocks\":4206,\"Total MH\":4555143176276.0000,\"Work Utility\":3749.51,\"Difficulty Accepted\":1071653490.00000000,\"Difficulty Rejected\":5623144.00000000,\"Difficulty Stale\":0.00000000,\"Best Share\":1003626349,\"Temperature\":72.00,\"freq_avg\":622,\"Fan Speed In\":4950,\"Fan Speed Out\":4920,\"Voltage\":1300,\"Power\":3491,\"Power_RT\":3482,\"Device Hardware%\":0.0220,\"Device Rejected%\":135.7304,\"Pool Rejected%\":0.5220,\"Pool Stale%\":0.0000,\"Last getwork\":0,\"Uptime\":66892,\"Chip Data\":\"HP3D04-20010214 C BINV04-192104B\",\"Power Current\":245,\"Power Fanspeed\":8880,\"Error Code Count\":0,\"Factory Error Code Count\":0,\"Security Mode\":0,\"Liquid Cooling\":false,\"Hash Stable\":true,\"Hash Stable Cost Seconds\":1498,\"Hash Deviation%\":-0.3372,\"Target Freq\":622,\"Target MHS\":68765832,\"Env Temp\":28.50,\"Power Mode\":\"Normal\",\"Firmware Version\":\"\\'20200917.22.REL\\'\",\"CB Platform\":\"ALLWINNER_H3\",\"CB Version\":\"V10\",\"MAC\":\"C4:11:04:01:3C:75\",\"Factory GHS\":67051,\"Power Limit\":3500,\"Chip Temp Min\":65.00,\"Chip Temp Max\":87.70,\"Chip Temp Avg\":79.34}],\"id\":1}")))),
                "C4:11:04:01:3C:75");
    }
}
