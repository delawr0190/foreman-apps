package mn.foreman.strongu;

import mn.foreman.antminer.ConfValue;
import mn.foreman.antminer.ConfValueUtils;
import mn.foreman.model.Pool;

import com.google.common.collect.ImmutableMap;

import java.util.List;
import java.util.Map;

/**
 * All of the conf values that need to be obtained and provided to configure a
 * strongu.
 */
public enum StrongUConfValue
        implements ConfValue {

    /** Pool 1 url. */
    POOL_1_URL((params, confValues, pools, dest) ->
            ConfValueUtils.addPoolField(
                    dest,
                    "_stu_pool%durl",
                    pools,
                    0,
                    Pool::getUrl,
                    1)),

    /** Pool 1 user. */
    POOL_1_USER((params, confValues, pools, dest) ->
            ConfValueUtils.addPoolField(
                    dest,
                    "_stu_pool%duser",
                    pools,
                    0,
                    Pool::getUsername,
                    1)),

    /** Pool 1 pass. */
    POOL_1_PASS((params, confValues, pools, dest) ->
            ConfValueUtils.addPoolField(
                    dest,
                    "_stu_pool%dpw",
                    pools,
                    0,
                    Pool::getPassword,
                    1)),

    /** Pool 2 url. */
    POOL_2_URL((params, confValues, pools, dest) ->
            ConfValueUtils.addPoolField(
                    dest,
                    "_stu_pool%durl",
                    pools,
                    1,
                    Pool::getUrl,
                    2)),

    /** Pool 2 user. */
    POOL_2_USER((params, confValues, pools, dest) ->
            ConfValueUtils.addPoolField(
                    dest,
                    "_stu_pool%duser",
                    pools,
                    1,
                    Pool::getUsername,
                    2)),

    /** Pool 2 pass. */
    POOL_2_PASS((params, confValues, pools, dest) ->
            ConfValueUtils.addPoolField(
                    dest,
                    "_stu_pool%dpw",
                    pools,
                    1,
                    Pool::getPassword,
                    2)),

    /** Pool 3 url. */
    POOL_3_URL((params, confValues, pools, dest) ->
            ConfValueUtils.addPoolField(
                    dest,
                    "_stu_pool%durl",
                    pools,
                    2,
                    Pool::getUrl,
                    3)),

    /** Pool 3 user. */
    POOL_3_USER((params, confValues, pools, dest) ->
            ConfValueUtils.addPoolField(
                    dest,
                    "_stu_pool%duser",
                    pools,
                    2,
                    Pool::getUsername,
                    3)),

    /** Pool 3 pass. */
    POOL_3_PASS((params, confValues, pools, dest) ->
            ConfValueUtils.addPoolField(
                    dest,
                    "_stu_pool%dpw",
                    pools,
                    2,
                    Pool::getPassword,
                    3)),

    /** No beeper. */
    NO_BEEPER((params, confValues, pools, dest) ->
            dest.add(
                    ImmutableMap.of(
                            "key",
                            "_stu_nobeeper",
                            "value",
                            false))),

    /** No temp over ctrl. */
    NO_TEMP_OVER_CTRL((params, confValues, pools, dest) ->
            dest.add(
                    ImmutableMap.of(
                            "key",
                            "_stu_notempoverctrl",
                            "value",
                            false))),

    /** Fan pwm. */
    FAN_PWM((params, confValues, pools, dest) ->
            ConfValueUtils.addField(
                    "fan0",
                    "_stu_fan_customize_value",
                    confValues,
                    dest)),

    /** Freq 1. */
    FREQ_1((params, confValues, pools, dest) ->
            ConfValueUtils.addField(
                    "pll0",
                    "_stu_freq1",
                    confValues,
                    dest)),

    /** Freq 2. */
    FREQ_2((params, confValues, pools, dest) ->
            ConfValueUtils.addField(
                    "pll1",
                    "_stu_freq2",
                    confValues,
                    dest)),

    /** Freq 3. */
    FREQ_3((params, confValues, pools, dest) ->
            ConfValueUtils.addField(
                    "pll2",
                    "_stu_freq3",
                    confValues,
                    dest)),

    /** Freq 4. */
    FREQ_4((params, confValues, pools, dest) ->
            ConfValueUtils.addField(
                    "pll3",
                    "_stu_freq4",
                    confValues,
                    dest)),

    /** Work volt. */
    WORK_VOLT((params, confValues, pools, dest) ->
            ConfValueUtils.addField(
                    "workvolt",
                    "_stu_workvolt",
                    confValues,
                    dest)),

    /** Start volt. */
    START_VOLT((params, confValues, pools, dest) ->
            ConfValueUtils.addField(
                    "startvol",
                    "_stu_startvol",
                    confValues,
                    dest)),

    /** PLL start. */
    PLL_START((params, confValues, pools, dest) ->
            ConfValueUtils.addField(
                    "pllstart",
                    "_stu_pllstart",
                    confValues,
                    dest)),

    /** PLL step. */
    PLL_STEP((params, confValues, pools, dest) ->
            ConfValueUtils.addField(
                    "pllstep",
                    "_stu_pllstep",
                    confValues,
                    dest));

    /** The setter. */
    private Setter setter;

    /**
     * Constructor.
     *
     * @param setter The setter.
     */
    StrongUConfValue(final Setter setter) {
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
