package mn.foreman.cgminer.response;

import mn.foreman.model.AbstractBuilder;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.Validate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * A generic cgminer response.
 *
 * <p>Note: the {@link #values} will be populated differently depending on what
 * command was executed.</p>
 *
 * <p>The {@link CgMinerStatusSection} is always present, even on a failed
 * command.</p>
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CgMinerResponse {

    /** The ID. */
    @JsonProperty("id")
    private long id;

    /** The {@link CgMinerStatusSection}. */
    @JsonProperty("STATUS")
    private CgMinerStatusSection statusSection;

    /** The returned values. */
    @JsonAlias({"SUMMARY", "DEVS", "POOLS"})
    private List<Map<String, String>> values;

    /**
     * Constructor.
     *
     * <p>Note:  intentionally hidden (only for JACKSON).</p>
     */
    private CgMinerResponse() {
        // Do nothing.
    }

    /**
     * Constructor.
     *
     * @param statusSection The {@link CgMinerStatusSection}.
     * @param values        The values.
     * @param id            The ID.
     */
    private CgMinerResponse(
            final CgMinerStatusSection statusSection,
            final List<Map<String, String>> values,
            final long id) {
        Validate.notNull(
                statusSection,
                "statusSection is required");
        this.statusSection = statusSection;
        this.values = new ArrayList<>(values);
        this.id = id;
    }

    /**
     * Returns the ID.
     *
     * @return The ID.
     */
    public long getId() {
        return this.id;
    }

    /**
     * Returns the {@link CgMinerStatusSection}.
     *
     * @return The {@link CgMinerStatusSection}.
     */
    public CgMinerStatusSection getStatusSection() {
        return this.statusSection;
    }

    /**
     * Returns the values.
     *
     * @return The values.
     */
    public List<Map<String, String>> getValues() {
        List<Map<String, String>> values = this.values;
        if (this.values == null) {
            values = Collections.emptyList();
        }
        return Collections.unmodifiableList(values);
    }

    /**
     * Checks to see if the response contains data.
     *
     * @return Whether or not the response contains data.
     */
    public boolean hasValues() {
        return ((isSuccess()) &&
                (this.values != null) &&
                (!this.values.isEmpty()));
    }

    /**
     * Checks to see if the message was successful.
     *
     * @return Whether or not the message was successful.
     */
    public boolean isSuccess() {
        return this.statusSection.getStatusCode()
                .equals(CgMinerStatusCode.SUCCESS);
    }

    /** A builder for creating new {@link CgMinerResponse responses}. */
    public static class Builder
            extends AbstractBuilder<CgMinerResponse> {

        /** The values. */
        private final List<Map<String, String>> values =
                new ArrayList<>();

        /** The ID. */
        private long id;

        /** The {@link CgMinerStatusSection}. */
        private CgMinerStatusSection statusSection =
                new CgMinerStatusSection.Builder()
                        .setStatusCode(CgMinerStatusCode.FATAL)
                        .build();

        /**
         * Adds the provided values.
         *
         * @param values The values.
         *
         * @return The {@link Builder} instance.
         */
        public Builder addValues(final Map<String, String> values) {
            this.values.add(values);
            return this;
        }

        @Override
        public CgMinerResponse build() {
            return new CgMinerResponse(
                    this.statusSection,
                    this.values,
                    this.id);
        }

        /**
         * Sets the ID.
         *
         * @param id The ID.
         *
         * @return The builder instance.
         */
        public Builder setId(final long id) {
            this.id = id;
            return this;
        }

        /**
         * Sets the {@link CgMinerStatusSection}.
         *
         * @param statusSection The section.
         *
         * @return The builder instance.
         */
        public Builder setStatusSection(
                final CgMinerStatusSection statusSection) {
            this.statusSection = statusSection;
            return this;
        }
    }
}