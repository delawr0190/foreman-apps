package mn.foreman.api;

import mn.foreman.api.actions.Actions;
import mn.foreman.api.actions.ActionsImpl;
import mn.foreman.api.miners.Miners;
import mn.foreman.api.miners.MinersImpl;
import mn.foreman.api.pickaxe.Pickaxe;
import mn.foreman.api.pickaxe.PickaxeImpl;

import com.fasterxml.jackson.databind.ObjectMapper;

/** An implementation of a {@link ForemanApi}. */
public class ForemanApiImpl
        implements ForemanApi {

    /** The client ID. */
    private final String clientId;

    /** The json mapper. */
    private final ObjectMapper objectMapper;

    /** The pickaxe ID. */
    private final String pickaxeId;

    /** The web utilities. */
    private final WebUtil webUtil;

    /**
     * Constructor.
     *
     * @param clientId     The client ID.
     * @param pickaxeId    The pickaxe ID.
     * @param objectMapper The mapper.
     * @param webUtil      The web utilities.
     */
    public ForemanApiImpl(
            final String clientId,
            final String pickaxeId,
            final ObjectMapper objectMapper,
            final WebUtil webUtil) {
        this.clientId = clientId;
        this.pickaxeId = pickaxeId;
        this.objectMapper = objectMapper;
        this.webUtil = webUtil;
    }

    @Override
    public Actions actions() {
        return new ActionsImpl(
                this.objectMapper,
                this.webUtil);
    }

    @Override
    public Miners miners() {
        return new MinersImpl(
                this.clientId,
                this.pickaxeId,
                this.objectMapper,
                this.webUtil);
    }

    @Override
    public Pickaxe pickaxe() {
        return new PickaxeImpl(
                this.pickaxeId,
                this.objectMapper,
                this.webUtil);
    }
}