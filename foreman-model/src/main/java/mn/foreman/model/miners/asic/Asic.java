package mn.foreman.model.miners.asic;

import mn.foreman.model.AbstractBuilder;
import mn.foreman.model.miners.BigDecimalSerializer;
import mn.foreman.model.miners.FanInfo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.MapDifference;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.math.BigDecimal;
import java.util.*;

/**
 * The following POJO represents a JSON object with the following format:
 *
 * <pre>
 *   {
 *     "hashRate": 13674000000000.52,
 *     "boards": 2,
 *     "fans": {
 *       "num": 2,
 *       "speeds": [
 *         4080,
 *         4560
 *       ]
 *     },
 *     "temps": [
 *       56,
 *       52,
 *       54,
 *       71,
 *       67,
 *       69
 *     ],
 *     "powerState": "",
 *     "powerMode": "normal",
 *     "hasErrors": false,
 *     "rawStats": {}
 *   }
 * </pre>
 *
 * <p>Note: the raw stats that are sent above are a subset of the raw stats
 * that are returned by a miner. The stats are based on a user-defined whitelist
 * of stats.</p>
 */
public class Asic {

    /** Miscellaneous rig attributes. */
    private final List<Map<String, String>> attributes;

    /** The board count. */
    private final int boards;

    /** The fan readings. */
    private final FanInfo fans;

    /** Whether or not errors were reported. */
    private final Boolean hasErrors;

    /** The hash rate. */
    @JsonSerialize(using = BigDecimalSerializer.class)
    private final BigDecimal hashRate;

    /** The power mode. */
    private final PowerMode powerMode;

    /** The power state. */
    private final String powerState;

    /** The whitelisted raw stats. */
    private final Map<String, Object> rawStats;

    /** The temp sensor readings. */
    private final List<Integer> temps;

    /**
     * Constructor.
     *
     * @param hashRate   The hash rate.
     * @param boards     The board count.
     * @param powerMode  The power mode.
     * @param fans       The fan information.
     * @param temps      The temperatures.
     * @param powerState The power state.
     * @param hasErrors  Whether or not errors were observed.
     * @param attributes Rig attributes.
     * @param rawStats   The flattended raw stats.
     */
    private Asic(
            @JsonProperty("hashRate") final BigDecimal hashRate,
            @JsonProperty("boards") final int boards,
            @JsonProperty("powerMode") final PowerMode powerMode,
            @JsonProperty("fans") final FanInfo fans,
            @JsonProperty("temps") final List<Integer> temps,
            @JsonProperty("powerState") final String powerState,
            @JsonProperty("hasErrors") final Boolean hasErrors,
            @JsonProperty("attributes") final List<Map<String, String>> attributes,
            @JsonProperty("rawStats") final Map<String, Object> rawStats) {
        Validate.notNull(
                hashRate,
                "hashRate cannot be null");
        Validate.notNull(
                fans,
                "fans cannot be null");
        Validate.notNull(
                temps,
                "temps cannot be null");
        Validate.notNull(
                hasErrors,
                "hasErrors cannot be null");
        this.hashRate = hashRate;
        this.boards = boards;
        this.powerMode = powerMode;
        this.fans = fans;
        this.temps = new ArrayList<>(temps);
        this.powerState = powerState;
        this.hasErrors = hasErrors;
        this.attributes = new ArrayList<>(attributes);
        this.rawStats = rawStats;
    }

    @Override
    public boolean equals(final Object other) {
        final boolean isEqual;
        if (other == null) {
            isEqual = false;
        } else if (getClass() != other.getClass()) {
            isEqual = false;
        } else {
            final Asic asic = (Asic) other;
            final MapDifference<String, Object> diff =
                    Maps.difference(
                            this.rawStats,
                            asic.rawStats);
            isEqual =
                    diff.areEqual() &&
                            new EqualsBuilder()
                                    .append(this.hashRate, asic.hashRate)
                                    .append(this.boards, asic.boards)
                                    .append(this.powerMode, asic.powerMode)
                                    .append(this.fans, asic.fans)
                                    .append(this.temps, asic.temps)
                                    .append(this.powerState, asic.powerState)
                                    .append(this.hasErrors, asic.hasErrors)
                                    .append(this.attributes, asic.attributes)
                                    .isEquals();
        }
        return isEqual;
    }

    /**
     * Returns the attributes.
     *
     * @return The attributes.
     */
    public List<Map<String, String>> getAttributes() {
        return Collections.unmodifiableList(this.attributes);
    }

    /**
     * Returns the boards.
     *
     * @return The boards.
     */
    public int getBoards() {
        return this.boards;
    }

    /**
     * Returns the {@link FanInfo}.
     *
     * @return The {@link FanInfo}.
     */
    public FanInfo getFans() {
        return this.fans;
    }

    /**
     * Returns whether or not errors were observed.
     *
     * @return Whether or not errors were observed.
     */
    public Boolean getHasErrors() {
        return this.hasErrors;
    }

