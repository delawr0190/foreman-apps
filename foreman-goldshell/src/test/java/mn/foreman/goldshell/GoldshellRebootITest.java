package mn.foreman.goldshell;

import mn.foreman.util.AbstractAsyncActionITest;
import mn.foreman.util.http.FakeHttpMinerServer;
import mn.foreman.util.http.HttpHandler;

import com.google.common.collect.ImmutableMap;

import java.util.Collections;
import java.util.concurrent.TimeUnit;

/** Test rebooting a goldshell. */
public class GoldshellRebootITest
        extends AbstractAsyncActionITest {

    /** Constructor. */
    public GoldshellRebootITest() {
        super(
                8080,
                8080,
                new GoldshellRebootAction(
                        1,
                        TimeUnit.SECONDS),
                Collections.singletonList(
                        () -> new FakeHttpMinerServer(
                                8080,
                                ImmutableMap.of(
                                        "/mcb/restart",
                                        new HttpHandler(
                                                "",
                                                ""),
                                        "/mcb/cgminer",
                                        new HttpHandler(
                                                "cgminercmd=devs",
                                                "{\n" +
                                                        "    \"status\": 2,\n" +
                                                        "    \"data\": [\n" +
                                                        "        {\n" +
                                                        "            \"valid\": 15,\n" +
                                                        "            \"av_hashrate\": 3051310.1080000001,\n" +
                                                        "            \"rejected\": 4434,\n" +
                                                        "            \"time\": 1293,\n" +
                                                        "            \"id\": 0,\n" +
                                                        "            \"powerplan\": 0,\n" +
                                                        "            \"hwerrors\": 497,\n" +
                                                        "            \"fanspeed\": \"2940 rpm / 2940 rpm / 2940 rpm / 2940 rpm\",\n" +
                                                        "            \"accepted\": 222312,\n" +
                                                        "            \"hwerr_ration\": 0.0021679999999999997,\n" +
                                                        "            \"nonces\": 227243,\n" +
                                                        "            \"hashrate\": 2992548.7789999998,\n" +
                                                        "            \"temp\": \"83.0 Â°C / 73.8 Â°C\",\n" +
                                                        "            \"minerstatus\": 0\n" +
                                                        "        },\n" +
                                                        "        {\n" +
                                                        "            \"valid\": 15,\n" +
                                                        "            \"av_hashrate\": 3058388.8669999999,\n" +
                                                        "            \"rejected\": 4353,\n" +
                                                        "            \"time\": 1293,\n" +
                                                        "            \"id\": 1,\n" +
                                                        "            \"powerplan\": 0,\n" +
                                                        "            \"hwerrors\": 224,\n" +
                                                        "            \"fanspeed\": \"2940 rpm / 2940 rpm / 2940 rpm / 2940 rpm\",\n" +
                                                        "            \"accepted\": 222918,\n" +
                                                        "            \"hwerr_ration\": 0.000983,\n" +
                                                        "            \"nonces\": 227496,\n" +
                                                        "            \"hashrate\": 3240137.4839999998,\n" +
                                                        "            \"temp\": \"83.0 Â°C / 73.9 Â°C\",\n" +
                                                        "            \"minerstatus\": 0\n" +
                                                        "        },\n" +
                                                        "        {\n" +
                                                        "            \"valid\": 15,\n" +
                                                        "            \"av_hashrate\": 3048403.4069999999,\n" +
                                                        "            \"rejected\": 4528,\n" +
                                                        "            \"time\": 1293,\n" +
                                                        "            \"id\": 2,\n" +
                                                        "            \"powerplan\": 0,\n" +
                                                        "            \"hwerrors\": 189,\n" +
                                                        "            \"fanspeed\": \"2940 rpm / 2940 rpm / 2940 rpm / 2940 rpm\",\n" +
                                                        "            \"accepted\": 222004,\n" +
                                                        "            \"hwerr_ration\": 0.000837,\n" +
                                                        "            \"nonces\": 226722,\n" +
                                                        "            \"hashrate\": 3205471.7319999999,\n" +
                                                        "            \"temp\": \"84.0 Â°C / 73.5 Â°C\",\n" +
                                                        "            \"minerstatus\": 0\n" +
                                                        "        },\n" +
                                                        "        {\n" +
                                                        "            \"valid\": 15,\n" +
                                                        "            \"av_hashrate\": 3061376.3110000002,\n" +
                                                        "            \"rejected\": 4445,\n" +
                                                        "            \"time\": 1293,\n" +
                                                        "            \"id\": 3,\n" +
                                                        "            \"powerplan\": 0,\n" +
                                                        "            \"hwerrors\": 151,\n" +
                                                        "            \"fanspeed\": \"2940 rpm / 2940 rpm / 2940 rpm / 2940 rpm\",\n" +
                                                        "            \"accepted\": 223048,\n" +
                                                        "            \"hwerr_ration\": 0.000675,\n" +
                                                        "            \"nonces\": 227645,\n" +
                                                        "            \"hashrate\": 3013904.3689999998,\n" +
                                                        "            \"temp\": \"84.0 Â°C / 73.9 Â°C\",\n" +
                                                        "            \"minerstatus\": 0\n" +
                                                        "        }\n" +
                                                        "    ]\n" +
                                                        "}"),
                                        "/mcb/pools",
                                        new HttpHandler(
                                                "",
                                                "[\n" +
                                                        "    {\n" +
                                                        "        \"url\": \"stratum+tcp://us-ckb.2miners.com:6464\",\n" +
                                                        "        \"legal\": true,\n" +
                                                        "        \"active\": true,\n" +
                                                        "        \"dragid\": 0,\n" +
                                                        "        \"user\": \"[REDACTED]\",\n" +
                                                        "        \"pool-priority\": 0,\n" +
                                                        "        \"pass\": \"[REDACTED]\"\n" +
                                                        "    },\n" +
                                                        "    {\n" +
                                                        "        \"url\": \"stratum+tcp://ckb.f2pool.com:4300\",\n" +
                                                        "        \"legal\": true,\n" +
                                                        "        \"active\": false,\n" +
                                                        "        \"dragid\": 1,\n" +
                                                        "        \"user\": \"[REDACTED]\",\n" +
                                                        "        \"pool-priority\": 1,\n" +
                                                        "        \"pass\": \"[REDACTED]\"\n" +
                                                        "    }\n" +
                                                        "]")))),
                new GoldshellFactory(
                        1,
                        TimeUnit.SECONDS),
                Collections.emptyMap(),
                true);
    }
}