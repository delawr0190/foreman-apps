package mn.foreman.pickaxe.configuration.yml;

import mn.foreman.pickaxe.configuration.Configuration;
import mn.foreman.pickaxe.configuration.ConfigurationFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.File;
import java.io.IOException;

/** A {@link ConfigurationFactory} that produces a {@link YmlConfiguration}. */
public class YmlConfigurationFactory
        implements ConfigurationFactory {

    @Override
    public Configuration load(final String path)
            throws IOException {
        final ObjectMapper objectMapper =
                new ObjectMapper(new YAMLFactory());
        return objectMapper.readValue(
                new File(path),
                YmlConfiguration.class);
    }
}