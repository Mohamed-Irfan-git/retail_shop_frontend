package com.example.retail_frontend.config;

import java.io.*;
import java.nio.file.*;
import java.util.Properties;

public class FileConfigRepository implements ConfigRepository {

    private final Properties props = new Properties();
    private final Path path;

    public FileConfigRepository(Path path) {
        this.path = path;
        load();
    }

    /**
     * Load properties from file
     */
    private void load() {
        if (Files.exists(path)) {
            try (InputStream in = Files.newInputStream(path)) {
                props.load(in);
            } catch (IOException e) {
                System.err.println("Failed to load config: " + e.getMessage());
            }
        }
    }

    @Override
    public String get(String key) {
        return props.getProperty(key);
    }

    @Override
    public void set(String key, String value) {
        if (key == null || value == null) return;
        props.setProperty(key, value);
        save();
    }

    @Override
    public String get(String key, String defaultValue) {
        return props.getProperty(key, defaultValue);
    }

    @Override
    public boolean containsKey(String key) {
        return props.containsKey(key);
    }

    /**
     * Save properties to file
     */
    private void save() {
        try {
            // Ensure parent directories exist
            Files.createDirectories(path.getParent());

            try (OutputStream out = Files.newOutputStream(path)) {
                props.store(out, "Retail ERP Client Config");
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to save config", e);
        }
    }
}
