package mn.foreman.lolminer.v6.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.assertEquals;

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
                        "    \"Software\": \"lolMiner 0.6\",\n" +
                        "    \"Mining\": {\n" +
                        "        \"Coin\": \"AION\",\n" +
                        "        \"Algorithm\": \"Equihash 210\\/9\"\n" +
                        "    },\n" +
                        "    \"Stratum\": {\n" +
                        "        \"Current_Pool\": \"na.aionmine.org:3333\",\n" +
                        "        \"Current_User\": \"0xa0e1ff18f69eac5d17fc8c5ac078739d64cc0a8ae2f84b7ca6d55c33bff8dc52.lolMiner\",\n" +
                        "        \"Average_Latency\": 0.0\n" +
                        "    },\n" +
                        "    \"Session\": {\n" +
                        "        \"Startup\": 1547410837,\n" +
                        "        \"Startup_String\": \"2019-01-13_15-20-37\",\n" +
                        "        \"Uptime\": 140,\n" +
                        "        \"Last_Update\": 1547410977,\n" +
                        "        \"Active_GPUs\": 1,\n" +
                        "        \"Performance_Summary\": 65.1,\n" +
                        "        \"Accepted\": 4,\n" +
                        "        \"Submitted\": 4\n" +
                        "    },\n" +
                        "    \"GPUs\": [\n" +
                        "        {\n" +
                        "            \"Index\": 0,\n" +
                        "            \"Name\": \"GeForce GTX 1050 Ti\",\n" +
                        "            \"Performance\": 65.1,\n" +
                        "            \"Session_Accepted\": 4,\n" +
                        "            \"Session_Submitted\": 4,\n" +
                        "            \"PCIE_Address\": \"1:0\"\n" +
                        "        }\n" +
                        "    ]\n" +
                        "}";

        final Response response =
                new ObjectMapper()
                        .readValue(
                                json,
                                Response.class);

        assertEquals(
                "na.aionmine.org:3333",
                response.stratum.pool);
        assertEquals(
                new BigDecimal("65.1"),
                response.session.hashRate);

        final List<Response.Gpu> gpus = response.gpus;
        assertEquals(
                1,
                gpus.size());
        final Response.Gpu gpu = gpus.get(0);
        assertEquals(
                0,
                gpu.index);
        assertEquals(
                "1:0",
                gpu.address);
        assertEquals(
                "GeForce GTX 1050 Ti",
                gpu.name);
    }
}