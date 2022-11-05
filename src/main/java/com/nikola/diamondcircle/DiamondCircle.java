package com.nikola.diamondcircle;

import com.nikola.diamondcircle.controller.StartController;
import com.nikola.diamondcircle.game.GameRunner;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Logger;

public class DiamondCircle extends Application {
    public static final String LOG_PATH = "data" + File.separator + "logs" + File.separator + "game.log";
    public static final Logger logger;
    public static final Handler handler;

    static {
        try {
            handler = new FileHandler(LOG_PATH);
            logger = Logger.getLogger(DiamondCircle.class.getName());
            logger.addHandler(handler);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

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