package mn.foreman.obelisk;

import mn.foreman.model.AbstractPasswordAction;
import mn.foreman.model.error.MinerException;
import mn.foreman.obelisk.json.Dashboard;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;

import java.util.Map;

/** Changes the password of an obelisk. */
public class ObeliskPasswordAction
        extends AbstractPasswordAction {

    /** The mapper for writing json. */
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Override
    public boolean doChange(
            final String ip,
            final int port,
            final Map<String, Object> parameters,
            final String oldPassword,
            final String newPassword)
            throws MinerException {
        try {
            final String username =
                    (String) parameters.getOrDefault("username", "");
            final String password =
                    (String) parameters.getOrDefault("password", "");
            ObeliskQuery.runSessionQuery(
                    ObeliskQuery.Context
                            .<Dashboard>builder()
                            .apiIp(ip)
                            .apiPort(port)
                            .uri("/api/action/changePassword")
                            .method("POST")
                            .username(username)
                            .password(password)
                            .content(
                                    OBJECT_MAPPER.writeValueAsString(
                                            ImmutableMap.of(
                                                    "username",
                                                    "admin",
                                                    "oldPassword",
                                                    oldPassword,
                                                    "newPassword",
                                                    newPassword)))
                            .rawResponseCallback(s -> {
                            })
                            .build());
        } catch (final Exception e) {
            throw new MinerException(e);
        }
        return true;
    }
}