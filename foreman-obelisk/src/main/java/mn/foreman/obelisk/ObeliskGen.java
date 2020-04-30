package mn.foreman.obelisk;

import java.util.LinkedList;
import java.util.List;

/** An {@link ObeliskGen} represents all of the existing Obelisk generations. */
public enum ObeliskGen {

    /** obelisk-ob1. */
    Gen1(
            2,
            "Fan %d Speed",
            "RPM"),

    /** obelisk-ob2. */
    Gen2(
            1,
            "Fan Speed",
            "%");

    /** The fan speed key pattern. */
    private final String fanSpeedKeyPattern;

    /** The fan speed units. */
    private final String fanSpeedUnits;

    /** The number of fans. */
    private final int numFans;

    /**
     * Constructor.
     *
     * @param numFans            The number of fans.
     * @param fanSpeedKeyPattern The fan speed key pattern.
     * @param fanSpeedUnits      The fan speed units.
     */
    ObeliskGen(
            final int numFans,
            final String fanSpeedKeyPattern,
            final String fanSpeedUnits) {
        this.numFans = numFans;
        this.fanSpeedKeyPattern = fanSpeedKeyPattern;
        this.fanSpeedUnits = fanSpeedUnits;
    }

    /**
     * Returns all of the fans that can be obtained.
     *
     * @return The fans.
     */
    public List<String> getFanKeys() {
        final List<String> fans = new LinkedList<>();
        for (int i = 1; i <= this.numFans; i++) {
            fans.add(String.format(this.fanSpeedKeyPattern, i));
        }
        return fans;
    }

    /**
     * Returns the fan speed units.
     *
     * @return The fan speed units.
     */
    public String getFanSpeedUnits() {
        return this.fanSpeedUnits;
    }

    /**
     * Returns the number of fans.
     *
     * @return The number of fans.
     */
    public int getNumFans() {
        return this.numFans;
    }
}
