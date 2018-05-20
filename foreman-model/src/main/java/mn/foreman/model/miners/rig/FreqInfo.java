package mn.foreman.model.miners.rig;

import mn.foreman.model.AbstractBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/** A {@link FreqInfo} represents {@link Gpu} frequency readings. */
public class FreqInfo {

    /** The core frequency. */
    private final int freq;

    /** The memory frequency. */
    private final int memFreq;

    /**
     * Constructor.
     *
     * @param freq    The core frequency.
     * @param memFreq The memory frequency.
     */
    private FreqInfo(
            @JsonProperty("freq") final int freq,
            @JsonProperty("memFreq") final int memFreq) {
        Validate.isTrue(
                freq > 0,
                "Freq must be > 0");
        Validate.isTrue(
                memFreq > 0,
                "Mem freq must be > 0");
        this.freq = freq;
        this.memFreq = memFreq;
    }

    @Override
    public boolean equals(final Object other) {
        final boolean isEqual;
        if (other == null) {
            isEqual = false;
        } else if (getClass() != other.getClass()) {
            isEqual = false;
        } else {
            final FreqInfo freqInfo = (FreqInfo) other;
            isEqual =
                    new EqualsBuilder()
                            .append(this.freq, freqInfo.freq)
                            .append(this.memFreq, freqInfo.memFreq)
                            .isEquals();
        }
        return isEqual;
    }

    /**
     * Returns the frequency.
     *
     * @return The frequency.
     */
    public int getFreq() {
        return this.freq;
    }

    /**
     * Returns the memory frequency.
     *
     * @return The memory frequency.
     */
    public int getMemFreq() {
        return this.memFreq;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(this.freq)
                .append(this.memFreq)
                .hashCode();
    }

    @Override
    public String toString() {
        return String.format(
                "%s [ " +
                        "freq=%s, " +
                        "memFreq=%s" +
                        " ]",
                getClass().getSimpleName(),
                this.freq,
                this.memFreq);
    }

    /** A builder for creating {@link FreqInfo FreqInfos}. */
    public static class Builder
            extends AbstractBuilder<FreqInfo> {

        /** The core frequency. */
        private int freq;

        /** The memory frequency. */
        private int memFreq;

        @Override
        public FreqInfo build() {
            return new FreqInfo(
                    this.freq,
                    this.memFreq);
        }

        /**
         * Sets the core frequency.
         *
         * @param freq The core frequency.
         *
         * @return This builder instance.
         */
        public Builder setFreq(final String freq) {
            if (freq != null && !freq.isEmpty()) {
                this.freq = Integer.parseInt(freq);
            }
            return this;
        }

        /**
         * Sets the memory frequency.
         *
         * @param memFreq The memory frequency.
         *
         * @return This builder instance.
         */
        public Builder setMemFreq(final String memFreq) {
            if (memFreq != null && !memFreq.isEmpty()) {
                this.memFreq = Integer.parseInt(memFreq);
            }
            return this;
        }
    }
}