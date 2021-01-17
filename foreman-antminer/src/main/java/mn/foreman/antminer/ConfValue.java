package mn.foreman.antminer;

import mn.foreman.api.model.Pool;

import java.util.List;
import java.util.Map;

/** A common interface for a configuration value and how it's to be handled. */
public interface ConfValue {

    /**
     * Obtains the prop value and adds the appropriate property.
     *
     * @param parameters The parameters.
     * @param confValues The conf values.
     * @param pools      The pools.
     * @param dest       The destination.
     */
    void getAndSet(
            Map<String, Object> parameters,
            Map<String, Object> confValues,
            List<Pool> pools,
            List<Map<String, Object>> dest);

    /** Provides a mechanism for adding parameters. */
    @FunctionalInterface
    interface Setter {

        /**
         * Obtains the prop value and adds the appropriate property.
         *
         * @param parameters The parameters.
         * @param confValues The conf values.
         * @param pools      The pools.
         * @param dest       The destination.
         */
        void getAndSet(
                Map<String, Object> parameters,
                Map<String, Object> confValues,
                List<Pool> pools,
                List<Map<String, Object>> dest);
    }
}