    /**
     * Returns the hash rate.
     *
     * @return The hash rate.
     */
    public BigDecimal getHashRate() {
        return this.hashRate;
    }

    /**
     * Returns the power mode.
     *
     * @return The power mode.
     */
    public PowerMode getPowerMode() {
        return this.powerMode;
    }

    /**
     * Returns the power state.
     *
     * @return The power state.
     */
    public String getPowerState() {
        return this.powerState;
    }

    /**
     * Returns the whitelisted raw stats.
     *
     * @return The whitelisted raw stats.
     */
    public Map<String, Object> getRawStats() {
        return Collections.unmodifiableMap(this.rawStats);
    }

    /**
     * Returns the temps.
     *
     * @return The temps.
     */
    public List<Integer> getTemps() {
        return Collections.unmodifiableList(this.temps);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(this.hashRate)
                .append(this.boards)
                .append(this.powerMode)
                .append(this.fans)
                .append(this.temps)
                .append(this.powerState)
                .append(this.hasErrors)
                .append(this.attributes)
                .append(this.rawStats)
                .hashCode();
    }

    @Override
    public String toString() {
        return String.format(
                "%s [ " +
                        "hashRate=%s, " +
                        "boards=%d, " +
                        "powerMode=%s, " +
                        "fans=%s, " +
                        "temps=%s, " +
                        "powerState=%s, " +
                        "hasErrors=%s, " +
                        "attributes=%s, " +
                        "rawStats=%s" +
                        " ]",
                getClass().getSimpleName(),
                this.hashRate,
                this.boards,
                this.powerMode,
                this.fans,
                this.temps,
                this.powerState,
                this.hasErrors,
                this.attributes,
                this.rawStats);
    }

    /** ASIC power modes. */
    public enum PowerMode {

        /** Sleeping. */
        @JsonProperty("sleeping")
        SLEEPING,

        /** Low power. */
        @JsonProperty("low")
        LOW,

        /** Normal power. */
        @JsonProperty("normal")
        NORMAL,

        /** High power. */
        @JsonProperty("high")
        HIGH;

        /**
         * Returns the name.
         *
         * @return The name.
         */
        @JsonValue
        public String getName() {
            return this.name().toLowerCase();
        }
    }

