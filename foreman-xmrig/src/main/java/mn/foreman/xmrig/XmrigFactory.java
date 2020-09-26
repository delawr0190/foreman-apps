package mn.foreman.xmrig;

import mn.foreman.model.AlternatingMiner;
import mn.foreman.model.Miner;
import mn.foreman.model.MinerFactory;
import mn.foreman.xmrig.current.XmrigNew;
import mn.foreman.xmrig.old.XmrigOld;

import java.util.Map;

/**
 * A {@link MinerFactory} implementation that parses a configuration and creates
 * a {@link Miner} that will query an instance of xmrig.
 */
public class XmrigFactory
        implements MinerFactory {

    @Override
    public Miner create(final Map<String, Object> config) {
        final String apiIp = config.get("apiIp").toString();
        final int apiPort = Integer.parseInt(config.get("apiPort").toString());
        return new AlternatingMiner(
                apiIp,
                apiPort,
                // Check the old API first - provides enriched metrics for
                // older miners
                new XmrigOld(
                        apiIp,
                        apiPort),
                new XmrigNew(
                        apiIp,
                        apiPort,
                        config.containsKey("accessToken")
                                ? config.get("accessToken").toString()
                                : null));
    }
}