package com.nikola.diamondcircle.game;

import java.util.concurrent.ScheduledExecutorService;

public class Ghost extends Thread {
    private Boolean isRunning;

    private Board board;

    public Ghost(Board board) {
        this.board = board;
        isRunning = true;
    }

    public void run() {
        synchronized (this) {
            try {
                awaitCondition();
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }
        board.setDiamondPositions();
        try {
            sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void awaitCondition() throws InterruptedException {
        while (!isRunning) {
            wait();
        }
    }

    public void pause() {
        isRunning = false;
    }

    public void unpause() {
        isRunning = true;
        this.notify();
    }
}
