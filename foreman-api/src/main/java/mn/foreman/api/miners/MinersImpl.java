package mn.foreman.api.miners;

import mn.foreman.api.JsonUtils;
import mn.foreman.api.WebUtil;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class MinersImpl
        implements Miners {

    /** The logger for this class. */
    private static final Logger LOG =
            LoggerFactory.getLogger(MinersImpl.class);

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

    @Override
    public Optional<Miner> update(
            final int minerId,
            final String name,
            final String apiIp,
            final String platform,
            final String minerType,
            final String serial) {
        Optional<Miner> result = Optional.empty();
        try {
            final Map<String, String> args = new HashMap<>();
            args.put("name", name);
            args.put("platform", platform);
            args.put("minerType", minerType);
            if (apiIp != null) {
                args.put("apiIp", apiIp);
            }
            if (serial != null) {
                args.put("serial", serial);
            }
            final Optional<String> response =
                    this.webUtil.put(
                            String.format(
                                    "/api/miners/%s/%s/%d",
                                    this.clientId,
                                    this.pickaxeId,
                                    minerId),
                            this.objectMapper.writeValueAsString(args));
            if (response.isPresent()) {
                result =
                        JsonUtils.fromJson(
                                response.get(),
                                this.objectMapper,
                                new TypeReference<Miner>() {
                                });
            }
        } catch (final JsonProcessingException e) {
            LOG.warn("Exception occurred while parsing json", e);
        }
        return result;
    }
}
