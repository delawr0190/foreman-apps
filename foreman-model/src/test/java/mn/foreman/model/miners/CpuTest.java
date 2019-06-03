package mn.foreman.model.miners;

import mn.foreman.model.miners.cpu.Cpu;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.io.IOException;
import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

/** Unit tests for {@link Cpu}. */
public class CpuTest {

    /**
     * Verifies that {@link Cpu CPUs} can be serialized.
     *
     * @throws IOException On failure to (de)serialize.
     */
    @Test
    @SuppressWarnings("ConstantConditions")
    public void testSerialization()
            throws IOException {
        final Cpu cpu =
                new Cpu.Builder()
                        .setTemp(69)
                        .setFanSpeed(70)
                        .setFrequency(new BigDecimal("420"))
                        .addThread(new BigDecimal("1234"))
                        .build();

        final ObjectMapper objectMapper =
                new ObjectMapper();

        final String json =
                objectMapper.writeValueAsString(cpu);

        final Cpu newCpu =
                objectMapper.readValue(
                        json,
                        Cpu.class);

        assertEquals(
                cpu,
                newCpu);
    }
}