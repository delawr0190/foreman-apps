package mn.foreman.jceminer.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.List;

/**
 * The following model provides a {@link Response} model object of a response
 * from jceminer:
 *
 * <pre>
 *     GET /
 * </pre>
 *
 * <p>The expected format of that object is:</p>
 *
 * <pre>
 * {
 *   "hashrate": {
 *     "thread_0": 771.17,
 *     "thread_1": 801.35,
 *     "thread_2": 796.32,
 *     "thread_3": 742.88,
 *     "thread_4": 585.85,
 *     "thread_5": 585.85,
 *     "thread_6": 796.32,
 *     "thread_7": 796.32,
 *     "thread_8": 708.87,
 *     "thread_9": 801.69,
 *     "thread_all": [771.17, 801.35, 796.32, 742.88, 585.85, 585.85, 796.32, 796.32, 708.87, 801.69],
 *     "thread_gpu": [771.17, 801.35, 796.32, 742.88, 1171.70, 796.32, 796.32, 708.87, 801.69],
 *     "total": 7386.59,
 *     "max": 7390.37
 *   },
 *   "result": {
 *     "wallet": "WALLET.219000",
 *     "pool": "bittube.miner.rocks:5555",
 *     "ssl": false,
 *     "currency": "BitTube (TUBE)",
 *     "difficulty": 219008,
 *     "shares": 4161,
 *     "hashes": 911292288,
 *     "uptime": "34:56:08",
 *     "effective": 7245.82
 *   },
 *   "gpu_status": [
 *     {
 *       "index": 0,
 *       "temperature": 69,
 *       "fan": 78,
 *       "processor": "Ellesmere",
 *       "memory": 4096,
 *       "good_shares": 429,
 *       "bad_shares": 0
 *      },
 *     {
 *       "index": 1,
 *       "temperature": 69,
 *       "fan": 78,
 *       "processor": "Ellesmere",
 *       "memory": 4096,
 *       "good_shares": 464,
 *       "bad_shares": 0
 *     },
 *     {
 *       "index": 2,
 *       "temperature": 64,
 *       "fan": 74,
 *       "processor": "Ellesmere",
 *       "memory": 4096,
 *       "good_shares": 484,
 *       "bad_shares": 0
 *     },
 *     {
 *       "index": 3,
 *       "temperature": 64,
 *       "fan": 74,
 *       "processor": "Ellesmere",
 *       "memory": 4096,
 *       "good_shares": 428,
 *       "bad_shares": 0
 *     },
 *     {
 *       "index": 4,
 *       "temperature": 57,
 *       "fan": 66,
 *       "processor": "Ellesmere",
 *       "memory": 8192,
 *       "good_shares": 611,
 *       "bad_shares": 0
 *     },
 *     {
 *       "index": 5,
 *       "temperature": 75,
 *       "fan": 88,
 *       "processor": "Ellesmere",
 *       "memory": 4096,
 *       "good_shares": 469,
 *       "bad_shares": 0
 *     },
 *     {
 *       "index": 6,
 *       "temperature": 75,
 *       "fan": 88,
 *       "processor": "Ellesmere",
 *       "memory": 4096,
 *       "good_shares": 468,
 *       "bad_shares": 0
 *     },
 *     {
 *       "index": 7,
 *       "temperature": 75,
 *       "fan": 86,
 *       "processor": "Ellesmere",
 *       "memory": 4096,
 *       "good_shares": 391,
 *       "bad_shares": 0
 *     },
 *     {
 *       "index": 8,
 *       "temperature": 68,
 *       "fan": 78,
 *       "processor": "Ellesmere",
 *       "memory": 4096,
 *       "good_shares": 418,
 *       "bad_shares": 0
 *     },
 *   ],
 *   "miner": {
 *     "version": "jce/0.32e/gpu",
 *     "platform": "Intel(R) Pentium(R) CPU G4400 @ 3.30GHz",
 *     "system": "Windows 64-bits",
 *     "algorithm": "13"
 *   }
 * }
 * </pre>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Response {

    /** The GPUs. */
    @JsonProperty("gpu_status")
    public List<Gpu> gpus;

    /** The hash rate. */
    @JsonProperty("hashrate")
    public Hashrate hashrate;

    /** The miner. */
    @JsonProperty("miner")
    public Miner miner;

    /** The result. */
    @JsonProperty("result")
    public Result result;

    /** A model object representing a gpu JSON object. */
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Gpu {

        /** The bad shares. */
        @JsonProperty("bad_shares")
        public int badShares;

        /** The fan. */
        @JsonProperty("fan")
        public int fan;

        /** The good shares. */
        @JsonProperty("good_shares")
        public int goodShares;

        /** The index. */
        @JsonProperty("index")
        public int index;

        /** The temperature. */
        @JsonProperty("temperature")
        public int temperature;
    }

    /** A model object representing the hash rate JSON object. */
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Hashrate {

        /** The total hash rate. */
        @JsonProperty("total")
        public BigDecimal total;
    }

    /** A model object representing the miner JSON object. */
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Miner {

        /** The version. */
        @JsonProperty("version")
        public String version;
    }

    /** A model object representing the result JSON object. */
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Result {

        /** The pool. */
        @JsonProperty("pool")
        public String pool;

        /** The shares. */
        @JsonProperty("shares")
        public int shares;
    }
}