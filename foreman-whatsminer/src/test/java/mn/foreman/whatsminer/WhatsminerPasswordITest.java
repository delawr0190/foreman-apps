package mn.foreman.whatsminer;

import mn.foreman.model.ApplicationConfiguration;
import mn.foreman.util.AbstractSyncActionITest;
import mn.foreman.util.rpc.FakeRpcMinerServer;
import mn.foreman.util.rpc.RpcHandler;

import com.google.common.collect.ImmutableMap;

import java.util.Collections;

/** Test changing passwords on a Whatsminer. */
public class WhatsminerPasswordITest
        extends AbstractSyncActionITest {

    /** Constructor. */
    public WhatsminerPasswordITest() {
        super(
                4028,
                new WhatsminerPasswordAction(
                        new ApplicationConfiguration()),
                Collections.singletonList(
                        () -> new FakeRpcMinerServer(
                                4028,
                                ImmutableMap.of(
                                        "{\"cmd\":\"get_token\"}",
                                        new RpcHandler(
                                                "{\"STATUS\":\"S\",\"When\":1608417125,\"Code\":134,\"Msg\":{\"time\":\"6915\",\"salt\":\"BQ5hoXV9\",\"newsalt\":\"a5TtWui2\"},\"Description\":\"whatsminer v1.1\"}"),
                                        "{\"enc\":1,\"data\":\"R5OGMYYYx2LtfDiwrwsQbX/ezo5h0hhSjZivRWfl/GRk2WXn8mB9hlz7lSPoQOcFdShlatcrjhMDWwAUXuNMc+tNUURlostUfXVUMsDoca8e84UzwGARfjcGN9jD2p4k\"}",
                                        new RpcHandler(
                                                "{\"enc\":\"99J8w83zIqB3/n9c5GlK0Ms8rMLrtR4I9L3mL3ihmpoy4J0v+AHcKtijbyE92q22MIKC6VKEUcYL2KQS50euQWMqSBlxCPx8YYy4oRkOE4C7kjyvgs9rRMH/mhiVH9k+\"}")))),
                ImmutableMap.of(
                        "oldPassword",
                        "my-auth-password",
                        "newPassword",
                        "new"),
                true,
                false);
    }
}
