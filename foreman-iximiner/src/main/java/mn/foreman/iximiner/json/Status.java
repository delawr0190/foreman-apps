package mn.foreman.iximiner.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.List;

/**
 * The following model provides a {@link Status} model object of a json from
 * iximiner:
 *
 * <pre>
 *     GET /status
 * </pre>
 *
 * <p>The expected format of that object is either:</p>
 *
 * <pre>
 * [
 *   {
 *     "name": "myname",
 *     "block_height": 526337,
 *     "time_running": 37,
 *     "total_blocks": 1,
 *     "shares": 26,
 *     "rejects": 0,
 *     "earned": 0,
 *     "hashers": [
 *       {
 *         "type": "GPU",
 *         "subtype": "CUDA",
 *         "devices": [
 *           {
 *             "id": 0,
 *             "bus_id": "08:00.0",
 *             "name": "GeForce GTX 970 (4GB)",
 *             "intensity": 68,
 *             "hashrate": 69560.2
 *           }
 *         ]
 *       }
 *     ]
 *   }
 * ]
 * </pre>
 *
 * OR:
 *
 * <pre>
 * [
 *   {
 *     "name": "myname",
 *     "block_height": 526337,
 *     "time_running": 73,
 *     "total_blocks": 1,
 *     "shares": 14,
 *     "rejects": 0,
 *     "earned": 0,
 *     "hashers": [
 *       {
 *         "type": "CPU",
 *         "subtype": "CPU",
 *         "devices": [
 *           {
 *             "id": 0,
 *             "bus_id": "",
 *             "name": "AMD Ryzen 5 2600 Six-Core Processor            ",
 *             "intensity": 80,
 *             "hashrate": 17440.1
 *           }
 *         ]
 *       }
 *     ]
 *   }
 * ]
 * </pre>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Status {

    /** The hashers. */
    @JsonProperty("hashers")
    public List<Hasher> hashers;

    /** The rejects shares. */
    @JsonProperty("rejects")
    public int rejects;

    /** The accepted shares. */
    @JsonProperty("shares")
    public int shares;

    /** A device JSON object. */
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Device {

        /** The bus ID. */
        @JsonProperty("bus_id")
        public String busId;

        /** The hash rate. */
        @JsonProperty("hashrate")
        public BigDecimal hashRate;

        /** The id. */
        @JsonProperty("id")
        public int id;

        /** The name. */
        @JsonProperty("name")
        public String name;
    }

    /** A hasher JSON object. */
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Hasher {

        /** The devices. */
        @JsonProperty("devices")
        public List<Device> devices;

        /** The type. */
        @JsonProperty("type")
        public String type;
    }
}
