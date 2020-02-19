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
                                                "            \"hash_rate\": 5.05\n" +
                                                "          }\n" +
                                                "        }\n" +
                                                "      ]\n" +
                                                "    },\n" +
                                                "    \"1\": {\n" +
                                                "      \"solvers\": [\n" +
                                                "        {\n" +
                                                "          \"algorithm\": \"aeternity\",\n" +
                                                "          \"speed_info\": {\n" +
                                                "            \"hash_rate\": 4.92\n" +
                                                "          }\n" +
                                                "        }\n" +
                                                "      ]\n" +
                                                "    },\n" +
                                                "    \"2\": {\n" +
                                                "      \"solvers\": [\n" +
                                                "        {\n" +
                                                "          \"algorithm\": \"aeternity\",\n" +
                                                "          \"speed_info\": {\n" +
                                                "            \"hash_rate\": 4.54\n" +
                                                "          }\n" +
                                                "        }\n" +
                                                "      ]\n" +
                                                "    },\n" +
                                                "    \"3\": {\n" +
                                                "      \"solvers\": [\n" +
                                                "        {\n" +
                                                "          \"algorithm\": \"aeternity\",\n" +
                                                "          \"speed_info\": {\n" +
                                                "            \"hash_rate\": 4.9\n" +
                                                "          }\n" +
                                                "        }\n" +
                                                "      ]\n" +
                                                "    },\n" +
                                                "    \"4\": {\n" +
                                                "      \"solvers\": [\n" +
                                                "        {\n" +
                                                "          \"algorithm\": \"aeternity\",\n" +
                                                "          \"speed_info\": {\n" +
                                                "            \"hash_rate\": 4.75\n" +
                                                "          }\n" +
                                                "        }\n" +
                                                "      ]\n" +
                                                "    },\n" +
                                                "    \"5\": {\n" +
                                                "      \"solvers\": [\n" +
                                                "        {\n" +
                                                "          \"algorithm\": \"aeternity\",\n" +
                                                "          \"speed_info\": {\n" +
                                                "            \"hash_rate\": 4.75\n" +
                                                "          }\n" +
                                                "        }\n" +
                                                "      ]\n" +
                                                "    }\n" +
                                                "  }\n" +
                                                "}"),
                                "/api/status",
                                new HttpHandler(
                                        "",
                                        "{\n" +
                                                "  \"algorithm\": \"aeternity\",\n" +
                                                "  \"stratum\": {\n" +
                                                "    \"accepted_shares\": 31,\n" +
                                                "    \"rejected_shares\": 0,\n" +
                                                "    \"accepted_share_rate\": 0.09,\n" +
                                                "    \"rejected_share_rate\": 0\n" +
                                                "  },\n" +
                                                "  \"miners\": {\n" +
                                                "    \"0\": {\n" +
                                                "      \"solver\": {\n" +
                                                "        \"solution_rate\": 4.98\n" +
                                                "      },\n" +
                                                "      \"device\": {\n" +
                                                "        \"temperature\": 46,\n" +
                                                "        \"power\": 162,\n" +
                                                "        \"fan_speed\": 56,\n" +
                                                "        \"global_memory_used\": 6361,\n" +
                                                "        \"utilization\": {\n" +
                                                "          \"gpu\": 83,\n" +
                                                "          \"memory\": 40\n" +
                                                "        },\n" +
                                                "        \"clocks\": {\n" +
                                                "          \"core\": 1632,\n" +
                                                "          \"memory\": 5508\n" +
                                                "        },\n" +
                                                "        \"pci\": {\n" +
                                                "          \"bar1_used\": 229,\n" +
                                                "          \"rx_throughput\": 627,\n" +
                                                "          \"tx_throughput\": 91\n" +
                                                "        }\n" +
                                                "      }\n" +
                                                "    },\n" +
                                                "    \"1\": {\n" +
                                                "      \"solver\": {\n" +
                                                "        \"solution_rate\": 4.9\n" +
                                                "      },\n" +
                                                "      \"device\": {\n" +
                                                "        \"temperature\": 48,\n" +
                                                "        \"power\": 162,\n" +
                                                "        \"fan_speed\": 58,\n" +
                                                "        \"global_memory_used\": 6073,\n" +
                                                "        \"utilization\": {\n" +
                                                "          \"gpu\": 69,\n" +
                                                "          \"memory\": 43\n" +
                                                "        },\n" +
                                                "        \"clocks\": {\n" +
                                                "          \"core\": 1632,\n" +
                                                "          \"memory\": 5508\n" +
                                                "        },\n" +
                                                "        \"pci\": {\n" +
                                                "          \"bar1_used\": 229,\n" +
                                                "          \"rx_throughput\": 609,\n" +
                                                "          \"tx_throughput\": 63\n" +
                                                "        }\n" +
                                                "      }\n" +
                                                "    },\n" +
                                                "    \"2\": {\n" +
                                                "      \"solver\": {\n" +
                                                "        \"solution_rate\": 4.7\n" +
                                                "      },\n" +
                                                "      \"device\": {\n" +
                                                "        \"temperature\": 47,\n" +
                                                "        \"power\": 71,\n" +
                                                "        \"fan_speed\": 58,\n" +
                                                "        \"global_memory_used\": 6338,\n" +
                                                "        \"utilization\": {\n" +
                                                "          \"gpu\": 89,\n" +
                                                "          \"memory\": 53\n" +
                                                "        },\n" +
                                                "        \"clocks\": {\n" +
                                                "          \"core\": 1632,\n" +
                                                "          \"memory\": 5508\n" +
                                                "        },\n" +
                                                "        \"pci\": {\n" +
                                                "          \"bar1_used\": 229,\n" +
                                                "          \"rx_throughput\": 475,\n" +
                                                "          \"tx_throughput\": 57\n" +
                                                "        }\n" +
                                                "      }\n" +
                                                "    },\n" +
                                                "    \"3\": {\n" +
                                                "      \"solver\": {\n" +
                                                "        \"solution_rate\": 4.91\n" +
                                                "      },\n" +
                                                "      \"device\": {\n" +
                                                "        \"temperature\": 45,\n" +
                                                "        \"power\": 158,\n" +
                                                "        \"fan_speed\": 55,\n" +
                                                "        \"global_memory_used\": 5952,\n" +
                                                "        \"utilization\": {\n" +
                                                "          \"gpu\": 93,\n" +
                                                "          \"memory\": 53\n" +
                                                "        },\n" +
                                                "        \"clocks\": {\n" +
                                                "          \"core\": 1632,\n" +
                                                "          \"memory\": 5508\n" +
                                                "        },\n" +
                                                "        \"pci\": {\n" +
                                                "          \"bar1_used\": 229,\n" +
                                                "          \"rx_throughput\": 446,\n" +
                                                "          \"tx_throughput\": 1807\n" +
                                                "        }\n" +
                                                "      }\n" +
                                                "    },\n" +
                                                "    \"4\": {\n" +
                                                "      \"solver\": {\n" +
                                                "        \"solution_rate\": 4.81\n" +
                                                "      },\n" +
                                                "      \"device\": {\n" +
                                                "        \"temperature\": 45,\n" +
                                                "        \"power\": 70,\n" +
                                                "        \"fan_speed\": 55,\n" +
                                                "        \"global_memory_used\": 5952,\n" +
                                                "        \"utilization\": {\n" +
                                                "          \"gpu\": 97,\n" +
                                                "          \"memory\": 55\n" +
                                                "        },\n" +
                                                "        \"clocks\": {\n" +
                                                "          \"core\": 1632,\n" +
                                                "          \"memory\": 5508\n" +
                                                "        },\n" +
                                                "        \"pci\": {\n" +
                                                "          \"bar1_used\": 229,\n" +
                                                "          \"rx_throughput\": 512,\n" +
                                                "          \"tx_throughput\": 43\n" +
                                                "        }\n" +
                                                "      }\n" +
                                                "    },\n" +
                                                "    \"5\": {\n" +
                                                "      \"solver\": {\n" +
                                                "        \"solution_rate\": 4.78\n" +
                                                "      },\n" +
                                                "      \"device\": {\n" +
                                                "        \"temperature\": 43,\n" +
                                                "        \"power\": 159,\n" +
                                                "        \"fan_speed\": 53,\n" +
                                                "        \"global_memory_used\": 6195,\n" +
                                                "        \"utilization\": {\n" +
                                                "          \"gpu\": 80,\n" +
                                                "          \"memory\": 45\n" +
                                                "        },\n" +
                                                "        \"clocks\": {\n" +
                                                "          \"core\": 1632,\n" +
                                                "          \"memory\": 5508\n" +
                                                "        },\n" +
                                                "        \"pci\": {\n" +
                                                "          \"bar1_used\": 229,\n" +
                                                "          \"rx_throughput\": 800,\n" +
                                                "          \"tx_throughput\": 56\n" +
                                                "        }\n" +
                                                "      }\n" +
                                                "    }\n" +
                                                "  },\n" +
                                                "  \"version\": \"v12.1.0-5083c32\",\n" +
                                                "  \"start_time\": 1547723050\n" +
                                                "}"),
                                "/api/v1/status/stratum",
                                new HttpHandler(
                                        "",
                                        "{\n" +
                                                "  \"stratums\": {\n" +
                                                "    \"aeternity\": {\n" +
                                                "      \"failover_uris\": [\n" +
                                                "        {\n" +
                                                "          \"name\": \"aeternity:\\/\\/ak_xxxx.1080Ti@ae.f2pool.com:7898\",\n" +
                                                "          \"active\": true\n" +
                                                "        }\n" +
                                                "      ],\n" +
                                                "      \"accepted_shares\": 16,\n" +
                                                "      \"rejected_shares\": 0,\n" +
                                                "      \"accepted_share_rate\": 0.15,\n" +
                                                "      \"rejected_share_rate\": 0\n" +
                                                "    }\n" +
                                                "  }\n" +
                                                "}"))),
                new MinerStats.Builder()
                        .setApiIp("127.0.0.1")
                        .setApiPort(8080)
                        .addPool(
                                new Pool.Builder()
                                        .setName("ae.f2pool.com:7898")
                                        .setStatus(true, true)
                                        .setPriority(0)
                                        .setCounts(16, 0, 0)
                                        .build())
                        .addRig(
                                new Rig.Builder()
                                        .setHashRate(new BigDecimal("28.910000000000000142108547152020037174224853515625"))
                                        .addGpu(
                                                new Gpu.Builder()
                                                        .setName("GPU 0")
                                                        .setTemp(46)
                                                        .setIndex(0)
                                                        .setBus(0)
                                                        .setFans(
                                                                new FanInfo.Builder()
                                                                        .setCount(1)
                                                                        .addSpeed(56)
                                                                        .setSpeedUnits("%")
                                                                        .build())
                                                        .setFreqInfo(
                                                                new FreqInfo.Builder()
                                                                        .setFreq(1632)
                                                                        .setMemFreq(5508)
                                                                        .build())
                                                        .build())
                                        .addGpu(
                                                new Gpu.Builder()
                                                        .setName("GPU 1")
                                                        .setTemp(48)
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
                                                                        .setFreq(1632)
                                                                        .setMemFreq(5508)
                                                                        .build())
                                                        .build())
                                        .addGpu(
                                                new Gpu.Builder()
                                                        .setName("GPU 2")
                                                        .setTemp(47)
                                                        .setIndex(2)
                                                        .setBus(0)
                                                        .setFans(
                                                                new FanInfo.Builder()
                                                                        .setCount(1)
                                                                        .addSpeed(58)
                                                                        .setSpeedUnits("%")
                                                                        .build())
                                                        .setFreqInfo(
                                                                new FreqInfo.Builder()
                                                                        .setFreq(1632)
                                                                        .setMemFreq(5508)
                                                                        .build())
                                                        .build())
                                        .addGpu(
                                                new Gpu.Builder()
                                                        .setName("GPU 3")
                                                        .setTemp(45)
                                                        .setIndex(3)
                                                        .setBus(0)
                                                        .setFans(
                                                                new FanInfo.Builder()
                                                                        .setCount(1)
                                                                        .addSpeed(55)
                                                                        .setSpeedUnits("%")
                                                                        .build())
                                                        .setFreqInfo(
                                                                new FreqInfo.Builder()
                                                                        .setFreq(1632)
                                                                        .setMemFreq(5508)
                                                                        .build())
                                                        .build())
                                        .addGpu(
                                                new Gpu.Builder()
                                                        .setName("GPU 4")
                                                        .setTemp(45)
                                                        .setIndex(4)
                                                        .setBus(0)
                                                        .setFans(
                                                                new FanInfo.Builder()
                                                                        .setCount(1)
                                                                        .addSpeed(55)
                                                                        .setSpeedUnits("%")
                                                                        .build())
                                                        .setFreqInfo(
                                                                new FreqInfo.Builder()
                                                                        .setFreq(1632)
                                                                        .setMemFreq(5508)
                                                                        .build())
                                                        .build())
                                        .addGpu(
                                                new Gpu.Builder()
                                                        .setName("GPU 5")
                                                        .setTemp(43)
                                                        .setIndex(5)
                                                        .setBus(0)
                                                        .setFans(
                                                                new FanInfo.Builder()
                                                                        .setCount(1)
                                                                        .addSpeed(53)
                                                                        .setSpeedUnits("%")
                                                                        .build())
                                                        .setFreqInfo(
                                                                new FreqInfo.Builder()
                                                                        .setFreq(1632)
                                                                        .setMemFreq(5508)
                                                                        .build())
                                                        .build())
                                        .addAttribute(
                                                "gpu_algo",
                                                "aeternity")
                                        .build())
                        .build());
    }
}