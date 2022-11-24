package com.nikola.diamondcircle.controller;

import com.nikola.diamondcircle.DiamondCircle;
import com.nikola.diamondcircle.game.*;
import com.nikola.diamondcircle.player.Player;
import com.nikola.diamondcircle.player.figure.Figure;
import com.nikola.diamondcircle.utils.Card;
import com.nikola.diamondcircle.utils.Position;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.logging.Level;
import java.util.stream.Stream;

import static javafx.scene.paint.Color.web;

public class GameController {
    private final Game game;
    private final Ghost ghost;
    @FXML
    public Label gameCountLabel;
    @FXML
    public Button pauseButton;
    @FXML
    public Label time;
    @FXML
    public ListView<Label> playerList;
    @FXML
    public ListView<ImageView> figureList;
    @FXML
    public Label cardDescription;
    @FXML
    public ImageView currentCard;
    @FXML
    public GridPane board;
    @FXML
    public Label title;


    public GameController(Game game) {
        this.game = game;
        ghost = new Ghost(game);
        ghost.start();
    }

    @FXML
    public void initialize() {
        try {
            Font titleFont = Font.loadFont(String.valueOf(StartController.class.getResource("/com/nikola/diamondcircle/assets/fonts/DungeonFont.ttf")), 35);
            title.setFont(titleFont);
            gameCountLabel.setText(countGames().toString());
            drawPlayers();
            drawCard(Card.BACK);
            drawFigureList();

        } catch (Exception e) {
            DiamondCircle.logger.log(Level.WARNING, e.fillInStackTrace().toString());
        }
    }

    public void drawPlayers() {
        for (Player player : game.players) {
            Label playerLabel = new Label(player.getName());
            playerLabel.setTextFill(web(player.getColor().getColorValue()));
            playerList.getItems().add(playerLabel);
        }
    }

    public void drawCard(Card card) {
        try {
            Image cardImage = new Image(card.getCardTexturePath());
            currentCard.setImage(cardImage);
            currentCard.setFitHeight(240);
            currentCard.setPreserveRatio(true);
        } catch (Exception e) {
            DiamondCircle.logger.log(Level.WARNING, e.fillInStackTrace().toString());
        }
    }

    public void drawFigureList() {
        for (Player player : game.players) {
            for (var figure : player.getFigures()) {
                Color figureColor = web(player.getColor().getColorValue());
                Lighting lightingEffect = new Lighting(new Light.Distant(40, 100, figureColor));
                ImageView sprite = new ImageView(figure.getTexturePath());
                sprite.setEffect(lightingEffect);
                figureList.getItems().add(sprite);
            }
        }

    }

    public void updateTimer(String elapsedTime) {
        time.setText(elapsedTime);
    }

    public void drawBoard() {
        board.getChildren().clear();
        for (int i = 0; i < Board.finalPosition; ++i) {
            GameObject object = game.board.getObjectAtPosition(i);
            if (object != GameObject.FIGURE) {

                ImageView sprite = new ImageView(object.getTexture());
                var pos = game.board.getValidPositions().get(i);
                board.add(sprite, pos.getX(), pos.getY(), 1, 1);
            }
        }
        for (var player : game.players) {
            Position pos = game.board.getValidPositions().get(player.getCurrentFigure().getCurrentPosition());
            ImageView sprite;

            if (!player.isFinished()) {
                Color figureColor = web(player.getColor().getColorValue());
                Lighting lightingEffect = new Lighting(new Light.Distant(40, 100, figureColor));
                Figure figure = player.getCurrentFigure();
                if (figure.isAlive() && !figure.isFinished()) {
                    sprite = new ImageView(player.getCurrentFigure().getTexturePath());
                    sprite.setEffect(lightingEffect);
                } else {
                    sprite = new ImageView(GameObject.EMPTY.getTexture());
                }

                board.add(sprite, pos.getX(), pos.getY(), 1, 1);
            }else {
                sprite = new ImageView(GameObject.EMPTY.getTexture());
                board.add(sprite, pos.getX(), pos.getY(), 1, 1);

            }

        }
    }

    public void updateMessage(String message) {
        cardDescription.setText(message);
    }

    @FXML
    public void changeRunState(ActionEvent actionEvent) {
        GameRunner.changeState();
        Ghost.changeState();
        if (pauseButton.getText().equals("Pause")) {
            pauseButton.setText("Resume");
        } else {
            pauseButton.setText("Pause");
        }
    }


    @FXML
    public void openFigureHistory() {
        var selected = figureList.getSelectionModel().getSelectedIndex();
        int playerIndex = selected / 4;
        int figure = selected - playerIndex * 4;
        var visited = game.players.get(playerIndex).getFigures().get(figure).getVisited();

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/nikola/diamondcircle/views/figure.fxml"));
            FigureController figureController = new FigureController(visited, game.board.getValidPositions());
            loader.setController(figureController);
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("DiamondCircle");
            stage.setScene(new Scene(root, 600, 600));
            stage.setResizable(false);
            stage.show();
        } catch (Exception e) {
            DiamondCircle.logger.log(Level.SEVERE, e.fillInStackTrace().toString());
        }

    }

    @FXML
    public void listGames(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/nikola/diamondcircle/views/fileLister.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Diamond Circle");
            stage.setScene(new Scene(root, 600, 800));

            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            DiamondCircle.logger.log(Level.WARNING, e.fillInStackTrace().toString());
        }
    }

    private Integer countGames() {
        String destinationFolder = "data" + File.separator + "games" + File.separator;
        File destFolder = new File(destinationFolder);
        int ret = 0;
        if (destFolder.isDirectory()) {
            ret = Stream.of(Objects.requireNonNull(destFolder.listFiles())).filter(File::isFile).map(File::getName).toList().size();
        }
        return ret;
    }

}
