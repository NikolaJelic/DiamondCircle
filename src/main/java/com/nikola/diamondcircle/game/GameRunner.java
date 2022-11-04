package com.nikola.diamondcircle.game;

import com.nikola.diamondcircle.DiamondCircle;
import com.nikola.diamondcircle.controller.GameController;
import com.nikola.diamondcircle.player.Player;
import com.nikola.diamondcircle.utils.Card;
import javafx.application.Platform;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;


public class GameRunner extends Thread {
    private static final Object pauseLock = new Object();
    public static Long elapsedTime = 0L;
    public static Long startTIme;
    private static boolean isRunning = true;
    private static Long pauseOffset = 0L;
    private Game game;
    private GameController gameController;

    private Ghost ghost;


    public GameRunner(Integer boardSize, List<String> playerNames) {
        this();
        game = new Game(boardSize, playerNames);
    }

    public GameRunner() {
    }

    public static void changeState() {
        synchronized (pauseLock) {
            if (isRunning) {
                isRunning = false;
                pauseOffset = elapsedTime;
            } else {
                isRunning = true;
                pauseLock.notifyAll();
                adjustTime();
            }
        }
    }

    public static void adjustTime() {
        startTIme = System.nanoTime();
    }

    @Override
    public void run() {
        adjustTime();
        int endPosition = 0;
        while (!game.isGameOver()) {
            try {
                //check if paused
                awaitCondition();

                //track time
                elapsedTime = pauseOffset + (System.nanoTime() - startTIme) / 1000000000;
                Platform.runLater(() -> gameController.updateTimer(elapsedTime.toString()));

                // update player positions
                updatePlayerPositions();

                //pick card and check holes
                game.pickCard();
                if (game.getCurrentCard() != Card.SPECIAL) {
                    Platform.runLater(() -> {
                        gameController.drawCard(game.getCurrentCard());
                        gameController.drawBoard();
                    });

                    //move player
                    Player currentPlayer = game.getCurrentPlayer();
                    System.out.println(currentPlayer.getName() + "Is playing");

                    endPosition = currentPlayer.getCurrentFigure().getDistance(game.getCurrentCard().getStep());
                    System.out.println(game.board.getPlayerPositions());
                    for (int i = 0; i < game.players.size(); ++i) {
                        if (game.board.getObjectAtPosition(currentPlayer.getCurrentFigure().getCurrentPosition() + endPosition) == GameObject.FIGURE) {
                            System.out.println("Spot is taken");
                            ++endPosition;
                        }
                    }

                    int finalEndPosition = endPosition;
                    Platform.runLater(() -> {
                        gameController.updateMessage(game.generateMoveMessage(currentPlayer, finalEndPosition));
                    });

                    for (int i = 0; i < endPosition; ++i) {
                        awaitCondition();
                        elapsedTime = pauseOffset + (System.nanoTime() - startTIme) / 1000000000;
                        Platform.runLater(() -> {
                            gameController.updateTimer(elapsedTime.toString());
                        });

                        game.makeMove(currentPlayer);
                        updatePlayerPositions();
                        Platform.runLater(() -> gameController.drawBoard());
                        sleep(1000);
                        currentPlayer.getCurrentFigure().addVisitedField(currentPlayer.getCurrentFigure().getCurrentPosition());

                    }
                    System.out.println(currentPlayer.toString());
                    game.nextPlayer();
                    currentPlayer.useNextFigure();

                }


            } catch (Exception e) {
                DiamondCircle.logger.log(Level.WARNING, e.fillInStackTrace().toString());
            }
        }
        if (game.isGameOver()) {
            writeFile();
        }
    }

    private void awaitCondition() throws InterruptedException {
        synchronized (pauseLock) {
            while (!isRunning) {
                pauseLock.wait();
            }
        }
    }

    public void setGameController(GameController gameController) {
        this.gameController = gameController;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public void writeFile() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy_MM_dd_HH_mm_ss");
        LocalDateTime now = LocalDateTime.now();
        String name = dtf.format(now) + ".txt";
        String prefix = "data" + File.separator + "games" + File.separator;
        File file = new File(prefix + name);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (Player player : game.players) {
                writer.write(player.toString());
            }
        } catch (IOException e) {
            DiamondCircle.logger.log(Level.WARNING, e.fillInStackTrace().toString());
        }
    }

    private void updatePlayerPositions() {
        List<Integer> playerPositions = new ArrayList<>();
        for (Player player : game.players) {
            playerPositions.add(player.getCurrentFigure().getCurrentPosition());
        }
        game.board.setPlayerPositions(playerPositions);
    }

}
