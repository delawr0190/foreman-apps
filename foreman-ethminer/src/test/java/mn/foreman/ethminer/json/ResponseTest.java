package mn.foreman.ethminer.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;

/** Unit tests for {@link Response}. */
public class ResponseTest {

    /** Test response parsing. */
    @Test
    public void testResponse() throws IOException {
        final String json =
                "{\n" +
                        "  \"id\": 1,\n" +
                        "  \"jsonrpc\": \"2.0\",\n" +
                        "  \"result\": {\n" +
                        "    \"ethhashrate\": 73056881,\n" +
                        "    \"ethhashrates\": [\n" +
                        "      14681287,\n" +
                        "      14506510,\n" +
                        "      14681287,\n" +
                        "      14506510,\n" +
                        "      0,\n" +
                        "      14681287\n" +
                        "    ],\n" +
                        "    \"ethinvalid\": 0,\n" +
                        "    \"ethpoolsw\": 0,\n" +
                        "    \"ethrejected\": 0,\n" +
                        "    \"ethshares\": 64,\n" +
                        "    \"fanpercentages\": [\n" +
                        "       90,\n" +
                        "       90,\n" +
                        "       90,\n" +
                        "       90,\n" +
                        "       100,\n" +
                        "       90\n" +
                        "    ],\n" +
                        "    \"pooladdrs\": \"eu1.ethermine.org:4444\",\n" +
                        "    \"powerusages\": [\n" +
                        "       0.0,\n" +
                        "       0.0,\n" +
                        "       0.0,\n" +
                        "       0.0,\n" +
                        "       0.0,\n" +
                        "       0.0\n" +
                        "    ],\n" +
                        "    \"runtime\": \"59\",\n" +
                        "    \"temperatures\": [\n" +
                        "       53,\n" +
                        "       50,\n" +
                        "       56,\n" +
                        "       58,\n" +
                        "       68,\n" +
                        "       60\n" +
                        "    ],\n" +
                        "    \"ispaused\": [\n" +
                        "       false,\n" +
                        "       false,\n" +
                        "       false,\n" +
                        "       false,\n" +
                        "       true,\n" +
                        "       false\n" +
                        "    ],\n" +
                        "    \"version\": \"ethminer-0.16.0.dev0+commit.41639944\"\n" +
                        "  }\n" +
                        "}";

        final Response response =
                new ObjectMapper()
                        .readValue(
                                json,
                                Response.class);
        final Response.Result result = response.result;
        assertNotNull(result);
        assertEquals(
                73056881,
                result.ethHashRate);
        assertEquals(
                0,
                result.ethInvalid);
        assertEquals(
                0,
                result.ethRejected);
        assertEquals(
                64,
                result.ethShares);
        assertEquals(
                Arrays.asList(
                        90,
                        90,
                        90,
                        90,
                        100,
                        90),
                result.fanPercentages);
        assertEquals(
                "eu1.ethermine.org:4444",
                result.poolAddress);
        assertEquals(
                "59",
                result.runtime);
        assertEquals(
                Arrays.asList(
                        53,
                        50,
                        56,
                        58,
                        68,
                        60),
                result.temperatures);
    }
}