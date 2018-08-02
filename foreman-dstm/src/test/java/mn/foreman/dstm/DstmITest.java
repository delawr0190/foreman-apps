package mn.foreman.dstm;

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

/** Runs an integration tests using {@link Dstm} against a fake API. */
public class DstmITest
        extends AbstractApiITest {

    /** Constructor. */
    public DstmITest() {
        super(
                new Dstm(
                        "dstm",
                        "127.0.0.1",
                        2222),
                new FakeRpcMinerServer(
                        2222,
                        ImmutableMap.of(
                                "{\"id\":1,\"method\":\"getstat\"}",
                                new RpcHandler(
                                        "{\"id\":1,\"result\":[{\"gpu_id\":0,\"gpu_name\":\"GeForce GTX 1070 Ti\",\"gpu_pci_bus_id\":1,\"gpu_pci_device_id\":0,\"gpu_uuid\":\"GPU-74f69592-e713-a562-23e0-1ea7cfaa3581\",\"temperature\":73,\"fan_speed\":73,\"sol_ps\":510.97,\"avg_sol_ps\":510.97,\"sol_pw\":2.77,\"avg_sol_pw\":2.81,\"power_usage\":184.20,\"avg_power_usage\":182.13,\"accepted_shares\":1,\"rejected_shares\":0,\"latency\":330},{\"gpu_id\":1,\"gpu_name\":\"GeForce GTX 1070 Ti\",\"gpu_pci_bus_id\":2,\"gpu_pci_device_id\":0,\"gpu_uuid\":\"GPU-e122ee54-2b6c-7c90-159c-461042739e29\",\"temperature\":70,\"fan_speed\":69,\"sol_ps\":497.26,\"avg_sol_ps\":497.26,\"sol_pw\":2.79,\"avg_sol_pw\":2.86,\"power_usage\":178.48,\"avg_power_usage\":173.72,\"accepted_shares\":2,\"rejected_shares\":0,\"latency\":218},{\"gpu_id\":2,\"gpu_name\":\"GeForce GTX 1070 Ti\",\"gpu_pci_bus_id\":3,\"gpu_pci_device_id\":0,\"gpu_uuid\":\"GPU-fdcb19f8-5b76-4257-14eb-dfae28e38a35\",\"temperature\":69,\"fan_speed\":68,\"sol_ps\":508.57,\"avg_sol_ps\":508.57,\"sol_pw\":3.05,\"avg_sol_pw\":3.12,\"power_usage\":166.52,\"avg_power_usage\":162.99,\"accepted_shares\":0,\"rejected_shares\":0,\"latency\":0},{\"gpu_id\":3,\"gpu_name\":\"GeForce GTX 1070 Ti\",\"gpu_pci_bus_id\":4,\"gpu_pci_device_id\":0,\"gpu_uuid\":\"GPU-1eb35e35-a50d-dc88-561a-e3ccc27e5d68\",\"temperature\":69,\"fan_speed\":69,\"sol_ps\":499.70,\"avg_sol_ps\":499.70,\"sol_pw\":2.85,\"avg_sol_pw\":2.97,\"power_usage\":175.27,\"avg_power_usage\":168.21,\"accepted_shares\":0,\"rejected_shares\":0,\"latency\":0},{\"gpu_id\":4,\"gpu_name\":\"GeForce GTX 1070 Ti\",\"gpu_pci_bus_id\":6,\"gpu_pci_device_id\":0,\"gpu_uuid\":\"GPU-e5c039fe-2bb7-21f3-6be6-e81e5311add9\",\"temperature\":69,\"fan_speed\":69,\"sol_ps\":510.77,\"avg_sol_ps\":510.77,\"sol_pw\":2.87,\"avg_sol_pw\":2.99,\"power_usage\":178.19,\"avg_power_usage\":170.88,\"accepted_shares\":0,\"rejected_shares\":0,\"latency\":0},{\"gpu_id\":5,\"gpu_name\":\"GeForce GTX 1070 Ti\",\"gpu_pci_bus_id\":9,\"gpu_pci_device_id\":0,\"gpu_uuid\":\"GPU-b4fed5c3-2ee4-c774-6aaa-27c891ad50ef\",\"temperature\":70,\"fan_speed\":70,\"sol_ps\":500.97,\"avg_sol_ps\":500.97,\"sol_pw\":2.75,\"avg_sol_pw\":2.96,\"power_usage\":182.14,\"avg_power_usage\":169.52,\"accepted_shares\":1,\"rejected_shares\":0,\"latency\":187},{\"gpu_id\":6,\"gpu_name\":\"GeForce GTX 1070 Ti\",\"gpu_pci_bus_id\":10,\"gpu_pci_device_id\":0,\"gpu_uuid\":\"GPU-8a6980e1-7fd4-ab58-a660-5ffbbdd1a667\",\"temperature\":69,\"fan_speed\":68,\"sol_ps\":513.16,\"avg_sol_ps\":513.16,\"sol_pw\":2.84,\"avg_sol_pw\":3.03,\"power_usage\":180.55,\"avg_power_usage\":169.12,\"accepted_shares\":0,\"rejected_shares\":0,\"latency\":0},{\"gpu_id\":7,\"gpu_name\":\"GeForce GTX 1070 Ti\",\"gpu_pci_bus_id\":11,\"gpu_pci_device_id\":0,\"gpu_uuid\":\"GPU-9e89c3d2-8f0e-bdde-99a4-0f5a2a7c90f6\",\"temperature\":62,\"fan_speed\":61,\"sol_ps\":295.41,\"avg_sol_ps\":295.41,\"sol_pw\":2.32,\"avg_sol_pw\":2.40,\"power_usage\":127.06,\"avg_power_usage\":123.13,\"accepted_shares\":0,\"rejected_shares\":0,\"latency\":0}],\"uptime\":44,\"contime\":40,\"server\":\"eu1-zcash.flypool.org\",\"port\":3333,\"user\":\"t1Ja3TR6QBRDUd897sLn1YSeKc8HnWmvHbu\",\"version\":\"0.6.1\",\"error\": null}"))),
                new MinerStats.Builder()
                        .setName("dstm")
                        .setApiIp("127.0.0.1")
                        .setApiPort(2222)
                        .addPool(
                                new Pool.Builder()
                                        .setName("eu1-zcash.flypool.org:3333")
                                        .setPriority(0)
                                        .setStatus(true, true)
                                        .setCounts(4, 0, 0)
                                        .build())
                        .addRig(
                                new Rig.Builder()
                                        .setName("dstm")
                                        .setHashRate(new BigDecimal("3836.80999999999994543031789362430572509765625"))
                                        .addGpu(
                                                new Gpu.Builder()
                                                        .setName("GeForce GTX 1070 Ti")
                                                        .setIndex(0)
                                                        .setBus(1)
                                                        .setTemp(73)
                                                        .setFans(
                                                                new FanInfo.Builder()
                                                                        .setCount(1)
                                                                        .addSpeed(73)
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
                                                        .setName("GeForce GTX 1070 Ti")
                                                        .setIndex(1)
                                                        .setBus(2)
                                                        .setTemp(70)
                                                        .setFans(
                                                                new FanInfo.Builder()
                                                                        .setCount(1)
                                                                        .addSpeed(69)
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
                                                        .setName("GeForce GTX 1070 Ti")
                                                        .setIndex(2)
                                                        .setBus(3)
                                                        .setTemp(69)
                                                        .setFans(
                                                                new FanInfo.Builder()
                                                                        .setCount(1)
                                                                        .addSpeed(68)
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
                                                        .setName("GeForce GTX 1070 Ti")
                                                        .setIndex(3)
                                                        .setBus(4)
                                                        .setTemp(69)
                                                        .setFans(
                                                                new FanInfo.Builder()
                                                                        .setCount(1)
                                                                        .addSpeed(69)
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
                                                        .setName("GeForce GTX 1070 Ti")
                                                        .setIndex(4)
                                                        .setBus(6)
                                                        .setTemp(69)
                                                        .setFans(
                                                                new FanInfo.Builder()
                                                                        .setCount(1)
                                                                        .addSpeed(69)
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
                                                        .setName("GeForce GTX 1070 Ti")
                                                        .setIndex(5)
                                                        .setBus(9)
                                                        .setTemp(70)
                                                        .setFans(
                                                                new FanInfo.Builder()
                                                                        .setCount(1)
                                                                        .addSpeed(70)
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
                                                        .setName("GeForce GTX 1070 Ti")
                                                        .setIndex(6)
                                                        .setBus(10)
                                                        .setTemp(69)
                                                        .setFans(
                                                                new FanInfo.Builder()
                                                                        .setCount(1)
                                                                        .addSpeed(68)
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
                                                        .setName("GeForce GTX 1070 Ti")
                                                        .setIndex(7)
                                                        .setBus(11)
                                                        .setTemp(62)
                                                        .setFans(
                                                                new FanInfo.Builder()
                                                                        .setCount(1)
                                                                        .addSpeed(61)
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