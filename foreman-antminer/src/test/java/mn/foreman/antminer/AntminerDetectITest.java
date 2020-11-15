package mn.foreman.antminer;

import mn.foreman.model.Detection;
import mn.foreman.util.AbstractDetectITest;
import mn.foreman.util.FakeMinerServer;
import mn.foreman.util.rpc.FakeRpcMinerServer;
import mn.foreman.util.rpc.RpcHandler;

import com.google.common.collect.ImmutableMap;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

/** Tests antminer detection. */
@RunWith(Parameterized.class)
public class AntminerDetectITest
        extends AbstractDetectITest {

    /**
     * Constructor.
     *
     * @param servers      The servers.
     * @param expectedType The expected type.
     */
    public AntminerDetectITest(
            final List<Supplier<FakeMinerServer>> servers,
            final AntminerType expectedType) {
        super(
                new AntminerDetectionStrategy("antMiner Configuration"),
                servers,
                Detection.builder()
                        .minerType(expectedType)
                        .ipAddress("127.0.0.1")
                        .port(4028)
                        .parameters(DEFAULT_ARGS)
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
                                // Antminer B3
                                Collections.singletonList(
                                        (Supplier<FakeMinerServer>) () -> new FakeRpcMinerServer(
                                                4028,
                                                ImmutableMap.of(
                                                        "{\"command\":\"version\"}",
                                                        new RpcHandler(
                                                                "{\"STATUS\":[{\"STATUS\":\"S\",\"When\":395315,\"Code\":22,\"Msg\":\"CGMiner versions\",\"Description\":\"bmminer 1.0.0\"}],\"VERSION\":[{\"CGMiner\":\"2.0.0\",\"API\":\"3.1\",\"Miner\":\"A.0.0.1\",\"CompileTime\":\"Wed Apr 11 22:21:40 CST 2018\",\"Type\":\"Antminer B3\"}],\"id\":1}")))),
                                AntminerType.ANTMINER_B3
                        },
                        {
                                // Antminer L3
                                Collections.singletonList(
                                        (Supplier<FakeMinerServer>) () -> new FakeRpcMinerServer(
                                                4028,
                                                ImmutableMap.of(
                                                        "{\"command\":\"version\"}",
                                                        new RpcHandler(
                                                                "{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1526315502,\"Code\":22,\"Msg\":\"CGMiner versions\",\"Description\":\"cgminer 4.9.0\"}],\"VERSION\":[{\"CGMiner\":\"4.9.0\",\"API\":\"3.1\",\"Miner\":\"1.0.1.3\",\"CompileTime\":\"Fri Aug 25 17:28:57 CST 2017\",\"Type\":\"Antminer L3+\"}],\"id\":1}")))),
                                AntminerType.ANTMINER_L3
                        },
                        {
                                // Antminer S9
                                Collections.singletonList(
                                        (Supplier<FakeMinerServer>) () -> new FakeRpcMinerServer(
                                                4028,
                                                ImmutableMap.of(
                                                        "{\"command\":\"version\"}",
                                                        new RpcHandler(
                                                                "{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1516328450,\"Code\":22,\"Msg\":\"BMMiner versions\",\"Description\":\"bmminer 1.0.0\"}],\"VERSION\":[{\"BMMiner\":\"2.0.0\",\"API\":\"3.1\",\"Miner\":\"16.8.1.3\",\"CompileTime\":\"Fri Nov 17 17:37:49 CST 2017\",\"Type\":\"Antminer S9\"}],\"id\":1}")))),
                                AntminerType.ANTMINER_S9
                        },
                        {
                                // Antminer S17
                                Collections.singletonList(
                                        (Supplier<FakeMinerServer>) () -> new FakeRpcMinerServer(
                                                4028,
                                                ImmutableMap.of(
                                                        "{\"command\":\"version\"}",
                                                        new RpcHandler(
                                                                "{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1576765845,\"Code\":22,\"Msg\":\"CGMiner versions\",\"Description\":\"cgminer 1.0.0\"}],\"VERSION\":[{\"BMMiner\":\"1.0.0\",\"API\":\"3.1\",\"Miner\":\"19.10.1.3\",\"CompileTime\":\"Tue Aug 20 10:37:07 CST 2019\",\"Type\":\"Antminer S17 Pro\"}],\"id\":1}")))),
                                AntminerType.ANTMINER_S17
                        },
                        {
                                // Antminer S17+
                                Collections.singletonList(
                                        (Supplier<FakeMinerServer>) () -> new FakeRpcMinerServer(
                                                4028,
                                                ImmutableMap.of(
                                                        "{\"command\":\"version\"}",
                                                        new RpcHandler(
                                                                "{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1595983646,\"Code\":22,\"Msg\":\"CGMiner versions\",\"Description\":\"cgminer 1.0.0\"}],\"VERSION\":[{\"BMMiner\":\"1.0.0\",\"API\":\"3.1\",\"Miner\":\"35.0.1.3\",\"CompileTime\":\"Mon Dec 16 12:57:06 CST 2019\",\"Type\":\"Antminer S17+\"}],\"id\":1}")))),
                                AntminerType.ANTMINER_S17P
                        },
                        {
                                // Antminer S19 Pro
                                Collections.singletonList(
                                        (Supplier<FakeMinerServer>) () -> new FakeRpcMinerServer(
                                                4028,
                                                ImmutableMap.of(
                                                        "{\"command\":\"version\"}",
                                                        new RpcHandler(
                                                                "{\"STATUS\": [{\"STATUS\": \"S\", \"When\": 1595967398, \"Code\": 22, \"Msg\": \"CGMiner versions\", \"Description\": \"cgminer 1.0.0\"}], \"VERSION\": [{\"BMMiner\": \"1.0.0\", \"API\": \"3.1\", \"Miner\": \"49.0.1.3\", \"CompileTime\": \"Mon Jun  1 20:27:44 CST 2020\", \"Type\": \"Antminer S19 Pro\"}], \"id\": 1}")))),
                                AntminerType.ANTMINER_S19_PRO
                        },
                        {
                                // Antminer Z9
                                Collections.singletonList(
                                        (Supplier<FakeMinerServer>) () -> new FakeRpcMinerServer(
                                                4028,
                                                ImmutableMap.of(
                                                        "{\"command\":\"version\"}",
                                                        new RpcHandler(
                                                                "{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1599985655,\"Code\":22,\"Msg\":\"CGMiner versions\",\"Description\":\"cgminer 4.9.0\"}],\"VERSION\":[{\"CGMiner\":\"4.9.0\",\"API\":\"3.1\",\"Miner\":\"9.0.0.5\",\"CompileTime\":\"Sat May 26 20:42:30 CST 2018\",\"Type\":\"Antminer Z9\"}],\"id\":1}")))),
                                AntminerType.ANTMINER_Z9
                        },
                        {
                                // Antminer Z9 Mini
                                Collections.singletonList(
                                        (Supplier<FakeMinerServer>) () -> new FakeRpcMinerServer(
                                                4028,
                                                ImmutableMap.of(
                                                        "{\"command\":\"version\"}",
                                                        new RpcHandler(
                                                                "{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1599985655,\"Code\":22,\"Msg\":\"CGMiner versions\",\"Description\":\"cgminer 4.9.0\"}],\"VERSION\":[{\"CGMiner\":\"4.9.0\",\"API\":\"3.1\",\"Miner\":\"9.0.0.5\",\"CompileTime\":\"Sat May 26 20:42:30 CST 2018\",\"Type\":\"Antminer Z9-Mini\"}],\"id\":1}")))),
                                AntminerType.ANTMINER_Z9M
                        },
                        {
                                // Braiins OS S9
                                Collections.singletonList(
                                        (Supplier<FakeMinerServer>) () -> new FakeRpcMinerServer(
                                                4028,
                                                ImmutableMap.of(
                                                        "{\"command\":\"version\"}",
                                                        new RpcHandler(
                                                                "{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1545884756,\"Code\":22,\"Msg\":\"BMMiner versions\",\"Description\":\"bmminer bOS_am1-s9-20181127-0_c34516b0\"}],\"VERSION\":[{\"BMMiner\":\"bOS_am1-s9-20181127-0_c34516b0\",\"API\":\"3.1\",\"Miner\":\"bOS_am1-s9-20181127-0_c34516b0\",\"CompileTime\":\"\",\"Type\":\"braiins-am1-s9\"}],\"id\":1}")))),
                                AntminerType.BRAIINS_S9
                        },
                        {
                                // Braiins OS+ S9
                                Collections.singletonList(
                                        (Supplier<FakeMinerServer>) () -> new FakeRpcMinerServer(
                                                4028,
                                                ImmutableMap.of(
                                                        "{\"command\":\"version\"}",
                                                        new RpcHandler(
                                                                "{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1597092539,\"Code\":22,\"Msg\":\"BOSminer+ versions\",\"Description\":\"BOSminer+ 0.2.0-36c56a9363\"}],\"VERSION\":[{\"API\":\"3.7\",\"BOSminer+\":\"0.2.0-36c56a9363\"}],\"id\":1}")))),
                                AntminerType.BRAIINS_S9_BOSP
                        },
                        {
                                // Braiins OS S9
                                Collections.singletonList(
                                        (Supplier<FakeMinerServer>) () -> new FakeRpcMinerServer(
                                                4028,
                                                ImmutableMap.of(
                                                        "{\"command\":\"version\"}",
                                                        new RpcHandler(
                                                                "{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1597196862,\"Code\":22,\"Msg\":\"BOSminer versions\",\"Description\":\"BOSminer 0.2.0-d360818\"}],\"VERSION\":[{\"API\":\"3.7\",\"BOSminer\":\"0.2.0-d360818\"}],\"id\":1}")))),
                                AntminerType.BRAIINS_S9_BOS
                        },
                        {
                                // Hiveon S9
                                Collections.singletonList(
                                        (Supplier<FakeMinerServer>) () -> new FakeRpcMinerServer(
                                                4028,
                                                ImmutableMap.of(
                                                        "{\"command\":\"version\"}",
                                                        new RpcHandler(
                                                                "{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1600293257,\"Code\":22,\"Msg\":\"BMMiner versions\",\"Description\":\"bmminer 1.0.0\"}],\"VERSION\":[{\"BMMiner\":\"2.0.0 rwglr\",\"API\":\"3.1\",\"Miner\":\"30.0.1.3\",\"CompileTime\":\"Tue Nov 20 10:12:30 UTC 2019\",\"Type\":\"Antminer S9 Hiveon\"}],\"id\":1}")))),
                                AntminerType.ANTMINER_S9
                        },
                        {
                                // NiceHash
                                Collections.singletonList(
                                        (Supplier<FakeMinerServer>) () -> new FakeRpcMinerServer(
                                                4028,
                                                ImmutableMap.of(
                                                        "{\"command\":\"version\"}",
                                                        new RpcHandler(
                                                                "{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1600299177,\"Code\":22,\"Msg\":\"CGMiner versions\",\"Description\":\"cgminer 4.11.1\"}],\"VERSION\":[{\"CGMiner\":\"4.11.1\",\"API\":\"3.7\",\"Miner\":\"26.0.1.3\",\"CompileTime\":\"Fri May  8 15:27:59 CST 2020\",\"Type\":\"Antminer S9 (vnish 3.8.6)\"}],\"id\":1}")))),
                                AntminerType.ANTMINER_S9
                        }
                });
    }
}