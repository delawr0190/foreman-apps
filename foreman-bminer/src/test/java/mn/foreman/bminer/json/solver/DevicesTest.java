package mn.foreman.bminer.json.solver;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;

/** Unit tests for {@link Devices} deserialization. */
public class DevicesTest {

    /**
     * Tests deserialization with a hash rate.
     *
     * @throws IOException on failure to deserialize
     */
    @Test
    public void testHashRateDeserialization() throws IOException {
        runDeserializationTest("hash_rate");
    }

    /**
     * Tests deserialization with a solution rate.
     *
     * @throws IOException on failure to deserialize
     */
    @Test
    public void testSolutionRateDeserialization() throws IOException {
        runDeserializationTest("solution_rate");
    }

    /**
     * Tests device deserialization.
     *
     * @throws IOException on failure to deserialize.
     */
    private void runDeserializationTest(
            final String rateKey) throws IOException {
        final String startingAlgorithm1 = "ethash";
        final double startingRate1 = 30828134.4;
        final String startingAlgorithm2 = "blake2s";
        final double startingRate2 = 1778500208.17;

        final String response = "{\n" +
                "  \"devices\": {\n" +
                "    \"0\": {\n" +
                "      \"solvers\": [\n" +
                "        {\n" +
                "          \"algorithm\": \"" + startingAlgorithm1 + "\",\n" +
                "          \"speed_info\": {\n" +
                "            \"" + rateKey + "\": " + startingRate1 + "\n" +
                "          }\n" +
                "        },\n" +
                "        {\n" +
                "          \"algorithm\": \"" + startingAlgorithm2 + "\",\n" +
                "          \"speed_info\": {\n" +
                "            \"" + rateKey + "\": " + startingRate2 + "\n" +
                "          }\n" +
                "        }\n" +
                "      ]\n" +
                "    },\n" +
                "    \"1\": {\n" +
                "      \"solvers\": [\n" +
                "        {\n" +
                "          \"algorithm\": \"" + (startingAlgorithm1 + 1) + "\",\n" +
                "          \"speed_info\": {\n" +
                "            \"" + rateKey + "\": " + (startingRate1 + 1) + "\n" +
                "          }\n" +
                "        },\n" +
                "        {\n" +
                "          \"algorithm\": \"" + (startingAlgorithm2 + 1) + "\",\n" +
                "          \"speed_info\": {\n" +
                "            \"" + rateKey + "\": " + (startingRate2 + 1) + "\n" +
                "          }\n" +
                "        }\n" +
                "      ]\n" +
                "    }\n" +
                "  }\n" +
                "}";

        final ObjectMapper objectMapper =
                new ObjectMapper();
        final Devices devices =
                objectMapper.readValue(
                        response,
                        Devices.class);

        final Map<String, Solvers> deviceMap = devices.devices;
        assertNotNull(deviceMap);
        assertEquals(
                2,
                deviceMap.size());

        for (int i = 0; i < deviceMap.size(); i++) {
            final Solvers solvers = deviceMap.get("" + i);
            assertNotNull(solvers);

            final List<Solver> solverList = solvers.solvers;
            assertNotNull(solvers);
            assertEquals(
                    2,
                    solverList.size());

            final Solver solver1 = solverList.get(0);
            assertEquals(
                    startingRate1 + i,
                    solver1.speedInfo.hashRate);

            final Solver solver2 = solverList.get(1);
            assertEquals(
                    startingRate2 + i,
                    solver2.speedInfo.hashRate);
        }
    }
}