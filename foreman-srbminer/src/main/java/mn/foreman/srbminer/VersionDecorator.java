package mn.foreman.srbminer;

import mn.foreman.io.Query;
import mn.foreman.model.Miner;
import mn.foreman.model.MinerID;
import mn.foreman.model.error.MinerException;
import mn.foreman.model.miners.MinerStats;

import com.fasterxml.jackson.core.type.TypeReference;

import java.util.Map;
import java.util.Optional;

/** Determines the version of srbminer that's running and uses it. */
public class VersionDecorator
        implements Miner {

    /** The new version. */
    private final Miner newVersion;

    /** The old version. */
    private final Miner oldVersion;

    /**
     * Constructor.
     *
     * @param oldVersion The old version.
     * @param newVersion The new version.
     */
    VersionDecorator(
            final Miner oldVersion,
            final Miner newVersion) {
        this.oldVersion = oldVersion;
        this.newVersion = newVersion;
    }

    @Override
    public int getApiPort() {
        return this.newVersion.getApiPort();
    }

    @Override
    public String getIp() {
        return this.newVersion.getIp();
    }

    @Override
    public Optional<String> getMacAddress() {
        return Optional.empty();
    }

    @Override
    public MinerID getMinerID() {
        return this.newVersion.getMinerID();
    }

    @Override
    public MinerStats getStats() throws MinerException {
        final Map<String, Object> response =
                Query.restQuery(
                        this.newVersion.getIp(),
                        this.newVersion.getApiPort(),
                        "/",
                        new TypeReference<Map<String, Object>>() {
                        });
        if (response.containsKey("hashrate_total_now")) {
            return this.oldVersion.getStats();
        } else {
            return this.newVersion.getStats();
        }
    }
}
