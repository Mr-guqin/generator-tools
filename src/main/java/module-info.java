module com.koo.generator {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires java.desktop;

    opens com.koo.generator to javafx.fxml;
    exports com.koo.generator;
}