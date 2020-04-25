package mn.foreman.api;

/**
 * A {@link ForemanApi} provides a mechanism for interacting with the Foreman
 * API.
 */
public interface ForemanApi {

    /**
     * Creates a new {@link Pickaxe} that can be leveraged to operate on the
     * <code>/api/pickaxe</code> Foreman API endpoint.
     *
     * @return The API handler.
     */
    Pickaxe pickaxe();
}
