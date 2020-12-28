package mn.foreman.api.sitemap;

import mn.foreman.api.JsonUtils;
import mn.foreman.api.WebUtil;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

/** All of the handlers for interacting with the SiteMap API. */
public class SiteMapImpl
        implements SiteMap {

    /** The logger for this class. */
    private static final Logger LOG =
            LoggerFactory.getLogger(SiteMapImpl.class);

    /** The json mapper. */
    private final ObjectMapper objectMapper;

    /** The web utilities. */
    private final WebUtil webUtil;

    /**
     * Constructor.
     *
     * @param objectMapper The mapper.
     * @param webUtil      The web utilities.
     */
    public SiteMapImpl(
            final ObjectMapper objectMapper,
            final WebUtil webUtil) {
        this.objectMapper = objectMapper;
        this.webUtil = webUtil;
    }

    @Override
    public Optional<Response> setLocation(
            final int minerId,
            final String rack,
            final int row,
            final int index) {
        Optional<Response> result = Optional.empty();
        try {
            final Optional<String> response =
                    this.webUtil.post(
                            String.format(
                                    "/api/site-map/location/%d",
                                    minerId),
                            this.objectMapper.writeValueAsString(
                                    ImmutableMap.of(
                                            "rack",
                                            rack,
                                            "row",
                                            row,
                                            "index",
                                            index)));
            if (response.isPresent()) {
                result =
                        JsonUtils.fromJson(
                                response.get(),
                                this.objectMapper,
                                new TypeReference<Response>() {
                                });
            }
        } catch (final JsonProcessingException e) {
            LOG.warn("Exception occurred while parsing json", e);
        }
        return result;
    }
}
