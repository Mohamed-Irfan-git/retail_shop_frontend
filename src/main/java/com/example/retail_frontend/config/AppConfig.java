package com.example.retail_frontend.config;

import java.nio.file.Path;

// app config class
public final class AppConfig {

    private static final ConfigRepository repo =
            new FileConfigRepository(Path.of("fxml/config/app.properties"));

    private AppConfig() {}

    // Get full backend URL using saved config
    public static String getServerBaseUrl() {
        String ip = repo.get("server.ip");
        String port = repo.get("server.port");
        boolean ssl = Boolean.parseBoolean(repo.get("server.ssl", "false"));
        return (ssl ? "https://" : "http://") + ip + ":" + port;
    }

    // Generic getter
    public static String get(String key) {
        return repo.get(key);
    }

    public static String get(String key, String defaultValue) {
        String value = repo.get(key);
        return value != null ? value : defaultValue;
    }

    // Generic setter
    public static void set(String key, String value) {
        repo.set(key, value);
    }
}
