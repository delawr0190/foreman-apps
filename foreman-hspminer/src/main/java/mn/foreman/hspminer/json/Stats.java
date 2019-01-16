package mn.foreman.hspminer.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.List;

/**
 * The following model provides a {@link Stats} model object of a response from
 * hspminer:
 *
 * <pre>
 *     GET /api/v1
 * </pre>
 *
 * <p>The expected format of that object is:</p>
 *
 * <pre>
 * {
 *   "report_time": 1547553371,
 *   "miner": [
 *     {
 *       "coin": "ae",
 *       "cur_time": 1547553371,
 *       "info": "",
 *       "reboot": 0,
 *       "start_time": 1547553068,
 *       "ae_worker": "Nvidia",
 *       "ae_pool": "ae.f2pool.com:7898",
 *       "aewallet": "ak",
 *       "ae_accept": 38,
 *       "ae_rejected": 0,
 *       "ae_giveup": 0,
 *       "ae_latency": 375,
 *       "ae_last_accept_time": 1547553340,
 *       "devices": [
 *         {
 *           "id": 0,
 *           "name": "GeForce GTX 1080 Ti",
 *           "pci": "1:0:0",
 *           "ae_hash": 250,
 *           "fan": 49,
 *           "core": 1695,
 *           "mem": 5508,
 *           "power": 126,
 *           "temp": 50,
 *           "submit": 6,
 *           "submit_time": 1547553327,
 *           "warning": ""
 *         },
 *         {
 *           "id": 1,
 *           "name": "GeForce GTX 1080 Ti",
 *           "pci": "2:0:0",
 *           "ae_hash": 248,
 *           "fan": 53,
 *           "core": 1695,
 *           "mem": 5508,
 *           "power": 153,
 *           "temp": 51,
 *           "submit": 6,
 *           "submit_time": 1547553330,
 *           "warning": ""
 *         },
 *         {
 *           "id": 2,
 *           "name": "GeForce GTX 1080 Ti",
 *           "pci": "3:0:0",
 *           "ae_hash": 248,
 *           "fan": 53,
 *           "core": 1695,
 *           "mem": 5508,
 *           "power": 120,
 *           "temp": 51,
 *           "submit": 5,
 *           "submit_time": 1547553267,
 *           "warning": ""
 *         },
 *         {
 *           "id": 3,
 *           "name": "GeForce GTX 1080 Ti",
 *           "pci": "5:0:0",
 *           "ae_hash": 249,
 *           "fan": 47,
 *           "core": 1695,
 *           "mem": 5508,
 *           "power": 125,
 *           "temp": 48,
 *           "submit": 6,
 *           "submit_time": 1547553320,
 *           "warning": ""
 *         },
 *         {
 *           "id": 4,
 *           "name": "GeForce GTX 1080 Ti",
 *           "pci": "6:0:0",
 *           "ae_hash": 251,
 *           "fan": 49,
 *           "core": 1695,
 *           "mem": 5508,
 *           "power": 125,
 *           "temp": 50,
 *           "submit": 9,
 *           "submit_time": 1547553340,
 *           "warning": ""
 *         },
 *         {
 *           "id": 5,
 *           "name": "GeForce GTX 1080 Ti",
 *           "pci": "7:0:0",
 *           "ae_hash": 247,
 *           "fan": 46,
 *           "core": 1695,
 *           "mem": 5508,
 *           "power": 121,
 *           "temp": 47,
 *           "submit": 6,
 *           "submit_time": 1547553323,
 *           "warning": ""
 *         }
 *       ]
 *     }
 *   ]
 * }
 * </pre>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Stats {

    /** The miners. */
    @JsonProperty("miner")
    public List<Miner> miners;

    /** A model object representation of a device. */
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Device {

        /** The GPU core clock. */
        @JsonProperty("core")
        public int core;

        /** The GPU fan speed percentage. */
        @JsonProperty("fan")
        public int fan;

        /** The hash rate. */
        @JsonProperty("ae_hash")
        public BigDecimal hashrate;

        /** The device id. */
        @JsonProperty("id")
        public int id;

        /** The GPU memory clock. */
        @JsonProperty("mem")
        public int mem;

        /** The GPU name. */
        @JsonProperty("name")
        public String name;

        /** The PCI information. */
        @JsonProperty("pci")
        public String pci;

        /** The GPU temp. */
        @JsonProperty("temp")
        public int temp;
    }

    /** A mock object representation of a miner. */
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Miner {

        /** The accepted shares. */
        @JsonProperty("ae_accept")
        public int accepted;

        /** The devices. */
        @JsonProperty("devices")
        public List<Device> devices;

        /** The pool. */
        @JsonProperty("ae_pool")
        public String pool;

        /** The rejected shares. */
        @JsonProperty("ae_rejected")
        public int rejected;

    }
}
