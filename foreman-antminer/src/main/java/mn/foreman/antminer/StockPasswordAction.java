package mn.foreman.antminer;

import mn.foreman.io.Query;
import mn.foreman.model.AbstractPasswordAction;
import mn.foreman.model.error.MinerException;
import mn.foreman.util.ParamUtils;

import org.apache.http.HttpStatus;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * A {@link StockPasswordAction} provides an {@link AbstractPasswordAction}
 * implementation that will change a password for an Antminer running the stock
 * firmware.
 */
public class StockPasswordAction
        extends AbstractPasswordAction {

    /** The realm. */
    private final String realm;

    /**
     * Constructor.
     *
     * @param realm The realm.
     */
    public StockPasswordAction(final String realm) {
        this.realm = realm;
    }

    @Override
    protected boolean doChange(
            final String ip,
            final int port,
            final Map<String, Object> parameters,
            final String oldPassword,
            final String newPassword)
            throws MinerException {
        final AtomicInteger responseCode = new AtomicInteger();
        try {
            Query.digestGet(
                    ip,
                    port,
                    this.realm,
                    "/cgi-bin/passwd.cgi",
                    parameters.getOrDefault("username", "root").toString(),
                    oldPassword,
                    Arrays.asList(
                            ParamUtils.toParam(
                                    "current_pw",
                                    oldPassword),
                            ParamUtils.toParam(
                                    "new_pw",
                                    newPassword),
                            ParamUtils.toParam(
                                    "new_pw_ctrl",
                                    newPassword)),
                    (integer, s) -> responseCode.set(integer));
        } catch (final Exception e) {
            throw new MinerException(
                    String.format(
                            "Failed to obtain a response from miner at %s:%d",
                            ip,
                            port));
        }
        return responseCode.get() == HttpStatus.SC_OK;
    }
}