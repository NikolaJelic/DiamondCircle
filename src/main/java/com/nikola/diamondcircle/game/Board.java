package com.nikola.diamondcircle.game;

import com.nikola.diamondcircle.utils.Position;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Board {
    public  int boardSize;
    public static Integer finalPosition;
    private final Integer HOLE_COUNT = 10;
    private final List<Position> validPositions;

    private int maxHeight, minHeight, maxWidth, minWidth;
    private List<Integer> holePositions;
    private List<Integer> diamondPositions;
    private List<Integer> playerPositions;

    public Board(int size) {
        if (size >= 7 && size <= 10) {
            validPositions = new ArrayList<>();
            boardSize = size;
            minHeight = 0;
            maxHeight = size - 1;
            minWidth = 0;
            maxWidth = size - 1;
            diamondPositions = new ArrayList<>();
            holePositions = new ArrayList<>();
            playerPositions = new ArrayList<>();
            generateValidPositions();
            finalPosition = validPositions.size() - 1;

        } else {
            throw new RuntimeException("Board size not valid!");
        }
    }

    public  int getBoardSize() {
        return boardSize;
    }

    public List<Integer> getDiamondPositions() {
        return diamondPositions;
    }

    public List<Position> getValidPositions() {
        return validPositions;
    }

    public List<Integer> getHolePositions() {
        return holePositions;
    }

    public List<Integer> getPlayerPositions() {
        return playerPositions;
    }

    public void setPlayerPositions(List<Integer> playerPositions) {
        this.playerPositions = playerPositions;
    }

    private void generateValidPositions() {
        if (boardSize % 2 == 0) {
            Position currentPosition = new Position(boardSize / 2 - 1, 0);
            validPositions.add(new Position(boardSize / 2 - 1, 0));

            while (!currentPosition.equals(validPositions.get(finalPosition))) {
                currentPosition = moveEven(currentPosition);
                if (!validPositions.contains(currentPosition))
                    validPositions.add(new Position(currentPosition.getX(), currentPosition.getY()));
            }
        } else {
            Position currentPosition = new Position(boardSize / 2, 0);
            validPositions.add(new Position(boardSize / 2, 0));
            while (!currentPosition.equals(validPositions.get(finalPosition))) {
                currentPosition = moveOdd(currentPosition);
                if (!validPositions.contains(currentPosition))
                    validPositions.add(new Position(currentPosition.getX(), currentPosition.getY()));
            }
        }
    }


    private Position moveOdd(Position currentPosition) {
        int middle = boardSize / 2;
        if (currentPosition.getY() >= minHeight && currentPosition.getY() < middle && currentPosition.getX() >= middle && currentPosition.getX() < maxWidth) {
            if (currentPosition.getY() == minHeight && currentPosition.getX() == middle) ++minHeight;
            currentPosition.moveLowerRight();
        } else if (currentPosition.getY() == minHeight && currentPosition.getX() < middle) {
            currentPosition.moveRight();
        } else if (currentPosition.getY() >= middle && currentPosition.getY() < maxHeight && currentPosition.getX() > middle && currentPosition.getX() <= maxWidth) {
            if (currentPosition.getX() == maxWidth && currentPosition.getY() == middle) --maxWidth;
            currentPosition.moveLowerLeft();
        } else if (currentPosition.getY() > middle && currentPosition.getY() <= maxHeight && currentPosition.getX() > minWidth && currentPosition.getX() <= middle) { //might need th change to <= middle
            if (currentPosition.getY() == maxHeight && currentPosition.getX() == middle) --maxHeight;
            currentPosition.moveUpperLeft();
        } else if (currentPosition.getY() > minHeight && currentPosition.getY() <= middle && currentPosition.getX() >= minWidth && currentPosition.getX() < middle) {
            if (currentPosition.getX() == minWidth && currentPosition.getY() == middle) ++minWidth;
            currentPosition.moveUpperRight();
        }
        return new Position(currentPosition.getX(), currentPosition.getY());
    }

    private Position moveEven(Position currentPosition) {
        int middle = boardSize / 2;
        if (currentPosition.getY() == minHeight && currentPosition.getX() < middle) {
            currentPosition.moveRight();
        } else if (currentPosition.getY() >= minHeight && currentPosition.getY() < middle - 1 && currentPosition.getX() >= middle && currentPosition.getX() < maxWidth) {
            if (currentPosition.getY() == minHeight && currentPosition.getX() == middle) {
                ++minHeight;
            }
            currentPosition.moveLowerRight();
        } else if (currentPosition.getY() < middle && currentPosition.getX() == maxWidth) {
            currentPosition.moveDown();
        } else if (currentPosition.getY() >= middle && currentPosition.getY() < maxHeight && currentPosition.getX() <= maxWidth && currentPosition.getX() > middle) {
            if (currentPosition.getX() == maxWidth && currentPosition.getY() == middle) {
                --maxWidth;
            }
            currentPosition.moveLowerLeft();
        } else if (currentPosition.getY() == maxHeight && currentPosition.getX() == middle) {
            currentPosition.moveLeft();
        } else if (currentPosition.getY() > middle && currentPosition.getY() <= maxHeight && currentPosition.getX() < middle && currentPosition.getX() >= minWidth) {
            if (currentPosition.getY() == maxHeight && currentPosition.getX() == middle - 1) {
                --maxHeight;
            }
            currentPosition.moveUpperLeft();
        } else if (currentPosition.getY() == middle && currentPosition.getX() == minWidth) {
            currentPosition.moveUp();
        } else if (currentPosition.getY() < middle && currentPosition.getY() > minHeight && currentPosition.getX() >= minWidth && currentPosition.getX() < middle - 2) {
            if (currentPosition.getX() == minWidth && currentPosition.getY() == middle - 1) {
                ++minWidth;
            }
            currentPosition.moveUpperRight();
        }
        return new Position(currentPosition.getX(), currentPosition.getY());
    }

    public void setDiamonds(List<Integer> diamonds) {
        diamondPositions = diamonds;
    }


    public synchronized void setHoles() {
        holePositions = new ArrayList<>();
        for (int i = 0; i < HOLE_COUNT; ++i) {
            holePositions.add(new Random().nextInt(validPositions.size()));
        }
    }



    public GameObject getObjectAtPosition(int position){
        try {
            if(position >= 0 && position <= finalPosition){
                if(playerPositions.contains(position)){
                    return GameObject.FIGURE;
                } else if (diamondPositions.contains(position)) {
                    diamondPositions.remove(position); //picked up
                    return GameObject.COIN;
                } else if (holePositions.contains(position)) {
                    return GameObject.HOLE;
                }else{
                    return GameObject.EMPTY;
                }
            }else{
                throw new IndexOutOfBoundsException("Position not valid");
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return null;
    }


}