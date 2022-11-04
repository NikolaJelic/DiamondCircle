package com.nikola.diamondcircle.game;

import com.nikola.diamondcircle.DiamondCircle;
import com.nikola.diamondcircle.player.Player;
import com.nikola.diamondcircle.utils.Card;
import com.nikola.diamondcircle.utils.ColorFactory;
import com.nikola.diamondcircle.utils.Deck;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class Game {
    private final Deck cards;
    public Board board;
    public List<Player> players;
    private int currentPlayerIndex;
    private Card currentCard;


    public Game(Integer boardSize, List<String> playerNames) {
        players = new ArrayList<>();
        currentCard = Card.BACK;
        currentPlayerIndex = 0;
        cards = new Deck();
        board = new Board(boardSize);
        for (String name : playerNames) {
            players.add(new Player(ColorFactory.getColor(), name));
        }
    }


    public void pickCard() {
        currentCard = cards.getCard();
        if (currentCard == Card.SPECIAL) {
            board.setHoles();
            for (Player player : players) {
                player.getCurrentFigure().interact(board.getObjectAtPosition(player.getCurrentFigure().getCurrentPosition()));
                player.useNextFigure();
            }
            board.removeHoles();
        }
    }

    public void makeMove(Player currentPlayer) {
        try {
            currentPlayer.getCurrentFigure().move();
            currentPlayer.getCurrentFigure().interact(board.getObjectAtPosition(currentPlayer.getCurrentFigure().getCurrentPosition()));
        } catch (Exception e) {
            DiamondCircle.logger.log(Level.SEVERE, e.fillInStackTrace().toString());
        }
    }

    public Player getCurrentPlayer() {
        Player ret = players.get(currentPlayerIndex % players.size());
        while (ret.isFinished()){
            ret = players.get(++currentPlayerIndex % players.size());
        }
return ret;
    }

    public void nextPlayer() {
        ++currentPlayerIndex;
    }

    public boolean isGameOver() {
        for (Player player : players) {
            if (!player.isFinished()) {
                return false;
            }
        }
        return true;
    }

    public String generateMoveMessage(Player player, Integer step) {
        return "Player " + player.getName() + ", figure " + player.getCurrentFigure().getFigureName() + " moves for " + step + " fields, from position " + player.getCurrentFigure().getCurrentPosition() + " to " + (player.getCurrentFigure().getCurrentPosition() + step) + ".";
    }

    public Card getCurrentCard() {
        return currentCard;
    }


}
