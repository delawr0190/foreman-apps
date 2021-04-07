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

import java.math.BigDecimal;
import java.util.*;
import java.util.function.Supplier;

/** Tests antminer detection. */
@RunWith(Parameterized.class)
public class AntminerDetectITest
        extends AbstractDetectITest {

    /**
     * Constructor.
     *
     * @param servers           The servers.
     * @param expectedType      The expected type.
     * @param args              The args.
     * @param hostnamePreferred Whether or not hostname is preferred.
     * @param hostname          The hostname.
     * @param worker            The worker.
     */
    public AntminerDetectITest(
            final List<Supplier<FakeMinerServer>> servers,
            final AntminerType expectedType,
            final Map<String, Object> args,
            final boolean hostnamePreferred,
            final String hostname,
            final String worker) {
        super(
                new AntminerDetectionStrategy(
                        "antMiner Configuration",
                        Arrays.asList(
                                new StockMacStrategy(
                                        "127.0.0.1",
                                        8080,
                                        "antMiner Configuration",
                                        "root",
                                        "root"),
                                new BraiinsMacStrategy(
                                        "127.0.0.1",
                                        8080,
                                        "root",
                                        "root")),
                        Arrays.asList(
                                new StockHostnameStrategy(
                                        "antMiner Configuration"),
                                new BraiinsHostnameStrategy()),
                        new AntminerFactory(BigDecimal.ONE).create(
                                ImmutableMap.of(
                                        "apiIp",
                                        "127.0.0.1",
                                        "apiPort",
                                        "4028"))),
                servers,
                args,
                Detection.builder()
                        .minerType(expectedType)
                        .ipAddress("127.0.0.1")
                        .port(4028)
                        .parameters(hostname != null || worker != null
                                ? toHostnameArgs(
                                hostnamePreferred,
                                hostname,
                                worker)
                                : args)
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
                                false,
                                null,
                                null
                        },
                        {
                                // Antminer L3+
                                Collections.singletonList(
                                        (Supplier<FakeMinerServer>) () -> new FakeRpcMinerServer(
                                                4028,
                                                ImmutableMap.of(
                                                        "{\"command\":\"version\"}",
                                                        new RpcHandler(
                                                                "{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1526315502,\"Code\":22,\"Msg\":\"CGMiner versions\",\"Description\":\"cgminer 4.9.0\"}],\"VERSION\":[{\"CGMiner\":\"4.9.0\",\"API\":\"3.1\",\"Miner\":\"1.0.1.3\",\"CompileTime\":\"Fri Aug 25 17:28:57 CST 2017\",\"Type\":\"Antminer L3+\"}],\"id\":1}")))),
                                AntminerType.ANTMINER_L3P,
                                DEFAULT_ARGS,
                                false,
                                null,
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
                                false,
                                null,
                                null
                        },
                        {
                                // Antminer S17 Pro
                                Collections.singletonList(
                                        (Supplier<FakeMinerServer>) () -> new FakeRpcMinerServer(
                                                4028,
                                                ImmutableMap.of(
                                                        "{\"command\":\"version\"}",
                                                        new RpcHandler(
                                                                "{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1576765845,\"Code\":22,\"Msg\":\"CGMiner versions\",\"Description\":\"cgminer 1.0.0\"}],\"VERSION\":[{\"BMMiner\":\"1.0.0\",\"API\":\"3.1\",\"Miner\":\"19.10.1.3\",\"CompileTime\":\"Tue Aug 20 10:37:07 CST 2019\",\"Type\":\"Antminer S17 Pro\"}],\"id\":1}")))),
                                AntminerType.ANTMINER_S17_PRO,
                                DEFAULT_ARGS,
                                false,
                                null,
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
                                false,
                                null,
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
                                false,
                                null,
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
                                false,
                                null,
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
                                false,
                                null,
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
                                false,
                                null,
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
                                false,
                                null,
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
                                false,
                                null,
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
                                false,
                                null,
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
                                false,
                                null,
                                null
                        },
                        {
                                // Hostname preferred (L3+)
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
                                AntminerType.ANTMINER_L3P,
                                toHostnameArgs(
                                        true,
                                        null,
                                        null),
                                true,
                                "antMiner",
                                null
                        },
                        {
                                // Worker preferred (L3+)
                                Collections.singletonList(
                                        (Supplier<FakeMinerServer>) () -> new FakeRpcMinerServer(
                                                4028,
                                                ImmutableMap.of(
                                                        "{\"command\":\"version\"}",
                                                        new RpcHandler(
                                                                "{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1526315502,\"Code\":22,\"Msg\":\"CGMiner versions\",\"Description\":\"cgminer 4.9.0\"}],\"VERSION\":[{\"CGMiner\":\"4.9.0\",\"API\":\"3.1\",\"Miner\":\"1.0.1.3\",\"CompileTime\":\"Fri Aug 25 17:28:57 CST 2017\",\"Type\":\"Antminer L3+\"}],\"id\":1}"),
                                                        "{\"command\":\"stats\"}",
                                                        new RpcHandler(
                                                                "{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1526315384,\"Code\":70,\"Msg\":\"CGMiner stats\",\"Description\":\"cgminer 4.9.0\"}],\"STATS\":[{\"CGMiner\":\"4.9.0\",\"Miner\":\"1.0.1.3\",\"CompileTime\":\"Fri Aug 25 17:28:57 CST 2017\",\"Type\":\"Antminer L3+\"}{\"STATS\":0,\"ID\":\"L30\",\"Elapsed\":393983,\"Calls\":0,\"Wait\":0.000000,\"Max\":0.000000,\"Min\":99999999.000000,\"GHS 5s\":\"504.56\",\"GHS av\":500.28,\"miner_count\":4,\"frequency\":\"384\",\"fan_num\":2,\"fan1\":3840,\"fan2\":3810,\"temp_num\":4,\"temp1\":46,\"temp2\":46,\"temp3\":46,\"temp4\":45,\"temp2_1\":54,\"temp2_2\":56,\"temp2_3\":56,\"temp2_4\":53,\"temp31\":0,\"temp32\":0,\"temp33\":0,\"temp34\":0,\"temp4_1\":0,\"temp4_2\":0,\"temp4_3\":0,\"temp4_4\":0,\"temp_max\":46,\"Device Hardware%\":0.0000,\"no_matching_work\":152,\"chain_acn1\":72,\"chain_acn2\":72,\"chain_acn3\":72,\"chain_acn4\":72,\"chain_acs1\":\" oooooooo oooooooo oooooooo oooooooo oooooooo oooooooo oooooooo oooooooo oooooooo\",\"chain_acs2\":\" oooooooo oooooooo oooooooo oooooooo oooooooo oooooooo oooooooo oooooooo oooooooo\",\"chain_acs3\":\" oooooooo oooooooo oooooooo oooooooo oooooooo oooooooo oooooooo oooooooo oooooooo\",\"chain_acs4\":\" oooooooo oooooooo oooooooo oooooooo oooooooo oooooooo oooooooo oooooooo oooooooo\",\"chain_hw1\":0,\"chain_hw2\":0,\"chain_hw3\":153,\"chain_hw4\":0,\"chain_rate1\":\"126.12\",\"chain_rate2\":\"125.79\",\"chain_rate3\":\"126.06\",\"chain_rate4\":\"126.59\"}],\"id\":1}"),
                                                        "{\"command\":\"pools\"}",
                                                        new RpcHandler(
                                                                "{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1526315222,\"Code\":7,\"Msg\":\"3 Pool(s)\",\"Description\":\"cgminer 4.9.0\"}],\"POOLS\":[{\"POOL\":0,\"URL\":\"stratum+tcp://us.litecoinpool.org:3333\",\"Status\":\"Alive\",\"Priority\":0,\"Quota\":1,\"Long Poll\":\"N\",\"Getworks\":24933,\"Accepted\":47384,\"Rejected\":212,\"Discarded\":230740,\"Stale\":15,\"Get Failures\":1,\"Remote Failures\":0,\"User\":\"obmllc.l3_1\",\"Last Share Time\":\"0:00:23\",\"Diff\":\"65.5K\",\"Diff1 Shares\":11805080,\"Proxy Type\":\"\",\"Proxy\":\"\",\"Difficulty Accepted\":2988769280.00000000,\"Difficulty Rejected\":13254656.00000000,\"Difficulty Stale\":0.00000000,\"Last Share Difficulty\":65536.00000000,\"Has Stratum\":true,\"Stratum Active\":true,\"Stratum URL\":\"us.litecoinpool.org\",\"Has GBT\":false,\"Best Share\":11224839560,\"Pool Rejected%\":0.4415,\"Pool Stale%\":0.0000},{\"POOL\":1,\"URL\":\"\",\"Status\":\"Dead\",\"Priority\":1,\"Quota\":1,\"Long Poll\":\"N\",\"Getworks\":0,\"Accepted\":0,\"Rejected\":0,\"Discarded\":0,\"Stale\":0,\"Get Failures\":0,\"Remote Failures\":0,\"User\":\"\",\"Last Share Time\":\"0\",\"Diff\":\"\",\"Diff1 Shares\":0,\"Proxy Type\":\"\",\"Proxy\":\"\",\"Difficulty Accepted\":0.00000000,\"Difficulty Rejected\":0.00000000,\"Difficulty Stale\":0.00000000,\"Last Share Difficulty\":0.00000000,\"Has Stratum\":false,\"Stratum Active\":false,\"Stratum URL\":\"\",\"Has GBT\":false,\"Best Share\":0,\"Pool Rejected%\":0.0000,\"Pool Stale%\":0.0000},{\"POOL\":2,\"URL\":\"\",\"Status\":\"Dead\",\"Priority\":2,\"Quota\":1,\"Long Poll\":\"N\",\"Getworks\":0,\"Accepted\":0,\"Rejected\":0,\"Discarded\":0,\"Stale\":0,\"Get Failures\":0,\"Remote Failures\":0,\"User\":\"\",\"Last Share Time\":\"0\",\"Diff\":\"\",\"Diff1 Shares\":0,\"Proxy Type\":\"\",\"Proxy\":\"\",\"Difficulty Accepted\":0.00000000,\"Difficulty Rejected\":0.00000000,\"Difficulty Stale\":0.00000000,\"Last Share Difficulty\":0.00000000,\"Has Stratum\":false,\"Stratum Active\":false,\"Stratum URL\":\"\",\"Has GBT\":false,\"Best Share\":0,\"Pool Rejected%\":0.0000,\"Pool Stale%\":0.0000}],\"id\":1}")))),
                                AntminerType.ANTMINER_L3P,
                                toHostnameArgs(
                                        false,
                                        null,
                                        null),
                                false,
                                null,
                                "obmllc.l3_1"
                        },
                        {
                                // Hostname preferred (L3+)
                                Arrays.asList(
                                        (Supplier<FakeMinerServer>) () -> new FakeRpcMinerServer(
                                                4028,
                                                ImmutableMap.of(
                                                        "{\"command\":\"version\"}",
                                                        new RpcHandler(
                                                                "{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1617801219,\"Code\":22,\"Msg\":\"BMMiner versions\",\"Description\":\"bmminer 2.4.2-1.2.2-90ed\"}],\"VERSION\":[{\"BMMiner\":\"2.4.2-1.2.2-90ed\",\"Miner\":\"30.0.1.3\",\"CompileTime\":\"Wed Nov 7 11:19:02 CST 2018\",\"Type\":\"Antminer S9i\",\"API\":\"3.1\"}],\"id\":1}"))),
                                        () -> new FakeHttpMinerServer(
                                                8080,
                                                ImmutableMap.of(
                                                        "/cgi-bin/get_network_info.cgi",
                                                        new HttpHandler(
                                                                "",
                                                                "<?xml version=\"1.0\" encoding=\"iso-8859-1\"?>\n" +
                                                                        "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\"\n" +
                                                                        "         \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n" +
                                                                        "<html xmlns=\"http://www.w3.org/1999/xhtml\" xml:lang=\"en\" lang=\"en\">\n" +
                                                                        " <head>\n" +
                                                                        "  <title>401 - Unauthorized</title>\n" +
                                                                        " </head>\n" +
                                                                        " <body>\n" +
                                                                        "  <h1>401 - Unauthorized</h1>\n" +
                                                                        " </body>\n" +
                                                                        "</html>",
                                                                exchange -> AntminerTestUtils.validateDigest(
                                                                        exchange,
                                                                        "antMiner Configuration"))))),
                                AntminerType.ANTMINER_S9I,
                                toHostnameArgs(
                                        false,
                                        null,
                                        null),
                                false,
                                null,
                                null
                        },
                });
    }

    /**
     * Creates test arguments to use a hostname.
     *
     * @param hostnamePreferred Whether or not hostname is preferred.
     * @param hostname          The hostname.
     * @param worker            The worker.
     *
     * @return The test args.
     */
    private static Map<String, Object> toHostnameArgs(
            final boolean hostnamePreferred,
            final String hostname,
            final String worker) {
        final Map<String, Object> args = new HashMap<>(DEFAULT_ARGS);
        if (hostnamePreferred) {
            args.put("hostnamePreferred", "true");
        } else {
            args.put("workerPreferred", "true");
        }
        if (hostname != null) {
            args.put("hostname", hostname);
        } else if (worker != null) {
            args.put("worker", worker);
        }
        args.put("webPort", "8080");
        return args;
    }
}