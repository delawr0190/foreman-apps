package mn.foreman.claymore.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/** Unit tests for {@link Response}. */
public class ResponseTest {

    /** Tests parsing a {@link Response}. */
    @Test
    public void testParse() throws IOException {
        final String message = "{" +
                "\"error\": null, " +
                "\"id\": 0, " +
                "\"result\": [" +
                "\"10.0 - ETH\", " +
                "\"0\", " +
                "\"0;0;0\", " +
                "\"0\", " +
                "\"0;0;0\", " +
                "\"0\", " +
                "\"52;47\", " +
                "\"daggerhashimoto.eu.nicehash.com:3353;decred.eu.nicehash.com:3354\", " +
                "\"0;0;0;0\"" +
                "]}";

        final Response response =
                new ObjectMapper()
                        .readValue(
                                message,
                                Response.class);
        assertNull(response.error);
        assertEquals(
                0,
                response.id);

        final List<String> results = response.result;
        assertEquals(
                9,
                results.size());
        assertEquals(
                "10.0 - ETH",
                results.get(0));
        assertEquals(
                "0",
                results.get(1));
        assertEquals(
                "0;0;0",
                results.get(2));
        assertEquals(
                "0",
                results.get(3));
        assertEquals(
                "0;0;0",
                results.get(4));
        assertEquals(
                "0",
                results.get(5));
        assertEquals(
                "52;47",
                results.get(6));
        assertEquals(
                "daggerhashimoto.eu.nicehash.com:3353;decred.eu.nicehash" +
                        ".com:3354",
                results.get(7));
        assertEquals(
                "0;0;0;0",
                results.get(8));
    }
}