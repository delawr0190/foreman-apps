package mn.foreman.cgminer;

import mn.foreman.cgminer.request.CgMinerCommand;
import mn.foreman.cgminer.request.CgMinerRequest;
import mn.foreman.model.Detection;
import mn.foreman.model.DetectionStrategy;
import mn.foreman.model.MinerType;
import mn.foreman.model.error.EmptySiteException;
import mn.foreman.model.error.MinerException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * A {@link CgMinerDetectionStrategy} provides a {@link DetectionStrategy} for
 * querying cgminer instances and identifying the {@link MinerType} that's
 * running.
 */
public class CgMinerDetectionStrategy
        implements DetectionStrategy {

    /** The logger for this class. */
    private static final Logger LOG =
            LoggerFactory.getLogger(CgMinerDetectionStrategy.class);

    /** The command to run. */
    private final CgMinerCommand command;

    /** The response strategy. */
    private final ResponsePatchingStrategy patchingStrategy;

    /** The factory to use for converting to a {@link MinerType}. */
    private final TypeFactory typeFactory;

    /**
     * Constructor.
     *
     * @param command          The command.
     * @param typeFactory      The factory for converting to a {@link
     *                         MinerType}.
     * @param patchingStrategy The patching strategy.
     */
    public CgMinerDetectionStrategy(
            final CgMinerCommand command,
            final TypeFactory typeFactory,
            final ResponsePatchingStrategy patchingStrategy) {
        this.command = command;
        this.typeFactory = typeFactory;
        this.patchingStrategy = patchingStrategy;
    }

    /**
     * Constructor.
     *
     * @param command     The command.
     * @param typeFactory The factory for converting to a {@link MinerType}.
     */
    public CgMinerDetectionStrategy(
            final CgMinerCommand command,
            final TypeFactory typeFactory) {
        this(
                command,
                typeFactory,
                new NullPatchingStrategy());
    }

    @Override
    public Optional<Detection> detect(
            final String ip,
            final int port,
            final Map<String, String> args) {
        Detection detection = null;
        try {
            final Map<String, List<Map<String, String>>> responseValues =
                    new HashMap<>();
            final CgMiner cgMiner =
                    new CgMiner.Builder()
                            .setApiIp(ip)
                            .setApiPort(port)
                            .setConnectTimeout(
                                    1,
                                    TimeUnit.SECONDS)
                            .addRequest(
                                    new CgMinerRequest.Builder()
                                            .setCommand(this.command)
                                            .build(),
                                    (builder, response) ->
                                            responseValues.putAll(
                                                    response.getValues()),
                                    this.patchingStrategy)
                            .build();

            // Attempt to query the miner for stats.  If there's a response,
            // then we found something
            cgMiner.getStats();

            final Optional<MinerType> type =
                    this.typeFactory.toType(responseValues);
            if (type.isPresent()) {
                detection =
                        Detection.builder()
                                .ipAddress(ip)
                                .port(port)
                                .minerType(type.get())
                                .build();
            }
        } catch (final MinerException | EmptySiteException e) {
            LOG.debug("No cgminer found on {}:{}", ip, port);
        }

        return Optional.ofNullable(detection);
    }
}
