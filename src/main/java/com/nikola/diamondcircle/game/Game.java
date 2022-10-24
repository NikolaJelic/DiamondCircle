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
    private int currentPlayer;
    private Deck cards;


    public Game(Integer boardSize, List<String> playerNames) {
        players = new ArrayList<>();
        currentPlayer = 0;
        cards = new Deck();
        board = new Board(boardSize);
        System.out.println(Board.finalPosition + "final position");
        for (String name : playerNames) {
            players.add(new Player(ColorFactory.getColor(), name));
        }
    }

    private Player getCurrentPlayer() {
        ++currentPlayer;
        return players.get(currentPlayer % players.size());
    }

    public void pickCard() {
        Card currentCard = cards.getCard();
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
        Player currentPlayer = getCurrentPlayer();
        currentPlayer.getCurrentFigure().move(steps);
        currentPlayer.getCurrentFigure().interact(board.getObjectAtPosition(getCurrentPlayer().getCurrentFigure().getCurrentPosition()));
        currentPlayer.getCurrentFigure().addVisitedField(currentPlayer.getCurrentFigure().getCurrentPosition());
        if (!currentPlayer.getCurrentFigure().isAlive() || !currentPlayer.getCurrentFigure().isFinished()) {
            currentPlayer.useNextFigure();
        }
        //TODO update player position
    }

    public String generateMoveMessage(Integer startPosition, Player player, Integer step) {
        //TODOD update gui
        return "Player " + player.getName() + ", figure " + player.getCurrentFigure().getFigureName() + " moves for "
                + step + " fields, from position " + startPosition + " to " + player.getCurrentFigure().getCurrentPosition() + ".";
    }

}
