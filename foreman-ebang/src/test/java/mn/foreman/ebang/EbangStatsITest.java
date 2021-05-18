package mn.foreman.ebang;

import mn.foreman.model.miners.FanInfo;
import mn.foreman.model.miners.MinerStats;
import mn.foreman.model.miners.Pool;
import mn.foreman.model.miners.asic.Asic;
import mn.foreman.util.AbstractApiITest;
import mn.foreman.util.http.FakeHttpMinerServer;
import mn.foreman.util.http.HttpHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

/** Runs an integration tests using {@link Ebang} against a fake API. */
public class EbangStatsITest
        extends AbstractApiITest {

    /** Constructor. */
    public EbangStatsITest() {
        super(
                new EbangFactory(
                        1,
                        TimeUnit.SECONDS,
                        new ObjectMapper())
                        .create(
                                ImmutableMap.<String, Object>builder()
                                        .put(
                                                "apiIp",
                                                "127.0.0.1")
                                        .put(
                                                "apiPort",
                                                "8080")
                                        .put(
                                                "username",
                                                "username")
                                        .put(
                                                "password",
                                                "password")
                                        .put(
                                                "statsWhitelist",
                                                Arrays.asList(
                                                        "DEVS.0.Temperature",
                                                        "DEVS.0.MHS av"))
                                        .build()),
                new FakeHttpMinerServer(
                        8080,
                        ImmutableMap.of(
                                "/user/login",
                                new HttpHandler(
                                        "username=username&word=password&yuyan=1&login=Login&get_password=",
                                        Collections.emptyMap(),
                                        "<html></html>",
                                        ImmutableMap.of(
                                                "Set-Cookie",
                                                "-http-session-=1::http.session::647531ff4c6d47243a8ed9a41a26e473; path=/; domain=127.0.0.1; httponly")),
                                "/Cgminer/CgminerGetVal",
                                new HttpHandler(
                                        "",
                                        ImmutableMap.of(
                                                "Cookie",
                                                "-http-session-=1::http.session::647531ff4c6d47243a8ed9a41a26e473"),
                                        "{\"status\": 1, \"feedback\": {\"Mpassword3\":\"\",\"Mip3\":\"stratum+tcp://btc.ss.poolin.com:25\",\"Mwork3\":\"\",\"Mpassword2\":\"\",\"Mip2\":\"stratum+tcp://yyy.com:1883\",\"Mip1\":\"stratum+tcp://aaa.com:3001\",\"Mwork2\":\"\",\"Mpassword1\":\"\",\"Mwork1\":\"111\"}}",
                                        Collections.emptyMap()),
                                "/Cgminer/CgminerStatus",
                                new HttpHandler(
                                        "",
                                        ImmutableMap.of(
                                                "Cookie",
                                                "-http-session-=1::http.session::647531ff4c6d47243a8ed9a41a26e473"),
                                        "{\"status\": 1, \"feedback\": {\"currentpooltime\":\"103:20:45\",\"rejected\":265,\"findblock\":0,\"accepted\":2033,\"averfigure\":\"12.28T\",\"currentaccident\":8192.00000000,\"getworks\":35361,\"fiveavg\":\"12.51T\",\"harderrornumber\":0.0002,\"worktime\":\"123:6:37\",\"fivesecfigure\":\"12.44T\",\"cgminerstatus\":\"stratum+tcp://aaa.com:3001\"}}",
                                        Collections.emptyMap()),
                                "/alarm/GetAlarmLoop",
                                new HttpHandler(
                                        "",
                                        ImmutableMap.of(
                                                "Cookie",
                                                "-http-session-=1::http.session::647531ff4c6d47243a8ed9a41a26e473"),
                                        "{\"status\": 1, \"feedback\": {\"eth1Alarm\":0,\"calValue\":12526.698000,\"pllValue\":785,\"tmpValue\":60,\"calAlarm\":0,\"poolAlarm\":0,\"tempAlarm\":0}}",
                                        Collections.emptyMap()),
                                "/Status/getsystemstatus",
                                new HttpHandler(
                                        "",
                                        ImmutableMap.of(
                                                "Cookie",
                                                "-http-session-=1::http.session::647531ff4c6d47243a8ed9a41a26e473"),
                                        "{\"status\": 1, \"feedback\": {\"memoryfree\":71,\"CPUavailability\":99,\"systemsoftwareversion\":\"soft_v9.1.0.32\",\"devicefan\":5520,\"productname\":\"DWM_BTC1000\",\"devicefan2\":5640,\"device4temp\":60,\"device3temp\":0,\"bank1snmpversion\":\"soft_v9.1.0.32\",\"ethrate\":\"7394b/s\",\"bank2snmpversion\":\"soft_v9.1.0.34\",\"devicepn\":\"**********\",\"device1temp\":\"60,55|55,51\",\"fpgawareversion\":\"Fpga v1.0.13\",\"Productdescription\":\"The Miner Control Device\",\"hardwareversion\":\"EBAZ4303-13-1.1.1.1\",\"flashResidualspace\":57,\"device2temp\":\"55,52|58,54\"}}",
                                        Collections.emptyMap()))),
                new MinerStats.Builder()
                        .setApiIp("127.0.0.1")
                        .setApiPort(8080)
                        .addPool(
                                new Pool.Builder()
                                        .setName("aaa.com:3001")
                                        .setWorker("111")
                                        .setPriority(0)
                                        .setStatus(
                                                true,
                                                true)
                                        .setCounts(
                                                2033,
                                                265,
                                                0)
                                        .build())
                        .addAsic(
                                new Asic.Builder()
                                        .setHashRate(new BigDecimal("12526698000000.000000"))
                                        .setFanInfo(
                                                new FanInfo.Builder()
                                                        .setCount(2)
                                                        .addSpeed(5520)
                                                        .addSpeed(5640)
                                                        .setSpeedUnits("RPM")
                                                        .build())
                                        .addTemp(60)
                                        .addTemp(55)
                                        .addTemp(55)
                                        .addTemp(51)
                                        .addTemp(60)
                                        .build())
                        .build());
    }
}