    /** A builder for creating {@link Asic ASICs}. */
    public static class Builder
            extends AbstractBuilder<Asic> {

        /** The attributes. */
        private final List<Map<String, String>> attributes = new ArrayList<>();

        /** The raw json. */
        private final Map<String, Object> rawStats = new LinkedHashMap<>();

        /** The temperatures. */
        private final List<Integer> temps = new LinkedList<>();

        /** The number of boards. */
        private int boards = -1;

        /** The fan information. */
        private FanInfo fanInfo;

        /** Whether or not errors were observed. */
        private Boolean hasErrors = UNDEFINED_BOOL;

        /** The hash rate. */
        private BigDecimal hashRate;

        /** The power mode. */
        private PowerMode powerMode = PowerMode.NORMAL;

        /** The power state. */
        private String powerState = null;

        /**
         * Adds a rig attribute.
         *
         * @param key   The key.
         * @param value The value.
         *
         * @return This builder instance.
         */
        public Asic.Builder addAttribute(
                final String key,
                final String value) {
            if ((key != null) && (!key.isEmpty()) &&
                    (value != null) && (!value.isEmpty())) {
                this.attributes.add(
                        ImmutableMap.of(
                                "key",
                                key,
                                "value",
                                value));
            }
            return this;
        }

        /**
         * Adds the provided attributes.
         *
         * @param attributes The attributes to add.
         *
         * @return This builder instance.
         */
        public Asic.Builder addAttributes(
                final List<Map<String, String>> attributes) {
            if (attributes != null) {
                attributes.forEach(this::addAttributes);
            }
            return this;
        }

        /**
         * Adds the provided attributes.
         *
         * @param attributes The attributes to add.
         *
         * @return This builder instance.
         */
        public Asic.Builder addAttributes(final Map<String, String> attributes) {
            if (attributes != null) {
                this.attributes.add(new HashMap<>(attributes));
            }
            return this;
        }

        /**
         * Adds the provided values.
         *
         * @param values The values.
         *
         * @return This builder instance.
         */
        public Builder addRawStats(final Map<String, Object> values) {
            for (final Map.Entry<String, Object> entry : values.entrySet()) {
                this.rawStats.put(
                        entry.getKey()
                                .toLowerCase()
                                .replace(" ", "_"),
                        entry.getValue());
            }
            return this;
        }

        /**
         * Adds a new temperature reading.
         *
         * @param temp The new temperature reading.
         *
         * @return The builder instance.
         */
        public Builder addTemp(final String temp) {
            if ((temp != null) && !temp.isEmpty() && !temp.replace("-", "").isEmpty()) {
                final List<String> temps = new LinkedList<>();
                if (StringUtils.countMatches(temp, "-") > 1) {
                    String sign = "";
                    for (final String tempValue : temp.split("-")) {
                        if (tempValue != null && !tempValue.isEmpty()) {
                            temps.add(sign + tempValue);
                            sign = "";
                        } else {
                            sign = "-";
                        }
                    }
                } else {
                    temps.add(temp);
                }
                temps.forEach(s -> addTemp(Double.valueOf(s).intValue()));
            }
            return this;
        }

        /**
         * Adds a new temperature reading.
         *
         * @param temp The new temperature reading.
         *
         * @return The builder instance.
         */
        public Builder addTemp(final int temp) {
            // Ignore temps of 0, as many miners report metrics for sensors
            // that aren't being used.  If there's a problem, no sensors
            // will display a temperature and an error will display
            if (temp != 0) {
                this.temps.add(temp);
            }
            return this;
        }

        /**
         * Adds the provided temps.
         *
         * @param temps The temps to add.
         *
         * @return This builder instance.
         */
        public Builder addTemps(final List<String> temps) {
            temps.forEach(this::addTemp);
            return this;
        }

        @Override
        public Asic build() {
            return new Asic(
                    this.hashRate,
                    this.boards,
                    this.powerMode,
                    this.fanInfo,
                    this.temps,
                    this.powerState,
                    this.hasErrors,
                    this.attributes,
                    this.rawStats);
        }

        /**
         * Creates a builder from the asic provided.
         *
         * @param asic The asic to reference.
         *
         * @return This builder instance.
         */
        public Builder fromAsic(final Asic asic) {
            if (asic != null) {
                setBoards(asic.boards);
                setPowerMode(asic.powerMode);
                setFanInfo(asic.fans);
                hasErrors(asic.hasErrors);
                setHashRate(asic.hashRate);
                setPowerState(asic.powerState);
                asic.temps.forEach(this::addTemp);
                addAttributes(asic.attributes);
                addRawStats(asic.rawStats);
            }
            return this;
        }

        /**
         * Returns the attributes.
         *
         * @return The attributes.
         */
        public List<Map<String, String>> getAttributes() {
            return Collections.unmodifiableList(this.attributes);
        }

        /**
         * Sets whether or not errors were observed.
         *
         * @param hasErrors Whether or not errors were observed.
         *
         * @return The builder instance.
         */
        public Builder hasErrors(final Boolean hasErrors) {
            this.hasErrors = hasErrors;
            return this;
        }

        /**
         * Returns whether or not the builder has fans.
         *
         * @return Whether or not the builder has fans.
         */
        public boolean hasFans() {
            return this.fanInfo != null;
        }

        /**
         * Sets the board count.
         *
         * @param boards The board count.
         *
         * @return This builder instance.
         */
        public Asic.Builder setBoards(final String boards) {
            return setBoards(Integer.parseInt(boards));
        }

        /**
         * Sets the board count.
         *
         * @param boards The board count.
         *
         * @return This builder instance.
         */
        public Asic.Builder setBoards(final long boards) {
            return setBoards((int) boards);
        }

        /**
         * Sets the board count.
         *
         * @param boards The board count.
         *
         * @return This builder instance.
         */
        public Asic.Builder setBoards(final int boards) {
            this.boards = boards;
            return this;
        }

        /**
         * Sets the compile time.
         *
         * @param time The compile time.
         *
         * @return This builder instance.
         */
        public Asic.Builder setCompileTime(final String time) {
            if (time != null && !time.isEmpty()) {
                addAttribute(
                        "compile_time",
                        time);
            }
            return this;
        }

        /**
         * Sets the fan information.
         *
         * @param fanInfo The fan info.
         *
         * @return The builder instance.
         */
        public Builder setFanInfo(final FanInfo fanInfo) {
            this.fanInfo = fanInfo;
            return this;
        }

        /**
         * Sets the hash rate.
         *
         * @param hashRate The hash rate.
         *
         * @return The builder instance.
         */
        public Builder setHashRate(final BigDecimal hashRate) {
            this.hashRate = hashRate;
            return this;
        }

        /**
         * Sets the type.
         *
         * @param type The type.
         *
         * @return This builder instance.
         */
        public Asic.Builder setMinerType(final String type) {
            if (type != null && !type.isEmpty()) {
                addAttribute(
                        "miner_type",
                        type);
            }
            return this;
        }

        /**
         * Sets the rig id
         *
         * @param rigId The rig id.
         *
         * @return This builder instance.
         */
        public Asic.Builder setMrrRigId(final String rigId) {
            if (rigId != null && !rigId.isEmpty()) {
                addAttribute(
                        "mrr_rig_id",
                        rigId);
            }
            return this;
        }

        /**
         * Sets the power mode.
         *
         * @param powerMode The mode.
         *
         * @return This builder instance.
         */
        public Asic.Builder setPowerMode(final PowerMode powerMode) {
            this.powerMode = powerMode;
            return this;
        }

        /**
         * Sets the power state.
         *
         * @param powerState The power state.
         *
         * @return This builder instance.
         */
        public Builder setPowerState(final String powerState) {
            if (powerState != null) {
                this.powerState = powerState.toLowerCase();
            }
            return this;
        }
    }
}