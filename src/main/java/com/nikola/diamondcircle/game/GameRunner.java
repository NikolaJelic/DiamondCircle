package com.nikola.diamondcircle.game;

import com.nikola.diamondcircle.DiamondCircle;
import com.nikola.diamondcircle.controller.GameController;
import com.nikola.diamondcircle.player.Player;
import javafx.application.Platform;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
        while (!game.isGameOver()) {
            try {
                //check if paused
                awaitCondition();

                //track time
                elapsedTime = pauseOffset + (System.nanoTime() - startTIme) / 1000000000;
                Platform.runLater(() -> gameController.updateTimer(elapsedTime.toString()));

                //pick card and check holes
                game.pickCard();
                Platform.runLater(() -> {
                    gameController.drawCard(game.getCurrentCard());
                    gameController.drawBoard();
                });


                //move player
                Player currentPlayer = game.getCurrentPlayer();



                Platform.runLater(() -> gameController.updateMessage(game.generateMoveMessage(currentPlayer, game.getCurrentCard().getStep())));

                for (int i = 0; i < game.getCurrentCard().getStep(); ++i) {
                    elapsedTime = pauseOffset + (System.nanoTime() - startTIme) / 1000000000;
                    Platform.runLater(() -> {
                        gameController.updateTimer(elapsedTime.toString());
                    });

                    game.makeMove(currentPlayer);
                    Platform.runLater(() -> gameController.drawBoard());
                    sleep(10);
                    currentPlayer.getCurrentFigure().addVisitedField(currentPlayer.getCurrentFigure().getCurrentPosition());
                    currentPlayer.useNextFigure();
                }

                //check for end of turn collisions
                for (int i = 0; i < game.players.size() - 1; ++i) {
                    if (Collections.frequency(game.board.getPlayerPositions(), currentPlayer.getCurrentFigure().getCurrentPosition()) > 1) {
                        game.makeMove(currentPlayer);
                        Platform.runLater(() -> gameController.drawBoard());
                        sleep(1000);
                        currentPlayer.getCurrentFigure().addVisitedField(currentPlayer.getCurrentFigure().getCurrentPosition());
                        currentPlayer.useNextFigure();
                    }
                }
                currentPlayer.getCurrentFigure().interact(game.board.getObjectAtPosition(currentPlayer.getCurrentFigure().getCurrentPosition()));
                currentPlayer.getCurrentFigure().interact(game.board.getObjectAtPosition(currentPlayer.getCurrentFigure().getCurrentPosition()));
                currentPlayer.getCurrentFigure().interact(game.board.getObjectAtPosition(currentPlayer.getCurrentFigure().getCurrentPosition()));
                currentPlayer.useNextFigure();
                //Interact 3 times to check for overlap at the end
                game.nextPlayer();

                System.out.println(currentPlayer.toString());


            } catch (Exception e) {
                DiamondCircle.logger.log(Level.WARNING, e.fillInStackTrace().toString());
            }
        }
        if(game.isGameOver()){
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

    public void writeFile(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy_MM_dd_HH_mm_ss");
        LocalDateTime now = LocalDateTime.now();
        String name =  dtf.format(now) + ".txt";
        String prefix = "data"+ File.separator+"games"+File.separator;
        File file = new File(prefix+name);
        try( BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
           for(Player player : game.players){
               writer.write(player.toString());
           }
        }catch (IOException e){
            DiamondCircle.logger.log(Level.WARNING, e.fillInStackTrace().toString());
        }
    }

}
