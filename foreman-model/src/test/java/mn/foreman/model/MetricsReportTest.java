package mn.foreman.model;

import mn.foreman.model.metadata.ApiVersion;
import mn.foreman.model.metadata.Metadata;
import mn.foreman.model.miners.FanInfo;
import mn.foreman.model.miners.MinerStats;
import mn.foreman.model.miners.Pool;
import mn.foreman.model.miners.asic.Asic;
import mn.foreman.model.miners.rig.FreqInfo;
import mn.foreman.model.miners.rig.Gpu;
import mn.foreman.model.miners.rig.Rig;

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
                        .setHashRate(new BigDecimal(1))
                        .setFanInfo(fanInfo)
                        .addTemp(32)
                        .hasErrors(true)
                        .build();

        final Rig rig =
                new Rig.Builder()
                        .setHashRate(new BigDecimal("5678.0000"))
                        .addGpu(
                                new Gpu.Builder()
                                        .setName("gpu0")
                                        .setIndex("0")
                                        .setBus("1")
                                        .setFans(
                                                new FanInfo.Builder()
                                                        .setCount(1)
                                                        .addSpeed(1000)
                                                        .setSpeedUnits("RPM")
                                                        .build())
                                        .setFreqInfo(
                                                new FreqInfo.Builder()
                                                        .setFreq("420")
                                                        .setMemFreq("69")
                                                        .build())
                                        .setTemp("42069")
                                        .build())
                        .addGpu(
                                new Gpu.Builder()
                                        .setName("gpu1")
                                        .setIndex("1")
                                        .setBus("2")
                                        .setFans(
                                                new FanInfo.Builder()
                                                        .setCount(1)
                                                        .addSpeed(2000)
                                                        .setSpeedUnits("RPM")
                                                        .build())
                                        .setFreqInfo(
                                                new FreqInfo.Builder()
                                                        .setFreq("69")
                                                        .setMemFreq("420")
                                                        .build())
                                        .setTemp("69420")
                                        .build())
                        .build();

        final MinerStats minerStats =
                new MinerStats.Builder()
                        .setApiIp("127.0.0.1")
                        .setApiPort(42069)
                        .addPool(pool)
                        .addAsic(asic)
                        .addRig(rig)
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