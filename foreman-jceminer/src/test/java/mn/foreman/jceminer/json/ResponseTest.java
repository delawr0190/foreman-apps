package mn.foreman.jceminer.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import static junit.framework.TestCase.assertEquals;

/** Unit tests for {@link Response}. */
public class ResponseTest {

    /**
     * Tests JSON parsing.
     *
     * @throws IOException on failure to parse.
     */
    @Test
    public void testParse() throws IOException {
        final String json =
                "{\n" +
                        "  \"hashrate\":\n" +
                        "  {\n" +
                        "    \"thread_0\": 771.17,\n" +
                        "    \"thread_1\": 801.35,\n" +
                        "    \"thread_2\": 796.32,\n" +
                        "    \"thread_3\": 742.88,\n" +
                        "    \"thread_4\": 585.85,\n" +
                        "    \"thread_5\": 585.85,\n" +
                        "    \"thread_6\": 796.32,\n" +
                        "    \"thread_7\": 796.32,\n" +
                        "    \"thread_8\": 708.87,\n" +
                        "    \"thread_9\": 801.69,\n" +
                        "    \"thread_all\": [771.17, 801.35, 796.32, 742.88, 585.85, 585.85, 796.32, 796.32, 708.87, 801.69],\n" +
                        "    \"thread_gpu\": [771.17, 801.35, 796.32, 742.88, 1171.70, 796.32, 796.32, 708.87, 801.69],\n" +
                        "    \"total\": 7386.59,\n" +
                        "    \"max\": 7390.37\n" +
                        "  },\n" +
                        "  \"result\":\n" +
                        "  {\n" +
                        "     \"wallet\": \"WALLET.219000\",\n" +
                        "     \"pool\": \"bittube.miner.rocks:5555\",\n" +
                        "     \"ssl\": false,\n" +
                        "     \"currency\": \"BitTube (TUBE)\",\n" +
                        "     \"difficulty\": 219008,\n" +
                        "     \"shares\": 4161,\n" +
                        "     \"hashes\": 911292288,\n" +
                        "     \"uptime\": \"34:56:08\",\n" +
                        "     \"effective\": 7245.82\n" +
                        "  },\n" +
                        "  \"gpu_status\":\n" +
                        "  [\n" +
                        "    { \"index\": 0, \"temperature\": 69, \"fan\": 78, \"processor\": \"Ellesmere\", \"memory\": 4096, \"good_shares\": 429, \"bad_shares\": 0 },\n" +
                        "    { \"index\": 1, \"temperature\": 69, \"fan\": 78, \"processor\": \"Ellesmere\", \"memory\": 4096, \"good_shares\": 464, \"bad_shares\": 0 },\n" +
                        "    { \"index\": 2, \"temperature\": 64, \"fan\": 74, \"processor\": \"Ellesmere\", \"memory\": 4096, \"good_shares\": 484, \"bad_shares\": 0 },\n" +
                        "    { \"index\": 3, \"temperature\": 64, \"fan\": 74, \"processor\": \"Ellesmere\", \"memory\": 4096, \"good_shares\": 428, \"bad_shares\": 0 },\n" +
                        "    { \"index\": 4, \"temperature\": 57, \"fan\": 66, \"processor\": \"Ellesmere\", \"memory\": 8192, \"good_shares\": 611, \"bad_shares\": 0 },\n" +
                        "    { \"index\": 5, \"temperature\": 75, \"fan\": 88, \"processor\": \"Ellesmere\", \"memory\": 4096, \"good_shares\": 469, \"bad_shares\": 0 },\n" +
                        "    { \"index\": 6, \"temperature\": 75, \"fan\": 88, \"processor\": \"Ellesmere\", \"memory\": 4096, \"good_shares\": 468, \"bad_shares\": 0 },\n" +
                        "    { \"index\": 7, \"temperature\": 75, \"fan\": 86, \"processor\": \"Ellesmere\", \"memory\": 4096, \"good_shares\": 391, \"bad_shares\": 0 },\n" +
                        "    { \"index\": 8, \"temperature\": 68, \"fan\": 78, \"processor\": \"Ellesmere\", \"memory\": 4096, \"good_shares\": 418, \"bad_shares\": 0 }\n" +
                        "  ],\n" +
                        "  \"miner\":\n" +
                        "  {\n" +
                        "     \"version\": \"jce/0.32e/gpu\",\n" +
                        "     \"platform\": \"Intel(R) Pentium(R) CPU G4400 @ 3.30GHz\",\n" +
                        "     \"system\": \"Windows 64-bits\",\n" +
                        "     \"algorithm\": \"13\"\n" +
                        "  }\n" +
                        "}";

        final Response response =
                new ObjectMapper()
                        .readValue(
                                json,
                                Response.class);

        assertEquals(
                new BigDecimal("7386.59"),
                response.hashrate.total);

        assertEquals(
                "bittube.miner.rocks:5555",
                response.result.pool);
        assertEquals(
                4161,
                response.result.shares);

        final List<Response.Gpu> gpus = response.gpus;
        assertEquals(
                9,
                gpus.size());

        validateGpu(
                0,
                69,
                0,
                429,
                78,
                gpus.get(0));
        validateGpu(
                1,
                69,
                0,
                464,
                78,
                gpus.get(1));
        validateGpu(
                2,
                64,
                0,
                484,
                74,
                gpus.get(2));
        validateGpu(
                3,
                64,
                0,
                428,
                74,
                gpus.get(3));
        validateGpu(
                4,
                57,
                0,
                611,
                66,
                gpus.get(4));
        validateGpu(
                5,
                75,
                0,
                469,
                88,
                gpus.get(5));
        validateGpu(
                6,
                75,
                0,
                468,
                88,
                gpus.get(6));
        validateGpu(
                7,
                75,
                0,
                391,
                86,
                gpus.get(7));
        validateGpu(
                8,
                68,
                0,
                418,
                78,
                gpus.get(8));
    }

    /**
     * Validates the {@link Response.Gpu} against the expected values.
     *
     * @param index       The index.
     * @param temperature The temperature.
     * @param badShares   The bad shares.
     * @param goodShares  The good shares.
     * @param fan         The fan.
     * @param gpu         The gpu.
     */
    private static void validateGpu(
            final int index,
            final int temperature,
            final int badShares,
            final int goodShares,
            final int fan,
            final Response.Gpu gpu) {
        assertEquals(
                index,
                gpu.index);
        assertEquals(
                temperature,
                gpu.temperature);
        assertEquals(
                badShares,
                gpu.badShares);
        assertEquals(
                goodShares,
                gpu.goodShares);
        assertEquals(
                fan,
                gpu.fan);
    }
}