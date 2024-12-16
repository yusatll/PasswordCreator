module org.password.passwordcreator {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens org.password.passwordcreator to javafx.fxml;
    exports org.password.passwordcreator;
}