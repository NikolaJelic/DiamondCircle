package com.nikola.diamondcircle.game;

import com.nikola.diamondcircle.DiamondCircle;
import com.nikola.diamondcircle.player.Player;
import com.nikola.diamondcircle.utils.Card;
import com.nikola.diamondcircle.utils.ColorFactory;
import com.nikola.diamondcircle.utils.Deck;
import com.nikola.diamondcircle.utils.Position;

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
            players.add(new Player(ColorFactory.getColor(), name, board.getValidPositions()));
        }
    }


    public void pickCard() {
        currentCard = cards.getCard();
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
        while (ret.isFinished()) {
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
        int positionWithStep = player.getCurrentFigure().getCurrentPosition() + step;
        Position finalPos = (positionWithStep < board.getValidPositions().size()) ? board.getValidPositions().get(positionWithStep)  : board.getValidPositions().get(board.getValidPositions().size() -1);
        return "Player " + player.getName() + ", figure " + player.getCurrentFigure().getFigureName() + " moves for " + step + " fields, from position " + board.getValidPositions().get(player.getCurrentFigure().getCurrentPosition()) + " to " + finalPos + ".";
    }

    public Card getCurrentCard() {
        return currentCard;
    }


}
