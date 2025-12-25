package com.example.retail_frontend.util;

import javafx.scene.control.Alert;

public final class AlertUtil {

    private AlertUtil() {}

    public static void info(String msg) {
        show(Alert.AlertType.INFORMATION, msg);
    }

    public static void error(String msg) {
        show(Alert.AlertType.ERROR, msg);
    }

    private static void show(Alert.AlertType type, String msg) {
        Alert alert = new Alert(type);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
