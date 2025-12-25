package com.example.retail_frontend.ui.login;

import com.example.retail_frontend.config.AppConfig;
import com.example.retail_frontend.navigation.SceneNavigator;
import com.example.retail_frontend.util.AlertUtil;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class LoginController {

    @FXML private TextField txtUsername;
    @FXML private PasswordField txtPassword;

    private final HttpClient client = HttpClient.newHttpClient();

    @FXML
    private void handleLogin() {

        String username = txtUsername.getText().trim();
        String password = txtPassword.getText().trim();

        if (username.isEmpty() || password.isEmpty()) {
            AlertUtil.error("Username or password cannot be empty");
            return;
        }

        try {
            // Build backend login URL
            String url = AppConfig.getServerBaseUrl() + "/api/login";

            // Create JSON body
            String json = String.format("{\"username\":\"%s\",\"password\":\"%s\"}",
                    username, password);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                AlertUtil.info("Login successful!");
                // Navigate to main dashboard
                SceneNavigator.goTo("dashboard/DashboardView.fxml",null);
            } else if (response.statusCode() == 401) {
                AlertUtil.error("Invalid username or password");
            } else {
                AlertUtil.error("Login failed: " + response.statusCode());
            }

        } catch (Exception e) {
            e.printStackTrace();
            AlertUtil.error("Cannot connect to backend");
        }
    }
}
