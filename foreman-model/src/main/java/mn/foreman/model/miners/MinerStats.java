package mn.foreman.model.miners;

import mn.foreman.model.AbstractBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * The following POJO represents a JSON object with the following format:
 *
 * <pre>
 *   {
 *     "name": "miner #1",
 *     "apiIp": "192.168.1.1",
 *     "apiPort": 42069,
 *     "temperature": 69,
 *     "speedInfo": {
 *       ...
 *     },
 *     "pools": [
 *       ...
 *     ],
 *     "asics": [
 *       ...
 *     ]
 *   }
 * </pre>
 */
public class MinerStats {

    /** The API IP address. */
    private final String apiIp;

    /** The API port. */
    private final int apiPort;

    /** All of the ASICs. */
    private final List<Asic> asics;

    /** The miner name. */
    private final String name;

    /** All of the pools. */
    private final List<Pool> pools;

    /** The speed-related information. */
    private final SpeedInfo speedInfo;

    /** The temperature (could be average). */
    private final BigDecimal temperature;

    /**
     * Constructor.
     *
     * @param name        The miner name.
     * @param apiIp       The API IP address.
     * @param apiPort     The API port.
     * @param temperature The temperature.
     * @param speedInfo   The speed-related information.
     * @param pools       The pools.
     * @param asics       The ASICs.
     */
    private MinerStats(
            @JsonProperty("name") final String name,
            @JsonProperty("apiIp") final String apiIp,
            @JsonProperty("apiPort") final int apiPort,
            @JsonProperty("temperature") final BigDecimal temperature,
            @JsonProperty("speedInfo") final SpeedInfo speedInfo,
            @JsonProperty("pools") final List<Pool> pools,
            @JsonProperty("asics") final List<Asic> asics) {
        Validate.notEmpty(
                name,
                "name cannot be empty");
        Validate.notEmpty(
                apiIp,
                "apiIp cannot be empty");
        Validate.inclusiveBetween(
                0, Integer.MAX_VALUE, apiPort,
                "apiPort cannot be empty");
        Validate.notNull(
                temperature,
                "temperature cannot be null");
        Validate.notNull(
                speedInfo,
                "speedInfo cannot be null");
        this.name = name;
        this.apiIp = apiIp;
        this.apiPort = apiPort;
        this.temperature = temperature;
        this.speedInfo = speedInfo;
        this.pools = new ArrayList<>(pools);
        this.asics = new ArrayList<>(asics);
    }

    @Override
    public boolean equals(final Object other) {
        final boolean isEqual;
        if (other == null) {
            isEqual = false;
        } else if (getClass() != other.getClass()) {
            isEqual = false;
        } else {
            final MinerStats miner = (MinerStats) other;
            isEqual =
                    new EqualsBuilder()
                            .append(this.name, miner.name)
                            .append(this.apiIp, miner.apiIp)
                            .append(this.apiPort, miner.apiPort)
                            .append(this.temperature, miner.temperature)
                            .append(this.speedInfo, miner.speedInfo)
                            .append(this.pools, miner.pools)
                            .append(this.asics, miner.asics)
                            .isEquals();
        }
        return isEqual;
    }

    /**
     * Returns the API IP.
     *
     * @return The API IP.
     */
    public String getApiIp() {
        return this.apiIp;
    }

    /**
     * Returns the API port.
     *
     * @return The API port.
     */
    public int getApiPort() {
        return this.apiPort;
    }

    /**
     * Returns the ASICs.
     *
     * @return The ASICs.
     */
    public List<Asic> getAsics() {
        return this.asics;
    }

    /**
     * Returns the name.
     *
     * @return The name.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Returns the pools.
     *
     * @return The pools.
     */
    public List<Pool> getPools() {
        return this.pools;
    }

    /**
     * Returns the speed-related information.
     *
     * @return The speed-related information.
     */
    public SpeedInfo getSpeedInfo() {
        return this.speedInfo;
    }

    /**
     * Returns the temperature.
     *
     * @return The temperature.
     */
    public BigDecimal getTemperature() {
        return this.temperature;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(this.name)
                .append(this.apiIp)
                .append(this.apiPort)
                .append(this.temperature)
                .append(this.speedInfo)
                .append(this.pools)
                .append(this.asics)
                .build();
    }

    @Override
    public String toString() {
        return String.format(
                "%s [ name=%s, " +
                        "apiIp=%s, " +
                        "apiPort=%d, " +
                        "temperature=%s, " +
                        "speedInfo=%s, " +
                        "pools=%s, " +
                        "asics=%s ]",
                getClass().getSimpleName(),
                this.name,
                this.apiIp,
                this.apiPort,
                this.temperature,
                this.speedInfo,
                this.pools,
                this.asics);
    }

    /** A builder for creating {@link MinerStats stats}. */
    public static class Builder
            extends AbstractBuilder<MinerStats> {

        /** All of the ASICs. */
        private final List<Asic> asics = new LinkedList<>();

        /** All of the pools. */
        private final List<Pool> pools = new LinkedList<>();

        /** The API IP address. */
        private String apiIp;

        /** The API port. */
        private int apiPort;

        /** The miner name. */
        private String name;

        /** The speed-related information. */
        private SpeedInfo speedInfo = new SpeedInfo.Builder().build();

        /** The temperature (could be average). */
        private BigDecimal temperature = UNDEFINED_DECIMAL;

        /**
         * Adds the provided {@link Asic}.
         *
         * @param asic The {@link Asic}.
         *
         * @return The {@link Builder} instance.
         */
        public Builder addAsic(final Asic asic) {
            this.asics.add(asic);
            return this;
        }

        /**
         * Adds the provided {@link Pool}.
         *
         * @param pool The {@link Pool}.
         *
         * @return The {@link Builder} instance.
         */
        public Builder addPool(final Pool pool) {
            this.pools.add(pool);
            return this;
        }

        /**
         * Creates the new {@link MinerStats}.
         *
         * @return The new {@link MinerStats}.
         */
        @Override
        public MinerStats build() {
            return new MinerStats(
                    this.name,
                    this.apiIp,
                    this.apiPort,
                    this.temperature,
                    this.speedInfo,
                    this.pools,
                    this.asics);
        }

        /**
         * Sets the API IP.
         *
         * @param apiIp The API IP.
         *
         * @return The {@link Builder} instance.
         */
        public Builder setApiIp(final String apiIp) {
            this.apiIp = apiIp;
            return this;
        }

        /**
         * Sets the API port.
         *
         * @param apiPort The API port.
         *
         * @return The {@link Builder} instance.
         */
        public Builder setApiPort(final int apiPort) {
            this.apiPort = apiPort;
            return this;
        }

        /**
         * Sets the name.
         *
         * @param name The name.
         *
         * @return The {@link Builder} instance.
         */
        public Builder setName(final String name) {
            this.name = name;
            return this;
        }

        /**
         * Sets the {@link SpeedInfo}.
         *
         * @param speedInfo The {@link SpeedInfo}.
         *
         * @return The {@link Builder} instance.
         */
        public Builder setSpeedInfo(final SpeedInfo speedInfo) {
            this.speedInfo = speedInfo;
            return this;
        }

        /**
         * Sets the temperature.
         *
         * @param temperature The temperature.
         *
         * @return The {@link Builder} instance.
         */
        public Builder setTemperature(final BigDecimal temperature) {
            this.temperature = temperature;
            return this;
        }
    }
}