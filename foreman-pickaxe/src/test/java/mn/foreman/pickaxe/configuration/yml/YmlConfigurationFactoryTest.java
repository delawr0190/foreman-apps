package mn.foreman.pickaxe.configuration.yml;

import mn.foreman.pickaxe.configuration.Configuration;
import mn.foreman.pickaxe.configuration.ConfigurationFactory;

import org.junit.Test;

import java.io.IOException;
import java.util.List;
import java.util.Map;

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

        final List<Map<String, String>> antConfigs =
                configuration.getAntminerConfigs();
        assertEquals(
                2,
                antConfigs.size());

        final Map<String, String> antConfig1 =
                antConfigs.get(0);
        assertEquals(
                "miner 1",
                antConfig1.get("name"));
        assertEquals(
                "antminer_l3",
                antConfig1.get("type"));
        assertEquals(
                "127.0.0.1",
                antConfig1.get("apiIp"));
        assertEquals(
                "42069",
                antConfig1.get("apiPort"));

        final Map<String, String> antConfig2 =
                antConfigs.get(1);
        assertEquals(
                "miner 2",
                antConfig2.get("name"));
        assertEquals(
                "antminer_b3",
                antConfig2.get("type"));
        assertEquals(
                "128.0.0.1",
                antConfig2.get("apiIp"));
        assertEquals(
                "42070",
                antConfig2.get("apiPort"));
    }
}