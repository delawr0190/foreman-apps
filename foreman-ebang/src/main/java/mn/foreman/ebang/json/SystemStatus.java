package mn.foreman.ebang.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/** The system status object. */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SystemStatus {

    /** The feedback. */
    @JsonProperty("feedback")
    public Feedback feedback;

    /** The feedback object. */
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Feedback {

        /** The device 1 temp. */
        @JsonProperty("device1temp")
        public String device1Temp;

        /** The device 2 temp. */
        @JsonProperty("device2Temp")
        public String device2Temp;

        /** The device 3 temp. */
        @JsonProperty("device3Temp")
        public int device3Temp;

        /** The device 4 temp. */
        @JsonProperty("device4temp")
        public int device4Temp;

        /** The device fan. */
        @JsonProperty("devicefan")
        public int deviceFan;

        /** The device 2 fan. */
        @JsonProperty("devicefan2")
        public int deviceFan2;

        /** The product name. */
        @JsonProperty("productname")
        public String productName;
    }
}
