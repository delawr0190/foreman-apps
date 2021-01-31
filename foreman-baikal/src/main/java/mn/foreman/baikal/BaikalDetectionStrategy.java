package mn.foreman.baikal;

import mn.foreman.model.Detection;
import mn.foreman.model.DetectionStrategy;
import mn.foreman.model.DetectionUtils;
import mn.foreman.model.Miner;
import mn.foreman.util.ArgUtils;

import org.apache.http.HttpStatus;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

/** Detects a baikal miner. */
public class BaikalDetectionStrategy
        implements DetectionStrategy {

    /** The miner to obtain stats. */
    private final Miner miner;

    /** The default web port. */
    private final String webPort;

    /**
     * Constructor.
     *
     * @param webPort The default web port.
     * @param miner   The miner to query to obtain the worker name.
     */
    public BaikalDetectionStrategy(
            final String webPort,
            final Miner miner) {
        this.webPort = webPort;
        this.miner = miner;
    }

    @Override
    public Optional<Detection> detect(
            final String ip,
            final int port,
            final Map<String, Object> args) {
        final AtomicBoolean isBaikal = new AtomicBoolean(false);
        HttpURLConnection connection = null;
        try {
            final URL url =
                    new URL(
                            String.format(
                                    "http://%s:%d/login.php",
                                    ip,
                                    Integer.parseInt(
                                            args.getOrDefault(
                                                    "webPort",
                                                    this.webPort).toString())));
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("HEAD");
            isBaikal.set(connection.getResponseCode() == HttpStatus.SC_OK);
        } catch (final Exception e) {
            // Ignore
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }

        Detection detection = null;
        if (isBaikal.get()) {
            final Map<String, Object> newArgs = new HashMap<>(args);

            if (ArgUtils.isWorkerPreferred(args)) {
                DetectionUtils.addWorkerFromStats(
                        this.miner,
                        newArgs);
            }

            detection =
                    Detection.builder()
                            .ipAddress(ip)
                            .port(port)
                            .minerType(BaikalType.GENERIC)
                            .parameters(newArgs)
                            .build();
        }
        return Optional.ofNullable(detection);
    }
}
