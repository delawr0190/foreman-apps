package mn.foreman.api.notifications;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;
import java.util.List;

/**
 * A {@link Notifications} provides a handler for interacting with the
 * <code>/api/notifications</code> Foreman API endpoint.
 */
public interface Notifications {

    /**
     * Obtains the Telegram notifications.
     *
     * @param sinceId        The previously observed id (-1 to disable).
     * @param sinceTimestamp The cutoff date (null to disable).
     *
     * @return The Telegram notifications.
     */
    List<Notification> telegram(
            int sinceId,
            Instant sinceTimestamp);

    /** A notification. */
    @JsonIgnoreProperties(ignoreUnknown = true)
    class Notification {

        /** The failing miners. */
        @JsonProperty("failingMiners")
        public List<FailingMiner> failingMiners;

        /** The notification ID. */
        @JsonProperty("id")
        public int id;

        /** When the notification was published. */
        @JsonProperty("published")
        public Instant published;

        /** The subject. */
        @JsonProperty("subject")
        public String subject;

        /** A miner that is currently failing. */
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class FailingMiner {

            /** The diagnosis. */
            @JsonProperty("diagnosis")
            public List<String> diagnosis;

            /** The miner's name. */
            @JsonProperty("miner")
            public String miner;

            /** The miner ID. */
            @JsonProperty("minerId")
            public int minerId;
        }
    }
}
