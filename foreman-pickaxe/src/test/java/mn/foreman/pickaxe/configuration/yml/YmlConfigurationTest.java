package mn.foreman.pickaxe.configuration.yml;

import mn.foreman.pickaxe.configuration.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNull;

/** Unit tests for the {@link YmlConfiguration}. */
public class YmlConfigurationTest {

    /** Tests loading of a configuration file. */
    @Test
    public void testParseId()
            throws IOException {
        final Configuration configuration =
                new ObjectMapper(new YAMLFactory()).readValue(
                        new File(
                                YmlConfigurationTest.class.getResource(
                                        "/pickaxe_id.yml").getPath()),
                        YmlConfiguration.class);

        assertEquals(
                "nicebro",
                configuration.getApiKey());
        assertEquals(
                "cafebabe",
                configuration.getPickaxeId());
    }

    /** Tests loading of a configuration file. */
    @Test
    public void testParseNoId()
            throws IOException {
        final Configuration configuration =
                new ObjectMapper(new YAMLFactory()).readValue(
                        new File(
                                YmlConfigurationTest.class.getResource(
                                        "/pickaxe_no-id.yml").getPath()),
                        YmlConfiguration.class);

        assertEquals(
                "nicebro",
                configuration.getApiKey());
        assertNull(
                configuration.getPickaxeId());
    }
}