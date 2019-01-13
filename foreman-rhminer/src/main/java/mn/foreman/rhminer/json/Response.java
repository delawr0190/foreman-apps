package mn.foreman.rhminer.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.List;

/**
 * The following class represents a rhminer API response similar to the
 * following:
 *
 * <pre>
 * {
 *   "infos": [
 *     {
 *       "name": "GPU0",
 *       "threads": 50,
 *       "speed": 15,
 *       "accepted": 0,
 *       "rejected": 0,
 *       "temp": 0,
 *       "fan": 0
 *     }
 *   ],
 *   "speed": 15,
 *   "accepted": 0,
 *   "rejected": 0,
 *   "failed": 0,
 *   "uptime": 261,
 *   "extrapayload": "",
 *   "stratum.server": "pasc.f2pool.com:15555",
 *   "stratum.user": "529692-23.616E73656E6C6565.001",
 *   "diff": 8.0e-6
 * }
 * </pre>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response {

    /** The accepted share count. */
    @JsonProperty("accepted")
    public int accepted;

    /** The devices. */
    @JsonProperty("infos")
    public List<Info> infos;

    /** The pool URL. */
    @JsonProperty("stratum.server")
    public String pool;

    /** The rejected share count. */
    @JsonProperty("rejected")
    public int rejected;

    /** The uptime. */
    @JsonProperty("uptime")
    public int uptime;

    /** A model representation of an info. */
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Info {

        /** The fan percentage. */
        @JsonProperty("fan")
        public int fan;

        /** The hash rate. */
        @JsonProperty("speed")
        public BigDecimal hashRate;

        /** The name. */
        @JsonProperty("name")
        public String name;

        /** The temperature. */
        @JsonProperty("temp")
        public int temp;
    }
}