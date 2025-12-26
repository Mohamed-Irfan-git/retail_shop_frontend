package com.example.retail_frontend.ui.config;

import com.example.retail_frontend.config.AppConfig;
import com.example.retail_frontend.navigation.SceneNavigator;
import com.example.retail_frontend.network.BackendHealthChecker;
import com.example.retail_frontend.network.HttpBackendHealthChecker;
import com.example.retail_frontend.util.AlertUtil;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class ServerConfigController {

    @FXML private TextField ip;
    @FXML private TextField port;
    @FXML private CheckBox useSsl;
    @FXML private ComboBox<String> dbType;
    @FXML private TextField dbName;
    @FXML private TextField dbPort;

    @FXML private Circle statusIndicator;
    @FXML private Label statusLabel;
    @FXML private ProgressIndicator testProgress;

    @FXML private Button testBtn;
    @FXML private Button saveTestBtn;
    @FXML private Button cancelBtn;

    private final BackendHealthChecker checker = new HttpBackendHealthChecker();

    @FXML
    public void initialize() {
        // Load saved config
        ip.setText(AppConfig.get("server.ip"));
        port.setText(AppConfig.get("server.port"));
        dbName.setText(AppConfig.get("db.name"));
        dbPort.setText(AppConfig.get("db.port"));
        useSsl.setSelected(Boolean.parseBoolean(AppConfig.get("server.ssl", "false")));
        dbType.getItems().addAll("PostgreSQL", "MySQL", "Oracle", "SQL Server");
        dbType.setValue(AppConfig.get("db.type"));

        // Initialize UI status
        setStatusNeutral();
        testProgress.setVisible(false);
    }

    @FXML
    private void onTest() {
        setStatusNeutral();
        testProgress.setVisible(true);

        new Thread(() -> {
            boolean success = checker.isBackendAlive(ip.getText(), port.getText(), useSsl.isSelected());

            Platform.runLater(() -> {
                testProgress.setVisible(false);
                if (success) setStatusSuccess();
                else setStatusError();
            });
        }).start();
    }

    @FXML
    private void saveAndTest() {
        // Save configuration
        AppConfig.set("server.ip", ip.getText());
        AppConfig.set("server.port", port.getText());
        AppConfig.set("server.ssl", String.valueOf(useSsl.isSelected()));
        AppConfig.set("db.name", dbName.getText());
        AppConfig.set("db.port", dbPort.getText());
        AppConfig.set("db.type", dbType.getValue());

        // Test backend
        testProgress.setVisible(true);
        new Thread(() -> {
            boolean success = checker.isBackendAlive(ip.getText(), port.getText(), useSsl.isSelected());

            Platform.runLater(() -> {
                testProgress.setVisible(false);
                if (success) {
                    setStatusSuccess();
                    AlertUtil.info("Backend connected successfully");
                    SceneNavigator.goTo("login/LoginView.fxml", "/css/LoginStyle.css");


                } else {
                    setStatusError();
                    AlertUtil.error("Cannot connect to backend");
                }
            });
        }).start();
    }

    @FXML
    private void onCancel() {
        // Reset fields
        ip.setText(AppConfig.get("server.ip"));
        port.setText(AppConfig.get("server.port"));
        useSsl.setSelected(Boolean.parseBoolean(AppConfig.get("server.ssl", "false")));
        dbName.setText(AppConfig.get("db.name"));
        dbPort.setText(AppConfig.get("db.port"));
        dbType.setValue(AppConfig.get("db.type"));
        setStatusNeutral();
    }

    // Status helpers
    private void setStatusNeutral() {
        statusIndicator.setFill(Color.GRAY);
        statusLabel.setText("Not tested");
    }

    private void setStatusSuccess() {
        statusIndicator.setFill(Color.web("#27ae60"));
        statusLabel.setText("Connection OK");
    }

    private void setStatusError() {
        statusIndicator.setFill(Color.web("#e74c3c"));
        statusLabel.setText("Connection Failed");
    }
}
