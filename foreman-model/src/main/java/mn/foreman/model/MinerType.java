package mn.foreman.model;

/**
 * A {@link MinerType} provides a mechanism for specifying the miner type in
 * configuration files and for grouping in Foreman.
 */
public interface MinerType {

    /**
     * Returns the category.
     *
     * @return The category.
     */
    Category getCategory();

    /**
     * Returns the dashboard ID.
     *
     * @return The dashboard ID.
     */
    String getSlug();

    /** A {@link Category} represents a Foreman dashboard miner category. */
    enum Category {

        /** The ASIC category. */
        ASIC("ASIC");

        /** The category name. */
        private final String name;

        /**
         * Constructor.
         *
         * @param name The category name.
         */
        Category(final String name) {
            this.name = name;
        }

        /**
         * Returns the category name.
         *
         * @return The category name.
         */
        public String getName() {
            return this.name;
        }
    }
}