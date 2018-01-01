package mn.foreman.model.miners;

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
        final BigDecimal temperature = new BigDecimal(69);

        final BigDecimal hashRate = new BigDecimal(13500000.00);
        final BigDecimal avgHashRate = new BigDecimal(12900000.00);
        final SpeedInfo speedInfo =
                new SpeedInfo.Builder()
                        .setHashRate(hashRate)
                        .setAvgHashRate(avgHashRate)
                        .build();

        final String poolName = "poolName";
        final Boolean enabled = true;
        final int priority = 420;
        final Pool pool =
                new Pool.Builder()
                        .setName(poolName)
                        .setEnabled(enabled)
                        .setPriority(priority)
                        .build();

        final String asicName = "ASIC 0";
        final BigDecimal asicTemperature = new BigDecimal(420);
        final Asic asic =
                new Asic.Builder()
                        .setName(asicName)
                        .setTemperature(asicTemperature)
                        .build();

        final MinerStats stats =
                new MinerStats.Builder()
                        .setName(name)
                        .setApiIp(apiIp)
                        .setApiPort(apiPort)
                        .setSpeedInfo(speedInfo)
                        .setTemperature(temperature)
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
        assertEquals(
                temperature,
                newMinerStats.getTemperature());
        assertEquals(
                speedInfo,
                newMinerStats.getSpeedInfo());

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