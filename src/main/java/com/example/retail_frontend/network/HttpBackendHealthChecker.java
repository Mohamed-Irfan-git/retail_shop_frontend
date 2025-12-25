package com.example.retail_frontend.network;

import com.example.retail_frontend.config.AppConfig;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpBackendHealthChecker implements BackendHealthChecker {

    @Override
    public boolean isBackendAlive() {
        String url = AppConfig.getServerBaseUrl() + "/health";
        return testUrl(url);
    }

    @Override
    public boolean isBackendAlive(String ip, String port, boolean useSsl) {
        String protocol = useSsl ? "https" : "http";
        String url = protocol + "://" + ip + ":" + port + "/health";
        return testUrl(url);
    }

    private boolean testUrl(String urlString) {
        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(3000); // 3 seconds timeout
            conn.setReadTimeout(3000);
            conn.setRequestMethod("GET");
            conn.connect();
            int code = conn.getResponseCode();
            return code >= 200 && code < 300;
        } catch (Exception e) {
            return false;
        }
    }
}
