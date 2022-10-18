package com.nikola.diamondcircle.utils;
public class Position {
    private int x;
    private int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void moveRight(){
        x++;
    }
    public void moveLeft(){--x;}
    public void moveUp(){
        --y;
    }
    public void moveDown(){
        ++y;
    }
    public  void  moveUpperRight(){
        moveRight();
        moveUp();
    }
    public  void moveLowerRight(){
        moveDown();
        moveRight();
    }
    public void moveUpperLeft(){
        moveLeft();
        moveUp();
    }

    public void moveLowerLeft(){
        moveLeft();
        moveDown();
    }

    @Override
    public String toString() {
        return "(" + x + "," + y + ")";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Position) {
            return ((Position) obj).x == this.x && ((Position) obj).y == this.y;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 3 * hash + x;
        hash = 5 * hash + y;
        return hash;
    }

}
