package mn.foreman.lolminer;

import mn.foreman.model.miners.FanInfo;
import mn.foreman.model.miners.MinerStats;
import mn.foreman.model.miners.Pool;
import mn.foreman.model.miners.rig.FreqInfo;
import mn.foreman.model.miners.rig.Gpu;
import mn.foreman.model.miners.rig.Rig;
import mn.foreman.util.AbstractApiITest;
import mn.foreman.util.rpc.FakeRpcMinerServer;
import mn.foreman.util.rpc.RpcHandler;

import com.google.common.collect.ImmutableMap;

import java.math.BigDecimal;

/** Runs an integration tests using {@link LolminerITest} against a fake API. */
public class LolminerITest
        extends AbstractApiITest {

    /** Constructor. */
    public LolminerITest() {
        super(
                new Lolminer(
                        "lolminer",
                        "127.0.0.1",
                        8080),
                new FakeRpcMinerServer(
                        8080,
                        ImmutableMap.of(
                                "",
                                new RpcHandler(
                                        "{\n" +
                                                "  \"Software\": \"lolMiner 0.41\",\n" +
                                                "  \"Startup\": \"2018-08-04 10:17:42 \",\n" +
                                                "  \"Coin\": \"BitcoinZ (BTCZ)\",\n" +
                                                "  \"Algorithm\": \"Equihash 144.5\",\n" +
                                                "  \"ConnectedPool\": \"mine-btcz-euro.equipool.1ds.us:50061\",\n" +
                                                "  \"ConnectedUser\": \"t1dbQvbohSUAGE6dZpUYru7e18SCNVipVXY.lolMiner\",\n" +
                                                "  \"LastUpdate(5s)\": \"22:33:46 \",\n" +
                                                "  \"LastUpdate(60s)\": \"22:32:55 \",\n" +
                                                "  \"TotalSpeed(5s)\": \"24722.0225\",\n" +
                                                "  \"TotalSpeed(60s)\": \" \",\n" +
                                                "  \"GPU0\": {\n" +
                                                "    \"Name\": \"AMD Radeon (TM) RX 480 Graphics\",\n" +
                                                "    \"Speed(5s)\": \"13468.8252\",\n" +
                                                "    \"Speed(60s)\": \"13429.5117\"\n" +
                                                "  },\n" +
                                                "  \"GPU1\": {\n" +
                                                "    \"Name\": \"Radeon RX 580 Series\",\n" +
                                                "    \"Speed(5s)\": \"11253.1973\",\n" +
                                                "    \"Speed(60s)\": \"11262.5537\"\n" +
                                                "  }\n" +
                                                "}"))),
                new MinerStats.Builder()
                        .setName("lolminer")
                        .setApiIp("127.0.0.1")
                        .setApiPort(8080)
                        .addPool(
                                new Pool.Builder()
                                        .setName("mine-btcz-euro.equipool.1ds.us:50061")
                                        .setStatus(true, true)
                                        .setPriority(0)
                                        .setCounts(0, 0, 0)
                                        .build())
                        .addRig(
                                new Rig.Builder()
                                        .setName("lolminer_lolMiner 0.41")
                                        .setHashRate(new BigDecimal("24722.0225"))
                                        .addGpu(
                                                new Gpu.Builder()
                                                        .setName("AMD Radeon (TM) RX 480 Graphics")
                                                        .setIndex(0)
                                                        .setBus(0)
                                                        .setTemp(0)
                                                        .setFans(
                                                                new FanInfo.Builder()
                                                                        .setCount(0)
                                                                        .setSpeedUnits("%")
                                                                        .build())
                                                        .setFreqInfo(
                                                                new FreqInfo.Builder()
                                                                        .setFreq(0)
                                                                        .setMemFreq(0)
                                                                        .build())
                                                        .build())
                                        .addGpu(
                                                new Gpu.Builder()
                                                        .setName("Radeon RX 580 Series")
                                                        .setIndex(1)
                                                        .setBus(0)
                                                        .setTemp(0)
                                                        .setFans(
                                                                new FanInfo.Builder()
                                                                        .setCount(0)
                                                                        .setSpeedUnits("%")
                                                                        .build())
                                                        .setFreqInfo(
                                                                new FreqInfo.Builder()
                                                                        .setFreq(0)
                                                                        .setMemFreq(0)
                                                                        .build())
                                                        .build())
                                        .build())
                        .build());
    }
}