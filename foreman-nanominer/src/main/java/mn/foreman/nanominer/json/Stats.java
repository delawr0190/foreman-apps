package mn.foreman.nanominer.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * The following model provides a {@link Stats} model object of a response from
 * t-rex:
 *
 * <pre>
 *     GET /summary
 * </pre>
 *
 * <p>The expected format of that object is:</p>
 *
 * <pre>
 * {
 *   "Algorithms": [
 *     {
 *       "Ethash": {
 *         "CurrentPool": "eth-us-east1.nanopool.org:9999",
 *         "GPU 0": {
 *           "Accepted": 3,
 *           "Denied": 0,
 *           "Hashrate": "4.591157e+06"
 *         },
 *         "ReconnectionCount": 0,
 *         "Total": {
 *           "Accepted": 3,
 *           "Denied": 0,
 *           "Hashrate": "4.591157e+06"
 *         }
 *       }
 *     }
 *   ],
 *   "Devices": [
 *     {
 *       "GPU 0": {
 *         "Name": "GeForce GTX 1050 Ti",
 *         "Platform": "CUDA",
 *         "Pci": "01:00.0",
 *         "Temperature": 46
 *       }
 *     }
 *   ],
 *   "WorkTime": 898
 * }
 * </pre>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Stats {

    /** The algorithms. */
    @JsonProperty("Algorithms")
    public List<Map<String, AlgoStat>> algorithms;

    /** The devices. */
    @JsonProperty("Devices")
    public List<Map<String, Device>> devices;

    /** The per-algo statistics. */
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class AlgoStat {

        /** The current pool. */
        @JsonProperty("CurrentPool")
        public String pool;

        /** The aggregated stats. */
        @JsonProperty("Total")
        public AlgoStatTotal total;
    }

    /** The total stats for an algorithm. */
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class AlgoStatTotal {

        /** The accepted shares. */
        @JsonProperty("Accepted")
        public int accepted;

        /** The hashrate. */
        @JsonProperty("Hashrate")
        public BigDecimal hashrate;

        /** The rejected shares. */
        @JsonProperty("Denied")
        public int rejected;
    }

    /** A single device. */
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Device {

        /** The GPU name. */
        @JsonProperty("Name")
        public String name;

        /** The PCI information. */
        @JsonProperty("Pci")
        public String pci;

        /** The temperature. */
        @JsonProperty("Temperature")
        public int temp;

    }
}