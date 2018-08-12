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
public class BminerITest
        extends AbstractApiITest {

    /** Constructor. */
    public BminerITest() {
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
                                                "          \"algorithm\": \"ethash\",\n" +
                                                "          \"speed_info\": {\n" +
                                                "            \"hash_rate\": 30828134.4\n" +
                                                "          }\n" +
                                                "        },\n" +
                                                "        {\n" +
                                                "          \"algorithm\": \"blake2s\",\n" +
                                                "          \"speed_info\": {\n" +
                                                "            \"hash_rate\": 1778500208.17\n" +
                                                "          }\n" +
                                                "        }\n" +
                                                "      ]\n" +
                                                "    }\n" +
                                                "  }\n" +
                                                "}\n"),
                                "/api/v1/status/device",
                                new HttpHandler(
                                        "",
                                        "{\n" +
                                                "  \"devices\": {\n" +
                                                "    \"0\": {\n" +
                                                "      \"temperature\": 83,\n" +
                                                "      \"power\": 199,\n" +
                                                "      \"fan_speed\": 74,\n" +
                                                "      \"global_memory_used\": 4385,\n" +
                                                "      \"utilization\": {\n" +
                                                "        \"gpu\": 100,\n" +
                                                "        \"memory\": 73\n" +
                                                "      },\n" +
                                                "      \"clocks\": {\n" +
                                                "        \"core\": 1809,\n" +
                                                "        \"memory\": 5005\n" +
                                                "      }\n" +
                                                "    }\n" +
                                                "  }\n" +
                                                "}"),
                                "/api/v1/status/stratum",
                                new HttpHandler(
                                        "",
                                        "{\n" +
                                                "  \"stratums\": {\n" +
                                                "    \"blake2s\": {\n" +
                                                "      \"failover_uris\": [\n" +
                                                "        {\n" +
                                                "          \"name\": \"blake2s://DDXKDhq73GRM3hjh6uee57fJ3LS2ctNtyi.my:c=XVG,d=92@blake2s.mine.zpool.ca:5766\",\n" +
                                                "          \"active\": true\n" +
                                                "        }\n" +
                                                "      ],\n" +
                                                "      \"accepted_shares\": 1,\n" +
                                                "      \"rejected_shares\": 0,\n" +
                                                "      \"accepted_share_rate\": 0.02,\n" +
                                                "      \"rejected_share_rate\": 0\n" +
                                                "    },\n" +
                                                "    \"ethash\": {\n" +
                                                "      \"failover_uris\": [\n" +
                                                "        {\n" +
                                                "          \"name\": \"ethproxy://0xb76d43eAaB2e905028a7f0F3aF13C7A84c477B9f.w@guangdong-pool.ethfans.org:3333/\",\n" +
                                                "          \"active\": true\n" +
                                                "        }\n" +
                                                "      ],\n" +
                                                "      \"accepted_shares\": 2,\n" +
                                                "      \"rejected_shares\": 0,\n" +
                                                "      \"accepted_share_rate\": 0.01,\n" +
                                                "      \"rejected_share_rate\": 0\n" +
                                                "    }\n" +
                                                "  }\n" +
                                                "}"))),
                new MinerStats.Builder()
                        .setApiIp("127.0.0.1")
                        .setApiPort(8080)
                        .addPool(
                                new Pool.Builder()
                                        .setName("blake2s.mine.zpool.ca:5766")
                                        .setStatus(true, true)
                                        .setPriority(0)
                                        .setCounts(1, 0, 0)
                                        .build())
                        .addPool(
                                new Pool.Builder()
                                        .setName("guangdong-pool.ethfans.org:3333")
                                        .setStatus(true, true)
                                        .setPriority(0)
                                        .setCounts(2, 0, 0)
                                        .build())
                        .addRig(
                                new Rig.Builder()
                                        .setHashRate(new BigDecimal("1809328342.570000171661376953125"))
                                        .addGpu(
                                                new Gpu.Builder()
                                                        .setName("GPU 0")
                                                        .setTemp(83)
                                                        .setIndex(0)
                                                        .setBus(0)
                                                        .setFans(
                                                                new FanInfo.Builder()
                                                                        .setCount(1)
                                                                        .addSpeed(74)
                                                                        .setSpeedUnits("%")
                                                                        .build())
                                                        .setFreqInfo(
                                                                new FreqInfo.Builder()
                                                                        .setFreq(1809)
                                                                        .setMemFreq(5005)
                                                                        .build())
                                                        .build())
                                        .build())
                        .build());
    }
}