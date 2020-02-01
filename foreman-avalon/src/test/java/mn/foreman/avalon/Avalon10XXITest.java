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
public class Avalon10XXITest
        extends AbstractApiITest {

    /** Constructor. */
    public Avalon10XXITest() {
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
                                                "      \"When\": 103856,\n" +
                                                "      \"Code\": 11,\n" +
                                                "      \"Msg\": \"Summary\",\n" +
                                                "      \"Description\": \"cgminer 4.11.1\"\n" +
                                                "    }\n" +
                                                "  ],\n" +
                                                "  \"SUMMARY\": [\n" +
                                                "    {\n" +
                                                "      \"Elapsed\": 103806,\n" +
                                                "      \"MHS av\": 36550242.2,\n" +
                                                "      \"MHS 30s\": 34751312.19,\n" +
                                                "      \"MHS 1m\": 35107900.66,\n" +
                                                "      \"MHS 5m\": 36332565.86,\n" +
                                                "      \"MHS 15m\": 36258813.66,\n" +
                                                "      \"Found Blocks\": 0,\n" +
                                                "      \"Getworks\": 8809,\n" +
                                                "      \"Accepted\": 14331,\n" +
                                                "      \"Rejected\": 280,\n" +
                                                "      \"Hardware Errors\": 135,\n" +
                                                "      \"Utility\": 8.28,\n" +
                                                "      \"Discarded\": 0,\n" +
                                                "      \"Stale\": 0,\n" +
                                                "      \"Get Failures\": 0,\n" +
                                                "      \"Local Work\": 1759224,\n" +
                                                "      \"Remote Failures\": 0,\n" +
                                                "      \"Network Blocks\": 549,\n" +
                                                "      \"Total MH\": 3796956022127.0,\n" +
                                                "      \"Work Utility\": 520331.0,\n" +
                                                "      \"Difficulty Accepted\": 879945296.0,\n" +
                                                "      \"Difficulty Rejected\": 16178298.0,\n" +
                                                "      \"Difficulty Stale\": 0.0,\n" +
                                                "      \"Best Share\": 905879849,\n" +
                                                "      \"Device Hardware%\": 0.0,\n" +
                                                "      \"Device Rejected%\": 1.7971,\n" +
                                                "      \"Pool Rejected%\": 1.8054,\n" +
                                                "      \"Pool Stale%\": 0.0,\n" +
                                                "      \"Last getwork\": 0\n" +
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
                                                "      \"When\": 103857,\n" +
                                                "      \"Code\": 7,\n" +
                                                "      \"Msg\": \"1 Pool(s)\",\n" +
                                                "      \"Description\": \"cgminer 4.11.1\"\n" +
                                                "    }\n" +
                                                "  ],\n" +
                                                "  \"POOLS\": [\n" +
                                                "    {\n" +
                                                "      \"POOL\": 0,\n" +
                                                "      \"URL\": \"stratum+tcp://sha256.mining-dutch.nl:9996\",\n" +
                                                "      \"Status\": \"Alive\",\n" +
                                                "      \"Priority\": 0,\n" +
                                                "      \"Quota\": 1,\n" +
                                                "      \"Long Poll\": \"N\",\n" +
                                                "      \"Getworks\": 8809,\n" +
                                                "      \"Accepted\": 14331,\n" +
                                                "      \"Rejected\": 280,\n" +
                                                "      \"Works\": 879230,\n" +
                                                "      \"Discarded\": 0,\n" +
                                                "      \"Stale\": 0,\n" +
                                                "      \"Get Failures\": 0,\n" +
                                                "      \"Remote Failures\": 0,\n" +
                                                "      \"User\": \"xxxx\",\n" +
                                                "      \"Last Share Time\": 103855,\n" +
                                                "      \"Diff1 Shares\": 900237312,\n" +
                                                "      \"Proxy Type\": \"\",\n" +
                                                "      \"Proxy\": \"\",\n" +
                                                "      \"Difficulty Accepted\": 879945296.0,\n" +
                                                "      \"Difficulty Rejected\": 16178298.0,\n" +
                                                "      \"Difficulty Stale\": 0.0,\n" +
                                                "      \"Last Share Difficulty\": 65536.0,\n" +
                                                "      \"Work Difficulty\": 65536.0,\n" +
                                                "      \"Has Stratum\": true,\n" +
                                                "      \"Stratum Active\": true,\n" +
                                                "      \"Stratum URL\": \"sha256.mining-dutch.nl\",\n" +
                                                "      \"Stratum Difficulty\": 65536.0,\n" +
                                                "      \"Has Vmask\": true,\n" +
                                                "      \"Has GBT\": false,\n" +
                                                "      \"Best Share\": 905879849,\n" +
                                                "      \"Pool Rejected%\": 1.8054,\n" +
                                                "      \"Pool Stale%\": 0.0,\n" +
                                                "      \"Bad Work\": 5233,\n" +
                                                "      \"Current Block Height\": 620247,\n" +
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
                                        .setName("sha256.mining-dutch.nl:9996")
                                        .setPriority(0)
                                        .setStatus(
                                                true,
                                                true)
                                        .setCounts(
                                                14331,
                                                280,
                                                0)
                                        .build())
                        .addAsic(
                                new Asic.Builder()
                                        .setHashRate(new BigDecimal("34751312190000.000"))
                                        .setFanInfo(
                                                new FanInfo.Builder()
                                                        .setCount(1)
                                                        .addSpeed(47)
                                                        .setSpeedUnits("%")
                                                        .build())
                                        .addTemp(28)
                                        .addTemp(75)
                                        .build())
                        .build());
    }
}