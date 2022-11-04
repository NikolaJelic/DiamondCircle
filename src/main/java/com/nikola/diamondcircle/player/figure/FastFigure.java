package com.nikola.diamondcircle.player.figure;

import com.nikola.diamondcircle.game.GameObject;
import com.nikola.diamondcircle.utils.Color;

import static java.lang.Thread.sleep;

public class FastFigure extends Figure {

    public FastFigure(Integer maxPosition, Color color) {
        super(maxPosition, color);
    }

    @Override
    public String getTexturePath() {
        return getPathPrefix() + "fast.png";
    }

    @Override
    public Integer getDistance(Integer steps) {
        return diamondCount + steps * 2;
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
        return "fast";
    }

}
