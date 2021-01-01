package mn.foreman.api;

import mn.foreman.api.actions.Actions;
import mn.foreman.api.miners.Miners;
import mn.foreman.api.pickaxe.Pickaxe;
import mn.foreman.api.sitemap.SiteMap;

/**
 * A {@link ForemanApi} provides a mechanism for interacting with the Foreman
 * API.
 */
public interface ForemanApi {

    /**
     * Creates a new {@link Actions} that can be leveraged to operate on the
     * <code>/api/actions</code> Foreman API endpoint.
     *
     * @return The API handler.
     */
    Actions actions();

    /**
     * Creates a new {@link Miners} that can be leveraged to operate on the
     * <code>/api/miners</code> Foreman API endpoint.
     *
     * @return The API handler.
     */
    Miners miners();

    /**
     * Creates a new {@link Pickaxe} that can be leveraged to operate on the
     * <code>/api/pickaxe</code> Foreman API endpoint.
     *
     * @return The API handler.
     */
    Pickaxe pickaxe();

    /**
     * Creates a new {@link SiteMap} that can be leveraged to operate on the
     * <code>/api/site-map</code> Foreman API endpoint.
     *
     * @return The API handler.
     */
    SiteMap siteMap();
}
