package mn.foreman.api.miners;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

/**
 * A {@link Miners} provides a handler for interacting with the
 * <code>/api/miners</code> Foreman API endpoint.
 */
public interface Miners {

    /**
     * Returns all of the miners.
     *
     * @return The miners.
     */
    List<Miner> all();

    /**
     * Returns one {@link Miner}.
     *
     * @param minerId The ID.
     *
     * @return The miner.
     */
    Optional<Miner> one(int minerId);

    /** A miner object. */
    @JsonIgnoreProperties(ignoreUnknown = true)
    class Miner {

        /** The API ip. */
        @JsonProperty("apiIp")
        public String apiIp;

        /** The API port. */
        @JsonProperty("apiPort")
        public int apiPort;

        /** The miner ID. */
        @JsonProperty("id")
        public int id;

        /** When the miner was last updated. */
        @JsonProperty("lastUpdated")
        public Instant lastUpdated;

        /** The miner name. */
        @JsonProperty("name")
        public String name;
    }
}
