package mn.foreman.chisel;

import mn.foreman.model.Miner;
import mn.foreman.model.error.MinerException;
import mn.foreman.model.miners.FanInfo;
import mn.foreman.model.miners.MinerStats;
import mn.foreman.model.miners.Pool;
import mn.foreman.model.miners.rig.FreqInfo;
import mn.foreman.model.miners.rig.Gpu;
import mn.foreman.model.miners.rig.Rig;
import mn.foreman.util.FakeMinerServer;
import mn.foreman.util.http.FakeHttpMinerServer;
import mn.foreman.util.http.HttpHandler;

import com.google.common.collect.ImmutableMap;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static org.easymock.EasyMock.*;

/** Unit tests for the {@link ChiselMinerDecorator}. */
public class ChiselMinerDecoratorTest {

    /**
     * Verifies that the decorator properly adds GPU metrics to a {@link
     * MinerStats}.
     *
     * @throws Exception on failure to query.
     */
    @Test
    public void testDecoration()
            throws Exception {
        final int chiselPort = 42069;

        final String apiIp = "127.0.0.1";
        final int apiPort = 420;

        final Pool mockPool = createMock(Pool.class);
        replay(mockPool);

        final Gpu mockGpu1 = createMock(Gpu.class);
        replay(mockGpu1);

        final Gpu mockGpu2 = createMock(Gpu.class);
        replay(mockGpu2);

        final BigDecimal hashRate = BigDecimal.TEN;

        final Rig mockRig = createMock(Rig.class);
        expect(mockRig.getHashRate())
                .andReturn(hashRate);
        expect(mockRig.getGpus())
                .andReturn(
                        Arrays.asList(
                                mockGpu1,
                                mockGpu2));
        replay(mockRig);

        final MinerStats mockStats = createMock(MinerStats.class);
        expect(mockStats.getApiIp())
                .andReturn(apiIp);
        expect(mockStats.getApiPort())
                .andReturn(apiPort);
        expect(mockStats.getPools())
                .andReturn(Collections.singletonList(mockPool));
        expect(mockStats.getRigs())
                .andReturn(Collections.singletonList(mockRig));
        replay(mockStats);

        final Miner mockMiner = createMock(Miner.class);
        expect(mockMiner.getStats()).andReturn(mockStats);
        replay(mockMiner);

        final Miner chiselMiner =
                new ChiselMinerDecorator(
                        apiIp,
                        chiselPort,
                        mockMiner);

        try (final FakeMinerServer fakeChisel =
                     new FakeHttpMinerServer(
                             chiselPort,
                             ImmutableMap.of(
                                     "/stats",
                                     new HttpHandler(
                                             "",
                                             "[\n" +
                                                     "  {\n" +
                                                     "    \"id\": 0,\n" +
                                                     "    \"busId\": 1,\n" +
                                                     "    \"name\": \"GeForce GTX 1070 Ti\",\n" +
                                                     "    \"temp\": 67,\n" +
                                                     "    \"fan\": 99,\n" +
                                                     "    \"clocks\": {\n" +
                                                     "      \"core\": 1234,\n" +
                                                     "      \"memory\": 5678\n" +
                                                     "    },\n" +
                                                     "    \"processes\": [\n" +
                                                     "      \"ethminer\",\n" +
                                                     "      \"something else\"\n" +
                                                     "    ]\n" +
                                                     "  },\n" +
                                                     "  {\n" +
                                                     "    \"id\": 1,\n" +
                                                     "    \"busId\": 3,\n" +
                                                     "    \"name\": \"GeForce GTX 1080 Ti\",\n" +
                                                     "    \"temp\": 69,\n" +
                                                     "    \"fan\": 75,\n" +
                                                     "    \"clocks\": {\n" +
                                                     "      \"core\": 5678,\n" +
                                                     "      \"memory\": 1234\n" +
                                                     "    },\n" +
                                                     "    \"processes\": [\n" +
                                                     "      \"ethminer\",\n" +
                                                     "      \"something else\"\n" +
                                                     "    ]\n" +
                                                     "  }\n" +
                                                     "]")))) {
            fakeChisel.start();

            final MinerStats actualStats =
                    chiselMiner.getStats();
            assertEquals(
                    apiIp,
                    actualStats.getApiIp());
            assertEquals(
                    apiPort,
                    actualStats.getApiPort());
            assertEquals(
                    Collections.singletonList(mockPool),
                    actualStats.getPools());
            assertTrue(
                    actualStats.getAsics().isEmpty());
            final List<Rig> actualRigs =
                    actualStats.getRigs();
            assertEquals(
                    1,
                    actualRigs.size());
            final Rig actualRig =
                    actualRigs.get(0);
            assertEquals(
                    hashRate,
                    actualRig.getHashRate());
            final List<Gpu> actualGpus =
                    actualRig.getGpus();
            assertEquals(
                    2,
                    actualGpus.size());
            assertGpuEquals(
                    actualGpus.get(0),
                    0,
                    1,
                    "GeForce GTX 1070 Ti",
                    67,
                    99,
                    1234,
                    5678);
            assertGpuEquals(
                    actualGpus.get(1),
                    1,
                    3,
                    "GeForce GTX 1080 Ti",
                    69,
                    75,
                    5678,
                    1234);
        }

        verify(mockMiner);
        verify(mockStats);
        verify(mockRig);
        verify(mockGpu2);
        verify(mockGpu1);
        verify(mockPool);
    }

    /**
     * Verifies that metrics are passed through when chisel can't be reached.
     *
     * @throws MinerException on failure to query.
     */
    @Test
    public void testDecorationSkippedOnException()
            throws MinerException {
        final MinerStats mockStats = createMock(MinerStats.class);
        replay(mockStats);

        final Miner mockMiner = createMock(Miner.class);
        expect(mockMiner.getStats()).andReturn(mockStats);
        replay(mockMiner);

        final Miner chiselMiner =
                new ChiselMinerDecorator(
                        "127.0.0.1",
                        42069,
                        mockMiner);

        assertEquals(
                mockStats,
                chiselMiner.getStats());

        verify(mockMiner);
        verify(mockStats);
    }

    /**
     * Verifies that the {@link Gpu} matches the expected values.
     *
     * @param gpu       The {@link Gpu}.
     * @param id        The ID.
     * @param busId     The bus ID.
     * @param name      The name.
     * @param temp      The temp.
     * @param fan       The fan.
     * @param coreClock The core clock.
     * @param memClock  The mem clock.
     */
    private static void assertGpuEquals(
            final Gpu gpu,
            final int id,
            final int busId,
            final String name,
            final int temp,
            final int fan,
            final int coreClock,
            final int memClock) {
        assertEquals(
                id,
                gpu.getIndex());
        assertEquals(
                busId,
                gpu.getBus());
        assertEquals(
                name,
                gpu.getName());
        assertEquals(
                temp,
                gpu.getTemp());

        final FanInfo fanInfo = gpu.getFans();
        assertEquals(
                1,
                fanInfo.getCount());
        assertEquals(
                Collections.singletonList(fan),
                fanInfo.getSpeeds());
        assertEquals(
                "%",
                fanInfo.getSpeedUnits());

        final FreqInfo freqInfo = gpu.getFreqInfo();
        assertEquals(
                coreClock,
                freqInfo.getFreq());
        assertEquals(
                memClock,
                freqInfo.getMemFreq());
    }
}