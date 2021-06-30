package mn.foreman.blackminer;

import mn.foreman.cgminer.CgMinerDetectionStrategy;
import mn.foreman.cgminer.request.CgMinerCommand;
import mn.foreman.model.ApplicationConfiguration;
import mn.foreman.model.Detection;
import mn.foreman.util.AbstractDetectITest;
import mn.foreman.util.rpc.FakeRpcMinerServer;
import mn.foreman.util.rpc.HandlerInterface;
import mn.foreman.util.rpc.RpcHandler;

import com.google.common.collect.ImmutableMap;
import one.util.streamex.EntryStream;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/** Tests detection of blackminer miners. */
@RunWith(Parameterized.class)
public class BlackminerDetectITest
        extends AbstractDetectITest {

    /**
     * Constructor.
     *
     * @param handlers       The handlers.
     * @param expectedType   The expected type.
     * @param additionalArgs The additional arguments.
     * @param detectionArgs  The detection args.
     */
    public BlackminerDetectITest(
            final Map<String, HandlerInterface> handlers,
            final BlackminerType expectedType,
            final Map<String, Object> additionalArgs,
            final Map<String, Object> detectionArgs) {
        super(
                new CgMinerDetectionStrategy(
                        CgMinerCommand.VERSION,
                        new BlackminerTypeFactory(),
                        new ApplicationConfiguration()),
                Collections.singletonList(
                        () -> new FakeRpcMinerServer(
                                4028,
                                handlers)),
                EntryStream
                        .of(additionalArgs)
                        .append(DEFAULT_ARGS)
                        .toMap(),
                Detection.builder()
                        .minerType(expectedType)
                        .ipAddress("127.0.0.1")
                        .port(4028)
                        .parameters(detectionArgs)
                        .build(),
                (integer, stringObjectMap) -> stringObjectMap);
    }

    /**
     * Test parameters
     *
     * @return The test parameters.
     */
    @Parameterized.Parameters
    public static List<Object[]> parameters() {
        return Arrays.asList(
                new Object[][]{
                        {
                                // Blackminer F1
                                ImmutableMap.of(
                                        "{\"command\":\"version\"}",
                                        new RpcHandler(
                                                "{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1544107423,\"Code\":70,\"Msg\":\"CGMiner stats\",\"Description\":\"ccminer 2.3.3\"}],\"VERSION\":[{\"CGMiner\":\"2.3.3\",\"Miner\":\"1.3.0.8\",\"CompileTime\":\"2018-11-19 20-59-09 CST\",\"Type\":\"Blackminer F1\"}],\"id\":1}"),
                                        "{\"command\":\"pools\"}",
                                        new RpcHandler(
                                                "{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1593395695,\"Code\":7,\"Msg\":\"3 Pool(s)\",\"Description\":\"ccminer 2.3.3\"}],\"POOLS\":[{\"POOL\":0,\"URL\":\"xxx:8080\",\"Status\":\"Alive\",\"Priority\":0,\"Quota\":0,\"Long Poll\":\"N\",\"Getworks\":820,\"Accepted\":906,\"Rejected\":6,\"Discarded\":12216,\"Stale\":0,\"Get Failures\":0,\"Remote Failures\":0,\"User\":\"xxx\",\"Last Share Time\":\"0:00:08\",\"Diff\":\"  8.0000\",\"Diff1 Shares\":7407,\"Proxy Type\":\"\",\"Proxy\":\"\",\"Difficulty Accepted\":7296.00000000,\"Difficulty Rejected\":48.00000000,\"Difficulty Stale\":0.00000000,\"Last Share Difficulty\":8.00000000,\"Has Stratum\":true,\"Stratum Active\":true,\"Stratum URL\":\"xxx.com\",\"Has GBT\":false,\"Best Share\":\"  0.0000\",\"Pool Rejected%\":0.6536,\"Pool Stale%\":0.0000},{\"POOL\":1,\"URL\":\"\",\"Status\":\"Dead\",\"Priority\":1,\"Quota\":0,\"Long Poll\":\"N\",\"Getworks\":0,\"Accepted\":0,\"Rejected\":0,\"Discarded\":0,\"Stale\":0,\"Get Failures\":0,\"Remote Failures\":0,\"User\":\"\",\"Last Share Time\":\"0\",\"Diff\":\"  0.0000\",\"Diff1 Shares\":0,\"Proxy Type\":\"\",\"Proxy\":\"\",\"Difficulty Accepted\":0.00000000,\"Difficulty Rejected\":0.00000000,\"Difficulty Stale\":0.00000000,\"Last Share Difficulty\":0.00000000,\"Has Stratum\":false,\"Stratum Active\":false,\"Stratum URL\":\"\",\"Has GBT\":false,\"Best Share\":\"  0.0000\",\"Pool Rejected%\":0.0000,\"Pool Stale%\":0.0000},{\"POOL\":2,\"URL\":\"\",\"Status\":\"Dead\",\"Priority\":2,\"Quota\":0,\"Long Poll\":\"N\",\"Getworks\":0,\"Accepted\":0,\"Rejected\":0,\"Discarded\":0,\"Stale\":0,\"Get Failures\":0,\"Remote Failures\":0,\"User\":\"\",\"Last Share Time\":\"0\",\"Diff\":\"  0.0000\",\"Diff1 Shares\":0,\"Proxy Type\":\"\",\"Proxy\":\"\",\"Difficulty Accepted\":0.00000000,\"Difficulty Rejected\":0.00000000,\"Difficulty Stale\":0.00000000,\"Last Share Difficulty\":0.00000000,\"Has Stratum\":false,\"Stratum Active\":false,\"Stratum URL\":\"\",\"Has GBT\":false,\"Best Share\":\"  0.0000\",\"Pool Rejected%\":0.0000,\"Pool Stale%\":0.0000}],\"id\":1}")),
                                BlackminerType.BLACKMINER_F1,
                                Collections.emptyMap(),
                                DEFAULT_ARGS
                        },
                        {
                                // Blackminer F1 Mini
                                ImmutableMap.of(
                                        "{\"command\":\"version\"}",
                                        new RpcHandler(
                                                "{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1593395696,\"Code\":22,\"Msg\":\"CGMiner versions\",\"Description\":\"ccminer 2.3.3\"}],\"VERSION\":[{\"CGMiner\":\"2.3.3\",\"API\":\"3.1\",\"Miner\":\"1.3.0.8\",\"CompileTime\":\"2020-06-12 16-01-07 CST\",\"Type\":\"Blackminer F1-MINI\"}],\"id\":1}"),
                                        "{\"command\":\"pools\"}",
                                        new RpcHandler(
                                                "{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1593395695,\"Code\":7,\"Msg\":\"3 Pool(s)\",\"Description\":\"ccminer 2.3.3\"}],\"POOLS\":[{\"POOL\":0,\"URL\":\"xxx:8080\",\"Status\":\"Alive\",\"Priority\":0,\"Quota\":0,\"Long Poll\":\"N\",\"Getworks\":820,\"Accepted\":906,\"Rejected\":6,\"Discarded\":12216,\"Stale\":0,\"Get Failures\":0,\"Remote Failures\":0,\"User\":\"xxx\",\"Last Share Time\":\"0:00:08\",\"Diff\":\"  8.0000\",\"Diff1 Shares\":7407,\"Proxy Type\":\"\",\"Proxy\":\"\",\"Difficulty Accepted\":7296.00000000,\"Difficulty Rejected\":48.00000000,\"Difficulty Stale\":0.00000000,\"Last Share Difficulty\":8.00000000,\"Has Stratum\":true,\"Stratum Active\":true,\"Stratum URL\":\"xxx.com\",\"Has GBT\":false,\"Best Share\":\"  0.0000\",\"Pool Rejected%\":0.6536,\"Pool Stale%\":0.0000},{\"POOL\":1,\"URL\":\"\",\"Status\":\"Dead\",\"Priority\":1,\"Quota\":0,\"Long Poll\":\"N\",\"Getworks\":0,\"Accepted\":0,\"Rejected\":0,\"Discarded\":0,\"Stale\":0,\"Get Failures\":0,\"Remote Failures\":0,\"User\":\"\",\"Last Share Time\":\"0\",\"Diff\":\"  0.0000\",\"Diff1 Shares\":0,\"Proxy Type\":\"\",\"Proxy\":\"\",\"Difficulty Accepted\":0.00000000,\"Difficulty Rejected\":0.00000000,\"Difficulty Stale\":0.00000000,\"Last Share Difficulty\":0.00000000,\"Has Stratum\":false,\"Stratum Active\":false,\"Stratum URL\":\"\",\"Has GBT\":false,\"Best Share\":\"  0.0000\",\"Pool Rejected%\":0.0000,\"Pool Stale%\":0.0000},{\"POOL\":2,\"URL\":\"\",\"Status\":\"Dead\",\"Priority\":2,\"Quota\":0,\"Long Poll\":\"N\",\"Getworks\":0,\"Accepted\":0,\"Rejected\":0,\"Discarded\":0,\"Stale\":0,\"Get Failures\":0,\"Remote Failures\":0,\"User\":\"\",\"Last Share Time\":\"0\",\"Diff\":\"  0.0000\",\"Diff1 Shares\":0,\"Proxy Type\":\"\",\"Proxy\":\"\",\"Difficulty Accepted\":0.00000000,\"Difficulty Rejected\":0.00000000,\"Difficulty Stale\":0.00000000,\"Last Share Difficulty\":0.00000000,\"Has Stratum\":false,\"Stratum Active\":false,\"Stratum URL\":\"\",\"Has GBT\":false,\"Best Share\":\"  0.0000\",\"Pool Rejected%\":0.0000,\"Pool Stale%\":0.0000}],\"id\":1}")),
                                BlackminerType.BLACKMINER_F1_MINI,
                                Collections.emptyMap(),
                                DEFAULT_ARGS
                        },
                        {
                                // Blackminer F1 Ultra
                                ImmutableMap.of(
                                        "{\"command\":\"version\"}",
                                        new RpcHandler(
                                                "{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1590413031,\"Code\":22,\"Msg\":\"CGMiner versions\",\"Description\":\"ccminer 2.3.3\"}],\"VERSION\":[{\"CGMiner\":\"2.3.3\",\"API\":\"3.1\",\"Miner\":\"1.3.0.8\",\"CompileTime\":\"2020-04-05 16-27-23 CST\",\"Type\":\"Blackminer F1-ULTRA\"}],\"id\":1}"),
                                        "{\"command\":\"pools\"}",
                                        new RpcHandler(
                                                "{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1593395695,\"Code\":7,\"Msg\":\"3 Pool(s)\",\"Description\":\"ccminer 2.3.3\"}],\"POOLS\":[{\"POOL\":0,\"URL\":\"xxx:8080\",\"Status\":\"Alive\",\"Priority\":0,\"Quota\":0,\"Long Poll\":\"N\",\"Getworks\":820,\"Accepted\":906,\"Rejected\":6,\"Discarded\":12216,\"Stale\":0,\"Get Failures\":0,\"Remote Failures\":0,\"User\":\"xxx\",\"Last Share Time\":\"0:00:08\",\"Diff\":\"  8.0000\",\"Diff1 Shares\":7407,\"Proxy Type\":\"\",\"Proxy\":\"\",\"Difficulty Accepted\":7296.00000000,\"Difficulty Rejected\":48.00000000,\"Difficulty Stale\":0.00000000,\"Last Share Difficulty\":8.00000000,\"Has Stratum\":true,\"Stratum Active\":true,\"Stratum URL\":\"xxx.com\",\"Has GBT\":false,\"Best Share\":\"  0.0000\",\"Pool Rejected%\":0.6536,\"Pool Stale%\":0.0000},{\"POOL\":1,\"URL\":\"\",\"Status\":\"Dead\",\"Priority\":1,\"Quota\":0,\"Long Poll\":\"N\",\"Getworks\":0,\"Accepted\":0,\"Rejected\":0,\"Discarded\":0,\"Stale\":0,\"Get Failures\":0,\"Remote Failures\":0,\"User\":\"\",\"Last Share Time\":\"0\",\"Diff\":\"  0.0000\",\"Diff1 Shares\":0,\"Proxy Type\":\"\",\"Proxy\":\"\",\"Difficulty Accepted\":0.00000000,\"Difficulty Rejected\":0.00000000,\"Difficulty Stale\":0.00000000,\"Last Share Difficulty\":0.00000000,\"Has Stratum\":false,\"Stratum Active\":false,\"Stratum URL\":\"\",\"Has GBT\":false,\"Best Share\":\"  0.0000\",\"Pool Rejected%\":0.0000,\"Pool Stale%\":0.0000},{\"POOL\":2,\"URL\":\"\",\"Status\":\"Dead\",\"Priority\":2,\"Quota\":0,\"Long Poll\":\"N\",\"Getworks\":0,\"Accepted\":0,\"Rejected\":0,\"Discarded\":0,\"Stale\":0,\"Get Failures\":0,\"Remote Failures\":0,\"User\":\"\",\"Last Share Time\":\"0\",\"Diff\":\"  0.0000\",\"Diff1 Shares\":0,\"Proxy Type\":\"\",\"Proxy\":\"\",\"Difficulty Accepted\":0.00000000,\"Difficulty Rejected\":0.00000000,\"Difficulty Stale\":0.00000000,\"Last Share Difficulty\":0.00000000,\"Has Stratum\":false,\"Stratum Active\":false,\"Stratum URL\":\"\",\"Has GBT\":false,\"Best Share\":\"  0.0000\",\"Pool Rejected%\":0.0000,\"Pool Stale%\":0.0000}],\"id\":1}")),
                                BlackminerType.BLACKMINER_F1_ULTRA,
                                Collections.emptyMap(),
                                DEFAULT_ARGS
                        },
                        {
                                // Blackminer (unknown)
                                ImmutableMap.of(
                                        "{\"command\":\"version\"}",
                                        new RpcHandler(
                                                "{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1590413031,\"Code\":22,\"Msg\":\"CGMiner versions\",\"Description\":\"ccminer 2.3.3\"}],\"VERSION\":[{\"CGMiner\":\"2.3.3\",\"API\":\"3.1\",\"Miner\":\"1.3.0.8\",\"CompileTime\":\"2020-04-05 16-27-23 CST\",\"Type\":\"Blackminer F1-u\"}],\"id\":1}"),
                                        "{\"command\":\"pools\"}",
                                        new RpcHandler(
                                                "{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1593395695,\"Code\":7,\"Msg\":\"3 Pool(s)\",\"Description\":\"ccminer 2.3.3\"}],\"POOLS\":[{\"POOL\":0,\"URL\":\"xxx:8080\",\"Status\":\"Alive\",\"Priority\":0,\"Quota\":0,\"Long Poll\":\"N\",\"Getworks\":820,\"Accepted\":906,\"Rejected\":6,\"Discarded\":12216,\"Stale\":0,\"Get Failures\":0,\"Remote Failures\":0,\"User\":\"xxx\",\"Last Share Time\":\"0:00:08\",\"Diff\":\"  8.0000\",\"Diff1 Shares\":7407,\"Proxy Type\":\"\",\"Proxy\":\"\",\"Difficulty Accepted\":7296.00000000,\"Difficulty Rejected\":48.00000000,\"Difficulty Stale\":0.00000000,\"Last Share Difficulty\":8.00000000,\"Has Stratum\":true,\"Stratum Active\":true,\"Stratum URL\":\"xxx.com\",\"Has GBT\":false,\"Best Share\":\"  0.0000\",\"Pool Rejected%\":0.6536,\"Pool Stale%\":0.0000},{\"POOL\":1,\"URL\":\"\",\"Status\":\"Dead\",\"Priority\":1,\"Quota\":0,\"Long Poll\":\"N\",\"Getworks\":0,\"Accepted\":0,\"Rejected\":0,\"Discarded\":0,\"Stale\":0,\"Get Failures\":0,\"Remote Failures\":0,\"User\":\"\",\"Last Share Time\":\"0\",\"Diff\":\"  0.0000\",\"Diff1 Shares\":0,\"Proxy Type\":\"\",\"Proxy\":\"\",\"Difficulty Accepted\":0.00000000,\"Difficulty Rejected\":0.00000000,\"Difficulty Stale\":0.00000000,\"Last Share Difficulty\":0.00000000,\"Has Stratum\":false,\"Stratum Active\":false,\"Stratum URL\":\"\",\"Has GBT\":false,\"Best Share\":\"  0.0000\",\"Pool Rejected%\":0.0000,\"Pool Stale%\":0.0000},{\"POOL\":2,\"URL\":\"\",\"Status\":\"Dead\",\"Priority\":2,\"Quota\":0,\"Long Poll\":\"N\",\"Getworks\":0,\"Accepted\":0,\"Rejected\":0,\"Discarded\":0,\"Stale\":0,\"Get Failures\":0,\"Remote Failures\":0,\"User\":\"\",\"Last Share Time\":\"0\",\"Diff\":\"  0.0000\",\"Diff1 Shares\":0,\"Proxy Type\":\"\",\"Proxy\":\"\",\"Difficulty Accepted\":0.00000000,\"Difficulty Rejected\":0.00000000,\"Difficulty Stale\":0.00000000,\"Last Share Difficulty\":0.00000000,\"Has Stratum\":false,\"Stratum Active\":false,\"Stratum URL\":\"\",\"Has GBT\":false,\"Best Share\":\"  0.0000\",\"Pool Rejected%\":0.0000,\"Pool Stale%\":0.0000}],\"id\":1}")),
                                BlackminerType.BLACKMINER_F1,
                                Collections.emptyMap(),
                                DEFAULT_ARGS
                        },
                        {
                                // Blackminer F1 Ultra (workername preferred)
                                ImmutableMap.of(
                                        "{\"command\":\"version\"}",
                                        new RpcHandler(
                                                "{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1590413031,\"Code\":22,\"Msg\":\"CGMiner versions\",\"Description\":\"ccminer 2.3.3\"}],\"VERSION\":[{\"CGMiner\":\"2.3.3\",\"API\":\"3.1\",\"Miner\":\"1.3.0.8\",\"CompileTime\":\"2020-04-05 16-27-23 CST\",\"Type\":\"Blackminer F1-ULTRA\"}],\"id\":1}"),
                                        "{\"command\":\"pools\"}",
                                        new RpcHandler(
                                                "{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1593395695,\"Code\":7,\"Msg\":\"3 Pool(s)\",\"Description\":\"ccminer 2.3.3\"}],\"POOLS\":[{\"POOL\":0,\"URL\":\"xxx:8080\",\"Status\":\"Alive\",\"Priority\":0,\"Quota\":0,\"Long Poll\":\"N\",\"Getworks\":820,\"Accepted\":906,\"Rejected\":6,\"Discarded\":12216,\"Stale\":0,\"Get Failures\":0,\"Remote Failures\":0,\"User\":\"xxx\",\"Last Share Time\":\"0:00:08\",\"Diff\":\"  8.0000\",\"Diff1 Shares\":7407,\"Proxy Type\":\"\",\"Proxy\":\"\",\"Difficulty Accepted\":7296.00000000,\"Difficulty Rejected\":48.00000000,\"Difficulty Stale\":0.00000000,\"Last Share Difficulty\":8.00000000,\"Has Stratum\":true,\"Stratum Active\":true,\"Stratum URL\":\"xxx.com\",\"Has GBT\":false,\"Best Share\":\"  0.0000\",\"Pool Rejected%\":0.6536,\"Pool Stale%\":0.0000},{\"POOL\":1,\"URL\":\"\",\"Status\":\"Dead\",\"Priority\":1,\"Quota\":0,\"Long Poll\":\"N\",\"Getworks\":0,\"Accepted\":0,\"Rejected\":0,\"Discarded\":0,\"Stale\":0,\"Get Failures\":0,\"Remote Failures\":0,\"User\":\"\",\"Last Share Time\":\"0\",\"Diff\":\"  0.0000\",\"Diff1 Shares\":0,\"Proxy Type\":\"\",\"Proxy\":\"\",\"Difficulty Accepted\":0.00000000,\"Difficulty Rejected\":0.00000000,\"Difficulty Stale\":0.00000000,\"Last Share Difficulty\":0.00000000,\"Has Stratum\":false,\"Stratum Active\":false,\"Stratum URL\":\"\",\"Has GBT\":false,\"Best Share\":\"  0.0000\",\"Pool Rejected%\":0.0000,\"Pool Stale%\":0.0000},{\"POOL\":2,\"URL\":\"\",\"Status\":\"Dead\",\"Priority\":2,\"Quota\":0,\"Long Poll\":\"N\",\"Getworks\":0,\"Accepted\":0,\"Rejected\":0,\"Discarded\":0,\"Stale\":0,\"Get Failures\":0,\"Remote Failures\":0,\"User\":\"\",\"Last Share Time\":\"0\",\"Diff\":\"  0.0000\",\"Diff1 Shares\":0,\"Proxy Type\":\"\",\"Proxy\":\"\",\"Difficulty Accepted\":0.00000000,\"Difficulty Rejected\":0.00000000,\"Difficulty Stale\":0.00000000,\"Last Share Difficulty\":0.00000000,\"Has Stratum\":false,\"Stratum Active\":false,\"Stratum URL\":\"\",\"Has GBT\":false,\"Best Share\":\"  0.0000\",\"Pool Rejected%\":0.0000,\"Pool Stale%\":0.0000}],\"id\":1}")),
                                BlackminerType.BLACKMINER_F1_ULTRA,
                                ImmutableMap.of(
                                        "workerPreferred",
                                        "true"),
                                EntryStream
                                        .of(
                                                ImmutableMap.<String, Object>of(
                                                        "workerPreferred",
                                                        "true"))
                                        .append(DEFAULT_ARGS)
                                        .append(
                                                "worker",
                                                "xxx")
                                        .toMap()
                        }
                });
    }
}
