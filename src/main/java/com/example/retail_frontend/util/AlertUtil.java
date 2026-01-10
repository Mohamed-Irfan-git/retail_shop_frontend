package com.example.retail_frontend.util;

import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

public final class AlertUtil {

    private AlertUtil() {}

    private static final Duration SHORT = Duration.seconds(3);
    private static final Duration LONG  = Duration.seconds(4);

    public static void info(String msg) {
        base("Information", msg)
                .hideAfter(SHORT)
                .showInformation();
    }

    public static void success(String msg) {
        base("Success", msg)
                .hideAfter(SHORT)
                .showConfirm();
    }

    public static void error(String msg) {
        base("Error", msg)
                .hideAfter(LONG)
                .showError();
    }

    public static void warning(String msg) {
        base("Warning", msg)
                .hideAfter(LONG)
                .showWarning();
    }


    private static Notifications base(String title, String msg) {
        Notifications notification = Notifications.create()
                .title(title)
                .text(msg)
                .position(Pos.CENTER);   // CENTER of window

        Window owner = getCurrentWindow();
        if (owner != null) {
            notification.owner(owner); // VERY IMPORTANT
        }

        return notification;
    }


    private static Window getCurrentWindow() {
        return Stage.getWindows()
                .stream()
                .filter(Window::isShowing)
                .findFirst()
                .orElse(null);
    }
}
