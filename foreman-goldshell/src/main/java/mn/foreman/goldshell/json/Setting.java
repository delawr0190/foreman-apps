package mn.foreman.goldshell.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/** A response from <code>/mcb/setting</code>. */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Setting {

    /** The LED control flag. */
    @JsonProperty("ledcontrol")
    public boolean ledControl;

    /** The manual flag. */
    @JsonProperty("manual")
    public boolean manual;

    /** The power plan. */
    @JsonProperty("manualPowerplan")
    public String manualPowerPlan;

    /** The MAC. */
    @JsonProperty("name")
    public String name;

    /** The power plans. */
    @JsonProperty("powerplans")
    public List<PowerPlan> powerPlans;

    /** The select value. */
    @JsonProperty("select")
    public int select;

    /** The temp control. */
    @JsonProperty("tempcontrol")
    public boolean tempControl;

    /** The version. */
    @JsonProperty("version")
    public String version;

    /** A model representation of a power plan. */
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class PowerPlan {

        /** The info. */
        @JsonProperty("info")
        public String info;

        /** The level. */
        @JsonProperty("level")
        public int level;
    }
}
