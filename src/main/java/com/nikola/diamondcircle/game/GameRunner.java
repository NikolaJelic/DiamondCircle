package com.nikola.diamondcircle.game;

import javafx.animation.AnimationTimer;

import java.util.List;

import static java.lang.Thread.sleep;

public class GameRunner extends AnimationTimer {
    private Game game;
    private Long elapsedTime;
    private Long startTIme;
    private Long pauseOffset;


    public GameRunner() {
        this.elapsedTime = 0l;
        this.startTIme = System.nanoTime();
        this.pauseOffset = 0l;
    }

    public GameRunner(Integer boardSize, List<String> playerNames) {
        this();
        game = new Game(boardSize, playerNames);
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

    public void setGame(Game game) {
        this.game = game;
    }
}
