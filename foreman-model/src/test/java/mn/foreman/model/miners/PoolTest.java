package mn.foreman.model.miners;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

/** Unit tests for {@link Pool}. */
public class PoolTest {

    /**
     * Verifies that {@link Pool pools} can be serialized.
     *
     * @throws IOException On failure to (de)serialize.
     */
    @Test
    @SuppressWarnings("ConstantConditions")
    public void testSerialization()
            throws IOException {
        final String name = "name";
        final int priority = 69;
        final Boolean enabled = true;

        final Pool pool =
                new Pool.Builder()
                        .setName(name)
                        .setPriority(priority)
                        .setEnabled(enabled)
                        .build();

        final ObjectMapper objectMapper =
                new ObjectMapper();

        final String json =
                objectMapper.writeValueAsString(pool);

        final Pool newPool =
                objectMapper.readValue(
                        json,
                        Pool.class);

        assertEquals(
                pool,
                newPool);
        assertEquals(
                name,
                newPool.getName());
        assertEquals(
                priority,
                newPool.getPriority());
        assertEquals(
                enabled,
                newPool.getEnabled());
    }
}