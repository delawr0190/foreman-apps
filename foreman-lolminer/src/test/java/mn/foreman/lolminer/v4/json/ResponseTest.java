package mn.foreman.lolminer.v4.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.io.IOException;
import java.math.BigDecimal;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNull;

/** Unit tests for {@link Response}. */
public class ResponseTest {

    /**
     * Tests JSON parsing.
     *
     * @throws IOException on falure to parse.
     */
    @Test
    public void testParse()
            throws IOException {
        final String json =
                "{\n" +
                        "  \"Software\": \"lolMiner 0.41\",\n" +
                        "  \"Startup\": \"2018-08-04 10:17:42 \",\n" +
                        "  \"Coin\": \"BitcoinZ (BTCZ)\",\n" +
                        "  \"Algorithm\": \"Equihash 144.5\",\n" +
                        "  \"ConnectedPool\": \"mine-btcz-euro.equipool.1ds.us:50061\",\n" +
                        "  \"ConnectedUser\": \"t1dbQvbohSUAGE6dZpUYru7e18SCNVipVXY.lolMiner\",\n" +
                        "  \"LastUpdate(5s)\": \"22:33:46 \",\n" +
                        "  \"LastUpdate(60s)\": \"22:32:55 \",\n" +
                        "  \"TotalSpeed(5s)\": \"24722.0225\",\n" +
                        "  \"TotalSpeed(60s)\": \" \",\n" +
                        "  \"GPU0\": {\n" +
                        "    \"Name\": \"AMD Radeon (TM) RX 480 Graphics\",\n" +
                        "    \"Speed(5s)\": \"13468.8252\",\n" +
                        "    \"Speed(60s)\": \"13429.5117\"\n" +
                        "  },\n" +
                        "  \"GPU1\": {\n" +
                        "    \"Name\": \"Radeon RX 580 Series\",\n" +
                        "    \"Speed(5s)\": \"11253.1973\",\n" +
                        "    \"Speed(60s)\": \"11262.5537\"\n" +
                        "  }\n" +
                        "}";

        final Response response =
                new ObjectMapper()
                        .readValue(
                                json,
                                Response.class);

        assertEquals(
                "mine-btcz-euro.equipool.1ds.us:50061",
                response.pool);
        assertEquals(
                new BigDecimal("24722.0225"),
                response.totalSpeed);
        assertEquals(
                "AMD Radeon (TM) RX 480 Graphics",
                response.gpu0.name);
        assertEquals(
                "Radeon RX 580 Series",
                response.gpu1.name);
        assertNull(response.gpu2);
        assertNull(response.gpu3);
        assertNull(response.gpu4);
        assertNull(response.gpu5);
        assertNull(response.gpu6);
        assertNull(response.gpu7);
        assertNull(response.gpu8);
        assertNull(response.gpu9);
        assertNull(response.gpu10);
        assertNull(response.gpu11);
        assertNull(response.gpu12);
        assertNull(response.gpu13);
        assertNull(response.gpu14);
    }
}