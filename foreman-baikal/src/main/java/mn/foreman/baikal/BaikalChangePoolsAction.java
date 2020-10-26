package mn.foreman.baikal;

import mn.foreman.model.AbstractChangePoolsAction;
import mn.foreman.model.Pool;
import mn.foreman.model.error.MinerException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * An {@link AbstractChangePoolsAction} implementation that will change the
 * pools on a Baikal miner.
 */
public class BaikalChangePoolsAction
        extends AbstractChangePoolsAction {

    /** The logger for this class. */
    private static final Logger LOG =
            LoggerFactory.getLogger(BaikalChangePoolsAction.class);

    @Override
    @SuppressWarnings("unchecked")
    protected boolean doChange(
            final String ip,
            final int port,
            final Map<String, Object> parameters,
            final List<Pool> pools)
            throws MinerException {
        final AtomicBoolean success = new AtomicBoolean(false);

        try {
            final List<Map<String, Object>> poolData = new LinkedList<>();
            for (int i = 0; i < pools.size(); i++) {
                poolData.add(
                        toPool(
                                pools.get(i),
                                i,
                                parameters));
            }

            final String poolJson =
                    new ObjectMapper()
                            .writeValueAsString(poolData);
            LOG.info("New pool data: {}", poolJson);

            BaikalUtils.query(
                    ip,
                    port,
                    parameters.get("password").toString(),
                    "/f_settings.php?pools=" + URLEncoder.encode(
                            poolJson,
                            "UTF-8"),
                    (integer, s) -> success.set(s.contains("data")));
        } catch (final JsonProcessingException e) {
            throw new MinerException(e);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return success.get();
    }

    /**
     * Converts the provided info into a pool.
     *
     * @param pool   The new pool.
     * @param index  The index, used for priority.
     * @param params The params.
     *
     * @return The pool data.
     */
    private static Map<String, Object> toPool(
            final Pool pool,
            final int index,
            final Map<String, Object> params) {
        final Map<String, Object> newPool = new HashMap<>();
        newPool.put("url", pool.getUrl());
        newPool.put("algo",
                params.getOrDefault(
                        "algo_" + index,
                        params.get("algo" + index)).toString());
        newPool.put("user", pool.getUsername());
        newPool.put("pass", pool.getPassword());
        newPool.put("extranonce", true);
        newPool.put("priority", Integer.toString(index));
        return newPool;
    }
}
