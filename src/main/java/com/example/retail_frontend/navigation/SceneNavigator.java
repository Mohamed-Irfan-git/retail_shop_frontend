package com.example.retail_frontend.navigation;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public final class SceneNavigator {

    private static Stage stage;

    private SceneNavigator() {}

    public static void init(Stage primaryStage) {
        stage = primaryStage;
        stage.initStyle(StageStyle.UNDECORATED);
    }



    public static void goTo(String fxmlPath, String cssPath) {
        try {
            Parent root = FXMLLoader.load(
                    SceneNavigator.class.getResource("/fxml/" + fxmlPath)

            );
            Scene scene = new Scene(root);

            if (cssPath != null && !cssPath.isEmpty()) {
                scene.getStylesheets().add(
                        SceneNavigator.class.getResource(cssPath).toExternalForm()
                );
            }

            stage.setScene(scene);
            stage.sizeToScene();
            stage.centerOnScreen();



        } catch (Exception e) {
            throw new RuntimeException("Navigation error: " + fxmlPath, e);
        }
    }

}