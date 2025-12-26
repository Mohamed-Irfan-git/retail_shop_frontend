package com.example.retail_frontend;



import com.example.retail_frontend.navigation.SceneNavigator;
import com.example.retail_frontend.network.BackendHealthChecker;
import com.example.retail_frontend.network.HttpBackendHealthChecker;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class App extends Application {

    @Override
    public void start(Stage stage) {
        SceneNavigator.init(stage);

        BackendHealthChecker checker = new HttpBackendHealthChecker();

        if (checker.isBackendAlive()) {
            SceneNavigator.goTo("login/LoginView.fxml","/css/LoginStyle.css");
        } else {
            SceneNavigator.goTo("config/ServerConfigView.fxml","/css/ServerConfigStyle.css");
        }

        stage.setTitle("Retail ERP System");
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}