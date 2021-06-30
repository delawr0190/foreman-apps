package mn.foreman.avalon;

import mn.foreman.io.ApiRequest;
import mn.foreman.io.ApiRequestImpl;
import mn.foreman.io.Connection;
import mn.foreman.io.ConnectionFactory;
import mn.foreman.model.ApplicationConfiguration;
import mn.foreman.model.BlinkStrategy;

import java.util.Map;

/** A {@link BlinkStrategy} for blinking LEDs on an Avalon. */
public class AvalonBlinkStrategy
        implements BlinkStrategy {

    /** The configuration. */
    private final ApplicationConfiguration applicationConfiguration;

    /**
     * Constructor.
     *
     * @param applicationConfiguration The configuration.
     */
    public AvalonBlinkStrategy(final ApplicationConfiguration applicationConfiguration) {
        this.applicationConfiguration = applicationConfiguration;
    }

    @Override
    public boolean startBlink(
            final String ip,
            final int port,
            final Map<String, Object> parameters) {
        return runCommand(
                ip,
                port,
                "1");
    }

    @Override
    public boolean stopBlink(
            final String ip,
            final int port,
            final Map<String, Object> parameters) {
        return runCommand(
                ip,
                port,
                "0");
    }

    /**
     * Runs a command against the miner.
     *
     * @param ip      The ip.
     * @param port    The port.
     * @param command The command.
     *
     * @return Whether or not the command was successful.
     */
    private boolean runCommand(
            final String ip,
            final int port,
            final String command) {
        boolean success = false;

        final ApiRequest request =
                new ApiRequestImpl(
                        ip,
                        (port == 8081 || port == 4029 ? 4029 : 4028),
                        "ascset|0,led,1-" + command);

        final ApplicationConfiguration.SocketConfig socketConfig =
                this.applicationConfiguration.getWriteSocketTimeout();

        final Connection connection =
                ConnectionFactory.createRawConnection(
                        request,
                        socketConfig.getSocketTimeout(),
                        socketConfig.getSocketTimeoutUnits());
        connection.query();

        if (request.waitForCompletion(
                socketConfig.getSocketTimeout(),
                socketConfig.getSocketTimeoutUnits())) {
            final String response = request.getResponse();
            success = response != null && response.toLowerCase().contains("ok");
        }

        return success;
    }
}