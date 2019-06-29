package mn.foreman.model.miners.rig;

import mn.foreman.model.AbstractBuilder;
import mn.foreman.model.miners.FanInfo;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/** A {@link Gpu} represents a single GPU in a {@link Rig}. */
public class Gpu {

    /** The bus index. */
    private final int bus;

    /** The fan info. */
    private final FanInfo fans;

    /** The frequency info. */
    private final FreqInfo freqInfo;

    /** The GPU index. */
    private final int index;

    /** The name. */
    private final String name;

    /** The temperature. */
    private final int temp;

    /**
     * Constructor.
     *
     * @param name     The name.
     * @param bus      The bus.
     * @param fans     The fans.
     * @param freqInfo The freq info.
     * @param index    The index.
     * @param temp     The temp.
     */
    private Gpu(
            @JsonProperty("bus") final int bus,
            @JsonProperty("fans") final FanInfo fans,
            @JsonProperty("freqInfo") final FreqInfo freqInfo,
            @JsonProperty("index") final int index,
            @JsonProperty("name") final String name,
            @JsonProperty("temp") final int temp) {
        Validate.isTrue(
                bus >= 0,
                "Bus must be >= 0");
        Validate.notNull(
                fans,
                "Fans cannot be null");
        Validate.notNull(
                freqInfo,
                "Freq cannot be null");
        Validate.isTrue(
                index >= 0,
                "Index must be >= 0");
        Validate.notNull(
                name,
                "Name cannot be null");
        Validate.notEmpty(
                name,
                "Name cannot be empty");
        this.bus = bus;
        this.fans = fans;
        this.freqInfo = freqInfo;
        this.index = index;
        this.name = name;
        this.temp = temp;
    }

    @Override
    public boolean equals(final Object other) {
        final boolean isEqual;
        if (other == null) {
            isEqual = false;
        } else if (getClass() != other.getClass()) {
            isEqual = false;
        } else {
            final Gpu gpu = (Gpu) other;
            isEqual =
                    new EqualsBuilder()
                            .append(this.bus, gpu.bus)
                            .append(this.fans, gpu.fans)
                            .append(this.freqInfo, gpu.freqInfo)
                            .append(this.index, gpu.index)
                            .append(this.name, gpu.name)
                            .append(this.temp, gpu.temp)
                            .isEquals();
        }
        return isEqual;
    }

    /**
     * Returns the bus index.
     *
     * @return The bus index.
     */
    public int getBus() {
        return this.bus;
    }

    /**
     * Returns the fan info.
     *
     * @return The fan info.
     */
    public FanInfo getFans() {
        return this.fans;
    }

    /**
     * Returns the frequency info.
     *
     * @return The frequency info.
     */
    public FreqInfo getFreqInfo() {
        return this.freqInfo;
    }

    /**
     * Returns the GPU index.
     *
     * @return The GPU index.
     */
    public int getIndex() {
        return this.index;
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
     * Returns the temp.
     *
     * @return The temp.
     */
    public int getTemp() {
        return this.temp;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(this.bus)
                .append(this.fans)
                .append(this.freqInfo)
                .append(this.index)
                .append(this.name)
                .append(this.temp)
                .hashCode();
    }

    @Override
    public String toString() {
        return String.format(
                "%s [ " +
                        "bus=%s, " +
                        "fans=%s, " +
                        "freqInfo=%s, " +
                        "index=%s, " +
                        "name=%s, " +
                        "temp=%s" +
                        " ]",
                getClass().getSimpleName(),
                this.bus,
                this.fans,
                this.freqInfo,
                this.index,
                this.name,
                this.temp);
    }

    /** A builder for creating {@link Gpu GPUs}. */
    public static class Builder
            extends AbstractBuilder<Gpu> {

        /** The bus separator. */
        private static final String BUS_SEPARATOR = ":";

        /** The bus. */
        private int bus;

        /** The fan info. */
        private FanInfo fans;

        /** The frequency info. */
        private FreqInfo freqInfo;

        /** The GPU index. */
        private int index;

        /** The name. */
        private String name;

        /** The temp. */
        private int temp;

        @Override
        public Gpu build() {
            return new Gpu(
                    this.bus,
                    this.fans,
                    this.freqInfo,
                    this.index,
                    this.name,
                    this.temp);
        }

        /**
         * Sets the bus.
         *
         * @param bus The bus.
         *
         * @return This builder instance.
         */
        public Builder setBus(final int bus) {
            this.bus = bus;
            return this;
        }

        /**
         * Sets the bus.
         *
         * @param bus The bus.
         *
         * @return This builder instance.
         */
        public Builder setBus(final String bus) {
            if (bus != null && !bus.isEmpty()) {
                if (bus.contains(BUS_SEPARATOR)) {
                    setBus(Integer.valueOf(bus.split(BUS_SEPARATOR)[0], 16));
                } else {
                    setBus(Integer.parseInt(bus));
                }
            }
            return this;
        }

        /**
         * Sets the fans.
         *
         * @param fans The fans.
         *
         * @return This builder instance.
         */
        public Builder setFans(final FanInfo fans) {
            this.fans = fans;
            return this;
        }

        /**
         * Sets the freq info.
         *
         * @param freqInfo The freq info.
         *
         * @return This builder instance.
         */
        public Builder setFreqInfo(final FreqInfo freqInfo) {
            this.freqInfo = freqInfo;
            return this;
        }

        /**
         * Sets the index.
         *
         * @param index The index.
         *
         * @return This builder instance.
         */
        public Builder setIndex(final int index) {
            this.index = index;
            return this;
        }

        /**
         * Sets the index.
         *
         * @param index The index.
         *
         * @return This builder instance.
         */
        public Builder setIndex(final String index) {
            if (index != null && !index.isEmpty()) {
                setIndex(Integer.parseInt(index));
            }
            return this;
        }

        /**
         * Sets the name.
         *
         * @param name The name.
         *
         * @return This builder instance.
         */
        public Builder setName(final String name) {
            if (name != null) {
                this.name = name
                        .trim()
                        .replace("Advanced Micro Devices, Inc.", "AMD");
            }
            return this;
        }

        /**
         * Sets the temp.
         *
         * @param temp The temp.
         *
         * @return This builder instance.
         */
        public Builder setTemp(final int temp) {
            this.temp = temp;
            return this;
        }

        /**
         * Sets the temp.
         *
         * @param temp The temp.
         *
         * @return This builder instance.
         */
        public Builder setTemp(final String temp) {
            if (temp != null && !temp.isEmpty()) {
                setTemp((int) Double.parseDouble(temp));
            }
            return this;
        }
    }
}