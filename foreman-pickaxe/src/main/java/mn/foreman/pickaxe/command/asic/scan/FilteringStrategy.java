package mn.foreman.pickaxe.command.asic.scan;

import mn.foreman.model.Detection;

/** Filters down the detections to the ones that match the desired MAC. */
public interface FilteringStrategy {

    /**
     * Filters down the provided {@link Detection detections} to the MAC that
     * was desired.
     *
     * @param detection The detection.
     * @param mac       The MAC.
     *
     * @return Whether or not it was filtered.
     */
    boolean matches(
            Detection detection,
            String mac);
}
