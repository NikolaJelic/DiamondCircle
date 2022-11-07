package com.nikola.diamondcircle.player;

import com.nikola.diamondcircle.DiamondCircle;
import com.nikola.diamondcircle.game.Board;
import com.nikola.diamondcircle.player.figure.Figure;
import com.nikola.diamondcircle.player.figure.FigureFactory;
import com.nikola.diamondcircle.utils.Color;
import com.nikola.diamondcircle.utils.Position;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class Player {
    private final Color color;
    private final String name;
    private final List<Figure> figures;
    private final List<Position> validPositions;

    private int currentFigureIndex;

    private boolean isFinished;

    public Player(Color color, String name, List<Position> validPositions) {
        this.color = color;
        this.name = name;
        this.currentFigureIndex = 0;
        this.isFinished = false;
        this.validPositions = validPositions;
        this.figures = new ArrayList<>();
        FigureFactory figureFactory = new FigureFactory();
        for (int i = 0; i < 4; ++i) {
            try {
                figures.add(figureFactory.getRandomFigure(Board.finalPosition -1, color));
            } catch (Exception e) {
                DiamondCircle.logger.log(Level.WARNING, e.fillInStackTrace().toString());
            }
        }
    }

    public boolean isFinished() {
        return isFinished;
    }

    public String getName() {
        return name;
    }

    public void useNextFigure() {
        try {
            if (getCurrentFigure().isFinished() || !getCurrentFigure().isAlive()) {
                if (currentFigureIndex < figures.size() - 1) {
                    ++currentFigureIndex;
                } else {
                    isFinished = true;
                }
            }
        } catch (NullPointerException e) {
            DiamondCircle.logger.log(Level.WARNING, e.fillInStackTrace().toString());
        }
    }

    public Figure getCurrentFigure() {
        try {
            return figures.get(currentFigureIndex);
        } catch (NullPointerException e) {
            DiamondCircle.logger.log(Level.SEVERE, e.fillInStackTrace().toString());
        }
        return null;
    }


    public Color getColor() {
        return color;
    }

    public List<Figure> getFigures() {
        return figures;
    }

    @Override
    public String toString() {
        StringBuilder player = new StringBuilder("Player " + name + " [" + color + "] " + '\n');
        for (Figure figure : figures) {
            List<Position> visited = new ArrayList<>();
            for(var pos : figure.getVisited()){
                visited.add(validPositions.get(pos));
            }

            player.append(figure.toString()).append(visited).append('\n');
        }
        return player.toString();
    }
}
