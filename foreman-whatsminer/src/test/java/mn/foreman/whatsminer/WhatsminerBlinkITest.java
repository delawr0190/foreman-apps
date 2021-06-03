package mn.foreman.whatsminer;

import mn.foreman.model.ApplicationConfiguration;
import mn.foreman.util.AbstractBlinkActionITest;
import mn.foreman.util.rpc.FakeRpcMinerServer;
import mn.foreman.util.rpc.RpcHandler;

import com.google.common.collect.ImmutableMap;

import java.util.Collections;

/** Tests blinking LEDs on Whatsminers. */
public class WhatsminerBlinkITest
        extends AbstractBlinkActionITest {

    /** Constructor. */
    public WhatsminerBlinkITest() {
        super(
                4028,
                new WhatsminerBlinkStrategy(
                        new ApplicationConfiguration()),
                Collections.singletonList(
                        () -> new FakeRpcMinerServer(
                                4028,
                                ImmutableMap.of(
                                        "{\"cmd\":\"get_token\"}",
                                        new RpcHandler(
                                                "{\"STATUS\":\"S\",\"When\":1608417125,\"Code\":134,\"Msg\":{\"time\":\"6915\",\"salt\":\"BQ5hoXV9\",\"newsalt\":\"a5TtWui2\"},\"Description\":\"whatsminer v1.1\"}"),
                                        "{\"enc\":1,\"data\":\"nMLVzjQG7hjSD+d5hwvYSitXund4MPNZopw3Uu2C85ezRF2GZs/KkSfBHTqQpqLSetRMV1BGPl6CzaDx7GD7fUQaM/eiNyeJTVrNWlKIMFYNGIEd57NKcoyttCWYmhnIXpnvOdDql4mubthUjNAawg==\"}",
                                        new RpcHandler(
                                                "{\"enc\":\"99J8w83zIqB3/n9c5GlK0Ms8rMLrtR4I9L3mL3ihmpoy4J0v+AHcKtijbyE92q22MIKC6VKEUcYL2KQS50euQWMqSBlxCPx8YYy4oRkOE4C7kjyvgs9rRMH/mhiVH9k+\"}"),
                                        "{\"enc\":1,\"data\":\"nMLVzjQG7hjSD+d5hwvYSn3H8OZ9zAvPCvBRa9yjzQTqCeCXdOWSDAIjiDW6DkxEp7WUCgnxLnxVg/OTpSKIbxgdXEl4gno5L17jJd20tCc=\"}",
                                        new RpcHandler(
                                                "{\"enc\":\"99J8w83zIqB3/n9c5GlK0Ms8rMLrtR4I9L3mL3ihmpoy4J0v+AHcKtijbyE92q22MIKC6VKEUcYL2KQS50euQWMqSBlxCPx8YYy4oRkOE4C7kjyvgs9rRMH/mhiVH9k+\"}")))),
                Collections.emptyMap(),
                true,
                false);
    }
}