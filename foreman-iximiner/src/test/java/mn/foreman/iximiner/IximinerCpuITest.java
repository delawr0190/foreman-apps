package mn.foreman.iximiner;

import mn.foreman.model.miners.MinerStats;
import mn.foreman.model.miners.Pool;
import mn.foreman.model.miners.cpu.Cpu;
import mn.foreman.util.AbstractApiITest;
import mn.foreman.util.http.FakeHttpMinerServer;
import mn.foreman.util.http.HttpHandler;

import com.google.common.collect.ImmutableMap;

import java.math.BigDecimal;

/** Runs an integration tests using {@link Iximiner} against a fake API. */
public class IximinerCpuITest
        extends AbstractApiITest {

    /** Constructor. */
    public IximinerCpuITest() {
        super(
                new IximinerFactory().create(
                        ImmutableMap.of(
                                "apiIp",
                                "127.0.0.1",
                                "apiPort",
                                "4068")),
                new FakeHttpMinerServer(
                        4068,
                        ImmutableMap.of(
                                "/status",
                                new HttpHandler(
                                        "",
                                        "[\n" +
                                                "  {\n" +
                                                "    \"name\": \"miners\",\n" +
                                                "    \"block_height\": 526337,\n" +
                                                "    \"time_running\": 73,\n" +
                                                "    \"total_blocks\": 1,\n" +
                                                "    \"shares\": 14,\n" +
                                                "    \"rejects\": 0,\n" +
                                                "    \"earned\": 0,\n" +
                                                "    \"hashers\": [\n" +
                                                "      {\n" +
                                                "        \"type\": \"CPU\",\n" +
                                                "        \"subtype\": \"CPU\",\n" +
                                                "        \"devices\": [\n" +
                                                "          {\n" +
                                                "            \"id\": 0,\n" +
                                                "            \"bus_id\": \"\",\n" +
                                                "            \"name\": \"AMD Ryzen 5 2600 Six-Core Processor            \",\n" +
                                                "            \"intensity\": 80,\n" +
                                                "            \"hashrate\": 17440.1\n" +
                                                "          }\n" +
                                                "        ]\n" +
                                                "      }\n" +
                                                "    ]\n" +
                                                "  }\n" +
                                                "]"))),
                new MinerStats.Builder()
                        .setApiIp("127.0.0.1")
                        .setApiPort(4068)
                        .addPool(
                                new Pool.Builder()
                                        .setName("not available")
                                        .setPriority(0)
                                        .setStatus(true, true)
                                        .setCounts(14, 0, 0)
                                        .build())
                        .addCpu(
                                new Cpu.Builder()
                                        .setName("AMD Ryzen 5 2600 Six-Core Processor")
                                        .setFanSpeed(0)
                                        .setFrequency(BigDecimal.ZERO)
                                        .addThread(new BigDecimal("17440.1"))
                                        .setTemp(0)
                                        .build())
                        .build());
    }
}