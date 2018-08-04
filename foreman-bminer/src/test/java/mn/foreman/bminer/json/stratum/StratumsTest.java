package mn.foreman.bminer.json.stratum;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static junit.framework.TestCase.*;

/** Unit tests for {@link Stratum}. */
public class StratumsTest {

    /**
     * Tests deserialization.
     *
     * @throws IOException on failure to parse.
     */
    @Test
    public void testDeserialization() throws IOException {
        final String response = "{\n" +
                "  \"stratums\": {\n" +
                "    \"blake2s\": {\n" +
                "      \"failover_uris\": [\n" +
                "        {\n" +
                "          \"name\": \"blake2s://DDXKDhq73GRM3hjh6uee57fJ3LS2ctNtyi.my:c=XVG,d=92@blake2s.mine.zpool.ca:5766\",\n" +
                "          \"active\": true\n" +
                "        }\n" +
                "      ],\n" +
                "      \"accepted_shares\": 1,\n" +
                "      \"rejected_shares\": 0,\n" +
                "      \"accepted_share_rate\": 0.02,\n" +
                "      \"rejected_share_rate\": 0\n" +
                "    },\n" +
                "    \"ethash\": {\n" +
                "      \"failover_uris\": [\n" +
                "        {\n" +
                "          \"name\": \"ethproxy://0xb76d43eAaB2e905028a7f0F3aF13C7A84c477B9f.w@guangdong-pool.ethfans.org:3333/\",\n" +
                "          \"active\": true\n" +
                "        }\n" +
                "      ],\n" +
                "      \"accepted_shares\": 2,\n" +
                "      \"rejected_shares\": 0,\n" +
                "      \"accepted_share_rate\": 0.01,\n" +
                "      \"rejected_share_rate\": 0\n" +
                "    }\n" +
                "  }\n" +
                "}";

        final ObjectMapper objectMapper =
                new ObjectMapper();
        final Stratums stratums =
                objectMapper.readValue(
                        response,
                        Stratums.class);

        assertNotNull(stratums);

        final Map<String, Stratum> stratumMap = stratums.stratums;
        assertNotNull(stratumMap);
        assertEquals(
                2,
                stratumMap.size());

        final Stratum stratum1 = stratumMap.get("blake2s");
        assertEquals(
                1,
                stratum1.acceptedShares);
        assertEquals(
                0,
                stratum1.rejectedShares);
        final List<Failover> failovers1 = stratum1.failovers;
        assertNotNull(failovers1);
        assertEquals(
                1,
                failovers1.size());
        final Failover failover1 = failovers1.get(0);
        assertEquals(
                "blake2s://DDXKDhq73GRM3hjh6uee57fJ3LS2ctNtyi.my:c=XVG,d=92@blake2s.mine.zpool.ca:5766",
                failover1.name);
        assertTrue(failover1.active);

        final Stratum stratum2 = stratumMap.get("ethash");
        assertEquals(
                2,
                stratum2.acceptedShares);
        assertEquals(
                0,
                stratum2.rejectedShares);
        final List<Failover> failovers2 = stratum2.failovers;
        assertNotNull(failovers2);
        assertEquals(
                1,
                failovers2.size());
        final Failover failover2 = failovers2.get(0);
        assertEquals(
                "ethproxy://0xb76d43eAaB2e905028a7f0F3aF13C7A84c477B9f.w@guangdong-pool.ethfans.org:3333/",
                failover2.name);
        assertTrue(failover2.active);
    }
}