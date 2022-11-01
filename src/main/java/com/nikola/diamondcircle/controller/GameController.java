package com.nikola.diamondcircle.controller;

import com.nikola.diamondcircle.DiamondCircle;
import com.nikola.diamondcircle.game.Board;
import com.nikola.diamondcircle.game.Game;
import com.nikola.diamondcircle.game.GameObject;
import com.nikola.diamondcircle.game.GameRunner;
import com.nikola.diamondcircle.player.Player;
import com.nikola.diamondcircle.utils.Card;
import com.nikola.diamondcircle.utils.Position;
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
import javafx.stage.Stage;

import java.util.logging.Level;

import static javafx.scene.paint.Color.web;

public class GameController {
    private final Game game;
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
    public ListView resultList;
    @FXML
    public ImageView currentCard;
    @FXML
    public GridPane board;
    @FXML
    public GridPane figureInfo;


    public GameController(Game game) {
        this.game = game;

    }


    @FXML
    public void initialize() {
        try {
            drawPlayers();
            drawCard(Card.BACK);
            drawFigureList();
        } catch (Exception e) {
            System.out.println(e.getMessage());
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
            Image cardImage = new Image(card.getCard());
            currentCard.setImage(cardImage);
            currentCard.setFitHeight(240);
            currentCard.setPreserveRatio(true);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            //TODO log
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
            if (!player.isFinished()) {
                Position pos = game.board.getValidPositions().get(player.getCurrentFigure().getCurrentPosition());
                Color figureColor = web(player.getColor().getColorValue());
                Lighting lightingEffect = new Lighting(new Light.Distant(40, 100, figureColor));
                ImageView sprite = new ImageView(player.getCurrentFigure().getTexturePath());
                sprite.setEffect(lightingEffect);
                board.add(sprite, pos.getX(), pos.getY(), 1, 1);
            }
        }
    }

    public void updateMessage(String message) {
        cardDescription.setText(message);
    }

    @FXML
    public void changeRunState() {
        GameRunner.changeState();
        if (pauseButton.getText().equals("Pause")) {
            pauseButton.setText("Resume");
        } else {
            pauseButton.setText("Pause");
        }
    }


    @FXML
    public void openFigureHistory() {
        var selected = figureList.getSelectionModel().getSelectedIndex();
        int playerIndex = selected / game.players.size();
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

}
