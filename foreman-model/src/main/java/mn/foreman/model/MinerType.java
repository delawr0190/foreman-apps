package mn.foreman.model;

/**
 * A {@link MinerType} provides a mechanism for specifying the miner type in
 * configuration files and for grouping in Foreman.
 */
public interface MinerType {

    /**
     * Returns the label.
     *
     * @return The label.
     */
    String getLabel();
}