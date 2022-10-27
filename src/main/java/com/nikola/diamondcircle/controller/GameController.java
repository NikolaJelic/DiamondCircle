package com.nikola.diamondcircle.controller;

import com.nikola.diamondcircle.game.Game;
import com.nikola.diamondcircle.game.GameRunner;
import com.nikola.diamondcircle.player.Player;
import com.nikola.diamondcircle.utils.Card;
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

import java.util.Arrays;

import static javafx.scene.paint.Color.web;

public class GameController {
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

    private Game game;
    private GameRunner gameRunner;
    private Image cardImage;


    public GameController(GameRunner gameRunner) {
        this.gameRunner = gameRunner;
        game = gameRunner.getGame();
        //TODO INIT LISTS, FIX PATHS
    }


    @FXML
    public void initialize() {
        board = new GridPane();
        playerList = new ListView<>();
        currentCard = new ImageView();
        figureList = new ListView<>();
        time = new Label("Text");
        try {
            for (Player player : game.players) {
                System.out.println(player.getName() + " Name");
                playerList.getItems().add(new Label(player.getName()));
            }
            drawPlayers();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        //  drawCard(Card.BACK);
        // drawFigureList();
        // drawBoard();
    }


    public void updateTimer() {
        time.setText(gameRunner.getTime());
    }

    public void drawPlayers() {
        playerList = new ListView<>();
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

            currentCard = new ImageView();
            Image cardImage = new Image(String.valueOf(getClass().getResource(card.getCard())));
            currentCard.setImage(cardImage);
            currentCard.setFitHeight(240);
            currentCard.setPreserveRatio(true);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            //TODO log
        }
    }

    public void drawBoard() {
        for (var validPos : game.board.getValidPositions()) {
            try {
                ImageView field = new ImageView(String.valueOf(getClass().getResource("com/nikola/diamondcircle/assets/drops/Ground.png")));
                board.add(field, validPos.getX(), validPos.getY());
            } catch (Exception e) {
                System.out.println(Arrays.toString(e.getStackTrace()) + "error");
            }
        }
    }

    public void changeRunState() {

    }
}
