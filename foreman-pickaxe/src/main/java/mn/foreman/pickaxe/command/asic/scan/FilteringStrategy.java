package mn.foreman.pickaxe.command.asic.scan;

import mn.foreman.model.Detection;

import java.util.List;

/** Filters down the detections to the ones that match the desired MAC. */
public interface FilteringStrategy {

    /**
     * Filters down the provided {@link Detection detections} to the MAC that
     * was desired.
     *
     * @param detection The detection.
     * @param macs      The MACs.
     *
     * @return Whether or not it was filtered.
     */
    boolean matches(
            Detection detection,
            List<String> macs);
}
