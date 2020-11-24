package mn.foreman.antminer;

import mn.foreman.antminer.util.AntminerTestUtils;
import mn.foreman.model.Detection;
import mn.foreman.util.AbstractDetectITest;
import mn.foreman.util.FakeMinerServer;
import mn.foreman.util.http.FakeHttpMinerServer;
import mn.foreman.util.http.HttpHandler;
import mn.foreman.util.rpc.FakeRpcMinerServer;
import mn.foreman.util.rpc.RpcHandler;

import com.google.common.collect.ImmutableMap;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.*;
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
     * @param args         The args.
     * @param hostname     The hostname.
     */
    public AntminerDetectITest(
            final List<Supplier<FakeMinerServer>> servers,
            final AntminerType expectedType,
            final Map<String, Object> args,
            final String hostname) {
        super(
                new AntminerDetectionStrategy("antMiner Configuration"),
                servers,
                args,
                Detection.builder()
                        .minerType(expectedType)
                        .ipAddress("127.0.0.1")
                        .port(4028)
                        .parameters(hostname != null ? toHostnameArgs(hostname) : args)
                        .build(),
                (integer, stringObjectMap) -> stringObjectMap);
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
                                AntminerType.ANTMINER_B3,
                                DEFAULT_ARGS,
                                null
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
                                AntminerType.ANTMINER_L3,
                                DEFAULT_ARGS,
                                null
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
                                AntminerType.ANTMINER_S9,
                                DEFAULT_ARGS,
                                null
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
                                AntminerType.ANTMINER_S17,
                                DEFAULT_ARGS,
                                null
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
                                AntminerType.ANTMINER_S17P,
                                DEFAULT_ARGS,
                                null
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
                                AntminerType.ANTMINER_S19_PRO,
                                DEFAULT_ARGS,
                                null
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
                                AntminerType.ANTMINER_Z9,
                                DEFAULT_ARGS,
                                null
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
                                AntminerType.ANTMINER_Z9M,
                                DEFAULT_ARGS,
                                null
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
                                AntminerType.BRAIINS_S9,
                                DEFAULT_ARGS,
                                null
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
                                AntminerType.BRAIINS_S9_BOSP,
                                DEFAULT_ARGS,
                                null
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
                                AntminerType.BRAIINS_S9_BOS,
                                DEFAULT_ARGS,
                                null
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
                                AntminerType.ANTMINER_S9,
                                DEFAULT_ARGS,
                                null
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
                                AntminerType.ANTMINER_S9,
                                DEFAULT_ARGS,
                                null
                        },
                        {
                                // Hostname preferred (L3)
                                Arrays.asList(
                                        (Supplier<FakeMinerServer>) () -> new FakeRpcMinerServer(
                                                4028,
                                                ImmutableMap.of(
                                                        "{\"command\":\"version\"}",
                                                        new RpcHandler(
                                                                "{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1526315502,\"Code\":22,\"Msg\":\"CGMiner versions\",\"Description\":\"cgminer 4.9.0\"}],\"VERSION\":[{\"CGMiner\":\"4.9.0\",\"API\":\"3.1\",\"Miner\":\"1.0.1.3\",\"CompileTime\":\"Fri Aug 25 17:28:57 CST 2017\",\"Type\":\"Antminer L3+\"}],\"id\":1}"))),
                                        () -> new FakeHttpMinerServer(
                                                8080,
                                                ImmutableMap.of(
                                                        "/cgi-bin/get_system_info.cgi",
                                                        new HttpHandler(
                                                                "",
                                                                "{\n" +
                                                                        "\"minertype\":\"Antminer L3+\",\n" +
                                                                        "\"nettype\":\"DHCP\",\n" +
                                                                        "\"netdevice\":\"eth0\",\n" +
                                                                        "\"macaddr\":\"C4:F3:12:B3:9F:FC\",\n" +
                                                                        "\"hostname\":\"antMiner\",\n" +
                                                                        "\"ipaddress\":\"192.168.1.189\",\n" +
                                                                        "\"netmask\":\"255.255.255.0\",\n" +
                                                                        "\"gateway\":\"\",\n" +
                                                                        "\"dnsservers\":\"\",\n" +
                                                                        "\"curtime\":\"01:15:51\",\n" +
                                                                        "\"uptime\":\"1\",\n" +
                                                                        "\"loadaverage\":\"0.50, 0.18, 0.06\",\n" +
                                                                        "\"mem_total\":\"251180\",\n" +
                                                                        "\"mem_used\":\"48680\",\n" +
                                                                        "\"mem_free\":\"202500\",\n" +
                                                                        "\"mem_buffers\":\"0\",\n" +
                                                                        "\"mem_cached\":\"0\",\n" +
                                                                        "\"system_mode\":\"GNU/Linux\",\n" +
                                                                        "\"ant_hwv\":\"1.0.1.3\",\n" +
                                                                        "\"system_kernel_version\":\"Linux 3.8.13 #22 SMP Tue Dec 2 15:26:11 CST 2014\",\n" +
                                                                        "\"system_filesystem_version\":\"Fri Aug 25 17:28:57 CST 2017\",\n" +
                                                                        "\"cgminer_version\":\"4.9.0\"\n" +
                                                                        "}",
                                                                exchange -> AntminerTestUtils.validateDigest(
                                                                        exchange,
                                                                        "antMiner Configuration"))))),
                                AntminerType.ANTMINER_L3,
                                toHostnameArgs(null),
                                "antMiner"
                        }
                });
    }

    /**
     * Creates test arguments to use a hostname.
     *
     * @param hostname The hostname.
     *
     * @return The test args.
     */
    private static Map<String, Object> toHostnameArgs(final String hostname) {
        final Map<String, Object> args = new HashMap<>(DEFAULT_ARGS);
        args.put("hostname_preferred", "true");
        if (hostname != null) {
            args.put("hostname", hostname);
        }
        args.put("webPort", "8080");
        return args;
    }
}