package mn.foreman.discover;

import mn.foreman.util.FakeMinerServer;
import mn.foreman.util.rpc.FakeRpcMinerServer;
import mn.foreman.util.rpc.RpcHandler;

import com.google.common.collect.ImmutableMap;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

/** Tests the discovery process of pickaxe. */
public class DiscoveryITest {

    /** Tests against a miner that exists. */
    @Test
    public void testFound() {
        runDiscovery(
                4028,
                Arrays.asList(
                        Discovery
                                .builder()
                                .query("{\"command\":\"pools\"}")
                                .response("{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1533152695,\"Code\":7,\"Msg\":\"5 Pool(s)\",\"Description\":\"cgminer 4.10.0\"}],\"POOLS\":[{\"POOL\":0,\"URL\":\"stratum+tcp://dash-eu.coinmine.pl:6099\",\"Status\":\"Alive\",\"Priority\":0,\"Quota\":0,\"Long Poll\":\"N\",\"Getworks\":1167,\"Accepted\":3038,\"Rejected\":8,\"Discarded\":28157,\"Stale\":5,\"Get Failures\":1,\"Remote Failures\":0,\"User\":\"Dash.default\",\"Last Share Time\":\"0:00:00\",\"Diff\":\" 69.2461\",\"Diff1 Shares\":6475798,\"Proxy Type\":\"\",\"Proxy\":\"\",\"Difficulty Accepted\":198412.94104085,\"Difficulty Rejected\":604.97361199,\"Difficulty Stale\":0.00000000,\"Last Share Difficulty\":69.24612531,\"Has Stratum\":true,\"Stratum Active\":true,\"Stratum URL\":\"dash-eu.coinmine.pl\",\"Has GBT\":false,\"Best Share\":\"  0.0000\",\"Pool Rejected%\":0.3040,\"Pool Stale%\":0.0000},{\"POOL\":1,\"URL\":\"\",\"Status\":\"Dead\",\"Priority\":1,\"Quota\":0,\"Long Poll\":\"N\",\"Getworks\":0,\"Accepted\":0,\"Rejected\":0,\"Discarded\":0,\"Stale\":0,\"Get Failures\":0,\"Remote Failures\":0,\"User\":\"\",\"Last Share Time\":\"0\",\"Diff\":\"  0.0000\",\"Diff1 Shares\":0,\"Proxy Type\":\"\",\"Proxy\":\"\",\"Difficulty Accepted\":0.00000000,\"Difficulty Rejected\":0.00000000,\"Difficulty Stale\":0.00000000,\"Last Share Difficulty\":0.00000000,\"Has Stratum\":false,\"Stratum Active\":false,\"Stratum URL\":\"\",\"Has GBT\":false,\"Best Share\":\"  0.0000\",\"Pool Rejected%\":0.0000,\"Pool Stale%\":0.0000},{\"POOL\":2,\"URL\":\"\",\"Status\":\"Dead\",\"Priority\":2,\"Quota\":0,\"Long Poll\":\"N\",\"Getworks\":0,\"Accepted\":0,\"Rejected\":0,\"Discarded\":0,\"Stale\":0,\"Get Failures\":0,\"Remote Failures\":0,\"User\":\"\",\"Last Share Time\":\"0\",\"Diff\":\"  0.0000\",\"Diff1 Shares\":0,\"Proxy Type\":\"\",\"Proxy\":\"\",\"Difficulty Accepted\":0.00000000,\"Difficulty Rejected\":0.00000000,\"Difficulty Stale\":0.00000000,\"Last Share Difficulty\":0.00000000,\"Has Stratum\":false,\"Stratum Active\":false,\"Stratum URL\":\"\",\"Has GBT\":false,\"Best Share\":\"  0.0000\",\"Pool Rejected%\":0.0000,\"Pool Stale%\":0.0000},\n{\"POOL\":3,\"URL\":\"stratum+tcp://dash.suprnova.cc:9991\",\"Status\":\"Alive\",\"Priority\":9998,\"Quota\":0,\"Long Poll\":\"N\",\"Getworks\":1,\"Accepted\":0,\"Rejected\":0,\"Discarded\":0,\"Stale\":0,\"Get Failures\":0,\"Remote Failures\":0,\"User\":\"devFeeMiner.9\",\"Last Share Time\":\"0\",\"Diff\":\" 64.0000\",\"Diff1 Shares\":0,\"Proxy Type\":\"\",\"Proxy\":\"\",\"Difficulty Accepted\":0.00000000,\"Difficulty Rejected\":0.00000000,\"Difficulty Stale\":0.00000000,\"Last Share Difficulty\":0.00000000,\"Has Stratum\":true,\"Stratum Active\":false,\"Stratum URL\":\"\",\"Has GBT\":false,\"Best Share\":\" 0.0000\",\"Pool Rejected%\":0.0000,\"Pool Stale%\":0.0000},{\"POOL\":4,\"URL\":\"stratum+tcp://dash-eu.coinmine.pl:6099\",\"Status\":\"Alive\",\"Priority\":9999,\"Quota\":0,\"Long Poll\":\"N\",\"Getworks\":1,\"Accepted\":0,\"Rejected\":0,\"Discarded\":0,\"Stale\":0,\"Get Failures\":0,\"Remote Failures\":0,\"User\":\"devFeeMiner.1\",\"Last Share Time\":\"0\",\"Diff\":\" 20.0000\",\"Diff1 Shares\":0,\"Proxy Type\":\"\",\"Proxy\":\"\",\"Difficulty Accepted\":0.00000000,\"Difficulty Rejected\":0.00000000,\"Difficulty Stale\":0.00000000,\"Last Share Difficulty\":0.00000000,\"Has Stratum\":true,\"Stratum Active\":false,\"Stratum URL\":\"\",\"Has GBT\":false,\"Best Share\":\" 0.0000\",\"Pool Rejected%\":0.0000,\"Pool Stale%\":0.0000}],\"id\":1}")
                                .success(true)
                                .build(),
                        Discovery
                                .builder()
                                .query("{\"command\":\"stats\"}")
                                .response("{\"STATUS\":[{\"STATUS\":\"S\",\"When\":395315,\"Code\":22,\"Msg\":\"CGMiner versions\",\"Description\":\"bmminer 1.0.0\"}],\"VERSION\":[{\"CGMiner\":\"2.0.0\",\"API\":\"3.1\",\"Miner\":\"A.0.0.1\",\"CompileTime\":\"Wed Apr 11 22:21:40 CST 2018\",\"Type\":\"Antminer B3\"}],\"id\":1}")
                                .success(true)
                                .build(),
                        Discovery
                                .builder()
                                .query("{\"command\":\"devs\"}")
                                .response("")
                                .success(true)
                                .build(),
                        Discovery
                                .builder()
                                .query("{\"command\":\"edevs\"}")
                                .response("")
                                .success(true)
                                .build(),
                        Discovery
                                .builder()
                                .query("{\"command\":\"summary\"}")
                                .response("")
                                .success(true)
                                .build(),
                        Discovery
                                .builder()
                                .query("{\"command\":\"version\"}")
                                .response("")
                                .success(true)
                                .build(),
                        Discovery
                                .builder()
                                .query("{\"command\":\"temps\"}")
                                .response("")
                                .success(true)
                                .build(),
                        Discovery
                                .builder()
                                .query("{\"command\":\"fans\"}")
                                .response("")
                                .success(true)
                                .build()));
    }
    
