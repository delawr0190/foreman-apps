package mn.foreman.autominer.json;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.ZonedDateTime;
import java.util.List;

/**
 * The following model provides a model of a response from autominer to the
 * following URL:
 *
 * <pre>
 *   GET /summary
 * </pre>
 *
 * <p>The expected format of that response is:</p>
 *
 * <pre>
 * {
 *   "GPUMiner": "trex",
 *   "GPUAlgo": "x16rt",
 *   "GPUApiPort": 4067,
 *   "CPUMiner": "NONE",
 *   "CPUAlgo": "NONE",
 *   "CPUApiPort": 0,
 *   "RigGroupGPU": 17,
 *   "RigGroupCPU": 18,
 *   "GPUList": [
 *     {
 *       "Id": 0,
 *       "Utilization": 99,
 *       "Temperature": 70,
 *       "FanSpeed": " 47",
 *       "MemoryClock": 5005,
 *       "CoreClock": 1733
 *     },
 *     {
 *       "Id": 1,
 *       "Utilization": 99,
 *       "Temperature": 65,
 *       "FanSpeed": " 35",
 *       "MemoryClock": 5005,
 *       "CoreClock": 1784
 *     },
 *     {
 *       "Id": 2,
 *       "Utilization": 99,
 *       "Temperature": 63,
 *       "FanSpeed": " 33",
 *       "MemoryClock": 5005,
 *       "CoreClock": 1809
 *     },
 *     {
 *       "Id": 3,
 *       "Utilization": 99,
 *       "Temperature": 65,
 *       "FanSpeed": " 34",
 *       "MemoryClock": 5005,
 *       "CoreClock": 1771
 *     },
 *     {
 *       "Id": 4,
 *       "Utilization": 99,
 *       "Temperature": 67,
 *       "FanSpeed": " 38",
 *       "MemoryClock": 5005,
 *       "CoreClock": 1771
 *     },
 *     {
 *       "Id": 5,
 *       "Utilization": 99,
 *       "Temperature": 68,
 *       "FanSpeed": " 40",
 *       "MemoryClock": 5005,
 *       "CoreClock": 1733
 *     }
 *   ]
 * }
 * </pre>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Response {

    /** The GPU algorithm. */
    @JsonProperty("GPUAlgo")
    public String gpuAlgo;

    /** The GPU api port. */
    @JsonProperty("GPUApiPort")
    public int gpuApiPort;

    /** The GPU miner. */
    @JsonProperty("GPUMiner")
    public String gpuMiner;

    /** The GPU rig group. */
    @JsonProperty("RigGroupGPU")
    public int gpuRigGroup;

    /** The GPUs. */
    @JsonProperty("GPUList")
    public List<Gpu> gpus;

    /** The start time. */
    @JsonProperty("StartTime")
    public ZonedDateTime startTime;

    /** Provides a model representation of a GPU. */
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Gpu {

        /** The core clock. */
        @JsonProperty("CoreClock")
        public int coreClock;

        /** The fan speed. */
        @JsonProperty("FanSpeed")
        public int fanSpeed;

        /** The ID. */
        @JsonProperty("Id")
        public int id;

        /** The memory clock. */
        @JsonProperty("MemoryClock")
        public int memoryClock;

        /** The temperature. */
        @JsonProperty("Temperature")
        public int temperature;
    }
}