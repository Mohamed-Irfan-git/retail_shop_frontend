package com.example.retail_frontend.network;

public interface BackendHealthChecker {

    /**
     * Tests backend using saved configuration.
     */
    boolean isBackendAlive();

    /**
     * Tests backend using provided parameters.
     * @param ip server IP or hostname
     * @param port server port
     * @param useSsl whether to use TLS/SSL
     */
    boolean isBackendAlive(String ip, String port, boolean useSsl);
}
