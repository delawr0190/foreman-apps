package mn.foreman.model;

import java.util.HashMap;
import java.util.Map;

/** Common utility callbacks for async actions. */
public class AsyncAsicActionUtils {

    /**
     * Patches the param IP and ports (ports only for testing) to the new IPs to
     * account for situations where a command may cause a miner's IP to change.
     *
     * @param originalParams The original.
     * @param newParams      The new params (pre-altered).
     *
     * @return The altered params.
     */
    public static Map<String, Object> ipChangingHook(
            final Map<String, Object> originalParams,
            final Map<String, Object> newParams) {
        final Map<String, Object> alteredParams = new HashMap<>(newParams);
        if (originalParams.containsKey("newApiIp")) {
            alteredParams.put(
                    "apiIp",
                    originalParams.get("newApiIp"));
        }
        // For testing
        if (originalParams.containsKey("newApiPort")) {
            alteredParams.put(
                    "apiPort",
                    originalParams.get("newApiPort"));
        }
        return alteredParams;
    }
}
