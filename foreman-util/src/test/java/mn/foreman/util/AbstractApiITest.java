package mn.foreman.util;

import mn.foreman.model.Miner;
import mn.foreman.model.miners.MinerStats;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

/** An abstract integration test for validating {@link Miner} implementations. */
public abstract class AbstractApiITest {

    /** The expected metrics. */
    private final MinerStats expectedStats;

    /** The fake servers. */
    private final List<FakeMinerServer> fakeMinerServers;

    /** The {@link Miner} under test. */
    private final Miner miner;

    /**
     * Constructor.
     *
     * @param miner            The {@link Miner} under test.
     * @param fakeMinerServers A fake servers for query testing.
     * @param expectedStats    The expected metrics.
     */
    public AbstractApiITest(
            final Miner miner,
            final List<FakeMinerServer> fakeMinerServers,
            final MinerStats expectedStats) {
        this.miner = miner;
        this.fakeMinerServers = new ArrayList<>(fakeMinerServers);
        this.expectedStats = expectedStats;
    }

    /**
     * Constructor.
     *
     * @param miner           The {@link Miner} under test.
     * @param fakeMinerServer A fake server for query testing.
     * @param expectedStats   The expected metrics.
     */
    public AbstractApiITest(
            final Miner miner,
            final FakeMinerServer fakeMinerServer,
            final MinerStats expectedStats) {
        this(
                miner,
                Collections.singletonList(fakeMinerServer),
                expectedStats);
    }

    /** Test the metrics querying performed by {@link #miner}. */
    @Test
    public void testMiner() throws Exception {
        try {
            this.fakeMinerServers.forEach(FakeMinerServer::start);
            assertEquals(
                    this.expectedStats,
                    this.miner.getStats());
            this.fakeMinerServers.forEach(server ->
                    assertTrue(
                            server.waitTillDone(
                                    10,
                                    TimeUnit.SECONDS)));
        } finally {
            this.fakeMinerServers.forEach(server -> {
                try {
                    server.close();
                } catch (final Exception e) {
                    // Ignore
                }
            });
        }
    }
}