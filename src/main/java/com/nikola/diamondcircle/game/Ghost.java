package com.nikola.diamondcircle.game;

import com.nikola.diamondcircle.DiamondCircle;

import java.util.logging.Level;

public class Ghost extends Thread {
    private static final Object pauseLock = new Object();
    private static boolean isRunning = true;
    private final Game game;


    public Ghost(Game game) {
        this.game = game;
    }

    public static void changeState() {
        synchronized (pauseLock) {
            if (isRunning) {
                isRunning = false;
            } else {
                isRunning = true;
                pauseLock.notifyAll();
            }
        }
    }


    @Override
    public void run() {

        while (!game.isGameOver()) {
            try {
                awaitCondition();
                game.board.setDiamondPositions();
                sleep(5000);

            } catch (Exception e) {
                DiamondCircle.logger.log(Level.WARNING, e.fillInStackTrace().toString());
            }
        }

    }

    private void awaitCondition() throws InterruptedException {
        synchronized (pauseLock) {
            while (!isRunning) {
                pauseLock.wait();
            }
        }
    }


}
