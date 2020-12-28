package mn.foreman.api.sitemap;

import mn.foreman.api.actions.StatusSubmit;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Optional;

/**
 * A {@link SiteMap} provides a handler for interacting with the
 * <code>/api/site-map/</code> Foreman API endpoint.
 */
public interface SiteMap {

    /**
     * Changes the site map location of a miner.
     *
     * @param minerId The miner ID.
     * @param rack    The rack.
     * @param row     The row.
     * @param index   The index.
     *
     * @return The response.
     */
    Optional<Response> setLocation(
            int minerId,
            String rack,
            int row,
            int index);

    /** An response. */
    @JsonIgnoreProperties(ignoreUnknown = true)
    class Response {

        /** The status. */
        @JsonProperty("status")
        public StatusSubmit status;
    }
}
