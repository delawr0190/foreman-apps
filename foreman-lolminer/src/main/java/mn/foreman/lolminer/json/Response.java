package mn.foreman.lolminer.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

/**
 * The following model provides a {@link Response} model object of a response
 * from lolminer.
 *
 * <pre>
 * {
 *   "Software": "lolMiner 0.41",
 *   "Startup": "2018-08-04 10:17:42 ",
 *   "Coin": "BitcoinZ (BTCZ)",
 *   "Algorithm": "Equihash 144.5",
 *   "ConnectedPool": "mine-btcz-euro.equipool.1ds.us:50061",
 *   "ConnectedUser": "t1dbQvbohSUAGE6dZpUYru7e18SCNVipVXY.lolMiner",
 *   "LastUpdate(5s)": "22:33:46 ",
 *   "LastUpdate(60s)": "22:32:55 ",
 *   "TotalSpeed(5s)": "24722.0225",
 *   "TotalSpeed(60s)": " ",
 *   "GPU0": {
 *     "Name": "AMD Radeon (TM) RX 480 Graphics",
 *     "Speed(5s)": "13468.8252",
 *     "Speed(60s)": "13429.5117"
 *   },
 *   "GPU1": {
 *     "Name": "Radeon RX 580 Series",
 *     "Speed(5s)": "11253.1973",
 *     "Speed(60s)": "11262.5537"
 *   }
 * }
 * </pre>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response {

    /** GPU 0. */
    @JsonProperty("GPU0")
    public Gpu gpu0;

    /** GPU 1. */
    @JsonProperty("GPU1")
    public Gpu gpu1;

    /** GPU 10. */
    @JsonProperty("GPU10")
    public Gpu gpu10;

    /** GPU 11. */
    @JsonProperty("GPU11")
    public Gpu gpu11;

    /** GPU 12. */
    @JsonProperty("GPU12")
    public Gpu gpu12;

    /** GPU 13. */
    @JsonProperty("GPU13")
    public Gpu gpu13;

    /** GPU 14. */
    @JsonProperty("GPU14")
    public Gpu gpu14;

    /** GPU 2. */
    @JsonProperty("GPU2")
    public Gpu gpu2;

    /** GPU 3. */
    @JsonProperty("GPU3")
    public Gpu gpu3;

    /** GPU 4. */
    @JsonProperty("GPU4")
    public Gpu gpu4;

    /** GPU 5. */
    @JsonProperty("GPU5")
    public Gpu gpu5;

    /** GPU 6. */
    @JsonProperty("GPU6")
    public Gpu gpu6;

    /** GPU 7. */
    @JsonProperty("GPU7")
    public Gpu gpu7;

    /** GPU 8. */
    @JsonProperty("GPU8")
    public Gpu gpu8;

    /** GPU 9. */
    @JsonProperty("GPU9")
    public Gpu gpu9;

    /** The pool. */
    @JsonProperty("ConnectedPool")
    public String pool;

    /** The software version. */
    @JsonProperty("Software")
    public String software;

    /** The hash rate. */
    @JsonProperty("TotalSpeed(5s)")
    public BigDecimal totalSpeed;

    /** A model representation of the GPU&lt;#&gt; JSON object. */
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Gpu {

        /** The GPU name. */
        @JsonProperty("Name")
        public String name;
    }
}
