package mn.foreman.cgminer.request;

import mn.foreman.model.AbstractBuilder;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A {@link CgMinerRequest} represents a command to be sent to cgminer.
 *
 * <p>The command that will be generated will adhere to the JSON api.</p>
 */
@JsonSerialize(using = CgMinerRequestSerializer.class)
public class CgMinerRequest {

    /** The {@link CgMinerCommand}. */
    private final List<CgMinerCommand> commands;

    /**
     * Constructor.
     *
     * @param commands The commands.
     */
    private CgMinerRequest(final List<CgMinerCommand> commands) {
        Validate.notNull(
                commands,
                "commands cannot be null");
        this.commands = new ArrayList<>(commands);
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
                            .append(this.commands, request.commands)
                            .isEquals();
        }
        return isEqual;
    }

    /**
     * Returns the {@link CgMinerCommand}.
     *
     * @return The {@link CgMinerCommand}.
     */
    public List<CgMinerCommand> getCommands() {
        return Collections.unmodifiableList(this.commands);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(this.commands)
                .hashCode();
    }

    /**
     * Returns whether or not this was a multi-request.
     *
     * @return Whether or not this was a multi-request.
     */
    public boolean isMulti() {
        return this.commands.size() > 1;
    }

    /**
     * Returns the request as a single command.
     *
     * @return The command.
     */
    public String toCommand() {
        return this.commands
                .stream()
                .map(CgMinerCommand::getCommand)
                .collect(Collectors.joining("+"));
    }

    @Override
    public String toString() {
        return String.format(
                "%s [ commands=%s ]",
                getClass().getSimpleName(),
                this.commands);
    }

    /** A builder for creating {@link CgMinerRequest requests}. */
    public static class Builder
            extends AbstractBuilder<CgMinerRequest> {

        /** The {@link CgMinerCommand}. */
        private final List<CgMinerCommand> commands = new LinkedList<>();

        /**
         * Adds the {@link CgMinerCommand}.
         *
         * @param command The command.
         *
         * @return The builder instance.
         */
        public Builder addCommand(final CgMinerCommand command) {
            this.commands.add(command);
            return this;
        }

        @Override
        public CgMinerRequest build() {
            return new CgMinerRequest(this.commands);
        }

        /**
         * Sets the {@link CgMinerCommand}.
         *
         * @param command The command.
         *
         * @return The builder instance.
         */
        public Builder setCommand(final CgMinerCommand command) {
            this.commands.clear();
            this.commands.add(command);
            return this;
        }
    }
}