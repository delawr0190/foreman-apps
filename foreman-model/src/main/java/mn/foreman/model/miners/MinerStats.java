package mn.foreman.model.miners;

import mn.foreman.model.AbstractBuilder;
import mn.foreman.model.miners.asic.Asic;
import mn.foreman.model.miners.rig.Rig;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.ArrayList;
import java.util.Collections;
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
 *     "pools": [
 *       ...
 *     ],
 *     "asics": [
 *       ...
 *     ],
 *     "rigs": [
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

    /** All of the rigs. */
    private final List<Rig> rigs;

    /**
     * Constructor.
     *
     * @param name    The miner name.
     * @param apiIp   The API IP address.
     * @param apiPort The API port.
     * @param pools   The pools.
     * @param asics   The ASICs.
     */
    private MinerStats(
            @JsonProperty("name") final String name,
            @JsonProperty("apiIp") final String apiIp,
            @JsonProperty("apiPort") final int apiPort,
            @JsonProperty("pools") final List<Pool> pools,
            @JsonProperty("asics") final List<Asic> asics,
            @JsonProperty("rigs") final List<Rig> rigs) {
        Validate.notEmpty(
                name,
                "name cannot be empty");
        Validate.notEmpty(
                apiIp,
                "apiIp cannot be empty");
        Validate.inclusiveBetween(
                0, Integer.MAX_VALUE, apiPort,
                "apiPort cannot be empty");
        this.name = name;
        this.apiIp = apiIp;
        this.apiPort = apiPort;
        this.pools = new ArrayList<>(pools);
        this.asics = new ArrayList<>(asics);
        this.rigs = new ArrayList<>(rigs);
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
                            .append(this.pools, miner.pools)
                            .append(this.asics, miner.asics)
                            .append(this.rigs, miner.rigs)
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
        return Collections.unmodifiableList(this.asics);
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
     * Returns the rigs.
     *
     * @return The rigs.
     */
    public List<Rig> getRigs() {
        return Collections.unmodifiableList(this.rigs);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(this.name)
                .append(this.apiIp)
                .append(this.apiPort)
                .append(this.pools)
                .append(this.asics)
                .append(this.rigs)
                .build();
    }

    @Override
    public String toString() {
        return String.format(
                "%s [ name=%s, " +
                        "apiIp=%s, " +
                        "apiPort=%d, " +
                        "pools=%s, " +
                        "asics=%s, " +
                        "rigs=%s" +
                        " ]",
                getClass().getSimpleName(),
                this.name,
                this.apiIp,
                this.apiPort,
                this.pools,
                this.asics,
                this.rigs);
    }

    /** A builder for creating {@link MinerStats stats}. */
    public static class Builder
            extends AbstractBuilder<MinerStats> {

        /** All of the ASICs. */
        private final List<Asic> asics = new LinkedList<>();

        /** All of the pools. */
        private final List<Pool> pools = new LinkedList<>();

        /** All of the rigs. */
        private final List<Rig> rigs = new LinkedList<>();

        /** The API IP address. */
        private String apiIp;

        /** The API port. */
        private int apiPort;

        /** The miner name. */
        private String name;

        /**
         * Adds the provided {@link Asic}.
         *
         * @param asic The {@link Asic}.
         *
         * @return The builder instance.
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
         * @return The builder instance.
         */
        public Builder addPool(final Pool pool) {
            this.pools.add(pool);
            return this;
        }

        /**
         * Adds the provided {@link Rig}.
         *
         * @param rig The {@link Rig}.
         *
         * @return This builder instance.
         */
        public Builder addRig(final Rig rig) {
            this.rigs.add(rig);
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
                    this.pools,
                    this.asics,
                    this.rigs);
        }

        /**
         * Sets the API IP.
         *
         * @param apiIp The API IP.
         *
         * @return The builder instance.
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
         * @return The builder instance.
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
         * @return The builder instance.
         */
        public Builder setName(final String name) {
            this.name = name;
            return this;
        }
    }
}