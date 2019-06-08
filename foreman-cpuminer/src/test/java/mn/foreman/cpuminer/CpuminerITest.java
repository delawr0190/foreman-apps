package mn.foreman.cpuminer;

import mn.foreman.model.miners.MinerStats;
import mn.foreman.model.miners.Pool;
import mn.foreman.model.miners.cpu.Cpu;
import mn.foreman.util.AbstractApiITest;
import mn.foreman.util.rpc.FakeRpcMinerServer;
import mn.foreman.util.rpc.RpcHandler;

import com.google.common.collect.ImmutableMap;

import java.math.BigDecimal;

/** Runs an integration test using {@link Cpuminer} against a fake API. */
public class CpuminerITest
        extends AbstractApiITest {

    /** Constructor. */
    public CpuminerITest() {
        super(
                new CpuminerFactory().create(
                        ImmutableMap.of(
                                "apiIp",
                                "127.0.0.1",
                                "apiPort",
                                "4048")),
                new FakeRpcMinerServer(
                        4048,
                        ImmutableMap.of(
                                "summary",
                                new RpcHandler(
                                        "NAME=cpuminer-opt;VER=3.9.1.1;API=1.0;ALGO=lyra2z;CPUS=12;URL=lyra2z.mine.zergpool.com:4553;HS=117826.50;KHS=117.83;ACC=0;REJ=0;SOL=0;ACCMN=0.000;DIFF=1327.367028;TEMP=0.0;FAN=0;FREQ=0;UPTIME=9;TS=1559572258|"),
                                "threads",
                                new RpcHandler(
                                        "CPU=0;kH/s=36.59|CPU=1;kH/s=38.73|CPU=2;kH/s=39.08|CPU=3;kH/s=39.24|CPU=4;kH/s=35.33|CPU=5;kH/s=38.89|CPU=6;kH/s=36.80|CPU=7;kH/s=39.34|CPU=8;kH/s=37.07|CPU=9;kH/s=38.76|CPU=10;kH/s=39.01|CPU=11;kH/s=39.24|"))),
                new MinerStats.Builder()
                        .setApiIp("127.0.0.1")
                        .setApiPort(4048)
                        .addPool(
                                new Pool.Builder()
                                        .setName("lyra2z.mine.zergpool.com:4553")
                                        .setStatus(true, true)
                                        .setPriority(0)
                                        .setCounts(0, 0, 0)
                                        .build())
                        .addCpu(
                                new Cpu.Builder()
                                        .setName("CPU 0")
                                        .setFanSpeed("0")
                                        .setTemp("0")
                                        .setFrequency("0")
                                        .addThread(new BigDecimal("36590.00"))
                                        .addThread(new BigDecimal("38730.00"))
                                        .addThread(new BigDecimal("39080.00"))
                                        .addThread(new BigDecimal("39240.00"))
                                        .addThread(new BigDecimal("35330.00"))
                                        .addThread(new BigDecimal("38890.00"))
                                        .addThread(new BigDecimal("36800.00"))
                                        .addThread(new BigDecimal("39340.00"))
                                        .addThread(new BigDecimal("37070.00"))
                                        .addThread(new BigDecimal("38760.00"))
                                        .addThread(new BigDecimal("39010.00"))
                                        .addThread(new BigDecimal("39240.00"))
                                        .build())
                        .build());
    }
}