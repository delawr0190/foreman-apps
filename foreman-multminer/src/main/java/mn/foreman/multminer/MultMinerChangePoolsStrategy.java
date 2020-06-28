package mn.foreman.multminer;

import mn.foreman.io.Query;
import mn.foreman.model.AbstractChangePoolsStrategy;
import mn.foreman.model.ChangePoolsStrategy;
import mn.foreman.model.Pool;
import mn.foreman.model.error.MinerException;

import com.google.common.collect.ImmutableMap;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * A {@link MultMinerChangePoolsStrategy} provides a {@link ChangePoolsStrategy}
 * implementation that will change the pools in use by a multminer device.
 */
public class MultMinerChangePoolsStrategy
        extends AbstractChangePoolsStrategy {

    /** The logger for this class. */
    private static final Logger LOG =
            LoggerFactory.getLogger(MultMinerChangePoolsStrategy.class);

    @Override
    protected boolean doChange(
            final String ip,
            final int port,
            final Map<String, Object> parameters,
            final List<Pool> pools)
            throws MinerException {
        boolean success;

        // Check and perform an algo update, if needed
        success =
                conditionallyUpdateAlgo(
                        ip,
                        port,
                        (String) parameters.getOrDefault(
                                "algo",
                                ""));
        if (success) {
            // Then, update the pools
            LOG.debug("Updating pools to {}", pools);
            success =
                    updateMiner(
                            ip,
                            port,
                            content -> {
                                for (int i = 0; i < pools.size(); i++) {
                                    addPool(
                                            pools.get(i),
                                            i,
                                            content);
                                }
                            },
                            () -> true);
        }

        return success;
    }

    /**
     * Adds the key and value to the destination.
     *
     * @param key   The key.
     * @param value The value.
     * @param dest  The destination.
     */
    private static void add(
            final String key,
            final String value,
            final List<Map<String, Object>> dest) {
        dest.add(
                ImmutableMap.of(
                        "key",
                        key,
                        "value",
                        value));
    }

    /**
     * Adds the provided pool.
     *
     * @param pool  The pool.
     * @param index The index.
     * @param dest  The destination.
     */
    private static void addPool(
            final Pool pool,
            final int index,
            final List<Map<String, Object>> dest) {
        add(
                String.format(
                        "p%durl",
                        index),
                String.format(
                        "-o%s %s",
                        (index == 0 ? "" : index),
                        pool.getUrl()),
                dest);
        add(
                String.format(
                        "p%duser",
                        index),
                String.format(
                        "-u%s %s",
                        (index == 0 ? "" : index),
                        pool.getUsername()),
                dest);
        add(
                String.format(
                        "p%dpwd",
                        index),
                String.format(
                        "-p%s %s",
                        (index == 0 ? "" : index),
                        pool.getPassword()),
                dest);
    }

    /**
     * Updates the miner's algorithm if the algorithm has changed.
     *
     * @param ip      The ip.
     * @param port    The port.
     * @param newAlgo The new algorithm.
     *
     * @return Whether or not the algorithm was successfully updated.
     *
     * @throws MinerException on failure.
     */
    private static boolean conditionallyUpdateAlgo(
            final String ip,
            final int port,
            final String newAlgo)
            throws MinerException {
        boolean success = true;
        if (hasAlgoChanged(newAlgo)) {
            LOG.debug("Changing algorithm to {}", newAlgo);
            success =
                    updateMiner(
                            ip,
                            port,
                            content ->
                                    content.add(
                                            ImmutableMap.of(
                                                    "key",
                                                    "mt",
                                                    "value",
                                                    newAlgo)),
                            () -> {
                                try {
                                    // Initially wait for the miner to start rebooting
                                    TimeUnit.SECONDS.sleep(20);

                                    // Now start checking for miner recovery
                                    return waitForReboot(ip, port);
                                } catch (final InterruptedException ie) {
                                    LOG.warn("Exception occurred while waiting for reboot",
                                            ie);
                                    return false;
                                }
                            });
        }
        return success;
    }

    /**
     * Checks to see if the algorithm has changed.
     *
     * @param newAlgo The new algorithm.
     *
     * @return Whether or not the algo has changed.
     */
    private static boolean hasAlgoChanged(final String newAlgo) {
        return newAlgo != null && !newAlgo.isEmpty();
    }

    /**
     * Updates the multminer instance, leveraging the provided {@link Consumer}
     * to add content based on the action that's being performed.
     *
     * @param ip           The ip.
     * @param port         The port.
     * @param contentAdder The content enricher.
     * @param delay        The delayer.
     *
     * @return Whether or not the update was successful.
     *
     * @throws MinerException on failure.
     */
    private static boolean updateMiner(
            final String ip,
            final int port,
            final Consumer<List<Map<String, Object>>> contentAdder,
            final Supplier<Boolean> delay)
            throws MinerException {
        boolean success;

        final List<Map<String, Object>> content = new LinkedList<>();
        add(
                "act",
                "pol",
                content);
        contentAdder.accept(content);

        try {
            final AtomicReference<Integer> statusCode = new AtomicReference<>();
            Query.post(
                    ip,
                    port,
                    "/index.csp",
                    content,
                    (code, s) -> {
                        LOG.debug(
                                "Received {} - response {}",
                                code,
                                statusCode);
                        statusCode.set(code);
                    });
            final Integer code = statusCode.get();
            success = (code != null && code == HttpStatus.SC_OK);
        } catch (final Exception e) {
            throw new MinerException(e);
        }

        // Wait, if desired, until proceeding
        return success && delay.get();
    }

    /**
     * Waits for a miner reboot to be completed.
     *
     * @param ip   The miner ip.
     * @param port The miner port.
     *
     * @return Whether or not the miner rebooted before the deadline.
     *
     * @throws InterruptedException on failure to wait.
     */
    private static boolean waitForReboot(
            final String ip,
            final int port)
            throws InterruptedException {
        boolean rebooted = false;

        final MultMiner miner =
                new MultMiner(
                        ip,
                        port);

        long now = System.currentTimeMillis();
        final long deadlineMillis = now + TimeUnit.MINUTES.toMillis(5);
        while (now < deadlineMillis) {
            try {
                miner.getStats();
                rebooted = true;
                break;
            } catch (final MinerException me) {
                LOG.debug("Miner hasn't rebooted yet - waiting");
                TimeUnit.SECONDS.sleep(10);
            }
            now = System.currentTimeMillis();
        }

        return rebooted;
    }
}
