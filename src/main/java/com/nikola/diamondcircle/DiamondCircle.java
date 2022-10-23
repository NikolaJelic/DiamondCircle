package com.nikola.diamondcircle;

import com.nikola.diamondcircle.controller.StartController;
import com.nikola.diamondcircle.game.Game;
import com.nikola.diamondcircle.game.GameRunner;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class DiamondCircle extends Application {
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        GameRunner gameRunner = new GameRunner();
        FXMLLoader fxmlLoader = new FXMLLoader(DiamondCircle.class.getResource("/com/nikola/diamondcircle/views/start.fxml"));
        Parent root = fxmlLoader.load();
        primaryStage.setTitle("DiamondCircle");
        primaryStage.setScene(new Scene(root, 500, 500));
        StartController startController = fxmlLoader.getController();
        startController.setGameRunner(gameRunner);
        primaryStage.setResizable(false);
        primaryStage.show();


    }


}