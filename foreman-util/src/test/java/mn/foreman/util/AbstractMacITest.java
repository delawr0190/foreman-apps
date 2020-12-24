package mn.foreman.util;

import mn.foreman.model.Miner;
import mn.foreman.model.MinerFactory;

import com.google.common.collect.ImmutableMap;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

/** An abstract integration test for validating {@link Miner} MAC obtaining. */
public abstract class AbstractMacITest {

    /** The arguments. */
    private static final Map<String, Object> ARGS =
            ImmutableMap.<String, Object>builder()
                    .put(
                            "apiIp",
                            "127.0.0.1")
                    .put(
                            "apiPort",
                            "4028")
                    .put(
                            "port",
                            "8080")
                    .put(
                            "testPort",
                            "8080")
                    .put(
                            "username",
                            "username")
                    .put(
                            "password",
                            "password")
                    .build();

    /** The expected MAC. */
    private final String expectedMac;

    /** The fake servers. */
    private final List<FakeMinerServer> fakeMinerServers;

    /** The {@link Miner} under test. */
    private final MinerFactory minerFactory;

    /**
     * Constructor.
     *
     * @param minerFactory     The {@link Miner} under test.
     * @param fakeMinerServers The fake servers.
     * @param expectedMac      The expected MAC.
     */
    public AbstractMacITest(
            final MinerFactory minerFactory,
            final List<FakeMinerServer> fakeMinerServers,
            final String expectedMac) {
        this.minerFactory = minerFactory;
        this.fakeMinerServers = new ArrayList<>(fakeMinerServers);
        this.expectedMac = expectedMac;
    }

    /** Tests querying the MAC. */
    @Test
    public void testMac() {
        try {
            this.fakeMinerServers.forEach(FakeMinerServer::start);
            final Miner miner =
                    this.minerFactory.create(
                            ARGS);
            assertEquals(
                    Optional.of(this.expectedMac),
                    miner.getMacAddress());
            this.fakeMinerServers.forEach(
                    server ->
                            server.waitTillDone(
                                    10,
                                    TimeUnit.SECONDS));
        } finally {
            this.fakeMinerServers
                    .forEach(server -> {
                        try {
                            server.close();
                        } catch (final Exception e) {
                            // Ignore
                        }
                    });
        }
    }
}