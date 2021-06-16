package mn.foreman.antminer;

import mn.foreman.io.Query;
import mn.foreman.model.miners.asic.Asic;

import java.math.BigDecimal;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/** Determines the power mode for a stock Antminer. */
public class StockPowerModeStrategy
        implements PowerModeStrategy {

    /** The ip. */
    private final String ip;

    /** The password. */
    private final String password;

    /** The port. */
    private final int port;

    /** The realm. */
    private final String realm;

    /** The socket timeout. */
    private final int socketTimeout;

    /** The socket timeout (units). */
    private final TimeUnit socketTimeoutUnits;

    /** The username. */
    private final String username;

    /**
     * Constructor.
     *
     * @param ip       The ip.
     * @param port     The port.
     * @param realm    The realm.
     * @param username The username.
     * @param password The password.
     */
    public StockPowerModeStrategy(
            final String ip,
            final int port,
            final String realm,
            final String username,
            final String password,
            final int socketTimeout,
            final TimeUnit socketTimeoutUnits) {
        this.ip = ip;
        this.port = port;
        this.realm = realm;
        this.username = username;
        this.password = password;
        this.socketTimeout = socketTimeout;
        this.socketTimeoutUnits = socketTimeoutUnits;
    }

    @Override
    public void setPowerMode(
            final Asic.Builder builder,
            final Map<String, String> values,
            final BigDecimal hashRate,
            final boolean hasErrors,
            final boolean hasFunctioningChips) {
        boolean reallyHasErrors = hasErrors;
        Asic.PowerMode powerMode = Asic.PowerMode.NORMAL;

        // Could be an older model and actually sleeping
        final String mode = values.getOrDefault("Mode", "");
        if ("254".equals(mode)) {
            powerMode = Asic.PowerMode.SLEEPING;
        } else {
            // Could be a new gen miner that needs to be explicitly checked
            if (hasErrors && !hasFunctioningChips) {
                try {
                    final AtomicBoolean sleeping = new AtomicBoolean(false);
                    Query.digestGet(
                            this.ip,
                            this.port,
                            this.realm,
                            "/cgi-bin/get_miner_conf.cgi",
                            this.username,
                            this.password,
                            (code, s) -> sleeping.set(s.contains("\"bitmain-work-mode\" : \"1\"")),
                            this.socketTimeout,
                            this.socketTimeoutUnits);
                    if (sleeping.get()) {
                        powerMode = Asic.PowerMode.SLEEPING;
                        reallyHasErrors = false;
                    }
                } catch (final Exception e) {
                    // Ignore
                }
            }
        }

        // Might be stuck
        if (powerMode == Asic.PowerMode.NORMAL &&
                hashRate.compareTo(BigDecimal.ZERO) == 0) {
            powerMode = Asic.PowerMode.IDLE;
        }

        builder
                .setPowerMode(powerMode)
                .hasErrors(reallyHasErrors);
    }
}
