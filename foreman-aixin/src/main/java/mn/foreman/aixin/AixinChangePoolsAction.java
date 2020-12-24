package mn.foreman.aixin;

import mn.foreman.model.AbstractChangePoolsAction;
import mn.foreman.model.Pool;
import mn.foreman.util.ParamUtils;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * A {@link AixinChangePoolsAction} provides an {@link AbstractChangePoolsAction}
 * implementation that will change the pools in use by an aixin device.
 */
public class AixinChangePoolsAction
        extends AbstractChangePoolsAction {

    @Override
    public boolean doChange(
            final String ip,
            final int port,
            final Map<String, Object> parameters,
            final List<Pool> pools) {
        final List<Map<String, Object>> content = new LinkedList<>();
        addParams(pools.get(0), 1, content);
        addParams(pools.get(1), 2, content);
        addParams(pools.get(2), 3, content);
        return AixinQuery.query(
                ip,
                "/api/updatePools",
                parameters,
                content);
    }

    /**
     * Adds parameters to the params.
     *
     * @param pool   The pool.
     * @param index  The index.
     * @param params The params.
     */
    private static void addParams(
            final Pool pool,
            final int index,
            final List<Map<String, Object>> params) {
        ParamUtils.addParam(
                "Pool" + index,
                pool.getUrl(),
                params);
        ParamUtils.addParam(
                "UserName" + index,
                pool.getUsername(),
                params);
        ParamUtils.addParam(
                "Password" + index,
                pool.getPassword(),
                params);
    }
}
