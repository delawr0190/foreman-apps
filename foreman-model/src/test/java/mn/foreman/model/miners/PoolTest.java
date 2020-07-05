package mn.foreman.model.miners;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

/** Unit tests for {@link Pool}. */
public class PoolTest {

    /** Tests setting counts from empty strings. */
    @Test
    public void testEmptyCounts() {
        final String name = "name";
        final int priority = 69;

        final Pool pool =
                new Pool.Builder()
                        .setName(name)
                        .setStatus(
                                true,
                                false)
                        .setPriority(priority)
                        .setCounts(
                                "",
                                "",
                                "")
                        .build();

        assertEquals(
                name,
                pool.getName());
        assertTrue(
                pool.getEnabled());
        assertFalse(
                pool.getStatus());
        assertEquals(
                priority,
                pool.getPriority());
        assertEquals(
                0,
                pool.getAccepted());
        assertEquals(
                0,
                pool.getRejected());
        assertEquals(
                0,
                pool.getStale());
    }

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
        final Boolean enabled = true;
        final Boolean status = false;
        final int priority = 69;
        final long accepted = 420;
        final long rejected = 421;
        final long stale = 422;

        final Pool pool =
                new Pool.Builder()
                        .setName(name)
                        .setStatus(
                                enabled,
                                status)
                        .setPriority(priority)
                        .setCounts(
                                accepted,
                                rejected,
                                stale)
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
                enabled,
                newPool.getEnabled());
        assertEquals(
                status,
                newPool.getStatus());
        assertEquals(
                priority,
                newPool.getPriority());
        assertEquals(
                accepted,
                newPool.getAccepted());
        assertEquals(
                rejected,
                newPool.getRejected());
        assertEquals(
                stale,
                newPool.getStale());
    }
}