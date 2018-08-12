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
    public void testLoadWithGroup()
            throws IOException {
        runLoadTest(
                "/pickaxe_group.yml",
                "group");
    }

    /** Tests loading of a configuration file. */
    @Test
    public void testLoadWithoutGroup()
            throws IOException {
        runLoadTest(
                "/pickaxe_no-group.yml",
                null);
    }

    /**
     * Runs a {@link YmlConfigurationFactory#load(String)} test.
     *
     * @param fileName      The file to load.
     * @param expectedGroup The expected group.
     *
     * @throws IOException on failure to read the file.
     */
    private static void runLoadTest(
            final String fileName,
            final String expectedGroup) throws IOException {
        final ConfigurationFactory configurationFactory =
                new YmlConfigurationFactory();
        final Configuration configuration =
                configurationFactory.load(
                        YmlConfigurationFactoryTest.class
                                .getResource(fileName).getPath());

        assertEquals(
                "nicebro",
                configuration.getApiKey());
        assertEquals(
                expectedGroup,
                configuration.getGroup());
        assertEquals(
                "http://localhost:80",
                configuration.getForemanApiUrl());
        assertEquals(
                "http://127.0.0.1:8080",
                configuration.getForemanConfigUrl());
    }
}