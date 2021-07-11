package mn.foreman.avalon;

import mn.foreman.model.ApplicationConfiguration;
import mn.foreman.util.AbstractSyncActionITest;
import mn.foreman.util.rpc.FakeRpcMinerServer;
import mn.foreman.util.rpc.HandlerInterface;
import mn.foreman.util.rpc.RpcHandler;

import com.google.common.collect.ImmutableMap;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

/** Tests changing power modes of an Avalon miner. */
@RunWith(Parameterized.class)
public class AvalonPowerModeITest
        extends AbstractSyncActionITest {

    /**
     * Constructor.
     *
     * @param rpcHandlers The RPC handlers.
     * @param mode        The mode.
     */
    public AvalonPowerModeITest(
            final Map<String, HandlerInterface> rpcHandlers,
            final String mode) {
        super(
                4028,
                new AvalonPowerModeAction(
                        new ApplicationConfiguration(),
                        new AvalonRebootAction(
                                new ApplicationConfiguration())),
                Collections.singletonList(
                        () -> new FakeRpcMinerServer(
                                4028,
                                rpcHandlers)),
                ImmutableMap.of(
                        "mode",
                        mode),
                true,
                false);
    }

    /**
     * Test parameters
     *
     * @return The test parameters.
     */
    @Parameterized.Parameters
    public static Collection<Object[]> parameters() {
        return Arrays.asList(
                new Object[][]{
                        {
                                // Avalon 1047
                                ImmutableMap.of(
                                        "ascset|0,hashpower,0",
                                        new RpcHandler("")),
                                "sleeping"
                        },
                        {
                                // Avalon 1047
                                ImmutableMap.of(
                                        "ascset|0,reboot,0",
                                        new RpcHandler("")),
                                "normal"
                        }
                });
    }
}