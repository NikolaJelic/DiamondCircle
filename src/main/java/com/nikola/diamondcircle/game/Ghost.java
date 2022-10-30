package com.nikola.diamondcircle.game;

public class Ghost extends Thread {
    private boolean isRunning;

    private Board board;

    public Ghost(Board board) {
        this.board = board;
        isRunning = true;
    }

    @Override
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

    private synchronized void awaitCondition() throws InterruptedException {
        while (!isRunning) {
            wait();
        }
    }

    public synchronized void pause() {
        isRunning = false;
    }

    public synchronized void unpause() {
        isRunning = true;
        this.notifyAll();
    }
}
