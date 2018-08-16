package mn.foreman.dragonmint.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.List;

/**
 * The following model provides a {@link Summary} model object of a response
 * from dragonmint:
 *
 * <pre>
 *     GET /api/summary
 * </pre>
 *
 * <p>The expected format of that object is:</p>
 *
 * <pre>
 * {
 *   "success": true,
 *   "DEVS": [
 *     {
 *       "ASC": 0,
 *       "Name": "DT1",
 *       "ID": 0,
 *       "Enabled": "Y",
 *       "Status": "Alive",
 *       "Temperature": 72,
 *       "MHS av": 4904247.73,
 *       "MHS 5s": 4745502.91,
 *       "MHS 1m": 4830953.1,
 *       "MHS 5m": 4923462.76,
 *       "MHS 15m": 4932258.41,
 *       "Accepted": 307,
 *       "Rejected": 2,
 *       "Hardware Errors": 6600,
 *       "Utility": 3.84,
 *       "Last Share Pool": 0,
 *       "Last Share Time": 1529186657,
 *       "Total MH": 23499497701,
 *       "Diff1 Work": 5471148,
 *       "Difficulty Accepted": 4733596,
 *       "Difficulty Rejected": 31507,
 *       "Last Share Difficulty": 16982,
 *       "Last Valid Work": 1529186665,
 *       "Device Hardware%": 0.1205,
 *       "Device Rejected%": 0.5759,
 *       "Device Elapsed": 4792,
 *       "Hash Rate": 4932258.41
 *     },
 *     {
 *       "ASC": 1,
 *       "Name": "DT1",
 *       "ID": 1,
 *       "Enabled": "Y",
 *       "Status": "Alive",
 *       "Temperature": 67,
 *       "MHS av": 4894650.23,
 *       "MHS 5s": 4883086.81,
 *       "MHS 1m": 4784555.03,
 *       "MHS 5m": 4916279.62,
 *       "MHS 15m": 4924235.45,
 *       "Accepted": 378,
 *       "Rejected": 1,
 *       "Hardware Errors": 2380,
 *       "Utility": 4.73,
 *       "Last Share Pool": 0,
 *       "Last Share Time": 1529186639,
 *       "Total MH": 23453511280,
 *       "Diff1 Work": 5460570,
 *       "Difficulty Accepted": 5831923,
 *       "Difficulty Rejected": 19325,
 *       "Last Share Difficulty": 16982,
 *       "Last Valid Work": 1529186665,
 *       "Device Hardware%": 0.0436,
 *       "Device Rejected%": 0.3539,
 *       "Device Elapsed": 4792,
 *       "Hash Rate": 4924235.45
 *     },
 *     {
 *       "ASC": 2,
 *       "Name": "DT1",
 *       "ID": 2,
 *       "Enabled": "Y",
 *       "Status": "Alive",
 *       "Temperature": 67,
 *       "MHS av": 4892684.36,
 *       "MHS 5s": 4446965.58,
 *       "MHS 1m": 4976618.15,
 *       "MHS 5m": 4976675.71,
 *       "MHS 15m": 4931665.2,
 *       "Accepted": 345,
 *       "Rejected": 3,
 *       "Hardware Errors": 7976,
 *       "Utility": 4.32,
 *       "Last Share Pool": 0,
 *       "Last Share Time": 1529186642,
 *       "Total MH": 23444092522,
 *       "Diff1 Work": 5458635,
 *       "Difficulty Accepted": 5405403,
 *       "Difficulty Rejected": 32220,
 *       "Last Share Difficulty": 16982,
 *       "Last Valid Work": 1529186665,
 *       "Device Hardware%": 0.1459,
 *       "Device Rejected%": 0.5903,
 *       "Device Elapsed": 4792,
 *       "Hash Rate": 4931665.2
 *     }
 *   ],
 *   "POOLS": [
 *     {
 *       "POOL": 1,
 *       "URL": "stratum+tcp://pool.ckpool.org:3333",
 *       "Status": "Alive",
 *       "Priority": 1,
 *       "Quota": 1,
 *       "Long Poll": "N",
 *       "Getworks": 0,
 *       "Accepted": 0,
 *       "Rejected": 0,
 *       "Works": 0,
 *       "Discarded": 0,
 *       "Stale": 0,
 *       "Get Failures": 0,
 *       "Remote Failures": 0,
 *       "User": "3GWdXx9dfLPvSe7d8UnxjnDnSAJodTTbrt.dragon-0ade5",
 *       "Last Share Time": 0,
 *       "Diff1 Shares": 0,
 *       "Proxy Type": "",
 *       "Proxy": "",
 *       "Difficulty Accepted": 0,
 *       "Difficulty Rejected": 0,
 *       "Difficulty Stale": 0,
 *       "Last Share Difficulty": 0,
 *       "Work Difficulty": 0,
 *       "Has Stratum": true,
 *       "Stratum Active": false,
 *       "Stratum URL": "",
 *       "Stratum Difficulty": 0,
 *       "Has Vmask": true,
 *       "Has GBT": false,
 *       "Best Share": 0,
 *       "Pool Rejected%": 0,
 *       "Pool Stale%": 0,
 *       "Bad Work": 0,
 *       "Current Block Height": 0,
 *       "Current Block Version": 536870912
 *     }
 *   ],
 *   "HARDWARE": {
 *     "Fan duty": 59
 *   },
 *   "tuning": false,
 *   "hashrates": []
 * }
 * </pre>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Summary {

    /** The devices. */
    @JsonProperty("DEVS")
    public List<Dev> devs;

    /** The hardware. */
    @JsonProperty("HARDWARE")
    public Hardware hardware;

    /** The pools. */
    @JsonProperty("POOLS")
    public List<Pool> pools;

    /** A model object representation of a DEV JSON object. */
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Dev {

        /** Whether or not the device is enabled. */
        @JsonProperty("Enabled")
        public String enabled;

        /** The hash rate. */
        @JsonProperty("MHS 5s")
        public BigDecimal hashRate;

        /** The status. */
        @JsonProperty("Status")
        public String status;

        /** The temperature. */
        @JsonProperty("Temperature")
        public int temperature;
    }

    /** A model object representation of a Hardware JSON object. */
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Hardware {

        /** The fan speed. */
        @JsonProperty("Fan duty")
        public int fanSpeed;
    }

    /** A model object representation of a Pool JSON object. */
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Pool {

        /** The accepted count. */
        @JsonProperty("Accepted")
        public int accepted;

        /** The pool priority. */
        @JsonProperty("Priority")
        public int priority;

        /** The rejected count. */
        @JsonProperty("Rejected")
        public int rejected;

        /** The stale count. */
        @JsonProperty("Stale")
        public int stale;

        /** The status. */
        @JsonProperty("Status")
        public String status;

        /** The url. */
        @JsonProperty("URL")
        public String url;
    }
}