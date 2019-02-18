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
public class BminerGrinITest
        extends AbstractApiITest {

    /** Constructor. */
    public BminerGrinITest() {
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
                                                "          \"algorithm\": \"cuckaroo29\",\n" +
                                                "          \"speed_info\": {\n" +
                                                "            \"hash_rate\": 4.7\n" +
                                                "          }\n" +
                                                "        }\n" +
                                                "      ]\n" +
                                                "    },\n" +
                                                "    \"1\": {\n" +
                                                "      \"solvers\": [\n" +
                                                "        {\n" +
                                                "          \"algorithm\": \"cuckaroo29\",\n" +
                                                "          \"speed_info\": {\n" +
                                                "            \"hash_rate\": 4.66\n" +
                                                "          }\n" +
                                                "        }\n" +
                                                "      ]\n" +
                                                "    },\n" +
                                                "    \"2\": {\n" +
                                                "      \"solvers\": [\n" +
                                                "        {\n" +
                                                "          \"algorithm\": \"cuckaroo29\",\n" +
                                                "          \"speed_info\": {\n" +
                                                "            \"hash_rate\": 4.71\n" +
                                                "          }\n" +
                                                "        }\n" +
                                                "      ]\n" +
                                                "    },\n" +
                                                "    \"3\": {\n" +
                                                "      \"solvers\": [\n" +
                                                "        {\n" +
                                                "          \"algorithm\": \"cuckaroo29\",\n" +
                                                "          \"speed_info\": {\n" +
                                                "            \"hash_rate\": 4.69\n" +
                                                "          }\n" +
                                                "        }\n" +
                                                "      ]\n" +
                                                "    },\n" +
                                                "    \"4\": {\n" +
                                                "      \"solvers\": [\n" +
                                                "        {\n" +
                                                "          \"algorithm\": \"cuckaroo29\",\n" +
                                                "          \"speed_info\": {\n" +
                                                "            \"hash_rate\": 4.7\n" +
                                                "          }\n" +
                                                "        }\n" +
                                                "      ]\n" +
                                                "    },\n" +
                                                "    \"5\": {\n" +
                                                "      \"solvers\": [\n" +
                                                "        {\n" +
                                                "          \"algorithm\": \"cuckaroo29\",\n" +
                                                "          \"speed_info\": {\n" +
                                                "            \"hash_rate\": 4.7\n" +
                                                "          }\n" +
                                                "        }\n" +
                                                "      ]\n" +
                                                "    },\n" +
                                                "    \"6\": {\n" +
                                                "      \"solvers\": [\n" +
                                                "        {\n" +
                                                "          \"algorithm\": \"cuckaroo29\",\n" +
                                                "          \"speed_info\": {\n" +
                                                "            \"hash_rate\": 4.68\n" +
                                                "          }\n" +
                                                "        }\n" +
                                                "      ]\n" +
                                                "    },\n" +
                                                "    \"7\": {\n" +
                                                "      \"solvers\": [\n" +
                                                "        {\n" +
                                                "          \"algorithm\": \"cuckaroo29\",\n" +
                                                "          \"speed_info\": {\n" +
                                                "            \"hash_rate\": 4.71\n" +
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
                                                "  \"algorithm\": \"cuckaroo29\",\n" +
                                                "  \"stratum\": {\n" +
                                                "    \"accepted_shares\": 570,\n" +
                                                "    \"rejected_shares\": 23,\n" +
                                                "    \"accepted_share_rate\": 0.12,\n" +
                                                "    \"rejected_share_rate\": 0\n" +
                                                "  },\n" +
                                                "  \"miners\": {\n" +
                                                "    \"0\": {\n" +
                                                "      \"solver\": {\n" +
                                                "        \"solution_rate\": 4.71\n" +
                                                "      },\n" +
                                                "      \"device\": {\n" +
                                                "        \"temperature\": 63,\n" +
                                                "        \"power\": 118,\n" +
                                                "        \"fan_speed\": 45,\n" +
                                                "        \"global_memory_used\": 5797,\n" +
                                                "        \"utilization\": {\n" +
                                                "          \"gpu\": 100,\n" +
                                                "          \"memory\": 78\n" +
                                                "        },\n" +
                                                "        \"clocks\": {\n" +
                                                "          \"core\": 1721,\n" +
                                                "          \"memory\": 4100\n" +
                                                "        },\n" +
                                                "        \"pci\": {\n" +
                                                "          \"bar1_used\": 5,\n" +
                                                "          \"rx_throughput\": 30,\n" +
                                                "          \"tx_throughput\": 11\n" +
                                                "        }\n" +
                                                "      }\n" +
                                                "    },\n" +
                                                "    \"1\": {\n" +
                                                "      \"solver\": {\n" +
                                                "        \"solution_rate\": 4.7\n" +
                                                "      },\n" +
                                                "      \"device\": {\n" +
                                                "        \"temperature\": 61,\n" +
                                                "        \"power\": 122,\n" +
                                                "        \"fan_speed\": 44,\n" +
                                                "        \"global_memory_used\": 5797,\n" +
                                                "        \"utilization\": {\n" +
                                                "          \"gpu\": 100,\n" +
                                                "          \"memory\": 80\n" +
                                                "        },\n" +
                                                "        \"clocks\": {\n" +
                                                "          \"core\": 1695,\n" +
                                                "          \"memory\": 4100\n" +
                                                "        },\n" +
                                                "        \"pci\": {\n" +
                                                "          \"bar1_used\": 5,\n" +
                                                "          \"rx_throughput\": 34,\n" +
                                                "          \"tx_throughput\": 20\n" +
                                                "        }\n" +
                                                "      }\n" +
                                                "    },\n" +
                                                "    \"2\": {\n" +
                                                "      \"solver\": {\n" +
                                                "        \"solution_rate\": 4.74\n" +
                                                "      },\n" +
                                                "      \"device\": {\n" +
                                                "        \"temperature\": 62,\n" +
                                                "        \"power\": 114,\n" +
                                                "        \"fan_speed\": 45,\n" +
                                                "        \"global_memory_used\": 5797,\n" +
                                                "        \"utilization\": {\n" +
                                                "          \"gpu\": 100,\n" +
                                                "          \"memory\": 67\n" +
                                                "        },\n" +
                                                "        \"clocks\": {\n" +
                                                "          \"core\": 1708,\n" +
                                                "          \"memory\": 4100\n" +
                                                "        },\n" +
                                                "        \"pci\": {\n" +
                                                "          \"bar1_used\": 5,\n" +
                                                "          \"rx_throughput\": 2,\n" +
                                                "          \"tx_throughput\": 0\n" +
                                                "        }\n" +
                                                "      }\n" +
                                                "    },\n" +
                                                "    \"3\": {\n" +
                                                "      \"solver\": {\n" +
                                                "        \"solution_rate\": 4.72\n" +
                                                "      },\n" +
                                                "      \"device\": {\n" +
                                                "        \"temperature\": 62,\n" +
                                                "        \"power\": 123,\n" +
                                                "        \"fan_speed\": 45,\n" +
                                                "        \"global_memory_used\": 5797,\n" +
                                                "        \"utilization\": {\n" +
                                                "          \"gpu\": 100,\n" +
                                                "          \"memory\": 85\n" +
                                                "        },\n" +
                                                "        \"clocks\": {\n" +
                                                "          \"core\": 1708,\n" +
                                                "          \"memory\": 4100\n" +
                                                "        },\n" +
                                                "        \"pci\": {\n" +
                                                "          \"bar1_used\": 5,\n" +
                                                "          \"rx_throughput\": 39,\n" +
                                                "          \"tx_throughput\": 9\n" +
                                                "        }\n" +
                                                "      }\n" +
                                                "    },\n" +
                                                "    \"4\": {\n" +
                                                "      \"solver\": {\n" +
                                                "        \"solution_rate\": 4.73\n" +
                                                "      },\n" +
                                                "      \"device\": {\n" +
                                                "        \"temperature\": 63,\n" +
                                                "        \"power\": 123,\n" +
                                                "        \"fan_speed\": 45,\n" +
                                                "        \"global_memory_used\": 5797,\n" +
                                                "        \"utilization\": {\n" +
                                                "          \"gpu\": 100,\n" +
                                                "          \"memory\": 73\n" +
                                                "        },\n" +
                                                "        \"clocks\": {\n" +
                                                "          \"core\": 1695,\n" +
                                                "          \"memory\": 4100\n" +
                                                "        },\n" +
                                                "        \"pci\": {\n" +
                                                "          \"bar1_used\": 5,\n" +
                                                "          \"rx_throughput\": 29,\n" +
                                                "          \"tx_throughput\": 10\n" +
                                                "        }\n" +
                                                "      }\n" +
                                                "    },\n" +
                                                "    \"5\": {\n" +
                                                "      \"solver\": {\n" +
                                                "        \"solution_rate\": 4.73\n" +
                                                "      },\n" +
                                                "      \"device\": {\n" +
                                                "        \"temperature\": 64,\n" +
                                                "        \"power\": 120,\n" +
                                                "        \"fan_speed\": 46,\n" +
                                                "        \"global_memory_used\": 5797,\n" +
                                                "        \"utilization\": {\n" +
                                                "          \"gpu\": 100,\n" +
                                                "          \"memory\": 65\n" +
                                                "        },\n" +
                                                "        \"clocks\": {\n" +
                                                "          \"core\": 1695,\n" +
                                                "          \"memory\": 4100\n" +
                                                "        },\n" +
                                                "        \"pci\": {\n" +
                                                "          \"bar1_used\": 5,\n" +
                                                "          \"rx_throughput\": 32,\n" +
                                                "          \"tx_throughput\": 10\n" +
                                                "        }\n" +
                                                "      }\n" +
                                                "    },\n" +
                                                "    \"6\": {\n" +
                                                "      \"solver\": {\n" +
                                                "        \"solution_rate\": 4.75\n" +
                                                "      },\n" +
                                                "      \"device\": {\n" +
                                                "        \"temperature\": 62,\n" +
                                                "        \"power\": 119,\n" +
                                                "        \"fan_speed\": 45,\n" +
                                                "        \"global_memory_used\": 5797,\n" +
                                                "        \"utilization\": {\n" +
                                                "          \"gpu\": 100,\n" +
                                                "          \"memory\": 68\n" +
                                                "        },\n" +
                                                "        \"clocks\": {\n" +
                                                "          \"core\": 1708,\n" +
                                                "          \"memory\": 4100\n" +
                                                "        },\n" +
                                                "        \"pci\": {\n" +
                                                "          \"bar1_used\": 5,\n" +
                                                "          \"rx_throughput\": 27,\n" +
                                                "          \"tx_throughput\": 9\n" +
                                                "        }\n" +
                                                "      }\n" +
                                                "    },\n" +
                                                "    \"7\": {\n" +
                                                "      \"solver\": {\n" +
                                                "        \"solution_rate\": 4.73\n" +
                                                "      },\n" +
                                                "      \"device\": {\n" +
                                                "        \"temperature\": 66,\n" +
                                                "        \"power\": 118,\n" +
                                                "        \"fan_speed\": 48,\n" +
                                                "        \"global_memory_used\": 5797,\n" +
                                                "        \"utilization\": {\n" +
                                                "          \"gpu\": 100,\n" +
                                                "          \"memory\": 80\n" +
                                                "        },\n" +
                                                "        \"clocks\": {\n" +
                                                "          \"core\": 1683,\n" +
                                                "          \"memory\": 4100\n" +
                                                "        },\n" +
                                                "        \"pci\": {\n" +
                                                "          \"bar1_used\": 5,\n" +
                                                "          \"rx_throughput\": 23,\n" +
                                                "          \"tx_throughput\": 8\n" +
                                                "        }\n" +
                                                "      }\n" +
                                                "    }\n" +
                                                "  },\n" +
                                                "  \"version\": \"v15.0.0-e143cb4\",\n" +
                                                "  \"start_time\": 1550426848\n" +
                                                "}"),
                                "/api/v1/status/stratum",
                                new HttpHandler(
                                        "",
                                        "{\n" +
                                                "  \"stratums\": {\n" +
                                                "    \"cuckaroo29\": {\n" +
                                                "      \"failover_uris\": [\n" +
                                                "        {\n" +
                                                "          \"name\": \"cuckaroo29:\\/\\/xxx.vii:foo@grincuckaroo29.usa.nicehash.com:3371\",\n" +
                                                "          \"active\": true\n" +
                                                "        }\n" +
                                                "      ],\n" +
                                                "      \"accepted_shares\": 586,\n" +
                                                "      \"rejected_shares\": 24,\n" +
                                                "      \"accepted_share_rate\": 0.17,\n" +
                                                "      \"rejected_share_rate\": 0.01\n" +
                                                "    }\n" +
                                                "  }\n" +
                                                "}"))),
                new MinerStats.Builder()
                        .setApiIp("127.0.0.1")
                        .setApiPort(8080)
                        .addPool(
                                new Pool.Builder()
                                        .setName("grincuckaroo29.usa.nicehash.com:3371")
                                        .setStatus(true, true)
                                        .setPriority(0)
                                        .setCounts(586, 24, 0)
                                        .build())
                        .addRig(
                                new Rig.Builder()
                                        .setHashRate(new BigDecimal("37.5499999999999971578290569595992565155029296875"))
                                        .addGpu(
                                                new Gpu.Builder()
                                                        .setName("GPU 0")
                                                        .setTemp(63)
                                                        .setIndex(0)
                                                        .setBus(0)
                                                        .setFans(
                                                                new FanInfo.Builder()
                                                                        .setCount(1)
                                                                        .addSpeed(45)
                                                                        .setSpeedUnits("%")
                                                                        .build())
                                                        .setFreqInfo(
                                                                new FreqInfo.Builder()
                                                                        .setFreq(1721)
                                                                        .setMemFreq(4100)
                                                                        .build())
                                                        .build())
                                        .addGpu(
                                                new Gpu.Builder()
                                                        .setName("GPU 1")
                                                        .setTemp(61)
                                                        .setIndex(1)
                                                        .setBus(0)
                                                        .setFans(
                                                                new FanInfo.Builder()
                                                                        .setCount(1)
                                                                        .addSpeed(44)
                                                                        .setSpeedUnits("%")
                                                                        .build())
                                                        .setFreqInfo(
                                                                new FreqInfo.Builder()
                                                                        .setFreq(1695)
                                                                        .setMemFreq(4100)
                                                                        .build())
                                                        .build())
                                        .addGpu(
                                                new Gpu.Builder()
                                                        .setName("GPU 2")
                                                        .setTemp(62)
                                                        .setIndex(2)
                                                        .setBus(0)
                                                        .setFans(
                                                                new FanInfo.Builder()
                                                                        .setCount(1)
                                                                        .addSpeed(45)
                                                                        .setSpeedUnits("%")
                                                                        .build())
                                                        .setFreqInfo(
                                                                new FreqInfo.Builder()
                                                                        .setFreq(1708)
                                                                        .setMemFreq(4100)
                                                                        .build())
                                                        .build())
                                        .addGpu(
                                                new Gpu.Builder()
                                                        .setName("GPU 3")
                                                        .setTemp(62)
                                                        .setIndex(3)
                                                        .setBus(0)
                                                        .setFans(
                                                                new FanInfo.Builder()
                                                                        .setCount(1)
                                                                        .addSpeed(45)
                                                                        .setSpeedUnits("%")
                                                                        .build())
                                                        .setFreqInfo(
                                                                new FreqInfo.Builder()
                                                                        .setFreq(1708)
                                                                        .setMemFreq(4100)
                                                                        .build())
                                                        .build())
                                        .addGpu(
                                                new Gpu.Builder()
                                                        .setName("GPU 4")
                                                        .setTemp(63)
                                                        .setIndex(4)
                                                        .setBus(0)
                                                        .setFans(
                                                                new FanInfo.Builder()
                                                                        .setCount(1)
                                                                        .addSpeed(45)
                                                                        .setSpeedUnits("%")
                                                                        .build())
                                                        .setFreqInfo(
                                                                new FreqInfo.Builder()
                                                                        .setFreq(1695)
                                                                        .setMemFreq(4100)
                                                                        .build())
                                                        .build())
                                        .addGpu(
                                                new Gpu.Builder()
                                                        .setName("GPU 5")
                                                        .setTemp(64)
                                                        .setIndex(5)
                                                        .setBus(0)
                                                        .setFans(
                                                                new FanInfo.Builder()
                                                                        .setCount(1)
                                                                        .addSpeed(46)
                                                                        .setSpeedUnits("%")
                                                                        .build())
                                                        .setFreqInfo(
                                                                new FreqInfo.Builder()
                                                                        .setFreq(1695)
                                                                        .setMemFreq(4100)
                                                                        .build())
                                                        .build())
                                        .addGpu(
                                                new Gpu.Builder()
                                                        .setName("GPU 6")
                                                        .setTemp(62)
                                                        .setIndex(6)
                                                        .setBus(0)
                                                        .setFans(
                                                                new FanInfo.Builder()
                                                                        .setCount(1)
                                                                        .addSpeed(45)
                                                                        .setSpeedUnits("%")
                                                                        .build())
                                                        .setFreqInfo(
                                                                new FreqInfo.Builder()
                                                                        .setFreq(1708)
                                                                        .setMemFreq(4100)
                                                                        .build())
                                                        .build())
                                        .addGpu(
                                                new Gpu.Builder()
                                                        .setName("GPU 7")
                                                        .setTemp(66)
                                                        .setIndex(7)
                                                        .setBus(0)
                                                        .setFans(
                                                                new FanInfo.Builder()
                                                                        .setCount(1)
                                                                        .addSpeed(48)
                                                                        .setSpeedUnits("%")
                                                                        .build())
                                                        .setFreqInfo(
                                                                new FreqInfo.Builder()
                                                                        .setFreq(1683)
                                                                        .setMemFreq(4100)
                                                                        .build())
                                                        .build())
                                        .build())
                        .build());
    }
}