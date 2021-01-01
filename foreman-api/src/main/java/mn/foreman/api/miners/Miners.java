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

    /**
     * Updates the miner with the provided parameters.
     *
     * @param minerId   The miner ID.
     * @param name      The name.
     * @param apiIp     The API IP (leave null to not update).
     * @param platform  The platform.
     * @param minerType The miner type.
     * @param serial    The serial (leave null to not update).
     *
     * @return The new {@link Miner}.
     */
    Optional<Miner> update(
            int minerId,
            String name,
            String apiIp,
            String platform,
            String minerType,
            String serial);

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

        /** The MAC address. */
        @JsonProperty("mac")
        public String mac;

        /** The miner name. */
        @JsonProperty("name")
        public String name;

        /** The platform. */
        @JsonProperty("platform")
        public String platform;

        /** The miner type. */
        @JsonProperty("minerType")
        public String type;
    }
}
