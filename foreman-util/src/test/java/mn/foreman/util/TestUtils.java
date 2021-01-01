package mn.foreman.util;

import com.google.common.collect.ImmutableMap;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/** Test utility functions. */
public class TestUtils {

    /**
     * Creates sample detect json.
     *
     * @return The json.
     */
    public static Map<String, Object> toDetectJson() {
        return ImmutableMap.of(
                "test",
                "true",
                "port",
                "8080",
                "arg1",
                "val1");
    }

    /**
     * Creates test network json.
     *
     * @param apiPort         The API port.
     * @param includeHostname Whether or not the hostname should be included.
     *
     * @return The test data.
     */
    public static Map<String, Object> toNetworkJson(
            final int apiPort,
            final boolean includeHostname) {
        final Map<String, Object> network = new HashMap<>();
        network.put("gateway", "192.168.1.1");
        network.put("netmask", "255.255.255.0");
        network.put("ipAddress", "192.168.1.189");
        network.put("dns", "192.168.1.1");
        if (includeHostname) {
            network.put("hostname", "hostname");
        }
        return ImmutableMap.of(
                "newApiIp",
                "127.0.0.1",
                "newApiPort",
                Integer.toString(apiPort),
                "test",
                "true",
                "network",
                network);
    }

    /**
     * Creates test network json.
     *
     * @param apiPort         The new API port.
     * @param includeHostname Whether or not the hostname should be included.
     * @param extra           The extra.
     *
     * @return The test data.
     */
    public static Map<String, Object> toNetworkJson(
            final int apiPort,
            final boolean includeHostname,
            final Map<String, Object> extra) {
        final Map<String, Object> params =
                new HashMap<>(
                        toNetworkJson(
                                apiPort,
                                includeHostname));
        params.putAll(extra);
        return params;
    }

    /**
     * Creates test pool json.
     *
     * @param extra The extra.
     *
     * @return The test data.
     */
    public static Map<String, Object> toPoolJson(
            final Map<String, Object> extra) {
        final Map<String, Object> params = new HashMap<>(toPoolJson());
        params.putAll(extra);
        return params;
    }

    /**
     * Creates test pool json.
     *
     * @return The test data.
     */
    public static Map<String, Object> toPoolJson() {
        return ImmutableMap.of(
                "test",
                true,
                "pools",
                Arrays.asList(
                        ImmutableMap.of(
                                "url",
                                "stratum+tcp://my-test-pool1.com:5588",
                                "user",
                                "my-test-username1",
                                "pass",
                                "my-test-password1"),
                        ImmutableMap.of(
                                "url",
                                "stratum+tcp://my-test-pool2.com:5588",
                                "user",
                                "my-test-username2",
                                "pass",
                                "my-test-password2"),
                        ImmutableMap.of(
                                "url",
                                "stratum+tcp://my-test-pool3.com:5588",
                                "user",
                                "my-test-username3",
                                "pass",
                                "my-test-password3")));
    }
}
