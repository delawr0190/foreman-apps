package mn.foreman.model;

import mn.foreman.model.metadata.ApiVersion;
import mn.foreman.model.metadata.Metadata;
import mn.foreman.model.miners.*;

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
        final SpeedInfo speedInfo =
                new SpeedInfo.Builder()
                        .setAvgHashRate(new BigDecimal(1))
                        .setAvgHashRateFiveSecs(new BigDecimal(2))
                        .build();

        final FanInfo fanInfo =
                new FanInfo.Builder()
                        .setCount(2)
                        .addSpeed(420)
                        .addSpeed(421)
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
                        .setSpeedInfo(speedInfo)
                        .setFanInfo(fanInfo)
                        .addTemp(32)
                        .hasErrors(true)
                        .build();

        final MinerStats minerStats =
                new MinerStats.Builder()
                        .setName("name")
                        .setApiIp("127.0.0.1")
                        .setApiPort(42069)
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