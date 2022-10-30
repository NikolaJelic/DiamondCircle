package com.nikola.diamondcircle.game;

import com.nikola.diamondcircle.player.Player;
import com.nikola.diamondcircle.utils.Card;
import com.nikola.diamondcircle.utils.ColorFactory;
import com.nikola.diamondcircle.utils.Deck;

import java.util.ArrayList;
import java.util.List;

public class Game {
    public Board board;
    public List<Player> players;
    private int currentPlayerIndex;
    private final Deck cards;

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
            //TODO Draw Holes
            for (Player player : players) {
                player.getCurrentFigure().interact(board.getObjectAtPosition(player.getCurrentFigure().getCurrentPosition()));
            }
        } else {
            makeMove(currentCard.getStep());
        }
    }

    public void makeMove(Integer steps) {
        Player currentPlayer = getCurrentPlayerIndex();
        currentPlayer.getCurrentFigure().move(steps);
        currentPlayer.getCurrentFigure().interact(board.getObjectAtPosition(currentPlayer.getCurrentFigure().getCurrentPosition()));
        currentPlayer.getCurrentFigure().addVisitedField(currentPlayer.getCurrentFigure().getCurrentPosition());
        System.out.println(currentPlayer.getCurrentFigure().toString() + "at turn " + currentPlayer.getCurrentFigure().getCurrentPosition());
        if (!currentPlayer.getCurrentFigure().isAlive() || currentPlayer.getCurrentFigure().isFinished()) {
            currentPlayer.useNextFigure();
        }

        //TODO update player position
    }

    private Player getCurrentPlayerIndex() {
        ++currentPlayerIndex;
        return players.get(currentPlayerIndex % players.size());
    }

    public boolean isGameOver() {
        for (Player player : players) {
            if(!player.isFinished()){
                return false;
            }
        }
        return true;
    }

    public String generateMoveMessage(Integer startPosition, Player player, Integer step) {
        //TODOD update gui
        return "Player " + player.getName() + ", figure " + player.getCurrentFigure().getFigureName() + " moves for " + step + " fields, from position " + startPosition + " to " + player.getCurrentFigure().getCurrentPosition() + ".";
    }

    public Card getCurrentCard() {
        return currentCard;
    }
}