    /**
     * Runs a discovery test.
     *
     * @param port     The port to query.
     * @param expected The expected results.
     */
    private static void runDiscovery(
            final int port,
            final List<Discovery> expected) {
        try (final FakeMinerServer fakeMinerServer =
                     new FakeRpcMinerServer(
                             4028,
                             ImmutableMap.of(
                                     "{\"command\":\"stats\"}",
                                     new RpcHandler(
                                             "{\"STATUS\":[{\"STATUS\":\"S\",\"When\":395315,\"Code\":22,\"Msg\":\"CGMiner versions\",\"Description\":\"bmminer 1.0.0\"}],\"VERSION\":[{\"CGMiner\":\"2.0.0\",\"API\":\"3.1\",\"Miner\":\"A.0.0.1\",\"CompileTime\":\"Wed Apr 11 22:21:40 CST 2018\",\"Type\":\"Antminer B3\"}],\"id\":1}"),
                                     "{\"command\":\"pools\"}",
                                     new RpcHandler(
                                             "{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1533152695,\"Code\":7,\"Msg\":\"5 Pool(s)\",\"Description\":\"cgminer 4.10.0\"}],\"POOLS\":[{\"POOL\":0,\"URL\":\"stratum+tcp://dash-eu.coinmine.pl:6099\",\"Status\":\"Alive\",\"Priority\":0,\"Quota\":0,\"Long Poll\":\"N\",\"Getworks\":1167,\"Accepted\":3038,\"Rejected\":8,\"Discarded\":28157,\"Stale\":5,\"Get Failures\":1,\"Remote Failures\":0,\"User\":\"Dash.default\",\"Last Share Time\":\"0:00:00\",\"Diff\":\" 69.2461\",\"Diff1 Shares\":6475798,\"Proxy Type\":\"\",\"Proxy\":\"\",\"Difficulty Accepted\":198412.94104085,\"Difficulty Rejected\":604.97361199,\"Difficulty Stale\":0.00000000,\"Last Share Difficulty\":69.24612531,\"Has Stratum\":true,\"Stratum Active\":true,\"Stratum URL\":\"dash-eu.coinmine.pl\",\"Has GBT\":false,\"Best Share\":\"  0.0000\",\"Pool Rejected%\":0.3040,\"Pool Stale%\":0.0000},{\"POOL\":1,\"URL\":\"\",\"Status\":\"Dead\",\"Priority\":1,\"Quota\":0,\"Long Poll\":\"N\",\"Getworks\":0,\"Accepted\":0,\"Rejected\":0,\"Discarded\":0,\"Stale\":0,\"Get Failures\":0,\"Remote Failures\":0,\"User\":\"\",\"Last Share Time\":\"0\",\"Diff\":\"  0.0000\",\"Diff1 Shares\":0,\"Proxy Type\":\"\",\"Proxy\":\"\",\"Difficulty Accepted\":0.00000000,\"Difficulty Rejected\":0.00000000,\"Difficulty Stale\":0.00000000,\"Last Share Difficulty\":0.00000000,\"Has Stratum\":false,\"Stratum Active\":false,\"Stratum URL\":\"\",\"Has GBT\":false,\"Best Share\":\"  0.0000\",\"Pool Rejected%\":0.0000,\"Pool Stale%\":0.0000},{\"POOL\":2,\"URL\":\"\",\"Status\":\"Dead\",\"Priority\":2,\"Quota\":0,\"Long Poll\":\"N\",\"Getworks\":0,\"Accepted\":0,\"Rejected\":0,\"Discarded\":0,\"Stale\":0,\"Get Failures\":0,\"Remote Failures\":0,\"User\":\"\",\"Last Share Time\":\"0\",\"Diff\":\"  0.0000\",\"Diff1 Shares\":0,\"Proxy Type\":\"\",\"Proxy\":\"\",\"Difficulty Accepted\":0.00000000,\"Difficulty Rejected\":0.00000000,\"Difficulty Stale\":0.00000000,\"Last Share Difficulty\":0.00000000,\"Has Stratum\":false,\"Stratum Active\":false,\"Stratum URL\":\"\",\"Has GBT\":false,\"Best Share\":\"  0.0000\",\"Pool Rejected%\":0.0000,\"Pool Stale%\":0.0000},\n{\"POOL\":3,\"URL\":\"stratum+tcp://dash.suprnova.cc:9991\",\"Status\":\"Alive\",\"Priority\":9998,\"Quota\":0,\"Long Poll\":\"N\",\"Getworks\":1,\"Accepted\":0,\"Rejected\":0,\"Discarded\":0,\"Stale\":0,\"Get Failures\":0,\"Remote Failures\":0,\"User\":\"devFeeMiner.9\",\"Last Share Time\":\"0\",\"Diff\":\" 64.0000\",\"Diff1 Shares\":0,\"Proxy Type\":\"\",\"Proxy\":\"\",\"Difficulty Accepted\":0.00000000,\"Difficulty Rejected\":0.00000000,\"Difficulty Stale\":0.00000000,\"Last Share Difficulty\":0.00000000,\"Has Stratum\":true,\"Stratum Active\":false,\"Stratum URL\":\"\",\"Has GBT\":false,\"Best Share\":\" 0.0000\",\"Pool Rejected%\":0.0000,\"Pool Stale%\":0.0000},{\"POOL\":4,\"URL\":\"stratum+tcp://dash-eu.coinmine.pl:6099\",\"Status\":\"Alive\",\"Priority\":9999,\"Quota\":0,\"Long Poll\":\"N\",\"Getworks\":1,\"Accepted\":0,\"Rejected\":0,\"Discarded\":0,\"Stale\":0,\"Get Failures\":0,\"Remote Failures\":0,\"User\":\"devFeeMiner.1\",\"Last Share Time\":\"0\",\"Diff\":\" 20.0000\",\"Diff1 Shares\":0,\"Proxy Type\":\"\",\"Proxy\":\"\",\"Difficulty Accepted\":0.00000000,\"Difficulty Rejected\":0.00000000,\"Difficulty Stale\":0.00000000,\"Last Share Difficulty\":0.00000000,\"Has Stratum\":true,\"Stratum Active\":false,\"Stratum URL\":\"\",\"Has GBT\":false,\"Best Share\":\" 0.0000\",\"Pool Rejected%\":0.0000,\"Pool Stale%\":0.0000}],\"id\":1}")))) {
            fakeMinerServer.start();

            final CgminerDiscoverStrategy discoverStrategy =
                    new CgminerDiscoverStrategy();
            final List<Discovery> discoveries =
                    discoverStrategy.discover(
                            "127.0.0.1",
                            port);

            fakeMinerServer.waitTillDone(
                    5,
                    TimeUnit.SECONDS);

            assertEquals(
                    expected,
                    discoveries);
        } catch (final Exception e) {
            throw new AssertionError(e);
        }
    }
}
