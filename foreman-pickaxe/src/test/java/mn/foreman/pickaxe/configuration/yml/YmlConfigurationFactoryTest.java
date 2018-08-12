package mn.foreman.pickaxe.configuration.yml;

import mn.foreman.pickaxe.configuration.Configuration;
import mn.foreman.pickaxe.configuration.ConfigurationFactory;

import org.junit.Test;

import java.io.IOException;

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
                        YmlConfigurationFactoryTest.class
                                .getResource("/pickaxe.yml").getPath());

        assertEquals(
                "nicebro",
                configuration.getApiKey());
        assertEquals(
                "http://localhost:80",
                configuration.getForemanApiUrl());
        assertEquals(
                "http://127.0.0.1:8080",
                configuration.getForemanConfigUrl());
    }
}