package mn.foreman.cgminer.request;

import mn.foreman.model.AbstractBuilder;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * A {@link CgMinerRequest} represents a command to be sent to cgminer.
 *
 * <p>The command that will be generated will adhere to the JSON api.</p>
 */
@JsonSerialize(using = CgMinerRequestSerializer.class)
public class CgMinerRequest {

    /** The {@link CgMinerCommand}. */
    private final CgMinerCommand command;

    /** The parameters. */
    private final List<String> parameters;

    /**
     * Constructor.
     *
     * @param command    The command.
     * @param parameters The parameters.
     */
    private CgMinerRequest(
            final CgMinerCommand command,
            final List<String> parameters) {
        Validate.notNull(
                command,
                "command cannot be null");
        if (command.isParameterRequired()) {
            final int expected = command.getNumParameters();
            Validate.isTrue(
                    expected == parameters.size(),
                    "too few parameters provided");
        }
        this.command = command;
        this.parameters = new ArrayList<>(parameters);
    }

    @Override
    public boolean equals(final Object other) {
        final boolean isEqual;
        if (other == null) {
            isEqual = false;
        } else if (getClass() != other.getClass()) {
            isEqual = false;
        } else {
            final CgMinerRequest request = (CgMinerRequest) other;
            isEqual =
                    new EqualsBuilder()
                            .append(this.command, request.command)
                            .append(this.parameters, request.parameters)
                            .isEquals();
        }
        return isEqual;
    }

    /**
     * Returns the {@link CgMinerCommand}.
     *
     * @return The {@link CgMinerCommand}.
     */
    public CgMinerCommand getCommand() {
        return this.command;
    }

    /**
     * Returns the parameters.
     *
     * @return The parameters.
     */
    public List<String> getParameters() {
        return this.parameters;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(this.command)
                .append(this.parameters)
                .hashCode();
    }

    @Override
    public String toString() {
        return String.format(
                "%s [ command=%s, parameters=%s ]",
                getClass().getSimpleName(),
                this.command,
                this.parameters);
    }

    /** A builder for creating {@link CgMinerRequest requests}. */
    public static class Builder
            extends AbstractBuilder<CgMinerRequest> {

        /** The command parameters. */
        private final List<String> parameters = new LinkedList<>();

        /** The {@link CgMinerCommand}. */
        private CgMinerCommand command;

        /**
         * Adds the provided parameter.
         *
         * @param parameter The parameter.
         *
         * @return The builder instance.
         */
        public Builder addParameter(final String parameter) {
            this.parameters.add(parameter);
            return this;
        }

        @Override
        public CgMinerRequest build() {
            return new CgMinerRequest(
                    this.command,
                    this.parameters);
        }

        /**
         * Sets the {@link CgMinerCommand}.
         *
         * @param command The command.
         *
         * @return The builder instance.
         */
        public Builder setCommand(final CgMinerCommand command) {
            this.command = command;
            return this;
        }
    }
}