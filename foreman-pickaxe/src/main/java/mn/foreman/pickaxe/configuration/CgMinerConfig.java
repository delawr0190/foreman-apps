package mn.foreman.pickaxe.configuration;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.Validate;

/** A cgminer configuration. */
public class CgMinerConfig {

    /** The API IP. */
    private String apiIp;

    /** The API port. */
    private int apiPort;

    /** The name. */
    private String name;

    /**
     * Constructor.
     *
     * @param name    The miner name.
     * @param apiIp   The miner API IP.
     * @param apiPort The miner API port.
     */
    public CgMinerConfig(
            @JsonProperty("name") final String name,
            @JsonProperty("apiIp") final String apiIp,
            @JsonProperty("apiPort") final int apiPort) {
        Validate.notEmpty(
                name,
                "name cannot be empty");
        Validate.notEmpty(
                apiIp,
                "apiIp cannot be empty");
        Validate.inclusiveBetween(
                0, Integer.MAX_VALUE, apiPort,
                "apiPort must be positive");
        this.name = name;
        this.apiIp = apiIp;
        this.apiPort = apiPort;
    }

    /**
     * Returns the miner API IP.
     *
     * @return The miner API IP.
     */
    public String getApiIp() {
        return this.apiIp;
    }

    /**
     * Returns the miner API port.
     *
     * @return The miner API port.
     */
    public int getApiPort() {
        return this.apiPort;
    }

    /**
     * Returns the miner name.
     *
     * @return The miner name.
     */
    public String getName() {
        return this.name;
    }
}