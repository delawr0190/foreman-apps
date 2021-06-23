package mn.foreman.antminer;

import mn.foreman.io.Query;
import mn.foreman.model.AbstractPasswordAction;
import mn.foreman.model.ApplicationConfiguration;
import mn.foreman.model.error.MinerException;
import mn.foreman.util.ParamUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;
import org.apache.http.HttpStatus;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * A {@link StockPasswordAction} provides an {@link AbstractPasswordAction}
 * implementation that will change a password for an Antminer running the stock
 * firmware.
 */
public class StockPasswordAction
        extends AbstractPasswordAction {

    /** The mapper for json processing. */
    private static final ObjectMapper MAPPER = new ObjectMapper();

    /** The configuration. */
    private final ApplicationConfiguration applicationConfiguration;

    /** The realm. */
    private final String realm;

    /**
     * Constructor.
     *
     * @param realm                    The realm.
     * @param applicationConfiguration The configuration.
     */
    public StockPasswordAction(
            final String realm,
            final ApplicationConfiguration applicationConfiguration) {
        this.realm = realm;
        this.applicationConfiguration = applicationConfiguration;
    }

    @Override
    protected boolean doChange(
            final String ip,
            final int port,
            final Map<String, Object> parameters,
            final String oldPassword,
            final String newPassword)
            throws MinerException {
        final AtomicBoolean status = new AtomicBoolean();
        try {
            if (AntminerUtils.isNewGen(
                    ip,
                    port,
                    this.realm,
                    parameters.getOrDefault("username", "root").toString(),
                    oldPassword,
                    this.applicationConfiguration.getReadSocketTimeout())) {
                changeNew(
                        ip,
                        port,
                        parameters,
                        oldPassword,
                        newPassword,
                        status);
            } else {
                changeOld(
                        ip,
                        port,
                        parameters,
                        oldPassword,
                        newPassword,
                        status);
            }
        } catch (final Exception e) {
            throw new MinerException(
                    String.format(
                            "Failed to obtain a response from miner at %s:%d",
                            ip,
                            port));
        }
        return status.get();
    }

    /**
     * Changes the password against miners running newer firmware.
     *
     * @param ip          The ip.
     * @param port        The port.
     * @param parameters  The parameters.
     * @param oldPassword The old password.
     * @param newPassword The new password.
     * @param status      The status.
     *
     * @throws Exception on failure.
     */
    private void changeNew(
            final String ip,
            final int port,
            final Map<String, Object> parameters,
            final String oldPassword,
            final String newPassword,
            final AtomicBoolean status) throws Exception {
        final String contentString =
                MAPPER.writeValueAsString(
                        ImmutableMap.of(
                                "curPwd",
                                oldPassword,
                                "newPwd",
                                newPassword,
                                "confirmPwd",
                                newPassword));
        Query.digestPost(
                ip,
                port,
                this.realm,
                "/cgi-bin/passwd.cgi",
                parameters.getOrDefault("username", "root").toString(),
                oldPassword,
                null,
                contentString,
                (integer, s) -> status.set(s != null && s.toLowerCase().contains("ok")),
                this.applicationConfiguration.getWriteSocketTimeout());
    }

    /**
     * Changes the password against miners running older firmware.
     *
     * @param ip          The ip.
     * @param port        The port.
     * @param parameters  The parameters.
     * @param oldPassword The old password.
     * @param newPassword The new password.
     * @param status      The status.
     *
     * @throws Exception on failure.
     */
    private void changeOld(
            final String ip,
            final int port,
            final Map<String, Object> parameters,
            final String oldPassword,
            final String newPassword,
            final AtomicBoolean status) throws Exception {
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
                (integer, s) -> status.set(integer == HttpStatus.SC_OK),
                this.applicationConfiguration.getWriteSocketTimeout());
    }
}