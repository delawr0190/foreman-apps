package mn.foreman.api;

import com.fasterxml.jackson.databind.ObjectMapper;

/** An implementation of a {@link ForemanApi}. */
public class ForemanApiImpl
        implements ForemanApi {

    /** The json mapper. */
    private final ObjectMapper objectMapper;

    /** The pickaxe ID. */
    private final String pickaxeId;

    /** The web utilities. */
    private final WebUtil webUtil;

    /**
     * Constructor.
     *
     * @param pickaxeId    The pickaxe ID.
     * @param objectMapper The mapper.
     * @param webUtil      The web utilities.
     */
    public ForemanApiImpl(
            final String pickaxeId,
            final ObjectMapper objectMapper,
            final WebUtil webUtil) {
        this.pickaxeId = pickaxeId;
        this.objectMapper = objectMapper;
        this.webUtil = webUtil;
    }

    @Override
    public Pickaxe pickaxe() {
        return new PickaxeImpl(
                this.pickaxeId,
                this.objectMapper,
                this.webUtil);
    }
}