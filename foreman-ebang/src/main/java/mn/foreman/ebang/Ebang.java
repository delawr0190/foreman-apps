package mn.foreman.ebang;

import mn.foreman.ebang.json.AlarmLoop;
import mn.foreman.ebang.json.CgminerStatus;
import mn.foreman.ebang.json.CgminerVal;
import mn.foreman.ebang.json.SystemStatus;
import mn.foreman.model.AbstractMiner;
import mn.foreman.model.MacStrategy;
import mn.foreman.model.error.MinerException;
import mn.foreman.model.miners.FanInfo;
import mn.foreman.model.miners.MinerStats;
import mn.foreman.model.miners.Pool;
import mn.foreman.model.miners.asic.Asic;
import mn.foreman.util.Flatten;
import mn.foreman.util.MrrUtils;
import mn.foreman.util.PoolUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.TimeUnit;

/** Obtains metrics from an Ebang. */
public class Ebang
        extends AbstractMiner {

    /** The json mapper. */
    private final ObjectMapper objectMapper;

    /** The API password. */
    private final String password;

    /** The socket timeout. */
    private final int socketTimeout;

    /** The socket timeout (units). */
    private final TimeUnit socketTimeoutUnits;

    /** The stats whitelist. */
    private final List<String> statsWhitelist;

    /** The API username. */
    private final String username;

    /**
     * Constructor.
     *
     * @param apiIp          The API IP.
     * @param apiPort        The API port.
     * @param username       The username.
     * @param password       The password.
     * @param statsWhitelist The stats whitelist.
     * @param macStrategy    The MAC strategy.
     */
    Ebang(
            final String apiIp,
            final int apiPort,
            final String username,
            final String password,
            final List<String> statsWhitelist,
            final MacStrategy macStrategy,
            final int socketTimeout,
            final TimeUnit socketTimeoutUnits,
            final ObjectMapper objectMapper) {
        super(
                apiIp,
                apiPort,
                macStrategy);
        this.username = username;
        this.password = password;
        this.statsWhitelist = new ArrayList<>(statsWhitelist);
        this.socketTimeout = socketTimeout;
        this.socketTimeoutUnits = socketTimeoutUnits;
        this.objectMapper = objectMapper;
    }

    @Override
    protected void addStats(
            final MinerStats.Builder statsBuilder)
            throws MinerException {
        try {
            final Map<String, Object> rawStats = new LinkedHashMap<>();

            final CgminerStatus cgminerStatus =
                    EbangQuery.query(
                            this.apiIp,
                            this.apiPort,
                            this.username,
                            this.password,
                            "/Cgminer/CgminerStatus",
                            null,
                            Collections.emptyList(),
                            (code, body) ->
                                    this.objectMapper.readValue(
                                            body,
                                            CgminerStatus.class),
                            (code, body) ->
                                    rawStats.putAll(
                                            Flatten.flattenAndFilter(
                                                    body,
                                                    this.statsWhitelist)),
                            this.socketTimeout,
                            this.socketTimeoutUnits)
                            .orElseThrow(() -> new MinerException("Failed to obtain status"));

            final AlarmLoop alarmLoop =
                    EbangQuery.query(
                            this.apiIp,
                            this.apiPort,
                            this.username,
                            this.password,
                            "/alarm/GetAlarmLoop",
                            null,
                            Collections.emptyList(),
                            (code, body) ->
                                    this.objectMapper.readValue(
                                            body,
                                            AlarmLoop.class),
                            (code, body) ->
                                    rawStats.putAll(
                                            Flatten.flattenAndFilter(
                                                    body,
                                                    this.statsWhitelist)),
                            this.socketTimeout,
                            this.socketTimeoutUnits)
                            .orElseThrow(() -> new MinerException("Failed to obtain loop"));

            final SystemStatus systemStatus =
                    EbangQuery.query(
                            this.apiIp,
                            this.apiPort,
                            this.username,
                            this.password,
                            "/Status/getsystemstatus",
                            null,
                            Collections.emptyList(),
                            (code, body) ->
                                    this.objectMapper.readValue(
                                            body,
                                            SystemStatus.class),
                            (code, body) ->
                                    rawStats.putAll(
                                            Flatten.flattenAndFilter(
                                                    body,
                                                    this.statsWhitelist)),
                            this.socketTimeout,
                            this.socketTimeoutUnits)
                            .orElseThrow(() -> new MinerException("Failed to obtain systemstatus"));

            final CgminerVal cgminerVal =
                    EbangQuery.query(
                            this.apiIp,
                            this.apiPort,
                            this.username,
                            this.password,
                            "/Cgminer/CgminerGetVal",
                            null,
                            Collections.emptyList(),
                            (code, body) ->
                                    this.objectMapper.readValue(
                                            body,
                                            CgminerVal.class),
                            (code, body) ->
                                    rawStats.putAll(
                                            Flatten.flattenAndFilter(
                                                    body,
                                                    this.statsWhitelist)),
                            this.socketTimeout,
                            this.socketTimeoutUnits)
                            .orElseThrow(() -> new MinerException("Failed to obtain val"));

            statsBuilder
                    .addAsic(
                            new Asic.Builder()
                                    .setHashRate(
                                            alarmLoop.feedback.hashRate
                                                    .multiply(BigDecimal.valueOf(Math.pow(1000, 2))))
                                    .setFanInfo(
                                            new FanInfo.Builder()
                                                    .setCount(2)
                                                    .addSpeed(systemStatus.feedback.deviceFan)
                                                    .addSpeed(systemStatus.feedback.deviceFan2)
                                                    .setSpeedUnits("RPM")
                                                    .build())
                                    .addTemps(extractTemps(systemStatus.feedback.device1Temp))
                                    .addTemps(extractTemps(systemStatus.feedback.device2Temp))
                                    .addTemp(systemStatus.feedback.device3Temp)
                                    .addTemp(systemStatus.feedback.device4Temp)
                                    .setMrrRigId(
                                            MrrUtils.getRigId(
                                                    cgminerVal.feedback.pool1Url,
                                                    cgminerVal.feedback.pool1Worker))
                                    .setMrrRigId(
                                            MrrUtils.getRigId(
                                                    cgminerVal.feedback.pool2Url,
                                                    cgminerVal.feedback.pool2Worker))
                                    .setMrrRigId(
                                            MrrUtils.getRigId(
                                                    cgminerVal.feedback.pool3Url,
                                                    cgminerVal.feedback.pool3Worker))
                                    .addRawStats(rawStats)
                                    .build())
                    .addPool(
                            new Pool.Builder()
                                    .setName(PoolUtils.sanitizeUrl(cgminerStatus.feedback.pool))
                                    .setWorker(
                                            toWorker(
                                                    cgminerStatus.feedback.pool,
                                                    cgminerVal))
                                    .setStatus(
                                            true,
                                            true)
                                    .setCounts(
                                            cgminerStatus.feedback.accepted,
                                            cgminerStatus.feedback.rejected,
                                            0)
                                    .build());
        } catch (final Exception e) {
            throw new MinerException(e);
        }
    }

    @Override
    protected void addToEquals(
            final EqualsBuilder equalsBuilder,
            final AbstractMiner other) {
        final Ebang otherMiner = (Ebang) other;
        equalsBuilder
                .append(this.username, otherMiner.username)
                .append(this.password, otherMiner.password)
                .append(this.socketTimeout, otherMiner.socketTimeout)
                .append(this.socketTimeoutUnits, otherMiner.socketTimeoutUnits);
    }

    @Override
    protected void addToHashCode(
            final HashCodeBuilder hashCodeBuilder) {
        hashCodeBuilder
                .append(this.username)
                .append(this.password)
                .append(this.socketTimeout)
                .append(this.socketTimeoutUnits);
    }

    @Override
    protected String addToString() {
        return String.format(
                ", username=%s, password=%s, socketTimeout=%d, socketTimeoutUnits=%s",
                this.username,
                this.password,
                this.socketTimeout,
                this.socketTimeoutUnits);
    }

    /**
     * Extracts the temps from the encoded strings.
     *
     * @param temp The temps.
     *
     * @return The temps.
     */
    private static List<String> extractTemps(final String temp) {
        final List<String> newTemps = new LinkedList<>();
        if (temp != null && !temp.isEmpty()) {
            for (final String splitTemp : temp.split("\\|")) {
                newTemps.addAll(Arrays.asList(splitTemp.split(",")));
            }
        }
        return newTemps;
    }

    /**
     * Finds the active worker.
     *
     * @param pool       The pool.
     * @param cgminerVal The cgminer config.
     *
     * @return The worker.
     */
    private static String toWorker(
            final String pool,
            final CgminerVal cgminerVal) {
        if (pool.equals(cgminerVal.feedback.pool1Url)) {
            return cgminerVal.feedback.pool1Worker;
        } else if (pool.equals(cgminerVal.feedback.pool2Url)) {
            return cgminerVal.feedback.pool2Worker;
        } else {
            return cgminerVal.feedback.pool3Worker;
        }
    }
}
