package mn.foreman.model.miners;

import mn.foreman.model.AbstractBuilder;
import mn.foreman.model.miners.asic.Asic;
import mn.foreman.model.miners.cpu.Cpu;
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
 *     ],
 *     "cpus": [
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

    /** All of the CPUs. */
    private final List<Cpu> cpus;

    /** All of the pools. */
    private final List<Pool> pools;

    /** All of the rigs. */
    private final List<Rig> rigs;

    /**
     * Constructor.
     *
     * @param apiIp   The API IP address.
     * @param apiPort The API port.
     * @param pools   The pools.
     * @param asics   The ASICs.
     */
    private MinerStats(
            @JsonProperty("apiIp") final String apiIp,
            @JsonProperty("apiPort") final int apiPort,
            @JsonProperty("pools") final List<Pool> pools,
            @JsonProperty("asics") final List<Asic> asics,
            @JsonProperty("rigs") final List<Rig> rigs,
            @JsonProperty("cpus") final List<Cpu> cpus) {
        Validate.notEmpty(
                apiIp,
                "apiIp cannot be empty");
        Validate.inclusiveBetween(
                0, Integer.MAX_VALUE, apiPort,
                "apiPort cannot be empty");
        this.apiIp = apiIp;
        this.apiPort = apiPort;
        this.pools = new ArrayList<>(pools);
        this.asics = new ArrayList<>(asics);
        this.rigs = new ArrayList<>(rigs);
        this.cpus = new ArrayList<>(cpus);
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
                            .append(this.apiIp, miner.apiIp)
                            .append(this.apiPort, miner.apiPort)
                            .append(this.pools, miner.pools)
                            .append(this.asics, miner.asics)
                            .append(this.rigs, miner.rigs)
                            .append(this.cpus, miner.cpus)
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
     * Returns the CPUs.
     *
     * @return The CPUs.
     */
    public List<Cpu> getCpus() {
        return Collections.unmodifiableList(this.cpus);
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
                .append(this.apiIp)
                .append(this.apiPort)
                .append(this.pools)
                .append(this.asics)
                .append(this.rigs)
                .append(this.cpus)
                .build();
    }

    @Override
    public String toString() {
        return String.format(
                "%s [ apiIp=%s, " +
                        "apiPort=%d, " +
                        "pools=%s, " +
                        "asics=%s, " +
                        "rigs=%s, " +
                        "cpus=%s" +
                        " ]",
                getClass().getSimpleName(),
                this.apiIp,
                this.apiPort,
                this.pools,
                this.asics,
                this.rigs,
                this.cpus);
    }

    /** A builder for creating {@link MinerStats stats}. */
    public static class Builder
            extends AbstractBuilder<MinerStats> {

        /** All of the ASICs. */
        private final List<Asic> asics = new LinkedList<>();

        /** All of the cpus. */
        private final List<Cpu> cpus = new LinkedList<>();

        /** All of the pools. */
        private final List<Pool> pools = new LinkedList<>();

        /** All of the rigs. */
        private final List<Rig> rigs = new LinkedList<>();

        /** The API IP address. */
        private String apiIp;

        /** The API port. */
        private int apiPort;

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
         * Adds the provided {@link Asic ASICs}.
         *
         * @param asics The {@link Asic ASICs}.
         *
         * @return This builder instance.
         */
        public Builder addAsics(final List<Asic> asics) {
            asics.forEach(this::addAsic);
            return this;
        }

        /**
         * Adds the provided {@link Cpu}.
         *
         * @param cpu The {@link Cpu}.
         *
         * @return This builder instance.
         */
        public Builder addCpu(final Cpu cpu) {
            if (cpu != null) {
                this.cpus.add(cpu);
            }
            return this;
        }

        /**
         * Adds the provided CPUs.
         *
         * @param cpus The CPUs to add.
         *
         * @return This builder instance.
         */
        public Builder addCpus(final List<Cpu> cpus) {
            cpus.forEach(this::addCpu);
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
         * Adds the provided {@link Pool pools}.
         *
         * @param pools The {@link Pool pools} to add.
         *
         * @return This builder instance.
         */
        public Builder addPools(final List<Pool> pools) {
            pools.forEach(this::addPool);
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
         * Adds the provided {@link Rig rigs}.
         *
         * @param rigs The {@link Rig rigs}.
         *
         * @return This builder instance.
         */
        public Builder addRigs(final List<Rig> rigs) {
            rigs.forEach(this::addRig);
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
                    this.apiIp,
                    this.apiPort,
                    this.pools,
                    this.asics,
                    this.rigs,
                    this.cpus);
        }

        /**
         * Populates the builder from the provided stats.
         *
         * @param stats The source stats.
         *
         * @return This builder instance.
         */
        public Builder fromStats(final MinerStats stats) {
            setApiIp(stats.getApiIp());
            setApiPort(stats.getApiPort());
            setPools(stats.getPools());
            addAsics(stats.getAsics());
            setRigs(stats.getRigs());
            addCpus(stats.getCpus());
            return this;
        }

        /**
         * Returns the current pools.
         *
         * @return The {@link Pool pools}.
         */
        public List<Pool> getPools() {
            return Collections.unmodifiableList(this.pools);
        }

        /**
         * Sets the {@link Pool pools}.
         *
         * @param pools The {@link Pool pools}.
         *
         * @return This builder instance.
         */
        public Builder setPools(final List<Pool> pools) {
            this.pools.clear();
            return addPools(pools);
        }

        /**
         * Returns the current rigs.
         *
         * @return The {@link Rig rigs}.
         */
        public List<Rig> getRigs() {
            return Collections.unmodifiableList(this.rigs);
        }

        /**
         * Sets the {@link Rig rigs}.
         *
         * @param rigs The {@link Rig rigs}.
         *
         * @return This builder instance.
         */
        public Builder setRigs(final List<Rig> rigs) {
            this.rigs.clear();
            return addRigs(rigs);
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
         * Sets the {@link Asic ASICs}.
         *
         * @param asics The {@link Asic ASICs}.
         *
         * @return This builder instance.
         */
        public Builder setAsics(final List<Asic> asics) {
            this.asics.clear();
            return addAsics(asics);
        }
    }
}