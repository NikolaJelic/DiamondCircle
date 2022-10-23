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
    private Integer currentFigure;

    public Player(Color color, String name) {
        this.color = color;
        this.name = name;
        this.currentFigure = 0;
        this.figures = new ArrayList<>();
        FigureFactory figureFactory = new FigureFactory();
        for (int i = 0; i < 4; ++i) {
            try {
                figures.add(figureFactory.getRandomFigure(Board.finalPosition,color ));
            } catch (Exception e) {
                //TODO LOGGER
                System.out.println(e.getMessage());
            }
        }
    }

    public String getName(){
        return name;
    }

    public Figure getCurrentFigure() {
        try {
            return figures.get(currentFigure);
        }catch (NullPointerException e){
            System.out.println(e.getMessage());
        }
      return null;
    }

    public void useNextFigure(){
        try {
           if(getCurrentFigure().isFinished() || !getCurrentFigure().isAlive()){
               if(currentFigure < figures.size() -1){
               ++currentFigure;
               }
           }
        }catch (NullPointerException e){
            System.out.println(e.getMessage());
        }
    }
}
