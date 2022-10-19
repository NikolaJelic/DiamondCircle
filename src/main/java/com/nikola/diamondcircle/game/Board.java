package com.nikola.diamondcircle.game;

import com.nikola.diamondcircle.utils.Position;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Board {
    public static int boardSize;
    public static Integer finalPosition;
    private final Integer HOLE_COUNT = 10;
    private final List<Position> validPositions;

    private int maxHeight, minHeight, maxWidth, minWidth;
    private List<Integer> holeFields;
    private List<Integer> diamondFields;
    private List<Integer> figureFields;

    public Board(int size) {
        if (size >= 7 && size <= 10) {
            validPositions = new ArrayList<>();
            Board.boardSize = size;
            minHeight = 0;
            maxHeight = size - 1;
            minWidth = 0;
            maxWidth = size - 1;
            diamondFields = new ArrayList<>();
            holeFields = new ArrayList<>();
            figureFields = new ArrayList<>();
            generateValidPositions();
            finalPosition = validPositions.size() - 1;

        } else {
            throw new RuntimeException("Board size not valid!");
        }
    }

    public static int getBoardSize() {
        return boardSize;
    }

    public List<Integer> getDiamondFields() {
        return diamondFields;
    }

    public List<Position> getValidPositions() {
        return validPositions;
    }

    public List<Integer> getHoleFields() {
        return holeFields;
    }

    public List<Integer> getFigureFields() {
        return figureFields;
    }

    public void setFigureFields(List<Integer> figureFields) {
        this.figureFields = figureFields;
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
        diamondFields = diamonds;
    }


    public synchronized void setHoles() {
        holeFields = new ArrayList<>();
        for (int i = 0; i < HOLE_COUNT; ++i) {
            holeFields.add(new Random().nextInt(validPositions.size()));
        }
    }


}