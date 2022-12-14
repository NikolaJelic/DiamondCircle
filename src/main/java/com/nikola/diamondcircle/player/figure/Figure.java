package com.nikola.diamondcircle.player.figure;

import com.nikola.diamondcircle.game.GameObject;
import com.nikola.diamondcircle.utils.Color;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public abstract class Figure {
    private static Integer counter = 1;
    protected String figureName;
    protected Integer currentPosition;
    protected Integer diamondCount;
    protected Integer maxPosition;
    protected Color color;
    protected List<Integer> visitedPositions;
    private Boolean alive;
    private Boolean finished;

    protected Figure(Integer maxPosition, Color color) {
        this.maxPosition = maxPosition;
        this.color = color;
        this.currentPosition = 0;
        visitedPositions = new ArrayList<>();
        visitedPositions.add(currentPosition);
        diamondCount = 0;
        alive = true;
        finished = false;
        figureName = counter.toString();
        ++counter;
    }


    protected String getPathPrefix() {
        return "com" + File.separator + "nikola" + File.separator + "diamondcircle" + File.separator + "assets" + File.separator + "figures" + File.separator;

    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(Boolean finished) {
        this.finished = finished;
    }


    public Boolean isAlive() {
        return alive;
    }

    public void setAlive(Boolean alive) {
        this.alive = alive;
    }

    public Integer getCurrentPosition() {
        return currentPosition;
    }


    public abstract String getTexturePath();

    public abstract Integer getDistance(Integer steps);

    public void move() {
        if (!isFinished() && isAlive()) {
            if (currentPosition < maxPosition) {
                ++currentPosition;
            } else {
                setFinished(true);
            }
        }
    }

    public abstract void interact(GameObject gameObject);

    public void addVisitedField(Integer position) {
        if (!visitedPositions.contains(position)) {
            visitedPositions.add(position);
        }
    }

    public String getFigureName() {
        return figureName;
    }

    @Override
    public String toString() {
        return "Figure " + figureName + "[" + getType() + ',' + color + "] " + " finished: " + finished + " move time: " + visitedPositions.size() + " | Travelled path: ";
    }

    protected abstract String getType();

    public List<Integer> getVisited() {
        return visitedPositions;
    }
}
