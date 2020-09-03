package mn.foreman.multminer;

import mn.foreman.model.miners.FanInfo;
import mn.foreman.model.miners.MinerStats;
import mn.foreman.model.miners.Pool;
import mn.foreman.model.miners.asic.Asic;
import mn.foreman.util.AbstractApiITest;
import mn.foreman.util.http.FakeHttpMinerServer;
import mn.foreman.util.http.HttpHandler;

import com.google.common.collect.ImmutableMap;

import java.math.BigDecimal;
import java.util.Collections;

/** Runs an integration tests using {@link MultMiner} against a fake API. */
public class MultMinerM1StatsITest
        extends AbstractApiITest {

    /** Constructor. */
    public MultMinerM1StatsITest() {
        super(
                new MultMinerFactory()
                        .create(
                                ImmutableMap.of(
                                        "apiIp",
                                        "127.0.0.1",
                                        "apiPort",
                                        "8080")),
                new FakeHttpMinerServer(
                        8080,
                        ImmutableMap.of(
                                "/gst.csp",
                                new HttpHandler(
                                        "",
                                        Collections.emptyMap(),
                                        "a=a",
                                        "{\"err\":0,\"bd\":[{\"ol\":0,\"cn\":0,\"tphr\":[0,0.00,0.00,0.00],\"cs\":[[0,0.00,0,0,\"0/0\",\"0/0\",\"0/0\",\"\"],[0,0.00,0,0,\"0/0\",\"0/0\",\"0/0\",\"\"],[0,0.00,0,0,\"0/0\",\"0/0\",\"0/0\",\"\"],[0,0.00,0,0,\"0/0\",\"0/0\",\"0/0\",\"\"],[0,0.00,0,0,\"0/0\",\"0/0\",\"0/0\",\"\"],[0,0.00,0,0,\"0/0\",\"0/0\",\"0/0\",\"\"],[0,0.00,0,0,\"0/0\",\"0/0\",\"0/0\",\"\"],[0,0.00,0,0,\"0/0\",\"0/0\",\"0/0\",\"\"]]},{\"ol\":1,\"cn\":8,\"tphr\":[315,54.56,56.75,42.12],\"cs\":[[1,74.93,0,0,\"1316/1365\",\"3/0\",\"743/782\",\"5.58GH/s | 743\"],[1,69.89,0,0,\"1316/1365\",\"4/0\",\"760/760\",\"5.18GH/s | 759\"],[1,79.24,0,0,\"1316/1365\",\"2/0\",\"701/755\",\"5.42GH/s | 699\"],[1,65.09,0,0,\"1316/1365\",\"1/0\",\"715/726\",\"5.35GH/s | 715\"],[1,60.78,0,0,\"1316/1365\",\"4/0\",\"693/705\",\"5.24GH/s | 693\"],[1,69.27,0,0,\"1316/1365\",\"2/0\",\"711/744\",\"5.18GH/s | 711\"],[1,85.51,0,0,\"1316/1365\",\"3/0\",\"731/791\",\"5.53GH/s | 729\"],[1,77.76,0,0,\"1316/1365\",\"8/0\",\"726/773\",\"5.20GH/s | 725\"]]},{\"ol\":1,\"cn\":8,\"tphr\":[258,50.75,54.38,43.88],\"cs\":[[1,63.24,0,0,\"1316/1365\",\"4/0\",\"682/733\",\"5.46GH/s | 681\"],[1,64.97,0,0,\"1316/1365\",\"8/0\",\"743/767\",\"5.47GH/s | 740\"],[1,62.14,0,0,\"1316/1365\",\"9/0\",\"697/781\",\"5.48GH/s | 694\"],[1,57.09,0,0,\"1316/1365\",\"5/0\",\"738/781\",\"5.35GH/s | 733\"],[1,86.01,0,0,\"1316/1365\",\"4/0\",\"762/766\",\"5.53GH/s | 762\"],[1,71.12,0,0,\"1316/1365\",\"5/0\",\"799/750\",\"5.46GH/s | 797\"],[1,80.72,0,0,\"1316/1365\",\"0/0\",\"738/728\",\"5.19GH/s | 738\"],[1,70.26,0,0,\"1316/1365\",\"3/0\",\"746/745\",\"5.60GH/s | 746\"]]},{\"ol\":1,\"cn\":8,\"tphr\":[272,51.25,51.50,43.88],\"cs\":[[1,70.75,0,0,\"1316/1365\",\"7/1\",\"724/753\",\"5.20GH/s | 722\"],[1,67.67,0,0,\"1316/1365\",\"7/0\",\"717/763\",\"5.22GH/s | 714\"],[1,66.20,0,0,\"1316/1365\",\"2/0\",\"698/753\",\"5.55GH/s | 697\"],[1,60.29,0,0,\"1316/1365\",\"4/0\",\"703/748\",\"5.46GH/s | 702\"],[1,60.29,0,0,\"1316/1365\",\"5/0\",\"723/708\",\"5.45GH/s | 722\"],[1,67.92,0,0,\"1316/1365\",\"3/0\",\"729/727\",\"5.20GH/s | 729\"],[1,80.59,0,0,\"1316/1365\",\"3/0\",\"683/772\",\"5.33GH/s | 681\"],[1,71.73,0,0,\"1316/1365\",\"4/0\",\"727/771\",\"5.55GH/s | 726\"]]},0],\"mt\":\"trb 2020/06/17 01:04:04 Hr:129.93 Pw:845\",\"ms\":[[\"working\",\"00:29:35\",\"xxx\",\"100\",\"1\",\"1\",\"-o pplns.trb.stratum.hashpool.com:8208\"],[\"connected\",\"00:29:36\",\"xxx\",\"0\",\"0\",\"1\",\"-o1 pplns.trb.stratum.hashpool.com:8208\"],[\"connected\",\"00:29:36\",\"xxx\",\"0\",\"0\",\"1\",\"-o2 solo.trb.stratum.hashpool.com:8208\"]],\"isalarm\":0}",
                                        Collections.emptyMap()))),
                new MinerStats.Builder()
                        .setApiIp("127.0.0.1")
                        .setApiPort(8080)
                        .addPool(
                                new Pool.Builder()
                                        .setName("pplns.trb.stratum.hashpool.com:8208")
                                        .setPriority(0)
                                        .setStatus(
                                                true,
                                                true)
                                        .setCounts(
                                                100,
                                                1,
                                                0)
                                        .build())
                        .addPool(
                                new Pool.Builder()
                                        .setName("pplns.trb.stratum.hashpool.com:8208")
                                        .setPriority(1)
                                        .setStatus(
                                                true,
                                                true)
                                        .setCounts(
                                                0,
                                                0,
                                                0)
                                        .build())
                        .addPool(
                                new Pool.Builder()
                                        .setName("solo.trb.stratum.hashpool.com:8208")
                                        .setPriority(2)
                                        .setStatus(
                                                true,
                                                true)
                                        .setCounts(
                                                0,
                                                0,
                                                0)
                                        .build())
                        .addAsic(
                                new Asic.Builder()
                                        .setHashRate(new BigDecimal("16147500000"))
                                        .setFanInfo(
                                                new FanInfo.Builder()
                                                        .setCount(0)
                                                        .setSpeedUnits("%")
                                                        .build())
                                        .addTemp(74)
                                        .addTemp(69)
                                        .addTemp(79)
                                        .addTemp(65)
                                        .addTemp(60)
                                        .addTemp(69)
                                        .addTemp(85)
                                        .addTemp(77)
                                        .addTemp(63)
                                        .addTemp(64)
                                        .addTemp(62)
                                        .addTemp(57)
                                        .addTemp(86)
                                        .addTemp(71)
                                        .addTemp(80)
                                        .addTemp(70)
                                        .addTemp(70)
                                        .addTemp(67)
                                        .addTemp(66)
                                        .addTemp(60)
                                        .addTemp(60)
                                        .addTemp(67)
                                        .addTemp(80)
                                        .addTemp(71)
                                        .setPowerState("trb")
                                        .build())
                        .build());
    }
}