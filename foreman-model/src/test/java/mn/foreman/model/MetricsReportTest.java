package mn.foreman.model;

import mn.foreman.model.metadata.ApiVersion;
import mn.foreman.model.metadata.Metadata;
import mn.foreman.model.miners.Asic;
import mn.foreman.model.miners.MinerStats;
import mn.foreman.model.miners.Pool;
import mn.foreman.model.miners.SpeedInfo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

import static org.junit.Assert.assertEquals;

/** Unit tests for the {@link MetricsReport}. */
public class MetricsReportTest {

    /**
     * Verifies that {@link MetricsReport reports} can be serialized.
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

        final String hashRate = "13.5 TH/s";
        final String avgHashRate = "12.9 TH/s";
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

        final MinerStats minerStats =
                new MinerStats.Builder()
                        .setName(name)
                        .setApiIp(apiIp)
                        .setApiPort(apiPort)
                        .setSpeedInfo(speedInfo)
                        .setTemperature(temperature)
                        .addPool(pool)
                        .addAsic(asic)
                        .build();

        final ZonedDateTime timestamp =
                ZonedDateTime.now(ZoneId.of("UTC"));
        final ApiVersion apiVersion =
                ApiVersion.V1_0_0;
        final Metadata metadata =
                new Metadata.Builder()
                        .setTimestamp(timestamp)
                        .setApiVersion(apiVersion)
                        .build();

        final MetricsReport metricsReport =
                new MetricsReport.Builder()
                        .setMetadata(metadata)
                        .addMinerStats(minerStats)
                        .build();

        final ObjectMapper objectMapper =
                new ObjectMapper()
                        .registerModule(new JavaTimeModule());

        final String json =
                objectMapper.writeValueAsString(metricsReport);

        final MetricsReport newMetricsReport =
                objectMapper.readValue(
                        json,
                        MetricsReport.class);

        assertEquals(
                metricsReport,
                newMetricsReport);
        assertEquals(
                metadata,
                newMetricsReport.getMetadata());

        final List<MinerStats> statsList = newMetricsReport.getMiners();
        assertEquals(
                1,
                statsList.size());
        assertEquals(
                minerStats,
                statsList.get(0));
    }
}