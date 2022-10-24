package com.nikola.diamondcircle.controller;

import com.nikola.diamondcircle.game.Game;
import com.nikola.diamondcircle.game.GameRunner;
import com.nikola.diamondcircle.player.Player;
import com.nikola.diamondcircle.utils.Card;
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
    public Label gameCountLabel;
    public Button pauseButton;
    public Label time;
    public ListView<Label> playerList;
    public ListView<ImageView> figureList;
    public Label cardDescription;
    public ListView resultList;
    public ImageView currentCard;
    public GridPane board;

    private Game game;
    private GameRunner gameRunner;
    private Image cardImage;


    public GameController(GameRunner gameRunner) {
        this.gameRunner = gameRunner;
        game = gameRunner.getGame();
        //gameRunner.start();
    }


    public void updateTimer() {
        time.setText(gameRunner.getTime());
    }

    public void drawPlayers() {
        for (Player player : game.players) {
            Label playerLabel = new Label(player.getName());
            playerLabel.setTextFill(web(player.getColor().getColorValue()));
            playerList.getItems().add(playerLabel);
        }
    }

    public void drawFigureList() {
        for (Player player : game.players) {
            for (var figure : player.getFigures()) {
                Color figureColor = web(player.getColor().getColorValue());
                Lighting lightingEffect = new Lighting(new Light.Distant(40, 100, figureColor));
                ImageView sprite = new ImageView(String.valueOf(getClass().getResource(figure.getTexturePath())));
                sprite.setEffect(lightingEffect);
                figureList.getItems().add(sprite);
            }
        }

    }

    public void drawCard(Card card) {
        try {
            Image cardImage = new Image(String.valueOf(getClass().getResource(card.getCard())));
            currentCard.setImage(cardImage);
            currentCard.setFitHeight(240);
            currentCard.setPreserveRatio(true);
        }catch (Exception e){
            //TODO log
        }
    }

    public void drawBoard(){

        for(int i = 0; i <  game.board.boardSize; ++i){
        }
    }

   public void changeRunState(){

   }
}
