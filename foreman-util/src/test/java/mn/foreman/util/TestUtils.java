package mn.foreman.util;

import com.google.common.collect.ImmutableMap;

import java.util.Arrays;
import java.util.Map;

/** Test utility functions. */
public class TestUtils {

    /**
     * Creates test pool json.
     *
     * @return The test data.
     */
    public static Map<String, Object> toPoolJson() {
        return ImmutableMap.of(
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
