package mn.foreman.cgminer.response;

import mn.foreman.model.AbstractBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * A POJO representation of the status section, as defined in the API-README:
 *
 * <pre>
 * The STATUS section is:
 *
 * STATUS=X,When=NNN,Code=N,Msg=string,Description=string|
 *
 * STATUS=X Where X is one of:
 * W - Warning
 * I - Informational
 * S - Success
 * E - Error
 * F - Fatal (code bug)
 *
 * When=NNN
 * Standard long time of request in seconds
 *
 * Code=N
 * Each unique reply has a unique Code (See api.c - #define MSG_NNNNNN)
 *
 * Msg=string
 * Message matching the Code value N
 *
 * Description=string
 * This defaults to the cgminer version but is the value of --api-description
 * if it was specified at runtime.
 * </pre>
 */
public class CgMinerStatusSection {

    /** The code. */
    private final int code;

    /** The description. */
    private final String description;

    /** The message. */
    private final String message;

    /** The statusCode. */
    private final CgMinerStatusCode statusCode;

    /** Timestamp of the message. */
    private final long when;

    /**
     * Constructor.
     *
     * @param statusCode  The status code.
     * @param when        Timestamp of the message.
     * @param code        The code.
     * @param message     The message.
     * @param description The description.
     */
    private CgMinerStatusSection(
            @JsonProperty("STATUS") final CgMinerStatusCode statusCode,
            @JsonProperty("When") final long when,
            @JsonProperty("Code") final int code,
            @JsonProperty("Msg") final String message,
            @JsonProperty("Description") final String description) {
        this.statusCode = statusCode;
        this.when = when;
        this.code = code;
        this.message = message;
        this.description = description;
    }

    @Override
    public boolean equals(final Object other) {
        final boolean isEqual;
        if (other == null) {
            isEqual = false;
        } else if (getClass() != other.getClass()) {
            isEqual = false;
        } else {
            final CgMinerStatusSection statusSection =
                    (CgMinerStatusSection) other;
            isEqual =
                    new EqualsBuilder()
                            .append(
                                    this.statusCode,
                                    statusSection.statusCode)
                            .append(
                                    this.when,
                                    statusSection.when)
                            .append(
                                    this.code,
                                    statusSection.code)
                            .append(
                                    this.message,
                                    statusSection.message)
                            .append(
                                    this.description,
                                    statusSection.description)
                            .isEquals();
        }
        return isEqual;
    }

    /**
     * Returns the code.
     *
     * @return The code.
     */
    public int getCode() {
        return this.code;
    }

    /**
     * Returns the description.
     *
     * @return The description.
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Returns the message.
     *
     * @return The message.
     */
    public String getMessage() {
        return this.message;
    }

    /**
     * Returns the statusCode.
     *
     * @return The statusCode.
     */
    public CgMinerStatusCode getStatusCode() {
        return this.statusCode;
    }

    /**
     * Returns the timestamp.
     *
     * @return The timestamp.
     */
    public long getWhen() {
        return this.when;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(this.statusCode)
                .append(this.when)
                .append(this.code)
                .append(this.message)
                .append(this.description)
                .hashCode();
    }

    @Override
    public String toString() {
        return String.format(
                "%s [ statusCode=%s, when=%d, code=%d, message=%s, desc=%s ]",
                getClass().getSimpleName(),
                this.statusCode,
                this.when,
                this.code,
                this.message,
                this.description);
    }

    /** A builder for creating {@link CgMinerStatusSection}. */
    public static class Builder
            extends AbstractBuilder<CgMinerStatusSection> {

        /** The code. */
        private int code = UNDEFINED_INT;

        /** The description. */
        private String description = UNDEFINED_STRING;

        /** The message. */
        private String message = UNDEFINED_STRING;

        /** The statusCode. */
        private CgMinerStatusCode statusCode = CgMinerStatusCode.FATAL;

        /** Timestamp of the message. */
        private long when = UNDEFINED_INT;

        @Override
        public CgMinerStatusSection build() {
            return new CgMinerStatusSection(
                    this.statusCode,
                    this.when,
                    this.code,
                    this.message,
                    this.description);
        }

        /**
         * Sets the code.
         *
         * @param code The code.
         *
         * @return The builder instance.
         */
        public Builder setCode(final int code) {
            this.code = code;
            return this;
        }

        /**
         * Sets the description.
         *
         * @param description The description.
         *
         * @return The builder instance.
         */
        public Builder setDescription(final String description) {
            this.description = description;
            return this;
        }

        /**
         * Sets the message.
         *
         * @param message The message.
         *
         * @return The builder instance.
         */
        public Builder setMessage(final String message) {
            this.message = message;
            return this;
        }

        /**
         * Sets the status code.
         *
         * @param code The code.
         *
         * @return The builder instance.
         */
        public Builder setStatusCode(final CgMinerStatusCode code) {
            this.statusCode = code;
            return this;
        }

        /**
         * Sets when.
         *
         * @param when When.
         *
         * @return The builder instance.
         */
        public Builder setWhen(final long when) {
            this.when = when;
            return this;
        }
    }
}