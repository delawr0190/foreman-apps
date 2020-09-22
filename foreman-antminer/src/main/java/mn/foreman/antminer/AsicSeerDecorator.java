package mn.foreman.antminer;

import mn.foreman.cgminer.CgMiner;
import mn.foreman.cgminer.request.CgMinerCommand;
import mn.foreman.cgminer.request.CgMinerRequest;
import mn.foreman.model.AsicAction;
import mn.foreman.model.error.EmptySiteException;
import mn.foreman.model.error.MinerException;
import mn.foreman.model.error.NotAuthenticatedException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * An {@link AsicSeerDecorator} provides a decorator that will prevent an action
 * from being performed if the ASIC indicates that it's running asicseer
 * firmware.
 */
public class AsicSeerDecorator
        implements AsicAction.CompletableAction {

    /** The logger for this class. */
    private static final Logger LOG =
            LoggerFactory.getLogger(AsicSeerDecorator.class);

    /** The real action. */
    private final AsicAction.CompletableAction realAction;

    /**
     * Constructor.
     *
     * @param realAction The real action.
     */
    public AsicSeerDecorator(final AsicAction.CompletableAction realAction) {
        this.realAction = realAction;
    }

    @Override
    public boolean run(
            final String ip,
            final int port,
            final Map<String, Object> args)
            throws NotAuthenticatedException, MinerException {
        final AtomicBoolean isSeer = new AtomicBoolean(false);
        final CgMiner cgMiner =
                new CgMiner.Builder()
                        .setApiIp(ip)
                        .setApiPort(args.get("apiPort").toString())
                        .addRequest(
                                new CgMinerRequest.Builder()
                                        .setCommand(CgMinerCommand.VERSION)
                                        .build(),
                                (builder, response) -> isSeer.set(isSeer(response.getValues())))
                        .build();
        try {
            cgMiner.getStats();
        } catch (final Exception e) {
            // Ignore - expected
        }

        if (isSeer.get()) {
            LOG.warn("Attempted to run an asicseer action that can't be performed");
            throw new MinerException("Must be changed from seer Remote Config");
        }

        return this.realAction.run(
                ip,
                port,
                args);
    }

    /**
     * Returns whether or not the version indicates asicseer.
     *
     * @param responseValues The values.
     *
     * @return Whether or not the version indicates asicseer.
     */
    private static boolean isSeer(
            final Map<String, List<Map<String, String>>> responseValues) {
        boolean isSeer = false;
        try {
            final Map<String, String> version =
                    AntminerUtils.toVersion(responseValues);
            isSeer =
                    version
                            .values()
                            .stream()
                            .anyMatch(s -> s.contains("asicseer"));
        } catch (final EmptySiteException e) {
            // Ignore
        }
        return isSeer;
    }
}
