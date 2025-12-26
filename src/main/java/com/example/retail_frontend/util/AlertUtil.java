package com.example.retail_frontend.util;

import javafx.scene.control.Alert;
import javafx.stage.Window;
import javafx.stage.Stage;

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

        // Center alert on the current active window
        Window owner = getCurrentWindow();
        if (owner != null) {
            alert.initOwner(owner);
        }

        alert.showAndWait();
    }

    private static Window getCurrentWindow() {
        return Stage.getWindows()
                .stream()
                .filter(Window::isShowing)
                .findFirst()
                .orElse(null);
    }
}
