module com.appregistros.servicioweb {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.net.http;
    requires com.fasterxml.jackson.databind;

    opens com.appregistros.servicioweb to javafx.fxml;
    opens com.appregistros.servicioweb.model to javafx.base;
    opens com.appregistros.servicioweb.controller to javafx.fxml;
    exports com.appregistros.servicioweb;
}
