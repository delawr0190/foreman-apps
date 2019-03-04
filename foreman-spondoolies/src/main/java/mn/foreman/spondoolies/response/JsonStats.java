package mn.foreman.spondoolies.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * The following class provides a model object representation of a json-stats
 * object that matches the following:
 *
 * <pre>
 * {
 *   "top-board": {
 *     "power": 2176,
 *     "psu-temp": 47,
 *     "psu-name": "Artesyn2000",
 *     "i2c-psu-error%": 0.28,
 *     "reported-hashrate": 270005,
 *     "avg-hashrate": 254783,
 *     "hardware-error%": 2.02,
 *     "min-temp": 47,
 *     "max-temp": 97,
 *     "rear-temp": 66,
 *     "front-temp": 44,
 *     "loops-enabled": [
 *       true,
 *       true,
 *       true,
 *       true,
 *       true
 *     ],
 *     "working-asics": 75,
 *     "enabled-engines": 441
 *   },
 *   "bottom-board": {
 *     "power": 2144,
 *     "psu-temp": 47,
 *     "psu-name": "Artesyn2000",
 *     "i2c-psu-error%": 0.3,
 *     "reported-hashrate": 269410,
 *     "avg-hashrate": 257932,
 *     "hardware-error%": 0.72,
 *     "min-temp": 47,
 *     "max-temp": 93,
 *     "rear-temp": 65,
 *     "front-temp": 43,
 *     "loops-enabled": [
 *       true,
 *       true,
 *       true,
 *       true,
 *       true
 *     ],
 *     "working-asics": 75,
 *     "enabled-engines": 442
 *   },
 *   "fan-speeds": [
 *     12028,
 *     11094,
 *     11956,
 *     11094
 *   ],
 *   "fan-percent": 100
 * }
 * </pre>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
class JsonStats {

    /** The bottom board. */
    @JsonProperty("bottom-board")
    Board bottomBoard;

    /** The fan speeds. */
    @JsonProperty("fan-speeds")
    List<Integer> fanSpeeds;

    /** The top board. */
    @JsonProperty("top-board")
    Board topBoard;

    /** A board model object representation. */
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Board {

        /** The front board temp. */
        @JsonProperty("front-temp")
        int frontTemp;

        /** The psu temp. */
        @JsonProperty("psu-temp")
        int psuTemp;

        /** The rear board temp. */
        @JsonProperty("rear-temp")
        int rearTemp;

        /** The number of working asics. */
        @JsonProperty("working-asics")
        int workingAsics;
    }
}
