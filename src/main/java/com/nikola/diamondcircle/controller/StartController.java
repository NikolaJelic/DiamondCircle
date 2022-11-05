package com.nikola.diamondcircle.controller;

import com.nikola.diamondcircle.DiamondCircle;
import com.nikola.diamondcircle.game.Game;
import com.nikola.diamondcircle.game.GameRunner;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class StartController {
    public Label titleLabel;
    public TextField playerInputField;
    public Button addPlayerButton;

    public ListView<String> playerList;
    public Button startButton;
    public TextField sizeInput;
    Integer size;
    private List<String> playerNames;
    private GameRunner gameRunner;
    private boolean canStart = false;

    @FXML
    public void initialize() {
        Font titleFont = Font.loadFont(String.valueOf(StartController.class.getResource("/com/nikola/diamondcircle/assets/fonts/DungeonFont.ttf")), 35);
        Font font = Font.loadFont(String.valueOf(StartController.class.getResource("/com/nikola/diamondcircle/assets/fonts/monogram.ttf")), 20);
        titleLabel.setFont(titleFont);
        playerInputField.setFont(font);
        addPlayerButton.setFont(font);
        startButton.setFont(font);
        sizeInput.setFont(font);
        playerNames = new ArrayList<>();

    }


    public void addPlayer(ActionEvent actionEvent) {
        try {
            String name = playerInputField.getText();
            if (!playerNames.contains(name)) {
                if (playerNames.size() >= 4) {
                    throw new IndexOutOfBoundsException("Cannot create more than 4 players.");
                }
                playerNames.add(name);
                playerList.getItems().add(name);
                playerInputField.setText("");
            }

        } catch (Exception e) {
            DiamondCircle.logger.log(Level.WARNING, e.fillInStackTrace().toString());
        }
    }

    public void startGame(ActionEvent actionEvent) {

        size = Integer.valueOf(sizeInput.getText());
        if (playerNames.size() >= 2 && playerNames.size() <= 4 && size >= 7 && size <= 10) {
            canStart = true;
        }
        try {
            if (!canStart) throw new Exception("Conditions not met");
            Game game = new Game(size, playerNames);
            gameRunner.setGame(game);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/nikola/diamondcircle/views/game.fxml"));
            GameController gameController = new GameController(game);
            loader.setController(gameController);
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("DiamondCircle");

            gameRunner.setGameController(gameController);
            stage.setScene(new Scene(root, 1000, 800));
            stage.setResizable(false);
            stage.show();
            gameRunner.start();
            stage.setOnCloseRequest(e -> {
                Platform.exit();
                System.exit(0);
            });
            ((Node) (actionEvent.getSource())).getScene().getWindow().hide();
        } catch (Exception e) {
            DiamondCircle.logger.log(Level.SEVERE, e.fillInStackTrace().toString());
        }

    }


    public void setGameRunner(GameRunner gameRunner) {
        this.gameRunner = gameRunner;
    }
}