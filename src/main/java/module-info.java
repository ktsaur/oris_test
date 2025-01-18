module ru.kpfu.oris_test {
    requires javafx.controls;
    requires javafx.fxml;
    requires cloudinary.core;
    requires com.fasterxml.jackson.databind;


    opens ru.kpfu.oris_test to javafx.fxml;
    exports ru.kpfu.oris_test;
}