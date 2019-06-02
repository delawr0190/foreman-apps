package mn.foreman.swarm.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.List;

/**
 * The following model provides a model of a response from swarm.
 *
 * <p>The expected format of that response is:</p>
 *
 * <pre>
 *   {
 *     "hsu": "khs",
 *     "fans": [
 *       "85",
 *       "80",
 *       "80",
 *       "80",
 *       "80",
 *       "85",
 *       "80",
 *       "80"
 *     ],
 *     "asic_total": null,
 *     "gpus": [
 *       "21751.1100",
 *       "21691.3850",
 *       "21872.1230",
 *       "14172.0000",
 *       "22028.8420",
 *       "21450.7080",
 *       "0.0008",
 *       "0.0008"
 *     ],
 *     "uptime": 384,
 *     "power": [
 *       "170",
 *       "173",
 *       "171",
 *       "75",
 *       "170",
 *       "169",
 *       "140",
 *       "154"
 *     ],
 *     "accepted": 275,
 *     "gpu_total": 122966.1697,
 *     "asics": null,
 *     "cpu_total": 137.82,
 *     "algo": "x16rt",
 *     "rejected": 1,
 *     "cpus": [
 *       "0.0490"
 *     ],
 *     "temps": [
 *       "70",
 *       "59",
 *       "62",
 *       "47",
 *       "65",
 *       "69",
 *       "58",
 *       "54"
 *     ]
 *   }
 * </pre>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Stats {

    /** The accepted shares. */
    @JsonProperty("accepted")
    public int accepted;

    /** The algo. */
    @JsonProperty("algo")
    public String algo;

    /** The fans. */
    @JsonProperty("fans")
    public List<Integer> fans;

    /** The GPU total hash rate. */
    @JsonProperty("gpu_total")
    public BigDecimal gpuTotal;

    /** The gpus. */
    @JsonProperty("gpus")
    public List<BigDecimal> gpus;

    /** The hsu. */
    @JsonProperty("hsu")
    public HashRateUnit hsu;

    /** The rejected shares. */
    @JsonProperty("rejected")
    public int rejected;

    /** The stratum. */
    @JsonProperty("stratum")
    public String stratum;

    /** The temps. */
    @JsonProperty("temps")
    public List<Integer> temps;

    /** The uptime. */
    @JsonProperty("uptime")
    public int uptime;
}