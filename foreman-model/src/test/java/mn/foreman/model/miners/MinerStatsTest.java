package mn.foreman.model.miners;

import mn.foreman.model.miners.asic.Asic;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.assertEquals;

/** Unit tests for {@link MinerStats}. */
public class MinerStatsTest {

    /**
     * Verifies that {@link MinerStats stats} can be serialized.
     *
     * @throws IOException On failure to (de)serialize.
     */
    @Test
    @SuppressWarnings("ConstantConditions")
    public void testSerialization()
            throws IOException {
        final String name = "name";
        final String apiIp = "127.0.0.1";
        final int apiPort = 42069;

        final FanInfo fanInfo =
                new FanInfo.Builder()
                        .setCount(2)
                        .addSpeed(420)
                        .addSpeed(421)
                        .setSpeedUnits("RPM")
                        .build();

        final Pool pool =
                new Pool.Builder()
                        .setName("poolName")
                        .setStatus(
                                false,
                                true)
                        .setPriority(420)
                        .setCounts(
                                1,
                                2,
                                3)
                        .build();

        final Asic asic =
                new Asic.Builder()
                        .setName("asicName")
                        .setHashRate(new BigDecimal(1234))
                        .setFanInfo(fanInfo)
                        .addTemp(32)
                        .hasErrors(true)
                        .build();

        final MinerStats stats =
                new MinerStats.Builder()
                        .setName(name)
                        .setApiIp(apiIp)
                        .setApiPort(apiPort)
                        .addPool(pool)
                        .addAsic(asic)
                        .build();

        final ObjectMapper objectMapper =
                new ObjectMapper();

        final String json =
                objectMapper.writeValueAsString(stats);

        final MinerStats newMinerStats =
                objectMapper.readValue(
                        json,
                        MinerStats.class);

        assertEquals(
                newMinerStats,
                newMinerStats);
        assertEquals(
                name,
                newMinerStats.getName());
        assertEquals(
                apiIp,
                newMinerStats.getApiIp());
        assertEquals(
                apiPort,
                newMinerStats.getApiPort());

        final List<Pool> pools = newMinerStats.getPools();
        assertEquals(
                1,
                pools.size());
        assertEquals(
                pool,
                pools.get(0));

        final List<Asic> asics = newMinerStats.getAsics();
        assertEquals(
                1,
                asics.size());
        assertEquals(
                asic,
                asics.get(0));
    }
}