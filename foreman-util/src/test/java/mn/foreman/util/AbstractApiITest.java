package mn.foreman.util;

import mn.foreman.model.Miner;
import mn.foreman.model.miners.MinerStats;

import org.junit.Test;

import java.util.concurrent.TimeUnit;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

/** An abstract integration test for validating {@link Miner} implementations. */
public abstract class AbstractApiITest {

    /** The expected metrics. */
    private final MinerStats expectedStats;

    /** The fake server. */
    private final FakeMinerServer fakeMinerServer;

    /** The {@link Miner} under test. */
    private final Miner miner;

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
        this.fakeMinerServer = fakeMinerServer;
        this.miner = miner;
        this.expectedStats = expectedStats;
    }

    /** Test the metrics querying performed by {@link #miner}. */
    @Test
    public void testMiner() throws Exception {
        try {
            this.fakeMinerServer.start();
            assertEquals(
                    this.expectedStats,
                    this.miner.getStats());
            assertTrue(
                    this.fakeMinerServer.waitTillDone(
                            10,
                            TimeUnit.SECONDS));
        } finally {
            this.fakeMinerServer.close();
        }
    }
}