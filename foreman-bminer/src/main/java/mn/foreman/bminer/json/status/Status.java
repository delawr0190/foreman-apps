package mn.foreman.bminer.json.status;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

/**
 * The following model provides a {@link Status} sub-model object of a response
 * from bminer:
 *
 * <pre>
 *     GET /api/status
 * </pre>
 *
 * <p>The expected format of that sub-object is:</p>
 *
 * <pre>
 * {
 *   "algorithm": "aeternity",
 *   "stratum": {
 *     "accepted_shares": 31,
 *     "rejected_shares": 0,
 *     "accepted_share_rate": 0.09,
 *     "rejected_share_rate": 0
 *   },
 *   "miners": {
 *     "0": {
 *       "solver": {
 *         "solution_rate": 4.98
 *       },
 *       "device": {
 *         "temperature": 46,
 *         "power": 162,
 *         "fan_speed": 56,
 *         "global_memory_used": 6361,
 *         "utilization": {
 *           "gpu": 83,
 *           "memory": 40
 *         },
 *         "clocks": {
 *           "core": 1632,
 *           "memory": 5508
 *         },
 *         "pci": {
 *           "bar1_used": 229,
 *           "rx_throughput": 627,
 *           "tx_throughput": 91
 *         }
 *       }
 *     },
 *     "1": {
 *       "solver": {
 *         "solution_rate": 4.9
 *       },
 *       "device": {
 *         "temperature": 48,
 *         "power": 162,
 *         "fan_speed": 58,
 *         "global_memory_used": 6073,
 *         "utilization": {
 *           "gpu": 69,
 *           "memory": 43
 *         },
 *         "clocks": {
 *           "core": 1632,
 *           "memory": 5508
 *         },
 *         "pci": {
 *           "bar1_used": 229,
 *           "rx_throughput": 609,
 *           "tx_throughput": 63
 *         }
 *       }
 *     },
 *     "2": {
 *       "solver": {
 *         "solution_rate": 4.7
 *       },
 *       "device": {
 *         "temperature": 47,
 *         "power": 71,
 *         "fan_speed": 58,
 *         "global_memory_used": 6338,
 *         "utilization": {
 *           "gpu": 89,
 *           "memory": 53
 *         },
 *         "clocks": {
 *           "core": 1632,
 *           "memory": 5508
 *         },
 *         "pci": {
 *           "bar1_used": 229,
 *           "rx_throughput": 475,
 *           "tx_throughput": 57
 *         }
 *       }
 *     },
 *     "3": {
 *       "solver": {
 *         "solution_rate": 4.91
 *       },
 *       "device": {
 *         "temperature": 45,
 *         "power": 158,
 *         "fan_speed": 55,
 *         "global_memory_used": 5952,
 *         "utilization": {
 *           "gpu": 93,
 *           "memory": 53
 *         },
 *         "clocks": {
 *           "core": 1632,
 *           "memory": 5508
 *         },
 *         "pci": {
 *           "bar1_used": 229,
 *           "rx_throughput": 446,
 *           "tx_throughput": 1807
 *         }
 *       }
 *     },
 *     "4": {
 *       "solver": {
 *         "solution_rate": 4.81
 *       },
 *       "device": {
 *         "temperature": 45,
 *         "power": 70,
 *         "fan_speed": 55,
 *         "global_memory_used": 5952,
 *         "utilization": {
 *           "gpu": 97,
 *           "memory": 55
 *         },
 *         "clocks": {
 *           "core": 1632,
 *           "memory": 5508
 *         },
 *         "pci": {
 *           "bar1_used": 229,
 *           "rx_throughput": 512,
 *           "tx_throughput": 43
 *         }
 *       }
 *     },
 *     "5": {
 *       "solver": {
 *         "solution_rate": 4.78
 *       },
 *       "device": {
 *         "temperature": 43,
 *         "power": 159,
 *         "fan_speed": 53,
 *         "global_memory_used": 6195,
 *         "utilization": {
 *           "gpu": 80,
 *           "memory": 45
 *         },
 *         "clocks": {
 *           "core": 1632,
 *           "memory": 5508
 *         },
 *         "pci": {
 *           "bar1_used": 229,
 *           "rx_throughput": 800,
 *           "tx_throughput": 56
 *         }
 *       }
 *     }
 *   },
 *   "version": "v12.1.0-5083c32",
 *   "start_time": 1547723050
 * }
 * </pre>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Status {

    /** The miners. */
    @JsonProperty("miners")
    public Map<String, Miner> miners;

    /** A model object representation of a device. */
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Device {

        /** The clocks. */
        @JsonProperty("clocks")
        public Clocks clocks;

        /** The fan speed. */
        @JsonProperty("fan_speed")
        public int fanSpeed;

        /** The temperature. */
        @JsonProperty("temperature")
        public int temperature;

        /** A model-object representation of the clocks. */
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class Clocks {

            /** The core clock. */
            @JsonProperty("core")
            public int core;

            /** The memory clock. */
            @JsonProperty("memory")
            public int memory;
        }
    }

    /** A model object representation of a miner. */
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Miner {

        /** The device. */
        @JsonProperty("device")
        public Device device;
    }
}
