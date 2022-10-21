package com.nikola.diamondcircle.player;

import com.nikola.diamondcircle.game.Board;
import com.nikola.diamondcircle.player.figure.Figure;
import com.nikola.diamondcircle.player.figure.FigureFactory;
import com.nikola.diamondcircle.utils.Color;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private final Color color;
    private final String name;
    private final List<Figure> figures;

    public Player(Color color, String name) {
        this.color = color;
        this.name = name;
        this.figures = new ArrayList<>();
        FigureFactory figureFactory = new FigureFactory();
        for (int i = 0; i < 4; ++i) {
            try {
                figures.add(figureFactory.getRandomFigure(Board.finalPosition));
            } catch (Exception e) {
                //TODO LOGGER
                System.out.println(e.getMessage());
            }
        }
    }

    public Figure getCurrentFigure() {
        try {
            return figures.get(0);
        }catch (NullPointerException e){
            System.out.println(e.getMessage());
        }
      return null;
    }

    public void useNextFigure(){
        try {
           if(getCurrentFigure().isFinished() || !getCurrentFigure().isAlive()){
               figures.remove(0);
           }
        }catch (NullPointerException e){
            System.out.println(e.getMessage());
        }
    }
}
