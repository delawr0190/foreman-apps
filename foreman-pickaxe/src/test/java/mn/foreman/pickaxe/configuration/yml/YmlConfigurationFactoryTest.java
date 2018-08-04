package mn.foreman.pickaxe.configuration.yml;

import mn.foreman.pickaxe.configuration.Configuration;
import mn.foreman.pickaxe.configuration.ConfigurationFactory;

import com.google.common.collect.ImmutableMap;
import org.junit.Test;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

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

        int minerIndex = 1;

        final List<Map<String, String>> antConfigs =
                configuration.getAntminerConfigs();
        assertEquals(
                2,
                antConfigs.size());
        validate(
                minerIndex++,
                antConfigs.get(0),
                ImmutableMap.of(
                        "type",
                        "antminer_l3"));
        validate(
                minerIndex++,
                antConfigs.get(1),
                ImmutableMap.of(
                        "type",
                        "antminer_b3"));

        validateGeneric(
                minerIndex++,
                configuration.getBminerConfigs());

        validateGeneric(
                minerIndex++,
                configuration.getCastxmrConfigs());

        validateGeneric(
                minerIndex++,
                configuration.getCcminerConfigs());

        validateGeneric(
                minerIndex++,
                configuration.getClaymoreConfigs(),
                ImmutableMap.of(
                        "type",
                        "zec",
                        "apiPassword",
                        "password"));

        validateGeneric(
                minerIndex++,
                configuration.getCryptoDredgeConfigs());

        validateGeneric(
                minerIndex++,
                configuration.getDstmConfigs());

        validateGeneric(
                minerIndex++,
                configuration.getEthminerConfigs());

        validateGeneric(
                minerIndex++,
                configuration.getEwbfConfigs());

        validateGeneric(
                minerIndex++,
                configuration.getExcavatorConfigs());

        validateGeneric(
                minerIndex++,
                configuration.getJceminerConfigs());

        validateGeneric(
                minerIndex++,
                configuration.getPhoenixConfigs(),
                ImmutableMap.of(
                        "apiPassword",
                        "password"));

        validateGeneric(
                minerIndex++,
                configuration.getSrbminerConfigs());

        validateGeneric(
                minerIndex++,
                configuration.getTrexConfigs());

        validateGeneric(
                minerIndex++,
                configuration.getXmrigConfigs());

        validateGeneric(
                minerIndex,
                configuration.getZenemyConfigs());
    }

    /**
     * Validates that the provided configuration map contains the expected
     * values.
     *
     * @param index      The miner index.
     * @param config     The configuration.
     * @param additional Anything additional that's expected to be in the
     *                   config.
     */
    private static void validate(
            final int index,
            final Map<String, String> config,
            final Map<String, String> additional) {
        assertEquals(
                "miner " + index,
                config.get("name"));
        assertEquals(
                "0.0.0." + index,
                config.get("apiIp"));
        assertEquals(
                Integer.toString(index),
                config.get("apiPort"));
        assertTrue(
                config.entrySet().containsAll(additional.entrySet()));
    }

    /**
     * Validates that the provided configurations contain the expected values.
     *
     * @param index      The miner index.
     * @param configs    The configurations.
     * @param additional Anything additional that's expected to be in the
     *                   config.
     */
    private static void validateGeneric(
            final int index,
            final List<Map<String, String>> configs,
            final Map<String, String> additional) {
        assertEquals(
                1,
                configs.size());
        final Map<String, String> config = configs.get(0);
        validate(
                index,
                config,
                additional);
    }

    /**
     * Validates that the provided configurations contain the expected values.
     *
     * @param index   The miner index.
     * @param configs The configurations.
     */
    private static void validateGeneric(
            final int index,
            final List<Map<String, String>> configs) {
        validateGeneric(
                index,
                configs,
                Collections.emptyMap());
    }
}