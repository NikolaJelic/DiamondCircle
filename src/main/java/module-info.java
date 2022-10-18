module com.nikola.diamondcircle {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.nikola.diamondcircle to javafx.fxml;
    exports com.nikola.diamondcircle;
}