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
public class TrexTelnetITest
        extends AbstractApiITest {

    /** Constructor. */
    public TrexTelnetITest() {
        super(
                new TrexFactory().create(
                        ImmutableMap.of(
                                "apiIp",
                                "127.0.0.1",
                                "apiPort",
                                "4058")),
                new FakeRpcMinerServer(
                        4058,
                        ImmutableMap.of(
                                "summary\n",
                                new RpcHandler(
                                        "{\"accepted_count\":331,\"active_pool\":{\"difficulty\":0.55857999999999997,\"ping\":38,\"retries\":0,\"url\":\"stratum+tcp://naw-eth.hiveon.net:4444\",\"user\":\"0x\"},\"algorithm\":\"ethash\",\"api\":\"3.2\",\"build_date\":\"Dec  2 2020 21:17:31\",\"coin\":\"eth\",\"cuda\":\"10.0\",\"description\":\"T-Rex NVIDIA GPU miner\",\"difficulty\":0.55857999999999997,\"driver\":\"450.80.02\",\"gpu_total\":8,\"gpus\":[{\"device_id\":0,\"efficiency\":\"268kH/W\",\"fan_speed\":49,\"gpu_id\":0,\"gpu_user_id\":0,\"hashrate\":29161504,\"hashrate_day\":0,\"hashrate_hour\":29167581,\"hashrate_instant\":29134973,\"hashrate_minute\":29158882,\"intensity\":22.0,\"low_load\":false,\"mtweak\":0,\"name\":\"GTX 1070 Ti\",\"power\":109,\"power_avr\":109,\"temperature\":67,\"vendor\":\"EVGA\"},{\"device_id\":1,\"efficiency\":\"265kH/W\",\"fan_speed\":49,\"gpu_id\":1,\"gpu_user_id\":1,\"hashrate\":28867241,\"hashrate_day\":0,\"hashrate_hour\":28910732,\"hashrate_instant\":28845724,\"hashrate_minute\":28867703,\"intensity\":22.0,\"low_load\":false,\"mtweak\":0,\"name\":\"GTX 1070 Ti\",\"power\":108,\"power_avr\":109,\"temperature\":67,\"vendor\":\"EVGA\"},{\"device_id\":2,\"efficiency\":\"269kH/W\",\"fan_speed\":48,\"gpu_id\":2,\"gpu_user_id\":2,\"hashrate\":29282933,\"hashrate_day\":0,\"hashrate_hour\":29290314,\"hashrate_instant\":29256152,\"hashrate_minute\":29283237,\"intensity\":22.0,\"low_load\":false,\"mtweak\":0,\"name\":\"GTX 1070 Ti\",\"power\":109,\"power_avr\":109,\"temperature\":65,\"vendor\":\"EVGA\"},{\"device_id\":3,\"efficiency\":\"264kH/W\",\"fan_speed\":48,\"gpu_id\":3,\"gpu_user_id\":3,\"hashrate\":29062841,\"hashrate_day\":0,\"hashrate_hour\":29087798,\"hashrate_instant\":29078027,\"hashrate_minute\":29064160,\"intensity\":22.0,\"low_load\":false,\"mtweak\":0,\"name\":\"GTX 1070 Ti\",\"power\":110,\"power_avr\":110,\"temperature\":66,\"vendor\":\"EVGA\"},{\"device_id\":4,\"efficiency\":\"266kH/W\",\"fan_speed\":50,\"gpu_id\":4,\"gpu_user_id\":4,\"hashrate\":28957120,\"hashrate_day\":0,\"hashrate_hour\":29008860,\"hashrate_instant\":28943478,\"hashrate_minute\":28959544,\"intensity\":22.0,\"low_load\":false,\"mtweak\":0,\"name\":\"GTX 1070 Ti\",\"power\":109,\"power_avr\":109,\"temperature\":68,\"vendor\":\"EVGA\"},{\"device_id\":5,\"efficiency\":\"266kH/W\",\"fan_speed\":51,\"gpu_id\":5,\"gpu_user_id\":5,\"hashrate\":28959627,\"hashrate_day\":0,\"hashrate_hour\":28996870,\"hashrate_instant\":28928259,\"hashrate_minute\":28950014,\"intensity\":22.0,\"low_load\":false,\"mtweak\":0,\"name\":\"GTX 1070 Ti\",\"power\":109,\"power_avr\":109,\"temperature\":70,\"vendor\":\"EVGA\"},{\"device_id\":6,\"efficiency\":\"267kH/W\",\"fan_speed\":50,\"gpu_id\":6,\"gpu_user_id\":6,\"hashrate\":29132617,\"hashrate_day\":0,\"hashrate_hour\":29158251,\"hashrate_instant\":29148756,\"hashrate_minute\":29133374,\"intensity\":22.0,\"low_load\":false,\"mtweak\":0,\"name\":\"GTX 1070 Ti\",\"power\":110,\"power_avr\":109,\"temperature\":67,\"vendor\":\"EVGA\"},{\"device_id\":7,\"efficiency\":\"268kH/W\",\"fan_speed\":50,\"gpu_id\":7,\"gpu_user_id\":7,\"hashrate\":29191997,\"hashrate_day\":0,\"hashrate_hour\":29153009,\"hashrate_instant\":29235356,\"hashrate_minute\":29167203,\"intensity\":22.0,\"low_load\":false,\"mtweak\":0,\"name\":\"GTX 1070 Ti\",\"power\":108,\"power_avr\":109,\"temperature\":68,\"vendor\":\"EVGA\"}],\"hashrate\":232615880,\"hashrate_day\":0,\"hashrate_hour\":232773415,\"hashrate_minute\":232584117,\"name\":\"t-rex\",\"os\":\"linux\",\"rejected_count\":0,\"revision\":\"99a7206c3590\",\"sharerate\":6.0819999999999999,\"sharerate_average\":5.4480000000000004,\"solved_count\":0,\"stat_by_gpu\":[{\"accepted_count\":35,\"rejected_count\":0,\"solved_count\":0},{\"accepted_count\":43,\"rejected_count\":0,\"solved_count\":0},{\"accepted_count\":34,\"rejected_count\":0,\"solved_count\":0},{\"accepted_count\":37,\"rejected_count\":0,\"solved_count\":0},{\"accepted_count\":40,\"rejected_count\":0,\"solved_count\":0},{\"accepted_count\":44,\"rejected_count\":0,\"solved_count\":0},{\"accepted_count\":36,\"rejected_count\":0,\"solved_count\":0},{\"accepted_count\":62,\"rejected_count\":0,\"solved_count\":0}],\"success\":1,\"ts\":1606955653,\"uptime\":3656,\"version\":\"0.19.1\"}"))),
                new MinerStats.Builder()
                        .setApiIp("127.0.0.1")
                        .setApiPort(4058)
                        .addPool(
                                new Pool.Builder()
                                        .setName("naw-eth.hiveon.net:4444")
                                        .setPriority(0)
                                        .setStatus(true, true)
                                        .setCounts(331, 0, 0)
                                        .build())
                        .addRig(
                                new Rig.Builder()
                                        .setHashRate(new BigDecimal(232615880))
                                        .addGpu(
                                                new Gpu.Builder()
                                                        .setIndex(0)
                                                        .setBus(0)
                                                        .setName("GTX 1070 Ti")
                                                        .setTemp(67)
                                                        .setFans(
                                                                new FanInfo.Builder()
                                                                        .setCount(1)
                                                                        .addSpeed(49)
                                                                        .setSpeedUnits("%")
                                                                        .build())
                                                        .setFreqInfo(
                                                                new FreqInfo.Builder()
                                                                        .setFreq(0)
                                                                        .setMemFreq(0)
                                                                        .build())
                                                        .build())
                                        .addGpu(
                                                new Gpu.Builder()
                                                        .setIndex(1)
                                                        .setBus(0)
                                                        .setName("GTX 1070 Ti")
                                                        .setTemp(67)
                                                        .setFans(
                                                                new FanInfo.Builder()
                                                                        .setCount(1)
                                                                        .addSpeed(49)
                                                                        .setSpeedUnits("%")
                                                                        .build())
                                                        .setFreqInfo(
                                                                new FreqInfo.Builder()
                                                                        .setFreq(0)
                                                                        .setMemFreq(0)
                                                                        .build())
                                                        .build())
                                        .addGpu(
                                                new Gpu.Builder()
                                                        .setIndex(2)
                                                        .setBus(0)
                                                        .setName("GTX 1070 Ti")
                                                        .setTemp(65)
                                                        .setFans(
                                                                new FanInfo.Builder()
                                                                        .setCount(1)
                                                                        .addSpeed(48)
                                                                        .setSpeedUnits("%")
                                                                        .build())
                                                        .setFreqInfo(
                                                                new FreqInfo.Builder()
                                                                        .setFreq(0)
                                                                        .setMemFreq(0)
                                                                        .build())
                                                        .build())
                                        .addGpu(
                                                new Gpu.Builder()
                                                        .setIndex(3)
                                                        .setBus(0)
                                                        .setName("GTX 1070 Ti")
                                                        .setTemp(66)
                                                        .setFans(
                                                                new FanInfo.Builder()
                                                                        .setCount(1)
                                                                        .addSpeed(48)
                                                                        .setSpeedUnits("%")
                                                                        .build())
                                                        .setFreqInfo(
                                                                new FreqInfo.Builder()
                                                                        .setFreq(0)
                                                                        .setMemFreq(0)
                                                                        .build())
                                                        .build())
                                        .addGpu(
                                                new Gpu.Builder()
                                                        .setIndex(4)
                                                        .setBus(0)
                                                        .setName("GTX 1070 Ti")
                                                        .setTemp(68)
                                                        .setFans(
                                                                new FanInfo.Builder()
                                                                        .setCount(1)
                                                                        .addSpeed(50)
                                                                        .setSpeedUnits("%")
                                                                        .build())
                                                        .setFreqInfo(
                                                                new FreqInfo.Builder()
                                                                        .setFreq(0)
                                                                        .setMemFreq(0)
                                                                        .build())
                                                        .build())
                                        .addGpu(
                                                new Gpu.Builder()
                                                        .setIndex(5)
                                                        .setBus(0)
                                                        .setName("GTX 1070 Ti")
                                                        .setTemp(70)
                                                        .setFans(
                                                                new FanInfo.Builder()
                                                                        .setCount(1)
                                                                        .addSpeed(51)
                                                                        .setSpeedUnits("%")
                                                                        .build())
                                                        .setFreqInfo(
                                                                new FreqInfo.Builder()
                                                                        .setFreq(0)
                                                                        .setMemFreq(0)
                                                                        .build())
                                                        .build())
                                        .addGpu(
                                                new Gpu.Builder()
                                                        .setIndex(6)
                                                        .setBus(0)
                                                        .setName("GTX 1070 Ti")
                                                        .setTemp(67)
                                                        .setFans(
                                                                new FanInfo.Builder()
                                                                        .setCount(1)
                                                                        .addSpeed(50)
                                                                        .setSpeedUnits("%")
                                                                        .build())
                                                        .setFreqInfo(
                                                                new FreqInfo.Builder()
                                                                        .setFreq(0)
                                                                        .setMemFreq(0)
                                                                        .build())
                                                        .build())
                                        .addGpu(
                                                new Gpu.Builder()
                                                        .setIndex(7)
                                                        .setBus(0)
                                                        .setName("GTX 1070 Ti")
                                                        .setTemp(68)
                                                        .setFans(
                                                                new FanInfo.Builder()
                                                                        .setCount(1)
                                                                        .addSpeed(50)
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