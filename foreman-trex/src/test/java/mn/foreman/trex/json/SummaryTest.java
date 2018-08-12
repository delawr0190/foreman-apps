package mn.foreman.trex.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.io.IOException;
import java.math.BigDecimal;

import static junit.framework.TestCase.assertEquals;

/** Unit tests for {@link Summary}. */
public class SummaryTest {

    /**
     * Tests JSON parsing.
     *
     * @throws IOException on failure to parse.
     */
    @Test
    public void testParse() throws IOException {
        final String json =
                "{\n" +
                        "  \"accepted_count\": 44,\n" +
                        "  \"active_pool\": {\n" +
                        "    \"retries\": 0,\n" +
                        "    \"url\": \"stratum+tcp://blockmasters.co:4553\",\n" +
                        "    \"user\": \"DMbUXFZfpHpzEGjHMfJivjgNXswmrsdaqB\"\n" +
                        "  },\n" +
                        "  \"algorithm\": \"lyra2z\",\n" +
                        "  \"api\": \"1.0\",\n" +
                        "  \"cuda\": \"9.20\",\n" +
                        "  \"description\": \"T-Rex NVIDIA GPU miner\",\n" +
                        "  \"difficulty\": 53.78282292371265,\n" +
                        "  \"gpu_total\": 8,\n" +
                        "  \"gpus\": [\n" +
                        "    {\n" +
                        "      \"device_id\": 0,\n" +
                        "      \"gpu_id\": 0,\n" +
                        "      \"hashrate\": 3059637,\n" +
                        "      \"intensity\": 20\n" +
                        "    },\n" +
                        "    {\n" +
                        "      \"device_id\": 1,\n" +
                        "      \"gpu_id\": 1,\n" +
                        "      \"hashrate\": 3036386,\n" +
                        "      \"intensity\": 20\n" +
                        "    },\n" +
                        "    {\n" +
                        "      \"device_id\": 2,\n" +
                        "      \"gpu_id\": 2,\n" +
                        "      \"hashrate\": 3033945,\n" +
                        "      \"intensity\": 20\n" +
                        "    },\n" +
                        "    {\n" +
                        "      \"device_id\": 3,\n" +
                        "      \"gpu_id\": 3,\n" +
                        "      \"hashrate\": 3039842,\n" +
                        "      \"intensity\": 20\n" +
                        "    },\n" +
                        "    {\n" +
                        "      \"device_id\": 4,\n" +
                        "      \"gpu_id\": 4,\n" +
                        "      \"hashrate\": 3036523,\n" +
                        "      \"intensity\": 20\n" +
                        "    },\n" +
                        "    {\n" +
                        "      \"device_id\": 5,\n" +
                        "      \"gpu_id\": 5,\n" +
                        "      \"hashrate\": 3074756,\n" +
                        "      \"intensity\": 20\n" +
                        "    },\n" +
                        "    {\n" +
                        "      \"device_id\": 6,\n" +
                        "      \"gpu_id\": 6,\n" +
                        "      \"hashrate\": 3053700,\n" +
                        "      \"intensity\": 20\n" +
                        "    },\n" +
                        "    {\n" +
                        "      \"device_id\": 7,\n" +
                        "      \"gpu_id\": 7,\n" +
                        "      \"hashrate\": 2966131,\n" +
                        "      \"intensity\": 20\n" +
                        "    }\n" +
                        "  ],\n" +
                        "  \"hashrate\": 24300920,\n" +
                        "  \"name\": \"t-rex\",\n" +
                        "  \"os\": \"win\",\n" +
                        "  \"rejected_count\": 0,\n" +
                        "  \"ts\": 1533090180,\n" +
                        "  \"uptime\": 290,\n" +
                        "  \"version\": \"0.5.6\"\n" +
                        "}\n";

        final Summary summary =
                new ObjectMapper()
                        .readValue(
                                json,
                                Summary.class);
        assertEquals(
                44,
                summary.acceptCount);
        assertEquals(
                "stratum+tcp://blockmasters.co:4553",
                summary.activePool.url);
        assertEquals(
                8,
                summary.gpuTotal);
        assertEquals(
                new BigDecimal(24300920),
                summary.hashRate);
        assertEquals(
                0,
                summary.rejectedCount);
        assertEquals(
                290,
                summary.uptime);
    }
}