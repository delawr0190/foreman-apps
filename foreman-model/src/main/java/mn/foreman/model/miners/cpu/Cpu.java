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

    /** The temp. */
    private final int temp;

    /** The thread rates. */
    private final List<BigDecimal> threads;

    /**
     * Constructor.
     *
     * @param fanSpeed  The fan speed.
     * @param temp      The temp.
     * @param frequency The frequency.
     * @param threads   The threads.
     */
    private Cpu(
            @JsonProperty("fanSpeed") final int fanSpeed,
            @JsonProperty("temp") final int temp,
            @JsonProperty("frequency") final BigDecimal frequency,
            @JsonProperty("threads") final List<BigDecimal> threads) {
        Validate.notNull(
                frequency,
                "frequency cannot be null");
        Validate.notNull(
                threads,
                "thread cannot be null");
        Validate.notEmpty(
                threads,
                "threads cannot be empty");
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
            final Cpu asic = (Cpu) other;
            isEqual =
                    new EqualsBuilder()
                            .append(this.fanSpeed, asic.fanSpeed)
                            .append(this.temp, asic.temp)
                            .append(this.frequency, asic.frequency)
                            .append(this.threads, asic.threads)
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
                        "fanSpeed=%d, " +
                        "temp=%d, " +
                        "frequency=%s, " +
                        "threads=%s" +
                        " ]",
                getClass().getSimpleName(),
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

        @Override
        public Cpu build() {
            return new Cpu(
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
