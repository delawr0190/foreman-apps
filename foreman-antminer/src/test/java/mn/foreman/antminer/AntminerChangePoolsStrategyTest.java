package mn.foreman.antminer;

import mn.foreman.model.Pool;
import mn.foreman.model.error.MinerException;

import com.google.common.collect.ImmutableMap;
import org.junit.Test;

import java.util.Arrays;

public class AntminerChangePoolsStrategyTest {

    @Test
    public void test() throws MinerException {
        new AntminerChangePoolsStrategy().change(
                "192.168.1.189",
                80,
                ImmutableMap.of(
                        "username",
                        "root",
                        "password",
                        "root"),
                Arrays.asList(
                        Pool
                                .builder()
                                .url("stratum+tcp://us.litecoinpool.org:3333")
                                .username("obmllc.l3_1")
                                .password("x")
                                .build(),
                        Pool
                                .builder()
                                .url("stratum+tcp://us.litecoinpool.org:3333")
                                .username("obmllc.l3_1")
                                .password("x")
                                .build(),
                        Pool
                                .builder()
                                .url("stratum+tcp://us.litecoinpool.org:3333")
                                .username("obmllc.l3_1")
                                .password("x")
                                .build()));
    }
}