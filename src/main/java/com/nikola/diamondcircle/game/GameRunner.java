package com.nikola.diamondcircle.game;

import javafx.animation.AnimationTimer;

import java.util.List;

import static java.lang.Thread.sleep;

public class GameRunner extends AnimationTimer {
    private Game game;
    private Long elapsedTime;
    private Long startTIme;
    private  Long pauseOffset;

    public GameRunner(Integer boardSize, List<String> playerNames) {
        game = new Game(boardSize, playerNames);
        this.elapsedTime = 0l;
        this.startTIme = System.nanoTime();
        this.pauseOffset = 0l;
    }


    @Override
    public void handle(long now) {
        elapsedTime = pauseOffset + (now - startTIme) / 100000000;
        game.pickCard();
        try {
            sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void start() {
        startTIme = System.nanoTime();
        pauseOffset = elapsedTime;
        super.start();

    }
}
