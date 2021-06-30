package mn.foreman.openminer;

import mn.foreman.model.ApplicationConfiguration;
import mn.foreman.util.AbstractSyncActionITest;
import mn.foreman.util.http.FakeHttpMinerServer;
import mn.foreman.util.http.HttpHandler;

import com.google.common.collect.ImmutableMap;

import java.util.Collections;

/** Tests firmware upgrading a Bitfury B8. */
public class BitfuryB8FirmwareUpgradeActionTest
        extends AbstractSyncActionITest {

    /** Constructor. */
    public BitfuryB8FirmwareUpgradeActionTest() {
        super(
                8080,
                new OpenMinerFirmwareUpgradeAction(
                        new ApplicationConfiguration()),
                Collections.singletonList(
                        () -> new FakeHttpMinerServer(
                                8080,
                                ImmutableMap.of(
                                        "/api/login",
                                        new HttpHandler(
                                                "{\"email\":\"my-auth-username\",\"password\":\"my-auth-password\"}",
                                                "{\"token\":\"eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJpYXQiOjE2MTU1MTI2OTIsImV4cCI6MTYxNTUxNjI5Miwicm9sZXMiOlsiUk9MRV9BRE1JTiJdLCJ1c2VybmFtZSI6InRlc3RAZXhhbXBsZS5jb20ifQ.W-0J1htmayQQ-FbDdmSi0Lejl1nKo-SjIMZ8Vu6GlaVSAW67mUb_5176ug7U38yhySDTcteu4YCsRwJcECdBDBwe9lS7VHBBXjuH4qUdpke34vyjD0wr04S1PNgOB7gYU7sYdahIoQZGIy8ISqORctQAtDDH2g_94ba9EYWJgkINx9t6H-BD2KNSEiP3gWDmB7zEdN7xMQIe93XMU6_Mc5BNS36Nd3nDAt2X3B37XFJ_cWTZuceEc1wYAbDmZszLJXkIykPrY83NSgzSMOOPY9vIpFnbfFM30RWJJ8A6-JFrhegFoWriY9fcsmUoIrkVWab03bcrn8rbXcOJbN6iHI3PSOIyH45StYHukMOhPikUG-TOATHG58-u0qxiBNw06hgZKV_ILxN0tCKleUKuztR2qTCEOIx5dJH5M58a0_nku3YEWnuOJ8LvVa_zHlfEJ1BLbw9SG0rBeLv02QYp9D6WtlUIfMY2IVWcQ6yk76gqCD0Sp7K1nDELTTZcpb-2CqbX3z4f8E-tv3BRffPjpZL35jx4k6ZAp4sW6JUso0a7RgFjjE7V898JPX7P8rHiQthCRkC2kfoGkfxuLGnWETXtlyP1Ac5QHUzH9-KqUc_zXLUqM8fhQ8cBpOkvN0qTTJUtyMv4frI6HfPKHsOidk0oDT2F1nl6G2Pf026m6GQ\"}"),
                                        "/api/om/miner/action",
                                        new HttpHandler(
                                                "{action: \"upgrade\", parameters: {}}",
                                                "{\"action\":null," +
                                                        "\"parameters\":[]}")))),
                Collections.emptyMap(),
                true,
                false);
    }
}
