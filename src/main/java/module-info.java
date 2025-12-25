module com.example.retail_frontend {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.net.http;

    opens com.example.retail_frontend.ui.login to javafx.fxml;
    opens com.example.retail_frontend.ui.config to javafx.fxml;
    opens com.example.retail_frontend.ui to javafx.fxml;
    // add more if you create dashboard, pos, etc.

    exports com.example.retail_frontend;
}
