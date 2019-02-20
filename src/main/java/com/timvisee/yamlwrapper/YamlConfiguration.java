package com.timvisee.yamlwrapper;

import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.error.YAMLException;
import org.yaml.snakeyaml.representer.Representer;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public class YamlConfiguration extends FileConfiguration {

    /**
     * Indentation size for YAML configurations that are saved.
     */
    private static final int YAML_INDENT_SIZE = 4;

    /**
     * YAML flow style.
     */
    private static final DumperOptions.FlowStyle YAML_FLOW_STYLE = DumperOptions.FlowStyle.BLOCK;

    /**
     * YAML configuration options.
     */
    private DumperOptions options = new DumperOptions();

    /**
     * YAML representer.
     */
    private Representer representer = new Representer();

    /**
     * YAML instance, holding the configuration.
     */
    private Yaml yaml = new Yaml(new Constructor(), representer, options);

    /**
     * Constructor.
     */
    public YamlConfiguration() {
        // Set the indent format
        options.setIndent(YAML_INDENT_SIZE);

        // Set the default flow style
        options.setDefaultFlowStyle(YAML_FLOW_STYLE);
        representer.setDefaultFlowStyle(YAML_FLOW_STYLE);
    }

    /**
     * Save the YAML configuration to a string.
     *
     * @return String holding the YAML configuration.
     */
    public String saveToString() {
        // Return the configuration string
        return yaml.dump(getValues());
    }

    /**
     * Load a YAML configuration from the given string.
     * An empty configuration object is returned if the configuration in the string is invalid.
     *
     * @param config String holding the YAML configuration.
     */
    public void loadFromString(String config) {
        // Make sure the contents are not null
        if (config == null)
            return;

        // Create a map to store the configuration in
        Map<?, ?> input = null;
        try {
            input = (Map<?, ?>) yaml.load(config);

        } catch (YAMLException | ClassCastException e) {
            e.printStackTrace();
        }

        // Convert sub-maps to sub-sections
        if (input != null)
            convertMapsToSections(input, this);
    }

    /**
     * Load a YAML configuration from the given file path.
     * An empty configuration object is returned if the file doesn't exist or was invalid.
     *
     * @param filePath File path to load the configuration from.
     * @return Loaded YAML configuration.
     */
    public static YamlConfiguration loadFromFile(String filePath) {
        // Create a file instance
        final File file = new File(filePath);

        // Make sure the file exists
        if (!file.isFile())
            return new YamlConfiguration();

        // Load the configuration file and return it
        return loadFromFile(file);
    }

    /**
     * Load a YAML configuration from the given file.
     * An empty configuration object is returned if the file doesn't exist or was invalid.
     *
     * @param file File to load the configuration from.
     * @return Loaded YAML configuration.
     */
    public static YamlConfiguration loadFromFile(File file) {
        // Return an empty configuration if the file is null
        if (file == null)
            return new YamlConfiguration();

        // Create a new configuration instance
        final YamlConfiguration config = new YamlConfiguration();

        // Try to load the configuration file
        try {
            config.load(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Return the configuration
        return config;
    }

    /**
     * Load a YAML configuration from the given input stream.
     * An empty configuration object is returned if the input stream is invalid.
     *
     * @param stream Input stream to load the configuration from.
     * @return Loaded YAML configuration.
     */
    public static YamlConfiguration loadFromStream(InputStream stream) {
        // Return an empty configuration if the stream is null
        if (stream == null)
            return new YamlConfiguration();

        // Create a new configuration instance
        final YamlConfiguration config = new YamlConfiguration();

        // Load the configuration
        try {
            config.load(stream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Return the configuration
        return config;
    }

    /**
     * Convert a map to a configuration section.
     * The parser creates a map holding the configuration.
     * Since we're not working with maps directly, this method can be used to convert these maps to proper configuration sections.
     * Sub-maps are automatically converted to sub-sections.
     *
     * @param input   Input map, that should be converted.
     * @param section Base configuration section.
     */
    private void convertMapsToSections(Map<?, ?> input, ConfigurationSection section) {
        // Loop through the map entries to convert it
        for (Map.Entry<?, ?> entry : input.entrySet()) {
            // Get the key and value
            final String key = entry.getKey().toString();
            final Object value = entry.getValue();

            // Set the value
            if (value instanceof Map)
                convertMapsToSections((Map<?, ?>) value, section.createSection(key));
            else
                section.set(key, value);
        }
    }
}
