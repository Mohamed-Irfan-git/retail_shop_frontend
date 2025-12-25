package com.example.retail_frontend.ui;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import javafx.stage.Window;

public class CustomTitleBarController {

    @FXML private HBox titleBar;
    @FXML private ImageView appIcon;
    @FXML private Region dragRegion;

    @FXML private Button minimizeButton;
    @FXML private Button closeButton;

    // For dragging
    private double dragOffsetX;
    private double dragOffsetY;

    @FXML
    public void initialize() {
        // Load icon if exists
        try {
            Image icon = new Image(getClass().getResourceAsStream("/images/app-icon.png"));
            if (icon != null) appIcon.setImage(icon);
        } catch (Exception ignored) {}

        // Window actions
        minimizeButton.setOnAction(e -> {
            Stage s = getStage();
            if (s != null) s.setIconified(true);
        });

        closeButton.setOnAction(e -> {
            Window w = titleBar.getScene().getWindow();
            if (w != null) w.hide();
        });

        // Drag window
        dragRegion.setOnMousePressed(this::onMousePressed);
        dragRegion.setOnMouseDragged(this::onMouseDragged);
    }

    private void onMousePressed(MouseEvent evt) {
        Stage s = getStage();
        if (s == null) return;
        dragOffsetX = evt.getScreenX() - s.getX();
        dragOffsetY = evt.getScreenY() - s.getY();
    }

    private void onMouseDragged(MouseEvent evt) {
        Stage s = getStage();
        if (s == null) return;
        s.setX(evt.getScreenX() - dragOffsetX);
        s.setY(evt.getScreenY() - dragOffsetY);
    }

    private Stage getStage() {
        if (titleBar == null || titleBar.getScene() == null) return null;
        Window w = titleBar.getScene().getWindow();
        return (w instanceof Stage) ? (Stage) w : null;
    }
}
