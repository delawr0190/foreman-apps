package mn.foreman.ebang;

import mn.foreman.util.AbstractAsyncActionITest;
import mn.foreman.util.http.FakeHttpMinerServer;
import mn.foreman.util.http.HttpHandler;
import mn.foreman.util.http.ServerHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;

import java.util.Collections;
import java.util.concurrent.TimeUnit;

/** Test factory resetting an ebang. */
public class EbangFactoryResetITest
        extends AbstractAsyncActionITest {

    /** Constructor. */
    public EbangFactoryResetITest() {
        super(
                8080,
                8080,
                new EbangFactoryResetAction(
                        1,
                        TimeUnit.SECONDS),
                Collections.singletonList(
                        () -> new FakeHttpMinerServer(
                                8080,
                                ImmutableMap.<String, ServerHandler>builder()
                                        .put(
                                                "/user/login",
                                                new HttpHandler(
                                                        "username=my-auth-username&word=my-auth-password&yuyan=1&login=Login&get_password=",
                                                        Collections.emptyMap(),
                                                        "",
                                                        ImmutableMap.of(
                                                                "Set-Cookie",
                                                                "-http-session-=1::http.session::647531ff4c6d47243a8ed9a41a26e473; path=/; domain=127.0.0.1; httponly")))
                                        .put(
                                                "/update/factory_restart",
                                                new HttpHandler(
                                                        "",
                                                        ImmutableMap.of(
                                                                "Cookie",
                                                                "-http-session-=1::http.session::647531ff4c6d47243a8ed9a41a26e473"),
                                                        "",
                                                        Collections.emptyMap()))
                                        .put(
                                                "/Cgminer/CgminerGetVal",
                                                new HttpHandler(
                                                        "",
                                                        ImmutableMap.of(
                                                                "Cookie",
                                                                "-http-session-=1::http.session::647531ff4c6d47243a8ed9a41a26e473"),
                                                        "{\"status\": 1, \"feedback\": {\"Mpassword3\":\"\",\"Mip3\":\"stratum+tcp://btc.ss.poolin.com:25\",\"Mwork3\":\"\",\"Mpassword2\":\"\",\"Mip2\":\"stratum+tcp://yyy.com:1883\",\"Mip1\":\"stratum+tcp://aaa.com:3001\",\"Mwork2\":\"\",\"Mpassword1\":\"\",\"Mwork1\":\"111\"}}",
                                                        Collections.emptyMap()))
                                        .put(
                                                "/Cgminer/CgminerStatus",
                                                new HttpHandler(
                                                        "",
                                                        ImmutableMap.of(
                                                                "Cookie",
                                                                "-http-session-=1::http.session::647531ff4c6d47243a8ed9a41a26e473"),
                                                        "{\"status\": 1, \"feedback\": {\"currentpooltime\":\"103:20:45\",\"rejected\":265,\"findblock\":0,\"accepted\":2033,\"averfigure\":\"12.28T\",\"currentaccident\":8192.00000000,\"getworks\":35361,\"fiveavg\":\"12.51T\",\"harderrornumber\":0.0002,\"worktime\":\"123:6:37\",\"fivesecfigure\":\"12.44T\",\"cgminerstatus\":\"stratum+tcp://aaa.com:3001\"}}",
                                                        Collections.emptyMap()))
                                        .put(
                                                "/alarm/GetAlarmLoop",
                                                new HttpHandler(
                                                        "",
                                                        ImmutableMap.of(
                                                                "Cookie",
                                                                "-http-session-=1::http.session::647531ff4c6d47243a8ed9a41a26e473"),
                                                        "{\"status\": 1, \"feedback\": {\"eth1Alarm\":0,\"calValue\":12526.698000,\"pllValue\":785,\"tmpValue\":60,\"calAlarm\":0,\"poolAlarm\":0,\"tempAlarm\":0}}",
                                                        Collections.emptyMap()))
                                        .put(
                                                "/Status/getsystemstatus",
                                                new HttpHandler(
                                                        "",
                                                        ImmutableMap.of(
                                                                "Cookie",
                                                                "-http-session-=1::http.session::647531ff4c6d47243a8ed9a41a26e473"),
                                                        "{\"status\": 1, \"feedback\": {\"memoryfree\":71,\"CPUavailability\":99,\"systemsoftwareversion\":\"soft_v9.1.0.32\",\"devicefan\":5520,\"productname\":\"DWM_BTC1000\",\"devicefan2\":5640,\"device4temp\":60,\"device3temp\":0,\"bank1snmpversion\":\"soft_v9.1.0.32\",\"ethrate\":\"7394b/s\",\"bank2snmpversion\":\"soft_v9.1.0.34\",\"devicepn\":\"**********\",\"device1temp\":\"60,55|55,51\",\"fpgawareversion\":\"Fpga v1.0.13\",\"Productdescription\":\"The Miner Control Device\",\"hardwareversion\":\"EBAZ4303-13-1.1.1.1\",\"flashResidualspace\":57,\"device2temp\":\"55,52|58,54\"}}",
                                                        Collections.emptyMap()))
                                        .build())),
                new EbangFactory(
                        1,
                        TimeUnit.SECONDS,
                        new ObjectMapper()),
                Collections.emptyMap(),
                true);
    }
}
