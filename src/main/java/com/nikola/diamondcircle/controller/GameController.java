package com.nikola.diamondcircle.controller;

import com.nikola.diamondcircle.game.Game;
import com.nikola.diamondcircle.game.GameObject;
import com.nikola.diamondcircle.game.GameRunner;
import com.nikola.diamondcircle.player.Player;
import com.nikola.diamondcircle.utils.Card;
import com.nikola.diamondcircle.utils.Position;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

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
        for (int i = 0; i < game.board.finalPosition; ++i) {
            GameObject object = game.board.getObjectAtPosition(i);
            if (object != GameObject.FIGURE) {
                ImageView sprite = new ImageView(object.getTexture());
                var pos = game.board.getValidPositions().get(i);
                board.add(sprite, pos.getX(),pos.getY(),1,1);
            }
        }
      for(var player : game.players){
            Position pos = game.board.getValidPositions().get(player.getCurrentFigure().getCurrentPosition());
            Color figureColor = web(player.getColor().getColorValue());
            Lighting lightingEffect = new Lighting(new Light.Distant(40, 100, figureColor));
            ImageView sprite = new ImageView(player.getCurrentFigure().getTexturePath());
            sprite.setEffect(lightingEffect);
            board.add(sprite,pos.getX(), pos.getY(),1,1);
        }
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


}
