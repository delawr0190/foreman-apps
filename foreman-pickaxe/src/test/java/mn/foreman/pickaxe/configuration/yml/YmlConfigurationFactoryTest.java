package mn.foreman.pickaxe.configuration.yml;

import mn.foreman.pickaxe.configuration.CgMinerConfig;
import mn.foreman.pickaxe.configuration.Configuration;
import mn.foreman.pickaxe.configuration.ConfigurationFactory;

import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static junit.framework.TestCase.assertEquals;

/** Unit tests for the {@link YmlConfiguration}. */
public class YmlConfigurationFactoryTest {

    /** Tests loading of a configuration file. */
    @Test
    public void testLoad()
            throws IOException {
        final ConfigurationFactory configurationFactory =
                new YmlConfigurationFactory();
        final Configuration configuration =
                configurationFactory.load(
                        getClass().getResource("/pickaxe.yml").getPath());

        assertEquals(
                "nicebro",
                configuration.getApiKey());
        assertEquals(
                "http://localhost:80",
                configuration.getForemanApiUrl());
        assertEquals(
                60,
                configuration.getPollFrequencyInSeconds());

        final List<CgMinerConfig> cgminerConfigs =
                configuration.getCgminerConfigs();
        assertEquals(
                2,
                cgminerConfigs.size());

        final CgMinerConfig cgMinerConfig1 =
                cgminerConfigs.get(0);
        assertEquals(
                "cgminer 1",
                cgMinerConfig1.getName());
        assertEquals(
                "127.0.0.1",
                cgMinerConfig1.getApiIp());
        assertEquals(
                42069,
                cgMinerConfig1.getApiPort());

        final CgMinerConfig cgMinerConfig2 =
                cgminerConfigs.get(1);
        assertEquals(
                "cgminer 2",
                cgMinerConfig2.getName());
        assertEquals(
                "128.0.0.1",
                cgMinerConfig2.getApiIp());
        assertEquals(
                42070,
                cgMinerConfig2.getApiPort());
    }
}