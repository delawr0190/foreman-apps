package mn.foreman.chisel.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * The following model provides a model of a response from chisel, the Foreman
 * metrics supplementing application.  A response from chisel is obtained from
 * performing a GET on the following URL:
 *
 * <pre>
 *     GET /stats
 * </pre>
 *
 * <p>The expected format of that response is:</p>
 *
 * <pre>
 * [
 *   {
 *     "id": 0,
 *     "busId": 1,
 *     "name": "GeForce GTX 1070 Ti",
 *     "temp": 67,
 *     "fan": 99,
 *     "clocks": {
 *       "core": 1234,
 *       "memory": 5678
 *     },
 *     "processes": [
 *       "ethminer",
 *       "something else"
 *     ]
 *   },
 *   {
 *     "id": 1,
 *     "busId": 3,
 *     "name": "GeForce GTX 1070 Ti",
 *     "temp": 69,
 *     "fan": 75,
 *     "clocks": {
 *       "core": 5678,
 *       "memory": 1234
 *     },
 *     "processes": [
 *       "ethminer",
 *       "something else"
 *     ]
 *   }
 * ]
 * </pre>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class GpuInfo {

    /** The bus ID. */
    @JsonProperty("busId")
    public int busId;

    /** The clocks. */
    @JsonProperty("clocks")
    public Clocks clocks;

    /** The fan speed (percentage). */
    @JsonProperty("fan")
    public int fan;

    /** The GPU ID. */
    @JsonProperty("id")
    public int id;

    /** The GPU name. */
    @JsonProperty("name")
    public String name;

    /** The processes. */
    @JsonProperty("processes")
    public List<String> processes;

    /** The temperature. */
    @JsonProperty("temp")
    public int temp;

    /**
     * A {@link Clocks} provides a model object representation of clock related
     * information for a single GPU.
     */
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