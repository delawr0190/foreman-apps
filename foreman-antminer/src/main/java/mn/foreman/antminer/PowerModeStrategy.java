package mn.foreman.antminer;

import mn.foreman.model.miners.asic.Asic;

import java.util.Map;

/** A strategy for determining the power mode. */
public interface PowerModeStrategy {

    /**
     * Sets the power mode.
     *
     * @param builder             The builder.
     * @param values              The values.
     * @param hasErrors           Whether or not hardware errors.
     * @param hasFunctioningChips Whether or not there are functioning chips.
     */
    void setPowerMode(
            Asic.Builder builder,
            Map<String, String> values,
            boolean hasErrors,
            boolean hasFunctioningChips);
}
