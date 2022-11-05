module com.nikola.diamondcircle {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.logging;


    opens com.nikola.diamondcircle to javafx.fxml;
    exports com.nikola.diamondcircle;
    exports  com.nikola.diamondcircle.controller;
    exports com.nikola.diamondcircle.game;
    exports com.nikola.diamondcircle.utils;
    exports com.nikola.diamondcircle.player;
    opens  com.nikola.diamondcircle.controller to javafx.fxml;
}