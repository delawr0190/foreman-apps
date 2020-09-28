package mn.foreman.pickaxe.run;

import mn.foreman.model.miners.MinerStats;

import java.time.ZonedDateTime;
import java.util.List;

/** Sends metrics to the dashboard. */
public interface MetricsSender {

    /**
     * Sends the provided metrics to the dashboard.
     *
     * @param publishTime The publish time.
     * @param stats       The metrics to send.
     */
    void sendMetrics(
            ZonedDateTime publishTime,
            List<MinerStats> stats);
}
