package mn.foreman.discover;

import lombok.Builder;
import lombok.Data;

/** A {@link Discovery} represents an obtained raw API response. */
@Data
@Builder
public class Discovery {

    /** The query. */
    String query;

    /** The response that was obtained. */
    String response;

    /** Whether or not the discovery attempt was successful. */
    boolean success;
}
