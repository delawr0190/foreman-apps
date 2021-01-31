package mn.foreman.multminer;

import mn.foreman.model.Detection;
import mn.foreman.util.AbstractDetectITest;
import mn.foreman.util.http.FakeHttpMinerServer;
import mn.foreman.util.http.HttpHandler;

import com.google.common.collect.ImmutableMap;
import one.util.streamex.EntryStream;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

/** Tests detection of a MultMiner. */
@RunWith(Parameterized.class)
public class MultMinerM1DetectITest
        extends AbstractDetectITest {

    /**
     * Constructor.
     *
     * @param args          The arguments.
     * @param detectionArgs The detection arguments.
     */
    public MultMinerM1DetectITest(
            final Map<String, Object> args,
            final Map<String, Object> detectionArgs) {
        super(
                new MultMinerDetectionStrategy(
                        new MultMinerFactory().create(
                                ImmutableMap.of(
                                        "apiIp",
                                        "127.0.0.1",
                                        "apiPort",
                                        "8080"))),
                "127.0.0.1",
                8080,
                args,
                () -> new FakeHttpMinerServer(
                        8080,
                        ImmutableMap.of(
                                "/gst.csp",
                                new HttpHandler(
                                        "",
                                        Collections.emptyMap(),
                                        "a=a",
                                        "{\"err\":0,\"bd\":[{\"ol\":0,\"cn\":0,\"tphr\":[0,0.00,0.00,0.00],\"cs\":[[0,0.00,0,0,\"0/0\",\"0/0\",\"0/0\",\"\"],[0,0.00,0,0,\"0/0\",\"0/0\",\"0/0\",\"\"],[0,0.00,0,0,\"0/0\",\"0/0\",\"0/0\",\"\"],[0,0.00,0,0,\"0/0\",\"0/0\",\"0/0\",\"\"],[0,0.00,0,0,\"0/0\",\"0/0\",\"0/0\",\"\"],[0,0.00,0,0,\"0/0\",\"0/0\",\"0/0\",\"\"],[0,0.00,0,0,\"0/0\",\"0/0\",\"0/0\",\"\"],[0,0.00,0,0,\"0/0\",\"0/0\",\"0/0\",\"\"]]},{\"ol\":1,\"cn\":8,\"tphr\":[315,54.56,56.75,42.12],\"cs\":[[1,74.93,0,0,\"1316/1365\",\"3/0\",\"743/782\",\"5.58GH/s | 743\"],[1,69.89,0,0,\"1316/1365\",\"4/0\",\"760/760\",\"5.18GH/s | 759\"],[1,79.24,0,0,\"1316/1365\",\"2/0\",\"701/755\",\"5.42GH/s | 699\"],[1,65.09,0,0,\"1316/1365\",\"1/0\",\"715/726\",\"5.35GH/s | 715\"],[1,60.78,0,0,\"1316/1365\",\"4/0\",\"693/705\",\"5.24GH/s | 693\"],[1,69.27,0,0,\"1316/1365\",\"2/0\",\"711/744\",\"5.18GH/s | 711\"],[1,85.51,0,0,\"1316/1365\",\"3/0\",\"731/791\",\"5.53GH/s | 729\"],[1,77.76,0,0,\"1316/1365\",\"8/0\",\"726/773\",\"5.20GH/s | 725\"]]},{\"ol\":1,\"cn\":8,\"tphr\":[258,50.75,54.38,43.88],\"cs\":[[1,63.24,0,0,\"1316/1365\",\"4/0\",\"682/733\",\"5.46GH/s | 681\"],[1,64.97,0,0,\"1316/1365\",\"8/0\",\"743/767\",\"5.47GH/s | 740\"],[1,62.14,0,0,\"1316/1365\",\"9/0\",\"697/781\",\"5.48GH/s | 694\"],[1,57.09,0,0,\"1316/1365\",\"5/0\",\"738/781\",\"5.35GH/s | 733\"],[1,86.01,0,0,\"1316/1365\",\"4/0\",\"762/766\",\"5.53GH/s | 762\"],[1,71.12,0,0,\"1316/1365\",\"5/0\",\"799/750\",\"5.46GH/s | 797\"],[1,80.72,0,0,\"1316/1365\",\"0/0\",\"738/728\",\"5.19GH/s | 738\"],[1,70.26,0,0,\"1316/1365\",\"3/0\",\"746/745\",\"5.60GH/s | 746\"]]},{\"ol\":1,\"cn\":8,\"tphr\":[272,51.25,51.50,43.88],\"cs\":[[1,70.75,0,0,\"1316/1365\",\"7/1\",\"724/753\",\"5.20GH/s | 722\"],[1,67.67,0,0,\"1316/1365\",\"7/0\",\"717/763\",\"5.22GH/s | 714\"],[1,66.20,0,0,\"1316/1365\",\"2/0\",\"698/753\",\"5.55GH/s | 697\"],[1,60.29,0,0,\"1316/1365\",\"4/0\",\"703/748\",\"5.46GH/s | 702\"],[1,60.29,0,0,\"1316/1365\",\"5/0\",\"723/708\",\"5.45GH/s | 722\"],[1,67.92,0,0,\"1316/1365\",\"3/0\",\"729/727\",\"5.20GH/s | 729\"],[1,80.59,0,0,\"1316/1365\",\"3/0\",\"683/772\",\"5.33GH/s | 681\"],[1,71.73,0,0,\"1316/1365\",\"4/0\",\"727/771\",\"5.55GH/s | 726\"]]},0],\"mt\":\"trb 2020/06/17 01:04:04 Hr:129.93 Pw:845\",\"ms\":[[\"working\",\"00:29:35\",\"xxx\",\"100\",\"1\",\"1\",\"-o pplns.trb.stratum.hashpool.com:8208\"],[\"connected\",\"00:29:36\",\"xxx\",\"0\",\"0\",\"1\",\"-o1 pplns.trb.stratum.hashpool.com:8208\"],[\"connected\",\"00:29:36\",\"xxx\",\"0\",\"0\",\"1\",\"-o2 solo.trb.stratum.hashpool.com:8208\"]],\"isalarm\":0}",
                                        Collections.emptyMap()))),
                Detection.builder()
                        .minerType(MultMinerType.M1)
                        .ipAddress("127.0.0.1")
                        .port(8080)
                        .parameters(detectionArgs)
                        .build());
    }

    /**
     * Test parameters
     *
     * @return The test parameters.
     */
    @Parameterized.Parameters
    public static Collection<Object[]> parameters() {
        return Arrays.asList(
                new Object[][]{
                        {
                                // Worker not preferred
                                DEFAULT_ARGS,
                                DEFAULT_ARGS
                        },
                        {
                                // Worker preferred
                                EntryStream
                                        .of(DEFAULT_ARGS)
                                        .append(
                                                "workerPreferred",
                                                "true")
                                        .toMap(),
                                EntryStream
                                        .of(DEFAULT_ARGS)
                                        .append(
                                                "workerPreferred",
                                                "true")
                                        .append(
                                                "worker",
                                                "xxx")
                                        .toMap()
                        }
                }
        );
    }
}