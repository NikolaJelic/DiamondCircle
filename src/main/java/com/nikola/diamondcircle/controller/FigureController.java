package com.nikola.diamondcircle.controller;


import com.nikola.diamondcircle.game.GameObject;
import com.nikola.diamondcircle.utils.Position;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.util.List;

public class FigureController {
    List<Integer> visited;
    List<Position> validPositions;

    @FXML
    GridPane travelledPath;

    FigureController(List<Integer> visited, List<Position> validPositions) {
        this.visited = visited;
        this.validPositions = validPositions;
    }

    @FXML
    public void initialize() {
        for (Integer pos : visited) {
            Position visitedPosition = validPositions.get(pos);
            ImageView sprite = new ImageView(GameObject.VISITED.getTexture());
            travelledPath.add(sprite, visitedPosition.getX(), visitedPosition.getY(), 1, 1);
        }
        travelledPath.centerShapeProperty();

    }
}
