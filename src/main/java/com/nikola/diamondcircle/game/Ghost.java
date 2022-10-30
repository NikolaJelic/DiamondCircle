package com.nikola.diamondcircle.game;

import com.nikola.diamondcircle.DiamondCircle;

import java.util.logging.Level;

public class Ghost extends Thread {
    private final Object ghostLock = new Object();
    private boolean isRunning;
    private final Board board;

    public Ghost(Board board) {
        this.board = board;
        isRunning = true;
    }

    @Override
    public void run() {
        synchronized (ghostLock) {
            try {
                awaitCondition();
            } catch (InterruptedException e) {
                DiamondCircle.logger.log(Level.SEVERE, "INTERRUPTED!", e.fillInStackTrace().toString());
                Thread.currentThread().interrupt();
            }
        }
        board.setDiamondPositions();
        try {
            sleep(5000);
        } catch (InterruptedException e) {
            DiamondCircle.logger.log(Level.SEVERE, "INTERRUPTED!", e.fillInStackTrace().toString());
            Thread.currentThread().interrupt();
        }
    }

    private void awaitCondition() throws InterruptedException {
        synchronized (ghostLock) {
            while (!isRunning) {
                ghostLock.wait();
            }
        }
    }

    public synchronized void pause() {
        isRunning = false;
    }

    public void unpause() {
        synchronized (ghostLock) {
            isRunning = true;
            ghostLock.notifyAll();
        }
    }
}
