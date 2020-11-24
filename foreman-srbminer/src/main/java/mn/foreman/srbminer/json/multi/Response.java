package mn.foreman.srbminer.json.multi;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.List;

/** The following model provides a model of a response from srbminer. */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Response {

    /** The algos. */
    @JsonProperty("algorithms")
    public List<Algorithm> algorithms;

    /** The CPU devices. */
    @JsonProperty("cpu_devices")
    public List<CpuDevice> cpuDevices;

    /** The GPU devices. */
    @JsonProperty("gpu_devices")
    public List<GpuDevice> gpuDevices;

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Algorithm {

        /** The hash rate. */
        @JsonProperty("hashrate")
        public Hashrate hashrate;

        /** The pool. */
        @JsonProperty("pool")
        public Pool pool;

        /** The shares. */
        @JsonProperty("shares")
        public Shares shares;

        /** The hash rate model object. */
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class Hashrate {

            /** The CPU. */
            @JsonProperty("cpu")
            public Cpu cpu;

            /** The GPU. */
            @JsonProperty("gpu")
            public Gpu gpu;

            /** The CPU model object. */
            @JsonIgnoreProperties(ignoreUnknown = true)
            public static class Cpu {

                /** The total CPU hash rate. */
                @JsonProperty("total")
                public BigDecimal total;
            }

            /** The GPU model object. */
            @JsonIgnoreProperties(ignoreUnknown = true)
            public static class Gpu {

                /** The total GPU hash rate. */
                @JsonProperty("total")
                public BigDecimal total;
            }
        }

        /** The pool model object. */
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class Pool {

            /** The pool. */
            @JsonProperty("pool")
            public String pool;
        }

        /** The shares model object. */
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class Shares {

            /** The accepted shares. */
            @JsonProperty("accepted")
            public int accepted;

            /** The rejected shares. */
            @JsonProperty("rejected")
            public int rejected;
        }
    }

    /** A model object of the JSON device object. */
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class CpuDevice {

        /** The device. */
        @JsonProperty("device")
        public String device;

        /** The bus id. */
        @JsonProperty("id")
        public int id;

        /** The model. */
        @JsonProperty("model")
        public String model;
    }

    /** A model object of the JSON device object. */
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class GpuDevice {

        /** The bus id. */
        @JsonProperty("bus_id")
        public int busId;

        /** The GPU compute error count. */
        @JsonProperty("compute_errors")
        public int computeErrors;

        /** The core clock. */
        @JsonProperty("core_clock")
        public int coreClock;

        /** The device id. */
        @JsonProperty("device_id")
        public int deviceId;

        /** The fan speed (RPMs). */
        @JsonProperty("fan_speed_rpm")
        public int fanSpeedRpm;

        /** The memory clock. */
        @JsonProperty("memory_clock")
        public int memoryClock;

        /** The model. */
        @JsonProperty("model")
        public String model;

        /** The temperature. */
        @JsonProperty("temperature")
        public int temperature;
    }
}