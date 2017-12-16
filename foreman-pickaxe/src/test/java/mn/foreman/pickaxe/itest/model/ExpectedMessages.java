package mn.foreman.pickaxe.itest.model;

import mn.foreman.model.AbstractBuilder;

import com.google.common.collect.ImmutableMap;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.util.*;

/**
 * {@link ExpectedMessages} provides an aggregator POJO that's capable of
 * mapping a response from cgminer to a canned response file.
 */
public class ExpectedMessages {

    /** All of the expected messages. */
    private final List<Map<String, String>> expectedMessages;

    /**
     * Constructor.
     *
     * @param expectedMessages All of the expected messages and their
     *                         responses.
     */
    ExpectedMessages(final List<Map<String, String>> expectedMessages) {
        this.expectedMessages = new ArrayList<>(expectedMessages);
    }

    /**
     * Returns the expected response for the provided request if one exists.
     *
     * @param request The request.
     *
     * @return The response, if one exists.
     */
    public Optional<String> getResponse(final String request) {
        return this.expectedMessages
                .stream()
                .map(map -> map.get(request))
                .filter(Objects::nonNull)
                .findFirst();
    }

    /** A builder for creating {@link ExpectedMessages}. */
    public static class Builder
            extends AbstractBuilder<ExpectedMessages> {

        /** All of the expected messages. */
        private final List<Map<String, String>> expectedMessages =
                new LinkedList<>();

        /**
         * Adds the expected  request and response in the provided files.
         *
         * @param requestPath  The request file.
         * @param responsePath The response file.
         *
         * @return This builder instance.
         */
        public Builder addMessageFromFile(
                final String requestPath,
                final String responsePath) {
            try {
                addMessage(
                        IOUtils.toString(
                                getClass().getResourceAsStream(
                                        requestPath),
                                "UTF-8"),
                        IOUtils.toString(
                                getClass().getResourceAsStream(
                                        responsePath),
                                "UTF-8"));
            } catch (final IOException ioe) {
                throw new IllegalArgumentException(ioe);
            }

            return this;
        }

        @Override
        public ExpectedMessages build() {
            return new ExpectedMessages(this.expectedMessages);
        }

        /**
         * Adds an expected message for the provided request and responses.
         *
         * @param request  The request.
         * @param response The response.
         */
        void addMessage(
                final String request,
                final String response) {
            this.expectedMessages.add(
                    ImmutableMap.of(
                            request,
                            response));
        }
    }
}