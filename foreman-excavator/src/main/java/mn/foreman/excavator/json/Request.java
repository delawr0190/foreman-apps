package mn.foreman.excavator.json;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A {@link Request} provides a mechanism for producing a JSON excavator
 * request.
 */
public class Request {

    /** The ID. */
    private final int id;

    /** The method. */
    private final String method;

    /** The params. */
    private final List<String> params;

    /**
     * Constructor.
     *
     * @param id     The ID.
     * @param method The method.
     * @param params The params.
     */
    public Request(
            @JsonProperty("id") final int id,
            @JsonProperty("method") final String method,
            @JsonProperty("params") final List<String> params) {
        this.id = id;
        this.method = method;
        this.params = params != null
                ? new ArrayList<>(params)
                : Collections.emptyList();
    }

    /**
     * Returns the ID.
     *
     * @return The ID.
     */
    public int getId() {
        return this.id;
    }

    /**
     * Returns the method.
     *
     * @return The method.
     */
    public String getMethod() {
        return this.method;
    }

    /**
     * Returns the params.
     *
     * @return The params.
     */
    public List<String> getParams() {
        return Collections.unmodifiableList(this.params);
    }
}