package com.nikola.diamondcircle.player.figure;

public abstract class Figure {
    private Boolean alive;
    private Boolean finished;
    protected Integer currentPosition;
    protected Integer diamondCount;
    protected Integer maxPosition;

    protected Figure() {
        this.currentPosition = 0;
        diamondCount = 0;
        alive = true;
        maxPosition =0;
    }

    public Figure(Integer maxPosition){
        this.maxPosition = maxPosition;
        this.currentPosition = 0;
        diamondCount = 0;
        alive = true;
        maxPosition =0;
    }

    public Boolean isFinished() {
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

    public void incrementCurrentPosition(){
        ++currentPosition;
    }

    public void collectDiamond(){
        ++diamondCount;
    }
    public Integer getDiamondCount(){
        return diamondCount;
    }

    public abstract String getTexturePath();

    public abstract void move(Integer steps);

}
