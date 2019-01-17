package mn.foreman.bminer;

import mn.foreman.model.miners.FanInfo;
import mn.foreman.model.miners.MinerStats;
import mn.foreman.model.miners.Pool;
import mn.foreman.model.miners.rig.FreqInfo;
import mn.foreman.model.miners.rig.Gpu;
import mn.foreman.model.miners.rig.Rig;
import mn.foreman.util.AbstractApiITest;
import mn.foreman.util.http.FakeHttpMinerServer;
import mn.foreman.util.http.HttpHandler;

import com.google.common.collect.ImmutableMap;

import java.math.BigDecimal;

/** Runs an integration tests using {@link Bminer} against a fake API. */
public class BminerCuckooITest
        extends AbstractApiITest {

    /** Constructor. */
    public BminerCuckooITest() {
        super(
                new Bminer(
                        "127.0.0.1",
                        8080),
                new FakeHttpMinerServer(
                        8080,
                        ImmutableMap.of(
                                "/api/v1/status/solver",
                                new HttpHandler(
                                        "",
                                        "{\n" +
                                                "  \"devices\": {\n" +
                                                "    \"0\": {\n" +
                                                "      \"solvers\": [\n" +
                                                "        {\n" +
                                                "          \"algorithm\": \"aeternity\",\n" +
                                                "          \"speed_info\": {\n" +
                                                "            \"hash_rate\": 2.44\n" +
                                                "          }\n" +
                                                "        }\n" +
                                                "      ]\n" +
                                                "    },\n" +
                                                "    \"1\": {\n" +
                                                "      \"solvers\": [\n" +
                                                "        {\n" +
                                                "          \"algorithm\": \"aeternity\",\n" +
                                                "          \"speed_info\": {\n" +
                                                "            \"hash_rate\": 2.44\n" +
                                                "          }\n" +
                                                "        }\n" +
                                                "      ]\n" +
                                                "    },\n" +
                                                "    \"2\": {\n" +
                                                "      \"solvers\": [\n" +
                                                "        {\n" +
                                                "          \"algorithm\": \"aeternity\",\n" +
                                                "          \"speed_info\": {\n" +
                                                "            \"hash_rate\": 2.44\n" +
                                                "          }\n" +
                                                "        }\n" +
                                                "      ]\n" +
                                                "    },\n" +
                                                "    \"3\": {\n" +
                                                "      \"solvers\": [\n" +
                                                "        {\n" +
                                                "          \"algorithm\": \"aeternity\",\n" +
                                                "          \"speed_info\": {\n" +
                                                "            \"hash_rate\": 2.46\n" +
                                                "          }\n" +
                                                "        }\n" +
                                                "      ]\n" +
                                                "    },\n" +
                                                "    \"4\": {\n" +
                                                "      \"solvers\": [\n" +
                                                "        {\n" +
                                                "          \"algorithm\": \"aeternity\",\n" +
                                                "          \"speed_info\": {\n" +
                                                "            \"hash_rate\": 2.44\n" +
                                                "          }\n" +
                                                "        }\n" +
                                                "      ]\n" +
                                                "    },\n" +
                                                "    \"5\": {\n" +
                                                "      \"solvers\": [\n" +
                                                "        {\n" +
                                                "          \"algorithm\": \"aeternity\",\n" +
                                                "          \"speed_info\": {\n" +
                                                "            \"hash_rate\": 2.51\n" +
                                                "          }\n" +
                                                "        }\n" +
                                                "      ]\n" +
                                                "    }\n" +
                                                "  }\n" +
                                                "}"),
                                "/api/v1/status/device",
                                new HttpHandler(
                                        "",
                                        "{\n" +
                                                "  \"devices\": {\n" +
                                                "    \"0\": {\n" +
                                                "      \"temperature\": 51,\n" +
                                                "      \"power\": 168,\n" +
                                                "      \"fan_speed\": 55,\n" +
                                                "      \"global_memory_used\": 6094,\n" +
                                                "      \"utilization\": {\n" +
                                                "        \"gpu\": 100,\n" +
                                                "        \"memory\": 63\n" +
                                                "      },\n" +
                                                "      \"clocks\": {\n" +
                                                "        \"core\": 1695,\n" +
                                                "        \"memory\": 5508\n" +
                                                "      },\n" +
                                                "      \"pci\": {\n" +
                                                "        \"bar1_used\": 229,\n" +
                                                "        \"rx_throughput\": 470,\n" +
                                                "        \"tx_throughput\": 58\n" +
                                                "      }" +
                                                "    },\n" +
                                                "    \"1\": {\n" +
                                                "      \"temperature\": 52,\n" +
                                                "      \"power\": 154,\n" +
                                                "      \"fan_speed\": 58,\n" +
                                                "      \"global_memory_used\": 5950,\n" +
                                                "      \"utilization\": {\n" +
                                                "        \"gpu\": 99,\n" +
                                                "        \"memory\": 41\n" +
                                                "      },\n" +
                                                "      \"clocks\": {\n" +
                                                "        \"core\": 1695,\n" +
                                                "        \"memory\": 5508\n" +
                                                "      },\n" +
                                                "      \"pci\": {\n" +
                                                "        \"bar1_used\": 229,\n" +
                                                "        \"rx_throughput\": 1028,\n" +
                                                "        \"tx_throughput\": 84\n" +
                                                "      }" +
                                                "    },\n" +
                                                "    \"2\": {\n" +
                                                "      \"temperature\": 52,\n" +
                                                "      \"power\": 149,\n" +
                                                "      \"fan_speed\": 59,\n" +
                                                "      \"global_memory_used\": 5950,\n" +
                                                "      \"utilization\": {\n" +
                                                "        \"gpu\": 97,\n" +
                                                "        \"memory\": 42\n" +
                                                "      },\n" +
                                                "      \"clocks\": {\n" +
                                                "        \"core\": 1695,\n" +
                                                "        \"memory\": 5508\n" +
                                                "      },\n" +
                                                "      \"pci\": {\n" +
                                                "        \"bar1_used\": 229,\n" +
                                                "        \"rx_throughput\": 741,\n" +
                                                "        \"tx_throughput\": 59\n" +
                                                "      }" +
                                                "    },\n" +
                                                "    \"3\": {\n" +
                                                "      \"temperature\": 50,\n" +
                                                "      \"power\": 149,\n" +
                                                "      \"fan_speed\": 50,\n" +
                                                "      \"global_memory_used\": 5950,\n" +
                                                "      \"utilization\": {\n" +
                                                "        \"gpu\": 98,\n" +
                                                "        \"memory\": 61\n" +
                                                "      },\n" +
                                                "      \"clocks\": {\n" +
                                                "        \"core\": 1695,\n" +
                                                "        \"memory\": 5508\n" +
                                                "      },\n" +
                                                "      \"pci\": {\n" +
                                                "        \"bar1_used\": 229,\n" +
                                                "        \"rx_throughput\": 522,\n" +
                                                "        \"tx_throughput\": 78\n" +
                                                "      }" +
                                                "    },\n" +
                                                "    \"4\": {\n" +
                                                "      \"temperature\": 51,\n" +
                                                "      \"power\": 127,\n" +
                                                "      \"fan_speed\": 53,\n" +
                                                "      \"global_memory_used\": 5950,\n" +
                                                "      \"utilization\": {\n" +
                                                "        \"gpu\": 99,\n" +
                                                "        \"memory\": 51\n" +
                                                "      },\n" +
                                                "      \"clocks\": {\n" +
                                                "        \"core\": 1695,\n" +
                                                "        \"memory\": 5508\n" +
                                                "      },\n" +
                                                "      \"pci\": {\n" +
                                                "        \"bar1_used\": 229,\n" +
                                                "        \"rx_throughput\": 1142,\n" +
                                                "        \"tx_throughput\": 52\n" +
                                                "      }" +
                                                "    },\n" +
                                                "    \"5\": {\n" +
                                                "      \"temperature\": 49,\n" +
                                                "      \"power\": 143,\n" +
                                                "      \"fan_speed\": 48,\n" +
                                                "      \"global_memory_used\": 5950,\n" +
                                                "      \"utilization\": {\n" +
                                                "        \"gpu\": 97,\n" +
                                                "        \"memory\": 52\n" +
                                                "      },\n" +
                                                "      \"clocks\": {\n" +
                                                "        \"core\": 1695,\n" +
                                                "        \"memory\": 5508\n" +
                                                "      },\n" +
                                                "      \"pci\": {\n" +
                                                "        \"bar1_used\": 229,\n" +
                                                "        \"rx_throughput\": 562,\n" +
                                                "        \"tx_throughput\": 37\n" +
                                                "      }" +
                                                "    }\n" +
                                                "  }\n" +
                                                "}"),
                                "/api/v1/status/stratum",
                                new HttpHandler(
                                        "",
                                        "{\n" +
                                                "  \"stratums\": {\n" +
                                                "    \"aeternity\": {\n" +
                                                "      \"failover_uris\": [\n" +
                                                "        {\n" +
                                                "          \"name\": \"somethingthing@mypool.com:5678\",\n" +
                                                "          \"active\": true\n" +
                                                "        }\n" +
                                                "      ],\n" +
                                                "      \"accepted_shares\": 1,\n" +
                                                "      \"rejected_shares\": 0,\n" +
                                                "      \"accepted_share_rate\": 0.02,\n" +
                                                "      \"rejected_share_rate\": 0\n" +
                                                "    }\n" +
                                                "  }\n" +
                                                "}"))),
                new MinerStats.Builder()
                        .setApiIp("127.0.0.1")
                        .setApiPort(8080)
                        .addPool(
                                new Pool.Builder()
                                        .setName("mypool.com:5678")
                                        .setStatus(true, true)
                                        .setPriority(0)
                                        .setCounts(1, 0, 0)
                                        .build())
                        .addRig(
                                new Rig.Builder()
                                        .setHashRate(new BigDecimal(14.73))
                                        .addGpu(
                                                new Gpu.Builder()
                                                        .setName("GPU 0")
                                                        .setTemp(51)
                                                        .setIndex(0)
                                                        .setBus(0)
                                                        .setFans(
                                                                new FanInfo.Builder()
                                                                        .setCount(1)
                                                                        .addSpeed(55)
                                                                        .setSpeedUnits("%")
                                                                        .build())
                                                        .setFreqInfo(
                                                                new FreqInfo.Builder()
                                                                        .setFreq(1695)
                                                                        .setMemFreq(5508)
                                                                        .build())
                                                        .build())
                                        .addGpu(
                                                new Gpu.Builder()
                                                        .setName("GPU 1")
                                                        .setTemp(52)
                                                        .setIndex(1)
                                                        .setBus(0)
                                                        .setFans(
                                                                new FanInfo.Builder()
                                                                        .setCount(1)
                                                                        .addSpeed(58)
                                                                        .setSpeedUnits("%")
                                                                        .build())
                                                        .setFreqInfo(
                                                                new FreqInfo.Builder()
                                                                        .setFreq(1695)
                                                                        .setMemFreq(5508)
                                                                        .build())
                                                        .build())
                                        .addGpu(
                                                new Gpu.Builder()
                                                        .setName("GPU 2")
                                                        .setTemp(52)
                                                        .setIndex(2)
                                                        .setBus(0)
                                                        .setFans(
                                                                new FanInfo.Builder()
                                                                        .setCount(1)
                                                                        .addSpeed(59)
                                                                        .setSpeedUnits("%")
                                                                        .build())
                                                        .setFreqInfo(
                                                                new FreqInfo.Builder()
                                                                        .setFreq(1695)
                                                                        .setMemFreq(5508)
                                                                        .build())
                                                        .build())
                                        .addGpu(
                                                new Gpu.Builder()
                                                        .setName("GPU 3")
                                                        .setTemp(50)
                                                        .setIndex(3)
                                                        .setBus(0)
                                                        .setFans(
                                                                new FanInfo.Builder()
                                                                        .setCount(1)
                                                                        .addSpeed(50)
                                                                        .setSpeedUnits("%")
                                                                        .build())
                                                        .setFreqInfo(
                                                                new FreqInfo.Builder()
                                                                        .setFreq(1695)
                                                                        .setMemFreq(5508)
                                                                        .build())
                                                        .build())
                                        .addGpu(
                                                new Gpu.Builder()
                                                        .setName("GPU 4")
                                                        .setTemp(51)
                                                        .setIndex(4)
                                                        .setBus(0)
                                                        .setFans(
                                                                new FanInfo.Builder()
                                                                        .setCount(1)
                                                                        .addSpeed(53)
                                                                        .setSpeedUnits("%")
                                                                        .build())
                                                        .setFreqInfo(
                                                                new FreqInfo.Builder()
                                                                        .setFreq(1695)
                                                                        .setMemFreq(5508)
                                                                        .build())
                                                        .build())
                                        .addGpu(
                                                new Gpu.Builder()
                                                        .setName("GPU 5")
                                                        .setTemp(49)
                                                        .setIndex(5)
                                                        .setBus(0)
                                                        .setFans(
                                                                new FanInfo.Builder()
                                                                        .setCount(1)
                                                                        .addSpeed(48)
                                                                        .setSpeedUnits("%")
                                                                        .build())
                                                        .setFreqInfo(
                                                                new FreqInfo.Builder()
                                                                        .setFreq(1695)
                                                                        .setMemFreq(5508)
                                                                        .build())
                                                        .build())
                                        .build())
                        .build());
    }
}