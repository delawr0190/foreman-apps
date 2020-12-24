package mn.foreman.api.miners;

import mn.foreman.api.JsonUtils;
import mn.foreman.api.WebUtil;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class MinersImpl
        implements Miners {

    /** The client ID. */
    private final String clientId;

    /** The mapper. */
    private final ObjectMapper objectMapper;

    /** The pickaxe ID. */
    private final String pickaxeId;

    /** The web util. */
    private final WebUtil webUtil;

    /**
     * Constructor.
     *
     * @param clientId     The client ID.
     * @param pickaxeId    The pickaxe ID.
     * @param objectMapper The mapper.
     * @param webUtil      The web util.
     */
    public MinersImpl(
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
    public List<Miner> all() {
        return this.webUtil.get(
                String.format(
                        "/api/miners/%s/%s",
                        this.clientId,
                        this.pickaxeId))
                .flatMap(
                        s -> JsonUtils.fromJson(
                                s,
                                this.objectMapper,
                                new TypeReference<List<Miner>>() {
                                }))
                .orElse(Collections.emptyList());
    }

    @Override
    public Optional<Miner> one(final int minerId) {
        return this.webUtil.get(
                String.format(
                        "/api/miners/%s/%s/%d",
                        this.clientId,
                        this.pickaxeId,
                        minerId))
                .flatMap(
                        s -> JsonUtils.fromJson(
                                s,
                                this.objectMapper,
                                new TypeReference<Miner>() {
                                }));
    }
}
