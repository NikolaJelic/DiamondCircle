package com.nikola.diamondcircle.game;

import com.nikola.diamondcircle.DiamondCircle;

import java.util.logging.Level;

public class Ghost extends Thread {
    private  static final Object ghostLock = new Object();
    private static boolean isRunning;
    private final Game game;

    public Ghost(Game game) {
        this.game  = game;
        isRunning = true;
    }

    @Override
    public void run() {
        synchronized (ghostLock) {
            while (!game.isGameOver()) {
                try {
                    awaitCondition();
                    game.board.setDiamondPositions();

                    sleep(5000);

                } catch (InterruptedException e) {
                    DiamondCircle.logger.log(Level.SEVERE, "INTERRUPTED!", e.fillInStackTrace().toString());
                    Thread.currentThread().interrupt();
                }
            }
        }

    }

    private void awaitCondition() throws InterruptedException {
        synchronized (ghostLock) {
            while (!isRunning) {
                ghostLock.wait();
            }
        }
    }

    public static void changeState() {
        synchronized (ghostLock) {
            if (isRunning) {
                isRunning = false;
            } else {
                isRunning = true;
                ghostLock.notifyAll();
            }
        }
    }
}
