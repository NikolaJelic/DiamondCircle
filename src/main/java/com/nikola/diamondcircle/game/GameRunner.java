package com.nikola.diamondcircle.game;

import com.nikola.diamondcircle.DiamondCircle;
import com.nikola.diamondcircle.controller.GameController;
import javafx.application.Platform;

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

    @Override
    public void run() {
        adjustTime();
        while (!game.isGameOver()) {
            try {
                awaitCondition();
                elapsedTime = pauseOffset + (System.nanoTime() - startTIme) / 1000000000;
                Platform.runLater(() -> gameController.updateTimer(elapsedTime.toString()));
                game.pickCard();
                Platform.runLater(() -> {
                    gameController.drawCard(game.getCurrentCard());
                });
                for(int i = 0; i < game.getCurrentCard().getStep(); ++i){
                    game.
                }

                game.board.removeHoles();
                sleep(100);
            } catch (Exception e) {
                DiamondCircle.logger.log(Level.WARNING, e.fillInStackTrace().toString());
            }
        }


    }

    public static void adjustTime() {
        startTIme = System.nanoTime();
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

}
