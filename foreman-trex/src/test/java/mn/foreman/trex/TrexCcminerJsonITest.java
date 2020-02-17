package mn.foreman.trex;

import mn.foreman.model.miners.FanInfo;
import mn.foreman.model.miners.MinerStats;
import mn.foreman.model.miners.Pool;
import mn.foreman.model.miners.rig.FreqInfo;
import mn.foreman.model.miners.rig.Gpu;
import mn.foreman.model.miners.rig.Rig;
import mn.foreman.util.AbstractApiITest;
import mn.foreman.util.rpc.FakeRpcMinerServer;
import mn.foreman.util.rpc.RpcHandler;

import com.google.common.collect.ImmutableMap;

import java.math.BigDecimal;

/** Runs an integration tests using {@link TrexJson} against a fake API. */
public class TrexCcminerJsonITest
        extends AbstractApiITest {

    /** Constructor. */
    public TrexCcminerJsonITest() {
        super(
                new TrexFactory().create(
                        ImmutableMap.of(
                                "apiIp",
                                "127.0.0.1",
                                "apiPort",
                                "4068")),
                new FakeRpcMinerServer(
                        4068,
                        ImmutableMap.of(
                                "summary",
                                new RpcHandler(
                                        "{\"accepted_count\":0,\"active_pool\":{\"difficulty\":256.0,\"ping\":0,\"retries\":0,\"url\":\"stratum+tcp://rvn.2miners.com:6060\",\"user\":\"RBX1G6nYDMHVtyaZiQWySMZw1Bb2DEDpT8.rig0\"},\"algorithm\":\"x16rv2\",\"api\":\"3.1\",\"build_date\":\"Oct 12 2019 08:42:59\",\"cuda\":\"10.0\",\"description\":\"T-Rex NVIDIA GPU miner\",\"difficulty\":354972.44650609588,\"gpu_total\":1,\"gpus\":[{\"device_id\":0,\"gpu_id\":0,\"gpu_user_id\":0,\"hashrate\":6488370,\"hashrate_day\":0,\"hashrate_hour\":0,\"hashrate_instant\":6596933,\"hashrate_minute\":6488370,\"intensity\":20.0,\"name\":\"GeForce GTX 1050 Ti\",\"temperature\":53,\"vendor\":\"NVIDIA\"}],\"hashrate\":6488370,\"hashrate_day\":0,\"hashrate_hour\":0,\"hashrate_minute\":6488370,\"name\":\"t-rex\",\"os\":\"win\",\"rejected_count\":0,\"revision\":\"ad1aa7ca3433\",\"sharerate\":0.0,\"sharerate_average\":0.0,\"solved_count\":0,\"success\":1,\"ts\":1581878303,\"uptime\":3,\"version\":\"0.14.6\",\"watchdog_stat\":{\"built_in\":true,\"startup_ts\":1581878298021741,\"total_restarts\":0,\"uptime\":5,\"wd_version\":\"0.14.6\"}}"))),
                new MinerStats.Builder()
                        .setApiIp("127.0.0.1")
                        .setApiPort(4068)
                        .addPool(
                                new Pool.Builder()
                                        .setName("rvn.2miners.com:6060")
                                        .setPriority(0)
                                        .setStatus(true, true)
                                        .setCounts(0, 0, 0)
                                        .build())
                        .addRig(
                                new Rig.Builder()
                                        .setHashRate(new BigDecimal("6488370"))
                                        .addGpu(
                                                new Gpu.Builder()
                                                        .setIndex(0)
                                                        .setBus(0)
                                                        .setName("GeForce GTX 1050 Ti")
                                                        .setTemp(53)
                                                        .setFans(
                                                                new FanInfo.Builder()
                                                                        .setCount(0)
                                                                        .setSpeedUnits("%")
                                                                        .build())
                                                        .setFreqInfo(
                                                                new FreqInfo.Builder()
                                                                        .setFreq(0)
                                                                        .setMemFreq(0)
                                                                        .build())
                                                        .build())
                                        .build())
                        .build());
    }
}