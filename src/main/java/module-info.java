module com.nikola.diamondcircle {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.nikola.diamondcircle to javafx.fxml;
    exports com.nikola.diamondcircle;
    exports  com.nikola.diamondcircle.controller;
    exports com.nikola.diamondcircle.game;
    exports com.nikola.diamondcircle.utils;
    opens  com.nikola.diamondcircle.controller to javafx.fxml;
}