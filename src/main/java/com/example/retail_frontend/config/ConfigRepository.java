package com.example.retail_frontend.config;

public interface ConfigRepository {

    /**
     * Get a value by key. Returns null if not present.
     */
    String get(String key);

    /**
     * Get a value by key, or return default if not present.
     */
    default String get(String key, String defaultValue) {
        String value = get(key);
        return value != null ? value : defaultValue;
    }

    /**
     * Save a key-value pair.
     */
    void set(String key, String value);

    /**
     * Check if a key exists in the repository.
     */
    default boolean containsKey(String key) {
        return get(key) != null;
    }
}
