package mn.foreman.mkxminer.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.List;

/**
 * The following model provides a model of a response from mkxminer to the
 * following URL:
 *
 * <pre>
 *   GET /stats
 * </pre>
 *
 * <p>The expected format of that response is:</p>
 *
 * <pre>
 * {
 *   "main": {
 *     "minerVersion": "4.2.0",
 *     "apiVersion": 1,
 *     "uptime": 20
 *   },
 *   "pool": {
 *     "address": "stratum+tcp://lyra2z.eu.nicehash.com:3365",
 *     "username": "1N2vf6Jm9jmScDUfwPeiicHNsXRSmvnjpL",
 *     "algorithm": "lyra2rev2"
 *   },
 *   "gpus": {
 *     "quantity": 2,
 *     "hashrate": 0.24,
 *     "alive": 2,
 *     "sick": 0,
 *     "dead": 0
 *   },
 *   "gpu": [
 *     {
 *       "name": "Radeon RX 580 Series",
 *       "status": 0,
 *       "hashrate": 0.11,
 *       "hwErrors": 0,
 *       "temperature": 55,
 *       "fan": 37,
 *       "gpuClock": 1360,
 *       "memClock": 2000,
 *       "activity": 100,
 *       "powertune": -30
 *     },
 *     {
 *       "name": "Radeon (TM) RX 480 Graphics",
 *       "status": 0,
 *       "hashrate": 0.13,
 *       "hwErrors": 0,
 *       "temperature": 55,
 *       "fan": 28,
 *       "gpuClock": 1303,
 *       "memClock": 1750,
 *       "activity": 100,
 *       "powertune": -30
 *     }
 *   ]
 * }
 * </pre>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Response {

    /** The aggregated GPU stats. */
    @JsonProperty("gpus")
    public GpuTotals gpuTotals;

    /** The GPUs. */
    @JsonProperty("gpu")
    public List<Gpu> gpus;

    /** The pool statistics. */
    @JsonProperty("pool")
    public Pool pool;

    /** The following class provides a POJO representation of a gpu. */
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Gpu {

        /** The fan speed. */
        @JsonProperty("fan")
        public int fan;

        /** The core clock. */
        @JsonProperty("gpuClock")
        public int gpuClock;

        /** The memory clock. */
        @JsonProperty("memClock")
        public int memClock;

        /** The name. */
        @JsonProperty("name")
        public String name;

        /** The temperature. */
        @JsonProperty("temperature")
        public int temperature;
    }

    /** The following class provides a POJO representation of gpus. */
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class GpuTotals {

        /** The hash rate. */
        @JsonProperty("hashrate")
        public BigDecimal hashrate;
    }

    /** The following class provides a POJO representation of a pool. */
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Pool {

        /** The stratum URL. */
        @JsonProperty("address")
        public String address;
    }
}
