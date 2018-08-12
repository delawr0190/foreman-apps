package mn.foreman.model.metadata;

import mn.foreman.model.AbstractBuilder;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.time.ZonedDateTime;

/**
 * The following POJO represents a JSON object with the following format:
 *
 * <pre>
 *   {
 *     "timestamp": "2017-12-11T18:53:49.600+0000",
 *     "appVersion": "1.2.3",
 *     "group": "aZ28fZzj"
 *   }
 * </pre>
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Metadata {

    /** The API version. */
    private final ApiVersion apiVersion;

    /** The group. */
    private final String group;

    /** The timestamp. */
    @JsonFormat(
            shape = JsonFormat.Shape.STRING,
            pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    private final ZonedDateTime timestamp;

    /**
     * Constructor.
     *
     * @param timestamp  The timestamp.
     * @param apiVersion The API version.
     */
    private Metadata(
            @JsonProperty("timestamp") final ZonedDateTime timestamp,
            @JsonProperty("apiVersion") final ApiVersion apiVersion,
            @JsonProperty("group") final String group) {
        Validate.notNull(
                timestamp,
                "timestamp cannot be null");
        Validate.notNull(
                apiVersion,
                "apiVersion cannot be null");
        this.timestamp = timestamp;
        this.apiVersion = apiVersion;
        this.group = group;
    }

    @Override
    public boolean equals(final Object other) {
        final boolean isEqual;
        if (other == null) {
            isEqual = false;
        } else if (getClass() != other.getClass()) {
            isEqual = false;
        } else {
            final Metadata metadata = (Metadata) other;
            isEqual =
                    new EqualsBuilder()
                            .append(this.timestamp, metadata.timestamp)
                            .append(this.apiVersion, metadata.apiVersion)
                            .append(this.group, metadata.group)
                            .isEquals();
        }
        return isEqual;
    }

    /**
     * Returns the {@link ApiVersion}.
     *
     * @return The {@link ApiVersion}.
     */
    public ApiVersion getApiVersion() {
        return this.apiVersion;
    }

    /**
     * Returns the group.
     *
     * @return The group.
     */
    public String getGroup() {
        return this.group;
    }

    /**
     * Returns the timestamp.
     *
     * @return The timestamp.
     */
    public ZonedDateTime getTimestamp() {
        return this.timestamp;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(this.timestamp)
                .append(this.apiVersion)
                .append(this.group)
                .hashCode();
    }

    @Override
    public String toString() {
        return String.format(
                "%s [ timestamp=%s, apiVersion=%s, group=%s ]",
                getClass().getSimpleName(),
                this.timestamp,
                this.apiVersion,
                this.group);
    }

    /** A builder for creating {@link Metadata metadatas}. */
    public static class Builder
            extends AbstractBuilder<Metadata> {

        /** The API version. */
        private ApiVersion apiVersion = ApiVersion.V1_0_0;

        /** The group. */
        private String group = null;

        /** The timestamp. */
        private ZonedDateTime timestamp = ZonedDateTime.now();

        @Override
        public Metadata build() {
            return new Metadata(
                    this.timestamp,
                    this.apiVersion,
                    this.group);
        }

        /**
         * Sets the API version.
         *
         * @param apiVersion The version.
         *
         * @return The builder instance.
         */
        public Builder setApiVersion(final ApiVersion apiVersion) {
            this.apiVersion = apiVersion;
            return this;
        }

        /**
         * Sets the group.
         *
         * @param group The group.
         *
         * @return This builder instance.
         */
        public Builder setGroup(final String group) {
            this.group = group;
            return this;
        }

        /**
         * Sets the timestamp.
         *
         * @param timestamp The timestamp.
         *
         * @return The builder instance.
         */
        public Builder setTimestamp(final ZonedDateTime timestamp) {
            this.timestamp = timestamp;
            return this;
        }
    }
}
