package com.nikola.diamondcircle.player.figure;

import com.nikola.diamondcircle.game.GameObject;
import com.nikola.diamondcircle.utils.Color;

import static java.lang.Thread.sleep;

public class RegularFigure extends Figure {

    public RegularFigure(Integer maxPosition, Color color) {
        super(maxPosition, color);
    }

    @Override
    public String getTexturePath() {

        return getPathPrefix() + "regular.png";
    }

    @Override
    public Integer getDistance(Integer steps) {
        return diamondCount + steps;
    }

    @Override
    public void interact(GameObject gameObject) {
        switch (gameObject) {
            case HOLE -> setAlive(false);
            case COIN -> diamondCount++;
        }
    }

    @Override
    protected String getType() {
        return "regular";
    }
}
