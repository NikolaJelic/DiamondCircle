package com.nikola.diamondcircle.player.figure;

import com.nikola.diamondcircle.game.GameObject;
import com.nikola.diamondcircle.utils.Color;

import java.util.ArrayList;
import java.util.List;

public abstract class Figure {
    private Boolean alive;
    private static Integer counter = 1;
    protected String figureName;
    private Boolean finished;
    protected Integer currentPosition;
    protected Integer diamondCount;
    protected Integer maxPosition;

    protected  Color color;
    protected List<Integer> visitedPositions;

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

    public void incrementCurrentPosition() {
        ++currentPosition;
    }

    public void collectDiamond() {
        ++diamondCount;
    }

    public Integer getDiamondCount() {
        return diamondCount;
    }

    public abstract String getTexturePath();

    public abstract void move(Integer steps);

    public abstract void interact(GameObject gameObject);

    public void addVisitedField(Integer position) {
        visitedPositions.add(position);
    }

    public String getFigureName() {
        return figureName;
    }

    protected abstract String getType();
    @Override
    public String toString() {
        return "Figure " + figureName + "[" + getType() + ',' + color+ "] | travelled path " + visitedPositions + " finished " + finished;
    }
}
