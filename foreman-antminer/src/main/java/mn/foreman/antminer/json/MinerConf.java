package mn.foreman.antminer.json;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/** A pojo representing an update to an Antminer configuration. */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({
        "pool1Url",
        "pool1User",
        "pool1Pass",
        "pool2Url",
        "pool2User",
        "pool2Pass",
        "pool3Url",
        "pool3User",
        "pool3Pass",
        "noBeeper",
        "noTempOverCtrl",
        "customizeFanSwitch",
        "customizeFanValue",
        "freq"
})
public class MinerConf {

    /** Whether or not the fan speed has been customized. */
    @JsonProperty("_ant_fan_customize_switch")
    @JsonAlias("bitmain-fan-ctrl")
    public Boolean customizeFanSwitch;

    /** The fan custom speed. */
    @JsonProperty("_ant_fan_customize_value")
    @JsonAlias("bitmain-fan-pwm")
    public int customizeFanValue;

    /** The frequency. */
    @JsonProperty("_ant_freq")
    @JsonAlias("bitmain-freq")
    public String freq;

    /** The no beeper value. */
    @JsonProperty("_ant_nobeeper")
    @JsonAlias("bitmain-nobeeper")
    public Boolean noBeeper;

    /** The no temp over ctrl value. */
    @JsonProperty("_ant_notempoverctrl")
    @JsonAlias("bitmain-notempoverctrl")
    public Boolean noTempOverCtrl;
}
