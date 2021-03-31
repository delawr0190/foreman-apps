package mn.foreman.avalon;

import mn.foreman.util.AbstractBlinkActionITest;
import mn.foreman.util.rpc.FakeRpcMinerServer;
import mn.foreman.util.rpc.RpcHandler;

import com.google.common.collect.ImmutableMap;

import java.util.Collections;

/** Tests blinking LEDs on Whatsminers. */
public class AvalonBlinkITest
        extends AbstractBlinkActionITest {

    /** Constructor. */
    public AvalonBlinkITest() {
        super(
                4028,
                new AvalonBlinkStrategy(),
                Collections.singletonList(
                        () -> new FakeRpcMinerServer(
                                4028,
                                ImmutableMap.of(
                                        "ascset|0,led,1-1",
                                        new RpcHandler(
                                                "STATUS=S,When=18984,Code=119,Msg=ASC 0 set OK,Description=cgminer 4.11.1"),
                                        "ascset|0,led,1-0",
                                        new RpcHandler(
                                                "STATUS=S,When=18984,Code=119,Msg=ASC 0 set OK,Description=cgminer 4.11.1")))),
                Collections.emptyMap(),
                true,
                false);
    }
}