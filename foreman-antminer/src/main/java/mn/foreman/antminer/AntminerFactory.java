package mn.foreman.antminer;

import mn.foreman.antminer.response.VersionResponseStrategy;
import mn.foreman.antminer.response.antminer.StatsResponseStrategy;
import mn.foreman.antminer.response.braiins.BraiinsResponseStrategy;
import mn.foreman.cgminer.CgMiner;
import mn.foreman.cgminer.PoolsResponseStrategy;
import mn.foreman.cgminer.RateMultiplyingDecorator;
import mn.foreman.cgminer.ResponseStrategy;
import mn.foreman.cgminer.request.CgMinerCommand;
import mn.foreman.model.Miner;
import mn.foreman.model.MinerFactory;

import com.google.common.collect.ImmutableMap;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * A {@link MinerFactory} implementation that parses a configuration and creates
 * a {@link Miner} that will query an Antminer running either stock firmware or
 * braiins.
 */
public class AntminerFactory
        implements MinerFactory {

    /** The hash rate multiplier. */
    private final BigDecimal multiplier;

    /**
     * Constructor.
     *
     * @param multiplier The hash rate multiplier.
     */
    public AntminerFactory(final BigDecimal multiplier) {
        this.multiplier = multiplier;
    }

    @Override
    public Miner create(final Map<String, String> config) {
        final String apiIp = config.get("apiIp");
        final String apiPort = config.get("apiPort");

        final CgMiner antminer =
                toMiner(
                        apiIp,
                        apiPort,
                        Arrays.asList(
                                ImmutableMap.of(
                                        CgMinerCommand.POOLS,
                                        new PoolsResponseStrategy()),
                                ImmutableMap.of(
                                        CgMinerCommand.STATS,
                                        new RateMultiplyingDecorator(
                                                "STATS",
                                                "GHS av",
                                                this.multiplier,
                                                new RateMultiplyingDecorator(
                                                        "STATS",
                                                        "GHS 5s",
                                                        this.multiplier,
                                                        new StatsResponseStrategy())))));

        final ResponseStrategy braiinsStrategy =
                new BraiinsResponseStrategy();
        final CgMiner braiins =
                toMiner(
                        apiIp,
                        apiPort,
                        Arrays.asList(
                                ImmutableMap.of(
                                        CgMinerCommand.POOLS,
                                        new PoolsResponseStrategy()),
                                ImmutableMap.of(
                                        CgMinerCommand.SUMMARY,
                                        braiinsStrategy),
                                ImmutableMap.of(
                                        CgMinerCommand.FANS,
                                        braiinsStrategy),
                                ImmutableMap.of(
                                        CgMinerCommand.TEMPS,
                                        braiinsStrategy)));

        return toMiner(
                apiIp,
                apiPort,
                Collections.singletonList(
                        ImmutableMap.of(
                                CgMinerCommand.VERSION,
                                new VersionResponseStrategy(
                                        antminer,
                                        braiins))));
    }

    /**
     * Creates a miner with the provided configuration.
     *
     * @param apiIp    The API IP.
     * @param apiPort  The API port.
     * @param requests The requests.
     *
     * @return The new miner.
     */
    private static CgMiner toMiner(
            final String apiIp,
            final String apiPort,
            final List<Map<CgMinerCommand, ResponseStrategy>> requests) {
        return new CgMiner.Builder()
                .setApiIp(apiIp)
                .setApiPort(apiPort)
                .addRequests(requests)
                .build();
    }
}