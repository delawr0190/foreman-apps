package mn.foreman.api.actions;

import mn.foreman.model.Network;
import mn.foreman.model.Pool;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Optional;

/**
 * An {@link Actions} provides a handler for interacting with the
 * <code>/api/actions</code> Foreman API endpoint.
 */
public interface Actions {

    /**
     * Changes the network for the provided miner.
     *
     * @param minerId The miner ID.
     * @param network The new network configuration.
     *
     * @return The response, if present.
     */
    Optional<Response> changeNetwork(
            int minerId,
            Network network);

    /**
     * Changes the pools for the provided miner.
     *
     * @param minerId The miner ID.
     * @param pools   The new pools.
     *
     * @return The response, if present.
     */
    Optional<Response> changePools(
            int minerId,
            List<Pool> pools);

    /**
     * Gets the status of the provided command.
     *
     * @param command The command.
     *
     * @return The status, if present.
     */
    Optional<StatusRunning> status(
            int command);

    /** An action response. */
    @JsonIgnoreProperties(ignoreUnknown = true)
    class Response {

        /** The new command ID. */
        @JsonProperty("command")
        public int command;

        /** The status. */
        @JsonProperty("status")
        public StatusSubmit status;
    }
}
