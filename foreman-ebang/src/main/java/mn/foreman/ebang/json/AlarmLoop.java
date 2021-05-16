package mn.foreman.ebang.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

/** The alarm loop. */
@JsonIgnoreProperties(ignoreUnknown = true)
public class AlarmLoop {

    /** The feedback. */
    @JsonProperty("feedback")
    public Feedback feedback;

    /** The feedback. */
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Feedback {

        /** The hash rate. */
        @JsonProperty("calValue")
        public BigDecimal hashRate;
    }
}
