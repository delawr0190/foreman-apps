package mn.foreman.model.metadata;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.Test;

import java.io.IOException;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import static org.junit.Assert.assertEquals;

/** Unit tests for {@link Metadata}. */
public class MetadataTest {

    /**
     * Verifies that {@link Metadata metadatas} can be serialized.
     *
     * @throws IOException On failure to (de)serialize.
     */
    @Test
    public void testSerialization()
            throws IOException {
        final ApiVersion apiVersion = ApiVersion.V1_0_0;
        final ZonedDateTime timestamp = ZonedDateTime.now(ZoneId.of("UTC"));

        final Metadata metadata =
                new Metadata.Builder()
                        .setApiVersion(apiVersion)
                        .setTimestamp(timestamp)
                        .build();

        final ObjectMapper objectMapper =
                new ObjectMapper()
                        .registerModule(new JavaTimeModule());

        final String json =
                objectMapper.writeValueAsString(metadata);

        final Metadata newMetadata =
                objectMapper.readValue(
                        json,
                        Metadata.class);

        assertEquals(
                metadata,
                newMetadata);
        assertEquals(
                apiVersion,
                newMetadata.getApiVersion());
        assertEquals(
                timestamp,
                newMetadata.getTimestamp());
    }
}