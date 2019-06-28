package mn.foreman.model.miners.cpu;

import mn.foreman.model.AbstractBuilder;
import mn.foreman.model.miners.BigDecimalSerializer;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * The following POJO represents a model object for a CPU that's performing
 * mining.
 */
public class Cpu {

    /** The fan speed. */
    private final int fanSpeed;

    /** The frequency. */
    @JsonSerialize(using = BigDecimalSerializer.class)
    private final BigDecimal frequency;

    /** The CPU name. */
    private final String name;

    /** The temp. */
    private final int temp;

    /** The thread rates. */
    private final List<BigDecimal> threads;

    /**
     * Constructor.
     *
     * @param name      The name.
     * @param fanSpeed  The fan speed.
     * @param temp      The temp.
     * @param frequency The frequency.
     * @param threads   The threads.
     */
    private Cpu(
            @JsonProperty("name") final String name,
            @JsonProperty("fanSpeed") final int fanSpeed,
            @JsonProperty("temp") final int temp,
            @JsonProperty("frequency") final BigDecimal frequency,
            @JsonProperty("threads") final List<BigDecimal> threads) {
        Validate.notNull(
                name,
                "name cannot be null");
        Validate.notEmpty(
                name,
                "name cannot be empty");
        Validate.notNull(
                frequency,
                "frequency cannot be null");
        Validate.notNull(
                threads,
                "thread cannot be null");
        Validate.notEmpty(
                threads,
                "threads cannot be empty");
        this.name = name;
        this.fanSpeed = fanSpeed;
        this.temp = temp;
        this.frequency = frequency;
        this.threads = new ArrayList<>(threads);
    }

    @Override
    public boolean equals(final Object other) {
        final boolean isEqual;
        if (other == null) {
            isEqual = false;
        } else if (getClass() != other.getClass()) {
            isEqual = false;
        } else {
            final Cpu cpu = (Cpu) other;
            isEqual =
                    new EqualsBuilder()
                            .append(this.name, cpu.name)
                            .append(this.fanSpeed, cpu.fanSpeed)
                            .append(this.temp, cpu.temp)
                            .append(this.frequency, cpu.frequency)
                            .append(this.threads, cpu.threads)
                            .isEquals();
        }
        return isEqual;
    }

    /**
     * Returns the fan speed.
     *
     * @return The fan speed.
     */
    public int getFanSpeed() {
        return this.fanSpeed;
    }

    /**
     * Returns the frequency.
     *
     * @return The frequency.
     */
    public BigDecimal getFrequency() {
        return this.frequency;
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

    /**
     * Returns the threads.
     *
     * @return The threads.
     */
    public List<BigDecimal> getThreads() {
        return Collections.unmodifiableList(this.threads);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(this.name)
                .append(this.fanSpeed)
                .append(this.temp)
                .append(this.frequency)
                .append(this.threads)
                .hashCode();
    }

    @Override
    public String toString() {
        return String.format(
                "%s [ " +
                        "name=%s, " +
                        "fanSpeed=%d, " +
                        "temp=%d, " +
                        "frequency=%s, " +
                        "threads=%s" +
                        " ]",
                getClass().getSimpleName(),
                this.name,
                this.fanSpeed,
                this.temp,
                this.frequency,
                this.threads);
    }

    /** A builder for creating {@link Cpu CPUs}. */
    public static class Builder
            extends AbstractBuilder<Cpu> {

        /** The threads. */
        private final List<BigDecimal> threads = new LinkedList<>();

        /** The fan speed. */
        private int fanSpeed = UNDEFINED_INT;

        /** The frequency. */
        private BigDecimal frequency = UNDEFINED_DECIMAL;

        /** The name. */
        private String name = UNDEFINED_STRING;

        /** The temp. */
        private int temp = UNDEFINED_INT;

        /**
         * Adds a thread.
         *
         * @param thread The thread.
         *
         * @return This builder instance.
         */
        public Builder addThread(final BigDecimal thread) {
            if (thread != null) {
                this.threads.add(thread);
            }
            return this;
        }

        /**
         * Adds the threads.
         *
         * @param threads The threads.
         *
         * @return This builder instance.
         */
        public Builder addThreads(final List<BigDecimal> threads) {
            threads.forEach(this::addThread);
            return this;
        }

        @Override
        public Cpu build() {
            return new Cpu(
                    this.name,
                    this.fanSpeed,
                    this.temp,
                    this.frequency,
                    this.threads);
        }

        /**
         * Sets the fan speed.
         *
         * @param fanSpeed The fan speed.
         *
         * @return This builder instance.
         */
        public Builder setFanSpeed(final int fanSpeed) {
            this.fanSpeed = fanSpeed;
            return this;
        }

        /**
         * Sets the fan speed.
         *
         * @param fanSpeed The fan speed.
         *
         * @return This builder instance.
         */
        public Builder setFanSpeed(final String fanSpeed) {
            return setFanSpeed((int) Double.parseDouble(fanSpeed));
        }

        /**
         * Sets the frequency.
         *
         * @param frequency The frequency.
         *
         * @return This builder instance.
         */
        public Builder setFrequency(final BigDecimal frequency) {
            if (frequency != null) {
                this.frequency = frequency;
            }
            return this;
        }

        /**
         * Sets the frequency.
         *
         * @param frequency The frequency.
         *
         * @return This builder instance.
         */
        public Builder setFrequency(final String frequency) {
            return setFrequency(new BigDecimal(frequency));
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
                this.name = name.trim();
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
        public Builder setTemp(final String temp) {
            return setTemp((int) Double.parseDouble(temp));
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
    }
}
