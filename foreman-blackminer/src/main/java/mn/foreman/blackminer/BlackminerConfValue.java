package mn.foreman.blackminer;

import mn.foreman.antminer.ConfValue;
import mn.foreman.antminer.ConfValueUtils;
import mn.foreman.api.model.Pool;

import com.google.common.collect.ImmutableMap;

import java.util.List;
import java.util.Map;

/**
 * All of the conf values that need to be obtained and provided to configure a
 * blackminer.
 */
public enum BlackminerConfValue
        implements ConfValue {

    /** Pool 1 url. */
    POOL_1_URL((params, confValues, pools, dest) ->
            ConfValueUtils.addPoolField(
                    dest,
                    "_bb_pool%durl",
                    pools,
                    0,
                    Pool::getUrl,
                    1)),

    /** Pool 1 user. */
    POOL_1_USER((params, confValues, pools, dest) ->
            ConfValueUtils.addPoolField(
                    dest,
                    "_bb_pool%duser",
                    pools,
                    0,
                    Pool::getUsername,
                    1)),

    /** Pool 1 pass. */
    POOL_1_PASS((params, confValues, pools, dest) ->
            ConfValueUtils.addPoolField(
                    dest,
                    "_bb_pool%dpw",
                    pools,
                    0,
                    Pool::getPassword,
                    1)),

    /** Pool 2 url. */
    POOL_2_URL((params, confValues, pools, dest) ->
            ConfValueUtils.addPoolField(
                    dest,
                    "_bb_pool%durl",
                    pools,
                    1,
                    Pool::getUrl,
                    2)),

    /** Pool 2 user. */
    POOL_2_USER((params, confValues, pools, dest) ->
            ConfValueUtils.addPoolField(
                    dest,
                    "_bb_pool%duser",
                    pools,
                    1,
                    Pool::getUsername,
                    2)),

    /** Pool 2 pass. */
    POOL_2_PASS((params, confValues, pools, dest) ->
            ConfValueUtils.addPoolField(
                    dest,
                    "_bb_pool%dpw",
                    pools,
                    1,
                    Pool::getPassword,
                    2)),

    /** Pool 3 url. */
    POOL_3_URL((params, confValues, pools, dest) ->
            ConfValueUtils.addPoolField(
                    dest,
                    "_bb_pool%durl",
                    pools,
                    2,
                    Pool::getUrl,
                    3)),

    /** Pool 3 user. */
    POOL_3_USER((params, confValues, pools, dest) ->
            ConfValueUtils.addPoolField(
                    dest,
                    "_bb_pool%duser",
                    pools,
                    2,
                    Pool::getUsername,
                    3)),

    /** Pool 3 pass. */
    POOL_3_PASS((params, confValues, pools, dest) ->
            ConfValueUtils.addPoolField(
                    dest,
                    "_bb_pool%dpw",
                    pools,
                    2,
                    Pool::getPassword,
                    3)),

    /** No beeper. */
    NO_BEEPER((params, confValues, pools, dest) ->
            dest.add(
                    ImmutableMap.of(
                            "key",
                            "_bb_nobeeper",
                            "value",
                            "false"))),

    /** No temp over ctrl. */
    NO_TEMP_OVER_CTRL((params, confValues, pools, dest) ->
            dest.add(
                    ImmutableMap.of(
                            "key",
                            "_bb_notempoverctrl",
                            "value",
                            "false"))),

    /** Fan ctrl. */
    FAN_CTRL((params, confValues, pools, dest) ->
            dest.add(
                    ImmutableMap.of(
                            "key",
                            "_bb_fan_customize_switch",
                            "value",
                            "false"))),

    /** Fan pwm. */
    FAN_PWM((params, confValues, pools, dest) ->
            dest.add(
                    ImmutableMap.of(
                            "key",
                            "_bb_fan_customize_value",
                            "value",
                            ""))),

    /** Freq. */
    FREQ((params, confValues, pools, dest) ->
            ConfValueUtils.addField(
                    "freq",
                    "_bb_freq",
                    confValues,
                    dest)),

    /** Coin type. */
    COIN_TYPE((params, confValues, pools, dest) -> {
        final String newAlgo = (String) params.get("algo");
        if (newAlgo != null && !newAlgo.isEmpty()) {
            // Algo was changed
            dest.add(
                    ImmutableMap.of(
                            "key",
                            "_bb_coin_type",
                            "value",
                            params.get("algo")));
        } else {
            // Copy through the old algorithm
            ConfValueUtils.addField(
                    "coin-type",
                    "_bb_coin_type",
                    confValues,
                    dest);
        }
    });

    /** The setter. */
    private Setter setter;

    /**
     * Constructor.
     *
     * @param setter The setter.
     */
    BlackminerConfValue(final Setter setter) {
        this.setter = setter;
    }

    @Override
    public void getAndSet(
            final Map<String, Object> parameters,
            final Map<String, Object> confValues,
            final List<Pool> pools,
            final List<Map<String, Object>> dest) {
        this.setter.getAndSet(parameters, confValues, pools, dest);
    }
}
