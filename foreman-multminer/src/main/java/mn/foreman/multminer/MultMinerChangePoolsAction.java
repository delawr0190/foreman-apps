package mn.foreman.multminer;

import mn.foreman.model.AbstractChangePoolsAction;
import mn.foreman.model.Pool;
import mn.foreman.model.error.MinerException;

import com.google.common.collect.ImmutableMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * A {@link MultMinerChangePoolsAction} provides an {@link
 * AbstractChangePoolsAction} implementation that will change the pools in use
 * by a multminer device.
 */
public class MultMinerChangePoolsAction
        extends AbstractChangePoolsAction {

    /** The logger for this class. */
    private static final Logger LOG =
            LoggerFactory.getLogger(MultMinerChangePoolsAction.class);

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
                    MultMinerQuery.query(
                            ip,
                            port,
                            "pol",
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
        MultMinerQuery.add(
                String.format(
                        "p%durl",
                        index),
                String.format(
                        "-o%s %s",
                        (index == 0 ? "" : index),
                        pool.getUrl()),
                dest);
        MultMinerQuery.add(
                String.format(
                        "p%duser",
                        index),
                String.format(
                        "-u%s %s",
                        (index == 0 ? "" : index),
                        pool.getUsername()),
                dest);
        MultMinerQuery.add(
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
                    MultMinerQuery.query(
                            ip,
                            port,
                            "pol",
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
                        port,
                        Collections.emptyList());

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