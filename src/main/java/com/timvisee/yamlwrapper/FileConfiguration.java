package com.timvisee.yamlwrapper;

import java.io.*;

public abstract class FileConfiguration extends Configuration {

    /**
     * Constructor.
     */
    public FileConfiguration() {
        super();
    }

    /**
     * Save the configuration to the given file.
     *
     * @param file File to save the configuration in.
     * @throws IOException Throws if failed to save the configuration to the given file.
     */
    public void save(File file) throws IOException {
        // The file may not be null
        if (file == null)
            return;

        // Create the parent directories if they don't exist
        file.getParentFile().mkdirs();

        // Get the configuration string
        String data = saveToString();

        // Construct the file writer

        // Save the data
        try (FileWriter writer = new FileWriter(file)) {
            writer.write(data);
        }
    }

    /**
     * Save the configuration to the given file.
     *
     * @param filePath Path of the file to save the configuration in.
     * @throws IOException Throws if failed to save the configuration to the given file.
     */
    public void save(String filePath) throws IOException {
        // Make sure the file path is not null or empty
        if (filePath == null || filePath.equals(""))
            return;

        // Save the file
        save(new File(filePath));
    }

    /**
     * Save the configuration to a string.
     *
     * @return Configuration string.
     */
    public abstract String saveToString();

    /**
     * Load a configuration file from the given file.
     *
     * @param file File to load the configuration from.
     * @throws FileNotFoundException Throws if the file that was given, doesn't exist.
     * @throws IOException           Throws if failed to load the configuration file.
     */
    public void load(File file) throws FileNotFoundException, IOException {
        load(new FileInputStream(file));
    }

    /**
     * Load the configuration from an input stream.
     *
     * @param stream Input stream to load the configuration from.
     * @throws IOException Throws if failed to load the configuration from the given input stream.
     */
    public void load(InputStream stream) throws IOException {
        // Make sure the input stream is not null
        if (stream == null)
            return;

        // Create the proper readers to read the file
        InputStreamReader reader = new InputStreamReader(stream);
        StringBuilder builder = new StringBuilder();

        // Safely read the file from the input reader
        try (BufferedReader input = new BufferedReader(reader)) {
            // Variable to hold the current line
            String line;

            // Read the file line by line
            while ((line = input.readLine()) != null) {
                builder.append(line);
                builder.append('\n');
            }

        }

        // Load the file from the given string
        loadFromString(builder.toString());
    }

    /**
     * Load a configuration from the given file path.
     *
     * @param file Path of the file holding the configuration to load.
     * @throws FileNotFoundException Thrown when the given file path doesn't exist.
     * @throws IOException           Thrown if failed to load the configuration.
     */
    public void load(String file) throws FileNotFoundException, IOException {
        // Make sure the file path is not null
        if (file == null || file.equals(""))
            return;

        // Load the file
        load(new File(file));
    }

    /**
     * Load the configuration from the given string.
     *
     * @param contents Configuration string.
     */
    public abstract void loadFromString(String contents);
}
