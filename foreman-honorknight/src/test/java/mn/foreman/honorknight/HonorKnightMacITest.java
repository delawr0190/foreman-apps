package mn.foreman.honorknight;

import mn.foreman.util.AbstractMacITest;
import mn.foreman.util.http.FakeHttpMinerServer;
import mn.foreman.util.http.HttpHandler;

import com.google.common.collect.ImmutableMap;

import java.util.Collections;

/** Tests obtaining a MAC address from an honorknight. */
public class HonorKnightMacITest
        extends AbstractMacITest {

    /** Constructor. */
    public HonorKnightMacITest() {
        super(
                new HonorKnightFactory(),
                Collections.singletonList(
                        new FakeHttpMinerServer(
                                8080,
                                ImmutableMap.of(
                                        "/api/overview",
                                        new HttpHandler(
                                                "",
                                                "{\n" +
                                                        "  \"decorator\": \"none\",\n" +
                                                        "  \"minerInfo\": {\n" +
                                                        "    \"usageMem\": 157,\n" +
                                                        "    \"os\": \"Linux\",\n" +
                                                        "    \"releaseDate\": \"20190903\",\n" +
                                                        "    \"totalMem\": 241,\n" +
                                                        "    \"ip\": \"192.168.123.101\",\n" +
                                                        "    \"minerVersion\": \"K2.1_20190903_122158\",\n" +
                                                        "    \"upTime\": \"2min \",\n" +
                                                        "    \"macAddress\": \"00:BF:B1:67:B5:D6\\n\",\n" +
                                                        "    \"protocol\": \"static\",\n" +
                                                        "    \"kernelVersion\": \"Linux rockchip 4.4.143 #4 SMP PREEMPT Thu May 23 16:06:17 CST 2019 aarch64 GNU/Linux\",\n" +
                                                        "    \"model\": \"K2.1\",\n" +
                                                        "    \"hardwareVersion\": \"v40\",\n" +
                                                        "    \"availableMem\": 145,\n" +
                                                        "    \"cacheMem\": 60,\n" +
                                                        "    \"sn\": \"ZTF1190605001886\",\n" +
                                                        "    \"loadAverage\": \"0.29 0.12 0.04 2/132 471\\n\"\n" +
                                                        "  },\n" +
                                                        "  \"ctx\": \"\",\n" +
                                                        "  \"success\": true,\n" +
                                                        "  \"cxt\": \"http://127.0.0.1:88\",\n" +
                                                        "  \"timestamp\": 1608704107816\n" +
                                                        "}")))),
                "00:BF:B1:67:B5:D6");
    }
}
